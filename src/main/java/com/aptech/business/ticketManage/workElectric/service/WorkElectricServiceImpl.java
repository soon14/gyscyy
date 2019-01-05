package com.aptech.business.ticketManage.workElectric.service;

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
import com.aptech.business.ticketManage.workElectric.dao.WorkElectricDao;
import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
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
import com.aptech.framework.context.RequestContext;
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
@Service("workElectricService")
@Transactional
public class WorkElectricServiceImpl extends AbstractBaseEntityOperation<WorkElectricEntity> implements WorkElectricService {
	
	@Autowired
	private WorkElectricDao workElectricDao;
	@Autowired
	private WorkTicketService workTicketService;
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
	public IBaseEntityOperation<WorkElectricEntity> getDao() {
		return workElectricDao;
	}
	@Override
	public void updateSpnrAgree(WorkElectricEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setSignerId(userEntity.getId());
			workElectricEntity.setSignerName(userEntity.getName());
			workElectricEntity.setSignerDate(new Date());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			workTicketService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workElectricEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setOndutyId(userEntity.getId());
			workElectricEntity.setOndutyName(userEntity.getName());
			workElectricEntity.setGetticketTime(new Date());
			super.updateEntity(workElectricEntity);
			
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//			SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
			SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketEntity.getUnitNameId()));
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "2");
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
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setDutyMonitorId(userEntity.getId());
			workElectricEntity.setDutyMonitorName(userEntity.getName());
			workElectricEntity.setApproveStarttime(t.getApproveStarttime());
			workElectricEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workElectricEntity);
			//更新主表状态为未执行
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkElectricEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketEntity workEntity=workTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			
			workTicketService.updateEntity(workEntity);
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
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workElectricEntity.setChangeOldPicId(t.getChangeOldPicId());
			workElectricEntity.setChangeOldPicName(t.getChangeOldPicName());
			workElectricEntity.setChangeNewPicId(t.getChangeNewPicId());
			workElectricEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			workTicketEntity.setGuarderId(t.getChangeNewPicId());
			workTicketEntity.setGuarderName(workElectricEntity.getChangeNewPicName());
			workTicketService.updateEntity(workTicketEntity);
			
			//获取三种人记录修改工作负责人
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricEntity.getWorkticketId()));
			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
			for (WorkTicketUserRelEntity entity : list) {
				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
				workTicketUserRelService.updateByMap("updateInfoByMap", entity);
			}
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setChangeSignerId(t.getChangeSignerId());
			workElectricEntity.setChangeSignerName(t.getChangeSignerName());
			workElectricEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			workTicketService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workElectricEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setChangeAllowId(t.getChangeAllowId());
			workElectricEntity.setChangeAllowName(t.getChangeAllowName());
			workElectricEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketService.updateEntity(workTicketEntity);
			
