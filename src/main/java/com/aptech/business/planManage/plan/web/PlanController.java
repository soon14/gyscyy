package com.aptech.business.planManage.plan.web;

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

import com.aptech.business.component.dictionary.PlanCycleEnum;
import com.aptech.business.component.dictionary.PlanStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.business.planManage.plan.service.PlanService;
import com.aptech.common.act.service.ActProcessService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 计划管理配置控制器
 * 
 * @author
 * @created 2017-11-13 15:10:16
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/plan")
public class PlanController extends BaseController<PlanEntity> {

	@Autowired
	private PlanService planService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Override
	public IBaseEntityOperation<PlanEntity> getService() {
		return planService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 计划周期
		ComboboxVO cycleCombobox = new ComboboxVO();
		for (PlanCycleEnum planCycleEnum : PlanCycleEnum.values()) {
			cycleCombobox.addOption(planCycleEnum.getCode(),
					planCycleEnum.getName());
		}
		model.put("planCycle", JsonUtil.toJson(cycleCombobox.getOptions()));
		// 计划类型
		ComboboxVO typeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("PLAN_TYPE");
		for (String key : typeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			typeCombobox.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
		}
		model.put("planType", JsonUtil.toJson(typeCombobox.getOptions()));

		return this.createModelAndView("planManage/plan/planList", model);
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request,String uuid) {
		Map<String, Object> model = new HashMap<String, Object>();
		PlanEntity planEntity=new PlanEntity();
		if(StringUtil.isNotEmpty(uuid)){
			 planEntity.setUuid(uuid);
		}else{
			 planEntity.setUuid(IdUtil.creatUUID());
		}
		model.put("planEntity", planEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 计划周期
		ComboboxVO cycleCombobox = new ComboboxVO();
		for (PlanCycleEnum planCycleEnum : PlanCycleEnum.values()) {
			cycleCombobox.addOption(planCycleEnum.getCode(),
					planCycleEnum.getName());
		}
		model.put("planCycle", JsonUtil.toJson(cycleCombobox.getOptions()));
		// 计划类型
		ComboboxVO typeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("PLAN_TYPE");
		for (String key : typeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			typeCombobox.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
		}
		model.put("planType", JsonUtil.toJson(typeCombobox.getOptions()));

		return this.createModelAndView("planManage/plan/planAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit")
	public ModelAndView getEditPage(HttpServletRequest request,String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		PlanEntity planEntity=null;
		if(StringUtil.isNotEmpty(id)){
			 planEntity=planService.findById(Long.parseLong(id));
		}else{
			 planEntity=new PlanEntity();
		}
		model.put("planEntity", planEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 计划周期
		ComboboxVO cycleCombobox = new ComboboxVO();
		for (PlanCycleEnum planCycleEnum : PlanCycleEnum.values()) {
			cycleCombobox.addOption(planCycleEnum.getCode(),
					planCycleEnum.getName());
		}
		model.put("planCycle", JsonUtil.toJson(cycleCombobox.getOptions()));
		// 计划类型
		ComboboxVO typeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("PLAN_TYPE");
		for (String key : typeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = typeMap.get(key);
			typeCombobox.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
		}
		model.put("planType", JsonUtil.toJson(typeCombobox.getOptions()));
		return this.createModelAndView("planManage/plan/planEdit", model);
	}
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail")
	public ModelAndView getDetail(HttpServletRequest request,String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		PlanEntity planEntity=planService.findById(Long.parseLong(id));
		model.put("planEntity", planEntity);
		return this.createModelAndView("planManage/plan/planDetail", model);
	}
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/add" )
	public @ResponseBody ResultObj add(@RequestBody PlanEntity planEntity, HttpServletRequest request) {
		return planService.add(planEntity);
	}
	/**
	 * @Description: 提交查询提交人
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,
				ProcessMarkEnum.PLAN_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
		request.setAttribute("userList", userList);
		return this.createModelAndView("planManage/plan/sureSubmitPerson", model);
	}
	/**
	 * @Description: 计划提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return planService.submit(id,params);
	}
	/**
	 * @Description: 审批页面初始化
	 * @author changl
	 * @Date 2017年6月5日 下午1:17:55
	 * @throws Exception
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		PlanEntity planEntity=planService.findById(id);
		model.put("planEntity", planEntity);
		//查询各个人的按钮权限 开始
				SysUserEntity userEntity= RequestContext.get().getUser();
				List<Condition> conditionsLc=new ArrayList<Condition>();
				conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
				conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
				conditionsLc.add(new Condition("task.end_time_ IS NULL"));
				conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.PLAN_PROCESS_KEY.getName()));
				List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
				TodoTaskEntity todoTaskEntity=null;
				if(!list.isEmpty()){
					todoTaskEntity=list.get(0);
					List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
					model.put("nodeList", nodeList);
				}
		//查询各个人的按钮权限 结束
		return this.createModelAndView("planManage/plan/planApprove",model);
	}
	/**
	 * @Description:   审批页面
	 * @author         changl 
	 * @Date           2017年11月17日 下午4:33:01 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/getApprove/{taskId}/{url}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long taskId,@PathVariable String url) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("url", url);
		model.put("date", new Date());
		userList=nodeConfigService.getNextNodeTransactor(taskId.toString(),ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("planManage/plan/aproveSubmit", model);
	}
	/**
	 * @Description:  同意(检修主任)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  15840344548
	 * @throws Exception
	 */
	@RequestMapping(value = "/overhaul")
	public @ResponseBody ResultObj overhaul(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", PlanStatusEnum.SPECIAL_WORK.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description: 同意(专工)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30 
	 * @throws Exception
	 */
	@RequestMapping(value = "/special")
	public @ResponseBody ResultObj special(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		if(finish){
			params.put("status", PlanStatusEnum.BIOTECH_DIRECTOR.getCode());
			return planService.approveFinish(t,params);
		}
		return planService.approve(t,params);
	}
	/**
	 * @Description: 同意(生技部主任)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  15840344548
	 * @throws Exception
	 */
	@RequestMapping(value = "/biotech")
	public @ResponseBody ResultObj biotech(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PlanStatusEnum.PLAN_DIRECTOR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description: 同意(计划主任)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  15840344548
	 * @throws Exception
	 */
	@RequestMapping(value = "/plan")
	public @ResponseBody ResultObj plan(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PlanStatusEnum.LEADER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description:   同意(领导)
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  15840344548
	 * @throws Exception
	 */
	@RequestMapping(value = "/leader")
	public @ResponseBody ResultObj leader(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		if(finish){
			params.put("status", PlanStatusEnum.COMPLETE.getCode());
			  //调用群发消息接口
		       List<String> receivePersonList=actProcessService.getAllUsersByProcessInstanceId(t.getId().toString(), ProcessMarkEnum.PLAN_PROCESS_KEY.getName());
		       String receivePersons="";
		       for (String receivePerson :receivePersonList) {
		    	   receivePersons+=receivePerson+",";
		        }
		       MessageCenterEntity messageEntity =new MessageCenterEntity();
		       messageEntity.setTitle("计划已执行");
		       PlanEntity planEntity=planService.findById(t.getId());
		       messageEntity.setContext("计划时间为："+planEntity.getTime()+"-"+planEntity.getTimeEnd()+"已执行");
		       messageEntity.setSendTime(new Date());
		       messageEntity.setReceivePerson(receivePersons);
		       SysUserEntity sysUserEntity = RequestContext.get().getUser();
		       messageEntity.setSendPerson(sysUserEntity.getId().toString());
		       messageEntity.setType("private");
		       messageCenterService.addMessage(messageEntity);
			return planService.approveFinish(t,params);
		}
		return planService.approve(t,params);
	}
	/**
	 * @Description: 驳回
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30 
	 * @throws Exception
	 */
	@RequestMapping(value = "/reject")
	public @ResponseBody ResultObj reject(@RequestBody PlanEntity t) {
		boolean finish=actTaskService.isParallelEndTask(t.getTaskId());
		Map<String, Object> params = new HashMap<String, Object>();
		if(finish){
			params.put("status", PlanStatusEnum.REJECT.getCode());
		}
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description:再提交
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  15840344548
	 * @throws Exception
	 */
	@RequestMapping(value = "/penging")
	public @ResponseBody ResultObj penging(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PlanStatusEnum.OVERHAUL_DIRECTOR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description: 删除
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30  
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ResultObj delete(@RequestBody PlanEntity t) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", PlanStatusEnum.DELETE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getApprove());
		return planService.approve(t,params);
	}
	/**
	 * @Description: 缺陷批量删除
	 * @author changl
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
			long longId = (long) id;
			PlanEntity t = planService.findById(longId);
			planService.validate(t);
		}
		for (Integer id : ids) {
			long longId = (long) id;
			PlanEntity t = planService.findById(longId);
			if (t != null) {
				planService.deleteEntity(longId);
			}
		}
		return resultObj;
	}
}