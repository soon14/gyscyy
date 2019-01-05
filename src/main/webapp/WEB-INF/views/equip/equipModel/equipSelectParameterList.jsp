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
				<li class="active">设备参数</li>
			</ul><!-- /.breadcrumb -->
		</div>
		
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
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
			<div class="row" style="overflow-Y:auto;max-height: 100px;">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >基础参数</h5>
						<table id="equipParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>参数</th>
	                                <th>默认值</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
<!--                     <div class="widget-main no-padding"> -->
<!-- 						<h5 class='table-title header smaller blue' >基础参数</h5> -->
<!-- 						<table id="equipParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 									<th class="center sorting_disabled" style="width:50px;"> -->
<!--         								<label class="pos-rel"> -->
<!--         									<input type="checkbox" class="ace" /> -->
<!--         									<span class="lbl"></span> -->
<!--         								</label> -->
<!--         							</th> -->
<!-- 	                                <th>参数</th> -->
<!-- 	                                <th>默认值</th> -->
<!--                                     <th>操作</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                         </table> -->
<!--                     </div> -->
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var equipParameterDatatables = new A.datatables({
						render: '#equipParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "id",width: "auto",orderable: true}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}, {data: "parameterType",width: "auto",orderable: true}, {data: "modelId",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: ,
                    						height: ,
                    						title: "设备参数增加",
                    						url:format_url('/equipParameter/getAdd'),
                    						closed: function(){
                    							equipParameterDatatables.draw(false);
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
										var data = equipParameterDatatables.getSelectRowDatas();
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
										var url = format_url('/equipParameter/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													equipParameterDatatables.draw(false);
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
											width: ,
											height: ,
											title: "设备参数编辑",
											url:format_url('/equipParameter/getEdit/' + id),
											closed: function(){
												equipParameterDatatables.draw(false);
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
										var url =format_url('/equipParameter/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											equipParameterDatatables.draw(false);
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
						equipParameterDatatables.draw();
					});
					$('#btnReset').on('click',function(){
					});
				});
			});
        </script>
    </body>
</html>