package com.aptech.business.filelearnReceiveUser.web;

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

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.business.filelearnReceiveUser.service.FilelearnReceiveUserService;
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
 * 文件学习接受人配置控制器
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/filelearnReceiveUser")
public class FilelearnReceiveUserController extends BaseController<FilelearnReceiveUserEntity> {
	
	@Autowired
	private FilelearnReceiveUserService filelearnReceiveUserService;
	
	@Override
	public IBaseEntityOperation<FilelearnReceiveUserEntity> getService() {
		return filelearnReceiveUserService;
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
		List<FilelearnReceiveUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("filelearnReceiveUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboFilelearnReceiveUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("filelearnReceiveUserCombobox", JsonUtil.toJson(comboFilelearnReceiveUserVO.getOptions()));
		return this.createModelAndView("filelearnReceiveUser/resource/views/filelearnReceiveUserList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<FilelearnReceiveUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("filelearnReceiveUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboFilelearnReceiveUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("filelearnReceiveUserCombobox", JsonUtil.toJson(comboFilelearnReceiveUserVO.getOptions()));
		
		return this.createModelAndView("filelearnReceiveUser/resource/views/filelearnReceiveUserAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		FilelearnReceiveUserEntity filelearnReceiveUserEntity = (FilelearnReceiveUserEntity)filelearnReceiveUserService.findById(id);
		model.put("entity", filelearnReceiveUserEntity);
		model.put("entityJson", JsonUtil.toJson(filelearnReceiveUserEntity));
		
		List<FilelearnReceiveUserEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("filelearnReceiveUserTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboFilelearnReceiveUserVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("filelearnReceiveUserCombobox", JsonUtil.toJson(comboFilelearnReceiveUserVO.getOptions()));
		
		return this.createModelAndView("filelearnReceiveUser/resource/views/filelearnReceiveUserEdit", model);
	}
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<FilelearnReceiveUserEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("isLearnDic".equals(sort.getField())) {
				sort.setField("isLearn");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);

		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<FilelearnReceiveUserEntity> dataList = filelearnReceiveUserService.findByCondition(conditions, page);
	
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal(dataList.size());
			}
		}
		return resultObj;
	}
}