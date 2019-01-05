<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		
		<div class="page-content">
		<div class="tabbable">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#workitemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
		 		</ul>
		 		
			<div class="tab-content">
			<div id="workitemEdit" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 600px">
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFireFormEdit">
		        <input type="hidden" id="id" name="id" value="${workTicketFireTwoEntity.id}" />
		        <input type="hidden" id="kgsgTotal" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly"  maxlength="64" value="${workTicketFireTwoEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right">单位名称</label>
								<div class="col-md-4">
										<input  id="unitNameIdEdit"  type="text" readonly="readonly" value="${workTicketFireTwoEntity.unitName}" class="col-md-12">
								</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdEdit"  type="text" readonly="readonly" value="${workTicketFireTwoEntity.groupName}" class="col-md-12">
								</div>
								<label class="col-md-2 control-label no-padding-right">动火工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly"   value="${workTicketFireTwoEntity.guarderName}"></input>
								</div>
							</div>
						
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员（包括工作负责人）</label>
									<div class="col-md-10">
										<textarea id="workClassMember"  readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketFireTwoEntity.workClassMember}</textarea>
									</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作班人数</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople"  type="text" readonly="readonly"  maxlength="20" value="${workTicketFireTwoEntity.workClassPeople}">
								</div>
								
								</div>
								
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作任务</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketFireTwoEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">动火地点</label>
									<div class="col-md-10">
										<textarea id="address" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketFireTwoEntity.address}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">设备编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentCode"  type="text" readonly="readonly" maxlength="64" value="${workTicketFireTwoEntity.equipmentCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="equipmentName"  type="text" readonly="readonly" maxlength="64" value="${workTicketFireTwoEntity.equipmentName}">
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">计划动火开始时间</label>
									<div class="col-md-4">
										<input class="col-md-12" id="plandateStart"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketFireTwoEntity.plandateStart}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">计划动火终了时间</label>
								<div class="col-md-4">
										<input class="col-md-12" id="plandateEnd"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketFireTwoEntity.plandateEnd}" type="date"/>'>
								</div>
							</div>
							
							
				</form>
			<!-- 列表开始 -->
						<div class="widget-main no-padding">
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应断开的断路器</h5>
							<table id="workSafe-table"
								class="table table-striped table-bordered table-hover"
								style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
									</tr>
								</thead>
							</table>
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应拉开的隔离开关</h5>
							<table id="workSafe-table-Two"
								class="table table-striped table-bordered table-hover"
								style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
									</tr>
								</thead>
							</table>
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应投切的相关电源（空气开关、熔断器、连接片）低压及二次回路</h5>
							<table id="workSafe-table-Three"
								class="table table-striped table-bordered table-hover"
								style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
									</tr>
								</thead>
							</table>
						</div>
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板</h5>
						<table id="workSafe-table-Four"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应设遮拦、应挂标示牌（位置）</h5>
						<table id="workSafe-table-Five"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">其他安全措施和注意事项</h5>
						<table id="workSafe-table-Six"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">动火作业单位应采取的安全措施</h5>
						<table id="workSafe-table-Seven"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
	 		<!-- 列表结束 -->
	 		<!-- 审批字段开始 -->
		    <form class="form-horizontal"  style="margin-right:100px;" id="spForm">
		    <div id="firetwoQf" class="tab-pane fade active in" style="margin-top: 50px;">
 						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作票签发人签名</label>
							<div class="col-md-4">
										<input class="col-md-12" id="signerName" name="signerName" type="text" readonly="readonly"  maxlength="64" value="${workFireTwoEntity.signerName}">
							</div>
						<label class="col-md-2 control-label no-padding-right">签发日期</label>
							<div class="col-md-4">
										<input class="col-md-12" id="signerDate" name="signerDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.signerDate}" type="date"/>'>
							</div>
						</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作票会签人签名</label>
						<div class="col-md-4">
									<input class="col-md-12" id="meetSignName" name="meetSignName" type="text" readonly="readonly"  maxlength="64" value="${workFireTwoEntity.meetSignName}">
						</div>
					<label class="col-md-2 control-label no-padding-right">会签日期</label>
						<div class="col-md-4">
									<input class="col-md-12" id="meetSignDate" name="meetSignDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.meetSignDate}" type="date"/>'>
						</div>
					</div>
			</div>
			
			<div id="firetwoSp" class="tab-pane fade active in" >
                    <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
						<div class="col-md-4">
						    <input class="col-md-12" id="otherApproveFire" name=otherApproveFire type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.otherApproveFire}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">签名日期</label>
						<div class="col-md-4">
							<input class="col-md-12" id="otherApproveFireDate" name="otherApproveFireDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.otherApproveFireDate}" type="date"/>'>
						</div>
					
		            </div>
				    <div class="form-group">
				    
		    	      <label class="col-md-2 control-label no-padding-right">安监部门负责人签名</label>
						<div class="col-md-4">
								<input class="col-md-12" id="otherApproveSafe" name="otherApproveSafe" type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.otherApproveSafe}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">签名日期</label>
						<div class="col-md-4">
								<input class="col-md-12" id="otherApproveSafeDate" name="otherApproveSafeDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.otherApproveSafeDate}" type="date"/>'>
						</div>
					</div>
			</div>
			<div id="firetwoJs" class="tab-pane fade active in">
					<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">运行值班人员签名</label>
							<div class="col-md-4">
										<input class="col-md-12" id="dutyMonitorName" name="dutyMonitorName" type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.dutyMonitorName}" >
							</div>
							<label class="col-md-2 control-label no-padding-right">收票日期</label>
						   <div class="col-md-4">
									<input class="col-md-12" id="dutyMonitorDate" name="dutyMonitorDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.dutyMonitorDate}" type="date"/>'>
						   </div>
					</div>
			</div>
			<div id="firetwoXk" class="tab-pane fade active in" >
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">确认本工作票安全措施已全部执行</label>
								<div class="col-md-4">
								</div>
							<label class="col-md-2 control-label no-padding-right">许可时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowDate" name="changeAllowDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketFireTwoEntity.changeAllowDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
										<input class="col-md-12" id="changeAllowName" name="changeAllowName" type="text" readonly="readonly" maxlength="64" value="${workTicketFireTwoEntity.changeAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">动火负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="fireUserName"name="fireUserName" type="text" readonly="readonly" maxlength="64" value="${workFireTwoEntity.fireUserName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">其他注意事项</label>
								<div class="col-md-10">
										<textarea id="other" name="other"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workFireTwoEntity.other}</textarea>
								</div>
							</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"> 可燃性、易爆气体含量或粉尘浓度合格</label>
								<div class="col-md-4"></div>
								<div class="col-md-4">
								</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
								<div class="col-md-4">
								  <input class="col-md-12" id="otherFireSign" name=otherFireSign type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.otherFireSign}" >
								</div>
								<label class="col-md-2 control-label no-padding-right">监护时间</label>
								<div class="col-md-4">
								    <input class="col-md-12" id="otherFireSignDate" name="otherFireSignDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.otherFireSignDate}" type="date"/>'>
								</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">允许动火时间</label>
								<div class="col-md-4">
										<input class="col-md-12" id="approveStarttime" name="approveStarttime" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireTwoEntity.approveStarttime}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">动火工作负责人</label>
						        <div class="col-md-4">
								<input class="col-md-12" id="otherPicSign" name=otherPicSign type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.otherPicSign}" >
						        </div>
						</div>
					    <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">动火执行人</label>
							<div class="col-md-4">
									<input class="col-md-12" id="otherExecutorSign" name=otherExecutorSign type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.otherExecutorSign}" >
							</div>
							<label class="col-md-2 control-label no-padding-right">消防监护人</label>
						    <div class="col-md-4">
								<input class="col-md-12" id="runFireSignName" name=runFireSignName type="text" readonly="readonly" maxlength="64"  value="${workFireTwoEntity.runFireSignName}" >
						    </div>
						</div>
			     </div>	
					<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">安全交底</h5>
						<div id="firetwoAqjd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">安全交底</label>
								<div class="col-md-10">
									<textarea id="workDisclosure" name="workDisclosure"
										readonly="readonly" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128">${workFireTwoEntity.workDisclosure}</textarea>
										
								</div>
							</div>
						</div>	
				<div id="firetwoZj">
					<h5 class="table-withoutbtn header smaller"
						style="margin-bottom:0px;">工作票终结,全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
				</div>
				<div id="firetwoZj" class="tab-pane fade active in"
					style="margin-top: 20px;">
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">终结时间</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endTime" name="endTime" type="text"
								readonly="readonly" maxlength="20"
								value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketFireTwoEntity.endTime}" type="date"/>'>
						</div>
						<label class="col-md-2 control-label no-padding-right">接地线共多少组</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endHand" name="endHand"
								type="text" readonly="readonly" maxlength="64"
								value="${workFireTwoEntity.endHand}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">已拆除(组)</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStandIndex" name="endStandIndex"
								type="text" readonly="readonly" maxlength="128"
								value="${workFireTwoEntity.endStandIndex}">
						</div>
						<label class="col-md-2 control-label no-padding-right">因另有工作保留(组)</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStand" name="endStand"
								type="text" readonly="readonly" maxlength="20"
								value="${workFireTwoEntity.endStand} ">
						</div>
					</div>
				
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">动火执行人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="otherendExecutorSign" name="otherendExecutorSign"
								type="text" readonly="readonly" maxlength="128"
								value=" ${workFireTwoEntity.otherendExecutorSign}">
						</div>
						<label class="col-md-2 control-label no-padding-right">动火工作负责人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endPicName" name="endPicName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketFireTwoEntity.endPicName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="otherendFireSign" name="otherendFireSign"
								type="text" readonly="readonly" maxlength="128"
								value="${workFireTwoEntity.otherendFireSign }">
						</div>
						<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endAllowName" name="endAllowName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketFireTwoEntity.endAllowName}">
						</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">备注</label>
								<div class="col-md-10">
									<textarea id="remark" name="remark"   readonly="readonly"
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workFireTwoEntity.remark}</textarea>
								</div>
					</div>
				</div>			
		
			</form>
			<!-- 审批字段结束 -->
