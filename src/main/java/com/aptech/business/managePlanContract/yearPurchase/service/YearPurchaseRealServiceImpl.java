package com.aptech.business.managePlanContract.yearPurchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.managePlanContract.yearPurchase.dao.YearPurchaseRealDao;
import com.aptech.business.managePlanContract.yearPurchase.domain.YearPurchaseRealEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 年度采购计划实际填报应用管理服务实现类
 *
 * @author 
 * @created 2018-09-04 13:38:00
 * @lastModified 
 * @history
 *
 */
@Service("yearPurchaseRealService")
@Transactional
public class YearPurchaseRealServiceImpl extends AbstractBaseEntityOperation<YearPurchaseRealEntity> implements YearPurchaseRealService {
	
	@Autowired
	private YearPurchaseRealDao yearPurchaseRealDao;
	
	@Override
	public IBaseEntityOperation<YearPurchaseRealEntity> getDao() {
		return yearPurchaseRealDao;
	}
}