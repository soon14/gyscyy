package com.aptech.business.overhaul.overhaulArrange.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.equip.equipAbnormal.domain.EquipAbnormalEnum;
import com.aptech.business.overhaul.overhaulArrange.dao.OverhaulArrangeDao;
import com.aptech.business.overhaul.overhaulArrange.dao.OverhaulArrangeDaoImpl;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeProcessEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.overhaul.overhaulArrange.exception.OverhaulArrangeException;
import com.aptech.business.overhaul.overhaulArrange.exception.OverhaulArrangeExceptionType;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.overhaulLog.service.OverhaulLogService;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulLogDetail.service.OverhaulLogDetailService;
import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSafe.service.OverhaulSafeService;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 工作安排应用管理服务实现类
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
@Service("overhaulArrangeService")
@Transactional
public class OverhaulArrangeServiceImpl extends AbstractBaseEntityOperation<OverhaulArrangeEntity> implements OverhaulArrangeService {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OverhaulArrangeDao overhaulArrangeDao;
	@Autowired
	private  OverhaulLogService overhaulLogService;
	@Autowired
	private OverhaulSafeService overhaulSafeService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OverhaulLogDetailService overhaulLogDetailService;
	@Override
	public IBaseEntityOperation<OverhaulArrangeEntity> getDao() {
		return overhaulArrangeDao;
	}
	@Override
	public void addEntity(OverhaulArrangeEntity t) {
		new OverhaulArrangeDaoImpl();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setCreateDate(new Date());
		t.setProcessStatus(OverhaulArrangeEnum.UNFINISH.getCode());
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(t.getOverhaulLogId());
		super.addEntity(t);
		//更新检修日志负责人、完成状态
		if(overhaulLogEntity!=null){
			overhaulLogService.executeSomeThing(overhaulLogEntity);
			overhaulLogEntity.setDutyUserId(new StringBuilder(overhaulLogEntity.getDutyUserId()).append(",").append(t.getDutyPersonId()).toString());
			SysUserEntity sysUserEntity =  sysUserService.findById(t.getDutyPersonId());
			overhaulLogEntity.setDutyUserName(new StringBuilder(overhaulLogEntity.getDutyUserName()).append(",").append(sysUserEntity.getName()).toString());
			overhaulLogEntity.setDutyUserLoginName(new StringBuilder(overhaulLogEntity.getDutyUserLoginName()).append(",").append(sysUserEntity.getLoginName()).toString());
			
//			overhaulLogEntity.setAskPersonId(new StringBuilder(overhaulLogEntity.getAskPersonId()).append(",").append(t.getAskPersonId()).toString());
//			
//			SysUserEntity sysUserEntity2 =  sysUserService.findById(t.getAskPersonId());
//		    overhaulLogEntity.setAskPersonName(new StringBuilder(overhaulLogEntity.getAskPersonName()).append(",").append(sysUserEntity2.getName()).toString());
			
			
			overhaulLogService.updEntity(overhaulLogEntity);
		}
	}
	
	@Override
	public ResultObj updEntity(OverhaulArrangeEntity overhaulArrangeEntity,Long id) {
		ResultObj resultObj = new ResultObj();
		OverhaulArrangeEntity entity = overhaulArrangeDao.findById(id);
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		//用户信息
		overhaulArrangeEntity.setId(id);
		overhaulArrangeEntity.setCreateDate(entity.getCreateDate());
		overhaulArrangeEntity.setCreateUserId(entity.getUpdateUserId());
		overhaulArrangeEntity.setUpdateDate(new Date());
		overhaulArrangeEntity.setUpdateUserId(userEntity.getId().toString());
		overhaulArrangeEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		overhaulArrangeEntity.setProcessStatus(OverhaulArrangeEnum.UNFINISH.getCode());
		super.updateEntity(overhaulArrangeEntity);
		if(overhaulArrangeEntity!=null){
			resultObj.setData(overhaulArrangeEntity);
		}
		//更新检修日志负责人、完成状态
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(overhaulArrangeEntity.getOverhaulLogId());
		if(overhaulLogEntity!=null){
			overhaulLogService.executeSomeThing(overhaulLogEntity);
			overhaulLogService.updEntity(overhaulLogEntity);
		}
		return resultObj;
	}
	
