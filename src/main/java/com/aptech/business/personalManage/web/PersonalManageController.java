package com.aptech.business.personalManage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.personalManage.domain.PersonalManageEntity;
import com.aptech.business.personalManage.service.PersonalManageService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.DataStatusEnum;
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
 * 人员管理配置控制器
 *
 * @author 
 * @created 2017-08-24 13:51:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/personalManage")
public class PersonalManageController extends BaseController<PersonalManageEntity> {
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private PersonalManageService personalManageService;
	
	@Override
	public IBaseEntityOperation<PersonalManageEntity> getService() {
		return personalManageService;
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
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitTreeList", JsonUtil.toJson(treeNodeList));
		//性别下拉框
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("CODE_SEX_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("sexCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//是否为外部人员下拉框
		Map<String, SysDictionaryVO> outStatusTypeMap  =  DictionaryUtil.getDictionaries("OUT_STATUS");
		ComboboxVO comboOutStatusTypeVO = new ComboboxVO();
		for(String key : outStatusTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outStatusTypeMap.get(key);
			comboOutStatusTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outStatusCombobox", JsonUtil.toJson(comboOutStatusTypeVO.getOptions()));
		return this.createModelAndView("personalManage/personalManageList", model);
	}
	
	public @ResponseBody ResultListObj getAddPage(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<PersonalManageEntity> pages = PageUtil.getPage(params);
		//pages.addOrder(Sort.desc("instockTime"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("status",FieldTypeEnum.STRING,MatchTypeEnum.EQ,'0'));
		List<PersonalManageEntity> personalManageEntities = personalManageService.findByCondition(conditions, pages);
		if(null != personalManageEntities && personalManageEntities.size()!=0){
			for(PersonalManageEntity personalManageEntity : personalManageEntities){
				if(personalManageEntity != null){
					String unitId = String.valueOf(personalManageEntity.getUnit());
					SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(unitId));
					personalManageEntity.setUnit(sysUnitEntity.getName());
				}
			}
		}
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (personalManageEntities != null) {
			if (pages != null) {
				resultObj.setData(personalManageEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//以后人员编号
		List<PersonalManageEntity> personalManageEntities = personalManageService.findAll();
		List<String> codes= new ArrayList<String>();
		for(PersonalManageEntity personalManageEntity:personalManageEntities){
			codes.add(personalManageEntity.getCode());
		}
		model.put("codes", JsonUtil.toJson(codes));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitTreeList", JsonUtil.toJson(treeNodeList));
		//性别下拉框
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("CODE_SEX_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("sexCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//是否为外部人员下拉框
		Map<String, SysDictionaryVO> outStatusTypeMap  =  DictionaryUtil.getDictionaries("OUT_STATUS");
		ComboboxVO comboOutStatusVO = new ComboboxVO();
		for(String key : outStatusTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outStatusTypeMap.get(key);
			comboOutStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outStatusCombobox", JsonUtil.toJson(comboOutStatusVO.getOptions()));
				
		//参建单位
		Map<String, SysDictionaryVO> constructionUnitsMap  =  DictionaryUtil.getDictionaries("CONSTRUCTION_UNITS");
		ComboboxVO constructionUnits = new ComboboxVO();
		for(String key : constructionUnitsMap.keySet()){
			SysDictionaryVO sysDictionaryVO = constructionUnitsMap.get(key);
			constructionUnits.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("constructionUnitsCombobox", JsonUtil.toJson(constructionUnits.getOptions()));				
		return this.createModelAndView("personalManage/personalManageAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		PersonalManageEntity personalManageEntity = (PersonalManageEntity)personalManageService.findById(id);
		//以后人员编号
		List<PersonalManageEntity> personalManageEntities = personalManageService.findAll();
		List<String> codes= new ArrayList<String>();
		for(PersonalManageEntity personalManage:personalManageEntities){
			if(!personalManage.getCode().equals(personalManageEntity.getCode())){
				codes.add(personalManage.getCode());
			}
		}
		model.put("codes", JsonUtil.toJson(codes));
		// 返回前台数据项
		model.put("entity", personalManageEntity);
		model.put("entityJson", JsonUtil.toJson(personalManageEntity));
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitTreeList", JsonUtil.toJson(treeNodeList));
		//性别下拉
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("CODE_SEX_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("sexCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//是否为外部人员下拉框
		Map<String, SysDictionaryVO> outStatusTypeMap  =  DictionaryUtil.getDictionaries("OUT_STATUS");
		ComboboxVO comboOutStatusVO = new ComboboxVO();
		for(String key : outStatusTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outStatusTypeMap.get(key);
				comboOutStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
			model.put("outStatusCombobox", JsonUtil.toJson(comboOutStatusVO.getOptions()));
					
			//参建单位
			Map<String, SysDictionaryVO> constructionUnitsMap  =  DictionaryUtil.getDictionaries("CONSTRUCTION_UNITS");
			ComboboxVO constructionUnits = new ComboboxVO();
			for(String key : constructionUnitsMap.keySet()){
				SysDictionaryVO sysDictionaryVO = constructionUnitsMap.get(key);
				constructionUnits.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
			model.put("constructionUnitsCombobox", JsonUtil.toJson(constructionUnits.getOptions()));	
		//this.UpdateUser(request, personalManageEntity, id);
		return this.createModelAndView("personalManage/personalManageEdit", model);
	}
	
	@RequestMapping("/bulkDel")
	public @ResponseBody ResultObj bulkDelete(@RequestBody List<Integer> ids){
		ResultObj resultObj = new ResultObj();
		for (Integer id : ids) {
			long longId = (long)id;
			PersonalManageEntity presonalManageEntity = this.getService().findById(longId);
			presonalManageEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
			personalManageService.updateEntity(presonalManageEntity);
		}
		return resultObj;
	}
	
	@RequestMapping("/singleDel/{id}")
	public @ResponseBody ResultObj delete(@PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		PersonalManageEntity presonalManageEntity = this.getService().findById(id);

		presonalManageEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		personalManageService.updateEntity(presonalManageEntity);
		return resultObj;
	}
	@RequestMapping("/AddUser")
	public @ResponseBody ResultObj AddUser(HttpServletRequest request,@RequestBody PersonalManageEntity t){
		ResultObj resultObj = new ResultObj();
		
		List<Condition> conditionsCode=new ArrayList<Condition>();
		conditionsCode.add(new Condition("a.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,t.getCode()));
		conditionsCode.add(new Condition("a.C_UNIT",FieldTypeEnum.STRING,MatchTypeEnum.EQ,t.getUnit()));
		conditionsCode.add(new Condition("a.C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,DataStatusEnum.NORMAL.ordinal()));
		List<PersonalManageEntity> personalManageEntities = personalManageService.findByCondition(conditionsCode, null);
		if(null != personalManageEntities && personalManageEntities.size()!=0){
			resultObj.setData("添加失败，本场站人员工号不可重复");
		}else{
			personalManageService.AddPersonalUser(t);
			resultObj.setData("添加成功");
		}
		return resultObj;
	}
	@RequestMapping("/UpdateUser/{id}")
	public @ResponseBody ResultObj UpdateUser(HttpServletRequest request,@RequestBody PersonalManageEntity personalManageEntity,@PathVariable Long id ){
		ResultObj resultObj = new ResultObj();
		PersonalManageEntity dataUserEntity = personalManageService.findById(id);
		dataUserEntity.setName(personalManageEntity.getName());
		dataUserEntity.setCode(personalManageEntity.getCode());
		dataUserEntity.setSex(personalManageEntity.getSex());
		dataUserEntity.setUnit(personalManageEntity.getUnit());
		dataUserEntity.setUnitName(personalManageEntity.getUnitName());
		dataUserEntity.setMobile(personalManageEntity.getMobile());
		dataUserEntity.setMail(personalManageEntity.getMail());
		dataUserEntity.setOutStatus(personalManageEntity.getOutStatus());
		dataUserEntity.setConstructionUnits(personalManageEntity.getConstructionUnits());
		dataUserEntity.setRemark(personalManageEntity.getRemark());
		dataUserEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		personalManageService.updateEntity(dataUserEntity);
		resultObj.setData("操作成功");
		return resultObj;
		
	}
}