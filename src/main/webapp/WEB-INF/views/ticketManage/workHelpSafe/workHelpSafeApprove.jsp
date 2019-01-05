<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>

	<div class="page-content">
		<div class="tabbable">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a data-toggle="tab" href="#workitemEdit"
					aria-expanded="true"> <i
						class="green ace-icon fa fa-home bigger-120"></i> 票据信息
				</a></li>
			</ul>
			<div class="tab-content">
				<div id="workitemEdit" class="tab-pane fade active in"
					style="overflow-x:hidden;overflow-y: auto;height: 600px">
					<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormEdit">
						<input type="hidden" id="id" name="id" value="${workHelpSafeEntity.id}" /> 
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">编码</label>
							<div class="col-md-4">
								<input class="col-md-12" id="code" type="text"
									readonly="readonly" maxlength="64"
									value="${workHelpSafeEntity.code}">
							</div>
							<label class="col-md-2 control-label no-padding-right">单位名称</label>
							<div class="col-md-4">
								<input id="unitNameIdEdit" type="text" readonly="readonly"
									value="${workHelpSafeEntity.unitName}" class="col-md-12">
							</div>
						</div>

						<div class="form-group">
							<%-- <label class="col-md-2 control-label no-padding-right">班组</label>
							<div class="col-md-4">
								<input id="groupIdEdit" type="text" readonly="readonly"
									value="${workHelpSafeEntity.groupName}" class="col-md-12">
							</div> --%>
							<label class="col-md-2 control-label no-padding-right">工作负责人</label>
							<div class="col-md-4">
								<input class="col-md-12" id="guarderName" type="text"
									readonly="readonly" value="${workHelpSafeEntity.guarderName}"></input>
							</div>
						</div>

						<%-- <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班人员（包括工作负责人）</label>
							<div class="col-md-10">
								<textarea id="workClassMember" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workHelpSafeEntity.workClassMember}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班人数</label>
							<div class="col-md-4">
								<input class="col-md-12" id="workClassPeople" type="text"
									readonly="readonly" maxlength="20"
									value="${workHelpSafeEntity.workClassPeople}">
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作内容</label>
							<div class="col-md-10">
								<textarea id="content" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workHelpSafeEntity.content}</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作地点</label>
							<div class="col-md-10">
								<textarea id="address" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workHelpSafeEntity.address}</textarea>
							</div>
						</div> --%>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">设备编号</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentCode" type="text"
									readonly="readonly" maxlength="64"
									value="${workHelpSafeEntity.equipmentCode}">
							</div>
							<label class="col-md-2 control-label no-padding-right">设备名称</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value="${workHelpSafeEntity.equipmentName}">
							</div>
						</div>
						<%-- <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">计划开始时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="plandateStart" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workHelpSafeEntity.plandateStart}" type="date"/>'>
							</div>
							<label class="col-md-2 control-label no-padding-right">计划终了时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workHelpSafeEntity.plandateEnd}" type="date"/>'>
							</div>
						</div> --%>
						<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea id="remarkOther"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workHelpSafeEntity.remarkOther}</textarea>
									</div>
						</div>
						<!-- 								<div class="form-group"> -->
						<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
						<!-- 									<div class="col-md-4"> -->
						<!-- 											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${workHelpSafeEntity.flawCode}"> -->
						<!-- 								</div> -->

						<!-- 							</div> -->
						
						<!-- 列表开始 -->
						<div class="widget-main no-padding">
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">操作项目</h5>
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
						<!-- 列表结束 -->
						<!-- 审批字段开始 -->
						<div id="workitemQf" class="tab-pane fade active in"
							style="margin-top: 50px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">执行人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeAllowName" name="changeAllowName"
										type="text" readonly="readonly" maxlength="64"
										value="${workHelpSafeEntity.changeAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">执行监护人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="endPicName" name="endPicName"
										type="text" readonly="readonly" maxlength="64"
										value="${workHelpSafeEntity.endPicName}">
								</div>
							</div>
						</div>
						<div id="workitemJp" class="tab-pane fade active in">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">恢复人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="endAllowName" name="endAllowName"
										type="text" readonly="readonly" maxlength="64"
										value="${workHelpSafeEntity.endAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">恢复监护人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="safeFlag" name="safeFlag"
										type="text" readonly="readonly" maxlength="64"
										value="${workHelpSafeEntity.safeFlag}">
								</div>
							</div>
						</div>
				</form>
			</div>
			<!-- 审批字段结束 -->
	</div>
