package com.aptech.business.defectManage.defectEquipment.web;

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

import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
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
 * 缺陷设备关系表配置控制器
 *
 * @author 
 * @created 2018-06-15 15:44:31
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/defectEquipment")
public class DefectEquipmentController extends BaseController<DefectEquipmentEntity> {
	
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	
	@Override
	public IBaseEntityOperation<DefectEquipmentEntity> getService() {
		return defectEquipmentService;
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
		List<DefectEquipmentEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("defectEquipmentTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDefectEquipmentVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("defectEquipmentCombobox", JsonUtil.toJson(comboDefectEquipmentVO.getOptions()));
		return this.createModelAndView("defectEquipment/resource/views/defectEquipmentList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<DefectEquipmentEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("defectEquipmentTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDefectEquipmentVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("defectEquipmentCombobox", JsonUtil.toJson(comboDefectEquipmentVO.getOptions()));
		
		return this.createModelAndView("defectEquipment/resource/views/defectEquipmentAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		DefectEquipmentEntity defectEquipmentEntity = (DefectEquipmentEntity)defectEquipmentService.findById(id);
		model.put("entity", defectEquipmentEntity);
		model.put("entityJson", JsonUtil.toJson(defectEquipmentEntity));
		
		List<DefectEquipmentEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("defectEquipmentTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboDefectEquipmentVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("defectEquipmentCombobox", JsonUtil.toJson(comboDefectEquipmentVO.getOptions()));
		
		return this.createModelAndView("defectEquipment/resource/views/defectEquipmentEdit", model);
	}
}