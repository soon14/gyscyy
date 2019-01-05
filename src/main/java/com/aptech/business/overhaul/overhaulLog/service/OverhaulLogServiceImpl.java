package com.aptech.business.overhaul.overhaulLog.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.PowerStatusEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulArrange.dao.OverhaulArrangeDao;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeProcessEnum;
import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulWorkTaskStatusEnum;
import com.aptech.business.overhaul.overhaulArrange.exception.OverhaulArrangeException;
import com.aptech.business.overhaul.overhaulArrange.exception.OverhaulArrangeExceptionType;
import com.aptech.business.overhaul.overhaulArrange.service.OverhaulArrangeService;
import com.aptech.business.overhaul.overhaulLog.dao.OverhaulLogDao;
import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.business.overhaul.overhaulLog.exception.OverhaulLogException;
import com.aptech.business.overhaul.overhaulLog.exception.OverhaulLogExceptionType;
import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.business.overhaul.overhaulLogDetail.service.OverhaulLogDetailService;
import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSafe.service.OverhaulSafeService;
import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.business.overhaul.overhaulSpareconsume.service.OverhaulSpareconsumeService;
import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.business.overhaul.overhaulWorkTask.service.OverhaulWorkTaskService;
import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.business.overhaul.power.exception.PowerException;
import com.aptech.business.overhaul.power.exception.PowerExceptionType;
import com.aptech.common.act.service.ActTaskService;
import com.aptech.common.system.role.service.SysRoleService;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.IdUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 检修日志应用管理服务实现类
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
@Service("overhaulLogService")
@Transactional
public class OverhaulLogServiceImpl extends AbstractBaseEntityOperation<OverhaulLogEntity> implements OverhaulLogService {
	
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private OverhaulLogDao overhaulLogDao;
	@Autowired
	private OverhaulArrangeDao overhaulArrangeDao;
	@Autowired
	private OverhaulSafeService overhaulSafeService;
	@Autowired
	private OverhaulArrangeService overhaulArrangeService;
	@Autowired
	private OverhaulWorkTaskService overhaulWorkTaskService;
	@Autowired
	private OverhaulLogDetailService overhaulLogDetailService;
	@Autowired
	private OverhaulSpareconsumeService overhaulSpareconsumeService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<OverhaulLogEntity> getDao() {
		return overhaulLogDao;
	}
	
	@Override
	public void addEntity(OverhaulLogEntity t) {
		String overhaulNumber=IdUtil.creatUUID();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		String overhaulLogId = t.getOverhaulLogId();
		t.setOverhaulNumber(overhaulNumber);
		t.setCreateDate(t.getLogDate());
		t.setCreateUserId(userEntity.getId());
		t.setSubmitUserId(t.getSubmitUserId());
		t.setUnitName(sysUnitService.findById((long)t.getUnitId()).getName());
		t.setSubmitUserName(t.getSubmitUserName());
		t.setProcessStatus(OverhaulArrangeProcessEnum.WATISUBMIT.getCode());
		t.setStatus(String.valueOf(OverhaulWorkTaskStatusEnum.NORMAL.getCode()));
		t.setId(Long.valueOf(overhaulLogId));
		if (t.getAskPersonId()!=null) {
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(t.getAskPersonId()));
			t.setAskPersonName(sysUserEntity.getName());
		}
		
