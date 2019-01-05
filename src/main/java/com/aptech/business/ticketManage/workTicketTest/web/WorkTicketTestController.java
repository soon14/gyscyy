package com.aptech.business.ticketManage.workTicketTest.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.ticketManage.workTicketTest.domain.WorkTicketTestEntity;
import com.aptech.business.ticketManage.workTicketTest.service.WorkTicketTestService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 可燃易爆实验配置控制器
 *
 * @author 
 * @created 2017-08-22 10:11:16
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicketTest")
public class WorkTicketTestController extends BaseController<WorkTicketTestEntity> {
	
	@Autowired
	private WorkTicketTestService workTicketTestService;
	
	@Override
	public IBaseEntityOperation<WorkTicketTestEntity> getService() {
		return workTicketTestService;
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
		List<WorkTicketTestEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workTicketTestTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkTicketTestVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workTicketTestCombobox", JsonUtil.toJson(comboWorkTicketTestVO.getOptions()));
		return this.createModelAndView("ticketManage/workFire/workTicketTestList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
	       String electricId=request.getParameter("electricId");
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkTicketTestEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workTicketTestTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkTicketTestVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workTicketTestCombobox", JsonUtil.toJson(comboWorkTicketTestVO.getOptions()));
        model.put("electricId", electricId); 
		
		return this.createModelAndView("ticketManage/workFireTwo/workTicketTestAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkTicketTestEntity workTicketTestEntity = (WorkTicketTestEntity)workTicketTestService.findById(id);
		model.put("entity", workTicketTestEntity);
		model.put("entityJson", JsonUtil.toJson(workTicketTestEntity));
		
		List<WorkTicketTestEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workTicketTestTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkTicketTestVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workTicketTestCombobox", JsonUtil.toJson(comboWorkTicketTestVO.getOptions()));
		model.put("id", id);
		return this.createModelAndView("ticketManage/workFire/workTicketTestEdit", model);
	}
}