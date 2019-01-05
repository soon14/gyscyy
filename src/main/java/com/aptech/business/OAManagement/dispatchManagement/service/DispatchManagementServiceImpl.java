package com.aptech.business.OAManagement.dispatchManagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.OAManagement.dispatchManagement.dao.DispatchManagementDao;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchApprovalStatusEnum;
import com.aptech.business.OAManagement.dispatchManagement.domain.DispatchManagementEntity;
import com.aptech.business.OAManagement.dispatchManagement.domain.TreeEntity;
import com.aptech.business.OAManagement.feedBack.service.DispatchFeedbackService;
import com.aptech.business.OAManagement.jointlySign.service.DispatchJointlySignService;
import com.aptech.business.OAManagement.leaderApproval.service.DispatchLeaderApprovalService;
import com.aptech.business.OAManagement.review.service.DispatchReviewService;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketException;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;
import com.aptech.common.act.service.ActProcessService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.DutiesOrgTypeEnum;
import com.aptech.common.workflow.modelEditor.domain.NodeConfigEntity;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

@Service("dispatchManagementService")
@Transactional
public class DispatchManagementServiceImpl extends AbstractBaseEntityOperation<DispatchManagementEntity> implements DispatchManagementService {

	@Autowired
	private DispatchManagementDao dispatchManagementDao;
	
	/**
	 * 用户服务对象
	 */
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 组织机构服务对象
	 */
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
	 * 流程服务对象
	 */
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private ActProcessService actProcessService;
	
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	
	/**
	 * 流程管理服务对象
	 */
	@Autowired
	private NodeConfigService nodeConfigService;
	
	@Autowired
	private UserUnitRelService userUnitRelService;
	
   	@Override
	public IBaseEntityOperation<DispatchManagementEntity> getDao() {
		return dispatchManagementDao;
	}

	@Override
	public List<SysUserEntity> getDrafterList() {
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<SysUserEntity> tmp = this.sysUserService.findByCondition(conditions, null);
		if (tmp != null) {
			return tmp;
		}
		return new ArrayList<SysUserEntity>();
	}

