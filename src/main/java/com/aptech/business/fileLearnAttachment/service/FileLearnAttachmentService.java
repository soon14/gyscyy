package com.aptech.business.fileLearnAttachment.service;

import java.util.List;

import com.aptech.business.fileLearnAttachment.domain.FileLearnAttachmentEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 文件学习附件应用管理服务接口
 *
 * @author 
 * @created 2018-04-10 16:34:05
 * @lastModified 
 * @history
 *
 */
public interface FileLearnAttachmentService  extends IBaseEntityOperation<FileLearnAttachmentEntity> {
	public void batchInsert(List<FileLearnAttachmentEntity> list);
	
}