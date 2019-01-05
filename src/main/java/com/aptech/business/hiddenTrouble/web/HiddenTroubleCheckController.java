package com.aptech.business.hiddenTrouble.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.hiddenTrouble.domain.HiddenTroubleCheckEntity;
import com.aptech.business.hiddenTrouble.service.HiddenTroubleCheckService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.YesOrNoEnum;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 隐患排查台账配置控制器
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/hiddenTroubleCheck")
public class HiddenTroubleCheckController extends BaseController<HiddenTroubleCheckEntity> {
	
	@Autowired
	private HiddenTroubleCheckService hiddenTroubleCheckService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	
	@Autowired
	private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<HiddenTroubleCheckEntity> getService() {
		return hiddenTroubleCheckService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{unitId}/{unitName}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long unitId, @PathVariable String unitName) {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("unitId", unitId.toString());
		model.put("unitName", unitName);
		
		return this.createModelAndView("/hiddenTroubleCheck/hiddenTroubleCheckList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	/**
	 * @param request
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/getAdd/{unitId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable String unitId){
		Map<String, Object> model = new HashMap<String, Object>();
		//检查方式
		ComboboxVO checkModeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> checkModeMap = DictionaryUtil.getDictionaries("HIDDEN_TROUBLE_CHECK_MODE");
		for(String key : checkModeMap.keySet()){
			SysDictionaryVO checkModePreVO = checkModeMap.get(key);
			checkModeCombobox.addOption(checkModePreVO.getCode(), checkModePreVO.getName());
		}
		model.put("checkModeCombobox", JsonUtil.toJson(checkModeCombobox.getOptions()));
		
		//整改责任人
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
		ComboboxVO correctivePersionCombobox = new ComboboxVO();
		if (userList != null && userList.size() >0) {
			for (SysUserEntity user :userList) {
				correctivePersionCombobox.addOption(user.getId().toString(), user.getName());
			}
		}
		model.put("correctivePersionCombobox", JsonUtil.toJson(correctivePersionCombobox.getOptions()));
		
		//检查人
		ComboboxVO checkPersionCombobox = new ComboboxVO();
		//配置检查人职务，取得职务下的人
		List<Condition> dutiesConditions = new ArrayList<Condition>();
		dutiesConditions.add(new Condition("dutiesId", FieldTypeEnum.STRING,MatchTypeEnum.EQ, "73"));
		List<SysDutiesDetailEntity> sysDutiesDetailEntites = sysDutiesDetailService.findByCondition(dutiesConditions, null);
		if (sysDutiesDetailEntites != null && sysDutiesDetailEntites.size() >0) {
			List<String> userUnitRelIds = new ArrayList<String>();
			List<String> unitIds = new ArrayList<String>();
			unitIds.add(unitId);
			for (SysDutiesDetailEntity user :sysDutiesDetailEntites) {
				userUnitRelIds.add(user.getUserUnitRelId().toString());
			}
			List<Condition> userConditions = new ArrayList<Condition>();
			userConditions.add(new Condition("c_id",FieldTypeEnum.STRING,MatchTypeEnum.IN, userUnitRelIds.toArray()));
			userConditions.add(new Condition("c_unit_id", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIds.toArray()));
			List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
			userConditions.clear();
			//查出用户Id；
			List<Long> userIds = new ArrayList<Long>();
			if(!userUnitRelList.isEmpty()){
				for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
					userIds.add(userUnitRelEntity.getUserId());
				}
				userConditions.add(new Condition("a.C_ID",FieldTypeEnum.STRING, MatchTypeEnum.IN, userIds.toArray()));
				userConditions.add(new Condition("a.C_STATUS",FieldTypeEnum.STRING, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));
				
				List<SysUserEntity> checkUserList = sysUserService.findByCondition(userConditions, null);
				if (checkUserList != null && checkUserList.size() >0) {
					for (SysUserEntity user :checkUserList) {
						checkPersionCombobox.addOption(user.getId().toString(), user.getName());
					}
				}
			}
		}
		model.put("checkPersionCombobox", JsonUtil.toJson(checkPersionCombobox.getOptions()));
		
		//是否整改
		ComboboxVO isCorrectiveCombobox = new ComboboxVO();
		isCorrectiveCombobox.addOption("1", YesOrNoEnum.YES.getDisplayName());
		isCorrectiveCombobox.addOption("0", YesOrNoEnum.NO.getDisplayName());
		model.put("isCorrectiveCombobox", JsonUtil.toJson(isCorrectiveCombobox.getOptions()));
		
		//组织ID
		model.put("unitId", unitId);
		return this.createModelAndView("/hiddenTroubleCheck/hiddenTroubleCheckAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		HiddenTroubleCheckEntity checkEntity = (HiddenTroubleCheckEntity)hiddenTroubleCheckService.findById(id);
		model.put("entity", checkEntity);
		model.put("entityJson", JsonUtil.toJson(checkEntity));
		
		//检查方式
		ComboboxVO checkModeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> checkModeMap = DictionaryUtil.getDictionaries("HIDDEN_TROUBLE_CHECK_MODE");
		for(String key : checkModeMap.keySet()){
			SysDictionaryVO checkModePreVO = checkModeMap.get(key);
			checkModeCombobox.addOption(checkModePreVO.getCode(), checkModePreVO.getName());
		}
		model.put("checkModeCombobox", JsonUtil.toJson(checkModeCombobox.getOptions()));
		
		//整改责任人
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, checkEntity.getUnitId()));
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
		ComboboxVO correctivePersionCombobox = new ComboboxVO();
		if (userList != null && userList.size() >0) {
			for (SysUserEntity user :userList) {
				correctivePersionCombobox.addOption(user.getId().toString(), user.getName());
			}
		}
		model.put("correctivePersionCombobox", JsonUtil.toJson(correctivePersionCombobox.getOptions()));
				
		//检查人
		ComboboxVO checkPersionCombobox = new ComboboxVO();
		//配置检查人职务，取得职务下的人
		List<Condition> dutiesConditions = new ArrayList<Condition>();
		dutiesConditions.add(new Condition("dutiesId", FieldTypeEnum.STRING,MatchTypeEnum.EQ, "73"));
		List<SysDutiesDetailEntity> sysDutiesDetailEntites = sysDutiesDetailService.findByCondition(dutiesConditions, null);
		if (sysDutiesDetailEntites != null && sysDutiesDetailEntites.size() >0) {
			List<String> userUnitRelIds = new ArrayList<String>();
			List<String> unitIds = new ArrayList<String>();
			unitIds.add(checkEntity.getUnitId());
			for (SysDutiesDetailEntity user :sysDutiesDetailEntites) {
				userUnitRelIds.add(user.getUserUnitRelId().toString());
			}
			List<Condition> userConditions = new ArrayList<Condition>();
			userConditions.add(new Condition("c_id",FieldTypeEnum.STRING,MatchTypeEnum.IN, userUnitRelIds.toArray()));
			userConditions.add(new Condition("c_unit_id", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIds.toArray()));
			List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
			userConditions.clear();
			//查出用户Id；
			List<Long> userIds = new ArrayList<Long>();
			if(!userUnitRelList.isEmpty()){
				for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
					userIds.add(userUnitRelEntity.getUserId());
				}
				userConditions.add(new Condition("a.C_ID",FieldTypeEnum.STRING, MatchTypeEnum.IN, userIds.toArray()));
				userConditions.add(new Condition("a.C_STATUS",FieldTypeEnum.STRING, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));

				List<SysUserEntity> checkUserList = sysUserService.findByCondition(userConditions, null);
				if (checkUserList != null && checkUserList.size() >0) {
					for (SysUserEntity user :checkUserList) {
						checkPersionCombobox.addOption(user.getId().toString(), user.getName());
					}
				}
			}
		}
		model.put("checkPersionCombobox", JsonUtil.toJson(checkPersionCombobox.getOptions()));

