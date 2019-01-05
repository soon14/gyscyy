package com.aptech.business.mobile.todoTask.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.mobile.util.MobileUtil;
import com.aptech.business.mobile.util.TokenUtil;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.web.base.BaseController;
import com.aptech.common.workflow.modelEditor.service.NodeConfigService;
import com.aptech.common.workflow.todoTask.domain.TodoTaskEntity;
import com.aptech.common.workflow.todoTask.service.TodoTaskService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.Page;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;



/**
 * @ClassName: TodoTackController.java
 * @author changl
 * @version V1.0
 * @param <O>
 * @Date 2017年4月11日 下午1:30:47
 */
@Controller
@RequestMapping("/mobile/todoTask")
public class TodoTaskMobileController<O> extends BaseController<TodoTaskEntity>{
	@Autowired
	private TodoTaskService todoTaskService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private NodeConfigService nodeConfigService;
	@Override
	public IBaseEntityOperation<TodoTaskEntity> getService() {
		return todoTaskService;
	}
	/**
	 * @Description:   代办列表
	 * @author         changl 
	 * @Date           2017年10月27日 上午10:29:41 
	 * @throws         Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getWorkList")
	public @ResponseBody String getWorkList(HttpServletRequest request) throws Exception {
		//解密
		String token = request.getParameter("token");
		token=MobileUtil.decodeString(token);
		String pageSize = request.getParameter("pageSize");
		pageSize=MobileUtil.decodeString(pageSize);
		String currentPage = request.getParameter("currentPage");
		currentPage=MobileUtil.decodeString(currentPage);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		SysUserEntity userEntity= user.get(token);
		//查询代办列表
		RequestContext.get().setUser(userEntity);
		Map<String, Object> params=new HashMap<String, Object>();
		List<Condition> conditions=new ArrayList<Condition>();
		params.put("order",new ArrayList<Map<String, Object>>());
		params.put("conditions", conditions);
		
		Page<O> page = new Page<O>();
		if(StringUtil.isEmpty(pageSize)){
			page.setPageSize(Integer.MAX_VALUE);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		if(StringUtil.isEmpty(currentPage)){
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(Integer.parseInt(currentPage));
		}
		List<TodoTaskEntity> obj=(List<TodoTaskEntity>) todoTaskService.findByCondition(params, page);
		resultMap.put("statusCode", "200");
		resultMap.put("message", "调用成功");
		resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
		Map<String, Object> objMap=new HashMap<String, Object>();
		objMap.put("workList", obj);
		resultMap.put("obj",objMap);
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
}
