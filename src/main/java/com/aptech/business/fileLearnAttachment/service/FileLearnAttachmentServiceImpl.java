package com.aptech.business.fileLearnAttachment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.fileLearnAttachment.dao.FileLearnAttachmentDao;
import com.aptech.business.fileLearnAttachment.domain.FileLearnAttachmentEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 文件学习附件应用管理服务实现类
 *
 * @author 
 * @created 2018-04-10 16:34:05
 * @lastModified 
 * @history
 *
 */
@Service("fileLearnAttachmentService")
@Transactional
public class FileLearnAttachmentServiceImpl extends AbstractBaseEntityOperation<FileLearnAttachmentEntity> implements FileLearnAttachmentService {
	
	@Autowired
	private FileLearnAttachmentDao fileLearnAttachmentDao;
	
	@Override
	public IBaseEntityOperation<FileLearnAttachmentEntity> getDao() {
		return fileLearnAttachmentDao;
	}
	public void batchInsert(List<FileLearnAttachmentEntity> list){
		for(FileLearnAttachmentEntity entity:list){
			fileLearnAttachmentDao.addEntity(entity);
		}
	}
}