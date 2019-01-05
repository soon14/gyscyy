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
					安全管理
				</li>
				<li class="active">安全月报、发文、会议</li>
				<li class="active">安全月报</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchcode">名称</label>：
	                    <input id="searchName" class="inputWidth text-left" placeholder="请输入名称" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                 <label class="searchLabel" for="searchScrapSource">年号</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchYearNum" style="border:none; padding:0px;height:30px;width:260px"></div>
	                    </div>
	                </div>
	            
<!--                    <div class="form-group" style="float:right; margin-right:-13px;"> -->
				<div class="form-group col-lg-6 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
<!-- 					</div> -->
					</div>
				</div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >安全月报</h5>
						<table id="safeReport_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>序号</th>
	                                <th>名称</th>
	                                <th>单位</th>
	                                <th>年号</th>
	                                <th>时间</th>
	                                <th>填写人</th>
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
					var exportExcel="";
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
					var safeReportDatatables = new A.datatables({
						render: '#safeReport_table',
						options: {
					        "ajax": {
					            "url": format_url("/safeReport/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	dd=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}},
							          {data: "name",width: "auto",orderable: true}, 
							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "yearNumString",name:"yearNum",width: "auto",orderable: true}, 
							          {data: "timeString",name:"time",width: "auto",orderable: true},
							          {data: "userName",name:"userId",width: "auto",orderable: true}, ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/safeReport/exportExcel"),JSON.stringify(dd));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
        							click:function(event){
                						listFormDialog = new A.dialog({
                    						width:800 ,
                    						height:460 ,
                    						title: "安全月报增加",
                    						url:format_url('/safeReport/getAdd'),
                    						closed: function(){
                    							safeReportDatatables.draw(false);
                    						}	
                    					}).render()
            						}
//             						click:function(event){
//             							A.loadPage({
// 											render : '#page-container',
// 											url : format_url('/safeReport/getAdd')
// 										});
//             						}
        						}
        					}
							, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = safeReportDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].userId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = '${sysUserEntity.id}';
										var loginName = '${sysUserEntity.loginName}'
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/safeReport/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													safeReportDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							},{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSearch').click();
            							exportExcel="exportExcel";
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.userId!=userId&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										A.loadPage({
// 											render : '#page-container',
// 											url : format_url("/safeReport/getEdit/"+id)
// 										});
// 									}
							click: function(event, nRow, nData){
								var id = nData.id;
								listFormDialog = new A.dialog({
									width:800 ,
									height:460 ,
									title: "安全月报修改",
									url:format_url('/safeReport/getEdit/' + id),
									closed: function(){
										safeReportDatatables.draw(false);
									}
								}).render();
							}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserEntity.id}
									var loginName = '${sysUserEntity.loginName}'
									if(data.userId!=userId&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var workPerson = nData.userId;
										var loginUser = '${sysUserEntity.id}';
									
			 							if(workPerson!=loginUser){
			 								alert("当前登陆人不是申请人不能删除!");
			 								return;
			 							}else{
										var id = nData.id;
										var url =format_url('/safeReport/delete/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											safeReportDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
			 							}
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
									listFormDialog = new A.dialog({
										width:800 ,
										height:460 ,
										title: "安全月报查看",
										url:format_url('/safeReport/getDetail/' + id),
										closed: function(){
											safeReportDatatables.draw(false);
										}
									}).render();
// 									A.loadPage({
// 										render : '#page-container',
// 										url : format_url("/safeReport/getDetail/"+ id)
// 									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchName").val()!=""){
	    					conditions.push({
	        					field: 't.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchName').val()
	        				});
    					}
						if($("#searchYearNumId").val()!=""){
	    					conditions.push({
	    						field: 't.C_YEAR_NUM',
	    						fieldType:'DATE',
	    						matchType:'EQ',
	    						value:$("#searchYearNumId").val()+"-01-01 00:00:00"
	    					});
    					}
						safeReportDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchName").val("");
						$("#searchYearNumId").val("");
					});
				});
			});
        </script>
    </body>
</html>