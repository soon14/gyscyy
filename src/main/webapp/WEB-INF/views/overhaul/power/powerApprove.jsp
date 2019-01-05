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
<div class="page-content">
	<div class="widget-main no-padding">
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息：</h5>
	<form class="form-horizontal" role="form"  style="margin-right:100px;" id="approveForm">
     <input id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			  <div class="col-md-12" style="margin-top:50px">
<!-- 				   <div class="form-group"> -->
<!-- 					    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						    <span style="color:red;"></span>申请编号 -->
<!-- 					    </label> -->
<!-- 					    <div class="col-md-4"> -->
<%-- 						    <input class="col-md-12" id="requestNumber" name="requestNumber" type="text" readOnly placeholder="" maxlength="64" value="${ entity.requestNumber }" readOnly> --%>
<!-- 						</div> -->
<!-- 					</div> -->
				   <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
						    <span style="color:red;"></span>设备编号
					    </label>
					    <div class="col-md-4">
						    <input class="col-md-12" id="equipNumber" name="equipNumber" type="text" readOnly placeholder="" maxlength="64" value="${ entity.equipNumber }" readOnly>
	
					    </div>	
					    <label class="col-md-2 control-label no-padding-right">
						    <span style="color:red;"></span>设备名称
					    </label>
					    <div class="col-md-4">
						    <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.equipName }">
						</div>
					</div>
				    <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
						    <span style="color:red;"></span>单位名称
					    </label>
					    <div class="col-md-4">
						    <input class="col-md-12" id="unitName" name="unitName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.unitName }">
					    </div>	
					    <label class="col-md-2 control-label no-padding-right">
						    <span style="color:red;"></span>申请时间
					    </label>
					    <div class="col-md-4">
						    <input class="col-md-12" id="requestDateString" name="requestDateString" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestDateString }">
						</div>
					 </div>
				     <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
						    申请人
					    </label>
					    <div class="col-md-4">
							 <input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestUserName }">
							
					    </div>	
					    <label class="col-md-2 control-label no-padding-right">
						    <span style="color:red;"></span>停送电方式
					    </label>
					    <div class="col-md-4">
	                    	<input class="col-md-12" id="powerStatusText" name="powerStatusText" type="text" placeholder="" maxlength="64" readOnly value="${ entity.powerStatusText }">
						</div>
					  </div>
				      <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
						    停送电内容
					    </label>
					    <div class="col-md-10">
						    <textarea name="powerDec" placeholder="" style="height:100px; resize:none;" class="col-md-12" readOnly maxlength="1024">${ entity.powerDec }</textarea>
					    </div>
				     </div>
    		
				      <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
						    备注
					    </label>
					    <div class="col-md-10">
						    <textarea name="remark" placeholder="" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
					    </div>
				      </div>
					   <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								附件
							</label>
							<div class="col-md-10">
								<div  id="divfile" >
							</div>
							</div>
					   </div>
				     </div>
			</form>
		</div>	
	</div>
	   <div class="col-xs-12">		     
		<div class="widget-main no-padding">
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >执行情况：</h5>				     
		<form class="form-horizontal" role="form"  style="margin-right:100px;" id="approveForm">
			<div class="" style="margin-top:50px">
			  	 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;"></span>执行结束时间
				    </label>
				    <div class="col-md-4">
                    	<input class="col-md-12" id="endDateString" name="endDateString" type="text" placeholder="" maxlength="64" readOnly value="${ entity.endDate}">
				    </div>	
    		     </div>
    		     
    		     <div  class="form-group">
    		      <label class="col-md-2 control-label no-padding-right">
					    执行人
				    </label>
				    <div class="col-md-4">
                    	<input class="col-md-12" id="excuteUserName" name="excuteUserName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.excuteUserName }">
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    监护人
				    </label>
				    <div class="col-md-4">
                    	<input class="col-md-12" id="costodyUserName" name="costodyUserName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.costodyUserName }">
				    </div>	
    		     </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    完成情况
				    </label>
				    <div class="col-md-10">
					    <textarea name="measur" placeholder="" style="height:100px; resize:none;" readOnly class="col-md-12" maxlength="1024">${ entity.measur }</textarea>
				    </div>
			     </div>
			      <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						结果附件
					</label>
					<div class="col-md-10">
						<div  id="resultdivfile" >
						</div>
					</div>
				</div>
              </div>
     	</form>
     	</div>
   	</div>
					
						
							
							
        <div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;" id="button" >
         		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
         		<c:if test="${nodeList.authFactorCode=='jxbzbh'}">
        		<button  id="power/reject1"  title="不通过"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='jxbztg'}">
        		<button  id="power/submitPersonAgree"  title="检修中心主任或副主任审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>

    			<c:if test="${nodeList.authFactorCode=='zzbh'}">
		    	<button  id="power/reject2"  title="不通过" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
		    	</c:if>

    			<c:if test="${nodeList.authFactorCode=='zztg'}">
        		<button  id="power/powerZztgAudit"  title="集控中心主任或副主任审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>
				<c:if test="${nodeList.authFactorCode=='leader'}">
        		<button  id="power/powerLeader"  title="总工、检检修分管主任审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='implement'}">
        		<button  id="power/powerMonitorAudit"  title="检修中心主任或副主任指派执行人"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>

		    	<c:if test="${nodeList.authFactorCode=='submit'}">
		    	<button  id="power/powerExecutedResult" title="执行人执行操作并填写执行结果" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok"></i>
		    				执行人执行操作并填写执行结果
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='cancel'}">
		    	<button id="power/cancel"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove"></i>
		    				取消
		    	</button>
		    	</c:if>
		    	<c:if test="${nodeList.authFactorCode=='submitAgain'}">
		    	<button  id="power/submitAgain" title="再次提交" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再次提交
		    	</button>
		    	</c:if>
 		    	</c:forEach> 
        	</div>
        </div>
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.attchmentIds},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					new A.uploaddropzone({
						render : "#resultdivfile",
						fileId:${entity.attchmentResultIds},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						autoDiscover:true,
						chargeUp:true,
					}).render('Other');
					//全部按钮
					$("#button button").on("click", function(e){
						var id=$("#id").val(); 
						var obj = $("#approveForm").serializeObject();
							obj.taskId=$("#taskId").val();
							obj.procInstId=$("#procInstId").val();
						var url=$(e.target).attr("id");
						var title=$(e.target).attr("title");
						//驳回不弹出框
						if(url=="power/reject1"||url=="power/reject2" ){
							var urls = "power/powerRejectAudit"
							listFormDialog = new A.dialog({
	    						width: 850,
	    						height: 400,
	    						title: title,
	    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id),
	    						closed: function(resule){
	    							if(listFormDialog.resule){
	    								obj.auditMsg =listFormDialog.resule; 
										  $.ajax({
												url : format_url("/"+url+"/"+id),
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'POST',
												data : JSON.stringify(obj),
												success: function(result){
													if(result.result=="success"){
														alert("审批成功");
														window.scrollTo(0,0);
														A.loadPage({
															render : '#page-container',
															url : format_url("/todoTask/list/1/10")
														});
													}else{
														alert(result.errorMsg);
													}
												},
												error:function(v,n){
													alert("审批失败");
												}
											});
									}
	    							
	    						}	
	    					}).render(); 
						}
						if(url=="power/submitPersonAgree" || url== "power/powerMonitorAudit"
								|| url=="power/powerExecutedResult"|| url=="power/powerZztgAudit"
								||url=="power/powerLeader"){
							listFormDialog = new A.dialog({
	    						width: 850,
	    						height: 600,
	    						title: title,
	    						url:format_url("/"+url+"/"+$("#taskId").val()+"/"+id),
	    						closed: function(){
	    						}	
	    					}).render(); 
						} 
						if(url=="power/cancel"){
							$.ajax({
								url : format_url("/"+url+"/"+id),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("审批成功");
										window.scrollTo(0,0);
										A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/1/10")
										});
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("审批失败");
								}
							});
						}
						
						if(url =="power/submitAgain"){
							var urls="power/sureSubmitPersonAgain";
							listFormDialog = new A.dialog({
	    						width: 850,
	    						height: 600,
	    						title: title,
	    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id),
	    						closed: function(resule){
	    							if(listFormDialog.resule){
										  obj.userList=listFormDialog.resule.join(",");
												$.ajax({
													url : format_url("/"+url+"/"+id),
		    										contentType : 'application/json',
		    										dataType : 'JSON',
		    										type : 'POST',
		    										data:JSON.stringify(obj),
		    										success: function(result){
		    											if(result.result=="success"){
		    												alert("再次提交成功");
		    												window.scrollTo(0,0);
		    												A.loadPage({
		    													render : '#page-container',
		    													url : format_url("/todoTask/list/1/10")
		    												});
		    											}else{
		    												alert(result.errorMsg);
		    											}
		    										},
		    										error:function(v,n){
		    											alert('提交失败');
		    										}
											});
									}
	    							
	    						}	
	    					}).render(); 
						}
    				});
				});
			});
        </script>
    </body>
</html>