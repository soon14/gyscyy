package com.aptech.business.annualProductionJob.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.business.annualProductionJob.service.AnnualProductionJobService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度生产工作计划配置控制器
 *
 * @author 
 * @created 2018-09-20 13:26:33
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/annualProductionJob")
public class AnnualProductionJobController extends BaseController<AnnualProductionJobEntity> {
	
	@Autowired
	private AnnualProductionJobService annualProductionJobService;
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
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<AnnualProductionJobEntity> getService() {
		return annualProductionJobService;
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
		//单位名称
		ComboboxVO unitIdCombobox = new ComboboxVO();
  		List<Condition> conditions = new ArrayList<Condition>();
  		String [] organizationIds = {"1", "3", "4", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : unitList){
  			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
  		//数据类型
  		model.put("type",type);
  		
  		conditions.clear();
	    SysUserEntity sysUserEntity = RequestContext.get().getUser();
	    conditions.add(new Condition("C_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId()));
	    List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
	    List<Long> userUnitIds = new ArrayList<Long>();
	    for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
	      userUnitIds.add(userUnitRelEntity.getId());
	    }
	    conditions.clear();
	    conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userUnitIds.toArray()));
	    List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
	    List<String> dutiesIds = new ArrayList<String>();
	    for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
	      dutiesIds.add(sysDutiesDetailEntity.getDutiesId());
	    }
	    conditions.clear();
	    conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, dutiesIds.toArray()));
	    List<SysDutiesEntity> sysDutiesList = sysDutiesService.findByCondition(conditions, null);
	    if(!sysDutiesList.isEmpty()){
	      for(SysDutiesEntity sysDutiesEntity : sysDutiesList){
	        if(sysDutiesEntity.getName().equals("计划经营处负责人")){ 
	          model.put("answerht", "1");//1有权限2没有权限
	          break;
	        }else{
	          model.put("answerht", "2");
	        }
	      }
	    }else{
	      model.put("answerht", "2");
	    }
	   model.put("sysUserEntity", sysUserEntity);
  		//根据不同type页显示不同的list页面
  		if("1".equals(type)){
  			//年度生产工作计划
  			return this.createModelAndView("annualProductionJob/annualProductionJobList", model);
  		}else{
  			//月度生产工作计划
  			return this.createModelAndView("annualProductionJob/monthProductionJobList", model);
  		}
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{type}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		//单位名称
		ComboboxVO unitIdCombobox = new ComboboxVO();
  		List<Condition> conditions = new ArrayList<Condition>();
  		String [] organizationIds = {"1", "3", "4", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : unitList){
  			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
		//数据类型
  		model.put("type",type);
  		//完成状态下拉框
		Map<String, SysDictionaryVO> completeStatusMap  =  DictionaryUtil.getDictionaries("COMPLETE_STATUS");
		ComboboxVO comboCompleteStatusVO = new ComboboxVO();
		for(String key : completeStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = completeStatusMap.get(key);
			comboCompleteStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("completeStatusCombobox", JsonUtil.toJson(comboCompleteStatusVO.getOptions()));
  		//根据不同type页显示不同的list页面
  		if("1".equals(type)){
  			//年度生产工作计划
  			return this.createModelAndView("annualProductionJob/annualProductionJobAdd", model);
  		}else{
  			//月度生产工作计划
  			return this.createModelAndView("annualProductionJob/monthProductionJobAdd", model);
  		}
		
	}
	
	/**
	 * @Description:   添加保存
	 * @author        ly
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/addSave")
	public @ResponseBody ResultObj addSave(@RequestBody AnnualProductionJobEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		t.setApproveStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
		this.getService().addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		String type = t.getType();
		if("1".equals(type)){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ANNUAL_PRODUCTION_JOB.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MONTH_PRODUCTION_JOB.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}
		
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		AnnualProductionJobEntity annualProductionJobEntity = (AnnualProductionJobEntity)annualProductionJobService.findById(id);
		model.put("entity", annualProductionJobEntity);
		model.put("entityJson", JsonUtil.toJson(annualProductionJobEntity));
		//单位名称
		ComboboxVO unitIdCombobox = new ComboboxVO();
  		List<Condition> conditions = new ArrayList<Condition>();
  		String [] organizationIds = {"1", "3", "4", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : unitList){
  			unitIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("unitIdList", JsonUtil.toJson(unitIdCombobox.getOptions()));
  		//完成状态下拉框
		Map<String, SysDictionaryVO> completeStatusMap  =  DictionaryUtil.getDictionaries("COMPLETE_STATUS");
		ComboboxVO comboCompleteStatusVO = new ComboboxVO();
		for(String key : completeStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = completeStatusMap.get(key);
			comboCompleteStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("completeStatusCombobox", JsonUtil.toJson(comboCompleteStatusVO.getOptions()));
  		//根据不同type页显示不同的list页面
  		if("1".equals(annualProductionJobEntity.getType())){
  			//年度生产工作计划
  			return this.createModelAndView("annualProductionJob/annualProductionJobEdit", model);
  		}else if("2".equals(annualProductionJobEntity.getType())&&"1".equals(annualProductionJobEntity.getSealStatus())){
  			return this.createModelAndView("annualProductionJob/monthProductionJobEditSeal", model);
  		}
  		else{
  			//月度生产工作计划
  			return this.createModelAndView("annualProductionJob/monthProductionJobEdit", model);
  		}
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody AnnualProductionJobEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
//		ProduceReplyEntity produceReplyEntity = produceReplyService.findById(t.getId());
		this.getService().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		String type = t.getType();
		if("1".equals(type)){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ANNUAL_PRODUCTION_JOB.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MONTH_PRODUCTION_JOB.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}
		 return resultObj;
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		AnnualProductionJobEntity annualProductionJobEntity = (AnnualProductionJobEntity)annualProductionJobService.findById(id);
		model.put("entity", annualProductionJobEntity);
		model.put("entityJson", JsonUtil.toJson(annualProductionJobEntity));
		
		//根据不同type页显示不同的list页面
  		if("1".equals(annualProductionJobEntity.getType())){
  			//年度生产工作计划
  			return this.createModelAndView("annualProductionJob/annualProductionJobDetail", model);
  		}else{
  			//月度生产工作计划
  			return this.createModelAndView("annualProductionJob/monthProductionJobDetail", model);
  		}
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel/{type}")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params,@PathVariable String type) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		Page<AnnualProductionJobEntity> page=new Page<AnnualProductionJobEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<AnnualProductionJobEntity> dataList = (List<AnnualProductionJobEntity>)this.getService().findByCondition(params, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		if("1".equals(type)){
			ExcelUtil.export(req, res, "年度生产工作计划报表模板.xlsx","年度生产工作计划.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "月度生产工作计划报表模板.xlsx","月度生产工作计划.xlsx", resultMap);
		}
		
	}
	
	/**
	* 批量删除
	* @author ly
	* @date 2018年9月10日 上午10:02:12 
	* @param ids
	* @return
	*/
	@RequestMapping(value = "/bulkDelete/{ids}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (int i=0;i<ids.size();i++) {
			AnnualProductionJobEntity  apjEntity=this.getService().findById(Long.valueOf(ids.get(i)));
			if(apjEntity!=null){
				this.getService().deleteEntity(apjEntity);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		String type = this.getService().findById(Long.valueOf(ids.get(0))).getType();
		if("1".equals(type)){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ANNUAL_PRODUCTION_JOB.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MONTH_PRODUCTION_JOB.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		return resultObj;
	}
	
	/**
	* 单个删除
	* @author ly
	* @date 2018年9月10日 上午10:02:40 
	* @param id
	* @return
	* @return ResultObj
	*/
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		AnnualProductionJobEntity apjEntity=this.getService().findById(id);
		if(apjEntity!=null){
			this.getService().deleteEntity(apjEntity);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		String type = apjEntity.getType();
		if("1".equals(type)){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ANNUAL_PRODUCTION_JOB.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MONTH_PRODUCTION_JOB.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		return resultObj;
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
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.NUNNAL_JOB_KEY.getName()));
		}else{
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.MONTH_JOB_KEY.getName()));
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
		return new ModelAndView("annualProductionJob/sureSubmitPerson",resultMap);
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
			annualProductionJobService.submit(id,selectUser);
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
		AnnualProductionJobEntity annualProductionJobEntity=annualProductionJobService.findById(id);
		resultMap.put("entity", annualProductionJobEntity);
		String typeQuota = annualProductionJobEntity.getType();
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
//		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		if("1".equals(typeQuota)){
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.NUNNAL_JOB_KEY.getName()));
		}else{
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.MONTH_JOB_KEY.getName()));
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
			return this.createModelAndView("annualProductionJob/annualProductionJobApprove", resultMap);
  		}else{
  			return this.createModelAndView("annualProductionJob/monthProductionJobApprove", resultMap);
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
		return this.createModelAndView("annualProductionJob/annualProductionJobAddSCJSC", model);
	}
	
	/**
	 * @Description:   生产技术处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSCJSC")
	public @ResponseBody ResultObj agreeSCJSC(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			
			annualProductionJobService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

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
	public @ResponseBody ResultObj disagreeSCJSC(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			annualProductionJobService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

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
		return this.createModelAndView("annualProductionJob/annualProductionJobAddJHJYC", model);
	}
	/**
	 * @Description:   计划经营处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYC")
	public @ResponseBody ResultObj agreeJHJYC(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			
			annualProductionJobService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

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
	public @ResponseBody ResultObj disagreeJHJYC(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			annualProductionJobService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);

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
		return this.createModelAndView("annualProductionJob/annualProductionJobAddDWLDTL", model);
	}
	/**
	 * @Description:   单位领导同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeDWLDTL")
	public @ResponseBody ResultObj agreeDWLDTL(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			
			annualProductionJobService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
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
	public @ResponseBody ResultObj disagreeDWLDTL(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			annualProductionJobService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
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
		return this.createModelAndView("annualProductionJob/annualProductionJobAddJHJYCXW", model);
	}
	/**
	 * @Description:   计划经营处下文执行
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYCXW")
	public @ResponseBody ResultObj agreeJHJYCXW(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			
			annualProductionJobService.approveTicketAgree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
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
	public @ResponseBody ResultObj disAgreeZf(HttpServletRequest request,@RequestBody AnnualProductionJobEntity quotaPlanApproveEntity){
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
			annualProductionJobService.approveTicketDisagree(taskId, procInstId, taskVariables, quotaPlanApproveEntity, userEntity);
			
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
			AnnualProductionJobEntity annualProductionJobEntity=new AnnualProductionJobEntity();
			annualProductionJobEntity.setSpFlag(ScienceTechnologyBtnEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),annualProductionJobEntity.getApproveIdea()==null?"":annualProductionJobEntity.getApproveIdea());//审批意见
			annualProductionJobEntity.setId(Long.valueOf(workId));
			annualProductionJobService.approveTicketAgree(taskId, procInstId, variables, annualProductionJobEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   鉴定跳转页面
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/invalid")
	public ModelAndView invalid(HttpServletRequest request){
		SysUserEntity userEntity= RequestContext.get().getUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=request.getParameter("id");
		resultMap.put("invalidId", id);
		resultMap.put("userEntity", userEntity);
		//鉴定下拉框
        ComboboxVO resultCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> resultEnumMap  =  DictionaryUtil.getDictionaries("RESULT");
        
        for(String key :  resultEnumMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = resultEnumMap.get(key);
        	resultCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("resultCombobox", JsonUtil.toJson(resultCombobox.getOptions()));
		AnnualProductionJobEntity anualProductionJobEntity = annualProductionJobService.findById(Long.valueOf(id));
		resultMap.put("entity", anualProductionJobEntity);
		return new ModelAndView("/annualProductionJob/annualProductionJobInvalid",resultMap);
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@RequestMapping("/saveInvalid")
	public @ResponseBody ResultObj saveInvalid(@RequestBody AnnualProductionJobEntity anualProductionJobEntity, HttpServletRequest request) {
		return annualProductionJobService.saveInvalid(anualProductionJobEntity);
	}
}