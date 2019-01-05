<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="">
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
                        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">缺陷处理列表</h5>
						<table id="solve-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>消缺结果</th>
	                                <th>消缺负责人</th>
	                                <th>消缺时间</th>
	                                <th>处理情况说明</th>
	                                <th>遗留问题</th>
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
					var solveDatatables = new A.datatables({
						render: '#solve-table',
						options: {
					        "ajax": {
					            "url": format_url("/solve/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'T.C_DEFECT_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:$('#defectId').val()
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "solveResultName",width: "auto",orderable: true}, 
							          {data: "userName",width: "auto",orderable: true}, 
							          {data: "solveTime",width: "auto",orderable: true},
							          {data: "solveExplain",width: "auto",orderable: true},
							          {data: "solveProblem",width: "auto",orderable: true}],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>