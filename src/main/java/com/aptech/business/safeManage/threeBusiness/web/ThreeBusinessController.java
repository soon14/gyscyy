package com.aptech.business.safeManage.threeBusiness.web;

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

import com.aptech.business.component.dictionary.ThreeBusCategoryTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeManage.infoSubmit.domain.InfoSubmitEntity;
import com.aptech.business.safeManage.threeBusiness.domain.ThreeBusinessEntity;
import com.aptech.business.safeManage.threeBusiness.service.ThreeBusinessService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
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
 * 三项业务配置控制器
 *
 * @author 
 * @created 2018-04-03 13:17:33
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/threeBusiness")
public class ThreeBusinessController extends BaseController<ThreeBusinessEntity> {
	
	@Autowired
	private ThreeBusinessService threeBusinessService;
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
	public IBaseEntityOperation<ThreeBusinessEntity> getService() {
		return threeBusinessService;
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
		
		//类别
		ComboboxVO typeEnumCombobox = new ComboboxVO();
		for(ThreeBusCategoryTypeEnum key : ThreeBusCategoryTypeEnum.values()){
			typeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(typeEnumCombobox.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/threeBusiness/threeBusinessList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//类别
		ComboboxVO typeEnumCombobox = new ComboboxVO();
		for(ThreeBusCategoryTypeEnum key : ThreeBusCategoryTypeEnum.values()){
			typeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(typeEnumCombobox.getOptions()));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 2));
		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 1));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName());		
		return this.createModelAndView("safeManage/threeBusiness/threeBusinessAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ThreeBusinessEntity threeBusinessEntity = (ThreeBusinessEntity)threeBusinessService.findById(id);
		model.put("entity", threeBusinessEntity);
		model.put("entityJson", JsonUtil.toJson(threeBusinessEntity));
		// 部门
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 2));
		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, 1));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
        model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		//类别
		ComboboxVO typeEnumCombobox = new ComboboxVO();
		for(ThreeBusCategoryTypeEnum key : ThreeBusCategoryTypeEnum.values()){
			typeEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("categoryCombobox", JsonUtil.toJson(typeEnumCombobox.getOptions()));
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(threeBusinessEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
		return this.createModelAndView("safeManage/threeBusiness/threeBusinessEdit", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		ThreeBusinessEntity targetEntity=threeBusinessService.findById(id);
		model.put("entity", targetEntity);
       //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(targetEntity.getCreateUserId()));
		model.put("userName", userEntity.getName());
	  //部门
		if(targetEntity.getUnitId()!=null&&targetEntity.getUnitId()!=""){
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(targetEntity.getUnitId()));
			model.put("unitName", sysUnitEntity.getName());
		}
		return this.createModelAndView("safeManage/threeBusiness/threeBusinessDetail", model);
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
		
		Page<ThreeBusinessEntity> page=new Page<ThreeBusinessEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("C_ID"));
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<ThreeBusinessEntity> dataList=threeBusinessService.findByCondition(conditions, page);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		
		ExcelUtil.export(req, res, "三项业务报表模板.xlsx","三项业务.xlsx", resultMap);
		
		
	}
	
	/**
	 * 修改保存
	 * @param t
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveBusinessPage/{id}")
	public @ResponseBody ResultObj saveTrainPage(@RequestBody ThreeBusinessEntity entity, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		threeBusinessService.updateEntity(entity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.THREETERMSBUSINESS.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
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
		
		ResultObj resultObj = new ResultObj();
		threeBusinessService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.THREETERMSBUSINESS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
			ThreeBusinessEntity entity = threeBusinessService.findById(longId);
			if (entity != null) {
				threeBusinessService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.THREETERMSBUSINESS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
}