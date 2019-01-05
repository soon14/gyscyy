package com.aptech.business.ticketManage.workSituation.web;

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

import com.aptech.business.ticketManage.workSituation.domain.WorkSituationEntity;
import com.aptech.business.ticketManage.workSituation.service.WorkSituationService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 开工和收工情况配置控制器
 *
 * @author 
 * @created 2017-06-05 17:12:39
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workSituation")
public class WorkSituationController extends BaseController<WorkSituationEntity> {
	
	@Autowired
	private WorkSituationService workSituationService;
	
	@Override
	public IBaseEntityOperation<WorkSituationEntity> getService() {
		return workSituationService;
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
		List<WorkSituationEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workSituationTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkSituationVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workSituationCombobox", JsonUtil.toJson(comboWorkSituationVO.getOptions()));
		return this.createModelAndView("ticketManage/workSituation/resource/views/workSituationList", model);
	}
	
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		String workId=request.getParameter("workId");
		model.put("workId", workId);
		return this.createModelAndView("ticketManage/workSituation/workSituationAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		WorkSituationEntity   entity=workSituationService.findById(id);
		model.put("workSituationEntity", entity);
		return this.createModelAndView("ticketManage/workSituation/workSituationEdit", model);
	}
	/**
	 * @Description:  自己写的修改方法 
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午4:50:53 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/update/{id}")
	public @ResponseBody ResultObj update(@RequestBody WorkSituationEntity t, HttpServletRequest request ,@PathVariable Long id) {
		return workSituationService.update(t,id);
	}
}