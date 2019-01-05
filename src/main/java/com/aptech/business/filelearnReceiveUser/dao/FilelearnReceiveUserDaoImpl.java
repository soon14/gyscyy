package com.aptech.business.filelearnReceiveUser.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 文件学习接受人应用数据类
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
@Repository("filelearnReceiveUserDao")
public class FilelearnReceiveUserDaoImpl extends AncestorDao<FilelearnReceiveUserEntity> implements FilelearnReceiveUserDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.filelearnReceiveUser";
	}
	
	public int deleteByFileId(Long fileId){
		return this.sqlSession.delete("deleteByFileId", fileId);
	}
}
