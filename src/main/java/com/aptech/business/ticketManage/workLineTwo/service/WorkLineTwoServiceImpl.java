package com.aptech.business.ticketManage.workLineTwo.service;

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
import com.aptech.business.ticketManage.workLineTwo.dao.WorkLineTwoDao;
import com.aptech.business.ticketManage.workLineTwo.domain.WorkLineTwoEntity;
import com.aptech.business.ticketManage.workTicketLineTwo.domain.WorkTicketLineTwoEntity;
import com.aptech.business.ticketManage.workTicketLineTwo.service.WorkTicketLineTwoService;
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
 * @author zzq
 * @created 2017-10-18 17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workLineTwoService")
@Transactional
public class WorkLineTwoServiceImpl extends AbstractBaseEntityOperation<WorkLineTwoEntity> implements WorkLineTwoService {
	
	@Autowired
	private WorkLineTwoDao workLineTwoDao;
	@Autowired
	private WorkTicketLineTwoService workTicketLineTwoService;
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
	public IBaseEntityOperation<WorkLineTwoEntity> getDao() {
		return workLineTwoDao;
	}
	@Override
	public void updateSpnrAgree(WorkLineTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setSignerId(userEntity.getId());
			workLineTwoEntity.setSignerName(userEntity.getName());
			workLineTwoEntity.setSignerDate(new Date());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TICKETS.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workLineTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setOndutyId(userEntity.getId());
			workLineTwoEntity.setOndutyName(userEntity.getName());
			workLineTwoEntity.setGetticketTime(new Date());
			super.updateEntity(workLineTwoEntity);
			
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
//			SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
			SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workTicketLineTwoEntity.getUnitNameId()));
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "5");
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
			String str="";
			StringBuilder sb=new StringBuilder(code);
			sb.replace(3, 5, str);
			String nowCode=sb.toString();
			//工作票编号结束
			//更新主表状态
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOW.getCode()));
			workTicketLineTwoEntity.setCode(nowCode);
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setDutyMonitorId(userEntity.getId());
			workLineTwoEntity.setDutyMonitorName(userEntity.getName());
			workLineTwoEntity.setApproveStarttime(t.getApproveStarttime());
			workLineTwoEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态为未执行
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOW.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkLineTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
			WorkTicketLineTwoEntity workEntity=workTicketLineTwoService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			
			workTicketLineTwoService.updateEntity(workEntity);
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
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workLineTwoEntity.setChangeOldPicId(t.getChangeOldPicId());
			workLineTwoEntity.setChangeOldPicName(t.getChangeOldPicName());
			workLineTwoEntity.setChangeNewPicId(t.getChangeNewPicId());
			workLineTwoEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.CHANGEISSUED.getCode()));
			workTicketLineTwoEntity.setGuarderId(t.getChangeNewPicId());
			workTicketLineTwoEntity.setGuarderName(workLineTwoEntity.getChangeNewPicName());
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			

			//获取三种人记录修改工作负责人
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_TYPE", FieldTypeEnum.INT, MatchTypeEnum.EQ,WorkTicketUserRelEnum.DUTY.getId()));
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,workLineTwoEntity.getWorkticketId()));
			List<WorkTicketUserRelEntity> list = workTicketUserRelService.findByCondition(conditions, null);
			for (WorkTicketUserRelEntity entity : list) {
				entity.setUserId(t.getChangeNewPicId());
//				workTicketUserRelService.updateEntity(entity);
				workTicketUserRelService.updateByMap("updateInfoByMap", entity);
			}
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setChangeSignerId(t.getChangeSignerId());
			workLineTwoEntity.setChangeSignerName(t.getChangeSignerName());
			workLineTwoEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.CHANGALLOW.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(1);
			workTicketUserRelEntity.setWorkTicketId(workLineTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setChangeAllowId(t.getChangeAllowId());
			workLineTwoEntity.setChangeAllowName(t.getChangeAllowName());
			workLineTwoEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workLineTwoEntity.setWorkPersonPicId(userEntity.getId());
			workLineTwoEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZJD.getCode())){//工作交底
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setWorkDisclosure(t.getWorkDisclosure());
			super.updateEntity(workLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workLineTwoEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERALLOW.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setDelayDate(t.getDelayDate());
			workLineTwoEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			workLineTwoEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERALLOW.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setDelayAllowId(t.getDelayAllowId());
			workLineTwoEntity.setDelayAllowName(t.getDelayAllowName());
			workLineTwoEntity.setDelayDate(t.getDelayDate());
			workLineTwoEntity.setDelayAllowSureDate(new Date());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWPIC.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体 
			workLineTwoEntity.setDelayPicId(t.getDelayPicId());
			workLineTwoEntity.setDelayPicName(t.getDelayPicName());
			workLineTwoEntity.setDelayPicSureDate(new Date());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineTwoEntity.setHandFlag(t.getHandFlag());
			workLineTwoEntity.setEndGroupIndex(t.getEndGroupIndex());
			workLineTwoEntity.setEndGroup(t.getEndGroup());
			workLineTwoEntity.setHand(t.getHand());
			workLineTwoEntity.setEndStandIndex(t.getEndStandIndex());
			workLineTwoEntity.setEndStand(t.getEndStand());
			workLineTwoEntity.setRecoverOther(t.getRecoverOther());
			super.updateEntity(workLineTwoEntity);
			
			WorkTicketLineTwoEntity workEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWEND.getCode()));
			workTicketLineTwoService.updateEntity(workEntity);
			WorkLineTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
			elentity.setRemarkOther(t.getRemarkOther());
			super.updateEntity(elentity);

		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			
			WorkTicketLineTwoEntity workEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.END.getCode()));
			workEntity.setChangeAllowIdea(t.getFileid());
			workTicketLineTwoService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workLineTwoEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineTwoEntity.setHeatCode(t.getHeatCode());
			workLineTwoEntity.setHeatPersonName(t.getHeatPersonName());
			workLineTwoEntity.setElectricCode(t.getElectricCode());
			workLineTwoEntity.setElectricPersonName(t.getElectricPersonName());
			workLineTwoEntity.setMachineCode(t.getMachineCode());
			workLineTwoEntity.setMachinePersonName(t.getMachinePersonName());
			workLineTwoEntity.setRecoverOther(t.getRecoverOther());
			workLineTwoEntity.setRunPicId(t.getRunPicId());
			workLineTwoEntity.setRunPicName(t.getRunPicName());
			workLineTwoEntity.setRunAllowId(t.getRunAllowId());
			workLineTwoEntity.setRunAllowName(t.getRunAllowName());
			workLineTwoEntity.setRunManagerId(t.getRunManagerId());
			workLineTwoEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWAPPLY.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			workLineTwoEntity.setRunSureDate(t.getRunSureDate());
			workLineTwoEntity.setRunAllowId(t.getRunAllowId());
			workLineTwoEntity.setRunAllowName(t.getRunAllowName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERAPPLY.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			workLineTwoEntity.setRunManagerId(t.getRunManagerId());
			workLineTwoEntity.setRunManagerName(t.getRunManagerName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.APPLY.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineTwoEntity.setStopPicId(t.getStopPicId());
			workLineTwoEntity.setStopPicName(t.getStopPicName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.ALLOWREGAIN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineTwoEntity.setStopSureDate(t.getStopSureDate());
			workLineTwoEntity.setStopAllowId(t.getStopAllowId());
			workLineTwoEntity.setStopAllowName(t.getStopAllowName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.MANAGERREGAIN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkLineTwoEntity workLineTwoEntity=this.findById(t.getId());
			//更新电气第一种票表
			workLineTwoEntity.setStopManagerId(t.getStopManagerId());
			workLineTwoEntity.setStopManagerName(t.getStopManagerName());
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
//			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.REGAIN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(t.getId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TOBEISSUED.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkLineTwoEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workLineTwoEntity.setSignerId(null);
			workLineTwoEntity.setSignerName(null);
			workLineTwoEntity.setSignerDate(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态为驳回
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineTwoEntity.setSignerId(null);
			workLineTwoEntity.setSignerName(null);
			workLineTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineTwoEntity.setOndutyId(null);
			workLineTwoEntity.setOndutyName(null);
			workLineTwoEntity.setGetticketTime(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态为驳回
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineTwoEntity.setSignerId(null);
			workLineTwoEntity.setSignerName(null);
			workLineTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineTwoEntity.setOndutyId(null);
			workLineTwoEntity.setOndutyName(null);
			workLineTwoEntity.setGetticketTime(null);
			//清空许可人填写内容
			workLineTwoEntity.setAllowPicPersonId(null);
			workLineTwoEntity.setAllowPicPersonName(null);
			workLineTwoEntity.setHand(null);
			workLineTwoEntity.setHandFlag(null);
			workLineTwoEntity.setOther(null);
			workLineTwoEntity.setWireway(null);
			workLineTwoEntity.setQuarantine(null);
			workLineTwoEntity.setOtherSafe("");
			super.updateEntity(workLineTwoEntity);
			//更新主表状态为驳回
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineTwoEntity.setChangeAllowDate(null);
			workTicketLineTwoEntity.setChangeAllowId(null);
			workTicketLineTwoEntity.setChangeAllowName(null);
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workLineTwoEntity.setSignerId(null);
			workLineTwoEntity.setSignerName(null);
			workLineTwoEntity.setSignerDate(null);
			//上一步的字段也清空2
			workLineTwoEntity.setOndutyId(null);
			workLineTwoEntity.setOndutyName(null);
			workLineTwoEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workLineTwoEntity.setDutyMonitorId(null);
			workLineTwoEntity.setDutyMonitorName(null);
			workLineTwoEntity.setApproveStarttime(null);
			workLineTwoEntity.setApproveEndtime(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态为驳回
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineTwoEntity.setChangeOldPicId(null);
			workLineTwoEntity.setChangeOldPicName(null);
			workLineTwoEntity.setChangeNewPicId(null);
			workLineTwoEntity.setChangeNewPicName(null);
			workLineTwoEntity.setChangeSignerId(null);
			workLineTwoEntity.setChangeSignerName(null);
			workLineTwoEntity.setChangeSignerDate(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoEntity.setGuarderId(t.getChangeOldPicId());
			workTicketLineTwoEntity.setGuarderName(t.getChangeOldPicName());
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			workLineTwoEntity.setChangeOldPicId(null);
			workLineTwoEntity.setChangeOldPicName(null);
			workLineTwoEntity.setChangeNewPicId(null);
			workLineTwoEntity.setChangeNewPicName(null);
			workLineTwoEntity.setChangeSignerId(null);
			workLineTwoEntity.setChangeSignerName(null);
			workLineTwoEntity.setChangeSignerDate(null);
			workLineTwoEntity.setChangeAllowId(null);
			workLineTwoEntity.setChangeAllowName(null);
			workLineTwoEntity.setChangeAllowDate(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoEntity.setGuarderId(t.getChangeOldPicId());
			workTicketLineTwoEntity.setGuarderName(t.getChangeOldPicName());
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setWorkPersonGroup(null);
			workLineTwoEntity.setWorkPersonPicId(null);
			workLineTwoEntity.setWorkPersonPicName(null);
			super.updateEntity(workLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkLineTwoEntity workLineTwoEntity = this.findById(t.getId());//查询这个表的实体
			workLineTwoEntity.setDelayDate(null);
			workLineTwoEntity.setDelayDutyMonitorId(null);
			workLineTwoEntity.setDelayDutyMonitorName(null);
			super.updateEntity(workLineTwoEntity);
			//更新主表状态
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workLineTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkLineTwoEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			elentity.setRemarkOther("");
			super.updateEntity(elentity);
			
			WorkTicketLineTwoEntity workEntity=workTicketLineTwoService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.PICSURE.getCode()));
			workTicketLineTwoService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkLineTwoEntity workElectricTwoEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(workElectricTwoEntity.getWorkticketId());
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
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
  		    String key=ProcessMarkEnum.WORK_TICKET_LINETWO_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			WorkTicketLineTwoEntity workTicketLineTwoEntity=workTicketLineTwoService.findById(Long.valueOf(id));
			workTicketLineTwoEntity.setId(Long.valueOf(id));
			workTicketLineTwoEntity.setWorkStatus(Long.valueOf(WorkLineStatusEnum.TOBEISSUED.getCode())); 
			
			workTicketLineTwoService.updateEntity(workTicketLineTwoEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkLineTwoEntity t, Long id) {
		WorkLineTwoEntity workLineTwoEntity=this.findById(id);
		workLineTwoEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workLineTwoEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workLineTwoEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workLineTwoEntity);
		return new ResultObj();
	}
}