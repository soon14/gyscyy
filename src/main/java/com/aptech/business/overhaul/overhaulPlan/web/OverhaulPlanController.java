package com.aptech.business.overhaul.overhaulPlan.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.service.DispatchManagementService;
import com.aptech.business.component.dictionary.OverhaulPlanStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.accidentMeasuresPlan.domain.AccidentMeasuresPlanEntity;
import com.aptech.business.managePlanContract.accidentMeasuresPlan.service.AccidentMeasuresPlanService;
import com.aptech.business.managePlanContract.annualMaintenancePlan.domain.AnnualMaintenancePlanEntity;
import com.aptech.business.managePlanContract.annualMaintenancePlan.service.AnnualMaintenancePlanService;
import com.aptech.business.managePlanContract.annualTechnicalPlan.domain.AnnualTechnicalPlanEntity;
import com.aptech.business.managePlanContract.annualTechnicalPlan.service.AnnualTechnicalPlanService;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulReviewEnum;
import com.aptech.business.overhaul.overhaulPlan.service.OverhaulPlanService;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulPlanMember.service.OverhaulPlanMemberService;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.business.overhaul.overhaulProject.service.OverhaulProjectService;
import com.aptech.common.fourcode.service.FourCodeService;
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
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修计划配置控制器
 *
 * @author 
 * @created 2017-06-09 10:42:58
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulPlan")
public class OverhaulPlanController extends BaseController<OverhaulPlanEntity> {
	@Autowired
	private OverhaulPlanService overhaulPlanService;
	@Autowired
	private OverhaulProjectService overhaulProjectService;
	@Autowired
	private SysUnitService sysUnitService;
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
	private OverhaulPlanMemberService overhaulPlanMemberService;
	@Autowired
	private FourCodeService fourCodeService;
	
	@Autowired
	private AccidentMeasuresPlanService accidentMeasuresPlanService;
	@Autowired
	private AnnualMaintenancePlanService annualMaintenancePlanService;
	@Autowired
	private AnnualTechnicalPlanService annualTechnicalPlanService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private DispatchManagementService dispatchManagementService;
	@Override
	public IBaseEntityOperation<OverhaulPlanEntity> getService() {
		return overhaulPlanService;
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
		// 单位名称下拉树
//		List<SysUnitEntity> unitNameIdTreeList = sysUnitService.getUnitTreeNodeList();
//		model.put("unitNameIdTreeList", JsonUtil.toJson(unitNameIdTreeList));
		
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 OR C_GENERATION_TYPE = 4 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	
		 //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		//申请人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	requestUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUser", JsonUtil.toJson(requestUserVO.getOptions()));
        
        
        
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHAUL_PLAN_STATUS");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("approveStatus", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        //获取登录人信息
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
  		
        
		return this.createModelAndView("overhaul/overhaulPlan/overhaulPlanList", model);
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 单位名称下拉树
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 OR C_GENERATION_TYPE = 4 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	    
        //当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		//检修人下拉列表
		ComboboxVO dutyUserVO = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	dutyUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("dutyUser", JsonUtil.toJson(dutyUserVO.getOptions()));
        
		model.put("userEntity", userEntity);
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		model.put("unitEntity", unitEntity);
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
		model.put("yeardate", df.format(new Date()));
		DateFormatUtil df1= DateFormatUtil.getInstance("yyyy-MM-dd");
		model.put("monthdate", df1.format(new Date()));
		
        //检修计划编码
		model.put("date", new Date());
		String code=fourCodeService.getBusinessCode("检修计划编号", model);
		model.put("planCode", code);
		
		//检修计划名称
		List<Condition> conditions = new ArrayList<Condition>();
		ComboboxVO comboVO = new ComboboxVO();
//  		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
		List<AccidentMeasuresPlanEntity> accidentEntities =  accidentMeasuresPlanService.findAll();
		for (AccidentMeasuresPlanEntity accidentMeasuresPlanEntity : accidentEntities) {//年度反事故措施类
			comboVO.addOption(accidentMeasuresPlanEntity.getId().toString(), accidentMeasuresPlanEntity.getSubject());
		}
		conditions.clear();
//		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
		List<AnnualMaintenancePlanEntity> annualEntities =  annualMaintenancePlanService.findAll();
		for (AnnualMaintenancePlanEntity annualMaintenancePlanEntity : annualEntities) {
			comboVO.addOption(annualMaintenancePlanEntity.getId().toString(), annualMaintenancePlanEntity.getAnnualSubject());
		}
		
		conditions.clear();
//		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
		List<AnnualTechnicalPlanEntity> technicalPlanEntities =  annualTechnicalPlanService.findAll();
		for (AnnualTechnicalPlanEntity annualTechnicalPlanEntity : technicalPlanEntities) {
			comboVO.addOption(annualTechnicalPlanEntity.getId().toString(), annualTechnicalPlanEntity.getSubject());
		}
		model.put("planList", JsonUtil.toJson(comboVO.getOptions()));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("/overhaul/overhaulPlan/overhaulPlanAdd", model);
	}
	
