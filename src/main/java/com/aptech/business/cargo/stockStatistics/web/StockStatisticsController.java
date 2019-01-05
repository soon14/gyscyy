package com.aptech.business.cargo.stockStatistics.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.stockStatistics.domain.StockStatisticsEntity;
import com.aptech.business.cargo.stockStatistics.service.StockStatisticsService;
import com.aptech.business.component.dictionary.RunCheckResultEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.run.runCheck.domain.RunCheckEntity;
import com.aptech.business.util.ExcelExportUtil;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.exception.request.BadRequestException;
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
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 运行检查配置控制器
 *
 * @author 
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/stockStatistics")
public class StockStatisticsController extends BaseController<StockStatisticsEntity> {
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private StockStatisticsService stockStatisticsService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<StockStatisticsEntity> getService() {
		return stockStatisticsService;
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
		//model.put("rlId", rlId);
/*		 ComboboxVO workRecordCombobox = new ComboboxVO();
         Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
         
         for(String key :  codeDateTypeMap.keySet()){
             SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
             workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
         }
         model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); */
         ComboboxVO comboRunCheckVO = new ComboboxVO();
         
         //从枚举取
         for(RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum.values()){
             comboRunCheckVO.addOption(runCheckResultEnum.getCode(), runCheckResultEnum.getName());
         }
         model.put("runCheckCombobox", JsonUtil.toJson(comboRunCheckVO.getOptions()));
         DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM");
         String dateStr = dUtil.format(new Date());
         model.put("giveDate", dateStr);
      // 部门下拉树
 		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
 		for (int i = 0; i < treeNodeList.size(); i++) {
 			treeNodeList.get(i).setOpen("true");
 		}
 		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		return this.createModelAndView("cargo/stockStatistics/stockStatisticsList", model);
	}

	
	   /**
	* @Title: expData
	* @Description: 导出功能
	* @author zhangzb
	* @date 2018年4月2日下午1:44:05
	* @param req
	* @param res
	* @param params
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcel/{giveDate1}")
	    public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params,Page<StockStatisticsEntity> page, @PathVariable String giveDate1) throws UnsupportedEncodingException{
		List<Condition> conditions = new ArrayList<Condition>();
		Map<String, Object> otherParams =new  HashMap<String, Object> ();
		page.setPageSize(Integer.MAX_VALUE);
		if(	StringUtil.isNotEmpty(giveDate1)){
			otherParams.put("giveDate",giveDate1 );
		}
		//本有前入库
		List<StockStatisticsEntity> stockStatisticsPastInEntities = stockStatisticsService.findByCondition("findByConditionStatisticsPastIn", conditions, otherParams, page);
		//本月前出库
		List<StockStatisticsEntity> stockStatisticsPastOutEntities = stockStatisticsService.findByCondition("findByConditionStatisticsPastOut", conditions, otherParams, page);
		//本月入库记录
		List<StockStatisticsEntity> stockStatisticsInEntities = stockStatisticsService.findByCondition("findByConditionStatisticsIn", conditions, otherParams, page);
		//本月出库记录
		List<StockStatisticsEntity> stockStatisticsOutEntities = stockStatisticsService.findByCondition("findByConditionStatisticsOut", conditions, otherParams, page);
		
		List<StockStatisticsEntity> stockStatisticsEntities=stockStatisticsService.findByCondition(conditions, page);
		List<StockStatisticsEntity> stockStatisticsList=new ArrayList<>();
		String materialId="";
		String unitId="";
		String wareHouseId="";
		if(!stockStatisticsEntities.isEmpty()){
			for(StockStatisticsEntity stock:stockStatisticsEntities){
				materialId=stock.getMaterialId();
				unitId=stock.getUnitId();
				wareHouseId=stock.getWareHouseId();
				String kcslString = stock.getTotalCount();
				if(!wareHouseId.isEmpty()&&!unitId.isEmpty()&&!materialId.isEmpty()){//单位、仓库、物资类同时非空
					if(!stockStatisticsPastInEntities.isEmpty()){//本有前入库非空
						for(StockStatisticsEntity stockPastIn:stockStatisticsPastInEntities){
							if(materialId.equals(stockPastIn.getMaterialId())
									&&unitId.equals(stockPastIn.getUnitId())
									&&wareHouseId.equals(stockPastIn.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setBeforeInNum(stockPastIn.getTotalCount());
							}
							
						}
					}
					if(!stockStatisticsPastOutEntities.isEmpty()){//本月前出库非空
						for(StockStatisticsEntity stockPastOut:stockStatisticsPastOutEntities){
							if(materialId.equals(stockPastOut.getMaterialId())
									&&unitId.equals(stockPastOut.getUnitId())
									&&wareHouseId.equals(stockPastOut.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setBeforeOutNum(stockPastOut.getTotalCount());
							}
							
						}
					}
					if(!stockStatisticsInEntities.isEmpty()){//本月入库记录非空
						for(StockStatisticsEntity stockIn:stockStatisticsInEntities){
							if(materialId.equals(stockIn.getMaterialId())
									&&unitId.equals(stockIn.getUnitId())
									&&wareHouseId.equals(stockIn.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setMouthInNum(stockIn.getTotalCount());
							}
							
						}
					}
					if(!stockStatisticsOutEntities.isEmpty()){//本月出库记录 非空
						for(StockStatisticsEntity stockOut:stockStatisticsOutEntities){
							if(materialId.equals(stockOut.getMaterialId())
									&&unitId.equals(stockOut.getUnitId())
									&&wareHouseId.equals(stockOut.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setMouthOutNum(stockOut.getTotalCount());
							}
							
						}
					}	
				}
				if(stock.getBeforeInNum()==null||"".equals(stock.getBeforeInNum())){
					stock.setBeforeInNum("0");
				}
				if(stock.getBeforeOutNum()==null||"".equals(stock.getBeforeOutNum())){
					stock.setBeforeOutNum("0");
				}
				if(stock.getMouthInNum()==null||"".equals(stock.getMouthInNum())){
					stock.setMouthInNum("0");
				}
				if(stock.getMouthOutNum()==null||"".equals(stock.getMouthOutNum())){
					stock.setMouthOutNum("0");
				}
//				double initialNum = Double.parseDouble(stock.getBeforeInNum())-Double.parseDouble(stock.getBeforeOutNum());
//				double totalCount = initialNum+Double.parseDouble(stock.getMouthInNum())-Double.parseDouble(stock.getMouthOutNum());
//				stock.setTotalCount(String.valueOf(totalCount));
//				stock.setInitialNum(String.valueOf(initialNum));
				double initialNum = Double.parseDouble(stock.getBeforeInNum())-Double.parseDouble(stock.getBeforeOutNum());
				if(initialNum<0){
					initialNum = 0;
				}
				double totalCount = initialNum+Double.parseDouble(stock.getMouthInNum())-Double.parseDouble(stock.getMouthOutNum());
				if(totalCount<0){
					totalCount=0;
				}
				NumberFormat nf = NumberFormat.getNumberInstance();
				DecimalFormat df = new DecimalFormat("#.00");
				nf.setMaximumFractionDigits(2);
				stock.setTotalCount(String.valueOf(nf.format(totalCount)));
				stock.setInitialNum(String.valueOf(nf.format(initialNum)));
				stockStatisticsList.add(stock);
			}
		}
	        Map<String,Object> resultMap=new HashMap<String, Object>();
	        resultMap.put("dataList", stockStatisticsList);
	        ExcelUtil.export(req, res, "库存统计报表模板.xlsx","库存统计.xlsx", resultMap);
	    }
   /**
	* @Description:库存结果统计
	* @author zhangxb	
	* @param <O>
	* @date 2018年4月2日下午17:38:54
	* @throws Exception
	*/
	@RequestMapping(value = "/statistics/{giveDate1}")
    public @ResponseBody ResultListObj statistics(@RequestBody Map<String, Object> params, Page<StockStatisticsEntity> page, @PathVariable String giveDate1) throws Exception{
		ResultListObj resultObj = new ResultListObj();
		List<Condition> conditions = new ArrayList<Condition>();
		page.setPageSize(Integer.MAX_VALUE);
		//时间维度
		String timespan=(String) params.get("timespan");
		DateFormatUtil dfuMonth = DateFormatUtil.getInstance("yyyy-MM");
		DateFormatUtil dfuDate = DateFormatUtil.getInstance("yyyy-MM-dd");
		//统计时间
		Calendar statisticsCal = Calendar.getInstance();
		DateFormatUtil dfuYear = DateFormatUtil.getInstance("yyyy");
		String lastDate = null;
		String thisMonth = null;
		String endMonth = null;
		int month  ;
		int year ;
		//如果是月份，月份加1，为年，年份加1
		if("month".equals(timespan)&&StringUtil.isNotEmpty(giveDate1)){
			month = Integer.parseInt(giveDate1.substring(6, 7));
			year = Integer.parseInt(giveDate1.substring(0, 4));
			statisticsCal.set(Calendar.YEAR, year);
			statisticsCal.set(Calendar.MONTH, 0);
			lastDate = dfuMonth.format(statisticsCal.getTime());
			statisticsCal.set(Calendar.YEAR, year);
			statisticsCal.set(Calendar.MONTH, month-1);
			statisticsCal.set(Calendar.DATE, 1);
			thisMonth = dfuDate.format(statisticsCal.getTime());
			statisticsCal.set(Calendar.YEAR, year);
			statisticsCal.set(Calendar.MONTH, month-1);
			statisticsCal.set(Calendar.DAY_OF_MONTH, statisticsCal.getActualMaximum(Calendar.DAY_OF_MONTH));  
			endMonth = dfuDate.format(statisticsCal.getTime());
		}else if("year".equals(timespan)&&StringUtil.isNotEmpty(giveDate1)){
//			month = Integer.parseInt(giveDate1.substring(6, 7));
			 year = Integer.parseInt(giveDate1.substring(0, 4));
			statisticsCal.setTime(dfuYear.parse(giveDate1));
			statisticsCal.add(Calendar.YEAR,1);
			statisticsCal.set(Calendar.MONTH,0);
			lastDate = dfuMonth.format(statisticsCal.getTime());
			statisticsCal.set(Calendar.YEAR, year);
			statisticsCal.set(Calendar.MONTH, 0);
			thisMonth = dfuMonth.format(statisticsCal.getTime());
			statisticsCal.set(Calendar.YEAR, year);
			statisticsCal.set(Calendar.MONTH, 11);
			endMonth = dfuMonth.format(statisticsCal.getTime());
		}
		Map<String, Object> otherParams =new  HashMap<String, Object> ();
		if(	StringUtil.isNotEmpty(giveDate1)&&StringUtil.isNotEmpty(lastDate)){
			otherParams.put("giveDate",giveDate1 );
			otherParams.put("lastDate",lastDate );
			otherParams.put("thisMonth",thisMonth );
			otherParams.put("endMonth",endMonth );
		}
//		String unit = "";
		List<Condition> unitConditions = new ArrayList<Condition>();
		if(params.get("unitId")!=null&&params.get("unitId")!=""){
			int searchUnitId=(int) params.get("unitId");
//			 unit = String.valueOf(searchUnitId);
			otherParams.put("searchUnitId",searchUnitId );
			unitConditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, searchUnitId));
			conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, searchUnitId));
		}
		
		//本月前入库
		List<StockStatisticsEntity> stockStatisticsPastInEntities = stockStatisticsService.findByCondition("findByConditionStatisticsPastIn", conditions, otherParams, page);
		//本月前出库
		List<StockStatisticsEntity> stockStatisticsPastOutEntities = stockStatisticsService.findByCondition("findByConditionStatisticsPastOut", conditions, otherParams, page);
		//本月入库记录
