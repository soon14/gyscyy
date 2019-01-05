package com.aptech.business.mobile.login.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.mobile.util.MobileUtil;
import com.aptech.business.mobile.util.TokenUtil;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.EncryptUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.BaseController;

@Controller
@RequestMapping("/mobile/login")
public class MobileLoginController extends BaseController<SysUserEntity> {
	@Autowired
	private SysUserService  userService;
	@Override
	public IBaseEntityOperation<SysUserEntity> getService() {
		return userService;
	}
	/**
	 * @Description:   登入
	 * @author         changl 
	 * @Date           2017年10月27日 上午10:29:41 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/login")
	public @ResponseBody String login(HttpServletRequest request) throws Exception {
		String token = request.getParameter("token");
		//解密
		token=MobileUtil.decodeString(token);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		resultMap.put("statusCode", "200");
		resultMap.put("message", "登录成功");
		resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
	/**
	 * @Description:   修改密码
	 * @author         changl 
	 * @Date           2017年10月27日 上午10:29:41 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/pswChanged")
	public @ResponseBody String pswChanged(HttpServletRequest request) throws Exception {
		//解密
		String token = request.getParameter("token");
		token=MobileUtil.decodeString(token);
		String oldPsw = request.getParameter("oldPsw");
		oldPsw=MobileUtil.decodeString(oldPsw);
		String newPsw = request.getParameter("newPsw");
		newPsw=MobileUtil.decodeString(newPsw);
		Map<String, Object> resultMap = new Hashtable<String, Object>();
		Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
		SysUserEntity userEntity=user.get(token);
		String loginid=userEntity.getLoginName();
		List<Condition> conditions = new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(loginid)){
			conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,loginid));
		}
		if(StringUtil.isNotEmpty(oldPsw)){
			oldPsw = new BigInteger(EncryptUtil.encryptSHA(oldPsw.getBytes())).toString();
			conditions.add(new Condition("C_PASSWORD", FieldTypeEnum.STRING, MatchTypeEnum.EQ,oldPsw));
		}
		List<SysUserEntity> list =  userService.findByCondition(conditions, null);
		if(list!=null && list.size()==0){
			resultMap.put("statusCode", "300");
			resultMap.put("message", "用户名或密码无效");
			resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
		}
		//修改密码
		userService.resetPassword(userEntity.getId(), newPsw);
		resultMap.put("statusCode", "200");
		resultMap.put("message", "调用成功");
		resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap));
	}
}
