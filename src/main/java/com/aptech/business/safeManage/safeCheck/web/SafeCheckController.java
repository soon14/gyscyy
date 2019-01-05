package com.aptech.business.safeManage.safeCheck.web;

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

import com.aptech.business.component.dictionary.SafeCheckHeadTypeEnum;
import com.aptech.business.component.dictionary.SafeCheckUnitTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.safeCheck.domain.SafeCheckEntity;
import com.aptech.business.safeManage.safeCheck.service.SafeCheckService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全检查配置控制器
 *
 * @author 
 * @created 2018-03-28 10:22:17
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeCheck")
public class SafeCheckController extends BaseController<SafeCheckEntity> {
	
	@Autowired
	private SafeCheckService safeCheckService;
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SafeCheckEntity> getService() {
		return safeCheckService;
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
		//上级检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(SafeCheckHeadTypeEnum key : SafeCheckHeadTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("safeCheckTypeEnumCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		//生产单位类型枚举
		ComboboxVO safeCheckUnitTypeEnumCombobox = new ComboboxVO();
		for(SafeCheckUnitTypeEnum key : SafeCheckUnitTypeEnum.values()){
			safeCheckUnitTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("safeCheckUnitTypeEnumCombobox", JsonUtil.toJson(safeCheckUnitTypeEnumCombobox.getOptions()));
		// 部门
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_LEVEL = 0 OR C_LEVEL = 2 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 6 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
//		companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		if(Integer.valueOf(type.toString())==1){
			return this.createModelAndView("safeManage/safeCheck/safeCheckHeadList", model);
		}else if(Integer.valueOf(type.toString())==2){
			return this.createModelAndView("safeManage/safeCheck/safeCheckUnitList", model);
		}else{
			return this.createModelAndView("safeManage/safeCheck/safeCheckRectifyList", model);
		}
		
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{type}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long type){
		Map<String, Object> model = new HashMap<String, Object>();
		 //单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 AND C_LEVEL = 2 OR C_LEVEL = 0 OR C_LEVEL = 3 "));
//		companyConditions.add(new Condition(" C_LEVEL = 2 OR C_LEVEL = 0 OR C_LEVEL = 3"));
//		companyConditions.add(new Condition("level",FieldTypeEnum.STRING,MatchTypeEnum.EQ,2+" OR C_LEVEL = "+0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//安全检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(SafeCheckHeadTypeEnum key : SafeCheckHeadTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("safeCheckTypeEnumCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		
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
		
		//获取登录人
//		SysUserEntity sysUserEntity = RequestContext.get().getUser();
//		model.put("sysUserEntity", sysUserEntity);

		if(Integer.valueOf(type.toString())==1){
			return this.createModelAndView("safeManage/safeCheck/safeCheckHeadAdd", model);
		}else if(Integer.valueOf(type.toString())==2){
			return this.createModelAndView("safeManage/safeCheck/safeCheckUnitAdd", model);
		}else{
			return this.createModelAndView("safeManage/safeCheck/safeCheckRectifyAdd", model);
		}
	}
	
