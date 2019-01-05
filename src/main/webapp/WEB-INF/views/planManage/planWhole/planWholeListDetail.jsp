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
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">整体计划表</h5>
						<table id="planWhole_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>计划开工时间</th>
	                                <th>计划完成时间</th>
	                                <th>计划总投资（万元）</th>
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
					
					var planWholeDatatables = new A.datatables({
						render: '#planWhole_table',
						options: {
					        "ajax": {
					            "url": format_url("/planWhole/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_PlAN_ID',
			        					fieldType:'STRING',
			        					matchType:'EQ',
			        					value:$("#id").val()
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "num",width: "auto",orderable: true},
							          {data: "projectName",width: "auto",orderable: true},
							          {data: "stratTime",width: "auto",orderable: true},
							          {data: "endTime",width: "auto",orderable: true}, 
							          {data: "planTotal",width: "auto",orderable: true}, 
							          {data: "remark",width: "auto",orderable: true}
							          ],
							btns: [{
								id: "edit",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 601,
											title: "整体计划查看",
											url:format_url('/planWhole/getDetail/'+id),
											closed: function(){
											}
										}).render();
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