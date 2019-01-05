package com.aptech.business.managePlanContract.yearPurchase.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.YearPurchaseBtnTypeEnum;
import com.aptech.business.component.dictionary.YearPurchaseStatusEnum;
import com.aptech.business.managePlanContract.yearPurchase.dao.YearPurchaseDao;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 年度采购应用管理服务实现类
 *
 * @author 
 * @created 2018-04-12 13:45:42
 * @lastModified 
 * @history
 *
 */
@Service("yearPurchaseService")
@Transactional
public class YearPurchaseServiceImpl extends AbstractBaseEntityOperation<YearPurchaseEntity> implements YearPurchaseService {
	
	@Autowired
	private YearPurchaseDao yearPurchaseDao;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<YearPurchaseEntity> getDao() {
		return yearPurchaseDao;
	}
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		SysUserEntity sysUserEntity = RequestContext.get().getUser();
		conditions.add(new Condition("a.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, sysUserEntity.getUnitId()));
		return findByCondition(conditions, page);
	}
	
	
	/**提交
	 * @Description:   
	 * @author         
	 * @Date           
	 * @throws         Exception
	 */
	@Override
	public ResultObj submit(String id,  String selectUser) {
		ResultObj resultObj = new ResultObj();
		YearPurchaseEntity entity  = this.findById(Long.parseLong(id));
		entity.setStatus(YearPurchaseStatusEnum.OVERHAUL.getCode());
		super.updateEntity(entity);
		// 准备启动流程
		ProcessInstance processInstance = actTaskService.startProcess(ProcessMarkEnum.YEAR_PURCHASE_PROCESS_KEY.getName(), id, null);
		
		//越过第一步
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);
		vars.put(BranchMarkEnum.BRANCH_KEY.getName(),ExamResultEnum.AGREE.getId());
		actTaskService.completeFirstTask(processInstance.getId(), vars);
		
//		ProcessInstance pInstance = actTaskService.startProcess(ProcessMarkEnum.YEAR_PURCHASE_PROCESS_KEY.getName(), id.toString(), vars);
//		actTaskService.complete(taskId, pInstance.getId(), vars);
//		actTaskService.complete(taskId,pInstance.getId(),null);
		return resultObj;
	}
	
	@Override
	public void updateSpnrAgree(YearPurchaseEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(YearPurchaseBtnTypeEnum.synmangeBtn.getCode())){
		    YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
		     workFireEntity.setStatus(YearPurchaseStatusEnum.PLANEXECUTED.getCode());
			super.updateEntity(workFireEntity);
		
		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.technologyBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(YearPurchaseStatusEnum.PLANEXECUTED.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.planBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(YearPurchaseStatusEnum.LEADER.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.leaderBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(YearPurchaseStatusEnum.EXECUTED.getCode());
			super.updateEntity(workFireEntity);

		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.planoperationBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(YearPurchaseStatusEnum.FINISH.getCode());
            super.updateEntity(workFireEntity);
            
		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			YearPurchaseEntity workFireEntity=yearPurchaseDao.findById(t.getId());
			 workFireEntity.setStatus(YearPurchaseStatusEnum.OVERHAUL.getCode());
			 super.updateEntity(workFireEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(YearPurchaseEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(YearPurchaseBtnTypeEnum.synmangeBtn.getCode())|| spFlag.equals(YearPurchaseBtnTypeEnum.technologyBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			 workFireEntity.setStatus(YearPurchaseStatusEnum.REJECT.getCode());
			this.updateEntity(workFireEntity);
			
		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.planBtn.getCode())){
			  YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			  workFireEntity.setStatus(YearPurchaseStatusEnum.REJECT.getCode());
			this.updateEntity(workFireEntity);
		}else if(spFlag.equals(YearPurchaseBtnTypeEnum.leaderBtn.getCode())){
			YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
			  workFireEntity.setStatus(YearPurchaseStatusEnum.REJECT.getCode());
			this.updateEntity(workFireEntity); 
		    
        }else if(spFlag.equals(YearPurchaseBtnTypeEnum.FP.getCode())){
            YearPurchaseEntity workFireEntity = this.findById(t.getId());//查询这个表的实体
            //更新主表状态为废票
            workFireEntity.setStatus(YearPurchaseStatusEnum.UNFINISH.getCode());
            yearPurchaseDao.updateEntity(workFireEntity);
        }
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.YEARPURCHASE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}