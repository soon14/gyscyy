package com.aptech.business.cargo.scarpLibrary.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.outStock.service.OutstockService;
import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.business.cargo.scarpLibrary.service.ScrapLibraryService;
import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.business.cargo.scrapLibraryDetail.service.ScrapLibraryDetailService;
import com.aptech.business.cargo.scrapLibraryDetailOut.domain.ScrapLibraryDetailOutEntity;
import com.aptech.business.cargo.scrapLibraryDetailOut.service.ScrapLibraryDetailOutService;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScrapLibraryHandleStatusEnum;
import com.aptech.business.component.dictionary.ScrapLibraryStatusEnum;
import com.aptech.business.component.dictionary.ScrapSourceEnum;
import com.aptech.business.component.dictionary.ScrapstockApproveStatusEnum;
import com.aptech.business.component.dictionary.ScrapstockExcuteEnum;
import com.aptech.business.component.dictionary.ScrapstockOutExcuteEnum;
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
 * 报废库管理配置控制器
 *
 * @author 
 * @created 2018-03-15 15:37:32
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/scrapLibrary")
public class ScrapLibraryController extends BaseController<ScrapLibraryEntity> {

	@Autowired
	private OutstockService outstockService;
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private DefinitionService definitionService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private ProcessNodeAuthService processNodeAuthService;
	@Autowired
	private ScrapLibraryService scrapLibraryService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private ScrapLibraryDetailService scrapLibraryDetailService;
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private ScrapLibraryDetailOutService scrapLibraryDetailOutService;
	@Autowired
	private StockService stockService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Override
	public IBaseEntityOperation<ScrapLibraryEntity> getService() {
		return scrapLibraryService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{type}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//报废来源
		ComboboxVO scrapSourceCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> scrapSourceMap = DictionaryUtil.getDictionaries("SCRAPSOUCE");
		for(String key : scrapSourceMap.keySet()){
			SysDictionaryVO scrapSourceVO = scrapSourceMap.get(key);
			scrapSourceCombobox.addOption(scrapSourceVO.getCode(), scrapSourceVO.getName());
		}
		model.put("scrapSourceCombobox", JsonUtil.toJson(scrapSourceCombobox.getOptions()));
		//入库类型
		ComboboxVO instockTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> instockTypeMap = DictionaryUtil.getDictionaries("SCRAPTYPE");
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO instockTypeVO = instockTypeMap.get(key);
			instockTypeCombobox.addOption(instockTypeVO.getCode(), instockTypeVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(instockTypeCombobox.getOptions()));
		//处理方式
		ComboboxVO processModeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> processModeMap = DictionaryUtil.getDictionaries("PROCESSMODE");
		for(String key : processModeMap.keySet()){
			SysDictionaryVO processModeVO = processModeMap.get(key);
			processModeCombobox.addOption(processModeVO.getCode(), processModeVO.getName());
		}
		model.put("processModeCombobox", JsonUtil.toJson(processModeCombobox.getOptions()));
		//审批状态
		ComboboxVO statusCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> statusMap = DictionaryUtil.getDictionaries("SCRAPLIBRARYSTATUS");
		for(String key : statusMap.keySet()){
			SysDictionaryVO statusVO = statusMap.get(key);
			statusCombobox.addOption(statusVO.getCode(), statusVO.getName());
		}
		model.put("statusCombobox", JsonUtil.toJson(statusCombobox.getOptions()));
		ComboboxVO comboScrapLibraryVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryCombobox", JsonUtil.toJson(comboScrapLibraryVO.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		List<Long> dutyIdList = new ArrayList<Long>();
		//获取登录人信息
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
		model.put("sysUserName", JsonUtil.toJson(sysUserEntity.getName()));
		model.put("loginName", JsonUtil.toJson(sysUserEntity.getLoginName()));
		//库管员职位下人员id
		dutyIdList.addAll(scrapLibraryService.getDutyDetailId(params));
		model.put("isDuty", dutyIdList.contains(sysUserEntity.getId()));
		
		List<Long> userIdList = new ArrayList<Long>();
		//系统管理员的人员id
		userIdList.addAll(scrapLibraryService.getUserId(params));
		model.put("isSystemManage", userIdList.contains(sysUserEntity.getId()));
		SysUserEntity sysUser = RequestContext.get().getUser();
		model.put("sysUser", sysUser);
		if(type==1){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryList", model);
		}else if(type==3){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryListOut", model);
		}else{
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryListHandle", model);
		}
		
	}
	
