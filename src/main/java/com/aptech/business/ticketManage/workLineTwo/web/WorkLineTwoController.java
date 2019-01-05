package com.aptech.business.ticketManage.workLineTwo.web;

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
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.workElectricTwo.domain.WorkElectricTwoEntity;
import com.aptech.business.ticketManage.workLineTwo.domain.WorkLineTwoEntity;
import com.aptech.business.ticketManage.workLineTwo.service.WorkLineTwoService;
import com.aptech.business.ticketManage.workTicketLineTwo.domain.WorkTicketLineTwoEntity;
import com.aptech.business.ticketManage.workTicketLineTwo.service.WorkTicketLineTwoService;
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
 * @author zzq
 * @created 2017-10-18 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workLineTwo")
public class WorkLineTwoController extends BaseController<WorkLineTwoEntity> {
	
	@Autowired
	private WorkLineTwoService workLineTwoService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private WorkTicketLineTwoService workTicketLineTwoService;
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
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkLineTwoEntity> getService() {
		return workLineTwoService;
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
		List<WorkLineTwoEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoList", model);
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
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddQf", model);
	}
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketLineTwoEntity.getId()));
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
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSp", model);
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
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddPz", model);
	}
	/**
	 *	跳转到许可页面
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		model.put("workLineTwoEntity", workLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("sdate", df.format(workTicketLineTwoEntity.getPlandateStart()));
		model.put("edate",df.format(workTicketLineTwoEntity.getPlandateEnd()));
		model.put("xkdate",df.format(new Date()));
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddXk", model);
	}
	
	/**
	 *	跳转到工作负责人变更页面
	 */
	@RequestMapping("/getAddGzfzrbd")
	public ModelAndView getAddGzfzrbd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,WorkSpResultEnum.WORKPICCHANGE.getCode());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.SIGNATURE.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workLineTwoEntity.getWorkticketId()));
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
		
		//查询职务下面的人
		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,WorkSafeTypeEnum.WORKPERSON.getCode()));
		List<SysDutiesDetailEntity> dutiesList =sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userCodeList=new ArrayList<String>();
		if(!dutiesList.isEmpty()){
			for (SysDutiesDetailEntity sysDutiesDetailEntity : dutiesList) {
				List<Condition> condition = new ArrayList<Condition>();
				Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
				condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
				List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
				for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
					if(!sysDutiesDetailEntity.getUserUnitRelId().equals(workTicketLineTwoEntity.getGuarderId().toString())){
						userCodeList.add(tempuserUnitRel.getUserId().toString());
					}
				}
			}
		}
		//变更人的下拉框
		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketLineTwoEntity.getGuarderId() ));//工作负责人
