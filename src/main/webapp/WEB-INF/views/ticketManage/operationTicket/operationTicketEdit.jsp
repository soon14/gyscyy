<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="ace-icon fa fa-home home-icon"></i> <a href="javascript:void(0);" onclick="firstPage()">首页</a>
			</li>
			<li class="active">两票管理</li>
			<li class="active">操作票管理</li>
			<li class="active">操作票</li>
			<li class="active">修改</li>
		</ul>
	</div>
	<div class="col-md-12">
		<div class="page-content">
			<div class="tabbable" style="margin-top: 20px;">
				<ul class="nav nav-tabs" id="myTab">
					<li class="active" ><a data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i> 票据信息
					</a></li>

<!-- 					<li><a data-toggle="tab" href="#operationDanger" -->
<!-- 						aria-expanded="false"> <i -->
<!-- 							class="green ace-icon fa fa-list bigger-120"></i> 危险因素控制卡 -->
<!-- 					</a></li> -->

				</ul>
				<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackTicket" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i> 返回
					</button>
				</div>
				<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 650px">

					<!-- 第一个div开始 -->
					<div id="workitem" class="tab-pane fade active in" >
						<form class="form-horizontal" role="form"
							style="margin-right:100px;" id="editForm">
							<input id="id" name="id" value="${t.id}" type="hidden"/>
							<input id="status"  name="status" value="${t.status}" type="hidden"/>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									编号
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="code" name="code" type="text"
										placeholder="" maxlength="64" value="${t.code}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>单位名称
								</label>
								<div class="col-md-4">
<!-- 											 <div id="unitIdDiv"></div> -->
										<input class="col-md-12" id="unitId" type="hidden" readonly="readonly" name="unitId" value="${t.unitId}"></input>
										<input class="col-md-12" id="unitName" type="text" readonly="readonly"  value="${t.unitName}"></input>
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备编码</label>
										<div class="col-md-4">
									<div id="equipmentCodeDiv">
									</div>
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" 
											placeholder="" maxlength="64" value="${t.equipmentName}" readonly="readonly">
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>操作开始时间
								</label>
								<div class="col-md-4">
									<div id="startDateDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>操作终了时间
								</label>
								<div class="col-md-4">
									<div id="endDateDiv"></div>
								</div>
							</div> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									工作票票号
								</label>
								<div class="col-md-4">
									<div id="workticketCodeDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">
									缺陷单编号 </label>
								<div class="col-md-4">
								<div id="defectCodeDiv"></div>
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>班组</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<select class="col-md-12 chosen-select" id="groupId" name="groupId"></select> -->
<!-- 									</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>发令单位
								</label>
								<div class="col-md-4">
										 <div id="startUnitIdDiv" ></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span> 发令人 
								</label>
								<div class="col-md-4">
										 <select id="startUserId" class="col-md-12" name="startUserId"></select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>受令人
								</label>
								<div class="col-md-4">
										 <select id="endUserId" class="col-md-12" name="endUserId"></select>
								</div>
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>受令时间 
								</label>
								<div class="col-md-4">
										 <div id="endTimeDiv"  style="border:none; padding:0px;"></div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									<span style="color:red;">*</span>操作任务
								</label>
								<div class="col-md-10">
									<textarea name="workText" placeholder=""
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.workText}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									备注 </label>
								<div class="col-md-10">
									<textarea name="remark" placeholder=""
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.remark}</textarea>
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									附件 </label>
								<div class="col-md-10" id="divfile" >
								</div>
							</div> -->
						</form>
						<div id="operationItem" ></div>
