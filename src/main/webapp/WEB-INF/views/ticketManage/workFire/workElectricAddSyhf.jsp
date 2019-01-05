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
					<button id="btnBackSyhfFh" class="btn btn-xs btn-primary">
						<i class="glyphicon glyphicon-share-alt"></i>
						返回
					</button>
				</div>	
				
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormSyhf">
		        <input type="hidden" id="id" name="id" value="${workElectricEntity.id}" />
		         <input type="hidden" id="taskIdSyhf"  value="${taskIdSyhf}" />
		        <input type="hidden" id="procInstIdSyhf"  value="${procInstIdSyhf}" />
		        <input type="hidden" id="procDefId"  value="${procDefId}" />
		        <input type="hidden" id="selectUser" name="selectUser" />
		        <input type="hidden" id="oneTotal"/>
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
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票交回，所列安全措施已拆除，可以试运行。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">日期：</label>
									<div class="col-md-4">
												<input class="col-md-12" id="stopSureDateSqsy" name="stopSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${syhfDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="stopPicId" name="stopPicId" type="hidden" readonly="readonly" maxlength="64" value="${userEntity.id}">
											<input class="col-md-12" id="stopPicName" name="stopPicName" type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作许可人</label>
									<div class="col-md-4">
												<input class="col-md-12" id="stopAllowId"  type="hidden" placeholder="" maxlength="64" >
												<input class="col-md-12" id="stopAllowName"  type="text" readonly="readonly"  maxlength="64" >
								</div>
								<label class="col-md-2 control-label no-padding-right">值长（值班长）</label>
								<div class="col-md-4">
												<input class="col-md-12" id="stopManagerId"  type="hidden" placeholder="" maxlength="64" >
												<input class="col-md-12" id="stopManagerName"  type="text" readonly="readonly"  maxlength="64" >
								</div>
							</div>
							
				</form>
			
				<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue" >恢复到原检修状态的安全措施</h5>
	 			<table id="workSafe-table-sqsy" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>需恢复到原检修状态的安全措施</th>
	                                <th>执行情况</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				
	 		</div>
			</div>
			</div>
    		<div style="margin-right:100px;">
    			<button id="sureSyhf" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				确定
    			</button>
    		</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var taskIdSyhf = $("#taskIdSyhf").val();//任务流程实例ID
					var procInstIdSyhf = $("#procInstIdSyhf").val();//流程实例ID
					var workid=${workTicketEntity.id};
					var eleId=${electricId};
					$('#workTicketFormSyhf').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							//添加按钮
							url = format_url("/workElectric/sureSyhf?eleId="+eleId+"&taskId="+taskIdSyhf+"&procInstId="+procInstIdSyhf);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#workTicketFormSyhf").serializeObject();
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
					$("#sureSyhf").on("click", function(){
						workticketDialog = new A.dialog({
							title:"提交确认",
							url:format_url("/workTicket/sureSubmit"),
							height:400,
							width:500
						}).render();
						
    				});
					
					
					$('#btnBackSyhfFh').on('click',function(){
						var id = $("#taskIdSyhf").val();//任务流程实例ID
						var procInstId = $("#procInstIdSyhf").val();//流程实例ID
						var procDefId = $("#procDefId").val();////流程定义ID
						var formURL="/workTicket/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + id + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstId+
									"&procDefId="+procDefId+"&formURL="+formURL)
						});
					});
					
					
//下面是安全措施的列表8
					
					var workSafeSyhfDatatables="";
					var conditionsSyhf=[];
					workSafeSyhfDatatables = new A.datatables({
						render: '#workSafe-table-sqsy',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSyhf.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:8//试运恢复
		            				});
					            	conditionsSyhf.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:${workTicketEntity.id}
		            				});
					            	d.conditions = conditionsSyhf;
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
							fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										  $(".dataTables_info").html(sPre);//下脚标
												$("#oneTotal").val(iTotal);
											},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var oneTotal=Number($("#oneTotal").val())+1;
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 280,
                    						title: "安全措施新增",
                    						url:format_url("/workSafe/getAdd?flag="+8+"&workId="+workid+"&total="+oneTotal ),
                    						closed: function(){
                    							workSafeSyhfDatatables.draw(false);
                    						}	
                    					}).render();
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 280,
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+8),
											closed: function(){
												workSafeSyhfDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeSyhfDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					
						
				});
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				$("#selectUser").val(selectUser);
				$("#workTicketFormSyhf").submit();
				
			}
        </script>
    </body>
</html>