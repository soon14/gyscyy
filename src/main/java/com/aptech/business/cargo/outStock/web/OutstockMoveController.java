package com.aptech.business.cargo.outStock.web;

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
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.outStock.domain.OutstockEntity;
import com.aptech.business.cargo.outStock.service.OutstockService;
import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.business.cargo.outStockDetail.service.OutstockDetailService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.component.dictionary.OutstockApproveStatusEnum;
import com.aptech.business.component.dictionary.OutstockBreakApproveStatusEnum;
import com.aptech.business.component.dictionary.OutstockBreakEnum;
import com.aptech.business.component.dictionary.OutstockExcuteEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.business.wareHouse.wareHouseArea.service.WareHouseAreaService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
 * 出库管理配置控制器
 *
 * @author 
 * @created 2017-07-21 09:26:05
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/outstockMove")
public class OutstockMoveController extends BaseController<OutstockEntity> {

	@Autowired
	private OutstockService outstockService;

	@Autowired
	private OutstockDetailService outstockDetailService;

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
	private OperateLogService operateLogService;
	@Autowired
	private StockService stockService;

	@Autowired
	private ProcessNodeAuthService processNodeAuthService;

	@Autowired
	private WareHouseService wareHouseService;
	
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	@Override
	public IBaseEntityOperation<OutstockEntity> getService() {
		return outstockService;
	}

	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		//出库类型下拉框
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outstockTypeCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//审核状态
		Map<String, SysDictionaryVO> approveStatusMap  =  DictionaryUtil.getDictionaries("OUT_APPROVE_STATUS");
		ComboboxVO approveStatusVO = new ComboboxVO();
		for(String key : approveStatusMap.keySet()){
			SysDictionaryVO sysDictionaryVO = approveStatusMap.get(key);
			approveStatusVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("approveStatusCombobox", JsonUtil.toJson(approveStatusVO.getOptions()));
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
		model.put("sysUserName", JsonUtil.toJson(sysUserEntity.getName()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		List<Long> dutyIdList = new ArrayList<Long>();
		//库管员职位下人员id
		dutyIdList.addAll(outstockService.getDutyDetailId(params));
		model.put("isDuty", dutyIdList.contains(sysUserEntity.getId()));
		
		List<Long> userIdList = new ArrayList<Long>();
		//系统管理员的人员id
		userIdList.addAll(outstockService.getUserId(params));
		model.put("isSystemManage", userIdList.contains(sysUserEntity.getId()));
		
		return this.createModelAndView("cargo/outstock/outstockMoveList", model);

	}

	/**
	 * 
	 * 列表页面查询数据
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:09:18
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj list(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<OutstockEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		//按照时间倒序排列
		List<OutstockEntity> outStockEntities = outstockService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (outStockEntities != null) {
			if (pages != null) {
				resultObj.setData(outStockEntities);
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
	 * @author wangyue
	 * @created 2017年7月27日 下午7:10:19
	 * @lastModified
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userUnitId = String.valueOf(sysUserEntity.getUnitId());
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));

		//生成出库单号
		String prefix = "CK";	//入库单号前缀
		int sequenLength = 4;	//入库单号序列号位数
		String GenerateCode = outstockService.generateOutstockNum(prefix, userUnitId, sequenLength);
		model.put("genetateCode", GenerateCode);
		
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));

		//出库类型
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		
		model.put("outstockTypeCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userUnitId));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,1));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getId().toString(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		//单位下拉框 add 2018/4/26
		List<SysUnitEntity> treeNodeList = this.getUnitTreeNodeList1();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		return this.createModelAndView("cargo/outstock/outstockMoveAdd", model);
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
	 * 
	 * 添加页列表返回空值
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:12:32
	 * @lastModified
	 */
	@RequestMapping("/search/list")
	public @ResponseBody ResultListObj searchList(HttpServletRequest request,@RequestBody Map<String,Object> params){
		Page<OutstockEntity> pages =PageUtil.getPage(params);
		List<OutstockEntity> outnStockEntities = new ArrayList<OutstockEntity>();
		ResultListObj resultObj =new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if(outnStockEntities != null){
			if(pages != null){
				resultObj.setData(outnStockEntities);
				resultObj.setRecordsTotal(0);
			} else{
				resultObj.setRecordsTotal((long)outnStockEntities.size());
			}
		}
		return resultObj;
	}

	/**
	 * 
	 * 添加页面保存
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:13:10
	 * @lastModified
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveAddPage/{materialId}")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialCount = entityMap.get("materialCount").toString();
		boolean finalResult = outstockService.compareKc(materialId, materialCount);
		int flag = 0;
		if(!finalResult){
			flag = 1;
			resultObj.setResult("exception");
		}
//		String materialUnitName = entityMap.get("materialUnitName").toString();
//		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialId));
//		String management = materialCategoryEntity.getManagement();
		int x = -1;
			x = materialCount.indexOf(".");
				if(x>0){
					resultObj.setResult("exceptionOne");	
				}
		if(x == -1){
			//添加数据到入库表
			OutstockEntity outstockEntity = new OutstockEntity();
			
			Long unitId=Long.valueOf(entityMap.get("unitId").toString());
			
			outstockEntity.setOutstockNum(entityMap.get("outstockNum").toString());
			DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
			Date outstockdate = sdf.parse(entityMap.get("outstockTime").toString());
			outstockEntity.setOutstockTime(outstockdate);
			outstockEntity.setOutstockType(entityMap.get("outstockType").toString());
			outstockEntity.setWareHouseId(Long.valueOf(entityMap.get("wareHouseId").toString()));
			//通过用户id查找用户单位
			SysUserEntity applicantUser = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
			SysUnitEntity sysUnitEntity = sysUnitService.findById(applicantUser.getUnitId());
			//录入unitid 20180426
			if(unitId.equals(null)){
				outstockEntity.setUnitId(sysUnitEntity.getId());
			}else{
				outstockEntity.setUnitId(unitId);
			}
			outstockEntity.setApplicant(entityMap.get("applicant").toString());
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
			outstockEntity.setApplicantName(sysUserEntity.getName());
			outstockEntity.setApproveStatus("0");
			if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
				outstockEntity.setAttachment(entityMap.get("attachment").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
				outstockEntity.setRemark(entityMap.get("remark").toString());
			}
			outstockService.addEntity(outstockEntity);
			
			String materialIdStringTemp = entityMap.get("materialId").toString();
			String materialIdString = materialIdStringTemp.substring(1, materialIdStringTemp.length()-1);
			String materialCountStringTemp = entityMap.get("materialCount").toString();
			Map<String,Object> propertyMap = (Map<String, Object>) entityMap.get("propertyMap");
//			String materialGoodAreaStringTemp = entityMap.get("materialGoodArea").toString();
//			String materialGoodsPositionStringTemp = entityMap.get("materialGoodsPosition").toString();
			//添加数据到明细表
			OutstockDetailEntity outstockDetailEntity = new OutstockDetailEntity();
			String outscotckNumString = entityMap.get("outstockNum").toString();
			Long wareHouseId = Long.valueOf(entityMap.get("wareHouseId").toString());
			if(materialIdString.contains(",")){
				//有多条物资
//				outstockService.addPageSave(materialGoodAreaStringTemp,materialGoodsPositionStringTemp,materialCountStringTemp, materialIdString, outstockEntity, outscotckNumString,wareHouseId, sysUnitEntity,outstockDetailEntity, outstockdate);
				outstockService.addPageSave(materialCountStringTemp, materialIdString, outstockEntity, outscotckNumString,wareHouseId, sysUnitEntity,outstockDetailEntity, outstockdate,propertyMap);
			}else{
				//有一条物资
//				outstockService.addPageSaveSingle(materialGoodAreaStringTemp,materialGoodsPositionStringTemp,materialIdString, materialCountStringTemp, outstockEntity, outscotckNumString,wareHouseId, sysUnitEntity,outstockDetailEntity, outstockdate);
				outstockService.addPageSaveSingle(materialIdString, materialCountStringTemp, outstockEntity, outscotckNumString,wareHouseId, sysUnitEntity,outstockDetailEntity, outstockdate,propertyMap);
			}
			resultObj.setResult("success");
		}
		
		resultObj.setData(finalResult);
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
	 * @author wangyue
	 * @created 2017年7月27日 下午7:17:54
	 * @lastModified
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getModifyPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OutstockEntity outstockEntity = outstockService.findById(id);
		model.put("entity", outstockEntity);
		model.put("entityJson", JsonUtil.toJson(outstockEntity));

		//获取登录人
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));

		//出库类型
		Map<String,SysDictionaryVO> outstockTypeMap =DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key:outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO=outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outstockTypeCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
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
//		//获取获取列表
//		conditions.clear();
//		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getWareHouseId()));
//		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
//		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
//		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
//			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
//		}
//		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		//单位下拉框 add 2018/4/26
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
		conditions.clear();
		conditions.add(new Condition("a.C_OUTSTOCK_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		List<OutstockDetailEntity> outstockDetailList = outstockDetailService.findByCondition(conditions, null);
		if(!outstockDetailList.isEmpty()&&outstockDetailList!=null){
			OutstockDetailEntity outstockDetailEntity = outstockDetailList.get(0);
			conditions.clear();
			conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, outstockDetailEntity.getMaterialId()));
			conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
			List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
			model.put("materialId", stockEntities.get(0).getMaterialId());
			model.put("count", stockEntities.get(0).getInventoryQuantity());
		}
		return this.createModelAndView("cargo/outstock/outstockMoveEdit", model);
	}

	/**
	 * 
	 * 修改页面的列表检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @param outstockId
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:19:00
	 * @lastModified
	 */
	@RequestMapping("/editData/list/{outstockId}")
	public @ResponseBody ResultListObj EditList(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long outstockId){
		Page<OutstockDetailEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("materialId"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("a.C_OUTSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,outstockId));
		List<OutstockDetailEntity> outstockDetailEntities = outstockDetailService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (outstockDetailEntities != null) {
			if (pages != null) {
				resultObj.setData(outstockDetailEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}

	/**
	 * 
	 * 修改页面保存功能
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:19:39
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage/{materialId}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialCount = entityMap.get("materialCountArray").toString();
		boolean finalResult = outstockService.compareKc(materialId, materialCount);
		int flag = 0;
		if(!finalResult){
			flag = 1;
			resultObj.setResult("exception");
		}
//		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialId));
//		String management = materialCategoryEntity.getManagement();
		int x = -1;
			x = materialCount.indexOf(".");
				if(x>0){
					resultObj.setResult("exceptionOne");	
				}
		if(x == -1){
		Long outstockId = Long.valueOf(entityMap.get("id").toString());
		OutstockEntity outstockEntity = outstockService.findById(outstockId);
		
		Long unitId=Long.valueOf(entityMap.get("unitId").toString());
		
		outstockEntity.setOutstockNum(entityMap.get("outstockNum").toString());
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		Date outstockDate = sdf.parse(entityMap.get("outstockTime").toString());
		outstockEntity.setOutstockTime(outstockDate);
		outstockEntity.setOutstockType(entityMap.get("outstockType").toString());
		outstockEntity.setApplicant(entityMap.get("applicant").toString());
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entityMap.get("applicant").toString()));
		outstockEntity.setApplicantName(sysUserEntity.getName());
		outstockEntity.setWareHouseId(Long.valueOf(entityMap.get("wareHouseId").toString()));
		if(StringUtil.isNotEmpty((String)entityMap.get("attachment"))){
			outstockEntity.setAttachment(entityMap.get("attachment").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
			outstockEntity.setRemark(entityMap.get("remark").toString());
		}
		//通过用户id查找用户单位
		SysUnitEntity sysUnitEntity = sysUnitService.findById(sysUserEntity.getUnitId());
		//录入unitid 20180426
		if(unitId.equals(null)){
			outstockEntity.setUnitId(sysUnitEntity.getId());
		}else{
			outstockEntity.setUnitId(unitId);
		}
		outstockService.updateEntity(outstockEntity);
		
		List<String> materialIdList = (List<String>)entityMap.get("materialIdArray");
		String materialIdStringTemp = materialIdList.toString();
		String materialCountStringTemp = entityMap.get("materialCountArray").toString();
		String outstockNum = entityMap.get("outstockNum").toString();
		Long wareHouseId = Long.valueOf(entityMap.get("wareHouseId").toString());
		Map<String,Object> propertyMap = (Map<String, Object>) entityMap.get("propertyMap");
//		String materialGoodAreaStringTemp = entityMap.get("materialGoodArea").toString();
//		String materialGoodsPositionStringTemp = entityMap.get("materialGoodsPosition").toString();
		//添加数据到明细表
		if(null != materialIdList && materialIdList.size()>1){
			outstockService.editPageSave(/*materialGoodAreaStringTemp,materialGoodsPositionStringTemp,*/outstockId,materialIdList, materialIdStringTemp, materialCountStringTemp, outstockNum,wareHouseId, sysUnitEntity, outstockDate,propertyMap);
		}else if(materialIdList.size() == 1){
			outstockService.editPageSaveSingle(/*materialGoodAreaStringTemp,materialGoodsPositionStringTemp,*/outstockId,materialIdStringTemp, materialCountStringTemp, outstockNum,wareHouseId, sysUnitEntity, outstockDate);
		}
		resultObj.setResult("success");
		}
		resultObj.setData(finalResult);
		return resultObj;
	}

	/**
	 * 
	 * 出库管理查看页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:21:31
	 * @lastModified
	 */
	@RequestMapping("/showPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OutstockEntity outstockEntity = outstockService.findById(id);
		model.put("entity", outstockEntity);
		model.put("entityJson", JsonUtil.toJson(outstockEntity));

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));

		//出库类型
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outstockTypeCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
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
		conditions.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getWareHouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		return this.createModelAndView("cargo/outstock/outstockMoveShow", model);
	}
	/**
	 * 
	 * 出库管理删除功能（多行选择）
	 * 
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月31日 上午9:11:20
	 * @lastModified
	 */
	@RequestMapping("/multipleDel")
	public @ResponseBody ResultObj multipleDelete(@RequestBody List<Integer> ids){
		for (Integer id : ids) {
			long longId = (long)id;
			OutstockEntity outstockEntity = this.getService().findById(longId);
			if(outstockEntity != null){
				outstockService.deleteEntity(longId);
				OutstockDetailEntity outstockDetailEntity = new OutstockDetailEntity();
				outstockDetailEntity.setOutstockId(longId);
				outstockDetailService.deleteByCondition("deleteByOutstockId", outstockDetailEntity);
				//根据出入库单号删除出入库明细
				InOutStockEntity inOutStock = new InOutStockEntity();
				inOutStock.setCode(outstockEntity.getOutstockNum());
				inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}

	/**
	 * 
	 * 出库管理删除功能（单行）
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:24:03
	 * @lastModified
	 */
	@RequestMapping("/singleDelete/{id}")
	public @ResponseBody ResultObj singleDelete(HttpServletRequest request, @PathVariable Long id){
		OutstockEntity outstockEntity = outstockService.findById(id);
		outstockService.deleteEntity(id);
		OutstockDetailEntity outstockDetailEntity = new OutstockDetailEntity();
		outstockDetailEntity.setOutstockId(id);
		outstockDetailService.deleteByCondition("deleteByOutstockId", outstockDetailEntity);
		//根据出入库单号删除出入库明细
		InOutStockEntity inOutStock = new InOutStockEntity();
		inOutStock.setCode(outstockEntity.getOutstockNum());
		inOutStockService.deleteByCondition("deleteByStockCode", inOutStock);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OUTSTOCKMOVE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		//判断物资是否还在占用
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}

	/**
	 * 
	 * 出库管理导出功能
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author wangyue
	 * @created 2017年7月27日 下午7:24:37
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String outstockNum = req.getParameter("outstockNum");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String outstockType = req.getParameter("outstockType");
		String approveStatus = req.getParameter("approveStatus");
		String searchunitId = req.getParameter("searchunitId");
		String applicant = req.getParameter("applicant");
		
		Page<OutstockEntity> pages = new Page<OutstockEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(outstockNum)){
			conditions.add(new Condition("a.C_OUTSTOCK_NUM",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,outstockNum));
		}
		if(StringUtil.isNotEmpty(startTime)){
			conditions.add(new Condition("a.C_OUTSTOCK_TIME",FieldTypeEnum.DATE,MatchTypeEnum.GE,startTime));
		}
		if(StringUtil.isNotEmpty(endTime)){
			conditions.add(new Condition("a.C_OUTSTOCK_TIME",FieldTypeEnum.DATE,MatchTypeEnum.LE,endTime));
		}
		if(StringUtil.isNotEmpty(outstockType)){
			conditions.add(new Condition("a.C_OUTSTOCK_TYPE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,outstockType));
		}
		if(StringUtil.isNotEmpty(approveStatus)){
			conditions.add(new Condition("a.C_APPROVE_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,approveStatus));
		}
		if(StringUtil.isNotEmpty(searchunitId)&&!searchunitId.equals("undefined")){
			conditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchunitId));
		}
		if(StringUtil.isNotEmpty(applicant)){
			conditions.add(new Condition("a.C_APPLICANT",FieldTypeEnum.STRING,MatchTypeEnum.EQ,applicant));
		}
		
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<OutstockEntity> dataList=outstockService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "出库管理报表模板.xlsx","出库管理.xlsx", resultMap);
	}

	/**
	 * 
	 * 出库单提交审核
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @editer zhangxb
	 * @created 2017年8月1日 下午6:35:30
	 * @lastModified
	 */
	@RequestMapping("/sureSubmitPerson/{id}")
	public ModelAndView sureSubmitPerson(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Condition> conditions=new ArrayList<Condition>();
		OutstockEntity t = outstockService.findById(id);
		if("1".equals(t.getOutstockType())){
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.OUT_STOCK_PROCESS_KEY.getName()));
		}else{
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.OUT_STOCK_DRAW_PROCESS_KEY.getName()));
		}
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}

	/**
	 * 
	 * 调用工作流开始流程
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午6:35:49
	 * @lastModified
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return outstockService.submit(id, params);
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
	 * @created 2017年8月1日 下午6:59:47
	 * @lastModified
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		// 返回前台数据项
		OutstockEntity outstockEntity = outstockService.findById(id);
		model.put("entity", outstockEntity);
		model.put("entityJson", JsonUtil.toJson(outstockEntity));

		//出库类型
		Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
		ComboboxVO comboOutstockTypeVO = new ComboboxVO();
		for(String key : outstockTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
			comboOutstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("outstockTypeCombobox", JsonUtil.toJson(comboOutstockTypeVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
		if("1".equals(outstockEntity.getOutstockType())){
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.OUT_STOCK_PROCESS_KEY.getName()));
		}else{
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.OUT_STOCK_DRAW_PROCESS_KEY.getName()));
			
		}
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouse:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouse.getId().toString(), wareHouse.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		
		conditionsLc.clear();
		conditionsLc.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, outstockEntity.getWareHouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditionsLc, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));

		return this.createModelAndView("cargo/outstock/outstockApprove",model);
	}

	/**
	 * 
	 * 查库管审批处理人
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:37:15
	 * @lastModified
	 */
	@RequestMapping("/submitPersonStorekeeper/{taskId}")
	public ModelAndView submitPersonStorekeeper(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockExcuteEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}

	/**
	 * 
	 * 查领导审核处理人
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:37:47
	 * @lastModified
	 */
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockExcuteEnum.LEADER.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 查领导审核处理人
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:37:47
	 * @lastModified
	 */
	@RequestMapping("/submitPerson/{taskId}")
	public ModelAndView submitPerson(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockExcuteEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}

	/**
	 * 
	 * 查再次提交处理人
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:38:03
	 * @lastModified
	 */
	@RequestMapping("/submitPersonAgain/{taskId}")
	public ModelAndView submitPersonAgain(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockExcuteEnum.SUBMITAGAIN.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}

	/**
	 * 
	 * 驳回按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/reject")
	public @ResponseBody ResultObj reject(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.BACK_END.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 报废出库查再次提交处理人
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:38:03
	 * @lastModified
	 */
	@RequestMapping("/submitBreakAgain/{taskId}")
	public ModelAndView submitBreakAgain(HttpServletRequest request,@PathVariable String taskId) {
		//审批页面点击签发按钮的时候，把下一步的人查询出来
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockBreakEnum.SUBMITAGAIN.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 再次提交按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:43:27
	 * @lastModified
	 */
	@RequestMapping("/submitBreakAgain")
	public @ResponseBody ResultObj submitBreakAgain(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.SUBMITAGAIN.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 取消流程按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:44:13
	 * @lastModified
	 */
	@RequestMapping("/breakCancel")
	public @ResponseBody ResultObj breakCancel(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.CANCELPROCESS.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.CANCELPROCESS.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 报废出库管理人员审批人审批
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:37:15
	 * @lastModified
	 */
	@RequestMapping("/submitProductBreakStore/{taskId}")
	public ModelAndView submitProductBreakStore(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockBreakEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 报废出库生技部审批人审批
	 * 
	 * @param @param request
	 * @param @param taskId
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:37:15
	 * @lastModified
	 */
	@RequestMapping("/submitLeader/{taskId}")
	public ModelAndView submitPersonStore(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,OutstockBreakEnum.PRODUCTAGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 生产副总审核同意按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:41:01
	 * @lastModified
	 */
	@RequestMapping("/leaderPass")
	public @ResponseBody ResultObj leaderPass(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.END.getCode());
		params.put("DRAWOUTFLOW", OutstockBreakApproveStatusEnum.DRAWOUTFLOW.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.LEADER.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.LEADER.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 生技部送到生产副总审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/submitLeaderBreak")
	public @ResponseBody ResultObj leaderBreak(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.LEADERAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.PRODUCTAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.PRODUCTAGREE.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 *送到生技部审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/productBreak")
	public @ResponseBody ResultObj productBreak(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.PRODUCTAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.AGREE.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 * 报废出库驳回按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/breakReject")
	public @ResponseBody ResultObj breakReject(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockBreakApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockBreakEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockBreakEnum.BACK_END.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 同意按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:41:01
	 * @lastModified
	 */
	@RequestMapping("/pass")
	public @ResponseBody ResultObj pass(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		outstockEntity.setStoreKeeper(sysUserEntity.getName());
		return outstockService.approve(outstockEntity, params);
	}
	/**
	 * 
	 * 管理人员同意按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:41:01
	 * @lastModified
	 */
	@RequestMapping("/manageAgree")
	public @ResponseBody ResultObj manageAgree(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		outstockEntity.setApproveUser(sysUserEntity.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 * 检修班长送到库管员审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/stockKepper")
	public @ResponseBody ResultObj stockKepper(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.STOREKEEPER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.AGREE.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 * 提交领导审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:42:25
	 * @lastModified
	 */
	@RequestMapping("/leader")
	public @ResponseBody ResultObj leader(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.LEADER.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.LEADER.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 * 再次提交按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:43:27
	 * @lastModified
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.STOREKEEPER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.SUBMITAGAIN.getName());
		return outstockService.approve(outstockEntity, params);
	}

	/**
	 * 
	 * 取消流程按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月1日 下午7:44:13
	 * @lastModified
	 */
	@RequestMapping("/cancel")
	public @ResponseBody ResultObj cancel(@RequestBody OutstockEntity t){
		OutstockEntity outstockEntity = outstockService.findById(t.getId());
		outstockEntity.setTaskId(t.getTaskId());
		outstockEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OutstockApproveStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), OutstockExcuteEnum.CANCELPROCESS.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", OutstockExcuteEnum.CANCELPROCESS.getName());
		return outstockService.approve(outstockEntity, params);
	}
	
	@RequestMapping("/compare/{materialId}/{materialCount}")
	public @ResponseBody ResultObj compare(HttpServletRequest request,@PathVariable String materialId, @PathVariable String materialCount){
		ResultObj resultObj = new ResultObj();
		boolean result = false;
		if(materialId.contains(",")){
			//多条物资
			String[] materialIdArray = materialId.split(",");
			for(int i=0;i<materialIdArray.length;i++){
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialIdArray[i]));
				List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
				StockEntity stockEntity = list_stockEntity.get(0);
				Long kcsl = Long.parseLong(stockEntity.getInventoryQuantity());
				Long cksl = Long.parseLong(materialCount);
				if(kcsl>=cksl){
					result = true;
				}
			}
		}else{
			//单条物资
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("materialId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,materialId));
			List<StockEntity> list_stockEntity = stockService.findByCondition(conditions, null);
			Long kcsl = Long.parseLong(list_stockEntity.get(0).getInventoryQuantity());
			Long cksl = Long.parseLong(materialCount);
			if(kcsl>=cksl){
				result = true;
			}
		}
		resultObj.setData(result);
		return resultObj;
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
}