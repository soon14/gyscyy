package com.aptech.business.fileLearnAttachment.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.fileLearnAttachment.domain.FileLearnAttachmentEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 文件学习附件应用数据类
 *
 * @author 
 * @created 2018-04-10 16:34:05
 * @lastModified 
 * @history
 *
 */
@Repository("fileLearnAttachmentDao")
public class FileLearnAttachmentDaoImpl extends AncestorDao<FileLearnAttachmentEntity> implements FileLearnAttachmentDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.fileLearnAttachment";
	}
}
