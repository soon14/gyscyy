<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
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
				<li class="active">运行管理</li>
				<li class="active">运行检查</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline" role="form">
                    <div class="clearfix" style="margin-left: 1%">				
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">值班班次：</label>
                            <div class="form-group  input-width padding-zero  text-left">						
                    <select id="teamIdDiv" name="teamId"></select>					    					    				    
                 </div>
                   </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="searchLabel " for="form-field-1">值班负责人</label>：
                            <div class="padding-zero inputWidth  text-left">                    
                    	<select id="chargeIdDiv" name="chargeId"></select>
                    	</div>					    
                   </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">接班时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                    	<div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                   </div>
                            <label style="width:2.6%;text-align:center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                        <div id="searchEndfindTimeDiv"   style="border:none; padding:0px;"></div>
                   </div>
				   </div>				   
                       
                   </div>   
					<div class="clearfix" style="margin-left: 1%">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">值班值次：</label>
                            <div class="form-group  input-width padding-zero  text-left">						
                    <select id="dutyIdDiv" name="dutyId"></select>					    					    				    
                 </div>
                   </div>
                     <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="searchLabel" for="form-field-1">交接班状态</label>：
                            <div class="padding-zero inputWidth  text-left">                    
                    	<select id="grStateDiv" name="grState"></select>
                    	</div>					    
                   </div>
   				<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						<label class="" for="form-field-1">交班时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                    	<div id="searchStartGiveDateDiv"  style="border:none; padding:0px;"></div>
                   </div>
                            <label style="width:2.6%;text-align:center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                        <div id="searchEndGiveDateDiv"   style="border:none; padding:0px;"></div>
                   </div>
				   </div>	
				   </div>
				   <div class="clearfix" style="margin-left: 1%">
					   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<label class="" for="form-field-1">检测状态：</label>
	                            <div class="form-groupp padding-zero input-width  text-left">                    
	                    	<select id="runCheckDiv" name="runCheck"></select>
	                    	</div>					    
	                   </div>
	                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
	                    </div>
					    <div class="form-group col-lg-3 col-md-3 col-sm-12 col-xs-12" style="text-align: right;">
							<button id="btnSearch" class="btn btn-xs btn-primary">
								<i class="glyphicon glyphicon-search"></i>
								查询
							</button>
							<button id="btnReset" class="btn btn-xs btn-primary" style="margin-right: -6%">
								<i class="glyphicon glyphicon-repeat"></i>
								重置
							</button>								
						</div>	
					<div class="clearfix">			
					</div>                                   					
                </div>
            </div>
         </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
                        <div id="tableTitle"></div>
						<table id="runLog-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>		
								    <th>序号</th>	 							
	                                <th>接班时间</th>	                                        							
	                                <th>交班时间</th>	                                
	                                <th>值班班次</th>
	                                <th>值班值次</th>	                                
	                                <th>值班负责人</th>
	                                <th>交接班状态</th>
	                                <th>日期</th>
	                                <th>检测状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var dedit;
					$('#tableTitle').html('<h5 class="table-title header smaller blue" >运行值班日志</h5>');
								
								var searchStartfindTime = new A.my97datepicker({
									id: 'searchStartfindTime',
									name:'searchStartfindTime',
									render:'#searchStartfindTimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "#F{$dp.$D(\\'searchEndfindTime\\')}",
											minDate: "",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								var searchEndfindTime = new A.my97datepicker({
									id: 'searchEndfindTime',
									name:'searchEndfindTime',
									render:'#searchEndfindTimeDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "#F{$dp.$D(\\'searchStartfindTime\\')}",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								var searchStartfindTime = new A.my97datepicker({
									id: 'searchStartGiveDate',
									name:'searchStartGiveDate',
									render:'#searchStartGiveDateDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "#F{$dp.$D(\\'searchEndGiveDate\\')}",
											minDate: "",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								var searchEndfindTime = new A.my97datepicker({
									id: 'searchEndGiveDate',
									name:'searchEndGiveDate',
									render:'#searchEndGiveDateDiv',
									options : {
											isShowWeek : false,
											skin : 'ext',
											maxDate: "",
											minDate: "#F{$dp.$D(\\'searchStartGiveDate\\')}",
											dateFmt: "yyyy-MM-dd HH:mm"
									}
								}).render();
								var chargeIdCombobox = new A.combobox({
									render: "#chargeIdDiv",
									datasource:${runLogCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10
									}
									}).render();
								var teamIdCombobox = new A.combobox({
									render: "#teamIdDiv",
									//返回数据待后台返回TODO
									datasource: ${teamMemCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
								}).render();
								var orgAppCombobox = new A.combobox({
									render: "#dutyIdDiv",
									//返回数据待后台返回TODO
									datasource: ${orgAppCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
								}).render();
								var grStateCombobox = new A.combobox({
									render: "#grStateDiv",
									//返回数据待后台返回TODO
									datasource: ${grStateCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
								}).render();
								var runCheckCombobox = new A.combobox({
									render: "#runCheckDiv",
									//返回数据待后台返回TODO
									datasource: ${runCheckCombobox},
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10,
										"width":"100%"	
									}
								}).render();
								var exportExcel;
					var runLogDatatables = new A.datatables({
						render: '#runLog-table',
						options: {
					        "ajax": {
					            "url": format_url("/runLog/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {					            	
					            	d.conditions = conditions;
					            	//上一条、下一条代码
					            	dedit=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked:false,							
							 order: [[ 2, "desc" ]],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "date",width: "auto",orderable: true}, 							          
							          {data: "giveDate",width: "auto",orderable: true}, 							          
// 							          {data: "teamName",width: "10%",orderable: true}, 
							          {data: "dutyName",width: "auto",orderable: true}, 
							          {name:"dutyId",orderable: true,"width":"auto", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return row.teamName;  
						               	} },
							          {data: "chargeName",width: "auto",orderable: true}, 
							          {data: "grStateName",width: "auto",orderable: true},
					   				  {data: "createDate",width: "auto",orderable: true},
// 					   				 {data: "checkName",name:"check",width: "10%",orderable: true}
					   			  {data: "checkForRunName",width: "auto",name:"checkForRun",orderable: true,render : function(data, type, row, meta) {
					                   if(data=="未完成"){
					                	   return "<span style='color:red'>"+data+"</span>";
					                   }  return data;
					               }}],
					   				fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/runLog/exportExcel"),JSON.stringify(conditions));
										 }
										 exportExcel="";
									 },
							toolbars: [{
								label:"导出",
								icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
								events:{
		    						click:function(event){
		    							$('#btnSearch').click();
            							exportExcel="exportExcel";
// 		    							window.location.href=format_url("/runLog/exportExcel"); 
		    						}
								}
							}],
							optWidth:280,
							btns: [
// 							       {
// 						id: "detail",
// 						label:"运行检查详情",
// 						icon: "fa fa-binoculars",
// 						className: "blue ",
// 						events:{
// 							click: function(event, nRow, nData){
// 								var id = nData.id;																
// 								//上一条、下一条代码
// 								dedit.length=1;
// 								dedit.start=nData.start;
// 								$.ajax({url : format_url('/runCheck/index/' + id),
// 									contentType : 'application/json',
// 									type : 'POST',
// 									data : JSON.stringify(dedit),
// 									success : function(result) {
// 										var divshow = $("#page-container");
// 										divshow.text("");// 清空数据
// 										divshow.append(result); // 添加Html内容，不能用Text 或 Val
// 									}
// 								});
								
// 							}
// 						}
// 					},
					{
						id: "detailRunlog",
						label:"查看运行日志",
						icon: "fa fa-binoculars",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;																
								//上一条、下一条代码
								dedit.length=1;
								dedit.start=nData.start;
								$.ajax({url : format_url('/runLog/getDetailRunCheck/' + id),
									contentType : 'application/json',
									type : 'POST',
									data : JSON.stringify(dedit),
									success : function(result) {
										var divshow = $("#page-container");
										divshow.text("");// 清空数据
										divshow.append(result); // 添加Html内容，不能用Text 或 Val
									}
								});
								
							}
						}
					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];						
						if($('#searchStartfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartfindTime').val()
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'a.C_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndfindTime').val()
	    					});
						}
						if($('#searchStartGiveDate').val()){
	    					conditions.push({
	    						field: 'a.C_GIVE_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartGiveDate').val()
	    					});
						}
						if($('#searchEndGiveDate').val()){
	    					conditions.push({
	    						field: 'a.C_GIVE_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndGiveDate').val()
	    					});
						}
						if($('#teamIdDiv').val()){
	    					conditions.push({
	        					field: 'a.C_TEAM_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#teamIdDiv').val()
	        				});
						}
						if($('#chargeIdDiv').val()){
	    					conditions.push({
	        					field: 'a.C_CHARGE_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#chargeIdDiv').val()
	        				});
						}
						if($('#dutyIdDiv').val()){
	    					conditions.push({
	        					field: 'a.C_DUTY_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#dutyIdDiv').val()
	        				});
						}
						if($('#grStateDiv').val()){
	    					conditions.push({
	        					field: 'a.C_GR_STATE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#grStateDiv').val()
	        				});
						}
						runLogDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#teamIdDiv").val("");	
						$("#teamIdDiv").trigger("chosen:updated");
						$("#chargeIdDiv").val("");
						$("#chargeIdDiv").trigger("chosen:updated");						
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#searchStartGiveDate").val("");
						$("#searchEndGiveDate").val("");
						$("#dutyIdDiv").val("");
						$("#dutyIdDiv").trigger("chosen:updated");
						$("#grStateDiv").val("");
						$("#grStateDiv").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>