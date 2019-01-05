<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					两票管理
				</li>
				<li class="active">操作票</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content ">
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title-withoutbtn header smaller blue' style="margin-bottom: 0px;">操作票</h5>
						<table id="operationTicket_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>计划开始时间</th>
	                                <th>计划终了时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
	                                <th>鉴定结果</th>
<!--                                     <th>操作</th> -->
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog,operationTicketDatatables;
			var yearStart="${yearStart}";
			var yearEnd="${yearEnd}";
			var identify="${identify}";
			var qualifiedCount="${qualifiedCount}";
			var unQualifiedCount="${unQualifiedCount}";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var exportExcel="";
					 operationTicketDatatables = new A.datatables({
						render: '#operationTicket_table',
						options: {
					        "ajax": {
					            "url": format_url("/operationTicket/searchJanData?yearStart="+yearStart+"&yearEnd="+yearEnd+"&identify="+identify+"&qualifiedCount="+qualifiedCount+"&unQualifiedCount="+unQualifiedCount),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(d.search.value){
					            	d.conditions = JSON.parse(d.search.value);
					            	if(d.conditions){
						            	for(var index in d.conditions){
					            			if(d.conditions[index].type==1){
					            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
					            			$("#"+d.conditions[index].name).trigger("chosen:updated");
					            			}
					            			if(d.conditions[index].type==2){
					            				searchunitId.setValue(d.conditions[index].value);
						            		}
					            		}
					            	}
					            	}
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							bStateSave: true,
							searching: true,
							//aLengthMenu: [2],
							//iCookieDuration:'${cookieTime}',
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "code",width: "10%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "operateName",width: "10%",orderable: true},
							          {data: "guardianName",width: "10%",orderable: true},
							          {data: "workText",width: "10%",orderable: true}, 
							          {data: "workticketCode",width: "10%",orderable: true},
							          {data: "startDate",width: "10%",orderable: true}, 
							          {data: "endDate",width: "10%",orderable: true},
							          {data: "statusName",name:"status",width: "10%",orderable: true},
							          {data: "isSetName",name:"isSet",width: "5%",orderable: true},
							          {data: "identifyName",name:"identify",width: "5%",orderable: true},
							          ],
						    fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/operationTicket/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
							},
							toolbars: [],
							btns: []
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchcode').val()){
	    					conditions.push({
	        					field: 'T.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchcode',
	        					type:1,
	        					value:$('#searchcode').val()
	        				});
						}
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchoperateId').val()){
	    					conditions.push({
	        					field: 'T.C_OPERATE_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchoperateId',
	        					type:1,
	        					value:$('#searchoperateId').val()
	        				});
						}
						if($('#searchguardianId').val()){
	    					conditions.push({
	        					field: 'T.C_GUARDIAN_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchguardianId',
	        					type:1,
	        					value:$('#searchguardianId').val()
	        				});
						}
						if($('#searchStartstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartstartDate',
	        					type:1,
	    						value:$('#searchStartstartDate').val()
	    					});
						}
						if($('#searchEndstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndstartDate',
	        					type:1,
	    						value:$('#searchEndstartDate').val()
	    					});
						}
						if($('#searchStartendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartendDate',
	        					type:1,
	    						value:$('#searchStartendDate').val()
	    					});
						}
						if($('#searchEndendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndendDate',
	        					type:1,
	    						value:$('#searchEndendDate').val()
	    					});
						}
						if($('#searchworkText').val()){
	    					conditions.push({
	        					field: 'T.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchworkText',
	        					type:1,
	        					value:$('#searchworkText').val()
	        				});
						}
						if($('#searchprocessStatus').val()){
	    					conditions.push({
	        					field: 'T.C_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchprocessStatus',
	        					type:1,
	        					value:$('#searchprocessStatus').val()
	        				});
						}
						operationTicketDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchcode").val('');
						searchunitId.setValue(null);
						$("#searchoperateId").val("");
						$("#searchoperateId").trigger("chosen:updated");
						$("#searchguardianId").val("");
						$("#searchguardianId").trigger("chosen:updated");
						$("#searchprocessStatus").val("");
						$("#searchprocessStatus").trigger("chosen:updated");
						$("#searchStartstartDate").val("");
						$("#searchEndstartDate").val("");
						$("#searchStartendDate").val("");
						$("#searchEndendDate").val("");
						$("#searchworkText").val('');
					});
				});
			});
        </script>
    </body>
</html>