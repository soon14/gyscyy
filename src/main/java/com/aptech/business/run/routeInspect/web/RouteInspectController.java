package com.aptech.business.run.routeInspect.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.aptech.business.run.routeInspect.domain.RouteInspectEntity;
import com.aptech.business.run.routeInspect.service.RouteInspectService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 巡检记录配置控制器
 *
 * @author 
 * @created 2018-03-13 17:34:53
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/routeInspect")
public class RouteInspectController extends BaseController<RouteInspectEntity> {
	
	@Autowired
	private RouteInspectService routeInspectService;
	@Autowired
    private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<RouteInspectEntity> getService() {
		return routeInspectService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{rlId}")
	public ModelAndView list(HttpServletRequest request,@PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//下拉树 单位
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/routeInspect/routeInspectList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{rlId}")
	public ModelAndView getAddPage(HttpServletRequest request,@PathVariable Long rlId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//下拉树 单位内容根据具体业务定制
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(new Date());
        model.put("recordTime", dateStr);
        model.put("rlId", rlId);
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		
		return this.createModelAndView("run/runLog/routeInspect/routeInspectAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//添加编辑数据体
		RouteInspectEntity entity = (RouteInspectEntity)routeInspectService.findById(id);
		model.put("dataMap", entity);
		model.put("dataMapJson", JsonUtil.toJson(entity));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//下拉树 单位
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		return this.createModelAndView("run/runLog/routeInspect/routeInspectEdit", model);
	}
	/**
	 *	Detail页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/detail/{rlId}")
	public ModelAndView detail(HttpServletRequest request,@PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/routeInspect/routeInspectListDetail", model);
	}
	/**
	 * 单查看
	 * @param request
	 * @param params
	 * @param rlId
	 * @return
	 */
	@RequestMapping("/singleDetail/{id}")
    public ModelAndView singleDetail(HttpServletRequest request, @PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));
        RouteInspectEntity entity=routeInspectService.findById(id);
        model.put("entity", entity);
        return this.createModelAndView("run/runLog/routeInspect/routeInspectDetail", model);
    }
	/**
	 * 导出列表
	 * @param req
	 * @param res
	 * @param params
	 * @param rlId
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel")
    public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditionsChan=req.getParameter("conditions");
		System.out.println("params");
		System.out.println(params);
		params = (Map<String, Object>)JSONObject.fromObject(conditionsChan);
		List<Condition> conditions=OrmUtil.changeMapToCondition(params);
	    Page<RouteInspectEntity> pages = new Page<RouteInspectEntity>();
		pages.addOrder(Sort.desc("id"));
        List<RouteInspectEntity> dataList=routeInspectService.findByCondition(params, pages);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        int number = 1;
        for(RouteInspectEntity routeInspectEntity : dataList){
        	routeInspectEntity.setNumber(number++);
        }
        resultMap.put("dataList", dataList);
        ExcelUtil.export(req, res, "巡检记录报表模板.xlsx","巡检记录.xlsx", resultMap);
    }
	
	/**
	 *	Detail页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/detailForWind/{rlId}")
	public ModelAndView detailForWind(HttpServletRequest request,@PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/routeInspect/routeInspectListDetailForWind", model);
	}
	
	/**
	 * @Description:   集控运行日志-日例会
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2017年12月25日 上午11:47:58 
	 * @throws         Exception
	 */
	@RequestMapping("/searchData")
	public @ResponseBody  ResultListObj searchData(HttpServletRequest request,@RequestBody Map<String, Object> params){
		return routeInspectService.searchData(request,params);
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexForWind/{rlId}")
	public ModelAndView listForWind(HttpServletRequest request,@PathVariable Long rlId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//下拉树 单位
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("INSPECTION_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		model.put("rlId", rlId);
		return this.createModelAndView("run/runLog/routeInspect/routeInspectListForWind", model);
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
		//下拉树 单位内容根据具体业务定制
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("INSPECTION_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(new Date());
        model.put("recordTime", dateStr);
        model.put("rlId", rlId);
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		
		return this.createModelAndView("run/runLog/routeInspect/routeInspectAddForWind", model);
	}
	/**
	 *	添加场站运行日志
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addForWind", method = RequestMethod.POST)
	public @ResponseBody ResultObj addForWind(@RequestBody RouteInspectEntity routeInspectEntity, HttpServletRequest request) throws Exception {
		return routeInspectService.addForWind(routeInspectEntity,request);
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
			routeInspectService.deleteEntityForWind(id.longValue());
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
        List<RouteInspectEntity> dataList=routeInspectService.findByConditionForWind(rlId);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        int number = 1;
        for(RouteInspectEntity routeInspectEntity : dataList){
        	routeInspectEntity.setNumber(number++);
        }
        resultMap.put("dataList", dataList);
        ExcelUtil.export(req, res, "场站巡检记录报表模板.xlsx","巡视记录.xlsx", resultMap);
    }
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditForWind/{id}")
	public ModelAndView getEditPageForWind(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//添加编辑数据体
		RouteInspectEntity entity = (RouteInspectEntity)routeInspectService.findById(id);
		model.put("dataMap", entity);
		model.put("dataMapJson", JsonUtil.toJson(entity));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//下拉树 单位
		model.put("routeInspectTreeList", JsonUtil.toJson(treeNodeList));
		//下拉框 类型
		ComboboxVO comboRouteInspectVO = new ComboboxVO();
        Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("FAN_FACTORY_RECORD_TYPE");
        for(String key :  codeDateTypeMap.keySet()){
            SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
            comboRouteInspectVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
		model.put("inspectTypeCombobox", JsonUtil.toJson(comboRouteInspectVO.getOptions()));
		return this.createModelAndView("run/runLog/routeInspect/routeInspectEditForWind", model);
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
    ResultObj updateForWind(@RequestBody RouteInspectEntity routeInspectEntity, HttpServletRequest request) throws Exception{
        ResultObj resultObj = new ResultObj();
        routeInspectService.updateEntityForWind(routeInspectEntity);
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
		return routeInspectService.deleteForWind(id);
	}
}