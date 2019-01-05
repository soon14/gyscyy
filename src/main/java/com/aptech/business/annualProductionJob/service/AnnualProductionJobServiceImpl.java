package com.aptech.business.annualProductionJob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.annualProductionJob.dao.AnnualProductionJobDao;
import com.aptech.business.annualProductionJob.domain.AnnualProductionJobEntity;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyBtnEnum;
import com.aptech.business.component.dictionary.ScienceTechnologyPlanStatusEnum;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度生产工作计划应用管理服务实现类
 *
 * @author 
 * @created 2018-09-20 13:26:33
 * @lastModified 
 * @history
 *
 */
@Service("annualProductionJobService")
@Transactional
public class AnnualProductionJobServiceImpl extends AbstractBaseEntityOperation<AnnualProductionJobEntity> implements AnnualProductionJobService {
	
	@Autowired
	private AnnualProductionJobDao annualProductionJobDao;
	@Autowired
	private ActTaskService actTaskService;
	
	@Override
	public IBaseEntityOperation<AnnualProductionJobEntity> getDao() {
		return annualProductionJobDao;
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
	public void submit(String id, String selectUser) {
		AnnualProductionJobEntity annualProductionJobEntity=this.findById(Long.valueOf(id));
		String type = annualProductionJobEntity.getType();
		String key = "";
		//准备启动流程
		if("1".equals(type)){
			key=ProcessMarkEnum.NUNNAL_JOB_KEY.getName();
		}else{
			key=ProcessMarkEnum.MONTH_JOB_KEY.getName();
		}
	    
		Map<String, Object> vars=new HashMap<String,Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
//	    SysUserEntity sysUserEntity = RequestContext.get().getUser();

	    annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
		this.updateEntity(annualProductionJobEntity);
	}
	
	@Override
	public void approveTicketAgree(String taskId, String procInstId,
			Map<String, Object> variables,
			AnnualProductionJobEntity annualProductionJobEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		actTaskService.complete(taskId,procInstId,variables);
		this.updateSpnrAgree(annualProductionJobEntity,userEntity);
	}
	@Override
	public void updateSpnrAgree(
			AnnualProductionJobEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.PLANAPPROVE.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.MANAGE.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.EXCUTE.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.END.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZTJ.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.TECHNICALAPPROVE.getCode());
			super.updateEntity(annualProductionJobEntity);
		}
	}
	@Override
	public void approveTicketDisagree(String taskId, String procInstId,
			Map<String, Object> variables,
			AnnualProductionJobEntity annualProductionJobEntity,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		actTaskService.complete(taskId,procInstId,variables);
		this.updateSpnrDisagree(annualProductionJobEntity,userEntity);
	}
	@Override
	public void updateSpnrDisagree(
			AnnualProductionJobEntity t,
			SysUserEntity userEntity) {
		// TODO Auto-generated method stub
		String spFlag=t.getSpFlag();
		if(spFlag.equals(ScienceTechnologyBtnEnum.DWFZR.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZGFZR.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.AQJCB.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.FZJL.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.REJECT.getCode());
			super.updateEntity(annualProductionJobEntity);
		}else if(spFlag.equals(ScienceTechnologyBtnEnum.ZF.getCode())){
			AnnualProductionJobEntity annualProductionJobEntity = this.findById(t.getId());//查询这个表的实体
			annualProductionJobEntity.setApproveStatus(ScienceTechnologyPlanStatusEnum.CANCAL.getCode());
			super.updateEntity(annualProductionJobEntity);
		
	}
	}
	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(
			AnnualProductionJobEntity t) {
		
		AnnualProductionJobEntity  annualProductionJobEntity=this.findById(Long.valueOf(t.getId()));
		annualProductionJobEntity.setResult(t.getResult());
		annualProductionJobEntity.setContent(t.getContent());
			super.updateEntity(annualProductionJobEntity);
			return new ResultObj();
	}
}