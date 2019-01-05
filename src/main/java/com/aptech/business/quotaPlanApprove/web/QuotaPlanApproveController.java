package com.aptech.business.quotaPlanApprove.web;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.managePlanContract.annualProdutionPlan.domain.AnnualProdutionPlanEntity;
import com.aptech.business.quotaPlanApprove.domain.QuotaPlanApproveEntity;
import com.aptech.business.quotaPlanApprove.service.QuotaPlanApproveService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
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
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 指标计划流程表配置控制器
 *
 * @author 
 * @created 2018-09-21 10:10:36
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/quotaPlanApprove")
public class QuotaPlanApproveController extends BaseController<QuotaPlanApproveEntity> {
	
	@Autowired
	private QuotaPlanApproveService quotaPlanApproveService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private TodoTaskService todoTaskService;
	
	@Override
	public IBaseEntityOperation<QuotaPlanApproveEntity> getService() {
		return quotaPlanApproveService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		//场站名称
		ComboboxVO unitIdCombobox = new ComboboxVO();
  		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : unitList){
  			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("farmIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
  		//数据类型
  		model.put("type",type);
  		//默认当前年份
        DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy");
        String nowYear = sdf.format(new Date());
        model.put("nowYear",nowYear);
  	//根据不同tab页显示不同的table名称
  		if("1".equals(type)){
  			model.put("tableName","集团年度生产量流程");
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveList1", model);
  		}else if("2".equals(type)){
  			model.put("tableName","内控年度生产量流程");
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveList2", model);
  		}else{
  			model.put("tableName","年度生产指标流程");
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveList3", model);
  		}
		
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}/{type}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		QuotaPlanApproveEntity quotaPlanApproveEntity = (QuotaPlanApproveEntity)quotaPlanApproveService.findById(id);
		model.put("entity", quotaPlanApproveEntity);
		model.put("entityJson", JsonUtil.toJson(quotaPlanApproveEntity));
		if("1".equals(type)){
			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveDetail", model);
  		}else if("2".equals(type)){
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveDetailTwo", model);
  		}else{
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveDetailThree", model);
  		}
		
	}
	
	/**
	 * @Description:   提交确认  弹出框      这里要调用第一个流程接口，得到下一步的那些人的id集合
	 * @author         zhangzq 
	 * @Date           2017年6月13日 
	 * @throws         Exception
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		resultMap.put("id", id);
		if("1".equals(type)){
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.ANNUAL_PRODUCTION_PLAN_PROCESS_KEY.getName()));
		}else if("2".equals(type)){
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.NEIKONG_CAPACITY_KEY.getName()));
		}else{
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.NUNNAL_QUOTA_KEY.getName()));
		}
		
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		SysUserEntity starter= null;
		if(!RequestContext.get().isDeveloperMode()){
			starter = RequestContext.get().getUser();
		}
		List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
		resultMap.put("userList", userList);
		return new ModelAndView("quotaPlanApprove/sureSubmitPerson",resultMap);
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
			quotaPlanApproveService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	
	/**
	 *	生产量/生产指标审批页面
	 * @throws ParseException 
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 返回前台数据项
		QuotaPlanApproveEntity quotaPlanApproveEntity=quotaPlanApproveService.findById(id);
		resultMap.put("entity", quotaPlanApproveEntity);
		String typeQuota = quotaPlanApproveEntity.getType();
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
//		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		if("1".equals(typeQuota)){
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.ANNUAL_PRODUCTION_PLAN_PROCESS_KEY.getName()));
		}else if("2".equals(typeQuota)){
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.NEIKONG_CAPACITY_KEY.getName()));
		}else{
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.NUNNAL_QUOTA_KEY.getName()));
		}
		
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			resultMap.put("nodeList", nodeList);
		}
		
		if("1".equals(typeQuota)){
			return this.createModelAndView("quotaPlanApprove/quotaPlanApprove", resultMap);
  		}else if("2".equals(typeQuota)){
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveTwo", resultMap);
  		}else{
  			return this.createModelAndView("quotaPlanApprove/quotaPlanApproveThree", resultMap);
  		}
	}
	
	/**
	 *	跳转到生产技术处审核页面
	 */
	@RequestMapping("/getAddSCJSC")
	public ModelAndView getAddSCJSC(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("id", id);
		return this.createModelAndView("quotaPlanApprove/quotaPlanApproveAddSCJSC", model);
	}
	
	/**
	 * @Description:   生产技术处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSCJSC")
	public @ResponseBody ResultObj agreeSCJSC(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.DWFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId().toString());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			
			quotaPlanApproveService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   生产技术处不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeSCJSC")
	public @ResponseBody ResultObj disagreeSCJSC(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.DWFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			quotaPlanApproveService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到计划经营处页面
	 */
	@RequestMapping("/getAddJHJYC")
	public ModelAndView getAddJHJYC(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("id", id);
		return this.createModelAndView("quotaPlanApprove/quotaPlanApproveAddJHJYC", model);
	}
	/**
	 * @Description:   计划经营处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYC")
	public @ResponseBody ResultObj agreeJHJYC(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.ZGFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			
			quotaPlanApproveService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   计划经营处不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeJHJYC")
	public @ResponseBody ResultObj disagreeJHJYC(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.ZGFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			quotaPlanApproveService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到单位领导讨论页面
	 */
	@RequestMapping("/getAddDWLDTL")
	public ModelAndView getAddDWLDTL(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("id", id);
		return this.createModelAndView("quotaPlanApprove/quotaPlanApproveAddDWLDTL", model);
	}
	/**
	 * @Description:   单位领导同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeDWLDTL")
	public @ResponseBody ResultObj agreeDWLDTL(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.AQJCB.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			
			quotaPlanApproveService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   单位领导不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disagreeDWLDTL")
	public @ResponseBody ResultObj disagreeDWLDTL(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.AQJCB.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			quotaPlanApproveService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 *	跳转到计划经营处下文执行页面
	 */
	@RequestMapping("/getAddJHJYCXW")
	public ModelAndView getAddJHJYCXW(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String id=request.getParameter("id");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("id", id);
		return this.createModelAndView("quotaPlanApprove/quotaPlanApproveAddJHJYCXW", model);
	}
	/**
	 * @Description:   计划经营处下文执行
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYCXW")
	public @ResponseBody ResultObj agreeJHJYCXW(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.FZJL.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			
			quotaPlanApproveService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   作废
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeZf")
	public @ResponseBody ResultObj disAgreeZf(HttpServletRequest request,@RequestBody QuotaPlanApproveEntity quotaPlanApproveEntity){
		ResultObj resultObj = new ResultObj();
		try {
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.ZF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			quotaPlanApproveService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
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
			QuotaPlanApproveEntity quotaPlanApproveEntity=new QuotaPlanApproveEntity();
			quotaPlanApproveEntity.setSpFlag(ScienceTechnologyBtnEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),quotaPlanApproveEntity.getApproveIdea()==null?"":quotaPlanApproveEntity.getApproveIdea());//审批意见
			quotaPlanApproveEntity.setId(Long.valueOf(workId));
			quotaPlanApproveService.approveTicketAgree(taskId, procInstId, variables, quotaPlanApproveEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
}