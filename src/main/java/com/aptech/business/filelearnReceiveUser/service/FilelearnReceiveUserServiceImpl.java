package com.aptech.business.filelearnReceiveUser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.fileLearnAttachment.domain.FileLearnAttachmentEntity;
import com.aptech.business.filelearnReceiveUser.dao.FilelearnReceiveUserDao;
import com.aptech.business.filelearnReceiveUser.domain.FilelearnReceiveUserEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 文件学习接受人应用管理服务实现类
 *
 * @author 
 * @created 2018-04-10 16:34:02
 * @lastModified 
 * @history
 *
 */
@Service("filelearnReceiveUserService")
@Transactional
public class FilelearnReceiveUserServiceImpl extends AbstractBaseEntityOperation<FilelearnReceiveUserEntity> implements FilelearnReceiveUserService {
	
	@Autowired
	private FilelearnReceiveUserDao filelearnReceiveUserDao;
	
	@Override
	public IBaseEntityOperation<FilelearnReceiveUserEntity> getDao() {
		return filelearnReceiveUserDao;
	}
	public void batchInsert(List<FilelearnReceiveUserEntity> list){
		for(FilelearnReceiveUserEntity entity:list){
			filelearnReceiveUserDao.addEntity(entity);
		}
	}
	public int deleteByFileId(Long id){
		return filelearnReceiveUserDao.deleteByFileId(id);
	}
}