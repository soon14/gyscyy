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
				<li> 基本信息</li>
				<li class="active">值次管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
                        <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                 		<div  class="clearfix groupDiv">
						<label class="searchLabel" for="form-field-1">名称</label>：
		                <input id="searchname" class="inputWidth text-left"  maxlength="32" placeholder="请输入名称" type="text" >
                  		 </div>
		                <div  class="clearfix  groupRightDiv">
						<label class="searchLabel" for="form-field-1">编码</label>：
		                <input id="searchcode" class="inputWidth text-left"  maxlength="32" placeholder="请输入编码" type="text" >
                  		 </div>
                  		</div>
	             	 <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
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
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >值次</h5>
						<table id="dutyOrder_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>名称</th>
	                                <th>编码</th>
	                                <th>组织机构</th>
	                                <th>值长</th>
	                                <th>组员</th>
	                                <th>备注</th>
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
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var dutyOrderDatatables = new A.datatables({
						render: '#dutyOrder_table',
						options: {
					        "ajax": {
					            "url": format_url("/dutyOrder/search"),
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
							columns: [{data:"id", visible:false,orderable:false}, {data: "name",width: "0%",orderable: true}, {data: "code",width: "0%",orderable: true}, {data: "unitName",width: "0%",orderable: true}, {data: "teamLeaderName",width: "0%",orderable: true}, {data: "teamMember",width: "0%",orderable: true}, {data: "remark",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 471,
                    						title: "值次增加",
                    						url:format_url('/dutyOrder/getAdd'),
                    						closed: function(){
                    							dutyOrderDatatables.draw(false);
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
										var data = dutyOrderDatatables.getSelectRowDatas();
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
										var url = format_url('/dutyOrder/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													dutyOrderDatatables.draw(false);
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
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: 850,
											height: 471,
											title: "值次编辑",
											url:format_url('/dutyOrder/getEdit/' + id),
											closed: function(){
												dutyOrderDatatables.draw(false);
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
										var url =format_url('/dutyOrder/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											dutyOrderDatatables.draw(false);
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
						if($('#searchname').val()){
        					conditions.push({
        						field: 'a.C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchname').val().trim()
        					});
						}

						if($('#searchcode').val()){
        					conditions.push({
        						field: 'a.C_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchcode').val().trim()
        					});
						}

						dutyOrderDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchname').val('')
						$('#searchcode').val('')
					});
				});
			});
        </script>
    </body>
</html>