package com.aptech.business.ticketManage.workElectricTwo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workElectricTwo.domain.WorkElectricTwoEntity;
import com.aptech.business.ticketManage.workElectricTwo.service.WorkElectricTwoService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.business.ticketManage.workTicketTwo.service.WorkTicketTwoService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workElectricTwo")
public class WorkElectricTwoController extends BaseController<WorkElectricTwoEntity> {
	
	@Autowired
	private WorkElectricTwoService workElectricTwoService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private WorkTicketTwoService workTicketTwoService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	
	@Override
	public IBaseEntityOperation<WorkElectricTwoEntity> getService() {
		return workElectricTwoService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkElectricTwoEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTwoTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricTwoVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricTwoCombobox", JsonUtil.toJson(comboWorkElectricTwoVO.getOptions()));
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoList", model);
	}
	
	
	/**
	 *	跳转到签发页面
	 */
	@RequestMapping("/getAddQf")
	public ModelAndView getAddQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddQf", model);
	}
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketTwoEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
				
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSp", model);
	}
	/**
	 *	跳转到批准页面
	 */
	@RequestMapping("/getAddPz")
	public ModelAndView getAddPz(HttpServletRequest request){
		String electricId=request.getParameter("electricId");
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddPz", model);
	}
	/**
	 *	跳转到许可页面
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("sdate", df.format(workTicketTwoEntity.getPlandateStart()));
		model.put("edate",df.format(workTicketTwoEntity.getPlandateEnd()));
		model.put("dateXk", df.format(new Date()));
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddXk", model);
	}
	
	/**
	 *	跳转到工作负责人变更页面
	 */
	@RequestMapping("/getAddGzfzrbd")
	public ModelAndView getAddGzfzrbd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,WorkSpResultEnum.WORKPICCHANGE.getCode());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.SIGNATURE.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricTwoEntity.getWorkticketId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除签发人列表中的工作负责人
		Iterator<SysUserEntity> iterator = userList.iterator();
		
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
				
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		List<Long> userids = new ArrayList<Long>();//用户id
		for (SysUserEntity sysUserEntity : userList) {
			userids.add(sysUserEntity.getId());
		}
		
		//先查询角色
		conditions.clear();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"guarder"));
		List<SysRoleEntity> roleList=sysRoleService.findByCondition(conditions, null);
		SysRoleEntity sysRoleEntity=null;
		String roleId="";
		if(!roleList.isEmpty()){
			sysRoleEntity=roleList.get(0);
			roleId=sysRoleEntity.getId().toString();
		}
		//变更人的下拉框
		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketTwoEntity.getGuarderId() ));//工作负责人
