package com.aptech.business.cargo.inOutStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.inOutStock.dao.InOutStockDao;
import com.aptech.business.cargo.inOutStock.domain.InOutStockEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 出入库明细应用管理服务实现类
 *
 * @author 
 * @created 2017-07-15 16:14:14
 * @lastModified 
 * @history
 *
 */
@Service("inOutStockService")
@Transactional
public class InOutStockServiceImpl extends AbstractBaseEntityOperation<InOutStockEntity> implements InOutStockService {
	
	@Autowired
	private InOutStockDao inOutStockDao;
	
	@Override
	public IBaseEntityOperation<InOutStockEntity> getDao() {
		return inOutStockDao;
	}
}