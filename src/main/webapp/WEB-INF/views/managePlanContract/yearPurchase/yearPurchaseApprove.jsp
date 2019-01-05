<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    	<style>
			.page-content .tabbable{height:960px}
			.page-content .tab-content{height:100%}
		</style>
    </head>
	<body>

		<div class="page-content" id="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			    <input class="col-md-12" id="companyUnit" name="companyUnit" type="hidden"  value="${entity.companyUnit }" >
			    <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="hidden"  value="${entity.dutyUnit }" >
			   <div class="form-group">
				    	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>采购名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    采购计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.projectName }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 设备类别
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  规格型号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.amount }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预算单价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgetPrice" name="budgetPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.budgetPrice }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预算总价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.totalPrice }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预计采购时间
				    </label>
				    <div class="col-md-4">
				        <input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.buyTimeString }">
					</div>
				</div>
				
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="hidden" readonly="readonly" placeholder="" maxlength="64" value="${entity.createUserId }" >
	                   <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
                    </div>
    		     </div>
    		      <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任处室
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${unitEntity.name}">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyUnit" name="companyUnit" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12"  readonly="readonly" maxlength="128">${ entity.remark }</textarea>
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
        
        <div style="margin-right:100px;margin-top: 20px;">
					    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
					    <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
						<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				提交
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zfBtn'}">
						<button id="qxBtn" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				废票
		    			</button>
		    			</c:if>		    			
					    <c:if test="${nodeList.authFactorCode=='synmangeBtn'}">
						<button id="qfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-pencil"></i>
		    				综合管理处审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='technologyBtn'}">
						<button id="xffzrshBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				生产技术处审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='planBtn'}">
		    			<button id="ajfzrshBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='leaderBtn'}">
						<button id="ldshBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				单位领导审核
		    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='planoperationBtn'}">
		    			<button id="xkshBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				计划经营处执行
		    			</button>
		    			</c:if>
	    			</c:forEach>	    			
	        	</div>
		<script type="text/javascript">
			jQuery(function($) {
				
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			
        			//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						chargeUp:true
					}).render();
        			
        			
					   var taskId=$("#taskId").val();
					   var procInstId=$("#procInstId").val();
					   var procDefId=$("#procDefId").val();
					   
					   var electricId='${entity.id}';
					   //页面下方的签发按钮1
						$('#qfBtn').on('click',function(){
								workSafeOneDialog = new A.dialog({
	        						width: 1000,
	        						height: 430,
	        						title: "综合管理处审核",
	        						url:format_url("/yearPurchase/getAddQf?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
							
						});
					   
						//页面下方的会签发人审核按钮1
						$('#xffzrshBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "生产技术处审核",
        						url:format_url("/yearPurchase/getAddHqf?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
        			
						//页面下方的负责人审核按钮2
						$('#ajfzrshBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "计划经营处审核",
        						url:format_url("/yearPurchase/getAddxfjhrsh?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
        			
						//页面下方的安监部门负责人
						$('#ldshBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 450,
        						title: "单位领导审核",
        						url:format_url("/yearPurchase/getAddPz?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面安全总监
						$('#xkshBtn').on('click',function(){
									
								workSafeOneDialog = new A.dialog({
	        						width: 900,
	        						height: 400,
	        						title: "计划经营处执行",
	        						url:format_url("/yearPurchase/getAddAqzj?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();

						});
						
        			
        			
						//废票
						$('#qxBtn').on('click',function(){
							var obj = $("#editForm").serializeObject();
							obj.id='${entity.id}';
							obj.buyTime=$("#buyTime").val()+"-01 00:00:00";
							
							$.ajax({
								url : format_url("/yearPurchase/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('废票成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('废票失败');
								}
							});
						});
						
						//再提交
						$('#ztjBtn').on('click',function(){
							var id='${entity.id}';
							workSafeOneDialog = new A.dialog({
        						width: 500,
        						height: 500,
        						title: "再提交",
        						url:format_url("/yearPurchase/sureSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/yearPurchase/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							window.scrollTo(0,0);
							$("#page-container").load(format_url('/todoTask/list/1/10'));
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
						yearPurchaseDatatables.draw(false);
					}
				});
				
			}
        </script>
    </body>
</html>