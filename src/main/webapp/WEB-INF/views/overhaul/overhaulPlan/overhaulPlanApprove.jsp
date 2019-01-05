<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
<%@ page import="com.aptech.business.component.dictionary.OverhaulPlanStatusEnum"%>
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>

	<div class="page-content">
		<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"   id="approveForm">
				     <input id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
				    <div class="col-md-12" style="margin-top:20px">
			       <div class="form-group">
			       		<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>检修计划编号
						</label>
						<div class="col-md-4">
               			<input class="col-md-12" id="planNumber" readOnly type="text" value="${entity.planNumber}">
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申报日期
					    </label>
					    <div class="col-md-4">
							<input  class="col-md-12"  type="text"  value="${ entity.planYearString }" readOnly/>
	                    </div>
					    
	               </div>
		           <div class="form-group">
		           						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>单位名称
						</label>
						<div class="col-md-4">
							<input class="col-md-12"  type="text"  value="${ entity.unitName }" readOnly />

	                	</div>
	                	<label class="col-md-2 control-label no-padding-right">
					      修改项目
						</label>
						   <div class="col-md-4">
		                       <input class="col-md-12" id="updateProject" name="updateProject"  readonly="readonly" type="text" value="${entity.updateProject}" placeholder="" >
		                   </div>
	               </div>
	               <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							检修计划名称
						</label>
						<div class="col-md-4">
						<input class="col-md-12" id="planName" name="planName"  readonly="readonly" type="text" value="${entity.planName}" placeholder="" >
						</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>计划填报人
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12"  type="text"  value="${ entity.dutyUserName }" readOnly />
	                    </div>
	               </div>
	    			
		           <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
							<textarea  placeholder="" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
						</div>
	               </div>
			       <div class="form-group form-horizontal">
						<label class="col-md-1 control-label no-padding-right">
							附件
						</label>
						<div class="col-xs-10" id="divfile">
						</div>
						</div>
	               </div>
	               </div>
	            </form>
	       </div>
	    <div class="col-xs-12">		     
	    			
	    
		<div class="widget-main no-padding">
			<form id="overhaulProject" class="form-horizontal"  onsubmit="return false;">
				<div class="col-md-12" >
				<h5 class="table-title-withoutbtn header smaller lighter blue " style="margin-bottom:0px;">检修项目</h5>
				<table id="overhaulProject-table" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;"></span>项目名称</th>
                       		<th><span style="color:red;"></span>项目明细</th>
                       		<th>列入原因</th>
                       		<th>方案措施</th>
                       		<th><span style="color:red;"></span>开工时间</th>
                       		<th><span style="color:red;"></span>完成时间</th>
                       		<th>计划总投资(元)</th>
						</tr>
					</thead>
				</table>
				</div>
			</form>
		</div>
		
	      <div class="widget-main no-padding">
		   <div class="col-md-12" >
		    <h5 class="table-title-withoutbtn header smaller lighter blue " style="margin-bottom:0px;">停送电管理</h5>
			<div class="widget-main no-padding">
				 <table id="power_table" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							  <th>序号</th>
							  <th>申请编号</th>
                              <th>申请单位</th>
                              <th>停送电联系人</th>
                              <th>工作任务</th>
                              <th>是否停送电</th>
                              <th>申请停电时间</th>
                              <th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		 </div>
		 </div>
		</div>
</div>
        <div class="col-md-12">
    		<div style="margin-right:100px;margin-top: 20px;" id="button" >
         		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs"> 
         		<c:if test="${nodeList.authFactorCode=='zrbhBtn'}">
        		<button  id="overhaulPlan/auditReject"  code="zrbhBtn" title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='zrtgBtn'}">
        		<button  id="overhaulPlan/auditPass" code="zrtgBtn"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>
         		<c:if test="${nodeList.authFactorCode=='reject'}">
        		<button  id="overhaulPlan/auditReject" code="reject"  title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='pass'}">
        		<button  id="overhaulPlan/zgtgAuditPass" code="pass"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>
