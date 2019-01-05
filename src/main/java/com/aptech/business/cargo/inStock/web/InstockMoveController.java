package com.aptech.business.cargo.inStock.web;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.business.cargo.inOutStock.service.InOutStockService;
import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStock.service.InstockService;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.inStockDetail.service.InstockDetailService;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.component.dictionary.InstockApproveStatusEnum;
import com.aptech.business.component.dictionary.InstockExcuteEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ProtectResultEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.business.wareHouse.wareHouseArea.service.WareHouseAreaService;
import com.aptech.business.wareHouse.wareHouseAreaPosition.service.WareHouseAreaPositionService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.processNodeAuth.domain.ProcessNodeAuthEntity;
import com.aptech.common.workflow.processNodeAuth.service.ProcessNodeAuthService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 入库管理配置控制器
 *
 * @author 
 * @created 2017-07-19 11:32:25
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/instockMove")
public class InstockMoveController extends BaseController<InstockEntity> {
	
	@Autowired
	private InstockService instockService;
	
	@Autowired
	private InstockDetailService instockDetailService;
	
	@Autowired
	private MaterialCategoryService materialCategoryService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private InOutStockService inOutStockService;
	
	@Autowired
	private DefinitionService definitionService;
	
	@Autowired
	private NodeConfigService nodeConfigService;
	
