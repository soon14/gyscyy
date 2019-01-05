package com.aptech.business.assetManage.web;

import java.io.UnsupportedEncodingException;
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

import com.aptech.business.assetManage.domain.AssetManagementEntity;
import com.aptech.business.assetManage.service.AssetManagementService;
import com.aptech.business.component.dictionary.AssetManageTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.BaseController;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;
 
@Controller
@RequestMapping("/assetManagement") 
public class AssetManagementController extends BaseController<AssetManagementEntity> {
    @Autowired
	private AssetManagementService assetManagementService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FourCodeService fourCodeService;
    @Autowired
    private SysUnitService sysUnitService;
    @Autowired
    private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<AssetManagementEntity> getService() {
		return assetManagementService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView list(HttpServletRequest request, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		//功能名称
		for (AssetManageTypeEnum typeEnum : AssetManageTypeEnum.values()) {
			if (typeEnum.getCode().equals(type.toString())) {
				model.put("targetType", typeEnum.getName());
			}
		}

		//资质管理默认类型
		ComboboxVO defualtTypeCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> defualtTypeMap = DictionaryUtil.getDictionaries("ASSET_DEFUALT_TYPE");
		for(String key : defualtTypeMap.keySet()){
			SysDictionaryVO defualtTypeVO = defualtTypeMap.get(key);
			defualtTypeCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("defualtTypeCombobox", JsonUtil.toJson(defualtTypeCombo.getOptions()));

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		model.put("loginUser", sysUserEntity);
		if(type.equals("1")){
			return this.createModelAndView("safeManage/assetManage/list", model);
		}else{
			return this.createModelAndView("safeManage/assetManage/listPro", model);
		}
		
	}
	
	/**
	 * 条件查询
	 * 
	 * @Title: show
	 * @Description:
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<AssetManagementEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<AssetManagementEntity> dataList = assetManagementService.findByCondition(conditions, page);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		if (dataList != null && dataList.size() > 0) {
			for (AssetManagementEntity entity : dataList ) {
				if ("super".equals(sysUserEntity.getLoginName())) {
					entity.setButtonDisplayFlag(true);
				} else if (sysUserEntity.getId().toString().equals(entity.getCreater())) {
					entity.setButtonDisplayFlag(true);
				} else {
					entity.setButtonDisplayFlag(false);
				}
			}
		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) dataList.size());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/gotoAddPage/{type}")
	public ModelAndView gotoAddPage(HttpServletRequest request, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		//资质管理类型，1总部/2生产单位
		model.put("type", type);
		
		//功能名称
		for (AssetManageTypeEnum typeEnum : AssetManageTypeEnum.values()) {
			if (typeEnum.getCode().equals(type.toString())) {
				model.put("targetType", typeEnum.getName());
			}
		}
		// 部门
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
//				companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//				companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//资质管理默认类型
		ComboboxVO defualtTypeCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> defualtTypeMap = DictionaryUtil.getDictionaries("ASSET_DEFUALT_TYPE");
		for(String key : defualtTypeMap.keySet()){
			SysDictionaryVO defualtTypeVO = defualtTypeMap.get(key);
			defualtTypeCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("defualtTypeCombobox", JsonUtil.toJson(defualtTypeCombo.getOptions()));
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		if(type.equals("1")){
			return this.createModelAndView("safeManage/assetManage/add", model);
		}else{
			return this.createModelAndView("safeManage/assetManage/addPro", model);
		}
		
	}
	
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/addEntity", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEntity(@RequestBody AssetManagementEntity entity, HttpServletRequest request) {
		DateFormatUtil dfuYM = DateFormatUtil.getInstance("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());
//		SysUserEntity sysUserEntity = RequestContext.get().getUser();
//		entity.setCreater(sysUserEntity.getId().toString());
//		JSONArray jSONArray=JSONArray.fromObject(entity.getAppendix());
//			for (int i = 0; i < jSONArray.size(); i++) {
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			entity.setRelevantInfo(String.valueOf(josnObject.get("name")));
//		}
		Map<String, Object> codeparams=new HashMap<String, Object> ();
		codeparams.put("year", new Date());
		String code=fourCodeService.getBusinessCode("资质管理总部编码", codeparams);
		entity.setCode(code);
		this.assetManagementService.addEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTHEADQUARTERS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}
	/**
	 * 添加生产单位
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/addEntityPro", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEntityPro(@RequestBody AssetManagementEntity entity, HttpServletRequest request) {
		DateFormatUtil dfuYM = DateFormatUtil.getInstance("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());
//		SysUserEntity sysUserEntity = RequestContext.get().getUser();
//		entity.setCreater(sysUserEntity.getId().toString());
//		JSONArray jSONArray=JSONArray.fromObject(entity.getAppendix());
//			for (int i = 0; i < jSONArray.size(); i++) {
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			entity.setRelevantInfo(String.valueOf(josnObject.get("name")));
//		}
		Map<String, Object> codeparams=new HashMap<String, Object> ();
		codeparams.put("year", new Date());
		String code=fourCodeService.getBusinessCode("资质管理生产单位编码", codeparams);
		entity.setCode(code);
		this.assetManagementService.addEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTRODUCTIONUNIT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}

	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/gotoEditPage/{id}")
	public ModelAndView gotoEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//资质管理默认类型
		ComboboxVO defualtTypeCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> defualtTypeMap = DictionaryUtil.getDictionaries("ASSET_DEFUALT_TYPE");
		for(String key : defualtTypeMap.keySet()){
			SysDictionaryVO defualtTypeVO = defualtTypeMap.get(key);
			defualtTypeCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("defualtTypeCombobox", JsonUtil.toJson(defualtTypeCombo.getOptions()));
		
		AssetManagementEntity entity = assetManagementService.findById(id);
		
		if (entity != null) {
			model.put("assetManagementEntity", entity);
		} else {
			model.put("assetManagementEntity", new AssetManagementEntity());
		}
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entity.getCreater()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/assetManage/edit", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/gotoEditPro/{id}")
	public ModelAndView gotoEditPro(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		//资质管理默认类型
		ComboboxVO defualtTypeCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> defualtTypeMap = DictionaryUtil.getDictionaries("ASSET_DEFUALT_TYPE");
		for(String key : defualtTypeMap.keySet()){
			SysDictionaryVO defualtTypeVO = defualtTypeMap.get(key);
			defualtTypeCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("defualtTypeCombobox", JsonUtil.toJson(defualtTypeCombo.getOptions()));
		// 部门
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
//						companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//						companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		AssetManagementEntity entity = assetManagementService.findById(id);
		
		if (entity != null) {
			model.put("assetManagementEntity", entity);
		} else {
			model.put("assetManagementEntity", new AssetManagementEntity());
		}
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entity.getCreater()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/assetManage/editPro", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/gotoViewPage/{id}")
	public ModelAndView gotoViewPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		AssetManagementEntity entity=assetManagementService.findById(id);
		
		model.put("assetManagementEntity", entity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entity.getCreater()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/assetManage/view", model);
	}
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/gotoViewPro/{id}")
	public ModelAndView gotoViewPro(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		AssetManagementEntity entity=assetManagementService.findById(id);
		
		model.put("assetManagementEntity", entity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entity.getCreater()));
		model.put("sysUserEntity", sysUserEntity);
		if (entity.getUnitId()!=null) {
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(entity.getUnitId()));
			model.put("sysUnitEntity", sysUnitEntity);
		}
		return this.createModelAndView("safeManage/assetManage/viewPro", model);
	}
	
	/**
	 * 修改保存
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editEntity/{submenu}")
	public @ResponseBody
	ResultObj update(@RequestBody AssetManagementEntity entity, HttpServletRequest request,@PathVariable String submenu) {
		entity.setUpdateDate(new Date());
//		JSONArray jSONArray=JSONArray.fromObject(entity.getAppendix());
//		for (int i = 0; i < jSONArray.size(); i++) {
//			JSONObject josnObject= (JSONObject) jSONArray.get(i);
//			entity.setRelevantInfo(String.valueOf(josnObject.get("name")));
//		}
		assetManagementService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTHEADQUARTERS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTRODUCTIONUNIT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}
		return new ResultObj();
	}
	
	/**
	 * @Description: 删除
	 * @author 
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOne/{id}/{submenu}")
	public @ResponseBody
	ResultObj deleteOne( @PathVariable Long id,@PathVariable String submenu) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		assetManagementService.deleteEntity(id);
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @author 
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/allDelete/{submenu}")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<String> ids,@PathVariable String submenu) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (String id : ids) {
			Long longId = new Long(id);
			AssetManagementEntity entity = assetManagementService.findById(longId);
			if (entity != null) {
				assetManagementService.deleteEntity(longId);
			}
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.QUALIFICATIONMANAGEMENTRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		return resultObj;
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
		
		Page<AssetManagementEntity> page=new Page<AssetManagementEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<AssetManagementEntity> dataList = assetManagementService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);

		List<Map<String, Object>> queryParams = (List<Map<String, Object>>) params.get("conditions");
		String typeVale = "";
		for (Map<String, Object> param : queryParams) {
			if ("C_TYPE".equals(String.valueOf(param.get("field")))) {
				typeVale =  (String) param.get("value");
			}
		}
		if (AssetManageTypeEnum.TYPE1.getCode().equals(typeVale)) {
			ExcelUtil.export(req, res, "资质管理总部报表模板.xlsx","资质管理总部.xlsx", resultMap);
		} else if (AssetManageTypeEnum.TYPE2.getCode().equals(typeVale)) {
			ExcelUtil.export(req, res, "资质管理生产单位报表模板.xlsx","资质管理生产单位.xlsx", resultMap);
		}
	
	}
	
	
	
}