package com.aptech.business.train.trainPlan.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.component.dictionary.TrainPlanClassifyEnum;
import com.aptech.business.component.dictionary.TrainPlanRangeEnum;
import com.aptech.business.component.dictionary.TrainPlanStatusEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.train.trainPlan.domain.TrainPlanEntity;
import com.aptech.business.train.trainPlan.service.TrainPlanService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 培训计划配置控制器
 *
 * @author ly
 * @created 2018-03-19 10:04:50
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/trainPlan")
public class TrainPlanController extends BaseController<TrainPlanEntity> {
	
	@Autowired
	private TrainPlanService trainPlanService;
	
	@Autowired
	private SysUnitService sysUnitService;
	
	@Override
	public IBaseEntityOperation<TrainPlanEntity> getService() {
		return trainPlanService;
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
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//培训状态
		ComboboxVO trainPlanStatusCombobox = new ComboboxVO();
		for(TrainPlanStatusEnum key : TrainPlanStatusEnum.values()){
			trainPlanStatusCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanStatusCombobox", JsonUtil.toJson(trainPlanStatusCombobox.getOptions()));
		//培训分类
		ComboboxVO trainPlanClassifyCombobox = new ComboboxVO();
		for(TrainPlanClassifyEnum key : TrainPlanClassifyEnum.values()){
			trainPlanClassifyCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanClassifyCombobox", JsonUtil.toJson(trainPlanClassifyCombobox.getOptions()));
		//培训范围
		ComboboxVO trainPlanRangeCombobox = new ComboboxVO();
		for(TrainPlanRangeEnum key : TrainPlanRangeEnum.values()){
			trainPlanRangeCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanRangeCombobox", JsonUtil.toJson(trainPlanRangeCombobox.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		return this.createModelAndView("train/trainPlan/trainPlanList", model);
	}
	
	/**
	 *	跳转到添加页面
	 */
	@RequestMapping("/getAdd")
	public ModelAndView getAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		
		//培训状态
//		ComboboxVO trainPlanStatusCombobox = new ComboboxVO();
//		for(TrainPlanStatusEnum key : TrainPlanStatusEnum.values()){
//			trainPlanStatusCombobox.addOption(key.getCode(), key.getName());
//		}
//		model.put("trainPlanStatusCombobox", JsonUtil.toJson(trainPlanStatusCombobox.getOptions()));
		//培训分类
		ComboboxVO trainPlanClassifyCombobox = new ComboboxVO();
		for(TrainPlanClassifyEnum key : TrainPlanClassifyEnum.values()){
			trainPlanClassifyCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanClassifyCombobox", JsonUtil.toJson(trainPlanClassifyCombobox.getOptions()));
		//培训范围
		ComboboxVO trainPlanRangeCombobox = new ComboboxVO();
		for(TrainPlanRangeEnum key : TrainPlanRangeEnum.values()){
			trainPlanRangeCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanRangeCombobox", JsonUtil.toJson(trainPlanRangeCombobox.getOptions()));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		
		return this.createModelAndView("train/trainPlan/trainPlanAdd", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TrainPlanEntity trainPlanEntity = (TrainPlanEntity)trainPlanService.findById(id);
		model.put("entity", trainPlanEntity);
		model.put("entityJson", JsonUtil.toJson(trainPlanEntity));
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));
		//培训状态
//		ComboboxVO trainPlanStatusCombobox = new ComboboxVO();
//		for(TrainPlanStatusEnum key : TrainPlanStatusEnum.values()){
//			trainPlanStatusCombobox.addOption(key.getCode(), key.getName());
//		}
//		model.put("trainPlanStatusCombobox", JsonUtil.toJson(trainPlanStatusCombobox.getOptions()));
		//培训分类
		ComboboxVO trainPlanClassifyCombobox = new ComboboxVO();
		for(TrainPlanClassifyEnum key : TrainPlanClassifyEnum.values()){
			trainPlanClassifyCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanClassifyCombobox", JsonUtil.toJson(trainPlanClassifyCombobox.getOptions()));
		//培训范围
		ComboboxVO trainPlanRangeCombobox = new ComboboxVO();
		for(TrainPlanRangeEnum key : TrainPlanRangeEnum.values()){
			trainPlanRangeCombobox.addOption(key.getCode(), key.getName());
		}
		model.put("trainPlanRangeCombobox", JsonUtil.toJson(trainPlanRangeCombobox.getOptions()));
		
		return this.createModelAndView("train/trainPlan/trainPlanEdit", model);
	}

	/**
	* 添加保存
	* @author ly
	* @date 2018年3月19日 下午5:04:26 
	* @param trainPlanEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody TrainPlanEntity trainPlanEntity){
		ResultObj resultObj = new ResultObj();
		trainPlanService.saveAddPage(trainPlanEntity);
		return resultObj;
	}
	

	/**
	* 修改保存
	* @author ly
	* @date 2018年3月20日 上午9:32:04 
	* @param trainPlanEntity
	* @return
	* @return ResultObj
	*/
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody TrainPlanEntity trainPlanEntity){
		ResultObj resultObj = new ResultObj();
		trainPlanService.saveEditPage(trainPlanEntity);
		return resultObj;
	}
	

	/**
	* 跳转查看页
	* @author ly
	* @date 2018年3月20日 上午9:38:49 
	* @param request
	* @param id
	* @return
	* @return ModelAndView
	*/
	@RequestMapping("/showPage/{id}")
	public ModelAndView showPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TrainPlanEntity trainPlanEntity = (TrainPlanEntity)trainPlanService.findById(id);
		model.put("entity", trainPlanEntity);
		model.put("entityJson", JsonUtil.toJson(trainPlanEntity));
		model.put("sysUnitEntity", sysUnitService.findById(Long.valueOf(trainPlanEntity.getUnitId())).getName());
//		//获取登录人
		//SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//model.put("sysUserEntity", JsonUtil.toJson(sysUserEntity));
		return this.createModelAndView("train/trainPlan/trainPlanShow", model);
	}
	

	/**
	* 导出功能
	* @author ly
	* @date 2018年3月19日 下午5:57:30 
	* @param req
	* @param res
	* @param params
	* @throws UnsupportedEncodingException
	* @return void
	*/
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
 		List<TrainPlanEntity> dataList=trainPlanService.findByCondition(params, null);
 		int i= 1;
 		for(TrainPlanEntity planEnity: dataList){
 			planEnity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "培训计划报表模板.xlsx","培训计划.xlsx", resultMap);
	}
}