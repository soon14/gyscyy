package com.aptech.business.run.runCheck.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
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

import com.aptech.business.component.dictionary.RunCheckResultEnum;
import com.aptech.business.equip.equipModel.domain.EquipModelEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.run.runCheck.domain.RunCheckEntity;
import com.aptech.business.run.runCheck.domain.RunCheckEnum;
import com.aptech.business.run.runCheck.service.RunCheckService;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.util.ExcelExportUtil;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
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
import com.aptech.framework.web.base.ResultObj;

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
@RequestMapping("/runCheck")
public class RunCheckController extends BaseController<RunCheckEntity> {
	
	@Autowired
	private RunCheckService runCheckService;
	@Autowired
    private SysUserService sysUserService;
	@Autowired
	private RunLogService runLogService;
	@Override
	public IBaseEntityOperation<RunCheckEntity> getService() {
		return runCheckService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{rlId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		//检查统计类型
	    Map<Integer, SysDictionaryVO> sortProfessionMap = new HashMap<Integer, SysDictionaryVO>();
	    List<Integer> sortProfessionList = new ArrayList<Integer>();
		ComboboxVO workRecordCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            sortProfessionMap.put(sysDictionaryVO.getOrder(), sysDictionaryVO);
            sortProfessionList.add(sysDictionaryVO.getOrder());
//            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        Collections.sort(sortProfessionList);
        for(Integer i : sortProfessionList){
            SysDictionaryVO sysDictionaryVO = sortProfessionMap.get(i);
            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); 
         ComboboxVO comboRunCheckVO = new ComboboxVO();
         //从枚举取
         for(RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum.values()){
             comboRunCheckVO.addOption(runCheckResultEnum.getCode(), runCheckResultEnum.getName());
         }
         model.put("runCheckCombobox", JsonUtil.toJson(comboRunCheckVO.getOptions()));
      // 人员
  		ComboboxVO createPeople = new ComboboxVO();
  		List<Condition> userCondition = new ArrayList<Condition>();
 		SysUserEntity userEntity = RequestContext.get().getUser();
  		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
  		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
  		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
  		for (SysUserEntity user : userList) {
  			createPeople.addOption(user.getId().toString(), user.getName());
  		}
  		model.put("createPeopleCombobox", JsonUtil.toJson(createPeople.getOptions()));
		return this.createModelAndView("run/runLog/runCheckList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexForRunCheck/{rlId}")
	public ModelAndView indexForRunCheck(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		//检查统计类型
		Map<Integer, SysDictionaryVO> sortProfessionMap = new HashMap<Integer, SysDictionaryVO>();
		List<Integer> sortProfessionList = new ArrayList<Integer>();
		ComboboxVO workRecordCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			sortProfessionMap.put(sysDictionaryVO.getOrder(), sysDictionaryVO);
			sortProfessionList.add(sysDictionaryVO.getOrder());
//            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		Collections.sort(sortProfessionList);
		for(Integer i : sortProfessionList){
			SysDictionaryVO sysDictionaryVO = sortProfessionMap.get(i);
			workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); 
		ComboboxVO comboRunCheckVO = new ComboboxVO();
		//从枚举取
		for(RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum.values()){
			comboRunCheckVO.addOption(runCheckResultEnum.getCode(), runCheckResultEnum.getName());
		}
		model.put("runCheckCombobox", JsonUtil.toJson(comboRunCheckVO.getOptions()));
		// 人员
		ComboboxVO createPeople = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity = RequestContext.get().getUser();
		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			createPeople.addOption(user.getId().toString(), user.getName());
		}
		model.put("createPeopleCombobox", JsonUtil.toJson(createPeople.getOptions()));
		return this.createModelAndView("run/runLog/runCheckListRunCheck", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{rlId}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		//保护变动方式
        ComboboxVO workRecordCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); 
		ComboboxVO comboRunCheckVO = new ComboboxVO();
		//从枚举取
        for(RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum.values()){
            comboRunCheckVO.addOption(runCheckResultEnum.getCode(), runCheckResultEnum.getName());
        }
		model.put("runCheckCombobox", JsonUtil.toJson(comboRunCheckVO.getOptions()));
		DateFormatUtil dUtil = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
        String dateStr = dUtil.format(new Date());
        model.put("giveDate", dateStr);
        ComboboxVO comboWorkPlanVO = new ComboboxVO();
        List<SysUserEntity> allUsers = sysUserService.findAll();
        for(SysUserEntity sysUserEntity : allUsers){
            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
        }
        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));   
        SysUserEntity userEntity = RequestContext.get().getUser();
        model.put("userEntity", userEntity);
        model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/runCheckAdd", model);
	}
	
	/**
	 * @Description:   新增 
	 * @author         wangcc 
	 * @Date           2017年7月27日 下午10:48:21 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEquimentLedger(@RequestBody RunCheckEntity runCheckEntity, HttpServletRequest request) {
		RunLogEntity runLogEntity = runLogService.findById(Long.valueOf(runCheckEntity.getRlId()));
		runLogEntity.setCheckForRun("1");
		if(runCheckEntity.getType()==1){
			for(int i = 2;i<10;i++){
				RunCheckEntity runCheck = new RunCheckEntity();
				runCheck.setType(i);
				runCheck.setCheckResult(runCheckEntity.getCheckResult());
				runCheck.setCheckDate(runCheckEntity.getCheckDate());
				runCheck.setCheckPerson(runCheckEntity.getCheckPerson());
				runCheck.setSuggest(runCheckEntity.getSuggest());
				runCheck.setStatus(RunCheckEnum.NOLMAL.getId());
				runCheck.setRlId(runCheckEntity.getRlId());
				runCheckService.addEntity(runCheck);
			}
		}
		runLogService.updateEntity(runLogEntity);
		return new ResultObj();
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		RunCheckEntity runCheckEntity = (RunCheckEntity)runCheckService.findById(id);
		model.put("entity", runCheckEntity);
		model.put("entityJson", JsonUtil.toJson(runCheckEntity));
		SysUserEntity userEntity = sysUserService.findById(Long.valueOf(runCheckEntity.getCheckPerson()));
		model.put("userEntity", userEntity);
		List<RunCheckEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("runCheckTreeList", JsonUtil.toJson(treeNodeList));
		   ComboboxVO workRecordCombobox = new ComboboxVO();
	        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
	        
	        for(String key :  codeDateTypeMap.keySet()){
	        	if(!key.equals("1")){
	        		SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
		            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());	
	        	}
	        }
	        model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); 
	        ComboboxVO comboRunCheckVO = new ComboboxVO();
	        //TODO下拉框具体内容根据具体业务定制
	        //从枚举取
	        for(RunCheckResultEnum runCheckResultEnum : RunCheckResultEnum.values()){
	            comboRunCheckVO.addOption(runCheckResultEnum.getCode(), runCheckResultEnum.getName());
	        }
	        model.put("runCheckCombobox", JsonUtil.toJson(comboRunCheckVO.getOptions()));
	        ComboboxVO comboWorkPlanVO = new ComboboxVO();
	        List<SysUserEntity> allUsers = sysUserService.findAll();
	        for(SysUserEntity sysUserEntity : allUsers){
	            comboWorkPlanVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
	        }
	        //TODO下拉框具体内容根据具体业务定制
	        model.put("workPlanCombobox", JsonUtil.toJson(comboWorkPlanVO.getOptions()));   
		
		return this.createModelAndView("run/runLog/runCheckEdit", model);
	}
	/**
	* @Title: update
	* @Description:修改保存
	* @author sunliang
	* @date 2017年9月11日下午1:43:54
	* @param t
	* @param request
	* @return
	* @throws Exception
	* @throws
	*/
	@RequestMapping(value = "/update/")
    public @ResponseBody
    ResultObj update(@RequestBody RunCheckEntity t, HttpServletRequest request) throws Exception{
        ResultObj resultObj = new ResultObj();
        t.setStatus(RunCheckEnum.NOLMAL.getId());
        runCheckService.updateEntity(t);
        return resultObj;
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "/dataList/{rlId}")
	public @ResponseBody ResultListObj getList(HttpServletRequest request, @RequestBody Map<String, Object> params, @PathVariable Long rlId) {
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipModelEnum.NORMAL.getId()));
			conditions.add(new Condition("T.C_RL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, rlId));
			ResultListObj resultObj = new ResultListObj();
			Page<RunCheckEntity> page = PageUtil.getPage(params);
			page.setOrders(OrmUtil.changeMapToOrders(params));
			page.addOrder(Sort.desc("checkDate"));
			List<RunCheckEntity> runCheckEntities = runCheckService.findByCondition(conditions, page);
			resultObj.setDraw((Integer)params.get("draw"));
			if (runCheckEntities != null) {
				resultObj.setData(runCheckEntities);
				if (page != null) {
					resultObj.setRecordsTotal(page.getTotal());
				}else{
					resultObj.setRecordsTotal((long) runCheckEntities.size());
				}
			}
			return resultObj;
	}
	   /**
	* @Title: expData
	* @Description: 导出功能
	* @author sunliang
	* @date 2017年9月11日下午1:44:05
	* @param req
	* @param res
	* @param params
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcel")
	    public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
			Page<RunCheckEntity> page = new Page<RunCheckEntity>();
			page.setPageSize(1000);
			page.addOrder(Sort.desc("checkDate"));
	        String rlId=req.getParameter("rlId");
//	        params.put("conditions", JSONArray.fromObject(conditionsChan));
	        List<Condition> conditions = OrmUtil.changeMapToCondition(params);
	        conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.INT, MatchTypeEnum.EQ, EquipModelEnum.NORMAL.getId()));
	        conditions.add(new Condition("T.C_RL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, rlId));
	        List<RunCheckEntity> dataList=runCheckService.findByCondition(conditions, page);
	        DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
	        for (RunCheckEntity runCheckEntity : dataList) {
	            runCheckEntity.setCheckDateString(sdf.format(runCheckEntity.getCheckDate()));
	        }
	        Map<String,Object> resultMap=new HashMap<String, Object>();
	        resultMap.put("dataList", dataList);
	        ExcelUtil.export(req, res, "运行检查报表模板.xlsx","运行检查.xlsx", resultMap);
	    }
	/**
	 * @Description:获取检查结果统计列表
	 * @author wangcc	
	 * @date 2017年9月11日下午17:38:54
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStatistics")
	public ModelAndView getStatistics(HttpServletRequest request, Map<String, Object> params) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		//检查统计类型
	    Map<Integer, SysDictionaryVO> sortProfessionMap = new HashMap<Integer, SysDictionaryVO>();
	    List<Integer> sortProfessionList = new ArrayList<Integer>();
		ComboboxVO workRecordCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("WORK_RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            sortProfessionMap.put(sysDictionaryVO.getOrder(), sysDictionaryVO);
            sortProfessionList.add(sysDictionaryVO.getOrder());
//            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        Collections.sort(sortProfessionList);
        for(Integer i : sortProfessionList){
            SysDictionaryVO sysDictionaryVO = sortProfessionMap.get(i);
            workRecordCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("workRecordCombobox", JsonUtil.toJson(workRecordCombobox.getOptions())); 
//        model.put("workRecordCombobox", JsonUtil.toJson(DictionaryUtil.getDictionariesByOrder("WORK_RECORD_TYPE").getOptions()));
        //默认当前月份
        DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM");
        String cTime = sdf.format(new Date());
        model.put("cTime",cTime);
        RunCheckEntity checkEntity = new RunCheckEntity();
        checkEntity.setCheckResult(RunCheckEnum.CHECKRESULT.getId().toString());
        model.put("runCheckEntity",checkEntity);
		return this.createModelAndView("run/runCheck/runCheckStatisticsList", model);
	}
   /**
	* @Description:检查结果统计
	* @author wangcc	
	* @param <O>
	* @date 2017年9月11日下午17:38:54
	* @throws Exception
	*/
	@RequestMapping(value = "/statistics")
    public @ResponseBody ResultListObj statistics(@RequestBody Map<String, Object> params, Page<RunCheckEntity> page) throws Exception{
		ResultListObj resultObj = new ResultListObj();
		List<RunCheckEntity> list = runCheckService.statistics(params,null);
		resultObj.setDraw((Integer)params.get("draw"));
		if (list != null) {
	        resultObj.setData(list);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) list.size());
			}
		}
        return resultObj;
    }
	/**
	* @Title: detail
	* @Description: 运行检查查看列表
	* @author sunliang
	* @date 2017年9月14日上午11:47:20
	* @param request
	* @param params
	* @param rlId
	* @return
	* @throws
	*/
	@RequestMapping("/detail/{rlId}")
    public ModelAndView detail(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<RunRecordEntity> treeNodeList = null; 
        //TODO下拉树具体内容根据具体业务定制
        model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboRunRecordVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/runCheckDetail", model);
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
		List<RunCheckEntity>  entityList = runCheckService.statistics(params,list);
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("dataList", entityList);
		ExcelExportUtil excelUtil = new ExcelExportUtil(reportName);
		String tempFileName = excelUtil.makeExcel(tmpMap,list);
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