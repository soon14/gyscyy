package com.aptech.business.planManage.plan.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.PlanCycleEnum;
import com.aptech.business.component.dictionary.PlanStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.defectManage.defect.exception.DefectException;
import com.aptech.business.defectManage.defect.exception.DefectExceptionType;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.planManage.plan.dao.PlanDao;
import com.aptech.business.planManage.plan.domain.PlanEntity;
import com.aptech.business.planManage.planDetail.domain.PlanDetailEntity;
import com.aptech.business.planManage.planDetail.service.PlanDetailService;
import com.aptech.business.planManage.planWhole.domain.PlanWholeEntity;
import com.aptech.business.planManage.planWhole.service.PlanWholeService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 计划管理应用管理服务实现类
 *
 * @author 
 * @created 2017-11-13 15:10:16
 * @lastModified 
 * @history
 *
 */
@Service("planService")
@Transactional
public class PlanServiceImpl extends AbstractBaseEntityOperation<PlanEntity> implements PlanService {
	
	@Autowired
	private PlanDao planDao;
	@Autowired
	private PlanWholeService planWholeService;
	@Autowired
	private PlanDetailService planDetailService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<PlanEntity> getDao() {
		return planDao;
	}
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	@Override
	public ResultObj add(PlanEntity planEntity){
		//验证计划时间是否存在
		List<Condition> conditions =new ArrayList<Condition>();
		conditions.add(new Condition("T.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getUnitId()));
		conditions.add(new Condition("T.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getType()));
		conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE,planEntity.getTime()));
		conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.LE,planEntity.getTime()));
		conditions.add(new Condition("T.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NE,planEntity.getId()==null?"":planEntity.getId()));
		conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.NE,PlanStatusEnum.DELETE.getId()));
		List<PlanEntity> planList=planDao.findByCondition(conditions, null);
		if(!planList.isEmpty()){
			throw new DefectException("计划时间已存在");
		}
		if(planEntity.getCycle().equals(PlanCycleEnum.DOUYYYY.getCode())){
			conditions.clear();
			conditions.add(new Condition("T.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getUnitId()));
			conditions.add(new Condition("T.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getType()));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE,planEntity.getTimeEnd()));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.LE,planEntity.getTimeEnd()));
			conditions.add(new Condition("T.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NE,planEntity.getId()==null?"":planEntity.getId()));
			conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.NE,PlanStatusEnum.DELETE.getId()));
			planList=planDao.findByCondition(conditions, null);
			if(!planList.isEmpty()){
				throw new DefectException("计划时间已存在");
			}
			conditions.clear();
			conditions.add(new Condition("T.C_UNIT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getUnitId()));
			conditions.add(new Condition("T.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getType()));
			conditions.add(new Condition("C_TIME", FieldTypeEnum.STRING, MatchTypeEnum.GE,planEntity.getTime()));
			conditions.add(new Condition("C_TIME_END", FieldTypeEnum.STRING, MatchTypeEnum.LE,planEntity.getTimeEnd()));
			conditions.add(new Condition("T.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.NE,planEntity.getId()==null?"":planEntity.getId()));
			conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.NE,PlanStatusEnum.DELETE.getId()));
			planList=planDao.findByCondition(conditions, null);
			if(!planList.isEmpty()){
				throw new DefectException("计划时间已存在");
			}
		}
		planEntity.setStatus(PlanStatusEnum.PENDING.getId().longValue());
		if(planEntity.getCycle().equals(PlanCycleEnum.ONEYYYY.getCode())){
			planEntity.setTimeEnd(planEntity.getTime());
		}
		if(planEntity.getId()==null){
			planDao.addEntity(planEntity);
			conditions.clear();
			conditions.add(new Condition("C_UUID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,planEntity.getUuid()));
			List<PlanWholeEntity> planWholeList=planWholeService.findByCondition(conditions, null);
			for (PlanWholeEntity p:planWholeList) {
				p.setPlanId(planEntity.getId());
				planWholeService.updateEntity(p);
			}
			if(!planWholeList.isEmpty()){
				planDetailService.updatePlanDetailEntity(planWholeList.get(0).getId());
			}
		}else{
			PlanEntity t = this.findById(planEntity.getId());
			if(validate(t)){
			planEntity.setStatus(t.getStatus());
			planDao.updateEntity(planEntity);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PLAN.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(planEntity);
		return resultObj;
	}
	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id) {
		PlanEntity t = this.findById(id);
		if(validateSubmit(t)){
			planDao.deleteEntity(id);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PLAN.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
	}
	/**
	 * 提交实体
	 * 
	 * @param id
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		PlanEntity t = this.findById(id);
		if(validateSubmit(t)){
			//验证是否存在整体计划，详细计划。
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("C_PLAN_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));
			List <PlanWholeEntity> wholeList=planWholeService.findByCondition(conditions, null);
			if(wholeList.isEmpty()){
				throw new DefectException("整体计划最少有一条");
			}else{
				for (PlanWholeEntity p:wholeList) {
					conditions.clear();
					conditions.add(new Condition("C_PLAN_WHOLE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,p.getId()));
					List <PlanDetailEntity> detailList= planDetailService.findByCondition(conditions, null);
					if(detailList.isEmpty()){
						throw new DefectException("每条整体计划至少有一条详细计划");
					}
				}
			}
			t.setStatus(PlanStatusEnum.OVERHAUL_DIRECTOR.getId().longValue());
			planDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
			actTaskService.startProcess(ProcessMarkEnum.PLAN_PROCESS_KEY.getName(), id.toString(), vars);
		}
		return resultObj;
	}
	@Override
	public ResultObj approve(PlanEntity t,Map<String, Object> params) {
		if(validateStatus(t)){
			// 修改状态
		    PlanEntity tEntity = this.findById(t.getId());
			if(params.get("status")!=null){
				tEntity.setStatus(Long.parseLong(params.get("status").toString()));
			}
			planDao.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get(ExamMarkEnum.RESULT.getCode()).toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return  new ResultObj();
	}
	@Override
	public ResultObj approveFinish(PlanEntity t,Map<String, Object> params) {
		if(validateStatus(t)){
			// 修改状态
		    PlanEntity tEntity = this.findById(t.getId());
			tEntity.setStatus(Long.parseLong(params.get("status").toString()));
			Object status= actTaskService.getVarialble(t.getTaskId(), BranchMarkEnum.PARLLEL_BRANCH_KEY.getName());
			if(String.valueOf(status).equals(String.valueOf(ExamResultEnum.BACK.getId()))){
				tEntity.setStatus(PlanStatusEnum.REJECT.getId().longValue());
			}
			planDao.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get(ExamMarkEnum.RESULT.getCode()).toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return  new ResultObj();
	}
	// 基本验证
		public boolean validate(PlanEntity t) {
			if (t != null) {
				if (!(t.getStatus().toString().equals(PlanStatusEnum.PENDING.getCode()) || t
						.getStatus().toString().equals(PlanStatusEnum.REJECT.getCode()))) {
					throw new DefectException(DefectExceptionType.DEFECT_CODE_STATUS_2);
				}
			}
			return true;
		}
	// 提交基本验证
		public boolean validateSubmit(PlanEntity t) {
			if (t != null) {
//				throw new DefectException(DefectExceptionType.DEFECT_CODE_NULL);
				if (!t.getStatus().toString().equals(PlanStatusEnum.PENDING.getCode())) {
					throw new DefectException(DefectExceptionType.DEFECT_CODE_STATUS_2);
				}
			}
			return true;
		}
	// 流程基本验证 
			public boolean validateStatus(PlanEntity t) {
				PlanEntity tEntity = this.findById(t.getId());
				if (tEntity == null) {
					throw new DefectException(
							DefectExceptionType.DEFECT_CODE_NULL);
				}
				if (!t.getStatus().toString().equals(tEntity.getStatus().toString())) {
					throw new DefectException(
							DefectExceptionType.DEFECT_CODE_REPEAT);
				}
				return true;
			}
}