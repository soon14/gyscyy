package com.aptech.business.operation.userOperateLog.web;

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

import com.aptech.business.component.dictionary.InvestmentPlanCategoryTypeEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateLogEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.userOperateLog.domain.UserOperateLogEntity;
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
 * 用户操作配置控制器
 *
 * @author 
 * @created 2018-04-09 10:36:54
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/operateLog")
public class OperateLogController extends BaseController<OperateLogEntity> {
	
	@Autowired
	private OperateLogService userOperateLogService;
	@Autowired
	private SysUserService sysUserService;
	@Override
	public IBaseEntityOperation<OperateLogEntity> getService() {
		return userOperateLogService;
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
		List<UserOperateLogEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("userOperateLogTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUserOperateLogVO = new ComboboxVO();
		// 人员
		ComboboxVO searchuser = new ComboboxVO();
		List<Condition> userCondition = new ArrayList<Condition>();
		SysUserEntity userEntity= RequestContext.get().getUser();
 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
 		List<SysUserEntity> userList = sysUserService.findByCondition(userCondition, null);
		for (SysUserEntity user : userList) {
			searchuser.addOption(user.getId().toString(), user.getName());
		}
		model.put("searchuser", JsonUtil.toJson(searchuser.getOptions()));
		//操作模块
		ComboboxVO operateUserEnumCombobox = new ComboboxVO();
		for(OperateUserModuleEnum key : OperateUserModuleEnum.values()){
			operateUserEnumCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("operateUserEnumCombobox", JsonUtil.toJson(operateUserEnumCombobox.getOptions()));
		//操作类型
		ComboboxVO operateTypeCombobox = new ComboboxVO();
		for(OperateUserEnum key : OperateUserEnum.values()){
			operateTypeCombobox.addOption(key.getId().toString(), key.getName());
		}
		model.put("operateTypeCombobox", JsonUtil.toJson(operateTypeCombobox.getOptions()));
		//TODO下拉框具体内容根据具体业务定制
		model.put("userOperateLogCombobox", JsonUtil.toJson(comboUserOperateLogVO.getOptions()));
		return this.createModelAndView("userOperateLog/resource/views/userOperateLogList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<UserOperateLogEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("userOperateLogTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUserOperateLogVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("userOperateLogCombobox", JsonUtil.toJson(comboUserOperateLogVO.getOptions()));
		
		return this.createModelAndView("userOperateLog/resource/views/userOperateLogAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		OperateLogEntity userOperateLogEntity = (OperateLogEntity)userOperateLogService.findById(id);
		model.put("entity", userOperateLogEntity);
		model.put("entityJson", JsonUtil.toJson(userOperateLogEntity));
		
		List<UserOperateLogEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("userOperateLogTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboUserOperateLogVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("userOperateLogCombobox", JsonUtil.toJson(comboUserOperateLogVO.getOptions()));
		
		return this.createModelAndView("userOperateLog/resource/views/userOperateLogEdit", model);
	}
}