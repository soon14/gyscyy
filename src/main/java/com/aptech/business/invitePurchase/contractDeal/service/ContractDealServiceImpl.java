package com.aptech.business.invitePurchase.contractDeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.invitePurchase.contractDeal.dao.ContractDealDao;
import com.aptech.business.invitePurchase.contractDeal.domain.ContractDealEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 合同签订应用管理服务实现类
 *
 * @author 
 * @created 2018-09-12 10:01:29
 * @lastModified 
 * @history
 *
 */
@Service("contractDealService")
@Transactional
public class ContractDealServiceImpl extends AbstractBaseEntityOperation<ContractDealEntity> implements ContractDealService {
	
	@Autowired
	private ContractDealDao contractDealDao;
	
	@Override
	public IBaseEntityOperation<ContractDealEntity> getDao() {
		return contractDealDao;
	}
}