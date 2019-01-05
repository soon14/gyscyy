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
					标准管理
				</li>
				<li class="active">制度管理</li>
				<li class="active">新增</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-left: -55px">
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top: 30px" id="addForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						编码
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="">
                    </div>
                      <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
				    <div id="unitIdDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>制度名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="sysName" name="sysName" type="text" placeholder="" maxlength="20" value="">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						填报人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="applyUserId" name="applyUserId" readonly="readonly" type="hidden" placeholder="" maxlength="20" value="${userEntity.id}">
	                   <input class="col-md-12" id="applyUserName" name="applyUserName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${userEntity.name}">
                	</div>
				    
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>实施日期
				    </label>
				    <div class="col-md-4">
				    <div id="materialDateDiv"></div>
<!-- 						<input class="col-md-12" id="materialName" name="materialName" type="text" placeholder="" maxlength="0" value=""> -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
				    </label>
				    <div class="col-md-4">
				     <div id="yearNumDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>类别
					</label>
					<div class="col-md-4">
	                   <select class="col-md-12 chosen-select" id="type" name="type"></select>
                	</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>归口单位</label>
					<div class="col-md-4">
						<select class="col-md-12 chosen-select" id="addCentralizedUnitId" name="centralizedUnitId"></select>
					</div>
               </div>
               <div class="form-group" >
						<label class="col-md-2 control-label no-padding-right">
							附件
	                    </label>
						<div class="col-md-10" id="divfile">
	                    </div>
	                </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:-116px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//部门控件下拉树
					var unitId = new A.combotree({
					render: "#unitIdDiv",
					name: 'unitId',
					//返回数据待后台返回TODO
					datasource: ${unitNameIdTreeList},
					width:"210px",
					options: {
						treeId: 'searchunitId',
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
					}
				}).render();
					unitId.setValue(${userEntity.unitId});
					
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "materialDateId",
						name:"materialDate",
						render:"#materialDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					//日期组件
					var yearNumPicker = new A.my97datepicker({
						id: "yearNumId",
						name:"yearNum",
						render:"#yearNumDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					//类别
					var typeCombobox = new A.combobox({
						render : "#type",
						datasource : ${sysManagentTypeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
					//归口单位
					var unitCombobox = new A.combobox({
						render : "#addCentralizedUnitId",
						name : "centralizedUnitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							sysName:{required:true,maxlength:20},
							yearNum:{required:true,maxlength:20},
							materialDate:{required:true},
							type:{required:true,maxlength:20},
							unitId:{required:true,maxlength:20},
							applyUserId:{required:true,maxlength:20},
							centralizedUnitId:{required:true},
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/sysManagement");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.yearNum=$("#yearNumId").val()+"-01-01 00:00";
							
							//归口单位名称
							var unitName = $("#addCentralizedUnitId ").find("option:selected").text();
							obj.centralizedUnitName = unitName;
							
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
										url : format_url("/sysManagement/index")
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
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/sysManagement/index")
						});
    				});
    				
				});
			});
        </script>
    </body>
</html>