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
	<div class="col-md-12">
		<div class="page-content">
			<div class="tabbable" >
				<ul class="nav nav-tabs" id="myTab">
					<li class="active" ><a data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i> 票据信息
					</a></li>

<!-- 					<li><a data-toggle="tab" href="#operationDanger" -->
<!-- 						aria-expanded="false"> <i -->
<!-- 							class="green ace-icon fa fa-list bigger-120"></i> 危险因素控制卡 -->
<!-- 					</a></li> -->

				</ul>
				<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 600px">

					<!-- 第一个div开始 -->
					<div id="workitem" class="tab-pane fade active in" >
						<form class="form-horizontal" role="form"
							style="margin-right:100px;" id="approveForm">
							<input id="id" name="id" value="${t.id}" type="hidden"/>
							<input id="operateName"  value="${t.operateName}" type="hidden" />
							<input id="guardianName"  value="${t.guardianName}" type="hidden"/>
							<input id="operateItemNum"  value="${t.operateItemNum}" type="hidden"/>
							<input id="status"  name="status" value="${t.status}" type="hidden"/>
							<!-- <input id="endDate" name="endDate"  type="hidden"/> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									编号
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="code" name="code" type="text"
										placeholder="" maxlength="64" value="${t.code}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									单位名称
								</label>
								<div class="col-md-4">
											<input class="col-md-12" id="unitName" name="unitName" type="text" 
											placeholder="" maxlength="64" value="${t.unitName}" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">设备编码</label>
										<div class="col-md-4">
										<input class="col-md-12" id="equipmentCode" name="equipmentCode" type="text"
										placeholder="" maxlength="64" value="${t.equipmentCode}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" 
											placeholder="" maxlength="64" value="${t.equipmentName}" readonly="readonly">
								</div>
							</div>
							<%-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									操作开始时间
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="startDate" name="startDate" type="text" readonly="readonly"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.startDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									操作终了时间
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="endDate" name="endDate" type="text" readonly="readonly"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.endDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>">
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									工作票票号
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workticketCode" readonly="readonly"
										name="workticketCode" type="text" placeholder=""
										maxlength="64" value="${t.workticketCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									缺陷单编号 </label>
								<div class="col-md-4">
								<input class="col-md-12" id="defectCode" name="defectCode" type="text" 
											placeholder="" maxlength="64" value="${t.defectCode}" readonly="readonly">
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right"> -->
<!-- 									班组 </label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 								<input class="col-md-12" id="groupName" name="groupName" type="text"  -->
<!-- 											placeholder="" maxlength="64" value="${t.groupName}" readonly="readonly"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									发令单位
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="startUnitName" name="startUnitName" type="text"
										placeholder="" maxlength="64" value="${t.startUnitName}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									 发令人 
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="startUserName" name="startUserName" type="text"
										placeholder="" maxlength="64" value="${t.startUserName}" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									受令人
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="endUserName" name="endUserName" type="text"
										placeholder="" maxlength="64" value="${t.endUserName}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									受令时间 
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="endTime" name="endTime" type="text"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									操作任务
								</label>
								<div class="col-md-10"> 
									<textarea name="workText" placeholder="" readonly="readonly"
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.workText}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									备注 </label>
								<div class="col-md-10">
									<textarea name="remark" placeholder="" readonly="readonly"
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.remark}</textarea>
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									附件 </label>
								<div class="col-md-10" id="divfile" >
								</div>
							</div> -->
						</form>
						<div id="operationItem" ></div>
