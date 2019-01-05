package com.aptech.business.defectManage.solve.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.defectManage.solve.domain.SolveEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * ȱ�ݴ���Ӧ�������
 *
 * @author 
 * @created 2017-06-05 15:58:55
 * @lastModified 
 * @history
 *
 */
@Repository("solveDao")
public class SolveDaoImpl extends AncestorDao<SolveEntity> implements SolveDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.defectManage.solve";
	}
}
