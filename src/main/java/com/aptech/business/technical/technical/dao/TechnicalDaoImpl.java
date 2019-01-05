package com.aptech.business.technical.technical.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.technical.domain.TechnicalEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术监督应用数据类
 *
 * @author 
 * @created 2017-11-13 16:15:05
 * @lastModified 
 * @history
 *
 */
@Repository("technicalDao")
public class TechnicalDaoImpl extends AncestorDao<TechnicalEntity> implements TechnicalDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.technical.technical";
	}
}
