package com.aptech.business.safeManage.upperFile.service;

import com.aptech.business.safeManage.upperFile.domain.UpperFileEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 上级文件应用管理服务接口
 *
 * @author 
 * @created 2018-03-28 10:18:07
 * @lastModified 
 * @history
 *
 */
public interface UpperFileService  extends IBaseEntityOperation<UpperFileEntity> {
	ResultObj add(UpperFileEntity t);
}