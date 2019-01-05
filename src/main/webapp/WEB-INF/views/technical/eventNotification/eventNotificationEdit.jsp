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
			<input id="id" name="id" value="${id}" type="hidden">
			<input id="status" name="status" value="${entity.status}" type="hidden">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>填报单位
				    </label>
				    <div class="col-md-4">
				    <div id="searchunitName"></div>
<%-- 					    <input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" maxlength="20" value="${ entity.unitId }"> --%>
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>填报人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="20" value="${ sysUserEntity.name }">
					    <input class="col-md-12" id="userId" name="userId" type="hidden" readonly="readonly" placeholder="" maxlength="20" value="${ sysUserEntity.id }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  填报时间
				    </label>
				    <div class="col-md-4">
				    <div id="tbsjDiv"></div>
<%-- 					    <input class="col-md-12" id="fillTime" name="fillTime" type="text" placeholder="" maxlength="20" value="${ entity.fillTime }"> --%>
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>事件名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="eventName" name="eventName"  type="text" placeholder="" maxlength="20" value="${ entity.eventName }">
				    </div>	
				</div>
			   <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
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
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					var unitNameCombotree = new A.combotree({
						render: "#searchunitName",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
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
					unitNameCombotree.setValue('${entity.unitId}');
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "tbsjDivId",
						name:"fillTime",
						render:"#tbsjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					plandateStartDatePicker.setValue('${entity.fillTime}');
					$('#editForm').validate({
						debug:true,
						rules: {id:{maxlength:20},
							unitId:{required:true,maxlength:20},
							userId:{required:true,maxlength:20},
							fillTime:{required:true,maxlength:20},
							eventName:{required:true,maxlength:20},
							fileId:{maxlength:4000},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/eventNotification/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
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