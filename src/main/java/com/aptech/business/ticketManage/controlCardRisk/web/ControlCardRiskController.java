package com.aptech.business.ticketManage.controlCardRisk.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 工作票控制卡风险与措施配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:23
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/controlCardRisk")
public class ControlCardRiskController extends BaseController<ControlCardRiskEntity> {
	
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	
	@Override
	public IBaseEntityOperation<ControlCardRiskEntity> getService() {
		return controlCardRiskService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<ControlCardRiskEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("controlCardRiskTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboControlCardRiskVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("controlCardRiskCombobox", JsonUtil.toJson(comboControlCardRiskVO.getOptions()));
		return this.createModelAndView("ticketManage/controlcardrisk/controlCardRiskList", model);
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String cardId=request.getParameter("cardId");
		String uuid=request.getParameter("uuid");
		List<ControlCardRiskEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("controlCardRiskTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboControlCardRiskVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("controlCardRiskCombobox", JsonUtil.toJson(comboControlCardRiskVO.getOptions()));
		model.put("cardId", cardId);
		model.put("uuid", uuid);
		return this.createModelAndView("ticketManage/controlcardrisk/controlCardRiskAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		ControlCardRiskEntity controlCardRiskEntity=controlCardRiskService.findById(id);
		List<ControlCardRiskEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("controlCardRiskTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboControlCardRiskVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("controlCardRiskCombobox", JsonUtil.toJson(comboControlCardRiskVO.getOptions()));
		model.put("controlCardRiskEntity", controlCardRiskEntity);
		return this.createModelAndView("ticketManage/controlcardrisk/controlCardRiskEdit", model);
	}
}