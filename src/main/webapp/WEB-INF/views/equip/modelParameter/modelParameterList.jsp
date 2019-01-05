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
					模版管理
				</li>
				<li class="active">设备模版</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
           	<div class="tabbable" style="margin-top:2%;">
		 		<div style="float:right; margin-top:-10px;margin-right:25px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="glyphicon glyphicon-share-alt"></i>
						返回
					</button>
				</div>	
    		</div>
           	<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >基础参数</h5>
						<table id="modelBaseParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 2%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>参数</th>
	                                <th>默认值</th>
	                                <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
             <div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >技术参数</h5>
						<table id="modelTechnologyParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 7%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>参数</th>
	                                <th>默认值</th>
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
		var modelId = '${modelId}';
			var listFormDialog;
			var modelTechnologyParameterDatatables;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog'], function(A){
					var conditions=[];
					//基础参数
					var modelBaseParameterDatatables = new A.datatables({
						render: '#modelBaseParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/modelParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            		conditions=[];
					            	if(modelId){
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions.push({field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:modelId});
					            	}else{
					            		conditions.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1});
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						title: "模版参数增加",
                    						url:format_url('/modelParameter/getAdd/'+modelId),
                    						closed: function(){
                    							modelBaseParameterDatatables.draw(false);
                    							modelTechnologyParameterDatatables.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = modelParameterDatatables.getSelectRowDatas();
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
										var url = format_url('/modelParameter/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													modelBaseParameterDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											title: "模版参数编辑",
											url:format_url('/modelParameter/getEdit/' + id),
											closed: function(){
												modelBaseParameterDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/modelParameter/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											modelBaseParameterDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					//技术参数
					modelTechnologyParameterDatatables = new A.datatables({
						render: '#modelTechnologyParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/modelParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
						            	conditions=[];
					            	if(modelId){
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:2});
					            		conditions.push({field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:modelId});
					            	}else{
					            		conditions.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1});
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											title: "模版参数编辑",
											url:format_url('/modelParameter/getEdit/' + id),
											closed: function(){
												modelTechnologyParameterDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/modelParameter/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											modelTechnologyParameterDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						modelBaseParameterDatatables.draw();
						modelTechnologyParameterDatatables.draw();
					});
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/equipModel/index/10')
						});
    				});
				});
			});
        </script>
    </body>
</html>