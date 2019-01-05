package com.aptech.business.supplier.supplier.web;

import java.io.UnsupportedEncodingException;
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

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.cargo.materialCategory.domain.MaterialCategoryEntity;
import com.aptech.business.cargo.materialCategory.service.MaterialCategoryService;
import com.aptech.business.supplier.supplier.domain.SupplierEntity;
import com.aptech.business.supplier.supplier.service.SupplierService;
import com.aptech.business.supplier.supplierContact.domain.SupplierContactEntity;
import com.aptech.business.supplier.supplierContact.service.SupplierContactService;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
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
import com.mxgraph.util.svg.ParseException;

/**
 * 
 * 供应商管理配置控制器
 *
 * @author 
 * @created 2017-11-02 10:30:36
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseController<SupplierEntity> {
	
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierContactService supplierContactService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
	@Override
	public IBaseEntityOperation<SupplierEntity> getService() {
		return supplierService;
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
		model.put("supplierType", JsonUtil.toJson(supplierService.getSupplierType().getOptions()));
		return this.createModelAndView("supplier/supplierList", model);
	}
	
	/**
	 * 
	 * 列表检索
	 * 
	 * @param @param request
	 * @param @param params
	 * @param @return
	 * @return ResultListObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午7:35:49
	 * @lastModified
	 */
	@RequestMapping(value = "/data/list")
	public  @ResponseBody ResultListObj dataList(HttpServletRequest request,@RequestBody Map<String, Object> params){
		Page<SupplierEntity> pages=PageUtil.getPage(params);
		pages.addOrder(Sort.asc("supplierName"));
		List<Condition>conditions=OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.LONG, MatchTypeEnum.EQ,'N'));
		List<SupplierEntity>supplierEntities = supplierService.findByCondition(conditions, pages);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (supplierEntities != null) {
			if (pages != null) {
				resultObj.setData(supplierEntities);
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
		model.put("supplierType", JsonUtil.toJson(supplierService.getSupplierType().getOptions()));
		model.put("sexType", JsonUtil.toJson(supplierService.getSexType().getOptions()));
		return this.createModelAndView("supplier/supplierAdd", model);
	}
	
	/**
	 * 
	 * 添加保存
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午2:38:49
	 * @lastModified
	 */
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("s.C_SUPPLIER_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,entityMap.get("supplierName").toString()));
		List<SupplierEntity> supplierEntities = supplierService.findByCondition(conditions, null);
		if(supplierEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		//添加数据到供应商表
		SupplierEntity supplierEntity = new SupplierEntity();
		supplierEntity.setSupplierName(entityMap.get("supplierName").toString());
		supplierEntity.setSupplierType(entityMap.get("supplierType").toString());
		supplierEntity.setDeleteFlag("N");
		if(StringUtil.isNotEmpty((String)entityMap.get("address"))){
			supplierEntity.setAddress(entityMap.get("address").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyEmail"))){
			supplierEntity.setCompanyEmail(entityMap.get("companyEmail").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyFax"))){
			supplierEntity.setCompanyFax(entityMap.get("companyFax").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyTel"))){
			supplierEntity.setCompanyTel(entityMap.get("companyTel").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyWebsite"))){
			supplierEntity.setCompanyWebsite(entityMap.get("companyWebsite").toString());
		}
		supplierService.addEntity(supplierEntity);
		//添加数据到供应商联系人关联表
		if(!entityMap.get("contactName").toString().contains("[")){
			SupplierContactEntity supplierContactEntity=new SupplierContactEntity();
			supplierContactEntity.setSupplierId(supplierEntity.getId());
			if(StringUtil.isNotEmpty((String)entityMap.get("contactBusiness"))){
				supplierContactEntity.setBusiness(entityMap.get("contactBusiness").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactDepartment"))){
				supplierContactEntity.setDepartment(entityMap.get("contactDepartment").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactDuty"))){
				supplierContactEntity.setDuty(entityMap.get("contactDuty").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactEmail"))){
				supplierContactEntity.setEmail(entityMap.get("contactEmail").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactMobilePhone"))){
				supplierContactEntity.setMobilePhone(entityMap.get("contactMobilePhone").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactName"))){
				supplierContactEntity.setName(entityMap.get("contactName").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactTelephone"))){
				supplierContactEntity.setTelephone(entityMap.get("contactTelephone").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactSex"))){
				supplierContactEntity.setSex(entityMap.get("contactSex").toString());
			}
			supplierContactEntity.setDeleteFlag("N");
			supplierContactService.addEntity(supplierContactEntity);
		}else {
			SupplierContactEntity supplierContactEntity=new SupplierContactEntity();
			List<String> contactName = (List<String>)entityMap.get("contactName");
			List<String> contactBusiness = (List<String>)entityMap.get("contactBusiness");
			List<String> contactDepartment = (List<String>)entityMap.get("contactDepartment");
			List<String> contactDuty = (List<String>)entityMap.get("contactDuty");
			List<String> contactEmail = (List<String>)entityMap.get("contactEmail");
			List<String> contactMobilePhone = (List<String>)entityMap.get("contactMobilePhone");
			List<String> contactTelephone = (List<String>)entityMap.get("contactTelephone");
			List<String> contactSex = (List<String>)entityMap.get("contactSex");
			for(int i=0;i<contactName.size();i++){
				supplierContactEntity.setName(contactName.get(i));
				supplierContactEntity.setBusiness(contactBusiness.get(i));
				supplierContactEntity.setDepartment(contactDepartment.get(i));
				supplierContactEntity.setDuty(contactDuty.get(i));
				supplierContactEntity.setEmail(contactEmail.get(i));
				supplierContactEntity.setMobilePhone(contactMobilePhone.get(i));
				supplierContactEntity.setTelephone(contactTelephone.get(i));
				supplierContactEntity.setSex(contactSex.get(i));
				supplierContactEntity.setSupplierId(supplierEntity.getId());
				supplierContactEntity.setDeleteFlag("N");
				supplierContactService.addEntity(supplierContactEntity);
			}
		}
		return resultObj;
	}
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SupplierEntity supplierEntity = (SupplierEntity)supplierService.findById(id);
		model.put("entity", supplierEntity);
		model.put("entityJson", JsonUtil.toJson(supplierEntity));
		
		model.put("supplierType", JsonUtil.toJson(supplierService.getSupplierType().getOptions()));
		model.put("sexType", JsonUtil.toJson(supplierService.getSexType().getOptions()));
		return this.createModelAndView("supplier/supplierEdit", model);
	}
	
	/**
	 * 
	 * 修改保存
	 * 
	 * @param @param entityMap
	 * @param @return
	 * @param @throws ParseException
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午5:06:28
	 * @lastModified
	 */
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody Map<String, Object> entityMap) throws ParseException{
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("s.C_SUPPLIER_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,entityMap.get("supplierName").toString()));
		conditions.add(new Condition("s.C_ID", FieldTypeEnum.STRING, MatchTypeEnum.NE,entityMap.get("id").toString()));
		List<SupplierEntity> supplierEntities = supplierService.findByCondition(conditions, null);
		if(supplierEntities.size()>0){
			resultObj.setResult("exception");
			return resultObj;
		}
		//修改数据到供应商表
		SupplierEntity supplierEntity = new SupplierEntity();
		supplierEntity.setId(Long.parseLong(entityMap.get("id").toString()));
		supplierEntity.setSupplierName(entityMap.get("supplierName").toString());
		supplierEntity.setSupplierType(entityMap.get("supplierType").toString());
		supplierEntity.setDeleteFlag("N");
		if(StringUtil.isNotEmpty((String)entityMap.get("address"))){
			supplierEntity.setAddress(entityMap.get("address").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyEmail"))){
			supplierEntity.setCompanyEmail(entityMap.get("companyEmail").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyFax"))){
			supplierEntity.setCompanyFax(entityMap.get("companyFax").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyTel"))){
			supplierEntity.setCompanyTel(entityMap.get("companyTel").toString());
		}
		if(StringUtil.isNotEmpty((String)entityMap.get("companyWebsite"))){
			supplierEntity.setCompanyWebsite(entityMap.get("companyWebsite").toString());
		}
		supplierService.updateEntity(supplierEntity);
		//修改数据到供应商联系人关联表
		if(!entityMap.get("contactName").toString().contains("[")){
			SupplierContactEntity supplierContactEntity=supplierContactService.findById(supplierEntity.getId());
			supplierContactEntity.setSupplierId(supplierEntity.getId());
			if(StringUtil.isNotEmpty((String)entityMap.get("contactBusiness"))){
				supplierContactEntity.setBusiness(entityMap.get("contactBusiness").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactDepartment"))){
				supplierContactEntity.setDepartment(entityMap.get("contactDepartment").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactDuty"))){
				supplierContactEntity.setDuty(entityMap.get("contactDuty").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactEmail"))){
				supplierContactEntity.setEmail(entityMap.get("contactEmail").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactMobilePhone"))){
				supplierContactEntity.setMobilePhone(entityMap.get("contactMobilePhone").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactName"))){
				supplierContactEntity.setName(entityMap.get("contactName").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactTelephone"))){
				supplierContactEntity.setTelephone(entityMap.get("contactTelephone").toString());
			}
			if(StringUtil.isNotEmpty((String)entityMap.get("contactSex"))){
				supplierContactEntity.setSex(entityMap.get("contactSex").toString());
			}
			supplierContactEntity.setDeleteFlag("N");
			supplierContactService.updateEntity(supplierContactEntity);
		}else {
			supplierContactService.deleteEntity(supplierEntity.getId());
			SupplierContactEntity supplierContactMore=new SupplierContactEntity();
			List<String> contactName = (List<String>)entityMap.get("contactName");
			List<String> contactBusiness = (List<String>)entityMap.get("contactBusiness");
			List<String> contactDepartment = (List<String>)entityMap.get("contactDepartment");
			List<String> contactDuty = (List<String>)entityMap.get("contactDuty");
			List<String> contactEmail = (List<String>)entityMap.get("contactEmail");
			List<String> contactMobilePhone = (List<String>)entityMap.get("contactMobilePhone");
			List<String> contactTelephone = (List<String>)entityMap.get("contactTelephone");
			List<String> contactSex = (List<String>)entityMap.get("contactSex");
			for(int i=0;i<contactName.size();i++){
				supplierContactMore.setName(contactName.get(i));
				supplierContactMore.setBusiness(contactBusiness.get(i));
				supplierContactMore.setDepartment(contactDepartment.get(i));
				supplierContactMore.setDuty(contactDuty.get(i));
				supplierContactMore.setEmail(contactEmail.get(i));
				supplierContactMore.setMobilePhone(contactMobilePhone.get(i));
				supplierContactMore.setTelephone(contactTelephone.get(i));
				supplierContactMore.setSex(contactSex.get(i));
				supplierContactMore.setSupplierId(supplierEntity.getId());
				supplierContactMore.setDeleteFlag("N");
				supplierContactService.addEntity(supplierContactMore);
			}
		}
		return resultObj;
	}
	
	/**
	 * 
	 * 单行删除
	 * 
	 * @param @param id
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午7:29:24
	 * @lastModified
	 */
	@RequestMapping("/deleteSingle/{id}")
    public @ResponseBody ResultObj deleteSingle(@PathVariable Long id){
		ResultObj resultObj = new ResultObj();
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition(conditions, null);
		List<Long> materialCategory=new ArrayList<Long>();
		for(MaterialCategoryEntity materialCategoryEntity:materialCategoryEntities){
			materialCategory.add(materialCategoryEntity.getSupplierId());
		}
		if(!materialCategory.contains(id)){
			//删除供应商信息
			SupplierEntity supplierEntity = supplierService.findById(id);
			supplierEntity.setDeleteFlag("Y");
			supplierService.updateEntity(supplierEntity);
			//删除供应商联系人关联信息
			conditions.clear();
			conditions.add(new Condition("C_SUPPLIER_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));
			List<SupplierContactEntity> supplierContactEntities = supplierContactService.findByCondition(conditions, null);
			for (SupplierContactEntity supplierContactEntity:supplierContactEntities) {
				supplierContactEntity.setDeleteFlag("Y");
				supplierContactService.updateEntity(supplierContactEntity);
				resultObj.setResult("success");
			}
		}else {
			resultObj.setResult("exception");
			return resultObj;
		}
    	return resultObj;
    }
	
	/**
	 * 
	 * 多行删除
	 * 
	 * @param @param request
	 * @param @param ids
	 * @param @return
	 * @return ResultObj
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午7:41:14
	 * @lastModified
	 */
	@RequestMapping("/deleteMulti/{ids}")
    public @ResponseBody ResultObj deleteMulti(HttpServletRequest request, @PathVariable String ids){
		ResultObj resultObj = new ResultObj();
		String[] supplierIds = ids.split(",");
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"N"));
		List<MaterialCategoryEntity> materialCategoryEntities = materialCategoryService.findByCondition(conditions, null);
		List<String> materialCategory=new ArrayList<String>();
		for(MaterialCategoryEntity materialCategoryEntity:materialCategoryEntities){
			materialCategory.add(materialCategoryEntity.getSupplierId().toString());
		}
		boolean judge=true;
		for (int i = 0; i < supplierIds.length; i++) {
			if(materialCategory.indexOf(supplierIds[i])>=0){
				judge=false;
				break;
			}
		}
		for(int i=0;i<supplierIds.length;i++){
			if(judge==true){
				SupplierEntity supplierEntity=supplierService.findById(Long.valueOf(supplierIds[i]));
				supplierEntity.setDeleteFlag("Y");
				supplierService.updateEntity(supplierEntity);
				//删除供应商联系人关联信息
				conditions.clear();
				conditions.add(new Condition("C_SUPPLIER_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,Long.valueOf(supplierIds[i])));
				List<SupplierContactEntity> supplierContactEntities = supplierContactService.findByCondition(conditions, null);
				for(SupplierContactEntity supplierContactEntity:supplierContactEntities){
					supplierContactEntity.setDeleteFlag("Y");
					supplierContactService.updateEntity(supplierContactEntity);
					resultObj.setResult("success");
				}
			}else {
				resultObj.setResult("exception");
				return resultObj;
			}
		}
    	return resultObj;
    }
	
	/**
	 * 
	 * 导出
	 * 
	 * @param @param req
	 * @param @param res
	 * @param @throws UnsupportedEncodingException
	 * @return void
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 上午9:33:58
	 * @lastModified
	 */
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest req,HttpServletResponse res) throws UnsupportedEncodingException{
		String searchsupplierName = req.getParameter("searchsupplierName");
		String searchaddress = req.getParameter("searchaddress");
		String supplierType = req.getParameter("supplierType");
		Page<SupplierEntity> pages = new Page<SupplierEntity>();
		pages.addOrder(Sort.asc("supplierName"));
		pages.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions=new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(searchsupplierName)){
			conditions.add(new Condition("supplierName",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchsupplierName));
		}
		if(StringUtil.isNotEmpty(searchaddress)){
			conditions.add(new Condition("address",FieldTypeEnum.STRING,MatchTypeEnum.LIKE,searchaddress));
		}
		if(StringUtil.isNotEmpty(supplierType)){
			conditions.add(new Condition("supplierType",FieldTypeEnum.STRING,MatchTypeEnum.EQ,supplierType));
		}
		conditions.add(new Condition("deleteFlag",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"N"));
		List<SupplierEntity> dataList=supplierService.findByCondition(conditions, pages);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "供应商管理报表模板.xlsx","供应商管理.xlsx", resultMap);
	}
}