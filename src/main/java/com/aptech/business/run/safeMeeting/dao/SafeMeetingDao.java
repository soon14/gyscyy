package com.aptech.business.run.safeMeeting.dao;

import com.aptech.business.run.safeMeeting.domain.SafeMeetingEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全例会应用数据接口
 *
 * @author 
 * @created 2017-06-07 16:00:28
 * @lastModified 
 * @history
 *
 */
public interface SafeMeetingDao  extends IBaseEntityOperation<SafeMeetingEntity>{
	public void setFlag(boolean flag);
}