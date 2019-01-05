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
						     <input class="col-md-12" id="id" name="id" type="hidden" value="${dataMap.id}">
			
			     <input class="col-md-12" id="rlId" name="rlId" type="hidden" value="${dataMap.rlId}">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
				    			<input placeholder="请输入组织机构" id="unit" name="unit" type="hidden" value="${dataMap.unitId}" class="col-md-12">				    
				    
					    <div id="unitIdDiv"></div>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 设备类型
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="deviceType" name="deviceType" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${dataMap.deviceType}">
				    </div>	
				    
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>设备
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="deviceId" name="deviceId" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${dataMap.deviceId}">
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>运行方式
				    </label>
				    <div class="col-md-4">
					    <select class="col-md-12 chosen-select" id="runWayEditDiv" name="runWay"></select>
					</div>
				</div>
			</form>
    		<div style="margin-right:100px;margin-top:20px"">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
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
        			var appData = ${dataMapJson};
					//combotree组件
					var unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: ${runWayTreeList},
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
					unitIdCombotree.setValue($("#unit").val());

					//combobx组件
                	var runWayCombobox = new A.combobox({
                		render: "#runWayEditDiv",
                		//返回数据待后台返回TODO
                		datasource:${runWayCombobox},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		},
						initValue:"${dataMap.runWay}"
                	}).render();
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {unitId:{required:true,      maxlength:20},
							deviceId:{required:true,      maxlength:20},
							deviceType:{required:true,      maxlength:64},
							runWay:{required:true,      maxlength:20},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/runWay/"+id);
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
									listFormDialog.close();
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
				});
			});
        </script>
    </body>
</html>