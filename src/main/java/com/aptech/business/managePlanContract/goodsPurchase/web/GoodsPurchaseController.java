package com.aptech.business.managePlanContract.goodsPurchase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.GoodsPurchaseBtnTypeEnum;
import com.aptech.business.component.dictionary.GoodsPurchaseStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.YearPurchaseBtnTypeEnum;
import com.aptech.business.component.dictionary.ifEndStatusEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.goodsPurchase.domain.GoodsPurchaseEntity;
import com.aptech.business.managePlanContract.goodsPurchase.service.GoodsPurchaseService;
import com.aptech.business.managePlanContract.goodsRelation.domain.GoodsRelationEntity;
import com.aptech.business.managePlanContract.goodsRelation.service.GoodsRelationService;
import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.business.managePlanContract.purchaseOrganization.service.PurchaseOrganizationService;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.common.act.service.ActTaskService;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 物资采购配置控制器
 *
 * @author 
 * @created 2018-04-17 15:00:02
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/goodsPurchase")
public class GoodsPurchaseController extends BaseController<GoodsPurchaseEntity> {
	
	@Autowired
	private GoodsPurchaseService goodsPurchaseService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private GoodsRelationService goodsRelationService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private PurchaseOrganizationService purchaseOrganizationService;

	@Override
	public IBaseEntityOperation<GoodsPurchaseEntity> getService() {
		return goodsPurchaseService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/list/{unitId}")
	public ModelAndView index(HttpServletRequest request, Map<String, Object> params,@PathVariable Long unitId) {
		Map<String, Object> model = new HashMap<String, Object>();
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("GOODS_PLAN_TYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		
		SysUnitEntity sysUnitEntity = sysUnitService.findById(unitId);
	     model.put("organizationEntity", sysUnitEntity);
	     model.put("unitId", unitId);
	        
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseMenu", model);
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
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("GOODS_PLAN_TYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("planCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		
		//编码类型
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("GOODS_PURCHASE_STATUS");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("statusTypes", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        
        //采购计划名称
  		ComboboxVO comboboxPlanNameVO = new ComboboxVO();
  		//采购名称
  		ComboboxVO comboboxNameVO = new ComboboxVO();
//  		SysUnitEntity sysUnitEntity = sysUnitService.findById(unitId);
  		List<Condition> conditions1 = new ArrayList<Condition>();
//  		conditions1.add(new Condition("a.C_COMPANY_UNIT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUnitEntity.getId()));
  		
  		List<GoodsPurchaseEntity> nameList = goodsPurchaseService.findByCondition(conditions1, null);
  		
  	   for(GoodsPurchaseEntity entity : nameList){
  		 comboboxPlanNameVO.addOption(entity.getId().toString(), entity.getProjectName());
          }
          model.put("planNameList1", JsonUtil.toJson(comboboxPlanNameVO.getOptions()));
          for(GoodsPurchaseEntity entity : nameList){
        	  comboboxNameVO.addOption(entity.getId().toString(), entity.getName());
          }
          model.put("nameList1", JsonUtil.toJson(comboboxNameVO.getOptions()));
        
        //处室下拉框
        List<Condition> conditions = new ArrayList<Condition>();
  		conditions.add(new Condition("C_GENERATION_TYPE = 4 "));
  		conditions.add(new Condition(" C_ORGANIZATION = 5 "));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
    		for(SysUnitEntity unitEntity : unitList){
    			combouserVO.addOption(unitEntity.getId().toString(), unitEntity.getName());
    	    }
    	model.put("unitList", JsonUtil.toJson(combouserVO.getOptions()));
    	
    	//是否关闭下拉框
  		ComboboxVO comboboxIfEndVO = new ComboboxVO();
        for (ifEndStatusEnum ifEndStatusEnum : ifEndStatusEnum.values()) {
      	  comboboxIfEndVO.addOption(ifEndStatusEnum.getCode().toString(), ifEndStatusEnum.getName());
        }
        model.put("ifEndList", JsonUtil.toJson(comboboxIfEndVO.getOptions()));
        
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
   		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
 		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
 		model.put("userName", userEntity.getName());
 		
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
 		
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("EQUIPTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("typeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		
		//计划分类
		ComboboxVO planCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planMap = DictionaryUtil.getDictionaries("GOODS_PLAN_TYPE");
		for(String key : planMap.keySet()){
			SysDictionaryVO planVO = planMap.get(key);
			planCombobox.addOption(planVO.getCode(), planVO.getName());
			model.put("planCode", planVO.getCode());
		}
		model.put("planCombobox", JsonUtil.toJson(planCombobox.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
 		//计数单位下拉框
		Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
		ComboboxVO comboUnitVO = new ComboboxVO();
		for(String key : unitMap.keySet()){
			SysDictionaryVO sysDictionaryVO = unitMap.get(key);
			comboUnitVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("unitCombobox", JsonUtil.toJson(comboUnitVO.getOptions()));
		
		 //处室下拉框
        List<Condition> conditions = new ArrayList<Condition>();
  		conditions.add(new Condition("C_GENERATION_TYPE = 4 "));
  		conditions.add(new Condition(" C_ORGANIZATION = 5 "));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
    		for(SysUnitEntity unitEntity : unitList){
    			combouserVO.addOption(unitEntity.getId().toString(), unitEntity.getName());
    	    }
    	model.put("unitList", JsonUtil.toJson(combouserVO.getOptions()));
    	
		
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		GoodsPurchaseEntity goodsPurchaseEntity = (GoodsPurchaseEntity)goodsPurchaseService.findById(id);
		model.put("entity", goodsPurchaseEntity);
		model.put("entityJson", JsonUtil.toJson(goodsPurchaseEntity));
		
		//计划分类
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("EQUIPTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("typeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
		
		//计划分类
		ComboboxVO planCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planMap = DictionaryUtil.getDictionaries("GOODS_PLAN_TYPE");
		for(String key : planMap.keySet()){
			SysDictionaryVO planVO = planMap.get(key);
			planCombobox.addOption(planVO.getCode(), planVO.getName());
		}
		model.put("planCombobox", JsonUtil.toJson(planCombobox.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(goodsPurchaseEntity.getCreateUserId()));
 		model.put("userName", userEntity.getName());
 		//计数单位下拉框
		Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
		ComboboxVO comboUnitVO = new ComboboxVO();
		for(String key : unitMap.keySet()){
			SysDictionaryVO sysDictionaryVO = unitMap.get(key);
			comboUnitVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("unitCombobox", JsonUtil.toJson(comboUnitVO.getOptions()));
		
		 //处室下拉框
        List<Condition> conditions = new ArrayList<Condition>();
  		conditions.add(new Condition("C_GENERATION_TYPE = 4 "));
  		conditions.add(new Condition(" C_ORGANIZATION = 5 "));
  		
  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
  		ComboboxVO combouserVO = new ComboboxVO();
    		for(SysUnitEntity unitEntity : unitList){
    			combouserVO.addOption(unitEntity.getId().toString(), unitEntity.getName());
    	    }
    	model.put("unitList", JsonUtil.toJson(combouserVO.getOptions()));
		
		
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseEdit", model);
	}
	
	/**
	 * 查看页面
	 * @param request
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		GoodsPurchaseEntity goodsPurchaseEntity = (GoodsPurchaseEntity)goodsPurchaseService.findById(id);
		model.put("entity", goodsPurchaseEntity);
		model.put("entityJson", JsonUtil.toJson(goodsPurchaseEntity));
        //获取登录人信息
 		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(goodsPurchaseEntity.getCreateUserId()));
 		model.put("userName", userEntity.getName());
 		
 		if (goodsPurchaseEntity.getCompanyUnit()!=null && goodsPurchaseEntity.getCompanyUnit()!="") {
 			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.parseLong(goodsPurchaseEntity.getCompanyUnit()));
 			model.put("organizationEntity", sysUnitEntity);
		}
 		//责任处室
 		SysUnitEntity unitEntity = sysUnitService.findById(Long.parseLong(goodsPurchaseEntity.getDutyUnit()));
 	    model.put("unitEntity", unitEntity);

		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseDetail", model);
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveEditPage/{id}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody GoodsPurchaseEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		GoodsPurchaseEntity purchaseEntity = goodsPurchaseService.findById(t.getId());
		t.setStatus(purchaseEntity.getStatus());
		t.setCreateUserId(purchaseEntity.getCreateUserId());
		t.setCreateDate(purchaseEntity.getCreateDate());
		goodsPurchaseService.updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.GOODSPURCHASE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		 return resultObj;
	}
	
	/**
	 * @Description:   添加保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveAdd")
	public @ResponseBody ResultObj saveAdd(@RequestBody GoodsPurchaseEntity t, HttpServletRequest request) {
		return goodsPurchaseService.addEntityList(t, request);
//		ResultObj resultObj = new ResultObj();
//		goodsPurchaseService.addEntity(t);
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		GoodsPurchaseEntity purchaseEntity = goodsPurchaseService.findById(t.getId());
//		List<GoodsRelationEntity> rList = t.getGoodsList();
//		for (GoodsRelationEntity goodsEntity : rList) {
//
//			goodsEntity.setCreateDate(new Date());
//			goodsEntity.setCreateUserId(userEntity.getId().toString());
//			goodsEntity.setGoodsPurchaseId(purchaseEntity.getId());
//			
//			goodsRelationService.addEntity(goodsEntity);
//		}
//		
//		return resultObj;
		
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
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		Page<GoodsPurchaseEntity> page=new Page<GoodsPurchaseEntity>();
//		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<GoodsPurchaseEntity> dataList=goodsPurchaseService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
   	    ExcelUtil.export(req, res, "零星采购报表模板.xlsx","零星采购.xlsx", resultMap);
		
		
	}
	/**
	 * 批量导出模板
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcelModel")
	public void exportExcel(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		ExcelUtil.export(req, res, "零星采购(批量导入模板).xlsx","零星采购(批量导入模板).xlsx", resultMap);
	}
	
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd/{unitId}")
	public ModelAndView getBatchAdd(HttpServletRequest request,@PathVariable String unitId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("unitId", unitId);
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseBatchAdd", params);
	}
	
	/**
	 * 批量导入数据
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importData")
	public  @ResponseBody ResultObj importData(HttpServletRequest request,@RequestBody Map<String, Object> params) throws Exception {
		ResultObj resultObj = new ResultObj();
		String OriginalFilename = (String)params.get("fileName");
		String fileUrl = (String)params.get("fileUrl");
		String unitId = (String)params.get("unitId");

		String filePath = request.getSession().getServletContext().getRealPath(fileUrl);
		File newFile = new File(filePath);
		importExcelData(request,newFile,OriginalFilename,unitId);
		return resultObj;
	}
	
	public boolean importExcelData(HttpServletRequest request,File file,String OriginalFilename,String unitId) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			if (OriginalFilename.contains("xls") || OriginalFilename.contains("xlsx")) {
				Workbook workbook = getWorkbook(inputStream);
				return read(workbook,request,unitId);
			}
//			if (OriginalFilename.contains("xls")) {
//				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);// HSSFWorkbook
//				return read(hssfWorkbook,request);
//			} else if (OriginalFilename.contains("xlsx")) {
//				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);// XSSFWorkbook
//				return read(xssfWorkbook,request);
//			}
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	private Workbook getWorkbook(InputStream inputStream) throws Exception {

		Workbook wb = WorkbookFactory.create(inputStream);

		return wb;
	}
	
	/**
	 * @Description:   读取EXCEL
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws         Exception
	 */
	private boolean read(Workbook wb,HttpServletRequest request,String unitId) throws ParseException, Exception{
		
		boolean result = true;

		Sheet sheet = wb.getSheetAt(0);  
		if (sheet == null) {
			return false;
		}
		List<GoodsPurchaseEntity> list  = importPurchaseData(sheet,unitId);
	
		List<Condition> conditions = new ArrayList<Condition>();
		for (GoodsPurchaseEntity goodsPurchaseEntity : list) {
			
			String name = goodsPurchaseEntity.getName();
			String type = goodsPurchaseEntity.getType();
			String planType =goodsPurchaseEntity.getPlanType();
			String specification = goodsPurchaseEntity.getSpecification();
			String amount = goodsPurchaseEntity.getAmount();
			String budgePrice = goodsPurchaseEntity.getBudgePrice();
			String totalPrice = goodsPurchaseEntity.getTotalPrice();
			
			Date buyTime=goodsPurchaseEntity.getBuyTime();
			String projectName = goodsPurchaseEntity.getProjectName();
			String userId = goodsPurchaseEntity.getCreateUserId();
			String status = goodsPurchaseEntity.getStatus();
			String remark = goodsPurchaseEntity.getRemark();
			String unit = goodsPurchaseEntity.getUnit();
			String dutyUnit = goodsPurchaseEntity.getDutyUnit();
			String ifEndStatus = goodsPurchaseEntity.getIfEndStatus();
			String companyUnit = goodsPurchaseEntity.getCompanyUnit();
//			String code = goodsPurchaseEntity.getCode();
	
			//取得项目配置
			conditions.clear();
			conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, name));
			conditions.add(new Condition("a.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, type));
			conditions.add(new Condition("a.C_PLAN_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, planType));
			conditions.add(new Condition("a.C_SPECIFICATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, specification));
			conditions.add(new Condition("a.C_AMOUNT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, amount));
			conditions.add(new Condition("a.C_BUDGE_PRICE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, budgePrice));
			conditions.add(new Condition("a.C_TOTAL_PRICE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, totalPrice));
			conditions.add(new Condition("a.C_BUY_TIME", FieldTypeEnum.DATE, MatchTypeEnum.EQ, buyTime));
			conditions.add(new Condition("a.C_PROJECT_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, projectName));
			conditions.add(new Condition("a.C_CREATE_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userId));
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, status));
			conditions.add(new Condition("a.C_REMARK", FieldTypeEnum.STRING, MatchTypeEnum.EQ, remark));
			conditions.add(new Condition("a.C_UNIT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unit));
			conditions.add(new Condition("a.C_DUTY_UNIT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, dutyUnit));
			conditions.add(new Condition("a.C_IF_END_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ifEndStatus));
			conditions.add(new Condition("a.C_COMPANY_UNIT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, companyUnit));
//			conditions.add(new Condition("a.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		
			List<GoodsPurchaseEntity> settingEntityList = this.goodsPurchaseService.findByCondition(conditions, null);
			if (settingEntityList != null && settingEntityList.size() >0 && settingEntityList.get(0) != null) {
				goodsPurchaseEntity.setId(settingEntityList.get(0).getId());
				goodsPurchaseService.updateEntity(goodsPurchaseEntity);
			} else {
				
				goodsPurchaseService.addEntity(goodsPurchaseEntity);
			}
		}
		
		return result;  
	}
	
	/**
	 * 信息数据导入
	 * @param sheet
	 * @return
	 * @throws ParseException 
	 */
	private List<GoodsPurchaseEntity> importPurchaseData(Sheet sheet,String unitId) throws ParseException {
		List<GoodsPurchaseEntity> list = new ArrayList<GoodsPurchaseEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		DateFormatUtil creatUtil = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
		int rowCount = sheet.getPhysicalNumberOfRows();
		List<Condition> conditions = new ArrayList<Condition>();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			GoodsPurchaseEntity yeEntity = new GoodsPurchaseEntity();
			row = sheet.getRow(r);  
			if (row == null|| isBlankRow(row)){  
				continue;  
			}
			for (int c = 1; c < row.getPhysicalNumberOfCells()+1; c++){
				Cell cell = row.getCell(c);  
				String cellValue = "";  
				if (null != cell){  
					// 以下是判断数据的类型  
					switch (cell.getCellType()){ 
					//XSSFCell可以达到相同的效果
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字或时间 
						double d = cell.getNumericCellValue();
						if (HSSFDateUtil.isCellDateFormatted(cell)){//日期类型
							Date date = HSSFDateUtil.getJavaDate(d);
							
							cellValue =dUtil.format(date);
						}else {//数值类型
							int cellValue1 = (int)d;
							cellValue = cellValue1 + "";
						}
//						cellValue = String.valueOf(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串  
						cellValue = cell.getStringCellValue();  
						break;  
					case HSSFCell.CELL_TYPE_BLANK: // 空值  
						cellValue = "";  
						break;  
					default:  
						cellValue = "未知类型";  
						break;  
					}
				} 
				switch (c) {
//					case 1:
//						//计划编号
//						yeEntity.setCode(cellValue);
//						break;
					case 1:
						//项目采购名称
						yeEntity.setName(cellValue);
						break;
					case 2://设备类别
						Map<String, SysDictionaryVO> specialCodeMap  =  DictionaryUtil.getDictionaries("EQUIPTYPE");
						for(SysDictionaryVO sysDictionaryVO : specialCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								yeEntity.setType(sysDictionaryVO.getCode());
							}
						}
						break;	
						
					case 3://物资采购计划类别
						Map<String, SysDictionaryVO> planCodeMap  =  DictionaryUtil.getDictionaries("GOODS_PLAN_TYPE");
						for(SysDictionaryVO sysDictionaryVO : planCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								yeEntity.setPlanType(sysDictionaryVO.getCode());
							}
						}
						break;	
						
					case 4:
						//规格型号
						yeEntity.setSpecification(cellValue);
						break;
					case 5:
						//数量
						yeEntity.setAmount(cellValue);
						break;
					case 6:
						//计数单位
						Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
						for(SysDictionaryVO sysDictionaryVO : unitMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								yeEntity.setUnit(sysDictionaryVO.getCode());
							}
						}
						break;
					case 7:
						//预算单价
						yeEntity.setBudgePrice(cellValue);
						break;
					case 8:
						//预算总价
						yeEntity.setTotalPrice(cellValue);
						break;
					case 9:
						//预计采购时间
						if(cellValue!=null && cellValue!=""){
						yeEntity.setBuyTime(dUtil.parse(cellValue));
						}
						break;
					case 10:
						//采购计划名称
						yeEntity.setProjectName(cellValue);
						break;
					case 11:
						//采购人员
						if(cellValue!=null && cellValue!=""){
							conditions.clear();
							conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, cellValue));
							List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
							if(userList!=null && userList.size()>0){
								yeEntity.setCreateUserId(userList.get(0).getId().toString());
							}
						}
						break;
					case 12:
						//计划处室
						conditions.clear();
				  		conditions.add(new Condition("C_GENERATION_TYPE = 4 "));
				  		conditions.add(new Condition(" C_ORGANIZATION = 5 "));
				  		conditions.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,cellValue));
				  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
				    	if(unitList!=null&&unitList.size()>0){
				    		yeEntity.setDutyUnit(unitList.get(0).getId().toString());
				    	}
						break;
					case 13:
						//备注
						yeEntity.setRemark(cellValue);
						//流程状态赋初始值
						yeEntity.setStatus(GoodsPurchaseStatusEnum.PENDING.getCode());
						//是否关闭赋初始值
						yeEntity.setIfEndStatus(ifEndStatusEnum.opten.getCode());
						//计划所属场站id
						yeEntity.setCompanyUnit(unitId);
						//增加创建时间
						yeEntity.setCreateDate(creatUtil.parse(creatUtil.format(new Date())));
						break;
					default:  
						cellValue = "未知类型";
						break;  
				}
			}
			
			list.add(yeEntity);
		}
		return list;
	}
	  /**
     * @Description:   处理空格
     * @author         wangcc 
     * @throws         Exception
     */
  	public  boolean isBlankRow(Row row){
          if(row == null) return true;
          boolean result = true;
          for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
              Cell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
              String value = "";
              if(cell != null){
                  switch (cell.getCellType()) {
                  case Cell.CELL_TYPE_STRING:
                      value = cell.getStringCellValue();
                      break;
                  case Cell.CELL_TYPE_NUMERIC:
                      value = String.valueOf((int) cell.getNumericCellValue());
                      break;
                  case Cell.CELL_TYPE_BOOLEAN:
                      value = String.valueOf(cell.getBooleanCellValue());
                      break;
                  case Cell.CELL_TYPE_FORMULA:
                      value = String.valueOf(cell.getCellFormula());
                      break;
                  case Cell.CELL_TYPE_BLANK:
                	  value="";
                      break;
                  default:
                      break;
                  }
                   
                  if(!value.trim().equals("")){
                      result = false;
                      break;
                  }
              }
          }
           
          return result;
      }
  	
  	
	/**
	 *	审核页面跳转
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request,@PathVariable Long id,@PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		//查询各个人的按钮权限 开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.GOODS_PURCHASE_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		//返回前提数据
		GoodsPurchaseEntity goodsPurchaseEntity = goodsPurchaseService.findById(id);
		model.put("entity", goodsPurchaseEntity);
		
        //获取登录人信息
 		SysUserEntity sysUserEntity= sysUserService.findById(Long.parseLong(goodsPurchaseEntity.getCreateUserId()));
 		model.put("userName", sysUserEntity.getName());
 		
 		if (goodsPurchaseEntity.getCompanyUnit()!=null && goodsPurchaseEntity.getCompanyUnit()!="") {
 			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.parseLong(goodsPurchaseEntity.getCompanyUnit()));
 			model.put("organizationEntity", sysUnitEntity);
		}
 		//责任处室
 		SysUnitEntity unitEntity = sysUnitService.findById(Long.parseLong(goodsPurchaseEntity.getDutyUnit()));
 	    model.put("unitEntity", unitEntity);
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseApprove",model);
	}
	
	
	/**
	 * 选择审核人
	 * @param request
	 * @return
	 */
	@RequestMapping("/sureSubmit")
	public ModelAndView sureSubmitPerson(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		String id=request.getParameter("id");
		model.put("id", id);
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.GOODS_PURCHASE_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		String modelId="";
		if(!defList.isEmpty()){
			modelId=defList.get(0).getModelId();
		}
		//审批页面点击签发按钮的时候，把下一步的人查询出来
//		SysUserEntity starter= null;
//		if(!RequestContext.get().isDeveloperMode()){
//			starter = RequestContext.get().getUser();
//		}
		//List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
		conditions.clear();
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String [] codes ={"1052","1055"};//职务编码
		conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.IN,codes));
		List<SysDutiesEntity>  dutiesEntity = sysDutiesService.findByCondition(conditions, null);
	
		String [] strings ={dutiesEntity.get(0).getId().toString(),dutiesEntity.get(1).getId().toString()};
		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN,strings));
		
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
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userIdList.toArray()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.EQ, 1));
		List<SysUserEntity> userList = sysUserService.findByCondition(conditions, null);
		request.setAttribute("userList", userList);
		return this.createModelAndView("managePlanContract/goodsPurchase/sureSubmitPerson", model);
	}
	
	/**
	 * @Description:   提交方法
	 * @author         zhangzq 
	 * @Date           2017年6月12日 上午11:39:16 
	 * @throws         Exception
	 */
	@RequestMapping("/submit")
	public @ResponseBody ResultObj submit(@RequestBody YearPurchaseEntity entity, HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		this.goodsPurchaseService.submit(entity.getId().toString(), entity.getUserList());
		return resultObj;
	}
	
	
	
	/**
	 *	跳转到综合管理
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
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseQf", model);
	}
	
	/**
	 *	跳转到生产技术处
	 */
	@RequestMapping("/getAddHqf")
	public ModelAndView getAddHqf(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		model.put("dateHQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseHqf", model);
	}
	
	/**
	* @Title: getAddajfzrsh
	* @Description: 跳转到计划经营处
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
        return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchasePlan", model);
    }
	
	
	/**
	 *	跳转到单位领导
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
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchaseLeader", model);
	}
	/**
	 *	跳转到计划经营处执行
	 */
	@RequestMapping("/getAddAqzj")
	public ModelAndView getAddAqzj(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String electricId=request.getParameter("electricId");
		String taskId=request.getParameter("taskId");
		GoodsPurchaseEntity workFireEntity = goodsPurchaseService.findById(Long.valueOf(electricId));
		model.put("workFireEntity", workFireEntity);
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId, ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);//审批页面点击签发按钮的时候，把下一步的人查询出来
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userEntity", userEntity);
	     model.put("dateQf", new Date());
		model.put("electricId", electricId);
		return this.createModelAndView("managePlanContract/goodsPurchase/goodsPurchasePz", model);
	}

	/**
	 * @Description:   签发人同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreeQf")
	public @ResponseBody ResultObj agreeQf(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.synmangeBtn.getCode());
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
			goodsPurchaseService.updateSpnrAgree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeQf(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.synmangeBtn.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			SysUserEntity fzrEntity=sysUserService.findById(workFireEntity.getCreateUserId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			goodsPurchaseService.updateSpnrDisagree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj agreeHQf(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.technologyBtn.getCode());
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
			
			goodsPurchaseService.updateSpnrAgree(workFireEntity,userEntity);
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
	public @ResponseBody ResultObj disAgreeHQf(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.technologyBtn.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			SysUserEntity fzrEntity=sysUserService.findById(workFireEntity.getCreateUserId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			goodsPurchaseService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   计划经营处审核同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreexfSh")
	public @ResponseBody ResultObj agreexfSh(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.planBtn.getCode());
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
			goodsPurchaseService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:   计划经营处审核不同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreexfSh")
	public @ResponseBody ResultObj disAgreexfSh(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.planBtn.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			SysUserEntity fzrEntity=sysUserService.findById(workFireEntity.getCreateUserId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			goodsPurchaseService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
	/**
	 * @Description:   安全领导审批同意
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/agreePz")
	public @ResponseBody ResultObj agreePz(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.leaderBtn.getCode());
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
			goodsPurchaseService.updateSpnrAgree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	 * @Description:  安全领导审批驳回
	 * @author         zhangzq 
	 * @Date           20170608
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreePz")
	public @ResponseBody ResultObj disAgreePz(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.leaderBtn.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			//查询工作负责人的loginName
			SysUserEntity fzrEntity=sysUserService.findById(workFireEntity.getCreateUserId());
			//查询工作负责人的loginName
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			
			goodsPurchaseService.updateSpnrDisagree(workFireEntity,userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	
/**
 * 计划经营处执行
 * @param request
 * @param workFireEntity
 * @return
 */
	@RequestMapping("/agreeAqzj")
    public @ResponseBody ResultObj agreeAqzj(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
        ResultObj resultObj = new ResultObj();
        try {
            workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.planoperationBtn.getCode());
            String taskId=request.getParameter("taskId");
            String procInstId=request.getParameter("procInstId");
            SysUserEntity userEntity= RequestContext.get().getUser();
            
            Map<String, Object> taskVariables=new HashMap<String, Object>();
            taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
            taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
            taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.AGREE.getName());
            taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
            
           actTaskService.complete(taskId,procInstId, taskVariables);
           goodsPurchaseService.updateSpnrAgree(workFireEntity,userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }
	
	/**
	 * @Description:   废票
	 * @author         zhangzq 
	 * @Date           2017年7月5日 上午10:08:02 
	 * @throws         Exception
	 */
	@RequestMapping("/disAgreeFp")
	public @ResponseBody ResultObj disAgreeFp(HttpServletRequest request,@RequestBody GoodsPurchaseEntity workFireEntity){
		ResultObj resultObj = new ResultObj();
		try {
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.FP.getCode());
			String taskId=request.getParameter("taskId");
			String procInstId=request.getParameter("procInstId");
			String selectUser=request.getParameter("selectUser");
			SysUserEntity userEntity= RequestContext.get().getUser();
			Map<String, Object> taskVariables=new HashMap<String, Object>();
			taskVariables.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),"");
			taskVariables.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.BACK.getId());
			taskVariables.put(ExamMarkEnum.RESULT.getCode(),ExamResultEnum.BACK.getName());
			taskVariables.put(ExamMarkEnum.COMMENT.getCode(),workFireEntity.getApproveIdea()==null?"":workFireEntity.getApproveIdea());//审批意见
			actTaskService.complete(taskId,procInstId,taskVariables);
			goodsPurchaseService.updateSpnrDisagree(workFireEntity,userEntity);
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
			GoodsPurchaseEntity workFireEntity=new GoodsPurchaseEntity();
			workFireEntity.setSpFlag(GoodsPurchaseBtnTypeEnum.ZTJ.getCode());
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
			goodsPurchaseService.updateSpnrAgree(workFireEntity, userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObj;
	}
	/**
	* 物资采购关闭
	* @author ly
	* @date 2018年8月27日 上午9:44:51 
	* @param request
	* @param id
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/endPlan/{id}")
	public @ResponseBody ResultObj endPlan(HttpServletRequest request,@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		GoodsPurchaseEntity goodsPurchaseEntity = (GoodsPurchaseEntity)goodsPurchaseService.findById(id);
		goodsPurchaseEntity.setIfEndStatus(ifEndStatusEnum.opten.getCode());
		goodsPurchaseService.updateEntity(goodsPurchaseEntity);	
		return resultObj;
	}
}