package com.aptech.business.invitePurchase.contractDeal.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.invitePurchase.contractDeal.domain.ContractDealEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 合同签订应用数据类
 *
 * @author 
 * @created 2018-09-12 10:01:29
 * @lastModified 
 * @history
 *
 */
@Repository("contractDealDao")
public class ContractDealDaoImpl extends AncestorDao<ContractDealEntity> implements ContractDealDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.contractDeal";
	}
}
