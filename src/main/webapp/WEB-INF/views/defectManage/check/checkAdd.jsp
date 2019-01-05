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
					<li class="active">验收</li>
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
						<span style="color:red;">*</span>验收人
					</label>
					<div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="userId" name="userId"></select> -->
						<input class="col-md-12" id="userName" name="userName" type="text" placeholder="" maxlength="64" value="${userEntity.name}" readonly="readonly">
				    	<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}" readonly="readonly">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>验收时间
				    </label>
				    <div class="col-md-4">
				    	<div id="checkTimeDiv"></div>
                    </div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>验收结果
					</label>
					<div class="col-md-10">
					<textarea name="result" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						验收意见和建议
					</label>
					<div class="col-md-10">
					<textarea name="checkOpinions" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
					<textarea name="remark" placeholder="" style="height:60px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
                <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
							</div>
            </form>
               <!-- 下面是人的列表 -->
<!--                <form class="form-horizontal" role="form"  style="margin-right:100px;" > -->
<!-- 	                <div class="form-group " > -->
<!-- 	 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    </label> -->
<!-- 							<div class="col-sm-10"> -->
<!-- 								<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" > -->
<%-- 									<c:forEach items="${userList}" var="userList" varStatus="vs"> --%>
<%-- 										<option value="${userList.loginName}">${userList.name}</option> --%>
<%-- 									</c:forEach> --%>
<!-- 								</select> -->
<!-- 								<div class="hr hr-16 hr-dotted"></div> -->
<!-- 							</div> -->
<!-- 				     </div> -->
<!-- 			    </form> -->
    		<div style="margin-right:100px;" >
<!--     			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--     				<i class="ace-icon fa fa-times"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveOneBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-bottom: 20px">
    				<i class="glyphicon glyphicon-pawn"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox','uploaddropzone'], function(A){
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
					//添加按钮
					var url = "";
					$('#addForm').validate({
						debug:true,
						rules: {
							checkTime:{required:true,maxlength:64},
							result:{required:true},
						},
						submitHandler: function (form) {
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.taskId=${defectEntity.taskId};
							obj.procInstId=${defectEntity.procInstId};
							obj.defectId=${defectEntity.id};
							obj.processStatus=${defectEntity.processStatus};
							obj.unitId=obj.appraisalUnitId;
							var selectUser=$('[name="duallistbox_demo1[]"]').val();
							if(selectUser){obj.userList=selectUser.join(",");}
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
										alert('验收成功');	
// 										listFormDialog.close();
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('验收失败');
								}
							});
						}
					});
					$("#saveOneBtn").on("click", function(){
						 url = format_url("/check/solve");
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
					//日期组件
					var checkTimeDiv = new A.my97datepicker({
						id: "checkTime",
						name:"checkTime",
						render:"#checkTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					checkTimeDiv.setValue('<fmt:formatDate value="${date}" type="both"/>');
				});
				$('#btnBackZj').on('click',function(){
					var formURL="/defect/approve/"+ ${defectEntity.id};
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