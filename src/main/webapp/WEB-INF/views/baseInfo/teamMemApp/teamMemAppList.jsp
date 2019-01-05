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
					基本信息
				</li>
				<li class="active">班次管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">名称：</label>
		                <input id="searchname" class="form-control" placeholder="请输入名称" type="text" >
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">值长：</label>
		                <div style="width:76%;display:inline-block">
							<div id="searchApplicantDiv">
							</div>	
						</div>
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
						<h5 class='table-title header smaller blue' >班次</h5>
						<table id="teamMemApp_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>创建时间</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					
					var teamLeaderIdCombobox = new A.combobox({
						render: "#searchteamLeaderId",
						datasource:'${teamLeaderIdCombobox}',
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					var flowManagerNamesDiv=new A.selectbox({
						id: 'searchApplicantDivId',
						name:'searchApplicantNames',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#searchApplicantDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					
					var teamMemAppDatatables = new A.datatables({
						render: '#teamMemApp_table',
						options: {
					        "ajax": {
					            "url": format_url("/teamMemApp/search"),
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
							columns: [{data:"id", visible:false,orderable:false}, {data: "name",width: "15%",orderable: true}, {data: "code",width: "10%",orderable: true}, {data: "createDate",width: "20%",orderable: true}, {data: "teamLeaderId",width: "10%",orderable: true}, {data: "teamMemberIds",width: "20%",orderable: true}, {data: "remark",width: "auto",orderable: true}],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 850,
                    						height: 471,
                    						title: "班次增加",
                    						url:format_url('/teamMemApp/getAdd'),
                    						closed: function(){
                    							teamMemAppDatatables.draw(false);
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
										var data = teamMemAppDatatables.getSelectRowDatas();
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
										var url = format_url('/teamMemApp/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													teamMemAppDatatables.draw(false);
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
											title: "班次编辑",
											url:format_url('/teamMemApp/getEdit/' + id),
											closed: function(){
												teamMemAppDatatables.draw(false);
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
										var url =format_url('/teamMemApp/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											teamMemAppDatatables.draw(false);
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
						if($("#searchname").val()){
    						conditions.push({
    							field: 'teamClasses.C_NAME',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#searchname").val()
    						});
						}

						if($("#searchApplicantDivId").next('input').val()){
    						conditions.push({
    							field: 'teamClasses.C_TEAM_LEADER_ID',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#searchApplicantDivId").next('input').val()
    						});
						}

						teamMemAppDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchname').val('')
						$('#searchteamLeaderId').val('')
					});
					//动态加载js
					$.getScript(format_url("/static/js/testUrl.js"),function(){
					});
				});
			});
        </script>
    </body>
</html>