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
	   <div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">业务管理</li>
					<li class="active">待办业务</li>
					<li class="active">待办详细</li>
					<li class="active">终结</li>
				</ul>
		</div>
		
		<div class="col-md-12" >
		<div class="page-content">
			<div class="tabbable" style="margin-top: 50px;">
			<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackZj" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
			</div>	
			
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormZj">
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
<!-- 			<input type="hidden" id="selectUser" name="selectUser" /> -->
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">（1）材料、工具已清理完毕，现场确无残留火种，临时安全措施已拆除，常设遮拦已恢复，已恢复作业开始前状态，参与现场动火工作的相关人员已全部撤离，动火工作已结束。</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>终结时间</label>
								<div class="col-md-4">
											<div id="endTimeDiv"></div>
											<input  type="hidden" id="plandateStart" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>' />
								</div>
							</div>
						
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">（2）接地线</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>共多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endHand" name="endHand" type="number"  maxlength="15" value="${workFireEntity.endHand}">
								</div>
								
							</div>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>已拆除共多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endStandIndex" name="endStandIndex" type="number"  maxlength="128" value="${workFireEntity.endStandIndex}">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>因另有工作保留共多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endStand" name="endStand" type="number"  maxlength="20" value="${workFireEntity.endStand}">
								</div>
							</div>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right">动火工作负责人签名</label>
								<div class="col-md-4">
										<input class="col-md-12" id="endPicIdZhu" name="endPicIdZhu" type="hidden"  maxlength="128" value="${userEntity.id}">
										<input class="col-md-12" id="endPicNameZhu" name="endPicNameZhu" type="text" readonly="readonly" maxlength="128" value="${userEntity.name}">
								</div>
								
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
								<div class="col-md-10">
									<textarea id="remark" name="remark"   
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workFireEntity.remark}</textarea>
								</div>
							</div>
<!-- 				<div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">审批人</label> -->
<!-- 						<div class="col-sm-10" style="margin-left: -10px;"> -->
<!-- 							<select multiple="multiple" size="5" name="duallistbox_demo1[]" id="duallist"> -->
<%-- 								<c:forEach items="${userList}" var="userList" varStatus="vs"> --%>
<%-- 									<option value="${userList.loginName}">${userList.name}</option> --%>
<%-- 								</c:forEach> --%>
<!-- 							</select> -->
<!-- 						</div> -->
<!-- 			      </div> -->

			    </form>
			</div>
			</div>
    		<div style="margin-right:100px;">
    			
    			<button id="suerZj" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				确定
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					var workid='${workIdZj}';
					var taskIdZj='${taskIdZj}';
					var procInstIdZj='${procInstIdZj}';
					var procDefIdZj='${procDefIdZj}';
					//日期组件
					var endTimeDatePicker = new A.my97datepicker({
						id: "endTimeDivId",
						name:"endTimeZhu",
						render:"#endTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'plandateStart\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					endTimeDatePicker.setValue('${zjdate}');
// 					endTimeDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>');
					
					
					$('#addFormZj').validate({
						debug:true,
						rules:  {
							endTimeZhu:{required:true,maxlength:64},
							endHand:{required:true,maxlength:64},
							endStandIndex:{required:true,maxlength:64},
							endStand:{required:true,maxlength:64},
							remark:{maxlength:256}
							},
						submitHandler: function (form) {
							//添加按钮
							url=url;
// 							 url = format_url("/workFire/sureZj?taskId="+taskIdZj+"&procInstId="+procInstIdZj);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormZj").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('提交成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('提交失败');
								}
							});
						}
					});
					
					$('#btnBackZj').on('click',function(){
						var formURL="/workTicketFire/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + taskIdZj + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstIdZj+
									"&procDefId="+procDefIdZj+"&formURL="+formURL)
						});
					});
					
					$("#suerZj").on("click", function(){
						
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
// 						if(selectUser==null||selectUser==""){
// 							alert('请选择下一步审批人!');
// 							return;
// 						}
						url = format_url("/workFire/sureZj?taskId="+taskIdZj+"&procInstId="+procInstIdZj);
						$("#addFormZj").submit();
					
// 						workticketDialog = new A.dialog({
// 							title:"审批确认",
// 							url:format_url("/workTicketFire/sureSubmit"),
// 							height:400,
// 							width:500
// 						}).render();
    				});
				});
			});
// 			function goBackToSubmitPerson(id,selectUser){//回调函数
// 				$("#selectUser").val(selectUser);
// 				$("#addFormZj").submit();
// 			}
        </script>
    </body>
</html>