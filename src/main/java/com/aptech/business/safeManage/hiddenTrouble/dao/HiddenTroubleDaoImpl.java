package com.aptech.business.safeManage.hiddenTrouble.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.hiddenTrouble.domain.HiddenTroubleEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 隐患排查应用数据类
 *
 * @author 
 * @created 2018-03-31 12:52:23
 * @lastModified 
 * @history
 *
 */
@Repository("hiddenTroubleDao")
public class HiddenTroubleDaoImpl extends AncestorDao<HiddenTroubleEntity> implements HiddenTroubleDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.hiddenTrouble";
	}
}
