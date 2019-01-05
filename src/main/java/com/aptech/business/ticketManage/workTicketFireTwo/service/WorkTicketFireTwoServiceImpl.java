package com.aptech.business.ticketManage.workTicketFireTwo.service;

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
import com.aptech.business.component.dictionary.WorkFireStatusEnum;
import com.aptech.business.component.dictionary.WorkFireTwoStatusEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workFireTwo.domain.WorkFireTwoEntity;
import com.aptech.business.ticketManage.workFireTwo.service.WorkFireTwoService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketFireTwo.dao.WorkTicketFireTwoDao;
import com.aptech.business.ticketManage.workTicketFireTwo.domain.WorkTicketFireTwoEntity;
import com.aptech.business.ticketManage.workTicketFireTwo.exception.WorkTicketFireTwoException;
import com.aptech.business.ticketManage.workTicketFireTwo.exception.WorkTicketFireTwoExceptionType;
import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.act.service.ActTaskService;
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
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务实现类
 *
 * @author 
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Service("workTicketFireTwoService")
@Transactional
public class WorkTicketFireTwoServiceImpl extends AbstractBaseEntityOperation<WorkTicketFireTwoEntity> implements WorkTicketFireTwoService {
	
	@Autowired
	private WorkTicketFireTwoDao workTicketFireTwoDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private WorkFireTwoService workFireTwoService;
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
	private OperateLogService operateLogService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	@Override
	public IBaseEntityOperation<WorkTicketFireTwoEntity> getDao() {
		return workTicketFireTwoDao;
	}
	
