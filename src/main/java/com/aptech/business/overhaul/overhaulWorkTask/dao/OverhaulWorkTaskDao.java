package com.aptech.business.overhaul.overhaulWorkTask.dao;

import com.aptech.business.overhaul.overhaulWorkTask.domain.OverhaulWorkTaskEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修工作任务应用数据接口
 *
 * @author 
 * @created 2017-12-22 10:15:21
 * @lastModified 
 * @history
 *
 */
public interface OverhaulWorkTaskDao  extends IBaseEntityOperation<OverhaulWorkTaskEntity>{
	public void setFlag(boolean flag);
}