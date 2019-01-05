package com.aptech.business.wareHouse.wareHouseAreaPosition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.wareHouse.wareHouseAreaPosition.dao.WareHouseAreaPositionDao;
import com.aptech.business.wareHouse.wareHouseAreaPosition.domain.WareHouseAreaPositionEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 货区货位关联表应用管理服务实现类
 *
 * @author 
 * @created 2017-11-06 20:01:23
 * @lastModified 
 * @history
 *
 */
@Service("wareHouseAreaPositionService")
@Transactional
public class WareHouseAreaPositionServiceImpl extends AbstractBaseEntityOperation<WareHouseAreaPositionEntity> implements WareHouseAreaPositionService {
	
	@Autowired
	private WareHouseAreaPositionDao wareHouseAreaPositionDao;
	
	@Override
	public IBaseEntityOperation<WareHouseAreaPositionEntity> getDao() {
		return wareHouseAreaPositionDao;
	}
}