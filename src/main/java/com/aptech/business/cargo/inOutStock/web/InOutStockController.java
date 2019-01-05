package com.aptech.business.cargo.inOutStock.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.business.cargo.inOutStock.service.InOutStockService;
import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStock.service.InstockService;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.cargo.outStock.domain.OutstockEntity;
import com.aptech.business.cargo.outStock.service.OutstockService;
import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.business.cargo.scarpLibrary.service.ScrapLibraryService;
import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.business.excel.ExcelUtil;
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
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 出入库明细配置控制器
 *
 * @author 
 * @created 2017-07-15 16:14:14
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/inOutStock")
public class InOutStockController extends BaseController<InOutStockEntity> {
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private InOutStockService inOutStockService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private InstockService instockService;
	@Autowired
	private OutstockService outstockService;
	@Autowired
	private ScrapLibraryService scrapLibraryService;
	@Autowired
	private WareHouseService wareHouseService;
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<InOutStockEntity> getService() {
		return inOutStockService;
	}
	
	/**
	 * 
	 * 列表页初始化
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月8日 上午11:44:27
	 * @lastModified
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		//从数据字典取
		Map<String, SysDictionaryVO> billTypeMap  =  DictionaryUtil.getDictionaries("BILL_TYPE");
		ComboboxVO comboBillTypeVO = new ComboboxVO();
		for(String key : billTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = billTypeMap.get(key);
			comboBillTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("typeCombobox", JsonUtil.toJson(comboBillTypeVO.getOptions()));
		//从枚举取
		ComboboxVO comboManageVO = new ComboboxVO();
		for(ManagementTypeEnum managementTypeEnum : ManagementTypeEnum.values()){
			comboManageVO.addOption(managementTypeEnum.getCode(), managementTypeEnum.getName());
		}
		model.put("managementCombobox", JsonUtil.toJson(comboManageVO.getOptions()));
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		for (int i = 0; i < treeNodeList.size(); i++) {
			treeNodeList.get(i).setOpen("true");
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//生产厂家下拉框
		Map<String, SysDictionaryVO> prductionFactoryMap  =  DictionaryUtil.getDictionaries("PRODUCTION_FACTORY");
		ComboboxVO comboPrductionFactoryVO = new ComboboxVO();
		for(String key : prductionFactoryMap.keySet()){
			SysDictionaryVO sysDictionaryVO = prductionFactoryMap.get(key);
			comboPrductionFactoryVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("productionFactoryCombobox", JsonUtil.toJson(comboPrductionFactoryVO.getOptions()));
		
		return this.createModelAndView("cargo/inOutStock/inOutStockList", model);
	}
	
	/**
	 * 
	 * 列表页数据初始化
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月8日 上午11:44:41
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj list(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//按照时间倒序排列
		Page<InOutStockEntity> pages = PageUtil.getPage(params);
		//pages.addOrder(Sort.desc("C_TIME"));
		//pages.addOrder(Sort.asc("C_UNIT_NAME"));
		pages.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		conditions.add(new Condition("stock.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<InOutStockEntity> inOutStockEntities = inOutStockService.findByCondition(conditions, pages);
		
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
	 * 
	 * 查看页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月8日 上午11:45:09
	 * @lastModified
	 */
	@RequestMapping(value = "/showInfo/{id}")
	public ModelAndView showInfo(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据出入库表id查找出入库明细数据
		InOutStockEntity inOutStockEntity = inOutStockService.findById(id);
		List<Condition> conditions = new ArrayList<Condition>();
		if(inOutStockEntity.getType().equals("1")){
		//如果单据类型是1，为入库
			if(inOutStockEntity.getCode().startsWith("BF")){
				conditions.add(new Condition("t.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,inOutStockEntity.getCode()));
				List<ScrapLibraryEntity> scrapLibraryEntities = scrapLibraryService.findByCondition(conditions, null);
				if(null != scrapLibraryEntities && scrapLibraryEntities.size()!=0){
					for(ScrapLibraryEntity scrapLibraryEntity : scrapLibraryEntities){
						model.put("scrapLibraryEntity", scrapLibraryEntity);
						model.put("id", id);
						model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
						SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						model.put("SysUnitEntity", SysUnitEntity);
						SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
						model.put("sysUserEntity", sysUserEntity);
						//获取获取列表
						List<Condition> conditions1 = new ArrayList<Condition>();
						conditions1.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
						List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions1, null);
						ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
						for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
							comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
						}
						model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
						WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						model.put("sareHouseEntity", sareHouseEntity);
						return this.createModelAndView("cargo/inOutStock/scrapLibraryShowDetail", model);
					}
				}
			}else{
				conditions.add(new Condition("instockNum",FieldTypeEnum.STRING,MatchTypeEnum.EQ,inOutStockEntity.getCode()));
				List<InstockEntity> dataList=instockService.findByCondition(conditions, null);
				if(null != dataList && dataList.size()!=0){
					for(InstockEntity entity : dataList){
						// 返回前台数据项
						InstockEntity instockEntity = (InstockEntity)instockService.findById(entity.getId());
//						DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
//						String inTimeString = sdf.format(instockEntity.getInstockTime());
//						instockEntity.setShowInstockTime(inTimeString);
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
						List<Condition> conditions1 = new ArrayList<Condition>();
						conditions1.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
						List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions1, null);
						ComboboxVO comboWareHouseVO = new ComboboxVO();
						for(WareHouseEntity wareHouse:wareHouseEntities){
							comboWareHouseVO.addOption(wareHouse.getId().toString(), wareHouse.getWareHouseName());
						}
						model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
						//获取获取列表
						conditions1.clear();
						conditions1.add(new Condition("a.C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, instockEntity.getWareHouseId()));
						List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions1, null);
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
						return this.createModelAndView("cargo/inOutStock/inOutStockShowInfo", model);
					}
				}
			}
			
		}else if(inOutStockEntity.getType().equals("2")){
		//如果单据是2，为出库
			//判断是报废出库还是正常出库
			if(inOutStockEntity.getCode().startsWith("BF")){
				conditions.add(new Condition("t.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,inOutStockEntity.getCode()));
				List<ScrapLibraryEntity> scrapLibraryEntities = scrapLibraryService.findByCondition(conditions, null);
				if(null != scrapLibraryEntities && scrapLibraryEntities.size()!=0){
					for(ScrapLibraryEntity scrapLibraryEntity : scrapLibraryEntities){
						model.put("scrapLibraryEntity", scrapLibraryEntity);
						model.put("id", id);
						model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
						SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
						model.put("SysUnitEntity", SysUnitEntity);
						SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
						model.put("sysUserEntity", sysUserEntity);
						//获取获取列表
						List<Condition> conditions1 = new ArrayList<Condition>();
						conditions1.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
						List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions1, null);
						ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
						for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
							comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
						}
						model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
						WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
						model.put("sareHouseEntity", sareHouseEntity);
						return this.createModelAndView("cargo/inOutStock/scrapLibraryShowDetail", model);
					}
				}
			}else{
				conditions.add(new Condition("outstockNum",FieldTypeEnum.STRING,MatchTypeEnum.EQ,inOutStockEntity.getCode()));
				List<OutstockEntity> outstockEntities = outstockService.findByCondition(conditions, null);
				if(null != outstockEntities && outstockEntities.size()!=0){
					for(OutstockEntity entity : outstockEntities){
						OutstockEntity outstockEntity = outstockService.findById(entity.getId());
						model.put("entity", outstockEntity);
						model.put("entityJson", JsonUtil.toJson(outstockEntity));
						
						//获取登录人
						SysUserEntity sysUserEntity = RequestContext.get().getUser();
						model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
						
						//入库类型
						Map<String, SysDictionaryVO> outstockTypeMap  =  DictionaryUtil.getDictionaries("OUTSTOCK_TYPE");
						ComboboxVO comboInstockTypeVO = new ComboboxVO();
						for(String key : outstockTypeMap.keySet()){
							SysDictionaryVO sysDictionaryVO = outstockTypeMap.get(key);
							comboInstockTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
						}
						model.put("outstockTypeCombobox", JsonUtil.toJson(comboInstockTypeVO.getOptions()));
						return this.createModelAndView("cargo/inOutStock/inOutStockShow", model);
					}
				}
			}
		}else{
			conditions.add(new Condition("t.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,inOutStockEntity.getCode()));
			List<ScrapLibraryEntity> scrapLibraryEntities = scrapLibraryService.findByCondition(conditions, null);
			if(null != scrapLibraryEntities && scrapLibraryEntities.size()!=0){
				for(ScrapLibraryEntity scrapLibraryEntity : scrapLibraryEntities){
					model.put("scrapLibraryEntity", scrapLibraryEntity);
					model.put("id", id);
					model.put("entityJson", JsonUtil.toJson(scrapLibraryEntity));
					SysUnitEntity SysUnitEntity = sysUnitService.findById(Long.valueOf(scrapLibraryEntity.getStationSourceId()));
					model.put("SysUnitEntity", SysUnitEntity);
					SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(scrapLibraryEntity.getUserId()));
					model.put("sysUserEntity", sysUserEntity);
					//获取获取列表
					List<Condition> conditions1 = new ArrayList<Condition>();
					conditions1.add(new Condition("C_WAREHOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, scrapLibraryEntity.getWarehouseId()));
					List<WareHouseAreaEntity> wareHouseAreaEntities = wareHouseAreaService.findByCondition(conditions1, null);
					ComboboxVO comboboxHouseAreaVO = new ComboboxVO();
					for(WareHouseAreaEntity wareHouseAreaEntity :wareHouseAreaEntities){
						comboboxHouseAreaVO.addOption(wareHouseAreaEntity.getId().toString(), wareHouseAreaEntity.getName());
					}
					model.put("goodsAreaValues", JsonUtil.toJson(comboboxHouseAreaVO.getOptions()));
					WareHouseEntity sareHouseEntity = wareHouseService.findById(Long.valueOf(scrapLibraryEntity.getWarehouseId()));
					model.put("sareHouseEntity", sareHouseEntity);
					model.put("inOutStockEntity", inOutStockEntity);
					return this.createModelAndView("cargo/inOutStock/scrapLibraryShowDetail", model);
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 出入库导出
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月8日 上午11:45:43
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Page<InOutStockEntity> pages = new Page<InOutStockEntity>();
		pages.addOrder(Sort.desc("C_ID"));
		//pages.addOrder(Sort.desc("time"));
		//pages.addOrder(Sort.asc("unitId"));
		//pages.addOrder(Sort.desc("code"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		
		String searchcode = req.getParameter("searchcode");
		String searchType = req.getParameter("searchType");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String searchMaterialCode = req.getParameter("searchMaterialCode");
		String searchMaterialName = URLDecoder.decode(req.getParameter("searchMaterialName"), "utf-8");
		String searchMaterialModel = URLDecoder.decode(req.getParameter("searchMaterialModel"), "utf-8");
		String management = req.getParameter("management");
		String searchFacture = req.getParameter("searchFacture");
		String searchstationName = req.getParameter("searchstationName");
		if(StringUtil.isNotEmpty(searchcode)){
			conditions.add(new Condition("stock.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchcode));
		}
		if(StringUtil.isNotEmpty(searchType)){
			conditions.add(new Condition("stock.C_TYPE",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchType));
		}
		if(StringUtil.isNotEmpty(startTime)){
			conditions.add(new Condition("time",FieldTypeEnum.STRING,MatchTypeEnum.GE,startTime));
		}
		if(StringUtil.isNotEmpty(endTime)){
			conditions.add(new Condition("time",FieldTypeEnum.STRING,MatchTypeEnum.LE,endTime));
		}
		if(StringUtil.isNotEmpty(searchMaterialCode)){
			conditions.add(new Condition("material.C_CODE",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchMaterialCode));
		}
		if(StringUtil.isNotEmpty(searchMaterialName)){
			conditions.add(new Condition("material.C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchMaterialName));
		}
		if(StringUtil.isNotEmpty(searchMaterialModel)){
			conditions.add(new Condition("material.C_MODEL",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchMaterialModel));
		}
		if(StringUtil.isNotEmpty(management)){
			conditions.add(new Condition("material.C_MANAGEMENT",FieldTypeEnum.STRING,MatchTypeEnum.EQ,management));
		}
		if(StringUtil.isNotEmpty(searchFacture)){
			conditions.add(new Condition("material.C_MANUFACTURER",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchFacture));
		}
		if(StringUtil.isNotEmpty(searchstationName)&&!searchstationName.equals("undefined")){
			conditions.add(new Condition("stock.C_UNIT_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,searchstationName));
		}
		
		SysUserEntity sysUserEntity =RequestContext.get().getUser();
		conditions.add(new Condition("stock.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		List<InOutStockEntity> dataList=inOutStockService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "出入库明细报表模板.xlsx","出入库明细.xlsx", resultMap);
	}
}