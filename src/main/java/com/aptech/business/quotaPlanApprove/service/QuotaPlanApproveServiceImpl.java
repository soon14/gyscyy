package com.aptech.business.quotaPlanApprove.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.quotaPlanApprove.dao.QuotaPlanApproveDao;
import com.aptech.business.quotaPlanApprove.domain.QuotaPlanApproveEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 指标计划流程表应用管理服务实现类
 *
 * @author 
 * @created 2018-09-21 10:10:36
 * @lastModified 
 * @history
 *
 */
@Service("quotaPlanApproveService")
@Transactional
public class QuotaPlanApproveServiceImpl extends AbstractBaseEntityOperation<QuotaPlanApproveEntity> implements QuotaPlanApproveService {
	
	@Autowired
	private QuotaPlanApproveDao quotaPlanApproveDao;
	@Autowired
	private ActTaskService actTaskService;
	
	@Override
	public IBaseEntityOperation<QuotaPlanApproveEntity> getDao() {
		return quotaPlanApproveDao;
	}
	@Override
	public void submit(String id, String selectUser) {
		QuotaPlanApproveEntity quotaPlanApproveEntity=this.findById(Long.valueOf(id));
		String type = quotaPlanApproveEntity.getType();
		String key = "";
		//准备启动流程
		if("1".equals(type)){
			key=ProcessMarkEnum.ANNUAL_PRODUCTION_PLAN_PROCESS_KEY.getName();
		}else if("2".equals(type)){
			key=ProcessMarkEnum.NEIKONG_CAPACITY_KEY.getName();
		}else{
			key=ProcessMarkEnum.NUNNAL_QUOTA_KEY.getName();
		}
	    
		Map<String, Object> vars=new HashMap<String,Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
	    SysUserEntity sysUserEntity = RequestContext.get().getUser();

	    quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
	    quotaPlanApproveEntity.setProductionRunningId(sysUserEntity.getId().toString());
		this.updateEntity(quotaPlanApproveEntity);
	}
	
	@Override
	public void approveTicketAgree(String taskId, String procInstId,
			Map<String, Object> variables,
			QuotaPlanApproveEntity quotaPlanApproveEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		actTaskService.complete(taskId,procInstId,variables);
		this.updateSpnrAgree(quotaPlanApproveEntity,userEntity);
	}
	@Override
	public void updateSpnrAgree(
			QuotaPlanApproveEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setProductionSkillId(userEntity.getId().toString());
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.PLANAPPROVE.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setPlanRunningId(userEntity.getId().toString());
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.MANAGE.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setLeaderId(userEntity.getId().toString());
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.EXCUTE.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setPlanRunningDown(userEntity.getId().toString());
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.END.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZTJ.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}
	}
	@Override
	public void approveTicketDisagree(String taskId, String procInstId,
			Map<String, Object> variables,
			QuotaPlanApproveEntity quotaPlanApproveEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		actTaskService.complete(taskId,procInstId,variables);
		this.updateSpnrDisagree(quotaPlanApproveEntity,userEntity);
	}
	@Override
	public void updateSpnrDisagree(
			QuotaPlanApproveEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZF.getCode())){
			QuotaPlanApproveEntity quotaPlanApproveEntity = this.findById(t.getId());//查询这个表的实体
			quotaPlanApproveEntity.setStatus(ScienceTechnologyPlanStatusEnum.CANCAL.getCode());
			super.updateEntity(quotaPlanApproveEntity);
		
	}
	}
}