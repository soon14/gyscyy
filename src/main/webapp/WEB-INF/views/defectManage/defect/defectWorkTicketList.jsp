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
                       <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">工作票列表</h5>
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
	                                <th>工作票编号</th>
	                                <th>单位名称</th>
	                                <th>班组</th>
	                                <th>工作负责人</th>
	                                <th>工作内容</th>
	                                <th>设备名称</th>
	                                <th>计划开始时间</th>
	                                <th>计划终了时间</th>
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
					            "url": format_url("/workTicket/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'T.C_FLAW_CODE',
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
							          {data: "code",width: "8%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "groupName",width: "6%",orderable: true}, 
							          {data: "guarderName",width: "8%",orderable: true}, 
							          {data: "content",width: "18%",orderable: true}, 
							          {data: "equipmentName",width: "10%",orderable: true}, 
							          {data: "plandateStart",width: "10%",orderable: true},
							          {data: "plandateEnd",width: "10%",orderable: true}, 
							          {data: "workStatusName",name:"workStatus",width: "10%",orderable: true}, 
							          {data: "isSetName",name:"isSet",width: "10%",orderable: true}],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>