	/**
	 * 跳转发文查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goSelectDispatchPage")
	public ModelAndView gotoSelectDispatchPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/overhaul/overhaulPlan/selectDispatchOverhaul",model);
	}
	
	//把ID带回，排除以有的ID
	@RequestMapping("/selectDispatchData")
	public @ResponseBody ResultListObj g(HttpServletRequest request,@RequestBody  Map<String, Object> params){
			
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		page.setOrders(orders);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//5代表检修
		String[] type = {"5"};
		if (conditions != null) {
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING,MatchTypeEnum.IN, type));
		} else {
			conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING,MatchTypeEnum.IN, type));
		}
			
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
	
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			resultObj.setRecordsTotal(dataList.size());
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			}
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
		OverhaulPlanEntity overhaulPlan = overhaulPlanService.findById(id);
		model.put("id", id);
		model.put("entity", overhaulPlan);
		//检修单位下拉树
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 OR C_GENERATION_TYPE = 4 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
        //当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		//检修人下拉列表
		ComboboxVO dutyUserVO = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	dutyUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
    	DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
		model.put("date", df.format(new Date()));
		DateFormatUtil df1= DateFormatUtil.getInstance("yyyy-MM-dd");
		model.put("monthdate", df1.format(new Date()));
        model.put("dutyUser", JsonUtil.toJson(dutyUserVO.getOptions()));
        
        //检修计划名称
  		List<Condition> conditions = new ArrayList<Condition>();
  		ComboboxVO comboVO = new ComboboxVO();
//    		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
  		List<AccidentMeasuresPlanEntity> accidentEntities =  accidentMeasuresPlanService.findAll();
  		for (AccidentMeasuresPlanEntity accidentMeasuresPlanEntity : accidentEntities) {
  			comboVO.addOption(accidentMeasuresPlanEntity.getId().toString(), accidentMeasuresPlanEntity.getSubject());
  		}
  		conditions.clear();
//  		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
  		List<AnnualMaintenancePlanEntity> annualEntities =  annualMaintenancePlanService.findAll();
  		for (AnnualMaintenancePlanEntity annualMaintenancePlanEntity : annualEntities) {
  			comboVO.addOption(annualMaintenancePlanEntity.getId().toString(), annualMaintenancePlanEntity.getAnnualSubject());
  		}
  		
  		conditions.clear();
//  		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));		
  		List<AnnualTechnicalPlanEntity> technicalPlanEntities =  annualTechnicalPlanService.findAll();
  		for (AnnualTechnicalPlanEntity annualTechnicalPlanEntity : technicalPlanEntities) {
  			comboVO.addOption(annualTechnicalPlanEntity.getId().toString(), annualTechnicalPlanEntity.getSubject());
  		}
  		model.put("planList", JsonUtil.toJson(comboVO.getOptions()));
  		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		conditions.clear();
		conditions.add(new Condition("C_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulPlan.getDispatchId()));
//		conditions.add(new Condition("C_STATUS",FieldTypeEnum.INT, MatchTypeEnum.EQ,1));
		List<DispatchManagementEntity> list = dispatchManagementService.findByCondition(conditions, null);
		model.put("userUnitRels", JsonUtil.toJson(list));
		return this.createModelAndView("overhaul/overhaulPlan/overhaulPlanEdit", model);
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/detail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		OverhaulPlanEntity overhaulPlan = overhaulPlanService.findById(id);
		model.put("entity", overhaulPlan);
		return this.createModelAndView("overhaul/overhaulPlan/overhaulPlanDetail", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody OverhaulPlanEntity t, HttpServletRequest request){
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
			List<OverhaulProjectEntity> projectList = t.getList();
	
		if(projectList!=null && projectList.size()>0){
			for(OverhaulProjectEntity projectEntity:projectList){
				try {
					OverhaulProjectEntity entity = overhaulProjectService.findById(projectEntity.getId());
					projectEntity.setCreateDate(entity.getCreateDate());
					projectEntity.setStatus(entity.getStatus());
					projectEntity.setOverhualPlanId(t.getId());
					projectEntity.setStartDate(df.parse(projectEntity.getStartDateString()));
					projectEntity.setEndDate(df.parse(projectEntity.getEndDateString()));
					overhaulProjectService.updateEntity(projectEntity);
				} catch (ParseException e) {
					e.getMessage();
				}
			}
		}
		overhaulPlanService.updateEntity(t);
		 return new ResultObj();
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel/{conditions}")
	public void expData(HttpServletRequest req,HttpServletResponse res,@PathVariable String conditions) throws UnsupportedEncodingException{
		
		List<Map<String,Object>> list = JsonUtil.jsonToObj(conditions,List.class);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("conditions",list);
 		List<OverhaulPlanEntity> dataList=overhaulPlanService.findByCondition(param, null);
 		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "检修计划报表模板.xlsx","检修计划.xlsx", resultMap);
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.OVERHAUL_PLAN_PROCESS_KEY.getName()));
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
		return new ModelAndView("overhaul/overhaulPlan/sureSubmitPerson",resultMap);
	}
	
	/**
	 *	生技部主任/分管领导审批通过
	 */
	@RequestMapping("/overhaulPlanAudit/{taskId}/{id}/{codeBtn}")/*approve*/
	public ModelAndView overhaulPlanAudit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		OverhaulPlanEntity entity = overhaulPlanService.findById(id);
		entity.setAuditBtn(codeBtn);
		model.put("entity", entity);
		
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//获取下一步审批人
		return new ModelAndView("overhaul/overhaulPlan/overhaulPlanAudit",model);
	}
	/**
	 *	检修主任
	 */
	@RequestMapping("/overhaulPlanAuditJxzr/{taskId}/{id}/{codeBtn}")/*approve*/
	public ModelAndView overhaulPlanAuditJxzr(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		OverhaulPlanEntity entity = overhaulPlanService.findById(id);
		entity.setAuditBtn(codeBtn);
		model.put("entity", entity);
		
//		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
//		model.put("userList", userList);//获取下一步审批人
		
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		List<Condition> conditions=new ArrayList<Condition>();
		
		//生技部检修专工
//		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1020"));//生技部检修专工
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1061"));//生技部检修专工
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
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 144));//生产技术处
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
		request.setAttribute("userList", userList);
		
		//集控中心人员
		List<String> userIdList3 = new ArrayList<String>();
		conditions.clear();
