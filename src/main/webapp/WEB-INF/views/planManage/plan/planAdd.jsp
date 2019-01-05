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
					<li class="active">新增</li>
				</ul><!-- /.breadcrumb -->
				<div style="margin-right:55px;margin-top:10px;">
        		<button id="button" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        		</div>
        		<h5 class="table-title header smaller blue" style="margin-left:30px">基础信息</h5>
		</div>
	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
					<input id="id"  name="id"  value="${planEntity.id}" type="hidden"/>
					<input id="uuid"  name="uuid"  value="${planEntity.uuid}" type="hidden"/>
					<input id="status"  name="status"  value="${planEntity.status}" type="hidden"/>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
				    <div id="unitIdDiv" ></div>
					
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
					</label>
					<div class="col-md-4">
						<select class="col-md-12 chosen-select" id="userId" name="userId"></select>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划周期
				    </label>
				    <div class="col-md-4">
				   		 <select class="col-md-12 chosen-select" id="cycle" name="cycle"></select>
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划类型
					</label>
					<div class="col-md-4">
						 <select class="col-md-12 chosen-select" id="type" name="type"></select>
                	</div>
               </div>
		       <div class="form-group">
				    <label id="startTime" class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划时间
				    </label>
				    <div class="col-md-4">
				    	<div id="timeDiv"></div>
                    </div>
                    <div id="endTime" >
	                 </div>
               </div>
	              <div class="form-group">
	                 <label  class="col-md-2 control-label no-padding-right">
						总投资合计（万元）
				    </label>
				    <div class="col-md-4">
				    	<input id="planSum" name="planSum" value="" 
				    		class="col-md-12" type="text" readonly="readonly"/>
                    </div>
                    </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
	                   <textarea class="col-md-12" id="remark" name="remark" 
	                   style="height:100px; resize:none;"  placeholder="" maxlength="128" >${planEntity.remark}</textarea>
	                   
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
				   <div class="col-xs-10" id="divfile">
					</div>
               </div>
            </form>
              	<div class="form-group">
					<div id="planWhole">
					
					</div>
				</div>
    		<div style="margin-right:55px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
	</div>
		<script type="text/javascript">
		var flag="",planWholeDatatables;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var uuid = $('#uuid').val();
					//加载列表
					$.ajax({url : format_url("/planWhole/index"),
						contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
						data : {"uuid":uuid},
						success : function(result) {
							var divshow = $("#planWhole");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${planEntity.fileId}'==""?[]:'${planEntity.fileId}',
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//日期组件
					var timeDatePicker = new A.my97datepicker({
						id: "timeId",
						name:"time",
						render:"#timeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
// 								maxDate: "#F{$dp.$D(\\'timeEndId\\')}",
// 								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					timeDatePicker.setValue('${planEntity.time}');
					var userId = new A.combobox({
						render : "#userId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						initValue:"${planEntity.userId}"
					}).render();
					var planType = new A.combobox({
						render : "#type",
						datasource : ${planType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						initValue:"${planEntity.type}"
					}).render();
					var planCycle = new A.combobox({
						render : "#cycle",
						datasource : ${planCycle},
// 						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						initValue:"${planEntity.cycle}"
					}).render();
					//部门控件下拉树
					var unitId = new A.combotree({
					render: "#unitIdDiv",
					name: 'unitId',
					datasource: ${unitNameIdTreeList},
					width:"110px",
					options: {
						treeId: 'unitId',
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
					unitId.setValue(${planEntity.unitId});
					$('#addForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,      maxlength:20},
							userId:{required:true,      maxlength:20},
							cycle:{ required:true,     maxlength:64},
							type:{  required:true,    maxlength:20},
							time:{  required:true,    maxlength:20},
							timeEnd:{ required:true,     maxlength:20},
							remark:{     maxlength:128},
							fileId:{     maxlength:4000},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/plan/add");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										$("#id").val(result.data.id);
										alert('保存成功');
										if(flag==""){
											 $("#page-container").load(format_url('/plan/index'));
										}else if(flag=="add"){
											var tmp=planWholeDatatables.getDatasValue("num");
	            							var max = 0;
	            							for(var i=0;i<tmp.length;i++){ 
	            							  if(max<tmp[i])max=tmp[i];
	            							 }
	            							var  num=max+1;
	            							A.loadPage({
												render : '#page-container',
												url : format_url('/planWhole/getAdd'),
												data:{"planUuid":$("#uuid").val(),"planId":$("#id").val(),"num":num}
											});
										}else{
											A.loadPage({
												render : '#page-container',
												url : format_url('/planWhole/getEdit'),
												data:{"id":flag}
											});
										}
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					$("#button").on("click", function(e){
						window.scrollTo(0,0);
						 $("#page-container").load(format_url('/plan/index'));
    				});
					$("#cycle").on("change", function(){
						if($("#cycle").val()==0){
							$("#endTime").text("");
							$("#startTime").text("");
							$("#startTime").append("<span style='color:red;'>*</span>计划时间");
						}else{
							$("#endTime").append(
							  '<label class="col-md-2 control-label no-padding-right">'+
							  '<span style="color:red;">*</span>计划结束时间 </label>'+
							  '<div class="col-md-4"> <div id="timeEndDiv"></div> </div>'
							);
							var timeEndDatePicker = new A.my97datepicker({
								id: "timeEndId",
								name:"timeEnd",
								render:"#timeEndDiv",
								options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate: "",
										minDate: "#F{$dp.$D(\\'timeId\\')}",
										dateFmt: "yyyy"
								}
							}).render();
							$("#startTime").text("");
							$("#startTime").append("<span style='color:red;'>*</span>计划开始时间");
						}
    				});
				});
			});
        </script>
    </body>
</html>