package com.aptech.business.cargo.scrapLibraryDetail.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.scrapLibraryDetail.domain.ScrapLibraryDetailEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 报废库明细应用数据类
 *
 * @author 
 * @created 2018-03-16 19:29:22
 * @lastModified 
 * @history
 *
 */
@Repository("scrapLibraryDetailDao")
public class ScrapLibraryDetailDaoImpl extends AncestorDao<ScrapLibraryDetailEntity> implements ScrapLibraryDetailDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.scrapLibraryDetail";
	}
}
