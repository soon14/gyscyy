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
    
    			<li>物资管理</li>
    			<li>安全工器具管理</li>
    			<li class="active">修改</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right" style="margin-right:165px;">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">基础信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			     <input class="col-md-12" id="securityApparatus_id" name="id" type="hidden" value="${ entity.id }">
			     <input class="col-md-12" id="securityApparatus_code" name="code" type="hidden" value="${ entity.code }">
			   <div class="form-group">
			   		<div class="col-md-2">
                    </div>
				    <label class="col-md-1 control-label no-padding-right">
					    <span style="color:red;">*</span>工具名称
				    </label>
				    <div class="col-md-3">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="请输入工具名称" maxlength="64" value="${ entity.name }">
				    </div>	
				    <label class="col-md-1 control-label no-padding-right">
					    <span style="color:red;">*</span>所属单位
				    </label>
				    <div class="col-md-3">
					    <div id="securityApparatus_unitIdDiv"></div>
					</div>
					<div class="col-md-2">
                    </div>
				</div>
				<div class="form-group">
					<div class="col-md-2">
                    </div>
					<label class="col-md-1 control-label no-padding-right">
					    检验日期
				    </label>
				    <div class="col-md-3">
					    <div id="securityApparatus_checkDateDiv"></div>
				    </div>	
				    <label class="col-md-1 control-label no-padding-right">
					    到期日期
				    </label>
				    <div class="col-md-3">
					    <div id="securityApparatus_endDateDiv"></div>
					</div>
						<div class="col-md-2">
                    </div>
				</div>
		        <div class="form-group">
		        	<div class="col-md-2">
                    </div>
					<label class="col-md-1 control-label no-padding-right">备注</label>
					<div class="col-md-7">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
					</div>
						<div class="col-md-2">
                    </div>
               </div>
	          <div class="form-group form-horizontal">
	          		<div class="col-md-2">
                    </div>
						<label class="col-md-1 control-label no-padding-right">
							附件
						</label>
						<div class="col-xs-7" id="editAttachmentDiv">
						</div>
					<div class="col-md-2">
                    </div>
	           </div> 
			</form>
    		<div style="margin-right:100px;">
        		
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:165px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
				var appData = ${entityJson};
					//combotree组件
					var unitNameTreeData = ${unitNameIdTreeList};
					var securityApparatus_unitIdCombotree = new A.combotree({
						render: "#securityApparatus_unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: unitNameTreeData,
						width:"210px",
						options: {
							treeId: "securityApparatus_unitId",
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
					securityApparatus_unitIdCombotree.setValue(${entity.unitId});
					//日期组件
					var securityApparatus_checkDateDatePicker = new A.my97datepicker({
						id: "securityApparatus_checkDateId",
						name: "checkDate",
						render: "#securityApparatus_checkDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					securityApparatus_checkDateDatePicker.setValue(appData.checkDate);
					//日期组件
					var securityApparatus_endDateDatePicker = new A.my97datepicker({
						id: "securityApparatus_endDateId",
						name: "endDate",
						render: "#securityApparatus_endDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					securityApparatus_endDateDatePicker.setValue(appData.endDate);
					//附件上传fileId传数组
					var uploaddropzone=new A.uploaddropzone({
						render : "#editAttachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules:  {
								name:{required:true,   maxlength:64},
								unitId:{required:true,      maxlength:20},
								attachment:{maxlength:128},
								checkDate:{ required:true,  date:true,   maxlength:0},
								endDate:{ required:true,  date:true,   maxlength:0}},
							submitHandler: function (form) {
							//添加按钮
							var url = format_url("/securityApparatus/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									A.loadPage({
									render : '#page-container',
									url : format_url('/securityApparatus/index')
								});
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
    				$("#backBtnAdd").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/securityApparatus/index")
    					});
    				});
				});
			});
        </script>
    </body>
</html>