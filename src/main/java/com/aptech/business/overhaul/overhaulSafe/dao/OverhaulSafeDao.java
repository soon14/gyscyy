package com.aptech.business.overhaul.overhaulSafe.dao;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全交底应用数据接口
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
public interface OverhaulSafeDao  extends IBaseEntityOperation<OverhaulSafeEntity>{
	public void setFlag(boolean flag);
}