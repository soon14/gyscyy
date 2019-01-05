package com.aptech.business.defectManage.appraisal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.appraisal.dao.AppraisalDao;
import com.aptech.business.defectManage.appraisal.domain.AppraisalEntity;
import com.aptech.business.defectManage.appraisal.exception.AppraisalException;
import com.aptech.business.defectManage.appraisal.exception.AppraisalExceptionType;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

@Service("appraisalService")
@Transactional
public class AppraisalServiceImpl extends
		AbstractBaseEntityOperation<AppraisalEntity> implements
		AppraisalService {

	@Autowired
	private AppraisalDao appraisalDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private DefectService defectService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Override
	public IBaseEntityOperation<AppraisalEntity> getDao() {
		return appraisalDao;
	}

	/**
	 * 审批:流程
	 * 
	 * @param t
	 */
	@Override
	public ResultObj approve(AppraisalEntity t,  Map<String, Object> params) {
		if (validate(t)) {
			// 保存审批信息
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, t.getAppraisalResult()));
			conditions.add(new Condition("C_CATEGORY_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "APPRAISAL_RESULT"));
			List<SysDictionaryEntity> sysDictionaryList = sysDictionaryService.findByCondition(conditions, null);
			SysDictionaryEntity sysDictionaryEntity = sysDictionaryList.get(0);
			t.setApprovalOpinions(sysDictionaryEntity.getName());
			this.addEntity(t);
			// 修改状态
			DefectEntity tEntity = defectService.findById(Long.parseLong(t
					.getDefectId()));
			tEntity.setProcessStatus(params.get("status").toString());
			tEntity.setType(t.getGrade());
			tEntity.setAppraisalUserId(Long.parseLong(t.getUserId()));
			defectService.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
//			params.put(ExamMarkEnum.RESULT.getCode(), t.getApprovalOpinions()==null?"":t.getApprovalOpinions());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return new ResultObj();
	}
	/**
	 * 审批:流程（无信息）
	 * 
	 * @param t
	 */
	@Override
	public ResultObj approveStatus(AppraisalEntity t,  Map<String, Object> params) {
		if (validate(t)) {
			// 修改状态
			DefectEntity tEntity = defectService.findById(Long.parseLong(t
					.getDefectId()));
			if(params.get("status")!=null){
				tEntity.setProcessStatus(params.get("status").toString());
			}
			defectService.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
//			params.put(ExamMarkEnum.RESULT.getCode(), t.getApprovalOpinions()==null?"":t.getApprovalOpinions());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return new ResultObj();
	}
	// 基本验证
	public boolean validate(AppraisalEntity t) {
		DefectEntity tEntity = defectService.findById(Long.parseLong(t
				.getDefectId()));
		if (tEntity == null) {
			throw new AppraisalException(
					AppraisalExceptionType.APPRAISAL_CODE_NULL);
		}
//		if (!t.getProcessStatus().equals(tEntity.getProcessStatus())) {
//			throw new AppraisalException(
//					AppraisalExceptionType.APPRAISAL_CODE_REPEAT);
//		}
		return true;
	}
}