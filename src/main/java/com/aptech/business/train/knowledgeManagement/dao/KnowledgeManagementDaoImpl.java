package com.aptech.business.train.knowledgeManagement.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.train.knowledgeManagement.domain.KnowledgeManagementEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 知识库管理应用数据类
 *
 * @author 
 * @created 2018-03-26 13:43:00
 * @lastModified 
 * @history
 *
 */
@Repository("knowledgeManagementDao")
public class KnowledgeManagementDaoImpl extends AncestorDao<KnowledgeManagementEntity> implements KnowledgeManagementDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.knowledgeManagement";
	}
}
