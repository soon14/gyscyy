package com.aptech.business.dutyOrder.web;

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

import com.aptech.business.dutyOrder.domain.DutyOrderEntity;
import com.aptech.business.dutyOrder.service.DutyOrderService;
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
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 值次配置控制器
 *
 * @author 
 * @created 2017-09-13 17:23:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/dutyOrder")
public class DutyOrderController extends BaseController<DutyOrderEntity> {
	
	@Autowired
	private DutyOrderService dutyOrderService;
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
	public IBaseEntityOperation<DutyOrderEntity> getService() {
		return dutyOrderService;
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
		return this.createModelAndView("dutyOrder/dutyOrderList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//已存在编码
		List<DutyOrderEntity> DutyOrderEntities = dutyOrderService.findAll();
		List<String> codes = new ArrayList<String>();
		for (DutyOrderEntity dutyOrderEntity : DutyOrderEntities) {
			codes.add(dutyOrderEntity.getCode());
		}
		model.put("codes", JsonUtil.toJson(codes));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		List<Condition> conditions = new ArrayList<Condition>();
		//风电场值长、集控值班长职务ID
		Long[] leaderDutys = new Long[2];
		leaderDutys[0] = new Long(11);
		leaderDutys[1] = new Long(87);
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,leaderDutys));
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

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));

		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		//风电场值班员、集控值班员
  		Long[] dutys = new Long[2];
  		dutys[0] = new Long(34);
  		dutys[1] = new Long(88);
  		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.IN,dutys));//值班员
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
		List<SysUserEntity> userListBox2=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO2 = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox2){
  			combouserVO2.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox2", JsonUtil.toJson(combouserVO2.getOptions()));
		return this.createModelAndView("dutyOrder/dutyOrderAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		DutyOrderEntity dutyOrderEntity = (DutyOrderEntity)dutyOrderService.findById(id);
		//已存在编码
		List<DutyOrderEntity> DutyOrderEntities = dutyOrderService.findAll();
		List<String> codes = new ArrayList<String>();
		for (DutyOrderEntity dutyOrder : DutyOrderEntities) {
			if(!dutyOrder.getCode().equals(dutyOrderEntity.getCode())){
				codes.add(dutyOrder.getCode());
			}
		}
		model.put("codes", JsonUtil.toJson(codes));
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		model.put("entity", dutyOrderEntity);
		model.put("entityJson", JsonUtil.toJson(dutyOrderEntity));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,87));
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

		conditions.clear();
		conditions.add(new Condition("a.C_ID", FieldTypeEnum.STRING,MatchTypeEnum.IN, userStr.toArray()));

		List<SysUserEntity> userListBox=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox){
  			combouserVO.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox", JsonUtil.toJson(combouserVO.getOptions()));
  		//组员
  		conditions.clear();
		conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,88));
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

		List<SysUserEntity> userListBox2=sysUserService.findByCondition(conditions, null);
		ComboboxVO combouserVO2 = new ComboboxVO();
  		for(SysUserEntity sysUserEntity : userListBox2){
  			combouserVO2.addOption(sysUserEntity.getId().toString(), sysUserEntity.getName());
  	    }
  		model.put("userListBox2", JsonUtil.toJson(combouserVO2.getOptions()));
		return this.createModelAndView("dutyOrder/dutyOrderEdit", model);
	}
}