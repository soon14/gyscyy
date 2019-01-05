package com.aptech.business.train.trainManagement.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.service.DispatchManagementService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TrainPlanClassifyEnum;
import com.aptech.business.component.dictionary.TrainPlanRangeEnum;
import com.aptech.business.component.dictionary.TrainPlanStatusEnum;
import com.aptech.business.equip.equipAppraise.domain.EquipAppraiseEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.train.trainManagement.domain.TrainApprovalStatusEnum;
import com.aptech.business.train.trainManagement.domain.TrainManagementEntity;
import com.aptech.business.train.trainManagement.domain.TrainProcessEnum;
import com.aptech.business.train.trainManagement.service.TrainManagementService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;


/**
 * 培训管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/trainManagement")
public class TrainManagementController extends BaseController<TrainManagementEntity> {
	
	@Autowired
	private TrainManagementService trainManagementService;
	@Autowired
	private SysUnitService sysUnitService;
	
	/**
	 * 流程管理服务对象
	 */
	@Autowired
	private DefinitionService definitionService;
	/**
	 * 流程管理服务对象
	 */
	@Autowired
	private NodeConfigService nodeConfigService;
	
	@Autowired
	private DispatchManagementService dispatchManagementService;
	
	@Override
	public IBaseEntityOperation<TrainManagementEntity> getService() {
		return trainManagementService;
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
		return this.createModelAndView("train/trainManagement/list", model);
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
		//考核方式
		Map<String, SysDictionaryVO> assessmentMethodMap  =  DictionaryUtil.getDictionaries("ASSESSMENT_METHODS");
		ComboboxVO comboAssessmentMethodVO = new ComboboxVO();
		for(String key : assessmentMethodMap.keySet()){
			SysDictionaryVO sysDictionaryVO = assessmentMethodMap.get(key);
			comboAssessmentMethodVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("assessmentMethodCombobox", JsonUtil.toJson(comboAssessmentMethodVO.getOptions()));
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("sysUserEntity", sysUserEntity);
		String userUnitRels = "[]";
		model.put("userUnitRels", userUnitRels);
		return this.createModelAndView("train/trainManagement/add", model);
	}
	
	/**
	 *	跳转到修改页面
	 */
	@RequestMapping("/getEdit/{id}")
	public ModelAndView getEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TrainManagementEntity trainManagementEntity = (TrainManagementEntity)trainManagementService.findById(id);
		model.put("entity", trainManagementEntity);
		model.put("entityJson", JsonUtil.toJson(trainManagementEntity));
		
		// 部门下拉树
		List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
		model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));

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
		//考核方式
		Map<String, SysDictionaryVO> assessmentMethodMap  =  DictionaryUtil.getDictionaries("ASSESSMENT_METHODS");
		ComboboxVO comboAssessmentMethodVO = new ComboboxVO();
		for(String key : assessmentMethodMap.keySet()){
			SysDictionaryVO sysDictionaryVO = assessmentMethodMap.get(key);
			comboAssessmentMethodVO.addOption(sysDictionaryVO.getCode(), sysDictionaryVO.getName());
		}
		model.put("assessmentMethodCombobox", JsonUtil.toJson(comboAssessmentMethodVO.getOptions()));
		//获取关联设备
		List<EquipLedgerEntity> templist = new ArrayList<EquipLedgerEntity>();
		//检修计划名称
  		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_ID",FieldTypeEnum.INT, MatchTypeEnum.EQ,trainManagementEntity.getDispatchId()));
		List<DispatchManagementEntity> list = dispatchManagementService.findByCondition(conditions, null);
		model.put("userUnitRels", JsonUtil.toJson(list));
		return this.createModelAndView("train/trainManagement/edit", model);
	}

	/**
	* 添加保存
	 * @param trainManagementEntity
	 * @return
	 */
	@RequestMapping("/saveAddPage")
	public @ResponseBody ResultObj saveAddPage(@RequestBody TrainManagementEntity trainManagementEntity){
		ResultObj resultObj = new ResultObj();
		trainManagementService.saveAddPage(trainManagementEntity);
		return resultObj;
	}
	

	/**
	* 修改保存
	/**
	 * @param trainManagementEntity
	 * @return
	 */
	@RequestMapping("/saveEditPage")
	public @ResponseBody ResultObj saveEditPage(@RequestBody TrainManagementEntity trainManagementEntity){
		ResultObj resultObj = new ResultObj();
		trainManagementService.saveEditPage(trainManagementEntity);
		return resultObj;
	}
	

	/**
	* 跳转查看页
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/showPage/{id}")
	public ModelAndView showPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		// 返回前台数据项
		TrainManagementEntity trainManagementEntity = (TrainManagementEntity)trainManagementService.findById(id);
		model.put("entity", trainManagementEntity);
		model.put("entityJson", JsonUtil.toJson(trainManagementEntity));
		
//		//获取登录人
		return this.createModelAndView("train/trainManagement/show", model);
	}
	

	/**
	* 导出功能
	*/
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditions=req.getParameter("conditions");
		params.put("conditions", JSONArray.fromObject(conditions));
 		List<TrainManagementEntity> dataList=trainManagementService.findByCondition(params, null);
 		int i= 1;
 		for(TrainManagementEntity planEnity: dataList){
 			planEnity.setNumber(i++);
 		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		ExcelUtil.export(req, res, "培训管理报表模板.xlsx","培训管理.xlsx", resultMap);
	}
	
	/**
	 * 审查
	 * @param request
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping("/approve/{id}/{type}")
	public ModelAndView approve(HttpServletRequest request, @PathVariable Long id, @PathVariable String type){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("type", type);
		TrainManagementEntity entity = trainManagementService.findById(id);

		String approvalStatus = entity.getStatus();
		model.put("entity", entity);
		if(TrainApprovalStatusEnum.REVIEW.getCode().equals(approvalStatus)) {
			//同意
			return this.createModelAndView("train/trainManagement/review", model);
		} else if(TrainApprovalStatusEnum.RESUBMIT.getCode().equals(approvalStatus)) {
			//驳回
			model.put("entity", entity);
			model.put("entityJson", JsonUtil.toJson(entity));
			
			// 部门下拉树
			List<SysUnitEntity> treeNodeList = sysUnitService.getUnitTreeNodeList();
			model.put("unitNameIdTreeList", JsonUtil.toJson(treeNodeList));

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
			return this.createModelAndView("train/trainManagement/reSubmit", model);
		}
		return this.createModelAndView("train/trainManagement/review", model);
	}
	/**
	 * 数据列表中提交审批
	 * @param request
	 * @param id 数据ID
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/approvalSubmit")
	public @ResponseBody ResultObj approvalSubmit(@RequestBody TrainManagementEntity entity, HttpServletRequest request) {
		
		this.trainManagementService.submit(entity.getId().toString(), entity.getUserList());
		 
		return new ResultObj();
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		trainManagementService.delete(id);
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		for (String id : ids) {
			Long longId = new Long(id);
			TrainManagementEntity entity = trainManagementService.findById(longId);
			if (entity != null) {
				trainManagementService.delete(longId);
			}
		}
		return resultObj;
	}
	
	/**
	 * 领导审核：选择审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPerson/{taskId}")
	public ModelAndView submitPerson(HttpServletRequest request,@PathVariable String taskId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.TRAIN_MANAGEMENT_PROCESS_KEY.getName()));
		
		List<DefinitionEntity> defList= definitionService.findByCondition(conditions, null);
		
		if(!defList.isEmpty()){
			String modelId= defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来	
			SysUserEntity starter= null;
			if(!RequestContext.get().isDeveloperMode()){
				starter = RequestContext.get().getUser();
			}
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId, "", starter);
			
			resultMap.put("userList", userList);
		} 
	
		return new ModelAndView("/train/trainManagement/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 审查意见：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review")
	public @ResponseBody ResultObj review(@RequestBody TrainManagementEntity entity, HttpServletRequest request) {
		
		TrainManagementEntity reviewEntity = this.trainManagementService.findById(entity.getId());
		
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		reviewEntity.setReviewerID(sysUserEntity.getId().toString());
		reviewEntity.setReviewerName(sysUserEntity.getName());
		reviewEntity.setReviewTime(dfuYMd.format(cal.getTime()));
		reviewEntity.setStatus(entity.getStatus());
		reviewEntity.setReviewResult(entity.getReviewResult());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), entity.getUserList());
		
		if (TrainProcessEnum.AGREE.getCode().equals(entity.getReviewResult())) {
			//领导审核
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), TrainProcessEnum.AGREE.getCode());
			params.put("processStatus", TrainProcessEnum.AGREE.getCode());
			params.put("result", TrainProcessEnum.AGREE.getName());
			
		} else if (TrainProcessEnum.REJECT.getCode().equals(entity.getReviewResult())) {
			params.put(BranchMarkEnum.BRANCH_KEY.getName(),  TrainProcessEnum.REJECT.getCode());
			params.put("processStatus", TrainProcessEnum.REJECT.getCode());
			params.put("result", TrainProcessEnum.REJECT.getName());
			
		} 
		
		trainManagementService.approve(entity, params);
		
		trainManagementService.updateEntity(reviewEntity);
		
		return new ResultObj();
	}
	

	/**
	 * 跳转发文查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goSelectDispatchPage")
	public ModelAndView goSelectDispatchPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/train/trainManagement/selectDispatch",model);
	}
	/**
	 * 跳转发文查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotoSelectDispatchPage")
	public ModelAndView gotoSelectDispatchPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/train/trainManagement/selectDispatchOverhaul",model);
	}
	
	//把ID带回，排除以有的ID
	@RequestMapping("/selectDispatchData")
	public @ResponseBody ResultListObj g(HttpServletRequest request,@RequestBody  Map<String, Object> params){
			
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		page.setOrders(orders);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		String[] type = {"7","8"};
		if (conditions != null) {
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING,MatchTypeEnum.IN, type));
		} else {
			conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING,MatchTypeEnum.IN, type));
		}
			
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
	
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			resultObj.setRecordsTotal(dataList.size());
		}
		return resultObj;
	}
	//把ID带回，排除以有的ID
	@RequestMapping("/selectDispatchDataOverhaul")
	public @ResponseBody ResultListObj selectDispatchDataOverhaul(HttpServletRequest request,@RequestBody  Map<String, Object> params){
		
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		page.setOrders(orders);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "5"));
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
		
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			resultObj.setRecordsTotal(dataList.size());
		}
		return resultObj;
	}
}