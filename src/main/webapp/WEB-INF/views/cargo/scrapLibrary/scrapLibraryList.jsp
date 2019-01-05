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
					物资管理
				</li>
				<li class="active">报废申请</li>
				<li class="active">库内申请</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
					<div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchcode">申请编号</label>：
	                    <input id="searchcode" class="inputWidth text-left" placeholder="请输入申请编号" type="text">
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchunitNameId">来源电站</label>：
	                    <div id="searchunitNameId" class="inputWidth text-left padding-zero"></div>
	                </div>
	                <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-zero">
	                    <label class="searchLabel" for="form-field-1">报废时间</label>：
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchInstockTimeStartDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                    <div class="toLabel" for="searchStartTimeRightDiv" >~</div>
	                    <div class="form-group dateInputOther padding-zero text-left">
	                        <div id="searchInstockTimeEndDiv" style="border:none; padding:0px;"></div>
	                    </div>
	                </div>
	            </div>
				<div class="clearfix">
	                <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchScrapSource">报废来源</label>：
	                     <div class="inputWidth padding-zero  text-left">
	                    <select id="searchScrapSource" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	                <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12 padding-zero">
	                    <label class="searchLabel " for="searchguarderName">审核状态</label>：
	                    <div class="padding-zero inputWidth  text-left">
						  <select id="searchStatus" class="form-control chosen-select"></select>
						 </div>
	                </div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
	                    <label class="searchLabel" for="searchUserId">申请人</label>：
	                     <div class="inputWidth padding-zero  text-left">
	                    <select id="searchUserId" class="form-control chosen-select"></select>
	                    </div>
	                </div>
	            </div>
				<div class="clearfix">
	                
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
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >库内申请</h5>
						<table id="scrapLibrary_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>申请编号</th>
	                                <th>报废时间</th>
	                                <th>报废来源</th>
	                                <th>来源电站</th>
