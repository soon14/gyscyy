package com.aptech.business.contractFkjl.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.contractFkjl.domain.ContractFkjlEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 合同付款记录应用数据类
 *
 * @author 
 * @created 2018-09-11 15:39:29
 * @lastModified 
 * @history
 *
 */
@Repository("contractFkjlDao")
public class ContractFkjlDaoImpl extends AncestorDao<ContractFkjlEntity> implements ContractFkjlDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.contractFkjl";
	}
}
