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
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="reviewForm">
						<input class="col-md-12" id="id"  value="${dispatchManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="type" value="${type}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewRemindTimeValue" type="hidden" value="${dispatchManagementEntity.feedBackRemindTime}" >
						<input class="col-md-12" id="viewDispatchNumPreVal" type="hidden" value="${dispatchNumPreVal}" >
						<input class="col-md-12" id="viewDispatchNumYearVal" type="hidden" value="${dispatchNumYearVal}" >
						<input class="col-md-12" id="viewDispatchNumNoVal" type="hidden" value="${dispatchNumNo}" >
						<input class="col-md-12" id="viewRecipientIdVal" type="hidden" value="${dispatchManagementEntity.recipientId}" >
						<input class="col-md-12" id="viewReviewVal" type="hidden" value="${dispatchManagementEntity.review}" >
						<input class="col-md-12" id="jointlySelectedpersionIds" type="hidden" >
						<input class="col-md-12" id="jointlySelectedpersionNames" type="hidden" >
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
								<label class="col-md-12 control-label no-padding-right">审批意见：</label>
							</div>
							<div class="col-md-10">
								<textarea name="reviewComment" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
							</div>
						</div>
						<div class="form-group" style="display:none">
							<div class="col-md-2 no-padding-right">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right" >
								<div  id="documentText"></div>
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
			</div>
			<div style="margin-right:100px;margin-top:10px">
				<button id="discardedBtn"  class="btn btn-xs btn-danger pull-right"  data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>废弃
				</button>
				<button id="rejectBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
					<i class="ace-icon fa fa-times"></i>驳回
				</button>
				<button id="leaderAgreeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>领导审核
				</button>
				<button id="joinltySignBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>会签
				</button>
			</div>
		</div>
		<script type="text/javascript">
			var submitUserDialog;
			 var userDialog1 ;
			var selectedUserIds;
			var selectedUserNames;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#rejectBtn").hide();
						$("#leaderAgreeBtn").hide();
						$("#joinltySignBtn").hide();
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
				/* 	var documentUploaddropzone =new A.uploaddropzone({
						render : "#documentText",
						fileId:'${dispatchManagementEntity.documents}'==""?[]:${dispatchManagementEntity.documents},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						acceptedFiles:'.doc, .docx'
					}).render("1"); */
					/* var contentWysiwyg = new A.wysiwyg({
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
					contentWysiwyg.setValue(${dispatchManagementEntity.documents}); */
					//附件
					 var appendixUploaddropzone =new A.uploaddropzone({
						render : "#appendixFile",
						fileId:'${dispatchManagementEntity.appendix}'==""?[]:${dispatchManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render("2");

					$("#leaderAgreeBtn").on("click", function(){
	    					var urls=format_url('/dispatchManagement/submitPersonLeader/'+$("#taskId").val());
							var obj = $("#reviewForm").serializeObject();
							obj.approvalStatus = "4";
	    					submitUserDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "领导审核人",
								url:urls,
								closed: function(){
									if(submitUserDialog.resule){
										var selectUser = submitUserDialog.resule.join(",");
										var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
										 var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
										var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");
										 
										var userNames = selectUserNames.replace(reg, ",");
										obj.userList=selectUserIds;
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionCN = selectUserNames;
										
										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();
										//obj.documents=JSON.stringify(contentWysiwyg.getValue());
										obj.review = "1";
										$.ajax({
											url : format_url("/dispatchManagement/dispatchReview"),
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

						$("#rejectBtn").on("click", function(){
							var obj = $("#reviewForm").serializeObject();
							obj.approvalStatus = "5";
							//obj.userList=submitUserDialog.resule.join(",");
							obj.id = $("#id").val();
							obj.taskId = $("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							obj.review = "3";
							$.ajax({
								url : format_url("/dispatchManagement/dispatchReview"),
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

						var t2;
						$("#joinltySignBtn").on("click", function(){
							selectedUserIds ="";
							selectedUserNames="";
							userDialog1 = new A.dialog({
									title:'会签人' ,
									width: '1100',
									height: '780',
									url:format_url('/dispatchManagement/userSelect?singleSelect=2'),
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
											$("#jointlySelectedpersionIds").val(selectedUserIds);
											$("#jointlySelectedpersionNames").val(selectedUserNames);
											//延后执行选择领导审核人
											t2 = window.setTimeout(jointlysing, 100);
										}
										
									}
								}).render(); 
						});

						function jointlysing() {
							window.clearTimeout(t2);
							//选择的会签人
							var selectedJointlyIds = $("#jointlySelectedpersionIds").val();
							var selectedJointlyNames = $("#jointlySelectedpersionNames").val();
							
							var urls=format_url('/dispatchManagement/submitPersonLeaderByGroups/'+$("#taskId").val());
							var obj = $("#reviewForm").serializeObject();
							obj.approvalStatus = "3";
							obj.review = "2";
	    					submitUserDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "领导审核",
								url:urls,
								closed: function(){
									if(submitUserDialog.resule){
										obj.userList= submitUserDialog.resule.join(",");
										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();
										//obj.documents=JSON.stringify(contentWysiwyg.getValue());
										//obj.leaderSelectedPersion = submitUserDialog.resule.join(",");
										var selectUser = submitUserDialog.resule.join(",");
										var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
										var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
										var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");
										obj.userList=selectedJointlyIds;
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionCN = selectUserNames;
										
										obj.jiontlySelectedPersion = selectedJointlyIds;
										obj.jiontlySelectedPersionCn = selectedJointlyNames;
										
										$.ajax({
											url : format_url("/dispatchManagement/dispatchReview"),
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
							var obj = $("#reviewForm").serializeObject();
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
						
						///end
				});
			});
		</script>
	</body>
</html>