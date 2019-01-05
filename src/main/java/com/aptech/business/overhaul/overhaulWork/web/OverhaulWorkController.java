package com.aptech.business.overhaul.overhaulWork.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.business.overhaul.overhaulWork.domain.OverhaulWorkEntity;
import com.aptech.business.overhaul.overhaulWork.service.OverhaulWorkService;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修工作配置控制器
 *
 * @author 
 * @created 2017-08-11 09:27:00
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulWork")
public class OverhaulWorkController extends BaseController<OverhaulWorkEntity> {
	
	@Autowired
	private OverhaulWorkService overhaulWorkService;
	
	@Override
	public IBaseEntityOperation<OverhaulWorkEntity> getService() {
		return overhaulWorkService;
	}

}