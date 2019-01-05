package com.aptech.business.ticketManage.workElectricTwo.service;

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
import com.aptech.business.ticketManage.workElectricTwo.dao.WorkElectricTwoDao;
import com.aptech.business.ticketManage.workElectricTwo.domain.WorkElectricTwoEntity;
import com.aptech.business.ticketManage.workTicketTwo.domain.WorkTicketTwoEntity;
import com.aptech.business.ticketManage.workTicketTwo.service.WorkTicketTwoService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEnum;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
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
 * 电气工作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workElectricTwoService")
@Transactional
public class WorkElectricTwoServiceImpl extends AbstractBaseEntityOperation<WorkElectricTwoEntity> implements WorkElectricTwoService {
	
	@Autowired
	private WorkElectricTwoDao workElectricTwoDao;
	@Autowired
	private WorkTicketTwoService workTicketTwoService;
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
	public IBaseEntityOperation<WorkElectricTwoEntity> getDao() {
		return workElectricTwoDao;
	}
	@Override
	public void updateSpnrAgree(WorkElectricTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setSignerId(userEntity.getId());
			workElectricTwoEntity.setSignerName(userEntity.getName());
			workElectricTwoEntity.setSignerDate(new Date());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketTwoEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketTwoEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			workTicketTwoService.updateEntity(workTicketTwoEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workElectricTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setOndutyId(userEntity.getId());
			workElectricTwoEntity.setOndutyName(userEntity.getName());
			workElectricTwoEntity.setGetticketTime(new Date());
			super.updateEntity(workElectricTwoEntity);
			
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//			SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
			SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketEntity.getUnitNameId()));
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "3");
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
			String str="";
			StringBuilder sb=new StringBuilder(code);
			sb.replace(3, 5, str);
			String nowCode=sb.toString();
			//工作票编号结束
			
			//更新主表状态
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			workTicketEntity.setCode(nowCode);
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setDutyMonitorId(userEntity.getId());
			workElectricTwoEntity.setDutyMonitorName(userEntity.getName());
			workElectricTwoEntity.setApproveStarttime(t.getApproveStarttime());
			workElectricTwoEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态为未执行
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkElectricTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketTwoEntity workEntity=workTicketTwoService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			
			
			workTicketTwoService.updateEntity(workEntity);
			//更新电气第一种票表  
			elentity.setId(t.getId());
			elentity.setAllowPicPersonId(t.getAllowPicPersonId());
			elentity.setAllowPicPersonName(t.getAllowPicPersonName());
			elentity.setHand(t.getHand());
			elentity.setHandFlag(t.getHandFlag());
			elentity.setOther(t.getOther());
			elentity.setWireway(t.getWireway());
			elentity.setQuarantine(t.getQuarantine());
			elentity.setOtherSafe(t.getOtherSafe());
			super.updateEntity(elentity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(elentity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBG.getCode())){//工作负责人变更
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workElectricTwoEntity.setChangeOldPicId(t.getChangeOldPicId());
			workElectricTwoEntity.setChangeOldPicName(t.getChangeOldPicName());
			workElectricTwoEntity.setChangeNewPicId(t.getChangeNewPicId());
			workElectricTwoEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			workTicketEntity.setGuarderId(t.getChangeNewPicId());
			workTicketEntity.setGuarderName(workElectricTwoEntity.getChangeNewPicName());
			workTicketTwoService.updateEntity(workTicketEntity);
			
			//获取三种人记录修改工作负责人
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricTwoEntity.getWorkticketId()));
			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
			for (WorkTicketUserRelEntity entity : list) {
				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
				workTicketUserRelService.updateByMap("updateInfoByMap", entity);
			}
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setChangeSignerId(t.getChangeSignerId());
			workElectricTwoEntity.setChangeSignerName(t.getChangeSignerName());
			workElectricTwoEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workElectricTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setChangeAllowId(t.getChangeAllowId());
			workElectricTwoEntity.setChangeAllowName(t.getChangeAllowName());
			workElectricTwoEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
			
