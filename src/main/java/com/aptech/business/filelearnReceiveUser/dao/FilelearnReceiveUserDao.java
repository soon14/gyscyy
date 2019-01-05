package com.aptech.business.filelearnReceiveUser.dao;

import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 文件学习接受人应用数据接口
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
public interface FilelearnReceiveUserDao  extends IBaseEntityOperation<FilelearnReceiveUserEntity>{
	
	public int deleteByFileId(Long fileId);
	
}