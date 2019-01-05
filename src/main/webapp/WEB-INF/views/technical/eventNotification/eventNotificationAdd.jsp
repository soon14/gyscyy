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
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报单位
				    </label>
				    <div class="col-md-4">
										<div id="searchunitName"></div>
								    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
					</label>
					<div class="col-md-4">
<!-- 					<select id="searchTbrAddName" name="userId"></select> -->
										<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
										<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${userEntity.id }">
<!-- 	                   <input class="col-md-12" id="userId" name="userId" type="text" placeholder="" maxlength="20" value=""> -->
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报时间
				    </label>
				    <div class="col-md-4">
										<div id="tbsjDiv"></div>
									</div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>事件名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="eventName" name="eventName" type="text" placeholder="" maxlength="20" value="">
                	</div>
               </div>
		       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
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
					unitNameCombotree.setValue('${userEntity.unitId}');
					
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
					$('#addForm').validate({
						debug:true,
						rules:  {id:{maxlength:20},
							unitId:{required:true,maxlength:20},
							userId:{required:true,maxlength:20},
							fillTime:{required:true,maxlength:20},
							eventName:{required:true,maxlength:20},
							fileId:{maxlength:4000},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/eventNotification");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
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
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>