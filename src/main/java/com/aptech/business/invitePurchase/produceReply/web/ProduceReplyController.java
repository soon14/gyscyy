package com.aptech.business.invitePurchase.produceReply.web;

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

import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.ifEndStatusEnum;
import com.aptech.business.component.dictionary.itemTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.invitePurchase.produceReply.domain.ProduceReplyEntity;
import com.aptech.business.invitePurchase.produceReply.service.ProduceReplyService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
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
 * 立项批复配置控制器
 *
 * @author ly
 * @created 2018-09-07 14:52:40
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/produceReply")
public class ProduceReplyController extends BaseController<ProduceReplyEntity> {
	
	@Autowired
	private ProduceReplyService produceReplyService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	  private SysDutiesService sysDutiesService;
	  @Autowired
	  private SysDutiesDetailService sysDutiesDetailService;
	  @Autowired
	  private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<ProduceReplyEntity> getService() {
		return produceReplyService;
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
        
  		//立项单位
  		ComboboxVO departmentIdCombobox = new ComboboxVO();
  		conditions.clear();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
  		
  		List<SysUnitEntity> departmentIdList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : departmentIdList){
  			departmentIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("departmentIdList", JsonUtil.toJson(departmentIdCombobox.getOptions()));
		
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
		
		//事项类型（撤回用）
		model.put("itemType", itemTypeEnum.LX.getCode());
		
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
		
		return this.createModelAndView("invitePurchase/produceReply/produceReplyList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
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
        
  		//立项单位
  		ComboboxVO departmentIdCombobox = new ComboboxVO();
  		conditions.clear();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
  		
  		List<SysUnitEntity> departmentIdList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : departmentIdList){
  			departmentIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("departmentIdList", JsonUtil.toJson(departmentIdCombobox.getOptions()));
		
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
		
		return this.createModelAndView("invitePurchase/produceReply/produceReplyAdd", model);
	}
	
	/**
	 * @Description:   添加保存
	 * @author        ly
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/addSave")
	public @ResponseBody ResultObj addSave(@RequestBody ProduceReplyEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateId(userEntity.getId().toString());
		produceReplyService.addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PRODUCE_REPLY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ProduceReplyEntity produceReplyEntity = (ProduceReplyEntity)produceReplyService.findById(id);
		model.put("entity", produceReplyEntity);
		model.put("entityJson", JsonUtil.toJson(produceReplyEntity));
		
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
        
  		//立项单位
  		ComboboxVO departmentIdCombobox = new ComboboxVO();
  		conditions.clear();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
  		
  		List<SysUnitEntity> departmentIdList = sysUnitService.findByCondition(conditions, null);
  		
  		for(SysUnitEntity entity : departmentIdList){
  			departmentIdCombobox.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("departmentIdList", JsonUtil.toJson(departmentIdCombobox.getOptions()));
		
		//采购方式
		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("PURCHASE_MODE");
		for(String key : purchaseModeIdMap.keySet()){
			SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
			purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
		}
		model.put("purchaseModeIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
		
		return this.createModelAndView("invitePurchase/produceReply/produceReplyEdit", model);
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody ProduceReplyEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
//		ProduceReplyEntity produceReplyEntity = produceReplyService.findById(t.getId());
		produceReplyService.updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PRODUCE_REPLY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		 return resultObj;
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetailPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ProduceReplyEntity produceReplyEntity = (ProduceReplyEntity)produceReplyService.findById(id);
		model.put("entity", produceReplyEntity);
		model.put("entityJson", JsonUtil.toJson(produceReplyEntity));
		
		
		return this.createModelAndView("invitePurchase/produceReply/produceReplyDetail", model);
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
		
		Page<ProduceReplyEntity> page=new Page<ProduceReplyEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.asc("C_ID"));
		
		List<ProduceReplyEntity> dataList=produceReplyService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
   	    ExcelUtil.export(req, res, "立项批复报表模板.xlsx","立项批复.xlsx", resultMap);
		
		
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
		ExcelUtil.export(req, res, "立项批复(批量导入模板).xlsx","立项批复(批量导入模板).xlsx", resultMap);
	}
	
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd")
	public ModelAndView getBatchAdd(HttpServletRequest request){
		Map<String, Object> params = new HashMap<String, Object>();
		return this.createModelAndView("invitePurchase/produceReply/produceReplyBatchAdd", params);
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
		List<ProduceReplyEntity> list  = importPurchaseData(sheet);
	
		List<Condition> conditions = new ArrayList<Condition>();
		for (ProduceReplyEntity produceReplyEntity : list) {
			produceReplyService.addEntity(produceReplyEntity);
		}
		
		return result;  
	}
	
	/**
	 * 信息数据导入
	 * @param sheet
	 * @return
	 * @throws ParseException 
	 */
	private List<ProduceReplyEntity> importPurchaseData(Sheet sheet) throws ParseException {
		List<ProduceReplyEntity> list = new ArrayList<ProduceReplyEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		int rowCount = sheet.getPhysicalNumberOfRows();
		List<Condition> conditions = new ArrayList<Condition>();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			ProduceReplyEntity proEntity = new ProduceReplyEntity();
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
				    		proEntity.setUnitId(unitList.get(0).getId().toString());
				    	}
						break;
					case 2:
						//采购事项
						proEntity.setName(cellValue);
						break;
					case 3:
						//立项单位
						conditions.clear();
						conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
						conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
						conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.EQ, "5"));
				  		conditions.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,cellValue));
				  		List<SysUnitEntity> departmentList = sysUnitService.findByCondition(conditions, null);
				    	if(departmentList!=null&&departmentList.size()>0){
				    		proEntity.setDepartmentId(departmentList.get(0).getId().toString());
				    	}
						break;
					case 4:
						//现估价
						proEntity.setJudge(cellValue);
						break;
					case 5:
						//采购方式
						Map<String, SysDictionaryVO> purchaseCodeMap  =  DictionaryUtil.getDictionaries("PURCHASE_MODE");
						for(SysDictionaryVO sysDictionaryVO : purchaseCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								proEntity.setPurchaseModeId(sysDictionaryVO.getCode());
							}
						}
						break;
					case 6:
						//预计采购时间
						if(cellValue!=null && cellValue!=""){
						proEntity.setUpdateDate(dUtil.parse(cellValue));
						}
						break;
					case 7:
						//备注
						proEntity.setRemark(cellValue);
						proEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
						proEntity.setFile("[]");
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
			ProduceReplyEntity  produceReplyEntity=produceReplyService.findById(Long.valueOf(ids.get(i)));
			if(produceReplyEntity!=null){
				produceReplyEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
				produceReplyService.updateEntity(produceReplyEntity);
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
		ProduceReplyEntity  produceReplyEntity=produceReplyService.findById(id);
		if(produceReplyEntity!=null){
			produceReplyEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			produceReplyService.updateEntity(produceReplyEntity);
		}
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.COMPANY_TRENDS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
}