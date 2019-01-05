package com.aptech.business.defectManage.check.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.check.dao.CheckDao;
import com.aptech.business.defectManage.check.domain.CheckEntity;
import com.aptech.business.defectManage.check.exception.CheckException;
import com.aptech.business.defectManage.check.exception.CheckExceptionType;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.spring.Result;
import com.aptech.framework.web.base.ResultObj;

@Service("checkService")
@Transactional
public class CheckServiceImpl extends AbstractBaseEntityOperation<CheckEntity>
		implements CheckService {

	@Autowired
	private CheckDao checkDao;
	@Autowired
	private DefectService defectService;
	@Autowired
	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<CheckEntity> getDao() {
		return checkDao;
	}

	/**
	 * 审批：流程
	 * 
	 * @param t
	 */
	@Override
	public ResultObj approve(CheckEntity t,  Map<String, Object> params) {
		if (validate(t)) {
			// 保存审批信息
			this.addEntity(t);
			// 修改状态
			DefectEntity tEntity = defectService.findById(Long.parseLong(t
					.getDefectId()));
			tEntity.setProcessStatus(params.get("status").toString());
			defectService.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
//			params.put(ExamMarkEnum.RESULT.getCode(), t.getCheckOpinions()==null?"":t.getCheckOpinions());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		solve(t);
		return new ResultObj();
	}

	// 基本验证
	public boolean validate(CheckEntity t) {
		DefectEntity tEntity = defectService.findById(Long.parseLong(t
				.getDefectId()));
		if (tEntity == null) {
			throw new CheckException(
					CheckExceptionType.CHECK_CODE_NULL);
		}
		if (!t.getProcessStatus().equals(tEntity.getProcessStatus())) {
			throw new CheckException(
					CheckExceptionType.CHECK_CODE_REPEAT);
		}
		return true;
	}

	@Override
	public ResultObj solve(CheckEntity t) {
		// TODO Auto-generated method stub
		ResultObj resultObj = new ResultObj();
		DefectEntity defectEntity = defectService.findById(Long.valueOf(t.getDefectId()));
		defectEntity.setLossElectricity(t.getLossEletricity());
		defectService.updateEntity(defectEntity);
		return resultObj;
	}
}