package com.aptech.business.safeManage.educationTrain.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.EducationCategoryTypeEnum;
import com.aptech.business.component.dictionary.EducationTrainTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.educationTrain.domain.EducationTrainEntity;
import com.aptech.business.safeManage.educationTrain.service.EducationTrainService;
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
 * 教育培训配置控制器
 *
 * @author 
 * @created 2018-03-31 12:43:38
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/educationTrain")
public class EducationTrainController extends BaseController<EducationTrainEntity> {
	
	@Autowired
	private EducationTrainService educationTrainService;
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
	public IBaseEntityOperation<EducationTrainEntity> getService() {
		return educationTrainService;
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
		
		model.put("type", type);
		for (EducationTrainTypeEnum standardTypeEnum : EducationTrainTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("targetType", standardTypeEnum.getName());
			}
		}
		//安全检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(EducationCategoryTypeEnum key : EducationCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		 //单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
       
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/educationTrain/educationTrainList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<EducationTrainEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("educationTrainTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEducationTrainVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("educationTrainCombobox", JsonUtil.toJson(comboEducationTrainVO.getOptions()));
		//安全检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(EducationCategoryTypeEnum key : EducationCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);

		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/educationTrain/educationTrainAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EducationTrainEntity educationTrainEntity = (EducationTrainEntity)educationTrainService.findById(id);
		model.put("entity", educationTrainEntity);
		model.put("entityJson", JsonUtil.toJson(educationTrainEntity));
		//安全检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(EducationCategoryTypeEnum key : EducationCategoryTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(educationTrainEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);

		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		return this.createModelAndView("safeManage/educationTrain/educationTrainEdit", model);
	}
	
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveTrainPage/{id}")
	public @ResponseBody ResultObj  saveTrainPage(@RequestBody EducationTrainEntity entity, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		educationTrainService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINHEADQUARTERS.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINRODUCTIONUNIT.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
			
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
		
		Page<EducationTrainEntity> page=new Page<EducationTrainEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<EducationTrainEntity> dataList=educationTrainService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "教育培训总部报表模板.xlsx","教育培训总部.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "教育培训生产单位报表模板.xlsx","教育培训生产单位.xlsx", resultMap);
		}
		
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		EducationTrainEntity targetEntity=educationTrainService.findById(id);
		model.put("entity", targetEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		SysUnitEntity unitEntity = sysUnitService.findById(Long.valueOf(targetEntity.getUnitId()));
		model.put("unitName", unitEntity.getName());
		return this.createModelAndView("safeManage/educationTrain/educationTrainDetail", model);
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne(@PathVariable Long id) {
		
		EducationTrainEntity entity = educationTrainService.findById(id);
		ResultObj resultObj = new ResultObj();
		educationTrainService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINHEADQUARTERS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
			operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINRODUCTIONUNIT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			
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
			EducationTrainEntity entity = educationTrainService.findById(longId);
			if (entity != null) {
				educationTrainService.deleteEntity(longId);
				if(EducationTrainTypeEnum.TYPE1.getCode().equals(entity.getType().toString())) {
					operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINHEADQUARTERS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				} else if(EducationTrainTypeEnum.TYPE2.getCode().equals(entity.getType().toString())) {
					operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.EDUCATIONTRAINRODUCTIONUNIT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
				}
			}
		}
		return resultObj;
	}
}