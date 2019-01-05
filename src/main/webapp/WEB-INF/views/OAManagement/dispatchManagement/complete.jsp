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
			<div class="row" style="height:650px;overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="viewForm">
						<input class="col-md-12" id="id"  value="${dispatchManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewRemindTimeValue" type="hidden" value="${dispatchManagementEntity.feedBackRemindTime}" >
						<input class="col-md-12" id="viewDispatchNumPreVal" type="hidden" value="${dispatchNumPreVal}" >
						<input class="col-md-12" id="viewDispatchNumYearVal" type="hidden" value="${dispatchNumYearVal}" >
						<input class="col-md-12" id="viewDispatchNumNoVal" type="hidden" value="${dispatchNumNo}" >
						<input class="col-md-12" id="viewRecipientIdVal" type="hidden" value="${dispatchManagementEntity.recipientId}" >
						<input class="col-md-12" id="viewApprovalStatus" type="hidden" value="${dispatchManagementEntity.approvalStatus}" >
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
								<input  id="viewFeedBackRemindTime" type="text"  value="${dispatchManagementEntity.feedBackRemindTime}"  readonly>
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
						<div class="form-group" style="display:none">
							<div class="col-md-2 no-padding-right ">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right; " >
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
								<textarea name="remarks" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${dispatchManagementEntity.remarks}</textarea>
							</div>
						</div>
				
				</form>
			</div>
			<div style="margin-right:20px;margin-top:5px;">
			<!-- 	<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					关闭
				</button> -->
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
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
				/* 	var documentUploaddropzone =new A.uploaddropzone({
						render : "#documentText",
						fileId:'${dispatchManagementEntity.documents}'==""?[]:${dispatchManagementEntity.documents},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render("1"); */
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
// 										{name:'createLink', className:'btn-pink'},
// 										{name:'unlink', className:'btn-pink'},
// 										null,
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
					
					/* val approvalStatus = $("#viewApprovalStatus").val();
					if (approvalStatus) */
					
					
					
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
										{data: "reviewComment",width: "30%",orderable: false}, 
										{data: "reviewPersionName",width: "15%",orderable: false},
										{data: "reviewTime",width: "15%",orderable: false},
										{data: "reviewTypeCN",width: "15%",orderable: false}
								]
							}
					}).render();
					//反馈信息数据列表
					varfeedbackDatatables = new A.datatables({
							render: '#feedbackTable',
							options: {
								"ajax": {
									"url": format_url("/dispatchManagement/searchFeedBackData"),
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
										{data: "feedbackComment",width: "20%",orderable: false}, 
										{data: "feedBackFileName",width: "30%",orderable: false}, 
										{data: "feedbackPersionName",width: "20%",orderable: false},
										{data: "feedbackTime",width: "20%",orderable: false}
								]
							}
						}).render();
					
				});
			});
		</script>
	</body>
</html>