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
				<li class="active">工作票管理</li>
			<li class="active">电力线路第二种工作票</li>
			<li class="active">修改</li>
		</ul>
	</div>

	<div class="col-md-12">

		<div class="page-content">
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
				<div class="tab-content-own">
					<div id="workitemEdit" class="tab-pane fade active in">

						<form class="form-horizontal" role="form"
							style="margin-right:100px;" id="workTicketFormEdit">
							<input type="hidden" id="id" name="id" value="${workTicketLineTwoEntity.id}" />
							 <input type="hidden" id="typicalTicketId" name="typicalTicketId" value="${typicalTicketId}" />
							 <input type="hidden" id="electricId" name="electricId" value="${workLineTwoEntity.id}" />
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">编码</label>
								<div class="col-md-4">
									<input class="col-md-12" id="code" name="code" type="text"
										readonly="readonly" placeholder="" maxlength="64"
										value="${workTicketLineTwoEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>单位名称</label>
								<div class="col-md-4">
									<input id="unitNameIdEdit" type="hidden"
										value="${workTicketLineTwoEntity.unitNameId}" class="col-md-12">
									<input class="col-md-12" id="unitNameId" type="hidden"
										readonly="readonly" name="unitNameId"
										value="${userEntity.unitId}"></input> <input class="col-md-12"
										id="unitName" type="text" readonly="readonly"
										value="${userEntity.unitName}"></input>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>班组</label>
								<div class="col-md-4">
									<input id="groupIdEdit" type="hidden"
										value="${workTicketLineTwoEntity.groupId}" class="col-md-12">
									<select class="col-md-12 chosen-select" id="groupId"
										name="groupId"></select>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作负责人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="guarderName" type="text"
										readonly="readonly" name="guarderName"
										value="${workTicketLineTwoEntity.guarderName}"></input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作班人员</label>
								<div class="col-md-10">
									<textarea id="workClassMember" name="workClassMember"
										placeholder="" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128"
										onblur="gotoFindClassMember();">${workTicketLineTwoEntity.workClassMember}</textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workClassPeople"
										name="workClassPeople" type="text" readonly="readonly"
										maxlength="20" value="${workTicketLineTwoEntity.workClassPeople}">
								</div>
							</div>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作内容</label>
								<div class="col-md-10">
									<textarea id="content" name="content" placeholder=""
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workTicketLineTwoEntity.content}</textarea>
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>停电线路名称</label>
									<div class="col-md-10">
										<textarea id="endAllowIdea" name="endAllowIdea" placeholder="" style="height:50px; resize:none;" class="col-md-12" maxlength="128">${workTicketLineTwoEntity.endAllowIdea}</textarea>
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span
									style="color:red;">*</span>工作地段</label>
								<div class="col-md-10">
									<textarea id="address" name="address" placeholder=""
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workTicketLineTwoEntity.address}</textarea>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备编号</label>
								<div class="col-md-4">
									<div id="equipmentCodeWorkOneEditDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备名称</label>
								<div class="col-md-4">
									<input class="col-md-12" id="equipmentName"
										name="equipmentName" type="text" readonly="readonly"
										maxlength="64" value="${workTicketLineTwoEntity.equipmentName}">
								</div>
							</div>
							<div class="form-group">
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
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<div id="flawCodeDiv"></div> -->
<!-- 								</div> -->

<!-- 							</div> -->
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">是否需办理继电保护安全措施票：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="safeFlag" name="safeFlag" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="safeFlag" name="safeFlag" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">共（张）</label>
										<div class="col-md-4">
												<input class="col-md-12" id="safeNum" name="safeNum" type="text"  maxlength="64" value="${workTicketLineTwoEntity.safeNum}" readonly="readonly" >
										</div>
							</div>
								
						<div class="widget-main no-padding">
						<!-- zzq修改第二次需求 -->
							<h5 class="table-title header smaller lighter blue">继电保护安全措施操作项目</h5>
				 			<table id="workAqcs-table" class="table table-striped table-bordered table-hover" style="width:100%;">
										<thead>
											<tr>
												<th style="display:none;">主键</th>
				                                <th>序号</th>
				                                <th>执行</th>
