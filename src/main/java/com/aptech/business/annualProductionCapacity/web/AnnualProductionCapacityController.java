package com.aptech.business.annualProductionCapacity.web;

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

import com.aptech.business.annualProductionCapacity.domain.AnnualProductionCapacityEntity;
import com.aptech.business.annualProductionCapacity.service.AnnualProductionCapacityService;
import com.aptech.business.component.dictionary.AnnualQuotaStatusEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.defectManage.defectStatistics.domain.DefectStatisticsEntity;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.invitePurchase.contractApprove.domain.ContractApproveEntity;
import com.aptech.business.quotaPlanApprove.domain.QuotaPlanApproveEntity;
import com.aptech.business.quotaPlanApprove.service.QuotaPlanApproveService;
import com.aptech.business.quotaPlanHistory.domain.QuotaPlanHistoryEntity;
import com.aptech.business.quotaPlanHistory.service.QuotaPlanHistoryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
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
 * 年度生产量计划配置控制器
 *
 * @author 
 * @created 2018-09-17 18:30:24
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/annualProductionCapacity")
public class AnnualProductionCapacityController extends BaseController<AnnualProductionCapacityEntity> {
	
	@Autowired
	private AnnualProductionCapacityService annualProductionCapacityService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private QuotaPlanHistoryService quotaPlanHistoryService;
	@Autowired
	private QuotaPlanApproveService quotaPlanApproveService;
	
	@Override
	public IBaseEntityOperation<AnnualProductionCapacityEntity> getService() {
		return annualProductionCapacityService;
	}
	
