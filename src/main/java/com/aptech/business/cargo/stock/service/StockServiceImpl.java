package com.aptech.business.cargo.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.stock.dao.StockDao;
import com.aptech.business.cargo.stock.domain.StockEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 库存管理应用管理服务实现类
 *
 * @author 
 * @created 2017-07-17 16:40:59
 * @lastModified 
 * @history
 *
 */
@Service("stockService")
@Transactional
public class StockServiceImpl extends AbstractBaseEntityOperation<StockEntity> implements StockService {
	
	@Autowired
	private StockDao stockDao;
	
	@Override
	public IBaseEntityOperation<StockEntity> getDao() {
		return stockDao;
	}
	
	public void caseManage(double mCountDouble,double upperCountDouble,double lowerCountDouble, String kcslCount, String upperCount,String lowerCount,StockEntity stockEntity){
		int mCountInteger = (int) Math.floor(mCountDouble);
		int upperCountInteger = (int) Math.floor(upperCountDouble);
		int lowerCountInteger = (int) Math.floor(lowerCountDouble);
		kcslCount = String.valueOf(mCountInteger);
		upperCount = String.valueOf(upperCountInteger);
		lowerCount = String.valueOf(lowerCountInteger);
		//整数时判断是否短缺
		if(lowerCountDouble>mCountDouble){
			stockEntity.setIsShortage("1");
			int shortageInteger = lowerCountInteger-mCountInteger;
			stockEntity.setShortage(String.valueOf(shortageInteger));
		}else{
			stockEntity.setIsShortage("0");
			stockEntity.setShortage("0");
		}
		stockEntity.setInventoryQuantity(kcslCount);
		stockEntity.setUpperLimit(upperCount);
		stockEntity.setLowerLimit(lowerCount);
	}
	
	public void chargeManage(double mCountDouble,double upperCountDouble,double lowerCountDouble, String kcslCount, String upperCount,String lowerCount,StockEntity stockEntity){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
		kcslCount = String.valueOf(df.format(mCountDouble));
		upperCount = String.valueOf(df.format(upperCountDouble));
		lowerCount = String.valueOf(df.format(lowerCountDouble));
		//判断是否短缺
		if(lowerCountDouble>mCountDouble){
			stockEntity.setIsShortage("1");
			double shortageInteger = lowerCountDouble-mCountDouble;
			stockEntity.setShortage(String.valueOf(df.format(shortageInteger)));
		}else{
			stockEntity.setIsShortage("0");
			stockEntity.setShortage("0");
		}
		stockEntity.setInventoryQuantity(kcslCount);
		stockEntity.setUpperLimit(upperCount);
		stockEntity.setLowerLimit(lowerCount);
	}
}