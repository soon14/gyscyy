package com.aptech.business.managePlanContract.yearPurchase.web;

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

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseRealEntity;
import com.aptech.business.managePlanContract.yearPurchase.service.YearPurchaseRealService;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
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
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * 年度采购计划实际填报配置控制器
 *
 * @author 
 * @created 2018-09-04 13:38:00
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/yearPurchaseReal")
public class YearPurchaseRealController extends BaseController<YearPurchaseRealEntity> {
	
	@Autowired
	private YearPurchaseRealService yearPurchaseRealService;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<YearPurchaseRealEntity> getService() {
		return yearPurchaseRealService;
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
		List<YearPurchaseRealEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("yearPurchaseRealTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboYearPurchaseRealVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("yearPurchaseRealCombobox", JsonUtil.toJson(comboYearPurchaseRealVO.getOptions()));
		return this.createModelAndView("yearPurchaseReal/resource/views/yearPurchaseRealList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{yearPurchaseId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable String yearPurchaseId){
		Map<String, Object> model = new HashMap<String, Object>();
		//设备类别
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("EQUIPTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("typeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
 		model.put("yearPurchaseId", yearPurchaseId);
		
		return this.createModelAndView("managePlanContract/yearPurchase/yearPurchaseRealAdd", model);
	}
	
	/**
	 * @Description:   添加保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/addSave")
	public @ResponseBody ResultObj addSave(@RequestBody YearPurchaseRealEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		yearPurchaseRealService.addEntity(t);
//		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		YearPurchaseRealEntity yearPurchaseRealEntity = (YearPurchaseRealEntity)yearPurchaseRealService.findById(id);
		model.put("entity", yearPurchaseRealEntity);
		model.put("entityJson", JsonUtil.toJson(yearPurchaseRealEntity));
		//设备类别
		ComboboxVO planTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> planTypeMap = DictionaryUtil.getDictionaries("EQUIPTYPE");
		for(String key : planTypeMap.keySet()){
			SysDictionaryVO planTypeVO = planTypeMap.get(key);
			planTypeCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("typeCombobox", JsonUtil.toJson(planTypeCombobox.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
 		model.put("yearPurchaseId", yearPurchaseRealEntity.getYearPurchaseId());
		
		return this.createModelAndView("managePlanContract/yearPurchase/yearPurchaseRealEdit", model);
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/editSave/{id}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody YearPurchaseRealEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		yearPurchaseRealService.updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
//		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		 return resultObj;
	}
	/**
	* 跳转到查看页面
	* @author ly
	* @date 2018年9月4日 下午6:45:34 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		YearPurchaseRealEntity yearPurchaseRealEntity = (YearPurchaseRealEntity)yearPurchaseRealService.findById(id);
		model.put("entity", yearPurchaseRealEntity);
		model.put("entityJson", JsonUtil.toJson(yearPurchaseRealEntity));
 		model.put("yearPurchaseId", yearPurchaseRealEntity.getYearPurchaseId());
 		
		return this.createModelAndView("managePlanContract/yearPurchase/yearPurchaseRealDetail", model);
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
		
		Page<YearPurchaseRealEntity> page=new Page<YearPurchaseRealEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.asc("C_ID"));
		
		List<YearPurchaseRealEntity> dataList=yearPurchaseRealService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
   	    ExcelUtil.export(req, res, "年度实际采购报表模板.xlsx","年度实际采购.xlsx", resultMap);
		
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
		ExcelUtil.export(req, res, "年度实际采购(批量导入模板).xlsx","年度实际采购(批量导入模板).xlsx", resultMap);
	}
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd/{yearPurchaseId}")
	public ModelAndView getBatchAdd(HttpServletRequest request,@PathVariable String yearPurchaseId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("yearPurchaseId", yearPurchaseId);
		return this.createModelAndView("managePlanContract/yearPurchase/yearPurchaseRealBatchAdd", params);
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
		String yearId = request.getParameter("yearId").toString();

		String filePath = request.getSession().getServletContext().getRealPath(fileUrl);
		File newFile = new File(filePath);
		importExcelData(request,newFile,OriginalFilename,yearId);
		return resultObj;
	}
	
	public boolean importExcelData(HttpServletRequest request,File file,String OriginalFilename,String yearId) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			if (OriginalFilename.contains("xls") || OriginalFilename.contains("xlsx")) {
				Workbook workbook = getWorkbook(inputStream);
				return read(workbook,request,yearId);
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
	private boolean read(Workbook wb,HttpServletRequest request,String yearId) throws ParseException, Exception{
		
		boolean result = true;

		Sheet sheet = wb.getSheetAt(0);  
		if (sheet == null) {
			return false;
		}
		List<YearPurchaseRealEntity> list  = importPurchaseData(sheet,yearId);
	
		List<Condition> conditions = new ArrayList<Condition>();
		for (YearPurchaseRealEntity yearPurchaseRealEntity : list) {
			
			String name = yearPurchaseRealEntity.getName();
			String type = yearPurchaseRealEntity.getType();
			String specification = yearPurchaseRealEntity.getSpecification();
			String amount = yearPurchaseRealEntity.getAmount();
			String budgetPrice = yearPurchaseRealEntity.getBudgetPrice();
			String totalPrice = yearPurchaseRealEntity.getTotalPrice();
			Date realBuyTime=yearPurchaseRealEntity.getRealBuyTime();
//			String code = yearPurchaseEntity.getCode();
	
			//取得项目配置
			conditions.clear();
			conditions.add(new Condition("C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, name));
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, type));
			conditions.add(new Condition("C_SPECIFICATION", FieldTypeEnum.STRING, MatchTypeEnum.EQ, specification));
			conditions.add(new Condition("C_AMOUNT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, amount));
			conditions.add(new Condition("C_BUDGET_PRICE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, budgetPrice));
			conditions.add(new Condition("C_TOTAL_PRICE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, totalPrice));
			conditions.add(new Condition("C_REAL_BUY_TIME", FieldTypeEnum.DATE, MatchTypeEnum.EQ, realBuyTime));
			conditions.add(new Condition("C_YEAR_PURCHASE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, yearId));
//			conditions.add(new Condition("a.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, code));
		
			List<YearPurchaseRealEntity> settingEntityList = this.yearPurchaseRealService.findByCondition(conditions, null);
			if (settingEntityList != null && settingEntityList.size() >0 && settingEntityList.get(0) != null) {
				yearPurchaseRealEntity.setId(settingEntityList.get(0).getId());
				yearPurchaseRealService.updateEntity(yearPurchaseRealEntity);
			} else {
				yearPurchaseRealService.addEntity(yearPurchaseRealEntity);
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
	private List<YearPurchaseRealEntity> importPurchaseData(Sheet sheet,String yearId) throws ParseException {
		List<YearPurchaseRealEntity> list = new ArrayList<YearPurchaseRealEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		int rowCount = sheet.getPhysicalNumberOfRows();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			YearPurchaseRealEntity yeRealEntity = new YearPurchaseRealEntity();
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
						//设备名称
						yeRealEntity.setName(cellValue);
						break;
					case 2://设备类型
						Map<String, SysDictionaryVO> specialCodeMap  =  DictionaryUtil.getDictionaries("EQUIPTYPE");
						for(SysDictionaryVO sysDictionaryVO : specialCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								yeRealEntity.setType(sysDictionaryVO.getCode());
							}
						}
						break;	
					case 3:
						//规格型号
						yeRealEntity.setSpecification(cellValue);
						break;
					case 4:
						//实际采购时间
						if(cellValue!=null && cellValue!=""){
						yeRealEntity.setRealBuyTime(dUtil.parse(cellValue));
						}
						break;
					case 5:
						//数量
						yeRealEntity.setAmount(cellValue);
						break;
					case 6:
						//实际单价
						yeRealEntity.setBudgetPrice(cellValue);
						break;
					case 7:
						//实际总价
						yeRealEntity.setTotalPrice(cellValue);
						//年度计划采购id
						yeRealEntity.setYearPurchaseId(yearId);
						break;
					default:  
						cellValue = "未知类型";  
						break;  
				}
			}
			
			list.add(yeRealEntity);
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
}