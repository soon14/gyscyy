package com.aptech.business.overhaul.overhaulRecord.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.overhaul.overhaulPlan.domain.OverhaulPlanEntity;
import com.aptech.business.overhaul.overhaulRecord.dao.OverhaulRecordDao;
import com.aptech.business.overhaul.overhaulRecord.domain.OverhaulRecordEntity;
import com.aptech.common.system.user.domain.SysUserEntity;
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

/**
 * 
 * 设备检修记录应用管理服务实现类
 *
 * @author 
 * @created 2018-06-05 11:23:18
 * @lastModified 
 * @history
 *
 */
@Service("overhaulRecordService")
@Transactional
public class OverhaulRecordServiceImpl extends AbstractBaseEntityOperation<OverhaulRecordEntity> implements OverhaulRecordService {
	
	@Autowired
	private OverhaulRecordDao overhaulRecordDao;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<OverhaulRecordEntity> getDao() {
		return overhaulRecordDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.desc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
//		SysUserEntity userEntity = RequestContext.get().getUser();
//		conditions.add(new Condition("a.C_UNIT_ID",FieldTypeEnum.INT,MatchTypeEnum.EQ,userEntity.getUnitId()));
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(OverhaulRecordEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		
		overhaulRecordDao.addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPRECORD.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(OverhaulRecordEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		super.updateEntity(t);
		
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPRECORD.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPRECORD.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteEntity(Serializable id){
		OverhaulRecordEntity recordEntity  =this.findById(id);
		overhaulRecordDao.deleteEntity(recordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.EQUIPRECORD.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}