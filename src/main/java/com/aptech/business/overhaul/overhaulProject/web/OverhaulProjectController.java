package com.aptech.business.overhaul.overhaulProject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.business.overhaul.overhaulProject.domain.OverhaulProjectEntity;
import com.aptech.business.overhaul.overhaulProject.service.OverhaulProjectService;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 检修项目配置控制器
 *
 * @author 
 * @created 2017-06-12 18:48:28
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/overhaulProject")
public class OverhaulProjectController extends BaseController<OverhaulProjectEntity> {
	
	@Autowired
	private OverhaulProjectService overhaulProjectService;
	
	@Override
	public IBaseEntityOperation<OverhaulProjectEntity> getService() {
		return overhaulProjectService;
	}
	
}