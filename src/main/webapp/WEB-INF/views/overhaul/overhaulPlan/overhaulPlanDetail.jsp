<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>  
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>
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
					<li class="active">
					检修管理</li>
					<li class="active">
					检修计划</li>
					<li class="active">查看</li>
				</ul>
		</div>
	<div class="page-content">
			<div style="float:right; margin-right:50px;">
				<button id="cancelBtn"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
	       			<i class="fa fa-reply"></i>
	       			返回
	       		</button>
		    </div>
			<h5 class='table-title-withoutbtn header smaller blue' style="margin-right:50px;margin-left:30px;" >基础信息</h5>
							
		<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"   id="addForm">
				<input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id }">
				    <div class="col-md-12" style="margin-top:20px">
			       <div class="form-group">
			       		<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;">*</span>检修计划编号
						</label>
						<div class="col-md-4">
               				<input class="col-md-12" id="planNumber" name="planNumber" readOnly type="text" value="${entity.planNumber}">
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;"></span>申报日期
					    </label>
					    <div class="col-md-4">
							<input  class="col-md-12"  type="text"  value="${ entity.planYearString }" readOnly/>
	                    </div>
					    
	               </div>
		           <div class="form-group">
		           						<label class="col-md-1 control-label no-padding-right">
							<span style="color:red;"></span>单位名称
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
						<c:set var="planNameId "  value="${entity.planNameId}"/>
						<select class="col-md-12 chosen-select"  id="planNameId" name="planNameId"  readOnly disabled="disabled">
						<c:if test="${entity.planNameId=='0'}">
								<option value="0"  selected="selected">无</option>
								<option value="1"  >有年度计划</option>
						</c:if>
						<c:if test="${entity.planNameId=='1'}">
								<option value="0"  >无</option>
								<option value="1"  selected="selected" >有年度计划</option>
						</c:if>
						</select>
						</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;"></span>计划填报人
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12"  type="text"  value="${ entity.dutyUserName }" readOnly />
	                    </div>
	               </div>
	               <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							签报标题
						</label>
						<div class="col-md-4">
							<input class="col-md-12" id="dispatchTitle" name="dispatchTitle" type="text"  value="${entity.dispatchTitle}" readonly>
						</div>
<!-- 						<label class="col-md-2 control-label no-padding-right"> -->
<!-- 							发文字号 -->
<!-- 					    </label> -->
<!-- 					    <div class="col-md-4"> -->
<%-- 							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="${entity.dispatchNumber}" readonly> --%>
<!-- 	                    </div> -->
	               </div>
		           <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">
							备注
						</label>
						<div class="col-md-10">
							<textarea name="remark" placeholder="" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
						</div>
	               </div>
			       <div class="form-group form-horizontal">
						<label class="col-md-1 control-label no-padding-right">
							附件
						</label>
						<div class="col-md-10" id="divfile">
						</div>
	               </div>
	               </div>
	            </form>
	       </div>
	   <div class="row" style="margin-right:70px;margin-left:30px;">
	    <div class="col-xs-12">	
	    <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;">检修项目</h5>
		<div class="widget-main no-padding">
			<form id="overhaulProject">
				<table id="overhaulProject-table"  class="table table-striped table-bordered table-hover" style="width:100%;">
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
			</form>
		</div>
		</div>
		</div>
		
			<div class="row" style="margin-right:70px;margin-left:30px;">
		    <div class="col-xs-12" >	
		     <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;">停送电管理</h5>
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
						</tr>
					</thead>
				</table>
			</div>
		 </div>
		 </div>
		 
</div>
		<script type="text/javascript">
		var listFormDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					var overhaulPlanId=$('#id').val();
					
					//附件上传
					 var uploaddropzone =new A.uploaddropzone({
							render : "#divfile",
							fileId:${entity.attachementIds},
							maxFilesize:1,
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
								          ]
							}
						}).render();
					
				});
			});
        </script>
    </body>
</html>