</div>
</div>
	<div style="margin-right:100px;margin-top: 20px;">

		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
			<c:if test="${nodeList.authFactorCode=='ztjBtn'}">
				<button id="ztjBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 再提交
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='fpBtn'}">
				<button id="fpBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 废票
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='hfjhBtn'}">
				<button id="hfjhBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 监护人确认（恢复）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='hfBtn'}">
				<button id="hfBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 恢复
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='zxjhBtn'}">
				<button id="zxjhBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 监护人确认（执行）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='zxBtn'}">
				<button id="zxBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 执行
				</button>
			</c:if>
		</c:forEach>

	</div>
	<script type="text/javascript">
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker' ],function(A) {

								//下面是安全措施的列表 1
								var workId = '${workHelpSafeEntity.id}';
								var conditionsOne = [];
								var workSafeOneDatatables = new A.datatables(
										{
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
														d.length = 2147483647;
														return JSON
																.stringify(d);
													}
												},
												multiple : true,
												ordering : true,
												checked : false,
												paging : false,
												bInfo : false,
												columns: [{data:"id", visible:false,orderable:false}, 
												          {data: "orderSeq",width: "10%",orderable: true},
												          {data: "executeSituation",width: "15%",orderable: true},
// 												          {data: "executeDate",width: "10%",orderable: true},
												          {data: "signerContent",width: "50%",orderable: true},
												          {data: "hfSituation",width: "15%",orderable: true}
// 												          {data: "hfDate",width: "10%",orderable: true}
												          ],
												          btns : [
																	{
																		id : "editzx",
																		label : "修改",
																		icon : "fa fa-pencil-square-o bigger-130",
																		className : "green edit",
																		render: function(btnNode, data){
																			var workStatus=${workHelpSafeEntity.workStatus};
																			if(workStatus!="2"){
																				btnNode.hide();
																			}
																		},
																		events : {
																			click : function(event,nRow,nData) {
																				var id = nData.id;
																				workSafeOneDialog = new A.dialog(
																						{
																							width : 800,
																							height : 300,
																							title : "操作项目编辑",
																							url:format_url("/workSafe/getHelpSafeApproveEdit/"+ id+"/"+1),
																							closed : function() {
																								workSafeOneDatatables.draw(false);
																							}
																						})
																						.render();
																			}
																		}
																	} ,{
																		id : "edithf",
																		label : "修改",
																		icon : "fa fa-pencil-square-o bigger-130",
																		className : "green edit",
																		render: function(btnNode, data){
																			var workStatus=${workHelpSafeEntity.workStatus};
																			if(workStatus!="4"){
																				btnNode.hide();
																			}
																		},
																		events : {
																			click : function(event,nRow,nData) {
																				var id = nData.id;
																				workSafeOneDialog = new A.dialog(
																						{
																							width : 800,
																							height : 300,
																							title : "操作项目编辑",
																							url:format_url("/workSafe/getHelpSafeApproveEditHf/"+ id+"/"+1),
																							closed : function() {
																								workSafeOneDatatables.draw(false);
																							}
																						})
																						.render();
																			}
																		}
																	}]
											}
										}).render();

								
								
								//控制回显多选框结束
								var taskId = $("#taskId").val();
								var procInstId = $("#procInstId").val();
								var procDefId = $("#procDefId").val();
								//执行
								$('#zxBtn').on('click',function() {
													$.ajax({
																url : format_url("/workHelpSafe/isExecute/"
																		+ workId),
																contentType : 'application/json',
																dataType : 'JSON',
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {

																		var workPerson = '${workHelpSafeEntity.guarderId}';
																		var loginUser = '${userEntity.id}';

																		// 										if(workPerson==loginUser){
																		// 											alert("许可人和工作负责人和签发人不能是同一个人");
																		// 											return;
																		// 										}else{
																		workSafeOneDialog = new A.dialog(
																									{
																										width : 851,
																										height : 450,
																										title : "执行",
																										url : format_url("/workHelpSafe/getAddZx?taskId="+ taskId+"&workId="+workId
																												),
																										closed : function() {
																										}
																									})
																									.render();
																		// 										}
																	} else {
																		alert(result.errorMsg);
																	}
																},
																error : function(
																		v, n) {
																	alert('操作失败');
																}
															});
												});
								//执行监护
								$('#zxjhBtn').on('click',function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 400,
																title : "执行监护人审批",
																url : format_url("/workHelpSafe/getAddZxjh?taskId="+ taskId+"&workId="+workId
																		),
																closed : function() {
																}
															}).render();
								});
								
								//恢复
								$('#hfBtn').on('click',function() {
													
													$.ajax({
																url : format_url("/workHelpSafe/isExecuteHf/"
																		+ workId),
																contentType : 'application/json',
																dataType : 'JSON',
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {

																		var workPerson = '${workHelpSafeEntity.guarderId}';
																		var loginUser = '${userEntity.id}';

																		// 										if(workPerson==loginUser){
																		// 											alert("许可人和工作负责人和签发人不能是同一个人");
																		// 											return;
																		// 										}else{
																		workSafeOneDialog = new A.dialog(
																									{
																										width : 851,
																										height : 450,
																										title : "恢复",
																										url : format_url("/workHelpSafe/getAddHf?taskId="+ taskId+"&workId="+workId
																												),
																										closed : function() {
																										}
																									})
																									.render();
																		// 										}
																	} else {
																		alert(result.errorMsg);
																	}
																},
																error : function(
																		v, n) {
																	alert('操作失败');
																}
															});
												});
								//监护
								$('#hfjhBtn').on('click',function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 800,
																height : 200,
																title : "恢复监护人审批",
																url : format_url("/workHelpSafe/getAddHfjh?taskId="+ taskId+"&workId="+workId
																		),
																closed : function() {
																}
															}).render();
								});
								//再提交
								$('#ztjBtn')
										.on(
												'click',
												function() {
													var id = '${workHelpSafeEntity.id}';
													workSafeOneDialog = new A.dialog(
															{
																width : 500,
																height : 370,
																title : "再提交",
																url : format_url("/workHelpSafe/sureSubmit?id="
																		+ id),
																closed : function() {
																}
															}).render();
								});
								//废票
								$('#fpBtn').on('click',
												function() {
													var obj = $("#workTicketFormEdit").serializeObject();
													obj.id = '${workHelpSafeEntity.id}';
													$.ajax({
																url : format_url("/workHelpSafe/disAgreeFp?taskId="
																		+ taskId
																		+ "&procInstId="
																		+ procInstId
																		+ "&selectUser="
																		+ ""),
																contentType : 'application/json',
																dataType : 'JSON',
																data : JSON.stringify(obj),
																cache : false,
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {
																		alert('废票成功');
																		window.scrollTo(0,0);
																		$("#page-container").load(format_url('/todoTask/list/1/10'));
																	} else {
																		alert(result.result);
																	}
																},
																error : function(
																		v, n) {
																	alert('废票失败');
																}
															});
												});
						
							});
		});
		function goBackToSubmitPerson(id, selectUser) {//回调函数
			var taskId = $("#taskId").val();
			var procInstId = $("#procInstId").val();
			var url = format_url("/workLine/againSubmit?workId=" + id
					+ "&selectUser=" + selectUser + "&taskId=" + taskId
					+ "&procInstId=" + procInstId);
			$.ajax({
				contentType : "application/json",
				dataType : "JSON",
				url : url,
				success : function(result) {
					if (result.result == "success") {
						alert('操作成功');
						window.scrollTo(0, 0);
						$("#page-container").load(
								format_url('/todoTask/list/1/10'));
					} else {
						alert(result.errorMsg);
					}
				},
				error : function(v, n) {
					alert('操作失败');
					workTicketDatatables.draw(false);
				}
			});

		}
		function gotoQx() {
			window.scrollTo(0, 0);
			A.loadPage({
				render : '#page-container',
				url : format_url("/todoTask/list/" + 1 + "/" + 10)
			});
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