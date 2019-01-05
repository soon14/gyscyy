package com.aptech.business.overhaul.power.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.power.domain.PowerEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 停送电管理应用数据类
 *
 * @author 
 * @created 2017-07-31 14:17:20
 * @lastModified 
 * @history
 *
 */
@Repository("powerDao")
public class PowerDaoImpl extends AncestorDao<PowerEntity> implements PowerDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.power";
	}
}
