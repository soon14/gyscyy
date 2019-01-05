package com.aptech.business.technical.technicalExchange.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.technicalExchange.domain.TechnicalExchangeEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术交流应用数据类
 *
 * @author 
 * @created 2018-09-17 10:37:44
 * @lastModified 
 * @history
 *
 */
@Repository("technicalExchangeDao")
public class TechnicalExchangeDaoImpl extends AncestorDao<TechnicalExchangeEntity> implements TechnicalExchangeDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.technicalExchange";
	}
}
