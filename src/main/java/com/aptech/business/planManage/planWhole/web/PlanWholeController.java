package com.aptech.business.planManage.planWhole.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.business.planManage.planWhole.service.PlanWholeService;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 整体计划配置控制器
 *
 * @author 
 * @created 2017-11-13 15:10:22
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/planWhole")
public class PlanWholeController extends BaseController<PlanWholeEntity> {
	
	@Autowired
	private PlanWholeService planWholeService;
	
	@Override
	public IBaseEntityOperation<PlanWholeEntity> getService() {
		return planWholeService;
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
		return this.createModelAndView("planManage/planWhole/planWholeList", model);
	}
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/indexDetail")
	public ModelAndView listDetail(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView("planManage/planWhole/planWholeListDetail", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("planUuidWhole", IdUtil.creatUUID());
		model.put("planUuid", request.getParameter("planUuid"));
		model.put("planId", request.getParameter("planId"));
		model.put("num", request.getParameter("num"));
		return this.createModelAndView("planManage/planWhole/planWholeAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit")
	public ModelAndView getEditPage(HttpServletRequest request, String id){
		Map<String, Object> model = new HashMap<String, Object>();
		PlanWholeEntity planWholeEntity=planWholeService.findById(Long.parseLong(id));
		model.put("planWholeEntity", planWholeEntity);
		return this.createModelAndView("planManage/planWhole/planWholeEdit", model);
	}
	/**
	 *	跳转到查看页面
	 */
	@RequestMapping("/getDetail/{id}")
	public ModelAndView getDeatil(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		PlanWholeEntity planWholeEntity=planWholeService.findById(id);
		model.put("planWholeEntity", planWholeEntity);
		model.put("planId", planWholeEntity.getPlanId());
		return this.createModelAndView("planManage/planWhole/planWholeDetail", model);
	}
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/add" )
	public @ResponseBody ResultObj add(@RequestBody PlanWholeEntity planWholeEntity, HttpServletRequest request) {
		return planWholeService.add(planWholeEntity);
	}
}