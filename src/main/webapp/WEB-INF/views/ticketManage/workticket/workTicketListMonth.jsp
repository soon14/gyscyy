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
				<li class="active">两票管理</li>
				<li class="active">电气第一种工作票</li>
			</ul>
		</div>
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
                       <h5 class="table-title-withoutbtn header smaller blue " style='margin-bottom:0px;' >电气第一种工作票列表</h5>
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
	                                <th>许可人</th>
	                                <th>许可开始工作时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
	                                <th>鉴定结果</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var workticketDialog;
			var workTicketDatatables ="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					var exportExcel="";
					var yearStart="${yearStart}";
					var yearEnd="${yearEnd}";
					var identify="${identify}";
					var qualifiedCount="${qualifiedCount}";
					var unQualifiedCount="${unQualifiedCount}";
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/workTicket/searchJanData?yearStart="+yearStart+"&yearEnd="+yearEnd+"&identify="+identify+"&qualifiedCount="+qualifiedCount+"&unQualifiedCount="+unQualifiedCount),
// 					            "url": format_url("/workTicket/searchMarData?yearStart="+yearStart+"&yearEnd="+yearEnd),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'C_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1//电气第一种工作票
		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "9%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "groupName",width: "6%",orderable: true}, 
							          {data: "guarderName",width: "8%",orderable: true}, 
							          {data: "content",width: "10%",orderable: true}, 
							          {data: "equipmentName",width: "10%",orderable: true}, 
							          {data: "changeAllowName",width: "8%",orderable: true},
							          {data: "changeAllowDate",width: "8%",orderable: true}, 
							          {data: "workStatusName",name:"workStatus",width: "10%",orderable: true}, 
							          {data: "isSetName",name:"isSet",width: "5%",orderable: true},
							          {data: "identifyName",name:"identify",width: "6%",orderable: true}],
							          
										fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/workTicket/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
										 }
					}).render();
					
					
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchcode").val()!=""){
	    					conditions.push({
	    						field: 't.C_CODE',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$("#searchcode").val()
	    					});
						}
    					if(unitNameIdCombotree.getValue()!=null
    							&&unitNameIdCombotree.getValue()!=""){
    						conditions.push({
            					field: 't.C_UNIT_NAME_ID',
            					fieldType:'STRING',
            					matchType:'EQ',
            					value:unitNameIdCombotree.getValue()
            				});
    					}
    					if($("#searchgroupId").val()!=""){
    						conditions.push({
            					field: 't.C_GROUP_ID',
            					fieldType:'STRING',
            					matchType:'EQ',
            					value:$("#searchgroupId").val()
            				});
    					}
    					
    					
    					if($("#searchguarderName").val()!=""){
	    					conditions.push({
	        					field: 't.C_GUARDER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchguarderName').val()
	        				});
    					}
    					if($("#searchcontent").val()!=""){
	    					conditions.push({
	    						field: 't.C_CONTENT',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchcontent').val()
	    					});
    					}
    					
    					if($("#equipmentWorkOneCode").val()!=null&&$("#equipmentWorkOneCode").val()!=""){
	    					conditions.push({
	        					field: 't.C_EQUIPMENT_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#equipmentWorkOneCode').val()
	        				});
    					}
    					
    					if($("#searchworkStatus").val()!=""){
	    					conditions.push({
	    						field: 't.C_WORK_STATUS',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$('#searchworkStatus').val()
	    					});
    					}
    					
    					if($("#searchallowperson").val()!=""){
	    					conditions.push({
	        					field: 't.C_CHANGE_ALLOW_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchallowperson').val()
	        				});
    					}
    					
    					
    					if($("#searchStartplandateEndDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_CHANGE_ALLOW_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#searchStartplandateEndDivId").val()+" 00:00:00"
	    					});
    					}
    					if($("#searchEndplandateEndDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_CHANGE_ALLOW_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchEndplandateEndDivId").val()+" 23:59:59"
	    					});
    					}
    					
						workTicketDatatables.draw();
					});
					
					
					$('#btnReset').on('click',function(){
						conditions=[];
						$("#searchcode").val("");
						unitNameIdCombotree.setValue();
						$("#searchgroupId").val("");
						$("#searchgroupId").trigger("chosen:updated");
						$('#searchguarderName').val('');
						$("#searchguarderName").trigger("chosen:updated");
						
						$('#searchcontent').val('');
						$('#equipmentWorkOneCode').val('');
						$('#searchworkStatus').val('');
						$("#searchworkStatus").trigger("chosen:updated");
						
						$('#searchallowperson').val('');
						$("#searchallowperson").trigger("chosen:updated");
						
						$("#searchStartplandateEndDivId").val("");
						$("#searchEndplandateEndDivId").val("");
						
						//workTicketDatatables.draw(true);
					});
					
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				
				var url =format_url("/workElectric/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							workTicketDatatables.draw(false);
						}else{
							alert(result.errorMsg);
							workTicketDatatables.draw(false);
						}
					},
					error:function(v,n){
						alert('操作失败');
						workTicketDatatables.draw(false);
					}
				});
				
			}
        </script>
    </body>
</html>