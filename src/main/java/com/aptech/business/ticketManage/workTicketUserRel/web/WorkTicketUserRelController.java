package com.aptech.business.ticketManage.workTicketUserRel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.business.ticketManage.workTicketUserRel.domain.WorkTicketUserRelEntity;
import com.aptech.business.ticketManage.workTicketUserRel.service.WorkTicketUserRelService;
import com.aptech.common.web.base.BaseController;
import com.aptech.framework.orm.IBaseEntityOperation;

/**
 * 
 * 三种人controller
 *
 * @author 
 * @created 2017-06-05 17:12:46
 * @lastModified 
 * @history
 *
 */
@Controller
@RequestMapping("/workTicketUserRel")
public class WorkTicketUserRelController extends BaseController<WorkTicketUserRelEntity> {
	
	@Autowired
	private WorkTicketUserRelService workTicketUserRelService;
	public IBaseEntityOperation<WorkTicketUserRelEntity> getService() {
		return workTicketUserRelService;
	}
	
}