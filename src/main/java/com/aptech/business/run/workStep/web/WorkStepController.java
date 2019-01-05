package com.aptech.business.run.workStep.web;

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

import com.aptech.business.run.workStep.domain.WorkStepEntity;
import com.aptech.business.run.workStep.service.WorkStepService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.template.listForm.service.ListFormService;
import com.aptech.common.template.listForm.vo.AppListFormVO;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 工作步骤配置控制器
 *
 * @author 
 * @created 2017-06-02 11:28:16
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workStep")
public class WorkStepController extends BaseController<WorkStepEntity> {
	
	@Autowired
	private WorkStepService workStepService;
	@Autowired
    private ListFormService listFormService;
	@Override
	public IBaseEntityOperation<WorkStepEntity> getService() {
		return workStepService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{id}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkStepEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workStepTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkStepVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workStepCombobox", JsonUtil.toJson(comboWorkStepVO.getOptions()));
        model.put("wrId", id);		
		return this.createModelAndView("run/workRecord/workStepList", model);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/seach", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params, @PathVariable Long appId) {
		AppListFormVO appListFormVO = listFormService.getAppListFormVOById(appId);
		Page<Map<String, Object>> page = PageUtil.getPage(params);
		List<Condition> conditions = new ArrayList<Condition>();
		if (params.get("conditions") != null) {
			conditions = OrmUtil.changeMapToCondition(params);
			if(!RequestContext.get().isDeveloperMode()){
				SysUserEntity sysUserEntity = (SysUserEntity)RequestContext.get().getUser();
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(sysUserEntity.getUnitId());
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(sysUserEntity.getId());
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(sysUserEntity.getRoleIds());
						}
					}
				}
			}else{
				for(Condition condition: conditions){
					if(condition.getValue().toString().contains("${")){
						String flag = condition.getValue().toString();
						if(flag.equals("${currentUnitId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentUserId}")){
							condition.setValue(0);
						}else if(flag.equals("${currentRoleId}")){
							condition.setValue(0);
						}
					}
				}
			}
		}
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		if (page != null) {
			page.setOrders(orders);
		}
		List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) params.get("draw"));
		if (entities != null) {
			resultObj.setData(entities);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entities.size());
			}
		}
		return resultObj;
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{wrId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long wrId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<WorkStepEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workStepTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkStepVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workStepCombobox", JsonUtil.toJson(comboWorkStepVO.getOptions()));
        model.put("wpId", wrId);		
		return this.createModelAndView("run/workRecord/workStepAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		WorkStepEntity workStepEntity = (WorkStepEntity)workStepService.findById(id);
		model.put("dataMap", workStepEntity);
		model.put("dataMapJson", JsonUtil.toJson(workStepEntity));
		
		List<WorkStepEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("workStepTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboWorkStepVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("workStepCombobox", JsonUtil.toJson(comboWorkStepVO.getOptions()));
		
		return this.createModelAndView("run/workRecord/workStepEdit", model);
	}
}