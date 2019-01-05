<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">电气倒闸危险因素控制卡 </h5>
						<table id="operationDanger_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>顺序</th>
	                                <th>危险因素</th>
	                                <th>控制措施</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;"></h5>
                    <h5>参加操作、监护人说明：</h5>
                    <h5>我已明白上述危险因素和掌握控制措施，在操作过程中我将严格执行。</h5>
                    <div class="form-horizontal" style="margin-right:100px;margin-top: 20px;" >
							<div class="form-group ">
								<label class="col-md-2 control-label no-padding-right">
									操作人
								</label>
								<div class="col-md-2">
									 <input  id="operateId" class="col-md-12" type="text" disabled="disabled"/>
								</div>
								<label class="col-md-2 control-label no-padding-right">
									监护人
								</label>
								<div class="col-md-2">
									 <input  id="guardianId" class="col-md-12" type="text" disabled="disabled"/>
								</div>
							</div>
					</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					$("#operateId").val($("#operateName").val());
					$("#guardianId").val($("#guardianName").val());
					var conditions=[];
					var operationDangerDatatables = new A.datatables({
						render: '#operationDanger_table',
						options: {
					        "ajax": {
					            "url": format_url("/operationDanger/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	if($('#id').val()){
				    					conditions.push({
				        					field: "operationId",
				        					fieldType:'STRING',
				        					matchType:'EQ',
				        					value:$('#id').val()
				        				});
									}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked:false,
							paging:false,
							bInfo:false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "careCard",width: "auto",orderable: true}, 
							          {data: "startPicId",width: "auto",orderable: true}
							          ],
						}
					}).render();
				});
			});
        </script>
    </body>
</html>