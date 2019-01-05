package com.aptech.business.equip.equipTree.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEntity;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEnum;
import com.aptech.business.equip.equipAbnormal.service.EquipAbnormalService;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.business.equip.equipAppraise.service.EquipAppraiseService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.equip.equipModel.domain.EquipModelEntity;
import com.aptech.business.equip.equipModel.service.EquipModelService;
import com.aptech.business.equip.equipTree.domain.EquipTreeEntity;
import com.aptech.business.equip.equipTree.domain.EquipTreeEnum;
import com.aptech.business.equip.equipTree.domain.EquipTreeNode;
import com.aptech.business.equip.equipTree.service.EquipTreeService;
import com.aptech.business.equip.equiplParameter.service.EquipParameterService;
import com.aptech.business.equip.modelParameter.service.ModelParameterService;
import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.business.orgaApp.service.OrgaAppService;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.service.OverhaulSpareconsumeService;
import com.aptech.business.run.workRecord.domain.WorkRecordEntity;
import com.aptech.business.run.workRecord.service.WorkRecordService;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
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
 * 设备节点表配置控制器
 * 
 * @author
 * @created 2017-06-26 15:26:43
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/equiptree")
public class EquipTreeController extends BaseController<EquipTreeEntity> {
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private EquipTreeService equipTreeService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private EquipModelService equipModelService;
	@Autowired
	private EquipParameterService equipParameterService;
	@Autowired
	private ModelParameterService modelParameterService;
	@Autowired
	private OrgaAppService orgaAppService;
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private WorkTicketService workTicketService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private EquipAbnormalService equipAbnormalService;
	@Autowired
	private OperationTicketService operationTicketService;
	@Autowired
	private EquipAppraiseService equipAppraiseService;
	
	@Autowired
	private OverhaulSpareconsumeService overhaulSpareconsumeService;
	
	@Autowired
	private MaterialCategoryService materialCategoryService;
	
