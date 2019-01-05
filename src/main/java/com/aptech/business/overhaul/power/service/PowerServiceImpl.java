package com.aptech.business.overhaul.power.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.PowerStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.power.dao.PowerDao;
import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.business.overhaul.power.exception.PowerException;
import com.aptech.business.overhaul.power.exception.PowerExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
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
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 停送电管理应用管理服务实现类
 *
 * @author 
 * @created 2017-07-31 14:17:20
 * @lastModified 
 * @history
 *
 */
@Service("powerService")
@Transactional
public class PowerServiceImpl extends AbstractBaseEntityOperation<PowerEntity> implements PowerService {
	
	@Autowired
	private PowerDao powerDao;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	
	@Override
	public IBaseEntityOperation<PowerEntity> getDao() {
		return powerDao;
	}
	
	/**
	 * @Description: 查询
	 * @author 
	 * @Date 2017年7月31日 下午2:25
	 * @throws Exception
	 */
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if (page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setOrders(OrmUtil.changeMapToOrders(params));
//		page.addOrder(Sort.desc("requestDate"));
		page.addOrder(Sort.desc("C_ID"));
		
		
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		
//		List<Condition> conditioncode = new ArrayList<Condition>();
//		conditioncode.add(new Condition("userId",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
//		List<UserUnitRelEntity> unitList = userUnitRelService.findByCondition(conditioncode, null);
//		if(unitList!=null && unitList.size()>0){
//			Long[] unitArray = new Long[unitList.size()];
//			for(int i=0;i<unitList.size();i++){
//				unitArray[i]=unitList.get(i).getUnitId();
//			}
//			conditions.add(new Condition("unitId", FieldTypeEnum.INT, MatchTypeEnum.IN, unitArray));
//		}else{
//			conditions.add(new Condition("unitId", FieldTypeEnum.STRING, MatchTypeEnum.EQ, userEntity.getUnitId()));
//		}
		List<O> list = findByCondition(conditions, page);
		return list;
	}

