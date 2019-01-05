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
    		<form class="form-horizontal" role="form"  style="margin-right:100px;" id="overhaulRecordForm">
<%-- 		       <input id="orgId" name="orgId" type="hidden"  value="${orgnaizationId}"/> --%>
<%-- 		       <input id="unitId" name="unitId" type="hidden"  value="${orgnaizationId}"/> --%>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>开始时间
				    </label>
				    <div class="col-md-4">
						<div id="startDateDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>结束时间
					</label>
					<div class="col-md-4">
					   <div id="endDateDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>项目名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检修负责人
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="dutyUserId" name="dutyUserId"></select>
	                </div>
               </div>
		       <div class="form-group">
                	<label class="col-md-2 control-label no-padding-right">
						检修单位
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="unitId" name="unitId"></select>
                    </div>
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
// 			var orgnaizationId = ${orgnaizationId};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					//日期组件
					var startDateDatePicker = new A.my97datepicker({
						id: "startDateId",
						name:"startDate",
						render:"#startDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'endDateId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					//日期组件
					var endDateDatePicker = new A.my97datepicker({
						id: "endDateId",
						name:"endDate",
						render:"#endDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'startDateId\\')}",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					//combobx组件
					var dutyUserIdCombobox = new A.combobox({
						render: "#dutyUserId",
						//返回数据待后台返回TODO
						datasource:${searchuser},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					dutyUserIdCombobox.setValue(${userEntity.getId()});
					//combobx组件
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						//返回数据待后台返回TODO
						datasource:${unitList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					unitIdCombobox.setValue(${userEntity.getUnitId()});
					//combobx组件
// 					var equipLocalCombobox = new A.combobox({
// 						render: "#equipLocal",
// 						//返回数据待后台返回TODO
// 						datasource:${localList},
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
					$('#overhaulRecordForm').validate({
						debug:true,
						rules:  {
							id:{maxlength:20},
							name:{required:true,maxlength:64},
							startDate:{required:true,maxlength:64},
							endDate:{required:true,maxlength:64},
							dutyUserId:{required:true,maxlength:64},
// 							unitId:{required:true,maxlength:64},
							equipLocal:{maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulRecord");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#overhaulRecordForm").serializeObject();
							obj.startDate = $('#startDateId').val()+":00:00";
							if($('#endDateId').val()){
								obj.endDate = $('#endDateId').val()+":00:00";
							}
							console.log(obj.startDate);
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
									A.loadPage({
										render : '#page-container',
// 										url : format_url("/overhaulRecord/getDataList/"+orgnaizationId)
										url : format_url("/overhaulRecord/getDataList/")
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#overhaulRecordForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>