package com.aptech.business.defectManage.solve.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.defectManage.solve.dao.SolveDao;
import com.aptech.business.defectManage.solve.domain.SolveEntity;
import com.aptech.business.defectManage.solve.exception.SolveException;
import com.aptech.business.defectManage.solve.exception.SolveExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

@Service("solveService")
@Transactional
public class SolveServiceImpl extends AbstractBaseEntityOperation<SolveEntity>
		implements SolveService {

	@Autowired
	private SolveDao solveDao;
	@Autowired
	private DefectService defectService;
	@Autowired
	private ActTaskService actTaskService;

	@Override
	public IBaseEntityOperation<SolveEntity> getDao() {
		return solveDao;
	}

	/**
	 * 审批
	 * 
	 * @param t
	 */
	@Override
	public ResultObj approve(SolveEntity t, Map<String, Object> params) {
		if (validate(t)) {
			// 保存审批信息
			this.addEntity(t);
			// 修改状态
			DefectEntity tEntity = defectService.findById(Long.parseLong(t
					.getDefectId()));
			tEntity.setProcessStatus(params.get("status").toString());
			tEntity.setSolveUserId(Long.parseLong(t.getUserId()));
			defectService.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
//			params.put(ExamMarkEnum.RESULT.getCode(), t.getSolveExplain()==null?"":t.getSolveExplain());
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
	public ResultObj approveStatus(SolveEntity t,  Map<String, Object> params) {
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
//			params.put(ExamMarkEnum.RESULT.getCode(),"");
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return new ResultObj();
	}
	// 基本验证
	public boolean validate(SolveEntity t) {
		DefectEntity tEntity = defectService.findById(Long.parseLong(t
				.getDefectId()));
		if (tEntity == null) {
			throw new SolveException(
					SolveExceptionType.SOLVE_CODE_NULL);
		}
		if (!t.getProcessStatus().equals(tEntity.getProcessStatus())) {
			throw new SolveException(
					SolveExceptionType.SOLVE_CODE_REPEAT);
		}
		return true;
	}
}