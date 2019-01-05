package com.aptech.business.overhaul.overhaulLog.dao;

import java.util.List;

import com.aptech.business.overhaul.overhaulLog.domain.OverhaulLogEntity;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;

/**
 * 
 * 检修日志应用数据接口
 *
 * @author 
 * @created 2017-06-30 18:28:04
 * @lastModified 
 * @history
 *
 */
public interface OverhaulLogDao  extends IBaseEntityOperation<OverhaulLogEntity>{
	List<OverhaulLogEntity> findByDate(List<Condition> conditions, Page<OverhaulLogEntity> page);
	public void setFlag(boolean flag);
}