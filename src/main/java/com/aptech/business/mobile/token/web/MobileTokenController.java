package com.aptech.business.mobile.token.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.business.mobile.userMobile.domain.UserMobileEntity;
import com.aptech.business.mobile.userMobile.service.UserMobileService;
import com.aptech.business.mobile.util.MobileUtil;
import com.aptech.business.mobile.util.TokenUtil;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.orm.IBaseEntityOperation;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.EncryptUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;
import com.aptech.framework.web.base.BaseController;

/** @ClassName:     MobileTokenController.java 
 * @author         changl
 * @version        V1.0  
 * @Date           2017年10月31日 上午10:47:38 
 */
@Controller
@RequestMapping("/mobile/token")
public class MobileTokenController extends BaseController<SysUserEntity>  {
	@Autowired
	private SysUserService  userService;
	@Autowired
	private UserMobileService  userMobileService;
	@Override
	public IBaseEntityOperation<SysUserEntity> getService() {
		return userService;
	}
	/**
	 * @Description:   获取Token
	 * @author         changl 
	 * @Date           2017年10月27日 上午10:29:41 
	 * @throws         Exception
	 */
	@RequestMapping(value = "/getToken")
	public  @ResponseBody String getToken(HttpServletRequest request) throws Exception {
		String accessCode = request.getParameter("accessCode");
		//解密
		accessCode=MobileUtil.decodeString(accessCode);
		String[] arr = accessCode.split("##");
		Map<String,Object> errorMap = new HashMap<String,Object>();
		String loginid = arr[0];
		String pwd = arr[1];
		String equipment = arr[2];
		List<Condition> conditions = new ArrayList<Condition>();
		if(StringUtil.isNotEmpty(loginid)){
			conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,loginid));
		}
		if(StringUtil.isNotEmpty(pwd)){
			pwd = new BigInteger(EncryptUtil.encryptSHA(pwd.getBytes())).toString(32);
			conditions.add(new Condition("C_PASSWORD", FieldTypeEnum.STRING, MatchTypeEnum.EQ,pwd));
		}
		List<SysUserEntity> list =  userService.findByCondition(conditions, null);
		if(list!=null && list.size()==0){
			errorMap.put("statusCode", "300");
			errorMap.put("message", "用户名或密码无效");
			errorMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(errorMap));
		}else{
			conditions.clear();
			if(StringUtil.isNotEmpty(equipment)){
				conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.NE,loginid));
				conditions.add(new Condition("C_MOBILE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.EQ,equipment));
			}
			List<UserMobileEntity> list2 =  userMobileService.findByCondition(conditions, null);
			
			if(list2!=null && list2.size()>0){
				errorMap.put("statusCode", "303");
				errorMap.put("message", "您的设备已被其他用户绑定，请查证");
				errorMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));

				return MobileUtil.StringEncryptDes(JsonUtil.toJson(errorMap));
			}
			conditions.clear();
			conditions.add(new Condition("C_LOGIN_NAME", FieldTypeEnum.STRING, MatchTypeEnum.EQ,loginid));
			conditions.add(new Condition("C_MOBILE_FLAG", FieldTypeEnum.STRING, MatchTypeEnum.NE,equipment));
			List<UserMobileEntity> list3 =  userMobileService.findByCondition(conditions, null);
			if(list3!=null && list3.size()>0){
					errorMap.put("statusCode", "302");
					errorMap.put("message", "用户已绑定其他设备，请查证");
					errorMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					return MobileUtil.StringEncryptDes(JsonUtil.toJson(errorMap));
			}
			String token = RandomStringUtils.randomAlphanumeric(64);    

			Map<String, SysUserEntity> map = new HashMap<String,SysUserEntity>();
			SysUserEntity userEntity= list.get(0);
			userEntity.setLastLoginTime(new Date());
			map.put(token,userEntity);
			TokenUtil.addUserEntity(token, map);
			errorMap.put("message", "调用成功");
			errorMap.put("statusCode", "200");
			errorMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
			errorMap.put("token",token);
			RequestContext.get().setUser(userEntity);
			//绑定设备
			return MobileUtil.StringEncryptDes(JsonUtil.toJson(errorMap));
		}
	}
}
