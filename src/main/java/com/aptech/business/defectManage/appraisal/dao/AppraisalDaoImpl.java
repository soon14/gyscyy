package com.aptech.business.defectManage.appraisal.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.appraisal.domain.AppraisalEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * ȱ�ݼ�Ӧ�������
 *
 * @author 
 * @created 2017-06-05 15:58:43
 * @lastModified 
 * @history
 *
 */
@Repository("appraisalDao")
public class AppraisalDaoImpl extends AncestorDao<AppraisalEntity> implements AppraisalDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectManage.appraisal";
	}
}
