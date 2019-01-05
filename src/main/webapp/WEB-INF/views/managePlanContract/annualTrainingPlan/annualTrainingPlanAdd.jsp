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
				<li class="active">技术质量类计划</li>
				<li class="active">年度培训计划</li>
				<li class="active">新增</li>
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
		<div class="col-md-12" style="margin-top: 30px" >
			<form class="form-horizontal" role="form"  style="margin-right:13%;" id="addForm">
			<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
			<input type="hidden" id="selectUser" name="selectUser"/>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训单位
				    </label>
				    <div class="col-md-4">
<!-- 						<input class="col-md-12" id="unitId" name="unitId" type="text" placeholder="" maxlength="20" value=""> -->
						<div id="unitIdDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainName" name="trainName" type="text" placeholder="" maxlength="20" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训内容
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="trainContent" name="trainContent" type="text" placeholder="" maxlength="20" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训时间
					</label>
					<div class="col-md-4">
<!-- 	                   <input class="col-md-12" id="trainTime" name="trainTime" type="text" placeholder="" maxlength="20" value=""> -->
	                    <div id="trainTimeDiv"></div>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训人员
				    </label>
				    <div class="col-md-4">
<!-- 						<input class="col-md-12" id="trainMember" name="trainMember" type="text" placeholder="" maxlength="20" value=""> -->
						<select class="col-md-12 chosen-select" id="trainMember" name="trainMember"></select>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训地点
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="trainLocation" name="trainLocation" type="text" placeholder="" maxlength="20" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>培训类别
				    </label>
				    <div class="col-md-4">
<!-- 						<input class="col-md-12" id="trainType" name="trainType" type="text" placeholder="" maxlength="20" value=""> -->
						<select class="col-md-12 chosen-select" id="trainType" name="trainType"></select>
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>申请人
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="userId" name="userId"></select>
                    </div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
<!--     			<button id="submitBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:35px;"> -->
<!--     				<i class="glyphicon glyphicon-floppy-saved"></i> -->
<!--     				保存并提交 -->
<!--     			</button> -->
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//部门控件下拉树
					var unitId = new A.combotree({
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
						},
					}
				}).render();
					//日期组件
					var trainTimePicker = new A.my97datepicker({
						id: "trainTimeId",
						name:"trainTime",
						render:"#trainTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					//培训人员
					var trainMember = new A.combobox({
						render : "#trainMember",
						datasource : ${createPeopleCombobox},
						allowBlank: true,
						multiple:true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//培训类别
					var trainType = new A.combobox({
						render : "#trainType",
						datasource : ${trainingTypeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//申请人
					var userId = new A.combobox({
						render : "#userId",
						datasource : ${createPeopleCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					$('#addForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:20},
							trainName:{required:true,maxlength:20},
							trainContent:{required:true,maxlength:20},
							trainTime:{required:true,maxlength:20},
							trainMember:{required:true,maxlength:20},
							trainLocation:{required:true,maxlength:20},
							trainType:{required:true,maxlength:20},
							userId:{required:true,maxlength:20},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/annualTrainingPlan");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							var trainMembers = trainMember.getValue();
							if(trainMembers){
								obj.trainMember = trainMembers;
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
									A.loadPage({
										render : '#page-container',
										url : format_url("/annualTrainingPlan/list")
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#saveOrSubmit").val("");
						$("#addForm").submit();
    				});
					$("#submitBtn").on("click", function(){
						rewardDialog = new A.dialog({
							title:"生产技术处审批",
							url:format_url("/annualTrainingPlan/sureSubmit"),
							height:450,
							width:500
						}).render();
    				});
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/annualTrainingPlan/list")
						});
    				});
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				//给审批人  和提交标识 赋值
				$("#selectUser").val(selectUser);
				$("#saveOrSubmit").val("submit");
				$("#addForm").submit();
			}
        </script>
    </body>
</html>