	/**
	 *	生产量tab页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView tab(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("annualProductionCapacity/annualProductionCapacityTab", model);
	}
	/**
	 *	生产量tab页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/listThree")
	public ModelAndView tabThree(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("annualProductionCapacity/annualProductionQuotaTabTwo", model);
	}
	/**
	 *	生产量返回跳转到第二个tab页面
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/listTwo")
	public ModelAndView tabTwo(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("annualProductionCapacity/annualProductionCapacityTabTwo", model);
	}
	/**
	 *	生产指标tab页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/quotaList")
	public ModelAndView quotaTab(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("annualProductionCapacity/annualProductionQuotaTab", model);
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
  		
  		//指标名称
  		ComboboxVO purchaseModeIdCombobox = new ComboboxVO();
  		if("3".equals(type)){
  			//对应年度生产指标计划
  			Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("ANNUALQUOTATYPE");
  			for(String key : purchaseModeIdMap.keySet()){
  				SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
  				purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
  			}
  			model.put("quotaIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
  		}else{
  			//对应年度生产量计划
  			Map<String, SysDictionaryVO> purchaseModeIdMap = DictionaryUtil.getDictionaries("ANNUAL_PRODUCTION_TYPE");
  			for(String key : purchaseModeIdMap.keySet()){
  				SysDictionaryVO planTypeVO = purchaseModeIdMap.get(key);
  				purchaseModeIdCombobox.addOption(planTypeVO.getCode(), planTypeVO.getName());
  			}
  			model.put("quotaIdList", JsonUtil.toJson(purchaseModeIdCombobox.getOptions()));
  		}
  		
  		//根据不同tab页显示不同的table名称
  		if("1".equals(type)){
  			model.put("tableName","集团年度生产量计划");
  			return this.createModelAndView("annualProductionCapacity/annualProductionCapacity1List", model);
  		}else if("2".equals(type)){
  			model.put("tableName","内控年度生产量计划");
  			return this.createModelAndView("annualProductionCapacity/annualProductionCapacity2List", model);
  		}else{
  			model.put("tableName","年度生产指标计划");
  			return this.createModelAndView("annualProductionCapacity/annualProductionQuotaList", model);
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
		params.put("type", type);
		
		Page<AnnualProductionCapacityEntity> page=new Page<AnnualProductionCapacityEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.asc("C_ID"));
		
		List<AnnualProductionCapacityEntity> dataList= new ArrayList<AnnualProductionCapacityEntity>();
		dataList=(List<AnnualProductionCapacityEntity>)this.getService().findByCondition(params, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		//合并单元格
		//TODO
		List<int[]> dataSize=new ArrayList<int[]>();
		int start=2;
		int start1 = 2;
		int count = dataList.size()-6;
		int count1 = dataList.size()-1;
		int count2 = dataList.size()-8;
		if("3".equals(type)){
			for (int i=0;i<dataList.size();i++) {
				AnnualProductionCapacityEntity ds=dataList.get(i);
				if("show".equals(ds.getTdHide())&&i!=0){
					dataSize.add(new int []{start,1+i,1,1});
					start=1+i+1;
				}else if(i==count2){
					dataSize.add(new int []{start,9+i,1,1});
					start=1+i+1;
				}
				
			}
			for (int i=0;i<dataList.size();i++) {
				AnnualProductionCapacityEntity ds=dataList.get(i);
				
				if("show".equals(ds.getTdHideQuota())&&i!=0){
					dataSize.add(new int []{start1,1+i,2,2});
					start1=1+i+1;
				}else if(i ==count1){
					dataSize.add(new int []{start1,2+i,2,2});
				}
			}
		}else{
			for (int i=0;i<dataList.size();i++) {
				AnnualProductionCapacityEntity ds=dataList.get(i);
				if("show".equals(ds.getTdHide())&&i!=0){
					dataSize.add(new int []{start,1+i,1,1});
					start=1+i+1;
				}else if(i==count){
					dataSize.add(new int []{start,7+i,1,1});
					start=1+i+1;
				}
				
			}
			for (int i=0;i<dataList.size();i++) {
				AnnualProductionCapacityEntity ds=dataList.get(i);
				
				if("show".equals(ds.getTdHideQuota())&&i!=0){
					dataSize.add(new int []{start1,1+i,2,2});
					start1=1+i+1;
				}else if(i ==count1){
					dataSize.add(new int []{start1,2+i,2,2});
				}
			}
		}
		
		resultMap.put("dataSize", dataSize);
		
		if("1".equals(type)){
			ExcelUtil.export(req, res, "集团生产量计划报表模板.xlsx","集团生产量计划.xlsx", resultMap);
		}else if("2".equals(type)){
			ExcelUtil.export(req, res, "内控生产量计划报表模板.xlsx","内控生产量计划.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "年度生产指标计划报表模板.xlsx","年度生产指标计划.xlsx", resultMap);
		}
		
		
	}
	
	/**
	 * 批量导出模板
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcelModel/{type}")
	public void exportExcel(HttpServletRequest req,HttpServletResponse res,@PathVariable String type) throws UnsupportedEncodingException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if("1".equals(type)){
			ExcelUtil.export(req, res, "集团生产量计划(批量导入模板).xlsx","集团生产量计划(批量导入模板).xlsx", resultMap);
		}else if("2".equals(type)){
			ExcelUtil.export(req, res, "内控生产量计划(批量导入模板).xlsx","内控生产量计划(批量导入模板).xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "年度生产指标计划(批量导入模板).xlsx","年度生产指标计划(批量导入模板).xlsx", resultMap);
		}
	}
	
	/**
	 *	跳转到批量添加页面
	 */
	@RequestMapping("/getBatchAdd/{type}")
	public ModelAndView getBatchAdd(HttpServletRequest request,@PathVariable String type){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		return this.createModelAndView("annualProductionCapacity/annualProductionCapacityBatchAdd", params);
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
		String type = request.getParameter("type");

		String filePath = request.getSession().getServletContext().getRealPath(fileUrl);
		File newFile = new File(filePath);
		importExcelData(request,newFile,OriginalFilename,type);
		return resultObj;
	}
	
