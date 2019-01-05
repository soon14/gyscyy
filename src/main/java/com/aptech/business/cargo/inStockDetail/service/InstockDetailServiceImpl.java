package com.aptech.business.cargo.inStockDetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.inStockDetail.dao.InstockDetailDao;
import com.aptech.business.cargo.inStockDetail.domain.InstockDetailEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 入库物资明细应用管理服务实现类
 *
 * @author 
 * @created 2017-07-24 16:20:23
 * @lastModified 
 * @history
 *
 */
@Service("instockDetailService")
@Transactional
public class InstockDetailServiceImpl extends AbstractBaseEntityOperation<InstockDetailEntity> implements InstockDetailService {
	
	@Autowired
	private InstockDetailDao instockDetailDao;
	
	@Override
	public IBaseEntityOperation<InstockDetailEntity> getDao() {
		return instockDetailDao;
	}
	
}