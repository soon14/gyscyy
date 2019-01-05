<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<div class="page-content">
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>

<div class="widget-main no-padding">
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top:20px;" id="addForm">
				<input id="id" name="id" value="${entity.id }" type="hidden"/>
<!-- 				 <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;"></span>检修日志编号 -->
<!--                     </label> -->
<!-- 					<div class="col-md-4"> -->
<%-- 				    	<input id="overhaulNumber"  class="col-md-12"  value="${entity.overhaulNumber }" type="text" readOnly name="overhaulNumber"/> --%>
<!-- 				    </div> -->
<!--                 </div>			 -->
			     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>单位名称
                    </label>
					<div class="col-md-4">
				    	<input id="unitName" value="${entity.unitName }" readOnly type="text" style="width:100%;"/>
				    </div>
	            
             		<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>检修负责人
                    </label>
					<div class="col-md-4">
                    	<input id="dutyUserId" class="col-md-12"   name="dutyUserId" readOnly type="text"  value="${entity.dutyUserName }"/>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>应到人数
                    </label>
					<div class="col-md-4">
	                    <input class="col-md-12" id="shouldNumber"  readOnly value="${entity.shouldNumber }" type="text" placeholder="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>实到人数
                    </label>
					<div class="col-md-4">
	                    <input class="col-md-12" id="trustNumber" readOnly type="text"  value="${entity.trustNumber }">
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>请假人数
                    </label>
					<div class="col-md-4">
	                    <input class="col-md-12" id="leaveNumber" readOnly type="text"  name="leaveNumber" value="${entity.leaveNumber }">
                    </div>
                
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>日志时间
                    </label>
					<div class="col-md-4">
                    	<input id="logDate"  class="col-md-12"   readOnly type="text"  value="${entity.logDateString }" />
                    </div>
                </div>

		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						检修出勤人员
                    </label>
					<div class="col-md-10">
                    	<textarea id="checkAttendance" readOnly class="col-md-12" style="height:150px; resize:none;" name="checkAttendance" type="text" placeholder="" maxlength="128" value=" ">${entity.checkAttendance }</textarea>
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
           		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >班会前</h5>			
        
        <div class="col-xs-12">	
		<div class="widget-main no-padding">
		        <div class="form-group col-xs-12"  style="margin-top:20px;">
					<label class="col-md-1 control-label no-padding-right" style="text-align:right">
						班前会
                    </label>
					<div class="col-md-11">
                     	<textarea id="beforeMeet" readOnly class="col-md-12" style="height:150px; resize:none;" name="beforeMeet" type="text" placeholder="" maxlength="512" >${entity.beforeMeet }</textarea>
                    </div>
                </div>
                </div>
                  <div class="col-xs-12 col-xs-12">		 
		        <div class="form-group">
					<label class="col-md-1 control-label no-padding-right"  style="text-align:right">
						班前会参加人员
                    </label>
					<div class="col-md-11">
                    	<textarea id="beforePart" readOnly  class="col-md-12" style="height:150px; resize:none;" name="beforePart" type="text" placeholder="" maxlength="512" >${entity.beforePart }</textarea>
                    </div>
                </div>
                </div>
             </div>
            </div>
         
         
               
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >安全交底</h5>
		
		<div class="col-xs-12">	
	    <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;"></h5>
		<div class="widget-main no-padding">			
		 <form id="overhaulSoft-table1-form" class="form-horizontal"  onsubmit="return false;">
				<table id="overhaulSoft-table1" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;"></span>交代当日任务中作业环境、存在风险</th>
                       		<th><span style="color:red;"></span>措施检查</th>
						</tr>
					</thead>
				</table>
			</form>
			</div>
		 </div>
		 
		 
		 
		<div class="col-xs-12">	
	    <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;"></h5>
		<div class="widget-main no-padding">
		 <form id="overhaulSoft-table2-form" class="form-horizontal"  onsubmit="return false;">
				<table id="overhaulSoft-table2" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;"></span>交代预防当日任务风险的安全作业事项</th>
                       		<th><span style="color:red;"></span>措施检查</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>         
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >检修工作安排</h5>
		<div class="col-xs-12">	
	    <h5 class=" header smaller blue " style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;"></h5>
		<div class="widget-main no-padding">			
		 <form id="overhaulWork-table-form" class="form-horizontal"  onsubmit="return false;">
				<table id="overhaulWork-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:20px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;"></span>工作内容</th>
                       		<th><span style="color:red;"></span>负责人</th>
                       		<th><span style="color:red;"></span>检修人员</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		   
		 		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >班会后</h5>
				 <div class="col-xs-12" >	
		<div class="widget-main no-padding">
		        <div class="form-group col-xs-12" style="margin-top:20px">
					<label class="col-md-1 control-label no-padding-right" style="text-align:right;">
						班后会
                    </label>
					<div class="col-md-11">
	                    <textarea id="afterMeet" readOnly class="col-md-12" style="height:150px; resize:none;" name="afterMeet" type="text" placeholder="" maxlength="512">${entity.afterMeet }</textarea>
                    
                    </div>
                </div>
		        <div class="form-group col-xs-12" style="margin-top:20px;">
					<label class="col-md-1 control-label no-padding-right" style="text-align:right;">
						班后会参加人员
                    </label>
					<div class="col-md-11">
                   		 <textarea id="afterPart" readOnly class="col-md-12" style="height:150px; resize:none;" name="afterPart" type="text" placeholder="" maxlength="512" >${entity.afterPart }</textarea>
                    
                    </div>
                </div>
                </div>
                </div>
   	     
		
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >其他事项说明</h5>	
		     <div class="col-xs-12">		
		     <div class="widget-main no-padding">	
		        <div class="form-group col-xs-12" style="margin-top:20px">
					<label class="col-md-1 control-label no-padding-right"  style="text-align:right;">
						其他事项说明
                    </label>
					<div class="col-md-11">
                   		 <textarea id="other" readOnly class="col-md-12" style="height:150px; resize:none;" name="other" type="text" placeholder="" maxlength="512">${entity.other }</textarea>
                    </div>
                </div>
		</div>
		</div>	
    		<div class="col-xs-12" style="height:50px;">
    		</div>	
