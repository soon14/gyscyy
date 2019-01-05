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
					台账管理
				</li>
				<li class="active">设备台账</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
		           <div class="form-group">
						<label class="" for="form-field-1">设备编号：</label>
				   </div>
				   <div class="form-group">
		                <input id="searchequipmentNumber" class="form-control" placeholder="请输入设备编号" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">设备名称：</label>
				   </div>
				   <div class="form-group">
		                <input id="searchequipmentName" class="form-control" placeholder="请输入设备名称" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">设备类型：</label>
				   </div>
				   <div class="form-group">
		                <select id="searchequipType" class="form-control chosen-select" style="width:150px;"></select>
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">规格型号：</label>
				   </div>
				   <div class="form-group">
		                <input id="searchequipmentVersion" class="form-control" placeholder="请输入规格型号" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">制造商：</label>
				   </div>
				   <div class="form-group">
		                <input id="searchmanuFacturer" class="form-control" placeholder="请输入制造商" type="text" >
                   </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >设备台帐</h5>
						<table id="equipLedger-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>设备编号</th>
	                                <th>设备名称</th>
	                                <th>设备类型</th>
	                                <th>规格型号</th>
	                                <th>制造商</th>
	                                <th>备注</th>
	                                <th>创建人</th>
	                                <th>创建时间</th>
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
			var conditions=[],currentPage, pageSize;
			var equipLedgerDatatables;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var back = '${back}';
					var equipTypeCombobox = new A.combobox({
						render: "#searchequipType",
						datasource:${equipType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					equipLedgerDatatables = new A.datatables({
						render: '#equipLedger-table',
						options: {
					        "ajax": {
					        	"url": format_url("/equipLedger/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(conditions){
						            	d.conditions = conditions;
					            	}
					            	if(currentPage && pageSize){
										d.start = currentPage * pageSize;
										d.length = pageSize;
										currentPage = null;
										pageSize = null;
									}
					                return JSON.stringify(d);
					              }
					        },
					        fnInitComplete:function(){
								currentPage = '${currentPage}';
								pageSize = '${pageSize}';
								if(currentPage && pageSize){
									equipLedgerDatatables.setPage(currentPage, pageSize);
									currentPage = 0;
									pageSize = 10;
								}
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "code",width: "0%",orderable: true}, {data: "name",width: "0%",orderable: true}, {data: "equipTypeName",width: "0%",orderable: true}, {data: "equipmentVersion",width: "0%",orderable: true}, {data: "manuFacturer",width: "0%",orderable: true}, {data: "remark",width: "0%",orderable: true}, {data: "createUserName",width: "0%",orderable: true}, {data: "updateDate",width: "0%",orderable: true}],
							toolbars: [
							           {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
										equipLedgerloadpage = A.loadPage({
            	    						render : '#page-container',
            	    						url : format_url("/equipLedger/getAdd")
            	    					});
            						}
        						}
        					},
//         					{
// 								label:"删除",
// 								icon:"glyphicon glyphicon-trash",
// 								className:"btn-danger",
// 								events:{
// 									click: function(event){
// 										var data = equipLedgerDatatables.getSelectRowDatas();
// 										var ids = [];
// 										if(data.length && data.length>0){
// 											for(var i =0; i<data.length; i++){
// 												ids.push(data[i].id);
// 											}
// 										}
// 										if(ids.length < 1){
// 											alert('请选择要删除的数据');
// 											return;
// 										}
// 										var url = format_url('/equipLedger/'+ids);
// 										var url = format_url('/delaaa');
// 										A.confirm('您确认删除么？',function(){
// 											$.ajax({
// 												url : url,
// 												contentType : 'application/json',
// 												dataType : 'JSON',
// 												type : 'DELETE',
// 												data : JSON.stringify(ids),
// 												success: function(result){
// 													alert('删除成功');
// 													equipLedgerDatatables.draw(false);
// 												},
// 												error:function(v,n){
// 													alert('操作失败');
// 												}
// 											});
// 										});
// 									}
// 								}
// 							},
        					{
								label:"导入设备",
								icon: "glyphicon glyphicon-plus",
								className: "${appListItemEntity.style}",
								events:{
									click: function(event){
										equipLedgerloadpage= A.loadPage({
            	    						render : '#page-container',
            	    						url : format_url("/equipLedger/getAdd")
            	    					});
									}
								}
							}],
// 							btns: [
// 							       {
// 								id: "edit",
// 								label:"修改",
// 								icon: "fa fa-pencil bigger-130",
// 								className: "green edit",
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										A.loadPage({
// 											render : '#page-container',
// 											url : format_url('/equipLedger/getEdit/' + id)
// 										});
// 									}
// 								}
// 							}, 
// 							{
// 								id: "del",
// 								label:"删除",
// 								icon: "fa fa-trash-o bigger-130",
// 								className: "red",
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										console.log(id);
// 										var url =format_url('/equipLedger/del/');
// 										A.confirm('您确认删除么？',function(){
// 											$.ajax({
// 												url : url,
// 												contentType : 'application/json',
// 												dataType : 'JSON',
// 												type : 'POST',
// 												data : JSON.stringify(id),
// 												success: function(result){
// 													alert('删除成功');
// 													equipLedgerDatatables.draw(true);
// 												},
// 												error:function(v,n){
// 													alert('操作失败');
// 												}
// 											});
// 										});
// 									}
// 								}
// 							}
// 						]
						}
					}).render();
					//条件查询带回
					
					$('#btnSearch').on('click',function(){
						 searchequipmentNumber = $("#searchequipmentNumber").val();
						 searchequipmentName = $("#searchequipmentName").val();
						 searchequipType = $("#searchequipType").val();
						 searchequipmentVersion = $("#searchequipmentVersion").val();
						 searchmanuFacturer = $("#searchmanuFacturer").val();
						conditions=[];
						if(searchequipmentNumber){
	    					conditions.push({
	        					field: 'L.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchequipmentNumber
	        				});
						}
						if(searchequipmentName){
	    					conditions.push({
	        					field: 'L.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchequipmentName
	        				});
						}
						if(equipTypeCombobox.getValue()!=null&&equipTypeCombobox.getValue()+""!=""){
							conditions.push({
	        					field: 'L.C_EQUIP_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchequipType
	        				});
						}
						if(searchequipmentVersion){
							conditions.push({
	        					field: 'L.C_VERSION',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchequipmentVersion
	        				});
						}
						if(searchmanuFacturer){
							conditions.push({
	        					field: 'L.C_MANU_FACTURER',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchmanuFacturer
	        				});
						}
						equipLedgerDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchequipmentNumber').val('')
						$('#searchequipmentName').val('')
						equipTypeCombobox.setValue('');
						$('#searchequipmentVersion').val('')
						$('#searchmanuFacturer').val('')
						searchequipmentNumber = "";
						searchequipmentName = "";
						searchequipType = "";
						searchequipmentVersion = "";
						searchmanuFacturer = "";
					});
				});
			});
        </script>
    </body>
</html>