<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
				<li class="active">检修管理</li>
				<li class="active">设备检修记录</li>
				<li class="active">项目列表</</li>
				<li class="active">设备检修纪录查看</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
			<div style="float:right;margin-right:0px;">
				<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
        			<i class="fa fa-reply"></i>
        			返回
        		</button>
			</div>
			<h5 class='table-title header smaller blue' style="margin-bottom:20px!important;">基础信息</h5>
			<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"  style="margin-right:200px;margin-top:20px;" id="overhaulLogDetailForm">
				<input id="overhaulArrangeId" name="overhaulArrangeId" value="${overhaulArrangeId}" type="hidden"/>
				<div class="form-group">
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						外协单位 -->
<!--                     </label> -->
<!-- 					<div class="col-md-4"> -->
<%-- 				    	<input id="unitName" name="unitName" class="col-md-12" type="text" readonly="readonly" value="${overhaulLogDetailEntities.unitName}"/> --%>
<!-- 				    </div> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						检修负责人 -->
<!--                     </label> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<select class="col-md-12 chosen-select" id="dutyPerson" name="dutyPerson" ></select> -->
<!-- 				    </div> -->
                </div>
                <div class="form-group">
                 	<label class="col-md-2 control-label no-padding-right">
						外协单位
                    </label>
					<div class="col-md-4">
				    	<input id="unitName" name="unitName" class="col-md-12" type="text" readonly="readonly" value="${overhaulLogDetailEntities.unitName}"/>
				    </div>
				   <label class="col-md-2 control-label no-padding-right">
					  损失电量(万kWh)
                    </label>
                    <div class="col-md-4">
                        <input id="afterMeet" class="col-md-12" style="resize:none;" name="afterMeet" type="number" readonly="readonly" placeholder="请输入损失电量" maxlength="128" value="${overhaulLogDetailEntities.afterMeet}">
                    </div>
                </div>
			    <div class="form-group">
			    	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>开始时间
                    </label>
					<div class="col-md-4">
					   <input id="logDate" name="logDate" class="col-md-12" type="text" readonly="readonly" value="${overhaulLogDetailEntities.logDateString}"/>
                    </div>
					  <label class="col-md-2 control-label no-padding-right">
					  结束时间
                    </label>
					<div class="col-md-4">
                    	 <input id="endDate" name="endDate" class="col-md-12" type="text" readonly="readonly" value="${overhaulLogDetailEntities.endDateString}"/>
                    </div>
                </div>
                 
			    <div class="form-group">
             		<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作班成员
                    </label>
                    <div class="col-md-10">
	                    <textarea id="teamMemberNames" class="col-md-12" style="height:150px; resize:none;" name="teamMember" readonly="readonly" type="text" placeholder="请输入工作班成员" maxlength="128" value="">${overhaulLogDetailEntities.teamMember}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作完成情况
                    </label>
					<div class="col-md-10">
	                    <textarea id="workFinishInfo" class="col-md-12" style="height:150px; resize:none;" name="workFinishInfo" readonly="readonly" type="text" placeholder="请输入工作完成情况" maxlength="128" value="">${overhaulLogDetailEntities.workFinishInfo}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>遗留问题和应急措施
                    </label>
					<div class="col-md-10">
	                    <textarea id="problemMeasures" class="col-md-12" style="height:150px; resize:none;" name="problemMeasures" readonly="readonly" type="text" placeholder="请输入遗留问题和应急措施" maxlength="128" value="">${overhaulLogDetailEntities.problemMeasures}</textarea>
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
        
        
	    <div class="col-xs-12" style="margin-top:0px;">	
		<div class="widget-main no-padding">
			<form id="overhaulWorkTask-table-form" class="form-horizontal">
			        <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>工作任务</h5>			
					<table id="overhaulWorkTask-table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<thead>
							<tr>
								<th style="display:none;">主键</th>
								<th>序号</th>
								<th><span style="color:red;">*</span>设备名称</th>
								<th><span style="color:red;">*</span>工作任务</th>
	                       		<th><span style="color:red;">*</span>完成状态</th>
							</tr>
						</thead>
					</table>
			</form>
		</div>
		</div>
        	
	    <div class="col-xs-12" style="margin-top:0px;">	
		<div class="widget-main no-padding">
			<form id="overhaulSoft-table1-form" class="form-horizontal">
		        <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >安全交底</h5>			
				<table id="overhaulSoft-table1" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;">*</span>交代当日任务中作业环境、存在风险</th>
                       		<th><span style="color:red;">*</span>措施检查</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		
	    <div class="col-xs-12" style="margin-top:30px;">	
		<div class="widget-main no-padding">
		 <form id="overhaulSoft-table2-form" class="form-horizontal"  onsubmit="return false;">
		 		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' ></h5>
				<table id="overhaulSoft-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;">*</span>交代预防当日任务风险的安全作业事项</th>
                       		<th><span style="color:red;">*</span>措施检查</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		
		
	    <div class="col-xs-12" style="margin-top:0px;margin-bottom: 30px;">	
		<div class="widget-main no-padding">
			<form id="overhaulSpareconsume-table-form" class="form-horizontal">
			        <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>备件消耗</h5>			
					<table id="overhaulSpareconsume-table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<thead>
							<tr>
								<th style="display:none;">主键</th>
								<th>序号</th>
						        <th>物资编码</th> 
	                            <th>物资名称</th>
	                            <th>规格型号</th>
	                            <th>生产厂家</th>
	                            <th><span style="color:red;">*</span>数量</th>
	                            <th>单位</th>
	                            <th>设备</th>
							</tr>
						</thead>
					</table>
			</form>
		</div>
		</div>
    	
</div>
	<script type="text/javascript">
		    var type = "${type}";
			var overhaulRecordId = ${overhaulRecordId};
