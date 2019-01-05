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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="techniaclAddForm">
						<input id="uuid" name="uuidCode" type="hidden" value="${uuid}"/>
						<input id="orderSqe" name="orderSqe" type="hidden" value="${total}"/>
						<input id="technicalId" name="technicalId" type="hidden" value="${technicalId}"/>
							<div class="form-group" >
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>负责人</label>
								<div class="col-md-4">
								 <select id="picId" name="picId"    class="form-control chosen-select" ></select>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>监督专业</label>
									<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="jdzyId" name="jdzyId"></select>
								</div>
							</div>
							<div class="form-group" >
							<label class="col-md-2 control-label no-padding-right">计划时间</label>
									<div class="col-md-4">
										<div id="planTimeDiv"></div>
								</div>
							</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作项目</label>
								<div class="col-md-10">
									<textarea id="content" name="content"  style="height:80px; resize:none;" class="col-md-12" maxlength="28"></textarea>
								</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划缘由</label>
								<div class="col-md-10">
									<textarea id="danger" name="danger"  style="height:80px; resize:none;" class="col-md-12" maxlength="28"></textarea>
								</div>
						</div>
								
			</form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveWorkBtnSafe" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//技术监督下拉框
					var jdzyCombobox = new A.combobox({
						render: '#jdzyId',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					//工作负责人
					var searchguarderName = new A.combobox({
						render : "#picId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "timeId",
						name:"time",
						render:"#planTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					var flag=${flag};
				
						$('#techniaclAddForm').validate({
							debug:true,
							rules:  {
								picId:{required:true,maxlength:20},
								content:{required:true,maxlength:128},
								danger:{required:true,maxlength:128},
								jdzyId:{required:true,maxlength:128},
								}, 
							submitHandler: function (form) {
								//添加按钮
								var url = format_url("/technicalWork");
								var obj = $("#techniaclAddForm").serializeObject();
								obj.workType=${flag};
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('添加成功');
										workSafeOneDialog.close();
									},
									error:function(v,n){
										alert('添加失败');
									}
								});
							}
						});
					
					$("#saveWorkBtnSafe").on("click", function(){
						$("#techniaclAddForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>