package com.aptech.business.equip.equipAbnormalEquipRel.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 设备异动设备关联表配置控制器
 *
 * @author 
 * @created 2018-09-10 17:38:22
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/equipAbnormalEquipRel")
public class EquipAbnormalEquipRelController extends BaseController<EquipAbnormalEquipRelEntity> {
	
	@Autowired
	private EquipAbnormalEquipRelService equipAbnormalEquipRelService;
	
	@Override
	public IBaseEntityOperation<EquipAbnormalEquipRelEntity> getService() {
		return equipAbnormalEquipRelService;
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
		List<EquipAbnormalEquipRelEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipAbnormalEquipRelVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelCombobox", JsonUtil.toJson(comboEquipAbnormalEquipRelVO.getOptions()));
		return this.createModelAndView("equipAbnormalEquipRel/resource/views/equipAbnormalEquipRelList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<EquipAbnormalEquipRelEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipAbnormalEquipRelVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelCombobox", JsonUtil.toJson(comboEquipAbnormalEquipRelVO.getOptions()));
		return this.createModelAndView("equipAbnormalEquipRel/resource/views/equipAbnormalEquipRelAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		EquipAbnormalEquipRelEntity equipAbnormalEquipRelEntity = (EquipAbnormalEquipRelEntity)equipAbnormalEquipRelService.findById(id);
		model.put("entity", equipAbnormalEquipRelEntity);
		model.put("entityJson", JsonUtil.toJson(equipAbnormalEquipRelEntity));
		List<EquipAbnormalEquipRelEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboEquipAbnormalEquipRelVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("equipAbnormalEquipRelCombobox", JsonUtil.toJson(comboEquipAbnormalEquipRelVO.getOptions()));
		return this.createModelAndView("equipAbnormalEquipRel/resource/views/equipAbnormalEquipRelEdit", model);
	}
}