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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input type="hidden" name="id" value="${entity.id}">
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					    <span style="color:red;">*</span>物资编码
				    </label>
				    <div class="col-md-7">
					    <input class="col-md-12" id="code" name="code" type="text" readonly maxlength="64" value="${entity.code}">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					    <span style="color:red;">*</span>物资名称
				    </label>
				    <div class="col-md-7">
					    <input class="col-md-12" id="name" name="name" type="text" readonly maxlength="64" value="${entity.name}">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					    <span style="color:red;">*</span>规格型号
				    </label>
				    <div class="col-md-7">
					    <input class="col-md-12" id="model" name="model" type="text" readonly maxlength="64" value="${entity.model}">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					    <span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-7">
						<input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					    <span style="color:red;">*</span>生产厂家
				    </label>
				    <div class="col-md-7">
					    <input class="col-md-12" id="manufacturer" name="manufacturer" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.manufacturerName}">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
					 <span style="color:red;">*</span>物资属性
				    </label>
				    <div class="col-md-7">
					    <input class="col-md-12" id="materialType" name="materialType" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.materialTypeName}">
					</div>
				</div>
				<div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>备注
				    </label>
				    <div class="col-md-7">
						<textarea name="backUp" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" readonly="readonly">${entity.backUp}</textarea>
                    </div>
               </div>
<!-- 			   <div class="form-group"> -->
<!-- 				    <label class="col-md-5 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;">*</span>管理方式 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-7"> -->
<%-- 					    <input class="col-md-12" id="management" name="management" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.managementTypeName}"> --%>
<!-- 					</div> -->
<!-- 				</div> -->
				<!-- <div class="form-group">
				    <label class="col-md-5 control-label no-padding-right">
						<span style="color:red;">*</span>供应商
				    </label>
				    <div class="col-md-7">
						<select class="col-md-12 chosen-select" id="supplierId" name="supplierId"></select>
                    </div>
               </div> -->
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
<!--         		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--         			<i class="ace-icon glyphicon glyphicon-floppy-disk"></i> -->
<!--         			保存 -->
<!--         		</button> -->
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
//         			var productionFactoryData = ${productionFactoryCombobox};
// 					var produtionFactoryCombobox = new A.combobox({
// 						render: "#manufacturer",
// 						datasource:productionFactoryData,
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
// 					produtionFactoryCombobox.setValue(appData.manufacturer);
//         			var managementSource = ${managementCombobox};
// 					//combobx组件
//                 	var managementCombobox = new A.combobox({
//                 		render: "#management",
//                 		//返回数据待后台返回
//                 		datasource:managementSource,
//                 		//multiple为true时select可以多选
//                 		multiple:false,
//                 		//allowBlank为false表示不允许为空
//                 		allowBlank: true,
//                 		options:{
//                 			"disable_search_threshold":10
//                 		}
//                 	}).render();
//                 	managementCombobox.setValue(appData.management);
//                 	//计数单位下拉
//                 	var unitComboData = ${unitCombobox};
//                 	var unitCombobox = new A.combobox({
//                 		render: "#unit",
//                 		//返回数据待后台返回
//                 		datasource:unitComboData,
//                 		//multiple为true时select可以多选
//                 		multiple:false,
//                 		//allowBlank为false表示不允许为空
//                 		allowBlank: true,
//                 		options:{
//                 			"disable_search_threshold":10
//                 		}
//                 	}).render();
//                 	unitCombobox.setValue(appData.unit);
//                 	//物资类别下拉
//                 	var materialTypeData = ${materialTypeCombobox};
// 					var materialTypeCombobox = new A.combobox({
// 						render: "#materialType",
// 						datasource:materialTypeData,
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
// 					materialTypeCombobox.setValue(appData.materialType);
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
					}).render();
					supplierCombobox.setValue(appData.supplierId); */

				});
			});
        </script>
    </body>
</html>