<!-- 	                                <th>出入库类型</th> -->
	                                <th>申请人</th>
	                                <th>审批状态</th>
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
					var unitNameIdCombotree = new A.combotree({
						render: "#searchunitNameId",
						name: 'unitNameId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
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
					var scrapSourceCombobox = new A.combobox({
						render: "#searchScrapSource",
						datasource:${scrapSourceCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var searchUserIdCombobox = new A.combobox({
						render: "#searchUserId",
						datasource:${searchuser},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var instockTypeCombobox = new A.combobox({
						render: "#searchInstockType",
						datasource:${instockTypeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var processModeCombobox = new A.combobox({
						render: "#searchProcessMode",
						datasource:${processModeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var statusCombobox = new A.combobox({
						render: "#searchStatus",
						datasource:${statusCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					var searchInstockTimeStartDiv = new A.my97datepicker({
						id: 'searchInstockTimeStartDivId',
						name:'instockTime',
						render:'#searchInstockTimeStartDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchInstockTimeEndDivId\\')}",
								minDate: ""
						}
					}).render();
					
					var searchInstockTimeEndDiv = new A.my97datepicker({
						id: 'searchInstockTimeEndDivId',
						name:'instockTime',
						render:'#searchInstockTimeEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchInstockTimeStartDivId\\')}",
						}
					}).render();
					var userName = ${sysUserName};
					var userId = ${sysUserId};
					var exportExcel="";
					var scrapLibraryDatatables = new A.datatables({
						render: '#scrapLibrary_table',
						options: {
					        "ajax": {
					            "url": format_url("/scrapLibrary/data/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_TYPE',
			        					fieldType:'LONG',
			        					matchType:'EQ',
			        					value:${type}
			        				});
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
							          {data: "code",width: "auto",orderable: true}, 
							          {data: "instockTime",width: "auto",orderable: true}, 
							          {data: "scrapSourceName",name:"scrapSource",id:"scrapSource",width: "auto",orderable: true}, 
							          {data: "stationSourceName",name:"stationSourceId",id:"stationSourceId",width: "auto",orderable: true}, 
// 							          {data: "instockTypeName",name:"instockType",id:"instockType",width: "auto",orderable: true}, 
							          {data: "userName",id:"userId",width: "auto",orderable: true}, 
									{data: "statusName",name:"status",id:"status",width: "auto",orderable: true}],
									fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										 if(exportExcel){
											 exportExcels(format_url("/scrapLibrary/exportExcel"),JSON.stringify(dd));
										 }
										 exportExcel="";
									 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							A.loadPage({
											render : '#page-container',
											url : format_url("/scrapLibrary/getAdd/1")
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										
										var data = scrapLibraryDatatables.getSelectRowDatas();
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
										if(data[0].status!="0"){
											alert("提交之后不允许删除！");
											return;
										}
										var url = format_url('/scrapLibrary/bulkDelete/');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													scrapLibraryDatatables.draw(false);
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
									var userId = ${sysUser.id};
									var status = data.status;
									
									if(status!=0&&status!=1){
										btnNode.hide();
									}else{
				                        if(data.userId!=userId){
				                          btnNode.hide();
				                        }
									}
									//如果审核通过状态，超级管理员除外
									if(status==5){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										A.loadPage({
											render : '#page-container',
											url : format_url("/scrapLibrary/getEdit/1/"+id)
										});
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								render: function(btnNode, data){
									var userId = ${sysUser.id}
									
									if(data.userId!=userId){
										btnNode.hide();
									}
									if(data.status!="0"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/scrapLibrary/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											scrapLibraryDatatables.draw(false);
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
										url : format_url("/scrapLibrary/getDetail/1/"+ id)
									});
								}
							}
						},{
							id: "submit",
							label:"提交审核",
							icon: "fa fa-check-square-o bigger-130",
							className: "blue key",
							render: function(btnNode, data){
								var userId = ${sysUser.id};
								var loginName = '${sysUser.loginName}';
						
								if(data.userId!=userId){
									btnNode.hide();
								}
								if(data.status!="0"){
									btnNode.hide();
								}
							},
							events:{
								
									click: function(event, nRow, nData){
										var id = nData.id;
										if(!(nData.status==0||nData.status==1)){
											alert("该记录不是待提交或驳回状态，不能提交！");
											return;
										}
										submitUserDialog = new A.dialog({
											width: 850,
											height: 481,
											title: "报废库管理提交人",
											url:format_url('/scrapLibrary/sureSubmitPerson/'+id),
											closed: function(resule){
												var obj=new Object();
												var id = nData.id;
												var url =format_url('/scrapLibrary/submit/'+ id);
												if(submitUserDialog.resule){
													  obj.userList=submitUserDialog.resule.join(",");
													  $.ajax({
																url : url,
					    										contentType : 'application/json',
					    										dataType : 'JSON',
					    										type : 'POST',
					    										data:JSON.stringify(obj),
					    										success: function(result){
					    											if(result.result=="success"){
					    												alert("提交成功");
					    												scrapLibraryDatatables.draw(false);
					    											}else{
					    												alert(result.errorMsg);
					    											}
					    										},
					    										error:function(v,n){
					    											alert('提交失败');
					    										}
														});
												}
											}
										}).render();
									}
							}
						},{
							id: "print",
							label:"打印",
							icon: "glyphicon glyphicon-print",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									birtPrint("scrapLibraryApply.rptdesign",nData.id);
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchcode").val()!=""){
	    					conditions.push({
	        					field: 't.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchcode').val()
	        				});
    					}
						if(unitNameIdCombotree.getValue()!=null
    							&&unitNameIdCombotree.getValue()!=""){
    						conditions.push({
            					field: 't.C_STATION_SOURCE_ID',
            					fieldType:'STRING',
            					matchType:'EQ',
            					value:unitNameIdCombotree.getValue()
            				});
    					}
						if($("#searchInstockTimeStartDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_INSTOCK_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						value:$("#searchInstockTimeStartDivId").val()+" 00:00:00"
	    					});
    					}
    					if($("#searchInstockTimeEndDivId").val()!=""){
	    					conditions.push({
	    						field: 't.C_INSTOCK_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						value:$("#searchInstockTimeEndDivId").val()+" 23:59:59"
	    					});
    					}
    					if($("#searchScrapSource").val()!=""){
	    					conditions.push({
	        					field: 't.C_SCRAP_SOURCE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchScrapSource').val()
	        				});
    					}
//     					if($("#searchInstockType").val()!=""){
// 	    					conditions.push({
// 	        					field: 't.C_INSTOCK_TYPE',
// 	        					fieldType:'STRING',
// 	        					matchType:'LIKE',
// 	        					value:$('#searchInstockType').val()
// 	        				});
//     					}
//     					if($("#searchProcessMode").val()!=""){
// 	    					conditions.push({
// 	        					field: 't.C_PROCESS_MODE',
// 	        					fieldType:'STRING',
// 	        					matchType:'LIKE',
// 	        					value:$('#searchProcessMode').val()
// 	        				});
//     					}
    					if($("#searchStatus").val()!=""){
	    					conditions.push({
	        					field: 't.C_STATUS',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchStatus').val()
	        				});
    					}
    					if($("#searchUserId").val()!=""){
	    					conditions.push({
	        					field: 't.C_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#searchUserId').val()
	        				});
    					}
						scrapLibraryDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						unitNameIdCombotree.setValue();
						$("#searchcode").val("");
						$("#searchInstockTimeStartDivId").val("");
						$("#searchInstockTimeEndDivId").val("");
						$("#searchScrapSource").val("");
						$("#searchScrapSource").trigger("chosen:updated");
						$("#searchInstockType").val("");
						$("#searchInstockType").trigger("chosen:updated");
						$("#searchProcessMode").val("");
						$("#searchProcessMode").trigger("chosen:updated");
						$("#searchStatus").val("");
						$("#searchStatus").trigger("chosen:updated");
						$("#searchUserId").val("");
						$("#searchUserId").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>