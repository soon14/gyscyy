package com.aptech.business.cargo.scrapLibraryDetailOut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.scrapLibraryDetailOut.dao.ScrapLibraryDetailOutDao;
import com.aptech.business.cargo.scrapLibraryDetailOut.domain.ScrapLibraryDetailOutEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 报废申请外库物资明细应用管理服务实现类
 *
 * @author 
 * @created 2018-08-22 16:54:34
 * @lastModified 
 * @history
 *
 */
@Service("scrapLibraryDetailOutService")
@Transactional
public class ScrapLibraryDetailOutServiceImpl extends AbstractBaseEntityOperation<ScrapLibraryDetailOutEntity> implements ScrapLibraryDetailOutService {
	
	@Autowired
	private ScrapLibraryDetailOutDao scrapLibraryDetailOutDao;
	
	@Override
	public IBaseEntityOperation<ScrapLibraryDetailOutEntity> getDao() {
		return scrapLibraryDetailOutDao;
	}
}