	@Override
	public List<Map<String, String>> getDispatchNumberInfo() {
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, new Long(1)));
		List<Map<String, String>> tmp = this.findByCondition("findDispatchNumberInfo", conditions, null);
		if (tmp != null) {
			return tmp;
		}
		return new ArrayList<Map<String, String>>();
	}

	@Override
	public List<TreeEntity> getRecipientTree() {
		List<Condition> conditions = new ArrayList<Condition>();
		//组织机构
		conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.NE, "4"));
		conditions.add(new Condition("C_GENERATION_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.NE, "2"));
		List<SysUnitEntity> unitList = this.sysUnitService.findByCondition(conditions, null);
		
		//user
		conditions.clear();
		conditions.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<SysUserEntity> userList = this.sysUserService.findByCondition(conditions, null);
		
		List<TreeEntity> treeList = new ArrayList<TreeEntity>();
		if (unitList != null && unitList.size() > 0) {
			for (SysUnitEntity unitEntity : unitList) {
				if (unitEntity != null) {
					TreeEntity treeEntity = new TreeEntity();
					treeEntity.setId("O" + unitEntity.getId().toString());
					treeEntity.setCode(unitEntity.getCode());
					treeEntity.setName(unitEntity.getName());
					treeEntity.setParentId("O" + unitEntity.getParentId().toString());
					treeEntity.setNodeType("1");
					treeList.add(treeEntity);
					if (userList != null && userList.size() > 0) {
						for (SysUserEntity userEntity : userList) {
							if (userEntity != null && unitEntity.getId().toString().equals(userEntity.getUnitId().toString())) {
								TreeEntity tEntity = new TreeEntity();
								tEntity.setId("U" + userEntity.getId().toString());
								tEntity.setCode(userEntity.getLoginName());
								tEntity.setName(userEntity.getName());
								tEntity.setParentId("O" + unitEntity.getId().toString());
								tEntity.setNodeType("2");
								treeList.add(tEntity);
							}
						}
					}
				}
			}
		}

		return treeList;
	}

	@Override
	public void submit(String id, List<SysUserEntity> selectUser) {
		String selectUseIds = "";
		if (selectUser != null && selectUser.size() > 0) {
			for (int i= 0;i<selectUser.size(); i++) {
				selectUseIds += selectUser.get(i).getLoginName();
				if (i != selectUser.size()-1) {
					selectUseIds += ",";
				}
			}
		}
		//准备启动流程
		String key=ProcessMarkEnum.DISPATCH_MANAGEMENT_KEY.getName();	
		
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUseIds);//选定的审批人
  		
  		actTaskService.startProcess(key, id, vars);
		
		DispatchManagementEntity dispatchManagementEntity = this.findById(Long.valueOf(id));
		dispatchManagementEntity.setApprovalStatus(DispatchApprovalStatusEnum.REVIEW.getCode()); 
		
		super.updateEntity(dispatchManagementEntity);
		
	}

	@Override
	public void updateReviewInfo(DispatchManagementEntity entity) {
		dispatchManagementDao.updateReviewInfo(entity);
		
	}

	@Override
	public void updateJointlySignInfo(DispatchManagementEntity entity) {
		dispatchManagementDao.updateJointlySignInfo(entity);
		
	}

	@Override
	public void updateLeaderApprovalInfo(DispatchManagementEntity entity) {
		dispatchManagementDao.updateLeaderApprovalInfo(entity);
		
	}
	
	@Override
	public void updateComposingApprovalInfo(DispatchManagementEntity entity) {
		dispatchManagementDao.updateComposingApprovalInfo(entity);
		
	}

	@Override
	public void approve(DispatchManagementEntity entity, Map<String, Object> params) {
		actTaskService.setVarialble(entity.getTaskId(), params);
		// 调用流程接口
		params.put(ExamMarkEnum.RESULT.getCode(), params.get("result"));
		actTaskService.complete(entity.getTaskId(), entity.getProcInstId() ,params);
		
	}

	@Override
	public void updateFeedBackInfo(DispatchManagementEntity entity) {
		dispatchManagementDao.updateFeedBackInfo(entity);
	}

	@Override
	public void releaseDispatch(DispatchManagementEntity entity) {
		dispatchManagementDao.releaseDispatch(entity);
		
	}

	@Override
	public void dispatchDiscarded(String businessKey, String processInstanceId) {
		actProcessService.endProcess(processInstanceId);
		
	}
	
	@Override
	public List<SysUserEntity> getPerson4SignLeader(String taskId) {
		String [] activityIds = {"sid-d0431b61-d7f1-489b-aefd-ffa320f7f8cd"};
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("nodeId", FieldTypeEnum.STRING, MatchTypeEnum.IN, activityIds));
		List<NodeConfigEntity> mpdeList = this.nodeConfigService.findByCondition(conditions, null);
		if (mpdeList == null || mpdeList.size() == 0){
			return new ArrayList<SysUserEntity>();
		}
		NodeConfigEntity nodeConfigEntity = mpdeList.get(0);
		
		String[] condidateGroups = {"59"};
		List<Condition> dutiesConditions = new ArrayList<Condition>();
		dutiesConditions.add(new Condition("dutiesId", FieldTypeEnum.STRING,MatchTypeEnum.IN, condidateGroups));
		List<SysDutiesDetailEntity> sysDutiesDetailEntites = sysDutiesDetailService.findByCondition(dutiesConditions, null);
		List<Long> unitIds = new ArrayList<Long>();
		
		SysUserEntity starter = (SysUserEntity) actTaskService.getVarialble(taskId,CandidateMarkEnum.STARTER.getName());
		
		//获取启动人及其组织
		if (DutiesOrgTypeEnum.STARTER_ORG.getCode().equals(nodeConfigEntity.getOrganizationEnum())) {
			unitIds.add(starter.getUnitId());
		} else {
			// 获取所有组织信息
			List<SysUnitEntity> unitsList = sysUnitService.getUnitTreeNodeList();
			// 获取启动人的组织
			SysUnitEntity sysUnitEntity = sysUnitService.findById(starter.getUnitId());
			// 获取启动人所属组织的上级组织
			List<SysUnitEntity> sysUnits = new ArrayList<SysUnitEntity>();
			if (DutiesOrgTypeEnum.STARTER_PARENT_ORG.getCode().equals(nodeConfigEntity.getOrganizationEnum())) {
				sysUnits = findParentsUnit(sysUnitEntity, unitsList);
			} else if (DutiesOrgTypeEnum.STARTER_SUB_ORG.getCode().equals(nodeConfigEntity.getOrganizationEnum())) {
				sysUnits = findSubUnit(sysUnitEntity, unitsList);
			}
			for (SysUnitEntity unit : sysUnits) {
				unitIds.add(unit.getId());
			}
			unitIds.add(starter.getUnitId());
		}
		

