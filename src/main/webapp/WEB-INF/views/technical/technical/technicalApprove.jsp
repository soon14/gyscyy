<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a   data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							基础信息
						</a>
		 			</li>
		 		</ul>
				<div class="tab-content">
				<div id="workitem" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 600px">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
				                <input type="hidden" id="id" name="id" value="${technicalEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">单位名称</label>
									<div class="col-md-4">
										<input id="unitNameId" type="text" readonly="readonly"
												value="${technicalEntity.unitName}" class="col-md-12">
								    </div>
									<label class="col-md-2 control-label no-padding-right">填报人</label>
									<div class="col-md-4">
										<input id="fillId" type="text" readonly="readonly"
												value="${technicalEntity.fillName}" class="col-md-12">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">时间</label>
									<div class="col-md-4">
										<input id="plandateStartDiv" type="text" readonly="readonly"
												value="${technicalEntity.time}" class="col-md-12">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划名称</label>
									<div class="col-md-4">
										<input type="text" id="planName" name="planName" readonly="readonly" maxlength="64" class="col-md-12" value="${technicalEntity.planName }"></input>
								</div>
							    </div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea id="remark" name="remark" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${technicalEntity.remark}</textarea>
									</div>
								</div>
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
						</form>	
    			<div class="widget-main no-padding" >
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">定期工作</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>工作项目</th>
	                                <th>负责人</th>
	                                <th>计划缘由</th>
	                                <th>监督专业</th>
	                                <th>计划时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">临时工作</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>工作项目</th>
	                                <th>负责人</th>
	                                <th>计划缘由</th>
	                                <th>监督专业</th>
	                                <th>计划时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
                </div>
                <form class="form-horizontal" role="form" style="margin-right:100px;" id="spxxForm">
                <div id="jxzgDiv" class="tab-pane fade active in" style="margin-top: 50px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">生技处</label>
								<div class="col-md-4">
									<input class="col-md-12" id="jxzgName" name="jxzgName"
										type="text" readonly="readonly" maxlength="64"
										value="${technicalEntity.jxzgName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">审核日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="jxzgDate" name="jxzgDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${technicalEntity.jxzgDate}" type="date"/>'>
								</div>
							</div>
				</div>
				<div id="jxzrDiv" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">生技处主任</label>
								<div class="col-md-4">
									<input class="col-md-12" id="jxzrName" name="jxzrName"
										type="text" readonly="readonly" maxlength="64"
										value="${technicalEntity.jxzrName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">审核日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="jxzrDate" name="jxzrDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${technicalEntity.jxzrDate}" type="date"/>'>
								</div>
							</div>
				</div>
				<div id="sjbzrDiv" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">上级领导</label>
								<div class="col-md-4">
									<input class="col-md-12" id="sjbzrName" name="sjbzrName"
										type="text" readonly="readonly" maxlength="64"
										value="${technicalEntity.sjbzrName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">审核日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="sjbzrDate" name="sjbzrDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${technicalEntity.sjbzrDate}" type="date"/>'>
								</div>
							</div>
				</div>	
<!-- 				<div id="zgDiv" class="tab-pane fade active in" style="margin-top: 20px;"> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">总工</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="zgName" name="zgName" -->
<!-- 										type="text" readonly="readonly" maxlength="64" -->
<%-- 										value="${technicalEntity.zgName}"> --%>
<!-- 								</div> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">审核日期</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="zgDate" name="zgDate" -->
<!-- 										type="text" readonly="readonly" maxlength="64" -->
<%-- 										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${technicalEntity.zgDate}" type="date"/>'> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 				</div>		 -->
				</form>
		    	</div>
    		</div>
    	</div>
    </div>
</div>
<div class="col-md-12">
<div style="margin-right:10px;margin-top: 20px;">
		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
			<c:if test="${nodeList.authFactorCode=='zfBtn'}">
				<button id="zfBtn" class="btn btn-xs btn-danger pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 作废
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='ztjBtn'}">
				<button id="ztjBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 再提交
				</button>
			</c:if>

			<c:if test="${nodeList.authFactorCode=='biotechSPBtn'}">
				<button id="biotechSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技处审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='biotechManageSPBtn'}">
				<button id="biotechManageSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 生技部主任审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='leaderSPBtn'}">
				<button id="leaderSPBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 上级领导审核
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='jxzgBtn'}">
				<button id="jxzgBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 检修专工审核
				</button>
			</c:if>
		</c:forEach>

	</div>
