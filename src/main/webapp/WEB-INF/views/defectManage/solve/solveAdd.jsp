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
					<li class="active">处理</li>
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
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消缺结果
					</label>
					<div class="col-md-4">
					<select class="col-md-12 chosen-select" id="solveResult" name="solveResult"></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消缺负责人
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="userName" name="userName" type="text" placeholder="" maxlength="64" value="${userEntity.name}" readonly="readonly">
				    <input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}" readonly="readonly">
<!-- 				    	<select class="col-md-12 chosen-select" id="userId" name="userId"></select> -->
                    </div>
                 </div>
                 <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>实际完成时间
					</label>
					<div class="col-md-4">
						<div id="solveTimeDiv"></div>
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划完成时间
					</label>
					<div class="col-md-4">
						<div id="planTimeDiv"></div>
                	</div>
               </div>
                 <div class="form-group" id="defectTime" >
					<label class="col-md-2 control-label no-padding-right"  >
						<span style="color:red;">*</span>消缺时间
					</label>
					<div class="col-md-4">
						<div id="solveDateDiv"  ></div>
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>缺陷处理情况
				    </label>
				    <div class="col-md-10">
				   	 	<textarea id="solveExplain" name="solveExplain" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                    </div>
               </div>
                <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right" >
						<span style='color:red;' id="wcqk" >*</span>完成情况
				    </label>
				    <div class="col-md-10">
				   	 	<textarea id="endSolve" name="endSolve" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style='color:red;'  id="qxylyy" >*</span>缺陷遗留原因
					</label>
					<div class="col-md-10">
						<textarea id ="solveProblem" name="solveProblem" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
                </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right" >
						<span style='color:red;' id="lscs" >*</span>临时措施、应急方案及计划完成消缺时间
					</label>
					<div class="col-md-10">
						<textarea id="planEndTime" name="planEndTime" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
                </div>
                <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
							</div>
            </form>
             <!-- 下面是人的列表 -->
               <form class="form-horizontal" role="form"  style="margin-right:100px;" >
	                <div class="form-group " id="submitPerson">
	 					<label class="col-md-2 control-label no-padding-right">
	 					提交人
					    </label>
							<div class="col-sm-10">
								<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
									<c:forEach items="${userList}" var="userList" varStatus="vs">
										<option value="${userList.loginName}">${userList.name}</option>
									</c:forEach>
								</select>
								<div class="hr hr-16 hr-dotted"></div>
							</div>
				     </div>
			    </form>
    		<div style="margin-right:100px;">
<!--     			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--     				<i class="ace-icon fa fa-times"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="fa fa-check-square-o bigger-130"></i>
    				提交
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox','uploaddropzone'], function(A){
					$("#defectTime").attr("style","display:none;");//隐藏div
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					var af=$('#addForm').validate({
						debug:true,
						rules:{solveResult:{required:true,maxlength:64},
							solveTime:{required:true,maxlength:64},
							solveExplain:{required:true},
							 },
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/solve/check");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.taskId=${defectEntity.taskId};
							obj.procInstId=${defectEntity.procInstId};
							obj.defectId=${defectEntity.id};
							obj.processStatus=${defectEntity.processStatus};
							obj.unitId=obj.appraisalUnitId;
							var selectUser=$('[name="duallistbox_demo1[]"]').val();
							if($("#solveResult").val()!=1){
								obj.userList=selectUser.join(",");
							}
							 
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('操作成功');	
										window.scrollTo(0,0);
// 										listFormDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#solveResult").on("change", function(){
						if($("#solveResult").val()==1){
						af.settings.rules["endSolve"]={};
						af.settings.rules["solveProblem"]={};
						af.settings.rules["planEndTime"]={};
						$("#wcqk").hide();
						$("#qxylyy").hide();
						$("#lscs").hide();
							$("#submitPerson").hide();
						}else{
						af.settings.rules["endSolve"]={required:true};
						af.settings.rules["solveProblem"]={required:true};
						af.settings.rules["planEndTime"]={required:true};
						$("#wcqk").show();
						$("#qxylyy").show();
						$("#lscs").show();
							$("#submitPerson").show();
						}
   					});
					$("#saveBtn").on("click", function(){
						 var selectUser=$('[name="duallistbox_demo1[]"]').val();
						 if($("#solveResult").val()!=1){
							 if(selectUser==null||selectUser==""){
									alert('请选择审批人!');
									return;
								} 
						 }
							
							
							console.log($('#solveDateDivId').val());
							if(solveResult.getValue()==1){
							if($('#solveDateDivId').val()==""||$('#solveDateDivId').val()==null){
								alert('请填写消缺时间!');
								return;
							}
							}
						$("#addForm").submit();
    				});
					
					//发现人
// 					var userId = new A.combobox({
// 						render : "#userId",
// 						datasource : ${searchuser},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					userId.setValue('${userEntity.id}');
					var solveResult = new A.combobox({
						render : "#solveResult",
						datasource : ${solveResult},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					solveResult.change(function(){
						$("#defectTime").attr("style","display:none;");//隐藏div
						if(solveResult.getValue()==1){
// 							$("#defectTime").style.display="block";
// 							$("#defectTime").show();
							$("#defectTime").attr("style","display:block;");//显示div
							
						}
// 						if(solveResult.getValue()!=1){
// // 							$("#defectTime").style.display="block";
// // 							$("#defectTime").hidden();
// 							$("#defectTime").attr("style","display:none;");//隐藏div
// 						}
						
						
					});
					//日期组件
					var solveTimeDiv = new A.my97datepicker({
						id: "solveTime",
						name:"solveTime",
						render:"#solveTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					solveTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
					//日期组件
					var solveDateDiv = new A.my97datepicker({
						id: "solveDateDivId",
						name:"solveDate",
						render:"#solveDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var planTimeDiv = new A.my97datepicker({
						id: "planTime",
						name:"planTime",
						render:"#planTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					planTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
				});
				$('#btnBackZj').on('click',function(){
					var formURL="/defect/approve/"+ ${defectEntity.id};
					console.log(format_url("/todoTask/detail?id=${defectEntity.taskId}&currentPage="+ 1 
							+ "&pageSize=" + 20+"&procInstId=${defectEntity.procInstId}&procDefId=${defectEntity.procDefId}&formURL="+formURL))
					A.loadPage({
						render : '#page-container',
						url : format_url("/todoTask/detail?id=${defectEntity.taskId}&currentPage="+ 1 
								+ "&pageSize=" + 20+"&procInstId=${defectEntity.procInstId}&procDefId=${defectEntity.procDefId}&formURL="+formURL)
					});
				});
			});
        </script>
    </body>
</html>