<!-- 			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">备注：</h5> -->
<!-- 			<form class="form-horizontal"  style="margin-right:100px;" id="bzform"> -->
<!-- 			<div id="workitemBz" class="tab-pane fade active in" style="margin-top: 20px;">	 -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label> -->
<!-- 								<div class="col-md-10"> -->
<%-- 										<textarea id="remarkOther" name="remarkOther" readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workFireTwoEntity.remark}</textarea> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 			</div> -->
<!-- 			</form> -->
			</div>
			</div>
			</div><!-- tabbable -->
			</div><!-- page-content -->
				
        
        <div style="margin-right:100px;margin-top: 20px;">
					    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
					    <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
						<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再提交
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='fpBtn'}">
						<button id="qxBtn" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    			  <i class="ace-icon fa fa-floppy-o"></i>
		    				废票
		    			</button>
		    			</c:if>		    			
					    <c:if test="${nodeList.authFactorCode=='firetwoQfBtn'}">
						<button id="firetwoQfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				签发
		    			</button>
		    			</c:if>
					    <c:if test="${nodeList.authFactorCode=='firetwoHqfBtn'}">
						<button id="firetwoHqfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				会签发
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoJhBtn'}">
						<button id="firetwoJhBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				消防监护人审批
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoAjbfzBtn'}">
		    			<button id="firetwoAjbfzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				安监部门负责人审批
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoYxzbBtn'}">
		    			<button id="firetwoYxzbBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				运行值班人员收票
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoXkrBtn'}">
						<button id="firetwoXkrBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				许可人审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoXfjhBtn'}">
		    			<button id="firetwoXfjhBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				消防监护人审批(许可)
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='firetwoDhFzBtn'}">
		    			<button id="firetwoDhFzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				动火负责人验收
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='jhzjBtn'}">
		    			<button id="jhzjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				消防监护人审批(终结)
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='xkzjBtn'}">
		    			<button id="xkzjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				工作许可人审批(终结)
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zjBtn'}">
								<button id="zjBtn" class="btn btn-xs btn-success pull-right"
									style="margin-right:15px;">
									<i class="ace-icon fa fa-floppy-o"></i> 终结
								</button>
						</c:if>
						<c:if test="${nodeList.authFactorCode=='aqjdBtn'}">
								<button id="aqjdBtn" class="btn btn-xs btn-success pull-right"
									style="margin-right:15px;">
									<i class="ace-icon fa fa-floppy-o"></i> 安全交底
								</button>
						</c:if>
	    			</c:forEach>	    			
	        	</div>
		<script type="text/javascript">
			jQuery(function($) {
				var controlCardRiskDatatables="";
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var  workTicketFireId='${workTicketFireTwoEntity.id}'; 
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//表单详细
						if($(e.target).attr('href') == "#workitemEdit"){
						}
 						//2
						if($(e.target).attr('href') == "#workcarditemEdit"){
							controlCardRiskDatatables.draw(true);
						}
 					 });
					
				
					
					$('#bzform').validate({
						debug:true,
						rules: {
							},
						submitHandler: function (form) {
							var id = '${workFireTwoEntity.id}';
							//修改按钮
							var url = format_url("/workFire/updateBz/"+id);
							var obj = $("#bzform").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									 window.scrollTo(0,0);
									  A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/"+ 1 + "/" + 10)
									  });
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#bcbzBtn").on("click", function(){
    					$("#bzform").submit();
    				});
					
					
					
					//下面是安全措施的列表 1
					var workid = '${workTicketFireTwoEntity.id}';
					var conditionsOne = [];
					var workSafeOneDatatables = new A.datatables(
							{
								render : '#workSafe-table',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsOne
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 1
													});
											conditionsOne
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsOne;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
								}
							}).render();

					//下面是安全措施的列表 2

					var conditionsTwo = [];
					var workSafeTwoDatatables = new A.datatables(
							{
								render : '#workSafe-table-Two',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsTwo
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 2
													});
											conditionsTwo
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsTwo;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
									
								}
							}).render();

					//下面是安全措施的列表 3

					var conditionsThree = [];
					var workSafeThreeDatatables = new A.datatables(
							{
								render : '#workSafe-table-Three',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsThree
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 3
													});
											conditionsThree
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsThree;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
								}
							}).render();
					//下面是安全措施的列表 4
					var conditionsFour = [];
					var workSafeFourDatatables = new A.datatables(
							{
								render : '#workSafe-table-Four',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsFour = [];
											d.length = 2147483647;
											conditionsFour
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 4
													});
											conditionsFour
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsFour;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									paging : false,
									checked : false,
									bInfo : false,
									aLengthMenu : [ 5 ],
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
									
								}
							}).render();
					//下面是安全措施的列表 5
					var conditionsFive = [];
					var workSafeFiveDatatables = new A.datatables(
							{
								render : '#workSafe-table-Five',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											d.length = 2147483647;
											conditionsFive = [];
											conditionsFive
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 5
													});
											conditionsFive
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsFive;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
									
								}
							}).render();
					//下面是安全措施的列表 6
					var conditionsSix = [];
					var workSafeSixDatatables = new A.datatables(
							{
								render : '#workSafe-table-Six',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsSix = [];
											d.length = 2147483647;
											conditionsSix
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 6
													});
											conditionsSix
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketFireTwoEntity.id}'
													});
											d.conditions = conditionsSix;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
