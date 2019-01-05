package com.aptech.business.contractManage.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import com.aptech.business.component.dictionary.ContractLifeEnum;
import com.aptech.business.component.dictionary.ContractManageApproveStatusEnum;
import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.SysManagementStatusEnum;
import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.business.contractManage.service.ContractManageService;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.business.managePlanContract.supplierManage.service.SupplierManageService;
import com.aptech.business.sysManagement.domain.SysManagementEntity;
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
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.finishTask.service.FinishTaskService;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * 合同管理配置控制器
 *
 * @author 
 * @created 2018-04-17 14:03:40
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/contractManage")
public class ContractManageController extends BaseController<ContractManageEntity> {
	
	@Autowired
	private ContractManageService contractManageService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private FinishTaskService finishTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private SupplierManageService supplierManageService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<ContractManageEntity> getService() {
		return contractManageService;
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
		List<ContractManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("contractManageTreeList", JsonUtil.toJson(treeNodeList));
		//状态下拉框
        ComboboxVO codeDateTypesCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("ZBFS");
        
        for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("zbfsType", JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
        //所属部门
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
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));	
		
		conditions.clear();
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginId", sysUserEntity.getId().toString());
		model.put("loginName", sysUserEntity.getLoginName());
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
		return this.createModelAndView("contractManage/contractManageList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
        
        //所属部门
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
		model.put("ssbmList", JsonUtil.toJson(unitIdCombobox.getOptions()));
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));	
		
		//供应商
  		ComboboxVO supplierIdCombobox = new ComboboxVO();
  		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
  		
  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
  		
  		for(SupplierManageEntity entity : supplierIdList){
  			supplierIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("supplierIdList", JsonUtil.toJson(supplierIdCombobox.getOptions()));
  		// 合同年限
  			ComboboxVO contractLife = new ComboboxVO();
  			for (ContractLifeEnum contractLifeEnum : ContractLifeEnum.values()) {
  				contractLife.addOption(contractLifeEnum.getCode(),
  						contractLifeEnum.getName());
  			}
  			model.put("contractLife",
  					JsonUtil.toJson(contractLife.getOptions()));
		return this.createModelAndView("contractManage/contractManageAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ContractManageEntity contractManageEntity = (ContractManageEntity)contractManageService.findById(id);
		model.put("entity", contractManageEntity);
		model.put("entityJson", JsonUtil.toJson(contractManageEntity));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
        //所属部门
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
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
		
		//供应商
  		ComboboxVO supplierIdCombobox = new ComboboxVO();
  		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
  		
  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
  		
  		for(SupplierManageEntity entity : supplierIdList){
  			supplierIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("supplierIdList", JsonUtil.toJson(supplierIdCombobox.getOptions()));
  		// 合同年限
		ComboboxVO contractLife = new ComboboxVO();
		for (ContractLifeEnum contractLifeEnum : ContractLifeEnum.values()) {
			contractLife.addOption(contractLifeEnum.getCode(),
					contractLifeEnum.getName());
		}
		model.put("contractLife",
				JsonUtil.toJson(contractLife.getOptions()));
		return this.createModelAndView("contractManage/contractManageEdit", model);
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getShow/{id}")
	public ModelAndView getShowPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ContractManageEntity contractManageEntity = (ContractManageEntity)contractManageService.findById(id);
		SysUnitEntity unitEntity=sysUnitService.findById(Long.valueOf(contractManageEntity.getContractXdname()));
		contractManageEntity.setContractXdname(unitEntity.getName());
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key :  codeDateTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
        	if(sysDictionaryVO.getCode().equals(contractManageEntity.getZbfs())){
        		contractManageEntity.setZbfsName(sysDictionaryVO.getName());
        	}else{
        		contractManageEntity.setZbfsName("");
        	}
        }
		//供应商转中文
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Long.valueOf(contractManageEntity.getRemark())));
  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
  		if(!supplierIdList.isEmpty()){
  			contractManageEntity.setRemarkName(supplierIdList.get(0).getName());
  		}
		model.put("entity", contractManageEntity);
		model.put("entityJson", JsonUtil.toJson(contractManageEntity));
		String contractEndDateStr = contractManageEntity.getContractEndDate().substring(0, 10);
		model.put("contractEndDateStr", contractEndDateStr);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		
		return this.createModelAndView("contractManage/contractManageShow", model);
	}
	
	
	
	/**
	 * 
	 * 入库管理导出功能
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月25日 下午4:16:46
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String contractXdname = req.getParameter("contractXdname");
		String contractCode = req.getParameter("contractCode");
		String contractName = req.getParameter("contractName");
		String searchstatisticsDate = req.getParameter("searchstatisticsDate");
		String qdnf=req.getParameter("qdnf");//获取前台到底用没用年份查询
		Page<ContractManageEntity> pages = new Page<ContractManageEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		DateFormatUtil  dd=DateFormatUtil.getInstance("yyyy");
		if(qdnf==null||qdnf.equals("")){
			conditions.add(new Condition("C_QDRQ", FieldTypeEnum.STRING, MatchTypeEnum.LIKE, dd.format(new Date())));
		}
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		if(StringUtil.isNotEmpty(contractCode)){
			conditions.add(new Condition("C_CONTRACT_CODE", FieldTypeEnum.STRING,MatchTypeEnum.LIKE,contractCode));
		}
		if(StringUtil.isNotEmpty(contractName)){
			conditions.add(new Condition("C_CONTRACT_NAME", FieldTypeEnum.STRING,MatchTypeEnum.LIKE,contractName));
		}
		if(StringUtil.isNotEmpty(contractXdname)){
			conditions.add(new Condition("C_CONTRACT_XDNAME", FieldTypeEnum.STRING,MatchTypeEnum.EQ,contractXdname));
		}
		if(StringUtil.isNotEmpty(searchstatisticsDate)){
			conditions.add(new Condition("C_QDRQ", FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchstatisticsDate));
		}
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
		List<ContractManageEntity> dataList=contractManageService.findByCondition(conditions, pages);
		for (ContractManageEntity contractManageEntity : dataList) {
			contractManageEntity.setQdrqString(df.format(contractManageEntity.getQdrq()));
			SysUnitEntity unit=sysUnitService.findById(Long.valueOf(contractManageEntity.getContractXdname()));
			contractManageEntity.setContractXdname(unit.getName());
			if(contractManageEntity.getYjsmoney().equals(Istypicaleum.ISSETNO.getCode())){
				contractManageEntity.setYjsmoneyName(Istypicaleum.ISSETNO.getName());
			}
			if(contractManageEntity.getYjsmoney().equals(Istypicaleum.ISSETYES.getCode())){
				contractManageEntity.setYjsmoneyName(Istypicaleum.ISSETYES.getName());
			}
			//供应商转中文
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
			if(contractManageEntity.getRemark()!=null){
				conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Long.valueOf(contractManageEntity.getRemark())));
			}
	  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
	  		if(!supplierIdList.isEmpty()){
	  			contractManageEntity.setRemarkName(supplierIdList.get(0).getName());
	  		}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "合同台帐管理报表模板.xlsx","合同台帐管理.xlsx", resultMap);
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
		ExcelUtil.export(req, res, "合同台账管理(批量导入模板).xlsx","合同台账管理(批量导入模板).xlsx", resultMap);
	}
	
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd")
	public ModelAndView getBatchAdd(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		return this.createModelAndView("contractManage/contractManagerBatchAdd", params);
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

		String filePath = request.getSession().getServletContext().getRealPath(fileUrl);
		File newFile = new File(filePath);
		importExcelData(request,newFile,OriginalFilename);
		return resultObj;
	}
	
	public boolean importExcelData(HttpServletRequest request,File file,String OriginalFilename) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			if (OriginalFilename.contains("xls") || OriginalFilename.contains("xlsx")) {
				Workbook workbook = getWorkbook(inputStream);
				return read(workbook,request);
			}
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
	private boolean read(Workbook wb,HttpServletRequest request) throws ParseException, Exception{
		
		boolean result = true;

		Sheet sheet = wb.getSheetAt(0);  
		if (sheet == null) {
			return false;
		}
		List<ContractManageEntity> list  = importPurchaseData(sheet);
		SysUserEntity userEntity= RequestContext.get().getUser();
		for (ContractManageEntity contractManageEntity : list) {
			contractManageEntity.setCreateUserId(userEntity.getId().toString());
			contractManageEntity.setCreateDate(new Date());
			contractManageEntity.setUpdateUserId(userEntity.getId().toString());
			contractManageEntity.setUpdateDate(new Date());
			contractManageEntity.setYjsmoney(Istypicaleum.ISSETNO.getCode());
			contractManageEntity.setStatus("1");
			contractManageService.addEntity(contractManageEntity);
		}
		
		return result;  
	}
	
	/**
	 * 信息数据导入
	 * @param sheet
	 * @return
	 * @throws ParseException 
	 */
	private List<ContractManageEntity> importPurchaseData(Sheet sheet) throws ParseException {
		List<ContractManageEntity> list = new ArrayList<ContractManageEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		int rowCount = sheet.getPhysicalNumberOfRows();
		List<Condition> conditions = new ArrayList<Condition>();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			ContractManageEntity proEntity = new ContractManageEntity();
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
							double cellValue1 = (double)d;
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
					case 1:
						//所属部门
						conditions.clear();
						String [] organizationIds = {"1", "3", "4", "7"};
						conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
						conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
						conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
				  		conditions.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,cellValue));
				  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
				    	if(unitList!=null&&unitList.size()>0){
				    		proEntity.setContractXdname(unitList.get(0).getId().toString());
				    	}
						break;
					case 2:
						//合同编号
						if(cellValue!=null && cellValue!=""){
							proEntity.setContractCode(String.valueOf(cellValue));
						}
						break;
					case 3:
						//合同名称
						proEntity.setContractName(cellValue);
						break;
					case 4:
						//供应商
						conditions.clear();
						conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.DELETE.ordinal())));
						conditions.add(new Condition("a.C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,cellValue));
				  		List<SupplierManageEntity> supplierIdList = supplierManageService.findByCondition(conditions, null);
				  		if(!supplierIdList.isEmpty()){
				  			proEntity.setRemark(supplierIdList.get(0).getId().toString());
				  		}
						break;
						
					case 5:
						//合同金额
						proEntity.setContractMoney(cellValue);
						break;
					case 6:
						//签约时间
						if(cellValue!=null && cellValue!=""){
						proEntity.setQdrq(dUtil.parse(cellValue));
						}
						break;
					case 7:
						//采购方式
						Map<String, SysDictionaryVO> purchaseCodeMap  =  DictionaryUtil.getDictionaries("PURCHASE_MODE");
						for(SysDictionaryVO sysDictionaryVO : purchaseCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								proEntity.setZbfs(sysDictionaryVO.getCode());
							}
						}
						break;
					case 8:
						//备注
						if(cellValue!=null && cellValue!=""){
							if(cellValue.equals(Istypicaleum.ISSETNO.getName())){
								proEntity.setYjsmoney(Istypicaleum.ISSETNO.getCode());
							}
							if(cellValue.equals(Istypicaleum.ISSETYES.getName())){
								proEntity.setYjsmoney(Istypicaleum.ISSETYES.getCode());
							}
						}
						break;
					default:  
						cellValue = "未知类型";  
						break;  
				}
			}
			
			list.add(proEntity);
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
	 * 
	 * 打回招标采购
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @param farmId
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年11月4日 下午1:50:55
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStop/{id}")
	public @ResponseBody ResultObj resultConfirmStop(HttpServletRequest request, @PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		ContractManageEntity contractManageEntity = contractManageService.findById(id);
		contractManageEntity.setStatus(ContractManageApproveStatusEnum.PENDING.getCode());
		contractManageService.updateEntity(contractManageEntity);
		return resultObj;
	}
}