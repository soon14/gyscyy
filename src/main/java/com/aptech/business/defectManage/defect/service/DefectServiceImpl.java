package com.aptech.business.defectManage.defect.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.DefectStatusEnum;
import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.defectManage.defect.dao.DefectDao;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.defectManage.defect.exception.DefectException;
import com.aptech.business.defectManage.defect.exception.DefectExceptionType;
import com.aptech.business.defectManage.defectEquipment.domain.DefectEquipmentEntity;
import com.aptech.business.defectManage.defectEquipment.service.DefectEquipmentService;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.fourcode.service.FourCodeService;
import com.aptech.common.system.dictionary.domain.SysDictionaryEntity;
import com.aptech.common.system.dictionary.service.SysDictionaryService;
import com.aptech.common.system.dutiesdetail.domain.SysDutiesDetailEntity;
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
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

@Service("defectService")
@Transactional
public class DefectServiceImpl extends
		AbstractBaseEntityOperation<DefectEntity> implements DefectService {

	@Autowired
	private DefectDao defectDao;
	@Autowired
	private FourCodeService fourCodeService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Autowired
	private FourCodeService fourcode;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private DefectEquipmentService defectEquipmentService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysUnitService sysUnitService;
	@Override
	public IBaseEntityOperation<DefectEntity> getDao() {
		return defectDao;
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
			return findByCondition(conditions, page);
		}
	/**
	 * 添加实体
	 * 
	 * @param t
	 */
	@Override
	public ResultObj add(DefectEntity t) {
		Map<String, Object> params=new HashMap<String, Object> ();
		params.put("date", new Date());
		String code=fourCodeService.getBusinessCode("缺陷编码", params);
//		EquipLedgerEntity equipLedgerEntity = equipLedgerService.findById(Long.valueOf(t.getEquipId()));
		String equipIds[] = t.getEquipId().split(",");
		String ids = "";
		String names = "";
		List<Condition> conditions = new ArrayList<Condition>();
		for(int j = 0;j<equipIds.length;j++){
			conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipIds[j]));
			List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
			for(int i = 0;i<equipLedgeList.size();i++){
				EquipLedgerEntity equipLedgerEntity = equipLedgeList.get(i);
				ids+=equipLedgerEntity.getEquipManageType()+",";
//				SysDictionaryEntity sysDictionaryEntity = sysDictionaryService.findById(Long.valueOf(equipLedgerEntity.getEquipManageType()));
				names+=equipLedgerEntity.getEquipManageTypeName()+",";
			}
			conditions.clear();
		}
		t.setCode(code);
		t.setProcessStatus(DefectStatusEnum.PENDING.getCode());
		t.setCreateDate(new Date());
		t.setEquipManageType(ids);
		t.setEquipManageTypeName(names);
		defectDao.addEntity(t);
		if(!StringUtil.isEmpty(t.getUserList())){
			t.setProcessStatus(DefectStatusEnum.OVERHAUL.getCode());
			defectDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),t.getUserList());
			actTaskService.startProcess(
					ProcessMarkEnum.DEFECT_PROCESS_KEY.getName(),
					t.getId().toString(), vars);
		}
		String [] codes = t.getEquipId().split(",");
		String [] equipNames = t.getEquipName().split(",");
		String [] equipManageTypes = t.getEquipManageType().split(",");
		String [] equipManageTypeNames = t.getEquipManageTypeName().split(",");
		String [] equipTypes = t.getEquipType().split(",");
		String [] equipTypeNames = t.getEquipTypeName().split(",");
		for(int i = 0;i<codes.length;i++){
			DefectEquipmentEntity defectEquipmentEntity = new DefectEquipmentEntity();
			defectEquipmentEntity.setDefectId(t.getId().toString());
			defectEquipmentEntity.setEquipCode(codes[i]);
			defectEquipmentEntity.setStatus(DefectStatusEnum.PENDING.getCode());
				defectEquipmentEntity.setEquipName(equipNames[i]);
				defectEquipmentEntity.setUnitId(t.getUnitId().toString());
				defectEquipmentEntity.setUnitName(sysUnitService.findById(t.getUnitId()).getName());
				defectEquipmentEntity.setEquipManageType(equipManageTypes[i]);
				defectEquipmentEntity.setEquipManageTypeName(equipManageTypeNames[i]);
//				defectEquipmentEntity.setEquipType(equipTypes[i]);
//				defectEquipmentEntity.setEquipTypeName(equipTypeNames[i]);
				defectEquipmentService.addEntity(defectEquipmentEntity);
			
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DEFECT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		ResultObj resultObj = new ResultObj();
		resultObj.setData(t);
		return resultObj;
	}

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	@Override
	public ResultObj update(DefectEntity t) {
		ResultObj resultObj = new ResultObj();
		DefectEntity tEntity = this.findById(t.getId());
		t.setCreateDate(tEntity.getCreateDate());
		if(validate(tEntity)){
			String equipIds[] = t.getEquipId().split(",");
			String ids = "";
			String names = "";
			List<Condition> conditions = new ArrayList<Condition>();
			for(int j = 0;j<equipIds.length;j++){
				conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.EQ, equipIds[j]));
				List<EquipLedgerEntity> equipLedgeList = equipLedgerService.findByCondition(conditions, null);
				for(int i = 0;i<equipLedgeList.size();i++){
					EquipLedgerEntity equipLedgerEntity = equipLedgeList.get(i);
					ids+=equipLedgerEntity.getEquipManageType()+",";
//					SysDictionaryEntity sysDictionaryEntity = sysDictionaryService.findById(Long.valueOf(equipLedgerEntity.getEquipManageType()));
					names+=equipLedgerEntity.getEquipManageTypeName()+",";
				}
				conditions.clear();
			}
			t.setEquipManageType(ids);
			t.setEquipManageTypeName(names);
			t.setProcessStatus(DefectStatusEnum.PENDING.getCode());
			defectDao.updateEntity(t);
		}
		String [] codes = t.getEquipId().split(",");
		String [] equipNames = t.getEquipName().split(",");
		String [] equipManageTypes = t.getEquipManageType().split(",");
		String [] equipManageTypeNames = t.getEquipManageTypeName().split(",");
