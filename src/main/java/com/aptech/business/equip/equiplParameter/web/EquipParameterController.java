package com.aptech.business.equip.equiplParameter.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.equip.equiplParameter.domain.EquipParameterEntity;
import com.aptech.business.equip.equiplParameter.service.EquipParameterService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备参数配置控制器
 *
 * @author 
 * @created 2017-06-12 14:04:46
 * @lastModified 
 * @history
 *
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/equipParameter")
public class EquipParameterController extends BaseController<EquipParameterEntity> {
	
	@Autowired
	private EquipParameterService equipParameterService;
	
	@Override
	public IBaseEntityOperation<EquipParameterEntity> getService() {
		return equipParameterService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{appId}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipParameterEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipParameterTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipParameterVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipParameterCombobox", JsonUtil.toJson(comboEquipParameterVO.getOptions()));
		return this.createModelAndView("equip/equipmodel/equipParameterMoveList", model);
	}
	
	/**
	 *	list选择带回
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/equipSelectParameterList/{appId}")
	public ModelAndView selectParameterList(HttpServletRequest request, Map<String, Object> params, @PathVariable Long appId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipParameterEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipParameterTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipParameterVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipParameterCombobox", JsonUtil.toJson(comboEquipParameterVO.getOptions()));
		return this.createModelAndView("equip/equipmodel/equipSelectParameterList", model);
	}
	
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipParameterEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipParameterTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipParameterVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipParameterCombobox", JsonUtil.toJson(comboEquipParameterVO.getOptions()));
		
		return this.createModelAndView("equip/equipmodel/equipParameterMoveAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		Map<String, Object> dataMap = (Map<String, Object>)equipParameterService.findById(id);
		dataMap.put("id", id);
		model.put("dataMap", dataMap);
		model.put("dataMapJson", JsonUtil.toJson(dataMap));
		
		List<EquipParameterEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipParameterTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipParameterVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipParameterCombobox", JsonUtil.toJson(comboEquipParameterVO.getOptions()));
		
		return this.createModelAndView("equip/equipmodel/equipParameterMoveEdit", model);
	}

	
	
}