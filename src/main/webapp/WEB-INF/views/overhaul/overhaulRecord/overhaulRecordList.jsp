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
				<li class="active">检修管理</li>
				<li class="active">设备检修记录</li>
				<li class="active">项目列表</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
				<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                        <label class="searchLabel" for="searchName">项目名称</label>：
                         <input id="searchName" class="input-width text-left"  placeholder="请输入项目名称" type="text"></input>
                    </div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel longSearchLabel" for="searchguarderName">检修负责人</label>：
	                    <div class="padding-zero inputWidth  text-left">
						<select id="searchdutyUserName"  class="form-control chosen-select"></select>
						</div> 
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchStartDate">开始时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left" style="width: 65%">
                             	<div id="searchStartDate" style="border:none; padding:0px;"></div>
                            </div>
                    </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchEndDate">结束时间</label>：
                            <div class="form-group dateInputOther padding-zero text-left" style="width: 65%">
                             	<div id="searchEndDate" style="border:none; padding:0px;"></div>
                            </div>
                    </div>
                 </div>
			   <div class="clearfix">
                  <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                	<label class="searchLabel" for="unitId" >检修单位</label>：
				    <div class="padding-zero inputWidth  text-left">
						<select id="searchunitIdDiv" class="form-control chosen-select"></select>
                    </div>
               		</div>
                </div>
				<div class="clearfix">
                         <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >项目列表</h5>
						<table id="overhaulRecord_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>项目名称</th>
	                                <th>开始时间</th>
	                                <th>结束时间</th>
	                                <th>检修负责人</th>
	                                <th>检修单位</th>
