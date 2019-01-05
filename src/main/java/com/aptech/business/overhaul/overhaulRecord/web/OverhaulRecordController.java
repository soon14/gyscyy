package com.aptech.business.overhaul.overhaulRecord.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.business.overhaul.overhaulRecord.service.OverhaulRecordService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 设备检修记录配置控制器
 *
 * @author 
 * @created 2018-06-05 11:23:18
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulRecord")
public class OverhaulRecordController extends BaseController<OverhaulRecordEntity> {
	
	@Autowired
	private OverhaulRecordService overhaulRecordService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public IBaseEntityOperation<OverhaulRecordEntity> getService() {
		return overhaulRecordService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/getDataList")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
		ComboboxVO comboOverhaulRecordVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulRecordCombobox", JsonUtil.toJson(comboOverhaulRecordVO.getOptions()));
		
		// 人员
 		ComboboxVO searchuser = new ComboboxVO();
 		List<Condition> conditions = new ArrayList<Condition>();
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(
 				conditions, null);
 		
 		for (SysUserEntity user : userList) {
 			searchuser.addOption(user.getId().toString(), user.getName());
 		}
 		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
      //获取登录人信息
  		model.put("sysUserId", JsonUtil.toJson(userEntity.getId()));
  		model.put("loginName", JsonUtil.toJson(userEntity.getLoginName()));
		return this.createModelAndView("overhaul/overhaulRecord/overhaulRecordList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
        }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	    //设备属地下拉框
	    companyConditions.clear();
	    companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
	    companyConditions.add(new Condition(" C_ORGANIZATION = 1"));
	    List<SysUnitEntity> localList = sysUnitService.findByCondition(companyConditions, null);
	    ComboboxVO localVo = new ComboboxVO();
	    
	    for(SysUnitEntity sysUnitEntity : localList){
	    	localVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
	    }
	    model.put("localList", JsonUtil.toJson(localVo.getOptions()));
     
	 // 人员
 		ComboboxVO searchuser = new ComboboxVO();
 		List<Condition> conditions = new ArrayList<Condition>();
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(
 				conditions, null);
 		
 		for (SysUserEntity user : userList) {
 			searchuser.addOption(user.getId().toString(), user.getName());
 		}
 		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
 		model.put("userEntity", userEntity);
 		
        DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH");
		model.put("yeardate", df.format(new Date()));
		return this.createModelAndView("overhaul/overhaulRecord/overhaulRecordAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OverhaulRecordEntity overhaulRecordEntity = (OverhaulRecordEntity)overhaulRecordService.findById(id);
		model.put("entity", overhaulRecordEntity);
		model.put("entityJson", JsonUtil.toJson(overhaulRecordEntity));
		
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		ComboboxVO companyVo = new ComboboxVO();
		
		for(SysUnitEntity sysUnitEntity : unitList){
			companyVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
       }
	    model.put("unitList", JsonUtil.toJson(companyVo.getOptions()));
	    //设备属地下拉框
	    companyConditions.clear();
	    companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
	    companyConditions.add(new Condition(" C_ORGANIZATION = 1"));
	    List<SysUnitEntity> localList = sysUnitService.findByCondition(companyConditions, null);
	    ComboboxVO localVo = new ComboboxVO();
	    
	    for(SysUnitEntity sysUnitEntity : localList){
	    	localVo.addOption(sysUnitEntity.getId().toString(), sysUnitEntity.getName());
	    }
	    model.put("localList", JsonUtil.toJson(localVo.getOptions()));
	 // 人员
 		ComboboxVO searchuser = new ComboboxVO();
 		List<Condition> conditions = new ArrayList<Condition>();
 		SysUserEntity userEntity= RequestContext.get().getUser();
 		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(
 				conditions, null);
 		
 		for (SysUserEntity user : userList) {
 			searchuser.addOption(user.getId().toString(), user.getName());
 		}
 		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
 		model.put("userEntity", userEntity);
		return this.createModelAndView("overhaul/overhaulRecord/overhaulRecordEdit", model);
	}
	
	/**
	 * 跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		OverhaulRecordEntity overhaulRecordEntity = (OverhaulRecordEntity)overhaulRecordService.findById(id);
		model.put("entity", overhaulRecordEntity);
	      //获取登录人信息
		SysUserEntity userEntity= sysUserService.findById(Long.parseLong(overhaulRecordEntity.getDutyUserId().toString()));
		model.put("userName", userEntity.getName());
		SysUnitEntity unitEntity = sysUnitService.findById(Long.parseLong(overhaulRecordEntity.getUnitId().toString()));
		model.put("unitName", unitEntity.getName());
		if (overhaulRecordEntity.getEquipLocal()!=null) {
			SysUnitEntity localEntity = sysUnitService.findById(Long.parseLong(overhaulRecordEntity.getEquipLocal().toString()));
			model.put("localName", localEntity.getName());
		}
		model.put("orgnaizationId", id);
		return this.createModelAndView("overhaul/overhaulRecord/overhaulRecordDetail", model);
	}
}