package com.aptech.business.safeCheckProblem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.safeCheckProblem.dao.SafeCheckProblemDao;
import com.aptech.business.safeCheckProblem.domain.SafeCheckProblemEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全检查问题应用管理服务实现类
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Service("safeCheckProblemService")
@Transactional
public class SafeCheckProblemServiceImpl extends AbstractBaseEntityOperation<SafeCheckProblemEntity> implements SafeCheckProblemService {
	
	@Autowired
	private SafeCheckProblemDao safeCheckProblemDao;
	
	@Override
	public IBaseEntityOperation<SafeCheckProblemEntity> getDao() {
		return safeCheckProblemDao;
	}
}