// 		    var overhaulOrganizationId = ${overhaulOrganizationId};
		    var overhaulAddMore = true;
		    var overhaulWorkTask = [];
		    var overhaulspareconsume = [];
		    var localequipInfoArray;
			var fileId = ${overhaulLogDetailEntities.fileId};
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					var conditions=[];
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId : fileId,
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					//检修负责人
					var dutyUsersId = new A.combobox({
						render : "#dutyPerson",
						datasource : ${dutyPersons},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
// 					dutyUsersId.setValue(${recordEntity.dutyUserId});
// 工作任务 table展示---------------------------------start-------------------------------------------------------------------------------- 
					var overhaulWorkTaskDatatables = new A.datatables({
						render: '#overhaulWorkTask-table',
						options: {
							 "ajax": {
						            "url": format_url("/overhaulWorkTask/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "data": function (d) {	
						            	conditions.push({
					     					field: 'O.C_OVERHAUL_RECORD_ID',
					     					fieldType:'INT',
					     					matchType:'EQ',
					     					value:overhaulRecordId
					     				});
										conditions.push({
					     					field: 'O.C_STATUS',
					     					fieldType:'STRING',
					     					matchType:'EQ',
					     					value:"1"
					     				});
						            	d.conditions = conditions;
						                return JSON.stringify(d);
						              }
						        },
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(result){
										this.api().column(1).nodes().each(function(cell, i) {
										cell.innerHTML =  i + 1;
									});
									executeDataIds(result);
								},
								'fnInitComplete':function(result){
									executeDataIds(result);
								},
							columns: [ {data:"id", visible:false,orderable:false}, 
							           {data: null,width: "5%",orderable: false},
							           {data: "equipName",name:"equipId",width: "10%",orderable: false},
							           {data: "workTask",width: "75%",orderable: false},
							           {data: 'finishStatusString',name:"finishStatusString",width: "10%",orderable: false}
							 ],
							optWidth:50
						}
					}).render();
					
				
//工作任务table展示 -----------------------------end---------------------------------------------------------------------
//安全交底 table展示---------------------------------start-------------------------------------------------------------------------------- 
					var overhaulSafetDatatables1 = new A.datatables({
						render: '#overhaulSoft-table1',
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
							columns: [ {data:"id", visible:false,orderable:false}, 
							           {data: null,width: "5%",orderable: false},
							           {data: "danger",width: "83%",orderable: false},
							           {data: 'wayCheckName',name:"wayCheck",width: "10%",orderable: false}
							 ],
							optWidth:50,
						}
					}).render();
					
					function overhaulSafetinitDataTable1(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhaulRecordId = overhaulRecordId;
						params.logType = 'jobRisk';
						$.ajax({
							url: format_url("/overhaulSafe/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulSafetDatatables1.addRows(result.data);
							}
							
						})
					};
					overhaulSafetinitDataTable1();
					var overhaulSafetDatatables2 = new A.datatables({
						render: '#overhaulSoft-table2',
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
						columns: [ {data:"id", visible:false,orderable:false}, 
						           {data: null,width: "5%",orderable: false}, 
						           {data: "danger",width: "83%",orderable: false}, 
								   {data: 'wayCheckName',name:"wayCheck",width: "10%",orderable: false, }
						 ],
						 
						optWidth:50,
						}
					}).render();
					
					function overhaulSafetinitDataTable2(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhaulRecordId = overhaulRecordId;
						params.logType = 'safItem';
						$.ajax({
							url: format_url("/overhaulSafe/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulSafetDatatables2.addRows(result.data);
							}
							
						})
					};
					overhaulSafetinitDataTable2();
//安全交底table展示 -----------------------------end---------------------------------------------------------------------

//物资消耗 table展示 -----------------------------start---------------------------------------------------------------------

			        var conditions=[];
					var materialResult = [];
					var overhaulSpareconsumeDatatables = new A.datatables({
						render: '#overhaulSpareconsume-table',
						options: {
							"ajax": {
					            "url": format_url("/overhaulSpareconsume/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'O.C_OVERHAUL_RECORD_ID',
				     					fieldType:'INT',
				     					matchType:'EQ',
				     					value:overhaulRecordId
				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(result){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
								executeDataIds(result);
						},
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true},
							         {data: "code",width: "17%",orderable: true}, 
							         {data: "name",width: "17%",orderable: true}, 
							         {data: "model",width: "20%",orderable: true}, 
							         {data: "manufacturer",width: "15%",orderable: true}, 
							         {data: "number",width: "10%",orderable: true}, 
							         {data: "unit",width: "6%",orderable: true},
							         {data: "equipName",name:"equipId",width: "10%",orderable: true }
							        ],
							 optWidth:50,
						}
					}).render();
					
					function overhaulSpareconsumeinitDataTable(flag){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualLogId = overhaulArrangeId;
						$.ajax({
							url: format_url("/overhaulSpareconsume/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								if(flag){
									overhaulSpareconsumeDatatables.addRows(result.data);
								}
							}
						})
					};
					overhaulSpareconsumeinitDataTable();

		//物资消耗 table展示 -----------------------------end---------------------------------------------------------------------
					//返回
					$('#btnBack').on('click',function(){
						 A.loadPage({
								render : '#page-container',
// 								url : format_url("/overhaulRecord/getDataList/"+ orgnaizationId)
								url : format_url("/overhaulRecord/getDataList/")
						 });
					});
				});
			});
			
			//处理工作安排ID数组
			function executeDataIds(oSettings){
				var jsonObj = oSettings.json.data;
				localequipInfoArray = [];
				for(var i=0;i<jsonObj.length;i++){
					localequipInfoArray.push(jsonObj[i].id);
				}
			}
        </script>
    </body>
</html>