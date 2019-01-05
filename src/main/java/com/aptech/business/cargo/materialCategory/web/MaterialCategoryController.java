package com.aptech.business.cargo.materialCategory.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inStock.domain.InstockEntity;
import com.aptech.business.cargo.inStock.service.InstockService;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.inStockDetail.service.InstockDetailService;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.component.dictionary.ManagementTypeEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.business.supplier.supplier.domain.SupplierEntity;
import com.aptech.business.supplier.supplier.service.SupplierService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
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
 * 物资类别配置控制器
 *
 * @author 
 * @created 2017-07-14 11:59:28
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/materialCategory")
public class MaterialCategoryController extends BaseController<MaterialCategoryEntity> {
	
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private InstockService instockService;
	@Autowired
	private InstockDetailService instockDetailService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<MaterialCategoryEntity> getService() {
		return materialCategoryService;
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
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("cargo/materialCategory/materialCategoryList", model);
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
	public  @ResponseBody ResultListObj dataList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		//按照时间倒序排列
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("quote",FieldTypeEnum.STRING,MatchTypeEnum.NE,'2'));
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition(conditions, pages);
		//获得返回结果
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (materialCategoryEntities != null) {
			if (pages != null) {
				resultObj.setData(materialCategoryEntities);
				resultObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 添加页面初始化
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:31:29
	 * @lastModified
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		ComboboxVO comboMaterialCategoryVO = new ComboboxVO();
		for(ManagementTypeEnum managementTypeEnum : ManagementTypeEnum.values()){
			comboMaterialCategoryVO.addOption(managementTypeEnum.getCode(), managementTypeEnum.getName());
		}
		model.put("managementCombobox", JsonUtil.toJson(comboMaterialCategoryVO.getOptions()));
		//物资编码生成规则为WZ+日期+序号，共15位
		String prefix = "WZ";
		int sequenceLength = 4;
		String GenerateCode = materialCategoryService.generateMaterialCode(prefix, sequenceLength);
		model.put("genetateCode", GenerateCode);
		//生产厂家下拉框
		Map<String, SysDictionaryVO> prductionFactoryMap  =  DictionaryUtil.getDictionaries("PRODUCTION_FACTORY");
		ComboboxVO comboPrductionFactoryVO = new ComboboxVO();
		for(String key : prductionFactoryMap.keySet()){
			SysDictionaryVO sysDictionaryVO = prductionFactoryMap.get(key);
			comboPrductionFactoryVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("productionFactoryCombobox", JsonUtil.toJson(comboPrductionFactoryVO.getOptions()));
		//计数单位下拉框
		Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
		ComboboxVO comboUnitVO = new ComboboxVO();
		for(String key : unitMap.keySet()){
			SysDictionaryVO sysDictionaryVO = unitMap.get(key);
			comboUnitVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("unitCombobox", JsonUtil.toJson(comboUnitVO.getOptions()));
		//物资类别下拉树
		Map<String, SysDictionaryVO> materialTypeMap  =  DictionaryUtil.getDictionaries("MATERIAL_CATEGORY");
		ComboboxVO comboMaterialTypeVO = new ComboboxVO();
		for(String key : materialTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = materialTypeMap.get(key);
			comboMaterialTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("materialTypeCombobox", JsonUtil.toJson(comboMaterialTypeVO.getOptions()));
		//供应商下拉框
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<SupplierEntity> supplierEntities = supplierService.findByCondition(conditions, null);
		ComboboxVO comboSupplierVO = new ComboboxVO();
		for(SupplierEntity supplierEntity:supplierEntities){
			comboSupplierVO.addOption(supplierEntity.getId().toString(), supplierEntity.getSupplierName());
		}
		model.put("supplierCombobox", JsonUtil.toJson(comboSupplierVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("cargo/materialCategory/materialCategoryAdd", model);
	}
	
	/**
	 * 
	 * 修改页面初始化
	 * 
	 * @param @param request
	 * @param @param id
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:31:46
	 * @lastModified
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		MaterialCategoryEntity materialCategoryEntity = (MaterialCategoryEntity)materialCategoryService.findById(id);
		model.put("entity", materialCategoryEntity);
		model.put("entityJson", JsonUtil.toJson(materialCategoryEntity));
		
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
		//计数单位下拉框
		Map<String, SysDictionaryVO> unitMap  =  DictionaryUtil.getDictionaries("DIGIT");
		ComboboxVO comboUnitVO = new ComboboxVO();
		for(String key : unitMap.keySet()){
			SysDictionaryVO sysDictionaryVO = unitMap.get(key);
			comboUnitVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("unitCombobox", JsonUtil.toJson(comboUnitVO.getOptions()));
		//物资类别下拉树
		Map<String, SysDictionaryVO> materialTypeMap  =  DictionaryUtil.getDictionaries("MATERIAL_CATEGORY");
		ComboboxVO comboMaterialTypeVO = new ComboboxVO();
		for(String key : materialTypeMap.keySet()){
			SysDictionaryVO sysDictionaryVO = materialTypeMap.get(key);
			comboMaterialTypeVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("materialTypeCombobox", JsonUtil.toJson(comboMaterialTypeVO.getOptions()));
		//供应商下拉框
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<SupplierEntity> supplierEntities = supplierService.findByCondition(conditions, null);
		ComboboxVO comboSupplierVO = new ComboboxVO();
		for(SupplierEntity supplierEntity:supplierEntities){
			comboSupplierVO.addOption(supplierEntity.getId().toString(), supplierEntity.getSupplierName());
		}
		model.put("supplierCombobox", JsonUtil.toJson(comboSupplierVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("cargo/materialCategory/materialCategoryEdit", model);
	}
	
	/**
	 * 
	 * 物资种类列表导出
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:09
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		Page<MaterialCategoryEntity> pages = new Page<MaterialCategoryEntity>();
		pages.addOrder(Sort.desc("id"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("quote",FieldTypeEnum.STRING,MatchTypeEnum.NE,'2'));
		String searchcode = req.getParameter("searchcode");
		String searchname = URLDecoder.decode(req.getParameter("searchname"), "utf-8");
		String searchmodel = URLDecoder.decode(req.getParameter("searchmodel"), "utf-8");
		String produtionFactory = req.getParameter("produtionFactory");
		String searchmanagement = req.getParameter("searchmanagement");
		String searchMaterialType = req.getParameter("searchMaterialType");
		String searchmanufacturer = req.getParameter("searchmanufacturer");
		if(StringUtil.isNotEmpty(searchcode)){
			conditions.add(new Condition("code",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchcode));
		}
		if(StringUtil.isNotEmpty(searchname)){
			conditions.add(new Condition("name",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchname));
		}
		if(StringUtil.isNotEmpty(searchmodel)){
			conditions.add(new Condition("model",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchmodel));
		}
		if(StringUtil.isNotEmpty(produtionFactory)){
			conditions.add(new Condition("manufacturer",FieldTypeEnum.STRING,MatchTypeEnum.EQ,produtionFactory));
		}
		if(StringUtil.isNotEmpty(searchmanagement)&&!searchmanagement.equals("undefined")){
			conditions.add(new Condition("management",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchmanagement));
		}
		if(StringUtil.isNotEmpty(searchMaterialType)){
			conditions.add(new Condition("materialType",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchMaterialType));
		}
		if(StringUtil.isNotEmpty(searchmanufacturer)){
			conditions.add(new Condition("manufacturer",FieldTypeEnum.STRING,MatchTypeEnum.EQ,searchmanufacturer));
		}
		List<MaterialCategoryEntity> dataList=materialCategoryService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for(MaterialCategoryEntity materialCategoryEntity : dataList){
			conditions.clear();
			conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "PRODUCTION_FACTORY"));
			conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, materialCategoryEntity.getManufacturer()));
			List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
			materialCategoryEntity.setManufacturerName(sysDictionaryList.get(0).getName());
		}
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "物资类别报表模板.xlsx","物资类别.xlsx", resultMap);
	}
	
	/**
	 * 
	 * 添加物资类别数据
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:36:26
	 * @lastModified
	 */
	@RequestMapping("/addSave")
	public @ResponseBody ResultObj addSave(@RequestBody MaterialCategoryEntity materialEntity,HttpServletRequest request){
		boolean resultFlag = true;
		String resultMsg = "";
		String materialName = materialEntity.getName().trim();
		String materialModel = materialEntity.getModel().trim();
		String materialFactory = materialEntity.getManufacturer();
		//添加前先判断物资名称和规格型号、生产厂家是否会重
		//用物资名称查询，查规格型号是否在结果中
		if(resultFlag){
			List<Condition> conditions_name = new ArrayList<Condition>();
			conditions_name.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialName));
			List<MaterialCategoryEntity> materialEntities_model = materialCategoryService.findByCondition(conditions_name, null);
			if(null != materialEntities_model && !materialEntities_model.isEmpty()){
				for(MaterialCategoryEntity materialEntity_model : materialEntities_model){
					String modelString = materialEntity_model.getModel();
					String factoryString = materialEntity_model.getManufacturer();
					if(StringUtil.isNotEmpty(modelString) && modelString.equals(materialModel)){
						if(StringUtil.isNotEmpty(factoryString)&& factoryString.equals(materialFactory)){
						resultMsg = materialFactory+"生产的"+materialModel+"规格的"+materialName+"已经存在！";
						resultFlag = false;
						}
					}
				}
			}
		}
		if(resultFlag){
			materialEntity.setQuote("3");
			materialCategoryService.addEntity(materialEntity);
			resultMsg = "添加成功！";
		}
		ResultObj resultObj = new ResultObj();
		resultObj.setResult(resultMsg);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MATERIALCATEGORY.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}
	
	/**
	 * 
	 * 修改物资类别数据
	 * 
	 * @param @param request
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月4日 下午1:36:54
	 * @lastModified
	 */
	@RequestMapping("/editSave")
	public @ResponseBody ResultObj editSave(@RequestBody MaterialCategoryEntity materialEntity,HttpServletRequest request){
		ResultObj resultObj = new ResultObj();
		boolean resultFlag = true;
		String resultMsg = "";
		MaterialCategoryEntity materialCategoryEntity = materialCategoryService.findById(materialEntity.getId());
		String materialName = materialEntity.getName().trim();
		String materialModel = materialEntity.getModel().trim();
		String materialCode = materialEntity.getCode();
		String materialFactory = materialEntity.getManufacturer();
		
		//添加前判断物资编码是否会重
		List<String> material_Code = new ArrayList<String>();
		List<MaterialCategoryEntity> material = materialCategoryService.findAll();
		for(MaterialCategoryEntity materialCategory:material){
			if(!materialCategory.getId().equals(materialEntity.getId())){
			   material_Code.add(materialCategory.getCode());
		    }
		}
		if(resultFlag){
			if(material_Code.contains(materialCode)){
				resultMsg = materialCode+"已经存在！";
				resultFlag = false;
			}
		}
		
		//添加前先判断物资名称和规格型号、生产厂家是否会重
		//用物资名称查询，查规格型号是否在结果中
		
		if(resultFlag){
			List<Condition> conditions_name = new ArrayList<Condition>();
			conditions_name.add(new Condition("C_NAME",FieldTypeEnum.STRING,MatchTypeEnum.EQ,materialName));
			List<MaterialCategoryEntity> materialEntities_model = materialCategoryService.findByCondition(conditions_name, null);
			if(null != materialEntities_model && !materialEntities_model.isEmpty()){
				for(MaterialCategoryEntity materialEntity_model : materialEntities_model){
					String modelString = materialEntity_model.getModel();
					String factoryString = materialEntity_model.getManufacturer();
					if(!materialEntity_model.getId().equals(materialEntity.getId())){
						if(StringUtil.isNotEmpty(modelString) && modelString.equals(materialModel)){
							if(StringUtil.isNotEmpty(factoryString)&&factoryString.equals(materialFactory)){
							    resultMsg = materialFactory+"生产的"+materialModel+"规格的"+materialName+"已经存在！";
							    resultFlag = false;
							}
						}
					}
				}
			}
		}
		
		if(resultFlag){
			materialCategoryEntity.setCode(materialEntity.getCode());
			materialCategoryEntity.setName(materialEntity.getName());
			materialCategoryEntity.setModel(materialEntity.getModel());
			materialCategoryEntity.setUnit(materialEntity.getUnit());
			materialCategoryEntity.setManagement(materialEntity.getManagement());
			materialCategoryEntity.setManufacturer(materialEntity.getManufacturer());
			materialCategoryEntity.setMaterialType(materialEntity.getMaterialType());
			materialCategoryEntity.setSupplierId(materialEntity.getSupplierId());
			materialCategoryService.updateEntity(materialCategoryEntity);
			resultMsg = "操作成功！";
		}
		
		resultObj.setResult(resultMsg);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MATERIALCATEGORY.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getshowPage/{id}")
	public ModelAndView getShowPage(HttpServletRequest request,@PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		MaterialCategoryEntity materialCategoryEntity = (MaterialCategoryEntity)materialCategoryService.findById(id);
		model.put("entity", materialCategoryEntity);
		model.put("entityJson", JsonUtil.toJson(materialCategoryEntity));

		return this.createModelAndView("cargo/materialCategory/materialCategoryDetail", model);
	}
	
	/**
	 * 
	 * 单行删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月25日 下午5:44:35
	 * @lastModified
	 */
	@RequestMapping("/singleDel/{id}")
	public @ResponseBody ResultObj deleteMaterial(@PathVariable Long id){
		materialCategoryService.deleteEntity(id);
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MATERIALCATEGORY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	/**
	 * 
	 * 多行删除
	 * 
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月25日 下午5:46:55
	 * @lastModified
	 */
	@RequestMapping("/multipleDel")
	public @ResponseBody ResultObj multipleDelete(@RequestBody List<Integer> ids){
		for (Integer id : ids) {
		long longId = (long)id;
		MaterialCategoryEntity materialCategoryEntity = this.getService().findById(longId);
		if(materialCategoryEntity != null){
			this.getService().deleteEntity(longId);
		}
	}
		ResultObj resultObj = new ResultObj();
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.MATERIALCATEGORY.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	
	/**
	 * 
	 * 添加入库明细物资页面
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:24:35
	 * @lastModified
	 */
	@RequestMapping("/selectMaterialCategory")
	public ModelAndView selectMaterialCategory(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//列表中已存在的数据id
		String[] dataId = request.getParameterValues("dataIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChooseId = request.getParameter("wareHouseChooseId");
		model.put("wareHouseChooseId", JsonUtil.toJson(wareHouseChooseId));
		//物资管理方式下拉框
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
//		//货区
//		List<Condition> conditions = new ArrayList<Condition>();
//		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
//		conditions.add(new Condition("C_WARE_HOUSE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,wareHouseChooseId));
//		List<WareHouseGoodsEntity> wareHouseGoodsEntities=wareHouseGoodsService.findByCondition(conditions, null);
//		ComboboxVO comboWareHouseGoodVO = new ComboboxVO();
//		for(WareHouseGoodsEntity wareHouseGoodsEntity:wareHouseGoodsEntities){
//			comboWareHouseGoodVO.addOption(wareHouseGoodsEntity.getId().toString(), wareHouseGoodsEntity.getGoodsArea());
//		}
//		model.put("goodsAreaIds", JsonUtil.toJson(comboWareHouseGoodVO.getOptions()));
//		//货区
//		ComboboxVO goodsAllocationVO = new ComboboxVO();
//		for(WareHouseGoodsEntity wareHouseGoodsEntity:wareHouseGoodsEntities){
//			goodsAllocationVO.addOption(wareHouseGoodsEntity.getId().toString(), wareHouseGoodsEntity.getGoodsAllocation());
//		}
//		model.put("goodsAllocationIds", JsonUtil.toJson(goodsAllocationVO.getOptions()));
		return this.createModelAndView("cargo/materialCategory/selectMaterialCategory", model);
	}
	
	/**
	 * 
	 * 添加入库物资明细选择
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月18日 上午9:26:45
	 * @lastModified
	 */
	@RequestMapping("/instockMaterialCategoryList")
	public @ResponseBody ResultListObj getInstockMCList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String[] existDataIdArray = existDataString.split(",");
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		//排除前台数组
		if(null != existDataIdArray && existDataIdArray.length!=0){
			conditions.add(new Condition("m.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
		}
		//取所有物资返回（ly增加物资数量显示）
		conditions.add(new Condition("m.C_QUOTE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findByCondition", conditions, pages);
		//获得返回结果
		resultListObj.setDraw((Integer)params.get("draw"));
		if (materialCategoryEntities != null) {
			if (pages != null) {
				resultListObj.setData(materialCategoryEntities);
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	
	/**
	 * 
	 * 修改入库明细物资页面
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午11:22:22
	 * @lastModified
	 */
	@RequestMapping("/selectMaterialCategory4Edit")
	public ModelAndView selectMaterialCategory4Edit(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//列表中已存在的数据id
		String[] dataId = request.getParameterValues("eidtInstockDataIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
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
		String[] materialIds = request.getParameterValues("instockDetailList");
		model.put("materialIds", materialIds);
		return this.createModelAndView("cargo/materialCategory/selectMaterial4Edit", model);
	}
	
	/**
	 * 
	 * 出库单添加页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:28:57
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategory")
	public ModelAndView selectOutMaterialCategory(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//列表中已存在的数据id
		String[] dataId = request.getParameterValues("dataIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose =request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterial", model);
	}
	/**
	 * 
	 * 出库单添加页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:28:57
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategoryIn")
	public ModelAndView selectOutMaterialCategoryIn(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//列表中已存在的数据id
		String[] dataId = request.getParameterValues("dataIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose =request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterialIn", model);
	}
	/**
	 * 
	 * 出库单添加页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:28:57
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategoryHandle")
	public ModelAndView selectOutMaterialCategoryHandle(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		//列表中已存在的数据id
		String[] dataId = request.getParameterValues("dataIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose =request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterialHandle", model);
	}
	
	/**
	 * 
	 * 出库单修改页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午4:39:07
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategory4EditIn")
	public ModelAndView selectOutMaterialCategory4EditIn(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String[] dataId = request.getParameterValues("materialOutStockIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterial4EditIn", model);
	}
	/**
	 * 
	 * 出库单修改页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午4:39:07
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategory4Edit")
	public ModelAndView selectOutMaterialCategory4Edit(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String[] dataId = request.getParameterValues("materialOutStockIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterial4Edit", model);
	}
	/**
	 * 
	 * 出库单修改页面选择出库物资明细
	 * 
	 * @param @param request
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 下午4:39:07
	 * @lastModified
	 */
	@RequestMapping("/selectOutStockMaterialCategory4EditHandle")
	public ModelAndView selectOutMaterialCategory4EditHandle(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String[] dataId = request.getParameterValues("materialOutStockIdArray");
		model.put("existDataId", JsonUtil.toJson(dataId));
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		model.put("wareHouseChoose", JsonUtil.toJson(wareHouseChoose));
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
		return this.createModelAndView("cargo/materialCategory/selectOutMaterial4EditHandle", model);
	}
	
	/**
	 * 
	 * 根据所选仓库在入库中选择出库物资明细页面查询物资方法
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchOutStockMaterial")
	public @ResponseBody ResultListObj outMaterialList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.clear();
		if (wareHouseChoose != null && !"null".equals(wareHouseChoose)) {
			conditions.add(new Condition("C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
			conditions.add(new Condition("C_APPROVE_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"4"));
		}
		List<InstockEntity> instockEntities = instockService.findByCondition(conditions, null);
		List<Long> instockEntityIds=new ArrayList<Long>();
		for(InstockEntity instockEntity:instockEntities){
			instockEntityIds.add(instockEntity.getId());
		}
		conditions.clear();
		conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockEntityIds.toArray()));
//		conditions.add(new Condition("a.C_COUNT",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,"0"));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, null);
		List<Long> instockDetailMaterialIds=new ArrayList<Long>();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockDetailMaterialIds.add(instockDetailEntity.getMaterialId());
		}
		
		conditions.clear();
		if (existDataString != null && !"null".equals(existDataString)) {
			String[] existDataIdArray = existDataString.split(",");
			if(null != existDataIdArray && existDataIdArray.length!=0){
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockDetailMaterialIds.toArray()));
				conditions.add(new Condition("st.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
				conditions.add(new Condition("st.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, "0.0"));
				conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
			}
		}else{
			conditions = new ArrayList<Condition>();
			conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));	
		}
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findOutStockMaterial", conditions, pages);
		List<MaterialCategoryEntity> materialCategoryCount = null;
		//物资数量为0，剔除
		if(materialCategoryEntities != null){
			materialCategoryCount = new ArrayList<MaterialCategoryEntity>();
			for(MaterialCategoryEntity m : materialCategoryEntities){
				if(!m.getInventoryCount().startsWith("0")){
					materialCategoryCount.add(m);
				}
			}
		}
		resultListObj.setDraw((Integer) params.get("draw"));
		if (materialCategoryCount != null) {
			resultListObj.setData(materialCategoryCount);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	/**
	 * 
	 * 根据所选仓库在入库中选择出库物资明细页面查询物资方法
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchOutStockMaterialIns")
	public @ResponseBody ResultListObj outMaterialListIns(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.clear();
		if (wareHouseChoose != null && !"null".equals(wareHouseChoose)) {
			conditions.add(new Condition("C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
		}
		List<InstockEntity> instockEntities = instockService.findByCondition(conditions, null);
		List<Long> instockEntityIds=new ArrayList<Long>();
		for(InstockEntity instockEntity:instockEntities){
			instockEntityIds.add(instockEntity.getId());
		}
		conditions.clear();
		conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockEntityIds.toArray()));
//		conditions.add(new Condition("a.C_COUNT",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,"0"));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, null);
		List<Long> instockDetailMaterialIds=new ArrayList<Long>();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockDetailMaterialIds.add(instockDetailEntity.getMaterialId());
		}
		
		conditions.clear();
		if (existDataString != null && !"null".equals(existDataString)) {
			String[] existDataIdArray = existDataString.split(",");
			if(null != existDataIdArray && existDataIdArray.length!=0){
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockDetailMaterialIds.toArray()));
				conditions.add(new Condition("st.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
				conditions.add(new Condition("st.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, "0.0"));
				conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
			}
		}
		
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findOutStockMaterial", conditions, pages);
		List<MaterialCategoryEntity> materialCategoryCount = null;
		//物资数量为0，剔除
		if(materialCategoryEntities != null){
			materialCategoryCount = new ArrayList<MaterialCategoryEntity>();
			for(MaterialCategoryEntity m : materialCategoryEntities){
				if(!m.getInventoryCount().startsWith("0")){
					materialCategoryCount.add(m);
				}
			}
		}
		resultListObj.setDraw((Integer) params.get("draw"));
		if (materialCategoryCount != null) {
			resultListObj.setData(materialCategoryCount);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	/**
	 * 
	 * 根据所选仓库在入库中选择出库物资明细页面查询物资方法
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchOutStockMaterialIn")
	public @ResponseBody ResultListObj outMaterialListIn(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.clear();
		if (wareHouseChoose != null && !"null".equals(wareHouseChoose)) {
			conditions.add(new Condition("C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
		}
		List<InstockEntity> instockEntities = instockService.findByCondition(conditions, null);
		List<Long> instockEntityIds=new ArrayList<Long>();
		for(InstockEntity instockEntity:instockEntities){
			instockEntityIds.add(instockEntity.getId());
		}
		conditions.clear();
		conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockEntityIds.toArray()));
//		conditions.add(new Condition("a.C_COUNT",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,"0"));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, null);
		List<Long> instockDetailMaterialIds=new ArrayList<Long>();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockDetailMaterialIds.add(instockDetailEntity.getMaterialId());
		}
		
		conditions.clear();
		if (existDataString != null && !"null".equals(existDataString)) {
			String[] existDataIdArray = existDataString.split(",");
			if(null != existDataIdArray && existDataIdArray.length!=0){
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockDetailMaterialIds.toArray()));
				conditions.add(new Condition("st.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
				conditions.add(new Condition("st.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, "0.0"));
				conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
			}
		}
		
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findOutStockMaterial", conditions, pages);
		List<MaterialCategoryEntity> materialCategoryCount = null;
		//物资数量为0，剔除
		if(materialCategoryEntities != null){
			materialCategoryCount = new ArrayList<MaterialCategoryEntity>();
			for(MaterialCategoryEntity m : materialCategoryEntities){
				if(!m.getInventoryCount().startsWith("0")){
					materialCategoryCount.add(m);
				}
			}
		}
		resultListObj.setDraw((Integer) params.get("draw"));
		if (materialCategoryCount != null) {
			resultListObj.setData(materialCategoryCount);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	/**
	 * 
	 * 根据所选仓库在入库中选择出库物资明细页面查询物资方法
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchOutStockMaterialHandleO")
	public @ResponseBody ResultListObj outMaterialListHandleO(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.clear();
		if (wareHouseChoose != null && !"null".equals(wareHouseChoose)) {
			conditions.add(new Condition("C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
		}
		List<InstockEntity> instockEntities = instockService.findByCondition(conditions, null);
		List<Long> instockEntityIds=new ArrayList<Long>();
		for(InstockEntity instockEntity:instockEntities){
			instockEntityIds.add(instockEntity.getId());
		}
		conditions.clear();
		conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockEntityIds.toArray()));
//		conditions.add(new Condition("a.C_COUNT",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,"0"));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, null);
		List<Long> instockDetailMaterialIds=new ArrayList<Long>();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockDetailMaterialIds.add(instockDetailEntity.getMaterialId());
		}
		
		conditions.clear();
		if (existDataString != null && !"null".equals(existDataString)) {
			String[] existDataIdArray = existDataString.split(",");
			if(null != existDataIdArray && existDataIdArray.length!=0){
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
//				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockDetailMaterialIds.toArray()));
				conditions.add(new Condition("st.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
				conditions.add(new Condition("st.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, "0.0"));
				conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
			}
		}
		
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findOutStockMaterial", conditions, pages);
		List<MaterialCategoryEntity> materialCategoryCount = null;
		//物资数量为0，剔除
		if(materialCategoryEntities != null){
			materialCategoryCount = new ArrayList<MaterialCategoryEntity>();
			for(MaterialCategoryEntity m : materialCategoryEntities){
				if(!m.getInventoryCount().startsWith("0")){
					materialCategoryCount.add(m);
				}
			}
		}
		resultListObj.setDraw((Integer) params.get("draw"));
		if (materialCategoryCount != null) {
			resultListObj.setData(materialCategoryCount);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
	/**
	 * 
	 * 根据所选仓库在入库中选择出库物资明细页面查询物资方法
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author liweiran
	 * @created 2017年8月3日 上午10:25:33
	 * @lastModified
	 */
	@RequestMapping("/searchOutStockMaterialHandle")
	public @ResponseBody ResultListObj outMaterialListHandle(HttpServletRequest request,@RequestBody Map<String, Object> params){
		ResultListObj resultListObj = new ResultListObj();
		Page<MaterialCategoryEntity> pages = PageUtil.getPage(params);
		pages.addOrder(Sort.desc("id"));//zzq修改为了弹出框是正序的(ly根据测试新的要求，改为倒序)
		//接收前台传送已有数据的物资id
		String existDataString = request.getParameter("existDataId");
		String wareHouseChoose = request.getParameter("wareHouseChoose");
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.clear();
		if (wareHouseChoose != null && !"null".equals(wareHouseChoose)) {
			conditions.add(new Condition("C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
		}
		List<InstockEntity> instockEntities = instockService.findByCondition(conditions, null);
		List<Long> instockEntityIds=new ArrayList<Long>();
		for(InstockEntity instockEntity:instockEntities){
			instockEntityIds.add(instockEntity.getId());
		}
		conditions.clear();
		conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_INSTOCK_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockEntityIds.toArray()));
//		conditions.add(new Condition("a.C_COUNT",FieldTypeEnum.STRING,MatchTypeEnum.NOT_IN,"0"));
		List<InstockDetailEntity> instockDetailEntities = instockDetailService.findByCondition(conditions, null);
		List<Long> instockDetailMaterialIds=new ArrayList<Long>();
		for(InstockDetailEntity instockDetailEntity:instockDetailEntities){
			instockDetailMaterialIds.add(instockDetailEntity.getMaterialId());
		}
		
		conditions.clear();
		if (existDataString != null && !"null".equals(existDataString)) {
			String[] existDataIdArray = existDataString.split(",");
			if(null != existDataIdArray && existDataIdArray.length!=0){
				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.NOT_IN,existDataIdArray));
//				conditions.add(new Condition("mc.C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,instockDetailMaterialIds.toArray()));
				conditions.add(new Condition("st.C_WARE_HOUSE_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,wareHouseChoose));
				conditions.add(new Condition("st.C_INVENTORY_QUANTITY", FieldTypeEnum.STRING, MatchTypeEnum.GT, "0.0"));
				conditions.add(new Condition("st.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
			}
		}
		
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition("findOutStockMaterial", conditions, pages);
		List<MaterialCategoryEntity> materialCategoryCount = null;
		//物资数量为0，剔除
		if(materialCategoryEntities != null){
			materialCategoryCount = new ArrayList<MaterialCategoryEntity>();
			for(MaterialCategoryEntity m : materialCategoryEntities){
				if(!m.getInventoryCount().startsWith("0")){
					materialCategoryCount.add(m);
				}
			}
		}
		resultListObj.setDraw((Integer) params.get("draw"));
		if (materialCategoryCount != null) {
			resultListObj.setData(materialCategoryCount);
			if (pages != null) {
				resultListObj.setRecordsTotal(pages.getTotal());
			}
		}
		return resultListObj;
	}
}