//										}, {
//											data : "executeSituation",
//											width : "20%",
//											orderable : true
									} ],
								}
							}).render();
					
					//下面是安全措施的列表 2
					
					var conditionsSeven=[];
					var workSafeSevenDatatables="";
					workSafeSevenDatatables = new A.datatables({
						render: '#workSafe-table-Seven',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length = 2147483647;
					            	conditionsSeven.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:12
		            				});
					            	conditionsSeven.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketFireTwoEntity.id}'
		            				});
					            	d.conditions = conditionsSeven;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "60%",orderable: true}
							          ],
									
						}
					}).render();										
					
					   //控制回显多选框结束
					   var taskId=$("#taskId").val();
					   var procInstId=$("#procInstId").val();
					   var procDefId=$("#procDefId").val();
					   
					   var electricId='${workFireTwoEntity.id}';
					   //页面下方的签发按钮1
						$('#firetwoQfBtn').on('click',function(){
							var workPerson='${workTicketFireTwoEntity.guarderId}';
							var loginUser='${userEntity.id}';
 							/* if(workPerson==loginUser){
 								alert("工作负责人和签发人不能是同一个人");
 								return;
 							   }else{ */																		
										workSafeOneDialog = new A.dialog({
			        						width: 1000,
			        						height: 400,
			        						title: "签发",
			        						url:format_url("/workFireTwo/getAddQf?electricId="+electricId+"&taskId="+taskId ),
			        						closed: function(){
			        						}	
			        					}).render();
										
									
//  							}
						});
						//废票
						$('#qxBtn').on('click',function(){
							var obj = $("#workTicketFireFormEdit").serializeObject();
							obj.id='${workTicketFireTwoEntity.id}';
							$.ajax({
								url : format_url("/workFireTwo/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('废票成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('废票失败');
								}
							});
						});
						//页面下方的会签发人按钮
						$('#firetwoHqfBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "会签人",
        						url:format_url("/workFireTwo/getAddHqf?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						//页面下方监护人审核按钮
						$('#firetwoJhBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "监护人审核",
        						url:format_url("/workFireTwo/getAddxfjhrsh?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						//页面下方安监部门负责人审核按钮
						$('#firetwoAjbfzBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "安监部审核",
        						url:format_url("/workFireTwo/getAddajfzrsh?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的收票按钮
						$('#firetwoYxzbBtn').on('click',function() {
								workSafeOneDialog = new A.dialog({
											width : 1000,
											height : 400,
											title : "收票",
											url : format_url("/workFireTwo/getAddSp?electricId="+ electricId+ "&taskId="+ taskId),
											closed : function() {
											}
										}).render();
							});
			
						
						//页面下方的许可按钮
						$('#firetwoXkrBtn').on('click',function(){
							var workId='${workTicketFireTwoEntity.id}';
// 							$.ajax({
// 								url : format_url("/workTicketFireTwo/isExecute/"+workId),
// 								contentType : 'application/json',
// 								dataType : 'JSON',
// 								type : 'POST',
// 								success: function(result){
// 									if(result.result=="success"){
										
 										var workPerson='${workTicketFireTwoEntity.guarderId}';
 										var qfPerson='${workFireTwoEntity.signerId}';
 										var loginUser='${userEntity.id}';
										
//  										if(workPerson==loginUser||qfPerson==loginUser){
//  											alert("许可人和工作负责人和签发人不能是同一个人");
//  											return;
//  										}else{										
											workSafeOneDialog = new A.dialog({
				        						width: 1250,
				        						height: 700,
				        						title: "许可审批",
				        						url:format_url("/workFireTwo/getAddXk?electricId="+electricId+"&taskId="+taskId ),
				        						closed: function(){
				        						}	
				        					}).render();
											
//  										}
// 									}else{
// 										alert(result.errorMsg);
// 									}
// 								},
// 								error:function(v,n){
// 									alert('操作失败');
// 								}
// 							});
						});
						//消防监护人确认措施
						$('#firetwoXfjhBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 300,
        						title: "消防监护人确认",
        						url:format_url("/workFireTwo/getAddjhrqr?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						
						//页面下方的终结按钮
						$('#zjBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workFireTwo/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						
						//终结  动火负责人验收
						$('#firetwoDhFzBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workFireTwo/getAddfzrys?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						
						//终结  消防监护人
						$('#jhzjBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workFireTwo/getAddZjJh?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//终结  许可人
						$('#xkzjBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workFireTwo/getAddZjXk?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						
						//页面下方的安全交底按钮
						$('#aqjdBtn').on('click',function() {
								workSafeOneDialog = new A.dialog(
										{
											width : 700,
											height : 250,
											title : "安全交底",
											url : format_url("/workFireTwo/getAddGzjd?electricId="+ electricId+ "&taskId="+ taskId),
											closed : function() {
											}
										}).render();
								});
					
						//废票
						$('#fpBtn').on('click',function(){
							var obj = $("#workTicketFireFormEdit").serializeObject();
							obj.id='${workFireTwoEntity.id}';
							$.ajax({
								url : format_url("/workFireTwo/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('废票成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('废票失败');
								}
							});
						});
						
						//再提交
						$('#ztjBtn').on('click',function(){
							var id='${workTicketFireTwoEntity.id}';
							workSafeOneDialog = new A.dialog({
        						width: 500,
        						height: 450,
        						title: "再提交",
        						url:format_url("/workTicketFireTwo/sureSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/workFireTwo/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							window.scrollTo(0,0);
							$("#page-container").load(format_url('/todoTask/list/1/10'));
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
						workTicketFireDatatables.draw(false);
					}
				});
				
			}
			function gotoQx(){
				  window.scrollTo(0,0);
				  A.loadPage({
						render : '#page-container',
						url : format_url("/todoTask/list/"+ 1 + "/" + 10)
					});
			}
			
			//判断值是否在数组里的方法zzq
			function contains(arr, obj) {
				  var i = arr.length;
				  while (i--) {
				    if (arr[i] === obj) {
				      return true;
				    }
				  }
				  return false;
			}
        </script>
    </body>
</html>