	/**
	 *	新增
	 */
	@RequestMapping("/add/{submenu}")
	public @ResponseBody ResultObj  addHead(HttpServletRequest request,@RequestBody SafeCheckEntity safeCheckEntity,@PathVariable String submenu){
		safeCheckService.addEntity(safeCheckEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKHIGHUPCHECK.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPRODUCTIONUNIT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPROBLEMANDABARBEITUNGINFO.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(safeCheckEntity);
		return resultObj;
	}
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{type}/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,@PathVariable Long type, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeCheckEntity safeCheckEntity = (SafeCheckEntity)safeCheckService.findById(id);
		model.put("entity", safeCheckEntity);
		model.put("entityJson", JsonUtil.toJson(safeCheckEntity));
		
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 AND C_LEVEL = 2 OR C_LEVEL = 0 OR C_LEVEL = 3 "));	
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//安全检查类型枚举
		ComboboxVO safeCheckTypeEnumCombobox = new ComboboxVO();
		for(SafeCheckHeadTypeEnum key : SafeCheckHeadTypeEnum.values()){
			safeCheckTypeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("safeCheckTypeEnumCombobox", JsonUtil.toJson(safeCheckTypeEnumCombobox.getOptions()));
		
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
		
//        //获取登录人信息
 		SysUserEntity sysUserEntity= sysUserService.findById(Long.parseLong(safeCheckEntity.getCreatePeopleId()));
 		model.put("sysUserEntity", sysUserEntity);	
				
		if(Integer.valueOf(type.toString())==1){
			return this.createModelAndView("safeManage/safeCheck/safeCheckHeadEdit", model);
		}else if(Integer.valueOf(type.toString())==2){
			return this.createModelAndView("safeManage/safeCheck/safeCheckUnitEdit", model);
		}else{
			return this.createModelAndView("safeManage/safeCheck/safeCheckRectifyEdit", model);
		}
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getshowPage/{type}/{id}")
	public ModelAndView getShowPage(HttpServletRequest request,@PathVariable Long type, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeCheckEntity safeCheckEntity = (SafeCheckEntity)safeCheckService.findById(id);
		model.put("entity", safeCheckEntity);
		model.put("entityJson", JsonUtil.toJson(safeCheckEntity));
		//生产单位回显
		if(safeCheckEntity.getUnitId()!=null){
			model.put("sysUnitEntity", sysUnitService.findById(Long.valueOf(safeCheckEntity.getUnitId())));
		}
		
	    //获取登录人信息
			SysUserEntity userEntity= sysUserService.findById(Long.parseLong(safeCheckEntity.getCreatePeopleId()));
			model.put("userEntity", userEntity);	
			
		if(Integer.valueOf(type.toString())==1){
			return this.createModelAndView("safeManage/safeCheck/safeCheckHeadDetail", model);
		}else if(Integer.valueOf(type.toString())==2){
			return this.createModelAndView("safeManage/safeCheck/safeCheckUnitDetail", model);
		}else{
			return this.createModelAndView("safeManage/safeCheck/safeCheckRectifyDetail", model);
		}
	}
	/**
	* 修改保存
	* @author ly
	* @date 2018年3月20日 上午9:32:04 
	* @param trainPlanEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/saveEditPage/{submenu}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody SafeCheckEntity safeCheckEntity,@PathVariable String submenu){
		ResultObj resultObj = new ResultObj();
		SafeCheckEntity odlSafeCheckEntity  = safeCheckService.findById(safeCheckEntity.getId());
		safeCheckEntity.setCreateDate(odlSafeCheckEntity.getCreateDate());
		safeCheckService.updateEntity(safeCheckEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKHIGHUPCHECK.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPRODUCTIONUNIT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPROBLEMANDABARBEITUNGINFO.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}
		return resultObj;
	}
	/**
	* 导出功能
	* @author ly
	* @date 2018年3月19日 下午5:57:30 
	* @param req
	* @param res
	* @param params
	* @throws UnsupportedEncodingException
	* @return void
	*/
	@RequestMapping("/exportExcel/{type}")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params,@PathVariable Long type) throws UnsupportedEncodingException{
		
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
 		List<SafeCheckEntity> dataList=safeCheckService.findByCondition(params, null);
 		int i= 1;
 		for(SafeCheckEntity safeCheckEnity: dataList){
 			safeCheckEnity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "安全检查上级检查报表模板.xlsx","安全检查上级检查报表.xlsx", resultMap);
		}else if(Integer.valueOf(type.toString())==2){
			ExcelUtil.export(req, res, "安全检查生产单位报表模板.xlsx","安全检查生产单位报表.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "安全检查存在问题及整改情况报表模板.xlsx","安全检查存在问题及整改情况报表.xlsx", resultMap);
		}
	}
	
	/**
	 * 删除
	 * @Title: delete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}/{submenu}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj delete(@PathVariable Long id,@PathVariable String submenu) {
		operateLogService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKHIGHUPCHECK.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPROBLEMANDABARBEITUNGINFO.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 批量删除
	 * 
	 * @Title: bulkDelete
	 * @Description:
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/bulkDelete/{submenu}", method = RequestMethod.DELETE)
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids,@PathVariable String submenu) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Integer id : ids) {
			long longId = (long)id;
			SafeCheckEntity safeCheckEntity = this.getService().findById(longId);
			if(safeCheckEntity != null){
				this.getService().deleteEntity(longId);
			}
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKHIGHUPCHECK.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SAFECHECKPROBLEMANDABARBEITUNGINFO.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
}