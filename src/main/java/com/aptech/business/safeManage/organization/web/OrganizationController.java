package com.aptech.business.safeManage.organization.web;

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

import com.aptech.business.component.dictionary.OrganizationTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.organization.domain.OrganizationEntity;
import com.aptech.business.safeManage.organization.service.OrganizationService;
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
 * 组织机构配置控制器
 *
 * @author 
 * @created 2018-03-27 16:31:43
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController<OrganizationEntity> {
	
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<OrganizationEntity> getService() {
		return organizationService;
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
		List<OrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("organizationTreeList", JsonUtil.toJson(treeNodeList));
		model.put("type", type);
		for (OrganizationTypeEnum standardTypeEnum : OrganizationTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("targetType", standardTypeEnum.getName());
			}
		}
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		 //单位下拉列表
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
   		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
 		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
 		model.put("userName", userEntity.getName());   
 		if(type==1){
 			return this.createModelAndView("safeManage/organization/organizationList", model);
 		}else{
 			return this.createModelAndView("safeManage/organization/organizationListPro", model);
 		}
		
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("organizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("organizationCombobox", JsonUtil.toJson(comboOrganizationVO.getOptions()));
		
		 //单位下拉列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/organization/organizationAdd", model);
	}
	
	/**
	 * 新增(总部)
	 * @param request
	 * @param safeReportEntity
	 * @return
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody OrganizationEntity organizationEntity){
		organizationService.addEntity(organizationEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONHEADQUARTERS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(organizationEntity);
		return resultObj;
	}
	
	
	
	/**
	 *	跳转到生产单位添加页面
	 */
	@RequestMapping("/getAddPro")
	public ModelAndView getAddPro(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("organizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("organizationCombobox", JsonUtil.toJson(comboOrganizationVO.getOptions()));
		
		//单位下拉列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		conditions.add(new Condition("C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/organization/organizationAddPro", model);
	}
	
	/**
	 * 新增(生产单位)
	 * @param request
	 * @param safeReportEntity
	 * @return
	 */
	@RequestMapping("/addPro")
	public @ResponseBody ResultObj  addPro(HttpServletRequest request,@RequestBody OrganizationEntity organizationEntity){
		organizationService.addEntity(organizationEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONPRODUCTIONUNIT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(organizationEntity);
		return resultObj;
	}
	
	
	
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OrganizationEntity organizationEntity = (OrganizationEntity)organizationService.findById(id);
		model.put("entity", organizationEntity);
		model.put("entityJson", JsonUtil.toJson(organizationEntity));
		
		List<OrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("organizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("organizationCombobox", JsonUtil.toJson(comboOrganizationVO.getOptions()));
		
		 //单位下拉列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(organizationEntity.getCreateUserId()));
 		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/organization/organizationEdit", model);
	}
	
	
	
	/**
	 *	跳转到生产单位修改页面
	 */
	@RequestMapping("/getEditPro/{id}")
	public ModelAndView getEditPro(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OrganizationEntity organizationEntity = (OrganizationEntity)organizationService.findById(id);
		model.put("entity", organizationEntity);
		model.put("entityJson", JsonUtil.toJson(organizationEntity));
		
		List<OrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("organizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("organizationCombobox", JsonUtil.toJson(comboOrganizationVO.getOptions()));
		
		//单位下拉列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2 AND C_GENERATION_TYPE = 1 AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		conditions.add(new Condition("C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(organizationEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/organization/organizationEditPro", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		OrganizationEntity organizationEntity=organizationService.findById(id);
		model.put("entity", organizationEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(organizationEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/organization/organizationDetail", model);
	}
	/**
	 * 跳转到生产单位查看页面
	 */
	@RequestMapping("/getDetailPro/{id}")
	public ModelAndView getDetailPro(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		OrganizationEntity organizationEntity=organizationService.findById(id);
		model.put("entity", organizationEntity);
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(organizationEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/organization/organizationDetailPro", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/organizationEdit/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody OrganizationEntity t, HttpServletRequest request) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		organizationService.updateEntity(t);
//			JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setName(String.valueOf(josnObject.get("name")));
//				t.setFileId(array.toString());
//				
//			
//		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONHEADQUARTERS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		 return new ResultObj();
	}
	/**
	 * 修改生产单位保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/organizationEditpro/{id}")
	public @ResponseBody
	ResultObj organizationEditpro(@RequestBody OrganizationEntity t, HttpServletRequest request) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setUnitName(sysUnitService.findById(t.getUnitId()).getName());
		organizationService.updateEntity(t);
//			JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setName(String.valueOf(josnObject.get("name")));
//				t.setFileId(array.toString());
//				
//			
//		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONPRODUCTIONUNIT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
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
		OrganizationEntity t = organizationService.findById(id);
		if (t != null) {
			t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			organizationService.updateEntity(t);
		
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
	ResultObj allDelete(@RequestBody List<Integer> ids,@PathVariable String submenu) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Integer id : ids) {
			long longId = (long) id;
			OrganizationEntity t = organizationService.findById(longId);
			if (t != null) {
				t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
				organizationService.updateEntity(t);
			}
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.ORGANIZATIONPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
		
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, TargetManageTypeEnum.TYPE1.getCode()));
		Page<OrganizationEntity> page=new Page<OrganizationEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<OrganizationEntity> dataList=organizationService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "组织机构总部报表模板.xlsx","组织机构总部.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "组织机构生产单位报表模板.xlsx","组织机构生产单位.xlsx", resultMap);
		}
		
	}
	
}