<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
<style type="text/css">
.tab-content-own {
	border: 1px solid #C5D0DC;
	padding: 16px 12px 50px;
	position: relative;
}
</style>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="ace-icon fa fa-home home-icon"></i> <a href="javascript:void(0);" onclick="firstPage()">首页</a>
			</li>
			<li class="active">两票管理</li>
			<li class="active">继电保护安全措施票</li>
			<li class="active">修改</li>
		</ul>
	</div>

	<div class="col-md-12">

		<div class="page-content" >
			<div class="tabbable" style="margin-top: 20px;">
				<ul class="nav nav-tabs">
					<li class="active"><a id="workLiEdit" data-toggle="tab"
						href="#workitemEdit" aria-expanded="true"> <i
							class="green ace-icon fa fa-home bigger-120"></i> 票据信息
					</a></li>
				</ul>
				<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i> 返回
					</button>
				</div>
				<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 650px">
					<div id="workitemEdit" class="tab-pane fade active in">

						<form class="form-horizontal" role="form"
							style="margin-right:100px;" id="workTicketFormEdit">
							<input type="hidden" id="id" name="id" value="${workHelpSafeEntity.id}" />
							 <input type="hidden" id="typicalTicketId" name="typicalTicketId" value="${typicalTicketId}" />
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">编码</label>
								<div class="col-md-4">
									<input class="col-md-12" id="code" name="code" type="text"
										readonly="readonly" placeholder="" maxlength="64"
										value="${workHelpSafeEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>单位名称</label>
								<div class="col-md-4">
									<input id="unitNameIdEdit" type="hidden"
										value="${workHelpSafeEntity.unitNameId}" class="col-md-12">
									<input class="col-md-12" id="unitNameId" type="hidden"
										readonly="readonly" name="unitNameId"
										value="${userEntity.unitId}"></input> <input class="col-md-12"
										id="unitName" type="text" readonly="readonly"
										value="${userEntity.unitName}"></input>
								</div>
							</div>
							<div class="form-group">
								<%-- <label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>班组</label>
								<div class="col-md-4">
									<input id="groupIdEdit" type="hidden"
										value="${workHelpSafeEntity.groupId}" class="col-md-12">
									<select class="col-md-12 chosen-select" id="groupId"
										name="groupId"></select>
								</div> --%>
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作负责人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="guarderName" type="text"
										readonly="readonly" name="guarderName"
										value="${workHelpSafeEntity.guarderName}"></input>
								</div>
							</div>

							<%-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作班人员</label>
								<div class="col-md-10">
									<textarea id="workClassMember" name="workClassMember"
										placeholder="" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128"
										onblur="gotoFindClassMember();">${workHelpSafeEntity.workClassMember}</textarea>
								</div>
							</div> --%>

							<%-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workClassPeople"
										name="workClassPeople" type="text" readonly="readonly"
										maxlength="20" value="${workHelpSafeEntity.workClassPeople}">
								</div>
							</div>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作内容</label>
								<div class="col-md-10">
									<textarea id="content" name="content" placeholder=""
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workHelpSafeEntity.content}</textarea>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作地点</label>
								<div class="col-md-10">
									<textarea id="address" name="address" placeholder=""
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workHelpSafeEntity.address}</textarea>
								</div>
							</div> --%>

							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">设备编号</label>
								<div class="col-md-4">
									<div id="equipmentCodeWorkOneEditDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
								<div class="col-md-4">
									<input class="col-md-12" id="equipmentName"
										name="equipmentName" type="text" readonly="readonly"
										maxlength="64" value="${workHelpSafeEntity.equipmentName}">
								</div>
							</div>
							<!-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>计划开始时间</label>
								<div class="col-md-4">
									<div id="plandateStartDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>计划终了时间</label>
								<div class="col-md-4">
									<div id="plandateEndDiv"></div>
								</div>
							</div> -->
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea id="remarkOther" name="remarkOther" placeholder="请填写备注" style="height:80px; resize:none;" class="col-md-12" maxlength="256">${workHelpSafeEntity.remarkOther}</textarea>
									</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<div id="flawCodeDiv"></div> -->
<!-- 								</div> -->

<!-- 							</div> -->
								
						<div class="widget-main no-padding">
							<h5 class="table-title header smaller lighter blue">操作项目</h5>
							<table id="workSafe-table"
								class="table table-striped table-bordered table-hover"
								style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>序号</th>
		                                <th>执行</th>
