package com.aptech.business.contractManage.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.contractManage.domain.ContractManageEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 合同管理应用数据类
 *
 * @author 
 * @created 2018-04-17 14:03:40
 * @lastModified 
 * @history
 *
 */
@Repository("contractManageDao")
public class ContractManageDaoImpl extends AncestorDao<ContractManageEntity> implements ContractManageDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.contractManage";
	}
}
