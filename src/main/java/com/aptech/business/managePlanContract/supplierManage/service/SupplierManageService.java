package com.aptech.business.managePlanContract.supplierManage.service;

import java.io.Serializable;
import java.text.ParseException;

import com.aptech.business.managePlanContract.supplierManage.domain.SupplierManageEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 供应商管理应用管理服务接口
 *
 * @author 
 * @created 2018-07-26 17:15:46
 * @lastModified 
 * @history
 *
 */
public interface SupplierManageService  extends IBaseEntityOperation<SupplierManageEntity> {
	
	/**
	 * 复制
	 * @param ids
	 */
	public void bulkCopyAdd(String ids) throws ParseException ;
	
}