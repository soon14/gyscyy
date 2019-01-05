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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addAssetForm">
				<input class="col-md-12" id="addType" name="type"  value="${type}" type="hidden">
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" readonly="readonly" name="code" type="text" placeholder="" maxlength="20" value="">
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
						<input class="col-md-12" id="certificate"  name="certificate" type="text" placeholder="" maxlength="20" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>有效期</label>

					<div class="col-md-4">
						<div id="addTimeDiv"></div>
					</div>

				</div>
				
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label> 
					<div class="col-md-4"> 
 						<input class="col-md-12" id="assetName" name="assetName" type="text" placeholder="" maxlength="64" value="">
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
 						<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
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
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
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
						var dd = new Date();
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
					
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options: {
							maxFiles:1,
						}
					}).render();
					
					//单位
					 searchunitid = new A.combobox({
						render : "#unitIdSelect",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//日期组件 签订时间
					var addTimeDatePicker = new A.my97datepicker({
						id: "addTime",
						name:"time",
						render:"#addTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					$('#addAssetForm').validate({
						debug:true,
						rules:  {
							name:{required:true, maxlength:64},
							defualtType:{required:true, maxlength:64},
							year:{ required:true},
							time:{ required:true },
							assetName:{ required:true },
							certificate:{ required:true },
							fileId:{maxlength:128}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/assetManagement/addEntityPro");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addAssetForm").serializeObject();
							var year = obj.year;
							var time = obj.time
							var timeYear = time.substring(0,4);
							//附件上传
							obj.appendix=JSON.stringify(uploaddropzone.getValue());
							obj.type=$("#type").val()
							
							var defualtTypeName = $("#addDefualtType option:checked").text();
							obj.defualtTypeName = defualtTypeName;
							
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
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addAssetForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>