package com.aptech.business.train.trainStatistics.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.train.trainStatistics.domain.TrainStatisticsEntity;
import com.aptech.business.train.trainStatistics.domain.TrainStatisticsVO;
import com.aptech.business.train.trainStatistics.service.TrainStatisticsService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;

/**
 * 
 * 培训统计配置控制器
 *
 * @author 
 * @created 2018-03-20 13:39:12
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/trainStatistics")
public class TrainStatisticsController extends BaseController<TrainStatisticsEntity> {
	
	@Autowired
	private TrainStatisticsService trainStatisticsService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<TrainStatisticsEntity> getService() {
		return trainStatisticsService;
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
		// 部门下拉树
		List<SysUnitEntity> unitTreeNodeList = sysUnitService.getUnitTreeNodeList();
		ComboboxVO unitComboBox = new ComboboxVO();
		for (SysUnitEntity entity : unitTreeNodeList) {
			unitComboBox.addOption(entity.getId().toString(), entity.getName());
		}
		model.put("unitNameIdTreeList", JsonUtil.toJson(unitComboBox.getOptions()));
		
		List<TrainStatisticsVO> treeNodeList = null; 
		//TODO下拉树具体内容根据具体业务定制
		model.put("trainStatisticsTreeList", JsonUtil.toJson(treeNodeList));
		ComboboxVO comboTrainStatisticsVO = new ComboboxVO();
		//TODO下拉框具体内容根据具体业务定制
		model.put("trainStatisticsCombobox", JsonUtil.toJson(comboTrainStatisticsVO.getOptions()));
		return this.createModelAndView("train/trainStatistics/trainStatisticsList", model);
	}
	
	
	/**
	 * @Description:   导出
	 * @author         changl 
	 * @throws ParseException 
	 * @Date           2017年6月28日 上午11:03:09 
	 * @throws         Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException, ParseException{
		String conditions=req.getParameter("conditions");
		params = (Map<String, Object>)JSONObject.fromObject(conditions);
		List<TrainStatisticsVO> entities = this.trainStatisticsService.findByCondition1(params);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		int i= 1;
 		for(TrainStatisticsVO trainStatisticsEnity: entities){
 			trainStatisticsEnity.setNumber(i++);
 		}
		resultMap.put("dataList", entities);
		ExcelUtil.export(req, res, "培训统计报表模板.xlsx","培训统计.xlsx", resultMap);
	}
	
	/**
	 * list数据查询
	 * 
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/seach", method = RequestMethod.POST)
	public @ResponseBody ResultListObj commonList(HttpServletRequest request,
			@RequestBody Map<String, Object> params) throws ParseException {
	//	AppListFormVO appListFormVO = listFormService.getAppListFormVOById(44);
		Page<Map<String, Object>> page = PageUtil.getPage(params);
		List<TrainStatisticsVO> entities = this.trainStatisticsService.findByCondition1(params);
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		if (page != null) {
			page.setOrders(orders);
		}
	//	List<Map<String, Object>> entities = listFormService.findListByCondition(appListFormVO, conditions, page);
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
}