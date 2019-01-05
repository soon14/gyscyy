package com.aptech.business.safeManage.infoSubmit.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.safeManage.infoSubmit.domain.InfoSubmitEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 信息报送应用数据类
 *
 * @author 
 * @created 2018-03-28 18:05:15
 * @lastModified 
 * @history
 *
 */
@Repository("infoSubmitDao")
public class InfoSubmitDaoImpl extends AncestorDao<InfoSubmitEntity> implements InfoSubmitDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.safeManage.infoSubmit";
	}
}
