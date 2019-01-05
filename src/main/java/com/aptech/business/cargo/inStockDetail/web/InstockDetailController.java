package com.aptech.business.cargo.inStockDetail.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.business.cargo.inStockDetail.service.InstockDetailService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 入库物资明细配置控制器
 *
 * @author 
 * @created 2017-07-24 16:20:23
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/instockDetail")
public class InstockDetailController extends BaseController<InstockDetailEntity> {
	
	@Autowired
	private InstockDetailService instockDetailService;
	
	@Override
	public IBaseEntityOperation<InstockDetailEntity> getService() {
		return instockDetailService;
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
		List<InstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("instockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboInstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("instockDetailCombobox", JsonUtil.toJson(comboInstockDetailVO.getOptions()));
		return this.createModelAndView("instockDetail/resource/views/instockDetailList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<InstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("instockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboInstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("instockDetailCombobox", JsonUtil.toJson(comboInstockDetailVO.getOptions()));
		
		return this.createModelAndView("instockDetail/resource/views/instockDetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		InstockDetailEntity instockDetailEntity = (InstockDetailEntity)instockDetailService.findById(id);
		model.put("entity", instockDetailEntity);
		model.put("entityJson", JsonUtil.toJson(instockDetailEntity));
		
		List<InstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("instockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboInstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("instockDetailCombobox", JsonUtil.toJson(comboInstockDetailVO.getOptions()));
		
		return this.createModelAndView("instockDetail/resource/views/instockDetailEdit", model);
	}
}