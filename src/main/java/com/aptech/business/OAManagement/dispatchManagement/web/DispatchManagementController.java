package com.aptech.business.OAManagement.dispatchManagement.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.OAManagement.dispatchManagement.domain.ApprovalResultEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.DenseEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchApprovalStatusEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchProcessEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.ReviewTypeEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.TreeEntity;
import com.aptech.business.OAManagement.dispatchManagement.domain.UrgencyEnum;
import com.aptech.business.OAManagement.dispatchManagement.service.DispatchManagementService;
import com.aptech.business.OAManagement.dispatchManagement.util.SortUtil;
import com.aptech.business.OAManagement.feedBack.domain.DispatchFeedbackEntity;
import com.aptech.business.OAManagement.feedBack.service.DispatchFeedbackService;
import com.aptech.business.OAManagement.jointlySign.domain.DispatchJointlySignEntity;
import com.aptech.business.OAManagement.jointlySign.service.DispatchJointlySignService;
import com.aptech.business.OAManagement.leaderApproval.domain.DispatchLeaderApprovalEntity;
import com.aptech.business.OAManagement.leaderApproval.service.DispatchLeaderApprovalService;
import com.aptech.business.OAManagement.review.domain.DispatchReviewEntity;
import com.aptech.business.OAManagement.review.service.DispatchReviewService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.web.ComboboxVO;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.common.workflow.definition.domain.DefinitionEntity;
import com.aptech.common.workflow.definition.service.DefinitionService;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.BaseController;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;
 
@Controller
@RequestMapping("/dispatchManagement") 
public class DispatchManagementController extends BaseController<DispatchManagementEntity> {
    @Autowired
	private DispatchManagementService dispatchManagementService;

	@Override
	public IBaseEntityOperation<DispatchManagementEntity> getService() {
		return dispatchManagementService;
	}
	
	@Autowired
	private SysUnitService sysUnitService;
	
	/**
	 * 反馈服务对象
	 */
	@Autowired
	private DispatchFeedbackService dispatchFeedbackService;
	
	/**
	 * 会签服务对象
	 */
	@Autowired
	private DispatchJointlySignService dispatchJointlySignService;
	
	/**
	 * 领导审查服务对象
	 */
	@Autowired
	private DispatchLeaderApprovalService dispatchLeaderApprovalService;
	
	/**
	 * 审查服务对象
	 */
	@Autowired
	private DispatchReviewService dispatchReviewService;
	/**
	 * user服务对象
	 */
	@Autowired
	private SysUserService sysUserService;
	
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
	
	/**
	 * 消息服务对象
	 */
	@Autowired
	private MessageCenterService messageCenterService;
	
	@Autowired
	private SysDutiesService sysDutiesService;
	
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView list(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//拟稿人
		ComboboxVO drafterCombobox = new ComboboxVO();
		List<SysUserEntity> userList = dispatchManagementService.getDrafterList();
		for(SysUserEntity user : userList){
			drafterCombobox.addOption(user.getId().toString(), user.getName());
		}
		model.put("drafterCombobox", JsonUtil.toJson(drafterCombobox.getOptions()));
		
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd");
		//开始时间
		Calendar startCal = Calendar.getInstance();
		model.put("startTime", dfuYMd.format(startCal.getTime()));
		
		//结束时间
		Calendar endCal = Calendar.getInstance();
		model.put("endTime", dfuYMd.format(endCal.getTime()));
		
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {1,3,4,5,6,10,11};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);
		
		//部门列表
		ComboboxVO department = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				department.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("tabValue", JsonUtil.toJson(department.getOptions()));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginUser", sysUserEntity);
		
