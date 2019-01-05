package com.aptech.business.train.trainManagement.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.train.trainManagement.domain.TrainManagementEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 培训管理数据类
 * @author Administrator
 *
 */
@Repository("trainManagementDao")
public class TrainManagementDaoImpl extends AncestorDao<TrainManagementEntity> implements TrainManagementDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.trainManagement";
	}
}
