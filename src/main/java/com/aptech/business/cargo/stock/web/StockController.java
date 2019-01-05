package com.aptech.business.cargo.stock.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.business.cargo.stock.service.StockService;
import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.wareHouse.wareHouse.domain.WareHouseEntity;
import com.aptech.business.wareHouse.wareHouse.service.WareHouseService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 库存管理配置控制器
 *
 * @author 
 * @created 2017-07-17 16:40:59
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/stock")
public class StockController extends BaseController<StockEntity> {
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private StockService stockService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private WareHouseService wareHouseService;
	@Override
	public IBaseEntityOperation<StockEntity> getService() {
		return stockService;
	}
	
	/**
	 * 
	 * 库存列表页面初始化
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月7日 下午2:49:09
	 * @lastModified
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		ComboboxVO comboManageVO = new ComboboxVO();
		for(ManagementTypeEnum managementTypeEnum : ManagementTypeEnum.values()){
			comboManageVO.addOption(managementTypeEnum.getCode(), managementTypeEnum.getName());
		}
		model.put("managementCombobox", JsonUtil.toJson(comboManageVO.getOptions()));
		 //获取登录人信息
  		SysUserEntity sysUserEntity = RequestContext.get().getUser();
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
		//物资类别
		Map<String, SysDictionaryVO> materialTypeMap  =  DictionaryUtil.getDictionaries("MATERIAL_CATEGORY");
		ComboboxVO comboMaterialTypeVO = new ComboboxVO();
		for(String key : materialTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = materialTypeMap.get(key);
			comboMaterialTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("materialTypeList", JsonUtil.toJson(comboMaterialTypeVO.getOptions()));
		//仓库名称
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("w.C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		conditions.add(new Condition("w.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,sysUserEntity.getUnitId()));
		conditions.add(new Condition("w.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1"));
		List<WareHouseEntity> wareHouseEntities=wareHouseService.findByCondition(conditions, null);
		ComboboxVO comboWareHouseVO = new ComboboxVO();
		for(WareHouseEntity wareHouseEntity:wareHouseEntities){
			comboWareHouseVO.addOption(wareHouseEntity.getWareHouseName(), wareHouseEntity.getWareHouseName());
		}
		model.put("wareHouseIds", JsonUtil.toJson(comboWareHouseVO.getOptions()));
		
        
     
  		model.put("sysUserId", JsonUtil.toJson(sysUserEntity.getId()));
  		model.put("loginName", JsonUtil.toJson(sysUserEntity.getLoginName()));
  		model.put("unitId", sysUserEntity.getUnitId());
  		model.put("dutyName",JsonUtil.toJson(sysUserEntity.getDutyName()) );
		
		model.put("productionFactoryCombobox", JsonUtil.toJson(comboPrductionFactoryVO.getOptions()));
		return this.createModelAndView("cargo/stock/stockList", model);
	}
	
	/**
	 * 
	 * 列表页面数据检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月28日 下午3:03:26
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj list(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<StockEntity> pages = PageUtil.getPage(params);
		pages.setOrders(OrmUtil.changeMapToOrders(params));
		pages.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String unitId = sysUserEntity.getUnitId().toString();
		if(!unitId.equals("144")){
			conditions.add(new Condition("sk.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, unitId));
		}
		conditions.add(new Condition("sk.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, 0));
		List<StockEntity> stockEntities = stockService.findByCondition(conditions, pages);
		for (StockEntity stockEntity : stockEntities) {
			String inventoryQuantity=stockEntity.getInventoryQuantity();
			String upperLimit=stockEntity.getUpperLimit();
			String lowerLimit=stockEntity.getLowerLimit();
//			stockEntity.setInventoryQuantity(StringUtil.substringBefore(inventoryQuantity, "."));
			stockEntity.setUpperLimit(StringUtil.substringBefore(upperLimit, "."));
			stockEntity.setLowerLimit(StringUtil.substringBefore(lowerLimit, "."));
			stockEntity.setShortage(StringUtil.substringBefore(stockEntity.getShortage(), "."));
		}
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (stockEntities != null) {
			if (pages != null) {
				resultObj.setData(stockEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 库存列表查看页面
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月7日 下午2:48:39
	 * @lastModified
	 */
	@RequestMapping(value = "/showInfo/{id}")
	public ModelAndView showInfo(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		StockEntity stock = (StockEntity)stockService.findById(id);
		model.put("entity", stock);
		model.put("entityJson", JsonUtil.toJson(stock));
		List<StockEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("stockTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboStockVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("stockCombobox", JsonUtil.toJson(comboStockVO.getOptions()));
		return this.createModelAndView("cargo/stock/stockShowInfo", model);
	}
	
	/**
	 * 
	 * 单行保存库存列表可编辑的三个数量并判断是否短缺及短缺数量
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @param cksl
	 * @param @param upperLimit
	 * @param @param lowerLimit
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月18日 下午3:44:37
	 * @lastModified
	 */
	@RequestMapping(value="/singleSave/{id}/{cksl}/{upperLimit}/{lowerLimit}", method = RequestMethod.POST)
	public  @ResponseBody ResultObj singleSave(HttpServletRequest request,@PathVariable Long id,@PathVariable String cksl,
			@PathVariable String upperLimit,@PathVariable String lowerLimit){
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("sk.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));
		List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
		StockEntity stockEntity = null;
		if(null != stockEntities && stockEntities.size()!=0){
			stockEntity = stockEntities.get(0);
		}
		String materialManageString = stockEntity.getMaterialManagement();
		String kcslCount = "0";
		String upperCount = "0";
		String lowerCount = "0";
		double mCountDouble = Double.parseDouble(cksl);
		double upperCountDouble = Double.parseDouble(upperLimit);
		double lowerCountDouble = Double.parseDouble(lowerLimit);
		String resultString = "";
		if(upperCountDouble<lowerCountDouble){
			resultString = "标准上限不能小于下限！";
			resultObj.setResult(resultString);
		}else{
			//如果物资的管理方式为实例（1）时，数量为整数
			if(StringUtil.isNotEmpty(materialManageString) && materialManageString.equals("1")){
				stockService.caseManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
				//如果物资的管理方式为记账（2）时，数量为小数
			}else if(StringUtil.isNotEmpty(materialManageString) && materialManageString.equals("2")){
				stockService.chargeManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
			}
			stockService.updateEntity(stockEntity);
		}
		
		resultObj.setData(stockEntity);
		return resultObj;
	}
	
	/**
	 * 
	 * 批量保存库存列表可编辑的三个数量并判断是否短缺及短缺数量
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年7月18日 下午4:38:12
	 * @lastModified
	 */
	@RequestMapping(value="/bulkSave", method = RequestMethod.GET)
	public @ResponseBody ResultObj buleSave(HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		String idsString = request.getParameter("ids");
		String ckslString = request.getParameter("cksl");
		String upperString = request.getParameter("upper");
		String lowerString = request.getParameter("lower");
		if(idsString.contains(",")){
			//多项保存
			String[] idsArray = idsString.split(",");
			String[] ckslArray = ckslString.split(",");
			String[] upperArray = upperString.split(",");
			String[] lowerArray = lowerString.split(",");
			for(int i=0;i<idsArray.length;i++){
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("sk.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Integer.valueOf(idsArray[i])));
				List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
				StockEntity stockEntity = null;
				if(null != stockEntities && stockEntities.size()!=0){
					stockEntity = stockEntities.get(0);
				}
				String materialManage = stockEntity.getMaterialManagement();
				String kcslCount = "0";
				String upperCount = "0";
				String lowerCount = "0";
				//前台传的值
				double mCountDouble = Double.parseDouble(ckslArray[i]);
				double upperCountDouble = Double.parseDouble(upperArray[i]);
				double lowerCountDouble = Double.parseDouble(lowerArray[i]);
				String resultString = "";
				if(upperCountDouble<lowerCountDouble){
					resultString = "标准上限不能小于下限！";
					resultObj.setResult(resultString);
				}else{
					if(StringUtil.isNotEmpty(materialManage) && materialManage.equals("1")){
						//物资类型管理方式为实例时，上下限为整数
						stockService.caseManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
					}else if(StringUtil.isNotEmpty(materialManage) && materialManage.equals("2")){
						//物资类型管理方式为记账时，上下限为小数
						stockService.chargeManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
					}
					stockService.updateEntity(stockEntity);
				}
			}
		}else{
			//单项保存
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("sk.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Long.valueOf(idsString)));
			List<StockEntity> stockEntities = stockService.findByCondition(conditions, null);
			StockEntity stockEntity = null;
			if(null != stockEntities && stockEntities.size()!=0){
				stockEntity = stockEntities.get(0);
			}
			String materialManage = stockEntity.getMaterialManagement();
			String kcslCount = "0";
			String upperCount = "0";
			String lowerCount = "0";
			double mCountDouble = Double.parseDouble(ckslString);
			double upperCountDouble = Double.parseDouble(upperString);
			double lowerCountDouble = Double.parseDouble(lowerString);
			String resultString = "";
			if(upperCountDouble<lowerCountDouble){
				resultString = "标准上限不能小于下限！";
				resultObj.setResult(resultString);
			}else{
				if(StringUtil.isNotEmpty(materialManage) && materialManage.equals("1")){
					//物资类型管理方式为实例时，上下限为整数
					stockService.caseManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
				}else if(StringUtil.isNotEmpty(materialManage) && materialManage.equals("2")){
					//物资类型管理方式为记账时，上下限为小数
					stockService.chargeManage(mCountDouble, upperCountDouble, lowerCountDouble, kcslCount, upperCount, lowerCount, stockEntity);
				}
				stockService.updateEntity(stockEntity);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.STOCK.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	
	/**
	 * 
	 * 库存列表导出
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月7日 下午2:48:22
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		Page<StockEntity> pages = new Page<StockEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
		List<StockEntity> dataList=stockService.findByCondition(params, pages);
		if(null != dataList && dataList.size()!=0){
			for(StockEntity stockEntity : dataList){
				//列表按照日期格式显示日期
				SysUnitEntity sysUnitEntity = sysUnitService.findById(Long.valueOf(stockEntity.getUnitId()));
				stockEntity.setUnitName(sysUnitEntity.getName());
				String inventoryQuantity=stockEntity.getInventoryQuantity();
				String upperLimit=stockEntity.getUpperLimit();
				String lowerLimit=stockEntity.getLowerLimit();
				stockEntity.setInventoryQuantity(StringUtil.substringBefore(inventoryQuantity, "."));
				stockEntity.setUpperLimit(StringUtil.substringBefore(upperLimit, "."));
				stockEntity.setLowerLimit(StringUtil.substringBefore(lowerLimit, "."));
				stockEntity.setShortage(StringUtil.substringBefore(stockEntity.getShortage(), "."));
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "库存管理报表模板.xlsx","库存管理.xlsx", resultMap);
	}
}