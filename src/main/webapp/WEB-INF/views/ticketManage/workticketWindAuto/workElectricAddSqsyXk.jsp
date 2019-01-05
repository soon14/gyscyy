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
					<li class="active">申请试运</li>
				</ul>
		</div>
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" style="margin-top: 50px;">
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackSqsyFh" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormSqsyXk">
		        <input type="hidden" id="id" name="id" value="${workElectricEntity.id}" />
		        <input type="hidden" id="taskIdSqsy"  value="${taskIdSqsy}" />
		        <input type="hidden" id="procInstIdSqsy"  value="${procInstIdSqsy}" />
		        <input type="hidden" id="procDefId"  value="${procDefId}" />
		         <input type="hidden" id="selectUser" name="selectUser" />
		         <input type="hidden" id="approveIdea" name="approveIdea" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketEntity.code}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">部门</label>
									<div class="col-md-4">
										<input  id="unitNameIdSpsy"  type="text" readonly="readonly" value="${workTicketEntity.unitNameId}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdSpsy"  type="text" readonly="readonly" value="${workTicketEntity.groupId}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">1.试运行设备</label>
								<div class="col-md-10">
											<input class="col-md-12" id="equipmentName"  readonly="readonly" type="text" placeholder="" maxlength="64" value="${workTicketEntity.equipmentName}">
								</div>
							 </div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">2.工作地点</label>
									<div class="col-md-10">
										<input id="address"  type="text"  readonly="readonly" class="col-md-12" maxlength="128" value="${workTicketEntity.address}"></input>
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">3.工作负责人</label>
									<div class="col-md-10">
											<input class="col-md-12" id="guarderName" readonly="readonly" type="text"   value="${workTicketEntity.guarderName}"></input>
									</div>
							</div>
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">4.工作票及试运行涉及系统其他工作票收回。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">热控工作票编号</label>
									<div class="col-md-4">
										<input  id="heatCode"  name="heatCode" type="text" readonly="readonly" maxlength="30" value="${workElectricEntity.heatCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="heatPersonName"  name="heatPersonName"  type="text" readonly="readonly" maxlength="30" value="${workElectricEntity.heatPersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">电气工作票编号</label>
									<div class="col-md-4">
										<input  id="electricCode" name="electricCode" type="text" readonly="readonly" value="${workElectricEntity.electricCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="electricPersonName" name="electricPersonName" type="text" readonly="readonly" value="${workElectricEntity.electricPersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">机械工作票编号</label>
									<div class="col-md-4">
										<input  id="machineCode" name="machineCode" type="text" readonly="readonly" value="${workElectricEntity.machineCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="machinePersonName" name="machinePersonName" type="text" readonly="readonly" value="${workElectricEntity.machinePersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他</label>
									<div class="col-md-10">
										<textarea id="recoverOther" name="recoverOther"  readonly="readonly" style="height:50px; resize:none;" class="col-md-12" maxlength="128">${workElectricEntity.recoverOther}</textarea>
									</div>
							</div>	
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票交回，所列安全措施已拆除，可以试运行。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">日期：</label>
									<div class="col-md-4">
												<input class="col-md-12" id="runSureDateSqsy" name="runSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dateSqsyTime}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
												<input class="col-md-12" id="runPicId" name="runPicId" type="hidden" placeholder="" maxlength="64" value="${workElectricEntity.runPicId}">
												<input class="col-md-12" id="runPicName" name="runPicName" type="text" readonly="readonly"  maxlength="64" value="${workElectricEntity.runPicName}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作许可人</label>
									<div class="col-md-4">
												<input class="col-md-12" id="runAllowId" name="runAllowId" type="hidden" placeholder="" maxlength="64" value="${userEntity.id}">
												<input class="col-md-12" id="runAllowName" name="runAllowName" type="text" readonly="readonly"  maxlength="64" value="${userEntity.name}">
								</div>
								<label class="col-md-2 control-label no-padding-right">值长（值班长）</label>
								<div class="col-md-4">
												<input class="col-md-12" id="runManagerId"  type="hidden" placeholder="" maxlength="64" >
												<input class="col-md-12" id="runManagerName"  type="text" readonly="readonly"  maxlength="64" >
								</div>
							</div>
							
				</form>
			
				<div class="widget-main no-padding">
				<h5 class="table header smaller">5.拆除安全措施</h5>
	 			<table id="workSafe-table-sqsy" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>需拆除的安全措施（工作负责人填写）</th>
	                                <th>执行情况</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				
	 		</div>
			</div>
			</div>
    		<div style="margin-right:100px;">
    			
    			<button id="agreeSqsyXk" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:10px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				同意
    			</button>
    		</div>
    		<div class="form-group" style="height:50px;">
    		</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var taskIdSqsy=$("#taskIdSqsy").val();
					var procInstIdSqsy=$("#procInstIdSqsy").val();  
					var workid=${workTicketEntity.id};
					$('#workTicketFormSqsyXk').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							//添加按钮
							 url = format_url("/workTicketWindAuto/agreeSqsyXk?taskId="+taskIdSqsy+"&procInstId="+procInstIdSqsy);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#workTicketFormSqsyXk").serializeObject();
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
									window.scrollTo(0,0);
									$("#page-container").load(format_url('/todoTask/list/1/10'));
								}
							});
						}
					});
					$("#agreeSqsyXk").on("click", function(){
						
						var workId=${workTicketEntity.id};
						$.ajax({
							url : format_url("/workTicketWindAuto/isExecuteSqsy/"+workId),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							success: function(result){
								if(result.result=="success"){
									workticketDialog = new A.dialog({
										title:"审批确认",
										url:format_url("/workTicketWindAuto/approveSubmit"),
										height:600,
										width:800
									}).render();
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert('操作失败');
							}
						});
    				});
					
					
					$('#btnBackSqsyFh').on('click',function(){
						var id = $("#taskIdSqsy").val();//任务流程实例ID
						var procInstId = $("#procInstIdSqsy").val();//流程实例ID
						var procDefId = $("#procDefId").val();////流程定义ID
						var formURL="/workTicketWindAuto/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + id + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstId+
									"&procDefId="+procDefId+"&formURL="+formURL)
						});
						
					});
					
					
//下面是安全措施的列表 1
					
					var workSafeSqsyDatatables="";
					var conditionsSqsy=[];
					workSafeSqsyDatatables = new A.datatables({
						render: '#workSafe-table-sqsy',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSqsy.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:7//申请试运
		            				});
					            	conditionsSqsy.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:${workTicketEntity.id}
		            				});
					            	d.conditions = conditionsSqsy;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 280,
											title: "安全措施编辑",
											url:format_url("/workSafe/getApproveEdit/"+ id+"/"+7),
											closed: function(){
												workSafeSqsyDatatables.draw(false);
											}
										}).render();
									}
								}
							}]
						}
					}).render();
					
						
				});
			});
			
			function goBackToSubmitPerson(id,selectUser,approveIdea){//回调函数
				$("#selectUser").val(selectUser);
				$("#approveIdea").val(approveIdea);
				$("#workTicketFormSqsyXk").submit();
				
			}
        </script>
    </body>
</html>