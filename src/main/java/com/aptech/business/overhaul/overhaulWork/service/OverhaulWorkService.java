package com.aptech.business.overhaul.overhaulWork.service;

import com.aptech.business.overhaul.overhaulWork.domain.OverhaulWorkEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修工作应用管理服务接口
 *
 * @author 
 * @created 2017-08-11 09:27:00
 * @lastModified 
 * @history
 *
 */
public interface OverhaulWorkService  extends IBaseEntityOperation<OverhaulWorkEntity> {
	public void deleteEntityByLogId(long overhaulLogId);

}