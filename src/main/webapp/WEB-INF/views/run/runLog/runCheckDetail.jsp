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
						<table id="runCheck_table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<h5 class='table-title-withoutbtn header smaller blue' >运行检查列表</h5>
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>检查时间</th>
	                                <th>检查人</th>
	                                <th>工作记录类型</th>
	                                <th>检查结果</th>	                                
	                                <th>意见和建议</th>
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
					var runCheckDatatables = new A.datatables({
						render: '#runCheck_table',
						options: {
					        "ajax": {
					            "url": format_url("/runCheck/dataList"),
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
							checked:false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 	
							          {data: "checkDate",width: "auto",orderable: true}, 
							          {data: "checkPersonName",name:"checkPerson",width: "auto",orderable: true}, 
							          {data: "typeName",name:"type",width: "auto",orderable: true},							          
							          {data: "checkResultName",name:"checkResult",width: "auto",orderable: true}, 
							          {data: "suggest",width: "auto",orderable: true}]
						
						}
					}).render();
				});
			});
        </script>
    </body>
</html>