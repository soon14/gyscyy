package com.aptech.business.ticketManage.ticketStatistics.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsVO;
import com.aptech.business.ticketManage.ticketStatistics.service.TicketStatisticsMonthService;
import com.aptech.business.ticketManage.ticketStatistics.service.TicketStatisticsService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workticketRepair.service.WorkticketRepairService;
import com.aptech.business.util.ExcelExportUtil;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.exception.request.BadRequestException;
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


/**
 * 两票统计控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/ticketStatistics")
public class TicketStatisticsController extends BaseController<TicketStatisticsVO> {
	
	@Autowired
	private TicketStatisticsService ticketStatisticsService;
	@Autowired
	private TicketStatisticsMonthService ticketStatisticsMonthService;
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private OrgaAppService orgaAppService;
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private WorkticketRepairService workticketRepairService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Override
	public IBaseEntityOperation<TicketStatisticsVO> getService() {
		return ticketStatisticsService;
	}

	/**
	 * 页面初始化话方法
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		String uuid=request.getParameter("uuid");
		List<Condition> conditions = new ArrayList<Condition>();
//		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.LE, String.valueOf(UnitLevelEnum.FARM.getCode())));
//		List<SysUnitEntity> treeNodeList = sysUnitService.findByCondition(conditions, null);
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		
		//TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		//刀闸状态
        Map<String, SysDictionaryVO> runWayMap  =  DictionaryUtil.getDictionaries("DATEUTIL");
   	 	ComboboxVO joinLandCombobox = new ComboboxVO();
        for(String key :  runWayMap.keySet()){
            SysDictionaryVO sysDictionaryVO = runWayMap.get(key);
            joinLandCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        resultMap.put("DateUtil", JsonUtil.toJson(joinLandCombobox.getOptions()));
        System.out.println(JsonUtil.toJson(joinLandCombobox.getOptions()));
		return this.createModelAndView("ticketManage/workTicketStatistics/ticketStatistics", resultMap);
	}
	
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> data) {
//		AppListFormVO appListFormVO = listFormService.getAppListFormVOById(appId);
		
		Map<String, Object> param =  (Map<String, Object>)data.get("param");
		Page<TicketStatisticsVO> page = PageUtil.getPage(data);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) data.get("draw"));
		//单位名称
		String unitId = "";
		if (param.get("unitId") !=null && !"".equals(param.get("unitId"))) {
			 unitId = String.valueOf((int)param.get("unitId"));
		} 
		//统计时间
		String searchYear = (String)param.get("searchTime");
		//统计方式
		String searchDateType = (String)param.get("searchDateType");
		

		List<Sort> orders = OrmUtil.changeMapToOrders(param);

		if (page != null) {
		  page.setOrders(orders);
		}
//		if(searchDateType==null || (searchDateType!=null && StringUtils.equals("年", searchDateType))){
			List<Map<String, String>>  entityList = ticketStatisticsService.getStatisticDataList(unitId, searchYear);
//		}else{
//			
//		}

		if (entityList != null) {
			resultObj.setData(entityList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entityList.size());
			}
		}
		return resultObj;
	}
	/**
	 * list数据查询月
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchMonthData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchMonthData(HttpServletRequest request, @RequestBody Map<String, Object> data) {
//		AppListFormVO appListFormVO = listFormService.getAppListFormVOById(appId);
		
		Map<String, Object> param =  (Map<String, Object>)data.get("param");
		Page<TicketStatisticsVO> page = PageUtil.getPage(data);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) data.get("draw"));
		//单位名称
		String unitId = "";
		if (param.get("unitId") !=null && !"".equals(param.get("unitId"))) {
			unitId = String.valueOf((int)param.get("unitId"));
		} 
		//统计时间
		String searchMonth = (String)param.get("searchTime");
		//统计方式
		String searchDateType = (String)param.get("searchDateType");
		
		
		List<Sort> orders = OrmUtil.changeMapToOrders(param);
		
		if (page != null) {
			page.setOrders(orders);
		}
//		if(searchDateType==null || (searchDateType!=null && StringUtils.equals("年", searchDateType))){
		List<Map<String, String>>  entityList = ticketStatisticsMonthService.getStatisticDataList(unitId, searchMonth);
//		}else{
//			
//		}
		
		if (entityList != null) {
			resultObj.setData(entityList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entityList.size());
			}
		}
		return resultObj;
	}

	/**
	 * 创建下载文件返回文件路径
	 * @throws IOException 
	 * @throws BadRequestException 
	 */
	@RequestMapping(value = "/createDataExcelFile/{yearText}")
	public @ResponseBody Map<String, Object> createDataExcelFile(HttpServletRequest request, @RequestBody String data,@PathVariable String yearText) throws BadRequestException, IOException {
		HashMap<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(data);
		//单位id
		String unitId = jsonObj.getString("unitId");
		//统计时间
		String searchYear = jsonObj.getString("searchTime");
		//导出文件名称
		String excelName = (String)request.getParameter("name");
		String reportName = "两票统计模板.xlsx";
		List<Map<String, String>>  entityList = ticketStatisticsService.getStatisticDataList(unitId, searchYear);
		Map<String, String> year = new HashMap<String, String>();
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		Map<String, String> excelMap = new HashMap<String, String>();
		yearText = yearText + "年";
		year.put("yearText", yearText);
		entityList.add(year);
		excelMap.put("excelName", excelName);
		entityList.add(excelMap);
		tmpMap.put("dataList", entityList);
		tmpMap.put("yearText", yearText);
		ExcelExportUtil excelUtil = new ExcelExportUtil(reportName);
		String tempFileName = excelUtil.generateExcel(tmpMap);
		map.put("tempFileName", tempFileName);
		
		return map;
	}
	/**
	 * 导出月数据
	 * @throws IOException 
	 * @throws BadRequestException 
	 */
	@RequestMapping(value = "/exportExcel/{monthText}")
	public @ResponseBody Map<String, Object> exportExcel(HttpServletRequest request, @RequestBody String data,@PathVariable String monthText) throws BadRequestException, IOException {
		HashMap<String,Object> map = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(data);
		//单位id
		String unitId = jsonObj.getString("unitId");
		//统计时间
		String searchMonth = jsonObj.getString("searchTime");
		//zzq 新增开始
		int day=0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			if(searchMonth.equals("")){
				 day=getDaysOfMonth(new Date());
			}else{
				 try {
					day=getDaysOfMonth(sdf.parse(searchMonth));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		System.out.println(day);
		//zzq 新增结束
		//导出文件名称
		String excelName = (String)request.getParameter("name");
		String reportName="";
		if(day==28){
			reportName = "两票月二八统计模板.xlsx";
		}else if(day==29){
			reportName = "两票月二九统计模板.xlsx";
		}else if(day==30){
			reportName = "两票月三十统计模板.xlsx";
		}else{
			reportName = "两票月统计模板.xlsx";
		}
		
		List<Map<String, String>>  entityList = ticketStatisticsMonthService.getStatisticDataList(unitId, searchMonth);
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		Map<String, String> excelMap = new HashMap<String, String>();
		excelMap.put("excelName", excelName);
		entityList.add(excelMap);
		tmpMap.put("dataList", entityList);
		tmpMap.put("monthText", monthText);
		ExcelExportUtil excelUtil = new ExcelExportUtil(reportName);
		
		String tempFileName = excelUtil.generateExcel(tmpMap);
		
		map.put("tempFileName", tempFileName);
		
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
		java.io.FileInputStream fis = null;
		java.io.OutputStream os = null;
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
	/**
	 * @Description:   获取一个月份有多少天
	 * @author         zhangzq 
	 * @Date           2018年9月5日 上午11:53:03 
	 * @throws         Exception
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
};