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
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="addTrainPlanForm">
				<input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
				<input class="col-md-12" id="status" name="status" type="hidden" value="${ entity.status }">
				<input class="col-md-12" id="" name="createPeopleId" type="hidden" value="${entity.createPeopleId }">
				<input class="col-md-12" id="dispatchTitleVal" name="dispatchTitle" type="hidden"  value="${entity.dispatchTitle }">
				<input class="col-md-12" id="dispatchIdVal"  type="hidden"  value="${entity.dispatchId }">
				<input class="col-md-12" id="dispatchNumberVal"  type="hidden"  value="${entity.dispatchNumber }">
				<input class="col-md-12" id="userList" type="hidden" >
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
							<input class="col-md-12" id="count" name="count" type="text" placeholder="" maxlength="64" value="${ entity.count }">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">培训时长(小时)：
						</label>
						<div class="padding-zero text-left col-md-9">
							<input class="col-md-12" id="duration" name="duration" type="text" placeholder="" maxlength="64" value="${ entity.duration }">
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>培训人：</label>
						<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="trainPersion" name="trainPersion" type="text" placeholder="" maxlength="64" value="${ entity.trainPersion }">
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文标题：</label>
						<div class="col-md-8 padding-zero text-left">
							<input class="col-md-12" id="dispatchTitle" name="dispatchTitle" type="text"  value="${dataMap.dispatchTitle }" readonly>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right" for="form-field-1">发文字号：</label>
				   		<div class="col-md-9 padding-zero text-left">
							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="${dataMap.dispatchNumber }" readonly>
						</div>
					</div>
				</div>
				<div class="form-group" style='padding-left:10%'>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
						<label class="col-md-3 control-label no-padding-right">
							<span style="color:red;">*</span>培训地点：
						</label>
						<div class="padding-zero  text-left col-md-8 ">
							<input class="col-md-12" id="trainLocation" name="trainLocation" readonly="readonly" type="text" placeholder="" maxlength="64" value="${entity.trainLocation }">
						</div>
					</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="col-md-3 control-label no-padding-right" for="form-field-1"><span style="color:red;">*</span>考核方式：</label>
							<div class="padding-zero  text-left col-md-8">
							<input class="col-md-12" id="assessmentMethod" name="assessmentMethod" readonly="readonly" type="text" placeholder="" maxlength="64" value="${entity.assessmentMethodName }">
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
						<textarea name="trainContent" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly" maxlength="128">${entity.trainContent }</textarea>
					</div>
               </div>
				<div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right padding-zero  " for="form-field-1">
							<span style="color:red;">*</span>课题名称：
						</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="titleName" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.titleName }</textarea>
					</div>
               </div>
	           <div class="form-group">
					<div class="col-lg-3 col-md-3 col-sm-4 col-xs-12 padding-zero"  style="width:17.5%">
						<label class="col-md-12 control-label no-padding-right">
							备注：
						</label>
					</div>
					<div class="text-left col-md-9 padding-zero" style="width:82.5%">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
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
					再提交
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					var appData = ${entityJson};
					var id = ${id};
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
					unitIdCombotree.setValue(appData.unitId);
					
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
					startDatePicker.setValue(appData.showTime);
					
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
					classifyCombobox.setValue(appData.classify);
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
					rangeCombobox.setValue(appData.range);
					
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#appendix",
						fileId:${entity.message},
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
							debugger;
							if(data && data[0]){
								$("#dispatchNumber").val (data[0].dispatchName);
								$("#dispatchTitleVal").val (data[0].title);
								self.setValue(data[0].title,data[0].id);
							};
						}
					}).render();
					selectDispatchTitle.setValue($("#dispatchTitleVal").val (), $("#dispatchIdVal").val ());
					
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
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/trainManagement/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addTrainPlanForm").serializeObject();
							obj.message=JSON.stringify(uploaddropzone.getValue());
							obj.status = "2";
							
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
									if(result.result=="success"){
										alert('修改成功');
										A.loadPage({
										render : '#page-container',
										url :  format_url("/todoTask/list/1/10")
									});
									}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						var urls = format_url("/trainManagement/submitPerson/" + $("#taskId").val());
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "审核人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
									var userList=submitUserDialog.resule.join(",");
									$("#userList").val(userList);
									$("#addTrainPlanForm").submit();
								}
							}
						}).render();
					});
					//由添加迁移页返回到列表页
					$("#backBtnAddTrainPlan").on("click",function(){
						//点击返回判断是否保存
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