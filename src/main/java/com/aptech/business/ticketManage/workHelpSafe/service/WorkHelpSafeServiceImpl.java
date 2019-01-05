package com.aptech.business.ticketManage.workHelpSafe.service;

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
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkHelpStatusEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workHelpSafe.dao.WorkHelpSafeDao;
import com.aptech.business.ticketManage.workHelpSafe.domain.WorkHelpSafeEntity;
import com.aptech.business.ticketManage.workHelpSafe.exception.WorkHelpSafeException;
import com.aptech.business.ticketManage.workHelpSafe.exception.WorkHelpSafeExceptionType;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.business.ticketManage.workTicketLine.exception.WorkTicketLineException;
import com.aptech.business.ticketManage.workTicketLine.exception.WorkTicketLineExceptionType;
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
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作票应用应用管理服务实现类
 *
 * @author 
 * @created 2017-10-26 11:50:39
 * @lastModified 
 * @history
 *
 */
@Service("workHelpSafeService")
@Transactional
public class WorkHelpSafeServiceImpl extends AbstractBaseEntityOperation<WorkHelpSafeEntity> implements WorkHelpSafeService {
	
	@Autowired
	private WorkHelpSafeDao workHelpSafeDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
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
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Override
	public IBaseEntityOperation<WorkHelpSafeEntity> getDao() {
		return workHelpSafeDao;
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
		//继电保护安全措施票
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(WorkHelpSafeEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.WORKHELPSAFE.getCode()));
		t.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
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
			//工作票编号开始
	        Map<String, Object> codeparams=new HashMap<String, Object> ();
			SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
			codeparams.put("workcode", sysunit.getNameAB());
			codeparams.put("workTicketType", "10");
			codeparams.put("YM", new Date());
			String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
			String str="";
			StringBuilder sb=new StringBuilder(code);
			sb.replace(4, 6, str);
			String nowCode=sb.toString();
			//工作票编号结束
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
			WorkHelpSafeEntity workHelpSafeEntity=this.findById(t.getId());
			workHelpSafeEntity.setCode(nowCode);
			workHelpSafeEntity.setId(t.getId());
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.TOBEISSUED.getCode())); 
			this.updateEntity(workHelpSafeEntity);
		}
		
		//
		if(!StringUtil.isEmpty(t.getTypicalTicketId())){
			TypicalTicketEntity tEntity= typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			tEntity.setWorkticket_id(t.getId());
			tEntity.setStatus(1l);
			typicalTicketService.updateEntity(tEntity);
			//更新状态本身是典型票0
			WorkHelpSafeEntity workHelpSafeEntity=this.findById(t.getId());
			workHelpSafeEntity.setIstypical(Long.valueOf(0));
			this.getDao().updateEntity(workHelpSafeEntity);
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
	}
	
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkHelpSafeEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(t.getId());
		workHelpSafeEntity.setUnitNameId(t.getUnitNameId());
		workHelpSafeEntity.setGroupId(t.getGroupId());
		workHelpSafeEntity.setGuarderId(userEntity.getId());
		workHelpSafeEntity.setGuarderName(userEntity.getName());
		workHelpSafeEntity.setWorkClassMember(t.getWorkClassMember());
		workHelpSafeEntity.setWorkClassPeople(t.getWorkClassPeople());
		workHelpSafeEntity.setContent(t.getContent());
		workHelpSafeEntity.setAddress(t.getAddress());
		workHelpSafeEntity.setEquipmentCode(t.getEquipmentCode());
		workHelpSafeEntity.setEquipmentName(t.getEquipmentName());
		workHelpSafeEntity.setPlandateStart(t.getPlandateStart());
		workHelpSafeEntity.setPlandateEnd(t.getPlandateEnd());
		workHelpSafeEntity.setFlawCode(t.getFlawCode());
		workHelpSafeEntity.setSafeFlag(t.getSafeFlag());
		workHelpSafeEntity.setSafeNum(t.getSafeNum());
		workHelpSafeEntity.setRemarkOther(t.getRemarkOther());
		super.updateEntity(workHelpSafeEntity);
		
		//两票设备关联
		List<Condition> eqConditions = new ArrayList<Condition>();
		String [] codes = t.getEquipmentCode().split(",");
		String [] equipNames = t.getEquipmentName().split(",");
		eqConditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
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
//	}else {
		//删除工作票与设备关联数据
		workTicketEquipService.deleteByCondition("deletetByWorkTicketId", workHelpSafeEntity.getId());
		for(int i = 0;i<codes.length;i++){
			WorkTicketEquipEntity workEquipEntity = new WorkTicketEquipEntity();
			workEquipEntity.setCreateDate(new Date());
			workEquipEntity.setCreateUserId(userEntity.getId().toString());
			workEquipEntity.setWorkticketId(t.getId());
			workEquipEntity.setEquipCode(codes[i]);
			workEquipEntity.setEquipName(equipNames[i]);
			workEquipEntity.setStatus(DefectStatusEnum.PENDING.getCode());
			workEquipEntity.setTicketType("1");
			
			eqConditions.clear();
			eqConditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
			List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(eqConditions, null);
			workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
		
			workTicketEquipService.addEntity(workEquipEntity);
		}