	@Override
	public ResultObj updEntityHis(OverhaulArrangeEntity overhaulArrangeEntity,Long id) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		OverhaulArrangeEntity entity = overhaulArrangeDao.findById(id);
		overhaulArrangeDao.setFlag(false);
		OverhaulArrangeEntity hisentity = overhaulArrangeDao.findById(id);
		if(hisentity==null){
			//插入工作安排历史表
			entity.setUpdateDate(new Date());
			entity.setUpdateUserId(userEntity.getId().toString());
			super.addEntity(entity);
		}
		overhaulArrangeDao.setFlag(true);
		//更新工作安排
		overhaulArrangeEntity.setId(id);
		overhaulArrangeEntity.setCreateDate(entity.getCreateDate());
		overhaulArrangeEntity.setCreateUserId(entity.getUpdateUserId());
		overhaulArrangeEntity.setUpdateDate(new Date());
		overhaulArrangeEntity.setUpdateUserId(userEntity.getId().toString());
		overhaulArrangeEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		overhaulArrangeEntity.setProcessStatus(OverhaulArrangeEnum.UNFINISH.getCode());
		super.updateEntity(overhaulArrangeEntity);
		if(overhaulArrangeEntity!=null){
			resultObj.setData(overhaulArrangeEntity);
		}
		//更新检修日志负责人、完成状态
		OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(overhaulArrangeEntity.getOverhaulLogId());
		if(overhaulLogEntity!=null){
			overhaulLogService.executeSomeThing(overhaulLogEntity);
			overhaulLogService.updEntity(overhaulLogEntity);
		}
		if(overhaulArrangeEntity!=null){
			resultObj.setData(overhaulArrangeEntity);
		}
		return resultObj;
	}
	
	
	
	
	
	/**
	 * @Description:   基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:28 
	 * @throws         Exception
	 */
	
	public boolean validate(OverhaulArrangeEntity overhaulArrangeEntity) {
		if (overhaulArrangeEntity == null) {
			throw new OverhaulArrangeException(OverhaulArrangeExceptionType.DEFECT_CODE_NULL);
		}                                    
		if (!(overhaulArrangeEntity.getProcessStatus().equals(EquipAbnormalEnum.WATISUBMIT.getCode()) || overhaulArrangeEntity.getProcessStatus().equals(EquipAbnormalEnum.REJECT.getCode()))) {
			throw new OverhaulArrangeException(OverhaulArrangeExceptionType.DEFECT_CODE_STATUS);
		}
		return true;
	}
	
	/**
	 * @Description:   流程基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:01 
	 * @throws         Exception
	 */
	public boolean validateStatus(OverhaulArrangeEntity overhaulArrangeEntity) {
		OverhaulArrangeEntity entity = this.findById(overhaulArrangeEntity.getId());
		if (entity == null) {
			throw new OverhaulArrangeException(
					OverhaulArrangeExceptionType.DEFECT_CODE_NULL);
		}
		if (!overhaulArrangeEntity.getProcessStatus().equals(entity.getProcessStatus())) {
			throw new OverhaulArrangeException(
					OverhaulArrangeExceptionType.DEFECT_CODE_REPEAT);
		}
		return true;
	}
	/**
	 * @Description:   工作安排提交
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:01 
	 * @throws         Exception
	 */
	@Override
	public ResultObj aloneSubmit(Long overhaulLogId, Long overhaulArrangeId,String taskId,String procInstId,Map<String, Object> params) {
		ResultObj resultObj = new ResultObj();
		OverhaulArrangeEntity overhaulArrangeEntity =  overhaulArrangeDao.findById(overhaulArrangeId);
		if(overhaulArrangeEntity!=null){
			overhaulArrangeEntity.setProcessStatus(OverhaulArrangeEnum.FINISH.getCode());
			overhaulArrangeDao.updateEntity(overhaulArrangeEntity);
		}
		List<Condition> conditions=new ArrayList<Condition>();
		conditions.add(new Condition("O.C_OVERHAUL_LOG_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,overhaulLogId));
		conditions.add(new Condition("O.C_PROCESS_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ,OverhaulArrangeEnum.UNFINISH.getCode()));
		List<OverhaulArrangeEntity> overhaulArrangeEntities = overhaulArrangeDao.findByCondition(conditions, null);
		if(overhaulArrangeEntities.isEmpty()){
			OverhaulLogEntity overhaulLogEntity = overhaulLogService.findById(overhaulLogId);
			overhaulLogEntity.setProcessStatus(OverhaulArrangeProcessEnum.RROCESSPEND.getCode());
			overhaulLogService.updEntity(overhaulLogEntity);
		}
		conditions.add(new Condition("O.C_DUTY_PERSON_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getDutyPersonId()));
		overhaulArrangeEntities = overhaulArrangeDao.findByCondition(conditions, null);
		if(overhaulArrangeEntities.isEmpty()){
			actTaskService.complete(taskId, procInstId,params);
		}
		return resultObj;
	}
	
	@Override
	public ResultObj batchDelete(List<Long> ids) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		for(Long id:ids){
			OverhaulArrangeEntity overhaulArrangeEntity = overhaulArrangeDao.findById(id);
			//删除工作安排
			if(overhaulArrangeEntity!=null){
				overhaulArrangeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
				overhaulArrangeDao.updateEntity(overhaulArrangeEntity);
				List<Condition> conditions = new ArrayList<Condition>();
				
				//删除检修日志明细
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulLogDetailEntity> logDetailEntities =  overhaulLogDetailService.findByCondition(conditions, null);
				for(OverhaulLogDetailEntity overhaulLogDetailEntity:logDetailEntities){
					overhaulLogDetailEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulLogDetailEntity.setUpdateDate(new Date());
					overhaulLogDetailEntity.setUpdateUserId(userEntity.getId());
					overhaulLogDetailService.updateEntity(overhaulLogDetailEntity);
				}
				//删除工作任务
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskService.findByCondition(conditions, null);
				for(OverhaulWorkTaskEntity workTaskEntity:workTaskEntities){
					workTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					workTaskEntity.setUpdateDate(new Date());
					workTaskEntity.setUpdateUserId(userEntity.getId());
					overhaulWorkTaskService.updateEntity(workTaskEntity);
				}
				//删除安全交底
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulSafeEntity> overhaulSafeEntities =  overhaulSafeService.findByCondition(conditions, null);
				for(OverhaulSafeEntity overhaulSafeEntity:overhaulSafeEntities){
					overhaulSafeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulSafeEntity.setUpdateDate(new Date());
					overhaulSafeEntity.setUpdateUserId(userEntity.getId());
					overhaulSafeService.update(overhaulSafeEntity);
				}
			}
		}
		return resultObj;
	}
	
	@Override
	public ResultObj deleteOnlyOne(HttpServletRequest request, Long id) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		OverhaulArrangeEntity overhaulArrangeEntity = overhaulArrangeDao.findById(id);
		//删除工作安排
		if(overhaulArrangeEntity!=null){
			overhaulArrangeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
			overhaulArrangeDao.updateEntity(overhaulArrangeEntity);
			List<Condition> conditions = new ArrayList<Condition>();
			
			//删除检修日志明细
			conditions.clear();
			conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
			List<OverhaulLogDetailEntity> logDetailEntities =  overhaulLogDetailService.findByCondition(conditions, null);
			for(OverhaulLogDetailEntity overhaulLogDetailEntity:logDetailEntities){
				overhaulLogDetailEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
				overhaulLogDetailEntity.setUpdateDate(new Date());
				overhaulLogDetailEntity.setUpdateUserId(userEntity.getId());
				overhaulLogDetailService.updateEntity(overhaulLogDetailEntity);
			}
			//删除工作任务
			conditions.clear();
			conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
			List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskService.findByCondition(conditions, null);
			for(OverhaulWorkTaskEntity workTaskEntity:workTaskEntities){
				workTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
				workTaskEntity.setUpdateDate(new Date());
				workTaskEntity.setUpdateUserId(userEntity.getId());
				overhaulWorkTaskService.updateEntity(workTaskEntity);
			}
			//删除安全交底
			conditions.clear();
			conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
			List<OverhaulSafeEntity> overhaulSafeEntities =  overhaulSafeService.findByCondition(conditions, null);
			for(OverhaulSafeEntity overhaulSafeEntity:overhaulSafeEntities){
				overhaulSafeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
				overhaulSafeEntity.setUpdateDate(new Date());
				overhaulSafeEntity.setUpdateUserId(userEntity.getId());
				overhaulSafeService.update(overhaulSafeEntity);
			}
		}
		return resultObj;
	}



}