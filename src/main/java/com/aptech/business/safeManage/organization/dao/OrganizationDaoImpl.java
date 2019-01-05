package com.aptech.business.safeManage.organization.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.organization.domain.OrganizationEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 组织机构应用数据类
 *
 * @author 
 * @created 2018-03-27 16:31:43
 * @lastModified 
 * @history
 *
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends AncestorDao<OrganizationEntity> implements OrganizationDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.organization";
	}
}
