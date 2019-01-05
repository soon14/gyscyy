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
                       <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">设备异动列表</h5>
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
	                                <th>异动单号</th>
	                                <th>单位名称</th>
	                                <th>异动说明</th>
	                                <th>设备编号</th>
	                                <th>开始时间</th>
	                                <th>结束时间</th>
	                                <th>申请人</th>
	                                <th>状态</th>
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
					            "url": format_url("/equipAbnormal/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_EQUIPMENT_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:$('#equipId').val()
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "equipAbnormalBill",width: "10%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "abnormalDesc",width: "30%",orderable: true},
							          {data: "equipmentCode",width: "10%",orderable: true},
							          {data: "planBeginDate",width: "10%",orderable: true}, 
							          {data: "planEndDate",width: "10%",orderable: true}, 
							          {data: "applyUserName",width: "10%",orderable: true}, 
							          {data: "processStatusName",width: "10%",orderable: true}],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>