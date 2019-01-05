<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style type="text/css">
		</style>
	</head>
	<body>
		<div id="page-content">
		<div class="page-content" >
			<div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
                       <h5 class="table-title-withoutbtn header smaller blue" style='margin-bottom:0px;' >风机工作票列表</h5>
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
	                                <th>许可时间</th>
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
             </div>
		<script type="text/javascript">
			var workticketDialog;
			var unitNameIdCombotree;
			var workTicketDatatables ="";
			var conditions=[];
			var yearStart="${yearStart}";
			var yearEnd="${yearEnd}";
			var identify="${identify}";
			var qualifiedCount="${qualifiedCount}";
			var unQualifiedCount="${unQualifiedCount}";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					
					 workTicketDatatables = new A.datatables({
						render: '#workTicket-table',
						options: {
					        "ajax": {
					            "url": format_url("/workTicketWindMechanical/searchJanData?yearStart="+yearStart+"&yearEnd="+yearEnd+"&identify="+identify+"&qualifiedCount="+qualifiedCount+"&unQualifiedCount="+unQualifiedCount),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							aLengthMenu: [20],
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
							          {data: "identifyName",name:"identify",width: "5%",orderable: true}],
					}
					}).render();
					
					
					
					$('#btnSearch').on('click',function(){
						searchFunction();
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
						$("#searchStartplandateEndDivId").val("");
						
						$("#searchEndplandateStartDivId").val("");
						$("#searchEndplandateEndDivId").val("");
						
						$("#searchcreatePersonId").empty();
						searchcreatePersonId = new A.combobox({
							render : "#searchcreatePersonId",
							datasource : ${searchuser},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
					
						$("#equipmentWorkOneCode").val("");
						
						$("#searchunitNameId").empty();
						unitNameIdCombotree = new A.combotree({
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
						
						$("#searchAllowPersonId").empty();
						searchcreatePersonId = new A.combobox({
							render : "#searchAllowPersonId",
							datasource : ${searchuser},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
						
						//workTicketDatatables.draw(true);
					});
					
				});
			});
			 function searchFunction(){
				 conditions=[];
					if($("#searchcode").val()!=""){
 					conditions.push({
 						field: 'code',
 						fieldType:'STRING',
 						matchType:'LIKE',
 						value:$("#searchcode").val().trim()
 					});
					}
					if(unitNameIdCombotree.getValue()!=null&&unitNameIdCombotree.getValue()!=""){
						conditions.push({
     					field: 'unitNameId',
     					fieldType:'STRING',
     					matchType:'EQ',
     					value:unitNameIdCombotree.getValue()
     				});
					}
					if($("#searchgroupId").val()!=""){
						conditions.push({
     					field: 'groupId',
     					fieldType:'STRING',
     					matchType:'EQ',
     					value:$("#searchgroupId").val()
     				});
					}
					
					//负责人
					if($("#searchcreatePersonId").val()!=""){
 					conditions.push({
     					field: 'guarderId',
     					fieldType:'INT',
     					matchType:'EQ',
     					value:$('#searchcreatePersonId').val()
     				});
					}
					
					if($("#searchcontent").val()!=""){
 					conditions.push({
 						field: 'content',
 						fieldType:'STRING',
 						matchType:'LIKE',
 						value:$('#searchcontent').val().trim()
 					});
					}
					
					//设备
					if($("#equipmentWorkOneCode").val()!=""){
 					conditions.push({
     					field: 'equipmentName',
     					fieldType:'STRING',
     					matchType:'LIKE',
     					value:$('#equipmentWorkOneCode').val()
     				});
					}
					
					if($("#searchworkStatus").val()!=""){
						console.log("选择："+$('#searchworkStatus').val());
	 					conditions.push({
	 						field: 'workStatus',
	 						fieldType:'STRING',
	 						matchType:'EQ',
	 						value:$('#searchworkStatus').val()
	 					});
					}
					
					console.log("开始："+$("#searchEndplandateStartDivId").val()+" 00:00:00");
					//许可开始时间
					if($("#searchEndplandateStartDivId").val()!=""){
 					conditions.push({
 						field: 'changeAllowDate',
 						fieldType:'DATE',
 						matchType:'GE',
 						value:$("#searchEndplandateStartDivId").val()+" 00:00:00"
 					});
 					
					}
					console.log("结束："+$("#searchEndplandateEndDivId").val()+" 00:00:00");
					if($("#searchEndplandateEndDivId").val()!=""){
 					conditions.push({
 						field: 'changeAllowDate',
 						fieldType:'DATE',
 						matchType:'LE',
 						value:$("#searchEndplandateEndDivId").val()+" 23:59:59"
 					});
 					
					}
					
					//许可人
					if($("#searchAllowPersonId").val()!=""){
 					conditions.push({
     					field: 'changeAllowId',
     					fieldType:'INT',
     					matchType:'EQ',
     					value:$('#searchAllowPersonId').val()
     				});
 					console.log("许可人："+$("#searchAllowPersonId").val());
					}
					workTicketDatatables.draw();
			 }
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/workTicketWindMechanical/submit?workId="+id+"&selectUser="+selectUser);
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
        </div>
    </body>
</html>