	@Override
	public ResultObj add(PowerEntity t) {
		t.setRequestNumber(IdUtil.creatUUID());
		t.setUnitName(sysUnitService.findById((long)t.getUnitId()).getName());
		t.setRequestUserName(sysUserService.findById((long)t.getRequestUserId()).getName());
		t.setAttchmentResultIds("[]");
		t.setStatus("0");
//		if(PowerStatusEnum.OVERHAUL.getCode().equals(t.getStatus())){
//			//提交审核流程
//			beforeSubmit(t);
//			this.addEntity(t);
//			Map<String, Object> vars = new HashMap<String, Object>();
//			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
//			
//			actTaskService.startProcess(ProcessMarkEnum.POWER_PROCESS_KEY.getName(), t.getId().toString(),vars);
//		}else{
			this.addEntity(t);
//		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.POWER.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(t);
		return resultObj;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		PowerEntity powerEntity  =this.findById(id);
//		isdeleteValidate(powerEntity.getId());
		super.deleteEntity(id);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.POWER.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	private void isdeleteValidate(Long id) {
		PowerEntity powerEntity  =this.findById(id);
		if(!powerEntity.getStatus().equals(PowerStatusEnum.CANCEL.getCode())
				&& !powerEntity.getStatus().equals(PowerStatusEnum.PENDING.getCode())){
			throw new PowerException(PowerExceptionType.POWER_DELETE_STATUS);
		}
	}
	private void isupdateValidate(Long id) {
		PowerEntity powerEntity  =this.findById(id);
		if(!powerEntity.getStatus().equals(PowerStatusEnum.REJECT.getCode())
				&& !powerEntity.getStatus().equals(PowerStatusEnum.PENDING.getCode())){
			throw new PowerException(PowerExceptionType.POWER_UPDATE_STATUS);
		}
	}
	@Override
	public ResultObj update(PowerEntity t) {
		ResultObj resultObj = new ResultObj();
		t.setUnitName(sysUnitService.findById((long)t.getUnitId()).getName());
		t.setRequestUserName(sysUserService.findById((long)t.getRequestUserId()).getName());
		t.setAttchmentResultIds("[]");
		
//		isupdateValidate(t.getId());
//		if(PowerStatusEnum.OVERHAUL.getCode().equals(t.getStatus())){
//			//提交审核流程
//			beforeSubmit(t);
//			Map<String, Object> vars = new HashMap<String, Object>();
//			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), t.getUserList());
//			actTaskService.startProcess(ProcessMarkEnum.POWER_PROCESS_KEY.getName(), t.getId().toString(), vars);
//		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.POWER.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		this.updateEntity(t);
		resultObj.setData(t);
		return resultObj;
	}

	@Override
	public ResultObj submit(Serializable id, Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		PowerEntity entity  = this.findById(id);
		beforeSubmit(entity);
		entity.setStatus(PowerStatusEnum.OVERHAUL.getCode());
		this.updateEntity(entity);
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
		// 准备启动流程
		actTaskService.startProcess(ProcessMarkEnum.POWER_PROCESS_KEY.getName(), id.toString(), vars);
	
		return resultObj;
	}
	
	@Override
	public ResultObj approve(PowerEntity t, Map<String, Object> params) {
		// 修改状态
		PowerEntity tEntity = this.findById(t.getId());
		if(params.get("status")!=null){
			tEntity.setStatus(params.get("status").toString());
		}
		if(t.getExcuteUserId()!=null ){
			tEntity.setExcuteUserId(t.getExcuteUserId());
			SysUserEntity excuteUser = sysUserService.findById((long)t.getExcuteUserId());
			tEntity.setExcuteUserName(excuteUser.getName());
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),excuteUser.getLoginName());
		}
		if(t.getCostodyUserId()!=null){
			tEntity.setCostodyUserId(t.getCostodyUserId());
			SysUserEntity costodyUser = sysUserService.findById((long)t.getCostodyUserId());
			tEntity.setCostodyUserName(costodyUser.getName());
		}
		if(t.getEndDate()!=null){
			tEntity.setEndDate(t.getEndDate());
		}
		if(t.getMeasur()!=null){
			tEntity.setMeasur(t.getMeasur());
		}
		if(t.getAttchmentResultIds()!=null){
			tEntity.setAttchmentResultIds(t.getAttchmentResultIds());
		}
		if((params.get("status")!=null)&&params.get("status").equals(PowerStatusEnum.REJECT.getCode())){
			SysUserEntity requestUser = sysUserService.findById((long)tEntity.getRequestUserId());
			params.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),requestUser.getLoginName());
		}
		this.updateEntity(tEntity);
		//设置当前流程,当前任务节点的下一个节点的办理人
		actTaskService.setVarialble(t.getTaskId(),params);
		// 调用流程接口
		params.put(ExamMarkEnum.COMMENT.getCode(), t.getAuditMsg()==null?"":t.getAuditMsg());
		actTaskService.complete(t.getTaskId(), t.getProcInstId(),params);
		return  new ResultObj();
	}

	@Override
	public ResultObj beforeSubmit(PowerEntity t) {
		Page<PowerEntity> page = new Page<PowerEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("equipNumber", FieldTypeEnum.STRING, MatchTypeEnum.EQ,t.getEquipNumber()));
		if(t.getId()!=null){
			conditions.add(new Condition("id", FieldTypeEnum.LONG, MatchTypeEnum.NE,t.getId()));
		}
		page.addOrder(Sort.desc("requestDate"));
		List<PowerEntity> list = findByCondition(conditions, page);
		if(!list.isEmpty()){
			//存在未完成的记录
			boolean flag = false;
			for(PowerEntity entity :list){
				String status = entity.getStatus();
				if(status.equals(PowerStatusEnum.MONITOR.getCode())|| status.equals(PowerStatusEnum.OVERHAUL.getCode())||status.equals(PowerStatusEnum.UNEXECUTED) ){
					throw new PowerException(PowerExceptionType.POWER_CODE_STATUS.getErrorMsg(),PowerExceptionType.POWER_CODE_STATUS.getErrorCode());
				}
				//最新一条记录与新增记录的停送电方式判断
				if(entity.equals(PowerStatusEnum.EXECUTED.getCode()) && !flag){
					flag =true;
					String powerStatus = list.get(0).getPowerStatus();
					if(powerStatus.equals(t.getPowerStatus())){
						//与上一条记录 停送电方式相同
						throw new PowerException(PowerExceptionType.POWER_CODE_TYPE.getErrorMsg(),PowerExceptionType.POWER_CODE_TYPE.getErrorCode());
					}
				}
				
			}

		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.POWER.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}