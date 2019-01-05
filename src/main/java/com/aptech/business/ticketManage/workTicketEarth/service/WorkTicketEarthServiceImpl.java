package com.aptech.business.ticketManage.workTicketEarth.service;

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
import com.aptech.business.component.dictionary.WorkSafeTypeEnum;
import com.aptech.business.component.dictionary.WorkStatusEnum;
import com.aptech.business.component.dictionary.WorkTypeEnum;
import com.aptech.business.ticketManage.controlCardRisk.domain.ControlCardRiskEntity;
import com.aptech.business.ticketManage.controlCardRisk.service.ControlCardRiskService;
import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.business.ticketManage.workControlCard.service.WorkControlCardService;
import com.aptech.business.ticketManage.workSafe.domain.WorkSafeEntity;
import com.aptech.business.ticketManage.workSafe.service.WorkSafeService;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketException;
import com.aptech.business.ticketManage.workTicket.exception.WorkTicketExceptionType;
import com.aptech.business.ticketManage.workEarth.domain.WorkEarthEntity;
import com.aptech.business.ticketManage.workEarth.service.WorkEarthService;
import com.aptech.business.ticketManage.workElectric.domain.WorkElectricEntity;
import com.aptech.business.ticketManage.workTicketEarth.dao.WorkTicketEarthDao;
import com.aptech.business.ticketManage.workTicketEarth.domain.WorkTicketEarthEntity;
import com.aptech.business.ticketManage.workTicketEarth.exception.WorkTicketEarthException;
import com.aptech.business.ticketManage.workTicketEarth.exception.WorkTicketEarthExceptionType;
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
@Service("workTicketEarthService")
@Transactional
public class WorkTicketEarthServiceImpl extends AbstractBaseEntityOperation<WorkTicketEarthEntity> implements WorkTicketEarthService {
	
