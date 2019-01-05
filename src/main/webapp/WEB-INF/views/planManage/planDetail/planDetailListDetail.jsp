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
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">详细计划表</h5>
						<table id="planDetail_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>序号</th>
	                                <th>项目名称</th>
	                                <th>具体项目明细</th>
	                                <th>方案、措施</th>
	                                <th>计划开工时间</th>
	                                <th>计划完成时间</th>
	                                <th>计划总投资（万元）</th>
	                                <th>完成状态</th>
	                                <th>备注</th>
                                    <th>操作</th>
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
					
					var planDetailDatatables = new A.datatables({
						render: '#planDetail_table',
						options: {
					        "ajax": {
					            "url": format_url("/planDetail/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions=[];
					            	conditions.push({
			        					field: 'C_PLAN_WHOLE_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:${planWholeId}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 40,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "num",width: "auto",orderable: true},
									  {data: "projectName",width: "auto",orderable: true},
									  {data: "projectDetail",width: "auto",orderable: true}, 
									  {data: "step",width: "auto",orderable: true},
									  {data: "stratTime",width: "auto",orderable: true},
									  {data: "endTime",width: "auto",orderable: true},
									  {data: "planTotal",width: "auto",orderable: true},
									  {data: "finishName",name: "finish",width: "auto",orderable: true},
									  {data: "remark",width: "auto",orderable: true},
									  ],
							btns: [ {
								id:"upload",
								label:"下载",
								icon: "glyphicon glyphicon-download-alt",
								className: "blue",
								render: function(btnNode, data){
									if(data.fileId=="[]"){
										btnNode.hide();
									};
								},
								events:{
									click: function(event, nRow, nData){
											$("#downloadForm").attr("action",format_url("/planDetail/upload/" + nData.id));
											$("#downloadForm").submit();
									}
								}
						}]
						}
					}).render();
				});
			});
        </script>
    </body>
</html>