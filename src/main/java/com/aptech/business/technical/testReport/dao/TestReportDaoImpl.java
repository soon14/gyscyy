package com.aptech.business.technical.testReport.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.technical.testReport.domain.TestReportEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 试验报告应用数据类
 *
 * @author 
 * @created 2018-09-05 14:35:08
 * @lastModified 
 * @history
 *
 */
@Repository("testReportDao")
public class TestReportDaoImpl extends AncestorDao<TestReportEntity> implements TestReportDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.testReport";
	}
}