//		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workElectricTwoEntity.getSignerId() ));//签发人
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NOT_IN,userids.toArray()));//签发人
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketLineTwoEntity.getChangeAllowId() ));//许可人
		//工作负责人变更签发人和许可人
		if (workLineTwoEntity.getChangeSignerId() !=null && workLineTwoEntity.getChangeAllowId()!=null) {
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workLineTwoEntity.getChangeSignerId()));//签发人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workLineTwoEntity.getChangeAllowId()));//许可人
		}
		if (workLineTwoEntity.getDelayAllowId()!=null) {
			//延期许可人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workLineTwoEntity.getDelayAllowId()));//许可人
		}
		if (workTicketLineTwoEntity.getEndAllowId()!=null) {
			//终结许可人
			conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.NE,workTicketLineTwoEntity.getEndAllowId()));//许可人
		}
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workTicketLineTwoEntity.getUnitNameId() ));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userCodeList.toArray()));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(SysUserEntity sysUserEntity : userListBox){
			comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		model.put("userListBox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzfzrbd", model);
	}
	/**
	 *	跳转到工作负责人变更页面  签发
	 */
	@RequestMapping("/getAddGzfzrbgQf")
	public ModelAndView getAddGzfzrbgQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workLineTwoEntity.getWorkticketId()));
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
		Iterator<SysUserEntity> iterator2 = userList.iterator();
		while (iterator2.hasNext()) {
			SysUserEntity sysUserEntity = iterator2.next();
				if (sysUserEntity.getId().equals(userEntity.getId())) {
					iterator2.remove();
			}
       }
		
		Long userId = workLineTwoEntity.getChangeOldPicId();
		Iterator<SysUserEntity> iterator3 = userList.iterator();
		while (iterator3.hasNext()) {
			SysUserEntity sysUserEntity = iterator3.next();
				if (sysUserEntity.getId().equals(userId)) {
					iterator3.remove();
			}
       }
	
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("qfrDate", new Date());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzfzrbdQf", model);
	}
	/**
	 *	跳转到工作负责人变更页面 许可
	 */
	@RequestMapping("/getAddGzfzrbgXk")
	public ModelAndView getAddGzfzrbgXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("xkrDate", new Date());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzfzrbdXk", model);
	}
	/**
	 *	跳转到工作人员变动页面
	 */
	@RequestMapping("/getAddGzrybd")
	public ModelAndView getAddGzrybd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzrybd", model);
	}
	/**
	 *	跳转到工作交底页面
	 */
	@RequestMapping("/getAddGzjd")
	public ModelAndView getAddGzjd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzjd", model);
	}
	/**
	 *	跳转到工作人员变动   负责人  页面
	 */
	@RequestMapping("/getAddGzrybdFzr")
	public ModelAndView getAddGzrybdFzr(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
			model.put("workPersonName", workLineTwoEntity.getChangeNewPicName());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
			model.put("workPersonName", workTicketLineTwoEntity.getGuarderName());
		}
		
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddGzrybdFzr", model);
	}
	
	/**
	 *	跳转到延期页面
	 */
	@RequestMapping("/getAddYq")
	public ModelAndView getAddYq(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.EXTENSION.getCode());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketLineTwoEntity.getId()));
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
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddYq", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddYqZzqz", model);
	}
	/**
	 *	跳转到延期  许可
	 */
	@RequestMapping("/getAddYqXk")
	public ModelAndView getAddYqXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.DUTY.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketLineTwoEntity.getId()));
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
		model.put("sdate", df.format(workTicketLineTwoEntity.getPlandateEnd()));
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddYqXk", model);
	}
	/**
	 *	跳转到延期工作负责人
	 */
	@RequestMapping("/getAddYqFzr")
	public ModelAndView getAddYqFzr(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//String taskId=request.getParameter("taskId");
		String electricId=request.getParameter("electricId");
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddYqFzr", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.THEEND.getCode());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketLineTwoEntity.getId()));
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
		
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddZj", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddZjXk", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkLineTwoEntity workLineTwoEntity=workLineTwoService.findById(id);
		List<WorkLineTwoEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		model.put("workLineTwoEntity", workLineTwoEntity);
		
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoEdit", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(Long.valueOf(workId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSqsy", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSqsyTime", new Date());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSqsyXk", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSqsyZzqz", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(Long.valueOf(workId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("syhfDate", new Date());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSyhf", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(Long.valueOf(workId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSyhfTime", new Date());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSyhfXk", model);
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
		WorkLineTwoEntity workLineTwoEntity = workLineTwoService.findById(Long.valueOf(electricId));
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(Long.valueOf(workId));
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workLineTwoEntity.getChangeNewPicName()!=null&&!workLineTwoEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workLineTwoEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", workTicketLineTwoEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoAddSyhfZzqz", model);
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
			workLineTwoService.submit(id,selectUser);
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
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkLineTwoEntity workElectric = workLineTwoService.findById(workLineTwoEntity.getId());
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketLineTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkLineTwoEntity workElectric = workLineTwoService.findById(workLineTwoEntity.getId());
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketLineTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkLineTwoEntity workElectric = workLineTwoService.findById(workLineTwoEntity.getId());
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketLineTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
			Long picId=workLineTwoEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或取这个工作负责人的实体
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:  许可人不同意
	 * @author        sml
	 * @Date          
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeXk")
	public @ResponseBody ResultObj disAgreeXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkLineTwoEntity workElectric = workLineTwoService.findById(workLineTwoEntity.getId());
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketLineTwoEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureGzfzrbg(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBG.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.WORKPICCHANGE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			Long picId=workLineTwoEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeOldPicName()!=null&&!workElectric.getChangeOldPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long newPicId=workLineTwoEntity.getChangeNewPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(newPicId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntity);
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long picId=workLineTwoEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeOldPicName()!=null&&!workElectric.getChangeOldPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeOldPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureGzrybd(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureGzjd(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZJD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workLineTwoEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disagreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workLineTwoEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureYq(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.YQ.getCode());
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
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeYqZzqz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeYqZzqz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.YQZZQZ.getCode());
			String workPersonId=request.getParameter("workPersonId"); 
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeYqXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.YQXK.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			SysUserEntity  sysEntity = new SysUserEntity();
			
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			WorkTicketLineTwoEntity workTicketTwoEntity = workTicketLineTwoService.findById(workElectric.getWorkticketId());
			if (workElectric.getChangeNewPicId()!=null) {
			    sysEntity=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysEntity.getLoginName());
			}else {
				sysEntity=sysUserService.findById(Long.valueOf(workTicketTwoEntity.getGuarderId()));//工作负责人的实体
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysEntity.getLoginName());
			}
			
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeYqFzr(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.YQFZR.getCode());
			
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeZjXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj disagreeZjXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String workPersonId=request.getParameter("workPersonId"); 
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureSqsy(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSY.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workLineTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.APPLYRUN.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeSqsyXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSYXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workLineTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeSqsyZzqz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SQSYZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或取这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj sureSyhf(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workLineTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.STOPRESTORE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeSyhfXk(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHFXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workLineTwoEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
	public @ResponseBody ResultObj agreeSyhfZzqz(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.SYHFZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String workPersonId=request.getParameter("workPersonId");
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			//增加判断++
			WorkLineTwoEntity workElectric=workLineTwoService.findById(Long.valueOf(workLineTwoEntity.getId()));
			if(workElectric!=null){
				if(workElectric.getChangeNewPicName()!=null&&!workElectric.getChangeNewPicName().equals("")){
					SysUserEntity  sysUserEntityChuan=sysUserService.findById(Long.valueOf(workElectric.getChangeNewPicId()));//或许这个变更后的工作负责人的实体
					taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntityChuan);
				}
			}
			//增加判断++
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
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
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(id);
		
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketLineTwoEntity.getUnitNameId()));
		workTicketLineTwoEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketLineTwoEntity.getGroupId()));
		workTicketLineTwoEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkLineTwoEntity> electricList=workLineTwoService.findByCondition(conditions, null);
		WorkLineTwoEntity workLineTwoEntity =null;
		if(!electricList.isEmpty()){
			workLineTwoEntity=electricList.get(0);
		}else{
			workLineTwoEntity=new WorkLineTwoEntity();
		}
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", workLineTwoEntity.getId());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoDetailSqsy", model);
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
		WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(id);
		//查询班组和部门
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workTicketLineTwoEntity.getUnitNameId()));
		workTicketLineTwoEntity.setUnitName(sysUnitEntity.getName());
		
		OrgaAppEntity orgaEntity = orgaAppService.findById(Long.valueOf(workTicketLineTwoEntity.getGroupId()));
		workTicketLineTwoEntity.setGroupName(orgaEntity.getName());//
				
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("workticketId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id ));
		List<WorkLineTwoEntity> electricList=workLineTwoService.findByCondition(conditions, null);
		WorkLineTwoEntity workLineTwoEntity =null;
		if(!electricList.isEmpty()){
			workLineTwoEntity=electricList.get(0);
		}else{
			workLineTwoEntity=new WorkLineTwoEntity();
		}
		model.put("workLineTwoEntity", workLineTwoEntity);
		model.put("workTicketLineTwoEntity", workTicketLineTwoEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", workLineTwoEntity.getId());
		return this.createModelAndView("ticketManage/worklinetwo/workLineTwoDetailSyhf", model);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkLineTwoEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workLineTwoService.update(t,id);
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkLineTwoEntity workLineTwoEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workLineTwoService.updateSpnrDisagree(workLineTwoEntity,userEntity);
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
			WorkLineTwoEntity workLineTwoEntity=new WorkLineTwoEntity();
			workLineTwoEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workLineTwoEntity.getApproveIdea()==null?"":workLineTwoEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workLineTwoEntity.setId(Long.valueOf(workId));
			workLineTwoService.updateSpnrAgree(workLineTwoEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}