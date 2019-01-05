package com.aptech.business.managePlanContract.purchaseType.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.managePlanContract.purchaseType.dao.PurchaseTypeDao;
import com.aptech.business.managePlanContract.purchaseType.domain.PurchaseTypeEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.OrmUtil;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.Sort;
import com.aptech.framework.orm.search.Condition;

/**
 * 
 * 采购类型应用管理服务实现类
 *
 * @author 
 * @created 2018-07-30 16:49:32
 * @lastModified 
 * @history
 *
 */
@Service("purchaseTypeService")
@Transactional
public class PurchaseTypeServiceImpl extends AbstractBaseEntityOperation<PurchaseTypeEntity> implements PurchaseTypeService {
	
	@Autowired
	private PurchaseTypeDao purchaseTypeDao;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<PurchaseTypeEntity> getDao() {
		return purchaseTypeDao;
	}
	
	@Override
	public <O> List<O> findByCondition(Map<String, Object> params, Page<O> page) {
		if(page == null) {
			page = new Page<O>();
			page.setPageSize(Integer.MAX_VALUE);
		}
   		page.setOrders(OrmUtil.changeMapToOrders(params));
		page.addOrder(Sort.asc("C_ID"));
		List<Condition> conditions = OrmUtil.changeMapToCondition(params);
		List<O> resultList = super.findByCondition(conditions, page);
		return resultList;
	}
	
	@Override
	public void addEntity(PurchaseTypeEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		super.addEntity(t);
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PURCHASETYPE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(PurchaseTypeEntity t) {
		
		PurchaseTypeEntity entity =purchaseTypeDao.findById(t.getId());
		t.setCreateDate(entity.getCreateDate());
		t.setCreateUserId(entity.getCreateUserId());
		purchaseTypeDao.updateEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PURCHASETYPE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PURCHASETYPE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id){
		PurchaseTypeEntity recordEntity  =this.findById(id);
		purchaseTypeDao.deleteEntity(recordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.PURCHASETYPE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}