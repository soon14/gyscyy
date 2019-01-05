<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>
    <%@ page import="com.aptech.common.system.config.domain.SysConfigEntity" %>
        <%@ page import="com.aptech.common.system.user.domain.SysUserEntity" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     
	<%
	SysConfigEntity sysConfigEntity= (SysConfigEntity)request.getSession().getAttribute("sysConfigEntity");
	SysUserEntity sysUserEntity= (SysUserEntity)request.getSession().getAttribute("session_key_user");
	%>
		<div id="navbar" class="navbar navbar-default  ace-save-state" style='text-align:left;'>
			<div class="navbar-container ace-save-state" id="navbar-container">
				<span class='navbarSpanTitle'><%=sysConfigEntity.getName()%></span>
				<div class="navbar-header">
					<a href="index.html" class="navbar-brand" style="height:45px;line-height:45px;float:none">
						<span >
							
							<a href="javascript:firstPage()" ><img alt="" src="../static/images/headImg.png" style="float: left;height: 48px;width: 48px;margin-top:3px"></a> 
						</span>
					</a>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav userliststyle">
						<li class="grey">
							<a  href="#" id='getPic' title='截图' style='background-color:#008b8b'>
								<i class="ace-icon fa fa-camera"  id="getPicStyle"></i>
								<span class="badge badge-grey" ></span>
							</a>
						</li>
						<li class="grey">
							<a  href="#" id='waittingMission' title='待办事项' style='background-color:#008b8b'>
								<i class="ace-icon fa fa-tasks"></i>
								<span class="badge badge-grey" ></span>
							</a>
						</li>

						<li class="lorange">
							<a  href="#" title='消息' id='waittingMessage' style='background-color:#6495ed'>
								<i class="ace-icon fa fa-bell icon-animated-bell"></i>
								<span class="badge badge-important"></span>
							</a>
						</li>


						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" id='headImg' />
								<span class="user-info">
									<small>欢迎,</small>
									<%=sysUserEntity.getName()%>
								</span>

								<i class="ace-icon fa fa-caret-down"></i>
							</a>

							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<!-- <li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										Settings
									</a>
								</li> -->

								<li>
									<a id="info" href="#">
										<i class="ace-icon fa fa-user"></i>
										个人信息
									</a>
								</li>
								<li>
									<a id="changeUnit" href="#">
										<i class="ace-icon fa fa-user"></i>
										单位变更
									</a>
								</li>
								<li>
									<a id="password" href="#">
										<i class="ace-icon fa fa-key"></i>
										密码修改
									</a>
								</li>
								<li class="divider"></li>

								<li>
									<a href="${ctx}/login/logout">
										<i class="ace-icon fa fa-sign-out"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>