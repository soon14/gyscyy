<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
			<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">计划管理</li>
					<li class="active">计划管理</li>
					<c:if test="${planId!=''}">
					<li class="active">修改</li>
					</c:if>
					<c:if test="${planId==''}">
					<li class="active">新增</li>
					</c:if>
					<li class="active">整体计划新增</li>
				</ul><!-- /.breadcrumb -->
				<div style="margin-right:55px;margin-top:10px;">
        		<button id="button" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        		</div>
        		<h5 class="table-title header smaller blue" style="margin-left:30px">整体信息</h5>
			</div>
	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addWholeForm">
		       	<input id="id"  name="id"  value="${planWholeEntity.id}" type="hidden" />
		       	<input id="uuidWhole"  name="uuidWhole"  value="${planUuidWhole}" type="hidden" />
		       	<input id="num"  name="num"  value="${num}" type="hidden"/>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="128" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						计划开工时间
					</label>
					<div class="col-md-4">
						<input class="col-md-12" id="stratTime" name="stratTime" type="text"
	                    placeholder="" maxlength="64" value="" readonly="readonly">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						计划完成时间
				    </label>
				    <div class="col-md-4">
				    	  <input class="col-md-12" id="endTime" name="endTime" type="text"
	                    placeholder="" maxlength="64" value="" readonly="readonly">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						计划总投资（万元）
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="planTotal" name="planTotal" type="text"
	                    placeholder="" maxlength="64" value="" readonly="readonly">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea class="col-md-12" id="remark" name="remark" 
						style="height:100px; resize:none;"    placeholder="" maxlength="128"></textarea>
                    </div>
               </div>
            </form>
               <div class="form-group">
					<div id="planDetail">
					
					</div>
				</div>
    		<div style="margin-right:55px;">
    			<button id="saveWholeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var id = $('#id').val();
					//加载列表
					$.ajax({url : format_url("/planDetail/index"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"id":id},
						success : function(result) {
							var divshow = $("#planDetail");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
					$('#addWholeForm').validate({
						debug:true,
						rules:  {
							projectName:{ required:true,      maxlength:128},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/planWhole/add");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addWholeForm").serializeObject();
							obj.uuid='${planUuid}';
							obj.planId='${planId}';
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
// 										$("#id").val(result.data.id);
										alert('保存成功');
										if(result.data.planId){
											 $("#page-container").load(format_url('/plan/getEdit'),{"id":result.data.planId});
										}else{
											 $("#page-container").load(format_url('/plan/getAdd'),{"uuid":result.data.uuid});
										}
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveWholeBtn").on("click", function(){
						$("#addWholeForm").submit();
    				});
					$("#button").on("click", function(e){
						if('${planId}'){
							 $("#page-container").load(format_url('/plan/getEdit'),{"id":'${planId}'});
						}else{
							 $("#page-container").load(format_url('/plan/getAdd'),{"uuid":'${planUuid}'});
						}
    				});
				});
			});
        </script>
    </body>
</html>