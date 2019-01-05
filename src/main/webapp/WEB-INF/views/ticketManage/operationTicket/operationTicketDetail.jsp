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
			<li class="active">查看</li>
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
							style="margin-right:100px;" id="approveForm">
							<input id="id" name="id" value="${t.id}" type="hidden"/>
							<input id="operateName"  value="${t.operateName}" type="hidden"/>
							<input id="guardianName"  value="${t.guardianName}" type="hidden"/>
							<input id="status"  name="status" value="0" type="hidden"/>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									编号
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="code" name="code" type="text"
										placeholder="" maxlength="64" value="${t.code}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									单位名称
								</label>
								<div class="col-md-4">
											<input class="col-md-12" id="unitName" name="unitName" type="text" 
											placeholder="" maxlength="64" value="${t.unitName}" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">设备编码</label>
										<div class="col-md-4">
										<input class="col-md-12" id="equipmentCode" name="equipmentCode" type="text"
										placeholder="" maxlength="64" value="${t.equipmentCode}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" 
											placeholder="" maxlength="64" value="${t.equipmentName}" readonly="readonly">
								</div>
							</div>
							<%-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									操作开始时间
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="startDate" name="startDate" type="text" readonly="readonly"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.startDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									操作终了时间
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="endDate" name="endDate" type="text" readonly="readonly"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.endDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>">
								</div>
							</div> --%>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									工作票票号
								</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workticketCode" readonly="readonly"
										name="workticketCode" type="text" placeholder=""
										maxlength="64" value="${t.workticketCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									缺陷单编号 </label>
								<div class="col-md-4">
								<input class="col-md-12" id="defectCode" name="defectCode" type="text" 
											placeholder="" maxlength="64" value="${t.defectCode}" readonly="readonly">
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right"> -->
<!-- 									班组 </label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 								<input class="col-md-12" id="defectCode" name="defectCode" type="text"  -->
<!-- 											placeholder="" maxlength="64" value="${t.groupName}" readonly="readonly"> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									发令单位
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="startUnitName" name="startUnitName" type="text"
										placeholder="" maxlength="64" value="${t.startUnitName}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									 发令人 
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="startUserName" name="startUserName" type="text"
										placeholder="" maxlength="64" value="${t.startUserName}" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									受令人
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="endUserName" name="endUserName" type="text"
										placeholder="" maxlength="64" value="${t.endUserName}" readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">
									受令时间 
								</label>
								<div class="col-md-4">
								<input class="col-md-12" id="endTime" name="endTime" type="text"
										placeholder="" maxlength="64" value="<fmt:formatDate value="${t.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									操作任务
								</label>
								<div class="col-md-10"> 
									<textarea name="workText" placeholder="" readonly="readonly"
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.workText}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									备注 </label>
								<div class="col-md-10">
									<textarea name="remark" placeholder="" readonly="readonly"
										style="height:100px; resize:none;" class="col-md-12"
										maxlength="128">${t.remark}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									附件 </label>
								<div class="col-md-10" id="divfile" >
								</div>
							</div>
							
							<div class="form-group" id="yydxpDiv">
								<label class="col-md-2 control-label no-padding-right">
									鉴定 </label>
								<div class="col-md-10 ">
									<button  id="yydxp" class="btn btn-xs btn-success" type="button">
									 鉴定
									</button>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									鉴定内容备注 </label>
								<div class="col-md-10">
									<textarea name="invalidContent" placeholder="" 
										style="height:100px; resize:none;" class="col-md-12" readonly="readonly"
										maxlength="128">${t.invalidContent}</textarea>
								</div>
							</div>
							
						</form>
						<div id="operationItem" ></div>
<!-- 						<form class="form-horizontal" role="form"  -->
<!-- 						style="margin-right:100px;margin-top: 15px"> -->
<!-- 							<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"> -->
<!-- 										终止原因</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea name="reason" placeholder="" id="reason" readonly="readonly" -->
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
											 <input  id="operateId" value="${t.picName}" class="col-md-12" type="text" readonly="readonly"/>
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
    		<div style="margin-right:100px;margin-top: 20px;"  >
        		<button   id="printBtn"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-print"></i>
		    				打印
		    	</button>
        	</div>
        </div>
	<script type="text/javascript">
	var listFormDialog;
		jQuery(function($) {
			seajs.use([ 'combobox', 'combotree', 'my97datepicker','uploaddropzone','selectbox' ],function(A) {
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${t.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					$("#equipmentCode").attr("title",'${t.equipmentCode}');
					$("#equipmentName").attr("title",'${t.equipmentName}');
					
					
					var id=${t.id};
					
					$('#yydxp').on('click', function() {
						
						listFormDialog = new A.dialog({
							title:"鉴定",
// 							url:format_url("/typicalTicket/invalid?id="+ id),
							url:format_url("/operationTicket/invalid?id="+ id),
							height:400,
							width:600,
							closed: function(){
// 								A.loadPage({
// 									render : '#page-container',
// 									url : format_url("/operationTicket/index")
// 								});
							}
						}).render();
						
					});
					
					
						//加载项目列表
						$.ajax({url : format_url("/operationItem/indexDetail"),
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							data : {"id":0},
							success : function(result) {
								var divshow = $("#operationItem");
								divshow.text("");// 清空数据
								divshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
						var print="operate_ticket.rptdesign";
						$("#printBtn").on("click", function(e){
								birtPrint(print,${t.id});
						});
						$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
							if($(e.target).attr('href') == "#workitem"){
								 print="operate_ticket.rptdesign";
							}
							if($(e.target).attr('href') == "#operationDanger"){
								print="operate_ticket_danger.rptdesign";
								//加载控制卡列表
								$.ajax({url : format_url("/operationDanger/indexDetail"),
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
							var rlId = "${rlId}";
							if(rlId==""){
								A.loadPage({
									render : '#page-container',
									url : format_url("/operationTicket/index")
								});
							}else {
								A.loadPage({
									render : '#page-container',
									url : format_url("/runLog/info/"+rlId)
								});	
							}
							
						});
					});
		});
	</script>
</body>
</html>