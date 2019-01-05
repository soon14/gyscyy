<%@ page language="java" contentType="text/html; charset=utf-8"  
	pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
	</head>
	<body>
		<div class="no-padding-right; " >
			<div class="row" style="height:680px;overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="joinltySignForm">
						<input class="col-md-12" id="id"  value="${receiptManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="type" value="${type}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewPublisherIdVal" type="hidden" value="${receiptManagementEntity.publisherId}" >
						<input class="col-md-12" id="viewReviewSelectedVal" type="hidden" value="${receiptManagementEntity.review}" >
						<input class="col-md-12" id="operateTypeVal"  value="${receiptManagementEntity.operateType}" type="hidden">
						<input class="col-md-12" id="jointlySelectedpersionIds" type="hidden" >
						<input class="col-md-12" id="jointlySelectedpersionNames" type="hidden" >
						<input class="col-md-12" id="productionUnitSelectedpersionIds" type="hidden" >
						<input class="col-md-12" id="productionUnitSelectedpersionNames" type="hidden" >
						<div class="form-group">
							<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建贵阳院勘测设计研究院有限公司签报登记</label> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
							</div>
							<div class="col-md-10" > 
		 						<input class="col-md-12" id="viewTitle" name="title" type="text" value="${receiptManagementEntity.title}" readonly>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>操作类型：</label> 
							</div>
							<div class="col-md-4"> 
	 							<input  type="text" class="col-md-12" value="${receiptManagementEntity.operateTypeCN}"  readonly>
							</div> 
						</div>
						<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id="signNoLabel"><span style="color:red;">*</span>发文字号：</label>
							<label class="col-md-12 control-label no-padding-right"  id="receiptNoLabel" style="display:none">来文字号：</label>
						</div>
						<div class="col-md-4">
							<input  type="text" name= "receiptNumber" class="col-md-12" value="${receiptManagementEntity.receiptNumber}"  readonly>
						</div>
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>来文编号：</label>
						</div>
						<div class="col-md-4">
							<input type="text" style="width:100%" id="receiptNoVal"  maxlength="15" name="receiptNo" value="${receiptManagementEntity.receiptNo}">
						</div>
					</div>
					
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>文件分类：</label> 
							</div>
							<div class="col-md-4"> 
		 							<input  id="viewReceiptType"  class="col-md-12"  type="text" value="${receiptManagementEntity.typeCN}" readonly>
							</div> 
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right" id ="signatureLabel"><span style="color:red;">*</span>发文单位：</label>
								<label class="col-md-12 control-label no-padding-right" id ="receiptLabel" style="display:none"><span style="color:red;">*</span>来文单位：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewReceiptUnitName" class="col-md-12" type="text"  value="${receiptManagementEntity.unitName}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewEmergencyLevel" type="text" class="col-md-12"  value="${receiptManagementEntity.emergencyLevelCN}"  readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">密级：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewSecurityLelvel" type="text" class="col-md-12" name= "securityLevelCN" value="${receiptManagementEntity.securityLevelCN}"  readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right" id="signUserLabel"><span style="color:red;">*</span>签报人 ：</label>
								<label class="col-md-12 control-label no-padding-right" id="startUserLabel" style="display:none"><span style="color:red;">*</span>发起人 ：</label>
							</div>
							<div class="col-md-4">
								<input class="col-md-12" id="viewPublisherName"  type="text" value="${receiptManagementEntity.publisherName }" readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
							</div>
							<div class="col-md-4">
								<input  class="col-md-12"  id="viewReceiptTime" type="text" value="${receiptManagementEntity.draftTime}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
							</div>
							<div class="col-xs-10">
								<div class="widget-main no-padding">
										<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>审核意见</h5>
										<table id="reviewTable" class="table table-striped table-bordered table-hover" style="width:100%;">
											<thead>
												<tr>
													<th>序号</th>
													<th>审核结果</th>
													<th>处理意见</th>
													<th>审核人</th>
													<th>审核时间</th>
													<th>审核类型</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
						</div>
						<!-- <div class="form-group">
							<div class="col-md-2 no-padding-right ">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right ">
								<div id="documentText"></div>
							</div>
						</div> -->
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">附件：</label>
							</div>
							<div class="col-md-10" id="appendixFile"></div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">备注：</label>
							</div>
							<div class="col-md-10">
								<textarea name="remarks" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${receiptManagementEntity.remarks}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">审批意见：</label>
							</div>
							<div class="col-md-10" id="appendixFile">
								<textarea name="receivingUnitComment"  style="height:80px; resize:none;" class="col-md-12" maxlength="255" ></textarea>
							</div>
					</div>
				</form>
				<form class="form-horizontal" role="form"  style="margin-right:100px; margin-top:10px" >
					
				</form>
				</div>
				<div style="margin-right:100px;">
					<button id="discardedBtn"  class="btn btn-xs btn-danger pull-right"  data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>废弃
					</button>
					<button id="rejectInitiatorBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回发起人
					</button>
					<button id="rejectDepartmentHeadBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回生产单位负责人
					</button>
					<button id="leaderAgreeBtn" class="btn btn-xs btn-success pull-right" style="margin-right:15px;display:none">
						<i class="ace-icon fa fa-floppy-o"></i>领导审核
					</button>
					<button id="departMentInsidejoinltySignBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>部门内部会签
					</button>
					<button id="joinltySignBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>处室负责人审核
					</button>
				</div>
		</div>
		<script type="text/javascript">
			var selectedUserIds;
			var selectedUserNames;
			var submitUserDialog;
			 var userDialog1 ;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#rejectInitiatorBtn").hide();
						$("#rejectDepartmentHeadBtn").hide();
						$("#leaderAgreeBtn").hide();
						$("#joinltySignBtn").hide();
						$("#discardedBtn").hide();
					}

					if  ($("#operateTypeVal").val() == "1") {
						 $('#receiptNumberNeedDiv').show();
						 $('#signatureLabel').show();
							$('#receiptLabel').hide();
							$('#signUserLabel').show();
							$('#startUserLabel').hide();
					} else {
						 $('#receiptNumberNeedDiv').hide();
							$('#signatureLabel').hide();
							$('#receiptLabel').show();
							$('#signUserLabel').hide();
							$('#startUserLabel').show();
					}

					//附件
					 var appendixUploaddropzone =new A.uploaddropzone({
						render : "#appendixFile",
						fileId:'${receiptManagementEntity.appendix}'==""?[]:${receiptManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render("2");
					
					var 	conditions =[];
					conditions.push({
						field: 'C_RECEIPT_ID',
						fieldType:'LONG',
						matchType:'EQ',
						value:$("#id").val()
					});
					//审查信息数据列表
					var reviewDatatables = new A.datatables({
							render: '#reviewTable',
							options: {
								"ajax": {
									"url": format_url("/receiptManagement/searchReviewData"),
									"contentType": "application/json",
									"type": "POST",
									"dataType": "JSON",
									"data": function (d) {
										d.conditions = conditions;
										return JSON.stringify(d);
									}
								},
								paging :false,
								multiple : true,
								checked: false,
								ordering: true,
								optWidth: 80,
								order:[[0,'desc']],
								columns: [
										{orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
											var startIndex = meta.settings._iDisplayStart;  
											row.start=startIndex + meta.row;
											return startIndex + meta.row + 1;  
										} },
										{data: "reviewResultCN",width: "15%",orderable: false}, 
										{data: "reviewComment",width: "30%",orderable: false, render : function(data, type, row, meta) {
											if (data ==null) {
												return  "";
											}
											var html = "<span ";
											var displayVal = data;
											if (data.length > 20) {
												displayVal = data.substring(0,20) + "...";
											}
											html += " title = '" +data + "'> "+displayVal+"</span>"
											return html;
										} }, 
										{data: "reviewPersionName",width: "15%",orderable: false},
										{data: "reviewTime",width: "15%",orderable: false},
										{data: "reviewTypeCN",width: "15%",orderable: false}
								]
							}
					}).render();
					
					//部门内部会签选择会签人
					var t2;
					$("#departMentInsidejoinltySignBtn").on("click", function(){
						if ($("#receiptNoVal").val() == "") {
							alert("请输入来文编号");
							return;
						}
						var selectedUserIds ="";
						var selectedUserNames="";
						userDialog1 = new A.dialog({
								title:'会签人' ,
								width: '1100',
								height: '780',
								url:format_url('/receiptManagement/userSelect?singleSelect=2'),
								closed: function(){
									if (data.length > 0) {
										for (var i=0 ;i <data.length; i++) {
											selectedUserIds += data[i].loginName;
											selectedUserNames += data[i].name;
											if (i != data.length -1 ) {
												selectedUserIds += ",";
												selectedUserNames += ",";
											}
										}
										//保存选择的会签人
										$("#productionUnitSelectedpersionIds").val(selectedUserIds);
										$("#productionUnitSelectedpersionNames").val(selectedUserNames);
										//延后执行选择领导审核人
										t2 = window.setTimeout(departMentInsidejointlysing, 100);
									}
									
								}
							}).render(); 
					});
					//选择处室负责人并且部门内部会签
					function departMentInsidejointlysing() {
						window.clearTimeout(t2);
						//选择的部门内部会签人
						var selectedProductionUnitJointlyIds = $("#productionUnitSelectedpersionIds").val();
						var selectedProductionUnitJointlyNames = $("#productionUnitSelectedpersionNames").val();
						var obj = $("#joinltySignForm").serializeObject();
						obj.departMentInsidejointlySign = "5";
						userDialog1 = new A.dialog({
							title:'处室负责人' ,
							width: '1100',
							height: '780',
							url:format_url('/receiptManagement/receivingUserSelect?singleSelect=2'),
							closed: function(){
								if (data.length > 0) {
									var selectedUserIds = "";
									var selectedUserNames =  "";
									for (var i=0 ;i <data.length; i++) {
										selectedUserIds += data[i].loginName;
										selectedUserNames += data[i].name;
										if (i != data.length -1 ) {
											selectedUserIds += ",";
											selectedUserNames += ",";
										}
									}
									obj.userList=selectedUserIds;
									//处室负责人
									obj.jointlySelectedPersion = selectedUserIds;
									obj.jointlySelectedPersionName = selectedUserNames;
									
									obj.departMentInsidejointlySelectedPersion = selectedProductionUnitJointlyIds;
									obj.departMentInsidejointlySelectedPersionName = selectedProductionUnitJointlyNames;
									obj.id = $("#id").val();
									obj.taskId = $("#taskId").val();
									obj.procInstId=$("#procInstId").val();
									
									$.ajax({
										url : format_url("/receiptManagement/departMentInsideJoinltySign"),
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

					//领导审批按钮
					$("#leaderAgreeBtn").on("click", function(){
							if ($("#receiptNoVal").val() == "") {
								alert("请输入来文编号");
								return;
							}
	    					var urls=format_url('/receiptManagement/submitPersonLeader/'+$("#taskId").val());
							var obj = $("#joinltySignForm").serializeObject();
	    					 submitUserDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "领导审核人",
								url:urls,
								closed: function(){
									if(submitUserDialog.resule){
										obj.userList=submitUserDialog.resule.join(","); 
										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();
										//obj.documents=JSON.stringify(contentWysiwyg.getValue());
										obj.receivingUnit = "1";
										
										//领导审核人
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionName = selectUserNames;
										var selectUser = submitUserDialog.resule.join(",");
										var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
										var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
										var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");
										//领导审核人
										obj.userList=selectUserIds;
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionName = selectUserNames;

										$.ajax({
											url : format_url("/receiptManagement/receivingHandle"),
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
						});

						//驳回部门负责人
						$("#rejectDepartmentHeadBtn").on("click", function(){
							var obj = $("#joinltySignForm").serializeObject();
							obj.id = $("#id").val();
							obj.taskId = $("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							obj.receivingUnit = "3";
							$.ajax({
								url : format_url("/receiptManagement/receivingHandle"),
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

						//驳回发起人
						$("#rejectInitiatorBtn").on("click", function(){
							var obj = $("#joinltySignForm").serializeObject();
							obj.id = $("#id").val();
							obj.taskId = $("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							obj.receivingUnit = "4";
							$.ajax({
								url : format_url("/receiptManagement/receivingHandle"),
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
						
						//选择会签人
						var t2;
						$("#joinltySignBtn").on("click", function(){
							if ($("#receiptNoVal").val() == "") {
								alert("请输入来文编号");
								return;
							}
							var obj = $("#joinltySignForm").serializeObject();
							obj.receivingUnit = "2";
							selectedUserIds ="";
							selectedUserNames="";
							userDialog1 = new A.dialog({
									title:'会签人' ,
									width: '1100',
									height: '780',
									url:format_url('/receiptManagement/userSelect?singleSelect=1'),
									closed: function(){
										if (data.length > 0) {
											for (var i=0 ;i <data.length; i++) {
												selectedUserIds += data[i].loginName;
												selectedUserNames += data[i].name;
												if (i != data.length -1 ) {
													selectedUserIds += ",";
													selectedUserNames += ",";
												}
											}
											obj.id = $("#id").val();
											obj.taskId = $("#taskId").val();
											obj.procInstId=$("#procInstId").val();

											//会签选择人
											obj.userList = selectedUserIds;
											obj.jointlySelectedPersion = selectedUserIds;
											obj.jointlySelectedPersionName = selectedUserNames;
											
											$.ajax({
												url : format_url("/receiptManagement/receivingHandle"),
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
						});
						//选择领导审核人并且接收人处理同意
						function jointlysing() {
							window.clearTimeout(t2);
							//选择的会签人
							var selectedJointlyIds = $("#jointlySelectedpersionIds").val();
							var selectedJointlyNames = $("#jointlySelectedpersionNames").val();
							debugger;
							var urls= format_url('/receiptManagement/submitPersonLeaderByGroups/'+$("#taskId").val());
							var obj = $("#joinltySignForm").serializeObject();
							obj.receivingUnit = "2";
	    					submitUserDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "领导审核人",
								url:urls,
								closed: function(){
									if(submitUserDialog.resule){
										//obj.userList= submitUserDialog.resule.join(",");

										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();

										var selectUser = submitUserDialog.resule.join(",");
										var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
										var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
										var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");
										//领导审核人
										obj.userList=selectedJointlyIds;
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionName = selectUserNames;
										//会签选择人
										obj.jointlySelectedPersion = selectedJointlyIds;
										obj.jointlySelectedPersionName = selectedJointlyNames;
										
										$.ajax({
											url : format_url("/receiptManagement/receivingHandle"),
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
						
						$("#discardedBtn").on("click", function(){
							var obj = $("#joinltySignForm").serializeObject();
							obj.id = $("#id").val();
							obj.taskId = $("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							$.ajax({
								url : format_url("/receiptManagement/receiptDiscarded"),
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
						//end
					
				});
			});
		</script>
	</body>
</html>