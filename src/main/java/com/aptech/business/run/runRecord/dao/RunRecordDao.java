package com.aptech.business.run.runRecord.dao;

import com.aptech.business.run.runRecord.domain.RunRecordEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 运行记事应用数据接口
 *
 * @author 
 * @created 2017-06-05 15:28:10
 * @lastModified 
 * @history
 *
 */
public interface RunRecordDao  extends IBaseEntityOperation<RunRecordEntity>{
	public void setFlag(boolean flag);
}