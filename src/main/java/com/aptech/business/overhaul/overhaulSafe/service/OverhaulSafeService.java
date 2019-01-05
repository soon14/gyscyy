package com.aptech.business.overhaul.overhaulSafe.service;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全交底应用管理服务接口
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
public interface OverhaulSafeService  extends IBaseEntityOperation<OverhaulSafeEntity> {
	public void deleteEntityByLogId(long overhaulLogId);
	public void update(OverhaulSafeEntity overhaulSafeEntity);

}