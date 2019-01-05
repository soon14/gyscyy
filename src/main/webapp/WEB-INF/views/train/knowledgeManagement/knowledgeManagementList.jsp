<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
<!-- 		<meta http-equiv="refresh" content="1;url=http://www.baidu.com"> -->
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>生产技术培训管理</li>
				<li class="active">知识库管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
	                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">创建人</label>：
							<div class="form-group input-width padding-zero text-left">
								<select id="createPeople" class="form-control chosen-select"></select>
							</div>
	                   </div>
	                   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">创建时间</label>：
	                   		<div class="form-group dateInputOther padding-zero text-left">
	                    		<div id="searchStartstartTimeDiv" style="border:none; padding:0px;height:30px;"></div>
	                    	</div>
	               			<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInputOther padding-zero text-left">
	                        	<div id="searchEndstartTimeDiv" style="border:none; padding:0px;height:30px;"></div>
	                        </div>
	                   </div>
	                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">类型</label>：
							<div class="input-width padding-zero  text-left">
			                	<select id="knowledgeType" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div>
                   </div>
					<div class="clearfix">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">主题</label>：
			                <input id="searchtitle" class="input-width text-left" placeholder="请输入主题" type="text" >
	                    </div>
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">关键字</label>：
			                <input id="keyword" class="input-width text-left" placeholder="请输入关键字" type="text" >
	                   </div>
	                   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >知识库管理表</h5>
						<table id="knowledge_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>主题</th>
	                                <th>关键字</th>
	                                <th>类型</th>
	                                <th>单位名称</th>
	                                <th>创建人</th>
	                                <th>创建时间</th>
	                                <th>下载次数</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','my97datepicker'], function(A){
					var conditions=[];
					//创建人
					var peopleCombobox = new A.combobox({
						render: "#createPeople",
						datasource:${createPeopleCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//开始时间
					var startDatePicker = new A.my97datepicker({
						id: 'searchStartstartTime',
						name: 'startTime',
						render:'#searchStartstartTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					//结束时间
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndstartTime',
						name: 'startTime',
						render:'#searchEndstartTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartstartTime\\')}",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					//知识库类型
					var knowledgeTypeCombobox = new A.combobox({
						render: "#knowledgeType",
						datasource:${knowledgeTypeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					var knowledgeDatatables = new A.datatables({
						render: '#knowledge_table',
						options: {
					        "ajax": {
					            "url": format_url("/knowledgeManagement/search"),
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
							order:[[0,'desc']],
							columns: [
							          {data:"id", visible:false,orderable:true}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "titleName",width: "15%",orderable: true}, 
							          {data: "keyword",width: "15%",orderable: true}, 
							          {data: "knowledgeStyleName",name:"knowledgeStyle",width: "15%",orderable: true}, 
							          {data: "unitName",width: "15%",orderable: true}, 
							          {data: "createPeopleName",name:"createPeopleId",width: "15%",orderable: true}, 
							          {data: "createDate",width: "15%",orderable: true}, 
							          {data: "downloadCount",width: "7%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
										A.loadPage({
											render : '#page-container',
											url : format_url('/knowledgeManagement/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = knowledgeDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createPeopleId);
											}
										}
										var loginUser = '${sysUserEntity.id}';
					                    var loginName = '${sysUserEntity.loginName}'
				                    	for(var j=0;j<userIds.length;j++){
				                            if(userIds[j]!=loginUser&&loginName!="super"){
				                              alert('记录中包含不是当前登陆人的记录不能删除!');
				                              return;
				                            }
			                          	}
					                    
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/knowledgeManagement/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													knowledgeDatatables.draw(false);
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
            							searchFunction();
    									exportExcels(format_url("/knowledgeManagement/exportExcel"),JSON.stringify(conditions));
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
									if(data.createPeopleId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url('/knowledgeManagement/getEdit/' + id)
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
									if(data.createPeopleId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/knowledgeManagement/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											knowledgeDatatables.draw(false);
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
										url : format_url('/knowledgeManagement/showPage/' + id)
									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						searchFunction();
					});
					function searchFunction(){
						conditions=[];

						if($('#createPeople').val()){
        					conditions.push({
        						field: 'k.C_CREATE_PEOPLE_ID',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#createPeople').val()
        					});
						}


						if($("#searchStartstartTime").val()){
        					conditions.push({
        						field: 'k.C_CREATE_DATE',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchStartstartTime").val()+" 00:00:00"
        					});
						}
						if($("#searchEndstartTime").val()){
        					conditions.push({
        						field: 'k.C_CREATE_DATE',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchEndstartTime").val()+" 23:59:59"
        					});
						}

						if($("#knowledgeType").val()){
    						conditions.push({
    							field: 'k.C_KNOWLEDGE_STYLE',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#knowledgeType").val()
    						});
						}
						
						if($("#searchtitle").val()){
    						conditions.push({
    							field: 'k.C_TITLE_NAME',
    							fieldType:'STRING',
    							matchType:'LIKE',
    							value:$("#searchtitle").val()
    						});
						}
						
						if($("#keyword").val()){
    						conditions.push({
    							field: 'k.C_KEYWORD',
    							fieldType:'STRING',
    							matchType:'LIKE',
    							value:$("#keyword").val()
    						});
						}

						knowledgeDatatables.draw();
					};
					$('#btnReset').on('click',function(){
						$('#searchStartstartTime').val('');
						$('#searchEndstartTime').val('');
						$('#createPeople').val('');
						$("#createPeople").trigger("chosen:updated");
						$('#knowledgeType').val('');
						$("#knowledgeType").trigger("chosen:updated");
						$('#searchtitle').val('');
						$('#keyword').val('');
					});
				});
			});
        </script>
    </body>
</html>