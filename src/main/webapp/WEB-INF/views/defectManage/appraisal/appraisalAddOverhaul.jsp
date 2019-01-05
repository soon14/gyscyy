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
					<li class="active">鉴定</li>
				</ul>
		</div>
		<div class="col-md-12"  style="margin-top: 50px;" >
			<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackZj" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
			</div>
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			 <div class="form-group">
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>执行结束时间 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div id="endTimeDiv"></div> -->
<!--                 	</div> -->
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>鉴定结果
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="appraisalResult" name="appraisalResult"></select>
                    </div>
                    <div id="gradeDiv">
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>缺陷类型
					</label>
					<div class="col-md-4">
						<select class="col-md-12 chosen-select" id="grade" name="grade"  ></select>
                	</div>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						鉴定人
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="userName" name="userName" type="text" placeholder="" maxlength="64" value="${userEntity.name}" readonly="readonly">
				    <input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}" readonly="readonly">
<!--  				    	<select class="col-md-12 chosen-select" id="userId" name="userId"></select>  -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>鉴定时间
					</label>
					<div class="col-md-4">
<!--  						<div id="appraisalTimeDiv"></div>  -->
 						<input class="col-md-12" id="appraisalTime" name="appraisalTime" type="text" placeholder="" maxlength="64" value="<fmt:formatDate value="${appraisalTime}" type="both"/>" readonly="readonly"> 
                	</div>
               </div>
                 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>处理建议
				    </label>
				    <div class="col-md-10">
				    	<textarea name="appraisalOpinions" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                    </div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						审批意见 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10"> -->