//		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"jkzxry"));//集控中心
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1025"));//集控中心主任
		List<SysDutiesEntity>  dutiesEntity3 = sysDutiesService.findByCondition(conditions, null);
		
		String dutyId3=dutiesEntity3.get(0).getId().toString();
		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId3));
		
		List<SysDutiesDetailEntity>  duEntity3 = sysDutiesDetailService.findByCondition(conditions, null);
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
//		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"jkzxry"));//集控中心
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1026"));//集控中心专责
		List<SysDutiesEntity>  dutiesEntity4 = sysDutiesService.findByCondition(conditions, null);
		
		String dutyId4=dutiesEntity4.get(0).getId().toString();
		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId4));
		
		List<SysDutiesDetailEntity>  duEntity4 = sysDutiesDetailService.findByCondition(conditions, null);
		for (SysDutiesDetailEntity sysDutiesDetailEntity : duEntity4) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userIdList3.add(tempuserUnitRel.getUserId().toString());
			}
		}
		conditions.clear();
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 138));//集控中心ID
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList3.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> userList3 = sysUserService.findByCondition(conditions, null);
		request.setAttribute("userList3", userList3);
		
		
		//安监处人员
		conditions.clear();
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1073"));//安全监察处人员
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
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 146));//安全监察处
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList2.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> userList2 = sysUserService.findByCondition(conditions, null);
		request.setAttribute("userList2", userList2);
		
		return new ModelAndView("overhaul/overhaulPlan/overhaulPlanAuditJxzr",model);
	}
	/**
	 *	
	 *  检修主任/专工 审批不通过
	 */
	@RequestMapping("/overhaulPlanAuditMsg/{taskId}/{id}/{codeBtn}")/*approve*/
	public ModelAndView overhaulPlanAuditMsg(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		OverhaulPlanEntity entity = overhaulPlanService.findById(id);
		entity.setAuditBtn(codeBtn);
		model.put("entity", entity);
		return new ModelAndView("overhaul/overhaulPlan/overhaulPlanAuditMsg",model);
	}
	
	/**
	 *	跳转到专工审核
	 */
	@RequestMapping("/overhaulPlanZgtgAudit/{taskId}/{id}/{codeBtn}")
	public ModelAndView getAddQf(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
//		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());//获取下一步审批人
		
		List<Condition> conditions=new ArrayList<Condition>();
		
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1021"));//生技部检修专工
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
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 144));//生产技术处
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
		model.put("userList", userList);
		if (userList.size()>0) {
			SysUserEntity userEntity = userList.get(0);
			model.put("userEntity", userEntity);
		}
		OverhaulPlanEntity entity = overhaulPlanService.findById(id);
		entity.setAuditBtn(codeBtn);
		model.put("entity", entity);
		return new ModelAndView("overhaul/overhaulPlan/overhaulPlanZg",model);
	
	}
	
	
	/**
	 * 提交审核
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody
	ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		OverhaulPlanMemberEntity memberEntity =new OverhaulPlanMemberEntity();
		
		String[] users=params.get("userList").toString().split(",");
		for (String user : users) {
			 
			 List<Condition> conditions = new ArrayList<Condition>();
			 conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, user));
			 SysUserEntity userEntity = sysUserService.findByLoginName(user);
			memberEntity.setCreateUserId(userEntity.getId().toString());
			memberEntity.setCode(user);
			memberEntity.setName(userEntity.getName());
			memberEntity.setOverhaulPlanId(id);
			memberEntity.setCreateDate(new Date());
			overhaulPlanMemberService.addEntity(memberEntity);
		}
	
		return overhaulPlanService.submit(id,params);
	}
	
	/**
	 * 审核通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/auditPass")
	public @ResponseBody ResultObj biotechApprove(@RequestBody OverhaulPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		String nextHandlers = "";
		if ("zrtgBtn".equals(t.getAuditBtn())) {
			String selectedIds = t.getUserList();
			String reviewer[] = selectedIds.split("&");
			nextHandlers = reviewer[0] + "," + reviewer[1] + "," + reviewer[2];
		} else {
			nextHandlers = t.getUserList();
		}
		
		
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),nextHandlers);
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		
		OverhaulPlanMemberEntity memberEntity =new OverhaulPlanMemberEntity();
//		
//	   if (nextHandlers != null && nextHandlers.length()>0) {
//		
//		   String[] users= nextHandlers.split(",");
//		   for (String user : users) {
//			   
//			   List<Condition> conditions = new ArrayList<Condition>();
//			   conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, user));
//			   SysUserEntity userEntity = sysUserService.findByLoginName(user);
//			   memberEntity.setCreateUserId(userEntity.getId().toString());
//			   memberEntity.setCode(user);
//			   memberEntity.setName(userEntity.getName());
//			   memberEntity.setOverhaulPlanId(t.getId());
//			   memberEntity.setCreateDate(new Date());
//			   overhaulPlanMemberService.addEntity(memberEntity);
//		   }
//	}
		return overhaulPlanService.approve(t,params);
	}
	/**
	 * 再次提交
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/againSubmit")
	public @ResponseBody
	ResultObj againSubmit(@RequestBody OverhaulPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		t.setAuditBtn("ztjBtn");//againSubmit
		
		  OverhaulPlanMemberEntity memberEntity =new OverhaulPlanMemberEntity();
			
			String[] users=t.getUserList().toString().split(",");
			for (String user : users) {
				 
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, user));
				SysUserEntity userEntity = sysUserService.findByLoginName(user);
				memberEntity.setCreateUserId(userEntity.getId().toString());
				memberEntity.setCode(user);
				memberEntity.setName(userEntity.getName());
				memberEntity.setOverhaulPlanId(t.getId());
				memberEntity.setCreateDate(new Date());
				overhaulPlanMemberService.addEntity(memberEntity);
			}
		return overhaulPlanService.approve(t,params);
	}


	/**
	 * 流程中修改提交(生技部检修专工流转和修改)
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/saveOverhaulPlan")
	public @ResponseBody
	ResultObj saveOverhaulPlan(@RequestBody OverhaulPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.AGREE.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		
		  OverhaulPlanMemberEntity memberEntity =new OverhaulPlanMemberEntity();
			
			String[] users=t.getUserList().toString().split(",");
			for (String user : users) {
				 
				 List<Condition> conditions = new ArrayList<Condition>();
				 conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, user));
				 SysUserEntity userEntity = sysUserService.findByLoginName(user);
				memberEntity.setCreateUserId(userEntity.getId().toString());
				memberEntity.setCode(user);
				memberEntity.setName(userEntity.getName());
				memberEntity.setOverhaulPlanId(t.getId());
				memberEntity.setCreateDate(new Date());
				overhaulPlanMemberService.addEntity(memberEntity);
			}
		return overhaulPlanService.approve(t,params);
	}
	/**
	 * 审核不通过
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/auditReject")
	public @ResponseBody
	ResultObj auditReject(@RequestBody OverhaulPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		return overhaulPlanService.approve(t,params);
	}
	/**
	 * 不同意执行
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/unAuditPass")
	public @ResponseBody
	ResultObj unAuditPass(@RequestBody OverhaulPlanEntity t) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		return overhaulPlanService.approve(t,params);
	}
	/**
	 * 流程取消
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/cancel")
	public @ResponseBody
	ResultObj cancel(@RequestBody OverhaulPlanEntity t) {
		t.setAuditBtn("qxBtn");
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.RESULT.getCode(), ExamResultEnum.BACK.getName());;
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		  OverhaulPlanMemberEntity memberEntity =new OverhaulPlanMemberEntity();
			
//			String[] users=t.getUserList().toString().split(",");
//			for (String user : users) {
//				 
//				 List<Condition> conditions = new ArrayList<Condition>();
//				 conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, user));
//				 SysUserEntity userEntity = sysUserService.findByLoginName(user);
//				memberEntity.setCreateUserId(userEntity.getId().toString());
//				memberEntity.setCode(user);
//				memberEntity.setName(userEntity.getName());
//				memberEntity.setOverhaulPlanId(t.getId());
//				memberEntity.setCreateDate(new Date());
//				overhaulPlanMemberService.addEntity(memberEntity);
//			}
		return overhaulPlanService.approve(t,params);
	}
	
	/**
	 *	审核页面跳转
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		OverhaulPlanEntity overhaulPlanEntity = overhaulPlanService.findById(id);
		model.put("overhualPlanId",id.intValue());
		List<OverhaulProjectEntity> projectList = overhaulProjectService.findByCondition(model, null);
		model.put("entity", overhaulPlanEntity);
		model.put("projectList", projectList);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.OVERHAUL_PLAN_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			System.out.println(nodeList.size());
			model.put("nodeList", nodeList);
		}
		return this.createModelAndView("overhaul/overhaulPlan/overhaulPlanApprove",model);
	}
	
	
	/**
	 *	跳转到修改页面（流程中）
	 */
	@RequestMapping("/overhaulPlanEdit/{taskId}/{id}/{codeBtn}")
	public ModelAndView overhaulPlanEdit(HttpServletRequest request,@PathVariable String taskId,@PathVariable Long id,@PathVariable String codeBtn){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OverhaulPlanEntity overhaulPlan = overhaulPlanService.findById(id);
		model.put("id", id);
		overhaulPlan.setAuditBtn(codeBtn);
		model.put("entity", overhaulPlan);
		//检修单位下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//检修人下拉列表
		//当前用户
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		//检修人下拉列表
		ComboboxVO dutyUserVO = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
        for(SysUserEntity sysUserEntity : allUsers){
        	dutyUserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
	
		model.put("dutyUser", JsonUtil.toJson(dutyUserVO.getOptions()));
		model.put("overhualPlanId",id.intValue());
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("overhaul/overhaulPlan/overhaulPlanApproveEdit", model);
	}
	
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<OverhaulPlanEntity> page = PageUtil.getPage(params);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<OverhaulPlanEntity> dataList = overhaulPlanService.findByCondition(conditions, page);
		SysUserEntity userEntity= RequestContext.get().getUser();
		if (dataList != null && dataList.size() > 0) {
			for (OverhaulPlanEntity entity : dataList) {
				if (OverhaulPlanStatusEnum.PROFESSIONALWORKER.getCode().equals(entity.getApproveStatus())){
					String overhaulReviewFlag = entity.getOverhaulWorkerReviewFlag();
					String centralizeReviewFlag = entity.getCentralizedReviewFlag();
					String safetyReviewFlag = entity.getSafetyReviewFlag();
					
					List<String> overhaulIdList = new ArrayList<String>();
					List<String> centralizeIdList = new ArrayList<String>();
					List<String> safetyIdList = new ArrayList<String>();
					String overhaulSelecteds = entity.getOverhaulWorkerSelected();
					String[] overhaulSelectedIds  = overhaulSelecteds.split(",");
					for (int i=0;i< overhaulSelectedIds.length ;i++) {
						overhaulIdList.add(overhaulSelectedIds[i]);
					}
					String centralizeSeleceds = entity.getCentralizedSelected();
					String[] centralizeSelecedIds = centralizeSeleceds.split(",");
					for (int i=0;i< centralizeSelecedIds.length ;i++) {
						centralizeIdList.add(centralizeSelecedIds[i]);
					}
					String safetySelecteds = entity.getSafetySelected();
					String[] safetySelectedIds = safetySelecteds.split(",");
					for (int i=0;i< safetySelectedIds.length ;i++) {
						safetyIdList.add(safetySelectedIds[i]);
					}
					if (!"1".equals(overhaulReviewFlag) && !"1".equals(centralizeReviewFlag) && !"1".equals(safetyReviewFlag)) {
						//未走专工审批流程
						if (overhaulIdList.contains(userEntity.getLoginName())) {
							//登录用户属于生技部检修专工审核人时,状态是“生技部检修专工审核”
							entity.setApproveStatusString(OverhaulReviewEnum.OVERHAUL_WORKER_REVIEW.getName());
						} else if (centralizeIdList.contains(userEntity.getLoginName())) {
							//登录用户属于集控中心审核人时,状态是“集控中心审核”
							entity.setApproveStatusString(OverhaulReviewEnum.CENTRRALIZE_REVIEW.getName());
						} else if (safetyIdList.contains(userEntity.getLoginName())) {
							//登录用户属于安监处审核人时,状态是“安监处审核”
							entity.setApproveStatusString(OverhaulReviewEnum.SAFETY_REVIEW.getName());
						} else {
							//登录用户即不属于集控中心审核人，也不属于安监处审核人,也不属于生技部检修专工审核人时,状态是“生技部检修专工审核”
							entity.setApproveStatusString(OverhaulReviewEnum.OVERHAUL_WORKER_REVIEW.getName());
						}
					} else if ("1".equals(overhaulReviewFlag) && !"1".equals(centralizeReviewFlag) && !"1".equals(safetyReviewFlag)) {
						//生技部检修专工审核同意,集控中心和安监部未审批
						if (centralizeIdList.contains(userEntity.getLoginName())) {
							//登录用户属于集控中心审核人时,状态是“集控中心审核”
							entity.setApproveStatusString(OverhaulReviewEnum.CENTRRALIZE_REVIEW.getName());
						} else if (safetyIdList.contains(userEntity.getLoginName())) {
							//登录用户属于安监处审核人时,状态是“安监处审核”
							entity.setApproveStatusString(OverhaulReviewEnum.SAFETY_REVIEW.getName());
						} else {
							//登录用户即不属于集控中心审核人，也不属于安监处审核人时,状态是“集控中心审核”
							entity.setApproveStatusString(OverhaulReviewEnum.CENTRRALIZE_REVIEW.getName());
						}
					} else if (!"1".equals(overhaulReviewFlag) && "1".equals(centralizeReviewFlag) && !"1".equals(safetyReviewFlag)) {
						// 集控中心审核同意,生技部检修专工和安监部未审批
						//登录用户属于生技部检修专工审核人时,状态是“生技部检修专工审核”
						if (overhaulIdList.contains(userEntity.getLoginName())) {
							entity.setApproveStatusString(OverhaulReviewEnum.OVERHAUL_WORKER_REVIEW.getName());
						}  else if (safetyIdList.contains(userEntity.getLoginName())) {
							//登录用户属于安监处审核人时,状态是“安监处审核”
							entity.setApproveStatusString(OverhaulReviewEnum.SAFETY_REVIEW.getName());
						} else {
							//登录用户即不属于生技部检修专工审核人，也不属于安监处审核人时,状态是“集控中心审核”
							entity.setApproveStatusString(OverhaulReviewEnum.OVERHAUL_WORKER_REVIEW.getName());
						}
					} else if ("1".equals(overhaulReviewFlag) && "1".equals(centralizeReviewFlag) && !"1".equals(safetyReviewFlag)) {
						//生技部检修专工审核 和 集控中心同意,安监部未审批
						entity.setApproveStatusString(OverhaulReviewEnum.SAFETY_REVIEW.getName());

					} else if (!"1".equals(overhaulReviewFlag) && "1".equals(centralizeReviewFlag) && "1".equals(safetyReviewFlag)) {
						// 集控中心审核和安监部同意,生技部检修专工未审批
						//登录用户属于生技部检修专工审核人时,状态是“生技部检修专工审核”
						entity.setApproveStatusString(OverhaulReviewEnum.OVERHAUL_WORKER_REVIEW.getName());
					} else if ("1".equals(overhaulReviewFlag) && !"1".equals(centralizeReviewFlag) && "1".equals(safetyReviewFlag)) {
						// 生技部检修专工审核 和 安监部同意,集控中心审核未审批
						entity.setApproveStatusString(OverhaulReviewEnum.CENTRRALIZE_REVIEW.getName());
					}
				} else {
					for (OverhaulPlanStatusEnum overhaulPlanStatusEnum : OverhaulPlanStatusEnum.values()) {
						if (overhaulPlanStatusEnum.getCode().equals(entity.getApproveStatus())) {
							entity.setApproveStatusString(overhaulPlanStatusEnum.getName());
						}
					}
				}
			}
		}
		
		
	
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal(dataList.size());
			}
		}
		return resultObj;
	}
	
}