	/**
	 * 列表数据查询
	 * @param request
	 * @param params
	 * @return
	 * date：20180427
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj listData(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//按照时间倒序排列
		Page<ScrapLibraryEntity> pages = PageUtil.getPage(params);
		pages.setOrders(OrmUtil.changeMapToOrders(params));
		pages.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		conditions.add(new Condition("t.C_STATION_SOURCE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<ScrapLibraryEntity> inOutStockEntities = scrapLibraryService.findByCondition(conditions, pages);
		
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (inOutStockEntities != null) {
			if (pages != null) {
				resultObj.setData(inOutStockEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{type}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long type){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门
		List<SysUnitEntity> treeNodeList = this.getUnitTreeNodeList1();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryCombobox", JsonUtil.toJson(comboScrapLibraryVO.getOptions()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		String userUnitId = String.valueOf(userEntity.getUnitId());
		model.put("userEntity", userEntity);
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));
		//报废来源   不按照设计模型做  那个有问题
		/*ComboboxVO scrapSourceCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> scrapSourceMap = DictionaryUtil.getDictionaries("SCRAPSOUCE");
		for(String key : scrapSourceMap.keySet()){
			SysDictionaryVO scrapSourceVO = scrapSourceMap.get(key);
			scrapSourceCombobox.addOption(scrapSourceVO.getCode(), scrapSourceVO.getName());
		}
		model.put("scrapSourceCombobox", JsonUtil.toJson(scrapSourceCombobox.getOptions()));*/
		//入库类型
		ComboboxVO instockTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> instockTypeMap = DictionaryUtil.getDictionaries("SCRAPTYPE");
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO instockTypeVO = instockTypeMap.get(key);
			instockTypeCombobox.addOption(instockTypeVO.getCode(), instockTypeVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(instockTypeCombobox.getOptions()));
		//处理方式 不按照设计模型做  那个有问题
		/*ComboboxVO processModeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> processModeMap = DictionaryUtil.getDictionaries("PROCESSMODE");
		for(String key : processModeMap.keySet()){
			SysDictionaryVO processModeVO = processModeMap.get(key);
			processModeCombobox.addOption(processModeVO.getCode(), processModeVO.getName());
		}
		model.put("processModeCombobox", JsonUtil.toJson(processModeCombobox.getOptions()));*/
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
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//物资属性向下拉框类型  add   by  zhangxb   2018/3/1
		Map<String, SysDictionaryVO> attributeTypeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
		ComboboxVO comboAttributeTypeVO = new ComboboxVO();
		for(String key : attributeTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = attributeTypeMap.get(key);
			comboAttributeTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("goodsAttribute", JsonUtil.toJson(comboAttributeTypeVO.getOptions()));
		model.put("type", type);
		if(type==1){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryAdd", model);
		}else if(type==3){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryAddOut", model);
		}else{
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryAddHandle", model);
		}
		
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
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{type}/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id, @PathVariable Long type){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ScrapLibraryEntity scrapLibraryEntity = (ScrapLibraryEntity)scrapLibraryService.findById(id);
		model.put("scrapLibraryEntity", scrapLibraryEntity);
		model.put("id", id);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
		//获取系统时间
		Date now = new Date(); 
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		String time = sdf.format( now ); 
		model.put("systemdate", JsonUtil.toJson(time));
		// 部门
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryCombobox", JsonUtil.toJson(comboScrapLibraryVO.getOptions()));
		SysUserEntity userEntity = RequestContext.get().getUser();
		model.put("userEntity", userEntity);
		//报废来源 不按照设计模型做  那个有问题
		/*ComboboxVO scrapSourceCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> scrapSourceMap = DictionaryUtil.getDictionaries("SCRAPSOUCE");
		for(String key : scrapSourceMap.keySet()){
			SysDictionaryVO scrapSourceVO = scrapSourceMap.get(key);
			scrapSourceCombobox.addOption(scrapSourceVO.getCode(), scrapSourceVO.getName());
		}
		model.put("scrapSourceCombobox", JsonUtil.toJson(scrapSourceCombobox.getOptions()));*/
		//入库类型
		ComboboxVO instockTypeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> instockTypeMap = DictionaryUtil.getDictionaries("SCRAPTYPE");
		for(String key : instockTypeMap.keySet()){
			SysDictionaryVO instockTypeVO = instockTypeMap.get(key);
			instockTypeCombobox.addOption(instockTypeVO.getCode(), instockTypeVO.getName());
		}
		model.put("instockTypeCombobox", JsonUtil.toJson(instockTypeCombobox.getOptions()));
		//处理方式  不按照设计模型做  那个有问题
		/*ComboboxVO processModeCombobox = new ComboboxVO();
		Map<String, SysDictionaryVO> processModeMap = DictionaryUtil.getDictionaries("PROCESSMODE");
		for(String key : processModeMap.keySet()){
			SysDictionaryVO processModeVO = processModeMap.get(key);
			processModeCombobox.addOption(processModeVO.getCode(), processModeVO.getName());
		}
		model.put("processModeCombobox", JsonUtil.toJson(processModeCombobox.getOptions()));*/
		if(scrapLibraryEntity.getWarehouseId()!=null){
			WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
			model.put("sareHouseEntity", sareHouseEntity);
		}
		
		//获取获取列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList = sysUserService.findByCondition(
				conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//物资属性向下拉框类型  add   by  zhangxb   2018/3/1
		Map<String, SysDictionaryVO> attributeTypeMap  =  DictionaryUtil.getDictionaries("GOODS_ATTRIBUTE");
		ComboboxVO comboAttributeTypeVO = new ComboboxVO();
		for(String key : attributeTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = attributeTypeMap.get(key);
			comboAttributeTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("goodsAttribute", JsonUtil.toJson(comboAttributeTypeVO.getOptions()));
		model.put("type", type);
		conditions.clear();
		conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
		List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions, null);
		if(scrapLibraryDetailEntities!=null&&!scrapLibraryDetailEntities.isEmpty()){
			ScrapLibraryDetailEntity scrapLibraryDetailEntity = scrapLibraryDetailEntities.get(0);
			MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(scrapLibraryDetailEntity.getMetrialId()));
			model.put("management", materialCategoryEntity.getManagement());
			conditions.clear();
			conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
			conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
			List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
//			model.put("count", stockEntities.get(0).getInventoryQuantity());
			model.put("materialId", scrapLibraryDetailEntity.getMetrialId());
			conditions.clear();
			conditions.add(new Condition("sk.C_MATERIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryDetailEntity.getMetrialId()));
			conditions.add(new Condition("sk.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
			List<StockEntity> stockEntitiesIn = stockService.findByCondition(conditions, null);
			if(!stockEntitiesIn.isEmpty()){
				model.put("countIn", stockEntitiesIn.get(0).getInventoryQuantity());
			}
			
		}
		
		if(type==1){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryEdit", model);
		}else if(type==3){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryEditOut", model);
		}else{
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryEditHandle", model);
		}
		
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
	 * @author zhangxb
	 * @created 2017年7月23日 下午12:59:34
	 * @lastModified
	 */
	@RequestMapping("/editData/list/{instockId}")
	public @ResponseBody ResultListObj EditList(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long instockId){
		Page<ScrapLibraryDetailEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.asc("C_METRIAL_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockId));
		List<ScrapLibraryDetailEntity> scrapLibraryDetailEntity = scrapLibraryDetailService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (scrapLibraryDetailEntity != null) {
			if (pages != null) {
				resultObj.setData(scrapLibraryDetailEntity);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
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
	 * @author zhangxb
	 * @created 2017年7月23日 下午12:59:34
	 * @lastModified
	 */
	@RequestMapping("/editDataOut/list/{instockId}")
	public @ResponseBody ResultListObj EditListOut(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long instockId){
		Page<ScrapLibraryDetailOutEntity> pages = PageUtil.getPage(params);
//		pages.addOrder(Sort.asc("C_METRIAL_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_SCRAP_LIBRARY_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockId));
		List<ScrapLibraryDetailOutEntity> scrapLibraryDetailOutEntity = scrapLibraryDetailOutService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (scrapLibraryDetailOutEntity != null) {
			if (pages != null) {
				resultObj.setData(scrapLibraryDetailOutEntity);
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
	 * @author zhangxb
	 * @created 2017年7月26日 上午9:59:16
	 * @lastModified
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveEditPage/{materialId}")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialCount = entityMap.get("materialCount").toString();
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
		if(x == -1&&flag!=1){
			
			Long scrapId = Long.valueOf(entityMap.get("id").toString());
			ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(scrapId);
			scrapLibraryEntity.setFileId(entityMap.get("fileId").toString());
			DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			scrapLibraryEntity.setInstockTime(sdf.parse(entityMap.get("instockTime").toString()));
//			scrapLibraryEntity.setInstockType(entityMap.get("instockType").toString());
			//scrapLibraryEntity.setProcessMode(entityMap.get("processMode").toString());
			//scrapLibraryEntity.setScrapSource(ScrapSourceEnum.MANUAL.getCode());
			scrapLibraryEntity.setStationSourceId(entityMap.get("stationSourceId").toString());
			scrapLibraryEntity.setStatus(ScrapLibraryStatusEnum.PENDING.getCode());
			scrapLibraryEntity.setWarehouseId(entityMap.get("warehouseId").toString());
			scrapLibraryEntity.setUserId(entityMap.get("userId").toString());
			if(entityMap.get("stockId")!=null){
				scrapLibraryEntity.setStockId(entityMap.get("stockId").toString());
			}
			
			if(entityMap.get("instockType")!=null){
				scrapLibraryEntity.setInstockType(entityMap.get("instockType").toString());
			}else{
				scrapLibraryEntity.setInstockType(null);
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
				scrapLibraryEntity.setRemark(entityMap.get("remark").toString());
			}
			scrapLibraryService.updateEntity(scrapLibraryEntity);
			Date instockTime = scrapLibraryEntity.getInstockTime();
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
			List<String> materialIdList = (List<String>)entityMap.get("materialIdArray");
			String materialIdStringTemp = materialIdList.toString();
			String materialCountStringTemp = entityMap.get("materialCount").toString();
			Long wareHouseId = Long.valueOf(entityMap.get("warehouseId").toString());
			Map<String,Object> propertyMap = (Map<String, Object>) entityMap.get("propertyMap");
			//添加数据到明细表
			if(null != materialIdList && materialIdList.size()>1){
				scrapLibraryService.editPageSave(scrapId,materialIdList, materialIdStringTemp, materialCountStringTemp,wareHouseId, instockTime,sysUnitEntity,propertyMap);
			}else if(materialIdList.size() == 1){
				scrapLibraryService.editPageSaveSingle(scrapId,materialIdStringTemp, materialCountStringTemp,wareHouseId, sysUnitEntity, instockTime,propertyMap);
			}
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SCRAPLIBRARY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		resultObj.setData(finalResult);
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
	 * @author zhangxb
	 * @created 2017年7月26日 上午9:59:16
	 * @lastModified
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveEditPageOut/{materialId}")
	public @ResponseBody ResultObj saveEditPageOut(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialUnitName = String.valueOf(entityMap.get("materialUnitName"));
		String materialCode = String.valueOf(entityMap.get("materialCode"));
		String materialName = String.valueOf(entityMap.get("materialName"));
		String materialModel = String.valueOf(entityMap.get("materialModel"));
		String materialManufacturer = String.valueOf(entityMap.get("materialManufacturer"));
		String count = String.valueOf(entityMap.get("count"));
		String goodsAttribute = String.valueOf(entityMap.get("goodsAttribute"));
		String goodsValidity = String.valueOf(entityMap.get("goodsValidity"));
		String goodsPrice = String.valueOf(entityMap.get("goodsPrice"));
		String code = String.valueOf(entityMap.get("code"));
		String unitId = String.valueOf(entityMap.get("stationSourceId"));
		String instockTime = String.valueOf(entityMap.get("instockTime"));
		String userId = String.valueOf(entityMap.get("userId"));
		String remark = String.valueOf(entityMap.get("remark"));
		String fileId = String.valueOf(entityMap.get("fileId"));
		String id = String.valueOf(entityMap.get("id"));
		int x = -1;
			x = count.indexOf(".");
			if(x>0){
				resultObj.setResult("exceptionOne");	
			}
		if(x == -1){
			ScrapLibraryEntity scrapLibraryEntity =scrapLibraryService.findById(Long.valueOf(id));
			if(entityMap.get("fileId")==null){
				scrapLibraryEntity.setFileId("[]");
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			scrapLibraryEntity.setInstockTime(sdf1.parse(instockTime));
			if(remark!=null){
				scrapLibraryEntity.setRemark(remark);
			}
			scrapLibraryEntity.setUserId(userId);
			scrapLibraryEntity.setStationSourceId(unitId);
			scrapLibraryEntity.setType("3");
			scrapLibraryEntity.setFileId(entityMap.get("fileId").toString());
			scrapLibraryEntity.setStatus(ScrapLibraryStatusEnum.PENDING.getCode());
			scrapLibraryService.updateEntity(scrapLibraryEntity);
			String[] counts = count.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String[] goodsPrices = goodsPrice.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String[] goodsValidities = goodsValidity.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String[] materialIds = materialId.split(",");
			
			
				for(int i = 0;i<materialIds.length;i++){
					List<Condition> conditions = new ArrayList<Condition>();
					conditions.add(new Condition("a.C_INSTOCK_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getId()));
					conditions.add(new Condition("a.C_METRIAL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialIds[i]));
					List<ScrapLibraryDetailEntity> scrapLibraryDetailEntities = scrapLibraryDetailService.findByCondition(conditions, null);
					if(!scrapLibraryDetailEntities.isEmpty()&&scrapLibraryDetailEntities!=null){
				ScrapLibraryDetailEntity scrapLibraryDetailEntity = scrapLibraryDetailEntities.get(0);
				scrapLibraryDetailEntity.setCount(counts[i]);
				scrapLibraryDetailEntity.setGoodsPrice(goodsPrices[i]);
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				scrapLibraryDetailEntity.setGoodsValidity(sdf.parse(goodsValidities[i]));
				double countD = Double.parseDouble(counts[i]);
				double goodsPriceD = Double.parseDouble(goodsPrices[i]);
				double totalPrice = countD*goodsPriceD;
				scrapLibraryDetailEntity.setTotalPrice(totalPrice);
				scrapLibraryDetailService.updateEntity(scrapLibraryDetailEntity);
				}
			else{
					ScrapLibraryDetailEntity scrapLibraryDetailEntity = new ScrapLibraryDetailEntity();
					scrapLibraryDetailEntity.setInstockId(scrapLibraryEntity.getId());
					scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialIds[i]));
					scrapLibraryDetailEntity.setCount(counts[i]);
					scrapLibraryDetailEntity.setGoodsPrice(goodsPrices[i]);
					scrapLibraryDetailEntity.setGoodsAttribute(goodsAttribute);;
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
					scrapLibraryDetailEntity.setGoodsValidity(sdf.parse(goodsValidities[i]));
					double countD = Double.parseDouble(counts[i]);
					double goodsPriceD = Double.parseDouble(goodsPrices[i]);
					double totalPrice = countD*goodsPriceD;
					scrapLibraryDetailEntity.setTotalPrice(totalPrice);
					scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);
				
			}
				}
			resultObj.setResult("success");
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SCRAPLIBRARY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{type}/{id}")
	public ModelAndView getDetail(HttpServletRequest request, @PathVariable Long id, @PathVariable Long type){
		Map<String, Object> model = new HashMap<String, Object>();
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(id);
		model.put("scrapLibraryEntity", scrapLibraryEntity);
		model.put("id", id);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
		SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
		model.put("SysUnitEntity", SysUnitEntity);
		SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
		model.put("sysUserEntity", sysUserEntity);
		if(scrapLibraryEntity.getWarehouseId()!=null){
			//获取获取列表
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
			List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
			ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
			for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
				comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
			}
			model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
			WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
			model.put("sareHouseEntity", sareHouseEntity);
		}
		if(type==1){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryDetail", model);
		}else if(type==3){
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryDetailOut", model);
		}else{
			return this.createModelAndView("cargo/scrapLibrary/scrapLibraryDetailHandle", model);
		}
		
	}
	/**
	 * @Description:   导出报废申请
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<ScrapLibraryEntity> page = new Page<ScrapLibraryEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));//page.addOrder(Sort.desc("instockTime"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<Condition> condition = OrmUtil.changeMapToCondition(params);
		//List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(params, null);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		condition.add(new Condition("t.C_STATION_SOURCE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(condition, page);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "库内申请模板.xlsx","库内申请.xlsx", resultMap);
	}
	/**
	 * @Description:   导出报废申请
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcelOut")
	public void exportExcelOut(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<ScrapLibraryEntity> page = new Page<ScrapLibraryEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));//page.addOrder(Sort.desc("instockTime"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<Condition> condition = OrmUtil.changeMapToCondition(params);
		//List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(params, null);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		condition.add(new Condition("t.C_STATION_SOURCE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(condition, page);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "库外申请模板.xlsx","库外申请.xlsx", resultMap);
	}
	/**
	 * @Description:   导出报废处理
	 * @author         changl 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@RequestMapping("/exportExcelHandle")
	public void exportExcelHandle(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		String conditions=req.getParameter("conditions");
		Page<ScrapLibraryEntity> page = new Page<ScrapLibraryEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));//page.addOrder(Sort.desc("instockTime"));
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<Condition> condition = OrmUtil.changeMapToCondition(params);
		//List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(params, null);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		condition.add(new Condition("t.C_STATION_SOURCE_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<ScrapLibraryEntity> dataList=scrapLibraryService.findByCondition(condition, page);
		for (int i = 0; i < dataList.size(); i++) {
			dataList.get(i).setNumber(i+1);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "报废处理模板.xlsx","报废处理.xlsx", resultMap);
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
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockExcuteEnum.SUBMITAGAIN.getId().toString());
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
	public @ResponseBody ResultObj submitBreakAgain(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.SUBMITAGAIN.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	public @ResponseBody ResultObj breakCancel(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.CANCELPROCESS.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.CANCELPROCESS.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockExcuteEnum.AGREE.getId().toString());
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
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockExcuteEnum.PRODUCTAGREE.getId().toString());
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
	public @ResponseBody ResultObj leaderPass(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.LEADER.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.LEADER.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setApproveUser(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	public @ResponseBody ResultObj leaderBreak(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.LEADERAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.PRODUCTAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.PRODUCTAGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	public @ResponseBody ResultObj productBreak(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.PRODUCTAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setStoreKeeper(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	public @ResponseBody ResultObj breakReject(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 出库单提交审核
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:35:30
	 * @lastModified
	 */
	@RequestMapping("/sureSubmitPerson/{id}")
	public ModelAndView sureSubmitPerson(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Condition> conditions=new ArrayList<Condition>();
		ScrapLibraryEntity t = scrapLibraryService.findById(id);
		if("1".equals(t.getType())){//1报废库内申请
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.OUT_SCRAP_STOCK_DRAW_PROCESS_KEY.getName()));
			
		}else if("3".equals(t.getType())){//3报废库外申请
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.IN_STOCK_DRAW_PROCESS_KEY.getName()));
			
		}else if("2".equals(t.getType())){
			conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ,ProcessMarkEnum.SCRAPLIBRARY_HANDLE_PROCESS_KEY.getName()));
		}
		List<DefinitionEntity> defList=definitionService.findByCondition(conditions, null);
		if(!defList.isEmpty()){
			String modelId=defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			model.put("userList", userList);
		}
		return this.createModelAndView("cargo/scrapLibrary/sureSubmitPerson", model);
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
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:35:49
	 * @lastModified
	 */
	@RequestMapping(value = "/submit/{id}")
	public @ResponseBody ResultObj submit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return scrapLibraryService.submit(id, params);
	}
	/**
	 * 
	 * 提交报废处理
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param params
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:35:49
	 * @lastModified
	 */
	@RequestMapping(value = "/submitHandle/{id}")
	public @ResponseBody ResultObj submitHandle(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		return scrapLibraryService.submitHandle(id, params);
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
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:59:47
	 * @lastModified
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(id);
		model.put("scrapLibraryEntity", scrapLibraryEntity);
		model.put("id", id);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
		SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
		model.put("SysUnitEntity", SysUnitEntity);
		//获取申请人
		SysUserEntity applyUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
		model.put("applyUserEntity", applyUserEntity);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
			//获取获取列表
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
			List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
			ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
			for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
				comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
			}
			model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
			WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
			model.put("sareHouseEntity", sareHouseEntity);
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.OUT_SCRAP_STOCK_DRAW_PROCESS_KEY.getName()));
		
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}

		return this.createModelAndView("cargo/scrapLibrary/scrapLibraryApprove",model);
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
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:59:47
	 * @lastModified
	 */
	@RequestMapping("/approveOut/{id}/{type}")
	public ModelAndView approveOut(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(id);
		model.put("scrapLibraryEntity", scrapLibraryEntity);
		model.put("id", id);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
		SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
		model.put("SysUnitEntity", SysUnitEntity);
		//获取申请人
		SysUserEntity applyUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
		model.put("applyUserEntity", applyUserEntity);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.IN_STOCK_DRAW_PROCESS_KEY.getName()));
		
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		
		return this.createModelAndView("cargo/scrapLibrary/scrapLibraryApproveOut",model);
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
	@RequestMapping("/outReject")
	public @ResponseBody ResultObj outReject(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	@RequestMapping("/submitReflow")
	public @ResponseBody ResultObj submitReflow(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.SUBMITAGAIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.SUBMITAGAIN.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	@RequestMapping("/drawStockflow")
	public @ResponseBody ResultObj drawStockflow(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.CANCEL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.FLOWCANCEL.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.FLOWCANCEL.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	@RequestMapping("/leaderAgree")
	public @ResponseBody ResultObj leaderAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.MANAGERAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.LEADERAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.LEADERAGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
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
	@RequestMapping("/submitSkillAgree")
	public @ResponseBody ResultObj submitSkillAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.LEADERAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.SKILLAGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.SKILLAGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}

	/**
	 * 
	 *管理人员送到生技部审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/submitSkillerM")
	public @ResponseBody ResultObj submitSkillerM(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.PRODUCTAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.CANNOTREPAIR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.CANNOTREPAIR.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *专业人员送到生技部审核
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/submitSkiller")
	public @ResponseBody ResultObj submitSkiller(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.PRODUCTAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.TECHNOLOGYNOREPAIR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.TECHNOLOGYNOREPAIR.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *送到技术人员修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/managerAgree")
	public @ResponseBody ResultObj managerAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.TECHNOLOGYREPAIR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.CANREPAIR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.CANREPAIR.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *变卖
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/submitStockShop")
	public @ResponseBody ResultObj submitStockShop(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.TOSHOPAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.SHOP.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.SHOP.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *技术人员修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/technologyRepair")
	public @ResponseBody ResultObj technologyRepair(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.TOMANAGER.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.TECHNOLOGYREPAIR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.TECHNOLOGYREPAIR.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *入账
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/addMoney")
	public @ResponseBody ResultObj addMoney(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.SHOPED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.MONEYIN.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.MONEYIN.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *丢弃
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/discard")
	public @ResponseBody ResultObj discard(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DISCARDED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.DISCARD.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.DISCARD.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *库管员确认修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/sureRepair")
	public @ResponseBody ResultObj sureRepair(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.REPAIRED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.SUREREPAIR.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.SUREREPAIR.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *退回给技术人员重新修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/repairFalse")
	public @ResponseBody ResultObj repairFalse(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.TECHNOLOGYREPAIR.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockOutExcuteEnum.REPAIRFALSE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockOutExcuteEnum.REPAIRFALSE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 提交人再次提交
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
	@RequestMapping("/submitReflowAgain/{taskId}")
	public ModelAndView submitReflowAgain(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.SUBMITAGAIN.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	
	/**
	 * 
	 * 管理人员不修复
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
	@RequestMapping("/getSkillerM/{taskId}")
	public ModelAndView getSkillerM(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.CANNOTREPAIR.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 不修复
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
	@RequestMapping("/getSkiller/{taskId}")
	public ModelAndView getSkiller(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.TECHNOLOGYNOREPAIR.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 修复后提交按钮
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
	@RequestMapping("/submitStater/{taskId}")
	public ModelAndView submitStater(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.TECHNOLOGYREPAIR.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 变卖提交按钮
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
	@RequestMapping("/submitShoper/{taskId}")
	public ModelAndView submitShoper(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.SHOP.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 领导同意
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
	@RequestMapping("/submitManager/{taskId}")
	public ModelAndView submitManager(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.LEADERAGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 生技部提交给领导
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
	@RequestMapping("/submitOutLeader/{taskId}")
	public ModelAndView submitOutLeader(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.SKILLAGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 管理人员提交给技术人员修复
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
	@RequestMapping("/submitTechnology/{taskId}")
	public ModelAndView submitTechnology(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.CANREPAIR.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 管理人员提交给技术人员修复
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
	@RequestMapping("/submitTechnologyAgain/{taskId}")
	public ModelAndView submitTechnologyAgain(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.REPAIRFALSE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
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
		String warehouseId = entityMap.get("warehouseId").toString();
		boolean finalResult = scrapLibraryService.compareKc(materialId, materialCount,warehouseId);//查询库存是否足够
		int flag = 0;
//		if(!finalResult){
//			flag = 1;
//			resultObj.setResult("exception");
//		}
//		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(Long.valueOf(materialId));
//		String management = materialCategoryEntity.getManagement();
		int x = -1;
//		if(management.equals("1")){
			
			x = materialCount.indexOf(".");
				if(x>0){
					resultObj.setResult("exceptionOne");	
				}
//			}
		
		if(x==-1&&flag!=1){
			ScrapLibraryEntity scrapLibraryEntity = new ScrapLibraryEntity();
			scrapLibraryEntity.setFileId(entityMap.get("fileId").toString());
			DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
			scrapLibraryEntity.setInstockTime(sdf.parse(entityMap.get("instockTime").toString()));
			if(entityMap.get("instockType")!=null){
				scrapLibraryEntity.setInstockType(entityMap.get("instockType").toString());
			}else{
				scrapLibraryEntity.setInstockType(null);
			}
			//scrapLibraryEntity.setProcessMode(entityMap.get("processMode").toString());//不要看设计模型  那个不对  和关键别改
			scrapLibraryEntity.setScrapSource(ScrapSourceEnum.MANUAL.getCode());//报废来源 手动添加0
			scrapLibraryEntity.setStationSourceId(entityMap.get("stationSourceId").toString());
			scrapLibraryEntity.setStatus(ScrapLibraryStatusEnum.PENDING.getCode());
			scrapLibraryEntity.setWarehouseId(entityMap.get("warehouseId").toString());
			scrapLibraryEntity.setUserId(entityMap.get("userId").toString());
			scrapLibraryEntity.setType(entityMap.get("type").toString());
			scrapLibraryEntity.setStockId(String.valueOf(entityMap.get("stockId")));
			if(StringUtil.isNotEmpty((String)entityMap.get("remark"))){
				scrapLibraryEntity.setRemark(entityMap.get("remark").toString());
			}
			scrapLibraryService.addEntity(scrapLibraryEntity);
			
			Date instockTime = scrapLibraryEntity.getInstockTime();
			SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
			String materialIdStringTemp = entityMap.get("materialId").toString();
			String materialIdString = materialIdStringTemp.substring(1, materialIdStringTemp.length()-1);
			String materialCountStringTemp = entityMap.get("materialCount").toString();
			Map<String,Object> propertyMap = (Map<String, Object>) entityMap.get("propertyMap");
			//添加数据到明细表
			ScrapLibraryDetailEntity scrapLibraryDetailEntity = new ScrapLibraryDetailEntity();
			Long wareHouseId = Long.valueOf(entityMap.get("warehouseId").toString());
			if(materialIdString.contains(",")){
				//有多条物资
				scrapLibraryService.addPageSave(/*goodsAreaId,goodsAllocationId,*/materialIdString, materialCountStringTemp, instockTime,wareHouseId, sysUnitEntity, scrapLibraryEntity,scrapLibraryDetailEntity,propertyMap);
			}else{
				//有一条物资
				scrapLibraryService.addPageSaveSingle(/*goodsAreaId,goodsAllocationId,*/materialIdString, materialCountStringTemp,wareHouseId, instockTime, sysUnitEntity, scrapLibraryEntity,scrapLibraryDetailEntity,propertyMap);
			}
			resultObj.setResult("success");
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SCRAPLIBRARY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		resultObj.setData(finalResult);
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
	@RequestMapping("/saveAddPageOut/{materialId}")
	public @ResponseBody ResultObj saveAddPageOut(@RequestBody Map<String, Object> entityMap,@PathVariable String materialId) throws ParseException{
		ResultObj resultObj = new ResultObj();
		String materialUnitName = String.valueOf(entityMap.get("materialUnitName"));
		String materialCode = String.valueOf(entityMap.get("materialCode"));
		String materialName = String.valueOf(entityMap.get("materialName"));
		String materialModel = String.valueOf(entityMap.get("materialModel"));
		String materialManufacturer = String.valueOf(entityMap.get("materialManufacturer"));
		String count = String.valueOf(entityMap.get("count"));
		String goodsAttribute = String.valueOf(entityMap.get("goodsAttribute"));
		String goodsValidity = String.valueOf(entityMap.get("goodsValidity"));
		String goodsPrice = String.valueOf(entityMap.get("goodsPrice"));
		String code = String.valueOf(entityMap.get("code"));
		String unitId = String.valueOf(entityMap.get("stationSourceId"));
		String instockTime = String.valueOf(entityMap.get("instockTime"));
		String userId = String.valueOf(entityMap.get("userId"));
		String remark = String.valueOf(entityMap.get("remark"));
		String fileId = String.valueOf(entityMap.get("fileId"));
//		String materialId = String.valueOf(entityMap.get("materialId"));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
		conditions.add(new Condition("w.C_WARE_HOUSE_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "报废外库"));
		List<WareHouseEntity> wareHouseList = wareHouseService.findByCondition(conditions, null);
		int x = -1;
		if(materialUnitName.equals("个")){
			x = count.indexOf(".");
			if(x>0){
				resultObj.setResult("exceptionOne");	
			}
		}
		if(x==-1){
			ScrapLibraryEntity scrapLibraryEntity = new ScrapLibraryEntity();
			if(entityMap.get("fileId")==null){
				scrapLibraryEntity.setFileId("[]");
			}
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			scrapLibraryEntity.setInstockTime(sdf1.parse(instockTime));
			if(!remark.equals("null")){
				scrapLibraryEntity.setRemark(remark);
			}
			scrapLibraryEntity.setUserId(userId);
			scrapLibraryEntity.setStationSourceId(unitId);
			scrapLibraryEntity.setType("3");
			scrapLibraryEntity.setFileId(entityMap.get("fileId").toString());
			scrapLibraryEntity.setStatus(ScrapLibraryStatusEnum.PENDING.getCode());
			scrapLibraryEntity.setWarehouseId(wareHouseList.get(0).getId().toString());
			scrapLibraryService.addEntity(scrapLibraryEntity);
			String [] materialIds = materialId.split(",");
			String [] counts = count.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String [] goodsValidities = goodsValidity.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String [] goodsAttributes = goodsAttribute.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			String [] goodsPrices = goodsPrice.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",");
			for(int i = 0;i<materialIds.length;i++){
				ScrapLibraryDetailEntity scrapLibraryDetailEntity = new ScrapLibraryDetailEntity();
				scrapLibraryDetailEntity.setInstockId(scrapLibraryEntity.getId());
				scrapLibraryDetailEntity.setMetrialId(Long.valueOf(materialIds[i]));
				scrapLibraryDetailEntity.setCount(counts[i]);
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
				scrapLibraryDetailEntity.setGoodsValidity(sdf.parse(goodsValidities[i]));
				scrapLibraryDetailEntity.setGoodsAttribute(goodsAttributes[i]);
				scrapLibraryDetailEntity.setGoodsPrice(goodsPrices[i]);
				double goodsPriceD = Double.valueOf(goodsPrices[i]);
				double countD = Double.valueOf(counts[i]);
				double totalPrice = goodsPriceD*countD;
				scrapLibraryDetailEntity.setTotalPrice(totalPrice);
				scrapLibraryDetailService.addEntity(scrapLibraryDetailEntity);	
			}
			resultObj.setResult("success");	
		}
		
		return  resultObj ;
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
	 * 管理员同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/manageAgree")
	public @ResponseBody ResultObj manageAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.PRODUCTAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setStoreKeeper(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 管理员不同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/manageDisgree")
	public @ResponseBody ResultObj manageDisgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 跳到管理员审批页面
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
	@RequestMapping("/submitManageAgree/{taskId}")
	public ModelAndView submitManageAgree(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 跳到生技处审批页面
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
	@RequestMapping("/submitBiotechAgree/{taskId}")
	public ModelAndView submitBiotechAgree(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 生技处同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/biotechAgree")
	public @ResponseBody ResultObj biotechAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.LEADERAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 生技处不同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/biotechDisagree")
	public @ResponseBody ResultObj biotechDisagree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 跳到生产副总审批页面
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
	@RequestMapping("/submitProductionAgree/{taskId}")
	public ModelAndView submitProductionAgree(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ScrapstockOutExcuteEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/outstock/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 生产副总同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/productionAgree")
	public @ResponseBody ResultObj productionAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setApproveUser(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 生产副总不同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/productionDisagree")
	public @ResponseBody ResultObj productionDisagree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapstockApproveStatusEnum.DIRECTORAPPROVE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理审核页面初始化
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午6:59:47
	 * @lastModified
	 */
	@RequestMapping("/approveHandle/{id}/{type}")
	public ModelAndView approveHandle(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(id);
		model.put("scrapLibraryEntity", scrapLibraryEntity);
		model.put("id", id);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
		SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
		model.put("SysUnitEntity", SysUnitEntity);
		//获取申请人
		SysUserEntity applyUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
		model.put("applyUserEntity", applyUserEntity);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		//获取获取列表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
		List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions, null);
		ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
		for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
			comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
		}
		model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
		WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
		model.put("sareHouseEntity", sareHouseEntity);
		
		List<Condition> conditionsLc=new ArrayList<Condition>();
		conditionsLc.add(new Condition("sys_user.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getLoginName()));
		conditionsLc.add(new Condition("procInst.BUSINESS_KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		conditionsLc.add(new Condition("task.end_time_ IS NULL"));
			conditionsLc.add(new Condition("procDef.KEY_", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.SCRAPLIBRARY_HANDLE_PROCESS_KEY.getName()));
			
		
		List<TodoTaskEntity> list =  todoTaskService.findByCondition(conditionsLc, null);
		TodoTaskEntity todoTaskEntity=null;
		if(!list.isEmpty()){
			todoTaskEntity=list.get(0);
			List<ProcessNodeAuthEntity> nodeList=processNodeAuthService.getAuthorityList(todoTaskEntity.getTaskDefKey());
			model.put("nodeList", nodeList);
		}
		
		conditions.clear();
		conditions.add(new Condition("sk.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, scrapLibraryEntity.getStockId()));
		List<StockEntity> stockList = stockService.findByCondition(conditions, null);
		model.put("stockType", stockList.get(0).getType());
		
		return this.createModelAndView("cargo/scrapLibrary/scrapLibraryApproveHandle",model);
	}
	/**
	 * 
	 * 报废处理审批人驳回审批
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
	@RequestMapping("/submitReject/{taskId}")
	public ModelAndView submitReject(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.BACK.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/scrapLibrary/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 报废处理审批人同意审批
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
	@RequestMapping("/submitRepair/{taskId}")
	public ModelAndView submitRepair(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
		model.put("userList", userList);
		return this.createModelAndView("cargo/scrapLibrary/sureSubmitPerson", model);
	}
	/**
	 * 
	 * 报废处理审批人同意审批
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
	@RequestMapping("/selectWarehouse/{taskId}")
	public ModelAndView selectWarehouse(HttpServletRequest request,@PathVariable String taskId){
		Map<String, Object> model = new HashMap<String, Object>();
//		List<SysUserEntity> userList=nodeConfigService.getNextNodeTransactor(taskId,ExamResultEnum.AGREE.getId().toString());
//		model.put("userList", userList);
		SysUserEntity userEntity = RequestContext.get().getUser();
		String userUnitId = String.valueOf(userEntity.getUnitId());
		// 部门
		List<SysUnitEntity> treeNodeList = this.getUnitTreeNodeList1();
		// TODO下拉树具体内容根据具体业务定制
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
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
		model.put("type", 2);
		return this.createModelAndView("cargo/scrapLibrary/selectWarehouse", model);
	}
	/**
	 *  
	 *报废出库管理人员同意
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/manageRepair")
	public @ResponseBody ResultObj manageRepair(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEPROFESSIONAL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setStoreKeeper(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理管理员不可修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/unRepair")
	public @ResponseBody ResultObj unRepair(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEBIOTECH.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", "不可修复");
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setStoreKeeper(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *报废出库专业人员修复
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/repairedAgree")
	public @ResponseBody ResultObj repairedAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBESTOREHOUSE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理管理员不可修复
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/disRepair")
	public @ResponseBody ResultObj disRepair(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEBIOTECH.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *报废处理库管员确认
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/confirmAgree")
	public @ResponseBody ResultObj confirmAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.REPAIRED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.CONFIRM.getName());
		params.put("type", t.getType());
		params.put("warehouseId", t.getWarehouseId());
		params.put("stationSourceId", t.getStationSourceId());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setApproveUser(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理库管员未确认
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/unConfirm")
	public @ResponseBody ResultObj unConfirm(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEBIOTECH.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *报废处理生技部同意
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/bioAgree")
	public @ResponseBody ResultObj bioAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEPROMANAGE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理生技部不同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/bioDisagree")
	public @ResponseBody ResultObj bioDisagree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *报废处理生产副总同意
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/proAgree")
	public @ResponseBody ResultObj proAgree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBESTOREHOUSEDEAL.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		scrapLibraryEntity.setApproveUser(sysUserEntity.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理生产副总不同意
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/proDisagree")
	public @ResponseBody ResultObj proDisagree(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.REJECT.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.BACK_END.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 *报废处理变卖
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月2日 下午2:21:09
	 * @lastModified
	 */
	@RequestMapping("/sale")
	public @ResponseBody ResultObj sale(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.SHOPED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.AGREE.getName());
		params.put("type", t.getType());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理丢弃
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/drop")
	public @ResponseBody ResultObj drop(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.DISCARDED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ScrapstockExcuteEnum.BACK_END.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.DROP.getName());
		params.put("type", t.getType());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理综合管理员处理
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2018年4月18日 下午7:41:05
	 * @lastModified
	 */
	@RequestMapping("/deal")
	public @ResponseBody ResultObj deal(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.SHOPED.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.SALE.getName());
		params.put("type", t.getType());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理再次提交按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author huoxy
	 * @created 2017年8月1日 下午7:43:27
	 * @lastModified
	 */
	@RequestMapping("/submitAgain")
	public @ResponseBody ResultObj submitAgain(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.TOBEMANAGE.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.AGREE.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.SUBMITAGAIN.getName());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
	/**
	 * 
	 * 报废处理取消流程按钮
	 * 
	 * @param @param t
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author zhangxb
	 * @created 2017年8月1日 下午7:44:13
	 * @lastModified
	 */
	@RequestMapping("/cancel")
	public @ResponseBody ResultObj cancel(@RequestBody ScrapLibraryEntity t){
		ScrapLibraryEntity scrapLibraryEntity = scrapLibraryService.findById(t.getId());
		scrapLibraryEntity.setTaskId(t.getTaskId());
		scrapLibraryEntity.setProcInstId(t.getProcInstId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ScrapLibraryHandleStatusEnum.END.getCode());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ExamResultEnum.BACK.getId());
		params.put(ExamMarkEnum.COMMENT.getCode(), "");
		params.put("result", ScrapstockExcuteEnum.SCRAP.getName());
		params.put("type", t.getType());
		return scrapLibraryService.approve(scrapLibraryEntity, params);
	}
}