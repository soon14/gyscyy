package com.aptech.business.quotaPlanHistory.web;

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

import com.aptech.business.quotaPlanHistory.domain.QuotaPlanHistoryEntity;
import com.aptech.business.quotaPlanHistory.service.QuotaPlanHistoryService;
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
 * 指标类计划历史数据配置控制器
 *
 * @author 
 * @created 2018-09-19 18:20:50
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/quotaPlanHistory")
public class QuotaPlanHistoryController extends BaseController<QuotaPlanHistoryEntity> {
	
	@Autowired
	private QuotaPlanHistoryService quotaPlanHistoryService;
	
	@Override
	public IBaseEntityOperation<QuotaPlanHistoryEntity> getService() {
		return quotaPlanHistoryService;
	}
	
	/**
	 *	跳转到历史数据查看页面
	 */
	@RequestMapping("/toHistoryMessage/{id}/{type}")
	public ModelAndView toHistoryMessage(HttpServletRequest request, @PathVariable String id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("apcId", id);
		model.put("type", type);
		//根据不同tab页显示不同的table名称
  		if("1".equals(type)){
  			model.put("tableName","集团年度生产量计划历史数据");
  		}else if("2".equals(type)){
  			model.put("tableName","内控年度生产量计划历史数据");
  		}else{
  			model.put("tableName","年度生产指标计划历史数据");
  		}
		return this.createModelAndView("annualProductionCapacity/quotaPlanHistoryList", model);
	}
}