package com.aptech.business.safeStep.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.invitePurchase.produceReply.domain.ProduceReplyEntity;
import com.aptech.business.safeStep.domain.SafeStepEntity;
import com.aptech.business.safeStep.service.SafeStepService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
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
import com.aptech.framework.web.base.ResultObj;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * 安全技术措施计划配置控制器
 *
 * @author 
 * @created 2018-09-14 14:40:53
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeStep")
public class SafeStepController extends BaseController<SafeStepEntity> {
	
	@Autowired
	private SafeStepService safeStepService;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<SafeStepEntity> getService() {
		return safeStepService;
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
		//生产单位
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
  		//安措分类
		ComboboxVO classifyListCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> classifyMap = DictionaryUtil.getDictionaries("SAFE_CLASSIFY");
		for(String key : classifyMap.keySet()){
			SysDictionaryVO planTypeVO = classifyMap.get(key);
			classifyListCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("classifyList", JsonUtil.toJson(classifyListCombobox.getOptions()));
		return this.createModelAndView("safeStep/safeStepList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//生产单位
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
  		//安措分类
		ComboboxVO classifyListCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> classifyMap = DictionaryUtil.getDictionaries("SAFE_CLASSIFY");
		for(String key : classifyMap.keySet()){
			SysDictionaryVO planTypeVO = classifyMap.get(key);
			classifyListCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("classifyList", JsonUtil.toJson(classifyListCombobox.getOptions()));
		
		return this.createModelAndView("safeStep/safeStepAdd", model);
	}
	
	/**
	 * @Description:   添加保存
	 * @author        ly
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/addSave")
	public @ResponseBody ResultObj addSave(@RequestBody SafeStepEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		this.getService().addEntity(t);
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
		SafeStepEntity safeStepEntity = (SafeStepEntity)safeStepService.findById(id);
		model.put("entity", safeStepEntity);
		model.put("entityJson", JsonUtil.toJson(safeStepEntity));
		
		//生产单位
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
  		//安措分类
		ComboboxVO classifyListCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> classifyMap = DictionaryUtil.getDictionaries("SAFE_CLASSIFY");
		for(String key : classifyMap.keySet()){
			SysDictionaryVO planTypeVO = classifyMap.get(key);
			classifyListCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("classifyList", JsonUtil.toJson(classifyListCombobox.getOptions()));
		
		return this.createModelAndView("safeStep/safeStepEdit", model);
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody SafeStepEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
//		ProduceReplyEntity produceReplyEntity = produceReplyService.findById(t.getId());
		this.getService().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
//		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		 return resultObj;
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeStepEntity safeStepEntity = (SafeStepEntity)this.getService().findById(id);
		model.put("entity", safeStepEntity);
		model.put("entityJson", JsonUtil.toJson(safeStepEntity));
		
		
		return this.createModelAndView("safeStep/safeStepDetail", model);
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
		
		Page<SafeStepEntity> page=new Page<SafeStepEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<SafeStepEntity> dataList=this.getService().findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
   	    ExcelUtil.export(req, res, "安全技术措施计划报表模板.xlsx","安全技术措施计划.xlsx", resultMap);
		
		
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
		ExcelUtil.export(req, res, "安全技术措施计划(批量导入模板).xlsx","安全技术措施计划(批量导入模板).xlsx", resultMap);
	}
	
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd")
	public ModelAndView getBatchAdd(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		return this.createModelAndView("safeStep/safeStepBatchAdd", params);
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
		List<SafeStepEntity> list  = importPurchaseData(sheet);
	
		List<Condition> conditions = new ArrayList<Condition>();
		for (SafeStepEntity safeStepEntity : list) {
			this.getService().addEntity(safeStepEntity);
		}
		
		return result;  
	}
	
	/**
	 * 信息数据导入
	 * @param sheet
	 * @return
	 * @throws ParseException 
	 */
	private List<SafeStepEntity> importPurchaseData(Sheet sheet) throws ParseException {
		List<SafeStepEntity> list = new ArrayList<SafeStepEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		int rowCount = sheet.getPhysicalNumberOfRows();
		List<Condition> conditions = new ArrayList<Condition>();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			SafeStepEntity safeEntity = new SafeStepEntity();
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
						//生产单位
						conditions.clear();
						String [] organizationIds = {"1", "3", "4", "7"};
						conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
						conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
						conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
				  		conditions.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,cellValue));
				  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
				    	if(unitList!=null&&unitList.size()>0){
				    		safeEntity.setUnitId(unitList.get(0).getId().toString());
				    	}
						break;
					case 2:
						//分类
						Map<String, SysDictionaryVO> classifyMap  =  DictionaryUtil.getDictionaries("SAFE_CLASSIFY");
						for(SysDictionaryVO sysDictionaryVO : classifyMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								safeEntity.setClassify(sysDictionaryVO.getCode());
							}
						}
						break;
					case 3:
						//内容
						safeEntity.setContent(cellValue);
						break;
					case 4:
						//费用预算
						safeEntity.setMoney(cellValue);
						break;
					case 5:
						//完成时间
						safeEntity.setEndTime(cellValue);
						break;
					case 6:
						//备注
						safeEntity.setRemark(cellValue);
						break;
					default:  
						cellValue = "未知类型";  
						break;  
				}
			}
			
			list.add(safeEntity);
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
			SafeStepEntity  safeStepEntity=this.getService().findById(Long.valueOf(ids.get(i)));
			if(safeStepEntity!=null){
				this.getService().deleteEntity(safeStepEntity);
			}
		}
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
		SafeStepEntity safeStepEntity=this.getService().findById(id);
		if(safeStepEntity!=null){
			this.getService().deleteEntity(safeStepEntity);
		}
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
}