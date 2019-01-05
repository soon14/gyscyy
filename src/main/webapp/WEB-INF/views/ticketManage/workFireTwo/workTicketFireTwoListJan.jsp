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
                        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom: 0px;">二级动火工作票列表</h5>
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
	                                <th>鉴定结果</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
<!-- 		<div style="margin-right:100px;"> -->
<!-- 		        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" > -->
<!-- 							<i class="ace-icon fa fa-times"></i> -->
<!-- 							取消 -->
<!-- 						</button> -->
<!-- 		    			<button id="qxdSaveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 		    				<i class="ace-icon fa fa-floppy-o"></i> -->
<!-- 		    				确定 -->
<!-- 		    			</button> -->
<!-- 		 </div> -->
		<script type="text/javascript">
		 var data=[];
			var workticketDialog;
			var workTicketDatatables ="";
			var yearStart="${yearStart}";
			var yearEnd="${yearEnd}";
			var identify="${identify}";
			var qualifiedCount="${qualifiedCount}";
			var unQualifiedCount="${unQualifiedCount}";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
						name: 'unitNameId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					
					
					var groupIdCombobox = new A.combobox({
						render: "#searchgroupId",
						datasource:${groupIdCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#searchworkStatus',
						datasource:${statusTypes},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					var searchStartplandateStartDiv = new A.my97datepicker({
						id: 'searchStartplandateStartDivId',
						name:'planDateEnd',
						render:'#searchStartplandateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchEndplandateStartDiv = new A.my97datepicker({
						id: 'searchEndplandateStartDivId',
						name:'planDateEnd',
						render:'#searchEndplandateStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchStartplandateEndDiv = new A.my97datepicker({
						id: 'searchStartplandateEndDivId',
						name:'planDateEnd',
						render:'#searchStartplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					var searchEndplandateEndDiv = new A.my97datepicker({
						id: 'searchEndplandateEndDivId',
						name:'planDateEnd',
						render:'#searchEndplandateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: ""
						}
					}).render();
					
					
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/workTicketFireTwo/searchJanData?yearStart="+yearStart+"&yearEnd="+yearEnd+"&identify="+identify+"&qualifiedCount="+qualifiedCount+"&unQualifiedCount="+unQualifiedCount),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
							ordering: true,
							multiple : false,
							aLengthMenu: [10],
							optWidth: 280,
							columns: [
							          {data:"id", visible:false,orderable:false},
							          {data: "code",width: "9%",orderable: true}, 
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "groupName",width: "6%",orderable: true}, 
							          {data: "guarderName",width: "8%",orderable: true}, 
							          {data: "content",width: "10%",orderable: true}, 
							          {data: "equipmentName",width: "10%",orderable: true}, 
							          {data: "plandateStart",width: "8%",orderable: true},
							          {data: "plandateEnd",width: "8%",orderable: true}, 
							          {data: "workStatusName",width: "10%",orderable: true}, 
							          {data: "isSetName",width: "5%",orderable: true},
							          {data: "identifyName",width: "5%",orderable: true}],
							
					}
					}).render();
					 $("#qxdSaveBtn").on("click", function(){
							data = workTicketDatatables.getSelectRowDatas();
							if(data==undefined){
								alert("请选择一条记录!");
								return;
							}
	 						$(".bootbox-close-button.close").trigger("click");
	    				});
					
					
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
    					if(unitNameIdCombotree.getValue()!=null&&unitNameIdCombotree.getValue()!=""){
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
	        					field: 't.C_GUARDER_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
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
    					
    					if($("#searchequipmentName").val()!=""){
	    					conditions.push({
	        					field: 't.C_EQUIPMENT_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchequipmentName').val()
	        				});
    					}
    					
    					if($("#searchworkStatus").val()!=""){
	    					conditions.push({
	    						field: 't.C_WORK_STATUS',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchworkStatus').val()
	    					});
    					}
    					if($("#searchStartplandateStartDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_PLANDATE_START',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#searchStartplandateStartDivId").val()+" 00:00:00"
	    					});
    					}
    					if($("#searchEndplandateStartDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_PLANDATE_START',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchEndplandateStartDivId").val()+" 23:59:59"
	    					});
    					}
    					
    					if($("#searchStartplandateEndDiv").val()!=""){
	    					conditions.push({
	    						field: 't.C_PLANDATE_END',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#searchStartplandateEndDiv").val()+" 00:00:00"
	    					});
    					}
    					if($("#searchEndplandateEndDiv").val()!=""){
	    					conditions.push({
	    						field: 't.C_PLANDATE_END',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchEndplandateEndDiv").val()+" 23:59:59"
	    					});
    					}
    					
						workTicketDatatables.draw();
					});
					
					
					$('#btnReset').on('click',function(){
						conditions=[];
						$("#searchcode").val("");
						unitNameIdCombotree.setValue("");
						$("#searchgroupId").val("");
						$("#searchgroupId").trigger("chosen:updated");
						$('#searchguarderName').val('');
						$('#searchcontent').val('');
						$('#searchequipmentName').val('');
						$('#searchworkStatus').val('');
						$("#searchworkStatus").trigger("chosen:updated");
						
						$("#searchStartplandateStartDivId").val("");
						$("#searchEndplandateStartDivId").val("");
						
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