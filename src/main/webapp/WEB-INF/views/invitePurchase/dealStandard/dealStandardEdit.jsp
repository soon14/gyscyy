<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					招标采购
				</li>
				<li class="active">定标请示函</li>
				<li class="active">修改</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
				<input class="col-md-12" id="status" name="status" type="hidden" placeholder="" maxlength="20" value="${ entity.status }">
				<input class="col-md-12" id="createId" name="createId" type="hidden" placeholder="" maxlength="20" value="${ entity.createId }">
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>所属部门
				    </label>
				    <div class="col-md-4">
						<select id="unitId" name="unitId" class="col-md-12 chosen-select" ></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>采购事项
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" value="${ entity.name }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>立项单位
					</label>
					<div class="col-md-4">
	                   <select id="departmentId" name="departmentId" class="col-md-12 chosen-select" ></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>请示批复时间
				    </label>
				    <div class="col-md-4">
						<div id="replyTimeDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark" class="col-md-12" style="height:100px; resize:none;">${ entity.remark }</textarea>
                    </div>
               </div>
               <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
					<div class="col-md-10" id="divfile"></div>
               </div>

            </form>
    		<div style="margin-right:100px;">
    			<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var appData = ${entityJson};
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.file},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					//所属部门
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						datasource:${unitIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					unitIdCombobox.setValue(appData.unitId);
					
					//立项单位
					var departmentIdCombobox = new A.combobox({
						render: "#departmentId",
						datasource:${departmentIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					departmentIdCombobox.setValue(appData.departmentId);
					
					//请示批复时间
					var replyTimeDiv = new A.my97datepicker({
						id: 'replyTimeDivId',
						name:'replyTime',
						render:'#replyTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					replyTimeDiv.setValue(appData.replyTimeStr);
					
					$('#editForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:20},
							name:{required:true,maxlength:64},
							departmentId:{required:true,maxlength:20},
							replyTime:{required:true,maxlength:20},
							remark:{maxlength:128},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/dealStandard/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							//附件上传
							obj.file=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('修改成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/dealStandard/index")
									});
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
						$("#editForm").submit();
    				});
// 					$("#submitBtn").on("click", function(){
// 						rewardDialog = new A.dialog({
// 							title:"生产技术处审批",
// 							url:format_url("/accidentMeasuresPlan/sureSubmit"),
// 							height:450,
// 							width:500
// 						}).render();
//     				});
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/dealStandard/index")
						});
    				});
				});
			});
        </script>
    </body>
</html>