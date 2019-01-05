package com.aptech.business.teamMemApp.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.teamMemApp.domain.TeamMemAppEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 班次应用数据类
 *
 * @author 
 * @created 2017-09-13 17:15:08
 * @lastModified 
 * @history
 *
 */
@Repository("teamMemAppDao")
public class TeamMemAppDaoImpl extends AncestorDao<TeamMemAppEntity> implements TeamMemAppDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.bussiness.teamMemApp";
	}
}
