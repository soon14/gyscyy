package com.aptech.business.OAManagement.receiptManagement.web;

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
import com.aptech.business.OAManagement.dispatchManagement.domain.TreeEntity;
import com.aptech.business.OAManagement.dispatchManagement.domain.UrgencyEnum;
import com.aptech.business.OAManagement.dispatchManagement.util.SortUtil;
import com.aptech.business.OAManagement.jointlySign.domain.ReceiptJointlySignEntity;
import com.aptech.business.OAManagement.jointlySign.service.ReceiptJointlySignService;
import com.aptech.business.OAManagement.leaderApproval.domain.ReceiptLeaderApprovalEntity;
import com.aptech.business.OAManagement.leaderApproval.service.ReceiptLeaderApprovalService;
import com.aptech.business.OAManagement.outDepartment.domain.ReceiptOutDepartmentEntity;
import com.aptech.business.OAManagement.outDepartment.service.ReceiptOutDepartmentService;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptApprovalStatusEnum;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptManagementEntity;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptProcessEnum;
import com.aptech.business.OAManagement.receiptManagement.domain.ReceiptReviewTypeEnum;
import com.aptech.business.OAManagement.receiptManagement.service.ReceiptManagementService;
import com.aptech.business.OAManagement.receivingUnit.domain.ReceiptReceivingUnitEntity;
import com.aptech.business.OAManagement.receivingUnit.service.ReceiptReceivingUnitService;
import com.aptech.business.OAManagement.review.domain.ReceiptReviewEntity;
import com.aptech.business.OAManagement.review.service.ReceiptReviewService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.excel.ExcelUtil;
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
import com.aptech.common.system.role.domain.SysRoleEntity;
import com.aptech.common.system.role.service.SysRoleService;
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
@RequestMapping("/receiptManagement") 
public class ReceiptManagementController extends BaseController<ReceiptManagementEntity> {
    @Autowired
	private ReceiptManagementService receiptManagementService;

	@Override
	public IBaseEntityOperation<ReceiptManagementEntity> getService() {
		return receiptManagementService;
	}
	@Autowired
	private SysUnitService sysUnitService;
	

	/**
	 * 会签服务对象
	 */
	@Autowired
	private ReceiptJointlySignService receiptJointlySignService;
	
	/**
	 * 领导审查服务对象
	 */
	@Autowired
	private ReceiptLeaderApprovalService receiptLeaderApprovalService;
	
	/**
	 * 审查服务对象
	 */
	@Autowired
	private ReceiptReviewService receiptReviewService;
	/**
	 * 接受单位服务对象
	 */
	@Autowired
	private ReceiptReceivingUnitService receiptReceivingUnitService;
	
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
	private SysRoleService roleService;
	@Autowired
	ReceiptOutDepartmentService receiptOutDepartmentService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
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

		//来文单位
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {3,4,5,6,10,11};
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
		model.put("receivingUnit", JsonUtil.toJson(department.getOptions()));
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		model.put("loginUser", sysUserEntity);
		
