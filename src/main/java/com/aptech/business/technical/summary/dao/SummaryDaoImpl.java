package com.aptech.business.technical.summary.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.summary.domain.SummaryEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 技术监督总结应用数据类
 *
 * @author 
 * @created 2018-03-14 14:02:22
 * @lastModified 
 * @history
 *
 */
@Repository("summaryDao")
public class SummaryDaoImpl extends AncestorDao<SummaryEntity> implements SummaryDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.technical.summary";
	}
}
