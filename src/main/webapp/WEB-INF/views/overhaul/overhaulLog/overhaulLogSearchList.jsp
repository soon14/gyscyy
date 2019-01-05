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
					检修管理
				</li>
				<li class="active">检修日志查询</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
							<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" >时间</label>：
							<div class="form-group dateInput padding-zero text-left">
		                		<div id="searchStartfindTimeDiv" style="border:none; padding:0px;"></div>
                   			</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
                         
							<div class="form-group dateInput padding-zero text-left">
		               		 	<div id="searchEndfindTimeDiv"  style="border:none; padding:0px;"></div>
                   			</div>
		           			</div>
		           			
			              	<div  class="form-group col-lg-6 col-md-6 col-sm-12 padding-zero text-right btnSearchBottom">
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
				<div class="col-xs-12" style="">
					<h5 class=' header smaller blue' style="margin-bottom:0px;border-bottom: 2px solid #438eb9!important;    font-weight: bold;">检修日志查询</h5>
					<div class="widget-main no-padding">
						<table id="overhaulLog_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
        							<th>序号</th>
	                                <th>时间</th>
	                                <th>单位名称</th>
	                                <th>检修负责人</th>
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
					var searchStartfindTime = new A.my97datepicker({
						id: 'searchStartfindTime',
						name:'searchStartfindTime',
						render:'#searchStartfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndfindTime\\')}",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
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
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					overhaulLogDatatables = new A.datatables({
						render: '#overhaulLog_table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulLog/searchDateList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							optWidth: 80,
							columns: [
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									  {data: "logDateString",name:"logDate",width: "auto",orderable: false}, 
									  {data: "unitName",width: "auto",orderable: false}, 
									  {data: "dutyUserName",width: "auto",orderable: false}],
							btns: [{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var logDateString = nData.logDateString;
									A.loadPage({
														render : '#page-container',
														url : format_url("/overhaulLog/getDetailByDate/"+ logDateString)
														
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
	    						field: 'logDate',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$('#searchStartfindTime').val()+" 00:00:00"
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'logDate',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$('#searchEndfindTime').val()+" 23:59:59"
	    					});
						}
						overhaulLogDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
					});
				});
			});
        </script>
    </body>
</html>