</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var id=$('#id').val();
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.attchmentIds},
						maxFilesize:1,
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true 
					}).render();
    				
    				
// 安全交底 table展示---------------------------------start-------------------------------------------------------------------------------- 
					overhaulSafetDatatables1 = new A.datatables({
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
							           {data: "danger",width:"83%",orderable: false},
							           {data: "wayCheckName",name:"wayCheck",width: "auto",orderable: false}
							 ],
							optWidth:50
						}
					}).render();
					
					function initDataTable1(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualLogId = ${entity.id};
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
					initDataTable1();
					overhaulSafetDatatables2 = new A.datatables({
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
						           {data: "danger",width:"83%",orderable: false},
						           {data: "wayCheckName",name:"wayCheck",width: "auto",orderable: false}
						], 
						optWidth:50
						}
					}).render();
					
					function initDataTable2(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualLogId = ${entity.id};
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
					initDataTable2();
//安全交底table展示 -----------------------------end---------------------------------------------------------------------
					


//检修工作安排table展示---------------------------------start-------------------------------------------------------------
					overhaulWorkDatatables = new A.datatables({
						render: '#overhaulWork-table',
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
							           {data: "work",width: "73%",orderable: false},
							           {data: 'dutyUserName',name:"dutyUserId",width: "10%",orderable: false},
							           {data: 'checkUserName',name:"checkUserId",width: "10%",orderable: false}
							 ],
						}
					}).render();
					
					function initDataTable3(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhualLogId =${entity.id};
						$.ajax({
							url: format_url("/overhaulWork/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulWorkDatatables.addRows(result.data);
							}
							
						})
					};
					initDataTable3();
//检修工作安排table展示---------------------------------------end------------------------------------------------------------					
					
				});
			});
			
        </script>
    </body>
</html>