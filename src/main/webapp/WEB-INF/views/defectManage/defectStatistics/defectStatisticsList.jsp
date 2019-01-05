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
					缺陷管理
				</li>
				<li class="active">缺陷统计</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left" role="form">
				 <div class="clearfix">
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">单位名称</label>：
							 <div id="searchunitIdDiv" class="inputWidth text-left padding-zero"></div>
	                   </div>
<!-- 	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!--                             <label class="searchLabel" for="searchprocessStatus">设备类型</label>： -->
<!--                             <div class="input-width padding-zero  text-left"> -->
<!--                                 <select id="searchequipType"  name="searchequipType"></select> -->
<!--                             </div> -->
<!--                         </div> -->
							<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
								<label class="searchLabel" for="searchprocessStatus">设备类型</label>：
								<div class="input-width padding-zero  text-left"> 
									<select id="searchequipmentType"  name="searchequipmentType"></select>
								</div>
							</div>
			           <div class="form-group col-lg-5 col-md-5 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">统计月份</label>：
							<div class="form-group dateInputOther padding-zero text-left">
								<div id="searchstatisticsMonthDiv" style="border:none; padding:0px;"></div>
							</div>
	                   </div>
	                   <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;">
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
					<div class="widget-main no-padding" id="defectStatistics-print">
                        <h5 class="table-title header smaller blue" >缺陷统计</h5>
						<table id="defectStatistics-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th rowspan="2" style="text-align:center">单位名称</th>
	                                <th rowspan="2" style="text-align:center">缺陷类型</th>
	                                <th rowspan="2" style="text-align:center">设备类型</th>
	                                <th colspan="4" style="text-align:center">本月</th>
	                                <th colspan="4" style="text-align:center">1月-本月 累计</th>
	                               
                                </tr>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">缺陷次</th>
<!-- 	                                <th style="text-align:center">缺陷类型比率</th> -->
	                                <th style="text-align:center">已消缺</th>
	                                <th style="text-align:center">未消缺</th>
	                                <th style="text-align:center">消除率</th>
<!-- 	                                <th style="text-align:center">未消除比例</th> -->
<!-- 	                                <th style="text-align:center">挂起次数</th> -->
<!-- 	                                <th style="text-align:center">挂起比例</th> -->
	                                <th style="text-align:center">缺陷次</th>
<!-- 	                                <th style="text-align:center">缺陷类型比率</th> -->
	                                <th style="text-align:center">已消缺</th>
	                                <th style="text-align:center">未消缺</th>
	                                <th style="text-align:center">消除率</th>
<!-- 	                                <th style="text-align:center">未消除比例</th> -->
<!-- 	                                <th style="text-align:center">挂起次数</th> -->
<!-- 	                                <th style="text-align:center">挂起比例</th> -->
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
// 					var searchequipType = new A.combobox({
// 						render : "#searchequipType",
// 						datasource : ${equipType},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
					var searchequipmentType = new A.combobox({
						render : "#searchequipmentType",
						datasource : ${equipmentTypeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//部门控件下拉树
					var searchunitId = new A.combotree({
						render: "#searchunitIdDiv",
						name: 'searchunitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
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
					var searchstatisticsMonth = new A.my97datepicker({
						id: 'searchstatisticsMonth',
						name:'searchstatisticsMonth',
						render:'#searchstatisticsMonthDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM"
						}
					}).render();
					
					var defectStatisticsDatatables = new A.datatables({
						render: '#defectStatistics-table',
						options: {
					        "ajax": {
					            "url": format_url("/defectStatistics/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	d.searchstatisticsMonth=$('#searchstatisticsMonth').val();
					            	if(undefined!=searchunitId.getValue()){
					            	d.searchunitId=searchunitId.getValue();
					            	}
					            	d.searchequipmentType=$('#searchequipmentType').val();
					            	d.conditions = conditions;
					            	dd=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
// 							ordering:true,
							columns: [{data:"unitId", visible:false,orderable:false}, 
								      {data: "unitName",width: "auto",orderable: true,
							        	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHide=="show" ) {
								        	       	$(td).attr('rowspan',rowData.rowspanNum);
								        	       	$(td).attr('align','center');
// 								        	       	var height=rowData.rowspanNum*32;
// 								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      },
// 								      {data: "typeName",width: "auto",orderable: true,
// 							        	  "createdCell": function (td, cellData, rowData, row, col) { 
// 								        	     if (rowData.tdHide=="show" ) {
// 								        	       	$(td).attr('rowspan',rowData.rowspanNumber);
// 								        	       	$(td).attr('align','center');
// // 								        	       	var height=rowData.rowspanNum*32;
// // 								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
// 								        	     }else{
// 								        	    	 $(td).hide();
// 								        	     }
// 								        	}, 
// 								      },
							          {data: "typeName",width: "10%",orderable: false,"sClass": "center",
							        	  "createdCell": function (td, cellData, rowData, row, col) { 
// 							        		  console.log(td);
// 							        		  console.log(cellData);
// 							        		  console.log(rowData);
// 							        		  console.log(row);
// 							        		  console.log(col);
// 							        		  debugger;
// 								        	     if (rowData.tdHide=="show" ) {
// 								        	       	$(td).attr('rowspan',10);
// 								        	       	$(td).attr('align','center');
// 								        	       	var height=rowData.rowspanNum*7;
// 								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
// 								        	     }else{
// 								        	    	 $(td).hide();
// 								        	     }
								        	}, 
							          },
							          {width: "10%",orderable: false,"sClass": "center",
											"render": function(data, type, row){
									        	  return row.equiptypeName ;
									          	}
									          },
							          {data: "findNum",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "scale",width: "auto",orderable: false,"sClass": "center"}, 
							          {data: "solveNum",width: "auto",orderable: false,"sClass": "center"},
							          {data: "avoid",width: "auto",orderable: false,"sClass": "center"},
							          {data: "solveScale",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "avoidScale",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "hangNum",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "hangScale",width: "auto",orderable: false,"sClass": "center"},
							          
							          {data: "findNumAll",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "scaleAll",width: "auto",orderable: false,"sClass": "center"}, 
							          {data: "solveNumAll",width: "auto",orderable: false,"sClass": "center"},
							          {data: "avoidAll",width: "auto",orderable: false,"sClass": "center"},
							          {data: "solveScaleAll",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "avoidScaleAll",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "hangNumAll",width: "auto",orderable: false,"sClass": "center"},
// 							          {data: "hangScaleAll",width: "auto",orderable: false,"sClass": "center"},
							 ],
							 fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 if(exportExcel){
									 exportExcels(format_url("/defectStatistics/exportExcel"),JSON.stringify(dd));
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
// 						conditions=[];
// 						if(searchunitId.getValue()!=undefined&&searchunitId.getValue()+""!=""){
// 	    					conditions.push({
// 	        					field: 'T2.C_ID',
// 	        					fieldType:'STRING',
// 	        					matchType:'EQ',
// 	        					value:searchunitId.getValue()
// 	        				});
// 						}
// 						if($('#searchstatisticsMonth').val()){
// 	    					conditions.push({
// 	        					field: 'date_format(T.C_FIND_TIME,"%Y-%m")',
// 	        					fieldType:'STRING',
// 	        					matchType:'EQ',
// 	        					value:$('#searchstatisticsMonth').val()
// 	        				});
// 						}

						defectStatisticsDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						searchunitId.setValue(undefined);
						$('#searchstatisticsMonth').val('');
						$("#searchequipmentType").val("");
						$("#searchequipmentType").trigger("chosen:updated");
						conditions=[];
					});
				});
			});
        </script>
    </body>
</html>