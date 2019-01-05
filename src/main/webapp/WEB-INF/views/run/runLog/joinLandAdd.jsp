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
			<form class="form-horizontal" role="form"  style="margin-right:10px;" id="addJLForm">
							<input class="col-md-12" id="rlId" name="rlId" type="hidden" value="${rlId}">
		       <div class="form-group">
					<label class="col-md-4 control-label no-padding-right">
						<span style="color:red;">*</span>电站
					</label>
					<div class="col-md-5">
					   <div id="unitIdDiv"></div>
                	</div>				   
               </div>
		       <div class="form-group">
		        <label class="col-md-4 control-label no-padding-right">
						<span style="color:red;">*</span>已合接地刀闸名称
				    </label>
				    <div class="col-md-8">
						<input class="col-md-8" id="closedName" name="closedName" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                    </div>
               </div>
		       <div class="form-group">              
               <label class="col-md-4 control-label no-padding-right">
						<span style="color:red;">*</span>已封接地线位置及组数
					</label>
					<div class="col-md-8">
	                   <input class="col-md-8" id="closedPosition" name="closedPosition" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                	</div>
                	</div>
		       <div class="form-group">               	
				    <label class="col-md-4 control-label no-padding-right">
						<span style="color:red;">*</span>现存放接地线组数
				    </label>
				    <div class="col-md-8">
						<input class="col-md-8" id="depositGroup" name="depositGroup" type="text" placeholder="${formField.toolTip}" maxlength="64" value="${formField.defaultValue}">
                    </div>
</div>              
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveJLBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//combotree数据源
				//	var combotreeSource = ${combotreeDataSourceMap};
					//combotree组件
					var unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: ${joinLandTreeList},
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
							}
						}
					}).render();
				/*	if(${appFormItemListVO[$i-1].defaultValue}){
						componentTreeName.setValue(${appFormItemListVO[$i-1].defaultValue});
					}*/
					$('#addJLForm').validate({
						rules:  {unitId:{required:true,maxlength:20},
							closedName:{required:true,maxlength:64},
							closedPosition:{required:true,maxlength:64},
							depositGroup:{required:true,maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/joinLand");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addJLForm").serializeObject();
							$("#saveJLBtn").attr({"disabled":"disabled"}); //设置按钮只读
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveJLBtn").on("click", function(){
						$("#addJLForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>