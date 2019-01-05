package com.aptech.business.equip.equipAbnormalReport.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelConstantEnum;
import com.aptech.business.equip.equipAbnormalEquipRel.domain.EquipAbnormalEquipRelEntity;
import com.aptech.business.equip.equipAbnormalEquipRel.service.EquipAbnormalEquipRelService;
import com.aptech.business.equip.equipAbnormalReport.dao.EquipAbnormalReportDao;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportConstantEnum;
import com.aptech.business.equip.equipAbnormalReport.domain.EquipAbnormalReportEntity;
import com.aptech.business.equip.equipAbnormalReport.exception.EquipAbnormalReportException;
import com.aptech.business.equip.equipAbnormalReport.exception.EquipAbnormalReportExceptionType;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
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
 * 设备异动报告应用管理服务实现类
 *
 * @author 
 * @created 2018-09-14 13:48:29
 * @lastModified 
 * @history
 *
 */
@Service("equipAbnormalReportService")
@Transactional
public class EquipAbnormalReportServiceImpl extends AbstractBaseEntityOperation<EquipAbnormalReportEntity> implements EquipAbnormalReportService {
	
	@Autowired
	private EquipAbnormalReportDao equipAbnormalReportDao;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private EquipAbnormalEquipRelService equipAbnormalEquipRelService;
	@Override
	public IBaseEntityOperation<EquipAbnormalReportEntity> getDao() {
		return equipAbnormalReportDao;
	}

