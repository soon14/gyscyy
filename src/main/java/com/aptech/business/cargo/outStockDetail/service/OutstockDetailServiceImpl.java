package com.aptech.business.cargo.outStockDetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.outStockDetail.dao.OutstockDetailDao;
import com.aptech.business.cargo.outStockDetail.domain.OutstockDetailEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 出库物资明细应用管理服务实现类
 *
 * @author wangyue
 * @created 2017年7月25日 下午5:16:17 
 * @lastModified 
 * @history
 *
 */
@Service("OutstockDetailService")
@Transactional
public class OutstockDetailServiceImpl extends AbstractBaseEntityOperation<OutstockDetailEntity> implements OutstockDetailService{

	@Autowired
	private OutstockDetailDao outstockDetailDao;

	@Override
	public IBaseEntityOperation<OutstockDetailEntity> getDao() {
		// TODO Auto-generated method stub
		return outstockDetailDao;
	}
	
	
}
