package com.aptech.business.ticketManage.operationDanger.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.operationDanger.domain.OperationDangerEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 操作票危险因素情况表应用数据类
 *
 * @author 
 * @created 2017-07-12 17:27:40
 * @lastModified 
 * @history
 *
 */
@Repository("operationDangerDao")
public class OperationDangerDaoImpl extends AncestorDao<OperationDangerEntity> implements OperationDangerDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.operationDanger";
	}
}
