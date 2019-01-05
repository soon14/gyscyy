package com.aptech.business.ticketManage.operationItem.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.operationItem.domain.OperationItemEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 操作票项目表应用数据类
 *
 * @author 
 * @created 2017-07-12 17:27:36
 * @lastModified 
 * @history
 *
 */
@Repository("operationItemDao")
public class OperationItemDaoImpl extends AncestorDao<OperationItemEntity> implements OperationItemDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.ticketManage.operationItem";
	}
}
