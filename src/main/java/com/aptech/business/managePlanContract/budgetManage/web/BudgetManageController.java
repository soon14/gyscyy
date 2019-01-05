package com.aptech.business.managePlanContract.budgetManage.web;

import java.util.ArrayList;
import java.util.Date;
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

import com.aptech.business.managePlanContract.budgetManage.domain.BudgetManageEntity;
import com.aptech.business.managePlanContract.budgetManage.service.BudgetManageService;
import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.business.managePlanContract.purchaseOrganization.service.PurchaseOrganizationService;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.ticketManage.ticketStatistics.domain.TicketStatisticsVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 预算管理配置控制器
 *
 * @author 
 * @created 2018-07-25 13:17:36
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/budgetManage")
public class BudgetManageController extends BaseController<BudgetManageEntity> {
	
	@Autowired
	private BudgetManageService budgetManageService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private PurchaseOrganizationService purchaseOrganizationService;
	
	@Override
	public IBaseEntityOperation<BudgetManageEntity> getService() {
		return budgetManageService;
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index/{id}")
	public ModelAndView list(HttpServletRequest request, Map<String, Object> params, @PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<BudgetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("budgetManageTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboBudgetManageVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("budgetManageCombobox", JsonUtil.toJson(comboBudgetManageVO.getOptions()));
		
		 //获取日期
  		DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
        String dateStr = df.format(new Date());
        model.put("giveDate", dateStr);
        
        SysUnitEntity sysUnitEntity = sysUnitService.findById(id);
        model.put("organizationEntity", sysUnitEntity);
        model.put("unitId", id);
        
		return this.createModelAndView("managePlanContract/budgetManage/budgetManageList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd/{unitId}")
	public ModelAndView getAddPage(HttpServletRequest request, @PathVariable Long unitId){
		Map<String, Object> model = new HashMap<String, Object>();
		List<BudgetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("budgetManageTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboBudgetManageVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("budgetManageCombobox", JsonUtil.toJson(comboBudgetManageVO.getOptions()));
        model.put("unitId", unitId);
        
		return this.createModelAndView("managePlanContract/budgetManage/budgetManageAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}/{unitId}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id,@PathVariable Long unitId){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		BudgetManageEntity budgetManageEntity = (BudgetManageEntity)budgetManageService.findById(id);
		model.put("entity", budgetManageEntity);
		model.put("entityJson", JsonUtil.toJson(budgetManageEntity));
		
		List<BudgetManageEntity> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("budgetManageTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboBudgetManageVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("budgetManageCombobox", JsonUtil.toJson(comboBudgetManageVO.getOptions()));
		
		 //获取日期
  		DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
        String dateStr = df.format(new Date());
        model.put("giveDate", dateStr);
        model.put("unitId", unitId);
        
		return this.createModelAndView("managePlanContract/budgetManage/budgetManageEdit", model);
	}
	
	/**
	 * @Description:   修改保存
	 * @author        
	 * @Date          
	 * @throws        
	 */
	@RequestMapping(value = "/saveEdit/{id}")
	public @ResponseBody ResultObj saveEdit(@RequestBody BudgetManageEntity t, HttpServletRequest request) {
		ResultObj resultObj = new ResultObj();
		budgetManageService.updateEntity(t);
	    return resultObj;
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> data) {
		
		Map<String, Object> param =  (Map<String, Object>)data.get("param");
		Page<TicketStatisticsVO> page = PageUtil.getPage(data);
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer) data.get("draw"));
		//统计时间
		String searchYear = (String)param.get("year");

		List<Sort> orders = OrmUtil.changeMapToOrders(param);

		if (page != null) {
		  page.setOrders(orders);
		}
			List<BudgetManageEntity>  entityList = budgetManageService.getDataList(searchYear);

		if (entityList != null) {
			resultObj.setData(entityList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) entityList.size());
			}
		}
		return resultObj;
	}
}