<!-- 				                                <th>执行时间</th> -->
				                                <th>操作项目</th>
				                                <th>恢复</th>
<!-- 				                                <th>恢复时间</th> -->
			                                    <th>操作</th>
			                                </tr>
			                            </thead>
			                </table>
							<!-- zzq修改第二次需求 -->
							<h5 class="table-title header smaller lighter blue">应采取的安全措施（停用线路重合闸装置、退出再启动功能等）(大约125个汉字)</h5>
							<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
										<th>操作</th>
									</tr>
								</thead>
							</table>
							<h5 class="table-title header smaller lighter blue">其他安全措施和注意事项(大约125个汉字)</h5>
							<table id="workSafe-table-Two"class="table table-striped table-bordered table-hover"style="width:100%;margin-top:54px;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
										<th>操作</th>
									</tr>
								</thead>
							</table>
							<h5 class="table-title header smaller lighter blue">线路安全措施(大约125个汉字)</h5>
							<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
<!-- 						<h5 class="table-withoutbtn header smaller" -->
<!-- 							style="margin-bottom:0px;">备注：</h5> -->
<!-- 							<div id="workitemBz" class="tab-pane fade active in" -->
<!-- 								style="margin-top: 20px;"> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">指定专责监护人</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<select class="col-md-12 chosen-select" id="remarkGuarderName" -->
<!-- 											name="remarkGuarderName" style="width: 200px;"></select> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea id="remarkResponsibleName" name="remarkResponsibleName" -->
<!-- 											style="height:80px; resize:none;" class="col-md-12" -->
<!-- 											maxlength="128">${workLineTwoEntity.remarkResponsibleName}</textarea> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea id="remarkOther" style="height:80px; resize:none;" name="remarkOther" -->
<!-- 											class="col-md-12" maxlength="128">${workLineTwoEntity.remarkOther}</textarea> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
						</form>
					</div>
					<div style="margin-right:100px;margin-top: 20px;">
						<button id="editBtn" class="btn btn-xs btn-success pull-right"
							style="margin-right:15px;">
							<i class="ace-icon fa fa-floppy-o"></i> 保存
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var controlCardRiskDatatables = "";
		var userUnitRels = ${userUnitRels};
		//初始数据备份
		var processUserUnitRels = ${userUnitRels};
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','selectbox' ],function(A) {
				$("input[name='safeFlag']").on('click',function(){
					var value=this.value;
					$("input[name='safeFlag']").each(function () {
						if(value!=this.value){
				        $(this).attr('checked',false);
						}
					});
				});
				$("input[name='safeFlag']").each(function () {
					if(this.value=='${workTicketLineTwoEntity.safeFlag}'){
			        $(this).attr('checked',true);
					}
				});
				/* $("input[name='safeNum']").on('click',function(){
					var value=this.value;
					$("input[name='safeNum']").each(function () {
						if(value!=this.value){
				        $(this).attr('checked',false);
						}
					});
				});
				$("input[name='safeNum']").each(function () {
					if(this.value=='${workTicketLineTwoEntity.safeNum}'){
			        $(this).attr('checked',true);
					}
				}); */
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
								qxdcodebox.setValue('${workTicketLineTwoEntity.flawCode}');

								var equipList=[];
						
								var selecttreeDh = new A.selectbox({
									id : 'equipmentWorkOneEditCode',
									name : 'equipmentCode',
									title : '设备台账',
									url : '/equipLedger/selectEquipLedger',
									render : '#equipmentCodeWorkOneEditDiv',
									width : '1100',//弹出窗体的宽度  可以不写这行
									hight : '780',//弹出窗体的高度  可以不写这行
									callback : function(data) {
										
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
										    selecttreeDh.setValue(codeList,codeList);
											$("#equipmentName").val(nameList);
											
											$("#equipmentWorkOneEditCode").attr("title",codeList);
											$("#equipmentName").attr("title",nameList);

									}
								}).render();
								selecttreeDh.setValue('${workTicketLineTwoEntity.equipmentCode}');
								//combobx组件
								var groupIdComboboxEdit = new A.combobox({
									render : "#groupId",
									//返回数据待后台返回TODO
									datasource : ${groupIdCombobox},
									multiple : false,
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								groupIdComboboxEdit.setValue($("#groupIdEdit").val());

								//日期组件
								var plandateStartDatePickerEdit = new A.my97datepicker(
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
								plandateStartDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workTicketLineTwoEntity.plandateStart}" type="both"/>');
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
								plandateEndDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workTicketLineTwoEntity.plandateEnd}" type="both"/>');

								//指定专责监护人 组件
								var userListBoxCombobox = new A.combobox({
									render : "#remarkGuarderName",
									//返回数据待后台返回TODO
									datasource : ${userListBox},
									//multiple为true时select可以多选
									multiple : false,
									//allowBlank为false表示不允许为空
									allowBlank : true,
									options : {
										"disable_search_threshold" : 10
									}
								}).render();
								userListBoxCombobox.setValue(${workLineTwoEntity.remarkGuarderName});

								$('#workTicketFormEdit').validate({
													debug : true,
													rules : {
														unitNameId : {
															required : true,
															maxlength : 64
														},
														groupId : {
															required : true,
															maxlength : 20
														},
														guarderName : {
															required : true,
															maxlength : 64
														},
														workClassMember : {
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
														},
														equipmentCode : {
															required : true
// 															maxlength : 64
														},
														equipmentName : {
															required : true
// 															maxlength : 64
														},
														plandateStart : {
															required : true,
															maxlength : 20
														},
														plandateEnd : {
															required : true,
															maxlength : 20
														}
														,endAllowIdea:{required:true,maxlength : 128 },
														safeNum:{digits:true },
													},
													submitHandler : function(
															form) {
														if (plandateStartDatePickerEdit
																.getValue() > plandateEndDatePickerEdit
																.getValue()) {
															alert("计划开始时间不能大于计划终了时间！");
															return;
														}
														var id = ${workTicketLineTwoEntity.id};
														
														/////////////////////////////////////////////////
														$.ajax({
															url: format_url('/workTicketLineTwo/editValidate/'+id),
															contentType : 'application/json',
															dataType : 'JSON',
															type: 'POST',
															success: function(result){
																if(result.result == 'success'){
																	//修改按钮
																	var url = format_url("/workTicketLineTwo/update/"
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
																									url : format_url("/workTicketLineTwo/index")
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
																	url : format_url("/workTicketLineTwo/index")
																});
													}
												});

								var workid = '${workTicketLineTwoEntity.id}';
								//zzq修改第二次需求   继电保护安全措施票开始
								var conditionsAqcs=[];
								var  oneTotal;
								var workSafeAqcsDatatables = new A.datatables({
									render: '#workAqcs-table',
									options: {
								        "ajax": {
								            "url": format_url("/workSafe/search"),
								            "contentType": "application/json",
								            "type": "POST",
								            "dataType": "JSON",
								            "data": function (d) {
								            	d.length=2147483647;
								            	conditionsAqcs=[];
								            	conditionsAqcs.push({
					            					field: 'C_SAFE_TYPE',
					            					fieldType:'INT',
					            					matchType:'EQ',
					            					value:15//为各种票引用的安全措施的固定值
					            				});
								            	conditionsAqcs.push({
													field : 'C_WORKTICKET_ID',
													fieldType : 'LONG',
													matchType : 'EQ',
													value : '${workTicketLineEntity.id}'
												});
								            	d.conditions = conditionsAqcs;
								                return JSON.stringify(d);
								              }
								        },
								        multiple : true,
										ordering: true,
										checked: false,
										paging:false,
										bInfo:false,
										columns: [{data:"id", visible:false,orderable:false}, 
// 										          {data: "orderSeq",width: "10%",orderable: true},
										          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
										          {data: "executeSituation",width: "15%",orderable: true},
// 										          {data: "executeDate",width: "10%",orderable: true},
										          {data: "signerContent",width: "50%",orderable: true},
										          {data: "hfSituation",width: "15%",orderable: true}
// 										          {data: "hfDate",width: "10%",orderable: true}
										          ],
										toolbars: [{
			        						label:"新增",
			        						icon:"glyphicon glyphicon-plus",
			        						className:"btn-success",
			        						events:{
			            						click:function(event){
			            							var safeFlag=$("input[name='safeFlag']:checked").val();
			            							if(safeFlag!="0"){
			            								alert("您没有选择办理继电保护安全措施票!");
			            								return;
			            							}
			            							var info =workSafeAqcsDatatables._datatables.page.info();
			            							  oneTotal=info.recordsTotal+1;
			            								workSafeOneDialog = new A.dialog({
			                        						width: 950,
			                        						height: 350,
			                        						title: "操作项目新增",
			                        						url:format_url("/workSafe/getHelpSafeAqcsAdd?flag="+15+"&workId="+ workid+"&total="+oneTotal),
			                        						closed: function(){
			                        							workSafeAqcsDatatables.draw(false);
			                        							gotoFindSafeNum(oneTotal,20);
			                        						}	
			                        					}).render();
			            							}
			        						}
			        					}],
										btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													workSafeOneDialog = new A.dialog({
														width: 950,
														height: 350,
														title: "操作项目编辑",
														url:format_url("/workSafe/getHelpSafeAqcsEdit/"+ id),
														closed: function(){
															workSafeAqcsDatatables.draw(false);
														}
													}).render();
												}
											}
										}, {
											id:"delete",
											label:"删除",
											icon: "fa fa-trash-o bigger-130",
											className: "red del",
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													var info =workSafeAqcsDatatables._datatables.page.info();
				     							       oneTotal=info.recordsTotal-1;
													var url =format_url('/workSafe/'+ id);
													A.confirm('您确认删除么？',function(){
														$.ajax({
															url : url,
			        										contentType : 'application/json',
			        										dataType : 'JSON',
			        										type : 'DELETE',
			        										success: function(result){
			        											alert('删除成功');
			        											workSafeAqcsDatatables.draw(false);
			        											gotoFindSafeNum(oneTotal,20);
			        										},
			        										error:function(v,n){
			        											alert('操作失败');
			        										}
														});
													});
												}
											}
									}]
									}
								}).render();
								//zzq修改第二次需求   继电保护安全措施票开始
								//下面是安全措施的列表 1
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
																	value : '${workTicketLineTwoEntity.id}'
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
												columns : [ {
													data : "id",
													visible : false,
													orderable : false
												}, /* {
													data : "orderSeq",
													width : "10%",
													orderable : true
												}, */ 
												{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
												{
													data : "signerContent",
													width : "80%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
												toolbars : [ /* {
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
																		title : "安全措施新增",
																		url : format_url("/workSafe/getAdd?flag="
																				+ 1+ "&workId="+ workid	+ "&total="+ oneTotal),
																		closed : function() {
																			workSafeOneDatatables.draw(false);
																		}
																	}).render();
														}
													}
												}  */],
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
																				title : "安全措施编辑",
																				url : format_url("/workSafe/getEdit/"
																						+ id+ "/"+ 1),
																				closed : function() {
																					workSafeOneDatatables.draw(false);
																				}
																			}).render();
																}
															}
														}
														/* ,{
															id : "delete",
															label : "删除",
															icon : "fa fa-trash-o bigger-130",
															className : "red del",
															events : {
																click : function(event,nRow,nData) {
																	var id = nData.id;
																	var url = format_url('/workSafe/'+ id);
																        	A.confirm(
																					'您确认删除么？',
																					function() {
																						$.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(result) {
																										alert('删除成功');
																										workSafeOneDatatables.draw(false);
																									},
																									error : function(v,n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														} */ ]
											}
										}).render();

								//下面是安全措施的列表 2

								var conditionsTwo = [];
								var workSafeTwoDatatables = new A.datatables(
										{
											render : '#workSafe-table-Two',
											options : {
												"ajax" : {
													"url" : format_url("/workSafe/search"),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														conditionsTwo
																.push({
																	field : 'C_SAFE_TYPE',
																	fieldType : 'INT',
																	matchType : 'EQ',
																	value : 2
																});
														conditionsTwo
																.push({
																	field : 'C_WORKTICKET_ID',
																	fieldType : 'LONG',
																	matchType : 'EQ',
																	value : '${workTicketLineTwoEntity.id}'
																});
														d.conditions = conditionsTwo;
														d.length=2147483647;
														return JSON.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												checked : false,
												paging:false,
												bInfo:false,
												columns : [ {
													data : "id",
													visible : false,
													orderable : false
												}, /* {
													data : "orderSeq",
													width : "10%",
													orderable : true
												}, */ 
												{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
												{
													data : "signerContent",
													width : "80%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
												toolbars : [ /* {
													label : "新增",
													icon : "glyphicon glyphicon-plus",
													className : "btn-success",
													events : {
														click : function(event) {
															var info =workSafeTwoDatatables._datatables.page.info();
					            							var  twoTotal=info.recordsTotal+1;
															workSafeOneDialog = new A.dialog(
																	{
																		width : 800,
																		height : 300,
																		title : "安全措施新增",
																		url : format_url("/workSafe/getAdd?flag="
																				+ 2+ "&workId="+ workid+ "&total="+ twoTotal),
																		closed : function() {
																			workSafeTwoDatatables.draw(false);
																		}
																	}).render();
														}
													}
												} */ ],
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
																				title : "安全措施编辑",
																				url : format_url("/workSafe/getEdit/"
																						+ id+ "/"+ 2),
																				closed : function() {
																					workSafeTwoDatatables.draw(false);
																				}
																			}).render();
																}
															}
														}
														/* ,{
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
																						$.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(result) {
																										alert('删除成功');
																										workSafeTwoDatatables.draw(false);
																									},
																									error : function(v,n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														} */ ]
											}
										}).render();

								//下面是安全措施的列表 3

								var conditionsThree = [];
								var workSafeThreeDatatables = new A.datatables(
										{
											render : '#workSafe-table-Three',
											options : {
												"ajax" : {
													"url" : format_url("/workSafe/search"),
													"contentType" : "application/json",
													"type" : "POST",
													"dataType" : "JSON",
													"data" : function(d) {
														conditionsThree
																.push({
																	field : 'C_SAFE_TYPE',
																	fieldType : 'INT',
																	matchType : 'EQ',
																	value : 3
																});
														conditionsThree
																.push({
																	field : 'C_WORKTICKET_ID',
																	fieldType : 'LONG',
																	matchType : 'EQ',
																	value : '${workTicketLineTwoEntity.id}'
																});
														d.conditions = conditionsThree;
														d.length=2147483647;
														return JSON.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												checked : false,
												paging:false,
												bInfo:false,
												columns : [ {
													data : "id",
													visible : false,
													orderable : false
												}, /* {
													data : "orderSeq",
													width : "10%",
													orderable : true
												}, */ 
												{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
												{
													data : "signerContent",
													width : "80%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
												toolbars : [ /* {
													label : "新增",
													icon : "glyphicon glyphicon-plus",
													className : "btn-success",
													events : {
														click : function(event) {
															var info =workSafeThreeDatatables._datatables.page.info();
				            								var  threeTotal=info.recordsTotal+1;
															workSafeOneDialog = new A.dialog(
																	{
																		width : 800,
																		height : 300,
																		title : "安全措施新增",
																		url : format_url("/workSafe/getAdd?flag="
																				+ 3
																				+ "&workId="+ workid+ "&total="+ threeTotal),
																		closed : function() {
																			workSafeThreeDatatables.draw(false);
																		}
																	}).render();
														}
													}
												}  */],
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
																				title : "安全措施编辑",
																				url : format_url("/workSafe/getEdit/"
																						+ id+ "/"+ 3),
																				closed : function() {
																					workSafeThreeDatatables.draw(false);
																				}
																			}).render();
																}
															}
														}
														/* ,{
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
																						$.ajax({
																									url : url,
																									contentType : 'application/json',
																									dataType : 'JSON',
																									type : 'DELETE',
																									success : function(
																											result) {
																										alert('删除成功');
																										workSafeThreeDatatables.draw(false);
																									},
																									error : function(v,n) {
																										alert('操作失败');
																									}
																								});
																					});
																}
															}
														}  */]
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
																						url : format_url("/workTicketLineTwo/index")
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
				$("#page-container").load(format_url('/workTicketLineTwo/index'));
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
		//打印页数
		function gotoFindSafeNum(oneTotal,limit){
		
		var count=((oneTotal%limit==0)?parseInt(oneTotal/limit):parseInt(oneTotal/limit+1));

			$("#safeNum").val(count);
			
		}
	</script>
</body>
</html>