		return this.createModelAndView("/OAManagement/dispatchManagement/list", model);
	}
	
	/**
	 *	list页面跳转
	 * @Title: 
	 * @Description:
	 * @param 
	 * @return
	 */
	@RequestMapping("/releaseIndex")
	public ModelAndView releaseList(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		
		//拟稿人
		ComboboxVO drafterCombobox = new ComboboxVO();
		List<SysUserEntity> userList = dispatchManagementService.getDrafterList();
		for(SysUserEntity user : userList){
			drafterCombobox.addOption(user.getId().toString(), user.getName());
		}
		model.put("drafterCombobox", JsonUtil.toJson(drafterCombobox.getOptions()));
		
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd");
		//开始时间
		Calendar startCal = Calendar.getInstance();
		model.put("startTime", dfuYMd.format(startCal.getTime()));
		
		//结束时间
		Calendar endCal = Calendar.getInstance();
		model.put("endTime", dfuYMd.format(endCal.getTime()));
		
		//部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {1,3,4,5,6,10,11};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);
		
		//部门列表
		ComboboxVO department = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				department.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("tabValue", JsonUtil.toJson(department.getOptions()));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginUser", sysUserEntity);
		
		return this.createModelAndView("/OAManagement/dispatchManagement/releaseList", model);
	}
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("emergencyLevelCN".equals(sort.getField())) {
				sort.setField("emergencyLevel");
			}
			if ("approvalStatusCN".equals(sort.getField())) {
				sort.setField("approvalStatus");
			}
			if ("releaseTimeStr".equals(sort.getField())) {
				sort.setField("releaseTime");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);
		if (page != null) {
			page.addOrder(Sort.desc("C_ID"));
		}
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<Condition> userConditions = new ArrayList<Condition>();
		userConditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "85"));
		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(userConditions, null);
		List<String> userUnitIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
			userUnitIds.add(sysDutiesDetailEntity.getUserUnitRelId());
		}
		userConditions.clear();
		userConditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitIds.toArray()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
		List<String> userIds = new ArrayList<String>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			userIds.add(userUnitRelEntity.getUserId().toString());
		}
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userId = sysUserEntity.getId().toString();
		List<DispatchManagementEntity> dataList = new ArrayList<DispatchManagementEntity>();
		if(userIds.contains(userId)){
			dataList = dispatchManagementService.findByCondition(conditions, page);
		}else{
			conditions.add(new Condition("C_DRAFTER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userId));
			dataList = dispatchManagementService.findByCondition(conditions, page);
		}
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
	
	/**
	 * 全部挂网条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchReleaseData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReleaseData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("emergencyLevelCN".equals(sort.getField())) {
				sort.setField("emergencyLevel");
			}
			if ("approvalStatusCN".equals(sort.getField())) {
				sort.setField("approvalStatus");
			}
			if ("releaseTimeStr".equals(sort.getField())) {
				sort.setField("releaseTime");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);
		if (page != null) {
//			page.setPageSize(Integer.MAX_VALUE);
			page.addOrder(Sort.desc("C_ID"));
		}
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
		
		//取出登录用户反馈过的发文
		conditions.clear();
		conditions.add(new Condition("C_FEEDBACK_PERSION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId().toString()));
		List<DispatchFeedbackEntity> feedbackDataList = dispatchFeedbackService.findByCondition(conditions, null);
		
		List<DispatchManagementEntity> returnDataList = new ArrayList<DispatchManagementEntity>();
		if (dataList != null) {
			for (DispatchManagementEntity entity :dataList) {
				if (feedbackDataList != null && feedbackDataList.size() > 0) {
					for (DispatchFeedbackEntity feedbackEntity : feedbackDataList) {
						if (entity.getId().intValue() == feedbackEntity.getDispatchId().intValue()) {
							entity.setReadFlag("1");
							break;
						}
					}
				}
				returnDataList.add(entity);
			}
			
		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (returnDataList != null) {
			resultObj.setData(returnDataList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal(returnDataList.size());
			}
		}
		return resultObj;
	}
	
	/*
	 *	跳转到添加页面
	 */
	@RequestMapping("/gotoAddPage")
	public ModelAndView gotoAddPage(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//拟稿人
		model.put("drafterName", sysUserEntity.getName());
		model.put("drafterId", sysUserEntity.getId().toString());
		
		DateFormatUtil dfuYMdHm = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		//拟稿时间
		model.put("drafterTime", dfuYMdHm.format(cal.getTime()));
		
		/*
		 * 发文字号
		 */
		//文号前缀
		ComboboxVO dispatchNumPreCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> dispatchNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
		for(String key : dispatchNumPreMap.keySet()){
			SysDictionaryVO dispatchNumPreVO = dispatchNumPreMap.get(key);
			dispatchNumPreCombo.addOption(dispatchNumPreVO.getCode(), dispatchNumPreVO.getName());
		}
		model.put("dispatchNumPreComboBox", JsonUtil.toJson(dispatchNumPreCombo.getOptions()));
		//发文文号时间序号信息
		List<Map<String,String>> dispatchNumberInfoList = this.dispatchManagementService.getDispatchNumberInfo();
		//文号 时间
		ComboboxVO dispatchNumYearComboBox = new ComboboxVO();
		int startValue = 1990;
		if (dispatchNumberInfoList != null && dispatchNumberInfoList.size() > 0) {
			Map<String,String> dataMap =  dispatchNumberInfoList.get(0);
			if (dataMap != null && dataMap.get("C_DISPATCH_NUM_START_TIME") != null) {
				try {
					startValue = Integer.parseInt(dataMap.get("C_DISPATCH_NUM_START_TIME").toString());
				} catch (NumberFormatException e) {
					startValue = 1990;
				}
			}
		}
		for (int i=0; i<100; i++ ) {
			int optionVal =  startValue + i;
			dispatchNumYearComboBox.addOption("" + optionVal , "" + optionVal);
		}
		
		model.put("dispatchNumYearComboBox", JsonUtil.toJson(dispatchNumYearComboBox.getOptions()));
		//文号序号
		String dispatchNumNo = "0000";
		if (dispatchNumberInfoList != null && dispatchNumberInfoList.size() > 0) {
			for (Map<String,String> dataMap : dispatchNumberInfoList) {
				if (dataMap.get("C_DISPATCH_NUMBER") != null) {
					dispatchNumNo = dataMap.get("C_DISPATCH_NUMBER").toString();
				}
			}
		}
		model.put("dispatchNumNo", dispatchNumNo);
		
		//发文类型 
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		ComboboxVO dispatchTypeComboBox = new ComboboxVO();
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			dispatchTypeComboBox.addOption(dispatchTypeVO.getCode(), dispatchTypeVO.getName());
		}
		model.put("dispatchTypeComboBox",  JsonUtil.toJson(dispatchTypeComboBox.getOptions()));

		//发文部门
		int [] organizationArray = {1,3,4,5,6,10,11};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);
		
		
		//部门列表
		ComboboxVO departmentComboBox = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				departmentComboBox.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("departmentComboBox", JsonUtil.toJson(departmentComboBox.getOptions()));
		//缓急程度
		ComboboxVO urgencyComboBox = new ComboboxVO();
		for (UrgencyEnum urgencyEnum : UrgencyEnum.values()) {
			urgencyComboBox.addOption(urgencyEnum.getCode(),  urgencyEnum.getName());
		}
		model.put("urgencyComboBox", JsonUtil.toJson(urgencyComboBox.getOptions()));
		//密级
		ComboboxVO denseComboBox = new ComboboxVO();
		for (DenseEnum denseEnum : DenseEnum.values()) {
			denseComboBox.addOption(denseEnum.getCode(),  denseEnum.getName());
		}
		model.put("denseComboBox", JsonUtil.toJson(denseComboBox.getOptions()));
		
		//反馈提醒时间
		model.put("remindTime", dfuYMd.format(cal.getTime()));
		
		//接收人
		List<TreeEntity> recipientTreeList = dispatchManagementService.getRecipientTree();
		model.put("recipientTree",JsonUtil.toJson(recipientTreeList));

		return this.createModelAndView("/OAManagement/dispatchManagement/add", model);
	}
	
	/**
	 * 添加
	 * 
	 * @Title: add
	 * @Description:
	 * @param T
	 * @return
	 */
	@RequestMapping(value = "/addEntity", method = RequestMethod.POST)
	public @ResponseBody ResultObj addEntity(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());
		entity.setDispatchName(entity.getDispatchNameSymbolName() + entity.getDispatchNameYear() + entity.getDispatchNameNum());
		this.dispatchManagementService.addEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
		ResultObj resultObj = new ResultObj();
		resultObj.setData(entity);
		return resultObj;
	}

	
	/**
	 *	跳转到修改页面
	 * @param request
	 * @param id 修改对象id
	 * @return
	 */
	@RequestMapping("/gotoEditPage/{id}")
	public ModelAndView gotoEditPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		
		DispatchManagementEntity entity = dispatchManagementService.findById(id);

		//拟稿人
		model.put("drafterName", entity.getDrafterName());

		//拟稿时间
		model.put("drafterTime", entity.getDraftTime());

		/*
		 * 发文字号
		 */
		//发文字号文号
		String dispatchNumPreValue = entity.getDispatchNameSymbolId();
		model.put("dispatchNumPreVal", dispatchNumPreValue);
		//发文文号年号
		String dispatchNumYear = entity.getDispatchNameYear();
		model.put("dispatchNumYearVal", dispatchNumYear);
		//发文文号序号
		String dispatchNumNo = entity.getDispatchNameNum();
		model.put("dispatchNumNo", dispatchNumNo);
		
		//文号前缀
		ComboboxVO dispatchNumPreCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> dispatchNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
		for(String key : dispatchNumPreMap.keySet()){
			SysDictionaryVO defualtTypeVO = dispatchNumPreMap.get(key);
			dispatchNumPreCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("dispatchNumPreComboBox", JsonUtil.toJson(dispatchNumPreCombo.getOptions()));
		//发文文号时间序号信息
		List<Map<String,String>> dispatchNumberInfoList = this.dispatchManagementService.getDispatchNumberInfo();
		//文号 时间
		ComboboxVO dispatchNumYearComboBox = new ComboboxVO();
		int startValue = 1990;
		if (dispatchNumberInfoList != null && dispatchNumberInfoList.size() > 0) {
			Map<String,String> dataMap =  dispatchNumberInfoList.get(0);
			if (dataMap != null && dataMap.get("C_DISPATCH_NUM_START_TIME") != null) {
				try {
					startValue = Integer.parseInt(dataMap.get("C_DISPATCH_NUM_START_TIME").toString());
				} catch (NumberFormatException e) {
					startValue = 1990;
				}
			}
		}
		for (int i=0; i<100; i++ ) {
			int optionVal =  startValue + i;
			dispatchNumYearComboBox.addOption("" + optionVal , "" + optionVal);
		}
		
		model.put("dispatchNumYearComboBox", JsonUtil.toJson(dispatchNumYearComboBox.getOptions()));
		
		//发文类型
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		ComboboxVO dispatchTypeComboBox = new ComboboxVO();
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			dispatchTypeComboBox.addOption(dispatchTypeVO.getCode(), dispatchTypeVO.getName());
		}
		model.put("dispatchTypeComboBox",  JsonUtil.toJson(dispatchTypeComboBox.getOptions()));
		
		//发文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {1,3,4,5,6,10,11};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);

		//部门列表
		ComboboxVO departmentComboBox = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				departmentComboBox.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("departmentComboBox", JsonUtil.toJson(departmentComboBox.getOptions()));
		
		//缓急程度
		ComboboxVO urgencyComboBox = new ComboboxVO();
		for (UrgencyEnum urgencyEnum : UrgencyEnum.values()) {
			urgencyComboBox.addOption(urgencyEnum.getCode(),  urgencyEnum.getName());
		}
		model.put("urgencyComboBox", JsonUtil.toJson(urgencyComboBox.getOptions()));
		
		//密级
		ComboboxVO denseComboBox = new ComboboxVO();
		for (DenseEnum denseEnum : DenseEnum.values()) {
			denseComboBox.addOption(denseEnum.getCode(),  denseEnum.getName());
		}
		model.put("denseComboBox", JsonUtil.toJson(denseComboBox.getOptions()));
		
		//反馈提醒时间
		model.put("remindTime", entity.getFeedBackRemindTime());
		
		//接收人
		List<TreeEntity> recipientTreeList = dispatchManagementService.getRecipientTree();
		model.put("recipientTree",JsonUtil.toJson(recipientTreeList));
		
		if (entity != null) {
			model.put("dispatchManagementEntity", entity);
		} else {
			model.put("dispatchManagementEntity", new DispatchManagementEntity());
		}
		
		return this.createModelAndView("/OAManagement/dispatchManagement/edit", model);
	}
	
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param id 查看对象id
	 * @return
	 */
	@RequestMapping("/gotoViewPage/{id}/{type}")
	public ModelAndView gotoViewPage(HttpServletRequest request, @PathVariable Long id, @PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		DispatchManagementEntity entity=dispatchManagementService.findById(id);
		
		model.put("dispatchManagementEntity", entity);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginUser", sysUserEntity);
		
		if ("all".equals(type)) {
			model.put("viewTitleName", "部内发文");
		} else if ("release".equals(type)) {
			model.put("viewTitleName", "全部挂网发文");
		}
		
		return this.createModelAndView("/OAManagement/dispatchManagement/view", model);
	}
	
	/**
	 * 跳转到反馈页面
	 * @param request
	 * @param id 查看对象id
	 * @return
	 */
	@RequestMapping("/goFeedbackPage/{id}")
	public ModelAndView goFeedbackPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		DispatchManagementEntity entity=dispatchManagementService.findById(id);

		//附件
		if (entity.getAppendix() != null && !"".equals(entity.getAppendix())) {
			JSONArray appendixJSONArray=JSONArray.fromObject(entity.getAppendix());
			for (int i = 0; i < appendixJSONArray.size(); i++) {
				JSONObject josnObject= (JSONObject) appendixJSONArray.get(i);
				entity.setAppendixName(String.valueOf(josnObject.get("name")));
			}
		}
		
		model.put("dispatchManagementEntity", entity);
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		//拟稿人
		model.put("drafterName", sysUserEntity.getName());
		model.put("drafterId", sysUserEntity.getId().toString());
				
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		//反馈时间
		model.put("feebackTime", dfuYMd.format(cal.getTime()));
		return this.createModelAndView("/OAManagement/dispatchManagement/feedBack", model);
	}
	
	/**
	 * 修改保存
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editEntity")
	public @ResponseBody
	ResultObj update(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		
		entity.setDispatchName(entity.getDispatchNameSymbolName() + entity.getDispatchNameYear() + entity.getDispatchNameNum());
		entity.setUpdateDate(new Date());

		dispatchManagementService.updateEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	
	/**
	 * @Description: 删除
	 * @param id 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/deleteOne/{id}")
	public @ResponseBody
	ResultObj deleteOne( @PathVariable Long id) {
		ResultObj resultObj = new ResultObj();
		dispatchManagementService.deleteEntity(id);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	/**
	 * @Description: 批量删除
	 * @param ids 删除对象id
	 * @return
	 */
	@RequestMapping(value = "/allDelete")
	public @ResponseBody
	ResultObj allDelete(@RequestBody List<String> ids) {
		ResultObj resultObj = new ResultObj();
		for (String id : ids) {
			Long longId = new Long(id);
			DispatchManagementEntity entity = dispatchManagementService.findById(longId);
			if (entity != null) {
				dispatchManagementService.deleteEntity(longId);
				SysUserEntity userEntity = RequestContext.get().getUser();
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.DISPATCH.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
			}
		}
		return resultObj;
	}
	
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportExcel")
	public void expData(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		Page<DispatchManagementEntity> page=new Page<DispatchManagementEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		if (page != null) {
			page.setPageSize(Integer.MAX_VALUE);
			page.addOrder(Sort.desc("C_ID"));
		}
		//////////////sml添加开始
		List<Condition> userConditions = new ArrayList<Condition>();
		userConditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "85"));
		List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(userConditions, null);
		List<String> userUnitIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
			userUnitIds.add(sysDutiesDetailEntity.getUserUnitRelId());
		}
		userConditions.clear();
		userConditions.add(new Condition("C_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitIds.toArray()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
		List<String> userIds = new ArrayList<String>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			userIds.add(userUnitRelEntity.getUserId().toString());
		}
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userId = sysUserEntity.getId().toString();
		List<DispatchManagementEntity> dataList = new ArrayList<DispatchManagementEntity>();
		if(userIds.contains(userId)){
			dataList = dispatchManagementService.findByCondition(conditions, page);
		}else{
			conditions.add(new Condition("C_DRAFTER_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userId));
			dataList = dispatchManagementService.findByCondition(conditions, page);
		}
		///////////////添加结束
//		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "发文管理报表模板.xlsx","全部发文.xlsx", resultMap);


	}
	
	/**
	 * 导出
	 * @param req
	 * @param res
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/exportReleaseExcel")
	public void expDataRelease(HttpServletRequest req,HttpServletResponse res,Map<String, Object> params) throws UnsupportedEncodingException{
		
		String conditionsChan=req.getParameter("conditions");
		
		params.put("conditions", JSONArray.fromObject(conditionsChan));
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		Page<DispatchManagementEntity> page=new Page<DispatchManagementEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		if (page != null) {
			page.setPageSize(Integer.MAX_VALUE);
			page.addOrder(Sort.desc("C_ID"));
		}
		
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		resultMap.put("dataList", dataList);
		
		ExcelUtil.export(req, res, "挂网发文管理报表模板.xlsx","全部挂网发文.xlsx", resultMap);
		
		
	}
	
	/**
	 * 查询审查信息
	 */
	@RequestMapping(value = "/searchReviewData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReviewData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<DispatchReviewEntity> dataList = new ArrayList<DispatchReviewEntity>();
		//部门负责人审批意见
		Page<DispatchReviewEntity> page = PageUtil.getPage(params);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<DispatchReviewEntity> reviewDataList = this.dispatchReviewService.findByCondition(conditions, page);
		if (reviewDataList != null && reviewDataList.size() > 0) {
			for (DispatchReviewEntity entity : reviewDataList) {
				entity.setReviewType(ReviewTypeEnum.REVIEW.getCode());
				dataList.add(entity);
			}
		}
		//会签意见
		Page<DispatchJointlySignEntity> page1 = PageUtil.getPage(params);
		List<Condition> conditions1 = OrmUtil.changeMapToCondition(params);
		List<DispatchJointlySignEntity> jointlySignDataList = this.dispatchJointlySignService.findByCondition(conditions1, page1);
		if (jointlySignDataList != null && jointlySignDataList.size() > 0) {
			for (DispatchJointlySignEntity entity : jointlySignDataList) {
				DispatchReviewEntity reviewEntiy = new DispatchReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setDispatchId(entity.getDispatchId());
				reviewEntiy.setReviewPersionId(entity.getSignPersionId());
				reviewEntiy.setReviewPersionName(entity.getSignPersionName());
				reviewEntiy.setReviewResult(entity.getSignResult());
				reviewEntiy.setReviewTime(entity.getSignTime());
				reviewEntiy.setReviewComment(entity.getSignComment());
				reviewEntiy.setReviewType(ReviewTypeEnum.JOINTLY_SIGN.getCode());
				dataList.add(reviewEntiy);
			}
		}
		
		//领导审核意见
		Page<DispatchLeaderApprovalEntity> page2 = PageUtil.getPage(params);
		List<Condition> conditions2 = OrmUtil.changeMapToCondition(params);
		List<DispatchLeaderApprovalEntity> leaderDataList = this.dispatchLeaderApprovalService.findByCondition(conditions2, page2);
		if (leaderDataList != null && leaderDataList.size() > 0) {
			for (DispatchLeaderApprovalEntity entity : leaderDataList) {
				DispatchReviewEntity reviewEntiy = new DispatchReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setDispatchId(entity.getDispatchId());
				reviewEntiy.setReviewPersionId(entity.getApprovalPersionId());
				reviewEntiy.setReviewPersionName(entity.getApprovalPersionName());
				reviewEntiy.setReviewResult(entity.getApprovalResult());
				reviewEntiy.setReviewTime(entity.getApprovalTime());
				reviewEntiy.setReviewComment(entity.getApprovalComment());
				reviewEntiy.setReviewType(ReviewTypeEnum.LEADER.getCode());
				dataList.add(reviewEntiy);
			}
		}
		
		Collections.sort(dataList, new SortUtil ());
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			if (page != null) {
				page.setResultList(dataList);
				page.setTotal(dataList.size());
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal(dataList.size());
			}
		}
		return resultObj;
	}
	
	/**
	 * 查询会签信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/searchJointlySingData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJointlySingData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<DispatchJointlySignEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<DispatchJointlySignEntity> dataList = this.dispatchJointlySignService.findByCondition(conditions, page);
		
		
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
	
	/**
	 * 查询领导审查信息
	 * @return
	 */
	@RequestMapping(value = "/searchLeaderApprovalData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchLeaderApprovalData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<DispatchLeaderApprovalEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<DispatchLeaderApprovalEntity> dataList = this.dispatchLeaderApprovalService.findByCondition(conditions, page);
		
		
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
	
	/**
	 * 查询反馈信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/searchFeedBackData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchFeedBackData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<DispatchFeedbackEntity> page = PageUtil.getPage(params);
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<DispatchFeedbackEntity> dataList = this.dispatchFeedbackService.findByCondition(conditions, page);
		
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
	
	/**
	 * 数据列表中提交审批
	 * @param request
	 * @param id 数据ID
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/approvalSubmit/{id}")
	public @ResponseBody ResultObj approvalSubmit(HttpServletRequest request,@PathVariable Long id,@RequestBody Map<String, Object> params) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.DISPATCH_MANAGEMENT_KEY.getName()));
		
		List<DefinitionEntity> defList= definitionService.findByCondition(conditions, null);
		ResultObj resultObj = new ResultObj();
		if(!defList.isEmpty()){
			String modelId= defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来	
			//SysUserEntity userEntity = RequestContext.get().getUser();
			SysUserEntity starter= null;
			if(!RequestContext.get().isDeveloperMode()){
				starter = RequestContext.get().getUser();
			}
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
			if (userList != null && userList.size() > 0) {
				this.dispatchManagementService.submit(id.toString(), userList);
				String userName = "";
				for (int i=0 ;i<userList.size(); i++) {
					SysUserEntity user = userList.get(i);
					userName += user.getName();
					if (i != userList.size()-1) {
						userName += ",";
					}
				}
				DispatchManagementEntity entity = this.dispatchManagementService.findById(id);
				entity.setCurrentStepUserList(userName);
				this.dispatchManagementService.updateEntity(entity);
			}else {
				resultObj.setResult("noPerson");
			}
		} 
		return resultObj;
	}
	
	/**
	 * 追加并提交审批
	 * @param entity 追加的发文对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAndApprovalSubmit")
	public @ResponseBody ResultObj addAndApprovalSubmit(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());

		//附件
		if (entity.getAppendix() != null && !"".equals(entity.getAppendix())) {
			JSONArray appendixJSONArray=JSONArray.fromObject(entity.getAppendix());
			for (int i = 0; i < appendixJSONArray.size(); i++) {
				JSONObject josnObject= (JSONObject) appendixJSONArray.get(i);
				entity.setAppendixName(String.valueOf(josnObject.get("name")));
			}
		}
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.DISPATCH_MANAGEMENT_KEY.getName()));
		
		List<DefinitionEntity> defList= definitionService.findByCondition(conditions, null);
		ResultObj resultObj = new ResultObj();
		if(!defList.isEmpty()){
			String modelId= defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来
			SysUserEntity userEntity = RequestContext.get().getUser();
			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",userEntity);
			if (userList != null && userList.size() > 0) {
				String userName = "";
				for (int i=0 ;i<userList.size(); i++) {
					SysUserEntity user = userList.get(i);
					userName += user.getName();
					if (i != userList.size()-1) {
						userName += ",";
					}
				}
				entity.setCurrentStepUserList(userName);
				this.dispatchManagementService.addEntity(entity);
				this.dispatchManagementService.submit(entity.getId().toString(), userList);
			} else {
				resultObj.setResult("noPerson");
			}
		}
		return resultObj;
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
		DispatchManagementEntity entity=dispatchManagementService.findById(id);

		//附件
		if (entity.getAppendix() != null && !"".equals(entity.getAppendix())) {
			JSONArray appendixJSONArray=JSONArray.fromObject(entity.getAppendix());
			for (int i = 0; i < appendixJSONArray.size(); i++) {
				JSONObject josnObject= (JSONObject) appendixJSONArray.get(i);
				entity.setAppendixName(String.valueOf(josnObject.get("name")));
			}
		}
		
		String dispatchType = entity.getType();
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			if (dispatchTypeVO.getCode().equals(dispatchType)){
				entity.setTypeCN(dispatchTypeVO.getName());
				break;
			}
		}
		
		model.put("dispatchManagementEntity", entity);
		String approvalStatus = entity.getApprovalStatus();
		
		if(DispatchApprovalStatusEnum.REVIEW.getCode().equals(approvalStatus)) {
			//待审批
			return this.createModelAndView("OAManagement/dispatchManagement/review", model);
		} else if(DispatchApprovalStatusEnum.JOINLTY_SING.getCode().equals(approvalStatus)) {
			//带会签
			return this.createModelAndView("OAManagement/dispatchManagement/JoinltySign", model);
		}  else if(DispatchApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getCode().equals(approvalStatus)) {
			//领导审批
			return this.createModelAndView("OAManagement/dispatchManagement/leaderApproval", model);
		} else if(DispatchApprovalStatusEnum.REVIEW_REJECT.getCode().equals(approvalStatus)) {
			setResubmitInfo(model, entity.getId());
			//部门负责人驳回,待再申请 
			return this.createModelAndView("OAManagement/dispatchManagement/reSubmit", model);
		} else if(DispatchApprovalStatusEnum.LEADER_APPROVAL_SIGN.getCode().equals(approvalStatus)) {
			//会签驳回，带部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/leaderApproval", model);
		} else if(DispatchApprovalStatusEnum.JOINLTY_SING_REJECT.getCode().equals(approvalStatus)) {
			//领导审核驳回会签
			return this.createModelAndView("OAManagement/dispatchManagement/review", model);
		} else if(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT.getCode().equals(approvalStatus)) {
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/JoinltySign", model);
		} else if(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode().equals(approvalStatus)) {
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/review", model);
		}  else if(DispatchApprovalStatusEnum.RESULTS.getCode().equals(approvalStatus)) {
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/complete", model);
		} else if(DispatchApprovalStatusEnum.JOINLTY_SING_REJECT_START.getCode().equals(approvalStatus)) {
			setResubmitInfo(model, entity.getId());
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/reSubmit", model);
		} else if(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getCode().equals(approvalStatus)) {
			setResubmitInfo(model, entity.getId());
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/reSubmit", model);
		}  else if(DispatchApprovalStatusEnum.DISCARDED.getCode().equals(approvalStatus)) {
			setResubmitInfo(model, entity.getId());
			//领导审核驳回部门领导再审批
			return this.createModelAndView("OAManagement/dispatchManagement/complete", model);
		}   else if(DispatchApprovalStatusEnum.COMPOSED.getCode().equals(approvalStatus)) {
			//排版
			return this.createModelAndView("OAManagement/dispatchManagement/composeApproval", model);
		}
		return this.createModelAndView("OAManagement/dispatchManagement/view", model);
	}
	
	private void setResubmitInfo(Map<String, Object> model, Long id){
		DispatchManagementEntity entity = dispatchManagementService.findById(id);

		//拟稿人
		model.put("drafterName", entity.getDrafterName());

		//拟稿时间
		model.put("drafterTime", entity.getDraftTime());

		/*
		 * 发文字号
		 */
		//发文字号文号
		String dispatchNumPreValue = entity.getDispatchNameSymbolId();
		model.put("dispatchNumPreVal", dispatchNumPreValue);
		//发文文号年号
		String dispatchNumYear = entity.getDispatchNameYear();
		model.put("dispatchNumYearVal", dispatchNumYear);
		//发文文号序号
		String dispatchNumNo = entity.getDispatchNameNum();
		model.put("dispatchNumNo", dispatchNumNo);
		
		//文号前缀
		ComboboxVO dispatchNumPreCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> dispatchNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
		for(String key : dispatchNumPreMap.keySet()){
			SysDictionaryVO defualtTypeVO = dispatchNumPreMap.get(key);
			dispatchNumPreCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("dispatchNumPreComboBox", JsonUtil.toJson(dispatchNumPreCombo.getOptions()));
		//发文文号时间序号信息
		List<Map<String,String>> dispatchNumberInfoList = this.dispatchManagementService.getDispatchNumberInfo();
		//文号 时间
		ComboboxVO dispatchNumYearComboBox = new ComboboxVO();
		int startValue = 1990;
		if (dispatchNumberInfoList != null && dispatchNumberInfoList.size() > 0) {
			Map<String,String> dataMap =  dispatchNumberInfoList.get(0);
			if (dataMap != null && dataMap.get("C_DISPATCH_NUM_START_TIME") != null) {
				try {
					startValue = Integer.parseInt(dataMap.get("C_DISPATCH_NUM_START_TIME").toString());
				} catch (NumberFormatException e) {
					startValue = 1990;
				}
			}
		}
		for (int i=0; i<100; i++ ) {
			int optionVal =  startValue + i;
			dispatchNumYearComboBox.addOption("" + optionVal , "" + optionVal);
		}
		
		model.put("dispatchNumYearComboBox", JsonUtil.toJson(dispatchNumYearComboBox.getOptions()));
		
		//发文类型
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		ComboboxVO dispatchTypeComboBox = new ComboboxVO();
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			dispatchTypeComboBox.addOption(dispatchTypeVO.getCode(), dispatchTypeVO.getName());
		}
		model.put("dispatchTypeComboBox",  JsonUtil.toJson(dispatchTypeComboBox.getOptions()));
		
		//发文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {1,3,4,5,6,10,11};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);

		//部门列表
		ComboboxVO departmentComboBox = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				departmentComboBox.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("departmentComboBox", JsonUtil.toJson(departmentComboBox.getOptions()));
		
		//缓急程度
		ComboboxVO urgencyComboBox = new ComboboxVO();
		for (UrgencyEnum urgencyEnum : UrgencyEnum.values()) {
			urgencyComboBox.addOption(urgencyEnum.getCode(),  urgencyEnum.getName());
		}
		model.put("urgencyComboBox", JsonUtil.toJson(urgencyComboBox.getOptions()));
		
		//密级
		ComboboxVO denseComboBox = new ComboboxVO();
		for (DenseEnum denseEnum : DenseEnum.values()) {
			denseComboBox.addOption(denseEnum.getCode(),  denseEnum.getName());
		}
		model.put("denseComboBox", JsonUtil.toJson(denseComboBox.getOptions()));
		
		//反馈提醒时间
		model.put("remindTime", entity.getFeedBackRemindTime());
		
		//接收人
		List<TreeEntity> recipientTreeList = dispatchManagementService.getRecipientTree();
		model.put("recipientTree",JsonUtil.toJson(recipientTreeList));
		
		if (entity != null) {
			model.put("dispatchManagementEntity", entity);
		} else {
			model.put("dispatchManagementEntity", new DispatchManagementEntity());
		}
	}
	
	/**
	 * 部门负责人：选择审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPersonReview")
	public ModelAndView submitPersonReview(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.DISPATCH_MANAGEMENT_KEY.getName()));
		
		List<DefinitionEntity> defList= definitionService.findByCondition(conditions, null);
		List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
		if(!defList.isEmpty()){
			String modelId= defList.get(0).getModelId();
			//审批页面点击签发按钮的时候，把下一步的人查询出来	
			//SysUserEntity userEntity = RequestContext.get().getUser();
			SysUserEntity starter= null;
			if(!RequestContext.get().isDeveloperMode()){
				starter = RequestContext.get().getUser();
			}
			userList= nodeConfigService.getFirstNodeTransactor(modelId, "", starter);
		}
	
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/dispatchManagement/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 领导审核：选择审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPersonLeader/{taskId}")
	public ModelAndView submitPersonLeader(HttpServletRequest request,@PathVariable String taskId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<SysUserEntity> userList= nodeConfigService.getNextNodeTransactor(taskId, DispatchProcessEnum.REVIEW_LEADER_APPROVAL.getCode());
		
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/dispatchManagement/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 领导审核：选择审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPersonJoinltySign/{taskId}")
	public ModelAndView submitPersonJoinltySign(HttpServletRequest request,@PathVariable String taskId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
		
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/dispatchManagement/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 审查意见：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dispatchReview")
	public @ResponseBody ResultObj dispatchReview(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		
		DispatchManagementEntity dispatchEntity = this.dispatchManagementService.findById(entity.getId());

		DateFormatUtil dfuYMdHM = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		dispatchEntity.setUpdateDate(cal.getTime());
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		dispatchEntity.setReviewPersion(sysUserEntity.getLoginName());
		dispatchEntity.setReviewPersionCN(sysUserEntity.getName());
		dispatchEntity.setReviewTime(dfuYMdHM.format(cal.getTime()));
		dispatchEntity.setApprovalStatus(entity.getApprovalStatus());
		dispatchEntity.setReviewComment(entity.getReviewComment());
		dispatchEntity.setReview(entity.getReview());
		dispatchEntity.setTaskId(entity.getTaskId());
		dispatchEntity.setProcInstId(entity.getProcInstId());
		dispatchEntity.setApprovalStatus(entity.getApprovalStatus());
		
		//reveiw记录表
		DispatchReviewEntity reviewEntity = new DispatchReviewEntity();
		reviewEntity.setDispatchId(entity.getId());
		reviewEntity.setReviewComment(entity.getReviewComment());
		reviewEntity.setReviewPersionId(dispatchEntity.getReviewPersion());
		reviewEntity.setReviewPersionName(dispatchEntity.getReviewPersionCN());
		if (DispatchProcessEnum.REVIEW_REJECT.getCode().equals(entity.getReview())) {
			reviewEntity.setReviewResult(ApprovalResultEnum.REJECT.getCode());
		} else {
			reviewEntity.setReviewResult(ApprovalResultEnum.AGREE.getCode());
		}
		reviewEntity.setReviewTime(dfuYMdHM.format(cal.getTime()));
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
		params.put("reviewer", entity.getReviewPersion());
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getReviewComment()==null?"":entity.getReviewComment());
		if (DispatchProcessEnum.REVIEW_LEADER_APPROVAL.getCode().equals(entity.getReview())) {

			String type = dispatchEntity.getType();
			String copyUser = "";
			String setCopyUserIds = "";
			if ("5".equals(type) || "7".equals(type) || "8".equals(type)) {
				List<SysUserEntity> copyUserList = this.dispatchManagementService.getPerson4TrainAndRepair(type, entity.getTaskId());
				if (copyUserList != null && copyUserList.size() > 0) {
					for (int i = 0;i<copyUserList.size(); i++) {
						SysUserEntity userEntity  = copyUserList.get(i);
						copyUser += userEntity.getLoginName();
						setCopyUserIds += userEntity.getId().toString();
						if (i != copyUserList.size() -1) {
							copyUser += ",";
							setCopyUserIds += ",";
						}
					} 
				}
				dispatchEntity.setCopyUserIds(setCopyUserIds);
			}
			dispatchEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			dispatchEntity.setLeaderSelectedPersionCN(entity.getLeaderSelectedPersionCN());
			dispatchEntity.setCurrentStepUserList(entity.getLeaderSelectedPersionCN());
			
			if (copyUser.length() > 0) {
				params.put(CandidateMarkEnum.COPY_HANDLERS.getName(), copyUser);
			}
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), DispatchProcessEnum.REVIEW_LEADER_APPROVAL.getCode());
			params.put("processStatus",DispatchApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());

		} else if (DispatchProcessEnum.REVIEW_JOINLTY_SIGN.getCode().equals(entity.getReview())) {
			
			dispatchEntity.setJiontlySelectedPersion(entity.getJiontlySelectedPersion());
			dispatchEntity.setJiontlySelectedPersionCn(entity.getJiontlySelectedPersionCn());
			dispatchEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			dispatchEntity.setLeaderSelectedPersionCN(entity.getLeaderSelectedPersionCN());			
			dispatchEntity.setCurrentStepUserList(entity.getJiontlySelectedPersionCn());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), DispatchProcessEnum.REVIEW_JOINLTY_SIGN.getCode());
			params.put("processStatus",DispatchApprovalStatusEnum.JOINLTY_SING.getId());
			params.put("result",ApprovalResultEnum.AGREE.getName());
			
		} else if (DispatchProcessEnum.REVIEW_REJECT.getCode().equals(entity.getReview())) {
			dispatchEntity.setCurrentStepUserList(dispatchEntity.getDrafterName());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), DispatchProcessEnum.REVIEW_REJECT.getCode());
			params.put("processStatus",DispatchApprovalStatusEnum.REVIEW_REJECT.getId());
			params.put("result",ApprovalResultEnum.REJECT.getName());
		}
		
		dispatchManagementService.approve(entity, params);

		dispatchManagementService.updateEntity(dispatchEntity);
		dispatchReviewService.addEntity(reviewEntity);
		
		return new ResultObj();
	}

	/**
	 * 会签：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dispatchJoinltySign")
	public @ResponseBody ResultObj dispatchJoinltySign(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {

		DispatchManagementEntity dispatchManagementEntity = this.dispatchManagementService.findById(entity.getId());

		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		entity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		entity.setJointlySignPersion(sysUserEntity.getLoginName());
		entity.setJointlySignPersionCN(sysUserEntity.getName());
		entity.setJointlySignTime(dfuYMd.format(cal.getTime()));
		

		//会签同意时，保存会签人id,驳回时jiontlySignedPersion属性清空
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getJointlySign())) {
			String signedPersion = dispatchManagementEntity.getJointlySignedPersion();
			if (signedPersion !=null && !"".equals(signedPersion)) {
				signedPersion += "," + sysUserEntity.getLoginName();
			} else {
				signedPersion = sysUserEntity.getLoginName();
			}
			entity.setJointlySignedPersion(signedPersion);
		} else {
			entity.setJointlySignedPersion("");
			entity.setJiontlySelectedPersion("");
			entity.setLeaderSelectedPersion("");
		}
	
		/**
		 * 判断是否所有人都同意
		 */
		//已经会签同意的人
		List<String> signededUserList = new ArrayList<String>();
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getJointlySign())) {
			boolean isAllJointlySign = true;
			//选择的所有会签的人
			String users = dispatchManagementEntity.getJiontlySelectedPersion();
			String [] userArry = users.split(",");
			//会签时，所有同意的人
			String signedUser = entity.getJointlySignedPersion();
			String [] signedUserArry = signedUser.split(",");
		
			for (String signedUid : signedUserArry) {
				signededUserList.add(signedUid);
			}

			for (int i=0; i<userArry.length;i++) {
				String signedUid = userArry[i];
				if (!signededUserList.contains(signedUid)) {
					isAllJointlySign = false;
					break;
				}
			}
			
			if (!isAllJointlySign) {
				entity.setApprovalStatus("3");
				entity.setCurrentStepUserList(dispatchManagementEntity.getCurrentStepUserList());
			} else {
				entity.setCurrentStepUserList(dispatchManagementEntity.getLeaderSelectedPersionCN());
			}
		} else {
			//会签时，所有同意的人
			String signedUser = dispatchManagementEntity.getJointlySignedPersion();
			if (signedUser != null) {
				String [] signedUserArry = signedUser.split(",");
			
				for (String signedUid : signedUserArry) {
					signededUserList.add(signedUid);
				}
			}
				
		}
		

		if (!DispatchProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getJointlySign())) {
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("a.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, signededUserList.toArray()));
				List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
				if (userList != null && userList.size() > 0) {
					for (SysUserEntity user : userList) {
						MessageCenterEntity messageEntity =new MessageCenterEntity();
						messageEntity.setTitle("发文: " + entity.getTitle() + "已驳回");
						//发送消息点击消息内容跳转到抄送列表时
						//messageEntity.setContext("<a href='#' onclick='goCopyTaskList()'> 发文: " + entity.getTitle() + "已驳回</a>");
						messageEntity.setContext(entity.getTitle() + "已驳回");
						messageEntity.setSendTime(new Date());
						messageEntity.setReceivePerson(user.getId().toString());
						messageEntity.setSendPerson(user.getId().toString());
						messageEntity.setType("private");
						messageCenterService.addMessage(messageEntity);
					}
				}
		}
		
		
		DispatchJointlySignEntity signEntity = new DispatchJointlySignEntity();
		signEntity.setDispatchId(entity.getId());
		signEntity.setSignComment(entity.getJointlySignComment());
		signEntity.setSignPersionId(entity.getJointlySignPersion());
		signEntity.setSignPersionName(entity.getJointlySignPersionCN());
		if (DispatchProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getJointlySign())) {
			signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			signEntity.setSignResult(ApprovalResultEnum.REJECT.getCode());
		}
		signEntity.setSignTime(dfuYMd.format(cal.getTime()));
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getJointlySignComment()==null?"":entity.getJointlySignComment());
		if (DispatchProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getJointlySign())) {
			String leaderSelectedPersionIds = dispatchManagementEntity.getLeaderSelectedPersion();

			String type = entity.getType();
			String copyUser = "";
			String setCopyUserIds = "";
			if ("5".equals(type) || "7".equals(type) || "8".equals(type)) {
				List<SysUserEntity> copyUserList = this.dispatchManagementService.getPerson4TrainAndRepair(type, entity.getTaskId());
				if (copyUserList != null && copyUserList.size() > 0) {
					for (int i = 0;i<copyUserList.size(); i++) {
						SysUserEntity userEntity  = copyUserList.get(i);
						copyUser += userEntity.getLoginName();
						setCopyUserIds += userEntity.getId().toString();
						if (i != copyUserList.size() -1) {
							copyUser += ",";
							setCopyUserIds += ",";
						}
					} 
				}
				entity.setCopyUserIds(setCopyUserIds);
			}
			if (copyUser.length() > 0) {
				params.put(CandidateMarkEnum.COPY_HANDLERS.getName(), copyUser);
			}
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),leaderSelectedPersionIds);
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "1");
			params.put("prOfCompleteInstances", "1");
			params.put("processStatus",DispatchApprovalStatusEnum.LEADER_APPROVAL_SIGN.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
		} else if (DispatchProcessEnum.JOINLTY_SING_REJECT_REVIEW.getCode().equals(entity.getJointlySign())) {
			//走流程时需要追加抄送人时使用
			//从流程图上根据配置职务取得抄送人，根据业务需求写代码，以下查询抄送人部分是因为会签驳回时要给已经回去同意的人发消息
//			List<SysUserEntity> copyUserList=nodeConfigService.getNextNodeCopyTransactor(entity.getTaskId(), DispatchProcessEnum.REVIEW_LEADER_APPROVAL.getCode());
			//查询抄送人
//			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//			conditions.add(new Condition("a.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, signededUserList.toArray()));
//			List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
//			String userLoginNames = "";
//			if (userList != null && userList.size() > 0) {
//				for (int i=0 ;i< userList.size();i++) {
//					SysUserEntity user = userList.get(i);
//					userLoginNames += user.getLoginName();
//					if (i != userList.size() -1) {
//						userLoginNames += ",";
//					}
//				}
//			}
//			//添加抄送人
//			if (userLoginNames.length() >0) {
//				params.put(CandidateMarkEnum.COPY_HANDLERS.getName(), userLoginNames);
//			}
			
			
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),dispatchManagementEntity.getReviewPersion());
			
			entity.setCurrentStepUserList(dispatchManagementEntity.getReviewPersionCN());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "2");
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",DispatchApprovalStatusEnum.REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
		} else if (DispatchProcessEnum.JOINLTY_SING_REJECT_START.getCode().equals(entity.getJointlySign())) {

			entity.setCurrentStepUserList(dispatchManagementEntity.getDrafterName());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "3");
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",DispatchApprovalStatusEnum.DTJ.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
		}

		dispatchManagementService.approve(entity, params);
		
		dispatchManagementService.updateJointlySignInfo(entity);
		dispatchJointlySignService.addEntity(signEntity);
		
		return new ResultObj();
	}
	
	/**
	 * 领导审批：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dispatchLeaderApproval")
	public @ResponseBody ResultObj dispatchLeaderApproval(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		
		DispatchManagementEntity dispatchManagementEntity = this.dispatchManagementService.findById(entity.getId());
		 
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		entity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		entity.setLeaderPersion(sysUserEntity.getLoginName());
		entity.setLeaderPersionCN(sysUserEntity.getName());
		entity.setLeaderTime(dfuYMd.format(cal.getTime()));
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getLeaderComment()==null?"":entity.getLeaderComment());
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getLeaderApproval())) {
			List<Condition> conditions = new ArrayList<Condition>();
			//查询文书编号|排版职务 对应职务编码-1076
			List<String> userIdList = new ArrayList<String>();
			conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,"1076"));
			List<SysDutiesEntity>  dutiesEntity = sysDutiesService.findByCondition(conditions, null);
			String dutyId3=dutiesEntity.get(0).getId().toString();
			conditions.clear();
			conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,dutyId3));
			//获取职务对应人员ID
			List<SysDutiesDetailEntity>  duEntity3 = sysDutiesDetailService.findByCondition(conditions, null);
			for (SysDutiesDetailEntity sysDutiesDetailEntity : duEntity3) {
				List<Condition> condition = new ArrayList<Condition>();
				Long userUnitid = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
				condition.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userUnitid));
				condition.add(new Condition("C_UNIT_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,143));
				List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(condition, null);
				for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
					userIdList.add(tempuserUnitRel.getUserId().toString());
				}
			}
			List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
			for(String userId:userIdList){
				conditions.clear();
				conditions.add(new Condition("a.C_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,Integer.parseInt(userId)));
				List<SysUserEntity> tempList = sysUserService.findByCondition(conditions, null);
				userList.add(tempList.get(0));
			}
			String logingNameString = "";
			String nameString = "";
			int i = 0;
			for(SysUserEntity userEntity:userList){
				logingNameString += userEntity.getLoginName() + ",";
				if(i < userList.size()-1){
					nameString += userEntity.getName() + ",";
				}else{
					nameString += userEntity.getName();
				}
				i++;
			}
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),logingNameString);
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "1");
			params.put("processStatus",DispatchApprovalStatusEnum.COMPOSED.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			entity.setApprovalStatus(DispatchApprovalStatusEnum.COMPOSED.getCode());
			entity.setCurrentStepUserList(nameString);
			
//			String type = dispatchManagementEntity.getType();
//			if ("5".equals(type) || "7".equals(type) || "8".equals(type)) {
//				String setCopyUserIds = dispatchManagementEntity.getCopyUserIds();
//				if (setCopyUserIds != null && setCopyUserIds.length()> 0) {
//					String [] uids = setCopyUserIds.split(",");
//					for (String uid: uids) {
//						MessageCenterEntity messageEntity =new MessageCenterEntity();
//						messageEntity.setTitle(dispatchManagementEntity.getTitle() + "-" + entity.getTypeCN() + "审批通过");
//						messageEntity.setSendTime(new Date());
//						messageEntity.setReceivePerson(uid);
//						messageEntity.setSendPerson(uid);
//						messageEntity.setContext("<a href='#' onclick='goOverCopyTaskList()'>" + dispatchManagementEntity.getTitle() + "-" + dispatchManagementEntity.getTypeCN() + "审批通过</a>");
//						messageEntity.setType("private");
//						messageCenterService.addMessage(messageEntity); 
//					} 
//				}
//			}
			
		} else if (DispatchProcessEnum.LEADER_APPROVAL_REJECT_JOINLTY_SING.getCode().equals(entity.getLeaderApproval())) {
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "2");
			params.put("processStatus",DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			entity.setApprovalStatus(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT.getCode());
			entity.setJiontlySelectedPersion(dispatchManagementEntity.getJiontlySelectedPersion());
			entity.setJiontlySelectedPersionCn(dispatchManagementEntity.getJiontlySelectedPersionCn());
			entity.setJointlySignedPersion("");
			entity.setCurrentStepUserList(dispatchManagementEntity.getJiontlySelectedPersionCn());
			
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),dispatchManagementEntity.getJiontlySelectedPersion());
		} else if (DispatchProcessEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode().equals(entity.getLeaderApproval())) {

			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),dispatchManagementEntity.getReviewPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "3");
			params.put("processStatus",DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			entity.setJointlySignedPersion("");
			entity.setCurrentStepUserList(dispatchManagementEntity.getReviewPersionCN());
			entity.setApprovalStatus(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode());

		} else if (DispatchProcessEnum.LEADER_APPROVAL_REJECT_START.getCode().equals(entity.getLeaderApproval())) {
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), "4");
			params.put("processStatus",DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			entity.setApprovalStatus(DispatchApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getCode());
			entity.setJointlySignedPersion("");
			entity.setCurrentStepUserList(dispatchManagementEntity.getDrafterName());
		}
		
		DispatchLeaderApprovalEntity reviewEntity = new DispatchLeaderApprovalEntity();
		reviewEntity.setDispatchId(entity.getId());
		reviewEntity.setApprovalComment(entity.getLeaderComment());
		reviewEntity.setApprovalPersionId(entity.getLeaderPersion());
		reviewEntity.setApprovalPersionName(entity.getLeaderPersionCN());
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getLeaderApproval())) {
			reviewEntity.setApprovalResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			reviewEntity.setApprovalResult(ApprovalResultEnum.REJECT.getCode());
		}
		reviewEntity.setApprovalTime(dfuYMd.format(cal.getTime()));
		
		dispatchManagementService.approve(entity, params);
		dispatchManagementService.updateLeaderApprovalInfo(entity);
		dispatchLeaderApprovalService.addEntity(reviewEntity);
		
		return new ResultObj();
	}
	/**
	 * 综合管理员同意
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/composeApproval")
	public @ResponseBody ResultObj composeApproval(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		entity.setUpdateDate(cal.getTime());
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		entity.setLeaderPersion(sysUserEntity.getLoginName());
		entity.setLeaderPersionCN(sysUserEntity.getName());
		entity.setLeaderTime(dfuYMd.format(cal.getTime()));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getLeaderComment()==null?"":entity.getLeaderComment());
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), "1");
		params.put("processStatus",DispatchApprovalStatusEnum.RESULTS.getId());
		params.put("result", ApprovalResultEnum.AGREE.getName());
		entity.setApprovalStatus(DispatchApprovalStatusEnum.RESULTS.getCode());
		entity.setCurrentStepUserList("");
			
		dispatchManagementService.approve(entity, params);
		dispatchManagementService.updateComposingApprovalInfo(entity);
		return new ResultObj();
	}
	
	
	@RequestMapping(value = "/searchDispatchInfoForFirstPage", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchDispatchInfoForFirstPage(HttpServletRequest request, @RequestBody Map<String, Object> params) throws ParseException {
		List<DispatchManagementEntity> returnList = new ArrayList<DispatchManagementEntity>();
		
		Page<DispatchManagementEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		if (page != null) {
			page.setPageSize(Integer.MAX_VALUE);
			page.addOrder(Sort.desc("C_ID"));
		}
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_DISPATCH_RELEASE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_APPROVAL_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, DispatchApprovalStatusEnum.RESULTS.getCode()));
		List<DispatchManagementEntity> dataList = dispatchManagementService.findByCondition(conditions, page);
		
		if (dataList == null || dataList.size() == 0) {
			ResultListObj resultObj = new ResultListObj();
			resultObj.setData(new ArrayList<DispatchManagementEntity>());
			resultObj.setDraw((Integer)params.get("draw"));
			resultObj.setRecordsTotal(0);
			return resultObj;
			
		}
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		if ("super".equals(sysUserEntity.getLoginName())) {
			returnList.addAll(dataList);
		} else {
			//所有用户
			List<SysUserEntity> useList = this.sysUserService.findAll();
			//登录用户接收的发文List
			List<DispatchManagementEntity> loginUsrDataList = new ArrayList<DispatchManagementEntity>();
			//取出登录用户可以接收的发文
			for (DispatchManagementEntity entity : dataList) {
				//发布状态 1全部挂网，2指定接收人
				String releaseStatus = entity.getReleaseStatus();
				String draftTime = entity.getDraftTime();
				entity.setDraftTime(dfuYMd.format(entity.getReleaseTime()));
				if ("1".equals(releaseStatus)) {
					loginUsrDataList.add(entity);
				} else {
					//指定接收人
					//接受人类型 1单位，2个人
					String recipientType = entity.getRecipientType();
					if ("1".equals(recipientType)) {
						String recipientId = entity.getRecipientId();
						if (recipientId != null) {
							recipientId = recipientId.replace("O", "");
						}
						if (recipientId.equals(sysUserEntity.getUnitId().toString())){
							loginUsrDataList.add(entity);
						}
					} else if ("2".equals(recipientType)) {
						if (entity.getRecipientId() != null) {
							String [] ids = entity.getRecipientId().split(",");
							for (String uids : ids) {
								uids = uids.replace("U", "");
								if (sysUserEntity.getId().toString().equals(uids)) {
									loginUsrDataList.add(entity);
								}
							}
						}
					}
				}
			}
			//取出登录用户反馈过的发文
			conditions.clear();
			conditions.add(new Condition("C_FEEDBACK_PERSION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getId().toString()));
			List<DispatchFeedbackEntity> feedbackDataList = dispatchFeedbackService.findByCondition(conditions, null);
			for (DispatchManagementEntity entity : loginUsrDataList) {
				Boolean isFeedBack = false;
				if (feedbackDataList != null && feedbackDataList.size() > 0) {
					for (DispatchFeedbackEntity feedBackEntity : feedbackDataList) {
						if (entity.getId().intValue() == feedBackEntity.getDispatchId().intValue()) {
							isFeedBack = true;
							break;
						}
					}
				}
				if (!isFeedBack){
					returnList.add(entity);
				}
			}
		}
		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (returnList != null) {
			resultObj.setData(returnList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal(returnList.size());
			}
		}
		return resultObj;
	}

	/**
	 * 反馈
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dispatchFeedBack")
	public @ResponseBody ResultObj dispatchFeedBack(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		
		
		Calendar cal = Calendar.getInstance();
		entity.setUpdateDate(cal.getTime());
		//获取登录人
		dispatchManagementService.updateFeedBackInfo(entity);
		
		DispatchManagementEntity dispatchEntity = dispatchManagementService.findById(entity.getId());

		DispatchFeedbackEntity feedBackEntity = new DispatchFeedbackEntity();
		feedBackEntity.setDispatchId(entity.getId());
		feedBackEntity.setFeedbackPersionId(entity.getFeedBackPersion());
		feedBackEntity.setFeedbackPersionName(entity.getFeedBackPersionName());
		feedBackEntity.setFeedBackFile(entity.getFeedBackFile());
		JSONArray documentJSONArray=JSONArray.fromObject(entity.getFeedBackFile());
		if (documentJSONArray.size() >0) {
			String feedbackName = "";
			for (int i = 0; i < documentJSONArray.size(); i++) {
				JSONObject josnObject= (JSONObject) documentJSONArray.get(i);
				feedbackName += String.valueOf(josnObject.get("name"));
				if (i != documentJSONArray.size() -1) {
					feedbackName += ",";
				}
			}
			feedBackEntity.setFeedBackFileName(feedbackName);
		}
		feedBackEntity.setFeedbackComment(entity.getFeedBackComment());
		feedBackEntity.setFeedbackTime(entity.getFeedBackTime());
		
		dispatchFeedbackService.addEntity(feedBackEntity);

		MessageCenterEntity messageEntity =new MessageCenterEntity();
		messageEntity.setTitle("发文: " + dispatchEntity.getTitle() + "已反馈");
		messageEntity.setSendTime(new Date());
		messageEntity.setContext("发文: " + dispatchEntity.getTitle() + "已反馈");
		messageEntity.setReceivePerson(dispatchEntity.getDrafterId());
		messageEntity.setSendPerson(dispatchEntity.getDrafterId());
		messageEntity.setType("private");
		messageCenterService.addMessage(messageEntity);
		
		return new ResultObj();
	}
	
	
	/**
	 *	跳转到发布页面
	 * @param request
	 * @param id 修改对象id
	 * @return
	 */
	@RequestMapping("/gotoSendDispatchPage/{id}")
	public ModelAndView gotoSendDispatchPage(HttpServletRequest request, @PathVariable Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		
		DispatchManagementEntity entity = dispatchManagementService.findById(id);

		//拟稿人
		model.put("drafterName", entity.getDrafterName());

		//拟稿时间
		model.put("drafterTime", entity.getDraftTime());

		/*
		 * 发文字号
		 */
		//发文字号文号
		String dispatchNumPreValue = entity.getDispatchNameSymbolId();
		model.put("dispatchNumPreVal", dispatchNumPreValue);
		//发文文号年号
		String dispatchNumYear = entity.getDispatchNameYear();
		model.put("dispatchNumYearVal", dispatchNumYear);
		//发文文号序号
		String dispatchNumNo = entity.getDispatchNameNum();
		model.put("dispatchNumNo", dispatchNumNo);
		
		//文号前缀
		ComboboxVO dispatchNumPreCombo = new ComboboxVO();
		Map<String, SysDictionaryVO> dispatchNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
		for(String key : dispatchNumPreMap.keySet()){
			SysDictionaryVO defualtTypeVO = dispatchNumPreMap.get(key);
			dispatchNumPreCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
		}
		model.put("dispatchNumPreComboBox", JsonUtil.toJson(dispatchNumPreCombo.getOptions()));
		//发文文号时间序号信息
		List<Map<String,String>> dispatchNumberInfoList = this.dispatchManagementService.getDispatchNumberInfo();
		//文号 时间
		ComboboxVO dispatchNumYearComboBox = new ComboboxVO();
		int startValue = 1990;
		if (dispatchNumberInfoList != null && dispatchNumberInfoList.size() > 0) {
			Map<String,String> dataMap =  dispatchNumberInfoList.get(0);
			if (dataMap != null && dataMap.get("C_DISPATCH_NUM_START_TIME") != null) {
				try {
					startValue = Integer.parseInt(dataMap.get("C_DISPATCH_NUM_START_TIME").toString());
				} catch (NumberFormatException e) {
					startValue = 1990;
				}
			}
		}
		for (int i=0; i<100; i++ ) {
			int optionVal =  startValue + i;
			dispatchNumYearComboBox.addOption("" + optionVal , "" + optionVal);
		}
		
		model.put("dispatchNumYearComboBox", JsonUtil.toJson(dispatchNumYearComboBox.getOptions()));
		
		//发文类型
		Map<String, SysDictionaryVO> dispatchTypeMap = DictionaryUtil.getDictionaries("DISPATCH_FILE_TYPE");
		ComboboxVO dispatchTypeComboBox = new ComboboxVO();
		for(String key : dispatchTypeMap.keySet()){
			SysDictionaryVO dispatchTypeVO = dispatchTypeMap.get(key);
			dispatchTypeComboBox.addOption(dispatchTypeVO.getCode(), dispatchTypeVO.getName());
		}
		model.put("dispatchTypeComboBox",  JsonUtil.toJson(dispatchTypeComboBox.getOptions()));
		
		//发文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {1,3,4,5,6,10,11};
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("C_DELETE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "N"));
		conditions.add(new Condition("C_ORGANIZATION", FieldTypeEnum.INT, MatchTypeEnum.IN, organizationArray));
		List<SysUnitEntity> departmentList = this.sysUnitService.findByCondition(conditions, null);

		//部门列表
		ComboboxVO departmentComboBox = new ComboboxVO();
		if (departmentList != null && departmentList.size() > 0) {
			for (SysUnitEntity unitEntiy : departmentList) {
				departmentComboBox.addOption(unitEntiy.getId().toString(), unitEntiy.getName());
			}
		}
		model.put("departmentComboBox", JsonUtil.toJson(departmentComboBox.getOptions()));
		
		//缓急程度
		ComboboxVO urgencyComboBox = new ComboboxVO();
		for (UrgencyEnum urgencyEnum : UrgencyEnum.values()) {
			urgencyComboBox.addOption(urgencyEnum.getCode(),  urgencyEnum.getName());
		}
		model.put("urgencyComboBox", JsonUtil.toJson(urgencyComboBox.getOptions()));
		
		//密级
		ComboboxVO denseComboBox = new ComboboxVO();
		for (DenseEnum denseEnum : DenseEnum.values()) {
			denseComboBox.addOption(denseEnum.getCode(),  denseEnum.getName());
		}
		model.put("denseComboBox", JsonUtil.toJson(denseComboBox.getOptions()));
		
		//反馈提醒时间
		model.put("remindTime", entity.getFeedBackRemindTime());
		
		//接收人
		List<TreeEntity> recipientTreeList = dispatchManagementService.getRecipientTree();
		model.put("recipientTree",JsonUtil.toJson(recipientTreeList));
		
		if (entity != null) {
			model.put("dispatchManagementEntity", entity);
		} else {
			model.put("dispatchManagementEntity", new DispatchManagementEntity());
		}
		
		return this.createModelAndView("/OAManagement/dispatchManagement/release", model);
	}
	
	/**
	 * 发布发文
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/releaseDispatch")
	public @ResponseBody ResultObj releaseDispatch(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		entity.setUpdateDate(new Date());
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		try {
			entity.setReleaseTime(dfuYMd.parse(dfuYMd.format(new Date())));
		} catch (ParseException e) {
			entity.setReleaseTime(new Date());
		}
		dispatchManagementService.releaseDispatch(entity);
		//发布后检修、采购、安全培训、技术培训发消息
		DispatchManagementEntity dispatchManagementEntity = dispatchManagementService.findById(entity.getId());
		String type = dispatchManagementEntity.getType();
//		if ("5".equals(type) || "6".equals(type) || "7".equals(type) || "8".equals(type)) {
//			List<String> userIdList = new ArrayList<String>();
//			Map<String, String> userNameMap = new HashMap<String, String>();
//			String recipientType = dispatchManagementEntity.getRecipientType();
//			if ("1".equals(recipientType)) {
//				String recipientId = entity.getRecipientId();
//				if (recipientId != null) {
//					recipientId = recipientId.replace("O", "");
//				}
//				int recipientIdIntVal = 0;
//				try {
//					recipientIdIntVal = Integer.parseInt(recipientId);
//				} catch(NumberFormatException e) {
//					
//				}
//				List<Condition> conditions = new ArrayList<Condition>();
//				conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//				conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ, recipientIdIntVal));
//				List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
//				if (userList != null && userList.size() > 0) {
//					for (SysUserEntity user : userList) {
//						userIdList.add(user.getId().toString());
//						userNameMap.put(user.getId().toString(), user.getName());
//					}
//				}
//			
//			} else if ("2".equals(recipientType)) {
//				if (entity.getRecipientId() != null) {
//					String [] ids = entity.getRecipientId().split(",");
//					String [] names = entity.getRecipientName().split(",");
//					for (int i = 0;i< ids.length; i++) {
//						String id = ids[i];
//						if (ids[i] != null) {
//							id = id.replace("U", "");
//						}
//						userIdList.add(id);
//						userNameMap.put(ids[i], names[i]);
//					}
//					
//				}
//			}
//
//			for (String uid : userIdList) {
//				MessageCenterEntity messageEntity =new MessageCenterEntity();
//				messageEntity.setTitle(entity.getTitle() + "-" + entity.getTypeCN());
//				messageEntity.setSendTime(new Date());
//				messageEntity.setReceivePerson(uid);
//				messageEntity.setSendPerson(uid);
//				messageEntity.setContext(entity.getTitle() + "-" + entity.getTypeCN() + "已发布");
//				messageEntity.setType("private");
//				messageCenterService.addMessage(messageEntity); 
//			}
//		}
		
		return new ResultObj();
	}
	
	/**
	 * 再提交
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reSubmit")
	public @ResponseBody ResultObj reSubmit(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		entity.setUpdateDate(new Date());
		entity.setApprovalStatus("2");
		entity.setDispatchName(entity.getDispatchNameSymbolName() + entity.getDispatchNameYear() + entity.getDispatchNameNum());
		entity.setCurrentStepUserList(entity.getReviewSelectedPersionCN());
		
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.DISPATCH_MANAGEMENT_KEY.getName()));
//		
//		List<DefinitionEntity> defList= definitionService.findByCondition(conditions, null);
//		
//		if(!defList.isEmpty()){
//			String modelId= defList.get(0).getModelId();
//			//审批页面点击签发按钮的时候，把下一步的人查询出来	
//			//SysUserEntity userEntity = RequestContext.get().getUser();
//			SysUserEntity starter= null;
//			if(!RequestContext.get().isDeveloperMode()){
//				starter = RequestContext.get().getUser();
//			}
//			List<SysUserEntity> userList= nodeConfigService.getFirstNodeTransactor(modelId,"",starter);
//			String selectUseIds = "";
//			if (userList != null && userList.size() > 0) {
//				for (int i= 0;i<userList.size(); i++) {
//					selectUseIds += userList.get(i).getLoginName();
//					if (i != userList.size()-1) {
//						selectUseIds += ",";
//					}
//				}
//			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), entity.getReviewSelectedPersion());
			params.put(ExamMarkEnum.COMMENT.getCode(), entity.getReviewComment()==null?"":entity.getReviewComment());
			params.put("processStatus",DispatchApprovalStatusEnum.REVIEW.getId());
			params.put("result",DispatchApprovalStatusEnum.REVIEW.getName());
			
			dispatchManagementService.approve(entity, params);
			
//			this.dispatchManagementService.submit(entity.getId().toString(), userList);
			dispatchManagementService.updateEntity(entity);
//		}
		
		return new ResultObj();
	}
	/**
	 * @Description:   人员选择带回
	 * @author         wjw 
	 * @Date           2017年4月11日 下午5:30:09 
	 * @throws         Exception
	 */
	@RequestMapping("/userSelect")
	public ModelAndView userSelect(HttpServletRequest request){
		List<SysDutiesEntity> list=sysDutiesService.findAll();
		ComboboxVO deties = new ComboboxVO();
		for (SysDutiesEntity t : list) {
			deties.addOption(t.getId().toString(), t.getName());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sysDuties", JsonUtil.toJson(deties.getOptions()));
		String singleSelect = request.getParameter("singleSelect");
		resultMap.put("singleSelect", JsonUtil.toJson(singleSelect));
		
		return new ModelAndView("/OAManagement/dispatchManagement/userSelect",resultMap);
	}
	
	@RequestMapping(value = "/dispatchDiscarded")
	public @ResponseBody ResultObj dispatchDiscarded(@RequestBody DispatchManagementEntity entity, HttpServletRequest request) {
		
		DispatchManagementEntity dispatchEntity = this.dispatchManagementService.findById(entity.getId());
		dispatchEntity.setApprovalStatus(DispatchApprovalStatusEnum.DISCARDED.getCode());
		dispatchEntity.setCurrentStepUserList("");
		
		dispatchManagementService.dispatchDiscarded(entity.getId().toString(), entity.getProcInstId());

		dispatchManagementService.updateEntity(dispatchEntity);
		
		return new ResultObj();
	}
	
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param id 查看对象id
	 * @return
	 */
	@RequestMapping("/gotoFeedbackViewPage/{id}")
	public ModelAndView gotoFeedbackViewPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		DispatchFeedbackEntity entity= this.dispatchFeedbackService.findById(id);
		
		model.put("dispatchFeedbackEntity", entity);

		return this.createModelAndView("/OAManagement/dispatchManagement/feedBackView", model);
	}

	
	/**
	 * 接收人处理时查询领导审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPersonLeaderByGroups/{taskId}")
	public ModelAndView submitPersonLeaderByGroups(HttpServletRequest request,@PathVariable String taskId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<SysUserEntity> userList = dispatchManagementService.getPerson4SignLeader(taskId);
	
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
	}
	/**
	 * @Description:   查看的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/showValidate/{id}")
	public @ResponseBody ResultObj showValidate(HttpServletRequest request ,@PathVariable Long id) {
		return dispatchManagementService.showValidate(id);
	}
}