//			//获取三种人记录修改工作负责人
//			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
//			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricTwoEntity.getWorkticketId()));
//			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
//			for (WorkTicketUserRelEntity entity : list) {
//				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
//			}
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workElectricTwoEntity.setWorkPersonPicId(userEntity.getId());
			workElectricTwoEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workElectricTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setWorkDisclosure(t.getWorkDisclosure());
			super.updateEntity(workElectricTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workElectricTwoEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workElectricTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setDelayDate(t.getDelayDate());
			workElectricTwoEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			workElectricTwoEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setDelayAllowId(t.getDelayAllowId());
			workElectricTwoEntity.setDelayAllowName(t.getDelayAllowName());
			workElectricTwoEntity.setDelayDate(t.getDelayDate());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWPIC.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setDelayPicId(t.getDelayPicId());
			workElectricTwoEntity.setDelayPicName(t.getDelayPicName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricTwoEntity.setEndGroupIndex(t.getEndGroupIndex());
			workElectricTwoEntity.setEndGroup(t.getEndGroup());
			workElectricTwoEntity.setEndStandIndex(t.getEndStandIndex());
			workElectricTwoEntity.setEndStand(t.getEndStand());
			workElectricTwoEntity.setRecoverOther(t.getRecoverOther());
			super.updateEntity(workElectricTwoEntity);
			
			WorkTicketTwoEntity workEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			workTicketTwoService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketTwoEntity workEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			workEntity.setChangeAllowIdea(t.getFileid());
			workTicketTwoService.updateEntity(workEntity);
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricTwoEntity.setHeatCode(t.getHeatCode());
			workElectricTwoEntity.setHeatPersonName(t.getHeatPersonName());
			workElectricTwoEntity.setElectricCode(t.getElectricCode());
			workElectricTwoEntity.setElectricPersonName(t.getElectricPersonName());
			workElectricTwoEntity.setMachineCode(t.getMachineCode());
			workElectricTwoEntity.setMachinePersonName(t.getMachinePersonName());
			workElectricTwoEntity.setRecoverOther(t.getRecoverOther());
			workElectricTwoEntity.setRunPicId(t.getRunPicId());
			workElectricTwoEntity.setRunPicName(t.getRunPicName());
			workElectricTwoEntity.setRunAllowId(t.getRunAllowId());
			workElectricTwoEntity.setRunAllowName(t.getRunAllowName());
			workElectricTwoEntity.setRunManagerId(t.getRunManagerId());
			workElectricTwoEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWAPPLY.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			workElectricTwoEntity.setRunSureDate(t.getRunSureDate());
			workElectricTwoEntity.setRunAllowId(t.getRunAllowId());
			workElectricTwoEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERAPPLY.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			workElectricTwoEntity.setRunManagerId(t.getRunManagerId());
			workElectricTwoEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.APPLY.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricTwoEntity.setStopPicId(t.getStopPicId());
			workElectricTwoEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWREGAIN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricTwoEntity.setStopSureDate(t.getStopSureDate());
			workElectricTwoEntity.setStopAllowId(t.getStopAllowId());
			workElectricTwoEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERREGAIN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricTwoEntity.setStopManagerId(t.getStopManagerId());
			workElectricTwoEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.REGAIN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkElectricTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workElectricTwoEntity.setSignerId(null);
			workElectricTwoEntity.setSignerName(null);
			workElectricTwoEntity.setSignerDate(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态为驳回
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricTwoEntity.setSignerId(null);
			workElectricTwoEntity.setSignerName(null);
			workElectricTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricTwoEntity.setOndutyId(null);
			workElectricTwoEntity.setOndutyName(null);
			workElectricTwoEntity.setGetticketTime(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态为驳回
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricTwoEntity.setSignerId(null);
			workElectricTwoEntity.setSignerName(null);
			workElectricTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricTwoEntity.setOndutyId(null);
			workElectricTwoEntity.setOndutyName(null);
			workElectricTwoEntity.setGetticketTime(null);
			//清空许可人添加的信息
			workElectricTwoEntity.setAllowPicPersonId(null);
			workElectricTwoEntity.setAllowPicPersonName(null);
			workElectricTwoEntity.setHand(null);
			workElectricTwoEntity.setHandFlag(null);
			workElectricTwoEntity.setOther(null);
			workElectricTwoEntity.setWireway(null);
			workElectricTwoEntity.setQuarantine(null);
			workElectricTwoEntity.setOtherSafe("");
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态为驳回
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEntity.setChangeAllowDate(null);
			workTicketEntity.setChangeAllowId(null);
			workTicketEntity.setChangeAllowName(null);
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricTwoEntity.setSignerId(null);
			workElectricTwoEntity.setSignerName(null);
			workElectricTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricTwoEntity.setOndutyId(null);
			workElectricTwoEntity.setOndutyName(null);
			workElectricTwoEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workElectricTwoEntity.setDutyMonitorId(null);
			workElectricTwoEntity.setDutyMonitorName(null);
			workElectricTwoEntity.setApproveStarttime(null);
			workElectricTwoEntity.setApproveEndtime(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态为驳回
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricTwoEntity.setChangeOldPicId(null);
			workElectricTwoEntity.setChangeOldPicName(null);
			workElectricTwoEntity.setChangeNewPicId(null);
			workElectricTwoEntity.setChangeNewPicName(null);
			workElectricTwoEntity.setChangeSignerId(null);
			workElectricTwoEntity.setChangeSignerName(null);
			workElectricTwoEntity.setChangeSignerDate(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricTwoEntity.setChangeOldPicId(null);
			workElectricTwoEntity.setChangeOldPicName(null);
			workElectricTwoEntity.setChangeNewPicId(null);
			workElectricTwoEntity.setChangeNewPicName(null);
			workElectricTwoEntity.setChangeSignerId(null);
			workElectricTwoEntity.setChangeSignerName(null);
			workElectricTwoEntity.setChangeSignerDate(null);
			workElectricTwoEntity.setChangeAllowId(null);
			workElectricTwoEntity.setChangeAllowName(null);
			workElectricTwoEntity.setChangeAllowDate(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setWorkPersonGroup(null);
			workElectricTwoEntity.setWorkPersonPicId(null);
			workElectricTwoEntity.setWorkPersonPicName(null);
			super.updateEntity(workElectricTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkElectricTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			workElectricTwoEntity.setDelayDate(null);
			workElectricTwoEntity.setDelayDutyMonitorId(null);
			workElectricTwoEntity.setDelayDutyMonitorName(null);
			super.updateEntity(workElectricTwoEntity);
			//更新主表状态
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkElectricTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			super.updateEntity(elentity);
			
			WorkTicketTwoEntity workEntity=workTicketTwoService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketTwoService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkElectricTwoEntity workElectricTwoTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(workElectricTwoTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketTwoService.updateEntity(workTicketEntity);
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
  		    String key=ProcessMarkEnum.WORK_TICKET_SECOND_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketTwoEntity workTicketEntity=workTicketTwoService.findById(Long.valueOf(id));
			workTicketEntity.setId(Long.valueOf(id));
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketTwoService.updateEntity(workTicketEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkElectricTwoEntity t, Long id) {
		WorkElectricTwoEntity workElectricTwoEntity=this.findById(id);
		workElectricTwoEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workElectricTwoEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workElectricTwoEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workElectricTwoEntity);
		return new ResultObj();
	}
}