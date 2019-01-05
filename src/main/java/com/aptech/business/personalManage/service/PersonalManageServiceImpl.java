package com.aptech.business.personalManage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import com.aptech.business.personalManage.dao.PersonalManageDao;
import com.aptech.business.personalManage.domain.PersonalManageEntity;
import com.aptech.common.system.user.dao.SysUserDao;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 人员管理应用管理服务实现类
 *
 * @author 
 * @created 2017-08-24 13:51:46
 * @lastModified 
 * @history
 *
 */
@Service("personalManageService")
@Transactional
public class PersonalManageServiceImpl extends AbstractBaseEntityOperation<PersonalManageEntity> implements PersonalManageService {
	@Autowired
	private PersonalManageDao personalManageDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserDao sysUserDao;
	@Override
	public IBaseEntityOperation<PersonalManageEntity> getDao() {
		return personalManageDao;
	}
	@Override
	public PersonalManageEntity AddPersonalUser(PersonalManageEntity t){
		t.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
		personalManageDao.addEntity(t);
		if (t.getAssociateUser()!=null && t.getAssociateUser() == 1) {
			SysUserEntity userEntity = new SysUserEntity();
			userEntity.setUnitId(Long.valueOf(t.getUnit()));
			userEntity.setLoginName(t.getCode());
			userEntity.setName(t.getName());
			userEntity.setSex(t.getSex());
			userEntity.setMobile(t.getMobile());
			userEntity.setMail(t.getMail());
			userEntity.setRoleIds("0");
			userEntity.setStatus(String.valueOf(DataStatusEnum.NORMAL.ordinal()));
			userEntity.setPassword("123456");
			sysUserService.addEntity(userEntity);
		}
		return null;
		
	}

}