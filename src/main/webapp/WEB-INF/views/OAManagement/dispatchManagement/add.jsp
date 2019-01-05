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
						<li class="active">添加</li>
					</ul><!-- /.breadcrumb -->
					<div style="margin-right:180px;margin-top:10px;">
						<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
							<i class="glyphicon glyphicon-share-alt"></i>
							返回
						</button>
					</div>
					<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
				</div>
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addDispatchForm">
					<input class="col-md-12" id="addDrafterTimeValue" type="hidden" value="${drafterTime}" >
					<input class="col-md-12" id="addRemindTimeValue" type="hidden" value="${remindTime}" >
					<input class="col-md-12" id="addApprovalStatus" name="approvalStatus" type="hidden">
					<input class="col-md-12" name="drafterId" type="hidden" value="${drafterId}">
					<div class="form-group">
						<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建集团贵阳勘测设计研究院有限公司发文处理单</label> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
						</div>
						<div class="col-md-10" > 
	 						<input class="col-md-12" id="addTitle" name="title" type="text" placeholder="" maxlength="64" value="">
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文字号：</label> 
						</div>
						<div class="col-md-10"> 
							<div  style="float:left;width:120px;margin-right:5px">
								<select id="addDispatchNum_Head"></select>
							</div>
							<div  style="float:left;width:100px;margin-right:5px">
								<select id="addDispatchNum_Year"></select>
							</div>
	 						<input  id="addDispatchNum_Num" type="text" name= "dispatchNameNum" maxlength="4" value="${dispatchNumNo}"  style="width:50px">
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文类型：</label> 
						</div>
						<div class="col-md-4"> 
	 							<select id="addDispatchType"></select>
						</div> 
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文部门：</label>
						</div>
						<div class="col-md-4">
							<select id="addDispatchDepartMent"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
						</div>
						<div class="col-md-4">
							<select id="addEmergencyLevel"></select>
						</div>
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">密级：</label>
						</div>
						<div class="col-md-4">
							<select id="addSecurityLevel"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿人：</label>
						</div>
						<div class="col-md-4">
							<input class="col-md-12" id="addDrafterName" type="text" name= "drafterName" placeholder="" maxlength="64" value="${drafterName }" readonly>
						</div>
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
						</div>
						<div class="col-md-4">
							<div id="addDrafterTimeDiv"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"> 
									<input name="feedBack" type="radio" value="1" class="ace" checked="checked"/> 
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
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;"  id="addFeedBackRemindTimeNeed">*</span>反馈提醒时间：</label>
						</div>
						<div class="col-md-4">
							<div id="addFeedBackRemindTimeDiv"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"> 
										<input name="releaseStatus" type="radio" value="1" class="ace" checked="checked"/> 
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
							<div id="addRecipientDiv"></div>
						</div>
					</div>
					<div class="form-group" style="display:none">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">公文正文：</label>
						</div>
						<div class="col-md-10 no-padding-right; ">
							<div id="addDocumentFile"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">附件：</label>
						</div>
						<div class="col-md-10" id="addAppendixFile"></div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">备注：</label>
						</div>
						<div class="col-md-10">
							<textarea name="remarks" placeholder="请输入备注" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
						</div>
					</div>
				</form>
			</div>
			<div style="margin-right:200px;margin-top:20px">
				<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
				<button id="submitApprovalBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					提交审批
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					//发文文号
					//文号前缀
					var addDispatchNumHeadCombobox = new A.combobox({
						render : "#addDispatchNum_Head",
						name : "dispatchNameSymbolId",
						datasource : ${dispatchNumPreComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//文号年
						var addDispatchNumYearCombobox = new A.combobox({
						render : "#addDispatchNum_Year",
						name : "dispatchNameYear",
						datasource : ${dispatchNumYearComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//发文类型
					var addDispatchTypeCombobox = new A.combobox({
						render : "#addDispatchType",
						name : "type",
						datasource : ${dispatchTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					$("#addDispatchType").change(function() {
							var type = $("#addDispatchType").val();
							if (type == '5' || type == '6' || type == '7' || type == '8') {
								$(":radio[name='releaseStatus'][value='2']").prop("checked", "checked");
								$('#addRecipientNeed').show();
								$('input[name="releaseStatus"]').attr("disabled",true);
							} else {
								$('input[name="releaseStatus"]').attr("disabled",false);
							}
					
					});
					
					//发文部门
					var addDispatchDepartMentCombobox = new A.combobox({
						render : "#addDispatchDepartMent",
						name : "departmentID",
						datasource : ${departmentComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//缓急程度
					var addEmergencyLevelCombobox = new A.combobox({
						render : "#addEmergencyLevel",
						name : "emergencyLevel",
						datasource : ${urgencyComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//密级
					var addSecurityLevelCombobox = new A.combobox({
						render : "#addSecurityLevel",
						name : "securityLelvel",
						datasource : ${denseComboBox},
						allowBlank: false,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();

					//拟稿时间 
					var addDrafterTimePicker = new A.my97datepicker({
						id: "addDrafterTime",
						name:"draftTime",
						render:"#addDrafterTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm "
						}
					}).render();
					addDrafterTimePicker.setValue($("#addDrafterTimeValue").val());
					
					//反馈提醒时间 
					var addFeedBackRemindTimePicker = new A.my97datepicker({
						id: "addFeedBackRemindTime",
						name:"feedBackRemindTime",
						render:"#addFeedBackRemindTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					addFeedBackRemindTimePicker.setValue($("#addRemindTimeValue").val());
					
					//接受人
					var addRecipientCombotree = new A.combotree({
						render: "#addRecipientDiv",
						name: 'recipientId',
						//返回数据待后台返回TODO
						datasource: ${recipientTree},
						width:"210px",
						multiple: true,
						options: {
							treeId: 'addRecipientId',
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
						/* 	,callback: {
								beforeOnCheck: function(e, treeId, treeNode){
										value = "";
										ids ="";
										var tree = $.fn.zTree.getZTreeObj(treeId);
										var nodes = tree.getCheckedNodes(true);
										for (var i=0, l=nodes.length; i<l; i++) {
											if(nodes[i].nodeType=='2'){
												if(i == l-1){
													ids += nodes[i].id.replace("user","");
												}else{
													ids += nodes[i].id.replace("user","") + ",";
												}
											}
											if(nodes[i].nodeType!='2' && !nodes[i].isParent){
												nodes[i].isParent = true;
											}
										}
										receiveUserIds = ids;
										
									}
								} */
						}
					}).render();

					//公文正文
				/* 	var documentFile =new A.uploaddropzone({
						render : "#addDocumentFile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						acceptedFiles:'.doc, .docx'
					}).render("1"); */
					/* var contentWysiwyg = new A.wysiwyg({
						render : "#addDocumentFile",
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
					}).render(); */
					
					//附件
					var appendixFile = new A.uploaddropzone({
						render : "#addAppendixFile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render(); 
					//反馈和不反馈变更响应
					$('#addFeedBackRemindTimeNeed').hide();
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
					$('#addRecipientNeed').hide();
					$('input[name="releaseStatus"]').change(function() {
						var value = $('input[name="releaseStatus"]:checked').val();
						if (value == "2") {
							$('#addRecipientNeed').show();
							addDipatchForm.settings.rules["recipientId"]={required:true};
						}  else {
							addDipatchForm.settings.rules["recipientId"]={};
							$('#addRecipientNeed').hide();
						}
					}); 

		 			var addDipatchForm = $('#addDispatchForm').validate({
						debug:true,
						rules:  {
							title:{required:true, maxlength:64},
							type:{required:true},
							draftTime:{required:true},
							departmentID:{ required:true}
						}, 
						submitHandler: function (form) {
							var approvalStatis = $("#addApprovalStatus").val();
							//添加按钮
							var url = format_url("/dispatchManagement/addEntity");
							if (approvalStatis == "1") {
								url = format_url("/dispatchManagement/addEntity");
							} else if (approvalStatis == "2") {
								url = format_url("/dispatchManagement/addAndApprovalSubmit");
							}
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addDispatchForm").serializeObject();
							//附件上传
							obj.appendix=JSON.stringify(appendixFile.getValue());
							obj
							//发文字号
							var preValue =  $("#addDispatchNum_Head  option:selected").text();
							if (preValue =="") {
								alert("请选择发文文号");
								return;
							}
							obj.dispatchNameSymbolName = preValue;
							var yearValue = $("#addDispatchNum_Year").val();
							if (yearValue == "") {
								alert("请选择发文年号");
								return;
							}
							var numValue = $("#addDispatchNum_Num").val();
							if (numValue == "" || numValue.length !=4) {
								alert("请输入4位发文序号");
								return;
							}
							obj.dispatchName=preValue + yearValue + numValue;
							
							//发文部门名称
							var departMentName = $("#addDispatchDepartMent  option:selected").text();
							obj.departmentName = departMentName;
							
							var value = $('input[name="releaseStatus"]:checked').val();
							
							var treeObj = $.fn.zTree.getZTreeObj("addRecipientId");
							var nodes = treeObj.getCheckedNodes(true);
							
							var recipientName ="";
							if (nodes.length > 0 ) {
								//obj.recipientType = nodes[0].nodeType;
								obj.recipientType = "2";
								for (var i=0 ; i<nodes.length  ; i++) {
									if(!nodes[i].isParent &&  nodes[i].nodeType != 1){
										recipientName +=  nodes[i].name;
										if (i != nodes.length -1) {
											recipientName += ",";
										}
									}
								}
							
							}
							obj.recipientName = recipientName;
							/*obj.documents=JSON.stringify(contentWysiwyg.getValue());
							 if(eval(documentFile.getValue()).length==0){
								alert("请至少添加一个公文正文");
								return;
							} */
							obj.appendix = JSON.stringify(appendixFile.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result == 'success'){
										alert('添加成功');
										A.loadPage({
											render : '#page-container',
											url : format_url('/dispatchManagement/index/')
										});
									} else if(result.result == 'noPerson'){
										alert("无可以审核的部门领导");
									} else {
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					}); 
			
					$("#saveBtn").on("click", function(){
						$("#addApprovalStatus").val("1");
						$("#addDispatchForm").submit();
					}); 
					$("#submitApprovalBtn").on("click", function(){
						$("#addApprovalStatus").val("2");
						$("#addDispatchForm").submit();
					}); 
					
					//由添加迁移页返回到列表页
    				$("#backBtnAddTrainPlan").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/dispatchManagement/index/")
    					});
    				});
				});
			});
		</script>
	</body>
</html>