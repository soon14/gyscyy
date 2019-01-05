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
			<div class="row" style="overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="javascript:void(0);" onclick="firstPage()">首页</a>
						</li>
						<li class="active">OA管理</li>
						<li class="active">发文管理</li>
						<li class="active">部内发文</li>
						<li class="active">发布</li>
					</ul><!-- /.breadcrumb -->
					<div style="margin-right:180px;margin-top:10px;">
						<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
							<i class="glyphicon glyphicon-share-alt"></i>
							返回
						</button>
					</div>
					<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
				</div>
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
					<input class="col-md-12" id="id" name="id"  value="${dispatchManagementEntity.id}" type="hidden">
					<input class="col-md-12" id="editDrafterTimeValue" type="hidden" value="${drafterTime}" >
					<input class="col-md-12" id="editRemindTimeValue" type="hidden" value="${dispatchManagementEntity.feedBackRemindTime}" >
					<input class="col-md-12" id="editDispatchNumPreVal" type="hidden" value="${dispatchNumPreVal}" >
					<input class="col-md-12" id="editDispatchNumYearVal" type="hidden" value="${dispatchNumYearVal}" >
					<input class="col-md-12" id="editDispatchNumNoVal" type="hidden" value="${dispatchNumNo}" >
					<input class="col-md-12" id="editRecipientTypeVal" type="hidden" value="${dispatchManagementEntity.recipientType}" >
					<input class="col-md-12" id="editRecipientIdVal" type="hidden" value="${dispatchManagementEntity.recipientId}" >
					<input class="col-md-12" id="editRecipientNameVal" type="hidden" value="${dispatchManagementEntity.recipientName}" >
					<input class="col-md-12" name="approvalStatus" type="hidden" value="${dispatchManagementEntity.approvalStatus}" >
					<input class="col-md-12" name="drafterId" type="hidden" value="${dispatchManagementEntity.drafterId}" >
					<div class="form-group">
						<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建集团贵阳勘测设计研究院有限公司发文处理单</label> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
						</div>
						<div class="col-md-10" > 
	 						<input class="col-md-12" id="editTitle" name="title" type="text" placeholder="" maxlength="64" value="${dispatchManagementEntity.title}">
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">发文字号：</label> 
						</div>
						<div class="col-md-10"> 
							<div  style="float:left;width:120px;margin-right:5px">
								<select id="editDispatchNum_Head"></select>
							</div>
							<div  style="float:left;width:100px;margin-right:5px">
								<select id="editDispatchNum_Year"></select>
							</div>
	 						<input  id="editDispatchNum_Num" type="text" name= "dispatchNameNum" maxlength="4" value="${dispatchNumNo}"  style="width:50px">
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文类型：</label> 
						</div>
						<div class="col-md-4"> 
	 							<select id="editDispatchType"></select>
						</div> 
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文部门：</label>
						</div>
						<div class="col-md-4">
							<select id="editDispatchDepartMent"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
						</div>
						<div class="col-md-4">
							<select id="editEmergencyLevel"></select>
						</div>
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">密级：</label>
						</div>
						<div class="col-md-4">
							<select id="editSecurityLevel"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿人：</label>
						</div>
						<div class="col-md-4">
							<input class="col-md-12" id="editDrafterName" name= "drafterName" type="text" placeholder="" maxlength="64" value="${dispatchManagementEntity.drafterName }" readonly>
						</div>
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
						</div>
						<div class="col-md-4">
							<div id="editDrafterTimeDiv"></div>
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
							<div id="editFeedBackRemindTimeDiv"></div>
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
							<div id="editRecipientDiv"></div>
						</div>
					</div>
					<div class="form-group">
							<div class="col-md-2"></div>
							<div class="col-md-10">
								<div class="widget-main no-padding">
										<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>审核意见</h5>
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
					<div class="form-group" style="display:none">
						<div class="col-md-2 no-padding-right ">
							<label class="col-md-12 control-label no-padding-right">公文正文：</label>
						</div>
						<div class="col-md-10 no-padding-right">
							<div  id="editDocumentFile"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">附件：</label>
						</div>
						<div class="col-md-10" id="editAppendixFile"></div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">备注：</label>
						</div>
						<div class="col-md-10">
							<textarea name="remarks" placeholder="请输入备注" style="height:80px; resize:none;" class="col-md-12" maxlength="255">${dispatchManagementEntity.remarks}</textarea>
						</div>
					</div>

				</form>
				
			</div>
			<div style="margin-right:200px;">
				<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					发布
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					//发文文号
					//文号前缀
					var editDispatchNumHeadCombobox = new A.combobox({
						render : "#editDispatchNum_Head",
						name : "dispatchNameSymbolId",
						datasource : ${dispatchNumPreComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editDispatchNumHeadCombobox.setValue($("#editDispatchNumPreVal").val());
					
					//文号年
					var editDispatchNumYearCombobox = new A.combobox({
						render : "#editDispatchNum_Year",
						name : "dispatchNameYear",
						datasource : ${dispatchNumYearComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editDispatchNumYearCombobox.setValue($("#editDispatchNumYearVal").val());
					
					//发文类型
					var editDispatchTypeCombobox = new A.combobox({
						render : "#editDispatchType",
						name : "type",
						datasource : ${dispatchTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editDispatchTypeCombobox.setValue(${dispatchManagementEntity.type});
					
					intReleaseStatus();
					$("#editDispatchType").change(function() {
						intReleaseStatus();
					});
					
					function intReleaseStatus() {
						var type = $("#editDispatchType").val();
						if (type == '5' || type == '6' || type == '7' || type == '8') {
							$(":radio[name='releaseStatus'][value='2']").prop("checked", "checked");
							$('#addRecipientNeed').show();
							$('input[name="releaseStatus"]').attr("disabled",true);
						} else {
							$('input[name="releaseStatus"]').attr("disabled",false);
						}
					}
					
					//发文部门
					var editDispatchDepartMentCombobox = new A.combobox({
						render : "#editDispatchDepartMent",
						name : "departmentID",
						datasource : ${departmentComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editDispatchDepartMentCombobox.setValue(${dispatchManagementEntity.departmentID});
					
					//缓急程度
					var editEmergencyLevelCombobox = new A.combobox({
						render : "#editEmergencyLevel",
						name : "emergencyLevel",
						datasource : ${urgencyComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editEmergencyLevelCombobox.setValue(${dispatchManagementEntity.emergencyLevel});
					
					//密级
					var editSecurityLevelCombobox = new A.combobox({
						render : "#editSecurityLevel",
						name : "securityLelvel",
						datasource : ${denseComboBox},
						allowBlank: false,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editSecurityLevelCombobox.setValue(${dispatchManagementEntity.securityLelvel});

					//拟稿时间 
					var editDrafterTimePicker = new A.my97datepicker({
						id: "editDrafterTime",
						name:"draftTime",
						render:"#editDrafterTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm "
						}
					}).render();
					editDrafterTimePicker.setValue($("#editDrafterTimeValue").val());
					
					//是否需要反馈 ${dispatchManagementEntity.feedBack}
					$('input[name="feedBack"][value="' + ${dispatchManagementEntity.feedBack} + '"]').attr("checked",true);
					var feedbackVal = $('input[name="feedBack"]:checked').val();
					if ("2"== feedbackVal) {
						$('#addFeedBackRemindTimeNeed').show();
					} else {
						$('#addFeedBackRemindTimeNeed').hide();
					}
					//全部挂网/特定接收人
					$('input[name="releaseStatus"][value="' + ${dispatchManagementEntity.releaseStatus} + '"]').attr("checked",true);
					var releaseVal = $('input[name="releaseStatus"]:checked').val();
					if ("2"== releaseVal) {
						$('#addRecipientNeed').show();
					} else {
						$('#addRecipientNeed').hide();
					}
					//反馈提醒时间 
					var editFeedBackRemindTimePicker = new A.my97datepicker({
						id: "editFeedBackRemindTime",
						name:"feedBackRemindTime",
						render:"#editFeedBackRemindTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					editFeedBackRemindTimePicker.setValue($("#editRemindTimeValue").val());
					
					//接受人
					var editRecipientCombotree = new A.combotree({
						render: "#editRecipientDiv",
						name: 'recipientId',
						//返回数据待后台返回TODO
						datasource: ${recipientTree},
						width:"210px",
						multiple: true,
						options: {
							treeId: 'editRecipientId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();

					var editRecipientId =  $("#editRecipientIdVal").val();
					var recipientType = $("#editRecipientTypeVal").val();
					var editRecipientName=  $("#editRecipientNameVal").val();
					
					if (editRecipientId != "") {
						editRecipientCombotree.spanNode.html("<font  color='black'>"+editRecipientName+"</font>");
						var recipientIds = editRecipientId.split(',');
						var editRecipientTreeObj = $.fn.zTree.getZTreeObj("editRecipientId");
						var allTreeNodes = editRecipientTreeObj.getNodes();
						for (var i =0; i< recipientIds.length; i++) {
							for (var j=0;j<allTreeNodes.length; j++) {
								if (allTreeNodes[j].isParent) {
									var editRecipientUserNodes = allTreeNodes[j].children;
									for (var k=0;k<editRecipientUserNodes.length; k++) {
										if (editRecipientUserNodes[k].id == recipientIds[i] ){
											editRecipientTreeObj.checkNode(editRecipientUserNodes[k], true, true);
											editRecipientUserNodes[k].checked =  true;
										}
									}
									
								} else {
									if (allTreeNodes[j].id ==  recipientIds[i] && allTreeNodes[j].nodeType == "2") {
										editRecipientTreeObj.checkNode(allTreeNodes[j], true, true);
										allTreeNodes[j].checked =  true;
									}
								}
							}
						}
					}
					//公文正文
/* 					var documentFile =new A.uploaddropzone({
						render : "#editDocumentFile",
						fileId: ${dispatchManagementEntity.documents},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						acceptedFiles:'.doc, .docx'
					}).render("1"); */
					/* var contentWysiwyg = new A.wysiwyg({
						render : "#editDocumentFile",
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
					contentWysiwyg.setValue(${dispatchManagementEntity.documents}); */
 					//附件
					var appendixFile = new A.uploaddropzone({
						render : "#editAppendixFile",
						fileId:${dispatchManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render("2"); 
					
					//反馈和不反馈变更响应
					$('input[name="feedBack"]').change(function() {
						var value = $('input[name="feedBack"]:checked').val();
						if (value == "2") {
							$('#addFeedBackRemindTimeNeed').show();
							addDipatchForm.settings.rules["feedBackRemindTime"]={required:true};
						}  else {
							$('#addFeedBackRemindTimeNeed').hide();
							addDipatchForm.settings.rules["feedBackRemindTime"]={};
						}
					});

					//全部挂网和特定接收人变更响应
					$('input[name="releaseStatus"]').change(function() {
						var value = $('input[name="releaseStatus"]:checked').val();
						if (value == "2") {
							$('#addRecipientNeed').show();
							//addDipatchForm.settings.rules["recipientId"]={required:true};
						}  else {
							$('#addRecipientNeed').hide();
							//addDipatchForm.settings.rules["recipientId"]={};
						}
					}); 
					
					var id = $('#id').val();
					var addDipatchForm =$('#editForm').validate({
						debug:true,
						rules:  {
							title:{required:true, maxlength:64},
							type:{required:true},
							draftTime:{required:true},
							departmentID:{ required:true}
						}, 
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/dispatchManagement/releaseDispatch/");
							var obj = $("#editForm").serializeObject();
							
							//发文字号
							var preValue =  $("#editDispatchNum_Head  option:selected").text();
							if (preValue =="") {
								alert("请选择发文文号");
								return;
							}
							obj.dispatchNameSymbolName = preValue;
							var yearValue = $("#editDispatchNum_Year").val();
							if (yearValue == "") {
								alert("请选择发文年号");
								return;
							}
							var numValue = $("#editDispatchNum_Num").val();
							if (numValue == "" || numValue.length !=4) {
								alert("请输入4位发文序号");
								return;
							}
							obj.dispatchName=preValue + yearValue + numValue;
							
							//发文部门名称
							var departMentName = $("#editDispatchDepartMent  option:selected").text();
							obj.departmentName = departMentName;
							obj.isRelease = "1";
							obj.releasePersionId = obj.drafterId;
							var value = $('input[name="releaseStatus"]:checked').val();
							obj.releaseStatus = value;
							
							var val = editRecipientCombotree.getValue();
							if (val =="") {
								alert("请选择发布人");
								return;
							}

							var treeObj = $.fn.zTree.getZTreeObj("editRecipientId");
							var nodes = treeObj.getCheckedNodes(true);
							var recipientName ="";
							var recipientIds = "";
							if (nodes.length > 0 ) {
								obj.recipientType = "2";
								for (var i=0 ; i<nodes.length  ; i++) {
									if(!nodes[i].isParent &&  nodes[i].nodeType != 1){
										recipientName +=  nodes[i].name;
										recipientIds += nodes[i].id;
										if (i != nodes.length -1) {
											recipientName += ",";
											recipientIds += ",";
										}
									}
								}
							
							}
							obj.recipientName = recipientName;
							obj.recipientId = recipientIds;
							
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());

							obj.appendix = JSON.stringify(appendixFile.getValue());

							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									A.loadPage({
										render : '#page-container',
										url : format_url('/dispatchManagement/index/')
									});
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
						$("#editForm").submit();
					});

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

					//由添加迁移页返回到列表页
    				$("#backBtnAddTrainPlan").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/dispatchManagement/index/")
    					});
    				});
					
					/* //会签数据列表
					var jointlySignDatatables = new A.datatables({
							render: '#allCheckTable',
							options: {
								"ajax": {
									"url": format_url("/dispatchManagement/searchJointlySingData"),
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
									"url": format_url("/dispatchManagement/searchLeaderApprovalData"),
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
						}).render(); */
				});
			});
		</script>
	</body>
</html>