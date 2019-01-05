package com.aptech.business.safeManage.targetManage.web;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.TargetManageTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.targetManage.domain.TargetManageEntity;
import com.aptech.business.safeManage.targetManage.service.TargetManageService;
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
 * 目标管理配置控制器
 *
 * @author 
 * @created 2018-03-23 14:32:07
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/targetManage")
public class TargetManageController extends BaseController<TargetManageEntity> {
	
	@Autowired
	private TargetManageService targetManageService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<TargetManageEntity> getService() {
		return targetManageService;
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
		List<TargetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		
		model.put("type", type);
		for (TargetManageTypeEnum standardTypeEnum : TargetManageTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("targetType", standardTypeEnum.getName());
			}
		}
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		 //单位下拉列表
		
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2  AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
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
 			return this.createModelAndView("safeManage/targetManage/targetManageList", model);
 		}else{
 			return this.createModelAndView("safeManage/targetManage/targetManageListPro", model);
 		}
		
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<TargetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		 //单位下拉列表
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2  AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/targetManage/targetManageAdd", model);
	}
	
	/**
	 * 添加(总部)
	 */
	@RequestMapping("/add")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody TargetManageEntity targetManageEntity){
		targetManageService.addEntity(targetManageEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEHEADQUARTERS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(targetManageEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到生产单位添加页面
	 */
	@RequestMapping("/getAddPro")
	public ModelAndView getAddPro(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<TargetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		//单位下拉列表
//		List<Condition> companyConditions = new ArrayList<Condition>();
//		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
//		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2  AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/targetManage/targetManageAddPro", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TargetManageEntity targetManageEntity = (TargetManageEntity)targetManageService.findById(id);
		model.put("entity", targetManageEntity);
		model.put("entityJson", JsonUtil.toJson(targetManageEntity));
		
		List<TargetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		 //单位下拉列表
//		List<Condition> companyConditions = new ArrayList<Condition>();
//		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
//		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
//		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2  AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
       model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetManageEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/targetManage/targetManageEdit", model);
	}
	
	
	/**
	 *	跳转到生产单位修改页面
	 */
	@RequestMapping("/getEditPro/{id}")
	public ModelAndView getEditPro(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TargetManageEntity targetManageEntity = (TargetManageEntity)targetManageService.findById(id);
		model.put("entity", targetManageEntity);
		model.put("entityJson", JsonUtil.toJson(targetManageEntity));
		
		List<TargetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("targetManageTreeList", JsonUtil.toJson(treeNodeList));
		//单位下拉列表
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL = 2  AND C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4 OR C_ORGANIZATION = 7"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetManageEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/targetManage/targetManageEditPro", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		TargetManageEntity targetEntity=targetManageService.findById(id);
		model.put("entity", targetEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/targetManage/targetManageDetail", model);
	}
	/**
	 * 跳转到生产单位查看页面
	 */
	@RequestMapping("/getDetailPro/{id}")
	public ModelAndView getDetailPro(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		TargetManageEntity targetEntity=targetManageService.findById(id);
		model.put("entity", targetEntity);
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/targetManage/targetManageDetailPro", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/targetEdit/{id}")
	public @ResponseBody
	ResultObj update(@RequestBody TargetManageEntity t, HttpServletRequest request) {
		
	
//			JSONArray jSONArray=JSONArray.fromObject(t.getFileId());
//			for (int i = 0; i < jSONArray.size(); i++) {
//				JSONArray array=new JSONArray();
//				JSONObject josnObject= (JSONObject) jSONArray.get(i);
//				array.add(jSONArray.get(i));
//				t.setName(String.valueOf(josnObject.get("name")));
//				t.setCode(IdUtil.creatUUID());
//		}
		
		TargetManageEntity tEntity=targetManageService.findById(t.getId());
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		tEntity.setUpdateDate(new Date());
		tEntity.setUpdateUserId(userEntity.getId().toString());
		tEntity.setName(t.getName());
		tEntity.setRemark(t.getRemark());
		tEntity.setFileId(t.getFileId());
		tEntity.setTargetDate(t.getTargetDate());
		tEntity.setSignDate(t.getSignDate());
//		tEntity.setSignUnitId(t.getSignUnitId());
//        SysUnitEntity sUnitEntity=sysUnitService.findById(tEntity.getSignUnitId());
//        tEntity.setSignUnitName(sUnitEntity.getName());
        
		targetManageService.updateEntity(tEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEHEADQUARTERS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	    return new ResultObj();
	}
	/**
	 * 修改保存生产单位
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/targetEditPro/{id}")
	public @ResponseBody
	ResultObj targetEditPro(@RequestBody TargetManageEntity t, HttpServletRequest request) {
		TargetManageEntity tEntity=targetManageService.findById(t.getId());
		SysUserEntity userEntity = RequestContext.get().getUser();
		tEntity.setUpdateDate(new Date());
		tEntity.setUpdateUserId(userEntity.getId().toString());
		tEntity.setName(t.getName());
		tEntity.setRemark(t.getRemark());
		tEntity.setFileId(t.getFileId());
		tEntity.setTargetDate(t.getTargetDate());
		tEntity.setSignDate(t.getSignDate());
		tEntity.setSignUnitId(t.getSignUnitId());
        SysUnitEntity sUnitEntity=sysUnitService.findById(tEntity.getSignUnitId());
        tEntity.setSignUnitName(sUnitEntity.getName());
		targetManageService.updateEntity(tEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEPRODUCTIONUNIT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
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
		TargetManageEntity t = targetManageService.findById(id);
		if (t != null) {
			t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			t.setUpdateDate(new Date());
			t.setUpdateUserId(userEntity.getId().toString());
			targetManageService.updateEntity(t);
		
		}
		if(submenu.equals("1")){//总部
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{//生产单位
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
			TargetManageEntity t = targetManageService.findById(longId);
			if (t != null) {
				t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
				t.setUpdateDate(new Date());
				t.setUpdateUserId(userEntity.getId().toString());
				targetManageService.updateEntity(t);
			}
		}
		if(submenu.equals("1")){//总部
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEHEADQUARTERS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else{//生产单位
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEPRODUCTIONUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
		
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, TargetManageTypeEnum.TYPE1.getCode()));
		Page<TargetManageEntity> page=new Page<TargetManageEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<TargetManageEntity> dataList=targetManageService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "目标管理总部报表模板.xlsx","目标管理总部.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "目标管理生产单位报表模板.xlsx","目标管理生产单位.xlsx", resultMap);
		}
		
	}
	/**
	 * @Description:   自己写的新增方法
	 * @author         huoxy
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addPro")
	public @ResponseBody ResultObj addPro(@RequestBody TargetManageEntity manageEntity, HttpServletRequest request ) {
		
		ResultObj resultObj = targetManageService.addPro(manageEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TARGETMANAGEPRODUCTIONUNIT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		resultObj.setData(manageEntity);
		return resultObj;
	}
}