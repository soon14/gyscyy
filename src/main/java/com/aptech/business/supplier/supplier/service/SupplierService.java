package com.aptech.business.supplier.supplier.service;

import com.aptech.business.supplier.supplier.domain.SupplierEntity;
import com.aptech.common.web.ComboboxVO;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 供应商管理应用管理服务接口
 *
 * @author 
 * @created 2017-11-02 10:30:36
 * @lastModified 
 * @history
 *
 */
public interface SupplierService  extends IBaseEntityOperation<SupplierEntity> {
	
	/**
	 * 
	 * 获取供应商类型
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月2日 下午2:27:29
	 * @lastModified
	 */
	public ComboboxVO getSupplierType();
	
	/**
	 * 
	 * 获取性别
	 * 
	 * @param @return
	 * @return ComboboxVO
	 * @throws 
	 * @author wangyue
	 * @created 2017年11月3日 下午1:59:14
	 * @lastModified
	 */
	public ComboboxVO getSexType();
}