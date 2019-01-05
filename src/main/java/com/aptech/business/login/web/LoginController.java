/*	
 * Web 5.0 Copyright 2016 Aptech, Co.ltd. All rights reserved.
 * 		 
 * FileName: LoginController.java
 *
 */

package com.aptech.business.login.web;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aptech.business.companyTrends.domain.CompanyTrendsEntity;
import com.aptech.business.companyTrends.service.CompanyTrendsService;
import com.aptech.business.component.dictionary.CompanyTrendsStatusEnum;
import com.aptech.business.defectManage.defect.service.DefectService;
import com.aptech.business.operation.userOperateLog.domain.OperateUserEnum;
import com.aptech.business.operation.userOperateLog.domain.OperateUserModuleEnum;
import com.aptech.business.operation.userOperateLog.service.OperateLogService;
import com.aptech.business.util.LunarUtil;
import com.aptech.common.system.config.domain.SysConfigEntity;
import com.aptech.common.system.config.service.SysConfigService;
import com.aptech.common.system.dictionary.util.DictionaryUtil;
import com.aptech.common.system.dictionary.web.SysDictionaryVO;
import com.aptech.common.system.unit.domain.SysUnitEntity;
import com.aptech.common.system.unit.domain.UnitLevelEnum;
import com.aptech.common.system.unit.service.SysUnitService;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.common.system.user.service.SysUserService;
import com.aptech.common.system.userUnit.domain.UserUnitRelEntity;
import com.aptech.common.system.userUnit.service.UserUnitRelService;
import com.aptech.framework.context.HttpContextExtractor;
import com.aptech.framework.orm.DataStatusEnum;
import com.aptech.framework.orm.search.Condition;
import com.aptech.framework.orm.search.FieldTypeEnum;
import com.aptech.framework.orm.search.MatchTypeEnum;
import com.aptech.framework.util.EncryptUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.web.base.ResultObj;

