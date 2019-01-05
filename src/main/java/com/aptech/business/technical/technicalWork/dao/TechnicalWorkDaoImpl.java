package com.aptech.business.technical.technicalWork.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.technicalWork.domain.TechnicalWorkEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术监督工作表应用数据类
 *
 * @author 
 * @created 2017-11-13 16:16:04
 * @lastModified 
 * @history
 *
 */
@Repository("technicalWorkDao")
public class TechnicalWorkDaoImpl extends AncestorDao<TechnicalWorkEntity> implements TechnicalWorkDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.technical.technicalWork";
	}
}
