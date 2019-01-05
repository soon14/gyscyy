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
					物资管理
				</li>
				<li class="active">库存统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
            <div class=" col-xs-12 search-content" >
				<div class="form-inline text-left" role="form">
					 <div class="clearfix">
					 	
					 	<div id="divTimespan" style="float: left; width: 70px;" class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" style="width:60px" for="form-field-1">时间维度：</label> 
						</div>
						<div style="float: left; margin-right: 60px;line-height:31px">
							<select id="timespan" style="width: 220px;height:31px;">
								<option value="month">月度</option>
								<option value="year">年度</option>
							</select> &nbsp;
						</div>
					 
                        <div class="form-group" >
                            <label class="" for="equipAbnormalBill">统计时间：</label>
                        </div>
                        <div class="form-group" style="height: 31px">
                            <div id="statisticsMonthDiv"  style="width:220px;border:none; padding:0px;"></div>
                        </div>
                        <div  class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
								<label class="searchLabel" for="searchUnitName">所属部门</label>：
				                <div id="searchUnitNameDiv" class="inputWidth text-left padding-zero"></div>
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
					<h5 class='table-title header smaller blue' >库存结果统计</h5>
					<div class="widget-main no-padding" id="defectStatistics-print">
						<table id="stockStatistics-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">物资名称</th>
	                                <th style="text-align:center">规格型号</th>
	                                <th style="text-align:center">计数单位</th>
	                                <th style="text-align:center">所属部门</th>
	                                <th style="text-align:center">所属仓库</th>
	                                <th style="text-align:center">上月库存</th>
	                                <th style="text-align:center">本月入库</th>
	                                <th style="text-align:center">本月出库</th>
	                                <th style="text-align:center">本月库存</th>
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
				seajs.use(['datatables', 'confirm','combobox','my97datepicker','combotree'], function(A){
					var conditions=[];
					
					/*
					 * 获取当年 yyyy
					 */
					function getYear() {
						var today = new Date();
						var strYear = today.getFullYear();
						return ""+strYear;
					}

					//获取当月 yyyy-MM
					function getMonth() {
						var date = new Date();
						var seperator1 = "-";
						var month = date.getMonth() + 1;
						var strDate = date.getDate();
						if (month >= 1 && month <= 9) {
							month = "0" + month;
						}
						if (strDate >= 0 && strDate <= 9) {
							strDate = "0" + strDate;
						}
						var currentdate = date.getFullYear() + seperator1 + month
						return currentdate;
					}
					//统计维度变更
					$("#timespan").on("change",function () {
						timeSapanChanged();
					});
					//单位下拉树
					var unitNameTreeData = ${unitNameIdTreeList};
					var searchunitId = new A.combotree({
						render: "#searchUnitNameDiv",
						name: 'unitId',
						id:'unitId',
						//返回数据待后台返回
						datasource: unitNameTreeData,
						width:"110px",
						options: {
							treeId: 'searchunitId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					//变换统计日期结束日期格式
					function timeSapanChanged(data){
						var dim = $("#timespan").val();
						var dateFmt;
						var value;

						if (dim == "month") {
							maxDate="%y-%M",
							dateFmt="yyyy-MM",
							value = getMonth();
						}
						if (dim == "year") {
							maxDate="%y",
							dateFmt="yyyy",
							value = getYear();
						}
						//统计日期
						 var startDatePicker1 = new A.my97datepicker({
							id: 'statisticsMonth',
							name:'statisticsMonth',
							render : '#statisticsMonthDiv',
							options : {
								dateFmt : dateFmt
							}
						}).render();
						
						if(1 != data){
							startDatePicker1.setValue(value);
						}
					}
					
					//统计月份
					var statisticsMonth = new A.my97datepicker({
						id: 'statisticsMonth',
						name:'statisticsMonth',
						render:'#statisticsMonthDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					<%--statisticsMonth.setMonthValueNoAddOne('${runCheckEntity.checkDate}');--%>
 					statisticsMonth.setMonthValue('${giveDate}'); 
					var exportExcel="";
					var StatisticsDatatables = new A.datatables({
						render: '#stockStatistics-table',
						options: {
					        "ajax": {
					            "url": format_url("/stockStatictisPrint/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.timespan=$('#timespan').val();
					            	d.unitId=searchunitId.getValue();
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [
							          {data: "materialName",width: "12%",orderable: false},
							          {data: "materialType",width: "12%",orderable: false},
							          {data: "technicalUnit",width: "12%",orderable: false},
							          {data: "unitName",width: "12%",orderable: false,"sClass": "center"},
							          {data: "warehouse",width: "12%",orderable: false,"sClass": "center"},
							          {data: "lastMonthStock",width: "10%",orderable: false,"sClass": "center"}, 
							          {data: "monthInstockNum",width: "10%",orderable: false,"sClass": "center"}, 
 							          {data: "monthOutstockNum",width: "10%",orderable: false,"sClass": "center"}, 
 							          {data: "monthStock",width: "10%",orderable: false,"sClass": "center"}
							 ],
							 fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 if(exportExcel){
									 var giveDate1 = $('#statisticsMonth').val();
									 var timespan = $('#timespan').val();
									 var unitId=searchunitId.getValue();
									 exportExcels(format_url("/stockStatictisPrint/exportExcel?giveDate1="+giveDate1+"&timespan="+timespan+"&unitId="+unitId));
								 }
								 exportExcel="";
							 },
							 toolbars: [{
									id:"dc",
	        						label:"导出",
	        						icon:"glyphicon glyphicon-download",
	        						className:"btn-primary",
	        						events:{
	            						click:function(event){
	            							if($('#statisticsMonth').val()==''){
	            								alert("请选择统计时间！");
	            								return;
	            							}
	            							StatisticsDatatables.draw();
	            							exportExcel="exportExcel";
	            						}
	        						}
	        					},{
	        						id: "print",
	        						label:"打印",
	        						icon: "glyphicon glyphicon-print",
	        						className: "btn-primary",
	        						events:{
	        							click: function(event, nRow, nData){
	        								if(searchunitId.getValue()==null){
	        									alert("请选择所属部门再进行打印！");
	        									return;
	        								}else{
	        									var unitId = searchunitId.getValue();
	        								}
	        								var dim = $("#timespan").val();
	        								if(dim=="month"){
	        									var time = $('#statisticsMonth').val();
		        								var timeDate = new Date(time.replace(/-/g,"/"));
		        								timeDate = new Date((timeDate/1000+86400)*1000);
		        								if(timeDate.getMonth()+1<9){
		        									nextTime = timeDate.getFullYear()+"-"+"0"+(timeDate.getMonth()+2);
		        								}else{
		        									nextTime = timeDate.getFullYear()+"-"+(timeDate.getMonth()+2);
		        								}
		        								
		        								birtPrintThree("scraplibraryStatics.rptdesign",time,nextTime,unitId);
	        								}else{
	        									var time = $('#statisticsMonth').val();
	        									var timeDate = new Date(time.replace(/-/g,"/"));
		        								timeDate = new Date((timeDate/1000+86400)*1000);
		        								nextTime = timeDate.getFullYear()+1;
		        								birtPrintThree("scraplibraryStatics.rptdesign",time,nextTime,unitId);
	        								}
	        								
	        								
	        							}
	        						}
	        					}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						if($('#statisticsMonth').val()==''){
							alert("请选择统计时间！");
							return;
						}
						var url = format_url("/stockStatictisPrint/statistics/"+$('#statisticsMonth').val());
				    	StatisticsDatatables._datatables.ajax.url(url).load();
				    	
					});
					$('#btnReset').on('click',function(){
						$('#statisticsMonth').val('');
						$('#statisticsTypeDiv').val('');
						$('#statisticsTypeDiv').trigger("chosen:updated");
						searchunitId.setValue(undefined);
					});
					function initSearch(){
						StatisticsDatatables.draw();
					}
				});
			});
        </script>
    </body>
</html>