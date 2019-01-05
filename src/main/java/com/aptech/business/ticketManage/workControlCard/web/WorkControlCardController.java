package com.aptech.business.ticketManage.workControlCard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 工作票危险因素控制卡配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:09
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workControlCard")
public class WorkControlCardController extends BaseController<WorkControlCardEntity> {
	
	@Autowired
	private WorkControlCardService workControlCardService;
	
	@Override
	public IBaseEntityOperation<WorkControlCardEntity> getService() {
		return workControlCardService;
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
		List<WorkControlCardEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workControlCardTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkControlCardVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workControlCardCombobox", JsonUtil.toJson(comboWorkControlCardVO.getOptions()));
		return this.createModelAndView("ticketManage/workcontrolcard/workControlCardList", model);
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkControlCardEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workControlCardTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkControlCardVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workControlCardCombobox", JsonUtil.toJson(comboWorkControlCardVO.getOptions()));
		
		return this.createModelAndView("ticketManage/workcontrolcard/workControlCardAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkControlCardEntity workControlCardEntity=workControlCardService.findById(id);
		
		List<WorkControlCardEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workControlCardTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkControlCardVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workControlCardCombobox", JsonUtil.toJson(comboWorkControlCardVO.getOptions()));
		
		model.put("workControlCardEntity", workControlCardEntity);
		return this.createModelAndView("ticketManage/workcontrolcard/workControlCardEdit", model);
	}
}