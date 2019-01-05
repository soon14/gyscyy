package com.aptech.business.managePlanContract.investmentPlan.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.InvestmentPlanStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.managePlanContract.investmentPlan.dao.InvestmentPlanDao;
import com.aptech.business.managePlanContract.investmentPlan.domain.InvestmentPlanEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 投资计划应用管理服务实现类
 *
 * @author 
 * @created 2018-04-08 13:31:35
 * @lastModified 
 * @history
 *
 */
@Service("investmentPlanService")
@Transactional
public class InvestmentPlanServiceImpl extends AbstractBaseEntityOperation<InvestmentPlanEntity> implements InvestmentPlanService {
	
	@Autowired
	private InvestmentPlanDao investmentPlanDao;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<InvestmentPlanEntity> getDao() {
		return investmentPlanDao;
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
	public void addEntity(InvestmentPlanEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setApproveStatus(InvestmentPlanStatusEnum.PENDING.getCode());
		super.addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INVESTNEBTPLAN.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
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
		InvestmentPlanEntity entity  = this.findById(id);
		entity.setApproveStatus(InvestmentPlanStatusEnum.OVERHAULDIRECTOR.getCode());
		super.updateEntity(entity);
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
		System.out.println(params.get("userList"));
		// 准备启动流程
		actTaskService.startProcess(ProcessMarkEnum.INVESTMENT_PLAN_PROCESS_KEY.getName(), id.toString(), vars);
		return resultObj;
	}
	@Override
	public ResultObj approve(InvestmentPlanEntity t, Map<String, Object> params) {
		// 修改状态
		InvestmentPlanEntity tEntity = this.findById(t.getId());
		if(ExamResultEnum.AGREE.getName().equals(params.get(ExamMarkEnum.RESULT.getCode()))){//审核通过的情况
			if("plantgBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(InvestmentPlanStatusEnum.PROFESSIONALWORKER.getCode());
			
			}else if("planoperationBtn".equals(t.getAuditBtn())){//分管生产领导审批
				tEntity.setApproveStatus(InvestmentPlanStatusEnum.FINISH.getCode());
				
			}
		}else{
			if("planbhBtn".equals(t.getAuditBtn())){
				tEntity.setApproveStatus(InvestmentPlanStatusEnum.REJECT.getCode());
			}else if("qxBtn".equals(t.getAuditBtn())){//cancel
				tEntity.setApproveStatus(InvestmentPlanStatusEnum.CANCEL.getCode());
			}
		}
		super.updateEntity(tEntity);
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		//设置当前流程,当前任务节点的下一个节点的办理人
		actTaskService.setVarialble(t.getTaskId(),params);
		// 调用流程接口
		actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
		return  new ResultObj();
	}
	@Override
	public void deleteEntity(Serializable id) {
		getDao().deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INVESTNEBTPLAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.INVESTNEBTPLAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}