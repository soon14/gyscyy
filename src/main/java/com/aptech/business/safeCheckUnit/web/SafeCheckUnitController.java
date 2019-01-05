package com.aptech.business.safeCheckUnit.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.safeCheckUnit.domain.SafeCheckUnitEntity;
import com.aptech.business.safeCheckUnit.service.SafeCheckUnitService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全检查组织机构表配置控制器
 *
 * @author 
 * @created 2018-09-03 10:56:03
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/safeCheckUnit")
public class SafeCheckUnitController extends BaseController<SafeCheckUnitEntity> {
	
	@Autowired
	private SafeCheckUnitService safeCheckUnitService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<SafeCheckUnitEntity> getService() {
		return safeCheckUnitService;
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

		//单位下拉框
		ComboboxVO comboboxVO = new ComboboxVO();
//		List<SafeCheckUnitEntity> unitList = this.safeCheckUnitService.findAll();
//		Page<SysUnitEntity> page = PageUtil.getPage(params);
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
		return this.createModelAndView("/safeCheck/unit/safeCheckUnitList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();

		//组织机构
		List<Condition> conditions = new ArrayList<Condition>();
		String [] organizationIds = {"1", "3", "4", "5", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
		
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		//单位列表
		ComboboxVO company = new ComboboxVO();
		if (unitList != null && unitList.size() > 0) {
			for (SysUnitEntity unitEntiy : unitList) {
				company.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("unitList", JsonUtil.toJson(company.getOptions()));

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginUser", sysUserEntity);
		
		return this.createModelAndView("/safeCheck/unit/safeCheckUnitAdd", model);
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
	public @ResponseBody ResultObj addEntity(@RequestBody SafeCheckUnitEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());
		this.safeCheckUnitService.addEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SafeCheckUnitEntity safeCheckUnitEntity = safeCheckUnitService.findById(id);
		
		model.put("entity", safeCheckUnitEntity);
		model.put("entityJson", JsonUtil.toJson(safeCheckUnitEntity));
		
		return this.createModelAndView("/safeCheck/unit/safeCheckUnitEdit", model);
	}
	
	/**
	 * 修改保存
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editEntity")
	public @ResponseBody
	ResultObj update(@RequestBody SafeCheckUnitEntity entity, HttpServletRequest request) {
		
		entity.setUpdateDate(new Date());

		safeCheckUnitService.updateEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
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
		safeCheckUnitService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
			SafeCheckUnitEntity entity = safeCheckUnitService.findById(longId);
			if (entity != null) {
				safeCheckUnitService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
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