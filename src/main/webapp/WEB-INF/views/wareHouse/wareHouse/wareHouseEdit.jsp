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
				<input id="id" name="id" type="hidden" value="${ entity.id }">
				<input id="status" name="status" type="hidden" value="${ entity.status }">
				<input id="storeKeeperId" name="storeKeeperId"  type="hidden" value="${entity.storeKeeper}" class="col-md-12">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>仓库名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="wareHouseName" name="wareHouseName" type="text" placeholder="" maxlength="20" value="${ entity.wareHouseName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>所属单位
				    </label>
				    <div class="col-md-4">
					    <div id="unitIdDiv"></div>
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>父仓库
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="parentWareHouse" name="parentWareHouse"></select>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>仓库类型
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="wareHouseType" name="wareHouseType"></select>
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 库管员
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="storeKeeper" name="storeKeeper"></select>
					</div>
				</div>
				<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>仓库地址
				    </label>
				    <div class="col-md-10">
					    <input class="col-md-12" id="wareHouseAddress" name="wareHouseAddress" type="text" placeholder="" maxlength="64" value="${ entity.wareHouseAddress }">
				    </div>	
				</div>
			</form>
    		<div style="margin-right:100px;margin-top:100px">
        		<button id="cancelBtn" class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					//combotree组件
					var unitNameIdTreeList=${unitNameIdTreeList};
					var unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource:unitNameIdTreeList,
						width:"210px",
						options: {
							treeId: "unitId",
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
					unitIdCombotree.setValue(appData.unitId);
					//combobx组件
					var parentWareHouse=${parentWareHouse};
                	var parentWareHouseCombobox = new A.combobox({
                		render: "#parentWareHouse",
                		//返回数据待后台返回TODO
                		datasource:parentWareHouse,
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	parentWareHouseCombobox.setValue(appData.parentWareHouse);
					//combobx组件
					var wareHouseType=${wareHouseType};
                	var wareHouseTypeCombobox = new A.combobox({
                		render: "#wareHouseType",
                		//返回数据待后台返回TODO
                		datasource:wareHouseType,
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	wareHouseTypeCombobox.setValue(appData.wareHouseType);
                	//库管员多选下拉框
                	var storeKeeperIds = $("#storeKeeperId").val();
                    var userListBox=${userListBox};
                    
					var storeKeeperIds = appData.storeKeeper;
					if(storeKeeperIds.indexOf("]")>-1){
						storeKeeperIds = appData.storeKeeper.substring(1, appData.storeKeeper.length-1);
					}
					var storeKeeperCombobox = new A.combobox({
						render: "#storeKeeper",
						//返回数据待后台返回TODO
						datasource:userListBox,
						//multiple为true时select可以多选
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
                		initValue:storeKeeperIds
					}).render();
					storeKeeperCombobox.setValue('${entity.storeKeeper}');
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
								wareHouseName:{required:true,maxlength:20},
								unitId:{required:true,maxlength:20},
								parentWareHouse:{required:true,maxlength:20},
								wareHouseType:{required:true,maxlength:20},
								wareHouseAddress:{required:true,maxlength:64},
								storeKeeper:{required:true,maxlength:20}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/wareHouse/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							
							var storeKeeper = storeKeeperCombobox.getValue();
							if(storeKeeper){
								obj.storeKeeper = storeKeeper;
							}
							
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="exception"){
										alert('修改失败！同一单位下仓库名称不能重复！');
										return;
									}else{
										alert('修改成功');
										listFormDialog.close();
										
									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					//保存按钮
					$("#editBtn").on("click", function(){
	 					$("#editForm").submit();
  				});
					
					//取消按钮
					$("#cancelBtn").on("click", function(){
						$("#page-container").load(format_url('/wareHouse/index/'));
   					});
				});
			});
        </script>
    </body>
</html>