//	}
		return new ResultObj();
	}
	//单个删除的方法 zzq
	@Override
	public void deleteEntity(Serializable id) {
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		workHelpSafeEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(workHelpSafeEntity);
		
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
		if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
			for(WorkTicketEquipEntity workTicketEquipEntity : WorkTicketEquipList){
				workTicketEquipService.deleteEntity(workTicketEquipEntity);
			}
		}
	}
	/** 批量删除
	 * @author         zhangzq 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		validateisDeleteBulk(ids);
		
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
			WorkHelpSafeEntity  workHelpSafeEntity=this.findById(Long.valueOf(ids.get(i)));
			if(workHelpSafeEntity!=null){
				String workStatus=workHelpSafeEntity.getWorkStatus().toString();
				if(!workStatus.equals(WorkHelpStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkHelpStatusEnum.GZPXQ.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				WorkHelpSafeEntity  workHelpSafeEntity=this.findById(Long.valueOf(ids.get(i)));
				workHelpSafeEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(workHelpSafeEntity);
			}
		}else{
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_BATHDELETE);
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
	public ResultObj saveInvalid(WorkHelpSafeEntity t) {
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(Long.valueOf(t.getId()));
		workHelpSafeEntity.setInvalidDate(t.getInvalidDate());
		workHelpSafeEntity.setInvalidId(t.getInvalidId());
		workHelpSafeEntity.setInvalidContent(t.getInvalidContent());
		workHelpSafeEntity.setIdentify(t.getIdentify());
		super.updateEntity(workHelpSafeEntity);
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
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisExecute(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   验证是否执行了安全措施的验证方法
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	private boolean validateisExecute(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEONE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getExecuteSituation()==null||workSafeEntity.getExecuteSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isExecuteHf(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisExecuteHf(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   验证是否执行了安全措施的验证方法
	 * @author         zhangzq 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	private boolean validateisExecuteHf(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEONE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
		boolean flag=true;
		for (WorkSafeEntity workSafeEntity : safeList) {
			if(workSafeEntity.getHfSituation()==null||workSafeEntity.getHfSituation().equals("")){
				flag=false;
				break;
			}
		}
		if (!flag) {
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	@Override
	public ResultObj isExecuteSqsy(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisExecuteSqsy(workHelpSafeEntity);
		return resultObj;
	}
	private boolean validateisExecuteSqsy(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
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
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ISEXECUTE);
		}
		return true;
	}
	@Override
	public ResultObj isExecuteSyhf(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisExecuteSyhf(workHelpSafeEntity);
		return resultObj;
	}
	private boolean validateisExecuteSyhf(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
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
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ISEXECUTE);
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
		List<WorkHelpSafeEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkHelpSafeEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkHelpStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkHelpStatusEnum.BHGZFZR.getCode())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATESTATUS);
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
		List<WorkHelpSafeEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkHelpSafeEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkHelpStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkHelpStatusEnum.GZPXQ.getCode())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_DELETESTATUS);
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
		List<WorkHelpSafeEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkHelpSafeEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkHelpStatusEnum.TOBESUBMIT.getCode())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_SUBMITSTATUS);
			}
			//只可以提交自己的
			if(!(userEntity.getId().toString()).equals(workTicket.getGuarderId().toString())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ONLYSELF);
			}
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
		List<WorkHelpSafeEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkHelpSafeEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkHelpStatusEnum.END.getCode())&&!workStatus.equals(WorkHelpStatusEnum.BHGZFZR.getCode())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_JIANDINGSTATUS);
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
			WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
			workHelpSafeEntity.setIsSet(Long.valueOf(IssetEnum.ISSETYES.getCode()));
			super.updateEntity(workHelpSafeEntity);
			
			//添加典型表主表数据
			TypicalTicketEntity tEntity=new TypicalTicketEntity();
			tEntity.setWorkticket_id(id);
			typicalTicketService.addEntity(tEntity);
			//修改典型表主表数据
			tEntity=typicalTicketService.findById(tEntity.getId());
			tEntity.setStatus(1l);
			tEntity.setType(TypicalTicketTypeEnum.OPERATIONTICKET.getCode());
			tEntity.setName("继电保护安全措施票");
			typicalTicketService.updateEntity(tEntity);
			
		}
		return resultObj;
	}
	private boolean issetValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkHelpSafeEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkHelpSafeEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())){
				throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_ENDSTATUS);
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
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisisQfAqcs(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   签发人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisQfAqcs(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFOUR.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==0){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTZEROQFR);
//		}
//		if(safeList.size()>3){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTOUTTHREEQFR);
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
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisisQfAqcsAdd(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   签发人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisQfAqcsAdd(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFOUR.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==3){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTOUTTHREEQFR);
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
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisisXkAqcs(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人是否写了安全措施
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisXkAqcs(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFIVE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==0){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTZEROXKR);
//		}
//		if(safeList.size()>3){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTOUTTHREEXKR);
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
		WorkHelpSafeEntity  workHelpSafeEntity=this.findById(id);
		validateisisXkAqcsAdd(workHelpSafeEntity);
		return resultObj;
	}
	/**
	 * @Description:   许可人是否写了安全措施新增按钮
	 * @author         zhangzq 
	 * @Date           2017年8月8日 上午8:21:34 
	 * @throws         Exception
	 */
	private boolean validateisisXkAqcsAdd(WorkHelpSafeEntity workHelpSafeEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workHelpSafeEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFEFIVE.getCode()+""));
		List<WorkSafeEntity> safeList=workSafeService.findByCondition(conditions, null);
