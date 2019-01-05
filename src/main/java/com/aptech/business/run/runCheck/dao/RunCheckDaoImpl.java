package com.aptech.business.run.runCheck.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.runCheck.domain.RunCheckEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 运行检查应用数据类
 *
 * @author 
 * @created 2017-09-08 15:00:42
 * @lastModified 
 * @history
 *
 */
@Repository("runCheckDao")
public class RunCheckDaoImpl extends AncestorDao<RunCheckEntity> implements RunCheckDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.runCheck";
	}
}
