package com.aptech.business.run.runWay.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.runWay.domain.RunWayEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 运行方式应用数据类
 *
 * @author 
 * @created 2017-06-20 09:26:27
 * @lastModified 
 * @history
 *
 */
@Repository("runWayDao")
public class RunWayDaoImpl extends AncestorDao<RunWayEntity> implements RunWayDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.runWay";
	}
}
