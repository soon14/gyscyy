package com.aptech.business.wareHouse.wareHouseArea.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.wareHouse.wareHouseArea.domain.WareHouseAreaEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 仓库货区表应用数据类
 *
 * @author 
 * @created 2017-11-06 19:58:49
 * @lastModified 
 * @history
 *
 */
@Repository("wareHouseAreaDao")
public class WareHouseAreaDaoImpl extends AncestorDao<WareHouseAreaEntity> implements WareHouseAreaDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.wareHouse.wareHouseArea";
	}
}
