package com.aptech.business.personalManage.service;



import com.aptech.business.personalManage.domain.PersonalManageEntity;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 人员管理应用管理服务接口
 *
 * @author 
 * @created 2017-10-19 17:34:22
 * @lastModified 
 * @history
 *
 */
public interface PersonalManageService  extends IBaseEntityOperation<PersonalManageEntity> {
	public PersonalManageEntity AddPersonalUser(PersonalManageEntity t);
}