package com.aptech.business.OAManagement.jointlySign.dao;
 
import org.springframework.stereotype.Repository;

import com.aptech.business.OAManagement.jointlySign.domain.DispatchJointlySignEntity;
import com.aptech.framework.orm.AncestorDao;

@Repository("dispatchJointlySignDao")
public class DispatchJointlySignDaoImpl extends AncestorDao<DispatchJointlySignEntity> implements DispatchJointlySignDao {
    @Override
	public String getNameSpace() {
		return "com.aptech.business.OAManagement.jointlySign.DispatchJointlySign";
	}
    
}