//		if(safeList.size()==3){
//			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_NOTOUTTHREEXKR);
//		}
		return true;
	}
	
	/**
	 * @Description:   查询全部工作票
	 * @author         wangcc 
	 * @Date           2017年9月8日 上午9:57:34 
	 * @throws         Exception
	 */
	@Override
	public  List<WorkHelpSafeEntity> findAll(Map<String, Object> params, Page<WorkHelpSafeEntity> page) {
		if(page == null) {
			page = new Page<WorkHelpSafeEntity>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
		List<WorkHelpSafeEntity> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	/**提交
	 * @Description:   
	 * @author         zhangzq 
	 * @Date           20171026
	 * @throws         Exception
	 */
	@Override
	public void submit(String id,String selectUser) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		//工作票编号开始
        Map<String, Object> codeparams=new HashMap<String, Object> ();
		SysUnitEntity sysunit=sysUnitService.findById(userEntity.getUnitId());
		codeparams.put("workcode", sysunit.getNameAB());
		codeparams.put("workTicketType", "10");
		codeparams.put("YM", new Date());
		String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
		String str="";
		StringBuilder sb=new StringBuilder(code);
		sb.replace(4, 6, str);
		String nowCode=sb.toString();
		//工作票编号结束
		//准备启动流程
	    String key=ProcessMarkEnum.WORK_TICKET_HELPSAFE_PROCESS_KEY.getName();	
	    
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
		WorkHelpSafeEntity workHelpSafeEntity=this.findById(Long.valueOf(id));
		workHelpSafeEntity.setCode(nowCode);
		workHelpSafeEntity.setId(Long.valueOf(id));
		workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.TOBEISSUED.getCode())); 
		super.updateEntity(workHelpSafeEntity);
	}
	
	@Override
	public void updateSpnrAgree(WorkHelpSafeEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		
		if(spFlag.equals(WorkBtnTypeEnum.ZX.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.JHRSURE.getCode()));
			workHelpSafeEntity.setChangeAllowId(userEntity.getId());
			workHelpSafeEntity.setChangeAllowName(userEntity.getName());
			workHelpSafeEntity.setChangeAllowDate(new Date());
			super.updateEntity(workHelpSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZXJH.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.DHF.getCode()));
			workHelpSafeEntity.setEndPicId(userEntity.getId());
			workHelpSafeEntity.setEndPicName(userEntity.getName());
			super.updateEntity(workHelpSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.HF.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.DHFJKRSURE.getCode()));
			workHelpSafeEntity.setEndAllowId(userEntity.getId());
			workHelpSafeEntity.setEndAllowName(userEntity.getName());
			super.updateEntity(workHelpSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.HFJH.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.END.getCode()));
			workHelpSafeEntity.setSafeNum(userEntity.getId().toString());
			workHelpSafeEntity.setSafeFlag(userEntity.getName());
			super.updateEntity(workHelpSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.TOBEISSUED.getCode()));
			super.updateEntity(workHelpSafeEntity);
		}
		
	}
	/**
	 * 驳回   zzq 20170614
	 */
	@Override
	public void updateSpnrDisagree(WorkHelpSafeEntity t,SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.ZX.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为驳回
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.BHGZFZR.getCode()));
			super.updateEntity(workHelpSafeEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.HF.getCode())){
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			//更新主表状态为驳回
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.BHGZFZR.getCode()));
			super.updateEntity(workHelpSafeEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			//更新主表状态为废票
			WorkHelpSafeEntity workHelpSafeEntity = this.findById(t.getId());//查询这个表的实体
			workHelpSafeEntity.setWorkStatus(Long.valueOf(WorkHelpStatusEnum.GZPXQ.getCode()));
			super.updateEntity(workHelpSafeEntity);
		}
		
	}
	
	/**
	 * @Description:   新增是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@Override
	public ResultObj addValidate(String uuid) {
		ResultObj resultObj = new ResultObj();
		isaddValidate(uuid);
		return resultObj;
	}
	private boolean isaddValidate(String uuid) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_UUID_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, uuid));
		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(WorkSafeTypeEnum.SAFEONE.getCode())));
		List<WorkSafeEntity> list=workSafeService.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_SAFEONE);
		}
		return true;
	}
	
	/**
	 * @Description:   修改是否填写了安全措施的验证
	 * @author         zhangzq 
	 * @Date           20171124
	 * @throws         Exception
	 */
	@Override
	public ResultObj editValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		iseditValidate(id);
		return resultObj;
	}
	private boolean iseditValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ, Long.valueOf(WorkSafeTypeEnum.SAFEONE.getCode())));
		List<WorkSafeEntity> list=workSafeService.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkHelpSafeException(WorkHelpSafeExceptionType.WORKTICKET_SAFEONE);
		}
		return true;
	}
	@Override
	public List<WorkHelpSafeEntity> searchJan(String yearStart, String yearEnd,
			Map<String, Object> params, Page<WorkHelpSafeEntity> page,String identify,String qualifiedCount,String unQualifiedCount
			) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date yearStartd = sdf.parse(yearStart);
		Date yearEndd=sdf.parse(yearEnd);
		if(page == null) {
			 page = new Page<WorkHelpSafeEntity>();
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
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY is NULL"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkHelpStatusEnum.END.getCode()));
   			List<WorkHelpSafeEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!qualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkHelpStatusEnum.END.getCode()));
   			List<WorkHelpSafeEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkHelpStatusEnum.END.getCode()));
   			List<WorkHelpSafeEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(identify.isEmpty()&&qualifiedCount.isEmpty()&&unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WORKHELPSAFE.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_WORK_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkHelpStatusEnum.END.getCode()));
   			List<WorkHelpSafeEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}
		
		return null;
	}
}