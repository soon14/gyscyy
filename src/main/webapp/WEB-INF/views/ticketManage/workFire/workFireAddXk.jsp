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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormXk">
				<input type="hidden" id="selectUser" name="selectUser" />
				<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
				<input class="col-md-12" id="allowPicPersonId" name="allowPicPersonId"  type="hidden"  maxlength="64" value="${workTicketEntity.guarderId}">
				<input class="col-md-12" id="allowPicPersonName" name="allowPicPersonName" type="hidden"  readonly="readonly" type="text"  maxlength="64" value="${workTicketEntity.guarderName}">		
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>许可时间</label>
					<div class="col-md-4">
						<div id="qksjDiv"></div>
				</div>
				<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="gzxkrId"  type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
					</div>
			</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>允许动火时间</label>
					<div class="col-md-4">
							<div id="dhsjDiv"></div>
					</div>
					<label class="col-md-2 control-label no-padding-right">动火工作负责人签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="otherPicSign"  type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.guarderName}">
							<input class="col-md-12" id="otherPicSignId"  type="hidden" readonly="readonly" maxlength="64" value="${workTicketEntity.guarderId}">
					</div>
			</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">动火执行人签名</label>
						<div class="col-md-4">
							 <select id="otherExecutorSignId"  name="otherExecutorSignId"></select>
					    </div>	
					<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
						<div class="col-md-4">
							 <select id="runFireSignId"  name="runFireSignId" ></select>
					    </div>	
			</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">动火单位负责人签名</label>
						<div class="col-md-4">
							 <select id="otherGroupSignId"  name="otherGroupSignId"></select>
					    </div>	
					<label class="col-md-2 control-label no-padding-right">安监部门负责人签名</label>
						<div class="col-md-4">
							 <select id="otherSafeSignId"  name="otherSafeSignId" ></select>
					    </div>	
			</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">安全总监签名</label>
						<div class="col-md-4">
							 <select id="runSafeDirectorId"  name="runSafeDirectorId"></select>
					    </div>	
					<label class="col-md-2 control-label no-padding-right">分管生产负责人(或总工程师)签名</label>
						<div class="col-md-4">
							 <select id="runLederSignId"  name="runLederSignId" ></select>
					    </div>	
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">其他安全注意事项</label>
				<div class="col-md-10">
					<textarea id="other" name="other" placeholder="请输入其他安全注意事项" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">审批意见</label>
				<div class="col-md-10">
					<textarea id="approveIdea" name="approveIdea" placeholder="请输入审批意见" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
			<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">审批人</label>
						<div class="col-sm-10">
							<select multiple="multiple" size="5" name="duallistbox_demo1[]" id="duallist">
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
							</select>
						</div>
			</div>
			</form>
    		<div style="margin-right:100px;">
    			<button id="disagreeXk" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreeXk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					
					//日期组件
					var approveStarttimeDatePicker = new A.my97datepicker({
						id: "qksjZhuId",
						name:"qksjZhu",
						render:"#qksjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					approveStarttimeDatePicker.setValue('${xkdate}');
					
					//日期组件
					var approvetimeDatePicker = new A.my97datepicker({
						id: "approveStarttimeId",
						name:"approveStarttime",
						render:"#dhsjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					approvetimeDatePicker.setValue('${dhdate}');
// 					approvetimeDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.approveStarttime}" type="date"/>');
					
					//动火执行人
					var searchguarderName = new A.combobox({
						render : "#otherExecutorSignId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					//消防监护人
					var otherFireSignName = new A.combobox({
						render : "#runFireSignId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//动火单位负责人
					var otherGroupSign = new A.combobox({
						render : "#otherGroupSignId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//安监部门负责人
					var otherSafeSign = new A.combobox({
						render : "#otherSafeSignId",
						datasource : ${ajUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//安全总监签名
					var runSafeDirector = new A.combobox({
						render : "#runSafeDirectorId",
						datasource : ${aqzjUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//分管生产负责人(或总工程师)签名
					var runLederSign = new A.combobox({
						render : "#runLederSignId",
						datasource : ${lederUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
		
					$('#addFormXk').validate({
						debug:true,
						rules:  {
							qksjZhu:{required:true,maxlength:64},
							otherExecutorSignId:{maxlength:64},
							runFireSignId:{maxlength:64},
							otherGroupSignId:{maxlength:64},
							runSafeDirectorId:{maxlength:64},
							runLederSignId:{maxlength:64},
				          	approveStarttime:{required:true,maxlength:64},
					        approveEndtime:{maxlength:64},
					        approveIdea:{maxlength:64}
							},
						submitHandler: function (form) {
							
							var sdate='${sdate}';
							var edate='${edate}';
							if(approveStarttimeDatePicker.getValue()<sdate||approveStarttimeDatePicker.getValue()>edate){
								alert("许可时间应该在批准时间范围之内！");
								return;
							}
							
							//添加按钮
							 url = url;
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addFormXk").serializeObject();
							
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
										workSafeOneDialog.close();
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
				
					$("#agreeXk").on("click", function(){
						
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						url = format_url("/workFire/agreeXk?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+selectUser);
						$("#addFormXk").submit();
    				});
					
					$("#disagreeXk").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						url = format_url("/workFire/disagreeXk?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+"");
						$("#addFormXk").submit();
						
    				});
				
				});
			});
			
        </script>
    </body>
</html>