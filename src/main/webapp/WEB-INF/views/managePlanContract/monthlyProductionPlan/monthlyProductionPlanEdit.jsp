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
					计划/合同管理
				</li>
				<li class="active">运维检修类计划</li>
				<li class="active">月度生产物资计划</li>
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
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			 <input id="id" name="id" value="${entity.id }" type="hidden">
			  <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
<!-- 						<input class="col-md-12" id="unitId" name="unitId" type="text" placeholder="" maxlength="20" value=""> -->
						<div id="unitIdDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传人
					</label>
					<div class="col-md-4">
<!-- 	                   <input class="col-md-12" id="userId" name="userId" type="text" placeholder="" maxlength="20" value=""> -->
<!-- 	                   <select class="col-md-12 chosen-select" id="userId" name="userId"></select> -->
						<input class="col-md-12" id="name" readonly="readonly"  type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
	                   <input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>物资名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="materialName" name="materialName" type="text" placeholder="" maxlength="20" value="${entity.materialName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>物资规格
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="materialType" name="materialType" type="text" placeholder="" maxlength="20" value="${entity.materialType }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>费用预算(万元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="charge" name="charge" type="text" placeholder="" maxlength="20" value="${entity.charge }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>型号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="version" name="version" type="text" placeholder="" maxlength="20" value="${entity.version }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="count" name="count" type="text" placeholder="" maxlength="20" value="${entity.count }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>功能要求
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="fuctionRequirement" name="fuctionRequirement" type="text" placeholder="" maxlength="20" value="${entity.fuctionRequirement }">
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
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
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
					unitId.setValue(${entity.unitId});
					//上传人
// 					var userId = new A.combobox({
// 						render : "#userId",
// 						datasource : ${createPeopleCombobox},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					userId.setValue(${entity.userId});
					$('#editForm').validate({
						debug:true,
						rules: {
							unitId:{required:true,maxlength:20},
							materialName:{required:true,maxlength:20},
							materialType:{required:true,maxlength:20},
							charge:{number:true,required:true,maxlength:20},
							version:{required:true,maxlength:20},
							count:{digits:true,required:true,maxlength:20},
							fuctionRequirement:{required:true,maxlength:20},
							userId:{required:true,maxlength:20},
							remark:{maxlength:20},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/monthlyProductionPlan/update/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
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
										url : format_url("/monthlyProductionPlan/list")
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
							url : format_url("/monthlyProductionPlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>