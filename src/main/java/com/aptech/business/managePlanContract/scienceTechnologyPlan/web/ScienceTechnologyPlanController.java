package com.aptech.business.managePlanContract.scienceTechnologyPlan.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.scienceTechnologyPlan.domain.ScienceTechnologyPlanEntity;
import com.aptech.business.managePlanContract.scienceTechnologyPlan.service.ScienceTechnologyPlanService;
import com.aptech.business.safeManage.emergencyManage.domain.EmergencyManageEntity;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.business.sysManagement.domain.SysManagementEntity;
import com.aptech.common.infoitem.domain.DataTypeEnum;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
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
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度科技计划配置控制器
 *
 * @author 
 * @created 2018-04-02 19:54:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/scienceTechnologyPlan")
public class ScienceTechnologyPlanController extends BaseController<ScienceTechnologyPlanEntity> {
	
	@Autowired
	private ScienceTechnologyPlanService scienceTechnologyPlanService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<ScienceTechnologyPlanEntity> getService() {
		return scienceTechnologyPlanService;
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
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("PLANTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planTypeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		SysUserEntity sysUserEntity=RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		List<Condition> conditions = new ArrayList<Condition>();
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
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView index(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("PLANTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planTypeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/technicalQualityPlan", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScienceTechnologyPlanVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scienceTechnologyPlanCombobox", JsonUtil.toJson(comboScienceTechnologyPlanVO.getOptions()));
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("PLANTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planTypeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity = RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> sysUserList = sysUserService.findByCondition(userCondition, null);
		ComboboxVO searchuser = new ComboboxVO();
		for (SysUserEntity user : sysUserList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ScienceTechnologyPlanEntity scienceTechnologyPlanEntity =scienceTechnologyPlanService.findById(id);
		model.put("entity", scienceTechnologyPlanEntity);
		model.put("entityJson", JsonUtil.toJson(scienceTechnologyPlanEntity));
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("PLANTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planTypeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity = RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> sysUserList = sysUserService.findByCondition(userCondition, null);
		ComboboxVO searchuser = new ComboboxVO();
		for (SysUserEntity user : sysUserList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanEdit", model);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         huoxy
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody ScienceTechnologyPlanEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return scienceTechnologyPlanService.update(t,id);
	}
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ScienceTechnologyPlanEntity scienceTechnologyPlanEntity = (ScienceTechnologyPlanEntity)scienceTechnologyPlanService.findById(id);
		model.put("entity", scienceTechnologyPlanEntity);
		model.put("entityJson", JsonUtil.toJson(scienceTechnologyPlanEntity));
		//TODO下拉框具体内容根据具体业务定制
		if(scienceTechnologyPlanEntity.getUnitId()!=""&&scienceTechnologyPlanEntity.getUnitId()!=null){
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(scienceTechnologyPlanEntity.getUnitId()));
			model.put("sysUnitEntity", sysUnitEntity);
		}
		if(scienceTechnologyPlanEntity.getUserId()!=""&&scienceTechnologyPlanEntity.getUserId()!=null){
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scienceTechnologyPlanEntity.getUserId()));
			model.put("sysUserEntity", sysUserEntity);
		}
		
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanDetail", model);
	}
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<ScienceTechnologyPlanEntity> page = new Page<ScienceTechnologyPlanEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("t.C_PLAN_COMPLETE_TIME"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<ScienceTechnologyPlanEntity> dataList=scienceTechnologyPlanService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "年度科技计划模板.xlsx","年度科技计划.xlsx", resultMap);
	}
	@RequestMapping("/exportExcelModel")
	public void exportExcel(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		ExcelUtil.export(req, res, "年度科技计划(批量导入模板).xlsx","年度科技计划(批量导入模板).xlsx", resultMap);
	}
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd")
	public ModelAndView getBatchAdd(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanBatchAdd", params);
	}
	/**
	 * 导入问题
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importScienceTechnologyPlan")
	public  @ResponseBody ResultObj importScienceTechnologyPlan(HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception {
		ResultObj resultObj = new ResultObj();
		String OriginalFilename = (String)params.get("fileName");
		String fileUrl = (String)params.get("fileUrl");

		String filePath = request.getSession().getServletContext().getRealPath(fileUrl);
		File newFile = new File(filePath);
		scienceTechnologyPlanService.importScienceTechnologyPlan(request,newFile,OriginalFilename);
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
		resultMap.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.SCIENCE_TECHNOLOGY_PLAN_PROCESS_KEY.getName()));
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
		return new ModelAndView("managePlanContract/scienceTechnologyPlan/sureSubmitPerson",resultMap);
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
			scienceTechnologyPlanService.submit(id,selectUser);
			resultObj.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultObj.setResult("error");
		}
		return resultObj;
	}
	/**
	 *	年度科技计划的审批页面
	 * @throws ParseException 
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type) throws ParseException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		resultMap.put("userEntity", userEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
//		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.SCIENCE_TECHNOLOGY_PLAN_PROCESS_KEY.getName()));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			resultMap.put("nodeList", nodeList);
		}
		// 返回前台数据项
		ScienceTechnologyPlanEntity scienceTechnologyPlanEntity=scienceTechnologyPlanService.findById(id);
		resultMap.put("entity", scienceTechnologyPlanEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scienceTechnologyPlanEntity.getUserId()));
		resultMap.put("sysUserEntity", sysUserEntity);
		SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(scienceTechnologyPlanEntity.getUnitId()));
		resultMap.put("sysUnitEntity", sysUnitEntity);
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanApprove", resultMap);
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
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanAddSCJSC", model);
	}
	/**
	 * @Description:   生产技术处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeSCJSC")
	public @ResponseBody ResultObj agreeSCJSC(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.DWFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId().toString());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			
			scienceTechnologyPlanService.approveTicketAgree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);

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
	public @ResponseBody ResultObj disagreeSCJSC(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.DWFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			scienceTechnologyPlanService.approveTicketDisagree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);

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
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanAddJHJYC", model);
	}
	/**
	 * @Description:   计划经营处同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYC")
	public @ResponseBody ResultObj agreeJHJYC(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.ZGFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			
			scienceTechnologyPlanService.approveTicketAgree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);

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
	public @ResponseBody ResultObj disagreeJHJYC(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.ZGFZR.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			scienceTechnologyPlanService.approveTicketDisagree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);

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
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanAddDWLDTL", model);
	}
	/**
	 * @Description:   单位领导同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeDWLDTL")
	public @ResponseBody ResultObj agreeDWLDTL(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.AQJCB.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			
			scienceTechnologyPlanService.approveTicketAgree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);
			
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
	public @ResponseBody ResultObj disagreeDWLDTL(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.AQJCB.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			scienceTechnologyPlanService.approveTicketDisagree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);
			
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
		return this.createModelAndView("managePlanContract/scienceTechnologyPlan/scienceTechnologyPlanAddJHJYCXW", model);
	}
	/**
	 * @Description:   计划经营处下文执行
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeJHJYCXW")
	public @ResponseBody ResultObj agreeJHJYCXW(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.FZJL.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			
			scienceTechnologyPlanService.approveTicketAgree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);
			
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
	public @ResponseBody ResultObj disAgreeZf(HttpServletRequest request,@RequestBody ScienceTechnologyPlanEntity scienceTechnologyPlanEntity){
		ResultObj resultObj = new ResultObj();
		try {
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.ZF.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),"驳回");
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			scienceTechnologyPlanService.approveTicketDisagree(taskId, procInstId, taskVariables, scienceTechnologyPlanEntity, userEntity);
			
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
			ScienceTechnologyPlanEntity scienceTechnologyPlanEntity=new ScienceTechnologyPlanEntity();
			scienceTechnologyPlanEntity.setSpFlag(ScienceTechnologyBtnEnum.ZTJ.getCode());
			String workId=request.getParameter("workId");
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			
			Map<String, Object> variables=new HashMap<String, Object>();
			variables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),selectUser);
			variables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
			variables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
			variables.put(ExamMarkEnum.COMMENT.getCode(),scienceTechnologyPlanEntity.getApproveIdea()==null?"":scienceTechnologyPlanEntity.getApproveIdea());//审批意见
			scienceTechnologyPlanEntity.setId(Long.valueOf(workId));
			scienceTechnologyPlanService.approveTicketAgree(taskId, procInstId, variables, scienceTechnologyPlanEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
}