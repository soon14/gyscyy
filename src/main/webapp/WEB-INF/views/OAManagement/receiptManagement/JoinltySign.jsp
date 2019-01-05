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
				<input class="col-md-12" id="id"  value="${receiptManagementEntity.id}" type="hidden">
				<input class="col-md-12" id="type" value="${type}" type="hidden">
				<input class="col-md-12" id="viewPublisherIdVal" type="hidden" value="${receiptManagementEntity.publisherId}" >
				<input class="col-md-12" id="operateTypeVal"  value="${receiptManagementEntity.operateType}" type="hidden">
				<input class="col-md-12" id="outSelectedpersionIds" type="hidden" >
				<input class="col-md-12" id="outSelectedpersionNames" type="hidden" >
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="joinltySignForm">
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
								<label class="col-md-12 control-label no-padding-right">来文编号：</label>
							</div>
							<div class="col-md-4">
								<input type="text" style="width:100%"  maxlength="15" value="${receiptManagementEntity.receiptNo}" readonly >
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>文件分类：</label> 
							</div>
							<div class="col-md-4"> 
		 							<input  id="viewDispatchType"  class="col-md-12"  type="text" value="${receiptManagementEntity.typeCN}" readonly>
							</div> 
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right" id ="signatureLabel"><span style="color:red;">*</span>发文单位：</label>
								<label class="col-md-12 control-label no-padding-right" id ="receiptLabel" style="display:none"><span style="color:red;">*</span>来文单位：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewUnitName" class="col-md-12" type="text"  value="${receiptManagementEntity.unitName}" readonly>
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
								<input  id="viewSecurityLelvel" type="text" class="col-md-12" value="${receiptManagementEntity.securityLevelCN}"  readonly>
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
								<input  class="col-md-12"  id="viewDispatchTime" type="text" value="${receiptManagementEntity.draftTime}" readonly>
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
						
						<!-- <div class="form-group">
							<div class="col-md-2 no-padding-right; ">
							</div>
							<div class="col-xs-10">
								<div class="widget-main no-padding">
										<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>接收单位处理信息</h5>
										<table id="receivingHandleTable" class="table table-striped table-bordered table-hover" style="width:100%;">
											<thead>
												<tr>
													<th>序号</th>
													<th>处理意见</th>
													<th>处理建议</th>
													<th>处理人</th>
													<th>处理时间</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
						</div> -->
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">审批意见：</label>
							</div>
							<div class="col-md-10" id="appendixFile">
								<textarea name="jointlySignComment"  style="height:80px; resize:none;" class="col-md-12" maxlength="255" ></textarea>
							</div>
					</div>
				</form>
				</div>
				<div style="margin-right:100px;">
					<button id="discardedBtn"  class="btn btn-xs btn-danger pull-right"  data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>废弃
					</button>
					<button id="rejectStartBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回发起人
					</button>
					<button id="rejectDepartmentBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回生产单位负责人
					</button>
					<button id="rejectReceivingBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回处室办理人
					</button>
					<button id="outDepartMentJointlyBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>外部部门会签
					</button>
					<button id="agreeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>领导审核
					</button>
				</div>
		</div>
		<script type="text/javascript">
		var userDialog1 ;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#agreeBtn").hide();
						$("#rejectStartBtn").hide();
						$("#rejectDepartmentBtn").hide();
						$("#rejectReceivingBtn").hide();
						$("#discardedBtn").hide();
					}
					
					if  ($("#operateTypeVal").val() == "1") {
						$('#signNoLabel').show();
						$('#receiptNoLabel').hide();
						$('#signatureLabel').show();
						$('#receiptLabel').hide();
						$('#signUserLabel').show();
						$('#startUserLabel').hide();
					} else {
						$('#signNoLabel').hide();
						$('#receiptNoLabel').show();
						$('#signatureLabel').hide();
						$('#receiptLabel').show();
						$('#signUserLabel').hide();
						$('#startUserLabel').show();
					}
					/* //发文正文
					var contentWysiwyg = new A.wysiwyg({
						render : "#documentText",
						options:{
							height:"300px",
							toolbar:[
										'font',
										null,
										'fontSize',
										null,
										{name:'bold', className:'btn-info'},
										{name:'italic', className:'btn-info'},
										{name:'strikethrough', className:'btn-info'},
										{name:'underline', className:'btn-info'},
										null,
										{name:'insertunorderedlist', className:'btn-success'},
										{name:'insertorderedlist', className:'btn-success'},
										{name:'outdent', className:'btn-purple'},
										{name:'indent', className:'btn-purple'},
										null,
										{name:'justifyleft', className:'btn-primary'},
										{name:'justifycenter', className:'btn-primary'},
										{name:'justifyright', className:'btn-primary'},
										{name:'justifyfull', className:'btn-inverse'},
										null,
										{name:'insertImage', className:'btn-success'},
										null,
										'foreColor',
										null,
										{name:'undo', className:'btn-grey'},
										{name:'redo', className:'btn-grey'}
								    ],
						}
					}).render();
					contentWysiwyg.setValue(${receiptManagementEntity.documents}); */
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
				/* 	//接收单位处理信息数据列表
					var handleDatatables = new A.datatables({
							render: '#receivingHandleTable',
							options: {
								"ajax": {
									"url": format_url("/receiptManagement/searchReceivingUnitData"),
									"contentType": "application/json",
									"type": "POST",
									"dataType": "JSON",
									"data": function (d) {
										d.conditions = conditions;
										return JSON.stringify(d);
									}
								},
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
										{data: "handleResultCN",width: "20%",orderable: false}, 
										{data: "handleComment",width: "30%",orderable: false}, 
										{data: "handlePersionName",width: "20%",orderable: false},
										{data: "handleTime",width: "20%",orderable: false}
								]
							}
					}).render(); */
					
					//同意按钮
					$("#agreeBtn").on("click", function(){
						var urls=format_url('/receiptManagement/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#joinltySignForm").serializeObject();
						obj.jointlySign = "1";
    					 submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "领导审核人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
									 
									obj.id = $("#id").val();
									obj.taskId = $("#taskId").val();
									obj.procInstId=$("#procInstId").val();
									var selectUser = submitUserDialog.resule.join(",");
									var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
									var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
									var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");
									//领导审核人
									obj.userList=selectUserIds;
									obj.leaderSelectedPersion = selectUserIds;
									obj.leaderSelectedPersionName = selectUserNames;
									$.ajax({
										url : format_url("/receiptManagement/receiptJoinltySign"),
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
											} else if(result.result=="noPerson"){
												alert("无可以审核的部门领导");
											} else{
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
					//驳回处室办理人
					$("#rejectReceivingBtn").on("click", function(){
						var obj = $("#joinltySignForm").serializeObject();
						obj.jointlySign = "2";
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						//obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						$.ajax({
							url : format_url("/receiptManagement/receiptJoinltySign"),
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
					//驳回生产部门负责人
					$("#rejectDepartmentBtn").on("click", function(){
						var obj = $("#joinltySignForm").serializeObject();
						obj.jointlySign = "3";
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						//obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						$.ajax({
							url : format_url("/receiptManagement/receiptJoinltySign"),
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
					$("#rejectStartBtn").on("click", function(){
						var obj = $("#joinltySignForm").serializeObject();
						obj.jointlySign = "4";
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						//obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						$.ajax({
							url : format_url("/receiptManagement/receiptJoinltySign"),
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

				//外部部门会签选择会签人
				var t2;
				$("#outDepartMentJointlyBtn").on("click", function(){
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
									$("#outSelectedpersionIds").val(selectedUserIds);
									$("#outSelectedpersionNames").val(selectedUserNames);
									//延后执行选择领导审核人
									t2 = window.setTimeout(jointlysing, 100);
								}
								
							}
						}).render(); 
				});
				//选择领导审核人并且外部部门会签
				function jointlysing() {
					window.clearTimeout(t2);
					//选择的外部部门会签人
					var selectedJointlyIds = $("#outSelectedpersionIds").val();
					var selectedJointlyNames = $("#outSelectedpersionNames").val();
					var urls= format_url('/receiptManagement/submitPersonLeaderByGroups/'+$("#taskId").val());
					var obj = $("#joinltySignForm").serializeObject();
					obj.jointlySign = "5";
					submitUserDialog = new A.dialog({
						width: 850,
						height: 481,
						title: "领导审核人",
						url:urls,
						closed: function(){
							if(submitUserDialog.resule){
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
								obj.outSelectedPersion = selectedJointlyIds;
								obj.outSelectedPersionName = selectedJointlyNames;
								
								$.ajax({
									url : format_url("/receiptManagement/receiptJoinltySign"),
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
		</script>
	</body>
</html>