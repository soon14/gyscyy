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
						<input class="col-md-12" id="id"  value="${dispatchManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="type" value="${type}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewRemindTimeValue" type="hidden" value="${dispatchManagementEntity.feedBackRemindTime}" >
						<input class="col-md-12" id="viewDispatchNumPreVal" type="hidden" value="${dispatchNumPreVal}" >
						<input class="col-md-12" id="viewDispatchNumYearVal" type="hidden" value="${dispatchNumYearVal}" >
						<input class="col-md-12" id="viewDispatchNumNoVal" type="hidden" value="${dispatchNumNo}" >
						<input class="col-md-12" id="viewRecipientIdVal" type="hidden" value="${dispatchManagementEntity.recipientId}" >
						<div class="form-group">
							<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建集团贵阳勘测设计研究院有限公司发文处理单</label> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
							</div>
							<div class="col-md-10" > 
		 						<input class="col-md-12" id="viewTitle" name="title" type="text" value="${dispatchManagementEntity.title}" readonly>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">发文字号：</label> 
							</div>
							<div class="col-md-10"> 
		 						<input  id="viewDispatchNum" type="text" name= "dispatchName" class="col-md-12" value="${dispatchManagementEntity.dispatchName}"  readonly>
		 					</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文类型：</label> 
							</div>
							<div class="col-md-4"> 
		 							<input  id="viewDispatchType"  class="col-md-12"  type="text" value="${dispatchManagementEntity.typeCN}" readonly>
							</div> 
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文部门：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewDispatchDepartment" class="col-md-12" type="text"  value="${dispatchManagementEntity.departmentName}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewEmergencyLevel" type="text" class="col-md-12"  value="${dispatchManagementEntity.emergencyLevelCN}"  readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">密级：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewSecurityLelvel" type="text" class="col-md-12" name= "dispatchNameNum" value="${dispatchManagementEntity.securityLelvelCN}"  readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿人：</label>
							</div>
							<div class="col-md-4">
								<input class="col-md-12" id="viewDrafterName"  type="text" value="${dispatchManagementEntity.drafterName }" readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
							</div>
							<div class="col-md-4">
								<input  class="col-md-12"  id="viewDispatchTime" type="text" value="${dispatchManagementEntity.draftTime}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"> 
										<input name="feedBack" type="radio" value="1" class="ace"/> 
										&nbsp;<span class="lbl bigger-100">不需要反馈</span>
								</label>
							</div>
							<div class="col-md-3">
								<label class="control-label no-padding-right"> 
									<input name="feedBack" type="radio" value="2" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100">需要反馈</span>
								</label>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;" id="addFeedBackRemindTimeNeed">*</span>反馈提醒时间：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewFeedBackRemindTime" type="text" class="col-md-12" value="${dispatchManagementEntity.feedBackRemindTime}"  readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"> 
											<input name="releaseStatus" type="radio" value="1" class="ace"/> 
											&nbsp;<span class="lbl bigger-100">全部挂网&nbsp;&nbsp;&nbsp;</span>
									</label>
							</div>
							<div class="col-md-3">
								<label class="control-label no-padding-right"> 
										<input name="releaseStatus" type="radio" value="2" class="ace input" /> 
										&nbsp;<span class="lbl bigger-100">特定接收人</span>
									</label>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;" id="addRecipientNeed">*</span>接收人：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewRecipient" class="col-md-12 no-padding-right" type="text"  value="${dispatchManagementEntity.recipientName}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"></label>
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
								<label class="col-md-12 control-label no-padding-right">审批意见：</label>
							</div>
							<div class="col-md-10" >
								<textarea name="jointlySignComment"  style="height:80px; resize:none;" class="col-md-12" maxlength="255" ></textarea>
							</div>
						</div>
						
						<div class="form-group" style="display:none">
							<div class="col-md-2 no-padding-right ">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right ">
								<div id="documentText"></div>
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
								<textarea name="remarks"  style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${dispatchManagementEntity.remarks}</textarea>
							</div>
						</div>
					
				</form>
				<form class="form-horizontal" role="form"  style="margin-right:100px; margin-top:10px" >
					
				</form>
				</div>
				<div style="margin-right:100px;">
					<button id="discardedBtn"  class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>废弃
					</button>
					<button id="reject1Btn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回到发起人
					</button> 
					<button id="rejectBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
						<i class="ace-icon fa fa-times"></i>驳回部门负责人
					</button> 
					<button id="agreeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>同意
					</button>
				</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#agreeBtn").hide();
						$("#rejectBtn").hide();
						$("#reject1Btn").hide();
						$("#discardedBtn").hide();
					}
					//是否需要反馈 ${dispatchManagementEntity.feedBack}
					$('input[name="feedBack"]').attr("disabled", true);
					$('input[name="feedBack"][value="' + ${dispatchManagementEntity.feedBack} + '"]').attr("checked",true);
					var feedbackVal = $('input[name="feedBack"]:checked').val();
					if ("2"== feedbackVal) {
						$('#addFeedBackRemindTimeNeed').show();
					} else {
						$('#addFeedBackRemindTimeNeed').hide();
					}
					//全部挂网/特定接收人
					$('input[name="releaseStatus"]').attr("disabled", true);
					$('input[name="releaseStatus"][value="' + ${dispatchManagementEntity.releaseStatus} + '"]').attr("checked",true);
					var releaseVal = $('input[name="releaseStatus"]:checked').val();
					if ("2"== releaseVal) {
						$('#addRecipientNeed').show();
					} else {
						$('#addRecipientNeed').hide();
					}
					//发文正文
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
					contentWysiwyg.setValue(${dispatchManagementEntity.documents});
					//附件
					 var appendixUploaddropzone =new A.uploaddropzone({
						render : "#appendixFile",
						fileId:'${dispatchManagementEntity.appendix}'==""?[]:${dispatchManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render("2");
					
					var 	conditions =[];
					conditions.push({
						field: 'C_DISPATCH_ID',
						fieldType:'LONG',
						matchType:'EQ',
						value:$("#id").val()
					});
					//审查信息数据列表
					var reviewDatatables = new A.datatables({
							render: '#reviewTable',
							options: {
								"ajax": {
									"url": format_url("/dispatchManagement/searchReviewData"),
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

					$("#agreeBtn").on("click", function(){
						//var urls=format_url('/dispatchManagement/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#joinltySignForm").serializeObject();
						obj.approvalStatus = "6";
						obj.jointlySign = "1";
    					/* submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "领导审核人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
									obj.userList=submitUserDialog.resule.join(","); */
									obj.id = $("#id").val();
									obj.taskId = $("#taskId").val();
									obj.procInstId=$("#procInstId").val();
									obj.documents=JSON.stringify(contentWysiwyg.getValue());
									$.ajax({
										url : format_url("/dispatchManagement/dispatchJoinltySign"),
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
						//	}
						//	}
						//}).render();
					});

					$("#rejectBtn").on("click", function(){
						var obj = $("#joinltySignForm").serializeObject();
						obj.approvalStatus = "7";
						obj.jointlySign = "2";
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						$.ajax({
							url : format_url("/dispatchManagement/dispatchJoinltySign"),
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
					
					$("#reject1Btn").on("click", function(){
						var obj = $("#joinltySignForm").serializeObject();
						obj.approvalStatus = "11";
						obj.jointlySign = "3";
						obj.id = $("#id").val();
						obj.taskId = $("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						$.ajax({
							url : format_url("/dispatchManagement/dispatchJoinltySign"),
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
							url : format_url("/dispatchManagement/dispatchDiscarded"),
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
					
					
				});
			});
		</script>
	</body>
</html>