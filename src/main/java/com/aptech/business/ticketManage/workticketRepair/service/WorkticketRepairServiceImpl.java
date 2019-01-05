package com.aptech.business.ticketManage.workticketRepair.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.business.ticketManage.repairTicket.service.RepairTicketService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.business.ticketManage.workticketRepair.dao.WorkticketRepairDao;
import com.aptech.business.ticketManage.workticketRepair.domain.WorkticketRepairEntity;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.StringUtil;

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
@Service("workticketRepairService")
@Transactional
public class WorkticketRepairServiceImpl extends AbstractBaseEntityOperation<WorkticketRepairEntity> implements WorkticketRepairService {
	
	@Autowired
	private WorkticketRepairDao workticketRepairDao;
	@Autowired
	private RepairTicketService repairTicketService;
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
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkticketRepairEntity> getDao() {
		return workticketRepairDao;
	}
	@Override
	public void updateSpnrAgree(WorkticketRepairEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			workticketRepairEntity.setAllowPicPersonId(repairTicketEntity.getGuarderId());;
			workticketRepairEntity.setAllowPicPersonName(repairTicketEntity.getGuarderName());
			super.updateEntity(workticketRepairEntity);
			//更新主表状态
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW_END.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
			
			//许可的时候需要生产工作票编号
			/*if(StringUtil.isEmpty(repairTicketEntity.getCode())){
				Map<String, Object> codeparams=new HashMap<String, Object> ();
				SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(repairTicketEntity.getUnitNameId()));
				codeparams.put("STITION", sysunit.getNameAB());
				codeparams.put("YM", new Date());
				String code=fourCodeService.getBusinessCode("紧急抢修单编码", codeparams);
				repairTicketEntity.setCode(code);
				repairTicketService.updateEntity(repairTicketEntity);
			}*/
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			super.updateEntity(workticketRepairEntity);
			//更新主表状态
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.LONGVALUESURE.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			workticketRepairEntity.setDutyMonitorId(userEntity.getId());
			workticketRepairEntity.setDutyMonitorName(userEntity.getName());
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为未执行
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_PERSON_END.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkticketRepairEntity elentity = this.findById(t.getId());//查询这个表的实体
			RepairTicketEntity workEntity=repairTicketService.findById(elentity.getWorkticketId());
			elentity.setOtherSafe(t.getOtherSafe());
			workticketRepairDao.updateEntity(elentity);
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_PERSON.getCode()));
			
			repairTicketService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(elentity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			RepairTicketEntity workEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			//更新工作票表
//			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			repairTicketService.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			RepairTicketEntity workEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			workticketRepairEntity.setRepairResult(t.getRepairResult());
			workticketRepairEntity.setRetainSafe(t.getRetainSafe());
			workticketRepairDao.updateEntity(workticketRepairEntity);
			//更新工作票表
			workEntity.setChangeAllowIdea(t.getFileid());
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_PERSON_END.getCode()));
			repairTicketService.updateEntity(workEntity);
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(3);
			workTicketUserRelEntity.setWorkTicketId(workticketRepairEntity.getWorkticketId());
			workTicketUserRelEntity.setUserId(userEntity.getId());//许可人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJZZ.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			workticketRepairEntity.setEndDutyMonitorId(t.getEndDutyMonitorId());
			workticketRepairEntity.setEndDutyMonitorName(t.getEndDutyMonitorName());
			workticketRepairEntity.setEndDutyMonitorDate(t.getEndDutyMonitorDate());
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为未执行
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(t.getId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
		}
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkticketRepairEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为驳回
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW_REJECT.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			//更新从表信息
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为驳回
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为驳回
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW_REJECT.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空3
			workticketRepairEntity.setDutyMonitorId(null);
			workticketRepairEntity.setDutyMonitorName(null);
			super.updateEntity(workticketRepairEntity);
			//更新主表状态为驳回
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW_REJECT.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkticketRepairEntity elentity = this.findById(t.getId());//查询这个表的实体
			//更新电气第二种票表
			super.updateEntity(elentity);
			RepairTicketEntity workEntity=repairTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW_REJECT.getCode()));
			repairTicketService.updateEntity(workEntity);
			
		}
		else if(spFlag.equals(WorkBtnTypeEnum.ZJZZ.getCode())){
			WorkticketRepairEntity elentity = this.findById(t.getId());//查询这个表的实体
			super.updateEntity(elentity);
			
			RepairTicketEntity workEntity=repairTicketService.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			repairTicketService.updateEntity(workEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkticketRepairEntity workticketRepairEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(workticketRepairEntity.getWorkticketId());
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			repairTicketService.updateEntity(repairTicketEntity);
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
//		List<Condition> conditions =new ArrayList<Condition>();
//		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,id));
//		List<WorkSafeEntity> workSafeList=workSafeService.findByCondition(conditions, null);
//		if(workSafeList.isEmpty()){
//			throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_SAFE_NULL);
//		}
//		List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
//		if(workControlCardList.isEmpty()||(
//				  StringUtil.isEmpty(workControlCardList.get(0).getCardSort())
//				&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortTwo())
//				&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortThree())
//				&&StringUtil.isEmpty(workControlCardList.get(0).getCardSortFour()))){
//			throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CARDSORT_NULL);
//		}
//		conditions.clear();conditions.add(new Condition("C_CONTROL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workControlCardList.get(0).getId()));
//		List<ControlCardRiskEntity> controlCardRiskList=controlCardRiskService.findByCondition(conditions, null);
//		if(controlCardRiskList.isEmpty()){
//			throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CONTROLCARDRISK_NULL);
//		}
		
			//准备启动流程
  		    String key=ProcessMarkEnum.REPAIRTICKE_PROCESS_KEY.getName();	
  		    
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, id, vars);
			
			RepairTicketEntity repairTicketEntity=repairTicketService.findById(Long.valueOf(id));
			repairTicketEntity.setId(Long.valueOf(id));
			repairTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW.getCode())); 
			
			repairTicketService.updateEntity(repairTicketEntity);
	}
}