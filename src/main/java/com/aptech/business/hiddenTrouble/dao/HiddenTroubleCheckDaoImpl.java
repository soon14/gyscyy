package com.aptech.business.hiddenTrouble.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.hiddenTrouble.domain.HiddenTroubleCheckEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 隐患排查台账应用数据类
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Repository("hiddenTroubleCheckDao")
public class HiddenTroubleCheckDaoImpl extends AncestorDao<HiddenTroubleCheckEntity> implements HiddenTroubleCheckDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.hiddenTroubleCheck";
	}
}