//			//获取三种人记录修改工作负责人
//			List<Condition> conditions = new ArrayList<Condition>();
//			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
//			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workElectricEntity.getWorkticketId()));
//			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
//			for (WorkTicketUserRelEntity entity : list) {
//				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
//			}
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workElectricEntity.setWorkPersonPicId(userEntity.getId());
			workElectricEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workElectricEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setWorkDisclosure(t.getWorkDisclosure());
			super.updateEntity(workElectricEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workElectricEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workElectricEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setDelayDate(t.getDelayDate());
			workElectricEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			workElectricEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setDelayAllowId(t.getDelayAllowId());
			workElectricEntity.setDelayAllowName(t.getDelayAllowName());
			workElectricEntity.setDelayDate(t.getDelayDate());
			workElectricEntity.setDelayPicSureDate(new Date());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWPIC.getCode()));
			workTicketService.updateEntity(workTicketEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setDelayPicId(t.getDelayPicId());
			workElectricEntity.setDelayPicName(t.getDelayPicName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricEntity.setEndGroupIndex(t.getEndGroupIndex());
			workElectricEntity.setEndGroup(t.getEndGroup());
			workElectricEntity.setEndStandIndex(t.getEndStandIndex());
			workElectricEntity.setEndStand(t.getEndStand());
			workElectricEntity.setRecoverOther(t.getRecoverOther());
			super.updateEntity(workElectricEntity);
			
			WorkTicketEntity workEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			workTicketService.updateEntity(workEntity);
			WorkElectricEntity elentity = this.findById(t.getId());//查询这个表的实体
			elentity.setRemarkOther(t.getRemarkOther());
			super.updateEntity(elentity);

		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketEntity workEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setChangeAllowIdea(t.getFileid());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			workTicketService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workElectricEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricEntity.setHeatCode(t.getHeatCode());
			workElectricEntity.setHeatPersonName(t.getHeatPersonName());
			workElectricEntity.setElectricCode(t.getElectricCode());
			workElectricEntity.setElectricPersonName(t.getElectricPersonName());
			workElectricEntity.setMachineCode(t.getMachineCode());
			workElectricEntity.setMachinePersonName(t.getMachinePersonName());
			workElectricEntity.setRecoverOther(t.getRecoverOther());
			workElectricEntity.setRunPicId(t.getRunPicId());
			workElectricEntity.setRunPicName(t.getRunPicName());
			workElectricEntity.setRunAllowId(t.getRunAllowId());
			workElectricEntity.setRunAllowName(t.getRunAllowName());
			workElectricEntity.setRunManagerId(t.getRunManagerId());
			workElectricEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWAPPLY.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			workElectricEntity.setRunSureDate(t.getRunSureDate());
			workElectricEntity.setRunAllowId(t.getRunAllowId());
			workElectricEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERAPPLY.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			workElectricEntity.setRunManagerId(t.getRunManagerId());
			workElectricEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.APPLY.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricEntity.setStopPicId(t.getStopPicId());
			workElectricEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWREGAIN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricEntity.setStopSureDate(t.getStopSureDate());
			workElectricEntity.setStopAllowId(t.getStopAllowId());
			workElectricEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERREGAIN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkElectricEntity workElectricEntity=this.findById(t.getId());
			//更新电气第一种票表
			workElectricEntity.setStopManagerId(t.getStopManagerId());
			workElectricEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.REGAIN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkElectricEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workElectricEntity.setSignerId(null);
			workElectricEntity.setSignerName(null);
			workElectricEntity.setSignerDate(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricEntity.setSignerId(null);
			workElectricEntity.setSignerName(null);
			workElectricEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricEntity.setOndutyId(null);
			workElectricEntity.setOndutyName(null);
			workElectricEntity.setGetticketTime(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricEntity.setSignerId(null);
			workElectricEntity.setSignerName(null);
			workElectricEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricEntity.setOndutyId(null);
			workElectricEntity.setOndutyName(null);
			workElectricEntity.setGetticketTime(null);
			
			//清空许可人填写信息
			workElectricEntity.setAllowPicPersonId(null);
			workElectricEntity.setAllowPicPersonName(null);
			workElectricEntity.setHand(null);
			workElectricEntity.setHandFlag(null);
			workElectricEntity.setOther(null);
			workElectricEntity.setWireway(null);
			workElectricEntity.setQuarantine(null);
			workElectricEntity.setOtherSafe("");
			super.updateEntity(workElectricEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketEntity.setChangeAllowDate(null);
			workTicketEntity.setChangeAllowId(null);
			workTicketEntity.setChangeAllowName(null);
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workElectricEntity.setSignerId(null);
			workElectricEntity.setSignerName(null);
			workElectricEntity.setSignerDate(null);
			//上一步的字段也清空2
			workElectricEntity.setOndutyId(null);
			workElectricEntity.setOndutyName(null);
			workElectricEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workElectricEntity.setDutyMonitorId(null);
			workElectricEntity.setDutyMonitorName(null);
			workElectricEntity.setApproveStarttime(null);
			workElectricEntity.setApproveEndtime(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricEntity.setChangeOldPicId(null);
			workElectricEntity.setChangeOldPicName(null);
			workElectricEntity.setChangeNewPicId(null);
			workElectricEntity.setChangeNewPicName(null);
			workElectricEntity.setChangeSignerId(null);
			workElectricEntity.setChangeSignerName(null);
			workElectricEntity.setChangeSignerDate(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workElectricEntity.setChangeOldPicId(null);
			workElectricEntity.setChangeOldPicName(null);
			workElectricEntity.setChangeNewPicId(null);
			workElectricEntity.setChangeNewPicName(null);
			workElectricEntity.setChangeSignerId(null);
			workElectricEntity.setChangeSignerName(null);
			workElectricEntity.setChangeSignerDate(null);
			workElectricEntity.setChangeAllowId(null);
			workElectricEntity.setChangeAllowName(null);
			workElectricEntity.setChangeAllowDate(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketEntity.setGuarderId(t.getChangeOldPicId());
			workTicketEntity.setGuarderName(t.getChangeOldPicName());
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setWorkPersonGroup(null);
			workElectricEntity.setWorkPersonPicId(null);
			workElectricEntity.setWorkPersonPicName(null);
			super.updateEntity(workElectricEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkElectricEntity workElectricEntity = this.findById(t.getId());//查询这个表的实体
			workElectricEntity.setDelayDate(null);
			workElectricEntity.setDelayDutyMonitorId(null);
			workElectricEntity.setDelayDutyMonitorName(null);
			super.updateEntity(workElectricEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketService.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkElectricEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			elentity.setRemarkOther("");
			super.updateEntity(elentity);
			
			WorkTicketEntity workEntity=workTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			workTicketService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkElectricEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketEntity workTicketEntity=workTicketService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketService.updateEntity(workTicketEntity);
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
			
			WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(id));
			workTicketEntity.setId(Long.valueOf(id));
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketService.updateEntity(workTicketEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkElectricEntity t, Long id) {
		WorkElectricEntity workElectricEntity=this.findById(id);
		workElectricEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workElectricEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workElectricEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workElectricEntity);
		return new ResultObj();
	}
}