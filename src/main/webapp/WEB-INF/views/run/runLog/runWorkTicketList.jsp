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
                         <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">操作票列表</h5>
						<table id="czTicket-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                  <th>操作票编号</th>
	                                <th>单位名称</th>
	                                <th>操作人</th>
	                                <th>监护人</th>
	                                <th>操作任务</th>
	                                <th>工作票编码</th>
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
			var code = "";
			var type = "";
			var rlId=${rlId};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
				
					var date='${dateStr}';
					
					var workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/runLog/workTicketSearch/"+rlId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
										field: 't.C_STATUS',
				    					fieldType:'INT',
				    					matchType:'EQ',
				    					value:1
				    				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							checked:false,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "8%",orderable: true,
						               		"render":function(data, type, row, meta){
						               			var code = row.code;
						               			var type = row.type;
// 						               			console.log(data);
// 						               			console.log(row);
						               			if(row.code==null){
						               				return "<span >"+''+"</span>"
						               			}else{
						               				return "<a href='#' onclick="+"searchCode('"+code+"','"+type+"')"+">"+data+"</a>"
						               			}
					        	 				
					        	 			}}, 
							          {data: "unitName",width: "15%",orderable: true},
							          {data: "groupName",width: "6%",orderable: true}, 
							          {data: "guarderName",width: "8%",orderable: true}, 
							          {data: "content",width: "20%",orderable: true}, 
							          {data: "equipmentName",width: "10%",orderable: true}, 
							          {data: "plandateStart",width: "10%",orderable: true},
							          {data: "plandateEnd",width: "10%",orderable: true,							        	  
							        	  "createdCell": function (td, cellData, rowData, row, col) {
								        	     if (rowData.plandateEnd>=date ) {
								        	       	$(td).parent("tr").attr('style','color:red');								        	       	
								        	     }
								        	}, 
							          }, 
							          {data: "workStatusName",name:"workStatus",width: "6%",orderable: true}, 
							          {data: "isSetName",name:"isSet",width: "6%",orderable: true}],
						}
					}).render();
					var opeTicketDatatables = new A.datatables({
						render: '#czTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/runLog/opeTicketSearch/"+rlId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
										field: 't.C_STATUS',
				    					fieldType:'INT',
				    					matchType:'EQ',
				    					value:1
				    				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							checked:false,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "200px",orderable: true,
							        	  "render":function(data, type, row, meta){
					               			var code = row.code;
					               			var type = row.type;
// 						               			console.log(data);
						               			console.log(row);
					               			if(row.code==null){
					               				return "<span >"+''+"</span>"
					               			}else{
					               				return "<a href='#' onclick="+"searchOperationCode('"+code+"')"+">"+data+"</a>"
					               			}
				        	 				
				        	 			}}, 
							          {data: "unitName",width: "100px",orderable: true},
							          {data: "operateName",width: "100px",orderable: true},
							          {data: "guardianName",width: "100px",orderable: true},
							          {data: "workText",width: "100px",orderable: true}, 
							          {data: "workticketCode",width: "200px",orderable: true},
							          {data: "startDate",width: "100px",orderable: true}, 
							          {data: "endDate",width: "100px",orderable: true, "createdCell": function (td, cellData, rowData, row, col) {
							        	     if (rowData.endDate>=date ) {
								        	       	$(td).parent("tr").attr('style','color:red');								        	       	
								        	     }
								        	}, },
							          {data: "statusName",name:"status",width: "100px",orderable: true},
							          {data: "isSetName",name:"isSet",width: "100px",orderable: true}],
						}
					}).render();
					
				});
			});
			function searchCode(code,type){
				
				var rlId=${rlId};
				if(type=="1"){
					url="/workTicket/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==2){
					url="/workTicketTwo/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==3){
					url="/workTicketFire/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==4){
					url="/workTicketFireTwo/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==6){
					url="/workTicketWindMechanical/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==8){
					url="/repairTicket/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==12){
					url="/workTicketLine/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==13){
					url="/workTicketLineTwo/runDetail?code="+code+"&rlId="+rlId;
				}else if(type==14){
					url="/workHelpSafe/runDetail?code="+code+"&rlId="+rlId;
				}
				A.loadPage({
					render : '#page-container',
					url:format_url(url),
				});
			}
			function searchOperationCode(code){
				var rlId=${rlId};
				url="/operationTicket/runDetail?code="+code+"&rlId="+rlId;
					
				A.loadPage({
					render : '#page-container',
					url:format_url(url),
				});
			}
        </script>
    </body>
</html>