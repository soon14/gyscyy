package com.aptech.business.safeManage.upperFile.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.upperFile.domain.UpperFileEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 上级文件应用数据类
 *
 * @author 
 * @created 2018-03-28 10:18:07
 * @lastModified 
 * @history
 *
 */
@Repository("upperFileDao")
public class UpperFileDaoImpl extends AncestorDao<UpperFileEntity> implements UpperFileDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.upperFile";
	}
}
