package com.aptech.business.managePlanContract.purchaseType.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.managePlanContract.purchaseType.domain.PurchaseTypeEntity;
import com.aptech.business.managePlanContract.purchaseType.service.PurchaseTypeService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 采购类型配置控制器
 *
 * @author 
 * @created 2018-07-30 16:49:32
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/purchaseType")
public class PurchaseTypeController extends BaseController<PurchaseTypeEntity> {
	
	@Autowired
	private PurchaseTypeService purchaseTypeService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<PurchaseTypeEntity> getService() {
		return purchaseTypeService;
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
		List<PurchaseTypeEntity> unitList = purchaseTypeService.findAll();
		
	   for(PurchaseTypeEntity entity : unitList){
		   comboboxVO.addOption(entity.getId().toString(), entity.getName());
        }
        model.put("nameList", JsonUtil.toJson(comboboxVO.getOptions()));
        List<Condition> conditions = new ArrayList<Condition>();
        //获取登录人信息
  		SysUserEntity userEntity= RequestContext.get().getUser();
    	model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
  		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
  		model.put("userName", userEntity.getName()); 
  		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginId", sysUserEntity.getId().toString());
		model.put("loginName", sysUserEntity.getLoginName());
		conditions.add(new Condition("C_USER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
		List<Long> userUnitIds = new ArrayList<Long>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			userUnitIds.add(userUnitRelEntity.getId());
		}
		conditions.clear();
		conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN, userUnitIds.toArray()));
		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> dutiesIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
			dutiesIds.add(sysDutiesDetailEntity.getDutiesId());
		}
		conditions.clear();
		conditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, dutiesIds.toArray()));
		List<SysDutiesEntity> sysDutiesList = sysDutiesService.findByCondition(conditions, null);
		if(!sysDutiesList.isEmpty()){
			for(SysDutiesEntity sysDutiesEntity : sysDutiesList){
				if(sysDutiesEntity.getName().equals("计划经营处负责人")){ 
					model.put("answerht", "1");//1有权限2没有权限
					break;
				}else{
					model.put("answerht", "2");
				}
			}
		}else{
			model.put("answerht", "2");
		}
		return this.createModelAndView("managePlanContract/purchaseType/purchaseTypeList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<PurchaseTypeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("purchaseTypeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboPurchaseTypeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("purchaseTypeCombobox", JsonUtil.toJson(comboPurchaseTypeVO.getOptions()));
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName()); 
		
		return this.createModelAndView("managePlanContract/purchaseType/purchaseTypeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		PurchaseTypeEntity purchaseTypeEntity = (PurchaseTypeEntity)purchaseTypeService.findById(id);
		model.put("entity", purchaseTypeEntity);
		model.put("entityJson", JsonUtil.toJson(purchaseTypeEntity));
		
		List<PurchaseTypeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("purchaseTypeTreeList", JsonUtil.toJson(treeNodeList));
		//获取登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		model.put("userName", userEntity.getName()); 
		
		return this.createModelAndView("managePlanContract/purchaseType/purchaseTypeEdit", model);
	}
}