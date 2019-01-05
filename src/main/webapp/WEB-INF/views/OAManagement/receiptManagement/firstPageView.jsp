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
			<div class="row" style="height:750px;overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="viewForm">
						<input class="col-md-12" id="id"  value="${receiptManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewRecipientIdVal" type="hidden" value="${receiptManagementEntity.publisherId}" >
						<input class="col-md-12" id="operateTypeVal"  value="${receiptManagementEntity.operateType}" type="hidden">
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
								<input type="text" style="width:100%"  maxlength="64" value="${receiptManagementEntity.receiptNo}" readonly >
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
								<input  class="col-md-12"  id="viewDispatchTime" type="text" value="${receiptManagementEntity.draftTime}" readonly>
							</div>
						</div>
					<!-- 	<div class="form-group">
							<div class="col-md-2 no-padding-right ">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right; " >
								<div id="documentText"></div>
							</div>
						</div> -->
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
									</div>
									<div class="col-xs-10">
								<div class="widget-main no-padding">
										<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>审查意见</h5>
										<table id="reviewTable" class="table table-striped table-bordered table-hover" style="width:100%;">
											<thead>
												<tr>
													<th>序号</th>
													<th>审核意见</th>
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
								<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>审核意见</h5>
								<table id="reviewTable" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th>序号</th>
											<th>审核意见</th>
											<th>处理意见</th>
											<th>审查人</th>
											<th>审查时间</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
				</div>
				<div class="form-group">
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
						</div>
				<div class="form-group">
					<div class="col-md-2 no-padding-right; ">
							</div>
							<div class="col-xs-10">
							<div class="widget-main no-padding">
							<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>会签审批</h5>
								<table id="allCheckTable" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th>序号</th>
											<th>审批决策</th>
											<th>审批意见</th>
											<th>审批人</th>
											<th>审批时间</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
				</div>
				<div class="form-group"> 
					<div class="col-md-2 no-padding-right; ">
							</div>
							<div class="col-xs-10">
							<div class="widget-main no-padding">
								<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>领导审批</h5>
								<table id="leaderReviewTable" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th>序号</th>
											<th>审批决策</th>
											<th>审批意见</th>
											<th>审批人</th>
											<th>审批时间</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
				</div> -->
				</form>
			</div>
			<div style="margin-right:20px;margin-top:10px;">
				<button id="backBtn"  class="btn btn-xs btn-danger pull-right">
					<i class="ace-icon fa fa-times"></i>
					返回
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					
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
								ordering: false,
								searching: true,
								checked: false,
								bStateSave: true,
								optWidth: 80,
								order: [[ 0, "desc" ]],
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

					/* //会签数据列表
					var jointlySignDatatables = new A.datatables({
							render: '#allCheckTable',
							options: {
								"ajax": {
									"url": format_url("/receiptManagement/searchJointlySingData"),
									"contentType": "application/json",
									"type": "POST",
									"dataType": "JSON",
									"data": function (d) {
										d.conditions = conditions;
										return JSON.stringify(d);
									}
								},
								multiple : true,
								ordering: false,
								searching: true,
								checked: false,
								bStateSave: true,
								optWidth: 80,
								order: [[ 0, "desc" ]],
								columns: [
										{orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
											var startIndex = meta.settings._iDisplayStart;  
											row.start=startIndex + meta.row;
											return startIndex + meta.row + 1;  
										} },
										{data: "signResultCN",width: "20%",orderable: false}, 
										{data: "signComment",width: "30%",orderable: false}, 
										{data: "signPersionName",width: "20%",orderable: false},
										{data: "signTime",width: "20%",orderable: false}
								]
							}
						}).render();

					//领导审批数据列表
					var leaderApprovalDatatables = new A.datatables({
							render: '#leaderReviewTable',
							options: {
								"ajax": {
									"url": format_url("/receiptManagement/searchLeaderApprovalData"),
									"contentType": "application/json",
									"type": "POST",
									"dataType": "JSON",
									"data": function (d) {
										d.conditions = conditions;
										return JSON.stringify(d);
									}
								},
								multiple : true,
								ordering: false,
								searching: true,
								checked: false,
								bStateSave: true,
								optWidth: 80,
								order: [[ 0, "desc" ]],
								columns: [
										{orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
											var startIndex = meta.settings._iDisplayStart;  
											row.start=startIndex + meta.row;
											return startIndex + meta.row + 1;  
										} },
										{data: "approvalResultCN",width: "20%",orderable: false}, 
										{data: "approvalComment",width: "30%",orderable: false}, 
										{data: "approvalPersionName",width: "20%",orderable: false},
										{data: "approvalTime",width: "20%",orderable: false}
								]
							}
						}).render();

					//接收单位处理信息数据列表
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
					
					
					$("#backBtn").on("click", function(){
						firstPage();
					});
				});
			});
		</script>
	</body>
</html>