package com.aptech.business.filelearnReceiveUser.service;

import java.util.List;

import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 文件学习接受人应用管理服务接口
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
public interface FilelearnReceiveUserService  extends IBaseEntityOperation<FilelearnReceiveUserEntity> {
	public void batchInsert(List<FilelearnReceiveUserEntity> list);
	public int deleteByFileId(Long id);
}