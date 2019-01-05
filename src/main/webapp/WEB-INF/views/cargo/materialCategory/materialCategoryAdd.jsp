<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
				<input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${sysUserEntity.id }">
		       <div class="form-group">
					<label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>物资编码
					</label>
					<div class="col-md-7">
	                   <input class="col-md-12" id="code" name="code" type="text" placeholder="请输入物资编码" maxlength="64" readonly="readonly" value="${genetateCode}" >
                	</div>
                </div>
		       <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>物资名称
				    </label>
				    <div class="col-md-7">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="请输入物资名称" maxlength="64" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>规格型号
					</label>
					<div class="col-md-7">
	                   <input class="col-md-12" id="model" name="model" type="text" placeholder="请输入规格型号" maxlength="64" value=" ">
                	</div>
                </div>
		       <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-7">
						<select id="unit" name="unit" class="form-control chosen-select" style="width: 150px;"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>生产厂家
					</label>
					<div class="col-md-7">
						<select id="manufacturer" name="manufacturer" class="form-control chosen-select" style="width: 150px;"></select>
                	</div>
                </div>
		       <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>物资类别
				    </label>
				    <div class="col-md-7">
						<select class="col-md-12 chosen-select" id="materialType" name="materialType"></select>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>备注
				    </label>
				    <div class="col-md-7">
						<textarea name="backUp" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
                    </div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 				    <label class="col-md-5 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>管理方式 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-7"> -->
<!-- 						<select class="col-md-12 chosen-select" id="management" name="management"></select> -->
<!--                     </div> -->
<!--                </div> -->
              <!--  <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>供应商
				    </label>
				    <div class="col-md-7">
						<select class="col-md-12 chosen-select" id="supplierId" name="supplierId"></select>
                    </div>
               </div> -->
            </form>
    		<div style="margin-right:100px;margin-top:35px">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var productionFactoryData = ${productionFactoryCombobox};
					var produtionFactoryCombobox = new A.combobox({
						render: "#manufacturer",
						datasource:productionFactoryData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combobx组件
					var managementSource = ${managementCombobox};
// 					var managementCombobox = new A.combobox({
// 						render: "#management",
// 						//返回数据待后台返回
// 						datasource:managementSource,
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
					//计数单位下拉
					var unitSource = ${unitCombobox};
					var unitCombobox = new A.combobox({
						render: "#unit",
						//返回数据待后台返回
						datasource:unitSource,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var materialTypeData = ${materialTypeCombobox};
					var materialTypeCombobox = new A.combobox({
						render: "#materialType",
						datasource:materialTypeData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//供应商下拉框
					/* var supplierData = ${supplierCombobox};
					var supplierCombobox = new A.combobox({
						render: "#supplierId",
						datasource:supplierData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render(); */
					$('#addForm').validate({
						debug:true,
						rules:  {
							code:{required:true, maxlength:64},
							name:{required:true, maxlength:64},
							model:{required:true, maxlength:64},
							unit:{required:true, maxlength:64},
							manufacturer:{required:true,maxlength:64},
// 							management:{required:true,maxlength:64},
							materialType:{required:true,maxlength:64}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/materialCategory/addSave");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert(result.result);
									//alert('添加成功');
									listFormDialog.close();
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