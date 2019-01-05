package com.aptech.business.ticketManage.workEarth.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.ticketManage.workEarth.dao.WorkEarthDao;
import com.aptech.business.ticketManage.workEarth.domain.WorkEarthEntity;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketEarth.domain.WorkTicketEarthEntity;
import com.aptech.business.ticketManage.workTicketEarth.service.WorkTicketEarthService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("WorkEarthService")
@Transactional
public class WorkEarthServiceImpl extends AbstractBaseEntityOperation<WorkEarthEntity> implements WorkEarthService {
	
	@Autowired
	private WorkEarthDao workEarthDao;
	@Autowired
	private WorkTicketEarthService workTicketEarthService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
    private WorkSafeService workSafeService;
	@Override
	public IBaseEntityOperation<WorkEarthEntity> getDao() {
		return workEarthDao;
	}
	@Override
	public void updateSpnrAgree(WorkEarthEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setSignerId(userEntity.getId());
			workEarthEntity.setSignerName(userEntity.getName());
			workEarthEntity.setSignerDate(new Date());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setOndutyId(userEntity.getId());
			workEarthEntity.setOndutyName(userEntity.getName());
			workEarthEntity.setGetticketTime(new Date());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.LONGVALUESURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
			//更新safe表   把空着的workid 变成  真的
	        WorkSafeEntity workSafeEntity=new WorkSafeEntity();
	        workSafeEntity.setWorkticketId(workEarthEntity.getWorkticketId());
	        workSafeEntity.setUuidCode(t.getHeatCode());
	        workSafeService.updateByMap("updateByUuid", workSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setDutyMonitorId(userEntity.getId());
			workEarthEntity.setDutyMonitorName(userEntity.getName());
			workEarthEntity.setApproveStarttime(t.getApproveStarttime());
			workEarthEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workEarthEntity);
			//更新主表状态为未执行
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkEarthEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketEarthEntity workEntity=workTicketEarthService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			//许可的时候需要生产工作票编号
			SysUnitEntity sysUnitEntity =sysUnitService.findById(Long.valueOf(workEntity.getUnitNameId()));
			SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHssmm");
			String date=sf.format(new Date());
			workEntity.setCode("DY"+date);
			workTicketEarthService.updateEntity(workEntity);
			//更新电气第一种票表  
			elentity.setId(t.getId());
			elentity.setAllowPicPersonId(t.getAllowPicPersonId());
			elentity.setAllowPicPersonName(t.getAllowPicPersonName());
			super.updateEntity(elentity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBG.getCode())){//工作负责人变更
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workEarthEntity.setChangeOldPicId(t.getChangeOldPicId());
			workEarthEntity.setChangeOldPicName(t.getChangeOldPicName());
			workEarthEntity.setChangeNewPicId(t.getChangeNewPicId());
			workEarthEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setChangeSignerId(t.getChangeSignerId());
			workEarthEntity.setChangeSignerName(t.getChangeSignerName());
			workEarthEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setChangeAllowId(t.getChangeAllowId());
			workEarthEntity.setChangeAllowName(t.getChangeAllowName());
			workEarthEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workEarthEntity.setWorkPersonPicId(userEntity.getId());
			workEarthEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workEarthEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERDELAY.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setDelayDate(t.getDelayDate());
			workEarthEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			workEarthEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setDelayAllowId(t.getDelayAllowId());
			workEarthEntity.setDelayAllowName(t.getDelayAllowName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWPIC.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setDelayPicId(t.getDelayPicId());
			workEarthEntity.setDelayPicName(t.getDelayPicName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workEarthEntity.setEndGroupIndex(t.getEndGroupIndex());
			workEarthEntity.setEndGroup(t.getEndGroup());
			workEarthEntity.setEndStandIndex(t.getEndStandIndex());
			workEarthEntity.setEndStand(t.getEndStand());
			super.updateEntity(workEarthEntity);
			
			WorkTicketEarthEntity workEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			workTicketEarthService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketEarthEntity workEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			workTicketEarthService.updateEntity(workEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			//更新电气第一种票表
			workEarthEntity.setHeatCode(t.getHeatCode());
			workEarthEntity.setHeatPersonName(t.getHeatPersonName());
			workEarthEntity.setElectricCode(t.getElectricCode());
			workEarthEntity.setElectricPersonName(t.getElectricPersonName());
			workEarthEntity.setMachineCode(t.getMachineCode());
			workEarthEntity.setMachinePersonName(t.getMachinePersonName());
			workEarthEntity.setRecoverOther(t.getRecoverOther());
			workEarthEntity.setRunPicId(t.getRunPicId());
			workEarthEntity.setRunPicName(t.getRunPicName());
			workEarthEntity.setRunAllowId(t.getRunAllowId());
			workEarthEntity.setRunAllowName(t.getRunAllowName());
			workEarthEntity.setRunManagerId(t.getRunManagerId());
			workEarthEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWAPPLY.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			workEarthEntity.setRunSureDate(t.getRunSureDate());
			workEarthEntity.setRunAllowId(t.getRunAllowId());
			workEarthEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERAPPLY.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			workEarthEntity.setRunManagerId(t.getRunManagerId());
			workEarthEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.APPLY.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			//更新电气第一种票表
			workEarthEntity.setStopPicId(t.getStopPicId());
			workEarthEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWREGAIN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			//更新电气第一种票表
			workEarthEntity.setStopSureDate(t.getStopSureDate());
			workEarthEntity.setStopAllowId(t.getStopAllowId());
			workEarthEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERREGAIN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkEarthEntity workEarthEntity=this.findById(t.getId());
			//更新电气第一种票表
			workEarthEntity.setStopManagerId(t.getStopManagerId());
			workEarthEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.REGAIN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(t.getId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkEarthEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workEarthEntity.setSignerId(null);
			workEarthEntity.setSignerName(null);
			workEarthEntity.setSignerDate(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态为驳回
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workEarthEntity.setSignerId(null);
			workEarthEntity.setSignerName(null);
			workEarthEntity.setSignerDate(null);
			//上一步的字段也清空2
			workEarthEntity.setOndutyId(null);
			workEarthEntity.setOndutyName(null);
			workEarthEntity.setGetticketTime(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态为驳回
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workEarthEntity.setSignerId(null);
			workEarthEntity.setSignerName(null);
			workEarthEntity.setSignerDate(null);
			//上一步的字段也清空2
			workEarthEntity.setOndutyId(null);
			workEarthEntity.setOndutyName(null);
			workEarthEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workEarthEntity.setDutyMonitorId(null);
			workEarthEntity.setDutyMonitorName(null);
			workEarthEntity.setApproveStarttime(null);
			workEarthEntity.setApproveEndtime(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态为驳回
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workEarthEntity.setChangeOldPicId(null);
			workEarthEntity.setChangeOldPicName(null);
			workEarthEntity.setChangeNewPicId(null);
			workEarthEntity.setChangeNewPicName(null);
			workEarthEntity.setChangeSignerId(null);
			workEarthEntity.setChangeSignerName(null);
			workEarthEntity.setChangeSignerDate(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workEarthEntity.setChangeOldPicId(null);
			workEarthEntity.setChangeOldPicName(null);
			workEarthEntity.setChangeNewPicId(null);
			workEarthEntity.setChangeNewPicName(null);
			workEarthEntity.setChangeSignerId(null);
			workEarthEntity.setChangeSignerName(null);
			workEarthEntity.setChangeSignerDate(null);
			workEarthEntity.setChangeAllowId(null);
			workEarthEntity.setChangeAllowName(null);
			workEarthEntity.setChangeAllowDate(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setWorkPersonGroup(null);
			workEarthEntity.setWorkPersonPicId(null);
			workEarthEntity.setWorkPersonPicName(null);
			super.updateEntity(workEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkEarthEntity workEarthEntity = this.findById(t.getId());//查询这个表的实体
			workEarthEntity.setDelayDate(null);
			workEarthEntity.setDelayDutyMonitorId(null);
			workEarthEntity.setDelayDutyMonitorName(null);
			super.updateEntity(workEarthEntity);
			//更新主表状态
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workEarthEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkEarthEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			super.updateEntity(elentity);
			
			WorkTicketEarthEntity workEntity=workTicketEarthService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEarthService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkEarthEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketEarthService.updateEntity(workTicketEarthEntity);
		}
	}
	/**提交电气第一种工作票
	 * @Description:   
	 * @author         sunliang 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketEarthEntity workTicketEarthEntity=workTicketEarthService.findById(Long.valueOf(id));
			workTicketEarthEntity.setId(Long.valueOf(id));
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketEarthService.updateEntity(workTicketEarthEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         sunliang 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkEarthEntity t, Long id) {
		WorkEarthEntity workEarthEntity=this.findById(id);
		workEarthEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workEarthEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workEarthEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workEarthEntity);
		return new ResultObj();
	}
}