	@Autowired
	private TodoTaskService todoTaskService;
	
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	
	@Autowired
	private SysDutiesService sysDutiesService;
	
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private WareHouseService wareHouseService;
	
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	
	@Autowired
	private WareHouseAreaPositionService wareHouseAreaPositionService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Override
	public IBaseEntityOperation<InstockEntity> getService() {
		return instockService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//入库类型下拉框
		Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("INSTOCK_TYPE");
		ComboboxVO comboInstockTypeVO = new ComboboxVO();
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
			comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
		//审核状态
		Map<String, SysDictionaryVO> approveStatusMap  =  DictionaryUtil.getDictionaries("APPROVE_STATUS");
		ComboboxVO approveStatusVO = new ComboboxVO();
		for(String key : approveStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = approveStatusMap.get(key);
			approveStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("approveStatusCombobox", JsonUtil.toJson(approveStatusVO.getOptions()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
		model.put("sysUserName", JsonUtil.toJson(sysUserEntity.getName()));
			
		List<Long> dutyIdList = new ArrayList<Long>();
		//库管员职位下人员id
		dutyIdList.addAll(instockService.getDutyDetailId(params));
		model.put("isDuty", dutyIdList.contains(sysUserEntity.getId()));
		List<Long> userIdList = new ArrayList<Long>();
		//系统管理员的人员id
		userIdList.addAll(instockService.getUserId(params));
		model.put("isSystemManage", userIdList.contains(sysUserEntity.getId()));
		return this.createModelAndView("cargo/instock/instockMoveList", model);
	}
	
	/**
	 * 
	 * 列表页面数据查询
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午10:00:01
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj list(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//按照时间倒序排列
		Page<InstockEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//instockTime
		List<Condition> condition=OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		condition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<InstockEntity> inStockEntities = instockService.findByCondition(condition, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (inStockEntities != null) {
			if (pages != null) {
				resultObj.setData(inStockEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 跳转到添加页面
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午9:59:45
	 * @lastModified
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userUnitId = String.valueOf(sysUserEntity.getUnitId());
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		
		//生成入库单号
		String prefix = "RK";	//入库单号前缀
		int sequenLength = 4;	//入库单号序列号位数
		String GenerateCode = instockService.generateCode(prefix, userUnitId, sequenLength);
		model.put("genetateCode", GenerateCode);
		
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));
		
		//入库类型
		Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("INSTOCK_TYPE");
		ComboboxVO comboInstockTypeVO = new ComboboxVO();
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
			comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
		
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userUnitId));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		//单位下拉框 add   by  zhangxb   2018/3/1
		List<SysUnitEntity> treeNodeList = this.getUnitTreeNodeList1();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//物资属性向下拉框类型  add   by  zhangxb   2018/3/1
		Map<String, SysDictionaryVO> attributeTypeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
		ComboboxVO comboAttributeTypeVO = new ComboboxVO();
		for(String key : attributeTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = attributeTypeMap.get(key);
			comboAttributeTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("goodsAttribute", JsonUtil.toJson(comboAttributeTypeVO.getOptions()));
		return this.createModelAndView("cargo/instock/instockMoveAdd", model);
	}
	
	public List<SysUnitEntity> getUnitTreeNodeList1() {
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.NE, "4"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<SysUnitEntity> sysUnitEntities = sysUnitService.findByCondition(conditions, null);
		return sysUnitEntities;
	}
	
	
	/**
	 * 添加页列表返回空值
	 * {该处请说明该method的含义和作用}
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月23日 下午12:59:28
	 * @lastModified
	 */
	@RequestMapping("/search/list")
	public @ResponseBody ResultListObj searchList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<InstockEntity> pages = PageUtil.getPage(params);
		List<InstockEntity> inStockEntities = new ArrayList<InstockEntity>();
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (inStockEntities != null) {
			if (pages != null) {
				resultObj.setData(inStockEntities);
				resultObj.setRecordsTotal(0);
			} else {
				resultObj.setRecordsTotal((long) inStockEntities.size());
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 添加页面保存
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @param @throws UnsupportedEncodingException
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午10:00:19
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage/{materialId}")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		//添加数据到入库表
		ResultObj resultObj = new ResultObj();
		String materialCount = entityMap.get("materialCount").toString();
//		String materialUnitName = entityMap.get("materialUnitName").toString();
//		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialId));
//		String management = materialCategoryEntity.getManagement();
		int x = -1;
			x = materialCount.indexOf(".");
				if(x>0){
					resultObj.setResult("exceptionOne");	
				}
		if(x == -1){
			InstockEntity instockEntity = new InstockEntity();
			instockEntity.setInstockNum(entityMap.get("instockNum").toString());
			DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
			Date instockdate = sdf.parse(entityMap.get("instockTime").toString());
			instockEntity.setInstockTime(instockdate);
			instockEntity.setInstockType(entityMap.get("instockType").toString());
			//通过用户id查找用户单位
			/*SysUserEntity applicantUser = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
			SysUnitEntity sysUnitEntity = sysUnitService.findById(applicantUser.getUnitId());
			instockEntity.setUnitId(sysUnitEntity.getId());*/
			//入库单位
			Long unitId=Long.valueOf(entityMap.get("unitId").toString());
			instockEntity.setUnitId(unitId);
			instockEntity.setApplicant(entityMap.get("applicant").toString());
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
			instockEntity.setApplicantName(sysUserEntity.getName());
			instockEntity.setApproveStatus("0");
			instockEntity.setWareHouseId(Long.valueOf(entityMap.get("wareHouseId").toString()));
			if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
				instockEntity.setAttachment(entityMap.get("attachment").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
				instockEntity.setRemark(entityMap.get("remark").toString());
			}
			instockService.addEntity(instockEntity);
			
			String materialIdStringTemp = entityMap.get("materialId").toString();
			String materialIdString = materialIdStringTemp.substring(1, materialIdStringTemp.length()-1);
			String materialCountStringTemp = entityMap.get("materialCount").toString();
			//添加数据到明细表
			InstockDetailEntity instockDetailEntity = new InstockDetailEntity();
			String instockNum = entityMap.get("instockNum").toString();
			Long wareHouseId = Long.valueOf(entityMap.get("wareHouseId").toString());
			String goodsAreaId = entityMap.get("goodsArea").toString();
			String goodsAllocationId = entityMap.get("goodsAllocation").toString();
			String goodsPrice = entityMap.get("goodsPrice").toString();
			String goodsValidity = entityMap.get("goodsValidity").toString();
			String goodsAttribute = entityMap.get("goodsAttribute").toString();
			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, goodsAttributeName));
//			List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
//			SysDictionaryEntity sysDictionaryEntity = sysDictionaryList.get(0);
//			String goodsAttribute = sysDictionaryEntity.getCode();
			if(materialIdString.contains(",")){
				instockService.addPageSave(goodsAreaId,goodsAllocationId,materialIdString, materialCountStringTemp, instockdate, instockNum,goodsPrice,goodsValidity,goodsAttribute,wareHouseId, unitId, instockEntity, instockDetailEntity);
			}else{
				instockService.addPageSaveSingle(goodsAreaId,goodsAllocationId,materialIdString, materialCountStringTemp, instockNum,goodsPrice,goodsValidity,goodsAttribute,wareHouseId, instockdate, unitId, instockEntity, instockDetailEntity);
			}
			resultObj.setResult("success");
		}
		
		
		return resultObj;
	}
	
	/**
	 * 
	 * 跳转到修改页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午10:00:38
	 * @lastModified
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getModifyPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		InstockEntity instockEntity = (InstockEntity)instockService.findById(id);
		WareHouseEntity wareHouse = wareHouseService.findById(Long.valueOf(instockEntity.getWareHouseId()));
		instockEntity.setWareHouseName(wareHouse.getWareHouseName());
		model.put("entity", instockEntity);
		model.put("entityJson", JsonUtil.toJson(instockEntity));
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		
		//入库类型
		Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("INSTOCK_TYPE");
		ComboboxVO comboInstockTypeVO = new ComboboxVO();
			for(String key : instockTypeMap.keySet()){
				SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
				comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
			}
		model.put("instockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,1));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		//获取获取列表
		conditions.clear();
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockEntity.getWareHouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		//单位下拉框 add   by  zhangxb   2018/3/1
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//物资属性向下拉框类型  add   by  zhangxb   2018/3/1
		Map<String, SysDictionaryVO> attributeTypeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
		ComboboxVO comboAttributeTypeVO = new ComboboxVO();
		for(String key : attributeTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = attributeTypeMap.get(key);
			comboAttributeTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("goodsAttribute", JsonUtil.toJson(comboAttributeTypeVO.getOptions()));
		//获取入库物资明细
		conditions.clear();
		conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, instockEntity.getId()));
		List<InstockDetailEntity> instockDetailList = instockDetailService.findByCondition(conditions, null);
		if(instockDetailList!=null&&!instockDetailList.isEmpty()){
			model.put("instockDetailList", instockDetailList.get(0).getMaterialId());
		}else{
			model.put("instockDetailList", "[]");
		}
		return this.createModelAndView("cargo/instock/instockMoveEdit", model);
	}
	
	/**
	 * 修改页面的列表检索
	 * {该处请说明该method的含义和作用}
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月23日 下午12:59:34
	 * @lastModified
	 */
	@RequestMapping("/editData/list/{instockId}")
	public @ResponseBody ResultListObj EditList(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long instockId){
		Page<InstockDetailEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.asc("materialId"));//materialName
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockId));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (instockDetailEntities != null) {
			if (pages != null) {
				resultObj.setData(instockDetailEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	 
	/**
	 * 
	 * 修改页面保存功能
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws ParseException 
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月26日 上午9:59:16
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage/{materialId}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialCount = entityMap.get("materialCountArray").toString();
		String materialUnitName = entityMap.get("materialUnitName").toString();
		int x = -1;
			x = materialCount.indexOf(".");
				if(x>0){
					resultObj.setResult("exceptionOne");	
				}
		if(x == -1){
			Long instockId = Long.valueOf(entityMap.get("id").toString());
			InstockEntity instockEntity = instockService.findById(instockId);
			instockEntity.setInstockNum(entityMap.get("instockNum").toString());
			DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
			Date instockDate = sdf.parse(entityMap.get("instockTime").toString());
			instockEntity.setInstockTime(instockDate);
			instockEntity.setInstockType(entityMap.get("instockType").toString());
			instockEntity.setApplicant(entityMap.get("applicant").toString());
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
			instockEntity.setApplicantName(sysUserEntity.getName());
			instockEntity.setWareHouseId(Long.valueOf(entityMap.get("wareHouseId").toString()));
			if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
				instockEntity.setAttachment(entityMap.get("attachment").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
				instockEntity.setRemark(entityMap.get("remark").toString());
			}
			
			//通过用户id查找用户单位
			SysUnitEntity sysUnitEntity = sysUnitService.findById(sysUserEntity.getUnitId());
			instockEntity.setUnitId(sysUnitEntity.getId());
			instockService.updateEntity(instockEntity);
			
			List<String> materialIdList = (List<String>)entityMap.get("materialIdArray");
			
			
			
			
			String materialIdStringTemp = materialIdList.toString();
			String materialCountStringTemp = entityMap.get("materialCountArray").toString();
			String instockNumString =entityMap.get("instockNum").toString();
			Long wareHouseId = Long.valueOf(entityMap.get("wareHouseId").toString());
			String goodsAreaId = entityMap.get("goodsArea").toString();
			String goodsAllocationId = entityMap.get("goodsAllocation").toString();
			String goodsPrice = entityMap.get("goodsPrice").toString();
			String goodsValidity = entityMap.get("goodsValidity").toString();
			String goodsAttribute = entityMap.get("goodsAttribute").toString();
//			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("C_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, showGoodsAttribute));
//			List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
//			SysDictionaryEntity sysDictionaryEntity = sysDictionaryList.get(0);
//			String goodsAttribute = sysDictionaryEntity.getCode();
			//添加数据到明细表
			if(null != materialIdList && materialIdList.size()>1){
				instockService.editPageSave(goodsAreaId,goodsAllocationId,instockId,materialIdList, materialIdStringTemp, materialCountStringTemp, instockNumString,goodsPrice,goodsValidity,goodsAttribute,wareHouseId, instockDate,  sysUnitEntity);
			}else if(materialIdList.size() == 1){
				instockService.editPageSaveSingle(goodsAreaId,goodsAllocationId,instockId,materialIdStringTemp, materialCountStringTemp, instockNumString,goodsPrice,goodsValidity,goodsAttribute,wareHouseId, sysUnitEntity, instockDate);
			}
			
			resultObj.setResult("success");
		}
		
		return resultObj;
	}
	
	/**
	 * 
	 * 入库管理导出功能
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月25日 下午4:16:46
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String instockNum = req.getParameter("instockNum");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String instockType = req.getParameter("instockType");
		String approveStatus = req.getParameter("approveStatus");
		String unitId = req.getParameter("unitId");
		String applicant = req.getParameter("applicant");
		
		Page<InstockEntity> pages = new Page<InstockEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(instockNum)){
			conditions.add(new Condition("a.C_INSTOCK_NUM", FieldTypeEnum.STRING,MatchTypeEnum.LIKE,instockNum));
		}
		if(StringUtil.isNotEmpty(startTime)){
			conditions.add(new Condition("a.C_INSTOCK_TIME", FieldTypeEnum.DATE,MatchTypeEnum.GE,startTime));
		}
		if(StringUtil.isNotEmpty(endTime)){
			conditions.add(new Condition("a.C_INSTOCK_TIME", FieldTypeEnum.DATE,MatchTypeEnum.LE,endTime));
		}
		if(StringUtil.isNotEmpty(instockType)){
			conditions.add(new Condition("a.C_INSTOCK_TYPE", FieldTypeEnum.STRING,MatchTypeEnum.EQ,instockType));
		}
		if(StringUtil.isNotEmpty(approveStatus)){
			conditions.add(new Condition("a.C_APPROVE_STATUS", FieldTypeEnum.STRING,MatchTypeEnum.EQ,approveStatus));
		}
		if(StringUtil.isNotEmpty(unitId)&&!unitId.equals("undefined")){
			conditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ, unitId));
		}
		if(StringUtil.isNotEmpty(applicant)){
			conditions.add(new Condition("a.C_APPLICANT",FieldTypeEnum.STRING,MatchTypeEnum.EQ,applicant));
		}
		
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<InstockEntity> dataList=instockService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "入库管理报表模板.xlsx","入库管理.xlsx", resultMap);
	}
	
	/**
	 * 
	 * 入库管理查看页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月25日 下午4:17:05
	 * @lastModified
	 */
	@RequestMapping("/showPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		InstockEntity instockEntity = (InstockEntity)instockService.findById(id);
		WareHouseEntity wareHouseEntity = wareHouseService.findById(Long.valueOf(instockEntity.getWareHouseId()));
		instockEntity.setWareHouseName(wareHouseEntity.getWareHouseName());
		model.put("entity", instockEntity);
		model.put("entityJson", JsonUtil.toJson(instockEntity));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		
		//入库类型
		Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("INSTOCK_TYPE");
		ComboboxVO comboInstockTypeVO = new ComboboxVO();
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
			comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouse:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouse.getId().toString(), wareHouse.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		//获取获取列表
		conditions.clear();
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockEntity.getWareHouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		//单位下拉框 add   by  zhangxb   2018/3/1
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
				
		//物资属性向下拉框类型  add   by  zhangxb   2018/3/1
		Map<String, SysDictionaryVO> attributeTypeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
		ComboboxVO comboAttributeTypeVO = new ComboboxVO();
		for(String key : attributeTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = attributeTypeMap.get(key);
			comboAttributeTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("goodsAttribute", JsonUtil.toJson(comboAttributeTypeVO.getOptions()));
		return this.createModelAndView("cargo/instock/instockMoveShow", model);
	}
	
	/**
	 * 
	 * 入库管理删除功能（单行）
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月25日 下午4:17:24
	 * @lastModified
	 */
	@RequestMapping("/singleDelete/{id}")
	public @ResponseBody ResultObj singleDelete(HttpServletRequest request, @PathVariable Long id){
		InstockEntity instockEntity = instockService.findById(id);
		//删除入库单信息
		instockService.deleteEntity(id);
		//删除入库明细
		InstockDetailEntity instockDetailEntity = new InstockDetailEntity();
		instockDetailEntity.setInstockId(id);
		instockDetailService.deleteByCondition("deleteByInstockId", instockDetailEntity);
		//删除关联出入库明细表数据
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(instockEntity.getInstockNum());
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		//判断物资是否还在占用
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	@RequestMapping("/multipleDel")
	public @ResponseBody ResultObj multipleDelete(@RequestBody List<Integer> ids){
		for (Integer id : ids) {
			long longId = (long)id;
			InstockEntity instockEntity = this.getService().findById(longId);
			if(instockEntity != null){
				instockService.deleteEntity(longId);
				InstockDetailEntity instockDetailEntity = new InstockDetailEntity();
				instockDetailEntity.setInstockId(longId);
				instockDetailService.deleteByCondition("deleteByInstockId", instockDetailEntity);
				//删除关联出入库明细表数据
				InOutStockEntity inOutStock = new InOutStockEntity();
				inOutStock.setCode(instockEntity.getInstockNum());
				inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INSTOCKMOVE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * 
	 * 入库单提交审核
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月28日 下午4:30:23
	 * @lastModified
	 */
	@RequestMapping("/sureSubmitPerson")
	public ModelAndView sureSubmitPerson(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.IN_STOCK_PROCESS_KEY.getName()));
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		return this.createModelAndView("cargo/instock/sureSubmitPerson", model);
	}
	 
	/**
	 * 
	 * 选择下一节点提交人
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月31日 下午1:25:15
	 * @lastModified
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return instockService.submit(id,params);
	}
	
	/**
	 * @Description: 入库审批查询提交人(提交领导审核)
	 * @author liweiran
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,InstockExcuteEnum.LEADER.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/instock/sureSubmitPerson", model);
	}
	
	/**
	 * @Description: 入库审批查询提交人(再次提交)
	 * @author liweiran
	 * @Date 2017年6月5日 下午1:19:30
	 * @throws Exception
	 */
	@RequestMapping("/submitPersonAgain/{taskId}")
	public ModelAndView submitPersonAgain(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,InstockExcuteEnum.SUBMITAGAIN.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/instock/sureSubmitPerson", model);
	}
	
	/**
	 * 
	 * 审核页面初始化
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 上午10:03:51
	 * @lastModified
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		// 返回前台数据项
		InstockEntity instockEntity = (InstockEntity)instockService.findById(id);
//		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
//		String inTimeString = sdf.format(instockEntity.getInstockTime());
//		instockEntity.setShowInstockTime(inTimeString);
		model.put("entity", instockEntity);
		model.put("entityJson", JsonUtil.toJson(instockEntity));
		
		//入库类型
		Map<String, SysDictionaryVO> instockTypeMap  =  DictionaryUtil.getDictionaries("INSTOCK_TYPE");
		ComboboxVO comboInstockTypeVO = new ComboboxVO();
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = instockTypeMap.get(key);
			comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.IN_STOCK_PROCESS_KEY.getName()));
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(null != list && !list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		
		return this.createModelAndView("cargo/instock/instockApprove",model);
	}
	
	/**
	 * 库管员驳回
	 */
	@RequestMapping("/reject")
	public @ResponseBody ResultObj reject(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.BACK_END.getName());
		return instockService.approve(instockEntity, params);
	}
	
	/**
	 * 同意
	 */
	@RequestMapping("/pass")
	public @ResponseBody ResultObj pass(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.MANAGEAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.AGREE.getName());
		return instockService.approve(instockEntity, params);
	}
	/**
	 * 管理员同意
	 */
	@RequestMapping("/managePass")
	public @ResponseBody ResultObj managePass(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.AGREE.getName());
		return instockService.approve(instockEntity, params);
	}

	/**
	 * 管理员驳回
	 */
	@RequestMapping("/manageReject")
	public @ResponseBody ResultObj manageReject(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.BACK_END.getName());
		return instockService.approve(instockEntity, params);
	}
	/**
	 * 库管员提交领导审核
	 */
	@RequestMapping("/leader")
	public @ResponseBody ResultObj leader(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.LEADER.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.LEADER.getName());
		return instockService.approve(instockEntity, params);
	}
	
	/**
	 * 提交人再次提交按钮
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.STOREKEEPER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.SUBMITAGAIN.getName());
		return instockService.approve(instockEntity, params);
	}
	
	/**
	 * 提交人再次提交按钮
	 */
	@RequestMapping("/cancel")
	public @ResponseBody ResultObj cancel(@RequestBody InstockEntity t){
		InstockEntity instockEntity = instockService.findById(t.getId());
		instockEntity.setTaskId(t.getTaskId());
		instockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", InstockApproveStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), InstockExcuteEnum.CANCELPROCESS.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", InstockExcuteEnum.CANCELPROCESS.getName());
		return instockService.approve(instockEntity, params);
	}
	
	/**
	* 仓库下拉列表与场站关联
	* @author ly
	* @date 2018年5月21日 下午3:25:46 
	* @param request
	* @param brandId
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/getWareHouseList/{unitNameId}")
    public @ResponseBody ResultObj getWareHouseList(HttpServletRequest request, @PathVariable Long unitNameId){
		ResultObj result = new ResultObj();
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,unitNameId));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		
		
		result.setData(JsonUtil.toJson(comboWareHouseVO.getOptions()));
		return result;
	}
	/**
	 * 
	 * 入库审批人同意审批
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月1日 下午7:37:15
	 * @lastModified
	 */
	@RequestMapping("/submitPerson/{taskId}")
	public ModelAndView submitPerson(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/scrapLibrary/sureSubmitPerson", model);
	}
}