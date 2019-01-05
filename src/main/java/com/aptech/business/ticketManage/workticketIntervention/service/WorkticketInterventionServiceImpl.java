package com.aptech.business.ticketManage.workticketIntervention.service;

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
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.interventionTicket.domain.InterventionTicketEntity;
import com.aptech.business.ticketManage.interventionTicket.exception.InterventionTicketException;
import com.aptech.business.ticketManage.interventionTicket.exception.InterventionTicketExceptionType;
import com.aptech.business.ticketManage.interventionTicket.service.InterventionTicketService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workticketIntervention.dao.WorkticketInterventionDao;
import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
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
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 电气工作票应用管理服务实现类
 *
 * @author 
 * @created 2017-06-29  17:12:46
 * @lastModified 
 * @history
 *
 */
@Service("workticketInterventionService")
@Transactional
public class WorkticketInterventionServiceImpl extends AbstractBaseEntityOperation<WorkticketInterventionEntity> implements WorkticketInterventionService {
	
	@Autowired
	private WorkticketInterventionDao workticketInterventionDao;
	@Autowired
	private InterventionTicketService interventionTicketService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Override
	public IBaseEntityOperation<WorkticketInterventionEntity> getDao() {
		return workticketInterventionDao;
	}
	@Override
	public void updateSpnrAgree(WorkticketInterventionEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setSignerId(userEntity.getId());
			workticketInterventionEntity.setSignerName(userEntity.getName());
			workticketInterventionEntity.setSignerDate(new Date());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setOndutyId(userEntity.getId());
			workticketInterventionEntity.setOndutyName(userEntity.getName());
			workticketInterventionEntity.setGetticketTime(new Date());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.LONGVALUESURE.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setDutyMonitorId(userEntity.getId());
			workticketInterventionEntity.setDutyMonitorName(userEntity.getName());
			workticketInterventionEntity.setApproveStarttime(t.getApproveStarttime());
			workticketInterventionEntity.setApproveEndtime(t.getApproveEndtime());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态为未执行
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkticketInterventionEntity elentity = this.findById(t.getId());//查询这个表的实体
			InterventionTicketEntity workEntity=interventionTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			//许可的时候需要生产工作票编号
			Map<String, Object> codeparams=new HashMap<String, Object> ();
			SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(workEntity.getUnitNameId()));
			codeparams.put("STITION", sysunit.getNameAB());
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("介入工作票编码", codeparams);
			workEntity.setCode(code);
			interventionTicketService.updateEntity(workEntity);
			//更新电气第二种票表  
			elentity.setId(t.getId());
			elentity.setAllowPicPersonId(t.getAllowPicPersonId());
			elentity.setAllowPicPersonName(t.getAllowPicPersonName());
			super.updateEntity(elentity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBG.getCode())){//工作负责人变更
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			workticketInterventionEntity.setChangeOldPicId(t.getChangeOldPicId());
			workticketInterventionEntity.setChangeOldPicName(t.getChangeOldPicName());
			workticketInterventionEntity.setChangeNewPicId(t.getChangeNewPicId());
			workticketInterventionEntity.setChangeNewPicName(sysUserEntity.getName());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setChangeSignerId(t.getChangeSignerId());
			workticketInterventionEntity.setChangeSignerName(t.getChangeSignerName());
			workticketInterventionEntity.setChangeSignerDate(t.getChangeSignerDate());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setChangeAllowId(t.getChangeAllowId());
			workticketInterventionEntity.setChangeAllowName(t.getChangeAllowName());
			workticketInterventionEntity.setChangeAllowDate(t.getChangeAllowDate());
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			workticketInterventionEntity.setWorkPersonPicId(userEntity.getId());
			workticketInterventionEntity.setWorkPersonPicName(userEntity.getName());
			super.updateEntity(workticketInterventionEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			workticketInterventionEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			super.updateEntity(workticketInterventionEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERDELAY.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第二种票表
//			workticketInterventionEntity.setEndGroupIndex(t.getEndGroupIndex());
//			workticketInterventionEntity.setEndGroup(t.getEndGroup());
//			workticketInterventionEntity.setEndStandIndex(t.getEndStandIndex());
//			workticketInterventionEntity.setEndStand(t.getEndStand());
//			super.updateEntity(workticketInterventionEntity);
			
			InterventionTicketEntity workEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			interventionTicketService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			
			InterventionTicketEntity workEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			interventionTicketService.updateEntity(workEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(t.getId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkticketInterventionEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			workticketInterventionEntity.setSignerId(null);
			workticketInterventionEntity.setSignerName(null);
			workticketInterventionEntity.setSignerDate(null);
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态为驳回
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workticketInterventionEntity.setSignerId(null);
			workticketInterventionEntity.setSignerName(null);
			workticketInterventionEntity.setSignerDate(null);
			//上一步的字段也清空2
			workticketInterventionEntity.setOndutyId(null);
			workticketInterventionEntity.setOndutyName(null);
			workticketInterventionEntity.setGetticketTime(null);
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态为驳回
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			workticketInterventionEntity.setSignerId(null);
			workticketInterventionEntity.setSignerName(null);
			workticketInterventionEntity.setSignerDate(null);
			//上一步的字段也清空2
			workticketInterventionEntity.setOndutyId(null);
			workticketInterventionEntity.setOndutyName(null);
			workticketInterventionEntity.setGetticketTime(null);
			//上一步的字段也清空3
			workticketInterventionEntity.setDutyMonitorId(null);
			workticketInterventionEntity.setDutyMonitorName(null);
			workticketInterventionEntity.setApproveStarttime(null);
			workticketInterventionEntity.setApproveEndtime(null);
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态为驳回
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第二种票表
			workticketInterventionEntity.setChangeOldPicId(null);
			workticketInterventionEntity.setChangeOldPicName(null);
			workticketInterventionEntity.setChangeNewPicId(null);
			workticketInterventionEntity.setChangeNewPicName(null);
			workticketInterventionEntity.setChangeSignerId(null);
			workticketInterventionEntity.setChangeSignerName(null);
			workticketInterventionEntity.setChangeSignerDate(null);
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新电气第二种票表
			workticketInterventionEntity.setChangeOldPicId(null);
			workticketInterventionEntity.setChangeOldPicName(null);
			workticketInterventionEntity.setChangeNewPicId(null);
			workticketInterventionEntity.setChangeNewPicName(null);
			workticketInterventionEntity.setChangeSignerId(null);
			workticketInterventionEntity.setChangeSignerName(null);
			workticketInterventionEntity.setChangeSignerDate(null);
			workticketInterventionEntity.setChangeAllowId(null);
			workticketInterventionEntity.setChangeAllowName(null);
			workticketInterventionEntity.setChangeAllowDate(null);
			super.updateEntity(workticketInterventionEntity);
			//更新主表状态
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第二种票表
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			workticketInterventionEntity.setWorkPersonGroup(null);
			workticketInterventionEntity.setWorkPersonPicId(null);
			workticketInterventionEntity.setWorkPersonPicName(null);
			super.updateEntity(workticketInterventionEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkticketInterventionEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第二种票表
//			elentity.setEndGroupIndex(null);
//			elentity.setEndGroup(null);
//			elentity.setEndStandIndex(null);
//			elentity.setEndStand(null);
			super.updateEntity(elentity);
			
			InterventionTicketEntity workEntity=interventionTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			interventionTicketService.updateEntity(workEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkticketInterventionEntity workticketInterventionEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(workticketInterventionEntity.getWorkticketId());
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			interventionTicketService.updateEntity(interventionTicketEntity);
		}
	}
	/**
	 * @Description:   提交方法
	 * @author         changl 
	 * @Date           2017年8月2日 下午3:02:57 
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
			//加验证
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id));
			List<WorkSafeEntity> workSafeList=workSafeService.findByCondition(conditions, null);
			if(workSafeList.isEmpty()){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_SAFE_NULL);
			}
			List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
			if(workControlCardList.isEmpty()||(
					  StringUtil.isEmpty(workControlCardList.get(0).getCardSort())
					&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortTwo())
					&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortThree())
					&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortFour()))){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CARDSORT_NULL);
			}
			conditions.clear();conditions.add(new Condition("C_CONTROL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workControlCardList.get(0).getId()));
			List<ControlCardRiskEntity> controlCardRiskList=controlCardRiskService.findByCondition(conditions, null);
			if(controlCardRiskList.isEmpty()){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CONTROLCARDRISK_NULL);
			}
			//准备启动流程
  		    String key=ProcessMarkEnum.INTERVENTIONTICKE_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			InterventionTicketEntity interventionTicketEntity=interventionTicketService.findById(Long.valueOf(id));
			interventionTicketEntity.setId(Long.valueOf(id));
			interventionTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			
			interventionTicketService.updateEntity(interventionTicketEntity);
	}
	/**
	 * @Description:   更新备注
	 * @author         zhangzq 
	 * @Date           2017年7月4日 上午10:20:44 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkticketInterventionEntity t, Long id) {
		WorkticketInterventionEntity workElectricEntity=this.findById(id);
		workElectricEntity.setRemarkOther(t.getRemarkOther());
		this.updateEntity(workElectricEntity);
		return new ResultObj();
	}
}