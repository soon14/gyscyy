package com.aptech.business.ticketManage.workFire.web;

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

import com.aptech.business.component.dictionary.WorkFireBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkFireTwoBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSpResultEnum;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.workFire.domain.WorkFireEntity;
import com.aptech.business.ticketManage.workFire.service.WorkFireService;
import com.aptech.business.ticketManage.workTicketFire.domain.WorkTicketFireEntity;
import com.aptech.business.ticketManage.workTicketFire.service.WorkTicketFireService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.service.SysRoleService;
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
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workFire")
public class WorkFireController extends BaseController<WorkFireEntity> {
	
	@Autowired
	private WorkFireService workFireService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private WorkTicketFireService workTicketFireService;
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
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<WorkFireEntity> getService() {
		return workFireService;
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
		List<WorkFireEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("ticketManage/workFire/workElectricList", model);
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
		return this.createModelAndView("ticketManage/workFire/workFireAddQf", model);
	}
	/**
	 *	跳转到会签发页面
	 */
	@RequestMapping("/getAddHqf")
	public ModelAndView getAddxffzrsh(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFire/workFireAddHqf", model);
	}
	/**
	* @Title: getAddajfzrsh
	* @Description: 跳转到消防监护人审核
	* @author sunliang
	* @date 2017年8月19日下午2:45:16
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddxfjhrsh")
    public ModelAndView getAddxfjhrsh(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
        String taskId=request.getParameter("taskId");
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("dateQf", new Date());
        model.put("electricId", electricId);
        return this.createModelAndView("ticketManage/workFire/workFireAddxfjhrsh", model);
    }
	/**
	 *	跳转到安监部门审批页面
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
	    model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFire/workFireAddPz", model);
	}
	/**
	 *	跳转到安全总监
	 */
	@RequestMapping("/getAddAqzj")
	public ModelAndView getAddAqzj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workFireEntity", workFireEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
	     model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFire/workFireAddAqzj", model);
	}

	/**
	* @Title: getAddjhrqr
	* @Description: 分管生产领导页面
	* @author sunliang
	* @date 2017年8月21日上午10:59:21
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddScld")
    public ModelAndView getAddScld(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
        WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
        WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
        model.put("workTicketEntity", workTicketEntity);
        model.put("workFireEntity", workFireEntity);
        SysUserEntity userEntity= RequestContext.get().getUser();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
        model.put("dateQf", new Date());
        return this.createModelAndView("ticketManage/workFire/workFireAddJhrqr", model);
    }
	/**
	 *	跳转到收票页面
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人和签发人
		Iterator<SysUserEntity> iterator = userList.iterator();
		while (iterator.hasNext()) {
			SysUserEntity sysUserEntity = iterator.next();
            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
            		iterator.remove();
            		break;
            	}
            }
       }
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFire/workFireAddSp", model);
	}
	
	/**
	 *	跳转到许可页面
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workFireEntity", workFireEntity);
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		model.put("sdate", df.format(workTicketEntity.getPlandateStart()));
		model.put("edate",df.format(workTicketEntity.getPlandateEnd()));
		model.put("xkdate",df.format(new Date()));
		model.put("dhdate",df.format(new Date()));
		model.put("electricId", electricId);

		
		 //工作负责人下拉列表
  	    ComboboxVO requestUserVO = new ComboboxVO();
	  	List<Condition> conditions=new ArrayList<Condition>();
	  	conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	  	conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
	  	List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
        for(SysUserEntity sysUserEntity : userListBox){
      	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
		
        //安监部负责人
		conditions.clear();
		ComboboxVO ajUserVo = new ComboboxVO();
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1039"));
		List<SysDutiesEntity>  dutiesEntity = sysDutiesService.findByCondition(conditions, null);
		
		String dutyId=dutiesEntity.get(0).getId().toString();
		conditions.clear();
	    conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId));
		
		List<SysDutiesDetailEntity>  duEntity = sysDutiesDetailService.findByCondition(conditions, null);
	    List<String> userIdList = new ArrayList<String>();
		for (SysDutiesDetailEntity sysDutiesDetailEntity : duEntity) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userIdList.add(tempuserUnitRel.getUserId().toString());
			}
		}
		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> ajuserList = sysUserService.findByCondition(conditions, null);
		for(SysUserEntity sysUserEntity : ajuserList){
			ajUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
	     model.put("ajUsers", JsonUtil.toJson(ajUserVo.getOptions()));
	     
	     
	     //安全总监
	     conditions.clear();
	     ComboboxVO aqzjUserVo = new ComboboxVO();
	     conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1040"));
	     List<SysDutiesEntity>  dutiesEntity2 = sysDutiesService.findByCondition(conditions, null);
	     
	     String dutyId2=dutiesEntity2.get(0).getId().toString();
	     conditions.clear();
	     conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId2));
	     
	     List<SysDutiesDetailEntity>  duEntity2 = sysDutiesDetailService.findByCondition(conditions, null);
	     List<String> userIdList2 = new ArrayList<String>();
	     for (SysDutiesDetailEntity sysDutiesDetailEntity : duEntity2) {
	    	 List<Condition> condition = new ArrayList<Condition>();
	    	 Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
	    	 condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
	    	 List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
	    	 for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
	    		 userIdList2.add(tempuserUnitRel.getUserId().toString());
	    	 }
	     }
	     conditions.clear();
	     conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList2.toArray()));
	     conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
	     List<SysUserEntity> aqzjuserList = sysUserService.findByCondition(conditions, null);
	     for(SysUserEntity sysUserEntity : aqzjuserList){
	    	 aqzjUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	     }
	     model.put("aqzjUsers", JsonUtil.toJson(aqzjUserVo.getOptions()));
	     
	     //分管生产领导
	     conditions.clear();
	     ComboboxVO ldUserVo = new ComboboxVO();
	     conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1041"));
	     List<SysDutiesEntity>  dutiesEntity3 = sysDutiesService.findByCondition(conditions, null);
	     
	     String dutyId3=dutiesEntity3.get(0).getId().toString();
	     conditions.clear();
	     conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId3));
	     
	     List<SysDutiesDetailEntity>  duEntity3 = sysDutiesDetailService.findByCondition(conditions, null);
	     List<String> userIdList3= new ArrayList<String>();
	     for (SysDutiesDetailEntity sysDutiesDetailEntity : duEntity3) {
	    	 List<Condition> condition = new ArrayList<Condition>();
	    	 Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
	    	 condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
	    	 List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
	    	 for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
	    		 userIdList3.add(tempuserUnitRel.getUserId().toString());
	    	 }
	     }
	     conditions.clear();
	     conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList3.toArray()));
	     conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
	     List<SysUserEntity> lduserList = sysUserService.findByCondition(conditions, null);
	     for(SysUserEntity sysUserEntity : lduserList){
	    	 ldUserVo.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	     }
	     model.put("lederUsers", JsonUtil.toJson(ldUserVo.getOptions()));
	        
        
		return this.createModelAndView("ticketManage/workFire/workFireAddXk", model);
	}
	
	
	
	/**
	* @Title: getAddfzrqr
	* @Description: 跳转到消防监护确认（许可）
	* @author sunliang
	* @date 2017年8月21日上午11:24:18
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddfzrqr")
    public ModelAndView getAddfzrqr(HttpServletRequest request){
        String electricId=request.getParameter("electricId");
        Map<String, Object> model = new HashMap<String, Object>();
        String taskId=request.getParameter("taskId");
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
        WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
        model.put("workTicketEntity", workTicketEntity);
        model.put("workFireEntity", workFireEntity);
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
    	model.put("dateQf", new Date());
        return this.createModelAndView("ticketManage/workFire/workFireAddJhqr", model);
    }


	/**
	 *	跳转到工作交底页面
	 */
	@RequestMapping("/getAddGzjd")
	public ModelAndView getAddGzjd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		 WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
	     WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workFire/workFireAddGzjd", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
