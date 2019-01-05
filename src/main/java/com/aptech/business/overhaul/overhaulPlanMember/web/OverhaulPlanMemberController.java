package com.aptech.business.overhaul.overhaulPlanMember.web;

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

import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulPlanMember.service.OverhaulPlanMemberService;
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
 * 检修计划人员配置控制器
 *
 * @author 
 * @created 2018-03-22 10:43:15
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulPlanMember")
public class OverhaulPlanMemberController extends BaseController<OverhaulPlanMemberEntity> {
	
	@Autowired
	private OverhaulPlanMemberService overhaulPlanMemberService;
	
	@Override
	public IBaseEntityOperation<OverhaulPlanMemberEntity> getService() {
		return overhaulPlanMemberService;
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
		List<OverhaulPlanMemberEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulPlanMemberTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulPlanMemberVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulPlanMemberCombobox", JsonUtil.toJson(comboOverhaulPlanMemberVO.getOptions()));
		return this.createModelAndView("overhaul/overhaulPlanMember/overhaulPlanMemberList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<OverhaulPlanMemberEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulPlanMemberTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulPlanMemberVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulPlanMemberCombobox", JsonUtil.toJson(comboOverhaulPlanMemberVO.getOptions()));
		
		return this.createModelAndView("overhaul/overhaulPlanMember/overhaulPlanMemberAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OverhaulPlanMemberEntity overhaulPlanMemberEntity = (OverhaulPlanMemberEntity)overhaulPlanMemberService.findById(id);
		model.put("entity", overhaulPlanMemberEntity);
		model.put("entityJson", JsonUtil.toJson(overhaulPlanMemberEntity));
		
		List<OverhaulPlanMemberEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("overhaulPlanMemberTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboOverhaulPlanMemberVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("overhaulPlanMemberCombobox", JsonUtil.toJson(comboOverhaulPlanMemberVO.getOptions()));
		
		return this.createModelAndView("overhaul/overhaulPlanMember/overhaulPlanMemberEdit", model);
	}
}