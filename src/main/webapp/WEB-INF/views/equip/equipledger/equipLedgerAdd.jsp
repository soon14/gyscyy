<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					设备管理
				</li>
				<li class="active">设备台账</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="tabbable" style="margin-top:2%;">
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="glyphicon glyphicon-share-alt"></i>
						返回
					</button>
				</div>	
    	</div>
    	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:0%;" id="addForm">
	           <input class="col-md-12" id="modelId" name="modelId" type="hidden" placeholder="${formField.toolTip}" maxlength="20" value="${formField.defaultValue}">
		       <div>
		       	   <h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		       	   <div>
				       <div class="form-group" style="margin-top: 10%;">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备编号
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="equipmentNumber" name="equipmentNumber" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备名称
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
		                    </div>
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								设备类型
							</label>
							<div class="col-md-4">
							   <select class="col-md-12 chosen-select" id="equipType" name="equipType"></select>	
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								规格型号
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="equipmentVersion" name="equipmentVersion" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
		                    </div>
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								制造商
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="manuFacturer" name="manuFacturer" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								备注
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="remark" name="remark" type="text" placeholder="${formField.toolTip}" maxlength="128" value="${formField.defaultValue}">
		                    </div>
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								父级节点
							</label>
							<div class="col-md-4">
								<div id="equipLedgerTree" name="equipLedgerTree"></div>
		                	</div>
		                	 <label class="col-md-2 control-label no-padding-right">
								设备模版
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="remark" name="remark" type="text" placeholder="${formField.toolTip}" maxlength="128" value="${formField.defaultValue}">
		                    </div>
		               </div>
				       <div class="form-group">
						    <label class="col-md-2 control-label no-padding-right">
								附件
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="fileId" name="fileId"  placeholder="${formField.toolTip}" ></textarea>
		                    </div>
						    <label class="col-md-2 control-label no-padding-right">
						    </label>
						    <div class="col-md-4">
		                    </div>
		               </div>
	               </div>
               	  
               </div>
            </form>
            <div>
            	 <h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础参数
            	 	<button id="equipModelSelect" class="btn btn-xs pull-right"  style="margin-right:15px;">
    				<i class="ace-icon glyphicon glyphicon-plus"></i>
    				参数模版选择
    			</button>
            	 </h5>
            	 
               	 <div class="form-group" style="margin-top: 10%;">基础参数列表</div>
               	 	<h5 class='table-title header smaller blue' style="margin-bottom:0px;">技术参数</h5>
               	 <div class="form-group" style="margin-top: 10%;">技术参数列表</div>
            </div>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','dialog'], function(A){
					//combobx组件
					var equipTypeCombobox = new A.combobox({
						render: "#equipType",
						//返回数据待后台返回TODO
						datasource:${equipType},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var unitTree = new A.combotree({
						render: "#equipLedgerTree",
						name: 'unitId',
						datasource: ${sysunit},
						width:"230px",
						expand:true,
						options: {
							treeId: 'funCodeTree',
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
    				$("#equipModelSelect").on("click",function(){
    					var equipParameterSelectDialog = new A.dialog({
    						title:"参数模版选择",
    						width:1000,
    						height:800,
    						url:format_url('/equipModel/selectModelList/10'),
    						closed: function(){
    						}
    					}).render();
//     					A.loadPage({
// 							render : '#page-container',
// 							url : format_url('/equipModel/selectModelList/10')
// 						});
    				});
    				$('#addForm').validate({
						debug:false,
						rules: {
							equipmentNumber: {
								required: true,
								maxlength: 64
							},
							equipmentName: {
								required: true,
								maxlength: 64
							},
							unitId: {
								required: true
							}
						},
						submitHandler: function (form) {
							var url = format_url("/equipLedger");
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url('/equipLedger/index')
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>