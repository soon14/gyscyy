package com.aptech.business.invitePurchase.dealStandard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.invitePurchase.dealStandard.dao.DealStandardDao;
import com.aptech.business.invitePurchase.dealStandard.domain.DealStandardEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 定标请示函应用管理服务实现类
 *
 * @author 
 * @created 2018-09-10 09:10:10
 * @lastModified 
 * @history
 *
 */
@Service("dealStandardService")
@Transactional
public class DealStandardServiceImpl extends AbstractBaseEntityOperation<DealStandardEntity> implements DealStandardService {
	
	@Autowired
	private DealStandardDao dealStandardDao;
	
	@Override
	public IBaseEntityOperation<DealStandardEntity> getDao() {
		return dealStandardDao;
	}
}