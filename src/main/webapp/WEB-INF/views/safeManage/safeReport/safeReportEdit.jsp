<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="editForm">
			<input id="id" name="id" type="hidden" value="${entity.id }">      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userName" name="userName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
										<select id="unitId" name="unitId" ></select>
								</div>
               </div>
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
					</label>
					<div class="col-md-4">
										<div id="yearNumDiv"></div>
								</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
						<div id="timeDiv"></div>
                    </div>
               </div>
         
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${entity.name }">
								</div>
               </div>
               <div class="form-group ">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
								<div class="col-md-10" id="divfile">
								</div>
							</div>
			</form>
    		<div style="margin-right:100px;">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					var yearNumPicker = new A.my97datepicker({
						id: "yearNumId",
						name:"yearNum",
						render:"#yearNumDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					yearNumPicker.setValue('<fmt:formatDate pattern="yyyy" value="${entity.yearNum}" type="both"/>');
					//日期组件
					var timePicker = new A.my97datepicker({
						id: "timeId",
						name:"time",
						render:"#timeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					timePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.time}" type="both"/>');
					//单位
					 searchunitid = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue(${entity.unitId});
					$('#editForm').validate({
						debug:true,
						rules: {
							name:{required:true,maxlength:4000},
							yearNum:{required:true,maxlength:200},
							unitId:{required:true,maxlength:200},
							time:{required:true,maxlength:200},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeReport/update");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.yearNum=$("#yearNumId").val()+"-01-01 00:00";
							obj.time=$("#timeId").val()+" 00:00";
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
						var time = $("#timeId").val();
						var yearNum = $("#yearNumId").val();
						var year = time.substring(0,4);
						if(yearNum!=year){
							alert("年号和时间未对应！")
							return;
						}
						if(uploaddropzone.getValue()==""){
							alert("未上传附件！");
							return;
						}
    					$("#editForm").submit();
    				});
					$("#backButton").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/safeReport/index')
						});
    				});
				});
			});
        </script>
    </body>
</html>