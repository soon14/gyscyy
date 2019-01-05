package com.aptech.business.technical.eventAnalysis.service;

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
import com.aptech.business.component.dictionary.TechnicalStatusEnum;
import com.aptech.business.messageCenter.domain.MessageCenterEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.technical.eventAnalysis.dao.EventAnalysisDao;
import com.aptech.business.technical.eventAnalysis.domain.EventAnalysisEntity;
import com.aptech.business.technical.eventNotification.domain.EventNotificationEntity;
import com.aptech.business.technical.testReport.domain.TestReportEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
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
 * 事件分析应用管理服务实现类
 *
 * @author 
 * @created 2018-09-07 17:36:53
 * @lastModified 
 * @history
 *
 */
@Service("eventAnalysisService")
@Transactional
public class EventAnalysisServiceImpl extends AbstractBaseEntityOperation<EventAnalysisEntity> implements EventAnalysisService {
	
	@Autowired
	private EventAnalysisDao eventAnalysisDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<EventAnalysisEntity> getDao() {
		return eventAnalysisDao;
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
	public void addEntity(EventAnalysisEntity t) {
		t.setStatus(EventNotificationStatusEnum.DTJ.getCode());
		getDao().addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_ANALYSIS.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	@Override
	public void updateEntity(EventAnalysisEntity t) {
		getDao().updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_ANALYSIS.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}

	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_ANALYSIS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EVENT_ANALYSIS.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	@Override
	public void submit(String id, String selectUser) {
		// TODO Auto-generated method stub
		//准备启动流程
	    String key=ProcessMarkEnum.EVENT_ANALYSIS_PROCESS_KEY.getName();	
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    EventAnalysisEntity  eventAnalysisEntity=this.findById(Long.valueOf(id)); 
	    eventAnalysisEntity.setId(Long.valueOf(id));
	    eventAnalysisEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
		super.updateEntity(eventAnalysisEntity);
	}
	@Override
	public void updateSpnrAgree(EventAnalysisEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.ZGZRSH.getCode())){
			//更新主表状态
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.SJBSH.getCode());
			super.updateEntity(eventAnalysisEntity);
		}
//		else if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
//			//更新主表状态
//			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
//			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.SJLDSH.getCode());
//			super.updateEntity(eventAnalysisEntity);
//		}
		else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			//更新主表状态
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.END.getCode());
			super.updateEntity(eventAnalysisEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.ZTJ.getCode())){
			//更新主表状态
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
			super.updateEntity(eventAnalysisEntity);
		}
	}
	@Override
	public void updateSpnrDisagree(EventAnalysisEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.ZGZRSH.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventAnalysisEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventAnalysisEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.REJECTOWNER.getCode());
			super.updateEntity(eventAnalysisEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.REJECT.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.ZF.getCode());
			super.updateEntity(eventAnalysisEntity);
		}
	}
	@Override
	public void updateSpnrDisagreeUp(EventAnalysisEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(EventNotificationStatusEnum.SJBSH.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.ZGZRSH.getCode());
			super.updateEntity(eventAnalysisEntity);
		}else if(spFlag.equals(EventNotificationStatusEnum.SJLDSH.getCode())){
			EventAnalysisEntity eventAnalysisEntity=this.findById(t.getId());
			//更新主表状态为驳回
			eventAnalysisEntity.setStatus(EventNotificationStatusEnum.SJBSH.getCode());
			super.updateEntity(eventAnalysisEntity);
		}
	}
}