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
				<li class="active">预算管理</li>
				<li class="active">预算填报</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
                   <div class="clearfix" >
                   
                      <div class="form-group  col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" style="height: 31px;">
							<label class="searchLabel" >年度</label>：
							<div class="padding-zero inputWidth  text-left">
		                		<div id="searchYearStartDiv" style="border:none; padding:0px;"></div>
                   			</div>
		          		 </div>
						<div class="form-group col-lg-9 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                            <button id="btnSearch" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-search"></i>
                           		     查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary" >
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
					<div class="widget-main no-padding" style="margin-top: 20px;">
						<label id="table_titile" style="font-weight: bold;margin-top:0px!important;color: #478FCA;">${giveDate}年度${organizationEntity.name }预算执行情况表</label>
						<h5 class='table-title header smaller blue' style="margin-top:0px!important"></h5>
						<table id="budgetManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>项目</th>
	                                <th>本年发生数</th>
	                                <th>本年预算数</th>
	                                <th>执行预算差额</th>
	                                <th>执行率</th>
<!-- 	                                <th>单位</th> -->
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
					var unitId = ${unitId};
					
					//采购时间 
					var searchPlanDateStartDiv = new A.my97datepicker({
						id: 'searchYearStartDivId',
						name:'year',
						render:'#searchYearStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					searchPlanDateStartDiv.setValue('${giveDate}');
					
					//发送ajax请求参数取得方法，返回选择值
					function getAjaxParam () {
						//统计时间
						var searchYear = $("#searchYearStartDivId").val();
						var param = {};
						param.year = searchYear;
						
						return param;
					}
					var param = getAjaxParam ();
					
					var budgetManageDatatables = new A.datatables({
						render: '#budgetManage_table',
						options: {
					        "ajax": {
					            "url": format_url("/budgetManage/searchData"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'C_UNIT_ID',
				     					fieldType:'INT',
				     					matchType:'EQ',
				     					value:unitId
				     				});
					            	d.param = param;
// 					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "projectName",width: "auto",orderable: true},
							          {data: "occurNum",width: "auto",orderable: true},
							          {data: "budgetNum",width: "auto",orderable: true}, 
							          {data: "differentNum",width: "auto",orderable: true}, 
							          {data: "implementRate",width: "auto",orderable: true}, 
// 							          {data: "unitId",width: "auto",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: '850',
                    						height: '400',
                    						title: "预算管理增加",
                    						url:format_url('/budgetManage/getAdd/'+unitId),
                    						closed: function(){
                    							budgetManageDatatables.draw(false);
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
										var data = budgetManageDatatables.getSelectRowDatas();
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
										var url = format_url('/budgetManage/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													budgetManageDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{
								label:"返回",
								icon: "fa fa-reply",
								className: "btn-primary ",
								events:{
									click: function(event, nRow, nData){
										A.loadPage({
											render : '#page-container',
											url : format_url("/purchaseOrganization/index")
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
											width: '850',
											height: '400',
											title: "预算管理编辑",
											url:format_url('/budgetManage/getEdit/' + id+'/'+unitId),
											closed: function(){
												budgetManageDatatables.draw(false);
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
										var url =format_url('/budgetManage/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											budgetManageDatatables.draw(false);
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
						if($("#searchYearStartDivId").val()!=""){
							conditions.push({
								field: 'C_YEAR',
								fieldType:'DATE',
								matchType:'EQ',
								value:$("#searchYearStartDivId").val()+"-01-01 00:00:00"
							});
						}
						
						var searchYear = $("#searchYearStartDivId").val();
						var yearText = searchYear + "年度";
						$("#table_titile").html(yearText + '${organizationEntity.name }'+"预算执行情况表");
						
						param = getAjaxParam ();
						
						budgetManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchYearStartDivId").val("");
						$("#searchYearStartDiv").val("");
					});
				});
			});
        </script>
    </body>
</html>