<!-- 		                                <th>执行时间</th> -->
		                                <th>操作项目</th>
		                                <th>恢复</th>
<!-- 		                                <th>恢复时间</th> -->
	                                    <th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
						</form>
					</div>
					
				</div>
			</div>
			
		</div>
		<div style="margin-right:100px;margin-top: 20px;">
						<button id="editBtn" class="btn btn-xs btn-success pull-right"
							style="margin-right:15px;">
							<i class="ace-icon fa fa-floppy-o"></i> 保存
						</button>
		</div>
	</div>
	<script type="text/javascript">
		var controlCardRiskDatatables = "";
		var userUnitRels = ${userUnitRels};
		//初始数据备份
		var processUserUnitRels = ${userUnitRels};
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','selectbox' ],function(A) {
								
								var qxdcodebox = new A.selectbox({
									id : 'flawCodeIdEdit',
									name : 'flawCode',
									title : '缺陷单',
									url : '/defect/defectList',
									render : '#flawCodeDiv',
									width : '1100',//弹出窗体的宽度  可以不写这行
									hight : '720',//弹出窗体的高度  可以不写这行
									callback : function(data) {
										qxdcodebox.setValue(data.code,data.code);
									}
								}).render();
								qxdcodebox.setValue('${workHelpSafeEntity.flawCode}');

								var equipList=[];
								var ids=[];
								var nameList='';
								var codeList='';
								var selecttreeDh = new A.selectbox({
									id : 'equipmentWorkOneEditCode',
									name : 'equipmentCode',
									title : '设备台账',
									url : '/equipLedger/selectEquipLedger',
									render : '#equipmentCodeWorkOneEditDiv',
									width : '1100',//弹出窗体的宽度  可以不写这行
									hight : '780',//弹出窗体的高度  可以不写这行
									callback : function(data) {
// 										if (data && data[0]) {
// 											selecttreeDh.setValue(data[0].code,data[0].code);
// 											$("#equipmentName").val(data[0].name);
// 										};
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
										    selecttreeDh.setValue(codeList,codeList);
											$("#equipmentName").val(nameList);

									}
								}).render();
								selecttreeDh.setValue('${workHelpSafeEntity.equipmentCode}');
								//combobx组件
								/* var groupIdComboboxEdit = new A.combobox({
									render : "#groupId",
									//返回数据待后台返回TODO
									datasource : ${groupIdCombobox},
									multiple : false,
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								groupIdComboboxEdit.setValue($("#groupIdEdit").val()); */

								//日期组件
								/* var plandateStartDatePickerEdit = new A.my97datepicker(
										{
											id : "plandateStartId",
											name : "plandateStart",
											render : "#plandateStartDiv",
											options : {
												isShowWeek : false,
												skin : 'ext',
												maxDate : "",
												minDate : "",
												dateFmt : "yyyy-MM-dd HH:mm"
											}
										}).render();
								plandateStartDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workHelpSafeEntity.plandateStart}" type="both"/>');
								//日期组件
								var plandateEndDatePickerEdit = new A.my97datepicker(
										{
											id : "plandateEndId",
											name : "plandateEnd",
											render : "#plandateEndDiv",
											options : {
												isShowWeek : false,
												skin : 'ext',
												maxDate : "",
												minDate : "",
												dateFmt : "yyyy-MM-dd HH:mm"
											}
										}).render();
								plandateEndDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workHelpSafeEntity.plandateEnd}" type="both"/>'); */

							

								$('#workTicketFormEdit').validate({
													debug : true,
													rules : {
														unitNameId : {
															required : true,
															maxlength : 64
														},
														/* groupId : {
															required : true,
															maxlength : 20
														}, */
														guarderName : {
															required : true,
															maxlength : 64
														},
														/* workClassMember : {
															required : true,
															maxlength : 128
														},
														workClassPeople : {
															required : true,
															maxlength : 20
														},
														content : {
															required : true,
															maxlength : 128
														},
														address : {
															required : true,
															maxlength : 128
														}, */
// 														equipmentCode : {
// 															required : true,
// 															maxlength : 64
// 														},
// 														equipmentName : {
// 															required : true,
// 															maxlength : 64
// 														},
														/* plandateStart : {
															required : true,
															maxlength : 20
														},
														plandateEnd : {
															required : true,
															maxlength : 20
														}
														,safeNum:{required:true,maxlength : 128 }, */
													},
													submitHandler : function(form) {
														/* if (plandateStartDatePickerEdit
																.getValue() > plandateEndDatePickerEdit
																.getValue()) {
															alert("计划开始时间不能大于计划终了时间！");
															return;
														} */
														var id = ${workHelpSafeEntity.id};
														$.ajax({
															url: format_url('/workHelpSafe/editValidate/'+id),
															contentType : 'application/json',
															dataType : 'JSON',
															type: 'POST',
															success: function(result){
																if(result.result == 'success'){
																	//修改按钮
																	var url = format_url("/workHelpSafe/update/"
																			+ id);
																	var obj = $("#workTicketFormEdit").serializeObject();
																	$.ajax({
																				url : url,
																				contentType : 'application/json',
																				dataType : 'JSON',
																				data : JSON.stringify(obj),
																				cache : false,
																				type : 'POST',
																				success : function(result) {
																					alert('操作成功');
																					window.scrollTo(0,0);
																					//这里是典型票点过来的保存，还是第一种票直接的保存，的判断跳转列表
																					var typicalTicketId = '${typicalTicketId}';
																					if (typicalTicketId != null
																							&& typicalTicketId != '') {
																						A.loadPage({
																									render : '#page-container',
																									url : format_url("/typicalTicket/index")
																								});
																					} else {
																						A.loadPage({
																									render : '#page-container',
																									url : format_url("/workHelpSafe/index")
																								});
																					}
																				},
																				error : function(v,n) {
																					alert('操作失败');
																				}
																			});
																	
																} else{
							   										alert(result.errorMsg);
							   									}
															}
														});
														
													}
												});
								$("#editBtn").on("click",function() {
													$("#workTicketFormEdit").submit();
												});

								$('#btnBack').on('click',function() {
													var typicalTicketId = '${typicalTicketId}';
													if (typicalTicketId != null&& typicalTicketId != '') {
														A.loadPage({
																	render : '#page-container',
																	url : format_url("/typicalTicket/index")
																});
													} else {
														A.loadPage({
																	render : '#page-container',
																	url : format_url("/workHelpSafe/index")
																});
													}
												});

								//下面是安全措施的列表 1
								var workid = '${workHelpSafeEntity.id}';
								var conditionsOne = [];
								var workSafeOneDatatables = new A.datatables({
											render : '#workSafe-table',
											options : {
												"ajax" : {
													"url" : format_url("/workSafe/search"),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														conditionsOne
																.push({
																	field : 'C_SAFE_TYPE',
																	fieldType : 'INT',
																	matchType : 'EQ',
																	value : 1
																});
														conditionsOne
																.push({
																	field : 'C_WORKTICKET_ID',
																	fieldType : 'LONG',
																	matchType : 'EQ',
																	value : '${workHelpSafeEntity.id}'
																});
														d.conditions = conditionsOne;
														d.length=2147483647;
														return JSON.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												checked : false,
												paging:false,
												bInfo:false,
												columns: [{data:"id", visible:false,orderable:false}, 
												          {data: "orderSeq",width: "10%",orderable: true},
												          {data: "executeSituation",width: "15%",orderable: true},
// 												          {data: "executeDate",width: "10%",orderable: true},
												          {data: "signerContent",width: "50%",orderable: true},
												          {data: "hfSituation",width: "15%",orderable: true}
// 												          {data: "hfDate",width: "10%",orderable: true}
												          ],
												toolbars : [ {
													label : "新增",
													icon : "glyphicon glyphicon-plus",
													className : "btn-success",
													events : {
														click : function(event) {
															var info =workSafeOneDatatables._datatables.page.info();
					            							var  oneTotal=info.recordsTotal+1;
															workSafeOneDialog = new A.dialog(
																	{
																		width : 800,
																		height : 300,
																		title : "操作项目新增",
																		url : format_url("/workSafe/getHelpSafeAdd?flag="
																				+ 1
																				+ "&workId="
																				+ workid
																				+ "&total="
																				+ oneTotal),
																		closed : function() {
																			workSafeOneDatatables.draw(false);
																		}
																	}).render();
														}
													}
												} ],
												btns : [
														{
															id : "edit",
															label : "修改",
															icon : "fa fa-pencil-square-o bigger-130",
															className : "green edit",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	workSafeOneDialog = new A.dialog(
																			{
																				width : 800,
																				height : 300,
																				title : "操作项目编辑",
																				url : format_url("/workSafe/getHelpSafeEdit/"
																						+ id
																						+ "/"
																						+ 1),
																				closed : function() {
																					workSafeOneDatatables
																							.draw(false);
																				}
																			})
																			.render();
																}
															}
														},
														{
															id : "delete",
															label : "删除",
															icon : "fa fa-trash-o bigger-130",
															className : "red del",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	var url = format_url('/workSafe/'
																			+ id);
																	A.confirm(
																					'您确认删除么？',
																					function() {
																						$
																								.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(
																											result) {
																										alert('删除成功');
																										workSafeOneDatatables
																												.draw(false);
																									},
																									error : function(
																											v,
																											n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														} ]
											}
										}).render();

								

								//第二个tab
								$('#workTicketFormCardEdit')
										.validate(
												{
													debug : true,
													rules : {},
													submitHandler : function(
															form) {
														var workClassMemberAdd = $(
																"#workClassMember")
																.val();
														if (workClassMemberAdd == "") {
															alert("工作班成员不能为空！");
															return;
														}
														var contentAdd = $(
																"#content")
																.val();
														if (contentAdd == "") {
															alert("工作内容不能为空！");
															return;
														}
														var addressAdd = $(
																"#address")
																.val();
														if (addressAdd == "") {
															alert("工作地点不能为空！");
															return;
														}
														//添加按钮
														var cardId = '${workControlCardEntity.id}';
														var url = format_url("/workControlCard/"
																+ cardId);
														//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
														var obj = $(
																"#workTicketFormCardEdit")
																.serializeObject();
														$
																.ajax({
																	url : url,
																	contentType : 'application/json',
																	dataType : 'JSON',
																	data : JSON
																			.stringify(obj),
																	cache : false,
																	type : 'POST',
																	success : function(
																			result) {
																		alert('修改成功');
																		var typicalTicketId = '${typicalTicketId}';
																		if (typicalTicketId != null
																				&& typicalTicketId != '') {
																			A
																					.loadPage({
																						render : '#page-container',
																						url : format_url("/typicalTicket/index")
																					});
																		} else {
																			A
																					.loadPage({
																						render : '#page-container',
																						url : format_url("/workTicketLine/index")
																					});
																		}
																	},
																	error : function(
																			v,
																			n) {
																		alert('添加失败');
																	}
																});
													}
												});
								$("#saveBtnCardTwo")
										.on(
												"click",
												function() {
													var checksum = $('input[type=checkbox]:checked').length;
													if (checksum == "") {
														alert("安全风险控制卡，未填写完整");
														return;
													}
													var fourTotal = $(
															"#fourTotal").val();
													if (fourTotal == "0") {
														alert("安全风险控制卡，作业风险分析与主要预控措施,未填写");
														return;
													}

													var chk_value = [];
													$(
															"input[name='cardSort1']:checked")
															.each(
																	function() {
																		chk_value
																				.push($(
																						this)
																						.val());
																	});
													$("#cardSort").val(
															chk_value);

													var chk_valueTwo = [];
													$(
															"input[name='cardSortTwo1']:checked")
															.each(
																	function() {
																		chk_valueTwo
																				.push($(
																						this)
																						.val());
																	});
													$("#cardSortTwo").val(
															chk_valueTwo);

													var chk_valueThree = [];
													$(
															"input[name='cardSortThree1']:checked")
															.each(
																	function() {
																		chk_valueThree
																				.push($(
																						this)
																						.val());
																	});
													$("#cardSortThree").val(
															chk_valueThree);

													var chk_valueFour = [];
													$(
															"input[name='cardSortFour1']:checked")
															.each(
																	function() {
																		chk_valueFour
																				.push($(
																						this)
																						.val());
																	});
													$("#cardSortFour").val(
															chk_valueFour);

													$("#workTicketFormCardEdit")
															.submit();
												});

								var conditionsCard = [];
								controlCardRiskDatatables = new A.datatables(
										{
											render : '#controlCardRisk-table',
											options : {
												"ajax" : {
													"url" : format_url("/controlCardRisk/search"),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														conditionsCard
																.push({
																	field : 'C_CONTROL_ID',
																	fieldType : 'LONG',
																	matchType : 'EQ',
																	value : '${workControlCardEntity.id}'
																});
														d.conditions = conditionsCard;
														return JSON
																.stringify(d);
													}
												},
												checked : false,
												multiple : true,
												ordering : true,
												optWidth : 120,
												aLengthMenu : [ 5, 10, 20 ],
												columns : [ {
													data : "id",
													visible : false,
													orderable : false
												}, {
													data : "dangerPoint",
													width : "auto",
													orderable : true
												}, {
													data : "mainControl",
													width : "auto",
													orderable : true
												} ],
												fnInfoCallback : function(
														oSettings, iStart,
														iEnd, iMax, iTotal,
														sPre) {
													$(
															"#controlCardRisk-table_info")
															.html(sPre);//下脚标
													$("#fourTotal").val(iTotal);
												},
												toolbars : [ {
													label : "新增",
													icon : "glyphicon glyphicon-plus",
													className : "btn-success",
													events : {
														click : function(event) {
															var cardId = '${workControlCardEntity.id}';
															workSafeTwoDialog = new A.dialog(
																	{
																		width : 800,
																		height : 350,
																		title : "工作票控制卡风险与措施增加",
																		url : format_url('/controlCardRisk/getAdd?cardId='
																				+ cardId),
																		closed : function() {
																			controlCardRiskDatatables
																					.draw(false);
																		}
																	}).render();
														}
													}
												} ],
												btns : [
														{
															id : "edit",
															label : "修改",
															icon : "fa fa-pencil-square-o bigger-130",
															className : "green edit",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	workSafeTwoDialog = new A.dialog(
																			{
																				width : 800,
																				height : 350,
																				title : "工作票控制卡风险与措施编辑",
																				url : format_url('/controlCardRisk/getEdit/'
																						+ id),
																				closed : function() {
																					controlCardRiskDatatables
																							.draw(false);
																				}
																			})
																			.render();
																}
															}
														},
														{
															id : "delete",
															label : "删除",
															icon : "fa fa-trash-o bigger-130",
															className : "red del",
															events : {
																click : function(
																		event,
																		nRow,
																		nData) {
																	var id = nData.id;
																	var url = format_url('/controlCardRisk/'
																			+ id);
																	A
																			.confirm(
																					'您确认删除么？',
																					function() {
																						$
																								.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(
																											result) {
																										alert('删除成功');
																										controlCardRiskDatatables
																												.draw(false);
																									},
																									error : function(
																											v,
																											n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														} ]
											}
										}).render();

								//控制回显多选框开始
								var onecheck = '${workControlCardEntity.cardSort}';
								var oneCheck = onecheck.split(",");
								$("input[name='cardSort1']")
										.each(
												function() {
													if (contains(oneCheck, $(
															this).val())) {
														$(
																"#cardSort"
																		+ $(
																				this)
																				.val())
																.attr(
																		"checked",
																		'true');
													}
												});
								var onecheckTwo = '${workControlCardEntity.cardSortTwo}';
								var oneCheckTwo = onecheckTwo.split(",");
								$("input[name='cardSortTwo1']")
										.each(
												function() {
													if (contains(oneCheckTwo,
															$(this).val())) {
														$(
																"#cardSortTwo"
																		+ $(
																				this)
																				.val())
																.attr(
																		"checked",
																		'true');
													}
												});

								var onecheckThree = '${workControlCardEntity.cardSortThree}';
								var oneCheckThree = onecheckThree.split(",");
								$("input[name='cardSortThree1']")
										.each(
												function() {
													if (contains(oneCheckThree,
															$(this).val())) {
														$(
																"#cardSortThree"
																		+ $(
																				this)
																				.val())
																.attr(
																		"checked",
																		'true');
													}
												});

								var onecheckFour = '${workControlCardEntity.cardSortFour}';
								var oneCheckFour = onecheckFour.split(",");
								$("input[name='cardSortFour1']")
										.each(
												function() {
													if (contains(oneCheckFour,
															$(this).val())) {
														$(
																"#cardSortFour"
																		+ $(
																				this)
																				.val())
																.attr(
																		"checked",
																		'true');
													}
												});
								//控制回显多选框结束
							});
		});

		function gotoQx() {
			var typicalTicketId = '${typicalTicketId}';
			if (typicalTicketId != null && typicalTicketId != '') {
				A.loadPage({
					render : '#page-container',
					url : format_url("/typicalTicket/index")
				});
			} else {
				$("#page-container").load(format_url('/workTicketLine/index'));
			}

		}
		function gotoFindClassMember() {
			var classMember = $("#workClassMember").val();
			var count = 0;
			var date = classMember.replace(/，/g, ",").split(",");
			for (var i = 0; i < date.length; i++) {
				if (date[i] != "") {
					count = count + 1;
				}
			}
			count = count + 1;
			$("#workClassPeople").val(count);
		}
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