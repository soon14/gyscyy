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
				<li class="active">场站运行日志</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
		    <div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">值班班次</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="teamIdDiv" name="teamId"></select>
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">值班负责人</label>：
                              <div class="padding-zero inputWidth  text-left">
                             	<select id="chargeIdDiv" name="chargeId"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="form-field-1">接班时间</label>：
                              <div class="form-group dateInputOther padding-zero text-left" >
                            	<div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                            	</div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
		                    <div class="form-group dateInputOther padding-zero text-left">
		                        <div id="searchEndfindTimeDiv"></div>
		                    </div>
                        </div>
                        
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">值班值次</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="dutyIdDiv" name="dutyId"></select>	
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">交接班状态</label>：
                              <div class="padding-zero inputWidth  text-left">
                             	<select id="grStateDiv" name="grState"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="form-field-1">交班时间</label>：
                              <div class="form-group dateInputOther padding-zero text-left" >
                            	<div id="searchStartGiveDateDiv"  style="border:none; padding:0px;"></div>
                            	</div>
                            <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
		                    <div class="form-group dateInputOther padding-zero text-left">
		                        <div id="searchEndGiveDateDiv"></div>
		                    </div>
                        </div>
                        
                    </div>
				 <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="form-field-1">检测状态</label>：
                            <div class="padding-zero inputWidth  text-left">
                             <select id="runCheckDiv" name="runCheck"></select>	
                              </div>
                        </div>
                         <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                         </div>
                          <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-repeat"></i>
                                重置
                            </button>
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
								var exportExcel="";
					var runLogDatatables = new A.datatables({
						render: '#runLog-table',
						options: {
					        "ajax": {
					            "url": format_url("/runLog/searchDataForWind"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {					            	
					            	d.conditions = conditions;
// 					            	//上一条、下一条代码
					            	dedit=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked:false,		
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "date",width: "15%",orderable: true}, 							          
							          {data: "giveDate",width: "15%",orderable: true}, 							          
							          {data: "dutyName",width: "10%",orderable: true}, 
							          {name:"dutyId",orderable: true,"width":"12%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return row.teamName;  
						               	} },
							          {data: "chargeName",width: "15%",orderable: true}, 
							          {data: "grStateName",width: "8%",orderable: true},
					   				  {data: "createDateDay",name:"createDate",width: "10%",orderable: true},
					   			  {data: "checkName",width: "8%",name:"check",orderable: true,render : function(data, type, row, meta) {
					                   if(data=="未检测"){
					                	   return "<span style='color:red'>"+data+"</span>";
					                   }  return data;
					               }}],
					   				fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/runLog/exportExcelWind"),JSON.stringify(conditions));
										 }
										 exportExcel="";
									 },
							toolbars: [
								{
									label:"新增",
									icon:"glyphicon glyphicon-plus",
									className:"btn-success",
									events:{
										click: function(event, nRow, nData){
											var unitId = ${userEntity.unitId};
											var newFlag = ${newFlag};
											if(unitId=="138"){
												alert("集控中心人员不可以填写场站运行日志");
												return;
											}
// 											else if(newFlag==false){
// 												alert("当日记录已填写!");
// 												return;
// 											}
											else{
												A.loadPage({
													render : '#page-container',
													url : format_url("/runLog/getAddForWind")
										});
											}
												
										}
									}
								}
								,{
								label:"导出",
								icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
								events:{
		    						click:function(event){
		    							var unitId = ${userEntity.unitId};
										var newFlag = ${newFlag};
										if(unitId=="138"){
											alert("集控中心人员不可以导出场站运行日志");
											return;
										}
		    							$('#btnSearch').click();
            							exportExcel="exportExcel";
		    						}
								}
							}],
							optWidth:280,
							btns: [ {
								id:"setting",
								label:"场站运行日志填报",
								icon: "fa fa-pencil-square",
								className: "gray setting",
								render: function(btnNode, data){
									if(data.grState=="done"){
										btnNode.hide();
									}
									if('${userEntity.id}'!=data.chargeId && '${userEntity.loginName}'!='super'){
										btnNode.hide();
									}
									
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;	
										var grState=nData.grState;
										if(grState=="done"){
											alert("交接班完成，不可填报运行日志！");
											return false;
										}
										A.loadPage({
											render : '#page-container',
											url : format_url("/runLog/infoForWind/" + id),
										});
									}
								}
						},{
								id: "edit",
								label:"交接班",
								icon: "fa bigger-130",
								className: "glyphicon glyphicon-transfer",
								render: function(btnNode, data){
									if(data.grState=="done"){
										btnNode.hide();
									}
									if('${userEntity.id}'!=data.chargeId){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var grState=nData.grState;
										var newFlag = ${newFlag};
										if(grState=="done"){
											alert("交接班已完成，不可修改！");
											return false;
										}else if(newFlag==false){
											alert("当日记录已填写！");
											return ;
										}	
										$.ajax({
											url : format_url("/runLog/invalidValidateWind/" + id),
    										contentType : 'application/json',
    										dataType : 'JSON',
    										type : 'POST',
    										success: function(result){
    											if(result.result=="success"){
    											A.loadPage({
    												render : '#page-container',
    												url : format_url("/runLog/getEditWind/" + id),
    											});
    											}else{
    												alert(result.errorMsg);
    											}
    										},
    										error:function(v,n){
    											alert('操作失败');
    										}
										});
										
									}
								}
							},{
						id: "detail",
						label:"查看",
						icon: "fa fa-binoculars",
						className: "blue ",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;																
								//上一条、下一条代码
								dedit.length=1;
								dedit.start=nData.start;
								$.ajax({url : format_url('/runLog/getDetailForWind/' + id),
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
						if($('#runCheckDiv').val()){
	    					conditions.push({
	        					field: 'a.C_CHECK',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#runCheckDiv').val()
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
						$("#runCheckDiv").val("");
						$("#runCheckDiv").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>