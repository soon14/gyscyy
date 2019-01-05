package com.aptech.business.cargo.stock.service;

import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 库存管理应用管理服务接口
 *
 * @author 
 * @created 2017-07-17 16:40:59
 * @lastModified 
 * @history
 *
 */
public interface StockService  extends IBaseEntityOperation<StockEntity> {

	public void caseManage(double mCountDouble,double upperCountDouble,double lowerCountDouble, String kcslCount, String upperCount,String lowerCount,StockEntity stockEntity);
	
	public void chargeManage(double mCountDouble,double upperCountDouble,double lowerCountDouble, String kcslCount, String upperCount,String lowerCount,StockEntity stockEntity);
}