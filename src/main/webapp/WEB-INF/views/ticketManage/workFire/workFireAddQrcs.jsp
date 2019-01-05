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
					<li class="active">执行人确认安全措施</li>
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
			<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormPz">
											<input type="hidden" id="twoTotal"/>			
	
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">氧气浓度合格。使用仪器</label>
				<div class="col-md-10">
					<textarea id="otherInstrument" name="otherInstrument" placeholder="请输入使用仪器" style="height:60px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
				<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">可燃性、易爆气体含量或粉尘浓度合格。使用仪器</h5>
	 			<table id="worktest-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>测试时间</th>
	                                <th>氧气测定值</th>
	                                <th>有毒有害气体测定值</th>
	                                <th>测试位置</th>
	                                <th>测量人</th>	                                
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>			
					</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">动火执行人签名</label>
					<div class="col-md-4">
							<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${workIdZjXk}">
							<input class="col-md-12" id="dutyMonitorName" name="dutyMonitorName" type="text" readonly="readonly" maxlength="64" value="${userEntity.name}">
					</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>审批意见</label>
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
				</div>
			</div>
    		<div style="margin-right:100px;">
    			<button id="disAgreePz" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-remove-circle"></i>
    				驳回
    			</button>
    			<button id="agreePz" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-ok"></i>
    				同意
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','duallistbox'], function(A){
					var workid='${workIdZjXk}';
					var taskIdZjXk='${taskIdZjXk}';
					var procInstIdZjXk='${procInstIdZjXk}';
					var procDefIdZjXk='${procDefIdZjXk}';
					var electricId='${electricId}';
					//安全措施 开始
					//下面是安全措施的列表 1
					var workTestDatatables="";
					var conditions=[];
					workTestDatatables = new A.datatables({
						render: '#worktest-table',
						options: {
					        "ajax": {
					            "url": format_url("/workTicketTest/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {					            	
					            	conditions.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:workid
		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "testDate",width: "10%",orderable: true},
							          {data: "testValue",width: "20%",orderable: true},
							          {data: "testToxicGas",width: "20%",orderable: true},
							          {data: "testAddress",width: "20%",orderable: true},
							          {data: "testPerson",width: "20%",orderable: true}
							          ],
							          fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										  $(".dataTables_info").html(sPre);//下脚标
												$("#twoTotal").val(iTotal);
											},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var twoTotal=Number($("#twoTotal").val())+1;
            							workTestDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workTicketTest/getAdd?electricId="+workid+"&total="+twoTotal),
                        						closed: function(){
                        							workTestDatatables.draw(false);
                        						}	
                        					}).render();
            							}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workTestDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "安全措施编辑",
											url:format_url("/workTicketTest/getEdit/"+ id),
											closed: function(){
												workTestDatatables.draw(false);
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
										var url =format_url('/workTicketTest/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workTestDatatables.draw(false);
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
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');										
					$("#agreePz").on("click", function(){
						var twoTotal=$("#twoTotal").val();
						if(!$("#otherInstrument").val()){
							alert("氧气浓度合格。使用仪器,未填写");
							return;
						}
						if(twoTotal=="0"){
							alert("可燃性、易爆气体含量或粉尘浓度合格。使用仪器,未填写");
							return;
						}
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						var selectUser=$('[name="duallistbox_demo1[]"]').val();
						if(selectUser==null||selectUser==""){
							alert('请选择下一步审批人!');
							return;
						}
						$('#addFormPz').validate({
							debug:true,
							rules:  {
								approveStarttime:{required:true,maxlength:64},
								approveEndtime:{required:true,maxlength:64},
								approveIdea:{required:true,maxlength:64},
								},
							submitHandler: function (form) {
								var url = format_url("/workFire/agreeQrcs?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&selectUser="+selectUser);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormPz").serializeObject();
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
						$("#addFormPz").submit();
    				});
					
					$("#disAgreePz").on("click", function(){
						var taskId=$("#taskId").val();
						var procInstId=$("#procInstId").val(); 
						$('#addFormPz').validate({
							debug:true,
							rules:  {},
							submitHandler: function (form) {
								var url = format_url("/workFire/disAgreeQrcs?taskId="+taskIdZjXk+"&procInstId="+procInstIdZjXk+"&selectUser="+"");
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#addFormPz").serializeObject();
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
						
						$("#addFormPz").submit();
						
					});
					$('#btnBackZjXk').on('click',function(){
						var formURL="/workTicketFire/approve/"+ workid;
						A.loadPage({
							render : '#page-container',
							url : format_url("/todoTask/detail?id=" + taskIdZjXk + "&currentPage="+ 1 
									+ "&pageSize=" + 10+"&procInstId="+procInstIdZjXk+
									"&procDefId="+procDefIdZjXk+"&formURL="+formURL)
						});
					});
				});
			});
        </script>
    </body>
</html>