//		获得职务下的用户和组织关系表的Id
		List<String> userUnitRelIds = new ArrayList<String>();
		for(SysDutiesDetailEntity sysDuty : sysDutiesDetailEntites){
			userUnitRelIds.add(sysDuty.getUserUnitRelId());
		}
		List<Condition> userConditions = new ArrayList<Condition>();

		userConditions.add(new Condition("c_id",FieldTypeEnum.STRING,MatchTypeEnum.IN,userUnitRelIds.toArray()));
		userConditions.add(new Condition("c_unit_id", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIds.toArray()));
		List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
		userConditions.clear();
		//查出用户Id；
		List<Long> userIds = new ArrayList<Long>();
		if(!userUnitRelList.isEmpty()){
			for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
				userIds.add(userUnitRelEntity.getUserId());
			}
			userConditions.add(new Condition("a.C_ID",FieldTypeEnum.STRING, MatchTypeEnum.IN, userIds.toArray()));
			userConditions.add(new Condition("a.C_STATUS",FieldTypeEnum.STRING, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));
			return sysUserService.findByCondition(userConditions, null);
		}else{
			return new ArrayList<SysUserEntity>();
		}

	}
	/**
	 * 查找当前部门的上级部门
	 */
	private List<SysUnitEntity> findParentsUnit(SysUnitEntity sysUnitEntity,
			List<SysUnitEntity> unitsList) {
		List<SysUnitEntity> sysunits = new ArrayList<SysUnitEntity>();
		for (SysUnitEntity sysUnit : unitsList) {
			if (sysUnit.getId().equals(sysUnitEntity.getParentId())) {
				sysunits.add(sysUnit);
				findParentsUnit(sysUnit, unitsList);
			}
		}
		return sysunits;
	}
	/**
	 * 查找当前部门的子部门
	 */
	private List<SysUnitEntity> findSubUnit(SysUnitEntity sysUnitEntity, List<SysUnitEntity> unitsList) {
		List<SysUnitEntity> sysunits = new ArrayList<SysUnitEntity>();
		for (SysUnitEntity sysUnit : unitsList) {
			if (sysUnit.getParentId().equals(sysUnitEntity.getId())) {
				sysunits.add(sysUnit);
				findSubUnit(sysUnit, unitsList);
			}
		}
		return sysunits;
	}

	@Override
	public List<SysUserEntity> getPerson4TrainAndRepair(String dispatchType, String taskId) {
		List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
		String dutiesId ="";
		if ("7".equals(dispatchType) || "8".equals(dispatchType)) {
			dutiesId ="77";
		} else if ("5".equals(dispatchType)) {
			dutiesId ="78";
		} else {
			return userList;
		}
		SysUserEntity starter = (SysUserEntity) actTaskService.getVarialble(taskId,CandidateMarkEnum.STARTER.getName());
		
		List<Condition> dutiesConditions = new ArrayList<Condition>();
		dutiesConditions.add(new Condition("dutiesId", FieldTypeEnum.STRING,MatchTypeEnum.EQ, dutiesId));
		List<SysDutiesDetailEntity> sysDutiesDetailEntites = sysDutiesDetailService.findByCondition(dutiesConditions, null);
		if (sysDutiesDetailEntites != null && sysDutiesDetailEntites.size() >0) {
			List<String> userUnitRelIds = new ArrayList<String>();
			List<String> unitIds = new ArrayList<String>();
			unitIds.add(starter.getUnitId().toString());
			
			for (SysDutiesDetailEntity user :sysDutiesDetailEntites) {
				userUnitRelIds.add(user.getUserUnitRelId().toString());
			}
			
			List<Condition> userConditions = new ArrayList<Condition>();
			userConditions.add(new Condition("c_id",FieldTypeEnum.STRING,MatchTypeEnum.IN, userUnitRelIds.toArray()));
			userConditions.add(new Condition("c_unit_id", FieldTypeEnum.STRING, MatchTypeEnum.IN, unitIds.toArray()));
			List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(userConditions, null);
			userConditions.clear();
			//查出用户Id；
			List<Long> userIds = new ArrayList<Long>();
			if(!userUnitRelList.isEmpty()){
				for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
					userIds.add(userUnitRelEntity.getUserId());
				}
				userConditions.add(new Condition("a.C_ID",FieldTypeEnum.STRING, MatchTypeEnum.IN, userIds.toArray()));
				userConditions.add(new Condition("a.C_STATUS",FieldTypeEnum.STRING, MatchTypeEnum.EQ, DataStatusEnum.NORMAL.ordinal()));

				userList = sysUserService.findByCondition(userConditions, null);
			}
		}
		return userList;
	}

	@Override
	public ResultObj showValidate(Long id) {
		// TODO Auto-generated method stub
		ResultObj resultObj = new ResultObj();
		isshowValidate(id);
		return resultObj;
	}
	private boolean isshowValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		if(sysUserEntity.getUnitId()==143||sysUserEntity.getUnitId()==144||sysUserEntity.getUnitId()==145||sysUserEntity.getUnitId()==146||sysUserEntity.getUnitId()==147||sysUserEntity.getUnitId()==148){
			conditions.add(new Condition("C_ID = 30 OR C_ID = 59 OR C_ID = 5"));
			List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
			List<String> userUnitRelIds = new ArrayList<String>();
			for(SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
				userUnitRelIds.add(sysDutiesDetailEntity.getUserUnitRelId());
			}
			conditions.clear();
			conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.STRING, MatchTypeEnum.IN, userUnitRelIds.toArray()));
			List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
			for(UserUnitRelEntity userUnitRelEntity : userUnitRelList){
				if(sysUserEntity.getId()==userUnitRelEntity.getUserId()){
					return true;
				}
			}
		}else{
			return false;
		}
		return true;
	}
}