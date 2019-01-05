package com.aptech.business.equip.equipAbnormal.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.equip.equipAbnormal.dao.EquipAbnormalDao;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalConstantEnum;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEntity;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEnum;
import com.aptech.business.equip.equipAbnormal.exception.EquipAbnormalException;
import com.aptech.business.equip.equipAbnormal.exception.EquipAbnormalExceptionType;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelConstantEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 设备异动应用管理服务实现类
 *
 * @author 
 * @created 2017-06-22 14:52:35
 * @lastModified 
 * @history
 *
 */
@Service("equipAbnormalService")
@Transactional
public class EquipAbnormalServiceImpl extends AbstractBaseEntityOperation<EquipAbnormalEntity> implements EquipAbnormalService {
	
	@Autowired
	private EquipAbnormalDao equipAbnormalDao;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Autowired
	private EquipAbnormalEquipRelService equipAbnormalEquipRelService;
	
	@Override
	public IBaseEntityOperation<EquipAbnormalEntity> getDao() {
		return equipAbnormalDao;
	}
	
	/**
	 * 提交流程，开始流程
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		EquipAbnormalEntity equipAbnormalEntity = this.findById(id);
		if(validate(equipAbnormalEntity)){
			equipAbnormalEntity.setProcessStatus(EquipAbnormalEnum.WAITCHECK.getCode());
			this.updateEntity(equipAbnormalEntity);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
			actTaskService.startProcess(ProcessMarkEnum.EQUIP_ABNORMAL_PROCESS_KEY.getName(), id.toString(), vars);
		}
		return resultObj;
	}
	
	/**
	 * 审核后继续下一步流程
	 */
	@Override
	public ResultObj approve(EquipAbnormalEntity equipAbnormalEntity,Map<String, Object> params) {
		if(validateStatus(equipAbnormalEntity)){
			// 修改状态
			EquipAbnormalEntity entity = this.findById(equipAbnormalEntity.getId());
			entity.setProcessStatus(params.get("processStatus").toString());
			entity.setStatus(EquipLedgerEnum.NORMAL.getId());
			if(equipAbnormalEntity.getExecuteUserId()!=null){
				entity.setExecuteUserId(equipAbnormalEntity.getExecuteUserId());
			}
			if(!StringUtils.isEmpty(equipAbnormalEntity.getExecuteUserName())){
				entity.setExecuteUserName(equipAbnormalEntity.getExecuteUserName());
			}
			if(!StringUtils.isEmpty(equipAbnormalEntity.getFinishInfo())){
				entity.setFinishInfo(equipAbnormalEntity.getFinishInfo());
			}
			if(equipAbnormalEntity.getStartDate()!=null){
				entity.setStartDate(equipAbnormalEntity.getStartDate());
			}
			if(equipAbnormalEntity.getEndDate()!=null){
				entity.setEndDate(equipAbnormalEntity.getEndDate());
			}
			if(equipAbnormalEntity.getExecuteFileId()!=null){
				entity.setExecuteFileId(equipAbnormalEntity.getExecuteFileId());
			}
			this.updateEntity(entity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(equipAbnormalEntity.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result"));
			actTaskService.complete(equipAbnormalEntity.getTaskId(), equipAbnormalEntity.getProcInstId(),params);
		}
		return  new ResultObj();
	}
	
	/**
	 * @Description:   基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:28 
	 * @throws         Exception
	 */
	
	public boolean validate(EquipAbnormalEntity equipAbnormalEntity) {
		if (equipAbnormalEntity == null) {
			throw new EquipAbnormalException(EquipAbnormalExceptionType.DEFECT_CODE_NULL);
		}
		if (!(equipAbnormalEntity.getProcessStatus().equals(EquipAbnormalEnum.WATISUBMIT.getCode()) || equipAbnormalEntity.getProcessStatus().equals(EquipAbnormalEnum.REJECT.getCode()))) {
			throw new EquipAbnormalException(EquipAbnormalExceptionType.DEFECT_CODE_STATUS);
		}
		return true;
	}
	
	/**
	 * @Description:   流程基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:01 
	 * @throws         Exception
	 */
	public boolean validateStatus(EquipAbnormalEntity equipAbnormalEntity) {
		EquipAbnormalEntity entity = this.findById(equipAbnormalEntity.getId());
		if (entity == null) {
			throw new EquipAbnormalException(
					EquipAbnormalExceptionType.DEFECT_CODE_NULL);
		}
		if (!equipAbnormalEntity.getProcessStatus().equals(entity.getProcessStatus())) {
			throw new EquipAbnormalException(
					EquipAbnormalExceptionType.DEFECT_CODE_REPEAT);
		}
		return true;
	}

	@Override
	public ResultObj addEquipAbnormal(EquipAbnormalEntity equipAbnormalEntity) {
		//登录人信息
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalEntity> list = new ArrayList<EquipAbnormalEntity>();
		if(equipAbnormalEntity.getEquipName()!=null && equipAbnormalEntity.getEquipmentCode()!=null){
			String tempCode = equipAbnormalEntity.getEquipmentCode();
			String tempName = equipAbnormalEntity.getEquipName();
			String[] arrayCode = null;
			String[] arrayName = null;
			if(tempCode.contains(",")){
				arrayCode = tempCode.split(",");
			}
			if(tempName.contains(",")){
				arrayName = tempName.split(",");
			}
			if(arrayCode!=null && arrayName!=null){
				for(int i = 0;i<arrayName.length;i++){
					EquipAbnormalEntity entity = new EquipAbnormalEntity();
					entity.setEquipmentCode(arrayCode[i]);
					entity.setEquipName(arrayName[i]);
					list.add(entity);
				}
			}
		}
		equipAbnormalDao.addEntity(equipAbnormalEntity);
		Long equipAbnormalId = equipAbnormalEntity.getId();
		for(EquipAbnormalEntity abnormalEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(abnormalEntity.getEquipmentCode());
			abnormalEquipRelEntity.setEquipName(abnormalEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setEquipAbnormalId(equipAbnormalId);
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMAL.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj updateEquipAbnormal(EquipAbnormalEntity equipAbnormalEntity) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalReportEntity> list = new ArrayList<EquipAbnormalReportEntity>();
		if(equipAbnormalEntity.getEquipName()!=null && equipAbnormalEntity.getEquipmentCode()!=null){
			String tempCode = equipAbnormalEntity.getEquipmentCode();
			String tempName = equipAbnormalEntity.getEquipName();
			String[] arrayCode = null;
			String[] arrayName = null;
			if(tempCode.contains(",")){
				arrayCode = tempCode.split(",");
			}
			if(tempName.contains(",")){
				arrayName = tempName.split(",");
			}
			if(arrayCode!=null && arrayName!=null){
				for(int i = 0;i<arrayName.length;i++){
					EquipAbnormalReportEntity entity = new EquipAbnormalReportEntity();
					entity.setEquipCode(arrayCode[i]);
					entity.setEquipName(arrayName[i]);
					list.add(entity);
				}
			}
		}
		equipAbnormalEntity.setProcessStatus(EquipAbnormalEnum.WATISUBMIT.getCode());
		equipAbnormalEntity.setStatus(EquipAbnormalConstantEnum.NORMAL.getId());
		//保存设备异动报告
		equipAbnormalDao.updateEntity(equipAbnormalEntity);
		//添加新的设备数据
		Long equipAbnormalId = equipAbnormalEntity.getId();
		//删除旧的设备数据
		equipAbnormalEquipRelService.deleteByCondition("delByEquipAbnormalId", equipAbnormalEntity.getId());
		for(EquipAbnormalReportEntity abnormalReportEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(abnormalReportEntity.getEquipCode());
			abnormalEquipRelEntity.setEquipName(abnormalReportEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setUpdateDate(new Date());
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			abnormalEquipRelEntity.setEquipAbnormalId(equipAbnormalId);
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMAL.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj deleteMore(List<Integer> ids) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Integer id : ids) {
			List<Condition> condition = new ArrayList<Condition>();
			condition.add(new Condition("C_EQUIP_ABNORMAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
			List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
			if(!list.isEmpty()){
				for(EquipAbnormalEquipRelEntity abnormalEquipRelEntity:list){
					abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.DELETE.getId());
					abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
					abnormalEquipRelEntity.setUpdateDate(new Date());
					equipAbnormalEquipRelService.updateEntity(abnormalEquipRelEntity);
				}
			}
			long longId = (long) id;
			EquipAbnormalEntity equipAbnormalEntity  = equipAbnormalDao.findById(longId);
			if(equipAbnormalEntity!=null){
				//流程状态(待提交)
				equipAbnormalEntity.setProcessStatus(EquipAbnormalEnum.WATISUBMIT.getCode());
				equipAbnormalEntity.setStatus(EquipLedgerEnum.DELETE.getId());
				equipAbnormalDao.updateEntity(equipAbnormalEntity);
			}
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMAL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj deleteEquipAbnormal(Long id) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> condition = new ArrayList<Condition>();
		condition.add(new Condition("C_EQUIP_ABNORMAL_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
		if(!list.isEmpty()){
			for(EquipAbnormalEquipRelEntity equipAbnormalEquipRel:list){
				equipAbnormalEquipRel.setStatus(EquipAbnormalEquipRelConstantEnum.DELETE.getId());
				equipAbnormalEquipRel.setUpdateUserId(userEntity.getId().toString());
				equipAbnormalEquipRel.setUpdateDate(new Date());
				equipAbnormalEquipRelService.updateEntity(equipAbnormalEquipRel);
			}
		}
		EquipAbnormalEntity equipAbnormalEntity  = equipAbnormalDao.findById(id);
		//流程状态(待提交)
		equipAbnormalEntity.setProcessStatus(EquipAbnormalEnum.WATISUBMIT.getCode());
		equipAbnormalEntity.setStatus(EquipLedgerEnum.DELETE.getId());
		equipAbnormalDao.updateEntity(equipAbnormalEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMAL.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}
}