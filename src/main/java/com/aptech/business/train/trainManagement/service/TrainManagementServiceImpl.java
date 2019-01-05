package com.aptech.business.train.trainManagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.train.trainManagement.dao.TrainManagementDao;
import com.aptech.business.train.trainManagement.domain.TrainApprovalStatusEnum;
import com.aptech.business.train.trainManagement.domain.TrainManagementEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 培训管理服务实现类
 * @author Administrator
 *
 */
@Service("trainManagementService")
@Transactional
public class TrainManagementServiceImpl extends AbstractBaseEntityOperation<TrainManagementEntity> implements TrainManagementService {
	
	@Autowired
	private TrainManagementDao trainManagementDao;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	/**
	 * 流程服务对象
	 */
	@Autowired
	private ActTaskService actTaskService;
	
	@Override
	public IBaseEntityOperation<TrainManagementEntity> getDao() {
		return trainManagementDao;
	}

	@Override
	public void saveAddPage(TrainManagementEntity t) {
		this.addEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TRAINMANAGEMENT.getName(), OperateUserEnum.ADD.getName(), "", OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void saveEditPage(TrainManagementEntity trainPlanEntity) {
		this.updateEntity(trainPlanEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TRAINMANAGEMENT.getName(), OperateUserEnum.EDIT.getName(), "", OperateUserEnum.EDIT.getId());
	}

	@Override
	public void delete(Long id) {
		this.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TRAINMANAGEMENT.getName(), OperateUserEnum.DELETE.getName(), "", OperateUserEnum.DELETE.getId());
	}

	@Override
	public void submit(String id, String selectUseIds) {
		//准备启动流程
		String key=ProcessMarkEnum.TRAIN_MANAGEMENT_PROCESS_KEY.getName();	
		
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUseIds);//选定的审批人
  		
  		actTaskService.startProcess(key, id, vars);
		
  		TrainManagementEntity trainManagementEntity = this.findById(Long.valueOf(id));
  		trainManagementEntity.setStatus(TrainApprovalStatusEnum.REVIEW.getCode()); 
		
		super.updateEntity(trainManagementEntity);
		
	}

	@Override
	public void approve(TrainManagementEntity entity, Map<String, Object> params) {
		actTaskService.setVarialble(entity.getTaskId(), params);
		// 调用流程接口
		params.put(ExamMarkEnum.RESULT.getCode(), params.get("result"));
		actTaskService.complete(entity.getTaskId(), entity.getProcInstId() ,params);
		
	}
	
}