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
				<li>生产技术培训管理</li>
				<li class="active">培训管理</li>
				<li class="active">添加</li>
			</ul>
			<div style="margin-right:200px;margin-top:10px;">
	   			<button id="backBtnAddTrainPlan" class="btn btn-xs btn-primary pull-right">
	   				<i class="glyphicon glyphicon-share-alt"></i>
	   				返回
	   			</button>
   			</div>
			<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px">基础信息</h5>
		</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addTrainPlanForm">
			   <input class="col-md-12" id="id" name="id" type="hidden" value="">
			   <input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${sysUserEntity.id }">
			   <input class="col-md-12" id="dispatchTitleVal" name="dispatchTitle" type="hidden">
			   <div class="form-group" style='padding-left:10%'>
				   <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
				   		<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>组织单位：
						</label>
						<div class="padding-zero  text-left col-md-8">
							<div id="searchunitId"></div>
						</div>
				   </div>
					
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训分类：</label>
							<div class="padding-zero  text-left col-md-8">
								<select id="trainClassify" name="classify" class="form-control chosen-select"></select>
							</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训范围：</label>
							<div class="padding-zero  text-left col-md-9">
								<select id="trainRange" name="range" class="form-control chosen-select"></select>
							</div>
				   	</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训时间：</label>
				   		<div class="col-md-8 padding-zero text-left">
							<div id="trainTimeDiv" name="trainTimeDiv" style="border:none; padding:0px;height:30px;"></div>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>培训人数：
						</label>
						<div class="padding-zero  text-left col-md-8 ">
							<input class="col-md-12" id="count" name="count" type="text" placeholder="" maxlength="64" value="">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">培训时长(小时)：
						</label>
						<div class="padding-zero text-left col-md-9">
							<input class="col-md-12" id="duration" name="duration" type="text" placeholder="" maxlength="64" value="">
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训人：</label>
				   		<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="trainPersion" name="trainPersion" type="text" placeholder="" maxlength="64" value="">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文标题：</label>
						<div class="col-md-8 padding-zero text-left">
							<div id="dispatchTitleDiv" name="dispatchId"></div>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文字号：</label>
				   		<div class="col-md-9 padding-zero text-left">
							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="" readonly>
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>培训地点：
						</label>
						<div class="padding-zero  text-left col-md-8 ">
							<input class="col-md-12" id="trainLocation" name="trainLocation" type="text" placeholder="" maxlength="64" value="">
						</div>
					</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>考核方式：</label>
							<div class="padding-zero  text-left col-md-8">
								<select id="assessmentMethod" name="assessmentMethod" class="form-control chosen-select"></select>
							</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
							<label class="col-md-12 control-label no-padding-right padding-zero  " for="form-field-1">
								<span style="color:red;">*</span>培训内容：
							</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="trainContent" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
				<div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
							<label class="col-md-12 control-label no-padding-right padding-zero  " for="form-field-1">
								<span style="color:red;">*</span>课题名称：
							</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="titleName" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
	           <div class="form-group" >
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
					<label class="col-md-12 control-label no-padding-right">
						备注：
					</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
               <div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right">
							培训信息：
						</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
							<div id="appendix" name="message"></div>
					</div>
               	</div>
			</form>
			   
			<div style="margin-right:200px;margin-top:25px;">
				<button id="saveBtn" class="btn btn-xs btn-success pull-right">
					<i class="ace-icon fa fa-floppy-o"></i>
					保存
				</button>
			</div>
		</div>
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					var unitIdCombotree = new A.combotree({
						render: "#searchunitId",
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
						}
					}).render();
					var startDatePicker = new A.my97datepicker({
						id: 'trainTimeDiv',
						name: 'time',
						render:'#trainTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					startDatePicker.setValue(getNowTime());
					//培训时间赋当前时间为初始值
					function getNowTime() {
						var date = new Date();
						var seperator1 = "-";
						var seperator2 = ":";
						var month = date.getMonth() + 1;
						var strDate = date.getDate();
						var h=date.getHours();
						var m=date.getMinutes();
						month = checkTime(month);
						strDate = checkTime(strDate);
						h = checkTime(h);
						m = checkTime(m);
						var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + h + seperator2 + m;
						return currentdate;
					}
					
					function checkTime(i)
					{
					if (i<10) 
					  {i="0" + i}
					  return i
					}
					
					//培训分类
					var classifyCombobox = new A.combobox({
						render: "#trainClassify",
						datasource:${trainPlanClassifyCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//考核方式
					var assessmentMethodCombobox = new A.combobox({
						render: "#assessmentMethod",
						datasource:${assessmentMethodCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//培训范围
					var rangeCombobox = new A.combobox({
						render: "#trainRange",
						datasource:${trainPlanRangeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();

					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#appendix",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();

					var selectDispatchTitle = new A.selectbox({
						id: 'selectDispatchTitle',
						name: 'dispatchId',
						title:'发文标题',
						url:'/trainManagement/goSelectDispatchPage',
						render:'#dispatchTitleDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							if(data && data[0]){
								$("#dispatchNumber").val (data[0].dispatchName);
								$("#dispatchTitleVal").val (data[0].title);
								self.setValue(data[0].title,data[0].id);
							};
						}
					}).render();

					$('#addTrainPlanForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true,maxlength:64},
							classify:{required:true,maxlength:20},
							range:{required:true,maxlength:64},
							time:{required:true,maxlength:20},
							count:{required:true,digits:true,maxlength:20},
							duration:{number:true,maxlength:20},
							titleName:{required:true,maxlength:128},
 							trainPersion:{required:true,maxlength:128},
 							trainLocation:{required:true,maxlength:128},
 							assessmentMethod:{required:true,maxlength:128},
 							trainContent:{required:true,maxlength:128}
						},
						submitHandler: function (form) {
							
							//添加按钮
							var url = format_url("/trainManagement/saveAddPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTrainPlanForm").serializeObject();
							obj.message=JSON.stringify(uploaddropzone.getValue());
							obj.status = "1";
							var treeObj = $.fn.zTree.getZTreeObj("unitId");
							var nodes = treeObj.getSelectedNodes();
							if (nodes.length > 0) {
								obj.unitName =  nodes[0].name;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
									render : '#page-container',
									url : format_url('/trainManagement/index/')
								});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addTrainPlanForm").submit();
					});
					//由添加迁移页返回到列表页
					$("#backBtnAddTrainPlan").on("click",function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/trainManagement/index/")
						});
					});
					
				});
			});
		</script>
	</body>
</html>