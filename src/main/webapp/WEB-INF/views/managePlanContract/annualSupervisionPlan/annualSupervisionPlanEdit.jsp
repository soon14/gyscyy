<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
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
					计划管理
				</li>
				<li class="active">技术质量类计划</li>
				<li class="active">年度技术监督计划</li>
				<li class="active">修改</li>
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
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="editForm">
			<input id="id" name="id" type="hidden" value="${entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="subject" name="subject" type="text" placeholder="" maxlength="20" value="${entity.subject }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任单位
					</label>
					<div class="col-md-4">
						<div id="unitIdDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>目的及方案
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="purposePlan" name="purposePlan" type="text" placeholder="" maxlength="20" value="${entity.purposePlan }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划完成时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="planDate" name="planDate" type="text" placeholder="" maxlength="20" value="${entity.planDate }">
                	</div>
               </div>
		       <div class="form-group">
					 <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传人
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="userId" name="userId"></select>
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>监督专业
				    </label>
				    <div class="col-md-4">
				    <select class="col-md-12 chosen-select" id="supervisionMajor" name="supervisionMajor"></select>
<!-- 						<input class="col-md-12" id="supervisionMajor" name="supervisionMajor" type="text" placeholder="" maxlength="20" value=""> -->
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                    <textarea placeholder="请输入备注" name="remark" class="col-md-12" style="height:90px; resize:none;">${entity.remark }</textarea>
                	</div>
				   
               </div>
		       <div class="form-group">
               <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
				    		<div class="col-md-10" id="divfile">
               </div>
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
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
						treeId: 'unitId',
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
					unitId.setValue(${entity.unitId});
					//combobox组件
					var userIdCombobox = new A.combobox({
							render: "#userId",
							datasource:${createPeopleCombobox},
							options:{
								"disable_search_threshold":10
							}
						}).render();
					userIdCombobox.setValue(${entity.userId});
					//监督专业
					var supervisionMajorCombobox = new A.combobox({
							render: "#supervisionMajor",
							datasource:${supervisionMajorCombobox},
							options:{
								"disable_search_threshold":10
							}
						}).render();
					supervisionMajorCombobox.setValue(${entity.supervisionMajor});
					$('#editForm').validate({
						debug:true,
						rules: {
							subject:{required:true,maxlength:20},
							unitId:{required:true,maxlength:20},
							purposePlan:{required:true,maxlength:20},
							planDate:{required:true,maxlength:20},
							supervisionMajor:{required:true,maxlength:20},
							userId:{required:true,maxlength:20},
							remark:{maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/annualSupervisionPlan/update/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
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
										url : format_url("/annualSupervisionPlan/list")
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
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualSupervisionPlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>