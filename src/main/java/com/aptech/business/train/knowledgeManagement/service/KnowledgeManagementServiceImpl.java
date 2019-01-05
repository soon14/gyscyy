package com.aptech.business.train.knowledgeManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.train.knowledgeManagement.dao.KnowledgeManagementDao;
import com.aptech.business.train.knowledgeManagement.domain.KnowledgeManagementEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 知识库管理应用管理服务实现类
 *
 * @author 
 * @created 2018-03-26 13:43:00
 * @lastModified 
 * @history
 *
 */
@Service("knowledgeManagementService")
@Transactional
public class KnowledgeManagementServiceImpl extends AbstractBaseEntityOperation<KnowledgeManagementEntity> implements KnowledgeManagementService {
	
	@Autowired
	private KnowledgeManagementDao knowledgeManagementDao;
	
	@Override
	public IBaseEntityOperation<KnowledgeManagementEntity> getDao() {
		return knowledgeManagementDao;
	}
}