/** 
 * 登录控制器类
 *
 * @author zhangjx
 * @created 2016年11月14日 下午6:15:52 
 * @lastModified 
 * @history
 * 
 */

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
    private SysConfigService sysConfigService;
	@Autowired
    private DefectService defectService;
	@Autowired
	private CompanyTrendsService companyTrendsService;
	@Autowired
	private UserUnitRelService userUnitRelService;
	@Autowired
	private OperateLogService operateLogService;
	/**
	 * 
	 * 登录页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangjx
	 * @throws ParseException 
	 * @created 2016年11月18日 下午4:34:42
	 * @lastModified
	 */
	@RequestMapping(value="/login")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ParseException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("theme", "default");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		model.put("day", new LunarUtil(calendar).toString());
		return new ModelAndView("login/login", model);
	}
	
	/**
	 * 
	 * 登录验证授权
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author zhangjx
	 * @created 2016年11月18日 下午4:34:58
	 * @lastModified
	 */
	@RequestMapping("/auth")
	public @ResponseBody ResultObj auth(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResultObj resultObj = new ResultObj();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		SysUserEntity userEntity = sysUserService.findByLoginName(username,password);
		if(userEntity == null){
			resultObj.setData("用户名或密码不正确");
			resultObj.setResult("error");
		}else{
			if(userEntity.getStatus().equals("2")){
				resultObj.setData("用户已被锁定");
				resultObj.setResult("error");
			}else{
				resultObj.setData(userEntity.getId());
				List<Condition> conditions = new ArrayList<Condition>();
//				conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
//				List<UserUnitRelEntity> userRelList = userUnitRelService.findByCondition(conditions, null);
//				List<Long> userUnitIdList = new ArrayList<Long>();
//				if(!userRelList.isEmpty()){
//					for(UserUnitRelEntity userUnitRelEntity : userRelList){
//						userUnitIdList.add(userUnitRelEntity.getUnitId());
//					}
//					conditions.clear();
//					conditions.add(new Condition("C_STATUS", FieldTypeEnum.STRING, MatchTypeEnum.EQ, String.valueOf(DataStatusEnum.NORMAL.ordinal())));	
//					//这句话有带商榷
//					conditions.add(new Condition("C_LEVEL", FieldTypeEnum.STRING, MatchTypeEnum.NE, UnitLevelEnum.STATION.getCode()));
//					conditions.add(new Condition("C_ID",FieldTypeEnum.LONG,MatchTypeEnum.IN,userUnitIdList.toArray()));
//					List<SysUnitEntity> sysUnitEntities = sysUnitService.findByCondition(conditions, null);
//					if(!sysUnitEntities.isEmpty()){
//						userEntity.setUnitId(sysUnitEntities.get(0).getId());
//						userEntity.setUnitName(sysUnitEntities.get(0).getName());
//					}
//				}
				conditions.add(new Condition("C_USER_ID",FieldTypeEnum.LONG,MatchTypeEnum.EQ,userEntity.getId()));
				List<UserUnitRelEntity> userUnitRelList = userUnitRelService.findByCondition(conditions, null);
				if(!userUnitRelList.isEmpty()){
					List<Long> unitIds = new ArrayList<Long>();
					for(UserUnitRelEntity userUnitRelEntity:userUnitRelList){
						unitIds.add(userUnitRelEntity.getUnitId());
					}
					userEntity.setUserUnitRelIds(unitIds);
				}
				HttpContextExtractor.setUser(request.getSession(), userEntity);	
			}
		}
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SYSUSER.getName(),OperateUserEnum.LOGIN.getName(),"",OperateUserEnum.LOGIN.getId());
		return resultObj;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response){
		SysUserEntity userEntity = (SysUserEntity)request.getSession().getAttribute("session_key_user");
		operateLogService.addOperateLog(userEntity,OperateUserModuleEnum.SYSUSER.getName(),OperateUserEnum.LOGOUT.getName(),"",OperateUserEnum.LOGOUT.getId());
		return new ModelAndView("redirect:/login/login");
	}
	
	/**
	 * 
	 * 主页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangjx
	 * @created 2016年11月18日 下午4:35:39
	 * @lastModified
	 */
	@RequestMapping(value="/main")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> model = new HashMap<String, String>();
		model.put("theme", "default");
		String uid = request.getParameter("uid");
		model.put("uid", uid);
		List<SysConfigEntity> sysConfigEntityList=sysConfigService.findAll();
		request.getSession().setAttribute("sysConfigEntity", (SysConfigEntity)sysConfigEntityList.get(0));
		return new ModelAndView("main", model);
	}
	/**
	 * 
	 * 主页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangjx
	 * @created 2016年11月18日 下午4:35:39
	 * @lastModified
	 */
	@RequestMapping(value="/loginTwo")
	public ModelAndView loginTwo(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> model = new HashMap<String, String>();
		model.put("theme", "default");
		String uid = request.getParameter("uid");
		model.put("uid", uid);
		List<SysConfigEntity> sysConfigEntityList=sysConfigService.findAll();
		request.getSession().setAttribute("sysConfigEntity", (SysConfigEntity)sysConfigEntityList.get(0));
		return new ModelAndView("login/loginTwo", model);
	}
	
	/**
	 * 
	 * 主页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhangjx
	 * @created 2016年11月18日 下午4:35:39
	 * @lastModified
	 */
	@RequestMapping(value="/index")
	public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> model = new HashMap<String, String>();
		model.put("theme", "default");
		List<SysConfigEntity> sysConfigEntityList=sysConfigService.findAll();
		request.getSession().setAttribute("sysConfigEntity", (SysConfigEntity)sysConfigEntityList.get(0));
		return new ModelAndView("/index/index", model);
	}
	
	/**
	 * 
	 * 验证用户名密码
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @author zhangjx
	 * @throws Exception 
	 * @created 2016年11月18日 下午3:26:32
	 * @lastModified
	 */
	private boolean checkPassword(String password, String encryptPwd) throws Exception{
		return StringUtils.equals(new BigInteger(EncryptUtil.encryptSHA(password.getBytes())).toString(32),
				encryptPwd);
	}
	
	/**
	 * 
	 * 验证码验证方法
	 * 
	 * @param parameter
	 * @return
	 * @author zhangjx
	 * @created 2016年11月18日 下午3:26:02
	 * @lastModified
	 */
	private boolean checkVerification(String parameter){
		return true;
	}
	
	/**
	 * 
	 * 首页公告模块
	 * 
	 * @param @return
	 * @return ModelAndView
	 * @throws 
	 * @author liweiran
	 * @created 2017年9月13日 下午5:59:21
	 * @lastModified
	 */
	@RequestMapping(value="/firstPage")
	public ModelAndView getFirstPage(){
		Map<String, Object> model = new HashMap<String, Object>();
		//鉴定为缺陷的缺陷相关信息
		List<Condition> conditions = new ArrayList<Condition>();
		conditions.add(new Condition("dd.C_APPRAISAL_RESULT", FieldTypeEnum.STRING, MatchTypeEnum.EQ, "1"));
		List<Map<String, Object>> defectDataList = defectService.findByCondition("findDefectProportionByCondition", conditions, null);
		// 缺陷类型
		Map<String, SysDictionaryVO> typeMap = DictionaryUtil.getDictionaries("DEFECT_TYPE");
		//缺陷总数
		int totalCount = 0;
		String defectDatas = "[";
		if (defectDataList != null && defectDataList.size() > 0) {
			for (Map<String, Object> tmpMap :defectDataList) {
				if (tmpMap != null && tmpMap.get("defectCount") != null) {
					totalCount += ((Long)tmpMap.get("defectCount")).intValue();
				}
			}
			String item = "";
			DecimalFormat df = new DecimalFormat("0.00");
			for (Map<String, Object> tmpMap :defectDataList) {
				if (tmpMap != null && typeMap != null) {
					for (SysDictionaryVO vo : typeMap.values()) {
						if (tmpMap.get("type") != null && tmpMap.get("defectCount") != null && tmpMap.get("type").toString().equals(vo.getCode())) {
							int defectCount = ((Long)tmpMap.get("defectCount")).intValue();
							float defectRate = 0;
							if (totalCount != 0) {
								defectRate = (float)defectCount/totalCount *100;
							} 
							item = "{name : '" + df.format(defectRate) + "%-" + vo.getName() + "', value : '" + df.format(defectRate) + "'}";
							defectDatas += item + ",";
							break;
						}
					}
				}
			}
			if (defectDatas.endsWith(",")) {
				defectDatas = defectDatas.substring(0, defectDatas.length() -1);
			}
			defectDatas += "]"; 
		}
		model.put("defectData", JsonUtil.toJson(defectDatas));
		
		//公司动态推荐页图片展示
		conditions.clear();
		conditions.add(new Condition("C_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,CompanyTrendsStatusEnum.GUARDIAN.getCode()));
		conditions.add(new Condition("C_COMMEND_STATUS",FieldTypeEnum.STRING,MatchTypeEnum.EQ,"true"));
		List<CompanyTrendsEntity> companyTrendsEntityList = companyTrendsService.findByCondition("findByCondition",conditions, null);
//		for(CompanyTrendsEntity companyTrendsEntity : messList){
//			String commendPicture = companyTrendsEntity.getCommendPicture();
//			Long commendId = companyTrendsEntity.getId();
//		}
		model.put("companyTrendsEntity", companyTrendsEntityList);
		return new ModelAndView("login/firstPage", model);
	}
}
