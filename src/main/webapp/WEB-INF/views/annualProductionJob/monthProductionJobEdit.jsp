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
					计划管理
				</li>
				<li class="active">生产运营计划</li>
				<li class="active">指标类计划</li>
				<li class="active">月度生产工作计划</li>
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
				<input class="col-md-12" id="type" name="type" type="hidden" placeholder="" maxlength="20" value="${ entity.type }">
				<input class="col-md-12" id="approveStatus" name="approveStatus" type="hidden" placeholder="" maxlength="20" value="${ entity.approveStatus }">
				<input class="col-md-12" id="result" name="result" type="hidden" placeholder="" maxlength="20" value="${ entity.result }">
				<input class="col-md-12" id="sealStatus" name="sealStatus" type="hidden" placeholder="" maxlength="20" value="${ entity.result }">
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
						<select id="unitId" name="unitId" class="col-md-12 chosen-select" ></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划内容
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="content" name="content" type="text" placeholder="" value="${ entity.content }">
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="workName" name="workName" type="text" placeholder="" value="${ entity.workName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划日期
				    </label>
				    <div class="col-md-4">
						<div id="planTimeDiv"></div>
                    </div>
               </div>
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成状态
					</label>
					<div class="col-md-4">
	                   <select id="completeStatus" name="completeStatus" class="col-md-12 chosen-select" ></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成时间
				    </label>
				    <div class="col-md-4">
						<div id="completeDateDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						进度安排
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="" name="progress" class="col-md-12" style="height:100px; resize:none;">${ entity.progress }</textarea>
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
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					var id = $('#id').val();
					var type = $('#type').val();
					//生产单位
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
					//完成状态
					var completeStatusCombobox = new A.combobox({
						render: "#completeStatus",
						datasource:${completeStatusCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					completeStatusCombobox.setValue(appData.completeStatus);
					//完成时间
					var completeDateDiv = new A.my97datepicker({
						id: 'completeDateId',
						name:'completeDate',
						render:'#completeDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					completeDateDiv.setValue(appData.completeDateStr);
					//计划月份
					var planTimeDiv = new A.my97datepicker({
						id: 'planTimeDivId',
						name:'planTime',
						render:'#planTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					planTimeDiv.setValue(appData.planMonthTimeStr);
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${entity.file}'==""?[]:${entity.file},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					$('#editForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:20},
							workName:{required:true,maxlength:64},
							content:{required:true,maxlength:128},
							planTime:{required:true,maxlength:64},
							completeStatus:{required:true,maxlength:64},
							completeDate:{required:true,maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/annualProductionJob/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.planTime=$("#planTimeDivId").val()+" 00:00:00";
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
										url : format_url("/annualProductionJob/index/"+type)
									});
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#editForm").submit();
    				});

					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualProductionJob/index/"+type)
						});
    				});
				});
			});
        </script>
    </body>
</html>