package com.aptech.business.run.workStep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.run.workStep.dao.WorkStepDao;
import com.aptech.business.run.workStep.domain.WorkStepEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 工作步骤应用管理服务实现类
 *
 * @author 
 * @created 2017-06-02 11:28:16
 * @lastModified 
 * @history
 *
 */
@Service("workStepService")
@Transactional
public class WorkStepServiceImpl extends AbstractBaseEntityOperation<WorkStepEntity> implements WorkStepService {
	
	@Autowired
	private WorkStepDao workStepDao;
	
	@Override
	public IBaseEntityOperation<WorkStepEntity> getDao() {
		return workStepDao;
	}
}