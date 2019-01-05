package com.aptech.business.run.joinLand.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.joinLand.domain.JoinLandEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 接地线（刀闸）情况应用数据类
 *
 * @author 
 * @created 2017-06-06 09:48:08
 * @lastModified 
 * @history
 *
 */
@Repository("joinLandDao")
public class JoinLandDaoImpl extends AncestorDao<JoinLandEntity> implements JoinLandDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.joinLand";
	}
}
