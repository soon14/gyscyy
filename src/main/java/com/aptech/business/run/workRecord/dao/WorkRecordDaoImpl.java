package com.aptech.business.run.workRecord.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.workRecord.domain.WorkRecordEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 定期工作记录应用数据类
 *
 * @author 
 * @created 2017-06-01 15:08:52
 * @lastModified 
 * @history
 *
 */
@Repository("workRecordDao")
public class WorkRecordDaoImpl extends AncestorDao<WorkRecordEntity> implements WorkRecordDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workRecord";
	}
}
