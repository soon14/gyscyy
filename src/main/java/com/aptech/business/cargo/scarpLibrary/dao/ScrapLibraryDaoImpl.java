package com.aptech.business.cargo.scarpLibrary.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.cargo.scarpLibrary.domain.ScrapLibraryEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 报废库管理应用数据类
 *
 * @author 
 * @created 2018-03-15 15:37:32
 * @lastModified 
 * @history
 *
 */
@Repository("scrapLibraryDao")
public class ScrapLibraryDaoImpl extends AncestorDao<ScrapLibraryEntity> implements ScrapLibraryDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.scarpLibrary";
	}
}
