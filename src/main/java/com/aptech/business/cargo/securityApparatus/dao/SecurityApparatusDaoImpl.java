package com.aptech.business.cargo.securityApparatus.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.securityApparatus.domain.SecurityApparatusEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全工器具管理应用数据类
 *
 * @author 
 * @created 2018-03-15 13:41:51
 * @lastModified 
 * @history
 *
 */
@Repository("securityApparatusDao")
public class SecurityApparatusDaoImpl extends AncestorDao<SecurityApparatusEntity> implements SecurityApparatusDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.securityApparatus";
	}
}
