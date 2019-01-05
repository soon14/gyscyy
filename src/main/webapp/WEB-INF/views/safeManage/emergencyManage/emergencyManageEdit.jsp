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
			<input id="id" name="id" type="hidden" value="${entity.id }">
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
						<select id="unitIdDiv" name="unitId" class="col-md-12 chosen-select"></select>
					</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>年号
					</label>
					<div class="col-md-4">
							<div id="yearNumDiv1"></div>
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
						<span style="color:red;">*</span>类别
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="type" name="type"></select>
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
					//部门控件下拉树
// 					var unitId = new A.combotree({
// 					render: "#unitIdDiv",
// 					name: 'unitId',
// 					//返回数据待后台返回TODO
// 					datasource: ${unitNameIdTreeList},
// 					width:"210px",
// 					options: {
// 						treeId: 'searchunitId',
// 						data: {
// 							key: {
// 								name: "name"
// 							},
// 							simpleData: {
// 								enable: true,
// 								idKey: "id",
// 								pIdKey: "parentId"
// 							}
// 						},
// 					}
// 				}).render();
// 					unitId.setValue(${entity.unitId});

                //单位
					 searchunitid = new A.combobox({
						render : "#unitIdDiv",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue(${entity.unitId});
					 
					var type = new A.combobox({
						render : "#type",
						datasource : ${emergencyType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					type.setValue("${entity.type}");
					var yearNumPicker = new A.my97datepicker({
						id: "yearNumId1",
						name:"yearNum1",
						render:"#yearNumDiv1",
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
					$('#editForm').validate({
						debug:true,
						rules: {
							unitId:{required:true,maxlength:30},
							userId:{required:true,maxlength:20},
							type:{required:true,maxlength:20},
							fileId:{required:true,maxlength:4000},
							name:{required:true,maxlength:20},
							time:{required:true,maxlength:20},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/emergencyManage/update");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.yearNum=$("#yearNumId1").val()+"-01-01 00:00";
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
						var yearNum = $("#yearNumId1").val();
						var year = time.substring(0,4);
						if(yearNum!=year){
							alert("年号和时间未对应！")
							return;
						}
						if(uploaddropzone.getValue()==""){
							alert("未上传相关资料！");
							return;
						}
    					$("#editForm").submit();
    				});
					$("#backButton").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/emergencyManage/index')
						});
    				});
				});
			});
        </script>
    </body>
</html>