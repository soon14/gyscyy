package com.aptech.business.ticketManage.operationDanger.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.ticketManage.operationDanger.domain.OperationDangerEntity;
import com.aptech.business.ticketManage.operationDanger.service.OperationDangerService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.JsonUtil;

/**
 * 
 * 操作票危险因素情况表配置控制器
 * 
 * @author
 * @created 2017-07-12 17:27:40
 * @lastModified
 * @history
 * 
 */
@Controller
@RequestMapping("/operationDanger")
public class OperationDangerController extends
		BaseController<OperationDangerEntity> {

	@Autowired
	private OperationDangerService operationDangerService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<OperationDangerEntity> getService() {
		return operationDangerService;
	}

	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		SysUserEntity userEntity= RequestContext.get().getUser();
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView(
				"ticketManage/operationDanger/operationDangerList", model);
	}
	/**
	 * list页面跳转
	 * 
	 * @Title:
	 * @Description:
	 * @param
	 * @return
	 */
	@RequestMapping("/indexDetail")
	public ModelAndView listDetail(HttpServletRequest request,
			Map<String, Object> params) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		SysUserEntity userEntity= RequestContext.get().getUser();

		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> userList=sysUserService.findByCondition(conditions, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		return this.createModelAndView(
				"ticketManage/operationDanger/operationDangerListDetail", model);
	}
	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return this.createModelAndView(
				"ticketManage/operationDanger/operationDangerAdd", model);
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request,
			@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		OperationDangerEntity t= operationDangerService.findById(id);
		model.put("t", t);
		return this.createModelAndView(
				"ticketManage/operationDanger/operationDangerEdit", model);
	}
}