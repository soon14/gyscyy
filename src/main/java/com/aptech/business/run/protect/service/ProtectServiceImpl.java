package com.aptech.business.run.protect.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.ProcessMarkEnum;
import com.aptech.business.component.dictionary.ProtectStatusEnum;
import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.business.equip.equipLedger.domain.EquipLedgerEntity;
import com.aptech.business.equip.equipLedger.service.EquipLedgerService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.run.protect.dao.ProtectDao;
import com.aptech.business.run.protect.domain.ProtectEntity;
import com.aptech.business.run.protect.exception.ProtectException;
import com.aptech.business.run.protect.exception.ProtectExceptionType;
import com.aptech.common.act.service.ActProcessService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.workflow.modelEditor.domain.BranchMarkEnum;
import com.aptech.common.workflow.modelEditor.domain.CandidateMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamMarkEnum;
import com.aptech.common.workflow.todoTask.domain.ExamResultEnum;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 保护投退应用管理服务实现类
 * 
 * @author
 * @created 2017-06-02 14:38:25
 * @lastModified
 * @history
 * 
 */
@Service("protectService")
@Transactional
public class ProtectServiceImpl extends
		AbstractBaseEntityOperation<ProtectEntity> implements ProtectService {

	@Autowired
	private ProtectDao protectDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private EquipLedgerService equipLedgerService;
	@Override
	public IBaseEntityOperation<ProtectEntity> getDao() {
		return protectDao;
	}

	@Override
	public void tocheck(Long id, Map<String, Object> params) {
		ProtectEntity protectEntity = this.findById(id);
		// 准备启动流程
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(CandidateMarkEnum.NEXT_HANDLERS.getName(),
				params.get("userList"));
		if (protectEntity.getApplyType() != null
				&& String.valueOf(protectEntity.getApplyType()).equals("0")) {
			vars.put(BranchMarkEnum.BRANCH_KEY.getName(),
					ExamResultEnum.BACK.getId());
			protectEntity.setCheckState(ProtectStatusEnum.SELEXECUTE.getCode());
		} else {
			vars.put(BranchMarkEnum.BRANCH_KEY.getName(),
					ExamResultEnum.AGREE.getId());
			protectEntity.setCheckState(ProtectStatusEnum.ZZCHECK.getCode());
		}
		protectDao.updateEntity(protectEntity);
		actTaskService.startProcess(
				ProcessMarkEnum.PROTECT_PROCESS_KEY.getName(), id.toString(),
				vars);
	}

	@Override
	public ResultObj check(ProtectEntity t, Map<String, Object> params) {
		// 修改状态
		ProtectEntity tEntity = this.findById(t.getId());
		tEntity.setCheckState(params.get("status").toString());
		protectDao.updateEntity(tEntity);
		// 设置当前流程,当前任务节点的下一个节点的办理人
		actTaskService.setVarialble(t.getTaskId(), params);
		params.put(ExamMarkEnum.RESULT.getCode(),
				params.get(ExamMarkEnum.RESULT.getCode()).toString());
		actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);
		return new ResultObj();
	}

	@Override
	public ResultObj checkAndUpdate(ProtectEntity t, Map<String, Object> params) {
		// 修改状态
		ProtectEntity tEntity = this.findById(t.getId());
		tEntity.setCheckState(params.get("status").toString());
		if (!"".equals(t.getWardPersonId()) && t.getWardPersonId() != null) {
			tEntity.setWardPersonId(t.getWardPersonId());
		}
		if (!"".equals(t.getUserList()) && t.getUserList() != null) {
			tEntity.setExecutePersonId(Integer.parseInt(t.getUserList()));
		}
		if (!"".equals(t.getExecuteEndtime()) && t.getExecuteEndtime() != null) {
			tEntity.setExecuteEndtime(t.getExecuteEndtime());
			SysUserEntity userEntity = RequestContext.get().getUser();
			tEntity.setExecutePersonId(userEntity.getId().intValue());
		}
		if (!"".equals(t.getFinishSituation())
				&& t.getFinishSituation() != null) {
			tEntity.setFinishSituation(t.getFinishSituation());
		}
		if (!StringUtil.isEmpty(t.getExecuteRemarks())) {
			tEntity.setExecuteRemarks(t.getExecuteRemarks());
		}
		if (!StringUtil.isEmpty(t.getExecuteFileId())) {
			tEntity.setExecuteFileId(t.getExecuteFileId());
		}
		protectDao.updateEntity(tEntity);
		// 设置当前流程,当前任务节点的下一个节点的办理人
		actTaskService.setVarialble(t.getTaskId(), params);
		params.put(ExamMarkEnum.RESULT.getCode(),
				params.get(ExamMarkEnum.RESULT.getCode()).toString());
		actTaskService.complete(t.getTaskId(), t.getProcInstId(), params);

		return new ResultObj();
	}

	@Override
	public ResultObj add(ProtectEntity t) throws Exception {
		ResultObj resultObj = new ResultObj();
		if (validate(t)) {
			SysUnitEntity sysUnitEntity = sysUnitService.findById((long) t
					.getUnitId());
			String applyTimeTemp = (t.getApplyTime().toString().replaceAll("-",
					"")).replaceAll(" ", "");
			
			DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm");
			String time =df.format(t.getApplyTime()).replaceAll("-","").replaceAll(" ", "");
			
			t.setCode(sysUnitEntity.getCode()+ time.replaceAll(":", ""));
//			t.setCode(sysUnitEntity.getCode()
//					+ applyTimeTemp.replaceAll(":", ""));processUserUnitRels
			t.setCheckState(ProtectStatusEnum.TOSUBMIT.getCode());
			t.setExecuteFileId("[]");
			t.setFlag("1");
			t.setEquipCode(t.getEquipNumber());
			this.addEntity(t);
			resultObj.setData(t);
			SysUserEntity userEntity = RequestContext.get().getUser();
			operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PROTECT.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		}
		return resultObj;
	}

	@Override
	public ResultObj update(ProtectEntity t) throws Exception {
		ResultObj resultObj = new ResultObj();
		t.setExecuteFileId("[]");
		t.setFlag("1");
		t.setEquipCode(t.getEquipNumber());
		protectDao.updateEntity(t);
		resultObj.setData(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PROTECT.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}

	// 基本验证
	public boolean validate(ProtectEntity t) throws Exception {
		List<Condition> conditions = new ArrayList<Condition>();

		conditions.add(new Condition("a.C_DEVICE_ID", FieldTypeEnum.INT,
				MatchTypeEnum.EQ, t.getDeviceId()));
		conditions.add(new Condition("a.C_FLAG", FieldTypeEnum.INT,
				MatchTypeEnum.EQ, "1"));
		conditions.add(new Condition("a.C_FLAG", FieldTypeEnum.INT,
				MatchTypeEnum.NE, ProtectStatusEnum.CANCEL.getCode()));
		List<ProtectEntity> protectEntityList = this.findByCondition(
				"findByCondition", conditions, null);
		if (protectEntityList.size() != 0) {
			for (int i = 0; i < protectEntityList.size(); i++) {
				ProtectEntity protectEntity = protectEntityList.get(i);
				if (t.getProtectWay().equals(protectEntity.getProtectWay())) {
					throw new ProtectException(
							ProtectExceptionType.PROTECT_CODE_WAY);

				}
				DateFormatUtil df = DateFormatUtil
						.getInstance("yyyy-MM-dd HH:mm");
				String applyTime = df.format(protectEntity.getApplyTime());
				if (t.getApplyTime().equals(applyTime)) {
					throw new ProtectException(
							ProtectExceptionType.PROTECT_CODE_TIME);

				}
				if (!"10".equals(protectEntity.getCheckState())) {
					throw new ProtectException(
							ProtectExceptionType.PROTECT_CODE_CHECK);

				}
			}
		}
		return true;
	}
	/**
	 * @Description: 删除
	 * @author changl
	 * @Date 2017年9月11日 上午10:57:51
	 * @throws Exception
	 */
	@Override
	public void deleteEntity(Serializable id) {
		ProtectEntity t = this.findById(id);
		t.setFlag("0");
		protectDao.updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PROTECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		// 调用流程结束接口
				if(!(t.getCheckState().equals("1")||t.getCheckState().equals("10")
						||t.getCheckState().equals("15"))){
					actProcessService.endProcess(t.getId().toString(), ProcessMarkEnum.PROTECT_PROCESS_KEY.getName());
				}
	}
	/**
	 * @Description: 批量删除
	 * @author changl
	 * @Date 2017年9月11日 上午10:57:51
	 * @throws Exception
	 */
	@Override
	public ResultObj delete(Serializable id,String role) throws Exception {
		ResultObj resultObj = new ResultObj();
		ProtectEntity t = this.findById(id);
		if ("admin".equals(role)) {
			// this.deleteEntity(id);
			t.setFlag("0");
			protectDao.updateEntity(t);
		} else {
			if (validateisDelete(t)) {
				// this.deleteEntity(id);
				t.setFlag("0");
				protectDao.updateEntity(t);
			}
		}
		// 调用流程结束接口
		if(!(t.getCheckState().equals("1")||t.getCheckState().equals("10")
				||t.getCheckState().equals("15"))){
			actProcessService.endProcess(t.getId().toString(), ProcessMarkEnum.PROTECT_PROCESS_KEY.getName());
		}
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PROTECT.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
			//流程未启动
		return resultObj;
	}

	/**
	 * @Description: 批量删除验证
	 * @author changl
	 * @Date 2017年9月11日 上午10:57:51
	 * @throws Exception
	 */
	private boolean validateisDelete(ProtectEntity t) throws Exception {
		if (!(t.getCheckState().equals(ProtectStatusEnum.TOSUBMIT.getCode()) || t
				.getCheckState().equals(ProtectStatusEnum.CANCEL.getCode()))) {
			throw new ProtectException(ProtectExceptionType.PROTECT_CODE_DEL);
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
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		conditions.add(new Condition("C_FLAG", FieldTypeEnum.STRING,
				MatchTypeEnum.EQ, "1"));
		return findByCondition(conditions, page);
	}

	@Override
	public String findByEquipId(String id) {
		ProtectEntity protectEntity = this.findById(Long.valueOf(id));
		String userIds="[";
		List<Condition> conditions =new ArrayList<Condition>();
		conditions.add(new Condition("L.C_CODE", FieldTypeEnum.STRING, MatchTypeEnum.IN, protectEntity.getEquipCode().split(",")));
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