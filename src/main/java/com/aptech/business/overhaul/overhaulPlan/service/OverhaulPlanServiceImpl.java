package com.aptech.business.overhaul.overhaulPlan.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.OverhaulPlanStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.managePlanContract.accidentMeasuresPlan.service.AccidentMeasuresPlanService;
import com.aptech.business.managePlanContract.annualMaintenancePlan.service.AnnualMaintenancePlanService;
import com.aptech.business.managePlanContract.annualTechnicalPlan.service.AnnualTechnicalPlanService;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.business.overhaul.overhaulPlan.dao.OverhaulPlanDao;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulPlanMember.service.OverhaulPlanMemberService;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.business.overhaul.overhaulProject.service.OverhaulProjectService;
import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.business.overhaul.power.exception.PowerException;
import com.aptech.business.overhaul.power.exception.PowerExceptionType;
import com.aptech.business.overhaul.power.service.PowerService;
import com.aptech.common.act.service.ActProcessService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修计划应用管理服务实现类
 *
 * @author 
 * @created 2017-06-09 10:42:58
 * @lastModified 
 * @history
 *
 */
@Service("overhaulPlanService")
@Transactional
public class OverhaulPlanServiceImpl extends AbstractBaseEntityOperation<OverhaulPlanEntity> implements OverhaulPlanService {
	
