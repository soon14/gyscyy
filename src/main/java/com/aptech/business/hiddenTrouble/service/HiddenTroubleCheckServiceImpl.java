package com.aptech.business.hiddenTrouble.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.business.hiddenTrouble.dao.HiddenTroubleCheckDao;
import com.aptech.business.hiddenTrouble.domain.HiddenTroubleCheckEntity;
import com.aptech.framework.orm.AbstractBaseEntityOperation;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 隐患排查台账应用管理服务实现类
 *
 * @author 
 * @created 2018-09-03 10:31:57
 * @lastModified 
 * @history
 *
 */
@Service("hiddenTroubleCheckService")
@Transactional
public class HiddenTroubleCheckServiceImpl extends AbstractBaseEntityOperation<HiddenTroubleCheckEntity> implements HiddenTroubleCheckService {
	
	@Autowired
	private HiddenTroubleCheckDao hiddenTroubleCheckDao;
	
	@Override
	public IBaseEntityOperation<HiddenTroubleCheckEntity> getDao() {
		return hiddenTroubleCheckDao;
	}
}