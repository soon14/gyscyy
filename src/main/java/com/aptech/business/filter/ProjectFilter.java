package com.aptech.business.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aptech.business.mobile.util.MobileUtil;
import com.aptech.business.mobile.util.TokenUtil;
import com.aptech.common.system.user.domain.SysUserEntity;
import com.aptech.framework.context.HttpContextExtractor;
import com.aptech.framework.context.RequestContext;
import com.aptech.framework.log.Log;
import com.aptech.framework.security.DeveloperUser;
import com.aptech.framework.util.DateFormatUtil;
import com.aptech.framework.util.JsonUtil;
import com.aptech.framework.util.StringUtil;

/**
 * 
 * @ClassName: ProjectFilter 
 * @Description: TODO 
 * @author zhangjx
 * @date 2016年9月18日 上午11:52:30 
 *
 */
public class ProjectFilter implements Filter {
	private static Log log = Log.getInstance(ProjectFilter.class);
	/**
	 * 是否关闭这个过滤器
	 */
	private boolean closed = false;

	/**
	 * 是否关闭权限过滤
	 */
	private boolean purviewClosed = false;
	
	/**
	 * 手机端标志
	 */
	private String mobile="mobile";
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public boolean isPurviewClosed() {
		return purviewClosed;
	}
	public void setPurviewClosed(boolean purviewClosed) {
		this.purviewClosed = purviewClosed;
	}
	
	@Override
	public void destroy() {

	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestType = request.getHeader("X-Requested-With");
		Boolean isAjax = false;
		if (requestType != null) {
			isAjax = requestType.equals("XMLHttpRequest");
		}

		log.debug("**********isAjax**************" + isAjax);
		String url;
		if(StringUtil.isEmpty(request.getContextPath())){
		    if(request.getRequestURI().equals("/")){
			url = "";
		    }else{
			url = request.getRequestURI().substring(1,  request.getRequestURI().length());
		    }
		}else{
			url = request.getRequestURI().replace(
				request.getContextPath() + "/", "");
		}

		if (StringUtil.isEmpty(url)) {
			url = "root";
		}
		
		log.debug("**********url**************" + url);
		try {
			if(!url.split("/")[0].equals(mobile)){
					if (closed) {
						RequestContext.get().setUser(
								DeveloperUser.INSTANCE);// 开发者模式
						RequestContext.get().setDeveloperMode(true);
					} else {
						Object user = HttpContextExtractor.getUser(request.getSession());
						if (user == null) {
							if (isAjax) {
								Map<String, Object> resultMap = new HashMap<String, Object>();
								resultMap.put("result", "timeout");
								resultMap.put("errorMsg", "session timeout");
								response.setContentType("application/json;charset=UTF-8");
								response.getWriter().print(JsonUtil.toJson(resultMap));
							} else {
								response.sendRedirect(request.getContextPath() + "/");
							}
							return;
						}else{
							String uid = request.getParameter("uid");
							if(!StringUtil.isEmpty(uid) && !uid.equals("undefined")){
								SysUserEntity sysUserEntity = (SysUserEntity) user; 
								if(!uid.equals(sysUserEntity.getId().toString())){
									if (isAjax) {
										Map<String, Object> resultMap = new Hashtable<String, Object>();
										resultMap.put("result", "redirect");
										resultMap.put("errorMsg", "多用户登录异常");
										response.setContentType("application/json;charset=UTF-8");
										response.getWriter().print(JsonUtil.toJson(resultMap));
									} else {
										response.sendRedirect(request.getContextPath() + "/");
									}
									return;
								}
							}
						}
						RequestContext.get().setUser(user);
					}
					RequestContext.get().setPurviewClosed(purviewClosed);
					chain.doFilter(request, response);
			}else{
				String token=request.getParameter("token");
				//解密
				token=MobileUtil.decodeString(token);
				SysUserEntity userEntity=null;
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, SysUserEntity> user = TokenUtil.getUserEntity(token);
				if(user==null){
					resultMap.put("statusCode", "301");
					resultMap.put("message", "token无效");
					resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
					response.getWriter().print(MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap)));
					return;
				}else{	
					 userEntity = user.get(token);
						if((new Date().getTime()-userEntity.getLastLoginTime().getTime()) >2592000000l){
							resultMap.put("statusCode", "302");
							resultMap.put("message", "token过期");
							resultMap.put("time", DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()));
							TokenUtil.clearUserEntity(token);
							response.getWriter().print(MobileUtil.StringEncryptDes(JsonUtil.toJson(resultMap)));
							return;
						}
				}
				//参数统一解密（后续扩展）
//				ServletRequest requestWrapper = null;  
//				if(request instanceof HttpServletRequest) {  
//					requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);  
//				}  
				RequestContext.get().setUser(userEntity);
				chain.doFilter(request, response);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);// TODO:将来再重构系统异常体系的时候需要处理这个地方 by
											// zhangjx 2016-9-18
		} finally {
			RequestContext.clear();
		}
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
