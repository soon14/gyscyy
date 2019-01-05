package com.aptech.business.run.runRecord.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.component.dictionary.BusinessDictCategoryEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.run.runLog.service.RunLogService;
import com.aptech.business.run.runRecord.dao.RunRecordDao;
import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.base.ResultListObj;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.validate.ValidateUtil;
import com.aptech.framework.web.base.PageUtil;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 运行记事应用管理服务实现类
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
@Service("runRecordService")
@Transactional
public class RunRecordServiceImpl extends AbstractBaseEntityOperation<RunRecordEntity> implements RunRecordService {
	
	@Autowired
	private RunRecordDao runRecordDao;
	@Autowired
	private RunLogService runLogService;
	@Autowired
	private OperateLogService operateLogService;
	@Override
	public IBaseEntityOperation<RunRecordEntity> getDao() {
		return runRecordDao;
	}

	@Override
	public ResultListObj searchDate(HttpServletRequest request,
			Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<RunRecordEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		runRecordDao.setFlag(true);
		conditions.add(new Condition("d.C_CATEGORY_CODE", FieldTypeEnum.STRING,MatchTypeEnum.EQ, "RECORD_TYPE"));
		List<RunRecordEntity> overhaulLogEntities =  (List<RunRecordEntity>) runRecordDao.findByCondition(conditions, page);
		resultObj.setDraw((Integer)params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	@Override
	public ResultListObj searchDateForWind(HttpServletRequest request,
			Map<String, Object> params) {
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		ResultListObj resultObj = new ResultListObj();
		Page<RunRecordEntity> page = PageUtil.getPage(params);
		page.setOrders(OrmUtil.changeMapToOrders(params));
		runRecordDao.setFlag(false);
		conditions.add(new Condition("d.C_CATEGORY_CODE", FieldTypeEnum.STRING,MatchTypeEnum.EQ, "FAN_FACTORY_RECORD_TYPE"));
		List<RunRecordEntity> overhaulLogEntities =  (List<RunRecordEntity>) runRecordDao.findByCondition(conditions, page);
		runRecordDao.setFlag(true);
		resultObj.setDraw((Integer)params.get("draw"));
		if (overhaulLogEntities != null) {
			resultObj.setData(overhaulLogEntities);
			resultObj.setRecordsTotal(page.getTotal());
		}
		return resultObj;
	}
	
	@Override
	public void addEntity(RunRecordEntity t) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		getDao().addEntity(t);
	}
	@Override
	public ResultObj addForWind(RunRecordEntity recordEntity,
			HttpServletRequest request) {
		SysUserEntity userEntity = RequestContext.get().getUser();
		ResultObj resultObj = new ResultObj();
		runRecordDao.setFlag(false);
		runRecordDao.addEntity(recordEntity);
		runRecordDao.setFlag(true);
		resultObj.setData(recordEntity);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return resultObj;
	}

	@Override
	public void deleteEntityForWind(Long id) {
		runRecordDao.setFlag(false);
		runRecordDao.deleteEntity(id);
		runRecordDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}

	@Override
	public List<RunRecordEntity> findByConditionForWind(Long rlId) {
		Page<RunRecordEntity> page = new Page<RunRecordEntity>();
		page.setPageSize(Integer.MAX_VALUE);
		page.addOrder(Sort.desc("id"));
		List<Condition> conditions=new ArrayList<Condition>();
        conditions.add(new Condition("a.C_RL_ID", FieldTypeEnum.LONG, MatchTypeEnum.EQ,rlId));
        runRecordDao.setFlag(false);
        List<RunRecordEntity> dataList=runRecordDao.findByCondition(conditions, page);
        runRecordDao.setFlag(true);
		return dataList;
	}

	@Override
	public void updateEntityForWind(RunRecordEntity recordEntity) {
		runRecordDao.setFlag(false);
		runRecordDao.updateEntity(recordEntity);
		runRecordDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}

	@Override
	public RunRecordEntity findByIdForWind(Long id) {
		runRecordDao.setFlag(false);
		RunRecordEntity recordEntity = runRecordDao.findById(id);
		runRecordDao.setFlag(true);
		return recordEntity;
	}
	
	@Override
	public ResultObj deleteForWind(Long id) {
		runRecordDao.setFlag(false);
		runRecordDao.deleteEntity(id);
		runRecordDao.setFlag(true);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
		return new ResultObj();
	}

	@Override
	public ResultObj addRun(RunRecordEntity t, Long rlId) {
		// TODO Auto-generated method stub
		RunRecordEntity runRecordEntity = new RunRecordEntity();
		runRecordEntity.setfZR(t.getfZR());
		runRecordEntity.setReceptDate(runLogService.findById(rlId).getDate());
		runRecordEntity.setRecordContent(t.getRecordContent());
		runRecordEntity.setRecordTime(t.getRecordTime());
		runRecordEntity.setRecordType(t.getRecordType());
		runRecordEntity.setUnitId(t.getUnitId());
		runRecordEntity.setRlId(t.getRlId());
		runRecordDao.addEntity(runRecordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.RUNRECORD.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
		return new ResultObj();
	}
}