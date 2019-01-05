package com.aptech.business.safeManage.safeStandard.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeStandard.domain.SafeStandardEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全标准化建设应用数据类
 *
 * @author 
 * @created 2018-04-02 13:58:10
 * @lastModified 
 * @history
 *
 */
@Repository("safeStandardDao")
public class SafeStandardDaoImpl extends AncestorDao<SafeStandardEntity> implements SafeStandardDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeManage.safeStandard";
	}
}
