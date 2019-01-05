package com.aptech.business.managePlanContract.purchaseOrganization.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.managePlanContract.budgetManage.domain.BudgetManageEntity;
import com.aptech.business.managePlanContract.purchaseOrganization.dao.PurchaseOrganizationDao;
import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
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
 * 物资采购计划组织机构列表应用管理服务实现类
 *
 * @author 
 * @created 2018-07-25 17:08:06
 * @lastModified 
 * @history
 *
 */
@Service("purchaseOrganizationService")
@Transactional
public class PurchaseOrganizationServiceImpl extends AbstractBaseEntityOperation<PurchaseOrganizationEntity> implements PurchaseOrganizationService {
	
	@Autowired
	private PurchaseOrganizationDao purchaseOrganizationDao;
	
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<PurchaseOrganizationEntity> getDao() {
		return purchaseOrganizationDao;
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
	public void addEntity(PurchaseOrganizationEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		super.addEntity(t);
		
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGEORGANIZATION.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(PurchaseOrganizationEntity t) {
		
		PurchaseOrganizationEntity entity =purchaseOrganizationDao.findById(t.getId());
		t.setCreateDate(entity.getCreateDate());
		t.setCreateUserId(entity.getCreateUserId());
		purchaseOrganizationDao.updateEntity(t);
		
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGEORGANIZATION.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGEORGANIZATION.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id){
		PurchaseOrganizationEntity recordEntity  =this.findById(id);
		purchaseOrganizationDao.deleteEntity(recordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.BUDGETMANAGEORGANIZATION.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
}