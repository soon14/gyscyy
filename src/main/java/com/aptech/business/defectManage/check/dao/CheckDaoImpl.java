package com.aptech.business.defectManage.check.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.check.domain.CheckEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * ȱ������Ӧ�������
 *
 * @author 
 * @created 2017-06-05 15:59:06
 * @lastModified 
 * @history
 *
 */
@Repository("checkDao")
public class CheckDaoImpl extends AncestorDao<CheckEntity> implements CheckDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectManage.check";
	}
}