		return this.createModelAndView("OAManagement/receiptManagement/list", model);
	}
	
	
	/**
	 * 条件查询
	 * @param request
	 * @param params 查询条件参数map
	 * @return
	 */
	@RequestMapping(value = "/searchData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<ReceiptManagementEntity> page = PageUtil.getPage(params);
		List<Sort> orders  = OrmUtil.changeMapToOrders(params);
		List<Sort> newOrders = new ArrayList<Sort>();
		for (Sort sort : orders) {
			if ("emergencyLevelCN".equals(sort.getField())) {
				sort.setField("emergencyLevel");
			}
			if ("approvalStatusCN".equals(sort.getField())) {
				sort.setField("approvalStatus");
			}
			if ("showTime".equals(sort.getField())) {
				sort.setField("createDate");
			}
			newOrders.add(sort);
		}
		page.setOrders(newOrders);
		if (page != null) {
//			page.setPageSize(Integer.MAX_VALUE);
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
		List<String> userNames = new ArrayList<String>();
		for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
			SysUserEntity sysUserEntity = sysUserService.findById(userUnitRelEntity.getUserId());
			userNames.add(sysUserEntity.getName());
		}
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		String userName = sysUserEntity.getName();
		List<ReceiptManagementEntity> dataList = new ArrayList<ReceiptManagementEntity>();
		if(userNames.contains(userName)){
			dataList = receiptManagementService.findByCondition(conditions, page);
		}else{
			conditions.add(new Condition("C_PUBLISHER_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userName));
			dataList = receiptManagementService.findByCondition(conditions, page);
		}

		
		ResultListObj resultObj = new ResultListObj();
		resultObj.setDraw((Integer)params.get("draw"));
		if (dataList != null) {
			resultObj.setData(dataList);
			resultObj.setRecordsTotal(dataList.size());
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
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
		//发布人
		model.put("publisherName", sysUserEntity.getName());
		model.put("publisherId", sysUserEntity.getId().toString());
		List<TreeEntity> publisherList = this.receiptManagementService.getPublisherTree();
		model.put("publisherTree",JsonUtil.toJson(publisherList));
		
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		//拟稿时间
		model.put("drafterTime", dfuYMd.format(cal.getTime()));
		
		//发起人所属单位
		SysUnitEntity unitEntity = sysUnitService.findById(sysUserEntity.getUnitId());
		model.put("loginUserUnitName", unitEntity.getName());
		
		//操作类型
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		ComboboxVO operateTypeCombo = new ComboboxVO();
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			operateTypeCombo.addOption(operateTypeVO.getCode(), operateTypeVO.getName());
		}
		model.put("operateTypeComboBox", JsonUtil.toJson(operateTypeCombo.getOptions()));
//		/*
//		 * 来文字号
//		 */
//		//文号前缀
//		ComboboxVO receiptNumPreCombo = new ComboboxVO();
//		Map<String, SysDictionaryVO> receiptNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
//		for(String key : receiptNumPreMap.keySet()){
//			SysDictionaryVO receiptNumPreVO = receiptNumPreMap.get(key);
//			receiptNumPreCombo.addOption(receiptNumPreVO.getCode(), receiptNumPreVO.getName());
//		}
//		model.put("receiptNumPreComboBox", JsonUtil.toJson(receiptNumPreCombo.getOptions()));
//		//收文文号时间序号信息
//		List<Map<String,String>> receiptNumberInfoList = this.receiptManagementService.getReceiptNumberInfo();
//		//文号 时间
//		ComboboxVO receiptNumYearComboBox = new ComboboxVO();
//		int startValue = 2018;
//		if (receiptNumberInfoList != null && receiptNumberInfoList.size() > 0) {
//			Map<String,String> dataMap =  receiptNumberInfoList.get(0);
//			if (dataMap != null && dataMap.get("C_RECEIPT_NUM_START_TIME") != null) {
//				try {
//					startValue = Integer.parseInt(dataMap.get("C_RECEIPT_NUM_START_TIME").toString());
//				} catch (NumberFormatException e) {
//					startValue = 2018;
//				}
//			}
//		}
//		for (int i=0; i<100; i++ ) {
//			int optionVal =  startValue + i;
//			receiptNumYearComboBox.addOption("" + optionVal , "" + optionVal);
//		}
//		
//		model.put("receiptNumYearComboBox", JsonUtil.toJson(receiptNumYearComboBox.getOptions()));
//		//文号序号
//		String receiptNumNo = "0000";
//		if (receiptNumberInfoList != null && receiptNumberInfoList.size() > 0) {
//			for (Map<String,String> dataMap : receiptNumberInfoList) {
//				if (dataMap.get("C_RECEIPT_NUMBER") != null) {
//					receiptNumNo = dataMap.get("C_RECEIPT_NUMBER").toString();
//				}
//			}
//		}
//		model.put("receiptNumNo", receiptNumNo);
		
		//收文类型 
		Map<String, SysDictionaryVO> receiptTypeMap = DictionaryUtil.getDictionaries("RECEIPT_TYPE");
		ComboboxVO receiptTypeComboBox = new ComboboxVO();
		for(String key : receiptTypeMap.keySet()){
			SysDictionaryVO receiptTypeVO = receiptTypeMap.get(key);
			receiptTypeComboBox.addOption(receiptTypeVO.getCode(), receiptTypeVO.getName());
		}
		model.put("receiptTypeComboBox",  JsonUtil.toJson(receiptTypeComboBox.getOptions()));

		//收文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {3,4,5,6,10,11};
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

		return this.createModelAndView("OAManagement/receiptManagement/add", model);
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
	public @ResponseBody ResultObj addEntity(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());
		
		this.receiptManagementService.addEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.RECEIPT.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
		
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
		
		ReceiptManagementEntity entity = receiptManagementService.findById(id);

		//发布人
		model.put("publisherId", entity.getPublisherId());
		model.put("publisherName", entity.getPublisherName());
		List<TreeEntity> publisherList = this.receiptManagementService.getPublisherTree();
		model.put("publisherTree",JsonUtil.toJson(publisherList));

		//拟稿时间
		model.put("drafterTime", entity.getDraftTime());
		//发起人所属单位
		model.put("loginUserUnitName", entity.getUnitName());
		//操作类型
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		ComboboxVO operateTypeCombo = new ComboboxVO();
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			operateTypeCombo.addOption(operateTypeVO.getCode(), operateTypeVO.getName());
		}
		model.put("operateTypeComboBox", JsonUtil.toJson(operateTypeCombo.getOptions()));
//		/*
//		 * 收文字号
//		 */
//		//收文字号文号
//		String receiptNumPreValue = entity.getReceiptNameSymbolId();
//		model.put("receiptNumPreVal", receiptNumPreValue);
//		//收文文号年号
//		String receiptNumYear = entity.getReceiptNameYear();
//		model.put("receiptNumYearVal", receiptNumYear);
//		//收文文号序号
//		String receiptNumNo = entity.getReceiptNameNum();
//		model.put("receiptNumNo", receiptNumNo);
//		
//		//文号前缀
//		ComboboxVO receiptNumPreCombo = new ComboboxVO();
//		Map<String, SysDictionaryVO> receiptNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
//		for(String key : receiptNumPreMap.keySet()){
//			SysDictionaryVO defualtTypeVO = receiptNumPreMap.get(key);
//			receiptNumPreCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
//		}
//		model.put("receiptNumPreComboBox", JsonUtil.toJson(receiptNumPreCombo.getOptions()));
//		//收文文号时间序号信息
//		List<Map<String,String>> receiptNumberInfoList = this.receiptManagementService.getReceiptNumberInfo();
//		//文号 时间
//		ComboboxVO receiptNumYearComboBox = new ComboboxVO();
//		int startValue = 2018;
//		if (receiptNumberInfoList != null && receiptNumberInfoList.size() > 0) {
//			Map<String,String> dataMap =  receiptNumberInfoList.get(0);
//			if (dataMap != null && dataMap.get("C_RECEIPT_NUM_START_TIME") != null) {
//				try {
//					startValue = Integer.parseInt(dataMap.get("C_RECEIPT_NUM_START_TIME").toString());
//				} catch (NumberFormatException e) {
//					startValue = 2018;
//				}
//			}
//		}
//		for (int i=0; i<100; i++ ) {
//			int optionVal =  startValue + i;
//			receiptNumYearComboBox.addOption("" + optionVal , "" + optionVal);
//		}
//		
//		model.put("receiptNumYearComboBox", JsonUtil.toJson(receiptNumYearComboBox.getOptions()));
		
		//收文类型
		Map<String, SysDictionaryVO> receiptTypeMap = DictionaryUtil.getDictionaries("RECEIPT_TYPE");
		ComboboxVO receiptTypeComboBox = new ComboboxVO();
		for(String key : receiptTypeMap.keySet()){
			SysDictionaryVO receiptTypeVO = receiptTypeMap.get(key);
			receiptTypeComboBox.addOption(receiptTypeVO.getCode(), receiptTypeVO.getName());
		}
		model.put("receiptTypeComboBox",  JsonUtil.toJson(receiptTypeComboBox.getOptions()));
		
		//收文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {3,4,5,6,10,11};
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
		
		if (entity != null) {
			model.put("receiptManagementEntity", entity);
		} else {
			model.put("receiptManagementEntity", new ReceiptManagementEntity());
		}
		
		return this.createModelAndView("OAManagement/receiptManagement/edit", model);
	}
	
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param id 查看对象id
	 * @return
	 */
	@RequestMapping("/gotoViewPage/{id}")
	public ModelAndView gotoViewPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		ReceiptManagementEntity entity=receiptManagementService.findById(id);
		//操作类型
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			if (operateTypeVO.getCode().equals(entity.getOperateType())) {
				entity.setOperateTypeCN(operateTypeVO.getName());
				break;
			}
		}
		
		model.put("receiptManagementEntity", entity);
	
		return this.createModelAndView("OAManagement/receiptManagement/view", model);
	}
	
	/**
	 * 修改保存
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editEntity")
	public @ResponseBody
	ResultObj update(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		entity.setUpdateDate(new Date());

		receiptManagementService.updateEntity(entity);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.RECEIPT.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
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
		receiptManagementService.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.RECEIPT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (String id : ids) {
			Long longId = new Long(id);
			ReceiptManagementEntity entity = receiptManagementService.findById(longId);
			if (entity != null) {
				receiptManagementService.deleteEntity(longId);
				operateLogService.addOperateLog(userEntity, OperateUserModuleEnum.RECEIPT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
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
		
		Page<ReceiptManagementEntity> page=new Page<ReceiptManagementEntity>();
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		if (page != null) {
			page.setPageSize(Integer.MAX_VALUE);
			page.addOrder(Sort.desc("C_ID"));
		}
		
		List<ReceiptManagementEntity> dataList = receiptManagementService.findByCondition(conditions, page);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		resultMap.put("dataList", dataList);

		ExcelUtil.export(req, res, "签报管理报表模板.xlsx","签报管理.xlsx", resultMap);


	}
	
	/**
	 * 查询审查信息
	 */
	@RequestMapping(value = "/searchReviewData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReviewData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<ReceiptReviewEntity> dataList = new ArrayList<ReceiptReviewEntity>();
		//部门领导审核
		Page<ReceiptReviewEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<ReceiptReviewEntity> reviewDataList = this.receiptReviewService.findByCondition(conditions, page);
		if (reviewDataList != null && reviewDataList.size() > 0) {
			for (ReceiptReviewEntity entity : reviewDataList) {
				entity.setReviewType(ReceiptReviewTypeEnum.REVIEW.getCode());
				dataList.add(entity);
			}
		}
		
		//接收人处理
		Page<ReceiptReceivingUnitEntity> page1 = PageUtil.getPage(params);
		List<Condition> conditions1 = OrmUtil.changeMapToCondition(params);
		List<ReceiptReceivingUnitEntity> receivingDataList = this.receiptReceivingUnitService.findByCondition(conditions1, page1);
		if (receivingDataList != null && receivingDataList.size() > 0) {
			for (ReceiptReceivingUnitEntity entity : receivingDataList) {
				ReceiptReviewEntity reviewEntiy = new ReceiptReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setReceiptId(entity.getReceiptId());
				reviewEntiy.setReviewPersionId(entity.getHandlePersionId());
				reviewEntiy.setReviewPersionName(entity.getHandlePersionName());
				reviewEntiy.setReviewResult(entity.getHandleResult());
				reviewEntiy.setReviewTime(entity.getHandleTime());
				reviewEntiy.setReviewComment(entity.getHandleComment());
				reviewEntiy.setReviewType(ReceiptReviewTypeEnum.RECEIVING_HANDLE.getCode());
				dataList.add(reviewEntiy);
			}
		}		
		
		//会签意见
		Page<ReceiptJointlySignEntity> page4 = PageUtil.getPage(params);
		List<Condition> conditions4 = OrmUtil.changeMapToCondition(params);
		List<ReceiptJointlySignEntity> jointlySignDataList = this.receiptJointlySignService.findByCondition(conditions4, page4);
		if (jointlySignDataList != null && jointlySignDataList.size() > 0) {
			for (ReceiptJointlySignEntity entity : jointlySignDataList) {
				ReceiptReviewEntity reviewEntiy = new ReceiptReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setReceiptId(entity.getReceiptId());
				reviewEntiy.setReviewPersionId(entity.getSignPersionId());
				reviewEntiy.setReviewPersionName(entity.getSignPersionName());
				reviewEntiy.setReviewResult(entity.getSignResult());
				reviewEntiy.setReviewTime(entity.getSignTime());
				reviewEntiy.setReviewComment(entity.getSignComment());
				String type = entity.getType();
				if(type!=null && !type.equals("")){
					if(type.equals("3")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.JOINTLY_SIGN.getCode());
					}else if(type.equals("1")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.REVIEW.getCode());
					}else if(type.equals("2")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.RECEIVING_HANDLE.getCode());
					}
				}
				dataList.add(reviewEntiy);
			}
		}
		//外部部门会签
		Page<ReceiptOutDepartmentEntity> page6 = PageUtil.getPage(params);
		List<Condition> conditions6 = OrmUtil.changeMapToCondition(params);
		List<ReceiptOutDepartmentEntity> outSignDataList = this.receiptOutDepartmentService.findByCondition(conditions6, page6);
		if (outSignDataList != null && outSignDataList.size() > 0) {
			for (ReceiptOutDepartmentEntity entity : outSignDataList) {
				ReceiptReviewEntity reviewEntiy = new ReceiptReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setReceiptId(entity.getReceiptId());
				reviewEntiy.setReviewPersionId(entity.getPersionId());
				reviewEntiy.setReviewPersionName(entity.getPersionName());
				reviewEntiy.setReviewResult(entity.getResult());
				reviewEntiy.setReviewTime(entity.getTime());
				reviewEntiy.setReviewComment(entity.getComment());
				reviewEntiy.setUnitId(entity.getUnitId());
				reviewEntiy.setUnitName(entity.getUnitName());
				String type = entity.getType();
				if(type!=null & !type.equals("")){
					if(type.equals("3")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.OUT_DEPARTMENT.getCode());
					}else if(type.equals("1")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.PRODUCTION_UNIT_JOINLTY_SING.getCode());
					}else if(type.equals("2")){
						reviewEntiy.setReviewType(ReceiptReviewTypeEnum.DEPARTMETNT_INSIDE_JOINLTY_SING.getCode());
					}
				}
				dataList.add(reviewEntiy);
			}
		}
				
		//领导审核意见
		Page<ReceiptLeaderApprovalEntity> page2 = PageUtil.getPage(params);
		List<Condition> conditions2 = OrmUtil.changeMapToCondition(params);
		List<ReceiptLeaderApprovalEntity> leaderDataList = this.receiptLeaderApprovalService.findByCondition(conditions2, page2);
		if (leaderDataList != null && leaderDataList.size() > 0) {
			for (ReceiptLeaderApprovalEntity entity : leaderDataList) {
				ReceiptReviewEntity reviewEntiy = new ReceiptReviewEntity();
				reviewEntiy.setId(entity.getId());
				reviewEntiy.setReceiptId(entity.getReceiptId());
				reviewEntiy.setReviewPersionId(entity.getApprovalPersionId());
				reviewEntiy.setReviewPersionName(entity.getApprovalPersionName());
				reviewEntiy.setReviewResult(entity.getApprovalResult());
				reviewEntiy.setReviewTime(entity.getApprovalTime());
				reviewEntiy.setReviewComment(entity.getApprovalComment());
				reviewEntiy.setReviewType(ReceiptReviewTypeEnum.LEADER.getCode());
				dataList.add(reviewEntiy);
			}
		}
		
		Collections.sort(dataList, new SortUtil () {
			@Override
			public int compare(Object obj1, Object obj2) {  
				ReceiptReviewEntity entity1 = (ReceiptReviewEntity)obj1;
				ReceiptReviewEntity entity2 = (ReceiptReviewEntity)obj2;
				DateFormatUtil dfuYMdHm = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
				try {
					 return dfuYMdHm.parse(entity1.getReviewTime()).compareTo(dfuYMdHm.parse(entity2.getReviewTime()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 0; 
			}
		});
		
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
	 * 查询接受单位处理信息
	 * @return
	 */
	@RequestMapping(value = "/searchReceivingUnitData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReceivingUnitData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<ReceiptReceivingUnitEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<ReceiptReceivingUnitEntity> dataList = this.receiptReceivingUnitService.findByCondition(conditions, page);
		
		
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
	 * 查询会签信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/searchJointlySingData", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchJointlySingData(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		Page<ReceiptJointlySignEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<ReceiptJointlySignEntity> dataList = this.receiptJointlySignService.findByCondition(conditions, page);
		
		
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
		Page<ReceiptLeaderApprovalEntity> page = PageUtil.getPage(params);
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		
		List<ReceiptLeaderApprovalEntity> dataList = this.receiptLeaderApprovalService.findByCondition(conditions, page);
		
		
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.RECEIPT_MANAGEMENT_KEY.getName()));
		
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
				this.receiptManagementService.submit(id.toString(), userList);
				
				String userName = "";
				for (int i=0 ;i<userList.size(); i++) {
					SysUserEntity user = userList.get(i);
					userName += user.getName();
					if (i != userList.size()-1) {
						userName += ",";
					}
				}
				ReceiptManagementEntity entity = this.receiptManagementService.findById(id);
				entity.setCurrentStepUserList(userName);
				this.receiptManagementService.updateEntity(entity);
			}else {
				resultObj.setResult("noPerson");
			}
		} 
		return resultObj;
	}
	
	/**
	 * 追加并提交审批
	 * @param entity 追加的收文对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAndApprovalSubmit")
	public @ResponseBody ResultObj addAndApprovalSubmit(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		entity.setCreateDate(cal.getTime());

		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.RECEIPT_MANAGEMENT_KEY.getName()));
		
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
				this.receiptManagementService.addEntity(entity);
				
				this.receiptManagementService.submit(entity.getId().toString(), userList);
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
		ReceiptManagementEntity entity=receiptManagementService.findById(id);

		String receiptType = entity.getType();
		Map<String, SysDictionaryVO> receiptTypeMap = DictionaryUtil.getDictionaries("RECEIPT_TYPE");
		for(String key : receiptTypeMap.keySet()){
			SysDictionaryVO receiptTypeVO = receiptTypeMap.get(key);
			if (receiptTypeVO.getCode().equals(receiptType)){
				entity.setTypeCN(receiptTypeVO.getName());
				break;
			}
		}
		//操作类型
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			if (operateTypeVO.getCode().equals(entity.getOperateType())) {
				entity.setOperateTypeCN(operateTypeVO.getName());
				break;
			}
		}
		
		model.put("receiptManagementEntity", entity);
		String approvalStatus = entity.getApprovalStatus();
		
		setResubmitInfo(model, entity.getId());
		
		if(ReceiptApprovalStatusEnum.REVIEW.getCode().equals(approvalStatus)) {
			//待部门负责人审核2
			return this.createModelAndView("OAManagement/receiptManagement/review", model);
		} else if(ReceiptApprovalStatusEnum.RECEIVING_UNIT_HANDLE.getCode().equals(approvalStatus)) {
			//待接收人处理
			return this.createModelAndView("OAManagement/receiptManagement/receivingPersion", model);
		} else if(ReceiptApprovalStatusEnum.JOINLTY_SING.getCode().equals(approvalStatus)) {
			//待会签
			return this.createModelAndView("OAManagement/receiptManagement/JoinltySign", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getCode().equals(approvalStatus)) {
			//不需要会签时，待领导审核
			return this.createModelAndView("OAManagement/receiptManagement/leaderApproval", model);
		} else if(ReceiptApprovalStatusEnum.REVIEW_REJECT.getCode().equals(approvalStatus)) {
			//部门负责人驳回
			return this.createModelAndView("OAManagement/receiptManagement/reSubmit", model);
		} else if(ReceiptApprovalStatusEnum.RECEIVING_REJECT_START.getCode().equals(approvalStatus)) {
			//接收人驳回发起人
			return this.createModelAndView("OAManagement/receiptManagement/reSubmit", model);
		} else if(ReceiptApprovalStatusEnum.RECEIVING_REJECT_REVIEW.getCode().equals(approvalStatus)) {
			//接收人驳回部门领导再审批
			return this.createModelAndView("OAManagement/receiptManagement/review", model);
		} else if(ReceiptApprovalStatusEnum.RECEIVING_REJECT_JOINLTY.getCode().equals(approvalStatus)) {
			//接收人驳回发起人
			return this.createModelAndView("OAManagement/receiptManagement/reSubmit", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_SIGN.getCode().equals(approvalStatus)) {
			//需要会签 待领导审核
			return this.createModelAndView("OAManagement/receiptManagement/leaderApproval", model);
		} else if(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_REVIEW.getCode().equals(approvalStatus)) {
			//会签驳回部门领导审核
			return this.createModelAndView("OAManagement/receiptManagement/review", model);
		} else if(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_START.getCode().equals(approvalStatus)) {
			//会签驳回发起人
			return this.createModelAndView("OAManagement/receiptManagement/reSubmit", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getCode().equals(approvalStatus)) {
			//领导审核驳回发起人
			return this.createModelAndView("OAManagement/receiptManagement/reSubmit", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_SIGN.getCode().equals(approvalStatus)) {
			//领导审核驳回会签
			return this.createModelAndView("OAManagement/receiptManagement/JoinltySign", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode().equals(approvalStatus)) {
			//领导审核驳回部门领导审核
			return this.createModelAndView("OAManagement/receiptManagement/review", model);
		} else if(ReceiptApprovalStatusEnum.RESULTS.getCode().equals(approvalStatus)) {
			//领导审批同意
			return this.createModelAndView("OAManagement/receiptManagement/complete", model);
		} else if(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_RECEIVING.getCode().equals(approvalStatus)) {
			//会签驳回接收人
			return this.createModelAndView("OAManagement/receiptManagement/receivingPersion", model);
		} else if(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_RECEIVING.getCode().equals(approvalStatus)) {
			//领导审核驳回接收人
			return this.createModelAndView("OAManagement/receiptManagement/receivingPersion", model);
		} else if(ReceiptApprovalStatusEnum.OUT_DEPARTMENT.getCode().equals(approvalStatus)) {
			//领导审核驳回接收人
			return this.createModelAndView("OAManagement/receiptManagement/outDepartment", model);
		} else if(ReceiptApprovalStatusEnum.OUT_DEPARTMENT_REJECT_SIGN.getCode().equals(approvalStatus)) {
			//领导审核驳回接收人
			return this.createModelAndView("OAManagement/receiptManagement/JoinltySign", model);
		} else if(ReceiptApprovalStatusEnum.OUT_DEPARTMENT_LEADER.getCode().equals(approvalStatus)) {
			//领导审核驳回接收人
			return this.createModelAndView("OAManagement/receiptManagement/leaderApproval", model);
		} else if(ReceiptApprovalStatusEnum.PRODUCTION_UNITD_JOINLTY.getCode().equals(approvalStatus)){
			//生产部门会签
			return this.createModelAndView("OAManagement/receiptManagement/productionUnitJoinltySignApproval", model);
		} else if(ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY.getCode().equals(approvalStatus)){
			//部门内部会签
			return this.createModelAndView("OAManagement/receiptManagement/departMentInsideJoinltySignApproval", model);
		} else if(ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY_REJECT.getCode().equals(approvalStatus)){
			return this.createModelAndView("OAManagement/receiptManagement/receivingPersion", model);
		}
		return this.createModelAndView("OAManagement/receiptManagement/review", model);
	}
	
	private void setResubmitInfo(Map<String, Object> model, Long id){
		ReceiptManagementEntity entity = receiptManagementService.findById(id);

		//发布人
		model.put("publisherId", entity.getPublisherId());
		model.put("publisherName", entity.getPublisherName());
		List<TreeEntity> publisherList = this.receiptManagementService.getPublisherTree();
		model.put("publisherTree",JsonUtil.toJson(publisherList));
		
		//拟稿时间
		model.put("drafterTime", entity.getDraftTime());
		//发起人所属单位
		model.put("loginUserUnitName", entity.getUnitName());
		//操作类型
		Map<String, SysDictionaryVO> operateTypeMap = DictionaryUtil.getDictionaries("RECEIPT_OPERATE_TYPE");
		ComboboxVO operateTypeCombo = new ComboboxVO();
		for(String key : operateTypeMap.keySet()){
			SysDictionaryVO operateTypeVO = operateTypeMap.get(key);
			operateTypeCombo.addOption(operateTypeVO.getCode(), operateTypeVO.getName());
		}
		model.put("operateTypeComboBox", JsonUtil.toJson(operateTypeCombo.getOptions()));
//		/*
//		 * 收文字号
//		 */
//		//收文字号文号
//		String receiptNumPreValue = entity.getReceiptNameSymbolId();
//		model.put("receiptNumPreVal", receiptNumPreValue);
//		//收文文号年号
//		String receiptNumYear = entity.getReceiptNameYear();
//		model.put("receiptNumYearVal", receiptNumYear);
//		//收文文号序号
//		String receiptNumNo = entity.getReceiptNameNum();
//		model.put("receiptNumNo", receiptNumNo);
//		
//		//文号前缀
//		ComboboxVO receiptNumPreCombo = new ComboboxVO();
//		Map<String, SysDictionaryVO> receiptNumPreMap = DictionaryUtil.getDictionaries("DISPATHCH_NUMBER");
//		for(String key : receiptNumPreMap.keySet()){
//			SysDictionaryVO defualtTypeVO = receiptNumPreMap.get(key);
//			receiptNumPreCombo.addOption(defualtTypeVO.getCode(), defualtTypeVO.getName());
//		}
//		model.put("receiptNumPreComboBox", JsonUtil.toJson(receiptNumPreCombo.getOptions()));
//		//收文文号时间序号信息
//		List<Map<String,String>> receiptNumberInfoList = this.receiptManagementService.getReceiptNumberInfo();
//		//文号 时间
//		ComboboxVO receiptNumYearComboBox = new ComboboxVO();
//		int startValue = 2018;
//		if (receiptNumberInfoList != null && receiptNumberInfoList.size() > 0) {
//			Map<String,String> dataMap =  receiptNumberInfoList.get(0);
//			if (dataMap != null && dataMap.get("C_RECEIPT_NUM_START_TIME") != null) {
//				try {
//					startValue = Integer.parseInt(dataMap.get("C_RECEIPT_NUM_START_TIME").toString());
//				} catch (NumberFormatException e) {
//					startValue = 2018;
//				}
//			}
//		}
//		for (int i=0; i<100; i++ ) {
//			int optionVal =  startValue + i;
//			receiptNumYearComboBox.addOption("" + optionVal , "" + optionVal);
//		}
//		
//		model.put("receiptNumYearComboBox", JsonUtil.toJson(receiptNumYearComboBox.getOptions()));
		
		//收文类型
		Map<String, SysDictionaryVO> receiptTypeMap = DictionaryUtil.getDictionaries("RECEIPT_TYPE");
		ComboboxVO receiptTypeComboBox = new ComboboxVO();
		for(String key : receiptTypeMap.keySet()){
			SysDictionaryVO receiptTypeVO = receiptTypeMap.get(key);
			receiptTypeComboBox.addOption(receiptTypeVO.getCode(), receiptTypeVO.getName());
		}
		model.put("receiptTypeComboBox",  JsonUtil.toJson(receiptTypeComboBox.getOptions()));
		
		//收文部门
		List<Condition> conditions = new ArrayList<Condition>();
		int [] organizationArray = {3,4,5,6,10,11};
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
		
		if (entity != null) {
			model.put("receiptManagementEntity", entity);
		} else {
			model.put("receiptManagementEntity", new ReceiptManagementEntity());
		}
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
		
		List<SysUserEntity> userList= nodeConfigService.getNextNodeTransactor(taskId, ReceiptProcessEnum.REVIEW_LEADER_APPROVAL.getCode());
	
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
	}
	
	/**
	 * 会签：选择审核人
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
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
	}
	/**
	 * 接收人处理：选择审核人
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/submitPersonReceivingHandle/{taskId}")
	public ModelAndView submitPersonReceivingHandle(HttpServletRequest request,@PathVariable String taskId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<SysUserEntity> userList = nodeConfigService.getNextNodeTransactor(taskId, ReceiptProcessEnum.REVIEW_JOINLTY_SIGN.getCode());
		
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
	}
	
	
	/**
	 * 审查意见：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receiptReview")
	public @ResponseBody ResultObj receiptReview(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());
//		receiptEntity.setProcInstId(entity.getProcInstId());
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		
		receiptEntity.setUpdateDate(cal.getTime());
		//获取登录人v
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		receiptEntity.setReviewPersion(sysUserEntity.getLoginName());
		receiptEntity.setReviewPersionName(sysUserEntity.getName());
		receiptEntity.setReviewTime(dfuYMd.format(cal.getTime()));
		receiptEntity.setReview(entity.getReview());
		receiptEntity.setReviewComment(entity.getReviewComment());
		receiptEntity.setRecevingSelectedPersion(entity.getRecevingSelectedPersion());
		receiptEntity.setRecevingSelectedPersionName(entity.getRecevingSelectedPersionName());
		
		//reveiw记录表
		ReceiptReviewEntity reviewEntity = new ReceiptReviewEntity();
		reviewEntity.setReceiptId(receiptEntity.getId());
		reviewEntity.setReviewComment(receiptEntity.getReviewComment());
		reviewEntity.setReviewPersionId(receiptEntity.getReviewPersion());
		reviewEntity.setReviewPersionName(receiptEntity.getReviewPersionName());
		if (ReceiptProcessEnum.REVIEW_REJECT.getCode().equals(receiptEntity.getReview())) {
			reviewEntity.setReviewResult(ApprovalResultEnum.REJECT.getCode());
		} else {
			reviewEntity.setReviewResult(ApprovalResultEnum.AGREE.getCode());
		}
		reviewEntity.setReviewTime(dfuYMd.format(cal.getTime()));
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
		
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getReviewComment()==null?"":entity.getReviewComment());
		if (ReceiptProcessEnum.REVIEW_LEADER_APPROVAL.getCode().equals(entity.getReview())) {
			//领导审核
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getCode());
			receiptEntity.setCurrentStepUserList(entity.getLeaderSelectedPersionName());
//			receiptEntity.setLeaderSelectedPersion(entity.getUserList());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.REVIEW_LEADER_APPROVAL.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
		} else if (ReceiptProcessEnum.REVIEW_JOINLTY_SIGN.getCode().equals(entity.getReview())) {
			//会签
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.RECEIVING_UNIT_HANDLE.getCode());
//			receiptEntity.setJointlySelectedPersion(entity.getUserList());
			receiptEntity.setCurrentStepUserList(entity.getRecevingSelectedPersionName());
			

			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.REVIEW_JOINLTY_SIGN.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.RECEIVING_UNIT_HANDLE.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
		} else if (ReceiptProcessEnum.REVIEW_REJECT.getCode().equals(entity.getReview())) {
			//驳回
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.REVIEW_REJECT.getCode()); 
			receiptEntity.setCurrentStepUserList(receiptEntity.getPublisherName());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.REVIEW_REJECT.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.REVIEW_REJECT.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
		}
		
		receiptManagementService.approve(entity, params);
		
		receiptManagementService.updateEntity(receiptEntity);
		receiptReviewService.addEntity(reviewEntity);
		
		return new ResultObj();
	}
	
	/**
	 * 接收人处理：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receivingHandle")
	public @ResponseBody ResultObj receivingHandle(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());
		
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		
		receiptEntity.setUpdateDate(cal.getTime());
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		receiptEntity.setReceivingUnit(entity.getReceivingUnit());
		receiptEntity.setReceivingUnitPersion(sysUserEntity.getLoginName());
		receiptEntity.setReceivingUnitPersionName(sysUserEntity.getName());
		receiptEntity.setReviewTime(dfuYMd.format(cal.getTime()));
		receiptEntity.setReceivingUnitComment(entity.getReceivingUnitComment());
		receiptEntity.setReceiptNo(entity.getReceiptNo());
		
		
		//接收单位处理记录表
		ReceiptReceivingUnitEntity receivingEntity = new ReceiptReceivingUnitEntity();
		receivingEntity.setReceiptId(receiptEntity.getId());
		receivingEntity.setHandleComment(receiptEntity.getReceivingUnitComment());
		receivingEntity.setHandlePersionId(receiptEntity.getReceivingUnitPersion());
		receivingEntity.setHandlePersionName(receiptEntity.getReceivingUnitPersionName());
		
		if (ReceiptProcessEnum.RECEIVING_PERSION_LEADER_APPROVAL.getCode().equals(receiptEntity.getReceivingUnit())) {
			receivingEntity.setHandleResult(ApprovalResultEnum.AGREE.getCode());
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_JOINLTY_SIGN.getCode().equals(receiptEntity.getReceivingUnit())) {
			receivingEntity.setHandleResult(ApprovalResultEnum.AGREE.getCode());
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_REJECT_REVIEW.getCode().equals(receiptEntity.getReceivingUnit())) {
			receivingEntity.setHandleResult(ApprovalResultEnum.REJECT.getCode());
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_REJECT_START.getCode().equals(receiptEntity.getReceivingUnit())) {
			receivingEntity.setHandleResult(ApprovalResultEnum.REJECT.getCode());
		}
		receivingEntity.setHandleTime(dfuYMd.format(cal.getTime()));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getReceivingUnitComment()==null?"":entity.getReceivingUnitComment());
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), entity.getUserList());
		if (ReceiptProcessEnum.RECEIVING_PERSION_LEADER_APPROVAL.getCode().equals(entity.getReceivingUnit())) {
			//领导审核
			receiptEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			receiptEntity.setLeaderSelectedPersionName(entity.getLeaderSelectedPersionName());
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getCode());
//			receiptEntity.setLeaderSelectedPersion(entity.getUserList());
			receiptEntity.setCurrentStepUserList(entity.getLeaderSelectedPersionName());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.RECEIVING_PERSION_LEADER_APPROVAL.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_JOINLTY_SIGN.getCode().equals(entity.getReceivingUnit())) {
			//会签
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.JOINLTY_SING.getCode());
			
			receiptEntity.setJointlySelectedPersion(entity.getJointlySelectedPersion());
			receiptEntity.setJointlySelectedPersionName(entity.getJointlySelectedPersionName());
			receiptEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			receiptEntity.setLeaderSelectedPersionName(entity.getLeaderSelectedPersionName());
			receiptEntity.setCurrentStepUserList(entity.getJointlySelectedPersionName());
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.RECEIVING_PERSION_JOINLTY_SIGN.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.JOINLTY_SING.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_REJECT_REVIEW.getCode().equals(entity.getReceivingUnit())) {
			//驳回部门负责人
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.RECEIVING_REJECT_REVIEW.getCode());
			receiptEntity.setJointlySelectedPersion("");
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setLeaderSelectedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getReviewPersionName());
			receiptEntity.setReceiptNo("");
			
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getReviewPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.RECEIVING_PERSION_REJECT_REVIEW.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.RECEIVING_REJECT_REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
		} else if (ReceiptProcessEnum.RECEIVING_PERSION_REJECT_START.getCode().equals(entity.getReceivingUnit())) {
			//驳回发起人
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.RECEIVING_REJECT_START.getCode());
			receiptEntity.setJointlySelectedPersion("");
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setLeaderSelectedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getPublisherName());
			receiptEntity.setReceiptNo("");
			
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.RECEIVING_PERSION_REJECT_START.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.RECEIVING_REJECT_START.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
		}
		
		receiptManagementService.approve(entity, params);
		
		receiptManagementService.updateEntity(receiptEntity);
		receiptReceivingUnitService.addEntity(receivingEntity);
		
		return new ResultObj();
	}
	
	
	/**
	 * 生产单位会签：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productionUnitJoinltySign")
	public @ResponseBody ResultObj productionUnitJoinltySign(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());
//		receiptEntity.setProcInstId(entity.getProcInstId());
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		receiptEntity.setProductionUntijointlySign(entity.getProductionUntijointlySign());
		receiptEntity.setProductionUntijointlySignComment(entity.getReviewComment());
		receiptEntity.setReviewPersion(sysUserEntity.getLoginName());//生产单位负责人
		receiptEntity.setReviewPersionName(sysUserEntity.getName());
		receiptEntity.setProductionUntijointlySignTime(dfuYMd.format(cal.getTime()));
		
	

//		if (!ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getProductionUntijointlySign())) {
//			if (signededUserList.size() > 0) {
//				List<Condition> conditions = new ArrayList<Condition>();
//				conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
//				conditions.add(new Condition("a.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, signededUserList.toArray()));
//				List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
//				if (userList != null && userList.size() > 0) {
//					for (SysUserEntity user : userList) {
//						MessageCenterEntity messageEntity =new MessageCenterEntity();
//						messageEntity.setTitle(entity.getTitle() + "已驳回");
//						messageEntity.setContext(entity.getTitle() + "已驳回");
//						messageEntity.setSendTime(new Date());
//						messageEntity.setReceivePerson(user.getId().toString());
//						messageEntity.setSendPerson(user.getId().toString());
//						messageEntity.setType("private");
//						messageCenterService.addMessage(messageEntity);
//					}
//				}
//			}
//		}
		
		ReceiptJointlySignEntity signEntity = new ReceiptJointlySignEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setSignComment(receiptEntity.getProductionUntijointlySignComment());
		signEntity.setSignPersionId(sysUserEntity.getLoginName());
		signEntity.setSignPersionName(sysUserEntity.getName());
		signEntity.setType("1");
		signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		signEntity.setSignTime(dfuYMd.format(cal.getTime()));
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getProductionUntijointlySignComment()==null?"":entity.getProductionUntijointlySignComment());
		String users = "";
		//生产部门会签
		receiptEntity.setProductionUntijointlySelectedPersion(entity.getProductionUntijointlySelectedPersion());//生产部门会签人
		receiptEntity.setProductionUntijointlySelectedPersionName(entity.getProductionUntijointlySelectedPersionName());
		receiptEntity.setRecevingSelectedPersion(entity.getRecevingSelectedPersion());//处室办理人
		receiptEntity.setRecevingSelectedPersionName(entity.getRecevingSelectedPersionName());
		users = entity.getProductionUntijointlySelectedPersion();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),users);
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.PRODUCTION_UNIT_JOINLTY_SING.getCode());
		params.put("prOfCompleteInstances", "0");
		params.put("processStatus",ReceiptApprovalStatusEnum.PRODUCTION_UNITD_JOINLTY.getId());
		params.put("result",ApprovalResultEnum.AGREE.getName());
		receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.PRODUCTION_UNITD_JOINLTY.getCode());
		receiptEntity.setCurrentStepUserList(receiptEntity.getProductionUntijointlySelectedPersionName());
		
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptJointlySignService.addEntity(signEntity);
		
		return new ResultObj();
	}
	
	/**
	 * (生产单位会签)审批意见：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productionExecute")
	public @ResponseBody ResultObj productionExecute(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());

		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		receiptEntity.setProductionUntijointlySign(entity.getProductionUntijointlySign());
		receiptEntity.setProductionUntijointlySignComment(entity.getProductionUntijointlySignComment());
		receiptEntity.setProductionUntijointlySignTime(dfuYMd.format(cal.getTime()));

		
		//会签同意时，保存会签人id,驳回时jiontlySignedPersion属性清空
		if (ApprovalResultEnum.AGREE.getCode().equals(receiptEntity.getProductionUntijointlySign())) {
			String signedPersion = receiptEntity.getProductionUntijointlySignedPersion();
			if (signedPersion !=null && !"".equals(signedPersion)) {
				signedPersion += "," + sysUserEntity.getLoginName();
			} else {
				signedPersion = sysUserEntity.getLoginName();
			}
			receiptEntity.setProductionUntijointlySignedPersion(signedPersion);
			receiptEntity.setProductionUntijointlySignPersion(sysUserEntity.getLoginName());
			receiptEntity.setProductionUnitjointlySignPersionName(sysUserEntity.getName());
		} else {
			receiptEntity.setProductionUntijointlySignedPersion("");//清空已会签的生产部门会签人
			receiptEntity.setProductionUntijointlySelectedPersion("");//清空选择的生产部门会签人
			receiptEntity.setProductionUntijointlySelectedPersionName("");
			receiptEntity.setRecevingSelectedPersion("");//清空选择的处室负责人
			receiptEntity.setRecevingSelectedPersionName("");
		}
	
		/**
		 * 判断是否所有人都同意
		 */
		//已经会签同意的人
		List<String> signededUserList = new ArrayList<String>();
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getProductionUntijointlySign())) {
			boolean isAllJointlySign = true;
			//选择的所有会签的人
			String users = receiptEntity.getProductionUntijointlySelectedPersion();
			String [] userArry = null;
			if(users.contains(",")){
				userArry = users.split(",");
			}else{
				userArry = new String[]{users};
			}
			//会签时，所有同意的人
			String signedUser = receiptEntity.getProductionUntijointlySignedPersion();
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
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.PRODUCTION_UNITD_JOINLTY.getCode());
			} else {
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.RECEIVING_UNIT_HANDLE.getCode());
				receiptEntity.setCurrentStepUserList(receiptEntity.getRecevingSelectedPersionName());
			}
		} 
	
		//审核意见
		ReceiptOutDepartmentEntity signEntity = new ReceiptOutDepartmentEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setComment(receiptEntity.getProductionUntijointlySignComment());
		signEntity.setPersionId(sysUserEntity.getLoginName());
		signEntity.setPersionName(sysUserEntity.getName());
		signEntity.setType("1");
		if (ReceiptProcessEnum.PRODUCTION_UNIT_AGREE.getCode().equals(entity.getProductionUntijointlySign())) {
			signEntity.setResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			signEntity.setResult(ApprovalResultEnum.REJECT.getCode());
		}
		signEntity.setTime(dfuYMd.format(cal.getTime()));
		signEntity.setUnitId(sysUserEntity.getUnitId().toString());
		signEntity.setUnitName(sysUserEntity.getUnitName());
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getProductionUntijointlySignComment()==null?"":entity.getProductionUntijointlySignComment());
		String users = "";
		if (ReceiptProcessEnum.PRODUCTION_UNIT_AGREE.getCode().equals(entity.getProductionUntijointlySign())) {
			users = receiptEntity.getRecevingSelectedPersion();
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),users);
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode());
			params.put("prOfCompleteInstances", "1");
			params.put("processStatus",ReceiptApprovalStatusEnum.RECEIVING_UNIT_HANDLE.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
		} else if (ReceiptProcessEnum.PRODUCTION_UNIT_REJECT.getCode().equals(entity.getProductionUntijointlySign())) {
			//驳回生产单位负责人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getReviewPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.PRODUCTION_UNIT_REJECT.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.PRODUCTION_UNITD_JOINLTY_REJECT.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getReviewPersionName());
			receiptEntity.setProductionUntijointlySignPersion("");//清空当前生产部门会签人
			receiptEntity.setProductionUnitjointlySignPersionName("");
			receiptEntity.setReceiptNo("");
		}
		
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptOutDepartmentService.addEntity(signEntity);
		
		return new ResultObj();
	}
	
	
	/**
	 * 部门内部会签：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/departMentInsideJoinltySign")
	public @ResponseBody ResultObj departMentInsideJoinltySign(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());
		//获取登录人
		receiptEntity.setDepartMentInsidejointlySign(entity.getDepartMentInsidejointlySign());
		receiptEntity.setDepartMentInsidejointlySignComment(entity.getDepartMentInsidejointlySignComment());
		receiptEntity.setDepartMentInsidejointlySignTime(dfuYMd.format(cal.getTime()));
		receiptEntity.setReceiptNo(entity.getReceiptNo());
		
		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		//审核意见
		ReceiptJointlySignEntity signEntity = new ReceiptJointlySignEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setSignComment(receiptEntity.getDepartMentInsidejointlySignComment());
		signEntity.setSignPersionId(sysUserEntity.getLoginName());
		signEntity.setSignPersionName(sysUserEntity.getName());
		signEntity.setType("2");
		//审批结果
		signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		signEntity.setSignTime(dfuYMd.format(cal.getTime()));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getDepartMentInsidejointlySignComment()==null?"":entity.getDepartMentInsidejointlySignComment());
		String users = "";
		receiptEntity.setDepartMentInsidejointlySelectedPersion(entity.getDepartMentInsidejointlySelectedPersion());//部门内部会签人
		receiptEntity.setDepartMentInsidejointlySelectedPersionName(entity.getDepartMentInsidejointlySelectedPersionName());
		receiptEntity.setJointlySelectedPersion(entity.getJointlySelectedPersion());//处室负责人
		receiptEntity.setJointlySelectedPersionName(entity.getJointlySelectedPersionName());
		users = entity.getDepartMentInsidejointlySelectedPersion();
		params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),users);
		params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.DEPARTMETNT_INSIDE_JOINLTY_SING.getCode());
		params.put("prOfCompleteInstances", "0");
		params.put("processStatus",ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY.getId());
		params.put("result",ApprovalResultEnum.AGREE.getName());
		signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY.getCode());
		receiptEntity.setCurrentStepUserList(receiptEntity.getDepartMentInsidejointlySelectedPersionName());
		
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptJointlySignService.addEntity(signEntity);
		
		return new ResultObj();
	}
	
	/**
	 * (部门内部会签)审批意见：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/departMentInsideExecute")
	public @ResponseBody ResultObj departMentInsideExecute(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());

		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		receiptEntity.setDepartMentInsidejointlySign(entity.getDepartMentInsidejointlySign());
		receiptEntity.setDepartMentInsidejointlySignComment(entity.getDepartMentInsidejointlySignComment());
		receiptEntity.setDepartMentInsidejointlySignTime(dfuYMd.format(cal.getTime()));

		
		//会签同意时，保存会签人id,驳回时jiontlySignedPersion属性清空
		if (ApprovalResultEnum.AGREE.getCode().equals(receiptEntity.getDepartMentInsidejointlySign())) {
			String signedPersion = receiptEntity.getDepartMentInsidejointlySignedPersion();
			if (signedPersion !=null && !"".equals(signedPersion)) {
				signedPersion += "," + sysUserEntity.getLoginName();
			} else {
				signedPersion = sysUserEntity.getLoginName();
			}
			receiptEntity.setDepartMentInsidejointlySignedPersion(signedPersion);
			receiptEntity.setDepartMentInsidejointlySignPersion(sysUserEntity.getLoginName());
			receiptEntity.setDepartMentInsidejointlySignPersionName(sysUserEntity.getName());
		} else {
			receiptEntity.setDepartMentInsidejointlySignedPersion("");
			receiptEntity.setDepartMentInsidejointlySignPersion("");//当前部门会签人
			receiptEntity.setDepartMentInsidejointlySignPersionName("");
			receiptEntity.setDepartMentInsidejointlySelectedPersion("");//选择的部门会签人
			receiptEntity.setDepartMentInsidejointlySelectedPersionName("");
			receiptEntity.setJointlySelectedPersion("");//处室负责人
			receiptEntity.setJointlySelectedPersionName("");
		}
	
		/**
		 * 判断是否所有人都同意
		 */
		//已经会签同意的人
		List<String> signededUserList = new ArrayList<String>();
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getDepartMentInsidejointlySign())) {
			boolean isAllJointlySign = true;
			//选择的所有会签的人
			String users = receiptEntity.getDepartMentInsidejointlySelectedPersion();
			String [] userArry = null;
			if(users.contains(",")){
				userArry = users.split(",");
			}else{
				userArry = new String[]{users};
			}
			//会签时，所有同意的人
			String signedUser = receiptEntity.getDepartMentInsidejointlySignedPersion();
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
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY.getCode());
			} else {
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.JOINLTY_SING.getCode());
				receiptEntity.setCurrentStepUserList(receiptEntity.getJointlySelectedPersionName());
			}
		} 
	
		//审核意见
		ReceiptOutDepartmentEntity signEntity = new ReceiptOutDepartmentEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setComment(receiptEntity.getDepartMentInsidejointlySignComment());
		signEntity.setPersionId(sysUserEntity.getLoginName());
		signEntity.setPersionName(sysUserEntity.getName());
		signEntity.setType("2");
		if (ReceiptProcessEnum.DEPARTMETNT_INSIDE_JOINLTY_SING_AGREE.getCode().equals(entity.getDepartMentInsidejointlySign())) {
			signEntity.setResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			signEntity.setResult(ApprovalResultEnum.REJECT.getCode());
		}
		signEntity.setTime(dfuYMd.format(cal.getTime()));
		signEntity.setUnitId(sysUserEntity.getUnitId().toString());
		signEntity.setUnitName(sysUserEntity.getUnitName());
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getDepartMentInsidejointlySignComment()==null?"":entity.getDepartMentInsidejointlySignComment());
		String users = "";
		if (ReceiptProcessEnum.DEPARTMETNT_INSIDE_JOINLTY_SING_AGREE.getCode().equals(entity.getDepartMentInsidejointlySign())) {
			users = receiptEntity.getJointlySelectedPersion();
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),users);
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode());
			params.put("prOfCompleteInstances", "1");
			params.put("processStatus",ReceiptApprovalStatusEnum.JOINLTY_SING.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
		} else if (ReceiptProcessEnum.DEPARTMETNT_INSIDE_JOINLTY_SING_REJECT.getCode().equals(entity.getDepartMentInsidejointlySign())) {
			//驳回处室办理人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getRecevingSelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.DEPARTMETNT_INSIDE_JOINLTY_SING_REJECT.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY_REJECT.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.DEPARTMETNT_INSIDE_JOINLTY_REJECT.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getRecevingSelectedPersionName());
		}
		
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptOutDepartmentService.addEntity(signEntity);
		
		return new ResultObj();
	}
	
	
	
	
	
	

	/**
	 * 外部部门会签：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receiptJoinltySign")
	public @ResponseBody ResultObj receiptJoinltySign(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());

		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		receiptEntity.setJointlySign(entity.getJointlySign());
		receiptEntity.setJointlySignComment(entity.getJointlySignComment());
		receiptEntity.setJointlySignTime(dfuYMd.format(cal.getTime()));
		
		//审核意见
		ReceiptJointlySignEntity signEntity = new ReceiptJointlySignEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setSignComment(receiptEntity.getJointlySignComment());
		signEntity.setSignPersionId(sysUserEntity.getLoginName());
		signEntity.setSignPersionName(sysUserEntity.getName());
		signEntity.setType("3");
		//外部部门会签
		if(ReceiptProcessEnum.JOINLTY_SING_OUT_DEPARTMENT.getCode().equals(entity.getJointlySign())){
			signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		}else if(ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getJointlySign())) {
			signEntity.setSignResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			signEntity.setSignResult(ApprovalResultEnum.REJECT.getCode());
		}
		signEntity.setSignTime(dfuYMd.format(cal.getTime()));
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getJointlySignComment()==null?"":entity.getJointlySignComment());
		String users = "";
		if (ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode().equals(entity.getJointlySign())) {
			//领导审核
			receiptEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			receiptEntity.setLeaderSelectedPersionName(entity.getLeaderSelectedPersionName());
			
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getLeaderSelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_AGREE.getCode());
			params.put("prOfCompleteInstances", "1");
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_NO_SIGN.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getLeaderSelectedPersionName());
		} else if (ReceiptProcessEnum.JOINLTY_SING_REJECT_RECEIVING_PERSION.getCode().equals(entity.getJointlySign())) {
			//驳回处室办理人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getRecevingSelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_REJECT_RECEIVING_PERSION.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_RECEIVING.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_RECEIVING.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getRecevingSelectedPersionName());
		} else if (ReceiptProcessEnum.JOINLTY_SING_REJECT_REVIEW.getCode().equals(entity.getJointlySign())) {
			//驳回部门负责人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getReviewPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_REJECT_REVIEW.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setReceiptNo("");
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_REVIEW.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getReviewPersionName());
		} else if (ReceiptProcessEnum.JOINLTY_SING_REJECT_START.getCode().equals(entity.getJointlySign())) {
			//驳回发起人
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_REJECT_START.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.DTJ.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setReceiptNo("");
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.JOINLTY_SING_REJECT_START.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getPublisherName());
		} else if (ReceiptProcessEnum.JOINLTY_SING_OUT_DEPARTMENT.getCode().equals(entity.getJointlySign())) {
			//外部部门会签
			receiptEntity.setOutSelectedPersion(entity.getOutSelectedPersion());
			receiptEntity.setOutSelectedPersionName(entity.getOutSelectedPersionName());
			receiptEntity.setLeaderSelectedPersion(entity.getLeaderSelectedPersion());
			receiptEntity.setLeaderSelectedPersionName(entity.getLeaderSelectedPersionName());
			
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getOutSelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.JOINLTY_SING_OUT_DEPARTMENT.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.OUT_DEPARTMENT.getId());
			params.put("result",ApprovalResultEnum.AGREE.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.OUT_DEPARTMENT.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getOutSelectedPersionName());
		}
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptJointlySignService.addEntity(signEntity);
		return new ResultObj();
	}
	
	/**
	 * 领导审批：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receiptLeaderApproval")
	public @ResponseBody ResultObj receiptLeaderApproval(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());
		 
		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		receiptEntity.setLeaderPersion(sysUserEntity.getLoginName());
		receiptEntity.setLeaderPersionName(sysUserEntity.getName());
		receiptEntity.setLeaderTime(dfuYMd.format(cal.getTime()));
		receiptEntity.setLeaderApproval(entity.getLeaderApproval());
		receiptEntity.setLeaderComment(entity.getLeaderComment());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), receiptEntity.getLeaderComment()==null?"":receiptEntity.getLeaderComment());
		if (ReceiptProcessEnum.LEADER_APPROVAL_AGREE.getCode().equals(entity.getLeaderApproval())) {
			//同意
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_AGREE.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.RESULTS.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.RESULTS.getCode());
			receiptEntity.setCurrentStepUserList("");
			
		} else if (ReceiptProcessEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode().equals(entity.getLeaderApproval())) {
			//驳回部门负责人
			//params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getReviewPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode());
			
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setReceiptNo("");
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_REVIEW.getCode());
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getReviewPersionName());
			
		} else if (ReceiptProcessEnum.LEADER_APPROVAL_REJECT_START.getCode().equals(entity.getLeaderApproval())) {
			//驳回发起人
			//params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),entity.getUserList());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_REJECT_START.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setReceiptNo("");
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_START.getCode());
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getPublisherName());
			
		} else if (ReceiptProcessEnum.LEADER_APPROVAL_REJECT_JOINLTY_SING.getCode().equals(entity.getLeaderApproval())) {
			//驳回处室负责人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getJointlySelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_REJECT_JOINLTY_SING.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_SIGN.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_SIGN.getCode());
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getJointlySelectedPersionName());
			
		} else if (ReceiptProcessEnum.LEADER_APPROVAL_REJECT_RECEIVING_PERSION.getCode().equals(entity.getLeaderApproval())) {
			//驳回接收人(部门领导审核 -> 领导审批)
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getReceivingUnitPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_REJECT_RECEIVING_PERSION.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_RECEIVING.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_RECEIVING.getCode());
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getReceivingUnitPersionName());
			receiptEntity.setLeaderSelectedPersion("");
			receiptEntity.setLeaderSelectedPersionName("");
			
		} else if (ReceiptProcessEnum.LEADER_APPROVAL_REJECT_RECEIVING_SING.getCode().equals(entity.getLeaderApproval())) {
			//驳回处室办理人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getRecevingSelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.LEADER_APPROVAL_REJECT_RECEIVING_SING.getCode());
			params.put("processStatus",ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_RECEIVING.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.LEADER_APPROVAL_REJECT_RECEIVING.getCode());
			receiptEntity.setJointlySignedPersion("");
			receiptEntity.setCurrentStepUserList(receiptEntity.getRecevingSelectedPersionName());
			receiptEntity.setLeaderSelectedPersion("");
			receiptEntity.setLeaderSelectedPersionName("");
		}

		
		ReceiptLeaderApprovalEntity leaderEntity = new ReceiptLeaderApprovalEntity();
		leaderEntity.setReceiptId(receiptEntity.getId());
		leaderEntity.setApprovalComment(receiptEntity.getLeaderComment());
		leaderEntity.setApprovalPersionId(receiptEntity.getLeaderPersion());
		leaderEntity.setApprovalPersionName(receiptEntity.getLeaderPersionName());
		
		if (ReceiptProcessEnum.LEADER_APPROVAL_AGREE.getCode().equals(entity.getLeaderApproval())) {
			leaderEntity.setApprovalResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			leaderEntity.setApprovalResult(ApprovalResultEnum.REJECT.getCode());
		}
		leaderEntity.setApprovalTime(dfuYMd.format(cal.getTime()));
		
		receiptManagementService.approve(entity, params);
		
		receiptManagementService.updateEntity(receiptEntity);
		
		receiptLeaderApprovalService.addEntity(leaderEntity);
		
		return new ResultObj();
	}

	/**
	 * 再提交
	 * @param entity 编辑对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reSubmit")
	public @ResponseBody ResultObj reSubmit(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		entity.setUpdateDate(new Date());
		entity.setApprovalStatus("2");
		entity.setJointlySelectedPersion("");
		entity.setJointlySignedPersion("");
		entity.setCurrentStepUserList(entity.getReviewSelectedPersionName());
		
//		List<Condition> conditions=new ArrayList<Condition>();
//		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.RECEIPT_MANAGEMENT_KEY.getName()));
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
			
			receiptManagementService.approve(entity, params);
			receiptManagementService.updateEntity(entity);
//		}
		
		return new ResultObj();
	}
	
	
	@RequestMapping(value = "/searchReceiptDataForFirstPage", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReceiptDataForFirstPage(HttpServletRequest request, @RequestBody Map<String, Object> params) throws ParseException {
		
		Page<ReceiptManagementEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		
		if (page != null) {
			page.setPageSize(7);
			page.addOrder(Sort.desc("C_ID"));
		}
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_APPROVAL_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ReceiptApprovalStatusEnum.RESULTS.getCode()));
		List<ReceiptManagementEntity> dataList = receiptManagementService.findByCondition(conditions, page);
		
		if (dataList == null || dataList.size() == 0) {
			ResultListObj resultObj = new ResultListObj();
			resultObj.setData(new ArrayList<ReceiptManagementEntity>());
			resultObj.setDraw((Integer)params.get("draw"));
			resultObj.setRecordsTotal(0);
			return resultObj;
			
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
	 * 跳转到查看页面
	 * @param request
	 * @param id 查看对象id
	 * @return
	 */
	@RequestMapping("/goReceiptViewPage/{id}")
	public ModelAndView goReceiptViewPage(HttpServletRequest request,@PathVariable Long id) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		ReceiptManagementEntity entity=receiptManagementService.findById(id);
		
		model.put("receiptManagementEntity", entity);
	
		return this.createModelAndView("/OAManagement/receiptManagement/firstPageView", model);
	}
	

	/**
	 * 数据列表中提交审批
	 * @param request
	 * @param id 数据ID
	 * @return
	 */
	@RequestMapping(value = "/getUnitNameByUseId/{id}")
	public @ResponseBody Map<String, Object> getUnitNameByUseId(HttpServletRequest request,@PathVariable Long id) {
		SysUserEntity user =sysUserService.findById(id);
		SysUnitEntity unitEntity = sysUnitService.findById(user.getUnitId());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("unitName", unitEntity.getName());
		
		return paramMap;
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
		conditions.add(new Condition("C_DEFINITION_KEY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, ProcessMarkEnum.RECEIPT_MANAGEMENT_KEY.getName()));
		
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
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
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
		
		return new ModelAndView("/OAManagement/receiptManagement/userSelect",resultMap);
	}
	
	@RequestMapping(value = "/receiptDiscarded")
	public @ResponseBody ResultObj receiptDiscarded(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity dispatchEntity = this.receiptManagementService.findById(entity.getId());
		dispatchEntity.setApprovalStatus(ReceiptApprovalStatusEnum.DISCARDED.getCode());
		dispatchEntity.setCurrentStepUserList("");
		
		receiptManagementService.dispatchDiscarded(entity.getId().toString(), entity.getProcInstId());

		receiptManagementService.updateEntity(dispatchEntity);
		
		return new ResultObj();
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
		List<SysUserEntity> userList = receiptManagementService.getLeaderPerson4ReceivingHandle(taskId);
	
		resultMap.put("userList", userList);
		return new ModelAndView("/OAManagement/receiptManagement/sureSubmitPerson",resultMap);
	}
	
	

	/**
	 * @Description:   人员选择带回
	 * @author         wjw 
	 * @Date           2017年4月11日 下午5:30:09 
	 * @throws         Exception
	 */
	@RequestMapping("/receivingUserSelect")
	public ModelAndView receivingUserSelect(HttpServletRequest request){
		List<SysDutiesEntity> list=sysDutiesService.findAll();
		ComboboxVO deties = new ComboboxVO();
		for (SysDutiesEntity t : list) {
			deties.addOption(t.getId().toString(), t.getName());
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("sysDuties", JsonUtil.toJson(deties.getOptions()));
		
		String singleSelect = request.getParameter("singleSelect");
		resultMap.put("singleSelect", JsonUtil.toJson(singleSelect));
		
		return new ModelAndView("/OAManagement/receiptManagement/receivingUserSelect",resultMap);
	}
	
	
	
	/**
	 * 接收人处理人员查询
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/searchReceivingUser", method = RequestMethod.POST)
	public @ResponseBody ResultListObj searchReceivingUser(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		Page<SysUserEntity> page = PageUtil.getPage(params);
		List<Sort> orders = OrmUtil.changeMapToOrders(params);
		page.setOrders(orders);
		
		boolean isRoot = false;
		if (conditions != null && conditions.size() > 0) {
			for (Condition con :conditions) {
				if ("a.C_UNIT_ID".equals(con.getFieldName()) && "0".equals(con.getValue())) {
					isRoot = true;
					break;
				} 
			}
		} else {
			isRoot = true;
		}
		List<SysUserEntity> resultList = null;
		if (isRoot) {
			String[] userLoginNameArray = {"zhumeizhou", "maotieniu", "zhaohu", "wujian", "fanglin", "yeming", "haungzhifeng", "liuzhenghua", "wangchuan"};
			conditions.add(new Condition("a.C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.IN, userLoginNameArray));
			resultList = this.sysUserService.findByCondition(conditions, page);
			if (resultList != null) {
				List<SysRoleEntity> roleList = roleService.findAll();
				Map<String, String> roleMap = new HashMap<String, String>();
				for(SysRoleEntity sysRoleEntity :roleList){
					String id = String.valueOf(sysRoleEntity.getId());
					String name = sysRoleEntity.getName();
					if(sysRoleEntity.getStatus().equals("1")){
						roleMap.put(id,name);
					}
				}
				for(SysUserEntity sysUserEntity : (List<SysUserEntity>)resultList){
					if(sysUserEntity.getRoleIds()!=null && sysUserEntity.getRoleIds()!=""){
						String[] roleIds = sysUserEntity.getRoleIds().split(",");
						String roleNames = "";
						for(int i=0;i<roleIds.length;i++){
							if(roleMap.get(roleIds[i]) != null){
								if(i<roleIds.length -1){
									roleNames += roleMap.get(roleIds[i])+",";
								}else {
									roleNames += roleMap.get(roleIds[i]);
								}
							}
						}
						sysUserEntity.setRoleName(roleNames);
					}
				}
			}
		} else {
			resultList = this.sysUserService.findByCondition(params, page);
		}
		ResultListObj resultObj = new ResultListObj();
		
		resultObj.setDraw((Integer)params.get("draw"));
		
		if (resultList != null) {
			resultObj.setData(resultList);
			if (page != null) {
				resultObj.setRecordsTotal(page.getTotal());
			} else {
				resultObj.setRecordsTotal((long) resultList.size());
			}
		}
		return resultObj;
	}
	
	/**
	 * 外部部门会签：同意/驳回处理
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receiptOutDepartment")
	public @ResponseBody ResultObj receiptOutDepartment(@RequestBody ReceiptManagementEntity entity, HttpServletRequest request) {
		
		ReceiptManagementEntity receiptEntity = this.receiptManagementService.findById(entity.getId());

		DateFormatUtil dfuYMd = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
		Calendar cal = Calendar.getInstance();
		receiptEntity.setUpdateDate(cal.getTime());

		//获取登录人
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		
		receiptEntity.setOutJointlySign(entity.getOutJointlySign());
		receiptEntity.setOutJointlySignComment(entity.getOutJointlySignComment());
		receiptEntity.setOutJointlySignPersion(sysUserEntity.getLoginName());
		receiptEntity.setOutJointlySignPersionName(sysUserEntity.getName());
		receiptEntity.setOutJointlySignTime(dfuYMd.format(cal.getTime()));

		
		//会签同意时，保存会签人id,驳回时jiontlySignedPersion属性清空
		if (ApprovalResultEnum.AGREE.getCode().equals(receiptEntity.getOutJointlySign())) {
			String signedPersion = receiptEntity.getOutSignedPersion();
			if (signedPersion !=null && !"".equals(signedPersion)) {
				signedPersion += "," + sysUserEntity.getLoginName();
			} else {
				signedPersion = sysUserEntity.getLoginName();
			}
			receiptEntity.setOutSignedPersion(signedPersion);
		} else {
			receiptEntity.setOutSignedPersion("");
			receiptEntity.setOutJointlySignPersion("");
			receiptEntity.setOutJointlySignPersionName("");
			receiptEntity.setOutSelectedPersion("");
			receiptEntity.setOutSelectedPersionName("");
			receiptEntity.setLeaderSelectedPersion("");
			receiptEntity.setLeaderSelectedPersionName("");
		}
	
		/**
		 * 判断是否所有人都同意
		 */
		//已经会签同意的人
		List<String> signededUserList = new ArrayList<String>();
		if (ApprovalResultEnum.AGREE.getCode().equals(entity.getOutJointlySign())) {
			boolean isAllJointlySign = true;
			//选择的所有会签的人
			String users = receiptEntity.getOutSelectedPersion();
			String [] userArry = users.split(",");
			//会签时，所有同意的人
			String signedUser = receiptEntity.getOutSignedPersion();
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
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.OUT_DEPARTMENT.getCode());
			} else {
				receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.OUT_DEPARTMENT_LEADER.getCode());
				receiptEntity.setCurrentStepUserList(receiptEntity.getLeaderSelectedPersionName());
			}
		} 