//		String [] equipTypes = t.getEquipType().split(",");
//		String [] equipTypeNames = t.getEquipTypeName().split(",");
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFECT_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ, t.getId()));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(conditions, null);
		if(defectEquipmentList!=null && defectEquipmentList.size()>0){
			for(DefectEquipmentEntity equipmentEntity:defectEquipmentList){
				defectEquipmentService.deleteEntity(equipmentEntity.getId());
			}
		}
		for(int i = 0;i<codes.length;i++){
			DefectEquipmentEntity defectEquipmentEntity = new DefectEquipmentEntity();
			defectEquipmentEntity.setDefectId(t.getId().toString());
			defectEquipmentEntity.setEquipCode(codes[i]);
			defectEquipmentEntity.setStatus(DefectStatusEnum.PENDING.getCode());
			defectEquipmentEntity.setEquipName(equipNames[i]);
			defectEquipmentEntity.setUnitId(t.getUnitId().toString());
			defectEquipmentEntity.setUnitName(sysUnitService.findById(t.getUnitId()).getName());
			defectEquipmentEntity.setEquipManageType(equipManageTypes[i]);
			defectEquipmentEntity.setEquipManageTypeName(equipManageTypeNames[i]);
			//defectEquipmentEntity.setEquipType(equipTypes[i]);
			//defectEquipmentEntity.setEquipTypeName(equipTypeNames[i]);
			defectEquipmentService.addEntity(defectEquipmentEntity);
		}
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DEFECT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		resultObj.setData(t);
		return resultObj;

	}

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	@Override
	public ResultObj delete(Serializable id) {
		ResultObj resultObj = new ResultObj();
		DefectEntity t = this.findById(id);
		if(validate(t)){
			defectDao.deleteEntity(id);
		}
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("C_DEFECT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id));
		List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(conditions, null);
		if(defectEquipmentList!=null&&defectEquipmentList.size()>0){
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentService.deleteEntity(defectEquipmentEntity);
			}
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DEFECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return resultObj;
	}
	@Override
	public void bulkDelete(Serializable [] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.DEFECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		String [] id = ids.toString().split(",");
		List<Condition> conditions = new ArrayList<Condition>();
		for(int i = 0;i<id.length;i++){
			conditions.add(new Condition("C_DEFECT_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ, id[i]));
			List<DefectEquipmentEntity> defectEquipmentList = defectEquipmentService.findByCondition(conditions, null);
			for(DefectEquipmentEntity defectEquipmentEntity : defectEquipmentList){
				defectEquipmentService.deleteEntity(defectEquipmentEntity);
			}
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
		DefectEntity t = this.findById(id);
		if(validate(t)){
			t.setProcessStatus(DefectStatusEnum.OVERHAUL.getCode());
			defectDao.updateEntity(t);
			// 准备启动流程
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(), params.get("userList"));
			actTaskService.startProcess(ProcessMarkEnum.DEFECT_PROCESS_KEY.getName(), id.toString(), vars);
		}
		return resultObj;
	}

	/**
	 * 审批：缺陷、主任挂起申请
	 * 
	 * @param id
	 */
	@Override
	public ResultObj approve(DefectEntity t,Map<String, Object> params) {
		if(validateStatus(t)){
			// 修改状态
			DefectEntity tEntity = this.findById(t.getId());
			tEntity.setProcessStatus(params.get("status").toString());
			defectDao.updateEntity(tEntity);
			//设置当前流程,当前任务节点的下一个节点的办理人
			actTaskService.setVarialble(t.getTaskId(),params);
			// 调用流程接口
			params.put(ExamMarkEnum.RESULT.getCode(), params.get(ExamMarkEnum.RESULT.getCode()).toString());
			actTaskService.complete(t.getTaskId(), t.getProcInstId(),
					params);
		}
		return  new ResultObj();
	}
	// 基本验证
	public boolean validate(DefectEntity t) {
//		if (t == null) {
//			throw new DefectException(DefectExceptionType.DEFECT_CODE_NULL);
//		}
//		if (!t.getProcessStatus().equals(DefectStatusEnum.PENDING.getCode())) {
//			throw new DefectException(DefectExceptionType.DEFECT_CODE_STATUS);
//		}
		return true;
	}
	// 流程基本验证
		public boolean validateStatus(DefectEntity t) {
			DefectEntity tEntity = this.findById(t.getId());
			if (tEntity == null) {
				throw new DefectException(
						DefectExceptionType.DEFECT_CODE_NULL);
			}
			if (!t.getProcessStatus().equals(tEntity.getProcessStatus())) {
				throw new DefectException(
						DefectExceptionType.DEFECT_CODE_REPEAT);
			}
			return true;
		}
		@Override
		public String findByEquipId(Long id) {
			DefectEntity defectEntity = this.findById(id);
			String userIds="[";
			List<Condition> conditions =new ArrayList<Condition>();
			conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.IN, defectEntity.getEquipId().split(",")));
			List<EquipLedgerEntity> list=equipLedgerService.findByCondition(conditions, null);
			if(!list.isEmpty()){
				for (EquipLedgerEntity t: list) {
					if(t.getId()!=null){
						userIds +=t.getId();
						userIds+=",";
					}
				}
			}
			if(userIds.lastIndexOf(",")+1==userIds.length()){
				userIds = userIds.substring(0,userIds.length()-1);
			}
			userIds+="]";
			return userIds.toString();
		}
}