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
                        <div id="tableTitle"></div>
						<table id="runRecord-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>								
	                                <th>序号</th>
	                                <th>记录类型</th>
	                                <th>记录时间</th>
	                                <th>单位名称</th>
	                                <th>负责人</th>	                                	                                
	                                <th>记录内容</th>
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
					
								$('#tableTitle').html('<h5 class="table-title header smaller blue" style="margin-bottom:0px;">运行记事列表</h5>');
							
					var runRecordDatatables = new A.datatables({
						render: '#runRecord-table',
						options: {
					        "ajax": {
					            "url": format_url("/runRecord/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_RL_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${rlId}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							checked:false,
							bInfo:false,
							paging:true,
							columns: [{data:"id", visible:false,orderable:false}, 
							      	 {orderable : false, "width" : "3%", "sClass" : "center", render : function(data, type,row, meta)
											{
										 	var startIndex = meta.settings._iDisplayStart;
										 	row.start = startIndex+ meta.row;
										 	return startIndex + meta.row + 1;
								           	}
							          },
							          {data: "recordTypeName",width: "auto",orderable: true},
							          {data: "recordTime",width: "auto",orderable: true}, 
							          {data: "unitName",width: "0%",orderable: true}, 
							          {data: "fZR",width: "10%",orderable: true}, 							          
							          {data: "recordContent",width: "auto",orderable: true}],
							          toolbars : [
													{
														label : "导出",
														icon : "glyphicon glyphicon-download",
														className : "btn-primary",
														events : {
															click : function(
																	event) {
																var rlId = ${rlId};
																window.location.href = format_url("/runRecord/exportExcel/"
																		+ rlId);
															}
														}
													} ],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>