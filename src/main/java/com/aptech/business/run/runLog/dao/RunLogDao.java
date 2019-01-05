package com.aptech.business.run.runLog.dao;

import com.aptech.business.run.runLog.domain.RunLogEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 运行日志应用数据接口
 *
 * @author 
 * @created 2017-06-05 10:52:45
 * @lastModified 
 * @history
 *
 */
public interface RunLogDao  extends IBaseEntityOperation<RunLogEntity>{
	public void setFlag(boolean flag);
}