		//是否整改
		ComboboxVO isCorrectiveCombobox = new ComboboxVO();
		isCorrectiveCombobox.addOption("1", YesOrNoEnum.YES.getDisplayName());
		isCorrectiveCombobox.addOption("0", YesOrNoEnum.NO.getDisplayName());
		model.put("isCorrectiveCombobox", JsonUtil.toJson(isCorrectiveCombobox.getOptions()));
		
		//组织ID
		model.put("unitId", checkEntity.getUnitId());
		
		return this.createModelAndView("/hiddenTroubleCheck/hiddenTroubleCheckEdit", model);
	}
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/addEntity", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEntity(@RequestBody HiddenTroubleCheckEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateTime(cal.getTime());

		this.hiddenTroubleCheckService.addEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDEN_TROUBLE_CHECK.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}
	/**
	 * 修改保存
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editEntity")
	public @ResponseBody
	ResultObj update(@RequestBody HiddenTroubleCheckEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setUpdateTime(cal.getTime());
		
		hiddenTroubleCheckService.updateEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDEN_TROUBLE_CHECK.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		hiddenTroubleCheckService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDEN_TROUBLE_CHECK.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		for (String id : ids) {
			Long longId = new Long(id);
			HiddenTroubleCheckEntity entity = hiddenTroubleCheckService.findById(longId);
			if (entity != null) {
				hiddenTroubleCheckService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDEN_TROUBLE_CHECK.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getView/{id}")
	public ModelAndView getViewPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		HiddenTroubleCheckEntity checkEntity = (HiddenTroubleCheckEntity)hiddenTroubleCheckService.findById(id);
		
		model.put("entity", checkEntity);
		model.put("entityJson", JsonUtil.toJson(checkEntity));
		
		//整改责任人
		SysUserEntity correctivePersion = this.sysUserService.findById(new Long(checkEntity.getCorrectivePersionId()));
		checkEntity.setCorrectivePersionName(correctivePersion.getName());
				
		//检查人
		SysUserEntity checkPersion = this.sysUserService.findById(new Long(checkEntity.getCheckPersionId()));
		checkEntity.setCheckPersionName(checkPersion.getName());

		
		//组织ID
		model.put("unitId", checkEntity.getUnitId());
		
		return this.createModelAndView("/hiddenTroubleCheck/hiddenTroubleCheckView", model);
	}
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<HiddenTroubleCheckEntity> page = PageUtil.getPage(params);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);		
		List<HiddenTroubleCheckEntity> dataList = hiddenTroubleCheckService.findByCondition(conditions, page);
		if (dataList != null && dataList.size() > 0) {
			conditions.clear();
			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
			List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
			
			for (HiddenTroubleCheckEntity safeCheckEntity : dataList) {
				for (SysUserEntity user : userList) {
					if (safeCheckEntity.getCheckPersionId().equals(user.getId().toString())) {
						safeCheckEntity.setCheckPersionName(user.getName());
					} else if (safeCheckEntity.getCorrectivePersionId().equals(user.getId().toString())) {
						safeCheckEntity.setCorrectivePersionName(user.getName());
					} else {
						continue;
					}
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
				resultObj.setRecordsTotal(dataList.size());
			}
		}
		return resultObj;
	}
	
	/**
	 *	组织列表页跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/unitList")
	public ModelAndView unitList(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();

		//单位下拉框
		ComboboxVO comboboxVO = new ComboboxVO();
		
		//组织机构
		List<Condition> conditions = new ArrayList<Condition>();
		String [] organizationIds = {"1", "3", "4", "5", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
				
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		for(SysUnitEntity entity : unitList){
			comboboxVO.addOption(entity.getId().toString(), entity.getName());
		}
		model.put("unitList", JsonUtil.toJson(comboboxVO.getOptions()));
		//获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userUnitId", userEntity.getUnitId());
		return this.createModelAndView("/hiddenTroubleCheck/hiddenTroubleCheckUnitList", model);
	}
	/**
	 * 组织机构条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchUnitData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchUnitData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<SysUnitEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		String [] organizationIds = {"1", "3", "4", "5", "7"};
		
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
		
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, page);
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (unitList != null) {
			resultObj.setData(unitList);
			resultObj.setRecordsTotal(unitList.size());
		}
		return resultObj;
	}
}