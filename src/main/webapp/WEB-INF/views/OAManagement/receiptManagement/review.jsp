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
						<input class="col-md-12" id="id"  value="${receiptManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="type" value="${type}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="operateTypeVal"  value="${receiptManagementEntity.operateType}" type="hidden">
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
		 							<input  id="viewReceiptType"  class="col-md-12"  type="text" value="${receiptManagementEntity.typeCN}" readonly>
							</div> 
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right" id ="signatureLabel"><span style="color:red;">*</span>发文单位：</label>
								<label class="col-md-12 control-label no-padding-right" id ="receiptLabel" style="display:none"><span style="color:red;">*</span>来文单位：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewDispatchDepartment" class="col-md-12" type="text"  value="${receiptManagementEntity.unitName}" readonly>
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
					<!-- 	<div class="form-group">
							<div class="col-md-2 no-padding-right">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right" >
								<div  id="documentText"></div>
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
							<div class="col-md-10">
								<textarea name="reviewComment" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
							</div>
						</div>
				</form>
			</div>
			<div style="margin-right:100px;margin-top:10px">
				<button id="discardedBtn"  class="btn btn-xs btn-danger pull-right"  data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>废弃
				</button>
				<button id="rejectBtn"  class="btn btn-xs btn-danger pull-right" style="margin-right:15px;">
					<i class="ace-icon fa fa-times"></i>驳回(发起人)
				</button>
				<button id="productionUnitSignBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i>生产部门会签
					</button>
				<button id="joinltySignBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>处室办理人
				</button>
			</div>
		</div>
		<script type="text/javascript">
			var submitUserDialog;
			var userDialog1 ;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var prossType = $("#type").val(); 
					if ("finish" == prossType) {
						$("#rejectBtn").hide();
						$("#leaderAgreeBtn").hide();
						$("#joinltySignBtn").hide();
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
					
					//生产部门会签选择会签人
					var t2;
					$("#productionUnitSignBtn").on("click", function(){
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
										t2 = window.setTimeout(jointlysing, 100);
									}
									
								}
							}).render(); 
					});
					//选择处室办理人并且生产部门会签
					function jointlysing() {
						window.clearTimeout(t2);
						//选择的生产部门会签人
						var selectedProductionUnitJointlyIds = $("#productionUnitSelectedpersionIds").val();
						var selectedProductionUnitJointlyNames = $("#productionUnitSelectedpersionNames").val();
						var obj = $("#reviewForm").serializeObject();
						obj.productionUntijointlySign = "5";
						userDialog1 = new A.dialog({
							title:'处室办理人' ,
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
									//处室办理人
									obj.recevingSelectedPersion = selectedUserIds;
									obj.recevingSelectedPersionName = selectedUserNames;
									//生产部门会签人
									obj.productionUntijointlySelectedPersion = selectedProductionUnitJointlyIds;
									obj.productionUntijointlySelectedPersionName = selectedProductionUnitJointlyNames;
									obj.id = $("#id").val();
									obj.taskId = $("#taskId").val();
									obj.procInstId=$("#procInstId").val();

									$.ajax({
										url : format_url("/receiptManagement/productionUnitJoinltySign"),
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
					$("#leaderAgreeBtn").on("click", function(){
	    					var urls=format_url('/receiptManagement/submitPersonLeader/'+$("#taskId").val());
							var obj = $("#reviewForm").serializeObject();
	    					submitUserDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "领导",
								url:urls,
								closed: function(){
									if(submitUserDialog.resule){
										//obj.userList=submitUserDialog.resule.join(",");
										var selectUser = submitUserDialog.resule.join(",");
										var selectUserIds = selectUser.substring(0,selectUser.lastIndexOf(","));
										var reg=new RegExp("&&", "g"); //创建正则RegExp对象   
										var selectUserNames = selectUser.substring(selectUser.lastIndexOf(",") + 1, selectUser.length).replace(reg, ",");

										obj.userList=selectUserIds;
										obj.leaderSelectedPersion = selectUserIds;
										obj.leaderSelectedPersionName = selectUserNames;
										
										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();
										//obj.documents=JSON.stringify(contentWysiwyg.getValue());
										obj.review = "1";
										$.ajax({
											url : format_url("/receiptManagement/receiptReview"),
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

						$("#joinltySignBtn").on("click", function(){
							var obj = $("#reviewForm").serializeObject();
							obj.review = "2";
							userDialog1 = new A.dialog({
								title:'处室办理人' ,
								width: '1100',
								height: '780',
								url:format_url('/receiptManagement/receivingUserSelect'),
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
										obj.recevingSelectedPersion = selectedUserIds;
										obj.recevingSelectedPersionName = selectedUserNames;
										obj.id = $("#id").val();
										obj.taskId = $("#taskId").val();
										obj.procInstId=$("#procInstId").val();

										$.ajax({
											url : format_url("/receiptManagement/receiptReview"),
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
							obj.id = $("#id").val();
							obj.taskId = $("#taskId").val();
							obj.procInstId=$("#procInstId").val();
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							obj.review = "3";
							$.ajax({
								url : format_url("/receiptManagement/receiptReview"),
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
							var obj = $("#reviewForm").serializeObject();
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
						
						
				});
			});
		</script>
	</body>
</html>