<%--          		<c:if test="${nodeList.authFactorCode=='reject'}"> --%>
<!--         		<button  id="overhaulPlan/auditReject" code="reject"  title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;"> -->
<!-- 		    				<i class="glyphicon glyphicon-remove-circle"></i> -->
<!-- 		    				驳回 -->
<!-- 		    	</button> -->
<%--     			</c:if> --%>
<%--     			<c:if test="${nodeList.authFactorCode=='pass'}"> --%>
<!--         		<button  id="overhaulPlan/auditPass" code="pass"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 		    				<i class="ace-icon fa fa-floppy-o"></i> -->
<!-- 		    				通过 -->
<!-- 		    	</button> -->
<%--     			</c:if> --%>
    			
         		<c:if test="${nodeList.authFactorCode=='sjzrbhBtn'}">
        		<button  id="overhaulPlan/auditReject"  code="sjzrbhBtn"  title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='sjzrtgBtn'}">
        		<button  id="overhaulPlan/auditPass"   code="sjzrtgBtn"   title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				通过
		    	</button>
    			</c:if>
         		<c:if test="${nodeList.authFactorCode=='ldbhBtn'}">
        		<button  id="overhaulPlan/auditReject"  code="ldbhBtn"  title=""  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				驳回
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='ldtgBtn'}">
        		<button  id="overhaulPlan/auditPass"  code="ldtgBtn"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				同意执行
		    	</button>
    			</c:if>
    			<c:if test="${nodeList.authFactorCode=='ldbtgBtn'}">
        		<button  id="overhaulPlan/unAuditPass"  code="ldbtgBtn"  title=""  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				不同意执行
		    	</button>
    			</c:if>

		    	<c:if test="${nodeList.authFactorCode=='submit'}">
		    	<button  id="overhaulPlan/overhaulPlanEdit" code="submit"   title="" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok"></i>
		    				提交
		    	</button>
		    	</c:if>
		        <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
		    	<button  id="overhaulPlan/againSubmit"  code="ztjBtn" title="" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再提交
		    	</button>
		    	</c:if>
		        <c:if test="${nodeList.authFactorCode=='qxBtn'}">
		    	<button  id="overhaulPlan/cancel"  code="qxBtn" title="" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    			<i class="glyphicon glyphicon-remove-circle"></i>
		    				取消流程
		    	</button>
		    	</c:if>

 		    	</c:forEach> 
        	</div>
        </div>
		<script type="text/javascript">
		var listFormDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					var overhaulPlanId=$('#id').val();
					
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.attachementIds},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					$("#cancelBtn").on("click", function(){
						 $("#page-container").load(format_url('/overhaulPlan/index'));
    				});
					var conditions=[];
					overhaulProjectDatatables = new A.datatables({
						render: '#overhaulProject-table',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [
							 {data:"id", visible:false,orderable:false},
						     {data: null,width: "5%",orderable: false},
							 {data: "projectName",width: "15%",orderable: false}, 
							 {data: "projecDetail",width: "15%",orderable: false}, 
							 {data: "projectReason",width: "15%",orderable: false}, 
							 {data: "measure",width: "15%",orderable: false}, 
							 {data: "startDateString",name:"startDate",width: "10%",orderable: false},
							 {data: "endDateString",name:"endDate",width: "10%",orderable: false},
							 {data: "totalMoney",width: "10%",orderable: false}
							 ],
							optWidth:50,
							toolbars: [
							   ],
						}
					}).render();
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualPlanId =${entity.id} ;
						$.ajax({
							url: format_url("/overhaulProject/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulProjectDatatables.addRows(result.data);
							}
							
						})
					};
					initDataTable();
					
					
					//停送电管理
					var  powerDatatables = new A.datatables({
							render: '#power_table',
							options: {
						        "ajax": {
						            "url": format_url("/power/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "async":false, 
						            "data": function (d) {
						             	conditions.push({
					     					field: 'C_OVERHAUL_PLAN_ID',
					     					fieldType:'INT',
					     					matchType:'EQ',
					     					value:overhaulPlanId
					     				});
						            	d.conditions = conditions;
						                return JSON.stringify(d);
						              }
						        },
						        multiple : true,
								ordering: true,
								checked: false,
								optWidth: 80,
								columns: [{data:"id", visible:false,orderable:false},
								          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
							                   var startIndex = meta.settings._iDisplayStart;  
							                   row.start=startIndex + meta.row;
							                   return startIndex + meta.row + 1;  
							               	} },
								          {data: "requestNumber",visible:false,width: "120px",orderable: false},
								          {data: "unitName",width: "auto",orderable: true}, 
								          {data: "requestUserName",width: "auto",orderable: true},
								          {data: "powerDec",width: "auto",orderable: true},
								          {data: "isPowerText",width: "auto",name:"isPower",orderable: true},
								          {data: "requestDateString",width: "auto",name:"requestDate",orderable: true},
								          ],
								          btns: [{
												id: "detail",
												label:"查看",
												icon: "fa fa-binoculars bigger-130",
												className: "blue ",
												events:{
													click: function(event, nRow, nData){
														var id = nData.id;
														listFormDialog = new A.dialog({
					                						width: 850,
					                						height: 870,
					                						title: "停送电查看",
					                						url:format_url('/power/getDetail/' + id),
					                						closed: function(){
					                							powerDatatables.draw(false);
					                						}	
					                					}).render()
//			 											A.loadPage({
//			 												render : '#page-container',
//			 												url : format_url('/power/getDetail/' + id)
//			 											});
													}
												}
											}
									]
							}
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
						if(url=="overhaulPlan/auditPass"  ){
							
							if('${entity.approveStatus}' == <%=OverhaulPlanStatusEnum.TECHNOLOGYLEADER.getCode() %>){
								var urls = "overhaulPlan/overhaulPlanAudit"
									listFormDialog = new A.dialog({
			    						width: 850,
			    						height: 300,
			    						title: "审批通过",
			    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
			    						closed: function(){
			    						}	
			    					}).render(); 
							}else if('${entity.approveStatus}' == <%=OverhaulPlanStatusEnum.TECHNOLOGYDIRECTOR.getCode() %>){
								var urls = "overhaulPlan/overhaulPlanAudit"
									listFormDialog = new A.dialog({
			    						width: 850,
			    						height: 450,
			    						title: "审批通过",
			    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
			    						closed: function(){
			    						}	
			    					}).render(); 
							}else{  //检修主任
								var urls = "overhaulPlan/overhaulPlanAuditJxzr"
								listFormDialog = new A.dialog({
		    						width: 850,
		    						height: 870,
		    						title: "审批通过",
		    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
		    						closed: function(){
		    						}	
		    					}).render(); 
							}
						
						}
						
						//专工审核通过overhaulPlan/zgtgAuditPass
					    if(url=="overhaulPlan/zgtgAuditPass"){
							var urls = "overhaulPlan/overhaulPlanZgtgAudit"
								listFormDialog = new A.dialog({
        						width: 850,
        						height: 300,
        						title: "审批通过",
        						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
        						closed: function(){
        						}	
        					}).render();
						
					    }
						
						if(url=="overhaulPlan/overhaulPlanEdit"){
							listFormDialog = new A.dialog({
	    						width: 1300,
	    						height: 800,
	    						title: "提交",
	    						url:format_url("/"+url+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
	    						closed: function(resule){
	    						}	
	    				}).render();
						}
						if(url=="overhaulPlan/auditReject" ){
							var urls = "overhaulPlan/overhaulPlanAuditMsg"
								listFormDialog = new A.dialog({
		    						width: 850,
		    						height: 300,
		    						title: "审批不通过",
		    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
		    						closed: function(){
		    							
		    						}	
		    				}).render();
						}
						if(url=="overhaulPlan/unAuditPass" ){
							var urls = "overhaulPlan/overhaulPlanAuditMsg"
								listFormDialog = new A.dialog({
		    						width: 850,
		    						height: 300,
		    						title: "不同意执行",
		    						url:format_url("/"+urls+"/"+$("#taskId").val()+"/"+id+"/"+codeBtn),
		    						closed: function(){
		    							
		    						}	
		    				}).render();
						}
						if(url == "overhaulPlan/cancel"){
							$.ajax({
								url:format_url("/overhaulPlan/cancel"),
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
						
						if(url == "overhaulPlan/againSubmit"){
							listFormDialog = new A.dialog({
								width: 850,
								height: 481,
								title: "检修主任审批",
								url:format_url('/overhaulPlan/sureSubmit?codeBtn='+codeBtn),
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