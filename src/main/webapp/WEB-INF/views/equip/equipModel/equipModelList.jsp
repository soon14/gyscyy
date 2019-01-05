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
					设备管理
				</li>
				<li class="active">设备参数配置</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content ">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline" role="form">
				<div class="clearfix" style="width: 103%">
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="" for="searchmodelNumber">模版编号：</label>
		                <input id="searchmodelNumber" class="form-control" placeholder="请输入模版编号" type="text" style="width: 65%">
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="" for="searchequipName">设备名称：</label>
		                <input id="searchequipName" class="form-control" placeholder="请输入设备名称" type="text" style="width: 65%">
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="" for="searchspecificationModel">规格型号：</label>
		                <input id="searchspecificationModel" class="form-control" placeholder="请输入规格型号" type="text" style="width: 65%">
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
						<label class="" for="searchmanuFacturer">制造商：</label>
		                <input id="searchmanuFacturer" class="form-control" placeholder="请输入制造商" type="text" style="width: 65%">
                   </div>
                   </div>
                   <div class="clearfix" style="width: 103%">
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="searchequipType">设备类型：</label>
				  <div class="inputWidth padding-zero  text-left">
			                <select id="searchequipType" class="form-control chosen-select" style="width:150px;"></select>
	               </div>
	               </div>
	               </div>
                   <div class="form-group" style="float:right; margin:0 30 0 0;">
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
						<h5 class='table-title header smaller blue' >设备模版</h5>
						<table id="equipModel-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>模版编号</th>
	                                <th>设备名称</th>
	                                <th>规格型号</th>
	                                <th>制造商</th>
	                                <th>设备类型</th>
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
			var equipModelDatatables,listFormDialog,equipModelDatatablesSelectId,conditions=[];
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree'], function(A){
					var equipTypeCombobox = new A.combobox({
						render: "#searchequipType",
						datasource:${equipType},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					equipModelDatatables = new A.datatables({
						render: '#equipModel-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipModel/dataList"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	if(d.search.value){
					            		d.conditions=JSON.parse(d.search.value);
					            		if(d.conditions){
						            		for(var index in d.conditions){
						            			$("#"+d.conditions[index].name).val(d.conditions[index].value);
						            			$("#"+d.conditions[index].name).trigger("chosen:updated");
						            		}
					            		}
					            	}
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
 							order:[[0,"desc"]],
							searching: true,
							optWidth: 80,
							bStateSave: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:true},{data: "modelNumber",width: "0%",orderable: true}, {data: "equipName",width: "0%",orderable: true}, {data: "specificationModel",width: "0%",orderable: true}, {data: "manuFacturer",width: "0%",orderable: true},{data: "equipTypeName",name:"equipType",width: "0%",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                    					A.loadPage({
            	    						render : '#page-container',
            	    						url : format_url("/equipModel/getAdd")
            	    					});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = equipModelDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/equipModel/bulkDelete/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													equipModelDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}
//         					,{
// 								label:"导出",
// 								icon: "glyphicon glyphicon-download",
// 								className: "btn-primary",
// 								events:{
// 									click: function(event){
// 										var data = equipModelDatatables.getSelectRowDatas();
// 										$.ajax({
// 											url: format_url(),
// 											contentType : 'application/json',
// 											dataType : 'JSON',
// 											type: 'POST',
// 											data : JSON.stringify(data),
// 											success: function(result){
// 												if(result.result == 'success'){
// 													alert("操作成功");
// 													equipModelDatatables.draw(false);
// 												}else if (result.result == 'error') {
// 													alert(result.errorMsg);
// 												}
// 											}
// 										})
// 									}
// 								}
// 							}
							],
							btns: [{
								id: "equipModeledit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										equipModelDatatablesSelectId = nData.id;
										A.loadPage({
            	    						render : '#page-container',
            	    						url:format_url('/equipModel/getEdit/' + equipModelDatatablesSelectId)
            	    					});
									}
								}
							}, {
								id:"equipModeldel",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/equipModel/delete/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											equipModelDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							 id:"equipModeldetail",
								label:"查看",
								icon: "fa fa-binoculars bigger-110",
								className: "blue",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url:format_url("/equipModel/detail/"+ id),
										});
									}
								}
						}
					]}
					}).render();
					//查询
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchmodelNumber").val()){
	    					conditions.push({
	        					field: 'C_MODEL_NUMBER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchmodelNumber',
	        					value:$("#searchmodelNumber").val()
	        				});
						}
						if($("#searchequipName").val()){
	    					conditions.push({
	        					field: 'C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchequipName',
	        					value:$("#searchequipName").val()
	        				});
						}
						if($("#searchspecificationModel").val()){
	    					conditions.push({
	        					field: 'C_SPECIFICATION_MODEL',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchspecificationModel',
	        					value:$("#searchspecificationModel").val()
	        				});
						}
						if($("#searchmanuFacturer").val()){
	    					conditions.push({
	        					field: 'C_MANU_FACTURER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'value:searchmanuFacturer',
	        					value:$("#searchmanuFacturer").val()
	        				});
						}
						if($("#searchequipType").val()){
	    					conditions.push({
	        					field: 'C_EQUIP_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchequipType',
	        					value:$("#searchequipType").val()
	        				});
						}
						equipModelDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					//重置
					$('#btnReset').on('click',function(){
						$('#searchmodelNumber').val("");	
						$('#searchequipName').val("");
						$('#searchspecificationModel').val("");
						$('#searchmanuFacturer').val("");
						$('#searchequipType').val("");
						$('#searchequipType').trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>