	@Autowired
	private OverhaulPlanDao overhaulPlanDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OverhaulProjectService overhaulProjectService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private OverhaulPlanMemberService overhaulPlanMemberService;
	@Autowired
	private AccidentMeasuresPlanService accidentMeasuresPlanService;
	@Autowired
	private AnnualMaintenancePlanService annualMaintenancePlanService;
	@Autowired
	private AnnualTechnicalPlanService annualTechnicalPlanService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private PowerService powerService;
	@Override
	public IBaseEntityOperation<OverhaulPlanEntity> getDao() {
		return overhaulPlanDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("createDate"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("o.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(OverhaulPlanEntity t) {
	Map<String, Object> params=new HashMap<String, Object> ();
	
	String overhaulPlanId = t.getOverhaulPlanId();
	    params.put("date", new Date());
		String code=fourCodeService.getBusinessCode("检修计划编号", params);
		t.setPlanNumber(code);
		t.setCreateDate(new Date());
		t.setUnitName(sysUnitService.findById(Long.valueOf(t.getUnitId())).getName());
		t.setDutyUserName(sysUserService.findById(t.getDutyUserId()).getName());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
		//计划名称
		if (t.getPlanNameId()!=null) {
			if (accidentMeasuresPlanService.findById(t.getPlanNameId())!=null) {
				t.setPlanName(accidentMeasuresPlanService.findById(t.getPlanNameId()).getSubject());
				
			}else if (annualMaintenancePlanService.findById(t.getPlanNameId())!=null) {
				t.setPlanName(annualMaintenancePlanService.findById(t.getPlanNameId()).getAnnualSubject());
				
			}else if (annualTechnicalPlanService.findById(t.getPlanNameId())!=null) {
				
				t.setPlanName(annualTechnicalPlanService.findById(t.getPlanNameId()).getSubject());
			}
		}
		super.addEntity(t);
		List<OverhaulProjectEntity> projectList = t.getList();
		for(OverhaulProjectEntity projectEntity:projectList){
			try {
				projectEntity.setOverhualPlanId(t.getId());
				projectEntity.setEndDate(df.parse(projectEntity.getEndDateString()));
				projectEntity.setStartDate(df.parse(projectEntity.getStartDateString()));
				overhaulProjectService.addEntity(projectEntity);
			} catch (ParseException e) {
				log.error("overhaulPlanService---addEntity",e);
			}
		}
		
		//更新停送电管理表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_OVERHAUL_PLAN_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulPlanId));
		List<PowerEntity> powerEntities= powerService.findByCondition(conditions, null);
		for(PowerEntity powerEntity :powerEntities){
			powerEntity.setOverhaulPlanId(t.getId());
			powerService.updateEntity(powerEntity);
		}
				
		if(OverhaulPlanStatusEnum.OVERHAULDIRECTOR.getCode().equals(t.getApproveStatus())){
			//提交审核流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
			
			actTaskService.startProcess(ProcessMarkEnum.OVERHAUL_PLAN_PROCESS_KEY.getName(), t.getId().toString(),vars);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULPLAN.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		OverhaulPlanEntity overhaulPlanEntity  =this.findById(id);
		isdeleteValidate(overhaulPlanEntity.getId());
		overhaulPlanEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		overhaulProjectService.deleteEntityByPlanId(overhaulPlanEntity.getId());
		super.updateEntity(overhaulPlanEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULPLAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	private void isdeleteValidate(Long id) {
		OverhaulPlanEntity overhaulPlanEntity  =this.findById(id);
		if(!overhaulPlanEntity.getApproveStatus().equals(OverhaulPlanStatusEnum.CANCEL.getCode())
				&& !overhaulPlanEntity.getApproveStatus().equals(OverhaulPlanStatusEnum.PENDING.getCode())){
			throw new PowerException(PowerExceptionType.POWER_DELETE_STATUS);
		}
	}
	private void isupdateValidate(Long id) {
		OverhaulPlanEntity overhaulPlanEntity  =this.findById(id);
		if(!overhaulPlanEntity.getApproveStatus().equals(OverhaulPlanStatusEnum.REJECT.getCode())
				&& !overhaulPlanEntity.getApproveStatus().equals(OverhaulPlanStatusEnum.PENDING.getCode())){
			throw new PowerException(PowerExceptionType.POWER_UPDATE_STATUS);
		}
	}
	
	public void updateEntity(OverhaulPlanEntity t){
		
		OverhaulPlanEntity entity = this.findById(t.getId());
		t.setDutyUserName(sysUserService.findById(t.getDutyUserId()).getName());
		t.setCreateDate(entity.getCreateDate());
		t.setCreateUserId(entity.getCreateUserId());
		t.setStatus(entity.getStatus());
		t.setUpdateDate(new Date());
		isupdateValidate(entity.getId());
		
		//计划名称
		if (t.getPlanNameId()!=null) {
			if (accidentMeasuresPlanService.findById(t.getPlanNameId())!=null) {
				t.setPlanName(accidentMeasuresPlanService.findById(t.getPlanNameId()).getSubject());
				
			}else if (annualMaintenancePlanService.findById(t.getPlanNameId())!=null) {
				t.setPlanName(annualMaintenancePlanService.findById(t.getPlanNameId()).getAnnualSubject());
				
			}else if (annualTechnicalPlanService.findById(t.getPlanNameId())!=null) {
				
				t.setPlanName(annualTechnicalPlanService.findById(t.getPlanNameId()).getSubject());
			}
		}
		
		super.updateEntity(t);

		List<OverhaulProjectEntity> list = t.getList();
		List<Long> delIds = t.getDelIds();
		//删除掉的项目
		if(delIds!=null  && delIds.size()>0){
			for(Long delid:delIds){
				overhaulProjectService.deleteEntity(delid);
			}
		}
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
		if(list!=null && !list.isEmpty()){
			for(OverhaulProjectEntity projectEntity:list){
				try {
					projectEntity.setOverhualPlanId(t.getId());
					projectEntity.setEndDate(df.parse(projectEntity.getEndDateString()));
					projectEntity.setStartDate(df.parse(projectEntity.getStartDateString()));
					if(projectEntity.getId()!=null && projectEntity.getId()!=0){
						OverhaulProjectEntity proEntity = overhaulProjectService.findById(projectEntity.getId());
						projectEntity.setCreateDate(proEntity.getCreateDate());
						projectEntity.setStatus(proEntity.getStatus());
						overhaulProjectService.updateEntity(projectEntity);
					}else{
						overhaulProjectService.addEntity(projectEntity);
					}
				} catch (ParseException e) {
					log.error("overhaulPlanService---updateEntity",e);
				}
			}
		}
		if(OverhaulPlanStatusEnum.OVERHAULDIRECTOR.getCode().equals(t.getApproveStatus())){
			//提交审核流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
			actTaskService.startProcess(ProcessMarkEnum.OVERHAUL_PLAN_PROCESS_KEY.getName(), t.getId().toString(),vars);
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULPLAN.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public OverhaulPlanEntity findById(Serializable id) {
		OverhaulPlanEntity overhaulPlan=super.findById(id);
		return overhaulPlan;
	}
	/**提交检修计划
	 * @Description:   
	 * @author         zhangxb 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		OverhaulPlanEntity entity  = this.findById(id);
		entity.setApproveStatus(OverhaulPlanStatusEnum.OVERHAULDIRECTOR.getCode());
		super.updateEntity(entity);
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
		System.out.println(params.get("userList"));
		// 准备启动流程
		actTaskService.startProcess(ProcessMarkEnum.OVERHAUL_PLAN_PROCESS_KEY.getName(), id.toString(), vars);
		return resultObj;
	}

	@Override
	public ResultObj approve(OverhaulPlanEntity t, Map<String, Object> params) {
		// 修改状态
		OverhaulPlanEntity tEntity = this.findById(t.getId());
		if(ExamResultEnum.AGREE.getName().equals(params.get(ExamMarkEnum.RESULT.getCode()))){//审核通过的情况
			if("zrtgBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.PROFESSIONALWORKER.getCode());
				String selectedIds = t.getUserList();
				String reviewer[] = selectedIds.split("&");
				tEntity.setOverhaulWorkerSelected(reviewer[0]);
				tEntity.setCentralizedSelected(reviewer[1]);
				tEntity.setSafetySelected(reviewer[2]);
				
			} else if("pass".equals(t.getAuditBtn())){
				
				SysUserEntity userEntity= RequestContext.get().getUser();
				List<String> overhaulIdList = new ArrayList<String>();
				List<String> centralizeIdList = new ArrayList<String>();
				List<String> safetyIdList = new ArrayList<String>();
				String overhaulSelecteds = tEntity.getOverhaulWorkerSelected();
				String[] overhaulSelectedIds  = overhaulSelecteds.split(",");
				for (int i=0;i< overhaulSelectedIds.length ;i++) {
					overhaulIdList.add(overhaulSelectedIds[i]);
				}
				String centralizeSeleceds = tEntity.getCentralizedSelected();
				String[] centralizeSelecedIds = centralizeSeleceds.split(",");
				for (int i=0;i< centralizeSelecedIds.length ;i++) {
					centralizeIdList.add(centralizeSelecedIds[i]);
				}
				String safetySelecteds = tEntity.getSafetySelected();
				String[] safetySelectedIds = safetySelecteds.split(",");
				for (int i=0;i< safetySelectedIds.length ;i++) {
					safetyIdList.add(safetySelectedIds[i]);
				}
				if (overhaulIdList.contains(userEntity.getLoginName())) {
					tEntity.setOverhaulWorkerReviewFlag("1");
				} else if (centralizeIdList.contains(userEntity.getLoginName())) {
					tEntity.setCentralizedReviewFlag("1");
				}  else if (safetyIdList.contains(userEntity.getLoginName())) {
					tEntity.setSafetyReviewFlag("1");
				}
				String overhaulReviewFlag = tEntity.getOverhaulWorkerReviewFlag();
				String centralizeReviewFlag = tEntity.getCentralizedReviewFlag();
				String safetyReviewFlag = tEntity.getSafetyReviewFlag();
				if ("1".equals(overhaulReviewFlag) && "1".equals(centralizeReviewFlag) && "1".equals(safetyReviewFlag)) {
					tEntity.setApproveStatus(OverhaulPlanStatusEnum.TECHNOLOGYDIRECTOR.getCode());
				}
//				tEntity.setApproveStatus(OverhaulPlanStatusEnum.WORKERMODIFY.getCode());
			}
			/*else if("submit".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.TECHNOLOGYDIRECTOR.getCode());
				List<OverhaulProjectEntity> list = t.getList();
				List<Long> delIds = t.getDelIds();
				//删除掉的项目
				if(delIds!=null  && delIds.size()>0){
					for(Long delid:delIds){
						overhaulProjectService.deleteEntity(delid);
					}
				}
				DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd");
				if(list!=null && !list.isEmpty()){
					for(OverhaulProjectEntity projectEntity:list){
						try {
							projectEntity.setOverhualPlanId(t.getId());
							projectEntity.setEndDate(df.parse(projectEntity.getEndDateString()));
							projectEntity.setStartDate(df.parse(projectEntity.getStartDateString()));
							if(projectEntity.getId()!=null && projectEntity.getId()!=0){
								OverhaulProjectEntity entity = overhaulProjectService.findById(projectEntity.getId());
								projectEntity.setCreateDate(entity.getCreateDate());
								projectEntity.setStatus(entity.getStatus());
								overhaulProjectService.updateEntity(projectEntity);
							}else{
								overhaulProjectService.addEntity(projectEntity);
							}
						} catch (ParseException e) {
							log.error("overhaulPlanService---updateEntity",e);
						}
					}
				}
			}*/
			else if("sjzrtgBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.TECHNOLOGYLEADER.getCode());
			}else if("ldtgBtn".equals(t.getAuditBtn())){//分管生产领导审批同意通过
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.FINISH.getCode());
				
				///------------审批通过 停送电管理发送消息
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("C_OVERHAUL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
				List<PowerEntity> powerEntities = powerService.findByCondition(conditions, null);
				if (!powerEntities.isEmpty()) {
					for (PowerEntity powerEntity : powerEntities) {
						powerEntity.setStatus("1");//审批后停送电状态
						powerEntity.setPlanName(tEntity.getPlanName());
						powerService.updateEntity(powerEntity);
						
					}
				   //集控中心人员
					List<Condition> userCondition = new ArrayList<Condition>();
			 		userCondition.add(new Condition("a.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
			 		userCondition.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,138));//集控中心
			 		List<SysUserEntity> allUsers = sysUserService.findByCondition(userCondition, null);
			 		if (!allUsers.isEmpty()) {
			 			for (SysUserEntity sysUserEntity : allUsers) {
							
			 				//调用群发消息接口
			 				MessageCenterEntity messageEntity =new MessageCenterEntity();
			 				messageEntity.setTitle("检修计划审批通过");
			 				messageEntity.setContext("检修计划-停送电管理功能审批通过");
			 				messageEntity.setSendTime(new Date());
			 				messageEntity.setReceivePerson(sysUserEntity.getId().toString());
			 				SysUserEntity UserEntity = RequestContext.get().getUser();
			 				messageEntity.setSendPerson(UserEntity.getId().toString());
			 				messageEntity.setType("private");
			 				messageCenterService.addMessage(messageEntity);
						}
					}
				}
				
			}else if("ztjBtn".equals(t.getAuditBtn())){//againSubmit
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.OVERHAULDIRECTOR.getCode());
			}
		}else{
			if("zrbhBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.REJECT.getCode());//检修专工填报
			}else if("reject".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.MAINTENANCE_REPORTT.getCode());
			}else if("sjzrbhBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.MAINTENANCE_REPORTT.getCode());
			}else if("ldbhBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.TECHNOLOGYDIRECTOR.getCode());
			}else if("ldbtgBtn".equals(t.getAuditBtn())){//分管生产领导审批不同意通过
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.UNFINISH.getCode());
				
			}else if("qxBtn".equals(t.getAuditBtn())){//cancel
				tEntity.setApproveStatus(OverhaulPlanStatusEnum.CANCEL.getCode());
			}
			
		}
		
		super.updateEntity(tEntity);
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		//设置当前流程,当前任务节点的下一个节点的办理人
		actTaskService.setVarialble(t.getTaskId(),params);
		// 调用流程接口
		actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
//		if (actTaskService.isParallelEndTask(t.getTaskId())== true) {
//			
//		}
		return  new ResultObj();
	}
	

	@Override
	public void insertMemberAuto(OverhaulPlanMemberEntity t,OverhaulPlanEntity planEntity) {
		List<Condition> conditions = new ArrayList<Condition>();
		
		      MessageCenterEntity messageEntity =new MessageCenterEntity();
				conditions.add(new Condition("O.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
				conditions.add(new Condition("O.C_OVERHUAL_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, planEntity.getId()));
				List<OverhaulProjectEntity> pEntity = overhaulProjectService.findByCondition(conditions, null);
				if (pEntity!=null&&pEntity.size()>0) {
					for (OverhaulProjectEntity overhaulProjectEntity : pEntity) {
						
						messageEntity.setTitle("检修计划已执行");
						messageEntity.setContext("检修项目开工时间设定："+overhaulProjectEntity.getStartDateString()+"请注意开工时间");
						messageEntity.setSendTime(new Date());
						messageEntity.setReceivePerson(t.getCreateUserId());
					
						messageEntity.setSendPerson(planEntity.getDutyUserId().toString());
						messageEntity.setType("private");
						messageCenterService.addMessage(messageEntity);
					}
					
				}
		
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULPLAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}