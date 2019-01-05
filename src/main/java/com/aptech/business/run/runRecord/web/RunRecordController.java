package com.aptech.business.run.runRecord.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.aptech.business.run.dispaCom.domain.DispaComEntity;
import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.business.run.runRecord.dao.RunRecordDao;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.run.runRecord.service.RunRecordService;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行记事配置控制器
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/runRecord")
public class RunRecordController extends BaseController<RunRecordEntity> {
	
	@Autowired
	private RunRecordService runRecordService;
	@Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RunRecordDao runRecordDao;
	@Override
	public IBaseEntityOperation<RunRecordEntity> getService() {
		return runRecordService;
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
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions())); 
		model.put("rlId", rlId);
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView("run/runLog/runRecordList", model);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/seach", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) {
	//	AppListFormVO appListFormVO = listFormService.getAppListFormVOById(44);
		Page<Map<String, Object>> page = PageUtil.getPage(params);
		List<Condition> conditions = new ArrayList<Condition>();
		if (params.get("conditions") != null) {
			conditions = OrmUtil.changeMapToCondition(params);
			if(!RequestContext.get().isDeveloperMode()){
				SysUserEntity sysUserEntity = (SysUserEntity)RequestContext.get().getUser();
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(sysUserEntity.getUnitId());
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(sysUserEntity.getId());
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(sysUserEntity.getRoleIds());
						}
					}
				}
			}else{
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(0);
						}
					}
				}
			}
		}
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		if (page != null) {
			page.setOrders(orders);
		}
	//	List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
