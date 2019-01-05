package com.aptech.business.orgaApp.dao;


import org.springframework.stereotype.Repository;

import com.aptech.business.orgaApp.domain.OrgaAppEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 班组应用数据类
 *
 * @author 
 * @created 2017-09-14 09:37:02
 * @lastModified 
 * @history
 *
 */
@Repository("orgaAppDao")
public class OrgaAppDaoImpl extends AncestorDao<OrgaAppEntity> implements OrgaAppDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.orgaApp";
	}
}