	@Override
	public IBaseEntityOperation<EquipTreeEntity> getService() {
		return equipTreeService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index/{appId}")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipTreeEntity> treeNodeList = null;
		model.put("equiptreeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquiptreeVO = new ComboboxVO();
		model.put("equiptreeCombobox",
				JsonUtil.toJson(comboEquiptreeVO.getOptions()));
		return this.createModelAndView("equip/equiptree/equiptreeList", model);
	}

	/**
	 * 跳转节点(组织机构)新增页面
	 */
	@RequestMapping(value = "/getAddLogicalEquip/{id}/{unitId}/{pathCode}", method = RequestMethod.GET)
	public ModelAndView getTreeNodeAdd(HttpServletRequest request,
			@PathVariable Long id, @PathVariable Long unitId,
			@PathVariable String pathCode) {
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		// 如果当前不是开发者模式
		if (!RequestContext.get().isDeveloperMode()) {
			conditions.add(new Condition("C_ID = " + userEntity.getUnitId()));
		}
		// 组织机构(父级节点)
		List<SysUnitEntity> sysUnitEntity = sysUnitService.findByCondition(
				conditions, null);
		model.put("sysunit", JsonUtil.toJson(sysUnitEntity));
		model.put("parentId", id);
		model.put("unitId", unitId);
		model.put("pathCode", pathCode);
		return this.createModelAndView("equip/equiptree/equiptreeAdd", model);
	}

	/**
	 * 跳转节点(设备)新增页面
	 */
	@RequestMapping(value = "/getAddPhysicsEquip/{id}/{unitId}/{pathCode}", method = RequestMethod.GET)
	public ModelAndView getEquipNodeAdd(HttpServletRequest request,
			@PathVariable Long id, @PathVariable Long unitId,
			@PathVariable String pathCode) {
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity = RequestContext.get().getUser();
		// 设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboequipType = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList) {
			comboequipType.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		// 设备模版
		conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipModelEntity> equipModelList = equipModelService
				.findByCondition(conditions, null);
		ComboboxVO equipModelcombobox = new ComboboxVO();
		for (EquipModelEntity equipModelEntity : equipModelList) {
			equipModelcombobox.addOption(equipModelEntity.getId().toString(),
					equipModelEntity.getModelNumber());
		}
		model.put("equipModel",
				JsonUtil.toJson(equipModelcombobox.getOptions()));
		//业务类型(运行方式、接地线闸)
		conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.BUSINESSTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesListForBusinessType = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboBusinessType = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesListForBusinessType) {
			comboBusinessType.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		model.put("businessType", JsonUtil.toJson(comboBusinessType.getOptions()));
		// 如果当前不是开发者模式
		if (!RequestContext.get().isDeveloperMode()) {
			conditions.add(new Condition("C_ID = " + userEntity.getUnitId()));
		}
		conditions = new ArrayList<Condition>();
		// 组织机构(父级节点)
		List<SysUnitEntity> sysUnitEntity = sysUnitService.findByCondition(
				conditions, null);
		model.put("sysunit", JsonUtil.toJson(sysUnitEntity));
		model.put("equipParentId", id);
		model.put("unitId", unitId);
		model.put("pathCode", pathCode);
		return this.createModelAndView("equip/equiptree/equiptreeEquipAdd",
				model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 组织机构
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		// 如果当前不是开发者模式
		if (!RequestContext.get().isDeveloperMode()) {
			conditions.add(new Condition("C_ID = " + userEntity.getUnitId()));
		}
		List<SysUnitEntity> sysUnitEntity = sysUnitService.findByCondition(
				conditions, null);
		model.put("sysunit", JsonUtil.toJson(sysUnitEntity));
		// 树(组织机构)
		EquipTreeEntity equipTreeEntity = equipTreeService.findById(id);
		model.put("equipTreeEntity", equipTreeEntity);
		model.put("id", id);
		return this.createModelAndView("equip/equiptree/equiptreeEdit", model);
	}

	/**
	 * 跳转到修改(设备)页面
	 */
	@RequestMapping("/getEdit/{id}/{unitId}")
	public ModelAndView getModifyPage(HttpServletRequest request,
			@PathVariable Long id, @PathVariable Long unitId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// 组织机构(父级节点)
		model.put("sysunit", JsonUtil.toJson(treeNodeList));
		// 设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboequipType = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList) {
			comboequipType.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		model.put("equipType", JsonUtil.toJson(comboequipType.getOptions()));
		// 设备模版
		List<EquipModelEntity> equipModellist = equipModelService.findAll();
		ComboboxVO equipModelcombobox = new ComboboxVO();
		for (EquipModelEntity equipModelEntity : equipModellist) {
			equipModelcombobox.addOption(equipModelEntity.getId().toString(),
					equipModelEntity.getModelNumber());
		}
		model.put("equipModel",
				JsonUtil.toJson(equipModelcombobox.getOptions()));
		//业务类型(运行方式、接地线闸)
		conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.BUSINESSTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesListForBusinessType = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboBusinessType = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesListForBusinessType) {
			comboBusinessType.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		model.put("businessType", JsonUtil.toJson(comboBusinessType.getOptions()));
		// 设备信息
		EquipLedgerEntity equipLedgerEntity = equipLedgerService.findById(id);
		equipLedgerEntity.setEquipParentId(unitId);
		model.put("equipLedgerEntity", equipLedgerEntity);

		return this.createModelAndView("equip/equiptree/equiptreeEquipEdit",
				model);
	}

	/**
	 * @Description: 跳转设备树
	 * @author wangcc
	 * @Date 2017年6月27日 下午6:39:27
	 * @throws Exception
	 */
	@RequestMapping("/getTree")
	public ModelAndView getTree(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return new ModelAndView("equip/equiptree/equiptree", resultMap);
	}

	/**
	 * @Description: 展示设备树
	 * @author wangcc
	 * @Date 2017年6月27日 下午3:30:45
	 * @throws Exception
	 */
	@RequestMapping("/showTree")
	public @ResponseBody List<EquipTreeNode> showTree(HttpServletRequest request) {
		// SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		// 如果当前不是开发者模式
		// if(!RequestContext.get().isDeveloperMode()){
		// conditions.add(new Condition("C_UNIT_ID = "+userEntity.getUnitId()));
		// }
		EquipTreeNode equipTreeNode = null;
		List<EquipTreeNode> equipTreeNodes = new ArrayList<EquipTreeNode>();
		conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equipTreeEntities = equipTreeService
				.findByCondition(conditions, null);
		for (EquipTreeEntity equipTreeEntity : equipTreeEntities) {
			equipTreeNode = new EquipTreeNode();
			equipTreeNode.setId(equipTreeEntity.getId());
			equipTreeNode.setCode(equipTreeEntity.getCode());
			equipTreeNode.setName(equipTreeEntity.getName());
			equipTreeNode.setParentId(equipTreeEntity.getParentId());
			equipTreeNode.setUnitId(equipTreeEntity.getUnitId());
			equipTreeNode.setPathCode(equipTreeEntity.getPathCode());
			equipTreeNode.setEquipId(equipTreeEntity.getEquipId());
			equipTreeNode.setTreeNodelevel(equipTreeEntity.getLevel());
			if (equipTreeEntity.getTreeType() == EquipTreeEnum.UNIT.getId()) {
				equipTreeNode
						.setIcon("../static/css/themes/default/tree/zTreeStyle/img/diy/1_open.png");
				equipTreeNode
						.setIconOpen("../static/css/themes/default/tree/zTreeStyle/img/diy/1_open.png");
				equipTreeNode
						.setIconClose("../static/css/themes/default/tree/zTreeStyle/img/diy/1_close.png");
				equipTreeNode.setOpen(false);
			} else if (equipTreeEntity.getTreeType() == EquipTreeEnum.LOGICALEQUIP
					.getId()) {
				equipTreeNode
						.setIcon("../static/css/themes/default/tree/zTreeStyle/img/diy/9.png");
				equipTreeNode
						.setIconOpen("../static/css/themes/default/tree/zTreeStyle/img/diy/9.png");
				equipTreeNode
						.setIconClose("../static/css/themes/default/tree/zTreeStyle/img/diy/9.png");
				equipTreeNode.setOpen(false);
			} else {
				equipTreeNode
						.setIcon("../static/css/themes/default/tree/zTreeStyle/img/diy/10.png");
				equipTreeNode
						.setIconOpen("../static/css/themes/default/tree/zTreeStyle/img/diy/10.png");
				equipTreeNode
						.setIconClose("../static/css/themes/default/tree/zTreeStyle/img/diy/10.png");
				equipTreeNode.setOpen(false);
			}
			equipTreeNodes.add(equipTreeNode);
		}
		return equipTreeNodes;
	}

	/**
	 * 设备树列表页面跳转
	 * 
	 * @Title:
	 * @Description:+
	 * @param
	 * @return
	 */
	@RequestMapping("/listForTree/{pathCode}/{id}")
	public ModelAndView treelist(HttpServletRequest request,
			@PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 设备类型
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesList = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboequipType = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesList) {
			comboequipType.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		// 设备类型
		conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, EquipTreeEnum.EQUIPTYPE.getCode()));
		List<SysDictionaryEntity> sysDictionaryEntitiesListX = sysDictionaryService
				.findByCondition(conditions, null);
		ComboboxVO comboequipX = new ComboboxVO();
		for (SysDictionaryEntity sysdictionaryentity : sysDictionaryEntitiesListX) {
			comboequipX.addOption(sysdictionaryentity.getCode().toString(),
					sysdictionaryentity.getName());
		}
		model.put("equipType", JsonUtil.toJson(comboequipX.getOptions()));
		model.put("pathCode", pathCode);
		model.put("id", id);
		return this.createModelAndView(
				"equip/equiptree/equipLedgerListForTree", model);
	}

