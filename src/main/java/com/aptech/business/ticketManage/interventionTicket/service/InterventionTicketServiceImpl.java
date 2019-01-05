package com.aptech.business.ticketManage.interventionTicket.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.IssetEnum;
import com.aptech.business.component.dictionary.Istypicaleum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.interventionTicket.dao.InterventionTicketDao;
import com.aptech.business.ticketManage.interventionTicket.domain.InterventionTicketEntity;
import com.aptech.business.ticketManage.interventionTicket.exception.InterventionTicketException;
import com.aptech.business.ticketManage.interventionTicket.exception.InterventionTicketExceptionType;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicketTwo.exception.WorkTicketTwoException;
import com.aptech.business.ticketManage.workTicketTwo.exception.WorkTicketTwoExceptionType;
import com.aptech.business.ticketManage.workticketIntervention.domain.WorkticketInterventionEntity;
import com.aptech.business.ticketManage.workticketIntervention.service.WorkticketInterventionService;
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
 * @created 2017-06-29 18:50:39
 * @lastModified 
 * @history
 *
 */
@Service("interventionTicketService")
@Transactional
public class InterventionTicketServiceImpl extends AbstractBaseEntityOperation<InterventionTicketEntity> implements InterventionTicketService {
	
	@Autowired
	private InterventionTicketDao interventionTicketDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private WorkticketInterventionService workticketInterventionService;
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
	@Override
	public IBaseEntityOperation<InterventionTicketEntity> getDao() {
		return interventionTicketDao;
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
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.INTERVENTION.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(InterventionTicketEntity t) {
		if(!StringUtil.isEmpty(t.getSaveOrSubmit())){
			//加验证
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("C_UUID_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getUuidWorkTicket()));
			List<WorkSafeEntity> workSafeList=workSafeService.findByCondition(conditions, null);
			if(workSafeList.isEmpty()){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_SAFE_NULL);
			}
			if(StringUtil.isEmpty(t.getCardSort())&&StringUtil.isEmpty(t.getCardSortTwo())&&
					StringUtil.isEmpty(t.getCardSortThree())&&StringUtil.isEmpty(t.getCardSortFour())){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CARDSORT_NULL);
			}
			List<ControlCardRiskEntity> controlCardRiskList=controlCardRiskService.findByCondition(conditions, null);
			if(controlCardRiskList.isEmpty()){
				throw new InterventionTicketException(InterventionTicketExceptionType.WORKTICKET_CONTROLCARDRISK_NULL);
			}
		}
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.INTERVENTION.getCode()));
		t.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
		//像电气第一种票表  加记录
		WorkticketInterventionEntity woel=new WorkticketInterventionEntity();
		woel.setWorkticketId(t.getId());
		woel.setRemarkOther(t.getRemarkOther());
		workticketInterventionService.addEntity(woel);
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
  		    String key=ProcessMarkEnum.INTERVENTIONTICKE_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
			InterventionTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setId(t.getId());
			workTicketEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			this.updateEntity(workTicketEntity);
		}
		//
		if(t.getTypicalTicketId()!=null&&!t.getTypicalTicketId().equals("")){
			TypicalTicketEntity tEntity= typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			tEntity.setWorkticket_id(t.getId());
			tEntity.setStatus(1l);
			typicalTicketService.updateEntity(tEntity);
			//更新状态本身是典型票0
			InterventionTicketEntity workTicketEntity=this.findById(t.getId());
			workTicketEntity.setIstypical(Long.valueOf(0));
			this.getDao().updateEntity(workTicketEntity);
		}
	}
	
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         changl 
	 * @Date           2017年8月4日 下午3:20:11 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(InterventionTicketEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		InterventionTicketEntity  workTicketEntity=this.findById(t.getId());
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
		super.updateEntity(workTicketEntity);
		WorkticketInterventionEntity  workticketInterventionEntity=workticketInterventionService.findById(Long.valueOf(t.getElectricId()));
//		workticketInterventionEntity.setRemarkGuarderName(t.getRemarkGuarderName());
//		workticketInterventionEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workticketInterventionEntity.setRemarkOther(t.getRemarkOther());
//		workticketInterventionEntity.setWorkCondition(t.getWorkCondition());
		workticketInterventionService.updateEntity(workticketInterventionEntity);
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
		InterventionTicketEntity  workTicketEntity=this.findById(id);
		workTicketEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
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
			InterventionTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
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
				InterventionTicketEntity  workTicketEntity=this.findById(Long.valueOf(ids.get(i)));
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
	public ResultObj saveInvalid(InterventionTicketEntity t) {
		InterventionTicketEntity  workTicketEntity=this.findById(Long.valueOf(t.getId()));
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
		InterventionTicketEntity  workTicketEntity=this.findById(id);
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
	private boolean validateisExecute(InterventionTicketEntity workTicketEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE", FieldTypeEnum.LONG, MatchTypeEnum.EQ,WorkSafeTypeEnum.SAFEONE.getCode()));
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
		List<InterventionTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			InterventionTicketEntity  workTicket=list.get(0);
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
		List<InterventionTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			InterventionTicketEntity  workTicket=list.get(0);
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
		List<InterventionTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			InterventionTicketEntity  workTicket=list.get(0);
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
		List<InterventionTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			InterventionTicketEntity  workTicket=list.get(0);
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
			InterventionTicketEntity  workTicketEntity=this.findById(id);
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
		List<InterventionTicketEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketTwoException(WorkTicketTwoExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			InterventionTicketEntity  workTicket=list.get(0);
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
	/**
	 * 引用典型票
	 * 
	 * @param  id
	 */
	@Override
	public ResultObj  yydxp(InterventionTicketEntity t){
		//清除
		t.setUuidCode(t.getUuidWorkTicket());
	    workSafeService.deleteByCondition("deleteByUuid", t);
	    controlCardRiskService.deleteByCondition("deleteByUuid", t);
		List<Condition> conditions =new  ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getOldid()));
		//查询引用票
		List<WorkSafeEntity> workSafeList=workSafeService.findByCondition(conditions, null);
		List<WorkControlCardEntity> workControlCardList=workControlCardService.findByCondition(conditions, null);
			//新增
			for(WorkSafeEntity tEntity:workSafeList){
				tEntity.setUuidCode(t.getUuidWorkTicket());
				tEntity.setExecuteSituation("");
				tEntity.setWorkticketId(null);
				workSafeService.addEntity(tEntity);
			}
			if(!workControlCardList.isEmpty()){
				conditions.clear();
				conditions.add(new Condition("C_CONTROL_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,workControlCardList.get(0).getId()));
				List<ControlCardRiskEntity> controlCardRiskList= controlCardRiskService.findByCondition(conditions, null);
			for(ControlCardRiskEntity tEntity:controlCardRiskList){
				tEntity.setControlId(null);
				tEntity.setUuidCode(t.getUuidWorkTicket());
				controlCardRiskService.addEntity(tEntity);
			}
		}
		return new  ResultObj();
	}
}