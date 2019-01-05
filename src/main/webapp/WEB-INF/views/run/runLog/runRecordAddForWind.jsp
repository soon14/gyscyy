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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addRRForm">
							<input class="col-md-12" id="rlId" name="rlId" type="hidden" value="${rlId}">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
					</label>
					<div class="col-md-4">
<!-- 					   <div id="unitIdDiv" ></div> -->
					   <input type="text" value="${sysUserEntity.unitName}"  readonly="readonly" class="col-md-12" />
					   <input type="hidden" name="unitId"  value="${sysUserEntity.unitId}" />
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>记录类型
				    </label>
				    <div class="col-md-4">
                	<select id="recordTypeDiv" class="col-md-12 chosen-select" name="recordType" data-placeholder="请选择记录类型"></select>					                                     
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>记录时间
					</label>
					<div class="col-md-4">
				    <input class="col-md-12" id="recordTime" name="recordTime" type="text" placeholder="${formField.toolTip}" maxlength="20" value="${recordTime}" readonly="readonly">				    					
                	</div>
				   	   <label class="col-md-2 control-label no-padding-right">
						负责人
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="fZR" placeholder="请输入负责人" name="fZR" type="text" placeholder="${formField.toolTip}" maxlength="20" >				    					
                    </div>	   
               </div>
               <div class="form-group">
  				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>记录内容
				    </label>	              
                <div class="col-md-10">
                    <textarea placeholder="请输入记录内容" name="recordContent"  class="col-md-12" style="height:150px; resize:none;"></textarea>                   				    
                    </div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveRRBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					//combotree组件
// 					var unitIdCombotree = new A.combotree({
// 						render: "#unitIdDiv",
// 						name: "unitId",
// 						//返回数据待后台返回TODO
// 						datasource: ${runRecordTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'unitId',
// 							data: {
// 								key: {
// 									name: "name"
// 								},
// 								simpleData: {
// 									enable: true,
// 									idKey: "id",
// 									pIdKey: "parentId"
// 								}
// 							}
// 						}
// 					}).render();
// 					unitIdCombotree.setValue(${sysUserEntity.unitId});
					var recordTypeCombobox = new A.combobox({
						render: "#recordTypeDiv",
						datasource:${recordTypeCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addRRForm').validate({
						debug:true,
						rules:  {rlId:{required:true},
							unitId:{required:true,maxlength:20},
							recordType:{required:true,maxlength:20},
							recordTime:{required:true,date:true},
							recordContent:{required:true,maxlength:250},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/runRecord/addForWind");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addRRForm").serializeObject();
							$("#saveRRBtn").attr({"disabled":"disabled"});

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
					$("#saveRRBtn").on("click", function(){
						$("#addRRForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>