//		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workElectricTwoEntity.getSignerId() ));//签发人
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NOT_IN,userids.toArray()));//签发人
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketTwoEntity.getChangeAllowId() ));//许可人
		//工作负责人变更签发人和许可人
		if (workElectricTwoEntity.getChangeSignerId() !=null && workElectricTwoEntity.getChangeAllowId()!=null) {
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workElectricTwoEntity.getChangeSignerId()));//签发人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workElectricTwoEntity.getChangeAllowId()));//许可人
		}
		if (workElectricTwoEntity.getDelayAllowId()!=null) {
			//延期许可人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workElectricTwoEntity.getDelayAllowId()));//许可人
		}
		if (workTicketTwoEntity.getEndAllowId()!=null) {
			//终结许可人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketTwoEntity.getEndAllowId()));//许可人
		}
		
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketTwoEntity.getUnitNameId() ));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("FIND_IN_SET("+roleId+",a.C_ROLE_IDS)"));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		Iterator<SysUserEntity> iterator2 = userListBox.iterator();
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		
//		for (SysUserEntity entity : userList) {
//			while (iterator2.hasNext()) {
//				SysUserEntity sysUserEntity = iterator2.next();
//				if (entity.getId()== sysUserEntity.getId()) {
//					iterator2.remove();
//				}else{
//					comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
//				}
//		    }
//		}
	
		for(SysUserEntity sysUserEntity : userListBox){
			comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		model.put("userListBox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzfzrbd", model);
	}
	/**
	 *	跳转到工作负责人变更页面  签发
	 */
	@RequestMapping("/getAddGzfzrbgQf")
	public ModelAndView getAddGzfzrbgQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricTwoEntity.getWorkticketId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		Iterator<SysUserEntity> iterator2 = userList.iterator();
		while (iterator2.hasNext()) {
			SysUserEntity sysUserEntity = iterator2.next();
				if (sysUserEntity.getId().equals(userEntity.getId())) {
					iterator2.remove();
			}
       }
		
		Long userId = workElectricTwoEntity.getChangeOldPicId();
		Iterator<SysUserEntity> iterator3 = userList.iterator();
		while (iterator3.hasNext()) {
			SysUserEntity sysUserEntity = iterator3.next();
				if (sysUserEntity.getId().equals(userId)) {
					iterator3.remove();
			}
       }
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来

		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("qfrDate", new Date());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzfzrbdQf", model);
	}
	/**
	 *	跳转到工作负责人变更页面 许可
	 */
	@RequestMapping("/getAddGzfzrbgXk")
	public ModelAndView getAddGzfzrbgXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("xkrDate", new Date());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzfzrbdXk", model);
	}
	/**
	 *	跳转到工作人员变动页面
	 */
	@RequestMapping("/getAddGzrybd")
	public ModelAndView getAddGzrybd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzrybd", model);
	}
	/**
	 *	跳转到工作交底页面
	 */
	@RequestMapping("/getAddGzjd")
	public ModelAndView getAddGzjd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzjd", model);
	}
	/**
	 *	跳转到工作人员变动   负责人  页面
	 */
	@RequestMapping("/getAddGzrybdFzr")
	public ModelAndView getAddGzrybdFzr(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
			model.put("workPersonName", workElectricTwoEntity.getChangeNewPicName());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
			model.put("workPersonName", workTicketTwoEntity.getGuarderName());
		}
		
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddGzrybdFzr", model);
	}
	
	/**
	 *	跳转到延期页面
	 */
	@RequestMapping("/getAddYq")
	public ModelAndView getAddYq(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.EXTENSION.getCode());
		
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketTwoEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
				
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
	
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddYq", model);
	}
	/**
	 *	跳转到延期  值长签字
	 */
	@RequestMapping("/getAddYqZzqz")
	public ModelAndView getAddYqZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddYqZzqz", model);
	}
	/**
	 *	跳转到延期  许可
	 */
	@RequestMapping("/getAddYqXk")
	public ModelAndView getAddYqXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.DUTY.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketTwoEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除工作负责人列表中de签发人和许可人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来

		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("sdate", df.format(workTicketTwoEntity.getPlandateEnd()));
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddYqXk", model);
	}
	/**
	 *	跳转到延期工作负责人
	 */
	@RequestMapping("/getAddYqFzr")
	public ModelAndView getAddYqFzr(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddYqFzr", model);
	}
	/**
	 *	跳转到终结页面
	 */
	@RequestMapping("/getAddZj")
	public ModelAndView getAddZj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZj", workId);
		model.put("taskIdZj", taskId);
		model.put("procInstIdZj", procInstId);
		model.put("procDefIdZj", procDefId);
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.THEEND.getCode());
		
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketTwoEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            	}
            }
       }
				
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
	
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("zjdate", df.format(new Date()));
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddZj", model);
	}
	/**
	 *	跳转到终结   许可人  界面
	 */
	@RequestMapping("/getAddZjXk")
	public ModelAndView getAddZjXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZjXk", workId);
		model.put("taskIdZjXk", taskId);
		model.put("procInstIdZjXk", procInstId);
		model.put("procDefIdZjXk", procDefId);
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddZjXk", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkElectricTwoEntity workElectricTwoEntity=workElectricTwoService.findById(id);
		List<WorkElectricTwoEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTwoTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricTwoVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricTwoCombobox", JsonUtil.toJson(comboWorkElectricTwoVO.getOptions()));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoEdit", model);
	}
	
	/**
	 *	跳转到申请试运页面
	 */
	@RequestMapping("/getEditSqsy")
	public ModelAndView getEditSqsy(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		
		String electricId=request.getParameter("electricId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.APPLYRUN.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefIdSqsy", procDefId);
		
		String workId=request.getParameter("workId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSqsy", model);
	}
	
	/**
	 *	跳转到申请试运的  许可人界面
	 */
	@RequestMapping("/getAddSqsyXk")
	public ModelAndView getAddSqsyXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.THEEND.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSqsyTime", new Date());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSqsyXk", model);
	}
	/**
	 *	跳转到申请试运的  值长签字  界面
	 */
	@RequestMapping("/getAddSqsyZzqz")
	public ModelAndView getAddSqsyZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSqsy", taskId);
		model.put("procInstIdSqsy", procInstId);
		model.put("procDefId", procDefId);
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSqsyZzqz", model);
	}
	
	/**
	 *	跳转到试运恢复页面
	 */
	@RequestMapping("/getEditSyhf")
	public ModelAndView getEditSyhf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.STOPRESTORE.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("syhfDate", new Date());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSyhf", model);
	}
	/**
	 *	跳转到试运恢复页面     许可
	 */
	@RequestMapping("/getEditSyhfXk")
	public ModelAndView getEditSyhfXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.STOPRESTORE.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSyhfTime", new Date());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSyhfXk", model);
	}
	/**
	 *	跳转到试运恢复页面   值长签字
	 */
	@RequestMapping("/getEditSyhfZzqz")
	public ModelAndView getEditSyhfZzqz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("taskIdSyhf", taskId);
		model.put("procInstIdSyhf", procInstId);
		model.put("procDefId", procDefId);
		String workId=request.getParameter("workId");
		String electricId=request.getParameter("electricId");
		WorkElectricTwoEntity workElectricTwoEntity = workElectricTwoService.findById(Long.valueOf(electricId));
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(Long.valueOf(workId));
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workElectricTwoEntity.getChangeNewPicName()!=null&&!workElectricTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workElectricTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoAddSyhfZzqz", model);
	}
	
	
	/**
	 * @Description:   提交方法
	 * @author         zhangzq 
	 * @Date           2017年6月12日 上午11:39:16 
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			workElectricTwoService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	/**
	 * @Description:   签发人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   签发人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkElectricTwoEntity workElectricTwo = workElectricTwoService.findById(workElectricTwoEntity.getId());
			WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwo.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   收票人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSp")
	public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   收票人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkElectricTwoEntity workElectricTwo = workElectricTwoService.findById(workElectricTwoEntity.getId());
			WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwo.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   值长同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   值长不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkElectricTwoEntity workElectricTwo = workElectricTwoService.findById(workElectricTwoEntity.getId());
			WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwo.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   许可人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
			Long picId=workElectricTwoEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或取这个工作负责人的实体
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:  许可人不同意
	 * @author         
	 * @Date           
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeXk")
	public @ResponseBody ResultObj disAgreeXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkElectricTwoEntity workElectricTwo = workElectricTwoService.findById(workElectricTwoEntity.getId());
			WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwo.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   工作负责人变更的确定
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzfzrbg")
	public @ResponseBody ResultObj sureGzfzrbg(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBG.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.WORKPICCHANGE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   工作负责人变更   签发  同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzfzrbgQf")
	public @ResponseBody ResultObj agreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   签发  不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeGzfzrbgQf")
	public @ResponseBody ResultObj disAgreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			Long picId=workElectricTwoEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeOldPicName()!=null&&!workElectricTwo.getChangeOldPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	/**
	 * @Description:   工作负责人变更   许可  同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzfzrbgXk")
	public @ResponseBody ResultObj agreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long newPicId=workElectricTwoEntity.getChangeNewPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(newPicId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntity);
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   许可  不同意
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeGzfzrbgXk")
	public @ResponseBody ResultObj disAgreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long picId=workElectricTwoEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeOldPicName()!=null&&!workElectricTwo.getChangeOldPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   工作人员变动的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzrybd")
	public @ResponseBody ResultObj sureGzrybd(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   工作交底的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzjd")
	public @ResponseBody ResultObj sureGzjd(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZJD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   工作人员变更   同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzrybdFzr")
	public @ResponseBody ResultObj agreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workElectricTwoEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   工作人员变动   许可  不同意
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeGzrybdFzr")
	public @ResponseBody ResultObj disagreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workElectricTwoEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   延期确定
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/sureYq")
	public @ResponseBody ResultObj sureYq(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.EXTENSION.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),"");//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   延期值长签字  同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeYqZzqz")
	public @ResponseBody ResultObj agreeYqZzqz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   延期值长签字    不同意
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeYqZzqz")
	public @ResponseBody ResultObj disAgreeYqZzqz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
			String workPersonId=request.getParameter("workPersonId"); 
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   延期 许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeYqXk")
	public @ResponseBody ResultObj agreeYqXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQXK.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			SysUserEntity  sysEntity = new SysUserEntity();
			
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			WorkTicketTwoEntity workTicketTwoEntity = workTicketTwoService.findById(workElectricTwo.getWorkticketId());
			if (workElectricTwo.getChangeNewPicId()!=null) {
			    sysEntity=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysEntity.getLoginName());
			}else {
				sysEntity=sysUserService.findById(Long.valueOf(workTicketTwoEntity.getGuarderId()));//工作负责人的实体
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysEntity.getLoginName());
			}
			
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   延期 负责人的  同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeYqFzr")
	public @ResponseBody ResultObj agreeYqFzr(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.YQFZR.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   终结确定
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	/**
	 * @Description:   终结  许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZjXk")
	public @ResponseBody ResultObj agreeZjXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   终结  许可人的      不同意
	 * @author         zhangzq 
	 * @Date           201706019
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeZjXk")
	public @ResponseBody ResultObj disagreeZjXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String workPersonId=request.getParameter("workPersonId"); 
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   申请试运确定
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/sureSqsy")
	public @ResponseBody ResultObj sureSqsy(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSY.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.APPLYRUN.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   申请试运  许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSqsyXk")
	public @ResponseBody ResultObj agreeSqsyXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSYXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   申请试运 值长签字的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSqsyZzqz")
	public @ResponseBody ResultObj agreeSqsyZzqz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSYZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或取这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	/**
	 * @Description:   试运恢复确定
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/sureSyhf")
	public @ResponseBody ResultObj sureSyhf(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.STOPRESTORE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   试运恢复  许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSyhfXk")
	public @ResponseBody ResultObj agreeSyhfXk(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHFXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workElectricTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   试运恢复  值长签字的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSyhfZzqz")
	public @ResponseBody ResultObj agreeSyhfZzqz(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHFZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkElectricTwoEntity workElectricTwo=workElectricTwoService.findById(Long.valueOf(workElectricTwoEntity.getId()));
			if(workElectricTwo!=null){
				if(workElectricTwo.getChangeNewPicName()!=null&&!workElectricTwo.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectricTwo.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	/**
	 *	详细页面的审批试运  zzq 20170621
	 */
	@RequestMapping("/sqsyDetail/{id}")
	public ModelAndView sqsyDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(id);
		
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketTwoEntity.getUnitNameId()));
		workTicketTwoEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketTwoEntity.getGroupId()));
		workTicketTwoEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkElectricTwoEntity> electricList=workElectricTwoService.findByCondition(conditions, null);
		WorkElectricTwoEntity workElectricTwoEntity =null;
		if(!electricList.isEmpty()){
			workElectricTwoEntity=electricList.get(0);
		}else{
			workElectricTwoEntity=new WorkElectricTwoEntity();
		}
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", workElectricTwoEntity.getId());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoDetailSqsy", model);
	}
	/**
	 * @Description:   详细页面的试运恢复
	 * @author         zhangzq 
	 * @Date           2017年6月21日 上午9:52:38 
	 * @throws         Exception
	 */
	 
	@RequestMapping("/syhfDetail/{id}")
	public ModelAndView syhfDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(id);
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketTwoEntity.getUnitNameId()));
		workTicketTwoEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketTwoEntity.getGroupId()));
		workTicketTwoEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkElectricTwoEntity> electricList=workElectricTwoService.findByCondition(conditions, null);
		WorkElectricTwoEntity workElectricTwoEntity =null;
		if(!electricList.isEmpty()){
			workElectricTwoEntity=electricList.get(0);
		}else{
			workElectricTwoEntity=new WorkElectricTwoEntity();
		}
		model.put("workElectricTwoEntity", workElectricTwoEntity);
		model.put("workTicketTwoEntity", workTicketTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", workElectricTwoEntity.getId());
		return this.createModelAndView("ticketManage/workelectrictwo/workElectricTwoDetailSyhf", model);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkElectricTwoEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workElectricTwoService.update(t,id);
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkElectricTwoEntity workElectricTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workElectricTwoService.updateSpnrDisagree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   再提交
	 * @author         zhangzq 
	 * @Date           2017年7月13日 上午9:26:10 
	 * @throws         Exception
	 */
	@RequestMapping("/againSubmit")
	public @ResponseBody ResultObj againSubmit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		try {
			WorkElectricTwoEntity workElectricTwoEntity=new WorkElectricTwoEntity();
			workElectricTwoEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workElectricTwoEntity.getApproveIdea()==null?"":workElectricTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workElectricTwoEntity.setId(Long.valueOf(workId));
			workElectricTwoService.updateSpnrAgree(workElectricTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}