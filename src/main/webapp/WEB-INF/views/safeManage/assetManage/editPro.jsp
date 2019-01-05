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
				<input class="col-md-12" id="editType" name="type"  value="${assetManagementEntity.type}" type="hidden">
				<input class="col-md-12" id="id" name="id"  value="${assetManagementEntity.id}" type="hidden">
				<input class="col-md-12" id="editYearNumValue" name="year"  value="${assetManagementEntity.year}" type="hidden">
				<input class="col-md-12" id="editTimeValue" name="time"  value="${assetManagementEntity.time}" type="hidden">
				<input class="col-md-12" id="editDefualtTypeValue" name="defualtType"  value="${assetManagementEntity.defualtType}" type="hidden">
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" readonly="readonly" name="code" type="text" placeholder="" maxlength="20" value="${assetManagementEntity.code }">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="createrName" readonly="readonly" name="createrName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="creater" name="creater" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>证书
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="certificate"  name="certificate" type="text" placeholder="" maxlength="20" value="${assetManagementEntity.certificate }">
                    </div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>有效期</label>
					<div class="col-md-4">
						<div id="editTimeDiv"></div>
					</div>
				</div>
				<div class="form-group">
				
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label> 
					<div class="col-md-4"> 
 						<input class="col-md-12" id="assetName" name="assetName" type="text" placeholder="" maxlength="64" value="${assetManagementEntity.assetName }">
					</div> 
					<label class="col-md-2 control-label no-padding-right">
						单位
				    </label>
				    <div class="col-md-4">
						<select id="unitIdSelect" name="unitId" class="col-md-12 chosen-select"></select>
                    </div>
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>姓名</label> 
					<div class="col-md-4"> 
 						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${assetManagementEntity.name }">
					</div> 
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
					<div class="col-md-10" id="divfile"></div>
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
					//附件上传

					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId : ${assetManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options: {
							maxFiles:1,
						}
					}).render();
					/**
					  * 获取当前年
					  */
					function getNowYear() {
						var today = new Date();
						var strYear = today.getFullYear();
						return strYear+"";
					}
					
					/*
					  *	 获取当前日期 yyyy-MM-dd
					  */
					function getFormatDate() {
						var today = new Date();
						var dd = new Date();
						dd.setDate(dd.getDate()-1);
						var strYear = dd.getFullYear();
						var strDay = dd.getDate();
						var strMonth = dd.getMonth() + 1;
						if (strMonth < 10) {
							strMonth = "0" + strMonth;
						}
						if (strDay < 10) {
							strDay = "0" + strDay;
						}
						var strday = strYear + "-" + strMonth + "-" + strDay;
						return strday;
					}
					
					//单位
					 searchunitid = new A.combobox({
						render : "#unitIdSelect",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue(${assetManagementEntity.unitId});
					//日期组件 签订时间
					var editTimeDatePicker = new A.my97datepicker({
						id: "editTime",
						name:"time",
						render:"#editTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					editTimeDatePicker.setValue($("#editTimeValue").val());
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							name:{required:true, maxlength:64},
							defualtType:{maxlength:64},
							time:{ required:true, maxlength:64 },
							assetName:{ required:true, maxlength:64 },
							certificate:{ required:true, maxlength:64 },
							fileId:{maxlength:128}
						},
						submitHandler: function (form) {
							
							//添加按钮
							var url = format_url("/assetManagement/editEntity/2");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.year = $("#editYearNum").val();
							obj.time =  $("#editTime").val();
							var year = obj.year;
							var time = obj.time;
							var timeYear = time.substring(0,4);
// 							if (year != timeYear) {
// 								alert("年号和选择时间的年不相同")
// 								return;
// 							}
							//附件上传
							obj.appendix=JSON.stringify(uploaddropzone.getValue());
							obj.defualtType = $("#editDefualtType").val();
							var defualtTypeName = $("#editDefualtType option:checked").text();
							obj.defualtTypeName = defualtTypeName;
							obj.time =  $("#editTime").val(); 
							obj.year =  $("#editYearNum").val(); 
							
							if(eval(uploaddropzone.getValue()).length!=1){
								alert("请添加一个附件");
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
						$("#editForm").submit();
					});
				});
			});
		</script>
	</body>
</html>