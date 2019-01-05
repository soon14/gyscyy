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
	<div class="page-content" >
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editgoodsForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
						<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
					  <input class="col-md-12" id="createUserId" name="createUserId" type="hidden" readonly="readonly" placeholder="" maxlength="64" value="${entity.createUserId }" >
	                   <input class="col-md-12" id="userName" name="userName" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>设备类别
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>物资采购计划类别
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="planType" name="planType" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.planTypeName }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>型号规格
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="number" placeholder="" readonly="readonly" maxlength="64" value="${ entity.amount }">
					</div>
				</div>
				<div class="form-group">
			      	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
					</label>
					<div class="col-md-4">
	                  <input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
                	</div>
				     <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计单价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgePrice" name="budgePrice" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.budgePrice }" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计总价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.totalPrice }" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计采购时间
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.buyTimeString }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 所需项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.projectName }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划处室
					</label>
					<div class="col-md-4">
	                 <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${unitEntity.name}">
                	</div>
				</div>
				 <div class="form-group">
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyName" name="companyName" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
				</div>
				
				 <div  id="jhgl" style="text-align: left;color: #478fca;font-weight: lighter;">
                </div>	
                <hr> 
			</form>
    		<div style="margin-right:100px;">

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
        			
					var id = $('#id').val();
					if('${entity.planType}'==1){
						$("#jhgl").append(' <div class="col-xs-12" style="margin-top:0px;">'	
						     +'  <div class="widget-main no-padding">'
						     +' <form id="goodsRelation-table-form" class="form-horizontal" >'
						     +'		        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">计划关联</h5>	'
						     +' <table id="goodsRelation_table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px; color: black;">'
						     +' 	<thead>'
						     +' 			<tr>'
						     +'                 <th>项目名称</th>'
						     +'                 <th>设备名称</th>'
						     +'                 <th>设备类别</th>'
						     +'                 <th>型号规格</th>'
						     +'                 <th>数量</th>'
						     +'                 <th>计数单位</th>'
						     +'                 <th>预算单价(万元)</th>'
						     +'                 <th>预算总价(万元)</th>'
						     +' 			</tr>'
						     +' 		</thead>'
						     +' 	</table>'
						     +' </form>'
						     +' </div>'
						     +' </div>');
						
						var goodsRelationDatatables;
						var conditions=[];
						 goodsRelationDatatables = new A.datatables({
							render: '#goodsRelation_table',
							options: {
								    serverSide: false,
							        multiple : true,
							        checked:false,
									bInfo:false,
									paging:false,
									ordering:true,
									optWidth: 120,
									order:[["1",'asc']],
							
							columns: [
							          {data: "projectName",width: "18%",orderable: true},
							          {data: "equipName",width: "18%",orderable: false},
							          {data: "equipType",width: "10%",orderable: true}, 
							          {data: "specification",width: "10%",orderable: true}, 
							          {data: "amount",width: "10%",orderable: true},
							          {data: "unit",width: "10%",orderable: true},
							          {data: "budgePrice",width: "10%",orderable: true}, 
							          {data: "totalPrice",width: "10%",orderable: true}
							         
							          ],
		
							}
						}).render();
						 
					 		function initDataTable(){
								var params = {};
								params.length = 10;
								params.start = 0;
								params.draw = 0;
								params.conditions = [];
// 								params.goodsId = id;
								$.ajax({
// 									"url": format_url("/goodsRelation/search"),
									"url": format_url("/goodsRelation/editData/list/"+id),
									contentType: "application/json",
									dataType: 'JSON',
									type: 'POST',
									data : JSON.stringify(params),
									success: function(result){
										goodsRelationDatatables.addRows(result.data);
									}
									
								});
							}; 
							
							initDataTable();  
						
					}
        			
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
	        						url:format_url("/goodsPurchase/getAddQf?electricId="+electricId+"&taskId="+taskId ),
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
        						url:format_url("/goodsPurchase/getAddHqf?electricId="+electricId+"&taskId="+taskId ),
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
        						url:format_url("/goodsPurchase/getAddxfjhrsh?electricId="+electricId+"&taskId="+taskId ),
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
        						url:format_url("/goodsPurchase/getAddPz?electricId="+electricId+"&taskId="+taskId ),
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
	        						url:format_url("/goodsPurchase/getAddAqzj?electricId="+electricId+"&taskId="+taskId ),
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
								url : format_url("/goodsPurchase/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
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
        						url:format_url("/goodsPurchase/sureSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/goodsPurchase/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
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
						goodsPurchaseDatatables.draw(false);
					}
				});
				
			}
        </script>
    </body>
</html>