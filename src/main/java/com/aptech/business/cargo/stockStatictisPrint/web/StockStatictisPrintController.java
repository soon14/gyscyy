package com.aptech.business.cargo.stockStatictisPrint.web;

import groovy.transform.Undefined;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.aptech.business.cargo.stockStatictisPrint.domain.StockStatictisPrintEntity;
import com.aptech.business.cargo.stockStatictisPrint.service.StockStatictisPrintService;
import com.aptech.business.cargo.stockStatistics.domain.StockStatisticsEntity;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
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
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 库存统计打印表配置控制器
 *
 * @author 
 * @created 2018-09-08 16:00:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/stockStatictisPrint")
public class StockStatictisPrintController extends BaseController<StockStatictisPrintEntity> {
	
	@Autowired
	private StockStatictisPrintService stockStatictisPrintService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<StockStatictisPrintEntity> getService() {
		return stockStatictisPrintService;
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
		List<StockStatictisPrintEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("stockStatictisPrintTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboStockStatictisPrintVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("stockStatictisPrintCombobox", JsonUtil.toJson(comboStockStatictisPrintVO.getOptions()));
		return this.createModelAndView("stockStatictisPrint/resource/views/stockStatictisPrintList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<StockStatictisPrintEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("stockStatictisPrintTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboStockStatictisPrintVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("stockStatictisPrintCombobox", JsonUtil.toJson(comboStockStatictisPrintVO.getOptions()));
		
		return this.createModelAndView("stockStatictisPrint/resource/views/stockStatictisPrintAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		StockStatictisPrintEntity stockStatictisPrintEntity = (StockStatictisPrintEntity)stockStatictisPrintService.findById(id);
		model.put("entity", stockStatictisPrintEntity);
		model.put("entityJson", JsonUtil.toJson(stockStatictisPrintEntity));
		
		List<StockStatictisPrintEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("stockStatictisPrintTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboStockStatictisPrintVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("stockStatictisPrintCombobox", JsonUtil.toJson(comboStockStatictisPrintVO.getOptions()));
		
		return this.createModelAndView("stockStatictisPrint/resource/views/stockStatictisPrintEdit", model);
	}
	   /**
		* @Description:库存结果统计
		* @author huoxy	
		* @param <O>
		* @date 2018年4月2日下午17:38:54
		* @throws Exception
		*/
		@RequestMapping(value = "/statistics/{giveDate1}")
	    public @ResponseBody ResultListObj statistics(@RequestBody Map<String, Object> params, Page<StockStatictisPrintEntity> page, @PathVariable String giveDate1) throws Exception{
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
				if(month==0||month==1||month==2){
					month = Integer.parseInt(giveDate1.substring(5, 7));
				}
				year = Integer.parseInt(giveDate1.substring(0, 4));
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, month-1);
				statisticsCal.set(Calendar.DATE, 1);
				thisMonth = dfuDate.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, month-1);
				statisticsCal.set(Calendar.DAY_OF_MONTH, statisticsCal.getActualMaximum(Calendar.DAY_OF_MONTH));  
				endMonth = dfuDate.format(statisticsCal.getTime());
			}else if("year".equals(timespan)&&StringUtil.isNotEmpty(giveDate1)){
//				month = Integer.parseInt(giveDate1.substring(6, 7));
				 year = Integer.parseInt(giveDate1.substring(0, 4));
				lastDate = dfuMonth.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, 0);
				thisMonth = dfuMonth.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year+1);
				statisticsCal.set(Calendar.MONTH, 0);
				endMonth = dfuMonth.format(statisticsCal.getTime());
			}
			Map<String, Object> otherParams =new  HashMap<String, Object> ();
			if(params.get("unitId")!=null&&params.get("unitId")!=""){
				int searchUnitId=(int) params.get("unitId");
				SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(searchUnitId));
				conditions.add(new Condition("C_UNIT_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUnitEntity.getName()));
			}
			conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, thisMonth));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, endMonth));
			List<StockStatictisPrintEntity> StockStatictisPrintEntities = stockStatictisPrintService.findByCondition("findByConditionTime", conditions,  page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (StockStatictisPrintEntities != null) {
		        resultObj.setData(StockStatictisPrintEntities);
				if (page != null) {
					resultObj.setRecordsTotal(page.getTotal());
				}
			}
	        return resultObj;
	    }
		  /**
		* @Title: expData
		* @Description: 导出功能
		* @author huoxy
		* @date 2018年4月2日下午1:44:05
		* @param req
		* @param res
		* @param params
		* @throws UnsupportedEncodingException
		* @throws
		*/
		@RequestMapping("/exportExcel")
		    public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
			List<Condition> conditions = new ArrayList<Condition>();
			Page<StockStatictisPrintEntity> page = new Page<StockStatictisPrintEntity>();
			page.setPageSize(Integer.MAX_VALUE);
			//时间维度
			String timespan = req.getParameter("timespan");
			String giveDate1 = req.getParameter("giveDate1");
			String unitId = req.getParameter("unitId");
