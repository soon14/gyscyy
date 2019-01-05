package com.aptech.business.managePlanContract.supplierManage.service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.managePlanContract.purchaseOrganization.domain.PurchaseOrganizationEntity;
import com.aptech.business.managePlanContract.supplierManage.dao.SupplierManageDao;
import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.util.DateFormatUtil;

/**
 * 
 * 供应商管理应用管理服务实现类
 *
 * @author 
 * @created 2018-07-26 17:15:46
 * @lastModified 
 * @history
 *
 */
@Service("supplierManageService")
@Transactional
public class SupplierManageServiceImpl extends AbstractBaseEntityOperation<SupplierManageEntity> implements SupplierManageService {
	
	@Autowired
	private SupplierManageDao supplierManageDao;
	@Autowired
	private OperateLogService operateLogService;
	
	@Override
	public IBaseEntityOperation<SupplierManageEntity> getDao() {
		return supplierManageDao;
	}
	
	@Override
	public void addEntity(SupplierManageEntity t) {
		t.setCreateDate(new Date());
		SysUserEntity userEntity = RequestContext.get().getUser();
		t.setCreateUserId(userEntity.getId().toString());
		t.setStatus("0");
		super.addEntity(t);
		
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPPLIERMANAGE.getName(),OperateUserEnum.ADD.getName(),"",OperateUserEnum.ADD.getId());
	}
	
	@Override
	public void updateEntity(SupplierManageEntity t) {
		
		SupplierManageEntity entity = supplierManageDao.findById(t.getId());
		t.setCreateUserId(entity.getCreateUserId());
		t.setCreateDate(entity.getCreateDate());
		t.setYear(entity.getYear());
		
		supplierManageDao.updateEntity(t);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPPLIERMANAGE.getName(),OperateUserEnum.EDIT.getName(),"",OperateUserEnum.EDIT.getId());
	}
	
	@Override
	public void bulkDelete(Serializable[] ids) {
		getDao().bulkDelete(ids);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPPLIERMANAGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Override
	public void deleteEntity(Serializable id){
		SupplierManageEntity recordEntity  =this.findById(id);
		supplierManageDao.deleteEntity(recordEntity);
		SysUserEntity userEntity = RequestContext.get().getUser();
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SUPPLIERMANAGE.getName(),OperateUserEnum.DELETE.getName(),"",OperateUserEnum.DELETE.getId());
	}
	
    /**
     * 复制
     * @throws ParseException 
     */
	@Override
	public void bulkCopyAdd(String ids) throws ParseException {
		DateFormatUtil df= DateFormatUtil.getInstance("yyyy");
		String[] strings =ids.split(",");
		
		for (String id : strings) {
				 SupplierManageEntity entity = new SupplierManageEntity();
				 entity  =supplierManageDao.findById(Long.parseLong(id));
				 String dateStr = df.format(entity.getYear());
				 String newDate = String.valueOf(Long.parseLong(dateStr)+1);  
				 entity.setCreateDate(new Date());
				 SysUserEntity userEntity = RequestContext.get().getUser();
				 entity.setCreateUserId(userEntity.getId().toString());
				 entity.setYear(df.parse(newDate));
				 super.addEntity(entity);
		}
	}
	
	
	
}