	public boolean importExcelData(HttpServletRequest request,File file,String OriginalFilename,String type) throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			if (OriginalFilename.contains("xls") || OriginalFilename.contains("xlsx")) {
				Workbook workbook = getWorkbook(inputStream);
				return read(workbook,request,type);
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
	private boolean read(Workbook wb,HttpServletRequest request,String type) throws ParseException, Exception{
		
		boolean result = true;

		Sheet sheet = wb.getSheetAt(0);  
		if (sheet == null) {
			return false;
		}
		List<AnnualProductionCapacityEntity> list  = importPurchaseData(sheet,type);
		//根据场站名称、指标名称、计划对比更新
		List<Condition> conditions = new ArrayList<Condition>();
		int count = 0;
		for (AnnualProductionCapacityEntity apcEntity : list) {
			String farmId = apcEntity.getFarmId();
			String quotaId = apcEntity.getQuotaId();
			String planCompareId = apcEntity.getPlanCompareId();
			Date year = apcEntity.getYear();
	
			//根据条件查询是否有数据
			conditions.clear();
			conditions.add(new Condition("ac.C_FARM_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, farmId));
			conditions.add(new Condition("ac.C_QUOTA_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, quotaId));
			conditions.add(new Condition("ac.C_PLAN_COMPARE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, planCompareId));
			conditions.add(new Condition("ac.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, type));
			conditions.add(new Condition("ac.C_YEAR", FieldTypeEnum.DATE, MatchTypeEnum.EQ, year));
			
			
			if("3".equals(type)){
				List<AnnualProductionCapacityEntity> cotEntityList = this.getService().findByCondition("findQuotaByCondition", conditions, null);
				if (cotEntityList != null && cotEntityList.size() >0 && cotEntityList.get(0) != null) {
					apcEntity.setId(cotEntityList.get(0).getId());
					annualProductionCapacityService.updateEntity(apcEntity);
					QuotaPlanHistoryEntity qpEntity = new QuotaPlanHistoryEntity();
					//更新的同时增加历史数据
					conditions.clear();
					conditions.add(new Condition("qp.C_APC_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, cotEntityList.get(0).getId().toString()));
					List<QuotaPlanHistoryEntity> qpHisList = quotaPlanHistoryService.findByCondition(conditions, null);
					if (qpHisList != null && qpHisList.size() >0 && qpHisList.get(0) != null) {
						qpEntity.setId(qpHisList.get(0).getId());
						qpEntity.setApcId(cotEntityList.get(0).getId().toString());
						//1月
						qpEntity.setJan(!("".equals(cotEntityList.get(0).getJan()))?cotEntityList.get(0).getJan():"0.00");
						//2月
						qpEntity.setFeb(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getFeb():"0.00");
						//3月
						qpEntity.setMar(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getMar():"0.00");
						//4月
						qpEntity.setApr(!("".equals(cotEntityList.get(0).getApr()))?cotEntityList.get(0).getApr():"0.00");
						//5月
						qpEntity.setMay(!("".equals(cotEntityList.get(0).getMay()))?cotEntityList.get(0).getMay():"0.00");
						//6月
						qpEntity.setJun(!("".equals(cotEntityList.get(0).getJun()))?cotEntityList.get(0).getJun():"0.00");
						//7月
						qpEntity.setJul(!("".equals(cotEntityList.get(0).getJul()))?cotEntityList.get(0).getJul():"0.00");
						//8月
						qpEntity.setAug(!("".equals(cotEntityList.get(0).getAug()))?cotEntityList.get(0).getAug():"0.00");
						//9月
						qpEntity.setSep(!("".equals(cotEntityList.get(0).getSep()))?cotEntityList.get(0).getSep():"0.00");
						//10月
						qpEntity.setOct(!("".equals(cotEntityList.get(0).getOct()))?cotEntityList.get(0).getOct():"0.00");
						//11月
						qpEntity.setNov(!("".equals(cotEntityList.get(0).getNov()))?cotEntityList.get(0).getNov():"0.00");
						//12月
						qpEntity.setDec(!("".equals(cotEntityList.get(0).getDec()))?cotEntityList.get(0).getDec():"0.00");
						quotaPlanHistoryService.updateEntity(qpEntity);
					}else{
						qpEntity.setApcId(cotEntityList.get(0).getId().toString());
						//1月
						qpEntity.setJan(!("".equals(cotEntityList.get(0).getJan()))?cotEntityList.get(0).getJan():"0.00");
						//2月
						qpEntity.setFeb(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getFeb():"0.00");
						//3月
						qpEntity.setMar(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getMar():"0.00");
						//4月
						qpEntity.setApr(!("".equals(cotEntityList.get(0).getApr()))?cotEntityList.get(0).getApr():"0.00");
						//5月
						qpEntity.setMay(!("".equals(cotEntityList.get(0).getMay()))?cotEntityList.get(0).getMay():"0.00");
						//6月
						qpEntity.setJun(!("".equals(cotEntityList.get(0).getJun()))?cotEntityList.get(0).getJun():"0.00");
						//7月
						qpEntity.setJul(!("".equals(cotEntityList.get(0).getJul()))?cotEntityList.get(0).getJul():"0.00");
						//8月
						qpEntity.setAug(!("".equals(cotEntityList.get(0).getAug()))?cotEntityList.get(0).getAug():"0.00");
						//9月
						qpEntity.setSep(!("".equals(cotEntityList.get(0).getSep()))?cotEntityList.get(0).getSep():"0.00");
						//10月
						qpEntity.setOct(!("".equals(cotEntityList.get(0).getOct()))?cotEntityList.get(0).getOct():"0.00");
						//11月
						qpEntity.setNov(!("".equals(cotEntityList.get(0).getNov()))?cotEntityList.get(0).getNov():"0.00");
						//12月
						qpEntity.setDec(!("".equals(cotEntityList.get(0).getDec()))?cotEntityList.get(0).getDec():"0.00");
						quotaPlanHistoryService.addEntity(qpEntity);
					}
					
				} else {
					//新增的同时，增加流程表数据
					conditions.clear();
					conditions.add(new Condition("ac.C_FARM_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, farmId));
					conditions.add(new Condition("ac.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, type));
					conditions.add(new Condition("ac.C_YEAR", FieldTypeEnum.DATE, MatchTypeEnum.EQ, year));
					List<AnnualProductionCapacityEntity> apcEntityList = this.getService().findByCondition("findQuotaByCondition", conditions, null);
					QuotaPlanApproveEntity qpEntity = new QuotaPlanApproveEntity();
					if (null == apcEntityList || apcEntityList.size() ==0) {
						if(count==0){
							qpEntity.setFarmId(list.get(0).getFarmId());
							qpEntity.setYear(list.get(0).getYear());
							qpEntity.setType(list.get(0).getType());
							quotaPlanApproveService.addEntity(qpEntity);
						}
					}
					annualProductionCapacityService.addEntity(apcEntity);
					count++;
				}
			}else{
				List<AnnualProductionCapacityEntity> cotEntityList = this.getService().findByCondition(conditions, null);
				if (cotEntityList != null && cotEntityList.size() >0 && cotEntityList.get(0) != null) {
					apcEntity.setId(cotEntityList.get(0).getId());
					annualProductionCapacityService.updateEntity(apcEntity);
					QuotaPlanHistoryEntity qpEntity = new QuotaPlanHistoryEntity();
					//更新的同时增加历史数据
					conditions.clear();
					conditions.add(new Condition("qp.C_APC_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, cotEntityList.get(0).getId().toString()));
					List<QuotaPlanHistoryEntity> qpHisList = quotaPlanHistoryService.findByCondition(conditions, null);
					if (qpHisList != null && qpHisList.size() >0 && qpHisList.get(0) != null) {
						qpEntity.setId(qpHisList.get(0).getId());
						qpEntity.setApcId(cotEntityList.get(0).getId().toString());
						//1月
						qpEntity.setJan(!("".equals(cotEntityList.get(0).getJan()))?cotEntityList.get(0).getJan():"0.00");
						//2月
						qpEntity.setFeb(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getFeb():"0.00");
						//3月
						qpEntity.setMar(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getMar():"0.00");
						//4月
						qpEntity.setApr(!("".equals(cotEntityList.get(0).getApr()))?cotEntityList.get(0).getApr():"0.00");
						//5月
						qpEntity.setMay(!("".equals(cotEntityList.get(0).getMay()))?cotEntityList.get(0).getMay():"0.00");
						//6月
						qpEntity.setJun(!("".equals(cotEntityList.get(0).getJun()))?cotEntityList.get(0).getJun():"0.00");
						//7月
						qpEntity.setJul(!("".equals(cotEntityList.get(0).getJul()))?cotEntityList.get(0).getJul():"0.00");
						//8月
						qpEntity.setAug(!("".equals(cotEntityList.get(0).getAug()))?cotEntityList.get(0).getAug():"0.00");
						//9月
						qpEntity.setSep(!("".equals(cotEntityList.get(0).getSep()))?cotEntityList.get(0).getSep():"0.00");
						//10月
						qpEntity.setOct(!("".equals(cotEntityList.get(0).getOct()))?cotEntityList.get(0).getOct():"0.00");
						//11月
						qpEntity.setNov(!("".equals(cotEntityList.get(0).getNov()))?cotEntityList.get(0).getNov():"0.00");
						//12月
						qpEntity.setDec(!("".equals(cotEntityList.get(0).getDec()))?cotEntityList.get(0).getDec():"0.00");
						quotaPlanHistoryService.updateEntity(qpEntity);
					}else{
						qpEntity.setApcId(cotEntityList.get(0).getId().toString());
						//1月
						qpEntity.setJan(!("".equals(cotEntityList.get(0).getJan()))?cotEntityList.get(0).getJan():"0.00");
						//2月
						qpEntity.setFeb(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getFeb():"0.00");
						//3月
						qpEntity.setMar(!("".equals(cotEntityList.get(0).getFeb()))?cotEntityList.get(0).getMar():"0.00");
						//4月
						qpEntity.setApr(!("".equals(cotEntityList.get(0).getApr()))?cotEntityList.get(0).getApr():"0.00");
						//5月
						qpEntity.setMay(!("".equals(cotEntityList.get(0).getMay()))?cotEntityList.get(0).getMay():"0.00");
						//6月
						qpEntity.setJun(!("".equals(cotEntityList.get(0).getJun()))?cotEntityList.get(0).getJun():"0.00");
						//7月
						qpEntity.setJul(!("".equals(cotEntityList.get(0).getJul()))?cotEntityList.get(0).getJul():"0.00");
						//8月
						qpEntity.setAug(!("".equals(cotEntityList.get(0).getAug()))?cotEntityList.get(0).getAug():"0.00");
						//9月
						qpEntity.setSep(!("".equals(cotEntityList.get(0).getSep()))?cotEntityList.get(0).getSep():"0.00");
						//10月
						qpEntity.setOct(!("".equals(cotEntityList.get(0).getOct()))?cotEntityList.get(0).getOct():"0.00");
						//11月
						qpEntity.setNov(!("".equals(cotEntityList.get(0).getNov()))?cotEntityList.get(0).getNov():"0.00");
						//12月
						qpEntity.setDec(!("".equals(cotEntityList.get(0).getDec()))?cotEntityList.get(0).getDec():"0.00");
						quotaPlanHistoryService.addEntity(qpEntity);
					}
				} else {
					//新增的同时，增加流程表数据
					conditions.clear();
					conditions.add(new Condition("ac.C_FARM_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, farmId));
					conditions.add(new Condition("ac.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, type));
					conditions.add(new Condition("ac.C_YEAR", FieldTypeEnum.DATE, MatchTypeEnum.EQ, year));
					List<AnnualProductionCapacityEntity> apcEntityList = this.getService().findByCondition(conditions, null);
					QuotaPlanApproveEntity qpEntity = new QuotaPlanApproveEntity();
					if (null == apcEntityList || apcEntityList.size() ==0) {
						if(count==0){
							qpEntity.setFarmId(list.get(0).getFarmId());
							qpEntity.setYear(list.get(0).getYear());
							qpEntity.setType(list.get(0).getType());
							qpEntity.setStatus(ScienceTechnologyPlanStatusEnum.TOBESUBMIT.getCode());
							quotaPlanApproveService.addEntity(qpEntity);
						}
					}
					annualProductionCapacityService.addEntity(apcEntity);
					count++;
				}
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
	private List<AnnualProductionCapacityEntity> importPurchaseData(Sheet sheet,String type) throws ParseException {
		List<AnnualProductionCapacityEntity> list = new ArrayList<AnnualProductionCapacityEntity>();
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd");
		int rowCount = sheet.getPhysicalNumberOfRows();
		List<Condition> conditions = new ArrayList<Condition>();
		Row row = sheet.getRow(1);  
		for (int r = 2; r <=rowCount; r++){
			AnnualProductionCapacityEntity anpcEntity = new AnnualProductionCapacityEntity();
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
//						conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
//						conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
//						conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, "1"));
				  		conditions.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,cellValue));
				  		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
				    	if(unitList!=null&&unitList.size()>0){
				    		anpcEntity.setFarmId(unitList.get(0).getId().toString());
				    	}
						break;
					case 2:
						//指标名称
						if("3".equals(type)){
							//对应年度生产指标计划
				  			Map<String, SysDictionaryVO> quotaCodeMap  =  DictionaryUtil.getDictionaries("ANNUALQUOTATYPE");
							for(SysDictionaryVO sysDictionaryVO : quotaCodeMap.values()){
								if(sysDictionaryVO.getName().equals(cellValue) ){
									anpcEntity.setQuotaId(sysDictionaryVO.getCode());
								}
							}
						}else{
							//对应年度生产量计划
				  			Map<String, SysDictionaryVO> quotaCodeMap  =  DictionaryUtil.getDictionaries("ANNUAL_PRODUCTION_TYPE");
							for(SysDictionaryVO sysDictionaryVO : quotaCodeMap.values()){
								if(sysDictionaryVO.getName().equals(cellValue) ){
									anpcEntity.setQuotaId(sysDictionaryVO.getCode());
								}
							}
						}
						break;
					case 3:
						//计划对比
						Map<String, SysDictionaryVO> planComCodeMap  =  DictionaryUtil.getDictionaries("QUOTATYPE");
						for(SysDictionaryVO sysDictionaryVO : planComCodeMap.values()){
							if(sysDictionaryVO.getName().equals(cellValue) ){
								anpcEntity.setPlanCompareId(sysDictionaryVO.getCode());
							}
						}
						break;
					case 4:
						//1月
						anpcEntity.setJan(cellValue);
						break;
					case 5:
						//2月
						anpcEntity.setFeb(cellValue);
						break;
					case 6:
						//3月
						anpcEntity.setMar(cellValue);
						break;
					case 7:
						//4月
						anpcEntity.setApr(cellValue);
						break;
					case 8:
						//5月
						anpcEntity.setMay(cellValue);
						break;
					case 9:
						//6月
						anpcEntity.setJun(cellValue);
						break;
					case 10:
						//7月
						anpcEntity.setJul(cellValue);
						break;
					case 11:
						//8月
						anpcEntity.setAug(cellValue);
						break;
					case 12:
						//9月
						anpcEntity.setSep(cellValue);
						break;
					case 13:
						//10月
						anpcEntity.setOct(cellValue);
						break;
					case 14:
						//11月
						anpcEntity.setNov(cellValue);
						break;
					case 15:
						//12月
						anpcEntity.setDec(cellValue);
						anpcEntity.setType(type);
						break;
					case 16:
						//年份
						String cellValueStr = String.valueOf((new Double(cellValue)).intValue());
						String year = cellValueStr+"-01-01";
						anpcEntity.setYear(dUtil.parse(year));
						anpcEntity.setType(type);
						break;
					default:  
						cellValue = "未知类型";  
						break;  
				}
			}
			
			list.add(anpcEntity);
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