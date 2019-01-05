package com.aptech.business.ticketManage.workLine.service;

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
import com.aptech.business.component.dictionary.WorkLineStatusEnum;
import com.aptech.business.ticketManage.workLine.dao.WorkLineDao;
import com.aptech.business.ticketManage.workLine.domain.WorkLineEntity;
import com.aptech.business.ticketManage.workTicketLine.domain.WorkTicketLineEntity;
import com.aptech.business.ticketManage.workTicketLine.service.WorkTicketLineService;
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
@Service("workLineService")
@Transactional
public class WorkLineServiceImpl extends AbstractBaseEntityOperation<WorkLineEntity> implements WorkLineService {
	
	@Autowired
	private WorkLineDao workLineDao;
	@Autowired
	private WorkTicketLineService workTicketLineService;
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
	public IBaseEntityOperation<WorkLineEntity> getDao() {
		return workLineDao;
	}
	@Override
	public void updateSpnrAgree(WorkLineEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setSignerId(userEntity.getId());
			workLineEntity.setSignerName(userEntity.getName());
			workLineEntity.setSignerDate(new Date());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TICKETS.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workLineEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setOndutyId(userEntity.getId());
			workLineEntity.setOndutyName(userEntity.getName());
			workLineEntity.setGetticketTime(new Date());
			super.updateEntity(workLineEntity);
			
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			//许可的时候需要生产工作票编号
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//	        SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
	        SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketLineEntity.getUnitNameId()));
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "4");
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
			String str="";
			StringBuilder sb=new StringBuilder(code);
			sb.replace(3, 5, str);
			String nowCode=sb.toString();
			//工作票编号结束
			//更新主表状态
			workTicketLineEntity.setCode(nowCode);
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOW.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setDutyMonitorId(userEntity.getId());
			workLineEntity.setDutyMonitorName(userEntity.getName());
			workLineEntity.setApproveStarttime(t.getApproveStarttime());
			workLineEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workLineEntity);
			//更新主表状态为未执行
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOW.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkLineEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketLineEntity workEntity=workTicketLineService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineService.updateEntity(workEntity);
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
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workLineEntity.setChangeOldPicId(t.getChangeOldPicId());
			workLineEntity.setChangeOldPicName(t.getChangeOldPicName());
			workLineEntity.setChangeNewPicId(t.getChangeNewPicId());
			workLineEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.CHANGEISSUED.getCode()));
			workTicketLineEntity.setGuarderId(t.getChangeNewPicId());
			workTicketLineEntity.setGuarderName(workLineEntity.getChangeNewPicName());
			workTicketLineService.updateEntity(workTicketLineEntity);
			
			//获取三种人记录修改工作负责人
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workLineEntity.getWorkticketId()));
			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
			for (WorkTicketUserRelEntity entity : list) {
				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
				workTicketUserRelService.updateByMap("updateInfoByMap", entity);
			}
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setChangeSignerId(t.getChangeSignerId());
			workLineEntity.setChangeSignerName(t.getChangeSignerName());
			workLineEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.CHANGALLOW.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workLineEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setChangeAllowId(t.getChangeAllowId());
			workLineEntity.setChangeAllowName(t.getChangeAllowName());
			workLineEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workLineEntity.setWorkPersonPicId(userEntity.getId());
			workLineEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setWorkDisclosure(t.getWorkDisclosure());
			super.updateEntity(workLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workLineEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERALLOW.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setDelayDate(t.getDelayDate());
			workLineEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			workLineEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERALLOW.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setDelayAllowId(t.getDelayAllowId());
			workLineEntity.setDelayAllowName(t.getDelayAllowName());
			workLineEntity.setDelayDate(t.getDelayDate());
			workLineEntity.setDelayAllowSureDate(new Date());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWPIC.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setDelayPicId(t.getDelayPicId());
			workLineEntity.setDelayPicName(t.getDelayPicName());
			workLineEntity.setDelayPicSureDate(new Date());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineEntity.setHandFlag(t.getHandFlag());
			workLineEntity.setEndGroupIndex(t.getEndGroupIndex());
			workLineEntity.setEndGroup(t.getEndGroup());
			workLineEntity.setHand(t.getHand());
			workLineEntity.setEndStandIndex(t.getEndStandIndex());
			workLineEntity.setEndStand(t.getEndStand());
			workLineEntity.setRecoverOther(t.getRecoverOther());
			super.updateEntity(workLineEntity);
			
			WorkTicketLineEntity workEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWEND.getCode()));
			workTicketLineService.updateEntity(workEntity);
			WorkLineEntity elentity = this.findById(t.getId());//查询这个表的实体
			elentity.setRemarkOther(t.getRemarkOther());
			super.updateEntity(elentity);

		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketLineEntity workEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.END.getCode()));
			workEntity.setChangeAllowIdea(t.getFileid());
			workTicketLineService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineEntity.setHeatCode(t.getHeatCode());
			workLineEntity.setHeatPersonName(t.getHeatPersonName());
			workLineEntity.setElectricCode(t.getElectricCode());
			workLineEntity.setElectricPersonName(t.getElectricPersonName());
			workLineEntity.setMachineCode(t.getMachineCode());
			workLineEntity.setMachinePersonName(t.getMachinePersonName());
			workLineEntity.setRecoverOther(t.getRecoverOther());
			workLineEntity.setRunPicId(t.getRunPicId());
			workLineEntity.setRunPicName(t.getRunPicName());
			workLineEntity.setRunAllowId(t.getRunAllowId());
			workLineEntity.setRunAllowName(t.getRunAllowName());
			workLineEntity.setRunManagerId(t.getRunManagerId());
			workLineEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWAPPLY.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			workLineEntity.setRunSureDate(t.getRunSureDate());
			workLineEntity.setRunAllowId(t.getRunAllowId());
			workLineEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERAPPLY.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			workLineEntity.setRunManagerId(t.getRunManagerId());
			workLineEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.APPLY.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineEntity.setStopPicId(t.getStopPicId());
			workLineEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWREGAIN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineEntity.setStopSureDate(t.getStopSureDate());
			workLineEntity.setStopAllowId(t.getStopAllowId());
			workLineEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERREGAIN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkLineEntity workLineEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineEntity.setStopManagerId(t.getStopManagerId());
			workLineEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
