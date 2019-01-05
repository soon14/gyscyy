package com.aptech.business.managePlanContract.purchaseOrganization.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.business.managePlanContract.purchaseOrganization.service.PurchaseOrganizationService;
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

/**
 * 
 * 物资采购计划组织机构列表配置控制器
 *
 * @author 
 * @created 2018-07-25 17:08:06
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/purchaseOrganization")
public class PurchaseOrganizationController extends BaseController<PurchaseOrganizationEntity> {
	
	@Autowired
	private PurchaseOrganizationService purchaseOrganizationService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<PurchaseOrganizationEntity> getService() {
		return purchaseOrganizationService;
	}
	
	/**
	 *	list页面跳转关联预算管理
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
		return this.createModelAndView("managePlanContract/purchaseOrganization/purchaseOrganizationList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView listPurchase(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();

		//单位下拉框
		ComboboxVO comboboxVO = new ComboboxVO();
//		List<SafeCheckUnitEntity> unitList = this.safeCheckUnitService.findAll();
		
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
		return this.createModelAndView("managePlanContract/purchaseOrganization/purchaseList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<PurchaseOrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("purchaseOrganizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboPurchaseOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("purchaseOrganizationCombobox", JsonUtil.toJson(comboPurchaseOrganizationVO.getOptions()));
		
       //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
		return this.createModelAndView("managePlanContract/purchaseOrganization/purchaseOrganizationAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PurchaseOrganizationEntity purchaseOrganizationEntity = (PurchaseOrganizationEntity)purchaseOrganizationService.findById(id);
		model.put("entity", purchaseOrganizationEntity);
		model.put("entityJson", JsonUtil.toJson(purchaseOrganizationEntity));
		
		List<PurchaseOrganizationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("purchaseOrganizationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboPurchaseOrganizationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("purchaseOrganizationCombobox", JsonUtil.toJson(comboPurchaseOrganizationVO.getOptions()));
		
        //获取登录人信息
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		model.put("userName", userEntity.getName());
		return this.createModelAndView("managePlanContract/purchaseOrganization/purchaseOrganizationEdit", model);
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