//		List<StockStatisticsEntity> stockStatisticsInEntities = stockStatisticsService.findByCondition("findByConditionStatisticsIn", conditions, otherParams, page);
//		//本月出库记录
//		List<StockStatisticsEntity> stockStatisticsOutEntities = stockStatisticsService.findByCondition("findByConditionStatisticsOut", conditions, otherParams, page);
		//上月入库
		List<StockStatisticsEntity> lastMonthStatisticsEntitiesIn = stockStatisticsService.findByCondition("findByConditionStatisticsIn", conditions, otherParams, page);
		//上月出库
		List<StockStatisticsEntity> lastMonthStatisticsEntitiesOut = stockStatisticsService.findByCondition("findByConditionStatisticsOut", conditions, otherParams, page);
//		conditions.clear();
//		conditions.add(new Condition("sis.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unit));
		List<StockStatisticsEntity> stockStatisticsEntities=stockStatisticsService.findByCondition(unitConditions,page);
		List<StockStatisticsEntity> stockStatisticsList=new ArrayList<>();
		String materialId="";
		String unitId="";
		String wareHouseId="";
		if(!stockStatisticsEntities.isEmpty()){
			for(StockStatisticsEntity stock:stockStatisticsEntities){
				materialId=stock.getMaterialId();
				unitId=stock.getUnitId();
				wareHouseId=stock.getWareHouseId();
				String kcslString = stock.getTotalCount();
				if(!wareHouseId.isEmpty()&&!unitId.isEmpty()&&!materialId.isEmpty()){//单位、仓库、物资类同时非空
//					if(!stockStatisticsPastInEntities.isEmpty()){//本有前入库非空
//						for(StockStatisticsEntity stockPastIn:stockStatisticsPastInEntities){
//							if(materialId.equals(stockPastIn.getMaterialId())
//									&&unitId.equals(stockPastIn.getUnitId())
//									&&wareHouseId.equals(stockPastIn.getWareHouseId())){//同一单位同一仓库同一物资类
//								stock.setBeforeInNum(stockPastIn.getTotalCount());
//							}
//							
//						}
//					}
//					if(!stockStatisticsPastOutEntities.isEmpty()){//本月前出库非空
//						for(StockStatisticsEntity stockPastOut:stockStatisticsPastOutEntities){
//							if(materialId.equals(stockPastOut.getMaterialId())
//									&&unitId.equals(stockPastOut.getUnitId())
//									&&wareHouseId.equals(stockPastOut.getWareHouseId())){//同一单位同一仓库同一物资类
//								stock.setBeforeOutNum(stockPastOut.getTotalCount());
//							}
//							
//						}
//					}
					if(!stockStatisticsPastInEntities.isEmpty()){//本月入库记录非空
						for(StockStatisticsEntity stockIn:stockStatisticsPastInEntities){
							if(materialId.equals(stockIn.getMaterialId())
									&&unitId.equals(stockIn.getUnitId())
									&&wareHouseId.equals(stockIn.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setMouthInNum(stockIn.getTotalCount());
							}
							
						}
					}
					if(!stockStatisticsPastOutEntities.isEmpty()){//本月出库记录 非空
						for(StockStatisticsEntity stockOut:stockStatisticsPastOutEntities){
							if(materialId.equals(stockOut.getMaterialId())
									&&unitId.equals(stockOut.getUnitId())
									&&wareHouseId.equals(stockOut.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setMouthOutNum(stockOut.getTotalCount());
							}
							
						}
					}	
					if(!lastMonthStatisticsEntitiesIn.isEmpty()){//上月入库记录 非空
						for(StockStatisticsEntity stockLastIn:lastMonthStatisticsEntitiesIn){
							if(materialId.equals(stockLastIn.getMaterialId())
									&&unitId.equals(stockLastIn.getUnitId())
									&&wareHouseId.equals(stockLastIn.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setBeforeInNum(stockLastIn.getTotalCount());
							}
							
						}
					}	
					if(!lastMonthStatisticsEntitiesOut.isEmpty()){//上月入库记录 非空
						for(StockStatisticsEntity stockLastOut:lastMonthStatisticsEntitiesOut){
							if(materialId.equals(stockLastOut.getMaterialId())
									&&unitId.equals(stockLastOut.getUnitId())
									&&wareHouseId.equals(stockLastOut.getWareHouseId())){//同一单位同一仓库同一物资类
								stock.setBeforeOutNum(stockLastOut.getTotalCount());
							}
							
						}
					}	
				}
				if(stock.getBeforeInNum()==null||"".equals(stock.getBeforeInNum())){
					stock.setBeforeInNum("0");
				}
				if(stock.getBeforeOutNum()==null||"".equals(stock.getBeforeOutNum())){
					stock.setBeforeOutNum("0");
				}
				if(stock.getMouthInNum()==null||"".equals(stock.getMouthInNum())){
					stock.setMouthInNum("0");
				}
				if(stock.getMouthOutNum()==null||"".equals(stock.getMouthOutNum())){
					stock.setMouthOutNum("0");
				}
				double initialNum = Double.parseDouble(stock.getBeforeInNum())-Double.parseDouble(stock.getBeforeOutNum());
				if(initialNum<0){
					initialNum = 0;
				}
				double totalCount = initialNum+Double.parseDouble(stock.getMouthInNum())-Double.parseDouble(stock.getMouthOutNum());
				if(totalCount<0){
					totalCount=0;
				}
				NumberFormat nf = NumberFormat.getNumberInstance();
				DecimalFormat df = new DecimalFormat("#.00");
				nf.setMaximumFractionDigits(2);
				stock.setTotalCount(String.valueOf(nf.format(totalCount)));
				stock.setInitialNum(String.valueOf(nf.format(initialNum)));
				stockStatisticsList.add(stock);
			}
		}
		
		resultObj.setDraw((Integer)params.get("draw"));
		if (stockStatisticsList != null) {
	        resultObj.setData(stockStatisticsList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			}
		}
        return resultObj;
    }
	
	/**
	 * 创建下载文件返回文件路径
	 * @throws IOException 
	 * @throws BadRequestException 
	 */
	@RequestMapping(value = "/createDataExcelFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createDataExcelFile(HttpServletRequest request, @RequestBody Map<String, Object> params) throws  IOException {
		List<int[]> list = new ArrayList<int[]>();
		HashMap<String,Object> map = new HashMap<String, Object>();
		String reportName = "运行检查结果统计.xlsx";
		/*List<RunCheckEntity>  entityList = stockStatisticsService.statistics(params,list);
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("dataList", entityList);*/
		ExcelExportUtil excelUtil = new ExcelExportUtil(reportName);
		/*String tempFileName = excelUtil.makeExcel(tmpMap,list);
		map.put("tempFileName", tempFileName);*/
		return map;
	}
	
	
	/**
	 * 文件导出相应方法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/exprotExcel", method = RequestMethod.POST)
	public void exprotExcel (HttpServletRequest request, HttpServletResponse response) throws Exception {
		//导出文件位置
		String tempFileName = (String)request.getParameter("path");

		//导出文件名称
		String excelName = (String)request.getParameter("name");

		downloadFile(request, response, excelName + ".xlsx", tempFileName);
	}
	
	/**
	 * @param 下载excel
	 * @param response 
	 * @param name 下载后文件名
	 * @param conditionPath 原文件
	 * @throws Exception
	 */
	private void downloadFile(HttpServletRequest request, HttpServletResponse response, String name, String conditionPath) throws Exception {
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			String filename = name;
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				// firefox浏览器
				filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				// IE浏览器
				filename = URLEncoder.encode(filename, "UTF-8");
			} else {
				// IE浏览器
				filename = URLEncoder.encode(filename, "UTF-8");
			}

			response.reset();
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename.replace(" ", ""));
			os = response.getOutputStream();
			fis = new java.io.FileInputStream(conditionPath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			fis.close();
			os.flush();
			os.close();
		}
	  }
}