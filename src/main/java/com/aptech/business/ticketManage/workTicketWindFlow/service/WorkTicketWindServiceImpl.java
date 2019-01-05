package com.aptech.business.ticketManage.workTicketWindFlow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.business.ticketManage.workTicketWindFlow.dao.WorkTicketWindDao;
import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.business.ticketManage.workTicketWindMechanical.service.WorkTicketWindMechanicalService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 风工机械作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workTicketWindService")
@Transactional
public class WorkTicketWindServiceImpl extends AbstractBaseEntityOperation<WorkTicketWindEntity> implements WorkTicketWindService {
	
/*	@Autowired
	private WorkElectricDao workElectricDao;*/
	@Autowired
	private WorkTicketWindDao workTicketWindDao;
	@Autowired
	private WorkTicketWindMechanicalService workTicketWindMechanicalService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkTicketWindEntity> getDao() {
		return workTicketWindDao;
	}
	@Override
	public void updateSpnrAgree(WorkTicketWindEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setSignerId(userEntity.getId());
			WorkTicketWindEntity.setSignerName(userEntity.getName());
			WorkTicketWindEntity.setSignerDate(new Date());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(WorkTicketWindEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketWindEntity.setOndutyId(userEntity.getId());
			WorkTicketWindEntity.setOndutyName(userEntity.getName());
			WorkTicketWindEntity.setGetticketTime(new Date());
			super.updateEntity(WorkTicketWindEntity);
			
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//			SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
			SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketEntity.getUnitNameId()));
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "6");
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
			String str="";
			StringBuilder sb=new StringBuilder(code);
			sb.replace(3, 5, str);
			String nowCode=sb.toString();
			//工作票编号结束
			
			//更新主表状态
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.LONGVALUESURE.getCode()));
			workTicketEntity.setCode(nowCode);
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDutyMonitorId(userEntity.getId());
			WorkTicketWindEntity.setDutyMonitorName(userEntity.getName());
			WorkTicketWindEntity.setApproveStarttime(t.getApproveStarttime());
			WorkTicketWindEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态为未执行
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkTicketWindEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketEntity workEntity=workTicketWindMechanicalService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketWindMechanicalService.updateEntity(workEntity);
			//更新电气第一种票表  
			elentity.setId(t.getId());
			elentity.setAllowPicPersonId(t.getAllowPicPersonId());
			elentity.setAllowPicPersonName(t.getAllowPicPersonName());
			elentity.setRemarkResponsibleName(t.getRemarkResponsibleName());
			super.updateEntity(elentity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(elentity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBG.getCode())){//工作负责人变更
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			WorkTicketWindEntity.setChangeOldPicId(t.getChangeOldPicId());
			WorkTicketWindEntity.setChangeOldPicName(t.getChangeOldPicName());
			WorkTicketWindEntity.setChangeNewPicId(t.getChangeNewPicId());
			WorkTicketWindEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			workTicketEntity.setGuarderId(t.getChangeNewPicId());
			workTicketEntity.setGuarderName(WorkTicketWindEntity.getChangeNewPicName());
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			

			//获取三种人记录修改工作负责人
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketWindEntity.getWorkticketId()));
			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
			for (WorkTicketUserRelEntity entity : list) {
				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
				workTicketUserRelService.updateByMap("updateInfoByMap", entity);
			}
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setChangeSignerId(t.getChangeSignerId());
			WorkTicketWindEntity.setChangeSignerName(t.getChangeSignerName());
			WorkTicketWindEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(WorkTicketWindEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setChangeAllowId(t.getChangeAllowId());
			WorkTicketWindEntity.setChangeAllowName(t.getChangeAllowName());
			WorkTicketWindEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(WorkTicketWindEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			WorkTicketWindEntity.setWorkPersonPicId(userEntity.getId());
			WorkTicketWindEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkCondition(t.getWorkCondition());
			super.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			WorkTicketWindEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERDELAY.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayDate(t.getDelayDate());
			WorkTicketWindEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			WorkTicketWindEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayAllowId(t.getDelayAllowId());
			WorkTicketWindEntity.setDelayAllowName(t.getDelayAllowName());
			WorkTicketWindEntity.setDelayDate(t.getDelayDate());
			WorkTicketWindEntity.setDelayAllowSureDate(new Date());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWPIC.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(WorkTicketWindEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayPicId(t.getDelayPicId());
			WorkTicketWindEntity.setDelayPicName(t.getDelayPicName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			WorkTicketWindEntity.setEndGroupIndex(t.getEndGroupIndex());
			WorkTicketWindEntity.setEndGroup(t.getEndGroup());
			WorkTicketWindEntity.setEndStandIndex(t.getEndStandIndex());
			WorkTicketWindEntity.setEndStand(t.getEndStand());
			super.updateEntity(WorkTicketWindEntity);
			
			WorkTicketEntity workEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			workTicketWindMechanicalService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setRemarkOther(t.getRemarkOther());
			WorkTicketWindEntity.setRunSureDate(new Date());
			super.updateEntity(WorkTicketWindEntity);
			
			WorkTicketEntity workEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			workEntity.setChangeAllowIdea(t.getFileid());
			workTicketWindMechanicalService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(WorkTicketWindEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setHeatCode(t.getHeatCode());
			WorkTicketWindEntity.setHeatPersonName(t.getHeatPersonName());
			WorkTicketWindEntity.setElectricCode(t.getElectricCode());
			WorkTicketWindEntity.setElectricPersonName(t.getElectricPersonName());
			WorkTicketWindEntity.setMachineCode(t.getMachineCode());
			WorkTicketWindEntity.setMachinePersonName(t.getMachinePersonName());
			WorkTicketWindEntity.setRecoverOther(t.getRecoverOther());
			WorkTicketWindEntity.setRunPicId(t.getRunPicId());
			WorkTicketWindEntity.setRunPicName(t.getRunPicName());
			WorkTicketWindEntity.setRunAllowId(t.getRunAllowId());
			WorkTicketWindEntity.setRunAllowName(t.getRunAllowName());
			WorkTicketWindEntity.setRunManagerId(t.getRunManagerId());
			WorkTicketWindEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWAPPLY.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			WorkTicketWindEntity.setRunSureDate(t.getRunSureDate());
			WorkTicketWindEntity.setRunAllowId(t.getRunAllowId());
			WorkTicketWindEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERAPPLY.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			WorkTicketWindEntity.setRunManagerId(t.getRunManagerId());
			WorkTicketWindEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.APPLY.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopPicId(t.getStopPicId());
			WorkTicketWindEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWREGAIN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopSureDate(t.getStopSureDate());
			WorkTicketWindEntity.setStopAllowId(t.getStopAllowId());
			WorkTicketWindEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERREGAIN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=this.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopManagerId(t.getStopManagerId());
			WorkTicketWindEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.REGAIN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkTicketWindEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			//上一步的字段也清空2
			WorkTicketWindEntity.setOndutyId(null);
			WorkTicketWindEntity.setOndutyName(null);
			WorkTicketWindEntity.setGetticketTime(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			//上一步的字段也清空2
			WorkTicketWindEntity.setOndutyId(null);
			WorkTicketWindEntity.setOndutyName(null);
			WorkTicketWindEntity.setGetticketTime(null);
			//清空许可人填写的信息
			WorkTicketWindEntity.setAllowPicPersonId(null);
			WorkTicketWindEntity.setAllowPicPersonName(null);
			WorkTicketWindEntity.setRemarkResponsibleName("");
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEntity.setChangeAllowDate(null);
			workTicketEntity.setChangeAllowId(null);
			workTicketEntity.setChangeAllowName(null);
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			//上一步的字段也清空2
			WorkTicketWindEntity.setOndutyId(null);
			WorkTicketWindEntity.setOndutyName(null);
			WorkTicketWindEntity.setGetticketTime(null);
			//上一步的字段也清空3
			WorkTicketWindEntity.setDutyMonitorId(null);
			WorkTicketWindEntity.setDutyMonitorName(null);
			WorkTicketWindEntity.setApproveStarttime(null);
			WorkTicketWindEntity.setApproveEndtime(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			WorkTicketWindEntity.setChangeOldPicId(null);
			WorkTicketWindEntity.setChangeOldPicName(null);
			WorkTicketWindEntity.setChangeNewPicId(null);
			WorkTicketWindEntity.setChangeNewPicName(null);
			WorkTicketWindEntity.setChangeSignerId(null);
			WorkTicketWindEntity.setChangeSignerName(null);
			WorkTicketWindEntity.setChangeSignerDate(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			WorkTicketWindEntity.setChangeOldPicId(null);
			WorkTicketWindEntity.setChangeOldPicName(null);
			WorkTicketWindEntity.setChangeNewPicId(null);
			WorkTicketWindEntity.setChangeNewPicName(null);
			WorkTicketWindEntity.setChangeSignerId(null);
			WorkTicketWindEntity.setChangeSignerName(null);
			WorkTicketWindEntity.setChangeSignerDate(null);
			WorkTicketWindEntity.setChangeAllowId(null);
			WorkTicketWindEntity.setChangeAllowName(null);
			WorkTicketWindEntity.setChangeAllowDate(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonGroup(null);
			WorkTicketWindEntity.setWorkPersonPicId(null);
			WorkTicketWindEntity.setWorkPersonPicName(null);
			super.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = this.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayDate(null);
			WorkTicketWindEntity.setDelayDutyMonitorId(null);
			WorkTicketWindEntity.setDelayDutyMonitorName(null);
			super.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkTicketWindEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			super.updateEntity(elentity);
			
			WorkTicketEntity workEntity=workTicketWindMechanicalService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketWindMechanicalService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkTicketWindEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
		}
	}
	/**提交电气第一种工作票
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           2017年6月12日 下午6:10:01 
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketEntity workTicketEntity=workTicketWindMechanicalService.findById(Long.valueOf(id));
			workTicketEntity.setId(Long.valueOf(id));
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketWindMechanicalService.updateEntity(workTicketEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkTicketWindEntity t, Long id) {
		WorkTicketWindEntity WorkTicketWindEntity=this.findById(id);
		
		if(t.getRemarkGuarderId()!=0){
			WorkTicketWindEntity.setRemarkGuarderId(t.getRemarkGuarderId());;
			WorkTicketWindEntity.setRemarkGuarderName(sysUserService.findById(t.getRemarkGuarderId()).getName());
		}
		WorkTicketWindEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		WorkTicketWindEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(WorkTicketWindEntity);
		return new ResultObj();
	}
}