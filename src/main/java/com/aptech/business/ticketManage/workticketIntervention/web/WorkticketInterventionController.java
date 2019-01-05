package com.aptech.business.ticketManage.workticketIntervention.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.ticketManage.interventionTicket.domain.InterventionTicketEntity;
import com.aptech.business.ticketManage.interventionTicket.service.InterventionTicketService;
import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
import com.aptech.business.ticketManage.workticketIntervention.service.WorkticketInterventionService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票配置控制器
 *
 * @author 
 * @created 2017-06-29 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workticketIntervention")
public class WorkticketInterventionController extends BaseController<WorkticketInterventionEntity> {
	
	@Autowired
	private WorkticketInterventionService workticketInterventionService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private InterventionTicketService interventionTicketService;
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
	@Override
	public IBaseEntityOperation<WorkticketInterventionEntity> getService() {
		return workticketInterventionService;
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
		List<WorkticketInterventionEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		return this.createModelAndView("workticketIntervention/workticketInterventionList", model);
	}
	
	
	/**
	 * @Description:  跳转到签发页面
	 * @author         changl 
	 * @Date           2017年8月3日 上午10:03:33 
	 * @throws         Exception
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
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddQf", model);
	}
	/**
	 * @Description:  跳转到收票页面 
	 * @author         changl 
	 * @Date           2017年8月3日 下午1:53:21 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddSp")
	public ModelAndView getAddSp(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddSp", model);
	}
	/**
	 * @Description:   值长签字
	 * @author         changl 
	 * @Date           2017年8月3日 上午10:33:13 
	 * @throws         Exception
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
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddPz", model);
	}
	/**
	 * @Description:   跳转到许可页面
	 * @author         changl 
	 * @Date           2017年8月3日 上午11:47:14 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddXk")
	public ModelAndView getAddXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
		model.put("interventionTicketEntity", interventionTicketEntity);
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddXk", model);
	}
	
	/**
	 * @Description:   跳转到工作负责人变更页面
	 * @author         changl 
	 * @Date           2017年8月3日 下午2:04:27 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddGzfzrbd")
	public ModelAndView getAddGzfzrbd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,WorkSpResultEnum.WORKPICCHANGE.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
		
		//先查询角色
		List<Condition> conditions=new ArrayList<Condition>();
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
		conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ,interventionTicketEntity.getUnitNameId() ));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("FIND_IN_SET("+roleId+",a.C_ROLE_IDS)"));
		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for(SysUserEntity sysUserEntity : userListBox){
			comboWorkTicketVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	    }
		model.put("userListBox", JsonUtil.toJson(comboWorkTicketVO.getOptions()));
		
		
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddGzfzrbd", model);
	}
	/**
	 * @Description:   跳转到工作负责人变更页面  签发
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:28:02 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddGzfzrbgQf")
	public ModelAndView getAddGzfzrbgQf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("qfrDate", new Date());
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddGzfzrbdQf", model);
	}
	/**
	 * @Description:   跳转到工作负责人变更页面 许可
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:28:07 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddGzfzrbgXk")
	public ModelAndView getAddGzfzrbgXk(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		model.put("xkrDate", new Date());
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddGzfzrbdXk", model);
	}
	/**
	 * @Description:   跳转到工作人员变动页面
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:28:13 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddGzrybd")
	public ModelAndView getAddGzrybd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
		model.put("interventionTicketEntity", interventionTicketEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddGzrybd", model);
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
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, WorkSpResultEnum.THEEND.getCode());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		String electricId=request.getParameter("electricId");
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		model.put("interventionTicketEntity", interventionTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddZj", model);
	}
	/**
	 * @Description:   跳转到终结   许可人  界面
	 * @author         changl 
	 * @Date           2017年8月3日 下午3:28:14 
	 * @throws         Exception
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
		WorkticketInterventionEntity workticketInterventionEntity = workticketInterventionService.findById(Long.valueOf(electricId));
		InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
		model.put("workticketInterventionEntity", workticketInterventionEntity);
		model.put("interventionTicketEntity", interventionTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		//判断工作负责人  到底是谁
		if(workticketInterventionEntity.getChangeNewPicName()!=null&&!workticketInterventionEntity.getChangeNewPicName().equals("")){
			model.put("workPersonId", workticketInterventionEntity.getChangeNewPicId());
		}else{
			model.put("workPersonId", interventionTicketEntity.getGuarderId());
		}
		return this.createModelAndView("ticketManage/workticketIntervention/workticketInterventionAddZjXk", model);
	}

	/**
	 * @Description:   提交方法
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:31:22 
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
			String id=request.getParameter("workId");
			String selectUser=request.getParameter("selectUser");
			workticketInterventionService.submit(id,selectUser);
		return resultObj;
	}
	
	/**
	 * @Description:   签发人同意
	 * @author         changl 
	 * @Date           2017年8月3日 上午10:12:58 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
			ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
			return resultObj;
	}
	/**
	 * @Description:   签发人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketInterventionEntity workElectric = workticketInterventionService.findById(workticketInterventionEntity.getId());
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(interventionTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   收票人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSp")
	public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   收票人不同意
	 * @author         changl 
	 * @Date           2017年8月4日 上午9:53:01 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketInterventionEntity workElectric = workticketInterventionService.findById(workticketInterventionEntity.getId());
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(interventionTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   值长同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   值长不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketInterventionEntity workElectric = workticketInterventionService.findById(workticketInterventionEntity.getId());
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(interventionTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人同意
	 * @author         changl 
	 * @Date           2017年8月3日 上午11:54:44 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
			ResultObj resultObj = new ResultObj();
			Long picId=workticketInterventionEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
			actTaskService.complete(taskId,procInstId, taskVariables);
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更的确定
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzfzrbg")
	public @ResponseBody ResultObj sureGzfzrbg(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBG.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.WORKPICCHANGE.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   工作负责人变更   签发  同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzfzrbgQf")
	public @ResponseBody ResultObj agreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   签发  不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeGzfzrbgQf")
	public @ResponseBody ResultObj disAgreeGzfzrbgQf(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGQF.getCode());
			Long picId=workticketInterventionEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	
	/**
	 * @Description:   工作负责人变更   许可  同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzfzrbgXk")
	public @ResponseBody ResultObj agreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long newPicId=workticketInterventionEntity.getChangeNewPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(newPicId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.STARTER.getName(),sysUserEntity);
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作负责人变更   许可  不同意
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeGzfzrbgXk")
	public @ResponseBody ResultObj disAgreeGzfzrbgXk(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZFZRBGXK.getCode());
			Long picId=workticketInterventionEntity.getChangeOldPicId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   工作人员变动的  确定
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/sureGzrybd")
	public @ResponseBody ResultObj sureGzrybd(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZRYBD.getCode());
			SysUserEntity userEntity= RequestContext.get().getUser();
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	
	/**
	 * @Description:   工作人员变更   同意
	 * @author         zhangzq 
	 * @Date           20170616
	 * @throws         Exception
	 */
	@RequestMapping("/agreeGzrybdFzr")
	public @ResponseBody ResultObj agreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workticketInterventionEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   工作人员变动   许可  不同意
	 * @author         zhangzq 
	 * @Date           201706016
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeGzrybdFzr")
	public @ResponseBody ResultObj disagreeGzrybdFzr(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.GZRYBDFZR.getCode());
			Long picId=workticketInterventionEntity.getWorkPersonPicId();
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   终结确定
	 * @author         changl 
	 * @Date           2017年8月4日 下午1:44:04 
	 * @throws         Exception
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workticketInterventionEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	
	
	/**
	 * @Description:   终结  许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZjXk")
	public @ResponseBody ResultObj agreeZjXk(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   终结  许可人的      不同意
	 * @author         zhangzq 
	 * @Date           201706019
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeZjXk")
	public @ResponseBody ResultObj disagreeZjXk(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String workPersonId=request.getParameter("workPersonId"); 
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(workPersonId));//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/updateBz/{id}")
	public @ResponseBody ResultObj updateBz(@RequestBody WorkticketInterventionEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workticketInterventionService.update(t,id);
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkticketInterventionEntity workticketInterventionEntity){
		ResultObj resultObj = new ResultObj();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketInterventionService.updateSpnrDisagree(workticketInterventionEntity,userEntity);
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
			WorkticketInterventionEntity workticketInterventionEntity=new WorkticketInterventionEntity();
			workticketInterventionEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workticketInterventionEntity.getApproveIdea()==null?"":workticketInterventionEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workticketInterventionEntity.setId(Long.valueOf(workId));
			workticketInterventionService.updateSpnrAgree(workticketInterventionEntity,userEntity);
		return resultObj;
	}
}