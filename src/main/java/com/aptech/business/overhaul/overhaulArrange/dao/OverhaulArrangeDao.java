package com.aptech.business.overhaul.overhaulArrange.dao;

import com.aptech.business.overhaul.overhaulArrange.domain.OverhaulArrangeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 工作安排应用数据接口
 *
 * @author 
 * @created 2017-12-20 16:24:01
 * @lastModified 
 * @history
 *
 */
public interface OverhaulArrangeDao  extends IBaseEntityOperation<OverhaulArrangeEntity>{
	public void setFlag(boolean flag);
}