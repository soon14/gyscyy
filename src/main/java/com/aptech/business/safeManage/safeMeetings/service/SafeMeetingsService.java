package com.aptech.business.safeManage.safeMeetings.service;

import com.aptech.business.safeManage.safeMeetings.domain.SafeMeetingsEntity;
import com.aptech.business.safeManage.safeSending.domain.SafeSendingEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.web.base.ResultObj;

/**
 * 
 * 安全会议应用管理服务接口
 *
 * @author 
 * @created 2018-03-29 10:03:15
 * @lastModified 
 * @history
 *
 */
public interface SafeMeetingsService  extends IBaseEntityOperation<SafeMeetingsEntity> {
	ResultObj add(SafeMeetingsEntity safeMeetingsEntity);
	ResultObj update(SafeMeetingsEntity safeMeetingsEntity);
}