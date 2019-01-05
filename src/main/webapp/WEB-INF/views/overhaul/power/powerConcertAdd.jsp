<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>
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
					<li class="active">
					检修管理</li>
					<li class="active">
					停送电管理</li>
					<li class="active">新增</li>
				</ul>
		</div>
		
<div class="col-md-12" >
<div class="page-content">
	<div style="float:right; margin-right:50px;">
								<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div>
								<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
							
								<div class="widget-main no-padding">
		<div class="tabbable" style="margin-top: 30px;">
			<div class="col-md-12" >
				<form class="form-horizontal" role="form"   id="addForm">
				   <input class="col-md-12" id="requestUserName" name="requestUserName" type="hidden" value="">
				   <input class="col-md-12" id="status" name="status" type="hidden" value="">
				   <input class="col-md-12" id="unitName" name="unitName" type="hidden" value="">
				   <input class="col-md-12" id="userList" name="userList" type="hidden" value="">
			       <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>设备编号
						</label>
						<div class="col-md-4">
							<div id="equipNumberDiv">
							</div>
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="" type="text" placeholder="" readOnly maxlength="64"  value="">
							<input class="col-md-12" id="equipName2" name="equipName" type="hidden" placeholder=""  maxlength="64"  value="">
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>单位名称
						</label>
						<div class="col-md-4">
		                   <div class="" id="unitIdDiv" >
		                   </div>
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请时间
					    </label>
					    <div class="col-md-4">
							<div id="requestDateDiv"></div>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>申请人
						</label>
						<div class="col-md-4">
<!-- 							<select class="col-md-12 chosen-select" id="requestUserId" name="requestUserId"></select> -->
							<input class="col-md-12" id="requestUserId" name="requestUserId" type="hidden"  maxlength="64"  value="${userEntity.id}">
							<input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" readOnly maxlength="64"  value="${userEntity.name}">
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>停送电方式
					    </label>
					    <div class="col-md-4" id="powerTypes">
					    	<select class="col-md-12 chosen-select" id="powerStatus" name="powerStatus"></select>
	                    </div>
	               </div>
		           <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>停送电内容
						</label>
						<div class="col-md-10">
							<textarea name="powerDec" id="powerDec"placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
						</div>
	               </div>
	    			
		           <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
						</div>
	               </div>
			       <div class="form-group form-horizontal">
						<label class="col-md-1 control-label no-padding-right">
							附件
						</label>
						<div class="col-xs-10" id="divfile">
						</div>
	               </div>
	               
	            </form>
	        </div>
    		<div style="margin-right:50px;">
<!--     			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:50px;" data-dismiss="modal"> -->
<!--     				<i class="ace-icon glyphicon glyphicon-remove"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
    			<button id="submitBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-ok"></i>
    				提交审批
    			</button>
    		</div>
		</div>
</div>
</div>
</div>
		<script type="text/javascript">
			var listFormDialog;
			var unitNameCombobox;
			var requestUserId;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//combobx组件
					 unitNameCombobox = new A.combotree({
						render: "#unitIdDiv",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
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
						},callback:function(data){
// 							console.log(data);
						}
						
					}).render();
					unitNameCombobox.setValue(${userEntity.unitId});
					$("#unitName").val('${unitEntity.name}');
// 					 $(".aptech-combotree-dropdown").on('click', function(){
// 						 $(".aptech-combotree .select-close").on('click', function(){
// 								getUserList();
// 							});
// 							getUserList();
// 					 });
// 					 $(".aptech-combotree-dropdown").css("width","98%");
					//停送电类型
					var powerTypeslist = new A.combobox({
						render : "#powerStatus",
						datasource : ${powerTypes},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							
						}
					}).render();
					
					//申请人
// 					 requestUserId = new A.combobox({
// 						render : "#requestUserId",
// 						datasource : ${requestUsers},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						},
// 						callback: function(data){
// 							if(data&&data[0]){
// 								$("#requestUserName").val(data[0].Name);
// 							};
							
// 						}
// 					}).render();
// 					requestUserId.setValue(${userEntity.id});
// 					$('#requestUserName').val('${userEntity.name}');

					//日期组件
					var requestDateDatePicker = new A.my97datepicker({
						id: "requestDate",
						render:"#requestDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					requestDateDatePicker.setValue('${date}');
					$('#requestDate').val('${date}');
					
					//设备列表初始化
					var equipNumbeSelectBox = new A.selectbox({
						id: 'equipNumberDiv',
						name:'equipNumber',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipNumberDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							if(data&&data[0]){
								$("#equipNumber").val(data[0].code);
								$("#equipName").val(data[0].name);
								$("#equipName2").val(data[0].name);
								self.setValue(data[0].code);
							};
							
						}
					}).render();
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true},
							requestDate:{required:true},
							requestUserId:{ required:true},
							equipName:{required:true},
							powerDec:{ required:true,maxlength:1024},
							powerStatus:{required:true}},
						    submitHandler: function (form) {
						    	
							var url = format_url("/power/save");
							var obj = $("#addForm").serializeObject();
							obj.requestDate = $('#requestDate').val()+":00";
							obj.attchmentIds=JSON.stringify(uploaddropzone.getValue());
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result == "success"){
										 alert('添加成功');
										 $("#page-container").load(format_url('/power/index'));
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
					
					$("#saveBtn").on("click", function(){
						$('#status').val("<%=PowerStatusEnum.PENDING.getCode() %>");
						$("#addForm").submit();
    				});
					
					$("#submitBtn").on("click", function(){
						$('#status').val("<%=PowerStatusEnum.OVERHAUL.getCode() %>");
						var url = format_url("/power/beforeSubmit");
						var obj = $("#addForm").serializeObject();
						obj.requestDate = $('#requestDate').val()+":00";
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result == "success"){
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "申请审批人",
										url:format_url('/power/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											if(listFormDialog.resule){
												  var userList=listFormDialog.resule.join(",");
												  $('#userList').val(userList);
												  $("#addForm").submit();
											}
										}
									}).render();
								}else{
									alert(result.errorMsg);
								}
							
							},
							error:function(v,n){
								alert('验证失败');
							}
						});
						
    				});
					
					$("#btnBack").on("click", function(){
						 $("#page-container").load(format_url('/power/index'));
    				});
    				
				});
			});
			function getUserList(){
				console.log(0);
				var selectunitId= 0;
				if(unitNameCombobox.getValue()!=null && unitNameCombobox.getValue()!="" && unitNameCombobox.getValue()!=0){
					selectunitId = unitNameCombobox.getValue();
					$.ajax({
						url: format_url("/overhaulLog/getUserList/"+selectunitId),
						contentType: "application/json",
						dataType: 'JSON',
						type: 'POST',
						data :"",
						success: function(result){
							var userList = eval(result.data);
							requestUserId = new A.combobox({
								render : "#requestUserId",
								datasource :userList,
								allowBlank: true,
								options : {
									"disable_search_threshold" : 10
								},
								callback: function(data){
								
								}
							}).render();
						}
					});
				}else{
					requestUserId = new A.combobox({
						render : "#requestUserId",
						datasource :${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
						
						}
					}).render();
				}
			}
        </script>
    </body>
</html>