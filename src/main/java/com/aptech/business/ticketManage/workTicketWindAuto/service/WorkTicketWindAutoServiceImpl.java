package com.aptech.business.ticketManage.workTicketWindAuto.service;

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

import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.WorkBtnTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicket.dao.WorkTicketDao;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketException;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;
import com.aptech.business.ticketManage.workTicket.service.WorkTicketService;
import com.aptech.business.ticketManage.workTicketWindFlow.domain.WorkTicketWindEntity;
import com.aptech.business.ticketManage.workTicketWindFlow.exception.WorkTicketWindException;
import com.aptech.business.ticketManage.workTicketWindFlow.exception.WorkTicketWindExceptionType;
import com.aptech.business.ticketManage.workTicketWindFlow.service.WorkTicketWindService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
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
 * @created 2017-06-02 11:50:39
 * @lastModified 
 * @history
 *
 */
@Service("workTicketWindAutoService")
@Transactional
public class WorkTicketWindAutoServiceImpl extends AbstractBaseEntityOperation<WorkTicketEntity> implements WorkTicketWindAutoService {
	
	@Autowired
	private WorkTicketDao workTicketDao;
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
	private WorkTicketService workTicketService;
	@Autowired
	private WorkTicketWindService workTicketWindService;
	@Override
	public WorkTicketDao getDao() {
		return workTicketDao;
	}

	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("createDate"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		for(Condition condition :conditions){
			if(condition.getFieldName().equals("code")){
				condition.setFieldName("t.C_CODE");
			}
		}
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		//本身是工作票
		conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
	
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.WINDAUTO.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	@Override
	public void addEntity(WorkTicketEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.WINDAUTO.getCode()));
		t.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		t.setCreateDate(new Date());
		super.addEntity(t);
		// 加记录
		WorkTicketWindEntity woel=new WorkTicketWindEntity();
		woel.setWorkticketId(t.getId());
		woel.setRemarkGuarderName(t.getRemarkGuarderName());
		woel.setRemarkResponsibleName(t.getRemarkResponsibleName());
		woel.setRemarkOther(t.getRemarkOther());
		workTicketWindService.addEntity(woel);
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
		//默认把安全措施的签发人填写 和许可人填写加无的记录
		WorkSafeEntity workSafeEntityAuto=null;
		workSafeEntityAuto=new WorkSafeEntity();
		workSafeEntityAuto.setWorkticketId(t.getId());
		workSafeEntityAuto.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFEFOURTEEN.getId()));
		workSafeEntityAuto.setSignerContent("无");
		workSafeEntityAuto.setOrderSeq(Long.valueOf(1));
		workSafeService.addEntity(workSafeEntityAuto);
		workSafeEntityAuto=new WorkSafeEntity();
		workSafeEntityAuto.setWorkticketId(t.getId());
		workSafeEntityAuto.setSafeType(Long.valueOf(Long.valueOf(WorkSafeTypeEnum.SAFEFOURTEEN.getId())));
		workSafeEntityAuto.setSignerContent("无");
		workSafeEntityAuto.setOrderSeq(Long.valueOf(1));
		workSafeService.addEntity(workSafeEntityAuto);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			WorkTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setId(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			this.updateEntity(workTicketEntity);
		}
	}
	
	
	@Override
	public ResultObj update(WorkTicketEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		WorkTicketEntity  workTicketEntity=this.findById(t.getId());
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
		isupdateValidate(id);
		super.updateEntity(workTicketEntity);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,id));//唯一编号
		Page<WorkTicketWindEntity> page = new Page<WorkTicketWindEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		List<WorkTicketWindEntity>  wrkTicketWindEntityList=workTicketWindService.findByCondition(conditions, page);
		if(!wrkTicketWindEntityList.isEmpty()){//只返回一条
			WorkTicketWindEntity wrkTicketWindEntity2=wrkTicketWindEntityList.get(0);
			wrkTicketWindEntity2.setRemarkGuarderName(t.getRemarkGuarderName());
			wrkTicketWindEntity2.setRemarkResponsibleName(t.getRemarkResponsibleName());
			wrkTicketWindEntity2.setRecoverOther(t.getRemarkOther());
			workTicketWindService.updateEntity(wrkTicketWindEntity2);
		}else{
			throw new WorkTicketWindException(WorkTicketWindExceptionType.WORKTICKET_DELETEFAIL);
		}
		return new ResultObj();
	}
	
	@Override
	public ResultObj isupdateValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATESTATUS);
			}
		}
		return new ResultObj();
	}

	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		boolean flag=true;
		for (int i=0;i<ids.size();i++) {
			WorkTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
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
				WorkTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
				workTicketEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(workTicketEntity);
			}
		}else{
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_BATHDELETE);
		}
		return new ResultObj();
	}
	
	@Override
	public void deleteEntity(Serializable id) {
		WorkTicketEntity  workTicketEntity=this.findById(id);
		deleteValidate(workTicketEntity.getId());
		workTicketEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(workTicketEntity);
	}


	/**
	 * @Description:   鉴定
	 * @author         zhangzq 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(WorkTicketEntity t) {
		WorkTicketEntity  workTicketEntity=this.findById(Long.valueOf(t.getId()));
		workTicketEntity.setInvalidDate(t.getInvalidDate());
		workTicketEntity.setInvalidId(t.getInvalidId());
		workTicketEntity.setInvalidContent(t.getInvalidContent());
		workTicketEntity.setIdentify(t.getIdentify());
		
		super.updateEntity(workTicketEntity);
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
		WorkTicketEntity  workTicketEntity=this.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEntity.getId()));
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
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_ISEXECUTE);
		}
		return resultObj;
	}
	
	@Override
	public ResultObj isExecuteSqsy(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketEntity  workTicketEntity=this.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEntity.getId()));
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
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_ISEXECUTE);
		}
		return resultObj;
	}
	@Override
	public ResultObj isExecuteSyhf(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketEntity  workTicketEntity=this.findById(id);
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEntity.getId()));
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
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_ISEXECUTE);
		}
		return resultObj;
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
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_SUBMITSTATUS);
			}
		}
		List<Condition> controlCardRisk=new ArrayList<Condition>();
		controlCardRisk.add(new Condition("workticketId", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkControlCardEntity> controlCardList = workControlCardService.findByCondition(controlCardRisk, null);
		controlCardRisk.clear();
		if(controlCardList!=null && controlCardList.size()>0){
			WorkControlCardEntity controlCardEntity = controlCardList.get(0);
			if((controlCardEntity.getCardSort()== null  || "".equals(controlCardEntity.getCardSort()))
					&& (controlCardEntity.getCardSortTwo()==null || "".equals(controlCardEntity.getCardSortTwo()))
					&&  (controlCardEntity.getCardSortThree()==null || "".equals(controlCardEntity.getCardSortThree()))
					&&  (controlCardEntity.getCardSortFour()==null || "".equals(controlCardEntity.getCardSortFour()))){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_NOCONTROLCARD);
			}
			controlCardRisk.add(new Condition("controlId", FieldTypeEnum.LONG, MatchTypeEnum.EQ, controlCardEntity.getId()));
			List<ControlCardRiskEntity> controlCardRiskList = controlCardRiskService.findByCondition(controlCardRisk, null);

			if(controlCardRiskList==null || controlCardRiskList.size()==0){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_NOCONTROLCARD);
			}
		}
		
		return resultObj;
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
		
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_JIANDINGSTATUS);
			}
		}
		return resultObj;
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
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_ENDSTATUS);
			}
		}
		//没有异常成功了
		WorkTicketEntity  workTicketEntity=this.findById(id);
		workTicketEntity.setIsSet(Long.valueOf(IssetEnum.ISSETYES.getCode()));
		super.updateEntity(workTicketEntity);
		return resultObj;
	}

	/**提交工作票
	 * @Description:   
	 * @throws         Exception
	 */
	public void submit(String id,String selectUser) {
			//准备启动流程
	    String key=ProcessMarkEnum.WORK_TICKET_WINDAUTO_PROCESS_KEY.getName();	
	    
		Map<String, Object> vars=new HashMap<String,Object>();
  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
	    actTaskService.startProcess(key, id, vars);
		
		WorkTicketEntity workTicketEntity=workTicketService.findById(Long.valueOf(id));
		workTicketEntity.setId(Long.valueOf(id));
		workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
		
		workTicketService.updateEntity(workTicketEntity);
	}

	@Override
	public ResultObj deleteValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_DELETESTATUS);
			}
		}
		return new ResultObj();
	}

	@Override
	public void updateSpnrAgree(WorkTicketWindEntity t, SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setSignerId(userEntity.getId());
			WorkTicketWindEntity.setSignerName(userEntity.getName());
			WorkTicketWindEntity.setSignerDate(new Date());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TICKETS.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setOndutyId(userEntity.getId());
			WorkTicketWindEntity.setOndutyName(userEntity.getName());
			WorkTicketWindEntity.setGetticketTime(new Date());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.LONGVALUESURE.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDutyMonitorId(userEntity.getId());
			WorkTicketWindEntity.setDutyMonitorName(userEntity.getName());
			WorkTicketWindEntity.setApproveStarttime(t.getApproveStarttime());
			WorkTicketWindEntity.setApproveEndtime(t.getApproveEndtime());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态为未执行
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOW.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.XK.getCode())){
			WorkTicketWindEntity elentity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketEntity workEntity=this.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setChangeAllowDate(t.getQksjZhu());
			workEntity.setChangeAllowId(userEntity.getId());
			workEntity.setChangeAllowName(userEntity.getName());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			//许可的时候需要生产工作票编号
			SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHssmm");
			String date=sf.format(new Date());
			workEntity.setCode("ZK"+date);
			this.updateEntity(workEntity);
			//更新电气第一种票表  
			elentity.setId(t.getId());
			elentity.setAllowPicPersonId(t.getAllowPicPersonId());
			elentity.setAllowPicPersonName(t.getAllowPicPersonName());
			workTicketWindService.updateEntity(elentity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBG.getCode())){//工作负责人变更
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			SysUserEntity  sysUserEntity=sysUserService.findById(Long.valueOf(t.getChangeNewPicId()));
			WorkTicketWindEntity.setChangeOldPicId(t.getChangeOldPicId());
			WorkTicketWindEntity.setChangeOldPicName(t.getChangeOldPicName());
			WorkTicketWindEntity.setChangeNewPicId(t.getChangeNewPicId());
			WorkTicketWindEntity.setChangeNewPicName(sysUserEntity.getName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGEISSUED.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){//工作负责人变更  签发
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setChangeSignerId(t.getChangeSignerId());
			WorkTicketWindEntity.setChangeSignerName(t.getChangeSignerName());
			WorkTicketWindEntity.setChangeSignerDate(t.getChangeSignerDate());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.CHANGALLOW.getCode()));
			this.updateEntity(workTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){//工作负责人变更  许可
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setChangeAllowId(t.getChangeAllowId());
			WorkTicketWindEntity.setChangeAllowName(t.getChangeAllowName());
			WorkTicketWindEntity.setChangeAllowDate(t.getChangeAllowDate());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBD.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonGroup(t.getWorkPersonGroup());
			WorkTicketWindEntity.setWorkPersonPicId(userEntity.getId());
			WorkTicketWindEntity.setWorkPersonPicName(userEntity.getName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonPicId(t.getWorkPersonPicId());
			WorkTicketWindEntity.setWorkPersonPicName(t.getWorkPersonPicName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERDELAY.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayDate(t.getDelayDate());
			WorkTicketWindEntity.setDelayDutyMonitorId(t.getDelayDutyMonitorId());
			WorkTicketWindEntity.setDelayDutyMonitorName(t.getDelayDutyMonitorName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERALLOW.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQXK.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayAllowId(t.getDelayAllowId());
			WorkTicketWindEntity.setDelayAllowName(t.getDelayAllowName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWPIC.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQFZR.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayPicId(t.getDelayPicId());
			WorkTicketWindEntity.setDelayPicName(t.getDelayPicName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			WorkTicketWindEntity.setEndGroupIndex(t.getEndGroupIndex());
			WorkTicketWindEntity.setEndGroup(t.getEndGroup());
			WorkTicketWindEntity.setEndStandIndex(t.getEndStandIndex());
			WorkTicketWindEntity.setEndStand(t.getEndStand());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			
			WorkTicketEntity workEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(t.getEndTimeZhu());
			workEntity.setEndPicId(t.getEndPicIdZhu());
			workEntity.setEndPicName(t.getEndPicNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWEND.getCode()));
			this.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			
			WorkTicketEntity workEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			//更新工作票表
			workEntity.setEndAllowId(t.getEndAllowIdZhu());
			workEntity.setEndAllowName(t.getEndAllowNameZhu());
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.END.getCode()));
			this.updateEntity(workEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSY.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
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
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWAPPLY.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
			WorkTicketWindEntity.setRunSureDate(t.getRunSureDate());
			WorkTicketWindEntity.setRunAllowId(t.getRunAllowId());
			WorkTicketWindEntity.setRunAllowName(t.getRunAllowName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERAPPLY.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SQSYZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
			WorkTicketWindEntity.setRunManagerId(t.getRunManagerId());
			WorkTicketWindEntity.setRunManagerName(t.getRunManagerName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.APPLY.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopPicId(t.getStopPicId());
			WorkTicketWindEntity.setStopPicName(t.getStopPicName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.ALLOWREGAIN.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopSureDate(t.getStopSureDate());
			WorkTicketWindEntity.setStopAllowId(t.getStopAllowId());
			WorkTicketWindEntity.setStopAllowName(t.getStopAllowName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.MANAGERREGAIN.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.SYHFZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity=workTicketWindService.findById(t.getId());
			//更新电气第一种票表
			WorkTicketWindEntity.setStopManagerId(t.getStopManagerId());
			WorkTicketWindEntity.setStopManagerName(t.getStopManagerName());
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.REGAIN.getCode()));
			this.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZTJ.getCode())){
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode()));
			this.updateEntity(workTicketEntity);
		}
		
	}

	@Override
	public void updateSpnrDisagree(WorkTicketWindEntity t,
			SysUserEntity userEntity) {
		String spFlag=t.getSpFlag();
		if(spFlag.equals(WorkBtnTypeEnum.QF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新从表信息
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			super.updateEntity(workTicketEntity);
			
		}else if(spFlag.equals(WorkBtnTypeEnum.SP.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//上一步的字段也清空1
			WorkTicketWindEntity.setSignerId(null);
			WorkTicketWindEntity.setSignerName(null);
			WorkTicketWindEntity.setSignerDate(null);
			//上一步的字段也清空2
			WorkTicketWindEntity.setOndutyId(null);
			WorkTicketWindEntity.setOndutyName(null);
			WorkTicketWindEntity.setGetticketTime(null);
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			super.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZZQZ.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
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
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态为驳回
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode()));
			super.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGQF.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			WorkTicketWindEntity.setChangeOldPicId(null);
			WorkTicketWindEntity.setChangeOldPicName(null);
			WorkTicketWindEntity.setChangeNewPicId(null);
			WorkTicketWindEntity.setChangeNewPicName(null);
			WorkTicketWindEntity.setChangeSignerId(null);
			WorkTicketWindEntity.setChangeSignerName(null);
			WorkTicketWindEntity.setChangeSignerDate(null);
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			super.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZFZRBGXK.getCode())){
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
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
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			super.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.GZRYBDFZR.getCode())){//工作人员变动
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setWorkPersonGroup(null);
			WorkTicketWindEntity.setWorkPersonPicId(null);
			WorkTicketWindEntity.setWorkPersonPicName(null);
			workTicketWindService.updateEntity(WorkTicketWindEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.YQZZQZ.getCode())){
			//更新电气第一种票表
			WorkTicketWindEntity WorkTicketWindEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			WorkTicketWindEntity.setDelayDate(null);
			WorkTicketWindEntity.setDelayDutyMonitorId(null);
			WorkTicketWindEntity.setDelayDutyMonitorName(null);
			workTicketWindService.updateEntity(WorkTicketWindEntity);
			//更新主表状态
			WorkTicketEntity workTicketEntity=this.findById(WorkTicketWindEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			super.updateEntity(workTicketEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.ZJXK.getCode())){
			WorkTicketWindEntity elentity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新电气第一种票表
			elentity.setEndGroupIndex(null);
			elentity.setEndGroup(null);
			elentity.setEndStandIndex(null);
			elentity.setEndStand(null);
			workTicketWindService.updateEntity(elentity);
			
			WorkTicketEntity workEntity=this.findById(elentity.getWorkticketId());
			//更新工作票表
			workEntity.setEndTime(null);
			workEntity.setEndPicId(null);
			workEntity.setEndPicName(null);
			workEntity.setEndAllowId(null);
			workEntity.setEndAllowName(null);
			workEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.PICSURE.getCode()));
			super.updateEntity(workEntity);
		}else if(spFlag.equals(WorkBtnTypeEnum.FP.getCode())){
			WorkTicketWindEntity workElectricTwoEntity = workTicketWindService.findById(t.getId());//查询这个表的实体
			//更新主表状态为废票
			WorkTicketEntity workTicketEntity=this.findById(workElectricTwoEntity.getWorkticketId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode()));
			super.updateEntity(workTicketEntity);
		}		
	}

}