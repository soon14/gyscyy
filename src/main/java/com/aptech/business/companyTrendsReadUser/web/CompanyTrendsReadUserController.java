package com.aptech.business.companyTrendsReadUser.web;

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

import com.aptech.business.companyTrendsReadUser.domain.CompanyTrendsReadUserEntity;
import com.aptech.business.companyTrendsReadUser.service.CompanyTrendsReadUserService;
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
 * 公司动态已读用户配置控制器
 *
 * @author 
 * @created 2018-07-27 11:24:27
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/companyTrendsReadUser")
public class CompanyTrendsReadUserController extends BaseController<CompanyTrendsReadUserEntity> {
	
	@Autowired
	private CompanyTrendsReadUserService companyTrendsReadUserService;
	
	@Override
	public IBaseEntityOperation<CompanyTrendsReadUserEntity> getService() {
		return companyTrendsReadUserService;
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
		List<CompanyTrendsReadUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("companyTrendsReadUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboCompanyTrendsReadUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("companyTrendsReadUserCombobox", JsonUtil.toJson(comboCompanyTrendsReadUserVO.getOptions()));
		return this.createModelAndView("companyTrendsReadUser/resource/views/companyTrendsReadUserList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<CompanyTrendsReadUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("companyTrendsReadUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboCompanyTrendsReadUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("companyTrendsReadUserCombobox", JsonUtil.toJson(comboCompanyTrendsReadUserVO.getOptions()));
		
		return this.createModelAndView("companyTrendsReadUser/resource/views/companyTrendsReadUserAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		CompanyTrendsReadUserEntity companyTrendsReadUserEntity = (CompanyTrendsReadUserEntity)companyTrendsReadUserService.findById(id);
		model.put("entity", companyTrendsReadUserEntity);
		model.put("entityJson", JsonUtil.toJson(companyTrendsReadUserEntity));
		
		List<CompanyTrendsReadUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("companyTrendsReadUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboCompanyTrendsReadUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("companyTrendsReadUserCombobox", JsonUtil.toJson(comboCompanyTrendsReadUserVO.getOptions()));
		
		return this.createModelAndView("companyTrendsReadUser/resource/views/companyTrendsReadUserEdit", model);
	}
}