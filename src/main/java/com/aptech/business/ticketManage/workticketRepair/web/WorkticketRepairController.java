package com.aptech.business.ticketManage.workticketRepair.web;

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
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.business.ticketManage.repairTicket.service.RepairTicketService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.business.ticketManage.workticketRepair.domain.WorkticketRepairEntity;
import com.aptech.business.ticketManage.workticketRepair.service.WorkticketRepairService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
@RequestMapping("/workticketRepair")
public class WorkticketRepairController extends BaseController<WorkticketRepairEntity> {
	
	@Autowired
	private WorkticketRepairService workticketRepairService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RepairTicketService repairTicketService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Autowired
	private SysRoleService sysRoleService;
	@Override
	public IBaseEntityOperation<WorkticketRepairEntity> getService() {
		return workticketRepairService;
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
		List<WorkticketRepairEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workElectricTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkElectricVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制

		model.put("workElectricCombobox", JsonUtil.toJson(comboWorkElectricVO.getOptions()));
		return this.createModelAndView("workticketRepair/workticketRepairList", model);
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
		WorkticketRepairEntity workticketRepairEntity = workticketRepairService.findById(Long.valueOf(electricId));
		RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.NE,WorkTicketUserRelEnum.PERMISSION.getId()));
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,repairTicketEntity.getId()));
		List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
		//移除许可人列表中的工作负责人
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
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddQf", model);
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
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddPz", model);
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
		String taskId=request.getParameter("taskId");
		WorkticketRepairEntity workticketRepairEntity = workticketRepairService.findById(Long.valueOf(electricId));
		RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
		model.put("repairTicketEntity", repairTicketEntity);
		model.put("workticketRepairEntity", workticketRepairEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddXk", model);
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
		WorkticketRepairEntity workticketRepairEntity = workticketRepairService.findById(Long.valueOf(electricId));
		RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
		model.put("workticketRepairEntity", workticketRepairEntity);
		model.put("repairTicketEntity", repairTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddZj", model);
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
		WorkticketRepairEntity workticketRepairEntity = workticketRepairService.findById(Long.valueOf(electricId));
		RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
		model.put("workticketRepairEntity", workticketRepairEntity);
		model.put("repairTicketEntity", repairTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddZjXk", model);
	}
	/**
	 * @Description:   跳转到终结  值长  界面
	 * @author         changl 
	 * @Date           2017年8月3日 下午3:28:14 
	 * @throws         Exception
	 */
	@RequestMapping("/getAddZjZz")
	public ModelAndView getAddZjZz(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		String taskId=request.getParameter("taskId");
		String procInstId=request.getParameter("procInstId");
		String procDefId=request.getParameter("procDefId");
		model.put("workIdZjZz", workId);
		model.put("taskIdZjZz", taskId);
		model.put("procInstIdZjZz", procInstId);
		model.put("procDefIdZjZz", procDefId);
		
		String electricId=request.getParameter("electricId");
		WorkticketRepairEntity workticketRepairEntity = workticketRepairService.findById(Long.valueOf(electricId));
		RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
		model.put("workticketRepairEntity", workticketRepairEntity);
		model.put("repairTicketEntity", repairTicketEntity);
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("electricId", electricId);
		return this.createModelAndView("ticketManage/workticketRepair/workticketRepairAddZjZz", model);
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
			workticketRepairService.submit(id,selectUser);
			resultObj.setResult("success");
		return resultObj;
	}
	
	/**
	 * @Description:   签发人同意
	 * @author         changl 
	 * @Date           2017年8月3日 上午10:12:58 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
			return resultObj;
	}
	/**
	 * @Description:   签发人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeQf")
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.QF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
			//查询工作负责人的loginName
			WorkticketRepairEntity workElectric = workticketRepairService.findById(workticketRepairEntity.getId());
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(repairTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeXk")
	public @ResponseBody ResultObj disAgreeXk(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketRepairEntity workElectric = workticketRepairService.findById(workticketRepairEntity.getId());
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(repairTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   收票人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSp")
	public @ResponseBody ResultObj agreeSp(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
			return resultObj;
	}
	/**
	 * @Description:   收票人不同意
	 * @author         changl 
	 * @Date           2017年8月4日 上午9:53:01 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeSp")
	public @ResponseBody ResultObj disAgreeSp(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
			ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.SP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketRepairEntity workElectric = workticketRepairService.findById(workticketRepairEntity.getId());
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(repairTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   值长同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   值长不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZZQZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			WorkticketRepairEntity workElectric = workticketRepairService.findById(workticketRepairEntity.getId());
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workElectric.getWorkticketId());
			SysUserEntity fzrEntity=sysUserService.findById(repairTicketEntity.getGuarderId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),fzrEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人同意
	 * @author         changl 
	 * @Date           2017年8月3日 上午11:54:44 
	 * @throws         Exception
	 */
	@RequestMapping("/agreeXk")
	public @ResponseBody ResultObj agreeXk(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			Long picId=workticketRepairEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.XK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			String selectUser=request.getParameter("selectUser");
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
//			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	
	/**
	 * @Description:   终结确定
	 * @author         changl 
	 * @Date           2017年8月4日 下午1:44:04 
	 * @throws         Exception
	 */
	@RequestMapping("/sureZj")
	public @ResponseBody ResultObj sureZj(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZJ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),workticketRepairEntity.getSelectUser());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),WorkSpResultEnum.THEEND.getCode());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	
	
	/**
	 * @Description:   终结  许可人的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZjXk")
	public @ResponseBody ResultObj agreeZjXk(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
//			String selectUser=workticketRepairEntity.getSelectUser();
			String selectUser=request.getParameter("selectUser");
			Long picId=workticketRepairEntity.getAllowPicPersonId();//工作负责人的id
			SysUserEntity  sysUserEntity=sysUserService.findById(picId);//或许这个工作负责人的实体
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
			return resultObj;
	}
	/**
	 * @Description:   终结  值长的  同意
	 * @author         zhangzq 
	 * @Date           20170619
	 * @throws         Exception
	 */
	@RequestMapping("/agreeZjZz")
	public @ResponseBody ResultObj agreeZjZz(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZJZZ.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=workticketRepairEntity.getSelectUser();
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			
			actTaskService.complete(taskId,procInstId, taskVariables);
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
			return resultObj;
	}
	/**
	 * @Description:   终结  许可人的      不同意
	 * @author         zhangzq 
	 * @Date           201706019
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeZjXk")
	public @ResponseBody ResultObj disagreeZjXk(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZJXK.getCode());
			Long workPersonId=workticketRepairEntity.getEndPicIdZhu(); 
			SysUserEntity  sysUserEntity=sysUserService.findById(workPersonId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   终结  值长      不同意
	 * @author         zhangzq 
	 * @Date           201706019
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeZjZz")
	public @ResponseBody ResultObj disagreeZjZz(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZJZZ.getCode());
			Long workPersonId=workticketRepairEntity.getEndPicIdZhu(); 
			SysUserEntity  sysUserEntity=sysUserService.findById(workPersonId);//或许这个工作负责人的实体
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),sysUserEntity.getLoginName());
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
		return resultObj;
	}
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody WorkticketRepairEntity workticketRepairEntity){
		ResultObj resultObj = new ResultObj();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			workticketRepairService.updateSpnrDisagree(workticketRepairEntity,userEntity);
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
			WorkticketRepairEntity workticketRepairEntity=new WorkticketRepairEntity();
			workticketRepairEntity.setSpFlag(WorkBtnTypeEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),workticketRepairEntity.getApproveIdea()==null?"":workticketRepairEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId, variables);
			workticketRepairEntity.setId(Long.valueOf(workId));
			workticketRepairService.updateSpnrAgree(workticketRepairEntity,userEntity);
		return resultObj;
	}

}