<!-- 	                                <th>设备属地</th> -->
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
// 			var orgnaizationId = '${orgnaizationId}';
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					//combobx组件
					var unitIdCombobox = new A.combobox({
						render: "#searchunitIdDiv",
						//返回数据待后台返回TODO
						datasource:${unitList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//工作负责人
					var searchDutyName = new A.combobox({
							render : "#searchdutyUserName",
							datasource : ${searchuser},
							allowBlank: true,
							multiple: false,
							options : {
								"disable_search_threshold" : 10
							}
						}).render();
					
					//开始时间
					var searchStartDatePicker = new A.my97datepicker({
						id: "searchStartDateId",
						name:"searchStartDate",
						render:"#searchStartDate",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					searchStartDatePicker.setValue('${entity.startDateString}');
					//结束时间
					var searchEndDatePicker = new A.my97datepicker({
						id: "searchEndDateId",
						name:"searchEndDate",
						render:"#searchEndDate",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH"
						}
					}).render();
					searchEndDatePicker.setValue('${entity.endDateString}');
					var overhaulRecordDatatables = new A.datatables({
						render: '#overhaulRecord_table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulRecord/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
// 					            	conditions.push({
// 			        					field: 'a.C_ORGANIZATION_ID',
// 			        					fieldType:'INT',
// 			        					matchType:'EQ',
// 			        					value:${orgnaizationId}
// 			        				});
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
							          {data: "name",width: "auto",orderable: true}, 
							          {data: "startDateString",name:"startDate",width: "auto",orderable: true}, 
							          {data: "endDateString",name:"endDate",width: "auto",orderable: true}, 
							          {data: "dutyUserName",name:"dutyUserId",width: "auto",orderable: true}, 
							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
// 							          {data: "equipLocalName",name:"equipLocal",width: "auto",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click: function(event, nRow, nData){
											 var currentPage = overhaulRecordDatatables.getCurrentPage();
													listFormDialog = new A.dialog({
			                    						width: '1000',
			                    						height: '471',
			                    						title: "项目增加",
// 			                    						url:format_url('/overhaulRecord/getAdd/'+orgnaizationId),
			                    						url:format_url('/overhaulRecord/getAdd'),
			                    						closed: function(){
			                    							overhaulRecordDatatables.draw(false);
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
										var data = overhaulRecordDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].dutyUserId);
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var loginUser = ${sysUserId};
										var loginName =${loginName};
										for(var j=0;j<userIds.length;j++){
											if(userIds[j]!=loginUser&&loginName!="super"){
												alert('记录中包含不是当前登陆人的记录不能删除!');
												return;
											}
										}
										var url = format_url('/overhaulRecord/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													overhaulRecordDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											});
										});
									}
								}
							}/* ,{
								label:"返回",
								icon: "fa fa-reply",
								className: "btn-primary ",
								events:{
									click: function(event, nRow, nData){
										A.loadPage({
											render : '#page-container',
											url : format_url("/equipOverhaulOrganization/index")
										});
									}
								}
							} */],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.dutyUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								events:{
            						click: function(event, nRow, nData){
            							     var id = nData.id;
											 var currentPage = overhaulRecordDatatables.getCurrentPage();
												listFormDialog = new A.dialog({
		                    						width: '950',
		                    						height: '471',
		                    						title: "项目修改",
// 		                    						url:format_url('/overhaulRecord/getEdit/'+id+"/"+orgnaizationId),
		                    						url:format_url('/overhaulRecord/getEdit/'+id),
		                    						closed: function(){
		                    							overhaulRecordDatatables.draw(false);
		                    						}	
		                    					}).render()
									}
        						}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUserId};
									var loginName =${loginName};
									if(data.dutyUserId!=userId && loginName!='super'){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/overhaulRecord/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											overhaulRecordDatatables.draw(false);
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
							id: "config",
							label:"设备检修纪录配置",
							icon: "fa fa-cog bigger-130",
							className: "blue ",
							render: function(btnNode, data){
								var userId = ${sysUserId};
								if(data.dutyUserId!=userId){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var overhaulRecord = nData.id;
// 									var overhaulOrganizationId = ${orgnaizationId};
									var action = "config";
									A.loadPage({
										render : '#page-container',
// 										url : format_url("/overhaulArrange/getInfo/"+ overhaulOrganizationId+"/"+overhaulRecord+"/"+action)
										url : format_url("/overhaulArrange/getInfo/"+overhaulRecord+"/"+action)
									});
								}
							}
						},{
							id: "detail",
							label:"检修记录查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							render: function(btnNode, data){
								var userId = ${sysUserId};
								if(data.dutyUserId==userId){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var overhaulRecord = nData.id;
// 									var overhaulOrganizationId = ${orgnaizationId};
									var action = "detail";
									A.loadPage({
										render : '#page-container',
// 										url : format_url("/overhaulArrange/getInfo/"+ overhaulOrganizationId+"/"+overhaulRecord+"/"+action)
										url : format_url("/overhaulArrange/getInfo/"+overhaulRecord+"/"+action)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchStartDateId').val()){
	    					conditions.push({
	        					field: 'a.C_START_DATE',
	        					fieldType:'DATE',
	        					matchType:'EQ',
	        					value:$('#searchStartDateId').val()+":00:00"
	        				});
						}
						if($('#searchEndDateId').val()){
	    					conditions.push({
	        					field: 'a.C_END_DATE',
	        					fieldType:'DATE',
	        					matchType:'EQ',
	        					value:$('#searchEndDateId').val()+":00:00"
	        				});
						}
				
						if($("#searchName").val()!=""){
							conditions.push({
								field: 'a.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						if($("#searchdutyUserName").val()!=""){
	    					conditions.push({
	        					field: 'a.C_DUTY_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchdutyUserName').val()
	        				});
    					}
						if($("#searchunitIdDiv").val()!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:$('#searchunitIdDiv').val()
	        				});
    					}
						overhaulRecordDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchStartDateId').val("");
						$('#searchEndDateId').val("");
						$("#searchName").val('');
						$("#searchdutyUserName").val('');
						$("#searchdutyUserName").trigger("chosen:updated");
						$("#searchunitIdDiv").val('');
						$("#searchunitIdDiv").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>