package com.aptech.business.overhaul.overhaulPlanMember.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.overhaul.overhaulPlanMember.dao.OverhaulPlanMemberDao;
import com.aptech.business.overhaul.overhaulPlanMember.domain.OverhaulPlanMemberEntity;
import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修计划人员应用管理服务实现类
 *
 * @author 
 * @created 2018-03-22 10:43:15
 * @lastModified 
 * @history
 *
 */
@Service("overhaulPlanMemberService")
@Transactional
public class OverhaulPlanMemberServiceImpl extends AbstractBaseEntityOperation<OverhaulPlanMemberEntity> implements OverhaulPlanMemberService {
	
	@Autowired
	private OverhaulPlanMemberDao overhaulPlanMemberDao;
	
	@Override
	public IBaseEntityOperation<OverhaulPlanMemberEntity> getDao() {
		return overhaulPlanMemberDao;
	}

}