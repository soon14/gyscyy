package com.aptech.business.safeManage.upperFile.web;

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

import com.aptech.business.component.dictionary.UpperFileTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.upperFile.domain.UpperFileEntity;
import com.aptech.business.safeManage.upperFile.service.UpperFileService;
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
 * 上级文件配置控制器
 *
 * @author 
 * @created 2018-03-28 10:18:07
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/upperFile")
public class UpperFileController extends BaseController<UpperFileEntity> {
	
	@Autowired
	private UpperFileService upperFileService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<UpperFileEntity> getService() {
		return upperFileService;
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
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		
		model.put("type", type);
		for (UpperFileTypeEnum standardTypeEnum : UpperFileTypeEnum.values()) {
			if (standardTypeEnum.getCode().equals(type.toString())) {
				model.put("targetType", standardTypeEnum.getName());
			}
		}
		 //单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
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
 		if(type==1){
 			return this.createModelAndView("safeManage/upperFile/upperFileList", model);
 		}else if(type==2){
 			return this.createModelAndView("safeManage/upperFile/upperFileListUnit", model);
 		}else{
 			return this.createModelAndView("safeManage/upperFile/upperFileListJianGuan", model);
 		}
		
	}
	
	/**
	 *	跳转到添加页面(集团公司)
	 */
	@RequestMapping("/getAddForGroupCompany")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/upperFile/upperFileAddGroupCompany", model);
	}
	
	/**
	 * 新增(集团公司、贵阳院)
	 * @param request
	 * @return
	 */
	@RequestMapping("/add/{submenu}")
	public @ResponseBody ResultObj  add(HttpServletRequest request,@RequestBody UpperFileEntity upperFileEntity,@PathVariable String submenu){
		upperFileService.addEntity(upperFileEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGROUPCOMPANY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}else{
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGUIYANGYUAN.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setData(upperFileEntity);
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面(贵阳院)
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAdd(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		 //单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/upperFile/upperFileAdd", model);
	}
	
	
	
	
	/**
	 *	跳转到监管单位添加页面
	 */
	@RequestMapping("/getAddJianGuan")
	public ModelAndView getAddJianGuan(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/upperFile/upperFileAddJianGuan", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		UpperFileEntity upperFileEntity = (UpperFileEntity)upperFileService.findById(id);
		model.put("entity", upperFileEntity);
		model.put("entityJson", JsonUtil.toJson(upperFileEntity));
		
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		 //单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
        //获取登录人信息
 		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(upperFileEntity.getCreateUserId()));
 		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/upperFile/upperFileEdit", model);
	}
	/**
	 *	跳转到监管单位修改页面
	 */
	@RequestMapping("/getEditJianGuan/{id}")
	public ModelAndView getEditJianGuan(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		UpperFileEntity upperFileEntity = (UpperFileEntity)upperFileService.findById(id);
		model.put("entity", upperFileEntity);
		model.put("entityJson", JsonUtil.toJson(upperFileEntity));
		
		List<UpperFileEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("upperFileTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUpperFileVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("upperFileCombobox", JsonUtil.toJson(comboUpperFileVO.getOptions()));
		//单位下拉列表
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("parentId",FieldTypeEnum.INT,MatchTypeEnum.EQ,0));
		companyConditions.add(new Condition("C_LEVEL",FieldTypeEnum.INT,MatchTypeEnum.EQ,7));
		companyConditions.add(new Condition("C_ORGANIZATION = 8 OR C_ORGANIZATION = 12"));
		companyConditions.add(new Condition("status", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
		}
		model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(upperFileEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/upperFile/upperFileEditJianGuan", model);
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fileUpdate/{id}/{submenu}")
	public @ResponseBody
	ResultObj update(@RequestBody UpperFileEntity t, HttpServletRequest request,@PathVariable Long id,@PathVariable String submenu) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGROUPCOMPANY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}else{
		}
		ResultObj resultObj = new ResultObj();
		UpperFileEntity upperFileEntity = upperFileService.findById(id);
		t.setCreateDate(upperFileEntity.getCreateDate());
		upperFileService.updateEntity(t);	
		return resultObj;
	}
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/fileUpdateJianGuan/{id}")
	public @ResponseBody
	ResultObj fileUpdateJianGuan(@RequestBody UpperFileEntity t, HttpServletRequest request) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setUnitName(sysUnitService.findById(t.getUnitId()).getName());
		upperFileService.updateEntity(t);	
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILESUPERVISEUNIT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
		
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
		UpperFileEntity t = upperFileService.findById(id);
		if (t != null) {
			t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			upperFileService.updateEntity(t);
		
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGROUPCOMPANY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGUIYANGYUAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("3")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILESUPERVISEUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @author 
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping(value = "/bulkDelete/{submenu}")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<Integer> ids,@PathVariable String submenu) {
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Integer id : ids) {
			long longId = (long) id;
			UpperFileEntity t = upperFileService.findById(longId);
			if (t != null) {
				t.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
				upperFileService.updateEntity(t);
			}
		}
		if(submenu.equals("1")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGROUPCOMPANY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("2")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILEGUIYANGYUAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}else if(submenu.equals("3")){
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILESUPERVISEUNIT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
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
		Page<UpperFileEntity> page=new Page<UpperFileEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<UpperFileEntity> dataList=upperFileService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		if(Integer.valueOf(type.toString())==1){
			ExcelUtil.export(req, res, "上级文件集团公司报表模板.xlsx","上级文件集团公司.xlsx", resultMap);
		}else if(Integer.valueOf(type.toString())==2){
			ExcelUtil.export(req, res, "上级文件贵阳院报表模板.xlsx","上级文件贵阳院.xlsx", resultMap);
		}else{
			ExcelUtil.export(req, res, "上级文件监管单位报表模板.xlsx","上级文件监管单位.xlsx", resultMap);
		}
		
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		UpperFileEntity fileEntity=upperFileService.findById(id);
		model.put("entity", fileEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(fileEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/upperFile/upperFileDetail", model);
	}
	
	/**
	 * 跳转到监管单位查看页面
	 */
	@RequestMapping("/getDetailJianGuan/{id}")
	public ModelAndView getDetailJianGuan(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		UpperFileEntity fileEntity=upperFileService.findById(id);
		model.put("entity", fileEntity);
		//获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(fileEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());	
		return this.createModelAndView("safeManage/upperFile/upperFileDetailJianGuan", model);
	}
/**
	 * @Description:   自己写的新增方法
	 * @author         huoxy
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/addJianGuan")
	public @ResponseBody ResultObj addJianGuan(@RequestBody UpperFileEntity upperFileEntity, HttpServletRequest request ) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		upperFileEntity.setCreateDate(new Date());
		upperFileEntity.setCreateUserId(userEntity.getId().toString());
		ResultObj resultObj = upperFileService.add(upperFileEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPERIORFILESUPERVISEUNIT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
}