</div>
<script type="text/javascript">
			var workSafeOneDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					var taskId = $("#taskId").val();
					var procInstId = $("#procInstId").val();
					var procDefId = $("#procDefId").val();
					var technicalId = ${technicalEntity.id};
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${technicalEntity.fileid},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					var technicalId='${technicalEntity.id}';
					var spstatus='${technicalEntity.spStatus}';
					//安全措施 开始
					//下面是安全措施的列表 1
					var conditionsOne=[];
					var workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/technicalWork/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsOne=[];
					            	conditionsOne.push({
		            					field: 'C_WORK_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1
		            				});
					            	conditionsOne.push({
		            					field: 'C_TECHNICAL_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${technicalEntity.id}'
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSqe",width: "auto",orderable: true},
							          {data: "content",width: "auto",orderable: true},
 							          {data: "picName",width: "auto",orderable: true},
 							          {data: "danger",width: "auto",orderable: true},
 							          {data: "jdzyName",width: "auto",orderable: true},
 							          {data: "time",width: "auto",orderable: true}
							          ],
							btns: [ 
						{
						    id:"440",
							label:"详细计划",
							icon: "fa fa-cog bigger-130",
							className: "blue",
							render: function(btnNode, data){
								
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var orderSqe=nData.orderSqe;
									
									if(spstatus!="7"&&spstatus!="9"){
										A.loadPage({
											render : '#page-container',
											//url:format_url("/technicalPlandetail/detail?id="+ id+"&technical="+technicalId+"&orderSqe="+orderSqe),
											url:format_url("/technicalPlandetail/indexSpnotAdd?id="+ id+"&technical="+technicalId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId+"&orderSqe="+orderSqe),
										});
									}else{
										A.loadPage({
											render : '#page-container',
											url:format_url("/technicalPlandetail/index?id="+ id+"&technical="+technicalId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId+"&orderSqe="+orderSqe),
										});
									}
								}
							}
						}
						]
						}
					}).render();
					//下面是安全措施的列表 2
					var conditionsTwo=[];
					var workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/technicalWork/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsTwo=[];
					            	conditionsTwo.push({
		            					field: 'C_WORK_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:2
		            				});
					            	conditionsTwo.push({
		            					field: 'C_TECHNICAL_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${technicalEntity.id}'
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSqe",width: "auto",orderable: true},
							          {data: "content",width: "auto",orderable: true},
 							          {data: "picName",width: "auto",orderable: true},
 							          {data: "danger",width: "auto",orderable: true},
 							         {data: "jdzyName",width: "auto",orderable: true},
 							        {data: "time",width: "auto",orderable: true}
							          ],
							btns: [
						{
						    id:"440",
							label:"详细计划",
							icon: "fa fa-cog bigger-130",
							className: "blue",
							render: function(btnNode, data){
								/* if(spstatus!="7"&&spstatus!="9"){
									btnNode.hide();
								} */
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var orderSqe=nData.orderSqe;
									
									if(spstatus!="7"&&spstatus!="9"){
										A.loadPage({
											render : '#page-container',
											//url:format_url("/technicalPlandetail/detail?id="+ id+"&technical="+technicalId+"&orderSqe="+orderSqe),
											url:format_url("/technicalPlandetail/indexSpnotAdd?id="+ id+"&technical="+technicalId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId+"&orderSqe="+orderSqe),
										});
									}else{
										A.loadPage({
											render : '#page-container',
											url:format_url("/technicalPlandetail/index?id="+ id+"&technical="+technicalId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId+"&orderSqe="+orderSqe),
										});
									}
								}
							}
						}
							
						]
						}
					}).render();
					
					
					//生技处审批
					$('#biotechSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技处审核",
									url : format_url("/technical/getAddBiotech?technicalId="
											+ technicalId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//生技部主任审批
					$('#biotechManageSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "生技主任审核",
									url : format_url("/technical/getAddBiotechManage?technicalId="
											+ technicalId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					//上级领导审批
					$('#leaderSPBtn').on('click',function() {
						workSafeOneDialog = new A.dialog(
								{
									width : 1000,
									height : 400,
									title : "上级领导审核",
									url : format_url("/technical/getAddLeader?technicalId="
											+ technicalId
											+ "&taskId="
											+ taskId),
									closed : function() {
									}
								}).render();	
					});
					
					//废票
					$('#zfBtn').on('click',function() {
							var obj = $("#workTicketForm").serializeObject();
							obj.id = ${technicalEntity.id};
							$.ajax({
										url : format_url("/technical/disAgreeFp?taskId="
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
										success : function(result) {
											if (result.result == "success") {
												alert('作废成功');
												window.scrollTo(0,0);
												$("#page-container").load(format_url('/todoTask/list/1/10'));
											} else {
												alert(result.result);
											}
										},
										error : function(v, n) {
											alert('作废失败');
										}
									});
					});
								//再提交
								$('#ztjBtn').on('click',function() {
// 									$.ajax({
// 										url: format_url("/technical/approveValidate?technicalId="+technicalId),
// 										contentType : 'application/json',
// 										dataType : 'JSON',
// 										type: 'POST',
// 										success: function(result){
// 											if(result.result == 'success'){
												 workSafeOneDialog = new A.dialog({
														width : 1000,
														height : 400,
														title : "负责人提交",
														url : format_url("/technical/getAddZtj?technicalId="
																+ technicalId
																+ "&taskId="
																+ taskId),
														closed : function() {
														}
													}).render();	
// 											} else{
// 												alert(result.errorMsg);
// 											}
// 										}
// 									});
								});
					
					
					});
			});
			/* 
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
							$("#page-container").load(format_url('/todoTask/list/1/10'));
						} else {
							alert(result.errorMsg);
						}
					},
					error : function(v, n) {
						alert('操作失败');
						workTicketDatatables.draw(false);
					}
				});

			} */
        </script>
    </body>
</html>