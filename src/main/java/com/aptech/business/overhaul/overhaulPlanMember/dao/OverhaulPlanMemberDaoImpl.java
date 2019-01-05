package com.aptech.business.overhaul.overhaulPlanMember.dao;

import org.springframework.stereotype.Repository;

import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.framework.orm.AncestorDao;

/**
 * 
 * 检修计划人员应用数据类
 *
 * @author 
 * @created 2018-03-22 10:43:15
 * @lastModified 
 * @history
 *
 */
@Repository("overhaulPlanMemberDao")
public class OverhaulPlanMemberDaoImpl extends AncestorDao<OverhaulPlanMemberEntity> implements OverhaulPlanMemberDao{
	
	@Override
	public String getNameSpace() {
		// TODO Auto-generated method stub
		return "com.aptech.business.overhaulPlanMember";
	}
}