//			String timespan=(String) params.get("timespan");
			DateFormatUtil dfuMonth = DateFormatUtil.getInstance("yyyy-MM");
			DateFormatUtil dfuDate = DateFormatUtil.getInstance("yyyy-MM-dd");
			//统计时间
			Calendar statisticsCal = Calendar.getInstance();
			String lastDate = null;
			String thisMonth = null;
			String endMonth = null;
			int month  ;
			int year ;
			//如果是月份，月份加1，为年，年份加1
			if("month".equals(timespan)&&StringUtil.isNotEmpty(giveDate1)){
				month = Integer.parseInt(giveDate1.substring(6, 7));
				if(month==0||month==1||month==2){
					month = Integer.parseInt(giveDate1.substring(5, 7));
				}
				year = Integer.parseInt(giveDate1.substring(0, 4));
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, month-1);
				statisticsCal.set(Calendar.DATE, 1);
				thisMonth = dfuDate.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, month-1);
				statisticsCal.set(Calendar.DAY_OF_MONTH, statisticsCal.getActualMaximum(Calendar.DAY_OF_MONTH));  
				endMonth = dfuDate.format(statisticsCal.getTime());
			}else if("year".equals(timespan)&&StringUtil.isNotEmpty(giveDate1)){
//				month = Integer.parseInt(giveDate1.substring(6, 7));
				 year = Integer.parseInt(giveDate1.substring(0, 4));
				lastDate = dfuMonth.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, 0);
				thisMonth = dfuMonth.format(statisticsCal.getTime());
				statisticsCal.set(Calendar.YEAR, year);
				statisticsCal.set(Calendar.MONTH, 11);
				endMonth = dfuMonth.format(statisticsCal.getTime());
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			Map<String, Object> otherParams =new  HashMap<String, Object> ();
			if(unitId=="undefined"&&unitId!=""){
				SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(unitId));
				conditions.add(new Condition("C_UNIT_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUnitEntity.getName()));
			}else{
				SysUserEntity sysUserEntit = RequestContext.get().getUser();
				conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntit.getUnitId()));
			}
			conditions.add(new Condition("C_MONTH_STOCK", FieldTypeEnum.STRING, MatchTypeEnum.GE, "0"));
			conditions.add(new Condition("C_LAST_MONTH_STOCK", FieldTypeEnum.STRING, MatchTypeEnum.GE, "0"));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, thisMonth));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, endMonth));
			List<StockStatictisPrintEntity> StockStatictisPrintEntities = stockStatictisPrintService.findByCondition("findByConditionTime", conditions,  page);
		        Map<String,Object> resultMap=new HashMap<String, Object>();
		        resultMap.put("dataList", StockStatictisPrintEntities);
		        ExcelUtil.export(req, res, "库存统计报表模板.xlsx","库存统计.xlsx", resultMap);
		    }
}