	@Override
	public ResultObj addEquipAbnormalReport(EquipAbnormalReportEntity equipAbnormalReportEntity,HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalReportEntity> list = new ArrayList<EquipAbnormalReportEntity>();
		if(equipAbnormalReportEntity.getEquipName()!=null && equipAbnormalReportEntity.getEquipCode()!=null){
			String tempCode = equipAbnormalReportEntity.getEquipCode();
			String tempName = equipAbnormalReportEntity.getEquipName();
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
		equipAbnormalReportEntity.setCreateDate(new Date());
		equipAbnormalReportEntity.setCreateUserId(userEntity.getId());
		equipAbnormalReportEntity.setProcessStatus(EquipAbnormalEnum.WATISUBMIT.getCode());
		equipAbnormalReportEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());;
		equipAbnormalReportDao.addEntity(equipAbnormalReportEntity);
		Long equipAbnormalReportId = equipAbnormalReportEntity.getId();
		for(EquipAbnormalReportEntity abnormalReportEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(abnormalReportEntity.getEquipCode());
			abnormalEquipRelEntity.setEquipName(abnormalReportEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setEquipAbnormalReportId(equipAbnormalReportId);
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMALREPORT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj updateEquipAbnormalReport(EquipAbnormalReportEntity equipAbnormalReportEntity,HttpServletRequest request) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<EquipAbnormalReportEntity> list = new ArrayList<EquipAbnormalReportEntity>();
		if(equipAbnormalReportEntity.getEquipName()!=null && equipAbnormalReportEntity.getEquipCode()!=null){
			String tempCode = equipAbnormalReportEntity.getEquipCode();
			String tempName = equipAbnormalReportEntity.getEquipName();
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
		equipAbnormalReportEntity.setCreateDate(new Date());
		equipAbnormalReportEntity.setCreateUserId(userEntity.getId());
		equipAbnormalReportEntity.setProcessStatus(EquipAbnormalEnum.WATISUBMIT.getCode());
		equipAbnormalReportEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());;
		//删除旧的设备数据
		equipAbnormalEquipRelService.deleteByCondition("delByEquipAbnormalReportId", equipAbnormalReportEntity.getId());
		//保存设备异动报告
		equipAbnormalReportDao.updateEntity(equipAbnormalReportEntity);
		//添加新的设备数据
		Long equipAbnormalReportId = equipAbnormalReportEntity.getId();
		for(EquipAbnormalReportEntity abnormalReportEntity:list){
			EquipAbnormalEquipRelEntity abnormalEquipRelEntity = new EquipAbnormalEquipRelEntity();
			abnormalEquipRelEntity.setEquipCode(abnormalReportEntity.getEquipCode());
			abnormalEquipRelEntity.setEquipName(abnormalReportEntity.getEquipName());
			abnormalEquipRelEntity.setCreateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setCreateDate(new Date());
			abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
			abnormalEquipRelEntity.setUpdateDate(new Date());
			abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.NORMAL.getId());
			abnormalEquipRelEntity.setEquipAbnormalReportId(equipAbnormalReportId);
			equipAbnormalEquipRelService.addEntity(abnormalEquipRelEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMALREPORT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj deleteOnlyOne(HttpServletRequest request,Long id) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> condition = new ArrayList<Condition>();
		condition.add(new Condition("C_EQUIP_ABNORMAL_REPORT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
		if(!list.isEmpty()){
			for(EquipAbnormalEquipRelEntity equipAbnormalEquipRel:list){
				equipAbnormalEquipRel.setStatus(EquipAbnormalEquipRelConstantEnum.DELETE.getId());
				equipAbnormalEquipRel.setUpdateUserId(userEntity.getId().toString());
				equipAbnormalEquipRel.setUpdateDate(new Date());
				equipAbnormalEquipRelService.updateEntity(equipAbnormalEquipRel);
			}
		}
		EquipAbnormalReportEntity abnormalReportEntity = equipAbnormalReportDao.findById(id);
		abnormalReportEntity.setStatus(EquipAbnormalReportConstantEnum.DELETE.getId());
		abnormalReportEntity.setUpdateUserId(userEntity.getId());
		abnormalReportEntity.setUpdateDate(new Date());
		equipAbnormalReportDao.updateEntity(abnormalReportEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMALREPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}
	

	@Override
	public ResultObj delSelectInfo(List<Long> ids) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		for (Long id : ids) {
			List<Condition> condition = new ArrayList<Condition>();
			condition.add(new Condition("C_EQUIP_ABNORMAL_REPORT_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
			List<EquipAbnormalEquipRelEntity> list = equipAbnormalEquipRelService.findByCondition(condition, null);
			if(!list.isEmpty()){
				for(EquipAbnormalEquipRelEntity abnormalEquipRelEntity:list){
					abnormalEquipRelEntity.setStatus(EquipAbnormalEquipRelConstantEnum.DELETE.getId());
					abnormalEquipRelEntity.setUpdateUserId(userEntity.getId().toString());
					abnormalEquipRelEntity.setUpdateDate(new Date());
					equipAbnormalEquipRelService.updateEntity(abnormalEquipRelEntity);
				}
			}
			EquipAbnormalReportEntity abnormalReportEntity = equipAbnormalReportDao.findById(id);
			abnormalReportEntity.setStatus(EquipAbnormalReportConstantEnum.DELETE.getId());
			abnormalReportEntity.setUpdateUserId(userEntity.getId());
			abnormalReportEntity.setUpdateDate(new Date());
			equipAbnormalReportDao.updateEntity(abnormalReportEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPABNORMALREPORT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		EquipAbnormalReportEntity equipAbnormalReportEntity = this.findById(id);
		if(validate(equipAbnormalReportEntity)){
			equipAbnormalReportEntity.setProcessStatus(EquipAbnormalEnum.WAITPROFESSIONWORK.getCode());
			this.updateEntity(equipAbnormalReportEntity);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
			actTaskService.startProcess(ProcessMarkEnum.EQUIP_ABNORMAL_REPORT_PROCESS_KEY.getName(), id.toString(), vars);
		}
		return resultObj;
	}
	

	/**
	 * 审核后继续下一步流程
	 */
	@Override
	public ResultObj approve(EquipAbnormalReportEntity equipAbnormalReportEntity,Map<String, Object> params) {
		if(validateStatus(equipAbnormalReportEntity)){
			// 修改状态
			EquipAbnormalReportEntity entity = this.findById(equipAbnormalReportEntity.getId());
			entity.setProcessStatus(params.get("processStatus").toString());
			entity.setStatus(EquipLedgerEnum.NORMAL.getId());
			this.updateEntity(entity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(equipAbnormalReportEntity.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get("result"));
			actTaskService.complete(equipAbnormalReportEntity.getTaskId(), equipAbnormalReportEntity.getProcInstId(),params);
		}
		return  new ResultObj();
	}
	
	/**
	 * @Description:   基本验证
	 * @author         wangcc 
	 * @Date           2018年9月20日 下午3:41:28 
	 * @throws         Exception
	 */
	public boolean validate(EquipAbnormalReportEntity equipAbnormalReportEntity) {
		if (equipAbnormalReportEntity == null) {
			throw new EquipAbnormalReportException(EquipAbnormalReportExceptionType.DEFECT_CODE_NULL);
		}
		if (!(equipAbnormalReportEntity.getProcessStatus().equals(EquipAbnormalEnum.WATISUBMIT.getCode()) || equipAbnormalReportEntity.getProcessStatus().equals(EquipAbnormalEnum.REJECT.getCode()))) {
			throw new EquipAbnormalReportException(EquipAbnormalReportExceptionType.DEFECT_CODE_STATUS);
		}
		return true;
	}
	
	/**
	 * @Description:   流程基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:01 
	 * @throws         Exception
	 */
	public boolean validateStatus(EquipAbnormalReportEntity equipAbnormalReportEntity) {
		EquipAbnormalReportEntity entity = this.findById(equipAbnormalReportEntity.getId());
		if (entity == null) {
			throw new EquipAbnormalReportException(
					EquipAbnormalReportExceptionType.DEFECT_CODE_NULL);
		}
		if (!equipAbnormalReportEntity.getProcessStatus().equals(entity.getProcessStatus())) {
			throw new EquipAbnormalReportException(
					EquipAbnormalReportExceptionType.DEFECT_CODE_REPEAT);
		}
		return true;
	}
}