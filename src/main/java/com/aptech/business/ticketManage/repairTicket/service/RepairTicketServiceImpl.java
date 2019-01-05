package com.aptech.business.ticketManage.repairTicket.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.business.ticketManage.repairTicket.dao.RepairTicketDao;
import com.aptech.business.ticketManage.repairTicket.domain.RepairTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketTwo.exception.WorkTicketTwoException;
import com.aptech.business.ticketManage.workTicketTwo.exception.WorkTicketTwoExceptionType;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.business.ticketManage.workticketRepair.domain.WorkticketRepairEntity;
import com.aptech.business.ticketManage.workticketRepair.service.WorkticketRepairService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务实现类
 *
 * @author 
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
@Service("repairTicketService")
@Transactional
public class RepairTicketServiceImpl extends AbstractBaseEntityOperation<RepairTicketEntity> implements RepairTicketService {
	
	@Autowired
	private RepairTicketDao repairTicketDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private WorkticketRepairService workticketRepairService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
   	private ActTaskService actTaskService;
	@Autowired
	private TypicalTicketService typicalTicketService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Override
	public IBaseEntityOperation<RepairTicketEntity> getDao() {
		return repairTicketDao;
	}
	
	/**
	 * 查询列表
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		page.addOrder(Sort.desc("id"));
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.REPAIR.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(RepairTicketEntity t) {
//		if(!StringUtil.isEmpty(t.getSaveOrSubmit())){
//			//加验证
//			List<Condition> conditions =new ArrayList<Condition>();
//			conditions.add(new Condition("C_UUID_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getUuidWorkTicket()));
//			List<WorkSafeEntity> workSafeList=workSafeService.findByCondition(conditions, null);
//			if(workSafeList.isEmpty()){
//				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_SAFE_NULL);
//			}
//			if(StringUtil.isEmpty(t.getCardSort())&&StringUtil.isEmpty(t.getCardSortTwo())&&
//					StringUtil.isEmpty(t.getCardSortThree())&&StringUtil.isEmpty(t.getCardSortFour())){
//				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CARDSORT_NULL);
//			}
//			List<ControlCardRiskEntity> controlCardRiskList=controlCardRiskService.findByCondition(conditions, null);
//			if(controlCardRiskList.isEmpty()){
//				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CONTROLCARDRISK_NULL);
//			}
//		}
		SysUserEntity userEntity= RequestContext.get().getUser();
		
		//工作票编号开始
        Map<String, Object> codeparams=new HashMap<String, Object> ();
//		SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
		SysUnitEntity sysunit=sysUnitService.findById(Long.parseLong(t.getUnitNameId()));
		codeparams.put("workcode", sysunit.getNameAB());
		codeparams.put("workTicketType", "7");
		codeparams.put("YM", new Date());
		String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
		String str="";
		StringBuilder sb=new StringBuilder(code);
		sb.replace(3, 5, str);
		String nowCode=sb.toString();
		//工作票编号结束

		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.REPAIR.getCode()));
		t.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setCode(nowCode);
		t.setCreateDate(new Date());
		super.addEntity(t);
		//像电气第一种票表  加记录
		WorkticketRepairEntity woel=new WorkticketRepairEntity();
		woel.setWorkticketId(t.getId());
		workticketRepairService.addEntity(woel);
		//像安全风险控制卡表加信息
		WorkControlCardEntity workControlCardEntity=new WorkControlCardEntity();
		workControlCardEntity.setWorkticketId(t.getId());
		workControlCardEntity.setCardSortDescription(t.getCardSortDescription());
		workControlCardEntity.setCardSort(t.getCardSort());
		workControlCardEntity.setCardSortTwo(t.getCardSortTwo());
		workControlCardEntity.setCardSortThree(t.getCardSortThree());
		workControlCardEntity.setCardSortFour(t.getCardSortFour());
		workControlCardService.addEntity(workControlCardEntity);
		//更新safe表   把空着的workid 变成  真的
		WorkSafeEntity workSafeEntity=new WorkSafeEntity();
		workSafeEntity.setWorkticketId(t.getId());
		workSafeEntity.setUuidCode(t.getUuidWorkTicket());
		workSafeService.updateByMap("updateByUuid", workSafeEntity);
		//更新cardRisk 表
		ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
		controlCardRiskEntity.setControlId(workControlCardEntity.getId());
		controlCardRiskEntity.setUuidCode(t.getUuidWorkTicket());
		controlCardRiskService.updateByMap("updateByUuid", controlCardRiskEntity);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.REPAIRTICKE_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
			RepairTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setId(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORK_ALLOW.getCode())); 
			this.updateEntity(workTicketEntity);
		}
		//
		if(t.getTypicalTicketId()!=null&&!t.getTypicalTicketId().equals("")){
			TypicalTicketEntity tEntity= typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			tEntity.setWorkticket_id(t.getId());
			tEntity.setStatus(1l);
			typicalTicketService.updateEntity(tEntity);
			//更新状态本身是典型票0
			RepairTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setIstypical(Long.valueOf(0));
			this.getDao().updateEntity(workTicketEntity);
		}
		//两票设备关联
		List<Condition> conditions = new ArrayList<Condition>();
		String [] codes={};
		String [] equipNames={};
		if (t.getEquipmentCode()!=null && t.getEquipmentName()!=null) {
			codes = t.getEquipmentCode().split(",");
			equipNames = t.getEquipmentName().split(",");
		}
			for(int i = 0;i<codes.length;i++){
				WorkTicketEquipEntity workEquipEntity = new WorkTicketEquipEntity();
				workEquipEntity.setCreateDate(new Date());
				workEquipEntity.setCreateUserId(userEntity.getId().toString());
				workEquipEntity.setWorkticketId(t.getId());
				workEquipEntity.setEquipCode(codes[i]);
				workEquipEntity.setEquipName(equipNames[i]);
				workEquipEntity.setStatus(DefectStatusEnum.PENDING.getCode());
				workEquipEntity.setTicketType("3");
				conditions.clear();
				conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
				
				workTicketEquipService.addEntity(workEquipEntity);
			}
			
			//保存三种人数据
			WorkTicketUserRelEntity workTicketUserRelEntity  = new WorkTicketUserRelEntity();
			workTicketUserRelEntity.setType(2);
			workTicketUserRelEntity.setWorkTicketId(t.getId());
			workTicketUserRelEntity.setUserId(t.getGuarderId());//工作负责人
			workTicketUserRelService.addEntity(workTicketUserRelEntity);	
			
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.REPAIRTICKET.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:11 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(RepairTicketEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		RepairTicketEntity  workTicketEntity=this.findById(t.getId());
		workTicketEntity.setUnitNameId(t.getUnitNameId());
		workTicketEntity.setGroupId(t.getGroupId());
		workTicketEntity.setGuarderId(userEntity.getId());
		workTicketEntity.setGuarderName(userEntity.getName());
		workTicketEntity.setWorkClassMember(t.getWorkClassMember());
		workTicketEntity.setWorkClassPeople(t.getWorkClassPeople());
		workTicketEntity.setContent(t.getContent());
		workTicketEntity.setAddress(t.getAddress());
		workTicketEntity.setEquipmentCode(t.getEquipmentCode());
		workTicketEntity.setEquipmentName(t.getEquipmentName());
		workTicketEntity.setPlandateStart(t.getPlandateStart());
		workTicketEntity.setPlandateEnd(t.getPlandateEnd());
		workTicketEntity.setFlawCode(t.getFlawCode());
		workTicketEntity.setSafe(t.getSafe());
		workTicketEntity.setRemarkOther(t.getRemarkOther());
		super.updateEntity(workTicketEntity);
		
		//两票设备关联
		List<Condition> eqConditions = new ArrayList<Condition>();
		String [] codes = t.getEquipmentCode().split(",");
		String [] equipNames = t.getEquipmentName().split(",");
//		eqConditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
		List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(eqConditions, null);
//		if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
//			for(int i = 0;i<WorkTicketEquipList.size();i++){
//				WorkTicketEquipEntity workEquipEntity = WorkTicketEquipList.get(i);
//				workEquipEntity.setEquipCode(codes[i]);
//				workEquipEntity.setEquipName(equipNames[i]);
//				
//				eqConditions.clear();
//				eqConditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
//				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(eqConditions, null);
//				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
//				
//				workTicketEquipService.updateEntity(workEquipEntity);
//			}
//		}else {
		//删除工作票与设备关联数据
		workTicketEquipService.deleteByCondition("deletetByWorkTicketId", workTicketEntity.getId());
			for(int i = 0;i<codes.length;i++){
				WorkTicketEquipEntity workEquipEntity = new WorkTicketEquipEntity();
				workEquipEntity.setCreateDate(new Date());
				workEquipEntity.setCreateUserId(userEntity.getId().toString());
				workEquipEntity.setWorkticketId(t.getId());
				workEquipEntity.setEquipCode(codes[i]);
				workEquipEntity.setEquipName(equipNames[i]);
				workEquipEntity.setStatus(DefectStatusEnum.PENDING.getCode());
				workEquipEntity.setTicketType("3");
				
				eqConditions.clear();
				eqConditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(eqConditions, null);
				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
			
				workTicketEquipService.addEntity(workEquipEntity);
			}
//		}
		
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.REPAIRTICKET.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	/**
	 * @Description:    单个删除时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:47 
	 * @throws         Exception
	 */
	@Override
	public void deleteEntity(Serializable id) {
		RepairTicketEntity  workTicketEntity=this.findById(id);
		workTicketEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
		if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
			for(WorkTicketEquipEntity workTicketEquipEntity : WorkTicketEquipList){
				workTicketEquipService.deleteEntity(workTicketEquipEntity);
			}
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.REPAIRTICKET.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		super.updateEntity(workTicketEntity);
	}
	/**
	 * @Description:   批量删除
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:18 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		
		if(validateisDeleteBulk(ids)){
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.REPAIRTICKET.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		
		String [] equipids = ids.toString().split(",");
		List<Condition> conditions = new ArrayList<Condition>();
		for(int i = 0;i<equipids.length;i++){
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipids[i]));
			List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
			  if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
				for(WorkTicketEquipEntity workTicketEquipEntity : WorkTicketEquipList){
					workTicketEquipService.deleteEntity(workTicketEquipEntity);
				}
			}
		}
		return new ResultObj();
	}
	/**
	 * @Description:   批量删除验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:18 
	 * @throws         Exception
	 */
	private boolean validateisDeleteBulk(List<Integer> ids) {
		boolean flag=true;
		for (int i=0;i<ids.size();i++) {
			RepairTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
			if(workTicketEntity!=null){
				String workStatus=workTicketEntity.getWorkStatus().toString();
				if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				RepairTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
				workTicketEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(workTicketEntity);
			}
		}else{
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_BATHDELETE);
		}
		
