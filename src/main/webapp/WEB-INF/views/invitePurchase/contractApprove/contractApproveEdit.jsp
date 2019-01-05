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
					招标采购
				</li>
				<li class="active">合同审批</li>
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
				<input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
				<input class="col-md-12" id="status" name="status" type="hidden" placeholder="" maxlength="20" value="${ entity.status }">
				<input class="col-md-12" id="createId" name="createId" type="hidden" placeholder="" maxlength="20" value="${ entity.createId }">
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>所属部门
				    </label>
				    <div class="col-md-4">
						<select id="unitId" name="unitId" class="col-md-12 chosen-select" ></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="contractCode" name="contractCode" type="text" placeholder="" value="${ entity.contractCode }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" type="text" placeholder="" value="${ entity.name }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>供应商
				    </label>
				    <div class="col-md-4">
						<select id="supplierId" name="supplierId" class="col-md-12 chosen-select" ></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同金额(元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="money" name="money" type="text" placeholder="" value="${entity.money }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>采购方式
					</label>
					<div class="col-md-4">
	                   <select id="purchaseModeId" name="purchaseModeId" class="col-md-12 chosen-select" ></select>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
					<div class="col-md-10" id="divfile"></div>
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
					
					//所属部门
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						datasource:${unitIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					unitIdCombobox.setValue(appData.unitId);
					
					//供应商
					var supplierIdCombobox = new A.combobox({
						render: "#supplierId",
						datasource:${supplierIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					supplierIdCombobox.setValue(appData.supplierId);
					
					//采购方式
					var purchaseModeIdCombobox = new A.combobox({
						render: "#purchaseModeId",
						datasource:${purchaseModeIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.file},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					$('#editForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:20},
							contractCode:{required:true,maxlength:64},
							name:{required:true,maxlength:64},
							supplierId:{required:true,maxlength:20},
							money:{number:true,required:true,maxlength:20},
							purchaseModeId:{required:true,maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/contractApprove/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							//附件上传
							obj.file=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('修改成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/contractApprove/index")
									});
								},
								error:function(v,n){
									alert('修改失败');
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
							url : format_url("/contractApprove/index")
						});
    				});
				});
			});
        </script>
    </body>
</html>