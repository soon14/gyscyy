<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"   style="margin-right:200px;margin-top:30px;" id="approveForm">
				<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="20" value="${ entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目类别
				    </label>
				    <div class="col-md-4">
				     	<input class="col-md-12" id="category" name="category" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.categoryName }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  年份
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="yearNum"  type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.yearNumString }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 股权结构(%)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="stockStruct" name="stockStruct" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.stockStruct }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设规模
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="buildSize" name="buildSize" type="text" placeholder="" maxlength="64" readonly="readonly"  value="${ entity.buildSize }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设规模量纲
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="buildSizeUnit" name="buildSizeUnit" type="text" placeholder="" maxlength="64" readonly="readonly" value="${ entity.buildSizeUnit }">
				    </div>
				</div>
			   <div class="form-group">
				    	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设阶段
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="buildStage" name="buildStage" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.buildStageName }">
					</div>
					  <label class="col-md-2 control-label no-padding-right">
						填写人
					  </label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" readonly="readonly" maxlength="64" value="${userName }">
                	</div>
				</div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 年度完成主要工程节点
				    </label>
				    <div class="col-md-10">
					    <textarea name="content" placeholder="" style="height:100px; resize:none;" class="col-md-12"  readonly="readonly" maxlength="512">${ entity.content }</textarea>
				    </div>
			     </div>
			     <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>小计
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalMoney" name="totalMoney" type="text" placeholder="" maxlength="64" readonly="readonly" value="${ entity.totalMoney }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 自有金额
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="ownMoney" name="ownMoney" type="text" placeholder="" readonly="readonly"  maxlength="64" value="${ entity.ownMoney }">
				    </div>	
				</div>
			   <div class="form-group">
				   
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>贷款
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="loanMoney" name="loanMoney" type="text" placeholder="" readonly="readonly"  maxlength="64" value="${ entity.loanMoney }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    其他
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="other" name="other" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.other }">
				    </div>	
				</div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly"   maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
			  <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
				  		附件
                   </label>
				   <div class="col-md-10" id="divfile">
                   </div>
               </div>
			</form>
        </div>
       	</div>
       	
       	<div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;" id="button" >
         		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
    			
         		<c:if test="${nodeList.authFactorCode=='planbhBtn'}">
        		<button  id="investmentPlan/auditReject"  code="planbhBtn" title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='plantgBtn'}">
        		<button  id="investmentPlan/auditPass" code="plantgBtn"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处审查
		    	</button>
    			</c:if>
         		
    			<c:if test="${nodeList.authFactorCode=='planoperationBtn'}">
        		<button  id="investmentPlan/auditPass"  code="planoperationBtn"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处执行
		    	</button>
    			</c:if>

		    	<c:if test="${nodeList.authFactorCode=='submit'}">
		    	<button  id="investmentPlan/overhaulPlanEdit" code="submit"   title="" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok"></i>
		    				提交
		    	</button>
		    	</c:if>

		    	
		        <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
		    	<button  id="investmentPlan/againSubmit"  code="ztjBtn" title="" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再提交
		    	</button>
		    	</c:if>
		        <c:if test="${nodeList.authFactorCode=='qxBtn'}">
		    	<button  id="investmentPlan/cancel"  code="qxBtn" title="" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    			<i class="glyphicon glyphicon-remove-circle"></i>
		    				取消流程
		    	</button>
		    	</c:if>

 		    	</c:forEach> 
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			
        			//附件上传
						var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${entity.fileId}'==""?[]:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
        			
						//全部按钮
						$("#button button").on("click", function(e){
							var id=$("#id").val(); 
							var obj = $("#approveForm").serializeObject();
								obj.taskId=$("#taskId").val();
								obj.procInstId=$("#procInstId").val();
							var url=$(e.target).attr("id");
							var codeBtn=$(e.target).attr("code");
							obj.auditBtn = codeBtn;
							
							//驳回不弹出框
							if(url=="investmentPlan/auditPass"  ){
								
								if('${entity.approveStatus}' =='2'){
									var urls = "investmentPlan/overhaulPlanAudit"
										listFormDialog = new A.dialog({
				    						width: 850,
				    						height: 300,
				    						title: "经营处执行通过",
				    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
				    						closed: function(){
				    						}	
				    					}).render(); 
								}else{
									var urls = "investmentPlan/overhaulPlanAudit"
									listFormDialog = new A.dialog({
			    						width: 850,
			    						height: 600,
			    						title: "审批通过",
			    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
			    						closed: function(){
			    						}	
			    					}).render(); 
								}
							
							}
						
							if(url=="investmentPlan/auditReject" ){
								var urls = "investmentPlan/overhaulPlanAuditMsg"
									listFormDialog = new A.dialog({
			    						width: 850,
			    						height: 300,
			    						title: "审批不通过",
			    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
			    						closed: function(){
			    							
			    						}	
			    				}).render();
							}
							if(url == "investmentPlan/cancel"){
								$.ajax({
									url:format_url("/investmentPlan/cancel"),
									contentType: "application/json",
									dataType: 'JSON',
									type: 'POST',
									data:JSON.stringify(obj),
									success: function(result){
										if(result.result=="success"){
											alert("取消成功");
											$("#page-container").load(format_url('/todoTask/list/1/10'));
										}else{
											alert(result.errorMsg);
										}
									}
									
								})
							}
							
							if(url == "investmentPlan/againSubmit"){
								listFormDialog = new A.dialog({
									width: 850,
									height: 481,
									title: "投资管理处提交",
									url:format_url('/investmentPlan/sureSubmit?codeBtn='+codeBtn),
									closed: function(resule){
										if(listFormDialog.resule){
											  obj.userList=listFormDialog.resule.join(",");
													$.ajax({
														url:format_url("/"+url),
			    										contentType : 'application/json',
			    										dataType : 'JSON',
			    										type : 'POST',
			    										data:JSON.stringify(obj),
			    										success: function(result){
			    											if(result.result=="success"){
			    												alert("提交成功");
			    												listFormDialog.close();
			    												$("#page-container").load(format_url('/todoTask/list/1/10'));
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