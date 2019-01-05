package com.aptech.business.orgaApp.web;


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

import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
 * 班组配置控制器
 *
 * @author 
 * @created 2017-09-14 09:37:02
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/orgaApp")
public class OrgaAppController extends BaseController<OrgaAppEntity> {
	
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Override
	public IBaseEntityOperation<OrgaAppEntity> getService() {
		return orgaAppService;
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
		List<OrgaAppEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("orgaAppTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOrgaAppVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("orgaAppCombobox", JsonUtil.toJson(comboOrgaAppVO.getOptions()));
		return this.createModelAndView("orgaApp/orgaAppList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//已存在编码
		List<OrgaAppEntity> OrgaAppEntities =orgaAppService.findAll();
		List<String> codes = new ArrayList<String>();
		for (OrgaAppEntity orgaAppEntity : OrgaAppEntities) {
			codes.add(orgaAppEntity.getCode());
		}
		model.put("codes", JsonUtil.toJson(codes));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,67));
 		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr.add(tempuserUnitRel.getUserId().toString());
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));

		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		//组员
  		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,68));
 		List<SysDutiesDetailEntity> sysDutiesDetailList2 = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr2 = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList2) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr2.add(tempuserUnitRel.getUserId().toString());
			}
		}

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr2.toArray()));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));

		List<SysUserEntity> userListBox2=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO2 = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox2){
  			combouserVO2.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox2", JsonUtil.toJson(combouserVO2.getOptions()));
		
		return this.createModelAndView("orgaApp/orgaAppAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		OrgaAppEntity orgaAppEntity = (OrgaAppEntity)orgaAppService.findById(id);
		//已存在编码
		List<OrgaAppEntity> OrgaAppEntities =orgaAppService.findAll();
		List<String> codes = new ArrayList<String>();
		for (OrgaAppEntity orgaApp : OrgaAppEntities) {
			if(!orgaApp.getCode().equals(orgaAppEntity.getCode())){
				codes.add(orgaApp.getCode());
			}
		}
		model.put("codes", JsonUtil.toJson(codes));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 返回前台数据项
		model.put("entity", orgaAppEntity);
		model.put("entityJson", JsonUtil.toJson(orgaAppEntity));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,14));
 		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr.add(tempuserUnitRel.getUserId().toString());
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));

		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		//组员
  		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,15));
 		List<SysDutiesDetailEntity> sysDutiesDetailList2 = sysDutiesDetailService.findByCondition(conditions, null);
		List<String> userStr2 = new ArrayList<String>();
		for (SysDutiesDetailEntity usersp : sysDutiesDetailList2) {
			List<Condition> condition = new ArrayList<Condition>();
			Long userUnitid = Long.parseLong(usersp.getUserUnitRelId());
			condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
			List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
			for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
				userStr2.add(tempuserUnitRel.getUserId().toString());
			}
		}

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr2.toArray()));
//		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING,MatchTypeEnum.EQ, userEntity.getUnitId()));

		List<SysUserEntity> userListBox2=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO2 = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox2){
  			combouserVO2.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox2", JsonUtil.toJson(combouserVO2.getOptions()));
		
		return this.createModelAndView("orgaApp/orgaAppEdit", model);
	}
}