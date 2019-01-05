package com.aptech.business.ticketManage.operationTicket.service;

import groovy.xml.Entity;

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
import com.aptech.business.component.dictionary.OperationStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.TypicalTicketApproveStatusEnum;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.ticketManage.operationDanger.domain.OperationDangerEntity;
import com.aptech.business.ticketManage.operationDanger.service.OperationDangerService;
import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.business.ticketManage.operationItem.service.OperationItemService;
import com.aptech.business.ticketManage.operationTicket.dao.OperationTicketDao;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketException;
import com.aptech.business.ticketManage.operationTicket.exception.OperationTicketExceptionType;
import com.aptech.business.ticketManage.typicalTicket.dao.TypicalTicketDao;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.service.TypicalTicketService;
import com.aptech.business.ticketManage.workTicketEquip.domain.WorkTicketEquipEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.duties.domain.SysDutiesEntity;
import com.aptech.common.system.duties.service.SysDutiesService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
import com.aptech.common.system.dutiesdetail.service.SysDutiesDetailService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
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
 * 操作票应用管理服务实现类
 * 
 * @author
 * @created 2017-07-12 15:53:44
 * @lastModified
 * @history
 * 
 */
@Service("operationTicketService")
@Transactional
public class OperationTicketServiceImpl extends
		AbstractBaseEntityOperation<OperationTicketEntity> implements
		OperationTicketService {

	@Autowired
	private OperationTicketDao operationTicketDao;
	@Autowired
	private OperationItemService operationItemService;
	@Autowired
	private OperationDangerService operationDangerService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TypicalTicketService typicalTicketService;
	@Autowired
	private TypicalTicketDao typicalTicketDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private SysDutiesDetailService sysDutiesDetailService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private SysDutiesService sysDutiesService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Override
	public IBaseEntityOperation<OperationTicketEntity> getDao() {
		return operationTicketDao;
	}
	/**
	 * @Description: 操作票新增
	 * @author changl
	 * @Date 2017年7月14日 下午4:30:38
	 * @throws Exception
	 */
	public void addEntity(OperationTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setOperateName(userEntity.getName());
		t.setOperateId(userEntity.getId());
		t.setOldid(t.getId());
		t.setStatus(Long.parseLong(OperationStatusEnum.PENDING.getCode()));
		t.setIsSet(Long.parseLong(IssetEnum.ISSETNO.getCode()));
		t.setIstypical(Istypicaleum.ISSETYES.getId());
		t.setOperateItemNum(0);
		t.setOperationCreateDate(new Date());
		this.getDao().addEntity(t);
		operationItemService.updateByMap("updateOperationId", t);
		operationDangerService.updateByMap("updateOperationId", t);
		//关联典型票
		if(t.getTypicalTicketId()!=null&&!t.getTypicalTicketId().equals("")){
			TypicalTicketEntity tEntity= typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			tEntity.setWorkticket_id(t.getId());
			tEntity.setStatus(Long.parseLong(TypicalTicketApproveStatusEnum.PENDING.getCode()));
			typicalTicketDao.updateEntity(tEntity);
			//本身典型票
			t.setIstypical(Istypicaleum.ISSETNO.getId());
			this.getDao().updateEntity(t);
		}
		//验证操作项目、危险控制卡
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_OPERATION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getId()));
		List<OperationItemEntity> operationItemList= operationItemService.findByCondition(conditions, null);
		if(operationItemList.isEmpty()){
			throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_ITEM_NULL);
		}
		if(!StringUtil.isEmpty(t.getUserList())){
			t.setStatus(Long.parseLong(OperationStatusEnum.GUARDIAN.getCode()));
			operationTicketDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
			actTaskService.startProcess(
					ProcessMarkEnum.OPERATIONTICKET_PROCESS_KEY.getName(),
					t.getId().toString(), vars);
		}
		
		//两票设备关联
		List<Condition> eqConditions = new ArrayList<Condition>();
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
				workEquipEntity.setTicketType("2");
				eqConditions.clear();
				eqConditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(eqConditions, null);
				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
				
				workTicketEquipService.addEntity(workEquipEntity);
			}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OPERATIONTICKET.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}

	/**
	 * @Description: 操作票修改
	 * @author changl
	 * @Date 2017年7月14日 下午4:30:38
	 * @throws Exception
	 */
	@Override
	public void updateEntity(OperationTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		OperationTicketEntity tEntity= operationTicketDao.findById(t.getId());
		if(tEntity!=null){
			tEntity.setTypicalTicketId(t.getTypicalTicketId());
		}
		if (validate(tEntity)) {
			
			tEntity.setEquipmentCode(t.getEquipmentCode());
			tEntity.setEquipmentName(t.getEquipmentName());
			tEntity.setWorkText(t.getWorkText());
			tEntity.setRemark(t.getRemark());
			tEntity.setWorkticketCode(t.getWorkticketCode());
			tEntity.setDefectCode(t.getDefectCode());
			tEntity.setUnitId(t.getUnitId());
			tEntity.setStartUserId(t.getStartUserId());
			tEntity.setStartUnitId(t.getStartUnitId());
			tEntity.setEndUserId(t.getEndUserId());
			tEntity.setEndTime(t.getEndTime());
			getDao().updateEntity(tEntity);
			
			//两票设备关联
			List<Condition> conditions = new ArrayList<Condition>();
			String [] codes = t.getEquipmentCode().split(",");
			String [] equipNames = t.getEquipmentName().split(",");
			conditions.add(new Condition("C_WORKTICKET_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
			List<WorkTicketEquipEntity> WorkTicketEquipList = workTicketEquipService.findByCondition(conditions, null);
			if(WorkTicketEquipList!=null && WorkTicketEquipList.size()>0){
				for(int i = 0;i<WorkTicketEquipList.size();i++){
					WorkTicketEquipEntity workEquipEntity = WorkTicketEquipList.get(i);
					workEquipEntity.setEquipCode(codes[i]);
					workEquipEntity.setEquipName(equipNames[i]);
					
					conditions.clear();
					conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
					List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
					workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
					
					workTicketEquipService.updateEntity(workEquipEntity);
				}
		}else {
			for(int i = 0;i<codes.length;i++){
				WorkTicketEquipEntity workEquipEntity = new WorkTicketEquipEntity();
				workEquipEntity.setCreateDate(new Date());
				workEquipEntity.setCreateUserId(userEntity.getId().toString());
				workEquipEntity.setWorkticketId(t.getId());
				workEquipEntity.setEquipCode(codes[i]);
				workEquipEntity.setEquipName(equipNames[i]);
				workEquipEntity.setStatus(DefectStatusEnum.PENDING.getCode());
				workEquipEntity.setTicketType("2");
				conditions.clear();
				conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, codes[i]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
				workEquipEntity.setEquipId(equipLedgeList.get(0).getId().toString());
			
				workTicketEquipService.addEntity(workEquipEntity);
			}
		}
			
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OPERATIONTICKET.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		}
	}
	@Override
	public void update(OperationTicketEntity t) {
			getDao().updateEntity(t);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OPERATIONTICKET.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id) {
		OperationTicketEntity t = this.findById(id);
		if (deletevalidate(t)) {
			getDao().deleteEntity(id);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OPERATIONTICKET.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
	}

	/**
	 * 提交实体
	 * 
	 * @param id
	 */
	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		OperationTicketEntity t = this.findById(id);
		if (validate(t)) {
			if ( String.valueOf(t.getStatus()).equals(TypicalTicketApproveStatusEnum.REJECT.getCode())) {
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_REJECT);
			}
			//验证操作项目、危险控制卡
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("C_OPERATION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getId()));
			List<OperationItemEntity> operationItemList= operationItemService.findByCondition(conditions, null);
			if(operationItemList.isEmpty()){
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_ITEM_NULL);
			}
			t.setStatus(Long.parseLong(OperationStatusEnum.GUARDIAN.getCode()));
			operationTicketDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),
					params.get("userList"));
			actTaskService.startProcess(
					ProcessMarkEnum.OPERATIONTICKET_PROCESS_KEY.getName(),
					id.toString(), vars);
		}
		return resultObj;
	}

	/**
	 * 审批
	 * 
	 * @param id
	 */
	@Override
	public ResultObj approve(OperationTicketEntity t, Map<String, Object> params) {
		if (validateStatus(t)) {
			// 修改状态
			OperationTicketEntity tEntity = this.findById(t.getId());
			tEntity.setStatus(Long.parseLong(params.get("status").toString()));
			String codeCode= params.get("status").toString();
			if(t.getGuardianId()!=null){
				tEntity.setGuardianId(t.getGuardianId());
				tEntity.setGuardianName(t.getGuardianName());
				tEntity.setGuardianDate(new Date());
			}
			if(t.getGuardianId()!=null){
				tEntity.setGuardianId(t.getGuardianId());
				tEntity.setGuardianName(t.getGuardianName());
			}
			if(t.getPicId()!=null){
				tEntity.setPicId(t.getPicId());
				tEntity.setPicName(t.getPicName());
			}
			if(t.getWorkManagerId()!=null){
				tEntity.setWorkManagerId(t.getWorkManagerId());
				tEntity.setWorkManagerName(t.getWorkManagerName());
			}
			/*if(!StringUtil.isEmpty(t.getCode())){
				tEntity.setCode(t.getCode());
			}*/
			if(t.getOperateItemNum()!=null){
				tEntity.setOperateItemNum(t.getOperateItemNum());
			}
			if (codeCode.equals("3")){
				//工作票编号开始
		        Map<String, Object> codeparams=new HashMap<String, Object> ();
				SysUnitEntity sysunit=sysUnitService.findById(tEntity.getUnitId());
				codeparams.put("workcode", sysunit.getNameAB());
				codeparams.put("workTicketType", "1");
				codeparams.put("YM", new Date());
				String code=fourCodeService.getBusinessCode("工作票编号", codeparams);
				String str="";
				StringBuilder sb=new StringBuilder(code);
				sb.replace(3, 5, str);
				String nowCode=sb.toString();
				tEntity.setCode(nowCode);
			}
			if (t.getEndDate()!=null){
				tEntity.setEndDate(t.getEndDate());
			}
			if (t.getStartDate()!=null){
				tEntity.setStartDate(t.getStartDate());
			}
			if(!StringUtil.isEmpty(t.getFileId())){
				tEntity.setFileId(t.getFileId());
			}
			operationTicketDao.updateEntity(tEntity);
			// 设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(), params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(),
					params.get(ExamMarkEnum.RESULT.getCode()).toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);
		}
		return new ResultObj();
	}

	// 基本验证
	public boolean validate(OperationTicketEntity t) {
		if (t == null) {
			throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_NULL);
		}
		if (!(String.valueOf(t.getStatus()).equals(
				DefectStatusEnum.PENDING.getCode()) || String.valueOf(
				t.getStatus()).equals(OperationStatusEnum.REJECT.getCode()))) {
			throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_STATUS);
		}
		if(t.getTypicalTicketId()!=null&&!t.getTypicalTicketId().equals("")){
			TypicalTicketEntity tEntity=typicalTicketService.findById(Long.parseLong(t.getTypicalTicketId()));
			if (!(String.valueOf(tEntity.getApproveStatus()).equals(
					DefectStatusEnum.PENDING.getCode()) || String.valueOf(
							tEntity.getApproveStatus()).equals(TypicalTicketApproveStatusEnum.REJECT.getCode())|| String.valueOf(
									tEntity.getApproveStatus()).equals(TypicalTicketApproveStatusEnum.RESULTS.getCode()))) {
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_STATUS);
			}
		}
		return true;
	}
	// 删除基本验证
		public boolean deletevalidate(OperationTicketEntity t) {
			if (t == null) {
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_NULL);
			}
			if (!String.valueOf(t.getStatus()).equals(
					OperationStatusEnum.PENDING.getCode())) {
				throw new OperationTicketException(OperationTicketExceptionType.OPERATIONTICKET_CODE_STATUS);
			}
			return true;
		}
	// 流程基本验证
	public boolean validateStatus(OperationTicketEntity t) {
		OperationTicketEntity tEntity = this.findById(t.getId());
		if (tEntity == null) {
			throw new OperationTicketException(
					OperationTicketExceptionType.OPERATIONTICKET_CODE_NULL);
		}
//		if (!t.getStatus().toString().equals(tEntity.getStatus().toString())) {
//			throw new OperationTicketException(
//					OperationTicketExceptionType.OPERATIONTICKET_CODE_REPEAT);
//		}
		return true;
	}
	/**
	 * @Description: 查询
	 * @author changl
	 * @Date 2016年11月7日 下午6:00:01
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		page.addOrder(Sort.desc("id"));
		conditions.add(new Condition("T.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,1));
		return findByCondition(conditions, page);
	}
	/**
	 * @Description: 查询台账处查询
	 * @author zhangxb
	 * @Date 2018年4月9日 下午11:00:01
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByConditionForEquip(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		page.addOrder(Sort.desc("id"));
		return operationTicketDao.findByCondition("findByConditionForEquip", conditions, page);
		
	}
	/**
	 * 设置典型票
	 * 
	 * @param  id
	 */
	@Override
	public ResultObj  isSet(OperationTicketEntity t,TypicalTicketEntity tEntity){
		OperationTicketEntity operationTicketEntity=this.getDao().findById(t.getId());
		//验证是否设置
		if(operationTicketEntity.getIsSet().toString().equals(IssetEnum.ISSETYES.getCode().toString())){
			throw new OperationTicketException(
					OperationTicketExceptionType.OPERATIONTICKET_CODE_ISSETYES);
		}
		if(!String.valueOf(operationTicketEntity.getStatus()).equals(
				OperationStatusEnum.RESULTS.getCode())){
			throw new OperationTicketException(
					OperationTicketExceptionType.OPERATIONTICKET_CODE_RESULTS);
		}
		this.getDao().updateEntity(t);
		//添加典型表主表数据
		tEntity.setWorkticket_id(t.getId());
		typicalTicketService.addEntity(tEntity);
		//修改典型表主表数据
		tEntity=typicalTicketService.findById(tEntity.getId());
		tEntity.setStatus(1l);
		tEntity.setApproveStatus(Long.parseLong(TypicalTicketApproveStatusEnum.RESULTS.getCode()));
		typicalTicketService.updateEntity(tEntity);
		return new  ResultObj();
	}
	/**
	 * 引用典型票
	 * 
	 * @param  id
	 */
	@Override
	public ResultObj  yydxp(OperationTicketEntity t){
		//清除
		operationItemService.updateByMap("deleteOperationId", t);
		operationDangerService.updateByMap("deleteOperationId", t);
		List<Condition> conditions =new  ArrayList<Condition>();
		conditions.add(new Condition("C_OPERATION_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getOldid()));
		//查询引用票
		List<OperationItemEntity> operationItemList=operationItemService.findByCondition(conditions, null);
		List<OperationDangerEntity> operationDangerList=operationDangerService.findByCondition(conditions, null);
		//新增
		for(OperationItemEntity tEntity:operationItemList){
			tEntity.setOperationId(t.getId());
			tEntity.setSimulationName("");
			tEntity.setActual("");
			operationItemService.addEntity(tEntity);
		}
		for(OperationDangerEntity tEntity:operationDangerList){
			tEntity.setOperationId(t.getId());
			operationDangerService.addEntity(tEntity);
		}
		return new  ResultObj();
	}
	/**
	 * @Description:   鉴定
	 * @author         changl 
	 * @Date           2017年12月14日 下午4:51:53 
	 * @throws         Exception
	 */
	@Override
	public ResultObj saveInvalid(OperationTicketEntity  t) {
		OperationTicketEntity  operationTicketEntity=this.findById(Long.valueOf(t.getId()));
		operationTicketEntity.setInvalidDate(t.getInvalidDate());
		operationTicketEntity.setInvalidId(t.getInvalidId());
		operationTicketEntity.setInvalidContent(t.getInvalidContent());
		operationTicketEntity.setIdentify(t.getIdentify());
		super.updateEntity(operationTicketEntity);
		return new ResultObj();
	}
	@Override
	public List<OperationTicketEntity> searchJan(String yearStart, String yearEnd,
			Map<String, Object> params, Page<OperationTicketEntity> page,String identify,String qualifiedCount,String unQualifiedCount
			) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date yearStartd = sdf.parse(yearStart);
		Date yearEndd=sdf.parse(yearEnd);
		if(page == null) {
			 page = new Page<OperationTicketEntity>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
   		page.addOrder(Sort.desc("C_ID"));
   		if(!identify.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//   			conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
//   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY is NULL"));
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getCode()));
   			List<OperationTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!qualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
//   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getCode()));
   			List<OperationTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(!unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
//   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_IDENTIFY", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "2"));
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getCode()));
   			List<OperationTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}else if(identify.isEmpty()&&qualifiedCount.isEmpty()&&unQualifiedCount.isEmpty()){
   			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
   			//本身是工作票
   			conditions.add(new Condition("t.C_ISTYPICAL", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
   			//第一种工作票
//   			conditions.add(new Condition("t.C_TYPE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, WorkTypeEnum.OPERATION.getCode()));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.GE, yearStartd));
   			conditions.add(new Condition("t.C_END_TIME", FieldTypeEnum.DATE, MatchTypeEnum.LE, yearEndd));
   			conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, OperationStatusEnum.RESULTS.getCode()));
   			List<OperationTicketEntity> resultList = super.findByCondition(conditions, page);	
   			return resultList;
   		}
		
		return null;
	}
	/**
	 * @Description:   新增时候的验证  
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj addValidate(SysUserEntity userEntity) {
		ResultObj resultObj = new ResultObj();
		isaddValidate(userEntity);
		return resultObj;
	}
	private boolean isaddValidate(SysUserEntity userEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		//conditions.add(new Condition("t.C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));
		conditions.add(new Condition("T.C_OPERATE_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, userEntity.getId()));
		//WorkStatusEnum.END.getCode;  WorkStatusEnum.WORKSTATUS_TYPE_INVALID.getCode;
		String [] arr2 = {OperationStatusEnum.INVALID.getCode(),OperationStatusEnum.RESULTS.getCode()};
		conditions.add(new Condition("T.C_STATUS", FieldTypeEnum.LONG, MatchTypeEnum.NOT_IN, arr2));
		conditions.add(new Condition("T.C_ISTYPICAL", FieldTypeEnum.INT, MatchTypeEnum.EQ, 1));
		List<OperationTicketEntity> list=this.findByCondition(conditions, null);
		if(!list.isEmpty()){
			throw new OperationTicketException(OperationTicketExceptionType.WORKTICKET_ISHAVE);
		}
		return true;
	}
	
	/**
	 * @Description:   新增时候的验证  
	 * @author         zhangzq 
	 * @Date           2017年6月29日 下午1:17:42 
	 * @throws         Exception
	 */
	@Override
	public ResultObj addCzpValidate(SysUserEntity userEntity) {
		ResultObj resultObj = new ResultObj();
		isaddCzpValidate(userEntity);
		return resultObj;
	}
	private boolean isaddCzpValidate(SysUserEntity userEntity) {
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("C_USER_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, userEntity.getId()));
		conditions.add(new Condition("C_UNIT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, userEntity.getUnitId()));
		List<UserUnitRelEntity> unitRelEntities=userUnitRelService.findByCondition(conditions, null);
		Long unitRelId = unitRelEntities.get(0).getId();
		
		conditions.clear();
		conditions.add(new Condition("C_USER_UNIT_REL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, unitRelId));
		List<SysDutiesDetailEntity> list=sysDutiesDetailService.findByCondition(conditions, null);
		boolean flag=false;
		if(!list.isEmpty()){
			for (SysDutiesDetailEntity sysDutiesDetailEntity : list) {
				String dutiesId=sysDutiesDetailEntity.getDutiesId();
				SysDutiesEntity  sysDutiesEntity=sysDutiesService.findById(Long.valueOf(dutiesId));
				if(sysDutiesEntity.getCode().equals("1012")){//工作负责人   运行专工1014
					flag=true;
					break;
				}else{
					flag=false;
				}
			}
			if(!flag){
				throw new OperationTicketException(OperationTicketExceptionType.SFKYYYDXP);
			}
			
		}else{
			throw new OperationTicketException(OperationTicketExceptionType.SFKYYYDXP);
		}
		return true;
	}
}