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
                       <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">操作票列表</h5>
						<table id="workTicket-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>操作票编号</th>
	                                <th>单位名称</th>
	                                <th>操作人</th>
	                                <th>监护人</th>
	                                <th>操作任务</th>
	                                <th>工作票编码</th>
	                                <th>开始时间</th>
	                                <th>终了时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
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
					var workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/operationTicket/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'T.C_DEFECT_CODE',
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
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "15%",orderable: true}, 
							          {data: "unitName",width: "8%",orderable: true},
							          {data: "operateName",width: "5%",orderable: true},
							          {data: "guardianName",width: "5%",orderable: true},
							          {data: "workText",width: "18%",orderable: true}, 
							          {data: "workticketCode",width: "10%",orderable: true},
							          {data: "startDate",width: "10%",orderable: true}, 
							          {data: "endDate",width: "10%",orderable: true},
							          {data: "statusName",name:"status",width: "10%",orderable: true},
							          {data: "isSetName",name:"isSet",width: "10%",orderable: true}],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>