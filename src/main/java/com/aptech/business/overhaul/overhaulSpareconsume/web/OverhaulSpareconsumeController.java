package com.aptech.business.overhaul.overhaulSpareconsume.web;

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

import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.service.OverhaulSpareconsumeService;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 备件消耗配置控制器
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulSpareconsume")
public class OverhaulSpareconsumeController extends BaseController<OverhaulSpareconsumeEntity> {
	
	@Autowired
	private OverhaulSpareconsumeService overhaulSpareconsumeService;
	
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	
	@Override
	public IBaseEntityOperation<OverhaulSpareconsumeEntity> getService() {
		return overhaulSpareconsumeService;
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
		List<OverhaulSpareconsumeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulSpareconsumeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulSpareconsumeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulSpareconsumeCombobox", JsonUtil.toJson(comboOverhaulSpareconsumeVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulSpareconsume/overhaulSpareconsumeList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<OverhaulSpareconsumeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulSpareconsumeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulSpareconsumeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulSpareconsumeCombobox", JsonUtil.toJson(comboOverhaulSpareconsumeVO.getOptions()));
		
		return this.createModelAndView("overhaul/overhaulSpareconsume/overhaulSpareconsumeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OverhaulSpareconsumeEntity overhaulSpareconsumeEntity = (OverhaulSpareconsumeEntity)overhaulSpareconsumeService.findById(id);
		model.put("entity", overhaulSpareconsumeEntity);
		model.put("entityJson", JsonUtil.toJson(overhaulSpareconsumeEntity));
		
		List<OverhaulSpareconsumeEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulSpareconsumeTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulSpareconsumeVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulSpareconsumeCombobox", JsonUtil.toJson(comboOverhaulSpareconsumeVO.getOptions()));
		
		return this.createModelAndView("overhaul/overhaulSpareconsume/overhaulSpareconsumeEdit", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/delete/{id}")
	public @ResponseBody <T> ResultObj delete(HttpServletRequest request, @PathVariable Long id){
		return overhaulSpareconsumeService.delete(request, id);
	}
	
	
	/**
	 *	添加设备相关数据
	 */
	@RequestMapping("/addMaterialInfo/{overhaulArrangeId}")
	public @ResponseBody <T> ResultObj addMaterialInfo(HttpServletRequest request,@RequestBody Map<String, Object> params, @PathVariable Long overhaulArrangeId){

		return overhaulSpareconsumeService.addEquipInfo(request, params,overhaulArrangeId);
	}
   
}