	/**
	 * 查询列表 zzq 20170605
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
        page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		//第一种工作票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.FIRETWO.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(WorkTicketFireTwoEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.FIRETWO.getCode()));
		t.setWorkStatus(Long.valueOf(WorkFireStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setCreateDate(new Date());
		super.addEntity(t);
		//像电气第一种票表  加记录
		WorkFireTwoEntity woel=new WorkFireTwoEntity();
		woel.setWorkticketId(t.getId());
		woel.setRemark(t.getRemarkResponsibleName());
		woel.setOther(t.getRemarkOther());
		woel.setOtherExecutorTwo(t.getInvalidContent());
		woel.setOtherCode(t.getIdentify());
		woel.setOtherCodeTwo(t.getInvalidId());
		woel.setOtherExecutorThree(t.getInvalidDate());
		woel.setOtherCodeThree(t.getGroupName());
        woel.setOtherExecutorId(t.getChangeAllowId());	
        woel.setOtherStyle(t.getRemarkGuarderName());      

		workFireTwoService.addEntity(woel);
		//像安全风险控制卡表加信息
//		WorkControlCardEntity workControlCardEntity=new WorkControlCardEntity();
//		workControlCardEntity.setWorkticketId(t.getId());
//		workControlCardEntity.setCardSortDescription(t.getCardSortDescription());
//		workControlCardEntity.setCardSort(t.getCardSort());
//		workControlCardEntity.setCardSortTwo(t.getCardSortTwo());
//		workControlCardEntity.setCardSortThree(t.getCardSortThree());
//		workControlCardEntity.setCardSortFour(t.getCardSortFour());
//		workControlCardService.addEntity(workControlCardEntity);
		//更新safe表   把空着的workid 变成  真的
		WorkSafeEntity workSafeEntity=new WorkSafeEntity();
		workSafeEntity.setWorkticketId(t.getId());
		workSafeEntity.setUuidCode(t.getUuidWorkTicket());
		workSafeService.updateByMap("updateByUuid", workSafeEntity);
		//更新cardRisk 表
//		ControlCardRiskEntity controlCardRiskEntity=new ControlCardRiskEntity();
//		controlCardRiskEntity.setControlId(workControlCardEntity.getId());
//		controlCardRiskEntity.setUuidCode(t.getUuidWorkTicket());
//		controlCardRiskService.updateByMap("updateByUuid", controlCardRiskEntity);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_FIRE_TWO_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
			WorkTicketFireTwoEntity workTicketFireEntity=this.findById(t.getId());
			workTicketFireEntity.setId(t.getId());
			workTicketFireEntity.setWorkStatus(Long.valueOf(WorkFireTwoStatusEnum.TOBEISSUED.getCode())); 
			this.updateEntity(workTicketFireEntity);
		}
		
		//
		if(!StringUtil.isEmpty(t.getTypicalTicketId())){
			TypicalTicketEntity tEntity= typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			tEntity.setWorkticket_id(t.getId());
			tEntity.setStatus(1l);
			typicalTicketService.updateEntity(tEntity);
			//更新状态本身是典型票0
			WorkTicketFireTwoEntity workTicketFireEntity=this.findById(t.getId());
			workTicketFireEntity.setIstypical(Long.valueOf(0));
			this.getDao().updateEntity(workTicketFireEntity);
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
				workEquipEntity.setTicketType("1");
				
				conditions.clear();
				conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
				
				workTicketEquipService.addEntity(workEquipEntity);
			}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORKFIRETWO.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkTicketFireTwoEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(t.getId());
		workTicketFireEntity.setUnitNameId(t.getUnitNameId());
		workTicketFireEntity.setGroupId(t.getGroupId());
		workTicketFireEntity.setGuarderId(userEntity.getId());
		workTicketFireEntity.setGuarderName(userEntity.getName());
		workTicketFireEntity.setWorkClassMember(t.getWorkClassMember());
		workTicketFireEntity.setWorkClassPeople(t.getWorkClassPeople());
		workTicketFireEntity.setContent(t.getContent());
		workTicketFireEntity.setAddress(t.getAddress());
		workTicketFireEntity.setEquipmentCode(t.getEquipmentCode());
		workTicketFireEntity.setEquipmentName(t.getEquipmentName());
		workTicketFireEntity.setPlandateStart(t.getPlandateStart());
		workTicketFireEntity.setPlandateEnd(t.getPlandateEnd());
		workTicketFireEntity.setFlawCode(t.getFlawCode());
		workTicketFireEntity.setSafeFlag(t.getSafeFlag());
		workTicketFireEntity.setSafeNum(t.getSafeNum());
		super.updateEntity(workTicketFireEntity);
		WorkFireTwoEntity  workFireEntity=workFireTwoService.findById(Long.valueOf(t.getElectricId()));
		workFireEntity.setRemark(t.getRemarkResponsibleName());
		workFireEntity.setOther(t.getRemarkOther());
		workFireEntity.setOtherExecutorTwo(t.getInvalidContent());
		workFireEntity.setOtherCode(t.getIdentify());
		workFireEntity.setOtherCodeTwo(t.getInvalidId());
		workFireEntity.setOtherExecutorThree(t.getInvalidDate());
		workFireEntity.setOtherCodeThree(t.getGroupName());
		workFireEntity.setOtherExecutorId(t.getChangeAllowId());  
		workFireEntity.setOtherStyle(t.getRemarkGuarderName());  
		workFireTwoService.updateEntity(workFireEntity);
		
		//两票设备关联
		List<Condition> conditions = new ArrayList<Condition>();
		String [] codes = t.getEquipmentCode().split(",");
		String [] equipNames = t.getEquipmentName().split(",");
//		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
//		List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
//		if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
//			for(int i = 0;i<WorkTicketEquipList.size();i++){
//				WorkTicketEquipEntity workEquipEntity = WorkTicketEquipList.get(i);
//				workEquipEntity.setEquipCode(codes[i]);
//				workEquipEntity.setEquipName(equipNames[i]);
//				
//				conditions.clear();
//				conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
//				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
//				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
//				
//				workTicketEquipService.updateEntity(workEquipEntity);
//			}
//	}else {
		workTicketEquipService.deleteByCondition("deletetByWorkTicketId", workTicketFireEntity.getId());
		for(int i = 0;i<codes.length;i++){
			WorkTicketEquipEntity workEquipEntity = new WorkTicketEquipEntity();
			workEquipEntity.setCreateDate(new Date());
			workEquipEntity.setCreateUserId(userEntity.getId().toString());
			workEquipEntity.setWorkticketId(t.getId());
			workEquipEntity.setEquipCode(codes[i]);
			workEquipEntity.setEquipName(equipNames[i]);
			workEquipEntity.setStatus(DefectStatusEnum.PENDING.getCode());
			workEquipEntity.setTicketType("1");
			
			conditions.clear();
			conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
			List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
			workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
		
			workTicketEquipService.addEntity(workEquipEntity);
		}
//	}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORKFIRETWO.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return new ResultObj();
	}
	//单个删除的方法 zzq
	@Override
	public void deleteEntity(Serializable id) {
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		workTicketFireEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(workTicketFireEntity);
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
		if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
			for(WorkTicketEquipEntity workTicketEquipEntity : WorkTicketEquipList){
				workTicketEquipService.deleteEntity(workTicketEquipEntity);
			}
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORKFIRETWO.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		validateisDeleteBulk(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.WORKFIRETWO.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		
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
	 * @Description:   批量删除的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 上午11:36:54 
	 * @throws         Exception
	 */
	private boolean validateisDeleteBulk(List<Integer> ids) {
		boolean flag=true;
		for (int i=0;i<ids.size();i++) {
			WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(Long.valueOf(ids.get(i)));
			if(workTicketFireEntity!=null){
				String workStatus=workTicketFireEntity.getWorkStatus().toString();
				if(!workStatus.equals(WorkFireTwoStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkFireTwoStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(Long.valueOf(ids.get(i)));
				workTicketFireEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(workTicketFireEntity);
			}
		}else{
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_BATHDELETE);
		}
		
		return true;
	}

	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(WorkTicketFireTwoEntity t) {
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(Long.valueOf(t.getId()));
		workTicketFireEntity.setInvalidDate(t.getInvalidDate());
		workTicketFireEntity.setInvalidId(t.getInvalidId());
		workTicketFireEntity.setInvalidContent(t.getInvalidContent());
		workTicketFireEntity.setIdentify(t.getIdentify());
		super.updateEntity(workTicketFireEntity);
		return new ResultObj();
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isExecute(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisExecute(workTicketFireEntity);
		return resultObj;
	}
	/**
	 * @Description:   验证是否执行了安全措施的验证方法
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	private boolean validateisExecute(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEONE.getCode()+" or C_SAFE_TYPE="+WorkSafeTypeEnum.SAFETWO.getCode()+" or C_SAFE_TYPE="+WorkSafeTypeEnum.SAFETHREE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getExecuteSituation()==null||workSafeEntity.getExecuteSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	@Override
	public ResultObj isExecuteSqsy(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisExecuteSqsy(workTicketFireEntity);
		return resultObj;
	}
	private boolean validateisExecuteSqsy(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ,WorkSafeTypeEnum.SAFESEVEN.getCode()));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getExecuteSituation()==null||workSafeEntity.getExecuteSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	@Override
	public ResultObj isExecuteSyhf(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisExecuteSyhf(workTicketFireEntity);
		return resultObj;
	}
	private boolean validateisExecuteSyhf(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ,WorkSafeTypeEnum.SAFEEIGHT.getCode()));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getExecuteSituation()==null||workSafeEntity.getExecuteSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
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
		List<WorkTicketFireTwoEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketFireTwoEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkFireTwoStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkFireTwoStatusEnum.FZRCHECK.getCode())){
				throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   单个删除时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		isdeleteValidate(id);
		return resultObj;
	}
	private boolean isdeleteValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketFireTwoEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketFireTwoEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkFireTwoStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkFireTwoStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
				throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_DELETESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   提交时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj tijiaoValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		istijiaoValidate(id);
		return resultObj;
	}
	private boolean istijiaoValidate(Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketFireTwoEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketFireTwoEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkFireTwoStatusEnum.TOBESUBMIT.getCode())){
				throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_SUBMITSTATUS);
			}
			//提交的时候查询变更人是谁
			conditions.clear();
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
			List<WorkFireTwoEntity> listelectric=workFireTwoService.findByCondition(conditions, null);
		}
		return true;
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
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
		List<WorkTicketFireTwoEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketFireTwoEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkFireTwoStatusEnum.END.getCode())&&!workStatus.equals(WorkFireTwoStatusEnum.FZRCHECK.getCode())){
				throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_JIANDINGSTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@Override
	public ResultObj setValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(issetValidate(id)){
			//没有异常成功了
			WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
			workTicketFireEntity.setIsSet(Long.valueOf(IssetEnum.ISSETYES.getCode()));
			super.updateEntity(workTicketFireEntity);
			
			//添加典型表主表数据
			TypicalTicketEntity tEntity=new TypicalTicketEntity();
			tEntity.setWorkticket_id(id);
			typicalTicketService.addEntity(tEntity);
			//修改典型表主表数据
			tEntity=typicalTicketService.findById(tEntity.getId());
			tEntity.setStatus(1l);
			tEntity.setType(TypicalTicketTypeEnum.OPERATIONTICKET.getCode());
			tEntity.setName("二级动火工作票设置典型票");
			typicalTicketService.updateEntity(tEntity);
			
		}
		return resultObj;
	}
	private boolean issetValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketFireTwoEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketFireTwoEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkFireTwoStatusEnum.END.getCode())){
				throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_ENDSTATUS);
			}
			//验证是否设置
			if(workTicket.getIsSet().toString().equals(IssetEnum.ISSETYES.getCode().toString())){
				throw new OperationTicketException(
						OperationTicketExceptionType.OPERATIONTICKET_CODE_ISSETYES);
			}
		}
		return true;
	}
	
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isQfAqcs(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisisQfAqcs(workTicketFireEntity);
		return resultObj;
	}
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisQfAqcs(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFOUR.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		if(safeList.size()==0){
			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTZEROQFR);
		}
//		if(safeList.size()>3){
//			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTOUTTHREEQFR);
//		}
		return true;
	}
	/**
	 * @Description:   签发人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isQfAqcsAdd(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisisQfAqcsAdd(workTicketFireEntity);
		return resultObj;
	}
	/**
	 * @Description:   签发人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisQfAqcsAdd(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFOUR.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==3){
//			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTOUTTHREEQFR);
//		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isXkAqcs(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisisXkAqcs(workTicketFireEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisXkAqcs(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFIVE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==0){
//			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTZEROXKR);
//		}
//		if(safeList.size()>3){
//			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTOUTTHREEXKR);
//		}
		return true;
	}
	/**
	 * @Description:   许可人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isXkAqcsAdd(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketFireTwoEntity  workTicketFireEntity=this.findById(id);
		validateisisXkAqcsAdd(workTicketFireEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisXkAqcsAdd(WorkTicketFireTwoEntity workTicketFireEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketFireEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFIVE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==3){
//			throw new WorkTicketFireTwoException(WorkTicketFireTwoExceptionType.WORKTICKET_NOTOUTTHREEXKR);
//		}
		return true;
	}

	@Override
	public List<WorkTicketFireTwoEntity> searchJan(String yearStart,
			String yearEnd, Map<String, Object> params,
			Page<WorkTicketFireTwoEntity> page, String identify,
			String qualifiedCount, String unQualifiedCount) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date yearStartd = sdf.parse(yearStart);
		Date yearEndd=sdf.parse(yearEnd);
		if(page == null) {
			 page = new Page<WorkTicketFireTwoEntity>();
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
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.FIRETWO.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY is NULL"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkFireTwoStatusEnum.END.getCode()));
   			List<WorkTicketFireTwoEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!qualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.FIRETWO.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkFireTwoStatusEnum.END.getCode()));
   			List<WorkTicketFireTwoEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.FIRETWO.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkFireTwoStatusEnum.END.getCode()));
   			List<WorkTicketFireTwoEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(identify.isEmpty()&&qualifiedCount.isEmpty()&&unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.FIRETWO.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkFireTwoStatusEnum.END.getCode()));
   			List<WorkTicketFireTwoEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}
		
		return null;
	}
}