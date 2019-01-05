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
		      <input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
		      <input id="status"  name="status"  value="${entity.status}" type="hidden"/>
<%-- 		      <input id="name" class="col-md-12" name="name" value="${entity.name}"  type="hidden"/> --%>
		          <input id="type" class="col-md-12" name="type" value="${entity.type}"  type="hidden"/>
		      		
		      		<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span> 填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="hidden" maxlength="64" readonly="readonly" value="${entity.createUserId }">
				         <input  class="col-md-12" id="userName" name="userName" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div> 
                    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
				    	<select id="signUnitId" name="signUnitId" ></select>
                    	<input id="signUnitName"  name="signUnitName"  type="hidden"/>
				    </div>
				</div>
			   <div class="form-group">
			       
					<label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span> 年号
				    </label>
				    <div class="col-md-4">
					    <div id="targetDateDiv"></div>
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
					    <div id="signDateDiv"></div>
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
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						备注 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10"> -->
<%-- 	                   <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;"  placeholder="" maxlength="128">${ entity.remark}</textarea> --%>
<!--                 	</div> -->
<!--                </div> -->
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span>附件
				    </label>
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
        			//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options : {
							maxFiles:1,
						}
					}).render();
        			
					//日期组件
					var targetDateDatePicker = new A.my97datepicker({
						id: "targetDateId",
						name: "targetDate",
						render: "#targetDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					targetDateDatePicker.setValue(appData.targetDateString);
					//日期组件
					var signDateDatePicker = new A.my97datepicker({
						id: "signDateId",
						name: "signDate",
						render: "#signDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					signDateDatePicker.setValue(appData.signDateString);
					
					//单位
					 searchunitid = new A.combobox({
						render : "#signUnitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue(appData.signUnitId);
					$("#signUnitName").val('${entity.signUnitName}');
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{      maxlength:20},
							name:{ required:true,maxlength:64},
							targetDate:{  required:true,    maxlength:64},
							signDate:{ required:true, maxlength:64},
							signUnitId:{required:true,maxlength:64}
// 							remark:{      maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/targetManage/targetEditPro/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.targetDate = $('#targetDateId').val()+"-01-01 00:00:00";
							obj.signDate = $('#signDateId').val()+" 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请至少添加一个附件");
								return;
							}
							
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
						
						var yearNumStr = targetDateDatePicker.getValue();
						var checkDateStr = signDateDatePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
							alert("年号和时间不符");
							return;
						}
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>