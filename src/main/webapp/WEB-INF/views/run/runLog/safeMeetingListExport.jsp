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
				<li>
					运行管理
				</li>
				<li class="active">工作安排</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">	
		<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
	             <div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel " for="searchguarderName">负责人</label>：
						  <input id="searchfZR" class="inputWidth text-left contentInput" placeholder="请输入负责人"  type="text"></input>
	                     </div>
						<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel " for="form-field-1">安排时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchWorkTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchWorkTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
						  <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	 						<label class="searchLabel" for="form-field-1">统计方式</label>：
	 						<div class="form-groupp adding-zero inputWidth  text-left">
	 							<select id="DateType" name="DateType"></select>
	 						</div>
	                   </div>
	            </div>
	            <div class="clearfix">
	              	 <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchcontent">工作内容</label>：
	                    <input id="searchcontent" class="inputWidth text-left contentInput" placeholder="请输入工作内容" type="text"></input>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                </div>
	                 <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
	                    <button id="btnSearch" class="btn btn-xs btn-primary">
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
					<h5 class="table-title header smaller blue" >工作安排</h5>
					<div class="widget-main no-padding">
						<table id="safeMeeting-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
        							<th>序号</th>
	                                <th>工作内容</th>
	                                <th>完成情况</th>
	                                <th>负责人</th>	                                
	                                <th>安排时间</th>	                                
                                    <th>操作 </th>
                                </tr>
                            </thead>
                        </table>
                    </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		var exportExcel="";
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var searchWorkTimeStartDiv = new A.my97datepicker({
						id: 'searchWorkTimeStartDivId',
						name:'workTime',
						render:'#searchWorkTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchWorkTimeStartDivId\\')}",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					var searchWorkTimeEndDiv = new A.my97datepicker({
						id: 'searchWorkTimeEndDivId',
						name:'workTime',
						render:'#searchWorkTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchWorkTimeEndDivId\\')}",
								dateFmt: "yyyy-MM"
						}
					}).render();
					//统计方式
					var DateType = new A.combobox({
							id:'DateType',
							name:'DateType',
							render : "#DateType",
							datasource : ${DateUtil},
							allowBlank: true,
							width:20,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
					$("#DateType").on("change", function(event){
						var selectValue = $(this).val();
						if(selectValue=="year"){
							var searchWorkTimeStartDiv = new A.my97datepicker({
								id: 'searchWorkTimeStartDivId',
								name:'workTime',
								render:'#searchWorkTimeStartDiv',
								options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate: "#F{$dp.$D(\\'searchWorkTimeStartDivId\\')}",
										minDate: "",
										dateFmt: "yyyy"
								}
							}).render();
							var searchWorkTimeEndDiv = new A.my97datepicker({
								id: 'searchWorkTimeEndDivId',
								name:'workTime',
								render:'#searchWorkTimeEndDiv',
								options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate: "",
										minDate: "#F{$dp.$D(\\'searchWorkTimeEndDivId\\')}",
										dateFmt: "yyyy"
								}
							}).render();
						}else{
							var searchWorkTimeStartDiv = new A.my97datepicker({
								id: 'searchWorkTimeStartDivId',
								name:'workTime',
								render:'#searchWorkTimeStartDiv',
								options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate: "#F{$dp.$D(\\'searchWorkTimeStartDivId\\')}",
										minDate: "",
										dateFmt: "yyyy-MM"
								}
							}).render();
							var searchWorkTimeEndDiv = new A.my97datepicker({
								id: 'searchWorkTimeEndDivId',
								name:'workTime',
								render:'#searchWorkTimeEndDiv',
								options : {
										isShowWeek : false,
										skin : 'ext',
										maxDate: "",
										minDate: "#F{$dp.$D(\\'searchWorkTimeEndDivId\\')}",
										dateFmt: "yyyy-MM"
								}
							}).render();
						}
    				});
					var conditions=[];
					var safeMeetingDatatables = new A.datatables({
						render: '#safeMeeting-table',
						options: {
					        "ajax": {
					            "url": format_url("/safeMeeting/searchList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 280,
							 order: [[ 0, "desc" ]],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "meetingContent",width: "auto",orderable: true}, 
							          {data: "checkStateName",name:"checkState",width: "auto",orderable: true}, 
							          {data: "fZR",width: "auto",orderable: true},
							          {data: "workTime",width: "auto",orderable: true}],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/safeMeeting/exportExcelRun"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
							toolbars: [
        					{
								label:"导出",
								icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
								events:{
									click:function(event){
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
								}
							}],
							btns: [{
							    id:"440",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url("/safeMeeting/show/"+ id),
										});
									}
								}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
    					if($("#searchfZR").val()!=""){
	    					conditions.push({
	    						field: 'C_FZR',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchfZR').val()
	    					});
    					}
    					
    					if($("#searchcontent").val()!=""){
	    					conditions.push({
	        					field: 'C_MEETING_CONTENT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchcontent').val()
	        				});
    					}
    					
    					
    					if($("#searchWorkTimeStartDivId").val()!=""){
	    					conditions.push({
	    						field: 'C_WORK_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#searchWorkTimeStartDivId").val()+" 00:00:00"
	    					});
    					}
    					if($("#searchWorkTimeEndDivId").val()!=""){
	    					conditions.push({
	    						field: 'C_WORK_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchWorkTimeEndDivId").val()+" 23:59:59"
	    					});
    					}
    					
    					safeMeetingDatatables.draw();
					});	
					$('#btnReset').on('click',function(){
						conditions=[];
						$("#searchfZR").val("");
						$("#searchcontent").val("");
						$("#searchWorkTimeStartDivId").val("");
						$("#searchWorkTimeEndDivId").val("");
						$("#DateType").val("");
						$("#DateType").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>