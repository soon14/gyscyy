package com.aptech.business.run.workStep.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.run.workStep.domain.WorkStepEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作步骤应用数据类
 *
 * @author 
 * @created 2017-06-02 11:28:16
 * @lastModified 
 * @history
 *
 */
@Repository("workStepDao")
public class WorkStepDaoImpl extends AncestorDao<WorkStepEntity> implements WorkStepDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.workStep";
	}
}
