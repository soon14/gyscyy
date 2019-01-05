package com.aptech.business.overhaul.equipOverhaulOrganization.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.overhaul.equipOverhaulOrganization.domain.EquipOverhaulOrganizationEntity;
import com.aptech.business.overhaul.equipOverhaulOrganization.service.EquipOverhaulOrganizationService;
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
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备检修纪录组织机构配置控制器
 *
 * @author 
 * @created 2018-08-21 11:18:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/equipOverhaulOrganization")
public class EquipOverhaulOrganizationController extends BaseController<EquipOverhaulOrganizationEntity> {
	
	@Autowired
	private EquipOverhaulOrganizationService equipOverhaulOrganizationService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<EquipOverhaulOrganizationEntity> getService() {
		return equipOverhaulOrganizationService;
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
        //组织机构
  		List<Condition> conditions = new ArrayList<Condition>();
  		String [] organizationIds = {"1", "3", "4", "7"};
  		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
  		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
  		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
  		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
  		//单位下拉框
		ComboboxVO comboboxVO = new ComboboxVO();
  		for(SysUnitEntity entity : unitList){
  			comboboxVO.addOption(entity.getId().toString(), entity.getName());
  		}
  		model.put("unitList", JsonUtil.toJson(comboboxVO.getOptions()));
  	    //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userUnitId", userEntity.getUnitId());
		return this.createModelAndView("overhaul/equipOverhaulOrganization/equipOverhaulOrganizationList", model);
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/getDataList")
	public @ResponseBody ResultListObj getDataList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		//组织机构
		Page<SysUnitEntity> pages = PageUtil.getPage(params);
		pages.setOrders(OrmUtil.changeMapToOrders(params));
		pages.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		String [] organizationIds = {"1", "3", "4", "7"};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationIds));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(conditions, pages);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (unitList != null) {
			resultObj.setRecordsTotal((long) unitList.size());
			resultObj.setData(unitList);
		}
		return resultObj;
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipOverhaulOrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipOverhaulOrganizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipOverhaulOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipOverhaulOrganizationCombobox", JsonUtil.toJson(comboEquipOverhaulOrganizationVO.getOptions()));
		//获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
 		model.put("userId", userEntity.getId());
		return this.createModelAndView("overhaul/equipOverhaulOrganization/equipOverhaulOrganizationAdd", model);
	}
	
	/**
	 * @Description:   设备检修记录新增
	 * @author         wangcc 
	 * @param <T>
	 * @Date           2018年8月21日 下午15:00:00 
	 * @throws         Exception
	 */
	@RequestMapping("/equipOverhaulOrganizationAdd")
	public @ResponseBody <T> ResultObj equipOverhaulOrganizationAdd(@RequestBody EquipOverhaulOrganizationEntity overhaulLogEntity, HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		equipOverhaulOrganizationService.addEntity(overhaulLogEntity);
		return resultObj;
	}
	
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EquipOverhaulOrganizationEntity equipOverhaulOrganizationEntity = (EquipOverhaulOrganizationEntity)equipOverhaulOrganizationService.findById(id);
		model.put("entity", equipOverhaulOrganizationEntity);
		return this.createModelAndView("overhaul/equipOverhaulOrganization/equipOverhaulOrganizationEdit", model);
	}
}