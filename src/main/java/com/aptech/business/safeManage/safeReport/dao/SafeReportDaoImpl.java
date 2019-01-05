package com.aptech.business.safeManage.safeReport.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.safeReport.domain.SafeReportEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 安全月报应用数据类
 *
 * @author 
 * @created 2018-03-28 11:18:00
 * @lastModified 
 * @history
 *
 */
@Repository("safeReportDao")
public class SafeReportDaoImpl extends AncestorDao<SafeReportEntity> implements SafeReportDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeReport";
	}
}
