package com.aptech.business.safeManage.emergencyManage.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.emergencyManage.domain.EmergencyManageEntity;
import com.aptech.business.safeManage.emergencyManage.service.EmergencyManageService;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
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
 * 应急管理配置控制器
 *
 * @author 
 * @created 2018-03-28 16:27:06
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/emergencyManage")
public class EmergencyManageController extends BaseController<EmergencyManageEntity> {
	
	@Autowired
	private EmergencyManageService emergencyManageService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 操作日志
	 */
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EmergencyManageEntity> getService() {
		return emergencyManageService;
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
		// 部门
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("EMERGENCYTYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("emergencyType", JsonUtil.toJson(emergencyType.getOptions()));
		SysUserEntity sysUserEntity=RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/emergencyManage/emergencyManageList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
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
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("EMERGENCYTYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("emergencyType", JsonUtil.toJson(emergencyType.getOptions()));
		return this.createModelAndView("safeManage/emergencyManage/emergencyManageAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EmergencyManageEntity emergencyManageEntity = (EmergencyManageEntity)emergencyManageService.findById(id);
		model.put("entity", emergencyManageEntity);
		model.put("entityJson", JsonUtil.toJson(emergencyManageEntity));
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition(" C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7 OR C_ORGANIZATION = 13"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
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
		// 设备类型
		Map<String, SysDictionaryVO> emergencyTypeMap = DictionaryUtil
						.getDictionaries("EMERGENCYTYPE");
		ComboboxVO emergencyType=new  ComboboxVO();
		for(String key :  emergencyTypeMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = emergencyTypeMap.get(key);
        	emergencyType.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }	
		model.put("emergencyType", JsonUtil.toJson(emergencyType.getOptions()));
		 //获取登录人信息
 		SysUserEntity sysUserEntity= sysUserService.findById(Long.parseLong(emergencyManageEntity.getUserId()));
 		model.put("sysUserEntity", sysUserEntity);	
		return this.createModelAndView("safeManage/emergencyManage/emergencyManageEdit", model);
	}
	@RequestMapping(value = "/update")
	public @ResponseBody ResultObj update(@RequestBody EmergencyManageEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		emergencyManageService.update(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EMERGENCIESMANAGE.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EmergencyManageEntity emergencyManageEntity = (EmergencyManageEntity)emergencyManageService.findById(id);
		model.put("entity", emergencyManageEntity);
		model.put("entityJson", JsonUtil.toJson(emergencyManageEntity));
		
		List<SafeReportEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("safeReportTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSafeReportVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("safeReportCombobox", JsonUtil.toJson(comboSafeReportVO.getOptions()));
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(emergencyManageEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(emergencyManageEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("safeManage/emergencyManage/emergencyManageDetail", model);
	}
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<SafeReportEntity> page = new Page<SafeReportEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("t.C_ID"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<EmergencyManageEntity> dataList=emergencyManageService.findByCondition(params, null);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "应急管理模板.xlsx","应急管理.xlsx", resultMap);
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne(@PathVariable Long id) {
		
		ResultObj resultObj = new ResultObj();
		emergencyManageService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EMERGENCIESMANAGE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		
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
			EmergencyManageEntity entity = emergencyManageService.findById(longId);
			if (entity != null) {
				emergencyManageService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EMERGENCIESMANAGE.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				
			}
		}
		return resultObj;
	}
}