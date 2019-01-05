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
						<li class="active">签报/来文管理</li>
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
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addReceiptForm">
					<input class="col-md-12" id="addDrafterTimeValue" type="hidden" value="${drafterTime}" >
					<input class="col-md-12" id="addApprovalStatus" name="approvalStatus" type="hidden">
					<input class="col-md-12" id ="addPublisherId"  type="hidden" value="${publisherId}">
					<input class="col-md-12" id="addPublisherName" type="hidden" value="${publisherName }">
					<input class="col-md-12" id="addUserUnitName" type="hidden" value="${loginUserUnitName }">
					<div class="form-group">
						<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建贵阳院勘测设计研究院有限公司签报登记</label> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
						</div>
						<div class="col-md-9" > 
	 						<input class="col-md-12" id="addTitle" name="title" type="text" placeholder="" maxlength="64" value="">
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>操作类型：</label> 
						</div>
						<div class="col-md-3"> 
	 							<select id="addOperateType"></select>
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id="signNoLabel"><span style="color:red;">*</span>发文字号：</label>
							<label class="col-md-12 control-label no-padding-right"  id="receiptNoLabel" style="display:none">来文字号：</label>
						</div>
						<div class="col-md-3">
							<input type="text" name= "receiptNumber" style="width:100%"  maxlength="15">
							<!-- <select id="addDispatchDepartMent"></select> -->
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">来文编号：</label>
						</div>
						<div class="col-md-3">
							<input type="text" name= "receiptNo" style="width:100%"  maxlength="15">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>文件分类：</label> 
						</div>
						<div class="col-md-3"> 
	 							<select id="addReceiptType"></select>
						</div> 
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id ="signatureLabel"><span style="color:red;">*</span>发文单位：</label>
							<label class="col-md-12 control-label no-padding-right" id ="receiptLabel" style="display:none"><span style="color:red;">*</span>来文单位：</label>
						</div>
						<div class="col-md-3">
							<input  id="addDispatchDepartMent" type="text" name= "unitName"  value="${loginUserUnitName}"  style="width:100%" maxlength="64" readonly="readonly">
							<!-- <select id="addDispatchDepartMent"></select> -->
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
						</div>
						<div class="col-md-3">
							<select id="addEmergencyLevel"></select>
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">密级：</label>
						</div>
						<div class="col-md-3">
							<select id="addSecurityLevel"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id="signUserLabel"><span style="color:red;">*</span>签报人 ：</label>
							<label class="col-md-12 control-label no-padding-right" id="startUserLabel" style="display:none"><span style="color:red;">*</span>发起人 ：</label>
						</div>
						<div class="col-md-3">
							<%-- <input class="col-md-12" id="addDrafterName" type="text" name= "publisherName" placeholder="" maxlength="64" value="${publisherName }" readonly> --%>
							<div id="addPublisherDiv"></div>
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
						</div>
						<div class="col-md-3">
							<div id="addDrafterTimeDiv"></div>
						</div>
					</div>
					<div class="form-group" style="display:none">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">公文正文：</label>
						</div>
						<div class="col-md-9 no-padding-right; ">
							<div id="addDocumentFile"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">附件：</label>
						</div>
						<div class="col-md-9" id="addAppendixFile"></div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">备注：</label>
						</div>
						<div class="col-md-9">
							<textarea name="remarks" placeholder="请输入备注" style="height:80px; resize:none;" class="col-md-12" maxlength="255"></textarea>
						</div>
					</div>
				</form>
			</div>
	    		<div style="margin-right:200px;">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg','selectbox'], function(A){
					
					//操作类型
					var operateTypeCombobox = new A.combobox({
						render : "#addOperateType",
						name : "operateType",
						datasource : ${operateTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					operateTypeCombobox.setValue("1");
					
				/* 	//发文文号
					//文号前缀
					var addReceiptNumHeadCombobox = new A.combobox({
						render : "#addReceiptNum_Head",
						name : "receiptNameSymbolId",
						datasource : ${receiptNumPreComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//文号年
						var addReceiptNumYearCombobox = new A.combobox({
						render : "#addReceiptNum_Year",
						name : "receiptNameYear",
						datasource : ${receiptNumYearComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); */
					
					//发文类型
					var addReceiptTypeCombobox = new A.combobox({
						render : "#addReceiptType",
						name : "type",
						datasource : ${receiptTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					addReceiptTypeCombobox.setValue("4");
					
					/* //发文部门
					var addReceiptUnitCombobox = new A.combobox({
						render : "#addDispatchDepartMent",
						name : "unitId",
						datasource : ${departmentComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); */
					
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
						name : "securityLevel",
						datasource : ${denseComboBox},
						allowBlank: false,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();

					//发布人
					var addPublisherCombotree = new A.combotree({
						render: "#addPublisherDiv",
						name: 'publisherId',
						//返回数据待后台返回TODO
						datasource: ${publisherTree},
						width:"210px",
						options: {
							treeId: 'publisherIdTree',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							},
							callback: {
								beforeOnClick: function(e, treeId, treeNode){
									var value = $('#addOperateType').val();
									var userId = treeNode.id;
									console.log();
									console.log(userId);
									if (value == "1") {
										if(userId.indexOf("U")!=-1){
											getUnitNameByUseId(userId.substr(1));
										}
									}
								}
							}
						}
					}).render();
					
					var publisherId =  $("#addPublisherId").val();
					//addPublisherCombotree.setValue(publisherId);
					var publisherIdTreeObj = $.fn.zTree.getZTreeObj("publisherIdTree");
					var allTreeNodes = publisherIdTreeObj.getNodes();
					var treeNodes = allTreeNodes[0].children;
					var publisherName =  $("#addPublisherName").val();
					for (var i=0;i<treeNodes.length; i++) {
								if (treeNodes[i].isParent) {
									var  userNodes = treeNodes[i].children;
									for (var j=0;j<userNodes.length; j++) {
										if(userNodes[j].id == publisherId ){
											publisherIdTreeObj.selectNode(userNodes[j], true, false);
											userNodes[j].checked = true;
											publisherName = userNodes[j].name;
											break;
										}
									}
								} else if (treeNodes[i].id == publisherId && treeNodes[i].nodeType =="2") {
									publisherIdTreeObj.selectNode(treeNodes[i], true, false);
									treeNodes[i].checked = true;
									publisherName = treeNodes[i].name;
									break;
								}
						}
						addPublisherCombotree.setValue(publisherName, 'name');

					function getUnitNameByUseId (userId) {
						console.log(userId);
						var url = format_url("/receiptManagement/getUnitNameByUseId/" + userId);
						console.log(url);
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : {},
							cache: false,
							type : 'POST',
							success: function(result){
								console.log(result.unitName);
								if (result.unitName) {
									$("#addDispatchDepartMent").val(result.unitName);
								}
							},
							error:function(v,n){
								alert('添加失败');
							}
						});
					}

					$('#addOperateType').change(function() {
						var value = $('#addOperateType').val();
						$("#addPublisherDiv").empty();
						addPublisherCombotree = new A.combotree({
							render: "#addPublisherDiv",
							name: 'publisherId',
							//返回数据待后台返回TODO
							datasource: ${publisherTree},
							width:"210px",
							options: {
								treeId: 'publisherIdTree',
								data: {
									key: {
										name: "name"
									},
									simpleData: {
										enable: true,
										idKey: "id",
										pIdKey: "parentId"
									}
								},
								callback: {
									beforeOnClick: function(e, treeId, treeNode){
										var value = $('#addOperateType').val();
										if (value == "1") {
											getUnitNameByUseId(treeNode.id);
										}
									}
								}
							}
						}).render();
						
						$('#addDispatchDepartMent').val("");
						
						if (value == "1") {
							addDipatchForm.settings.rules["receiptNumber"]={required:true};
							$('#signNoLabel').show();
							$('#receiptNoLabel').hide();
							$('#signatureLabel').show();
							$('#receiptLabel').hide();
							$('#signUserLabel').show();
							$('#startUserLabel').hide();
						}  else {
							addDipatchForm.settings.rules["receiptNumber"]={};
							$('#signNoLabel').hide();
							$('#receiptNoLabel').show();
							$('#signatureLabel').hide();
							$('#receiptLabel').show();
							$('#signUserLabel').hide();
							$('#startUserLabel').show();
						}
				});

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

					//公文正文
				/* 	var contentWysiwyg = new A.wysiwyg({
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
					}).render("2"); 

		 			var addDipatchForm = $('#addReceiptForm').validate({
						debug:true,
						rules:  {
							title:{required:true, maxlength:64},
							operateType : {required:true},
							receiptNumber : {required:true, maxlength:15},
							type:{required:true},
							draftTime:{required:true},
							unitName:{ required:true, maxlength:15}
						}, 
						submitHandler: function (form) {
							var approvalStatis = $("#addApprovalStatus").val();
							//添加按钮
							var url = format_url("/receiptManagement/addEntity");
							if (approvalStatis == "1") {
								url = format_url("/receiptManagement/addEntity");
							} else if (approvalStatis == "2") {
								url = format_url("/receiptManagement/addAndApprovalSubmit");
							}
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addReceiptForm").serializeObject();
							
							//发文字号
							/* var preValue =  $("#addReceiptNum_Head  option:selected").text();
							if (preValue =="") {
								alert("请选择来文文号");
								return;
							}
							obj.receiptNameSymbolName = preValue;
							var yearValue = $("#addReceiptNum_Year").val();
							if (yearValue == "") {
								alert("请选择来文年号");
								return;
							}
							var numValue = $("#addReceiptNum_Num").val();
							if (numValue == "" || numValue.length !=4) {
								alert("请输入4位来文序号");
								return;
							}
							obj.receiptNumber = preValue + yearValue + numValue; */
							
							/* //发文部门名称
							var unitName = $("#addDispatchDepartMent  option:selected").text();
							obj.unitName = unitName; */
							
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							var operaterTypeValue = $('#addOperateType').val();
							//发布人
							var val = addPublisherCombotree.getValue();
							if (val== undefined || val =="") {
								if (operaterTypeValue == "1") {
									alert("请选择签报人");
								} else {
									alert("请选择发起人");
								}
								return;
							}
							var treeObj = $.fn.zTree.getZTreeObj("publisherIdTree");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length > 0) {
								obj.publisherName =  nodes[0].name;
								obj.publisherId =  nodes[0].id;
							}

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
										/* listFormDialog.close(); */
										A.loadPage({
				    						render : '#page-container',
				    						url : format_url("/receiptManagement/index/")
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

		 			var value = $('#addOperateType').val();
					if (value == "1") {
						addDipatchForm.settings.rules["receiptNumber"]={required:true};
					} else {
						addDipatchForm.settings.rules["receiptNumber"]={};
					}

					$("#saveBtn").on("click", function(){
						$("#addApprovalStatus").val("1");
						$("#addReceiptForm").submit();
    				}); 
					$("#submitApprovalBtn").on("click", function(){
						$("#addApprovalStatus").val("2");
						$("#addReceiptForm").submit();
    				}); 
    				
					//由添加迁移页返回到列表页
    				$("#backBtnAddTrainPlan").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/receiptManagement/index/")
    					});
    				});
					
					
				});
			});
        </script>
    </body>
</html>