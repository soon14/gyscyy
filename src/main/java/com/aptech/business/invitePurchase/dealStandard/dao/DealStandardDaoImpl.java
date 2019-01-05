package com.aptech.business.invitePurchase.dealStandard.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.invitePurchase.dealStandard.domain.DealStandardEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 定标请示函应用数据类
 *
 * @author 
 * @created 2018-09-10 09:10:10
 * @lastModified 
 * @history
 *
 */
@Repository("dealStandardDao")
public class DealStandardDaoImpl extends AncestorDao<DealStandardEntity> implements DealStandardDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.dealStandard";
	}
}
