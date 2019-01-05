package com.aptech.business.wareHouse.wareHouseArea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.wareHouse.wareHouseArea.dao.WareHouseAreaDao;
import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 仓库货区表应用管理服务实现类
 *
 * @author 
 * @created 2017-11-06 19:58:49
 * @lastModified 
 * @history
 *
 */
@Service("wareHouseAreaService")
@Transactional
public class WareHouseAreaServiceImpl extends AbstractBaseEntityOperation<WareHouseAreaEntity> implements WareHouseAreaService {
	
	@Autowired
	private WareHouseAreaDao wareHouseAreaDao;
	
	@Override
	public IBaseEntityOperation<WareHouseAreaEntity> getDao() {
		return wareHouseAreaDao;
	}
}