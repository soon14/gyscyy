package com.aptech.business.invitePurchase.contractApprove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.invitePurchase.contractApprove.dao.ContractApproveDao;
import com.aptech.business.invitePurchase.contractApprove.domain.ContractApproveEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 合同审批应用管理服务实现类
 *
 * @author 
 * @created 2018-09-11 15:13:24
 * @lastModified 
 * @history
 *
 */
@Service("contractApproveService")
@Transactional
public class ContractApproveServiceImpl extends AbstractBaseEntityOperation<ContractApproveEntity> implements ContractApproveService {
	
	@Autowired
	private ContractApproveDao contractApproveDao;
	
	@Override
	public IBaseEntityOperation<ContractApproveEntity> getDao() {
		return contractApproveDao;
	}
}