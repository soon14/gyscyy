package com.aptech.business.safeManage.hiddenTrouble.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.aptech.business.component.dictionary.EducationTrainTypeEnum;
import com.aptech.business.component.dictionary.HiddenTroubleTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.hiddenTrouble.domain.HiddenTroubleEntity;
import com.aptech.business.safeManage.hiddenTrouble.service.HiddenTroubleService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 隐患排查配置控制器
 *
 * @author 
 * @created 2018-03-31 12:52:23
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/hiddenTrouble")
public class HiddenTroubleController extends BaseController<HiddenTroubleEntity> {
	
	@Autowired
	private HiddenTroubleService hiddenTroubleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<HiddenTroubleEntity> getService() {
		return hiddenTroubleService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long type) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_LEVEL = 0 OR C_LEVEL = 2   AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
//		companyConditions.add(new Condition(" C_LEVEL = 2 OR C_LEVEL = 0 OR C_LEVEL = 3"));
//				companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//				companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
		
		model.put("type", type);
		for (HiddenTroubleTypeEnum standardTypeEnum : HiddenTroubleTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("targetType", standardTypeEnum.getName());
			}
		}
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		model.put("userName", userEntity.getName());
		
		if(Integer.valueOf(type.toString())==3){
			return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleListCheck", model);
		}else if(Integer.valueOf(type.toString())==1){
			return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleListUnit", model);
		}else{
			return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleList", model);
		}
		
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<HiddenTroubleEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("hiddenTroubleTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		
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
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
		//部门
		conditions.clear();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);

		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));		
       //获取登录人信息
//		SysUserEntity userEntity= RequestContext.get().getUser();
//		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleAdd", model);
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddUnit")
	public ModelAndView getAddUnit(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<HiddenTroubleEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("hiddenTroubleTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
				.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
			emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		
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
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
		//部门
		conditions.clear();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));		
		//获取登录人信息
//		SysUserEntity userEntity= RequestContext.get().getUser();
//		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleAddUnit", model);
	}
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAddCheck")
	public ModelAndView getAddPageCheck(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
	
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 0 OR C_LEVEL = 2   AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);

		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		conditions.clear();
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
				.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
			emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));

		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleAddCheck", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		HiddenTroubleEntity hiddenTroubleEntity = (HiddenTroubleEntity)hiddenTroubleService.findById(id);
		model.put("entity", hiddenTroubleEntity);
		model.put("entityJson", JsonUtil.toJson(hiddenTroubleEntity));
		
		List<HiddenTroubleEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("hiddenTroubleTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);

		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));	
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		conditions.clear();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
       //获取登录人信息
//		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(hiddenTroubleEntity.getCreateUserId()));
//		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleEdit", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditUnit/{id}")
	public ModelAndView getEditUnit(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		HiddenTroubleEntity hiddenTroubleEntity = (HiddenTroubleEntity)hiddenTroubleService.findById(id);
		model.put("entity", hiddenTroubleEntity);
		model.put("entityJson", JsonUtil.toJson(hiddenTroubleEntity));
		
		List<HiddenTroubleEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("hiddenTroubleTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
				.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
			emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));	
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		conditions.clear();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
		//获取登录人信息
//		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(hiddenTroubleEntity.getCreateUserId()));
//		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleEditUnit", model);
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEditCheck/{id}")
	public ModelAndView getEditPageCheck(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		HiddenTroubleEntity hiddenTroubleEntity = (HiddenTroubleEntity)hiddenTroubleService.findById(id);
		model.put("entity", hiddenTroubleEntity);
		model.put("entityJson", JsonUtil.toJson(hiddenTroubleEntity));
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_LEVEL = 0 OR C_LEVEL = 2   AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 6"));
//		companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
		ComboboxVO comboHiddenTroubleVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("hiddenTroubleCombobox", JsonUtil.toJson(comboHiddenTroubleVO.getOptions()));
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
		model.put("userEntity", userEntity);
		model.put("userName", userEntity.getName());
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
				.getDictionaries("HIDDEN_TROUBLE_TYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
			emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}	
		model.put("categoryCombobox", JsonUtil.toJson(emergencyType.getOptions()));
		//获取登录人信息
//		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(hiddenTroubleEntity.getCreateUserId()));
//		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleEditCheck", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveEditPage/{id}")
	public @ResponseBody ResultObj saveTrainPage(@RequestBody HiddenTroubleEntity entity, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		hiddenTroubleService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEHEADQUARTERS.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEUNIT.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		} else if(HiddenTroubleTypeEnum.TYPE3.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLE_HAVE_TROUBLE.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		}
		return resultObj;
	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel/{type}")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params,@PathVariable Long type) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		Page<HiddenTroubleEntity> page=new Page<HiddenTroubleEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<HiddenTroubleEntity> dataList=hiddenTroubleService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "隐患排查上级排查报表模板.xlsx","上级排查.xlsx", resultMap);
		}else if (Integer.valueOf(type.toString())==2) {
			ExcelUtil.export(req, res, "隐患排查生产单位报表模板.xlsx","生产单位.xlsx", resultMap);
		}else {
			ExcelUtil.export(req, res, "隐患排查存在隐及排查情况报表模板.xlsx","存在隐患及排查情况.xlsx", resultMap);
		}
		
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		HiddenTroubleEntity targetEntity=hiddenTroubleService.findById(id);
		model.put("entity", targetEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		SysUnitEntity unitEntity = sysUnitService.findById(targetEntity.getUnitId());
		model.put("unitName", unitEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleDetail", model);
	}
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetailUnit/{id}")
	public ModelAndView getDetailUnit(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		HiddenTroubleEntity targetEntity=hiddenTroubleService.findById(id);
		model.put("entity", targetEntity);
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleDetailUnit", model);
	}
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetailCheck/{id}")
	public ModelAndView getDetailCheck(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		HiddenTroubleEntity targetEntity=hiddenTroubleService.findById(id);
		model.put("entity", targetEntity);
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(targetEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		return this.createModelAndView("safeManage/hiddenTrouble/hiddenTroubleDetailCheck", model);
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
	public @ResponseBody ResultObj addEntity(@RequestBody HiddenTroubleEntity entity, HttpServletRequest request) {
		this.hiddenTroubleService.addEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(HiddenTroubleTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEHEADQUARTERS.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		} else if(HiddenTroubleTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEUNIT.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		} else if(HiddenTroubleTypeEnum.TYPE3.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLE_HAVE_TROUBLE.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne(@PathVariable Long id) {
		
		HiddenTroubleEntity entity = hiddenTroubleService.findById(id);
		ResultObj resultObj = new ResultObj();
		hiddenTroubleService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEHEADQUARTERS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEUNIT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		} else if(HiddenTroubleTypeEnum.TYPE3.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLE_HAVE_TROUBLE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		}
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
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (String id : ids) {
			Long longId = new Long(id);
			HiddenTroubleEntity entity = hiddenTroubleService.findById(longId);
			if (entity != null) {
				hiddenTroubleService.deleteEntity(longId);
				if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
					operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEHEADQUARTERS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
					operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLEDISPOSEUNIT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				}  else if(HiddenTroubleTypeEnum.TYPE3.getCode().equals(entity.getType().toString())) {
					operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.HIDDENTROUBLE_HAVE_TROUBLE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				}
			}
		}
		return resultObj;
	}
	
	
	
}