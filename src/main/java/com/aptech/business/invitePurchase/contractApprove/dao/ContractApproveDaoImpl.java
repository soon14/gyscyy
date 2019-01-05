package com.aptech.business.invitePurchase.contractApprove.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.invitePurchase.contractApprove.domain.ContractApproveEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 合同审批应用数据类
 *
 * @author 
 * @created 2018-09-11 15:13:24
 * @lastModified 
 * @history
 *
 */
@Repository("contractApproveDao")
public class ContractApproveDaoImpl extends AncestorDao<ContractApproveEntity> implements ContractApproveDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.contractApprove";
	}
}
