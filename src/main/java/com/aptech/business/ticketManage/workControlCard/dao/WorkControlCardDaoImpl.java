package com.aptech.business.ticketManage.workControlCard.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.ticketManage.workControlCard.domain.WorkControlCardEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 工作票危险因素控制卡应用数据类
 *
 * @author 
 * @created 2017-06-05 17:12:09
 * @lastModified 
 * @history
 *
 */
@Repository("workControlCardDao")
public class WorkControlCardDaoImpl extends AncestorDao<WorkControlCardEntity> implements WorkControlCardDao{
	
	@Override
	public String getNameSpace() {
		return "com.aptech.business.workControlCard";
	}
}
