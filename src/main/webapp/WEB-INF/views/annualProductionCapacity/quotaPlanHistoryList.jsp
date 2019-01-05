<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
	
		<div class="page-content">			
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='header smaller blue' style="margin-bottom:0">${tableName }</h5>
						<table id="quotaPlanHistory_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">场站名称</th>
	                                <th style="text-align:center">指标名称</th>
	                                <th style="text-align:center">计划对比</th>
	                                <th style="text-align:center">1月</th>
	                                <th style="text-align:center">2月</th>
	                                <th style="text-align:center">3月</th>
	                                <th style="text-align:center">4月</th>
	                                <th style="text-align:center">5月</th>
	                                <th style="text-align:center">6月</th>
	                                <th style="text-align:center">7月</th>
	                                <th style="text-align:center">8月</th>
	                                <th style="text-align:center">9月</th>
	                                <th style="text-align:center">10月</th>
	                                <th style="text-align:center">11月</th>
	                                <th style="text-align:center">12月</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            <div style="margin-right:-10px;margin-top:30px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				关闭
    			</button>
    		</div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					var apcId = ${apcId};
					var type = ${type};
					
					var quotaPlanHistoryDatatables = new A.datatables({
						render: '#quotaPlanHistory_table',
						options: {
					        "ajax": {
					            "url": format_url("/quotaPlanHistory/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'qp.C_APC_ID',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:apcId
				     				});
					            	d.type = type;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [{data:"id", visible:false},
							          {data: "farmName",width: "8%","sClass": "center"},
							          {data: "quotaName",width: "8%","sClass": "center"}, 
							          {data: "planCompareName",width: "6%","sClass": "center"}, 
							          {data: "jan",width: "6%","sClass": "center"}, 
							          {data: "feb",width: "6%","sClass": "center"}, 
							          {data: "mar",width: "6%","sClass": "center"},  
							          {data: "apr",width: "6%","sClass": "center"},  
							          {data: "may",width: "6%","sClass": "center"},  
							          {data: "jun",width: "6%","sClass": "center"},  
							          {data: "jul",width: "6%","sClass": "center"},  
							          {data: "aug",width: "6%","sClass": "center"},  
							          {data: "sep",width: "6%","sClass": "center"},  
							          {data: "oct",width: "6%","sClass": "center"},  
							          {data: "nov",width: "6%","sClass": "center"},  
							          {data: "dec",width: "6%","sClass": "center"}
							          ]
						}
					}).render();
				});
			});
        </script>
    </body>
</html>