package com.aptech.business.defectManage.defect.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.defect.domain.DefectEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * ȱ�ݹ���Ӧ�������
 *
 * @author 
 * @created 2017-06-02 13:18:00
 * @lastModified 
 * @history
 *
 */
@Repository("defectDao")
public class DefectDaoImpl extends AncestorDao<DefectEntity> implements DefectDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defect";
	}
}
