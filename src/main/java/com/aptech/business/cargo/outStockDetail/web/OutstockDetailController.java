package com.aptech.business.cargo.outStockDetail.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.business.cargo.outStockDetail.service.OutstockDetailService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;


/**
 * 
 * 出库物资明细配置控制器
 *
 * @author wangyue
 * @created 2017年7月25日 下午6:38:11 
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/outstockDetail")
public class OutstockDetailController extends BaseController<OutstockDetailEntity> {
	
	@Autowired
	private OutstockDetailService outstockDetailService;
	
	@Override
	public IBaseEntityOperation<OutstockDetailEntity> getService() {
		return outstockDetailService;
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
		List<OutstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("outstockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOutstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("outstockDetailCombobox", JsonUtil.toJson(comboOutstockDetailVO.getOptions()));
		return this.createModelAndView("outstockDetail/outstockDetailList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<OutstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("outstockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOutstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("outstockDetailCombobox", JsonUtil.toJson(comboOutstockDetailVO.getOptions()));
		
		return this.createModelAndView("outstockDetail/outstockDetailAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OutstockDetailEntity outstockDetailEntity = (OutstockDetailEntity)outstockDetailService.findById(id);
		model.put("entity", outstockDetailEntity);
		model.put("entityJson", JsonUtil.toJson(outstockDetailEntity));
		
		List<OutstockDetailEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("outstockDetailTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOutstockDetailVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("outstockDetailCombobox", JsonUtil.toJson(comboOutstockDetailVO.getOptions()));
		
		return this.createModelAndView("outstockDetail/outstockDetailEdit", model);
	}
}