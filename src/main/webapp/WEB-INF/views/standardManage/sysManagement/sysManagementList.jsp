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
					标准管理
				</li>
				<li class="active">制度管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
				<div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchcode">制度编号</label>：
	                    <input id="searchcode" class="inputWidth text-left" placeholder="请输入制度编号" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">制度名称</label>：
	                    <input id="searchSysName" class="inputWidth text-left" placeholder="请输入制度名称" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchgroupId">年号</label>：
	                    <div class="inputWidth padding-zero  text-left">
	                        <div id="searchYearNum" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel " for="searchguarderName">状态</label>：
	                    <div class="padding-zero inputWidth  text-left">
						 <select id="searchStatus"  ></select>
						 </div>
	                    
	                </div>
	
	            </div>
                   <div class="form-group" style="float:right; margin-right:-13px;">
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
						<h5 class='table-title header smaller blue' >制度管理</h5>
						<table id="sysManagement_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
<!-- 	                                <th>主键</th> -->
 									<th>序号</th>
	                                <th>制度编号</th>
	                                <th>制度名称</th>
	                                <th>年号</th>
	                                <th>实施日期</th>
	                                <th>类别</th>
	                                <th>单位名称</th>
	                                <th>填报人</th>
	                                 <th>状态</th>
									<th>归口单位</th>
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
					var statusCombobox = new A.combobox({
						render: "#searchStatus",
						datasource:${sysmanagementStatusCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var searchYearNum = new A.my97datepicker({
						id: 'searchYearNumId',
						name:'yearNum',
						render:'#searchYearNum',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					var sysManagementDatatables = new A.datatables({
						render: '#sysManagement_table',
						options: {
					        "ajax": {
					            "url": format_url("/sysManagement/search"),
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
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "code",width: "auto",orderable: true}, 
							          {data: "sysName",width: "auto",orderable: true}, 
							          {data: "yearNumString",name:"yearNum",width: "auto",orderable: true}, 
							          {data: "materialDate",width: "auto",orderable: true}, 
							          {data: "typeName",name:"type",width: "auto",orderable: true}, 
							          {data: "unitName",width: "auto",orderable: true}, 
							          {data: "applyUserName",name:"applyUserId",width: "auto",orderable: true},
							          {data: "statusName",name:"status",width: "auto",orderable: true}, 
										{data: "centralizedUnitName",name:"status",width: "auto", orderable: true}
							],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url("/sysManagement/getAdd")
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = sysManagementDatatables.getSelectRowDatas();
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
										var url = format_url('/sysManagement/allDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													sysManagementDatatables.draw(false);
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
										A.loadPage({
											render : '#page-container',
											url : format_url("/sysManagement/getEdit/"+id)
										});
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
										var url =format_url('/sysManagement/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											sysManagementDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id:"start",
						 	label: "启用",
							icon: "fa fa-check bigger-130",
							className: "blue search",
							render: function(btnNode, data){
								var status = data.status;
								if(status==0){
									btnNode.hide();
								}
		                      },
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var status = nData.status;
									var unitId=nData.unitId;
									var url =format_url('/sysManagement/resultConfirmStart/'+ id );
										A.confirm('您确定要启用该制度吗？',function(){
											$.ajax({
												url : url,
	    										contentType : 'application/json',
	    										dataType : 'JSON',
	    										type : 'POST',
	    										success: function(result){
	    											if(result.result=='success'){
	    												sysManagementDatatables.draw(false);
	        											alert('启用成功');
	    											}
	    										},
	    										error:function(v,n){
	    											alert('操作失败');
	    										}
											});
										});
								}
							}
					}, {
							id:"stop",
						 	label: "弃用",
							icon: "fa fa-times bigger-130",
							className: "blue search",
							render: function(btnNode, data){
								var status = data.status;
								if(status==1){
									btnNode.hide();
								}
		                      },
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var status = nData.status;
									var url =format_url('/sysManagement/resultConfirmStop/'+ id );
										A.confirm('您确定要弃用该制度吗？',function(){
											$.ajax({
												url : url,
	    										contentType : 'application/json',
	    										dataType : 'JSON',
	    										type : 'POST',
	    										success: function(result){
	    											sysManagementDatatables.draw(false);
	    											alert('弃用成功');
	    										},
	    										error:function(v,n){
	    											alert('操作失败');
	    										}
											});
										});
								}
							}
					},
						{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/sysManagement/getDetail/"+ id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchStatus").val()!=""){
	    					conditions.push({
	        					field: 't.C_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchStatus').val()
	        				});
    					}
						if($("#searchcode").val()!=""){
	    					conditions.push({
	        					field: 't.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchcode').val()
	        				});
    					}
						if($("#searchSysName").val()!=""){
	    					conditions.push({
	        					field: 'C_SYS_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchSysName').val()
	        				});
    					}
						if($("#searchYearNumId").val()!=""){
	    					conditions.push({
	    						field: 'C_YEAR_NUM',
	    						fieldType:'DATE',
	    						matchType:'EQ',
	    						value:$("#searchYearNumId").val()+"-01-01 00:00:00"
	    					});
    					}
						sysManagementDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchStatus").val("");
						$("#searchStatus").trigger("chosen:updated");
						$("#searchcode").val("");
						$("#searchSysName").val("");
						$("#searchYearNumId").val("");
						$("#searchYearNumId").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>