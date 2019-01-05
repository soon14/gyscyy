package com.aptech.business.overhaul.overhaulLogDetail.dao;

import com.aptech.business.overhaul.overhaulLogDetail.domain.OverhaulLogDetailEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修日志明细应用数据接口
 *
 * @author 
 * @created 2018-01-04 10:56:15
 * @lastModified 
 * @history
 *
 */
public interface OverhaulLogDetailDao  extends IBaseEntityOperation<OverhaulLogDetailEntity>{
	public void setFlag(boolean flag);
}