//		if (entities != null) {
//			resultObj.setData(entities);
//			if (page != null) {
//				resultObj.setRecordsTotal(page.getTotal());
//			} else {
//				resultObj.setRecordsTotal((long) entities.size());
//			}
//		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{rlId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboRunRecordVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions()));  
        model.put("rlId", rlId);      
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = format.format(new Date());
        model.put("recordTime", dateStr);
		return this.createModelAndView("run/runLog/runRecordAdd", model);
	}
	/**
	 * @Description:   自己写的新增方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addRun/{rlId}")
	public @ResponseBody ResultObj addRun(@RequestBody RunRecordEntity t, HttpServletRequest request ,@PathVariable Long rlId) {
		return runRecordService.addRun(t,rlId);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		RunRecordEntity runRecordEntity = (RunRecordEntity)runRecordService.findById(id);
		model.put("dataMap", runRecordEntity);
		model.put("dataMapJson", JsonUtil.toJson(runRecordEntity));
		
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboRunRecordVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions()));  
		return this.createModelAndView("run/runLog/runRecordEdit", model);
	}
	/**
	* @Title: detail
	* @Description: 进入详细页
	* @author sunliang
	* @date 上午11:46:16
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
        return this.createModelAndView("run/runLog/runRecordDetail", model);
    }
	/**
	* @Title: hisSel
	* @Description: 历史记录查询
	* @author sunliang
	* @date 2017年9月4日下午5:38:22
	* @param request
	* @param params
	* @return
	* @throws
	*/
	@RequestMapping("/hisSel")
    public ModelAndView hisSel(HttpServletRequest request, Map<String, Object> params) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
        //编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions())); 
        return this.createModelAndView("run/runLog/runRecordHisList", model);
    }
	/**
	* @Title: update
	* @Description: 修改方法
	* @author sunliang
	* @date 2017年9月4日下午5:38:11
	* @param t
	* @param request
	* @return
	* @throws Exception
	* @throws
	*/
	@RequestMapping(value = "/update/")
    public @ResponseBody
    ResultObj update(@RequestBody RunRecordEntity t, HttpServletRequest request) throws Exception{
        ResultObj resultObj = new ResultObj();
        runRecordService.updateEntity(t);
        return resultObj;
    }
	/**
	* @Title: expData
	* @Description: 导出功能
	* @author sunliang
	* @date 2017年9月4日下午5:38:36
	* @param req
	* @param res
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcel/{rlId}")
    public void expData(HttpServletRequest req,HttpServletResponse res, @PathVariable Long rlId) throws UnsupportedEncodingException{
        List<Condition> conditions=new ArrayList<Condition>();
        
    	Page<RunRecordEntity> page=new Page<RunRecordEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_RECORD_TIME"));
		
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        List<RunRecordEntity> dataList=runRecordService.findByCondition(conditions, page);
        int number = 1;
        for(RunRecordEntity runRecordEntity : dataList){
        	runRecordEntity.setNumber(number++);
        }
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("dataList", dataList);
        ExcelUtil.export(req, res, "运行记事报表模板.xlsx","运行记事.xlsx", resultMap);
    }
	
	/**
	* @Title: detail
	* @Description: 进入详细页
	* @author wangchuncheng
	* @date 上午11:46:16
	* @param request
	* @param params
	* @param rlId
	* @return
	* @throws
	*/
	@RequestMapping("/detailForWind/{rlId}")
    public ModelAndView detailForWind(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<RunRecordEntity> treeNodeList = null; 
        //TODO下拉树具体内容根据具体业务定制
        model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
        ComboboxVO comboRunRecordVO = new ComboboxVO();
        //TODO下拉框具体内容根据具体业务定制
        model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
        model.put("rlId", rlId);
        return this.createModelAndView("run/runLog/runRecordDetailForWind", model);
    }
	
	/**
	 * @Description:   集控运行日志-运行记事
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchDate(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return runRecordService.searchDate(request,params);
	}
	
	/**
	 * @Description:   集控运行日志-工作日志
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年12月19日 上午22:10:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchDataForWind")
	public @ResponseBody  ResultListObj searchDateForWind(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return runRecordService.searchDateForWind(request,params);
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexForWind/{rlId}")
	public ModelAndView listForWind(HttpServletRequest request, Map<String, Object> params, @PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("FAN_FACTORY_RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions())); 
		model.put("rlId", rlId);
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView("run/runLog/runRecordListForWind", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddForWind/{rlId}")
	public ModelAndView getAddPageForWind(HttpServletRequest request, @PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboRunRecordVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("FAN_FACTORY_RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions()));  
        model.put("rlId", rlId);      
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = format.format(new Date());
        model.put("recordTime", dateStr);
        //获取登录人
		return this.createModelAndView("run/runLog/runRecordAddForWind", model);
	}
	
	/**
	 *	添加场站运行日志
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addForWind", method = RequestMethod.POST)
	public @ResponseBody ResultObj addForWind(@RequestBody RunRecordEntity recordEntity, HttpServletRequest request) throws Exception {
		return runRecordService.addForWind(recordEntity,request);
	}
	
	/**
	 * @Description: 批量删除
	 * @author wangchuncheng
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulkDeleteForWind")
	public @ResponseBody
	ResultObj bulkDeleteForWind(@RequestBody List<Integer> ids) {
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
			runRecordService.deleteEntityForWind(id.longValue());
		}
		return resultObj;
	}
	
	/**
	* @Title: expData
	* @Description: 导出功能
	* @author sunliang
	* @date 2017年9月4日下午5:38:36
	* @param req
	* @param res
	* @throws UnsupportedEncodingException
	* @throws
	*/
	@RequestMapping("/exportExcelForWind/{rlId}")
    public void expDataForWind(HttpServletRequest req,HttpServletResponse res, @PathVariable Long rlId) throws UnsupportedEncodingException{
        List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        List<RunRecordEntity> dataList=runRecordService.findByConditionForWind(rlId);
        int number = 1;
        for(RunRecordEntity runRecordEntity : dataList){
        	runRecordEntity.setNumber(number++);
        }
        Map<String,Object> resultMap=new HashMap<String, Object>();
        resultMap.put("dataList", dataList);
        ExcelUtil.export(req, res, "场站运行记事报表模板.xlsx","运行记事.xlsx", resultMap);
    }
	
	/**
	* @Title: hisSel
	* @Description: 历史记录查询
	* @author sunliang
	* @date 2017年9月4日下午5:38:22
	* @param request
	* @param params
	* @return
	* @throws
	*/
	@RequestMapping("/hisSelForWind")
    public ModelAndView hisSelForWind(HttpServletRequest request, Map<String, Object> params) {
        Map<String, Object> model = new HashMap<String, Object>();
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
        //TODO下拉树具体内容根据具体业务定制
        model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
        //编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("FAN_FACTORY_RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions())); 
        return this.createModelAndView("run/runLog/runRecordHisListForWind", model);
    }
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditForWind/{id}")
	public ModelAndView getEditPageForWind(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		// 返回前台数据项
		RunRecordEntity runRecordEntity = (RunRecordEntity)runRecordService.findByIdForWind(id);
		model.put("dataMap", runRecordEntity);
		model.put("dataMapJson", JsonUtil.toJson(runRecordEntity));
		
        List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//TODO下拉树具体内容根据具体业务定制
		model.put("runRecordTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboRunRecordVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("runRecordCombobox", JsonUtil.toJson(comboRunRecordVO.getOptions()));
		//编码类型
        ComboboxVO recordTypeCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("FAN_FACTORY_RECORD_TYPE");
        
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            recordTypeCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("recordTypeCombobox", JsonUtil.toJson(recordTypeCombobox.getOptions()));  
		return this.createModelAndView("run/runLog/runRecordEditForWind", model);
	}
	
	/**
	* @Title: update
	* @Description: 修改方法
	* @author sunliang
	* @date 2017年9月4日下午5:38:11
	* @param t
	* @param request
	* @return
	* @throws Exception
	* @throws
	*/
	@RequestMapping(value = "/updateForWind")
    public @ResponseBody
    ResultObj updateForWind(@RequestBody RunRecordEntity recordEntity, HttpServletRequest request) throws Exception{
        ResultObj resultObj = new ResultObj();
        runRecordService.updateEntityForWind(recordEntity);
        return resultObj;
    }
	/**
	 * @Description:   删除
	 * @author         wangcc 
	 * @Date           2018年4月25日 上23:29:34 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/delForWind/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj deleteForWind(HttpServletRequest request ,@PathVariable Long id) {
		return runRecordService.deleteForWind(id);
	}
	/**
	 * @Description:   查询
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/searchRun/{rid}")
	public @ResponseBody ResultListObj searchRun(HttpServletRequest req,@RequestBody Map<String, Object> params, @PathVariable Long rid) {
		 ResultListObj resultObj = new ResultListObj();
		 runRecordDao.setFlag(false);
		 	Page<RunRecordEntity> page = new Page<RunRecordEntity>();
			page.setPageSize(Integer.MAX_VALUE);
			page.setOrders(OrmUtil.changeMapToOrders(params));
//			page.addOrder(Sort.desc("id"));
//		 	String conditions=req.getParameter("conditions");
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_RL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, rid));
			conditions.add(new Condition("C_RECORD_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "telItem"));
//		 	params = (Map<String, Object>)JSONObject.fromObject(conditions);
			List<RunRecordEntity> runRecordList = runRecordService.findByCondition(conditions, page);
	        if (runRecordList != null) {
	            resultObj.setData(runRecordList);
	        }
	        resultObj.setDraw((Integer)params.get("draw"));
	        return resultObj;
	}
}