//		List<Condition> conditions = new ArrayList<Condition>();
//		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.DUTY.getId()));
//		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
//		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
//		//移除动火负责人列表中的签发人和许可人
//		Iterator<SysUserEntity> iterator = userList.iterator();
//		while (iterator.hasNext()) {
//			SysUserEntity sysUserEntity = iterator.next();
//            for(WorkTicketUserRelEntity workTicketUserRelEntity:list){
//            	if(sysUserEntity.getId().equals(workTicketUserRelEntity.getUserId())){
//            		iterator.remove();
//            	}
//            }
//       }
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
	 	model.put("zjdate", df.format(new Date()));
		return this.createModelAndView("ticketManage/workFire/workFireAddZj", model);
	}
	/**
	* @Title: getAddfzrys
	* @Description: 跳转到工作负责人验收
	* @author sunliang
	* @date 2017年8月21日下午2:50:15
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddZjrys")
    public ModelAndView getAddZjrys(HttpServletRequest request){
        String electricId=request.getParameter("electricId");
        Map<String, Object> model = new HashMap<String, Object>();
        
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
        
        
        List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
        model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
        
		 WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
	     WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);     

		 //工作负责人下拉列表
 	    ComboboxVO requestUserVO = new ComboboxVO();
	  	List<Condition> conditions=new ArrayList<Condition>();
	  	conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
	  	conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
	  	List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
       for(SysUserEntity sysUserEntity : userListBox){
     	 requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
       }
       model.put("requestUsers", JsonUtil.toJson(requestUserVO.getOptions()));
        
        return this.createModelAndView("ticketManage/workFire/workFireAddDhys", model);
    }
	
	/**
	 * @Title: getAddXkrys
	 * @Description: 跳转到消防监护人审批（终结）
	 * @author sunliang
	 * @date 2017年8月21日下午4:13:36
	 * @param request
	 * @return
	 * @throws
	 */
	@RequestMapping("/getAddZjJh")
	public ModelAndView getAddJhzj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workTicketEntity", workTicketEntity);
		model.put("workFireEntity", workFireEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		//获取三种人记录
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workTicketEntity.getId()));
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
		return this.createModelAndView("ticketManage/workFire/workFireAddZjJH", model);
	}
	/**
	* @Title: getAddXkrys
	* @Description: 跳转到许可人审批（终结）
	* @author sunliang
	* @date 2017年8月21日下午4:13:36
	* @param request
	* @return
	* @throws
	*/
	@RequestMapping("/getAddZjXk")
    public ModelAndView getAddZjXk(HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String electricId=request.getParameter("electricId");
        
        String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workId", workId);
		model.put("taskId", taskId);
		model.put("procInstId", procInstId);
		model.put("procDefId", procDefId);
       
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
	    model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
        
        WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
        WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
        model.put("workTicketEntity", workTicketEntity);
        model.put("workFireEntity", workFireEntity);
        SysUserEntity userEntity= RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("electricId", electricId);
        return this.createModelAndView("ticketManage/workFire/workFireAddZjXK", model);
    }
	
	
	/**
	 * @Description:   工作交底的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzjd")
	public @ResponseBody ResultObj sureGzjd(HttpServletRequest request,@RequestBody  WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.GZJD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workFireService.updateSpnrAgree(workFireEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkFireEntity workFireEntity=workFireService.findById(id);
		List<WorkFireEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		model.put("workFireEntity", workFireEntity);
		
		return this.createModelAndView("ticketManage/workelectric/workElectricEdit", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(Long.valueOf(workId));
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSqsy", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSqsyTime", new Date());
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSqsyXk", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFireEntity.getWorkticketId());
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		
			model.put("workPersonId", workTicketEntity.getGuarderId());
		
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSqsyZzqz", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(Long.valueOf(workId));
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("syhfDate", new Date());
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSyhf", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(Long.valueOf(workId));
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("dateSyhfTime", new Date());
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSyhfXk", model);
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
		WorkFireEntity workFireEntity = workFireService.findById(Long.valueOf(electricId));
		WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(Long.valueOf(workId));
		model.put("workFireEntity", workFireEntity);
		model.put("workTicketEntity", workTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
	
			model.put("workPersonId", workTicketEntity.getGuarderId());
		
		return this.createModelAndView("ticketManage/workelectric/workElectricAddSyhfZzqz", model);
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
			workFireService.submit(id,selectUser);
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
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   会签发人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeHQf")
	public @ResponseBody ResultObj agreeHQf(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.HQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
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
	@RequestMapping("/disAgreeHQf")
	public @ResponseBody ResultObj disAgreeHQf(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.HQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   消防监护人审核
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreexfSh")
	public @ResponseBody ResultObj agreexfSh(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   消防监护人审核不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreexfSh")
	public @ResponseBody ResultObj disAgreexfSh(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   安监部门审批同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.AJBM.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:  安监部门审批驳回
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.AJBM.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   安全总监审批同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeAqzj")
	public @ResponseBody ResultObj agreeAqzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.AQZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:  安全总监审批驳回
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeAqzj")
	public @ResponseBody ResultObj disAgreeAqzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.AQZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * 许可人审批不通过
	 * @param request
	 * @param workFireEntity
	 * @return
	 */
	 @RequestMapping("/disagreeXk")
     public @ResponseBody ResultObj disAgreeXk(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
         ResultObj resultObj = new ResultObj();
         try {
             workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XK.getCode());
             String taskId=request.getParameter("taskId");
             String procInstId=request.getParameter("procInstId");
             String selectUser=request.getParameter("selectUser");
             SysUserEntity userEntity= RequestContext.get().getUser();
             //查询工作负责人的loginName
             WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
             WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
             SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
             //查询工作负责人的loginName
             Map<String, Object> taskVariables=new HashMap<String, Object>();
             taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
             taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
             taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
             taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
             actTaskService.complete(taskId,procInstId,taskVariables);
             workFireService.updateSpnrDisagree(workFireEntity,userEntity);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return resultObj;
     }

	
	/**
	* @Title: agreeJhrqr
	* @Description: 分管生产领导审批同意
	* @author sunliang
	* @date 2017年8月21日上午11:01:56
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/agreeJhrqr")
    public @ResponseBody ResultObj agreeJhrqr(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            WorkFireEntity workFire = workFireService.findById(workFireEntity.getId());
            WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workFire.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.FGSCFZR.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            SysUserEntity userEntity= RequestContext.get().getUser();
            String selectUser=request.getParameter("selectUser");
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	* @Title: disAgreeJhrqr
	* @Description: 分管生产领导驳回
	* @author sunliang
	* @date 2017年8月21日下午5:35:56
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disagreeJhrqr")
    public @ResponseBody ResultObj disAgreeJhrqr(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.FGSCFZR.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            //查询工作负责人的loginName
            WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
            WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            //查询工作负责人的loginName
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            actTaskService.complete(taskId,procInstId,taskVariables);
            workFireService.updateSpnrDisagree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	/**
	 * @Description:   运行值班值班人审批
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSp")
    public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.SP.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	/**
	 * @Description:   运行值班值班人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
//			Long picId=workFireEntity.getAllowPicPersonId();//动火工作负责人的id
//			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或取这个工作负责人的实体
    		workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
		    String selectUser=request.getParameter("selectUser");
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	   
	
	//消防监护人同意（许可）
	
	@RequestMapping("/agreeFzrqr")
    public @ResponseBody ResultObj agreeFzrqr(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHRXK.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            String selectUser=request.getParameter("selectUser");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
        	//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
            actTaskService.complete(taskId,procInstId, taskVariables);
            workFireService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	   /**
	* @Title: disAgreeFzrqr
	* @Description: 负责人审批不通过
	* @author sunliang
	* @date 2017年8月21日下午7:19:53
	* @param request
	* @param workFireEntity
	* @return
	* @throws
	*/
	@RequestMapping("/disagreeFzrqr")
	    public @ResponseBody ResultObj disAgreeFzrqr(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
	        ResultObj resultObj = new ResultObj();
	        try {
	            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHRXK.getCode());
	            String taskId=request.getParameter("taskId");
	            String procInstId=request.getParameter("procInstId");
	            String selectUser=request.getParameter("selectUser");
	            SysUserEntity userEntity= RequestContext.get().getUser();
	            //查询工作负责人的loginName
	            WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
	            WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
	            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getChangeAllowId());
	            //查询工作负责人的loginName
	            Map<String, Object> taskVariables=new HashMap<String, Object>();
	            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
	            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
	            
	            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
	            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	            actTaskService.complete(taskId,procInstId,taskVariables);
	            workFireService.updateSpnrDisagree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.YS.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
	    	//查询工作负责人的loginName
			WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
			WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
				
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	   /**
		* @Title: agreeFzrys
		* @Description: 动火工作负责人验收
		* @author sunliang
		* @date 2017年8月21日下午2:53:41
		* @param request
		* @param workFireEntity
		* @return
		* @throws
		*/
		@RequestMapping("/agreeFzrys")
	       public @ResponseBody ResultObj agreeFzrys(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
	           ResultObj resultObj = new ResultObj();
	           try {
	               workFireEntity.setSpFlag(WorkFireBtnTypeEnum.DHFZRYS.getCode());
	               String taskId=request.getParameter("taskId");
	               String procInstId=request.getParameter("procInstId");
	               String selectUser=request.getParameter("selectUser");
	               SysUserEntity userEntity= RequestContext.get().getUser();
	               
	               Map<String, Object> taskVariables=new HashMap<String, Object>();
	               taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
	               taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
	               taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
	               taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	               
	               actTaskService.complete(taskId,procInstId, taskVariables);
	               workFireService.updateSpnrAgree(workFireEntity,userEntity);
	           } catch (Exception e) {
	               e.printStackTrace();
	           }
	           return resultObj;
	       }

		/**
		 * @Title: agreeXkrys
		 * @Description:消防监护审批同意（终结）
		 * @author sunliang
		 * @date 2017年8月21日下午8:05:45
		 * @param request
		 * @param workFireEntity
		 * @return
		 * @throws
		 */
		@RequestMapping("/agreeJhzj")
		public @ResponseBody ResultObj agreeJhzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
			ResultObj resultObj = new ResultObj();
			try {
	            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHRZJ.getCode());
				String taskId=request.getParameter("taskId");
				String procInstId=request.getParameter("procInstId");
				SysUserEntity userEntity= RequestContext.get().getUser();
				String selectUser=request.getParameter("selectUser");
				
				Map<String, Object> taskVariables=new HashMap<String, Object>();
				taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
				taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
				taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
				taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
				
				actTaskService.complete(taskId,procInstId, taskVariables);
				workFireService.updateSpnrAgree(workFireEntity,userEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultObj;
		}
		
		/**
		* @Title: disAgreeJhzj
		* @Description: 消防监护人审批（终结）不同意
		* @author sunliang
		* @date 2017年8月21日下午8:05:32
		* @param request
		* @param workFireEntity
		* @return
		* @throws
		*/
		@RequestMapping("/disAgreeJhzj")
	    public @ResponseBody ResultObj disAgreeJhzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
	        ResultObj resultObj = new ResultObj();
	        try {
	            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XFJHRZJ.getCode());
	            String taskId=request.getParameter("taskId");
	            String procInstId=request.getParameter("procInstId");
	            String selectUser=request.getParameter("selectUser");
	            SysUserEntity userEntity= RequestContext.get().getUser();
	            //查询工作负责人的loginName
	            WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
	            WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
	            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
	            //查询工作负责人的loginName
	            Map<String, Object> taskVariables=new HashMap<String, Object>();
	            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
	            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
	            
	            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
	            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	            actTaskService.complete(taskId,procInstId,taskVariables);
	            workFireService.updateSpnrDisagree(workFireEntity,userEntity);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return resultObj;
	    }
		
		/**
		* @Title: agreeXkrys
		* @Description:许可人验收通过
		* @author sunliang
		* @date 2017年8月21日下午8:05:45
		* @param request
		* @param workFireEntity
		* @return
		* @throws
		*/
		@RequestMapping("/agreeXkzj")
	    public @ResponseBody ResultObj agreeXkzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
	        ResultObj resultObj = new ResultObj();
	        try {
	            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XKZJ.getCode());
	            String taskId=request.getParameter("taskId");
	            String procInstId=request.getParameter("procInstId");
	            SysUserEntity userEntity= RequestContext.get().getUser();
	            
	            Map<String, Object> taskVariables=new HashMap<String, Object>();
	            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
	            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
	            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
	            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	            
	            actTaskService.complete(taskId,procInstId, taskVariables);
	            workFireService.updateSpnrAgree(workFireEntity,userEntity);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return resultObj;
	    }
		/**
		* @Title: disAgreeXkrys
		* @Description: 许可人验收不通过
		* @author sunliang
		* @date 2017年8月21日下午8:05:32
		* @param request
		* @param workFireEntity
		* @return
		* @throws
		*/
		@RequestMapping("/disAgreeXkzj")
	    public @ResponseBody ResultObj disAgreeXkzj(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
	        ResultObj resultObj = new ResultObj();
	        try {
	            workFireEntity.setSpFlag(WorkFireBtnTypeEnum.XKZJ.getCode());
	            String taskId=request.getParameter("taskId");
	            String procInstId=request.getParameter("procInstId");
	            String selectUser=request.getParameter("selectUser");
	            SysUserEntity userEntity= RequestContext.get().getUser();
	            //查询工作负责人的loginName
	            WorkFireEntity workElectric = workFireService.findById(workFireEntity.getId());
	            WorkTicketFireEntity workTicketEntity=workTicketFireService.findById(workElectric.getWorkticketId());
	            SysUserEntity fzrEntity=sysUserService.findById(workTicketEntity.getGuarderId());
	            //查询工作负责人的loginName
	            Map<String, Object> taskVariables=new HashMap<String, Object>();
	            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
	            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
	            
	            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
	            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
	            actTaskService.complete(taskId,procInstId,taskVariables);
	            workFireService.updateSpnrDisagree(workFireEntity,userEntity);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return resultObj;
	    }
		
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkFireEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workFireService.update(t,id);
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkFireEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workFireService.updateSpnrDisagree(workFireEntity,userEntity);
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
			WorkFireEntity workFireEntity=new WorkFireEntity();
			workFireEntity.setSpFlag(WorkFireBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workFireEntity.setId(Long.valueOf(workId));
			workFireService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}