//			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.REGAIN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(t.getId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TOBEISSUED.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkLineEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workLineEntity.setSignerId(null);
			workLineEntity.setSignerName(null);
			workLineEntity.setSignerDate(null);
			super.updateEntity(workLineEntity);
			//更新主表状态为驳回
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineEntity.setSignerId(null);
			workLineEntity.setSignerName(null);
			workLineEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineEntity.setOndutyId(null);
			workLineEntity.setOndutyName(null);
			workLineEntity.setGetticketTime(null);
			super.updateEntity(workLineEntity);
			//更新主表状态为驳回
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineEntity.setSignerId(null);
			workLineEntity.setSignerName(null);
			workLineEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineEntity.setOndutyId(null);
			workLineEntity.setOndutyName(null);
			workLineEntity.setGetticketTime(null);
			//清空许可人填写信息
			workLineEntity.setAllowPicPersonId(null);
			workLineEntity.setAllowPicPersonName(null);
			workLineEntity.setHand(null);
			workLineEntity.setHandFlag(null);
			workLineEntity.setOther(null);
			workLineEntity.setWireway(null);
			workLineEntity.setQuarantine(null);
			workLineEntity.setOtherSafe("");
			super.updateEntity(workLineEntity);
			//更新主表状态为驳回
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineEntity.setChangeAllowDate(null);
			workTicketLineEntity.setChangeAllowId(null);
			workTicketLineEntity.setChangeAllowName(null);
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineEntity.setSignerId(null);
			workLineEntity.setSignerName(null);
			workLineEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineEntity.setOndutyId(null);
			workLineEntity.setOndutyName(null);
			workLineEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workLineEntity.setDutyMonitorId(null);
			workLineEntity.setDutyMonitorName(null);
			workLineEntity.setApproveStarttime(null);
			workLineEntity.setApproveEndtime(null);
			super.updateEntity(workLineEntity);
			//更新主表状态为驳回
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineEntity.setChangeOldPicId(null);
			workLineEntity.setChangeOldPicName(null);
			workLineEntity.setChangeNewPicId(null);
			workLineEntity.setChangeNewPicName(null);
			workLineEntity.setChangeSignerId(null);
			workLineEntity.setChangeSignerName(null);
			workLineEntity.setChangeSignerDate(null);
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineEntity.setGuarderId(t.getChangeOldPicId());
			workTicketLineEntity.setGuarderName(t.getChangeOldPicName());
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineEntity.setChangeOldPicId(null);
			workLineEntity.setChangeOldPicName(null);
			workLineEntity.setChangeNewPicId(null);
			workLineEntity.setChangeNewPicName(null);
			workLineEntity.setChangeSignerId(null);
			workLineEntity.setChangeSignerName(null);
			workLineEntity.setChangeSignerDate(null);
			workLineEntity.setChangeAllowId(null);
			workLineEntity.setChangeAllowName(null);
			workLineEntity.setChangeAllowDate(null);
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineEntity.setGuarderId(t.getChangeOldPicId());
			workTicketLineEntity.setGuarderName(t.getChangeOldPicName());
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setWorkPersonGroup(null);
			workLineEntity.setWorkPersonPicId(null);
			workLineEntity.setWorkPersonPicName(null);
			super.updateEntity(workLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkLineEntity workLineEntity = this.findById(t.getId());//查询这个表的实体
			workLineEntity.setDelayDate(null);
			workLineEntity.setDelayDutyMonitorId(null);
			workLineEntity.setDelayDutyMonitorName(null);
			super.updateEntity(workLineEntity);
			//更新主表状态
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workLineEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkLineEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			elentity.setRemarkOther("");
			super.updateEntity(elentity);
			
			WorkTicketLineEntity workEntity=workTicketLineService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkLineEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketLineService.updateEntity(workTicketLineEntity);
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
  		    String key=ProcessMarkEnum.WORK_TICKET_LINE_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketLineEntity workTicketLineEntity=workTicketLineService.findById(Long.valueOf(id));
			workTicketLineEntity.setId(Long.valueOf(id));
			workTicketLineEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketLineService.updateEntity(workTicketLineEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkLineEntity t, Long id) {
		WorkLineEntity workLineEntity=this.findById(id);
		workLineEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workLineEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workLineEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workLineEntity);
		return new ResultObj();
	}
}