		//处理负责人、完成状态
		executeSomeThing(t);
		addValidate(t);//判断是否添加了检修日志
		super.addEntity(t);
		overhaulLogDao.setFlag(false);
		if(overhaulLogDao.findById(t.getId())==null){
			overhaulLogDao.addEntity(t);
		}
		overhaulLogDao.setFlag(true);
		//更新工作安排表
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("O.C_OVERHAUL_LOG_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulLogId));
		List<OverhaulArrangeEntity> overhaulArrangeEntities= overhaulArrangeService.findByCondition(conditions, null);
		for(OverhaulArrangeEntity overhaulArrangeEntity :overhaulArrangeEntities){
			overhaulArrangeEntity.setOverhaulLogId(t.getId());
			overhaulArrangeService.updateEntity(overhaulArrangeEntity);
			overhaulArrangeDao.setFlag(false);
			if(overhaulArrangeDao.findById(overhaulArrangeEntity.getId())==null){
				overhaulArrangeDao.addEntity(overhaulArrangeEntity);
			}
			overhaulArrangeDao.setFlag(true);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULLOG.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		OverhaulLogEntity overhaulLogEntity  =this.findById(id);
		overhaulLogEntity.setStatus(String.valueOf(DataStatusEnum.DELETE.ordinal()));
		super.updateEntity(overhaulLogEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULLOG.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	
	/**
	 * @Description:   基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:28 
	 * @throws         Exception
	 */
	
	public boolean validate(OverhaulLogEntity overhaulLogEntity) {
		if (overhaulLogEntity == null) {
			throw new OverhaulArrangeException(OverhaulArrangeExceptionType.DEFECT_CODE_NULL);
		}                                    
		if (!overhaulLogEntity.getProcessStatus().equals(OverhaulArrangeProcessEnum.WATISUBMIT.getCode())) {
			throw new OverhaulArrangeException(OverhaulArrangeExceptionType.DEFECT_CODE_STATUS);
		}
		return true;
	}
	
	@Override
	public ResultObj addValidate(OverhaulLogEntity t) {
		Page<OverhaulLogEntity> page = new Page<OverhaulLogEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		List<Condition> conditions = new ArrayList<Condition>();
 		DateFormatUtil df= DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(t.getLogDate()).toString().substring(0, 10);
	    conditions.add(new Condition("a.C_LOG_DATE",FieldTypeEnum.STRING  ,MatchTypeEnum.LIKE, dateStr));
		page.addOrder(Sort.desc("C_ID"));
		List<OverhaulLogEntity> list = overhaulLogDao.findByCondition(conditions, page);
		if(!list.isEmpty()){
			//存在未完成的记录
			for(OverhaulLogEntity entity :list){
				if(entity.getLogDate().toString().substring(0, 10).equals(t.getLogDate().toString().substring(0, 10))){
					//添加过检修日志
					throw new OverhaulLogException(OverhaulLogExceptionType.ADD_DATE_REPEAT.getErrorMsg(),OverhaulLogExceptionType.ADD_DATE_REPEAT.getErrorCode());
				}
			}
		}
		ResultObj resultObj = new ResultObj();
		return resultObj;
	}
	
	/**
	 * @Description:   流程基本验证
	 * @author         wangcc 
	 * @Date           2017年8月18日 下午3:41:01 
	 * @throws         Exception
	 */
	public boolean validateStatus(OverhaulLogEntity overhaulLogEntity) {
		OverhaulLogEntity entity = this.findById(overhaulLogEntity.getId());
		if (entity == null) {
			throw new OverhaulArrangeException(
					OverhaulArrangeExceptionType.DEFECT_CODE_NULL);
		}
		if (!overhaulLogEntity.getProcessStatus().equals(entity.getProcessStatus())) {
			throw new OverhaulArrangeException(
					OverhaulArrangeExceptionType.DEFECT_CODE_REPEAT);
		}
		return true;
	}

	@Override
	public ResultObj updEntity(OverhaulLogEntity overhaulLogEntity) {
		ResultObj resultObj = new ResultObj();
		DateFormatUtil sdf = DateFormatUtil.getInstance("yyyy-MM-dd");
		OverhaulLogEntity entity = overhaulLogDao.findById(overhaulLogEntity.getId());
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		//用户信息
		overhaulLogEntity.setCreateDate(entity.getCreateDate());
		overhaulLogEntity.setCreateUserId(entity.getCreateUserId());
		overhaulLogEntity.setUpdateDate(new Date());
		overhaulLogEntity.setUpdateUserId(userEntity.getId());
		overhaulLogEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		overhaulLogEntity.setUnitName(sysUnitService.findById((long)overhaulLogEntity.getUnitId()).getName());
		overhaulLogEntity.setOverhaulNumber(overhaulLogEntity.getOverhaulNumber());
		overhaulLogEntity.setFinishStatus(entity.getFinishStatus());
		overhaulLogEntity.setOverhaulClass(entity.getOverhaulClass());
		
		overhaulLogEntity.setAskPersonId(entity.getAskPersonId());
		overhaulLogEntity.setAskPersonName(entity.getAskPersonName());
		
//	    overhaulLogEntity.setOutUnitName(entity.getOutUnitName());
		
		executeSomeThing(overhaulLogEntity);
		super.updateEntity(overhaulLogEntity);
		if(overhaulLogEntity!=null){
			resultObj.setData(overhaulLogEntity);
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULLOG.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
		return resultObj;
	}
	/**
	 * @Description:   处理负责人、完成状态
	 * @author         wangcc 
	 * @Date           2017年12月29日 上午10:03:55 
	 * @throws         Exception
	 */
	@Override
	public OverhaulLogEntity executeSomeThing(OverhaulLogEntity paramEntity){
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("O.C_OVERHAUL_LOG_ID", FieldTypeEnum.STRING, MatchTypeEnum.EQ,paramEntity.getId()));
		List<OverhaulArrangeEntity> overhaulArrangeEntitys = overhaulArrangeService.findByCondition(conditions, null);
		
		boolean finishFlag  = true;
		StringBuilder sBuilderUserLogName = new StringBuilder();
		StringBuilder sBuilderUserName = new StringBuilder();
		StringBuilder sBuilderUserId = new StringBuilder();
//		StringBuilder sBuilderUserName1 = new StringBuilder();
//		StringBuilder sBuilderUserId1 = new StringBuilder();
		Map<Object, Object> map = new HashMap<Object, Object>();
//		Map<Object, Object> map1 = new HashMap<Object, Object>();
		Map<Object, Object> mapLogingName = new HashMap<Object, Object>();
		for(OverhaulArrangeEntity entity:overhaulArrangeEntitys){
			
			SysUserEntity sysUserEntity = sysUserService.findById(Long.valueOf(entity.getDutyPersonId()));
//			SysUserEntity sysUserEntity1 = sysUserService.findById(Long.valueOf(entity.getAskPersonId()));
			if(!StringUtils.equals(sysUserEntity.getLoginName(), "")){
				map.put(sysUserEntity.getId(), sysUserEntity.getName());
				mapLogingName.put(sysUserEntity.getId(), sysUserEntity.getLoginName());
				
//				map1.put(sysUserEntity1.getId(), sysUserEntity1.getName());
				
				//判断工作安排中的完成情况
				if(StringUtils.equals(entity.getFinishStatusString(),OverhaulArrangeEnum.UNFINISH.getName())){
					finishFlag = false;
				}
			}
		}

		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			sBuilderUserName.append(entry.getValue()).append(",");
			sBuilderUserId.append(entry.getKey()).append(",");
		}
		
		for (Map.Entry<Object, Object> entry : mapLogingName.entrySet()) {
			sBuilderUserLogName.append(entry.getValue()).append(",");
		}
		
//		for (Map.Entry<Object, Object> entry1 : map1.entrySet()) {
//			sBuilderUserName1.append(entry1.getValue()).append(",");
//			sBuilderUserId1.append(entry1.getKey()).append(",");
//		}
		
		paramEntity.setDutyUserName(sBuilderUserName.toString());
		paramEntity.setDutyUserId(sBuilderUserId.toString());
		paramEntity.setDutyUserLoginName(sBuilderUserLogName.toString());
		paramEntity.setFinishStatus(finishFlag==true?"1":"0");
		
//		paramEntity.setAskPersonName(sBuilderUserName1.toString());
//		paramEntity.setAskPersonId(sBuilderUserId1.toString());
		
		return paramEntity;
	}

	@Override
	public ResultObj batchDelete(List<Long> ids) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		for(Long id:ids){
			List<Condition> conditions = new ArrayList<Condition>();
			OverhaulLogEntity overhaulLogEntity = overhaulLogDao.findById(id);
			overhaulLogEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
			overhaulLogDao.updateEntity(overhaulLogEntity);
			conditions.add(new Condition("O.C_OVERHAUL_LOG_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
			List<OverhaulArrangeEntity> overhaulArrangeEntities =  overhaulArrangeService.findByCondition(conditions, null);
			if(!overhaulArrangeEntities.isEmpty()){
				for(OverhaulArrangeEntity overhaulArrangeEntity:overhaulArrangeEntities){
					//删除工作安排
					overhaulArrangeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulArrangeEntity.setUpdateDate(new Date());
					overhaulArrangeEntity.setUpdateUserId(userEntity.getId().toString());
					overhaulArrangeService.updateEntity(overhaulArrangeEntity);
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
					//删除备件消耗
					conditions.clear();
					conditions.add(new Condition("C_OVERHAUL_ARRANGE_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
					List<OverhaulSpareconsumeEntity> spareconsumeEntities =  overhaulSpareconsumeService.findByCondition(conditions, null);
					for(OverhaulSpareconsumeEntity overhaulEntity:spareconsumeEntities){
						overhaulEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
						overhaulSpareconsumeService.update(overhaulEntity);
					}
					
				}
				
			}
		}
		return resultObj;
	}

	@Override
	public ResultObj deleteOnlyOne(Long id) {
		ResultObj resultObj = new ResultObj();
		//用户信息
		SysUserEntity userEntity = RequestContext.get().getUser();
		List<Condition> conditions = new ArrayList<Condition>();
		OverhaulLogEntity overhaulLogEntity = overhaulLogDao.findById(id);
		overhaulLogEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
		overhaulLogDao.updateEntity(overhaulLogEntity);
		conditions.add(new Condition("O.C_OVERHAUL_LOG_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,id));
		List<OverhaulArrangeEntity> overhaulArrangeEntities =  overhaulArrangeService.findByCondition(conditions, null);
		if(!overhaulArrangeEntities.isEmpty()){
			for(OverhaulArrangeEntity overhaulArrangeEntity:overhaulArrangeEntities){
				//删除工作安排
				overhaulArrangeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
				overhaulArrangeEntity.setUpdateDate(new Date());
				overhaulArrangeEntity.setUpdateUserId(userEntity.getId().toString());
				overhaulArrangeService.updateEntity(overhaulArrangeEntity);
				//删除检修日志明细
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulLogDetailEntity> logDetailEntities =  overhaulLogDetailService.findByCondition(conditions, null);
				for(OverhaulLogDetailEntity overhaulLogDetailEntity:logDetailEntities){
					overhaulLogDetailEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulLogDetailEntity.setUpdateDate(new Date());
					overhaulLogDetailEntity.setUpdateUserId(userEntity.getId());
					overhaulLogDetailService.updateEntity(overhaulLogDetailEntity);
				}
				//删除工作任务
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulWorkTaskEntity> workTaskEntities =  overhaulWorkTaskService.findByCondition(conditions, null);
				for(OverhaulWorkTaskEntity workTaskEntity:workTaskEntities){
					workTaskEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					workTaskEntity.setUpdateDate(new Date());
					workTaskEntity.setUpdateUserId(userEntity.getId());
					overhaulWorkTaskService.updateEntity(workTaskEntity);
				}
				//删除安全交底
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulSafeEntity> overhaulSafeEntities =  overhaulSafeService.findByCondition(conditions, null);
				for(OverhaulSafeEntity overhaulSafeEntity:overhaulSafeEntities){
					overhaulSafeEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulSafeEntity.setUpdateDate(new Date());
					overhaulSafeEntity.setUpdateUserId(userEntity.getId());
					overhaulSafeService.update(overhaulSafeEntity);
				}
				//删除备件消耗
				conditions.clear();
				conditions.add(new Condition("C_OVERHAUL_RECORD_ID", FieldTypeEnum.INT, MatchTypeEnum.EQ,overhaulArrangeEntity.getId()));
				List<OverhaulSpareconsumeEntity> spareconsumeEntities =  overhaulSpareconsumeService.findByCondition(conditions, null);
				for(OverhaulSpareconsumeEntity overhaulEntity:spareconsumeEntities){
					overhaulEntity.setStatus(OverhaulWorkTaskStatusEnum.DELETE.getCode());
					overhaulSpareconsumeService.update(overhaulEntity);
				}
				
			}
		}
		return resultObj;
	}
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.OVERHAULLOG.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}