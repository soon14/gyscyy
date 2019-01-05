package com.aptech.business.sysManagement.web;

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

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.component.dictionary.SysManagementStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.sysManagement.domain.SysManagementEntity;
import com.aptech.business.sysManagement.service.SysManagementService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 制度管理配置控制器
 *
 * @author 
 * @created 2018-03-14 16:26:37
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/sysManagement")
public class SysManagementController extends BaseController<SysManagementEntity> {
	
	@Autowired
	private SysManagementService sysManagementService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<SysManagementEntity> getService() {
		return sysManagementService;
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
		List<SysManagementEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("sysManagementTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSysManagementVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("sysManagementCombobox", JsonUtil.toJson(comboSysManagementVO.getOptions()));
		//年号
//		ComboboxVO yearNumCombobox = new ComboboxVO();
//		Map<String, SysDictionaryVO> yearNumMap = DictionaryUtil.getDictionaries("YEARNUM");
//		for(String key : yearNumMap.keySet()){
//			SysDictionaryVO yearNumVO = yearNumMap.get(key);
//			yearNumCombobox.addOption(yearNumVO.getCode(), yearNumVO.getName());
//		}
//		model.put("yearNumCombobox", JsonUtil.toJson(yearNumCombobox.getOptions()));
		//状态
        ComboboxVO sysmanagementStatusCombobox = new ComboboxVO();
        Map<String, SysDictionaryVO> sysmanagementStatusMap  =  DictionaryUtil.getDictionaries("SYSMANAGEMENTSTATUS");
        
        for(String key :  sysmanagementStatusMap.keySet()){
        	SysDictionaryVO sysDictionaryVO = sysmanagementStatusMap.get(key);
        	sysmanagementStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
        }
        model.put("sysmanagementStatusCombobox", JsonUtil.toJson(sysmanagementStatusCombobox.getOptions()));

		//归口单位
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/sysManagement/sysManagementList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSysManagementVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("sysManagementCombobox", JsonUtil.toJson(comboSysManagementVO.getOptions()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//年号
//		ComboboxVO yearNumCombobox = new ComboboxVO();
//		Map<String, SysDictionaryVO> yearNumMap = DictionaryUtil.getDictionaries("YEARNUM");
//		for(String key : yearNumMap.keySet()){
//			SysDictionaryVO yearNumVO = yearNumMap.get(key);
//			yearNumCombobox.addOption(yearNumVO.getCode(), yearNumVO.getName());
//		}
//		model.put("yearNumCombobox", JsonUtil.toJson(yearNumCombobox.getOptions()));
		//制度管理类别
		ComboboxVO sysManagentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> sysManagentTypeMap = DictionaryUtil.getDictionaries("SYSMANAGENTMENTTYPE");
		for(String key : sysManagentTypeMap.keySet()){
			SysDictionaryVO sysManagentTypVO = sysManagentTypeMap.get(key);
			sysManagentTypeCombobox.addOption(sysManagentTypVO.getCode(), sysManagentTypVO.getName());
		}
		model.put("sysManagentTypeCombobox", JsonUtil.toJson(sysManagentTypeCombobox.getOptions()));

		//归口单位
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/sysManagement/sysManagementAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		SysManagementEntity sysManagementEntity = sysManagementService.findById(id);
		model.put("sysManagementEntity", sysManagementEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(sysManagementEntity.getApplyUserId()));
		model.put("sysUserEntity", sysUserEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSysManagementVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("sysManagementCombobox", JsonUtil.toJson(comboSysManagementVO.getOptions()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//年号
//		ComboboxVO yearNumCombobox = new ComboboxVO();
//		Map<String, SysDictionaryVO> yearNumMap = DictionaryUtil.getDictionaries("YEARNUM");
//		for(String key : yearNumMap.keySet()){
//			SysDictionaryVO yearNumVO = yearNumMap.get(key);
//			yearNumCombobox.addOption(yearNumVO.getCode(), yearNumVO.getName());
//		}
//		model.put("yearNumCombobox", JsonUtil.toJson(yearNumCombobox.getOptions()));
		//制度管理类别
		ComboboxVO sysManagentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> sysManagentTypeMap = DictionaryUtil.getDictionaries("SYSMANAGENTMENTTYPE");
		for(String key : sysManagentTypeMap.keySet()){
			SysDictionaryVO sysManagentTypVO = sysManagentTypeMap.get(key);
			sysManagentTypeCombobox.addOption(sysManagentTypVO.getCode(), sysManagentTypVO.getName());
		}
		model.put("sysManagentTypeCombobox", JsonUtil.toJson(sysManagentTypeCombobox.getOptions()));
		

		//归口单位
		String [] notContainUnitLevel = {"8","10","11","13"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.STRING, MatchTypeEnum.NOT_IN, notContainUnitLevel));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		ComboboxVO unitCombo = new ComboboxVO();
		
		for(SysUnitEntity unit : unitList){
			unitCombo.addOption(unit.getId().toString(), unit.getName());
		}
		model.put("unitList", JsonUtil.toJson(unitCombo.getOptions()));
		
		return this.createModelAndView("standardManage/sysManagement/sysManagementEdit", model);
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		SysManagementEntity sysManagementEntity = sysManagementService.findById(id);
		model.put("sysManagementEntity", sysManagementEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(sysManagementEntity.getApplyUserId()));
		model.put("sysUserEntity", sysUserEntity);
		SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(sysManagementEntity.getUnitId()));
		model.put("sysUnitEntity", sysUnitEntity);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSysManagementVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("sysManagementCombobox", JsonUtil.toJson(comboSysManagementVO.getOptions()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("userEntity", userEntity);

		//制度管理类别
		ComboboxVO sysManagentTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> sysManagentTypeMap = DictionaryUtil.getDictionaries("SYSMANAGENTMENTTYPE");
		for(String key : sysManagentTypeMap.keySet()){
			SysDictionaryVO sysManagentTypVO = sysManagentTypeMap.get(key);
			sysManagentTypeCombobox.addOption(sysManagentTypVO.getCode(), sysManagentTypVO.getName());
		}
		model.put("sysManagentTypeCombobox", JsonUtil.toJson(sysManagentTypeCombobox.getOptions()));
		return this.createModelAndView("standardManage/sysManagement/sysManagementDetail", model);
	}
	/**
	 * @Description:   自己写的修改方法
	 * @author         huoxy
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody SysManagementEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return sysManagementService.update(t,id);
	}
	/**
	 * 
	 * 改为有效状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @param farmId
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年11月4日 下午1:50:29
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStart/{id}")
	public @ResponseBody ResultObj resultConfirmStart(HttpServletRequest request, @PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		SysManagementEntity sysManagementEntity = sysManagementService.findById(id);
		sysManagementEntity.setStatus(SysManagementStatusEnum.YES.getCode());
		sysManagementService.updateEntity(sysManagementEntity);
			resultObj.setResult("success");
		return resultObj;
	}
	/**
	 * 
	 * 改为无效状态
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param status
	 * @param @param farmId
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年11月4日 下午1:50:55
	 * @lastModified
	 */
	@RequestMapping("/resultConfirmStop/{id}")
	public @ResponseBody ResultObj resultConfirmStop(HttpServletRequest request, @PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		SysManagementEntity sysManagementEntity = sysManagementService.findById(id);
		sysManagementEntity.setStatus(SysManagementStatusEnum.NO.getCode());
		sysManagementService.updateEntity(sysManagementEntity);
		return resultObj;
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
		sysManagementService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.REGULATIONS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
			SysManagementEntity entity = sysManagementService.findById(longId);
			if (entity != null) {
				sysManagementService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.REGULATIONS.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
}