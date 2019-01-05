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
				<div class="col-md-12" >
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="name" name="name" type="text" placeholder="请输入名称" maxlength="64" value="">
								</div>
							<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
								<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" placeholder="请输入编码" maxlength="64" value="">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">创建时间</label>
								<div class="col-md-4">
										<div id="createDateDiv"></div>
								</div>
							<label class="col-md-2 control-label no-padding-right">值长</label>
								<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="teamLeaderId" name="teamLeaderId"></select>
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea name="remark" placeholder="请输入备注内容" style="height:100px; resize:none;" class="col-md-12" maxlength="255"></textarea>
									</div>
								</div>
			</form>
		</div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//日期组件
					var createDateDatePicker = new A.my97datepicker({
						id: "createDateId",
						name:"createDate",
						render:"#createDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//combobx组件
					var teamLeaderIdCombobox = new A.combobox({
						render: "#teamLeaderId",
						//返回数据待后台返回TODO
						datasource:'${'teamLeaderIdCombobox'}',
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {id:{      maxlength:20},name:{required:true,      maxlength:64},code:{required:true,      maxlength:64},createDate:{      maxlength:0},teamLeaderId:{      maxlength:11},remark:{      maxlength:255},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/teamMemApp");
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
    				
					$.getScript(format_url("/static/js/testUrl.js"),function(){
					});
				});
			});
        </script>
    </body>
</html>