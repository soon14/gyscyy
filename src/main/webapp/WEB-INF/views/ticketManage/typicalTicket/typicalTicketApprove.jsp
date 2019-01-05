<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
<form class="form-horizontal" role="form"
							style="margin-right:100px;" id="approveForm">
							<input id="approveStatus" name="approveStatus" value="${tEntity.approveStatus}" type="hidden"/>
							<div class="form-group">
							    <label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>典型票名称
							    </label>
							    <div class="col-md-4">
									<input class="col-md-12" id="name" name="name" type="text" readonly="readonly"
									 placeholder="" maxlength="64" value="${tEntity.name}">
			                    </div>
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>典型票类型
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="typeName" name="typeName" type="text"  readonly="readonly"
								 placeholder="" maxlength="64" value="${tEntity.typeName}">
			                	</div>
			               </div>
</form>
	 <div id="typicalTicket"></div>
	 <div class="col-md-12">
		<div style="margin-right:100px;margin-top: -10px;" id="typicalbutton">
		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
	    	<%-- <c:if test="${nodeList.authFactorCode=='reject'}">
			<button id="reject" class="btn btn-xs btn-danger pull-right"
				style="margin-right:15px;">
				<i class="glyphicon glyphicon-remove-circle"></i> 驳回
			</button>
			</c:if>
	    	<c:if test="${nodeList.authFactorCode=='overhaul'}">
			<button id="overhaul" class="btn btn-xs btn-success pull-right"
				style="margin-right:15px;">
				<i class="glyphicon glyphicon-ok-sign"></i> 专工审批
			</button>
			</c:if>
	    	<c:if test="${nodeList.authFactorCode=='overhaulApprove'}">
			<button id="overhaulApprove" class="btn btn-xs btn-success pull-right"
				style="margin-right:15px;">
				<i class="glyphicon glyphicon-ok-sign"></i> 主任审批(1)
			</button>
			</c:if>
	    	<c:if test="${nodeList.authFactorCode=='biotechApprove'}">
			<button id="biotechApprove" class="btn btn-xs btn-success pull-right"
				style="margin-right:15px;">
				<i class="glyphicon glyphicon-ok-sign"></i> 主任审批(2)
			</button>
			</c:if> --%>
	    	<c:if test="${nodeList.authFactorCode=='overhaul'}">
			<button id="resultsApprove" class="btn btn-xs btn-success pull-right"
				style="margin-right:15px;">
				<i class="glyphicon glyphicon-ok-sign"></i> 专工审批
			</button>
			</c:if>
		</c:forEach>
		</div>
	</div>
	<script type="text/javascript">
	var listFormDialog;
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','uploaddropzone','selectbox' ],function(A) {
						//每一个票的审批页面
						var url="";
						if(${tEntity.type==1}){
							url=format_url("/workTicket/approve/"+'${tEntity.workticket_id}');
						}
						if(${tEntity.type==2}){
							url=format_url("/workTicketTwo/approve/"+'${tEntity.workticket_id}');
						}
						if(${tEntity.type==3}){
							url=format_url("/operationTicket/approve/"+'${tEntity.workticket_id}'+'/dealing');
						}
						if(${tEntity.type==4}){
							url=format_url("/interventionTicket/approve/"+'${tEntity.workticket_id}');
						}
						//加载票信息
						$.ajax({url : url,
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							data : {"id":0},
							success : function(result) {
								var divshow = $("#typicalTicket");
								divshow.text("");// 清空数据
								divshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
						//全部按钮
						$("#typicalbutton button").on("click", function(e){
							var id='${tEntity.id}'; 
							var obj = $("#approveForm").serializeObject();
								obj.taskId=$("#taskId").val();
								obj.procInstId=$("#procInstId").val();
								obj.id=id;
								var urls="";
								var url=$(e.target).attr("id");
								if(url=="reject"||url=="resultsApprove"){
									var loginUser = '${userEntity.id}';
									var createPersonId ='${tEntity.createPersonId}';
									if(createPersonId==loginUser){
		 								alert("审批人和设置人不能是同一个人!");
		 								return;
		 							}
									A.confirm('您确认要审批通过吗？',function(){
										$.ajax({
											url : format_url("/typicalTicket/"+url+"/"+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type : 'POST',
											data : JSON.stringify(obj),
											success: function(result){
												if(result.result=="success"){
													alert("审批成功");
													window.scrollTo(0,0);
													A.loadPage({
														render : '#page-container',
														url : format_url("/todoTask/list/1/10")
													});
												}else{
													alert(result.errorMsg);
												}
											},
											error:function(v,n){
												alert("审批失败");
											}
										});
									});
								}else{
									urls=format_url('/typicalTicket/submitPersonAgree/'+$("#taskId").val());
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "典型票提交人",
										url:urls,
										closed: function(){
											if(listFormDialog.resule){
											 	obj.userList=listFormDialog.resule.join(",");
											 	$.ajax({
													url : format_url("/typicalTicket/"+url+"/"+id),
													contentType : 'application/json',
													dataType : 'JSON',
													type : 'POST',
													data : JSON.stringify(obj),
													success: function(result){
														if(result.result=="success"){
															alert("审批成功");
															window.scrollTo(0,0);
															A.loadPage({
																render : '#page-container',
																url : format_url("/todoTask/list/1/10")
															});
														}else{
															alert(result.errorMsg);
														}
													},
													error:function(v,n){
														alert("审批失败");
													}
												});
											}
										}
									}).render();
								}
	    				});
					});
		});
	</script>
</body>
</html>