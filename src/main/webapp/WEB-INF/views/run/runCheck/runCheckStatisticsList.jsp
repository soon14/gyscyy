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
				<li class="active">检查结果统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
            <div class="col-xs-12 search-content" >
				<div class="form-inline text-left" role="form">
					 <div class="clearfix">
                        <div class="form-group" >
                            <label class="" for="equipAbnormalBill">统计月份：</label>
                        </div>
                        <div class="form-group" style="height: 31px">
                            <div id="statisticsMonthDiv"  style="width:175px;border:none; padding:0px;"></div>
                        </div>
                        <div class="form-group" style='margin-left:20px'>
                            <label class="" for="statisticsTypeDiv">检查统计类型：</label>
                        </div>
                        <div class="form-group" style='width:175px;'>
                            <select id="statisticsTypeDiv" ></select>
                        </div>
                       <div class="form-group" style="float: right;">
                            <button id="btnSearch" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                               		 查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px" type="button">
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
					<h5 class="table-title-withoutbtn header smaller blue " style="margin-bottom:0px;" id="table_title">
						<label id="table_titile">检查结果统计</label>
						<div style="float:right; margin-right:0px;">
							<button id="btnimport" class="btn btn-xs btn-primary">
								<i class="ace-icon glyphicon glyphicon-download"></i>
								导出
							</button>
						</div>
					</h5>
					<div class="widget-main no-padding" id="defectStatistics-print">
						<table id="runCheckStastics-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th rowspan="2" style="text-align:center">检查人员</th>
	                                <th rowspan="2" style="text-align:center">工作记录类型</th>
	                                <th id="curMonth" colspan="8" style="text-align:center">本月</th>
	                                <th id="otCMonth" colspan="8" style="text-align:center">1月-本月 累计</th>
	                               
                                </tr>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">记录次数</th>
	                                <th style="text-align:center">检查次数</th>
	                                <th style="text-align:center">检查率%</th>
	                                <th style="text-align:center">未检查次数</th>
<!-- 	                                <th style="text-align:center">未查率%</th> -->
	                                <th style="text-align:center">合格次数</th>
	                                <th style="text-align:center">合格率%</th>
	                                <th style="text-align:center">不合格次数</th>
	                                <th style="text-align:center">不合格率%</th>
	                                <th style="text-align:center">记录次数</th>
	                                <th style="text-align:center">检查次数</th>
	                                <th style="text-align:center">检查率%</th>
	                                <th style="text-align:center">未检查次数</th>
<!-- 	                                <th style="text-align:center">未查率%</th> -->
	                                <th style="text-align:center">合格次数</th>
	                                <th style="text-align:center">合格率%</th>
	                                <th style="text-align:center">不合格次数</th>
	                                <th style="text-align:center">不合格率%</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <form id="downloadForm" class="hide" method="post" action="${ctx}/runCheck/exprotExcel">
			<input type="hidden" name="name" id="downloadName">
			<input type="hidden" name="path" id="downloadPath">
		</form>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					//统计月份
					var statisticsMonth = new A.my97datepicker({
						id: 'statisticsMonth',
						name:'statisticsMonth',
						render:'#statisticsMonthDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "${cTime}",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					<%--statisticsMonth.setMonthValueNoAddOne('${runCheckEntity.checkDate}');--%>
 					statisticsMonth.setMonthValue('${cTime}'); 
					//业务类型
					var statisticsTypecombobox = new A.combobox({
						render: "#statisticsTypeDiv",
						datasource:${workRecordCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var StatisticsDatatables = new A.datatables({
						render: '#runCheckStastics-table',
						options: {
					        "ajax": {
					            "url": format_url("/runCheck/statistics"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        fnPreDrawCallback: function (oSettings) {
					        	var curMonth = $("#statisticsMonth").val();
								$("#curMonth").text(curMonth.substr(5,2)+"月");
								$("#otCMonth").text("01月-"+curMonth.substr(5,2)+"月");

					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [{data:"id", visible:false,orderable:true}, 
								      {data: "duties",width: "auto",orderable: false,
							        	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHide=="show" ) {
								        	       	$(td).attr('rowspan',rowData.rowspanNum);
								        	       	$(td).attr('align','center');
								        	       	var height=rowData.rowspanNum*32;
								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      },
							          {width: "10%",orderable: false,"sClass": "center",
										"render": function(data, type, row){
							        	  return row.typeName ;
							          	}
							          },
							          {data: "recordCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "checkCount",width: "auto",orderable: false,"sClass": "center"}, 
							          {data: "checkRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "unCheckCount",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "unCheckRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "qualiFiedCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "qualiFiedRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "unQualiFiedCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "unQualiFiedRate",width: "auto",orderable: false,"sClass": "center"},
							          
							          {data: "oTcRecordCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcCheckCount",width: "auto",orderable: false,"sClass": "center"}, 
							          {data: "oTcCheckRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcUnCheckCount",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "oTcUnCheckRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcQualiFiedCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcQualiFiedRate",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcUnQualiFiedCount",width: "auto",orderable: false,"sClass": "center"},
							          {data: "oTcUnQualiFiedRate",width: "auto",orderable: false,"sClass": "center"},
							 ]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						if($('#statisticsMonth').val()==''){
							alert("请选择月份！");
							return;
						}
						initSearch();
				    	StatisticsDatatables._datatables.search(JSON.stringify(conditions)).draw(true);
					});
					//导出按钮
					$('#btnimport').on('click',function(){
						var params = {};
						initSearch();
						params.conditions = conditions;
					 	$.ajax({
							type: 'POST',
							contentType : 'application/json',
							url: format_url("/runCheck/createDataExcelFile"),
 							data : JSON.stringify(params),
							dataType : "JSON",
							cache: false,
							success: function(datas){
								if(datas.tempFileName.length >0) { 
									var excelName = $("#table_titile").html();
									$("#downloadName").val(excelName);
									$("#downloadPath").val(datas.tempFileName);
					 				$("#downloadForm").submit();
								}
							},
							error: function(datas){
								console.log(datas);
							}
						}); 
					});
					$('#btnReset').on('click',function(){
						$('#statisticsMonth').val('');
						$('#statisticsTypeDiv').val('');
						$('#statisticsTypeDiv').trigger("chosen:updated");
					});
					function initSearch(){
						conditions = [];
						//统计月份
				    	if($('#statisticsMonth').val()){
	    					conditions.push({
	        					field: 'date_format(L.C_GIVE_DATE,"%Y-%m")',
	        					fieldType:'DATE',
	        					matchType:'EQ',
	        					value:$('#statisticsMonth').val()
	        				});
						}
						//检查统计类型
				    	if(statisticsTypecombobox.getValue()!=null&&statisticsTypecombobox.getValue()+""!=""){
							if(statisticsTypecombobox.getValue()=='1'){
								conditions.push({
		        					field: 'SD.C_CODE',
		        					fieldType:'INT',
		        					matchType:'IN',
		        					value:'2,3,4,5,6,7,8'
		        				});
							}else{
								conditions.push({
		        					field: 'SD.C_CODE',
		        					fieldType:'INT',
		        					matchType:'IN',
		        					value:$('#statisticsTypeDiv').val()+","
		        				});
							}
						}
					}
				});
			});
        </script>
    </body>
</html>