package com.aptech.business.cargo.scrapLibraryDetailOut.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.scrapLibraryDetailOut.domain.ScrapLibraryDetailOutEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 报废申请外库物资明细应用数据类
 *
 * @author 
 * @created 2018-08-22 16:54:34
 * @lastModified 
 * @history
 *
 */
@Repository("scrapLibraryDetailOutDao")
public class ScrapLibraryDetailOutDaoImpl extends AncestorDao<ScrapLibraryDetailOutEntity> implements ScrapLibraryDetailOutDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.scrapLibraryDetailOut";
	}
}
