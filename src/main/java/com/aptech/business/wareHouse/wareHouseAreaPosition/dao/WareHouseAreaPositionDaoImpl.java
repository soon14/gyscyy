package com.aptech.business.wareHouse.wareHouseAreaPosition.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.wareHouse.wareHouseAreaPosition.domain.WareHouseAreaPositionEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 货区货位关联表应用数据类
 *
 * @author 
 * @created 2017-11-06 20:01:23
 * @lastModified 
 * @history
 *
 */
@Repository("wareHouseAreaPositionDao")
public class WareHouseAreaPositionDaoImpl extends AncestorDao<WareHouseAreaPositionEntity> implements WareHouseAreaPositionDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.wareHouse.wareHouseAreaPosition";
	}
}