		return true;
	}

	/**
	 * @Description:   鉴定
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:23 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(RepairTicketEntity t) {
		RepairTicketEntity  workTicketEntity=this.findById(Long.valueOf(t.getId()));
		workTicketEntity.setInvalidDate(t.getInvalidDate());
		workTicketEntity.setInvalidId(t.getInvalidId());
		workTicketEntity.setInvalidContent(t.getInvalidContent());
		workTicketEntity.setIdentify(t.getIdentify());
		
		super.updateEntity(workTicketEntity);
		return new ResultObj();
	}
	/**
	 * @Description:   验证是否执行了安全措施 
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isExecute(Long id) {
		ResultObj resultObj = new ResultObj();
		RepairTicketEntity  workTicketEntity=this.findById(id);
		if(validateisExecute(workTicketEntity)){
		}
		return resultObj;
	}
	/**
	 * @Description:   验证是否执行了安全措施验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	private boolean validateisExecute(RepairTicketEntity workTicketEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEONE.getCode()+" or C_SAFE_TYPE="+WorkSafeTypeEnum.SAFETWO.getCode()+
						" or C_SAFE_TYPE="+WorkSafeTypeEnum.SAFETHREE.getCode()));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getExecuteSituation()==null||workSafeEntity.getExecuteSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj updateValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		isupdateValidate(id);
		return resultObj;
	}
	private boolean isupdateValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<RepairTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			RepairTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   单个删除时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(isdeleteValidate(id)){
		}
		return resultObj;
	}
	private boolean isdeleteValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<RepairTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			RepairTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
				throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_DELETESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   提交时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj tijiaoValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(istijiaoValidate(id)){
		}
		return resultObj;
	}
	private boolean istijiaoValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<RepairTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			RepairTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())){
				throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_SUBMITSTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj invalidValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		isinvalidValidate(id);
		return resultObj;
	}
	private boolean isinvalidValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<RepairTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			RepairTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_JIANDINGSTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:29 
	 * @throws         Exception
	 */
	@Override
	public ResultObj setValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(issetValidate(id)){
			//没有异常成功了
			RepairTicketEntity  workTicketEntity=this.findById(id);
			workTicketEntity.setIsSet(Long.valueOf(IssetEnum.ISSETYES.getCode()));
			super.updateEntity(workTicketEntity);
			//添加典型表主表数据
			TypicalTicketEntity tEntity=new TypicalTicketEntity();
			tEntity.setWorkticket_id(id);
			typicalTicketService.addEntity(tEntity);
			//修改典型表主表数据
			tEntity=typicalTicketService.findById(tEntity.getId());
			tEntity.setStatus(1l);
			tEntity.setType(TypicalTicketTypeEnum.OPERATIONTICKET.getCode());
			tEntity.setName("电气第二种工作票设置典型票");
			typicalTicketService.updateEntity(tEntity);
		}
		return resultObj;
	}
	private boolean issetValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<RepairTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			RepairTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())){
				throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_ENDSTATUS);
			}
			//验证是否设置
			if(workTicket.getIsSet().toString().equals(IssetEnum.ISSETYES.getCode().toString())){
				throw new OperationTicketException(
						OperationTicketExceptionType.OPERATIONTICKET_CODE_ISSETYES);
			}
		}
		return true;
	}
	@Override
	public List<RepairTicketEntity> searchJan(String yearStart, String yearEnd,
			Map<String, Object> params, Page<RepairTicketEntity> page,String identify,String qualifiedCount,String unQualifiedCount
			) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date yearStartd = sdf.parse(yearStart);
		Date yearEndd=sdf.parse(yearEnd);
		if(page == null) {
			 page = new Page<RepairTicketEntity>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
   		if(!identify.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.REPAIR.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY is NULL"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkStatusEnum.END.getCode()));
   			List<RepairTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!qualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.REPAIR.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkStatusEnum.END.getCode()));
   			List<RepairTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.REPAIR.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkStatusEnum.END.getCode()));
   			List<RepairTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(identify.isEmpty()&&qualifiedCount.isEmpty()&&unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.REPAIR.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkStatusEnum.END.getCode()));
   			List<RepairTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}
		return null;
	}
	
}