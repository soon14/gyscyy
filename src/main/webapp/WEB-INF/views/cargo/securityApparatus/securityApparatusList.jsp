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
				<li>物资管理</li>
				<li class="active">安全工器具管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
								<label class="searchLabel" for="searchSecurityApparatusName">工具名称</label>：
				                <input id="searchSecurityApparatusName" class="inputWidth text-left" placeholder="请输入工具名称" type="text" >
		           		 	</div>
							<div  class="clearfix  groupRightDiv">
								<label class="searchLabel" for="searchStationNameDiv">所属单位</label>：
		                		<div id="searchStationNameDiv" class="inputWidth text-left padding-zero"></div>
							</div>
						</div>
			        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" for="searchStartInstockTimeDiv">检验日期</label>：
							<div class="form-group dateInput padding-zero text-left">
			                    <div id="searchCheckStartInstockTimeDiv" style="border:none; padding:0px;"></div>
			                </div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInput padding-zero text-left">
	                            <div id="searchCheckEndInstockTimeDiv" style="border:none; padding:0px;"></div>
						    </div>
                   		 </div>
		        	</div>
		        <div class="clearfix">
		        	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<label class="searchLabel" for="searchStartInstockTimeDiv">到期时间</label>：
							<div class="form-group dateInput padding-zero text-left">
			                    <div id="searchStartInstockTimeDiv" style="border:none; padding:0px;"></div>
			                </div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInput padding-zero text-left">
	                            <div id="searchEndInstockTimeDiv" style="border:none; padding:0px;"></div>
						    </div>
                   		 </div>
                   <div class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
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
						<h5 class='table-title header smaller blue' >安全工器具管理</h5>
						<table id="securityApparatus_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">序号</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>工具名称</th>
	                                <th>创建时间</th>
	                                <th>创建人</th>
	                                <th>所属单位</th>
	                                <th>检验日期</th>
	                                <th>到期日期</th>
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
					//combotree组件
					var unitNameTreeData = ${unitNameIdTreeList};
					var searchUnitId = new A.combotree({
						render: "#searchStationNameDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: unitNameTreeData,
						width:"210px",
						options: {
							treeId: 'searchUnitId',
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
					var startCheckDatePicker = new A.my97datepicker({
						id: 'searchCheckStartTime',
						name: 'checkDate',
						render:'#searchCheckStartInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var endCheckDatePicker = new A.my97datepicker({
						id: 'searchCheckEndTime',
						name: 'checkDate',
						render:'#searchCheckEndInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchCheckStartTime\\')}",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var startDatePicker = new A.my97datepicker({
						id: 'searchStartTime',
						name: 'endDate',
						render:'#searchStartInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndTime',
						name: 'endDate',
						render:'#searchEndInstockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartTime\\')}",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var securityApparatusDatatables = new A.datatables({
						render: '#securityApparatus_table',
						options: {
					        "ajax": {
					            "url": format_url("/securityApparatus/data/list"),
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
							columns: [{data:"id", visible:false,orderable:false},
							/*  {data: "securityApparatus_id",
							 width: "auto",orderable: true}, */
							  {data: "name", width: "auto",orderable: true}, 
							  
							  {data: "createDate",width: "auto",orderable: true}, 
							   
							  {data: "createUser",name:"createUserId",width: "auto",orderable: true},
							  {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							  {data: "checkDate",width: "auto",orderable: true}, 
							  {data: "endDate",width: "auto",orderable: true}
							        ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						
            						click:function(event){
										A.loadPage({
											render : '#page-container',
											url : format_url('/securityApparatus/getAdd')
										});
            						}
            					
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = securityApparatusDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createUserId)
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = '${sysUserEntity.id}';
				                    	for(var j=0;j<userIds.length;j++){
				                            if(userIds[j]!=loginUser){
				                              alert('记录中包含不是当前登陆人的记录不能删除!');
				                              return;
				                            }
			                          	}
										var url = format_url('/securityApparatus/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													securityApparatusDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}, {
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										$('#btnSearch').click();
										var name = $('#searchInstockNum').val();
										var searchCheckStartTime = $('#searchCheckStartTime').val();
										var searchCheckEndTime = $('#searchCheckEndTime').val();
										var searchStartTime = $('#searchStartTime').val();
										var searchEndTime = $('#searchEndTime').val();
										var unitId= searchUnitId.getValue();
										window.location.href=format_url("/securityApparatus/exportExcel?name="+name
												+"&startTime="+searchStartTime+"&endTime="+searchEndTime
												+"&searchCheckStartTime="+searchCheckStartTime+"&searchCheckEndTime="+searchCheckEndTime
												+"&unitId="+unitId); 
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
				                    var loginName = '${sysUserEntity.loginName}'
									if(data.createUserId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
									
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										//var approveId = nData.applicant;
										A.loadPage({
											render : '#page-container',
											url : format_url('/securityApparatus/getEdit/' + id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var loginUser = '${sysUserEntity.id}';
				                    var loginName = '${sysUserEntity.loginName}'
									if(data.createUserId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
									
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/securityApparatus/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											securityApparatusDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}, {
						 	label: "查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue search",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url('/securityApparatus/showPage/' + id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						//工器具名
						if($("#searchSecurityApparatusName").val()){
    						conditions.push({
    							field: 'sa.c_name',
    							fieldType:'STRING',
    							matchType:'LIKE',
								name:'name',
								type:"1",
    							value:$("#searchSecurityApparatusName").val()
    						});
						}
						//检查时间开始
						if($("#searchCheckStartTime").val()){
        					conditions.push({
        						field: 'checkDate',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchCheckStartTime").val()
        					});
						}
						//检查时间结束
						if($("#searchCheckEndTime").val()){
        					conditions.push({
        						field: 'checkDate',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchCheckEndTime").val()
        					});
						}
						//到期时间开始
						if($("#searchStartTime").val()){
        					conditions.push({
        						field: 'endDate',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchStartTime").val()
        					});
						}
						//到期时间结束
						if($("#searchEndTime").val()){
        					conditions.push({
        						field: 'endDate',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchEndTime").val()
        					});
						}
						//入库类型
						if($("#searchInstockType").val()){
    						conditions.push({
    							field: 'instockType',
    							fieldType:'STRING',
    							matchType:'EQ',
    							name:'instockType',
								type:"1",
    							value:$("#searchInstockType").val()
    						});
						}
						//风场名称
						if(searchUnitId.getValue()!=null&&searchUnitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'SA.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchUnitId,
	        					type:2,
	        					value:searchUnitId.getValue()
	        				});
						}
						securityApparatusDatatables._datatables.search(JSON.stringify(conditions)).draw();
						//securityApparatusDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchSecurityApparatusName').val('');
						$('#searchStartTime').val('');
						$('#searchEndTime').val('');
						$('#searchCheckStartTime').val('');
						$('#searchCheckEndTime').val('');
						searchUnitId.setValue(null);
					});
				});
			});
        </script>
    </body>
</html>