//		else {
//			//会签时，所有同意的人
//			String signedUser = receiptEntity.getOutSignedPersion();
//			if (signedUser != null) {
//				String [] signedUserArry = signedUser.split(",");
//				
//				for (String signedUid : signedUserArry) {
//					signededUserList.add(signedUid);
//				}
//			}
//		}
	
		
		ReceiptOutDepartmentEntity signEntity = new ReceiptOutDepartmentEntity();
		signEntity.setReceiptId(receiptEntity.getId());
		signEntity.setComment(receiptEntity.getOutJointlySignComment());
		signEntity.setPersionId(sysUserEntity.getLoginName());
		signEntity.setPersionName(sysUserEntity.getName());
		signEntity.setType("3");
		if (ReceiptProcessEnum.OUT_DEPARTMENT_AGREE.getCode().equals(entity.getOutJointlySign())) {
			signEntity.setResult(ApprovalResultEnum.AGREE.getCode());
		} else {
			signEntity.setResult(ApprovalResultEnum.REJECT.getCode());
		}
		signEntity.setTime(dfuYMd.format(cal.getTime()));
		signEntity.setUnitId(sysUserEntity.getUnitId().toString());
		signEntity.setUnitName(sysUserEntity.getUnitName());
		

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ExamMarkEnum.COMMENT.getCode(), entity.getJointlySignComment()==null?"":entity.getJointlySignComment());
		String users = "";
		if (ReceiptProcessEnum.OUT_DEPARTMENT_AGREE.getCode().equals(entity.getOutJointlySign())) {
			users = receiptEntity.getLeaderSelectedPersion();
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),users);
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.OUT_DEPARTMENT_AGREE.getCode());
			params.put("prOfCompleteInstances", "1");
			params.put("processStatus",ReceiptApprovalStatusEnum.OUT_DEPARTMENT_LEADER.getId());
			params.put("result", ApprovalResultEnum.AGREE.getName());
		} else if (ReceiptProcessEnum.OUT_DEPARTMENT_REJECT.getCode().equals(entity.getOutJointlySign())) {
			//驳回处室负责人
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),receiptEntity.getJointlySelectedPersion());
			params.put(BranchMarkEnum.BRANCH_KEY.getName(), ReceiptProcessEnum.OUT_DEPARTMENT_REJECT.getCode());
			params.put("prOfCompleteInstances", "0");
			params.put("processStatus",ReceiptApprovalStatusEnum.OUT_DEPARTMENT_REJECT_SIGN.getId());
			params.put("result", ApprovalResultEnum.REJECT.getName());
			receiptEntity.setApprovalStatus(ReceiptApprovalStatusEnum.OUT_DEPARTMENT_REJECT_SIGN.getCode());
			receiptEntity.setCurrentStepUserList(receiptEntity.getJointlySelectedPersionName());
		}
		
		receiptManagementService.approve(entity, params);
		receiptManagementService.updateEntity(receiptEntity);
		receiptOutDepartmentService.addEntity(signEntity);
		
		return new ResultObj();
	}
}