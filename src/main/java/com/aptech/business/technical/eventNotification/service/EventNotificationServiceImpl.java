package com.aptech.business.technical.eventNotification.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.EventNotificationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TechnicalBtnTypeEnum;
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.messageCenter.service.MessageCenterService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.business.technical.eventNotification.dao.EventNotificationDao;
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.summary.domain.SummaryEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
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
import com.aptech.framework.validate.ValidateUtil;

/**
 * 
 * 事件通报应用管理服务实现类
 *
 * @author 
 * @created 2018-07-30 11:42:47
 * @lastModified 
 * @history
 *
 */
@Service("eventNotificationService")
@Transactional
public class EventNotificationServiceImpl extends AbstractBaseEntityOperation<EventNotificationEntity> implements EventNotificationService {
	
	@Autowired
	private EventNotificationDao eventNotificationDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private MessageCenterService messageCenterService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EventNotificationEntity> getDao() {
		return eventNotificationDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	@Override
	public void addEntity(EventNotificationEntity t) {
		t.setStatus(EventNotificationStatusEnum.DTJ.getCode());
		getDao().addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_NOTIFICATION.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(EventNotificationEntity t) {
		getDao().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_NOTIFICATION.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}

	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_NOTIFICATION.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_NOTIFICATION.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public void submit(String id, String selectUser) {
		// TODO Auto-generated method stub
		//准备启动流程
	    String key=ProcessMarkEnum.EVENT_NOTIFICATION_PROCESS_KEY.getName();	
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    EventNotificationEntity  eventNotificationEntity=this.findById(Long.valueOf(id)); 
	    eventNotificationEntity.setId(Long.valueOf(id));
	    eventNotificationEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
		super.updateEntity(eventNotificationEntity);
	}
	@Override
	public void updateSpnrAgree(EventNotificationEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.ZGZRSH.getCode())){
			//更新主表状态
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.SJBSH.getCode());
			super.updateEntity(eventNotificationEntity);
		}
//		else if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
//			//更新主表状态
//			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
//			eventNotificationEntity.setStatus(EventNotificationStatusEnum.SJLDSH.getCode());
//			super.updateEntity(eventNotificationEntity);
//		}
		else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			//更新主表状态
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.END.getCode());
//			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1066"));
//			List<SysDutiesEntity> SysDutiesList = sysDutiesService.findByCondition(conditions, null);
//			SysDutiesEntity sysDutiesEntity = SysDutiesList.get(0);
//			conditions.clear();
//			conditions.add(new Condition("C_DUTIES_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysDutiesEntity.getId()));
//			List<SysDutiesDetailEntity> sysDutiesDetailList = sysDutiesDetailService.findByCondition(conditions, null);
//			for( SysDutiesDetailEntity sysDutiesDetailEntity : sysDutiesDetailList){
//				conditions.clear();
//				Long id = Long.parseLong(sysDutiesDetailEntity.getUserUnitRelId());
//				conditions.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,id));
//				List<UserUnitRelEntity> list_userUnitRel = userUnitRelService.findByCondition(conditions, null);
//				for(UserUnitRelEntity tempuserUnitRel:list_userUnitRel){
//					SysUserEntity sysUserEntity = sysUserService.findById(tempuserUnitRel.getUserId());
//					MessageCenterEntity messageCenterEntity = new MessageCenterEntity();
//					messageCenterEntity.setType("private");
//					messageCenterEntity.setTitle("事件已通报");
//					messageCenterEntity.setContext("事件已通报给相关部门，请确认！");
//					messageCenterEntity.setSendTime(new Date());
//					messageCenterEntity.setReceivePerson(sysUserEntity.getId().toString());
//					messageCenterEntity.setSendPerson(userEntity.getId().toString());
//					messageCenterService.addMessage(messageCenterEntity);
//				}
//			}
			super.updateEntity(eventNotificationEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.ZTJ.getCode())){
			//更新主表状态
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
			super.updateEntity(eventNotificationEntity);
		}
	}
	@Override
	public void updateSpnrDisagree(EventNotificationEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.ZGZRSH.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventNotificationEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventNotificationEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventNotificationEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.REJECT.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.ZF.getCode());
			super.updateEntity(eventNotificationEntity);
		}
	}
	@Override
	public void updateSpnrDisagreeUp(EventNotificationEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
			super.updateEntity(eventNotificationEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			EventNotificationEntity eventNotificationEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventNotificationEntity.setStatus(EventNotificationStatusEnum.SJBSH.getCode());
			super.updateEntity(eventNotificationEntity);
		}
	}


	@Override
	public List<SysUserEntity> getPerson4TrainAndRepair(String taskId) {
		List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
		String dutiesId ="76";
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
                                                                                                                                                                                           
}