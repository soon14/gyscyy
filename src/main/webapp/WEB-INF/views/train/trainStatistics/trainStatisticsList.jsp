<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style>
			.dateInputOther {display:inline-block;width:150px;}
			.toLabel {
				    display: inline-block;
				    width: 16px;
				    text-align: center;
				    float: left;
				    margin-top: 4px;
				}
				.search-content {
				    padding-left: 3%;
				    padding-top: 20px;
				    height: 78px;
				}
				.buttonClass{
				    position: absolute;
				    right: 0;
				    top: 12px;
				}
		</style>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>生产技术培训管理</li>
				<li class="active">培训统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div id="divTimespan" style="float: left; width: 70px;" class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
					<label class="searchLabel" style="width:60px" for="form-field-1">办班单位：</label> 
				</div>
				<div style="width: 200px; float: left; margin-right: 60px;line-height:34px">
						<!-- <div id="searchunitId" class="input-width padding-zero  text-left"  style="width: 200px;height:34px;"> </div> -->
						<select id="searchunitId"  class="form-control chosen-select" style="width: 200px;height:34px;"></select>
				</div>
				<div id="divTimespan" style="float: left; width: 70px;" class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
					<label class="searchLabel" style="width:60px" for="form-field-1">时间维度：</label> 
				</div>
					<div style="float: left; margin-right: 60px;line-height:34px">
						<select id="timespan" style="width: 120px;height:34px;">
							<option value="month">月度</option>
							<option value="quarter">季度</option>
							<option value="year">年度</option>
							
						</select> &nbsp;
					</div>
					<div style="float: left; margin-right:60px;display:none" id="quarterConditonDiv">
						<div id="divTimeArea" style="float: left; width: 60px;" class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">季度：</label>
						</div>
						<div style="float: left;line-height:34px">
							<select id="quarter_select_area1" style="width:120px;height:34px;line-height:34px">
								<option value="1">第一季度</option>
								<option value="2">第二季度</option>
								<option value="3">第三季度</option>
								<option value="4">第四季度</option>
							</select>
						</div>
						<label class="toLabel" id="signQuarter">~</label>
						<div style="float: left;line-height:34px">
							<select id="quarter_select_area2" style="width:120px;height:34px;line-height:34px">
								<option value="1">第一季度</option>
								<option value="2">第二季度</option>
								<option value="3">第三季度</option>
								<option value="4">第四季度</option>
							</select>
						</div>
					</div>
					<div>
						<div id="divTimeArea" style="float: left; width: 60px;" class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">时间：</label>
						</div>
						<div id="divStartDatePicker" style="float: left; width: 150px;  height:34px;"></div>
						<label class="toLabel" id="sign">~</label>
							<div class="dateInputOther padding-zero text-left">
	                        	<div id="divEndDatePicker" style="border:none; padding:0px;height:30px;"></div>
	                        </div>
					</div>
				<div class="form-inline buttonClass" role="form">
                   <div  style="float:right;">
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
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >培训统计表</h5>
						<table id="trainStatistics_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>办班单位</th>
	                                <th>时间</th>
	                                <th>培训总次数</th>
	                                <th>定期培训次数</th>
	                                <th>非定期培训次数</th>
	                                <th>总培训人数</th>
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
					var conditions=[],dd=[];
					var exportExcel="";
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
					
					var combobox = new A.combobox({
						render: "#searchunitId",
						datasource: ${unitNameIdTreeList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: false,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//统计月开始日期
					var startDatePicker = new A.my97datepicker({
						id:"startDatePicker",
						render : '#divStartDatePicker',
						options : {
							maxDate: "#F{$dp.$D(\\'endDatePicker\\')}",
							dateFmt : "yyyy-MM"
						}
					}).render();
					startDatePicker.setValue(getMonth());
					//统计月结束日期
					var endDatePicker = new A.my97datepicker({
						id:"endDatePicker",
						render : '#divEndDatePicker',
						options : {
							minDate: "#F{$dp.$D(\\'startDatePicker\\')}",
							dateFmt : "yyyy-MM"
						}
					}).render();
					endDatePicker.setValue(getMonth());
					//统计维度变更
					$("#timespan").on("change",function () {
						timeSapanChanged();
// 						var timeSpan = $("#timespan").val();
// 						var url = "/standardManagement/getSortKeyByDateType?dateType=" + timeSpan + "&indicator=" + indicatorspan ;
// 						$.ajax({
// 							url:format_url(url),
// 							type:'post',
// 							dataType: "JSON",
// 							success:function(data){
// 								if(data){
// 									var keyList = eval(data.compareKeyList);
// 									$("#compareIndicatorspan").empty();
// 									(keyList).forEach(function(obj){  
// 										$("#compareIndicatorspan").append("<option value="+obj.code+">"+obj.name+"</option>");
// 									});
// 									$("#compareIndicatorspan").trigger("change", {selected: $("#compareIndicatorspan").val()});
// 								}
// 							}
// 						});
					});

					//变换统计日期结束日期格式
					function timeSapanChanged(data){
						var dim = $("#timespan").val();
						var dateFmt;
						var value;

						if (dim == "month") {
							maxDate="%y-%M",
							dateFmt="yyyy-MM",
							value = getMonth();
							$("#quarterConditonDiv").hide();
							$("#quarter_select_area option:first").prop("selected", 'selected');
							$("#divEndDatePicker").show();
							$("#sign").show();
						}
						if (dim == "year") {
							maxDate="%y",
							dateFmt="yyyy",
							value = getYear();
							$("#quarterConditonDiv").hide();
							$("#quarter_select_area option:first").prop("selected", 'selected');
							$("#divEndDatePicker").show();
							$("#sign").show();
						}
						if (dim == "quarter") {
							maxDate="%y",
							dateFmt="yyyy",
							value = getYear();
							$("#quarterConditonDiv").show();
							$("#divEndDatePicker").hide();
							$("#sign").hide();
						}
						//统计年开始日期
						 var startDatePicker1 = new A.my97datepicker({
							id:"startDatePicker",
							render : '#divStartDatePicker',
							options : {
								maxDate: "#F{$dp.$D(\\'endDatePicker\\')}",
								dateFmt : dateFmt
							}
						}).render();
						//统计年结束日期
						 var endDatePicker1 = new A.my97datepicker({
							id:"endDatePicker",
							render : '#divEndDatePicker',
							options : {
								minDate: "#F{$dp.$D(\\'startDatePicker\\')}",
								dateFmt : dateFmt
							}
						}).render();
						
						if(1 != data){
							startDatePicker1.setValue(value);
							endDatePicker1.setValue(value);
						}
					}
					var trainStatisticsDatatables = new A.datatables({
						render: '#trainStatistics_table',
						options: {
					        "ajax": {
					            "url": format_url("/trainStatistics/seach"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	d.timespan=$('#timespan').val();
					            	d.quarter_select_area1=$('#quarter_select_area1').val();
					            	d.quarter_select_area2=$('#quarter_select_area2').val();
					            	d.time1 = $("#startDatePicker").val();
					            	d.time2 = $("#endDatePicker").val();
					            	var unitid = "";
					            	if ($("#searchunitId").val() != undefined && $("#searchunitId").val() != null ) {
					            		unitid = "" + $("#searchunitId").val();
					            	}
					            	d.unitId = unitid;
					            	d.conditions = conditions;
					            	dd=d;
					                return JSON.stringify(d);
					              }
					        },
					        
					       // multiple : true,
					       	checked:false,
							bInfo:false,
							paging:false,
							ordering: true,
							optWidth: 80,
							order:[[0,'desc']],
							columns: [
							          {data:"id", visible:false,orderable:true}, 
							          {orderable: false,"width":"10%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "unitName",width: "20%",orderable: true}, 
							          {data: "showTime",width: "15%",orderable: true}, 
							          {data: "totalCount",width: "15%",orderable: true}, 
							          {data: "regularCount",width: "15%",orderable: true}, 
							          {data: "noRegularCount",width: "15%",orderable: true}, 
							          {data: "totalPeopleCount",width: "15%",orderable: true}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/trainStatistics/exportExcel"),JSON.stringify(dd));
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
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						trainStatisticsDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#quarterConditonDiv").hide();
						$("#timespan option:first").prop("selected", 'selected');
						//统计日期
						var resertStartDatePicker = new A.my97datepicker({
							id:"startDatePicker",
							render : '#divStartDatePicker',
							options : {
								dateFmt : "yyyy-MM"
							}
						}).render();
						resertStartDatePicker.setValue(getMonth());
						//统计日期
						var resertEndDatePicker = new A.my97datepicker({
							id:"endDatePicker",
							render : '#divEndDatePicker',
							options : {
								dateFmt : "yyyy-MM"
							}
						}).render();
						resertEndDatePicker.setValue(getMonth());
						$("#divEndDatePicker").show();
						$("#sign").show();
					});
				});
			});
        </script>
    </body>
</html>