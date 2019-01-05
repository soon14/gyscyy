package com.aptech.business.ticketManage.typicalTicket.service;

import java.io.Serializable;
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
import com.aptech.business.component.dictionary.TypicalTicketApproveStatusEnum;
import com.aptech.business.component.dictionary.TypicalTicketTypeEnum;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.ticketManage.operationTicket.dao.OperationTicketDao;
import com.aptech.business.ticketManage.operationTicket.domain.OperationTicketEntity;
import com.aptech.business.ticketManage.operationTicket.service.OperationTicketService;
import com.aptech.business.ticketManage.typicalTicket.dao.TypicalTicketDao;
import com.aptech.business.ticketManage.typicalTicket.domain.TypicalTicketEntity;
import com.aptech.business.ticketManage.typicalTicket.exception.TypicalTicketException;
import com.aptech.business.ticketManage.typicalTicket.exception.TypicalTicketExceptionType;
import com.aptech.business.ticketManage.workTicket.domain.WorkTicketEntity;
import com.aptech.business.ticketManage.workTicketEquip.service.WorkTicketEquipService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
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
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 典型票应用管理服务实现类
 * 
 * @author
 * @created 2017-07-20 15:55:55
 * @lastModified
 * @history
 * 
 */
@Service("typicalTicketService")
@Transactional
public class TypicalTicketServiceImpl extends
		AbstractBaseEntityOperation<TypicalTicketEntity> implements
		TypicalTicketService {

	@Autowired
	private TypicalTicketDao typicalTicketDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OperationTicketDao operationTicketDao;
	@Autowired
	private OperationTicketService operationTicketService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private WorkTicketEquipService workTicketEquipService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<TypicalTicketEntity> getDao() {
		return typicalTicketDao;
	}

	/**
	 * @Description: 新增典型票
	 * @author changl
	 * @Date 2017年7月21日 上午10:32:18
	 * @throws Exception
	 */
	public void addEntity(TypicalTicketEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setUnitId(userEntity.getUnitId());
//		t.setUnitName(userEntity.getUnitName());
		SysUnitEntity unitEntity = sysUnitService.findById(userEntity.getUnitId());
		t.setUnitName(unitEntity.getName());
		t.setCreatePersonId(userEntity.getId());
		t.setCreatePersonName(userEntity.getName());
		t.setCreateDate(new Date());
		t.setTicketType(Long.parseLong(TypicalTicketTypeEnum.OPERATIONTICKET
				.getCode()));
		t.setApproveStatus(Long
				.parseLong(TypicalTicketApproveStatusEnum.PENDING.getCode()));
		t.setStatus(0l);
		t.setFlag("1");
		getDao().addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TYPICALTICKET.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}

	/**
	 * @Description: 修改典型票
	 * @author changl
	 * @Date 2017年7月21日 上午10:32:18
	 * @throws Exception
	 */
	public void updateEntity(TypicalTicketEntity t) {
		TypicalTicketEntity tEntity = typicalTicketDao.findById(t.getId());
		if (validate(tEntity)) {
			tEntity.setName(t.getName());
			tEntity.setFlag("2");
			getDao().updateEntity(tEntity);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TYPICALTICKET.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
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
		TypicalTicketEntity t = this.findById(id);
		if (validate(t)) {
			t.setApproveStatus(Long.parseLong(TypicalTicketApproveStatusEnum.OVERHAUL.getCode()));
			t.setFlag("3");
			typicalTicketDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
				//新增审批流程
				actTaskService.startProcess(
						ProcessMarkEnum.TYPICALTICKET_PROCESS_KEY20.getName(),
						id.toString(), vars);
		}
		return resultObj;
	}
	/**
	 * 修改提交实体
	 * 
	 * @param id
	 */
	@Override
	public ResultObj editSubmit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		TypicalTicketEntity t = this.findById(id);
		if (validate(t)) {
			t.setApproveStatus(Long.parseLong(TypicalTicketApproveStatusEnum.OVERHAUL.getCode()));
			t.setFlag("3");
			typicalTicketDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),params.get("userList"));
		
			actTaskService.startProcess(ProcessMarkEnum.TYPICALTICKET_PROCESS_KEY30.getName(),id.toString(), vars);
		
		}
		return resultObj;
	}
	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id) {
		TypicalTicketEntity t = this.findById(id);
		if (validate(t)) {
			//修典型票标识
			OperationTicketEntity tEntity= operationTicketDao.findById(t.getWorkticket_id());
			if(tEntity!=null){
				tEntity.setIsSet(Long.parseLong(IssetEnum.ISSETNO.getCode()));
				operationTicketDao.updateEntity(tEntity);
			}
			getDao().deleteEntity(id);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.TYPICALTICKET.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		}
	}
	// 基本验证
	public boolean validate(TypicalTicketEntity t) {
		if (t == null) {
			throw new TypicalTicketException(TypicalTicketExceptionType.TYPICALTICKET_CODE_NULL);
		}
		if (!(String.valueOf(t.getApproveStatus()).equals(
				DefectStatusEnum.PENDING.getCode()) || String.valueOf(
				t.getApproveStatus()).equals(TypicalTicketApproveStatusEnum.REJECT.getCode()) || String.valueOf(
						t.getApproveStatus()).equals(TypicalTicketApproveStatusEnum.RESULTS.getCode()))) {
			throw new TypicalTicketException(TypicalTicketExceptionType.TYPICALTICKET_CODE_STATUS);
		}
		return true;
	}
	/**
	 * 审批
	 * 
	 * @param id
	 */
	@Override
	public ResultObj approve(TypicalTicketEntity t, Map<String, Object> params) {
		if (validateStatus(t)) {
			// 修改状态
			TypicalTicketEntity tEntity = this.findById(t.getId());
			tEntity.setApproveStatus(Long.parseLong(params.get("status").toString()));
			typicalTicketDao.updateEntity(tEntity);
			// 设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(), params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(),
					params.get(ExamMarkEnum.RESULT.getCode()).toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);
		}
		return new ResultObj();
	}
	
	// 流程基本验证
		public boolean validateStatus(TypicalTicketEntity t) {
			TypicalTicketEntity tEntity = this.findById(t.getId());
			if (tEntity == null) {
				throw new TypicalTicketException(
						TypicalTicketExceptionType.TYPICALTICKET_CODE_NULL);
			}
			if (!t.getApproveStatus().toString().equals(tEntity.getApproveStatus().toString())) {
				throw new TypicalTicketException(
						TypicalTicketExceptionType.TYPICALTICKET_CODE_REPEAT);
			}
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
			page.addOrder(Sort.desc("createDate"));
			List<Condition> conditions = OrmUtil.changeMapToCondition(params);
			conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,Istypicaleum.ISSETYES.getCode()));
			List<TypicalTicketEntity> list=(List<TypicalTicketEntity>) typicalTicketDao.findByCondition(conditions, page);
			for (TypicalTicketEntity typicalTicketEntity : list) {
				Long workTicketId=typicalTicketEntity.getWorkticket_id();
				if(workTicketId.toString()!=null&&!(workTicketId.toString()).equals("")){
					OperationTicketEntity operationTicketEntity=operationTicketService.findById(workTicketId);
					typicalTicketEntity.setIstypical(operationTicketEntity.getIstypical().toString());
				}
			}
			return (List<O>) list;
		}
		
		/**
		 * @Description:   鉴定
		 * @author         zhangzq 
		 * @Date           2017年6月21日 下午5:01:36 
		 * @throws         Exception
		 */
		@Override
		public ResultObj saveInvalid(TypicalTicketEntity t) {
			TypicalTicketEntity  workTicketEntity=this.findById(Long.valueOf(t.getId()));
			workTicketEntity.setInvalidDate(t.getInvalidDate());
			workTicketEntity.setInvalidId(t.getInvalidId());
			workTicketEntity.setInvalidContent(t.getInvalidContent());
			workTicketEntity.setIdentify(t.getIdentify());
			typicalTicketDao.updateEntity(workTicketEntity);
			return new ResultObj();
		}
}