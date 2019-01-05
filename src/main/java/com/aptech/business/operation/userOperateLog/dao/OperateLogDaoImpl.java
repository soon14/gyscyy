package com.aptech.business.operation.userOperateLog.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.operation.userOperateLog.domain.OperateLogEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 用户操作应用数据类
 *
 * @author 
 * @created 2018-04-09 10:36:54
 * @lastModified 
 * @history
 *
 */
@Repository("operateLogDao")
public class OperateLogDaoImpl extends AncestorDao<OperateLogEntity> implements OperateLogDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.OperateLogEntity";
	}
}
