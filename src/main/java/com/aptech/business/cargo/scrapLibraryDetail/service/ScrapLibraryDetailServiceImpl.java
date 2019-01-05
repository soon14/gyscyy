package com.aptech.business.cargo.scrapLibraryDetail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.cargo.scrapLibraryDetail.dao.ScrapLibraryDetailDao;
import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 报废库明细应用管理服务实现类
 *
 * @author 
 * @created 2018-03-16 19:29:22
 * @lastModified 
 * @history
 *
 */
@Service("scrapLibraryDetailService")
@Transactional
public class ScrapLibraryDetailServiceImpl extends AbstractBaseEntityOperation<ScrapLibraryDetailEntity> implements ScrapLibraryDetailService {
	
	@Autowired
	private ScrapLibraryDetailDao scrapLibraryDetailDao;
	
	@Override
	public IBaseEntityOperation<ScrapLibraryDetailEntity> getDao() {
		return scrapLibraryDetailDao;
	}
}