	@Autowired
	private WorkTicketEarthDao workTicketEarthDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private WorkEarthService workEarthService;
	@Autowired
	private WorkControlCardService workControlCardService;
	@Autowired
	private WorkSafeService workSafeService;
	@Autowired
	private ControlCardRiskService controlCardRiskService;
	@Autowired
   	private ActTaskService actTaskService;
	@Override
	public IBaseEntityOperation<WorkTicketEarthEntity> getDao() {
		return workTicketEarthDao;
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
		conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.EARTH.getCode()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	
	@Override
	public void addEntity(WorkTicketEarthEntity t) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		t.setGuarderId(userEntity.getId());
		t.setType(Long.valueOf(WorkTypeEnum.EARTH.getCode()));
		t.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBESUBMIT.getCode()));
		t.setIsSet(Long.valueOf(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Long.valueOf(Istypicaleum.ISSETYES.getCode()));
		t.setStatus(Long.valueOf(DataStatusEnum.NORMAL.ordinal()));
		super.addEntity(t);
		//像电气第一种票表  加记录
		WorkEarthEntity woel=new WorkEarthEntity();
		woel.setWorkticketId(t.getId());
		woel.setRemarkGuarderName(t.getRemarkGuarderName());
		woel.setRemarkResponsibleName(t.getRemarkResponsibleName());
		woel.setRemarkOther(t.getRemarkOther());
		workEarthService.addEntity(woel);
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
		workSafeEntityAuto.setSafeType(Long.valueOf(WorkSafeTypeEnum.SAFETEN.getCode()));
		workSafeEntityAuto.setSignerContent("无");
		workSafeEntityAuto.setOrderSeq(Long.valueOf(1));
		workSafeService.addEntity(workSafeEntityAuto);
		//提交
		String saveOrsubmit=t.getSaveOrSubmit();
		String selectUser=t.getSelectUser();
		if(saveOrsubmit!=null&&!saveOrsubmit.equals("")){
			//准备启动流程
  		    String key=ProcessMarkEnum.WORK_TICKET_EARTH_PROCESS_KEY.getName();	
			Map<String, Object> vars=new HashMap<String,Object>();
	  		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), selectUser);//选定的审批人
		    actTaskService.startProcess(key, t.getId().toString(), vars);
			
			WorkTicketEarthEntity workTicketEarthEntity=this.findById(t.getId());
			workTicketEarthEntity.setId(t.getId());
			workTicketEarthEntity.setWorkStatus(Long.valueOf(WorkStatusEnum.TOBEISSUED.getCode())); 
			this.updateEntity(workTicketEarthEntity);
		}
	}
	
	
	/**
	 * @Description:   自己写的修改方法
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj update(WorkTicketEarthEntity t, Long id) {
		SysUserEntity userEntity= RequestContext.get().getUser();
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(t.getId());
		workTicketEarthEntity.setUnitNameId(t.getUnitNameId());
		workTicketEarthEntity.setGroupId(t.getGroupId());
		workTicketEarthEntity.setGuarderId(userEntity.getId());
		workTicketEarthEntity.setGuarderName(userEntity.getName());
		workTicketEarthEntity.setWorkClassMember(t.getWorkClassMember());
		workTicketEarthEntity.setWorkClassPeople(t.getWorkClassPeople());
		workTicketEarthEntity.setContent(t.getContent());
		workTicketEarthEntity.setAddress(t.getAddress());
		workTicketEarthEntity.setEquipmentCode(t.getEquipmentCode());
		workTicketEarthEntity.setEquipmentName(t.getEquipmentName());
		workTicketEarthEntity.setPlandateStart(t.getPlandateStart());
		workTicketEarthEntity.setPlandateEnd(t.getPlandateEnd());
		workTicketEarthEntity.setFlawCode(t.getFlawCode());
		super.updateEntity(workTicketEarthEntity);
		WorkEarthEntity  workEarthEntity=workEarthService.findById(Long.valueOf(t.getElectricId()));
		workEarthEntity.setRemarkGuarderName(t.getRemarkGuarderName());
		workEarthEntity.setRemarkResponsibleName(t.getRemarkResponsibleName());
		workEarthEntity.setRecoverOther(t.getRemarkOther());
		workEarthService.updateEntity(workEarthEntity);
		return new ResultObj();
	}
	//单个删除的方法 zzq
	@Override
	public void deleteEntity(Serializable id) {
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(id);
		workTicketEarthEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(workTicketEarthEntity);
	}
	/** 批量删除
	 * @author         sunliang 
	 * @Date           2017年6月20日 下午3:34:59 
	 * @throws         Exception
	 */
	@Override
	public ResultObj deleteBulk(List<Integer> ids) {
		
		if(validateisDeleteBulk(ids)){
		}
		return new ResultObj();
	}
	/**
	 * @Description:   批量删除的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 上午11:36:54 
	 * @throws         Exception
	 */
	private boolean validateisDeleteBulk(List<Integer> ids) {
		boolean flag=true;
		for (int i=0;i<ids.size();i++) {
			WorkTicketEarthEntity  workTicketEarthEntity=this.findById(Long.valueOf(ids.get(i)));
			if(workTicketEarthEntity!=null){
				String workStatus=workTicketEarthEntity.getWorkStatus().toString();
				if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
					flag=false;
					break;
				}
			}
		}
		if (flag) {
			for (int i=0;i<ids.size();i++) {
				WorkTicketEarthEntity  workTicketEarthEntity=this.findById(Long.valueOf(ids.get(i)));
				workTicketEarthEntity.setStatus(Long.valueOf(DataStatusEnum.DELETE.ordinal()));
				super.updateEntity(workTicketEarthEntity);
			}
		}else{
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_BATHDELETE);
		}
		
		return true;
	}

	/**
	 * @Description:   鉴定
	 * @author         sunliang 
	 * @Date           2017年6月21日 下午5:01:36 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(WorkTicketEarthEntity t) {
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(Long.valueOf(t.getId()));
		workTicketEarthEntity.setInvalidDate(t.getInvalidDate());
		workTicketEarthEntity.setInvalidId(t.getInvalidId());
		workTicketEarthEntity.setInvalidContent(t.getInvalidContent());
		workTicketEarthEntity.setIdentify(t.getIdentify());
		
		super.updateEntity(workTicketEarthEntity);
		return new ResultObj();
	}
	/**
	 * @Description:   验证是否执行了安全措施
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	@Override
	public ResultObj isExecute(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(id);
		if(validateisExecute(workTicketEarthEntity)){
		}
		return resultObj;
	}
	/**
	 * @Description:   验证是否执行了安全措施的验证方法
	 * @author         sunliang 
	 * @Date           2017年6月28日 下午3:58:01 
	 * @throws         Exception
	 */
	private boolean validateisExecute(WorkTicketEarthEntity workTicketEarthEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEarthEntity.getId()));
		conditions.add(new Condition("C_SAFE_TYPE="+WorkSafeTypeEnum.SAFENINE.getCode()+" or C_SAFE_TYPE="+WorkSafeTypeEnum.SAFETEN.getCode()+""));
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
		return true;
	}
	@Override
	public ResultObj isExecuteSqsy(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(id);
		if(validateisExecuteSqsy(workTicketEarthEntity)){
		}
		return resultObj;
	}
	private boolean validateisExecuteSqsy(WorkTicketEarthEntity workTicketEarthEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEarthEntity.getId()));
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
		return true;
	}
	@Override
	public ResultObj isExecuteSyhf(Long id) {
		ResultObj resultObj = new ResultObj();
		WorkTicketEarthEntity  workTicketEarthEntity=this.findById(id);
		if(validateisExecuteSyhf(workTicketEarthEntity)){
		}
		return resultObj;
	}
	private boolean validateisExecuteSyhf(WorkTicketEarthEntity workTicketEarthEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,workTicketEarthEntity.getId()));
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
		return true;
	}
	/**
	 * @Description:   修改时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj updateValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(isupdateValidate(id)){
		}
		return resultObj;
	}
	private boolean isupdateValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEarthEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEarthEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   单个删除时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
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
		List<WorkTicketEarthEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEarthEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_DELETESTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   提交时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午1:17:42 
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
	       SysUserEntity userEntity= RequestContext.get().getUser();
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEarthEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketEarthException(WorkTicketEarthExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEarthEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.TOBESUBMIT.getCode())){
				throw new WorkTicketEarthException(WorkTicketEarthExceptionType.WORKTICKET_SUBMITSTATUS);
			}
			//提交的时候查询变更人是谁
            conditions.clear();
            conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
            List<WorkEarthEntity> listelectric=workEarthService.findByCondition(conditions, null);
            if(!listelectric.isEmpty()){
                WorkEarthEntity  workEarthEntity=listelectric.get(0);
                if(workEarthEntity.getChangeNewPicId()==null||(workEarthEntity.getChangeNewPicId().toString()).equals("")){
                    //没变更过
                    if(!(userEntity.getId().toString()).equals(workTicket.getGuarderId().toString())){
                        throw new WorkTicketEarthException(WorkTicketEarthExceptionType.WORKTICKET_ONLYSELF);
                    }
                }else{//变更过
                    if(!(userEntity.getId().toString()).equals(workEarthEntity.getChangeNewPicId().toString())){
                        throw new WorkTicketEarthException(WorkTicketEarthExceptionType.WORKTICKET_ONLYSELF);
                    }
                }
            }
		}
		return true;
	}
	/**
	 * @Description:   鉴定的时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@Override
	public ResultObj invalidValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		
		if(isinvalidValidate(id)){
		}
		return resultObj;
	}
	private boolean isinvalidValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEarthEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEarthEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())&&!workStatus.equals(WorkStatusEnum.WORKSTATUS_TYPE_TURNDOWN.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_JIANDINGSTATUS);
			}
		}
		return true;
	}
	/**
	 * @Description:   设置标准票时候的验证
	 * @author         sunliang 
	 * @Date           2017年6月29日 下午2:00:37 
	 * @throws         Exception
	 */
	@Override
	public ResultObj setValidate(Long id) {
		ResultObj resultObj = new ResultObj();
		if(issetValidate(id)){
			//没有异常成功了
			WorkTicketEarthEntity  workTicketEarthEntity=this.findById(id);
			workTicketEarthEntity.setIsSet(Long.valueOf(IssetEnum.ISSETYES.getCode()));
			super.updateEntity(workTicketEarthEntity);
		}
		return resultObj;
	}
	private boolean issetValidate(Long id) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("t.C_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, id));
		List<WorkTicketEarthEntity> list=this.findByCondition(conditions, null);
		if(list.isEmpty()){
			throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_UPDATEDELETE);
		}else{
			WorkTicketEarthEntity  workTicket=list.get(0);
			String workStatus=workTicket.getWorkStatus().toString();
			if(!workStatus.equals(WorkStatusEnum.END.getCode())){
				throw new WorkTicketException(WorkTicketExceptionType.WORKTICKET_ENDSTATUS);
			}
		}
		return true;
	}
}