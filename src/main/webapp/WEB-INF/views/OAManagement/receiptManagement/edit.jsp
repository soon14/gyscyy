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
						<li class="active">修改</li>
					</ul><!-- /.breadcrumb -->
					<div style="margin-right:180px;margin-top:10px;">
						<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
							<i class="glyphicon glyphicon-share-alt"></i>
							返回
						</button>
					</div>
					<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
				</div>
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
					<input class="col-md-12" id="id" name="id"  value="${receiptManagementEntity.id}" type="hidden">
					<input class="col-md-12" id="editDrafterTimeValue" type="hidden" value="${drafterTime}" >
					<input class="col-md-12" id="editReceiptNumPreVal" type="hidden" value="${receiptNumPreVal}" >
					<input class="col-md-12" id="editReceiptNumYearVal" type="hidden" value="${receiptNumYearVal}" >
					<input class="col-md-12" id="editReceiptNumNoVal" type="hidden" value="${receiptNumNo}" >
					<input class="col-md-12" name="approvalStatus" type="hidden" value="${receiptManagementEntity.approvalStatus}" >
					<input class="col-md-12" id ="editPublisherId"  type="hidden" value="${publisherId}">
					<input class="col-md-12" id="editPublisherName" type="hidden" value="${publisherName }">
						<input class="col-md-12" id="editUserUnitName" type="hidden" value="${receiptManagementEntity.unitName }">
					<div class="form-group">
						<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建贵阳院勘测设计研究院有限公司签报登记</label> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
						</div>
						<div class="col-md-9" > 
	 						<input class="col-md-12" id="editTitle" name="title" type="text" placeholder="" maxlength="64" value="${receiptManagementEntity.title}">
						</div> 
					</div>
					<%-- <div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">来文字号：</label> 
						</div>
						<div class="col-md-10"> 
							<div  style="float:left;width:120px;margin-right:5px">
								<select id="editReceiptNum_Head"></select>
							</div>
							<div  style="float:left;width:100px;margin-right:5px">
								<select id="editReceiptNum_Year"></select>
							</div>
	 						<input  id="editReceiptNum_Num" type="text" name= "receiptNameNum" maxlength="4" value="${receiptNumNo}"  style="width:50px">
						</div> 
					</div> --%>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>操作类型：</label> 
						</div>
						<div class="col-md-3"> 
	 							<select id="editOperateType"></select>
						</div> 
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id="signNoLabel"><span style="color:red;">*</span>发文字号：</label>
							<label class="col-md-12 control-label no-padding-right"  id="receiptNoLabel" style="display:none">来文字号：</label>
						</div>
						<div class="col-md-3">
							<input type="text" name= "receiptNumber" style="width:100%"  maxlength="15" value="${receiptManagementEntity.receiptNumber}" >
							<!-- <select id="addDispatchDepartMent"></select> -->
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">来文编号：</label>
						</div>
						<div class="col-md-3">
							<input type="text" name= "receiptNo" style="width:100%"  maxlength="15" value="${receiptManagementEntity.receiptNo}" >
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>文件分类：</label> 
						</div>
						<div class="col-md-3"> 
	 							<select id="editReceiptType"></select>
						</div> 
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id ="signatureLabel"><span style="color:red;">*</span>发文单位：</label>
							<label class="col-md-12 control-label no-padding-right" id ="receiptLabel" style="display:none"><span style="color:red;">*</span>来文单位：</label>
						</div>
						<div class="col-md-3">
							<input  id="editDispatchDepartMent" type="text" name= "unitName"  value="${receiptManagementEntity.unitName}"  style="width:100%"  maxlength="15">
							<!-- <select id="editReceiptUnitDiv"></select> -->
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
						</div>
						<div class="col-md-3">
							<select id="editEmergencyLevel"></select>
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">密级：</label>
						</div>
						<div class="col-md-3">
							<select id="editSecurityLevel"></select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right" id="signUserLabel"><span style="color:red;">*</span>签报人 ：</label>
							<label class="col-md-12 control-label no-padding-right" id="startUserLabel" style="display:none"><span style="color:red;">*</span>发起人 ：</label>
						</div>
						<div class="col-md-3">
							<%-- <input class="col-md-12" id="editPublisherName" name= "publisherName" type="text" placeholder="" maxlength="64" value="${receiptManagementEntity.publisherName }" readonly> --%>
							<div id="addPublisherDiv"></div>
						</div>
						<div class="col-md-3 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
						</div>
						<div class="col-md-3">
							<div id="editDrafterTimeDiv"></div>
						</div>
					</div>
					<!-- <div class="form-group" style="display:none">
						<div class="col-md-2 no-padding-right ">
							<label class="col-md-12 control-label no-padding-right">公文正文：</label>
						</div>
						<div class="col-md-9 no-padding-right">
							<div  id="editDocumentFile"></div>
						</div>
					</div> -->
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">附件：</label>
						</div>
						<div class="col-md-9" id="editAppendixFile"></div>
					</div>
					<div class="form-group">
						<div class="col-md-2 no-padding-right; ">
							<label class="col-md-12 control-label no-padding-right">备注：</label>
						</div>
						<div class="col-md-9">
							<textarea name="remarks" style="height:80px; resize:none;" class="col-md-12" maxlength="255">${receiptManagementEntity.remarks}</textarea>
						</div>
					</div>
				</form>
			</div>
			<div style="margin-right:200px;">
				<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					
					
					//操作类型
					var operateTypeCombobox = new A.combobox({
						render : "#editOperateType",
						name : "operateType",
						datasource : ${operateTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					operateTypeCombobox.setValue("1");
					/* //发文文号
					//文号前缀
					var editReceiptNumHeadCombobox = new A.combobox({
						render : "#editReceiptNum_Head",
						name : "receiptNameSymbolId",
						datasource : ${receiptNumPreComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editReceiptNumHeadCombobox.setValue($("#editReceiptNumPreVal").val());
					
					//文号年
					var editReceiptNumYearCombobox = new A.combobox({
						render : "#editReceiptNum_Year",
						name : "receiptNameYear",
						datasource : ${receiptNumYearComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editReceiptNumYearCombobox.setValue($("#editReceiptNumYearVal").val()); */
					
					//发文类型
					var editReceiptTypeCombobox = new A.combobox({
						render : "#editReceiptType",
						name : "type",
						datasource : ${receiptTypeComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editReceiptTypeCombobox.setValue(${receiptManagementEntity.type});
					
/* 					//发文部门
					var editReceiptUnitCombobox = new A.combobox({
						render : "#editReceiptUnitDiv",
						name : "unitId",
						datasource : ${departmentComboBox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editReceiptUnitCombobox.setValue(${receiptManagementEntity.unitId}); */
					
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
					editEmergencyLevelCombobox.setValue(${receiptManagementEntity.emergencyLevel});
					
					//密级
					var editSecurityLevelCombobox = new A.combobox({
						render : "#editSecurityLevel",
						name : "securityLevel",
						datasource : ${denseComboBox},
						allowBlank: false,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					editSecurityLevelCombobox.setValue(${receiptManagementEntity.securityLevel});

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
									var value = $('#editOperateType').val();
									if (value == "1") {
										getUnitNameByUseId(treeNode.id);
									}
								}
							}
						}
					}).render();
					
					function getUnitNameByUseId (userId) {
						var url = format_url("/receiptManagement/getUnitNameByUseId/" + userId);
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : {},
							cache: false,
							type : 'POST',
							success: function(result){
								debugger;
								if (result.unitName) {
									$("#editDispatchDepartMent").val(result.unitName);
								}
							},
							error:function(v,n){
								alert('添加失败');
							}
						});
					}

					$('#editOperateType').change(function() {
							var value = $('#editOperateType').val();
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
											var value = $('#editOperateType').val();
											if (value == "1") {
												getUnitNameByUseId(treeNode.id);
											}
										}
									}
								}
							}).render();

							$('#editDispatchDepartMent').val("");
							
							if (value == "1") {
								editForm.settings.rules["receiptNumber"]={required:true};
								$('#signNoLabel').show();
								$('#receiptNoLabel').hide();
								$('#signatureLabel').show();
								$('#receiptLabel').hide();
								$('#signUserLabel').show();
								$('#startUserLabel').hide();
							}  else {
								editForm.settings.rules["receiptNumber"]={};
								$('#signNoLabel').hide();
								$('#receiptNoLabel').show();
								$('#signatureLabel').hide();
								$('#receiptLabel').show();
								$('#signUserLabel').hide();
								$('#startUserLabel').show();
							}
					});

					var publisherIdTreeObj = $.fn.zTree.getZTreeObj("publisherIdTree");
					var allTreeNodes = publisherIdTreeObj.getNodes();
					var treeNodes = allTreeNodes[0].children;
					var publisherId =  $("#editPublisherId").val();
					var publisherName =  $("#editPublisherName").val();
					for (var i=0;i<treeNodes.length; i++) {
								if (treeNodes[i].isParent) {
									var  userNodes = treeNodes[i].children;
									for (var j=0;j<userNodes.length; j++) {
										if(userNodes[j].id == publisherId ){
											publisherIdTreeObj.checkNode(userNodes[j], true, false);
											userNodes[j].checked = true;
											publisherName = userNodes[j].name;
											//addPublisherCombotree.setValue(publisherName, 'name');
											break;
										}
									}
								} else if (treeNodes[i].id == publisherId && treeNodes[i].nodeType =="2") {
									publisherIdTreeObj.checkNode(treeNodes[i], true, false);
									treeNodes[i].checked = true;
									publisherName = treeNodes[i].name;
									
									break;
								}
					}
					addPublisherCombotree.setValue(publisherName, 'name');
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

					//公文正文
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
					var appendixFile = new A.uploaddropzone({
						render : "#editAppendixFile",
						fileId:${receiptManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render("2"); 

					var id = $('#id').val();
					var editForm = $('#editForm').validate({
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
							//添加按钮
							var url = format_url("/receiptManagement/editEntity/");
							var obj = $("#editForm").serializeObject();
							
							//发文字号
						/* 	var preValue =  $("#editReceiptNum_Head  option:selected").text();
							if (preValue =="") {
								alert("请选择发文文号");
								return;
							}
							obj.receiptNameSymbolName = preValue;
							var yearValue = $("#editReceiptNum_Year").val();
							if (yearValue == "") {
								alert("请选择发文年号");
								return;
							}
							var numValue = $("#editReceiptNum_Num").val();
							if (numValue == "" || numValue.length !=4) {
								alert("请输入4位发文序号");
								return;
							}
							obj.receiptNumber=preValue + yearValue + numValue;
							
							//发文部门名称
							var unitName = $("#editReceiptUnitDiv  option:selected").text();
							obj.unitName = unitName; */
							
							//obj.documents=JSON.stringify(contentWysiwyg.getValue());
							//发布人
							var val = addPublisherCombotree.getValue();
							var operaterTypeValue = $('#editOperateType').val();
							debugger;
							if (val ==undefined || val =="") {
								if (operaterTypeValue == "1") {
									alert("请选择签报人");
								} else {
									alert("请选择发起人");
								}
								return;
							}
							var treeObj = $.fn.zTree.getZTreeObj("publisherIdTree");
							var nodes = treeObj.getSelectedNodes();
							obj.publisherName =  nodes[0].name;
							obj.publisherId =  nodes[0].id;
							
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
			    						url : format_url("/receiptManagement/index/")
			    					});
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					var value = $('#editOperateType').val();
					if (value == "1") {
						editForm.settings.rules["receiptNumber"]={required:true};
						$('#signNoLabel').show();
						$('#receiptNoLabel').hide();
						$('#signatureLabel').show();
						$('#receiptLabel').hide();
						$('#signUserLabel').show();
						$('#startUserLabel').hide();
					} else {
						editForm.settings.rules["receiptNumber"]={};
						$('#signNoLabel').hide();
						$('#receiptNoLabel').show();
						$('#signatureLabel').hide();
						$('#receiptLabel').show();
						$('#signUserLabel').hide();
						$('#startUserLabel').show();
					}
					$("#editBtn").on("click", function(){
						$("#editForm").submit();
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