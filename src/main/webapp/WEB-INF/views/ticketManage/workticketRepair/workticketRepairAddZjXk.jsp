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
					<li class="active">终结许可</li>
				</ul>
		</div>
		
		<div class="col-md-12" >
			<div class="page-content">
			<div class="tabbable" style="margin-top: 50px;">
			<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackZjXk" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
			</div>	
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormZjXk">
			<input type="hidden" id="selectUser" name="selectUser" />
			<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
			<input class="col-md-12" id="workPersonId"  type="hidden"  maxlength="64" value="${workPersonId}">
			<input class="col-md-12" id="allowPicPersonId" name="allowPicPersonId"  type="hidden"  maxlength="64" value="${repairTicketEntity.guarderId}">
			<input class="col-md-12" id="allowPicPersonName" name="allowPicPersonName" type="hidden"  readonly="readonly" type="text"  maxlength="64" value="${repairTicketEntity.guarderName}">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">（1）全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>终结时间</label>
								<div class="col-md-4">
											<div id="endTimeDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
												<input class="col-md-12" id="endAllowIdZhu" name="endAllowIdZhu" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}">
												<input class="col-md-12" id="endAllowNameZhu" name="endAllowNameZhu" type="text" readonly="readonly"  maxlength="64" value="${userEntity.name}">
								</div>
							</div>
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">已汇报值长（值班长）。</h5>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>抢修结果</label>
									<div class="col-md-10" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 10%;">
										抢修已结束<input type="checkbox" name="repairResult" value="0" />
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										抢修未结束已转移工作票<input type="checkbox" name="repairResult" value="1" />
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">保留安全措施</label>
									<div class="col-md-10">
										<textarea id="retainSafe" name="retainSafe" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
							<div class="form-group" style="margin-top: 20px;">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
								<div class="col-md-10">
									<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
								</div>
							</div>
							<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
							</div>
				<%-- <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">审批人</label>
						<div class="col-sm-10" style="margin-left: -10px;">
							<select multiple="multiple" size="5" name="duallistbox_demo1[]" id="duallist">
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
						</div>
			   </div> --%>
			</form>
			</div>
			</div>
    		<div style="margin-right:100px;">
    			<!-- <button id="disagreeZjXk" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button> -->
    			<button id="agreeZjXk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','duallistbox'], function(A){
					
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					
					$("input[name='repairResult']").on('click',function(){
						var value=this.value;
						$("input[name='repairResult']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var workid='${workIdZjXk}';
					var taskIdZjXk='${taskIdZjXk}';
					var procInstIdZjXk='${procInstIdZjXk}';
					var procDefIdZjXk='${procDefIdZjXk}';
					//日期组件
					var endTimeDatePicker = new A.my97datepicker({
						id: "endTimeDivId",
						name:"endTimeZhu",
						render:"#endTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					endTimeDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>');
					
					$('#addFormZjXk').validate({
						debug:true,
						rules:  {
							endTimeZhu:{required:true,maxlength:64},
							repairResult:{required:true,maxlength:64},
							approveIdea:{required:true,maxlength:64},
							},
						submitHandler: function (form) {
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormZjXk").serializeObject();
							obj.fileid=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('审批成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('审批失败');
								}
							});
						}
					});
// 					$("#agreeZjXk").on("click", function(){
// 						url = format_url("/workticketRepair/agreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk);
// 						workticketDialog = new A.dialog({
// 							title:"提交确认",
// 							url:format_url("/repairTicket/sureSubmit"),
// 							height:400,
// 							width:500
// 						}).render();
//     				});
					
					 $("#agreeZjXk").on("click", function(){
							
							var selectUser=$('[name="duallistbox_demo1[]"]').val();
							/* if(selectUser==null||selectUser==""){
								alert('请选择下一步审批人!');
								return;
							} */
// 							url = format_url("/workticketRepair/agreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&selectUser="+selectUser);
							url = format_url("/workticketRepair/agreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk);
							$("#addFormZjXk").submit();

	    				});
					
					$("#disagreeZjXk").on("click", function(){
						var workPersonId=$("#workPersonId").val();
// 						workticketDialog = new A.dialog({
// 							title:"提交确认",
// 							url:format_url("/repairTicket/sureSubmit"),
// 							height:450,
// 							width:500
// 						}).render();
						url = format_url("/workticketRepair/disagreeZjXk?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&workPersonId="+workPersonId);
						$("#addFormZjXk").submit();
					});
					$('#btnBackZjXk').on('click',function(){
						var formURL="/repairTicket/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + taskIdZjXk + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstIdZjXk+
									"&procDefId="+procDefIdZjXk+"&formURL="+formURL)
						});
					});
				});
			});
				function goBackToSubmitPerson(id,selectUser){//回调函数
					$("#selectUser").val(selectUser);
					$("#addFormZjXk").submit();
				}
        </script>
    </body>
</html>