<!-- 						<form class="form-horizontal" role="form"  -->
<!-- 						style="margin-right:100px;margin-top: 15px"> -->
<!-- 							<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"> -->
<!-- 										终止原因</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea name="reason" placeholder="" id="reason" -->
<!-- 											style="height:100px; resize:none;" class="col-md-12" -->
<!-- 											maxlength="128">${t.reason}</textarea> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 						</form> -->
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">参加操作、监护人、负责人</h5>
		                    <div class="form-horizontal" style="margin-right:100px;margin-top: 20px;" >
									<div class="form-group ">
										<label class="col-md-2 control-label no-padding-right">
											操作人
										</label>
										<div class="col-md-2">
											 <input  value="${t.operateName}" class="col-md-12" type="text" readonly="readonly"/>
										</div>
										<label class="col-md-2 control-label no-padding-right">
											监护人
										</label>
										<div class="col-md-2">
											 <input  value="${t.guardianName}" class="col-md-12" type="text" readonly="readonly"/>
										</div>
										<label class="col-md-2 control-label no-padding-right">
											负责人
										</label>
										<div class="col-md-2">
											 <input  id="operateId" value="${t.picName}" class="col-md-12" type="text" disabled="disabled"/>
										</div>
									</div>
							</div>
					</div>
					<div id="operationDanger" class="tab-pane fade"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<div style="margin-right:100px;margin-top: 20px;">
			<button id="editBtn" class="btn btn-xs btn-success pull-right"
				style="margin-right:15px;">
				<i class="ace-icon fa fa-floppy-o"></i> 保存
			</button>
		</div>
	</div>
	<script type="text/javascript">
		var userUnitRels = ${userUnitRels};
		//初始数据备份
		var processUserUnitRels = ${userUnitRels};
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','uploaddropzone','selectbox' ],function(A) {
				//部门控件下拉树
			/* 	var unitId = new A.combotree({
				render: "#unitIdDiv",
				name: 'unitId',
				//返回数据待后台返回TODO
				datasource: ${unitNameIdTreeList},
				width:"210px",
				options: {
					treeId: 'searchunitId',
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
				unitId.setValue(${t.unitId}); */
				//部门控件下拉树
				var searchunitId = new A.combotree({
				render: "#startUnitIdDiv",
				name: 'startUnitId',
				//返回数据待后台返回TODO
				datasource: ${unitNameIdTreeList},
				width:"210px",
				options: {
					treeId: 'startUnitId',
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
				searchunitId.setValue(${t.startUnitId});
				var startUserId = new A.combobox({
					render : "#startUserId",
					datasource : ${searchuser},
					allowBlank: true,
					options : {
						"disable_search_threshold" : 10
					}
				}).render();
				startUserId.setValue(${t.startUserId});
				var endUserId = new A.combobox({
					render : "#endUserId",
					datasource : ${searchuser},
					allowBlank: true,
					options : {
						"disable_search_threshold" : 10
					}
				}).render();
				endUserId.setValue(${t.endUserId});
				//开始时间
				var endTime = new A.my97datepicker({
					id: 'endTime',
					name:'endTime',
					render:'#endTimeDiv',
					options : {
							isShowWeek : false,
							skin : 'ext',
							maxDate: "",
							minDate: "",
							dateFmt: "yyyy-MM-dd HH:mm:ss"
					}
				}).render();
				endTime.setValue('${t.endTime}');
				var workticketselectbox=new A.selectbox({
					id: 'workticketCodeId',
					name:'workticketCode',
					title:'工作票',
					url:'/workTicket/tickeList',
					render:'#workticketCodeDiv',
					width:'1100',//弹出窗体的宽度  可以不写这行
					hight:'820',//弹出窗体的高度  可以不写这行
					callback: function(data){
						workticketselectbox.setValue(data.code,data.code);
					}
				}).render();
				workticketselectbox.setValue('${t.workticketCode}','${t.workticketCode}');
				//combobx组件
				var groupIdCombobox = new A.combobox({
					render: "#groupId",
					//返回数据待后台返回TODO
					datasource:${groupIdCombobox},
					//multiple为true时select可以多选
					multiple:false,
					//allowBlank为false表示不允许为空
					allowBlank: true,
					options:{
						"disable_search_threshold":10
					}
				}).render();
				groupIdCombobox.setValue('${t.groupId}','${t.groupId}');
				var defectselectbox =new A.selectbox({
					id: 'defectCodeId',
					name:'defectCode',
					title:'缺陷单',
					url:'/defect/defectList',
					render:'#defectCodeDiv',
					width:'1100',//弹出窗体的宽度  可以不写这行
					hight:'720',//弹出窗体的高度  可以不写这行
					callback: function(data){
						defectselectbox.setValue(data.code,data.code);
					}
				}).render();
				defectselectbox.setValue('${t.defectCode}','${t.defectCode}');
				
				
				var equipmentselectbox=new A.selectbox({
					id: 'equipmentCode',
					name:'equipmentCode',
					title:'设备台账',
					url:'/equipLedger/selectEquipLedger',
					render:'#equipmentCodeDiv',
					width:'1100',//弹出窗体的宽度  可以不写这行
					hight:'780',//弹出窗体的高度  可以不写这行
					callback: function(data){
// 						if(data&&data[0]){
// 							$("#equipmentName").val(data[0].name);
// 							equipmentselectbox.setValue(data[0].code,data[0].code);
// 						};
						var ids=[];
						var nameList='';
						var codeList='';
						 for(var i=0; i< data.length;i++){
							 if(ids.length>0){
								 if(!contains(ids,data[i].id)){
									ids.push(data[i].id);
								     codeList += data[i].code+',';
								     nameList += data[i].name+',';
									 
								 }
							}else{
							  ids.push(data[i].id);
							  codeList += data[i].code+',';
							  nameList += data[i].name+',';
								
							}
						 }
						  equipmentselectbox.setValue(codeList,codeList);
							$("#equipmentName").val(nameList);
							$("#equipmentCode").attr("title",codeList);
							$("#equipmentName").attr("title",nameList);
					}
				}).render();
				equipmentselectbox.setValue('${t.equipmentCode}','${t.equipmentCode}');
					//附件上传
					/* var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${t.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render(); */
						//日期组件
						/* var startDateDatePicker = new A.my97datepicker({
							id : "startDateId",
							name : "startDate",
							render : "#startDateDiv",
							options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate : "",
								minDate : "",
								dateFmt : "yyyy-MM-dd HH:mm"
							}
						}).render();
						startDateDatePicker.setValue('<fmt:formatDate value="${t.startDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>'); */
						//日期组件
						/* var endDateDatePicker = new A.my97datepicker({
							id : "endDateId",
							name : "endDate",
							render : "#endDateDiv",
							options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate : "",
								minDate : "",
								dateFmt : "yyyy-MM-dd HH:mm"
							}
						}).render();
						endDateDatePicker.setValue('<fmt:formatDate value="${t.endDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>');
 */						$('#editForm').validate({
							debug : true,
							rules : {
									code : {required : true,maxlength : 64},
									unitId : {required : true,maxlength : 64},
									equipNumber : {required : true,maxlength : 46},
									equipmentCode : {required : true,maxlength : 64},
									startDate : {required : true,maxlength : 64},
									endDate : {required : true,maxlength : 64},
									workticketCode : {maxlength : 64},
									workText : {required : true,maxlength : 128},
									endTime : {required : true,maxlength : 64},
									startUnitId: {required : true,maxlength : 64},
									startUserId:{required : true,maxlength : 64},
									endUserId:{required : true,maxlength : 64},
							},
							submitHandler : function(form) {
								/* if(startDateDatePicker.getValue()>endDateDatePicker.getValue()){
									alert("操作开始时间不能大于操作终了时间！");
									return;
								} */
								//修改按钮
								var url = format_url("/operationTicket/update/"+${t.id});
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#editForm").serializeObject();
								//obj.fileId=JSON.stringify(uploaddropzone.getValue());
								obj.reason=$("#reason").val();
								obj.typicalTicketId='${typicalTicketId}';
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache : false,
									type : 'POST',
									success : function(result) {
										if(result.result=="success"){
											alert('修改成功');	
										if('${typicalTicketId}'){
												A.loadPage({
													render : '#page-container',
													url : format_url("/typicalTicket/index")
												});
											}else{
												A.loadPage({
													render : '#page-container',
													url : format_url("/operationTicket/index")
												});
											}
										}else{
											alert(result.errorMsg);
										}
									},
									error : function(v, n) {
										alert('修改失败');
									}
								});
							}
						});
						$("#editBtn").on("click", function() {
							$("#editForm").submit();
						});
						//加载项目列表
						$.ajax({url : format_url("/operationItem/index"),
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							data : {"id":0},
							success : function(result) {
								var divshow = $("#operationItem");
								divshow.text("");// 清空数据
								divshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							if($(e.target).attr('href') == "#operationDanger"){
								//加载控制卡列表
								$.ajax({url : format_url("/operationDanger/index"),
									contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
									data : {"id":0},
									success : function(result) {
										var divshow = $("#operationDanger");
										divshow.text("");// 清空数据
										divshow.append(result); // 添加Html内容，不能用Text 或 Val
									}
								});
							}
						});
						$('#btnBackTicket').on('click', function() {
							if('${typicalTicketId}'){
								A.loadPage({
									render : '#page-container',
									url : format_url("/typicalTicket/index")
								});
							}else{
								A.loadPage({
									render : '#page-container',
									url : format_url("/operationTicket/index")
								});
							}
						});
					});
		});
		
		//判断值是否在数组里的方法zzq
		function contains(arr, obj) {
			  var i = arr.length;
			  while (i--) {
			    if (arr[i] === obj) {
			      return true;
			    }
			  }
			  return false;
		}
	</script>
</body>
</html>