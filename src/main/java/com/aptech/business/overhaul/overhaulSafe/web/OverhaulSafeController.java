package com.aptech.business.overhaul.overhaulSafe.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.business.overhaul.overhaulSafe.domain.OverhaulSafeEntity;
import com.aptech.business.overhaul.overhaulSafe.service.OverhaulSafeService;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 安全交底配置控制器
 *
 * @author 
 * @created 2017-08-11 09:28:01
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulSafe")
public class OverhaulSafeController extends BaseController<OverhaulSafeEntity> {
	
	@Autowired
	private OverhaulSafeService overhaulSafeService;
	
	@Override
	public IBaseEntityOperation<OverhaulSafeEntity> getService() {
		return overhaulSafeService;
	}
	
}