<!-- 					<textarea name="approvalOpinions" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea> -->
<!--                 	</div> -->
<!--                </div> -->
            </form>
             <!-- 下面是人的列表 -->
               <form class="form-horizontal" role="form"  style="margin-right:100px;" >
	                <div class="form-group " >
	 					<label class="col-md-2 control-label no-padding-right">
	 					审批人
					    </label>
							<div class="col-sm-10">
								<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
									<c:forEach items="${userList1}" var="userList" varStatus="vs">
										<option value="${userList.loginName}">${userList.name}</option>
									</c:forEach>
								</select>
								<div class="hr hr-16 hr-dotted"></div>
							</div>
				     </div>
			    </form>
    		<div style="margin-right:100px;">
    			<!-- <button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" id="reject">
    				<i class="ace-icon fa fa-times"></i>
    				驳回
    			</button> -->
    			<button  class="btn btn-xs btn-primary pull-right"  style="margin-right:15px;" id="saveBtn">
    				<i class="glyphicon glyphicon-star"></i>
    				鉴定
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
 					$('.box1').attr("style","padding-left:0px");
					//添加按钮
					var url ="";
					var selectUser="";
					$("#grade").hide();
					$("#saveBtn").on("click", function(){
						 url = format_url("/appraisal/monitor");
							 var selectUser=$('[name="duallistbox_demo1[]"]').val();
								if(selectUser==null||selectUser==""){
									alert('请选择缺陷审批人!');
									return;
								}
						$('#addForm').validate({
							debug:true,
							rules: {appraisalUnitId:{required:true,maxlength:64},
								appraisalTime:{required:true,maxlength:64},
								appraisalResult:{required:true,maxlength:64},
								grade:{maxlength:64},
								repeat:{required:true,maxlength:64},
								endTime:{required:true,maxlength:64},
								appraisalOpinions:{required:true,maxlength:64},
								 },
							submitHandler: function (form) {
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addForm").serializeObject();
								obj.taskId=${defectEntity.taskId};
								obj.procInstId=${defectEntity.procInstId};
								obj.defectId=${defectEntity.id};
								obj.processStatus=${defectEntity.processStatus};
								obj.unitId=obj.appraisalUnitId;
								 selectUser=$('[name="duallistbox_demo1[]"]').val();
									obj.userList=selectUser.join(",");
								if(url==format_url("/appraisal/monitor")){
									 selectUser=$('[name="duallistbox_demo1[]"]').val();
									obj.userList=selectUser.join(",");
								};
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										if(result.result=="success"){
											alert('鉴定成功');	
											$("#page-container").load(format_url('/todoTask/list/1/10'));
										}else{
											alert(result.result);
										}
									},
									error:function(v,n){
										alert('鉴定失败');
									}
								});
							}
						});
						$("#addForm").submit();
						
    				});
					
					$("#reject").on("click", function(){
						url = format_url("/appraisal/function");
						$('#addForm').validate({
							debug:true,
							rules: {
								 },
							submitHandler: function (form) {
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addForm").serializeObject();
								obj.taskId=${defectEntity.taskId};
								obj.procInstId=${defectEntity.procInstId};
								obj.defectId=${defectEntity.id};
								obj.processStatus=${defectEntity.processStatus};
								obj.unitId=obj.appraisalUnitId;
								if(url==format_url("/appraisal/monitor")){
									 selectUser=$('[name="duallistbox_demo1[]"]').val();
									obj.userList=selectUser.join(",");
								};
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										if(result.result=="success"){
											alert('鉴定成功');	
											$("#page-container").load(format_url('/todoTask/list/1/10'));
										}else{
											alert(result.result);
										}
									},
									error:function(v,n){
										alert('鉴定失败');
									}
								});
							}
						});
							 
						$("#addForm").submit();
						
    				});
						//部门控件下拉树
						var appraisalUnitIdDiv = new A.combotree({
						render: "#appraisalUnitIdDiv",
						name: 'appraisalUnitId',
// 						返回数据待后台返回TODO
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
						appraisalUnitIdDiv.setValue('${userEntity.unitId}');
						//发现人
// 						var userId = new A.combobox({
// 							render : "#userId",
// 							datasource : ${searchuser},
// 							allowBlank: true,
// 							options : {
// 								"disable_search_threshold" : 10
// 							}
// 						}).render();
// 						userId.setValue('${userEntity.id}');
						var grade = new A.combobox({
							render : "#grade",
							datasource : ${type},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
// 						var repeat = new A.combobox({
// 							render : "#repeat",
// 							datasource : ${repeatType},
// 							allowBlank: true,
// 							options : {
// 								"disable_search_threshold" : 10
// 							}
// 						}).render();
						 var resultType = new A.combobox({
							render : "#appraisalResult",
							datasource : ${resultType},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
						 resultType.change(function(){
							 debugger;
								if(resultType.getValue()==2){
									$("#gradeDiv").hide();
								}else{
									$("#gradeDiv").show();
								}
								
							});
						//日期组件
						var appraisalTimeDiv = new A.my97datepicker({
							id: "appraisalTime",
							name:"appraisalTime",
							render:"#appraisalTimeDiv",
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: "yyyy-MM-dd HH:mm:ss"
							}
						}).render();
						appraisalTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
// 						var endTimeDiv = new A.my97datepicker({
// 							id: "endTime",
// 							name:"endTime",
// 							render:"#endTimeDiv",
// 							options : {
// 									isShowWeek : false,
// 									skin : 'ext',
// 									maxDate: "",
// 									minDate: "",
// 									dateFmt: "yyyy-MM-dd HH:mm:ss"
// 							}
// 						}).render();
// 						endTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
						var reportTimeDiv = new A.my97datepicker({
							id: "reportTime",
							name:"reportTime",
							render:"#reportTimeDiv",
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "",
									dateFmt: "yyyy-MM-dd HH:mm:ss"
							}
						}).render();
						reportTimeDiv.setValue('<fmt:formatDate value="${defect.createDate}" type="both"/>');
				});
				$('#btnBackZj').on('click',function(){
					var formURL="/defect/approve/"+ ${defectEntity.id};
					A.loadPage({
						render : '#page-container',
						url : format_url("/todoTask/detail?id=" + ${defectEntity.taskId} + "&currentPage="+ 1 
								+ "&pageSize=" + 20+"&procInstId="+${defectEntity.procInstId}+
								"&procDefId=${defectEntity.procDefId}&formURL="+formURL)
					});
				});
			});
        </script>
    </body>
</html>