<!-- 						<form class="form-horizontal" role="form"  -->
<!-- 						style="margin-right:100px;margin-top: 15px"> -->
<!-- 							<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"> -->
<!-- 										终止原因</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea name="reason" placeholder="" id="reason" readonly="readonly" -->
<!-- 											style="height:100px; resize:none;" class="col-md-12" -->
<!-- 											maxlength="128">${t.reason}</textarea> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 						</form> -->
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">参加操作、监护人、负责人</h5>
		                    <div class="form-horizontal" style="margin-right:100px;margin-top: 20px;" >
									<div class="form-group ">
										<label class="col-md-2 control-label no-padding-right">
											操作人
										</label>
										<div class="col-md-2">
											 <input  value="${t.operateName}" class="col-md-12" type="text" readonly="readonly"/>
										</div>
										<label class="col-md-2 control-label no-padding-right">
											监护人
										</label>
										<div class="col-md-2">
											 <input  value="${t.guardianName}" class="col-md-12" type="text" readonly="readonly"/>
										</div>
										<label class="col-md-2 control-label no-padding-right">
											负责人
										</label>
										<div class="col-md-2">
											 <input  value="${t.picName}" id="operateId" class="col-md-12" type="text" readonly="readonly"/>
										</div>
									</div>
							</div>
					</div>
					<div id="operationDanger" class="tab-pane fade"></div>
				</div>
			</div>
		</div>
	</div>
	 <div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;"id="button">
				<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
		    	<c:if test="${nodeList.authFactorCode=='rejectSubmit'}">
				<button id="rejectSubmit" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="fa fa-check-square-o bigger-130"></i> 提交
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='reject'}">
				<button id="reject" class="btn btn-xs btn-danger pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-remove-circle"></i> 驳回
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='invalid'}">
				<button id="invalid" class="btn btn-xs btn-danger pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-remove-circle"></i> 废票
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='foremanReject'}">
				<button id="foremanReject" class="btn btn-xs btn-danger pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-remove-circle"></i> 值长驳回
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='foremanConfirm'}">
				<button id="foremanConfirm" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-ok-sign"></i> 值长确定
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='implement'}">
				<button id="implement" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-ok-sign"></i> 记录结果
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='foremanExamine'}">
				<button id="foremanExamine" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-pawn"></i> 同意(值长)
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='monitor'}">
				<button id="monitor" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-pawn"></i> 同意(值班负责人)
				</button>
				</c:if>
				<c:if test="${nodeList.authFactorCode=='guardian'}">
				<button id="guardian" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="glyphicon glyphicon-pawn"></i> 同意(监护人)
				</button>
				</c:if>
				</c:forEach> 
			</div>
	</div>
	<script type="text/javascript">
	var listFormDialog;
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','uploaddropzone','selectbox' ],function(A) {
					//附件上传
					/* var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${t.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
					}).render(); */
						//加载项目列表
						$.ajax({url : format_url("/operationItem/indexDetail"),
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							data : {"id":0},
							success : function(result) {
								var divshow = $("#operationItem");
								divshow.text("");// 清空数据
								divshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							if($(e.target).attr('href') == "#operationDanger"){
								//加载控制卡列表
								$.ajax({url : format_url("/operationDanger/indexDetail"),
									contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
									data : {"id":0},
									success : function(result) {
										var divshow = $("#operationDanger");
										divshow.text("");// 清空数据
										divshow.append(result); // 添加Html内容，不能用Text 或 Val
									}
								});
							}
						});
						//全部按钮
						$("#button button").on("click", function(e){
							var id=$("#id").val(); 
							var obj = $("#approveForm").serializeObject();
								obj.taskId=$("#taskId").val();
								obj.procInstId=$("#procInstId").val();
								var urls="";
								var url=$(e.target).attr("id");
								if(url=="invalid"||url=="foremanConfirm"){
									$.ajax({
										url : format_url("/operationTicket/"+url+"/"+id),
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
								}else{
									debugger;
									if(url=="reject"){
										urls=format_url('/defect/submitPersonBackEnd/'+$("#taskId").val());
									}else if(url=="foremanReject"){
										urls=format_url('/defect/submitPersonBack/'+$("#taskId").val());
									}else if(url=="implement"){
										urls=format_url('/operationTicket/submitPersonAgree/'+id+'/'+$("#taskId").val());
									}else if(url=="rejectSubmit"){
										urls=format_url('/operationTicket/submitPerson2/'+id);
									} else{
										urls=format_url('/defect/submitPersonAgree/'+$("#taskId").val());
									} 
									var submitOK=false;
									if("implement"==url){
										 $("#operationItem_table tbody ").find("tr").each(function(i){
											 debugger;
    										   var tdArrs = $(this).children();
    										   var simulationName= tdArrs.eq(1).text();
    										   var actualName=tdArrs.eq(2).text();
    										   var contentText=tdArrs.eq(3).text();
//     										   for(j in simulationName){
	            								
    											   if(contentText!="无"&& simulationName!="√" && actualName!="√"){
    												   submitOK=true; 
    											   }
//     										   }
//     										   var actualName=tdArrs.eq(1).text();
// //     										   for(j in actualName){
//     											   if(actualName!="√"){
//     												   submitOK=true; 
// //     											   }
//     										   }
    								     });
									}
									debugger;
									if(submitOK){
										alert("操作项目未填写完！");
										return;
									}
									
									
									if("implement"==url){
										listFormDialog = new A.dialog({
											width: 850,
											height: 350,
											title: "记录结果",
											url:urls,
											closed: function(){
											/* 	if(listFormDialog.resule){
												 	obj.fileId=listFormDialog.resule;
												 	$.ajax({
														url : format_url("/operationTicket/"+url+"/"+id),
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
												} */
											}
										}).render();
									}else if("rejectSubmit"==url){
										debugger;
									 	listFormDialog = new A.dialog({
											width: 750,
											height: 450,
											title: "操作票提交人",
											url:urls,
											closed: function(){
												if(listFormDialog.resule){
												 	obj.userList=listFormDialog.resule.join(",");
												 	$.ajax({
														url : format_url("/operationTicket/"+url+"/"+id),
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
									}else {
// 									 	listFormDialog = new A.dialog({
// 											width: 750,
// 											height: 450,
// 											title: "操作票提交人",
// 											url:urls,
// 											closed: function(){
// 												if(listFormDialog.resule){
// 												 	obj.userList=listFormDialog.resule.join(",");
												 	$.ajax({
														url : format_url("/operationTicket/"+url+"/"+id),
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
// 												}
// 											}
// 										}).render(); 
									
									}
								}
	    				});
					});
		});
		
		/* function goBackToOperation(endDate){//回调函数
			$("#endDate").val(endDate);
		} */
	</script>
</body>
</html>