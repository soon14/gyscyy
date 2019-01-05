package com.aptech.business.managePlanContract.investmentPlan.web;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.InvestmentPlanCategoryTypeEnum;
import com.aptech.business.component.dictionary.InvestmentPlanStageTypeEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.investmentPlan.domain.InvestmentPlanEntity;
import com.aptech.business.managePlanContract.investmentPlan.service.InvestmentPlanService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.business.safeManage.educationTrain.domain.EducationTrainEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
 * 投资计划配置控制器
 *
 * @author 
 * @created 2018-04-08 13:31:35
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/investmentPlan")
public class InvestmentPlanController extends BaseController<InvestmentPlanEntity> {
	
	@Autowired
	private InvestmentPlanService investmentPlanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<InvestmentPlanEntity> getService() {
		return investmentPlanService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//项目分类
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(InvestmentPlanCategoryTypeEnum key : InvestmentPlanCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		
		//建设阶段
		ComboboxVO stageVo = new ComboboxVO();
		for(InvestmentPlanStageTypeEnum key : InvestmentPlanStageTypeEnum.values()){
			stageVo.addOption(key.getCode(), key.getName());
		}
		model.put("stageCombobox", JsonUtil.toJson(stageVo.getOptions()));
		
		//状态下拉框
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("INVESTMENT_PLANE_STATUS");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));

		
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		model.put("userName", userEntity.getName());
		
		List<Condition> conditions = new ArrayList<Condition>();
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
		
		return this.createModelAndView("managePlanContract/investmentPlan/investmentPlanList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//项目分类
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(InvestmentPlanCategoryTypeEnum key : InvestmentPlanCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		
		//建设阶段
		ComboboxVO stageVo = new ComboboxVO();
		for(InvestmentPlanStageTypeEnum key : InvestmentPlanStageTypeEnum.values()){
			stageVo.addOption(key.getCode(), key.getName());
		}
		model.put("stageCombobox", JsonUtil.toJson(stageVo.getOptions()));
		
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());

		return this.createModelAndView("managePlanContract/investmentPlan/investmentPlanAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		InvestmentPlanEntity investmentPlanEntity = (InvestmentPlanEntity)investmentPlanService.findById(id);
		model.put("entity", investmentPlanEntity);
		model.put("entityJson", JsonUtil.toJson(investmentPlanEntity));
		
		//项目分类
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(InvestmentPlanCategoryTypeEnum key : InvestmentPlanCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		
		//建设阶段
		ComboboxVO stageVo = new ComboboxVO();
		for(InvestmentPlanStageTypeEnum key : InvestmentPlanStageTypeEnum.values()){
			stageVo.addOption(key.getCode(), key.getName());
		}
		model.put("stageCombobox", JsonUtil.toJson(stageVo.getOptions()));
		
      //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(investmentPlanEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		
		return this.createModelAndView("managePlanContract/investmentPlan/investmentPlanEdit", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveEditPage/{id}")
	public @ResponseBody ResultObj saveTrainPage(@RequestBody InvestmentPlanEntity entity, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		InvestmentPlanEntity investmentPlanEntity = investmentPlanService.findById(entity.getId());
		entity.setCreateUserId(investmentPlanEntity.getCreateUserId());
		entity.setApproveStatus(investmentPlanEntity.getApproveStatus());
		investmentPlanService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INVESTNEBTPLAN.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		InvestmentPlanEntity targetEntity=investmentPlanService.findById(id);
		model.put("entity", targetEntity);
		
      //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());

		return this.createModelAndView("managePlanContract/investmentPlan/investmentPlanDetail", model);
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		Page<InvestmentPlanEntity> page=new Page<InvestmentPlanEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<InvestmentPlanEntity> dataList=investmentPlanService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
	
		ExcelUtil.export(req, res, "投资计划报表模板.xlsx","投资计划.xlsx", resultMap);
		
	}
	
	/**
	 *	审核页面跳转
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		InvestmentPlanEntity overhaulPlanEntity = investmentPlanService.findById(id);
		model.put("overhualPlanId",id.intValue());
		model.put("entity", overhaulPlanEntity);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.INVESTMENT_PLAN_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		//获取登录人信息
				SysUserEntity userEntity1= sysUserService.findById(Long.parseLong(overhaulPlanEntity.getCreateUserId()));
				model.put("userName", userEntity1.getName());
		return this.createModelAndView("managePlanContract/investmentPlan/investmentPlanApprove",model);
	}
	
	
	/**
	 *	跳转到提交
	 */
	@RequestMapping("/sureSubmit")/*approve*/
	public ModelAndView sureSubmit(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Condition> conditions = new ArrayList<Condition>();
		String id=request.getParameter("id");
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.INVESTMENT_PLAN_PROCESS_KEY.getName()));
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
		return new ModelAndView("managePlanContract/investmentPlan/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 提交审核
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
	
		return investmentPlanService.submit(id,params);
	}
	
	/**
	 * 再次提交
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/againSubmit")
	public @ResponseBody
	ResultObj againSubmit(@RequestBody InvestmentPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		t.setAuditBtn("ztjBtn");//againSubmit
		
		return investmentPlanService.approve(t,params);
	}
	
	/**
	 * 取消
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/cancel")
	public @ResponseBody
	ResultObj cancel(@RequestBody InvestmentPlanEntity t) {
		t.setAuditBtn("qxBtn");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		  
		return investmentPlanService.approve(t,params);
	}
	
	/**
	 *	
	 *  计划经营处审批不通过
	 */
	@RequestMapping("/overhaulPlanAuditMsg/{taskId}/{id}/{codeBtn}")/*approve*/
	public ModelAndView overhaulPlanAuditMsg(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		InvestmentPlanEntity entity = investmentPlanService.findById(id);
		
		model.put("entity", entity);
		return new ModelAndView("managePlanContract/investmentPlan/investmentPlanAuditMsg",model);
	}
	/**
	 *	审批通过
	 */
	@RequestMapping("/overhaulPlanAudit/{taskId}/{id}/{codeBtn}")/*approve*/
	public ModelAndView overhaulPlanAudit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		InvestmentPlanEntity entity = investmentPlanService.findById(id);
		entity.setAuditBtn(codeBtn);
		model.put("entity", entity);
		return new ModelAndView("managePlanContract/investmentPlan/investmentPlanAudit",model);
	}
	
	/**
	 * 审核不通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/auditReject")
	public @ResponseBody
	ResultObj auditReject(@RequestBody InvestmentPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		return investmentPlanService.approve(t,params);
	}
	
	/**
	 * 审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/auditPass")
	public @ResponseBody
	ResultObj biotechApprove(@RequestBody InvestmentPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		
		return investmentPlanService.approve(t,params);
	}
}