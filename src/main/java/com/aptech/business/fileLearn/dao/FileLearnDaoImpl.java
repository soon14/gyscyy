package com.aptech.business.fileLearn.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.fileLearn.domain.FileLearnEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 文件学习应用数据类
 *
 * @author 
 * @created 2018-04-02 16:50:12
 * @lastModified 
 * @history
 *
 */
@Repository("fileLearnDao")
public class FileLearnDaoImpl extends AncestorDao<FileLearnEntity> implements FileLearnDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.fileLearn";
	}

}