	/**
	 * @Description: 跳转设备关联工作票列表
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/workTicketListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getWorkTicketListInitWithEquip(HttpServletRequest request,
			@PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		// TODO下拉树具体内容根据具体业务定制
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		//zzq20180416修改查询班组列表开始
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditionsBanzu=new ArrayList<Condition>();
		conditionsBanzu.add(new Condition("a.C_ORGANIZATION", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<OrgaAppEntity> orgaApp = orgaAppService.findByCondition(conditionsBanzu, null);
		//zzq20180416修改查询班组列表结束
		ComboboxVO comboWorkTicketVO = new ComboboxVO();
		for (OrgaAppEntity orgaAppEntity : orgaApp) {
			comboWorkTicketVO.addOption(orgaAppEntity.getId().toString(),
					orgaAppEntity.getName());
		}
		// TODO下拉框具体内容根据具体业务定制
		resultMap.put("groupIdCombobox",
				JsonUtil.toJson(comboWorkTicketVO.getOptions()));

		// 状态下拉框
		// 编码类型
		ComboboxVO codeDateTypesCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap = DictionaryUtil
				.getDictionaries("WORKTICKET_TYPE");

		for (String key : codeDateTypeMap.keySet()) {
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			codeDateTypesCombobox.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
		}
		resultMap.put("statusTypes",
				JsonUtil.toJson(codeDateTypesCombobox.getOptions()));
		// 工作负责人下拉列表
		ComboboxVO requestUserVO = new ComboboxVO();
		// TODO下拉框具体内容根据具体业务定制
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity sysUserEntity : allUsers) {
			requestUserVO.addOption(sysUserEntity.getId().toString(),
					sysUserEntity.getName());
		}
		resultMap.put("requestUsers",
				JsonUtil.toJson(requestUserVO.getOptions()));
		//获取台账所有子设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition("  T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR  T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		//关联设备两票关系表
		
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		resultMap.put("equipIdJson", JsonUtil.toJson(equiotree));
		String userUnitRels = "[]";
		resultMap.put("userUnitRels", userUnitRels);
		return new ModelAndView("equip/equiptree/workTicketListWithEquip",
				resultMap);
	}

	/**
	 * @Description: 获取设备相关工作票
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/workTicketListWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getWorkTicketListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<WorkTicketEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);  
		List<WorkTicketEntity> entities = workTicketService.findByCondition("findByConditionForEquip", conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}

	/**
	 * @Description: 跳转设备关联操作票
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/operationTicketInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getOperationTicketInitWithEquip(
			HttpServletRequest request, String cookieTime,
			@PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cookieTime", cookieTime);
		//单位下拉框
		List<Condition> companyConditions = new ArrayList<Condition>();
		companyConditions.add(new Condition("C_GENERATION_TYPE = 1 "));
		companyConditions.add(new Condition(" C_ORGANIZATION = 0 OR C_ORGANIZATION = 1 OR C_ORGANIZATION = 3 OR C_ORGANIZATION = 4"));
		
		List<SysUnitEntity> unitList = sysUnitService.findByCondition(companyConditions, null);
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
 		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 状态下拉
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (OperationStatusEnum operationStatusEnum : OperationStatusEnum
				.values()) {
			searchprocessStatus.addOption(operationStatusEnum.getCode(),
					operationStatusEnum.getName());
		}
		model.put("searchprocessStatus",
				JsonUtil.toJson(searchprocessStatus.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return new ModelAndView("equip/equiptree/operationTicketWithEquip",
				model);
	}

	/**
	 * @Description: 获取设备相关操作票
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/operationListWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getOperationListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<OperationTicketEntity> page = PageUtil.getPage(params);
		List<OperationTicketEntity> entities = operationTicketService.findByConditionForEquip(params, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}

	/**
	 * @Description: 跳转设备有关联运行记录
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/workRecordListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getWorkRecordListInitWithEquip(
			HttpServletRequest request, @PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		resultMap.put("workRecordTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkRecordVO = new ComboboxVO();
		// TODO下拉框具体内容根据具体业务定制
		List<SysUserEntity> allUsers = sysUserService.findAll();
		for (SysUserEntity sysUserEntity : allUsers) {
			comboWorkRecordVO.addOption(sysUserEntity.getId().toString(),
					sysUserEntity.getName());
		}
		resultMap.put("workRecordCombobox",
				JsonUtil.toJson(comboWorkRecordVO.getOptions()));
		//班次
		ComboboxVO comboRunLogVO2 = new ComboboxVO();
        List<OrgaAppEntity> allOrgaAppEntity = orgaAppService.findAll();
        for(OrgaAppEntity orgaAppEntity : allOrgaAppEntity){
            comboRunLogVO2.addOption(orgaAppEntity.getId().toString(), orgaAppEntity.getName());
        }
        resultMap.put("orgAppCombobox", JsonUtil.toJson(comboRunLogVO2.getOptions()));
        //审批状态
  		ComboboxVO processStatusName = new ComboboxVO();
  		for (ProtectStatusEnum  protectstatusenum: ProtectStatusEnum.values()) {
  			processStatusName.addOption(protectstatusenum.getCode(),
  					protectstatusenum.getName());
  		}
  		resultMap.put("processStatusCombobox", JsonUtil.toJson(processStatusName.getOptions()));
		// 获取树节点相关设备
  		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		resultMap.put("equipIdJson", JsonUtil.toJson(equiotree));
		String userUnitRels = "[]";
		resultMap.put("userUnitRels", userUnitRels);
		return new ModelAndView("equip/equiptree/workRecordListWithEquip",
				resultMap);
	}

	/**
	 * @Description: 获取设备相关工作记录
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/workRecordListWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getworkRecordListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<WorkRecordEntity> page = PageUtil.getPage(params);
		List<WorkRecordEntity> entities = workRecordService.findByCondition(
				params, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}

	/**
	 * @Description: 跳转设备异动记录
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipAbnormalListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getEquipAbnormalListInitWithEquip(
			HttpServletRequest request, @PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 审批状态
		ComboboxVO processStatusName = new ComboboxVO();
		for (EquipAbnormalEnum equipAbnormalEnum : EquipAbnormalEnum.values()) {
			processStatusName.addOption(equipAbnormalEnum.getCode(),
					equipAbnormalEnum.getName());
		}
		model.put("processStatus",
				JsonUtil.toJson(processStatusName.getOptions()));
		// 单位名称
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		// 申请人
		model.put("applyUserId", JsonUtil.toJson(searchuser.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));
		
		model.put("pathCode", pathCode);
		
		return new ModelAndView("equip/equiptree/equipAbnormalListWithEquip", model);
	}

	/**
	 * @Description: 获取设备异动相关信息
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipAbnormalListWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getEquipAbnormalListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<EquipAbnormalEntity> page = PageUtil.getPage(params);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		String[] equitId = {};
		for (Condition con :conditions) {
			if ("R.C_EQUIP_CODE".equals(con.getFieldName())) {
				equitId = (String[])con.getValue();
				break;
			}
		}
		List<EquipAbnormalEntity> entities;
		if (equitId.length == 0) {
			entities = new ArrayList<EquipAbnormalEntity>();
		} else if (equitId.length == 1){
			if ("".equals("equitId[0]")) {
				entities = new ArrayList<EquipAbnormalEntity>();
			} else {
				entities = equipAbnormalService.findByCondition("findEquipAbnormalListWithEquip", conditions, page);
			}
		} else {
			entities = equipAbnormalService.findByCondition("findEquipAbnormalListWithEquip", conditions, page);
		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
	
	/**
	 * @Description: 设备台账关联检修列表
	 * @author wangcc
	 * @Date 2018年2月11日 上午11:30:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/overhaulTaskInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getOverhaulTaskListInitWithEquip(
			HttpServletRequest request, @PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
//		// 审批状态
//		ComboboxVO processStatusName = new ComboboxVO();
//		for (EquipAbnormalEnum equipAbnormalEnum : EquipAbnormalEnum.values()) {
//			processStatusName.addOption(equipAbnormalEnum.getCode(),
//					equipAbnormalEnum.getName());
//		}
//		model.put("processStatus",
//				JsonUtil.toJson(processStatusName.getOptions()));
//		// 单位名称
//		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
//		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
//		// 人员
//		ComboboxVO searchuser = new ComboboxVO();
//		List<SysUserEntity> userList = sysUserService.findByCondition(
//				new ArrayList<Condition>(), null);
//		for (SysUserEntity user : userList) {
//			searchuser.addOption(user.getId().toString(), user.getName());
//		}
//		// 申请人
//		model.put("applyUserId", JsonUtil.toJson(searchuser.getOptions()));
		//完成状态
		ComboboxVO finishStatusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> codeDateTypeMap  =  DictionaryUtil.getDictionaries("OVERHUALARRANGE_FINISHSTATUS");
		for(String key :  codeDateTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = codeDateTypeMap.get(key);
			finishStatusCombobox.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("overhaulArrangeFinishStatus", JsonUtil.toJson(finishStatusCombobox.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));

		if ("1".equals(pathCode)) {
			model.put("unitId", "0");
		} else if (equiotree != null && equiotree.size() > 0) {
			model.put("unitId", equiotree.get(0).getUnitId());
		} else {
			model.put("unitId", "-1");
		}
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return new ModelAndView("equip/equiptree/overhaulTaskListWithEquip", model);
	}
	
	/**
	 * @Description: 获取设备异动相关信息
	 * @author wangcc
	 * @Date 2018年2月11日 上午11:30:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/overhaulTaskWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getOverhaulTaskListWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<EquipAbnormalEntity> page = PageUtil.getPage(params);
		List<EquipAbnormalEntity> entities = equipAbnormalService
				.findByCondition(params, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
	
	
	

	/**
	 * @Description: 跳转设备关联缺陷记录
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/defectListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getDefectListInitWithEquip(HttpServletRequest request,
			@PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 状态下拉
		ComboboxVO searchprocessStatus = new ComboboxVO();
		for (DefectStatusEnum defectStatusEnum : DefectStatusEnum.values()) {
			searchprocessStatus.addOption(defectStatusEnum.getCode(),
					defectStatusEnum.getName());
		}
		resultMap.put("searchprocessStatus",
				JsonUtil.toJson(searchprocessStatus.getOptions()));
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil
				.getDictionaries("DEFECT_TYPE");
		ComboboxVO searchtype = getTypeMap(typeMap);
		resultMap.put("searchtype", JsonUtil.toJson(searchtype.getOptions()));
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		resultMap.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		resultMap.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(conditions, null);
		resultMap.put("equipIdJson", JsonUtil.toJson(equiotree));
		
		if ("1".equals(pathCode)) {
			resultMap.put("unitId", "0");
		} else if (equiotree != null && equiotree.size() > 0) {
			resultMap.put("unitId", equiotree.get(0).getUnitId());
		} else {
			resultMap.put("unitId", "-1");
		}
		String userUnitRels = "[]";
		resultMap.put("userUnitRels", userUnitRels);
		return new ModelAndView("equip/equiptree/defectListWithEquip", resultMap);
	}

	/**
	 * @Description: 获取设备相关缺陷
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/defectListWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getDefectListWithEquip(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<DefectEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("unitName".equals(sort.getField())) {
				sort.setField("unitId");
			}
			if ("equipName".equals(sort.getField())) {
				sort.setField("equipId");
			}
			if ("findUserName".equals(sort.getField())) {
				sort.setField("findUserId");
			}
			if ("typeName".equals(sort.getField())) {
				sort.setField("type");
			}
			if ("appraisalUserName".equals(sort.getField())) {
				sort.setField("appraisalUserId");
			}
			if ("processStatusName".equals(sort.getField())) {
				sort.setField("processStatus");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		String[] equitId = {};
		for (Condition con :conditions) {
			if ("T1.C_EQUIP_CODE".equals(con.getFieldName())) {
				equitId = (String[])con.getValue();
				break;
			}
		}
		List<DefectEntity> entities;
		if (equitId.length == 0) {
			entities = new ArrayList<DefectEntity>();
		} else if (equitId.length == 1){
			if ("".equals(equitId[0])) {
				entities = new ArrayList<DefectEntity>();
			} else {
				entities = defectService.findByCondition("findEquipDefectInfo", conditions, page);
			}
		} else {
			entities = defectService.findByCondition("findEquipDefectInfo", conditions, page);
		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			resultObj.setRecordsTotal((long) entities.size());
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			}
		}
		return resultObj;
	}

	/**
	 * @Description: 是否删除
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/isdel/{id}", method = RequestMethod.POST)
	public @ResponseBody ResultObj isDel(HttpServletRequest request,
			@PathVariable Long id) {
		return equipTreeService.isdel(id);
	}

	/**
	 * @Description: 是否新增
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/isAdd", method = RequestMethod.POST)
	public @ResponseBody ResultObj isAdd(
			@RequestBody Map<String, Object> params, HttpServletRequest request) {
		return equipTreeService.isAdd(params, request);
	}

	/**
	 * @Description: 是否新增
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/isEdit/{id}", method = RequestMethod.POST)
	public @ResponseBody ResultObj isEdit(
			@RequestBody Map<String, Object> params, @PathVariable Long id,
			HttpServletRequest request) {
		return equipTreeService.isEdit(params, id, request);
	}

	private ComboboxVO getTypeMap(Map<String, SysDictionaryVO> typeMap) {
		ComboboxVO searchtype = new ComboboxVO();
		List<Map.Entry<String, SysDictionaryVO>> list = new ArrayList<Map.Entry<String, SysDictionaryVO>>(
				typeMap.entrySet());
		Collections.sort(list,
				new Comparator<Map.Entry<String, SysDictionaryVO>>() {
					public int compare(Map.Entry<String, SysDictionaryVO> o1,
							Map.Entry<String, SysDictionaryVO> o2) {
						return (o1.getKey()).toString().compareTo(o2.getKey());
					}
				});
		for (Map.Entry<String, SysDictionaryVO> entry : list) {
			SysDictionaryVO sysDictionaryVO = entry.getValue();
			searchtype.addOption(sysDictionaryVO.getCode(),
					sysDictionaryVO.getName());
		}
		return searchtype;
	}
	/**
	 * @Description: 跳转设备评价
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipAppraiseListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getEquipAppraiseListInitWithEquip(
			HttpServletRequest request, @PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//原评
		Map<String, SysDictionaryVO> equipGradePre = DictionaryUtil
				.getDictionaries("EQUIP_GRADE_PRE");
		ComboboxVO searchEquipGradePre=getTypeMap(equipGradePre);
		model.put("equipGradePre", JsonUtil.toJson(searchEquipGradePre.getOptions()));
		//现评
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil
				.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));
		return new ModelAndView("equip/equiptree/equipAppraiseListInitWithEquip",
				model);
	}
	/**
	 * @Description: 跳转设备评价
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/testReportListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView getTestReportListInitWithEquip(
			HttpServletRequest request, @PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		//人员
		ComboboxVO searchuser = new ComboboxVO();
		List<SysUserEntity> userList = sysUserService.findByCondition(
				new ArrayList<Condition>(), null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//原评
		Map<String, SysDictionaryVO> equipGradePre = DictionaryUtil
				.getDictionaries("EQUIP_GRADE_PRE");
		ComboboxVO searchEquipGradePre=getTypeMap(equipGradePre);
		model.put("equipGradePre", JsonUtil.toJson(searchEquipGradePre.getOptions()));
		//现评
		Map<String, SysDictionaryVO> equipGradeNow= DictionaryUtil
				.getDictionaries("EQUIP_GRADE_NOW");
		ComboboxVO searchEquipGradeNow=getTypeMap(equipGradeNow);
		model.put("equipGradeNow", JsonUtil.toJson(searchEquipGradeNow.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));
		return new ModelAndView("equip/equiptree/testReportListInitWithEquip",
				model);
	}
	/**
	 * @Description: 获取设备异动相关信息
	 * @author wangcc
	 * @Date 2017年7月6日 上午11:14:09
	 * @throws Exception
	 */
	@RequestMapping(value = "/equipAppraiseListInitWithEquip", method = RequestMethod.POST)
	public @ResponseBody ResultListObj getEquipAppraiseListInitWithEquip(
			HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<EquipAppraiseEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<EquipAppraiseEntity> entities = null;
		entities = equipAppraiseService.findByCondition("findInfoUnionEquipAbnormalEquipRel", conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
	/**
	 * @Description: 跳转物资类别
	 * @author wangcc
	 * @Date 2017年7月6日 下午9:27:49
	 * @throws Exception
	 */
	@RequestMapping(value = "/materialCategoryListInitWithEquip/{pathCode}/{id}", method = RequestMethod.GET)
	public ModelAndView materialCategoryListInitWithEquip(HttpServletRequest request,
			@PathVariable String pathCode,@PathVariable long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		ComboboxVO comboMaterialCategoryVO = new ComboboxVO();
		for(ManagementTypeEnum managementTypeEnum : ManagementTypeEnum.values()){
			comboMaterialCategoryVO.addOption(managementTypeEnum.getCode(), managementTypeEnum.getName());
		}
		model.put("managementCombobox", JsonUtil.toJson(comboMaterialCategoryVO.getOptions()));
		//生产厂家下拉框
		Map<String, SysDictionaryVO> prductionFactoryMap  =  DictionaryUtil.getDictionaries("PRODUCTION_FACTORY");
		ComboboxVO comboPrductionFactoryVO = new ComboboxVO();
		for(String key : prductionFactoryMap.keySet()){
			SysDictionaryVO sysDictionaryVO = prductionFactoryMap.get(key);
			comboPrductionFactoryVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("productionFactoryCombobox", JsonUtil.toJson(comboPrductionFactoryVO.getOptions()));
		//物资类别下拉树
		Map<String, SysDictionaryVO> materialTypeMap  =  DictionaryUtil.getDictionaries("MATERIAL_CATEGORY");
		ComboboxVO comboMaterialTypeVO = new ComboboxVO();
		for(String key : materialTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = materialTypeMap.get(key);
			comboMaterialTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("materialTypeCombobox", JsonUtil.toJson(comboMaterialTypeVO.getOptions()));
		// 获取树节点相关设备
		List<EquipTreeEntity> equiptreeList = equipTreeService.findChildTreeById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(equiptreeList.size()>1){
			conditions.add(new Condition(" T.C_PATH_CODE LIKE '%"+pathCode+"-%'"+" OR T.C_PATH_CODE = '"+pathCode+"'"));
		}else{
			conditions.add(new Condition(" T.C_PATH_CODE ", FieldTypeEnum.LONG,
					MatchTypeEnum.LIKE, pathCode));
		}
		
		conditions.add(new Condition(" T.C_TREE_TYPE ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.PHYSICSEQUIP.getId()));
		conditions.add(new Condition(" T.C_STATUS ", FieldTypeEnum.LONG,
				MatchTypeEnum.EQ, EquipTreeEnum.NORMAL.getId()));
		List<EquipTreeEntity> equiotree = equipTreeService.findByCondition(
				conditions, null);
		model.put("equipIdJson", JsonUtil.toJson(equiotree));
		return new ModelAndView("equip/equiptree/materialCategoryListInitWithEquip",
				model);
	}
	/**
	 * 查询物资
	 * 
	 * @Title: show
	 * @Description:
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchMaterialCategory")
	public  @ResponseBody ResultListObj searchMaterialCategory(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<MaterialCategoryEntity> page = PageUtil.getPage(params);
		List<MaterialCategoryEntity> entities = materialCategoryService.findByCondition("findMaterialCategoryDataForEquip", conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
}