package com.aptech.business.cargo.scrapLibraryDetailOut.web;

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

import com.aptech.business.cargo.scrapLibraryDetailOut.domain.ScrapLibraryDetailOutEntity;
import com.aptech.business.cargo.scrapLibraryDetailOut.service.ScrapLibraryDetailOutService;
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
 * 报废申请外库物资明细配置控制器
 *
 * @author 
 * @created 2018-08-22 16:54:34
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/scrapLibraryDetailOut")
public class ScrapLibraryDetailOutController extends BaseController<ScrapLibraryDetailOutEntity> {
	
	@Autowired
	private ScrapLibraryDetailOutService scrapLibraryDetailOutService;
	
	@Override
	public IBaseEntityOperation<ScrapLibraryDetailOutEntity> getService() {
		return scrapLibraryDetailOutService;
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
		List<ScrapLibraryDetailOutEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailOutVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutCombobox", JsonUtil.toJson(comboScrapLibraryDetailOutVO.getOptions()));
		return this.createModelAndView("scrapLibraryDetailOut/resource/views/scrapLibraryDetailOutList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<ScrapLibraryDetailOutEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailOutVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutCombobox", JsonUtil.toJson(comboScrapLibraryDetailOutVO.getOptions()));
		
		return this.createModelAndView("scrapLibraryDetailOut/resource/views/scrapLibraryDetailOutAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ScrapLibraryDetailOutEntity scrapLibraryDetailOutEntity = (ScrapLibraryDetailOutEntity)scrapLibraryDetailOutService.findById(id);
		model.put("entity", scrapLibraryDetailOutEntity);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryDetailOutEntity));
		
		List<ScrapLibraryDetailOutEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailOutVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailOutCombobox", JsonUtil.toJson(comboScrapLibraryDetailOutVO.getOptions()));
		
		return this.createModelAndView("scrapLibraryDetailOut/resource/views/scrapLibraryDetailOutEdit", model);
	}
}