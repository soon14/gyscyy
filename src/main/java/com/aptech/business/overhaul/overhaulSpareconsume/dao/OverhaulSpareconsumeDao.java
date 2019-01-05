package com.aptech.business.overhaul.overhaulSpareconsume.dao;

import com.aptech.business.overhaul.overhaulSpareconsume.domain.OverhaulSpareconsumeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 备件消耗应用数据接口
 *
 * @author 
 * @created 2018-03-15 10:23:12
 * @lastModified 
 * @history
 *
 */
public interface OverhaulSpareconsumeDao  extends IBaseEntityOperation<OverhaulSpareconsumeEntity>{
	public void setFlag(boolean flag);
}