package com.aptech.business.supplier.supplierContact.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.supplier.supplierContact.domain.SupplierContactEntity;
import com.aptech.business.supplier.supplierContact.service.SupplierContactService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 供应商联系人关联表配置控制器
 *
 * @author 
 * @created 2017-11-02 14:14:40
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/supplierContact")
public class SupplierContactController extends BaseController<SupplierContactEntity> {
	
	@Autowired
	private SupplierContactService supplierContactService;
	
	@Override
	public IBaseEntityOperation<SupplierContactEntity> getService() {
		return supplierContactService;
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
		List<SupplierContactEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("supplierContactTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSupplierContactVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("supplierContactCombobox", JsonUtil.toJson(comboSupplierContactVO.getOptions()));
		return this.createModelAndView("supplierContact/supplierContactList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<SupplierContactEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("supplierContactTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSupplierContactVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("supplierContactCombobox", JsonUtil.toJson(comboSupplierContactVO.getOptions()));
		
		return this.createModelAndView("supplierContact/supplierContactAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		SupplierContactEntity supplierContactEntity = (SupplierContactEntity)supplierContactService.findById(id);
		model.put("entity", supplierContactEntity);
		model.put("entityJson", JsonUtil.toJson(supplierContactEntity));
		
		List<SupplierContactEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("supplierContactTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboSupplierContactVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("supplierContactCombobox", JsonUtil.toJson(comboSupplierContactVO.getOptions()));
		
		return this.createModelAndView("supplierContact/supplierContactEdit", model);
	}
}