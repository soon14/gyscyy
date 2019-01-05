package com.aptech.business.cargo.scrapLibraryDetail.web;

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

import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.business.cargo.scrapLibraryDetail.service.ScrapLibraryDetailService;
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
 * 报废库明细配置控制器
 *
 * @author 
 * @created 2018-03-16 19:29:22
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/scrapLibraryDetail")
public class ScrapLibraryDetailController extends BaseController<ScrapLibraryDetailEntity> {
	
	@Autowired
	private ScrapLibraryDetailService scrapLibraryDetailService;
	
	@Override
	public IBaseEntityOperation<ScrapLibraryDetailEntity> getService() {
		return scrapLibraryDetailService;
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
		List<ScrapLibraryDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailCombobox", JsonUtil.toJson(comboScrapLibraryDetailVO.getOptions()));
		return this.createModelAndView("scrapLibraryDetail/resource/views/scrapLibraryDetailList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<ScrapLibraryDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailCombobox", JsonUtil.toJson(comboScrapLibraryDetailVO.getOptions()));
		
		return this.createModelAndView("scrapLibraryDetail/resource/views/scrapLibraryDetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		ScrapLibraryDetailEntity scrapLibraryDetailEntity = (ScrapLibraryDetailEntity)scrapLibraryDetailService.findById(id);
		model.put("entity", scrapLibraryDetailEntity);
		model.put("entityJson", JsonUtil.toJson(scrapLibraryDetailEntity));
		
		List<ScrapLibraryDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("scrapLibraryDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboScrapLibraryDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("scrapLibraryDetailCombobox", JsonUtil.toJson(comboScrapLibraryDetailVO.getOptions()));
		
		return this.createModelAndView("scrapLibraryDetail/resource/views/scrapLibraryDetailEdit", model);
	}
}