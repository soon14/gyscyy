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
				<li class="active">公司动态</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">标题</label>：
							<input id="searchtitle" class="input-width text-left" placeholder="请输入标题" type="text" >
	                   </div>
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
							<label class="searchLabel" for="form-field-1">发布时间</label>：
			                <div class="form-group dateInputOther padding-zero text-left">
	                    		<div id="publishId" style="border:none; padding:0px;height:30px;width:260px"></div>
	                    	</div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">状态</label>：
							<div class="input-width padding-zero  text-left">
			                	<select id="statusId" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div>
	                   <div class="form-group col-lg-3 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >公司动态</h5>
						<table id="trainPlan_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>标题</th>
	                                <th>发布内容</th>
	                                <th>发布时间</th>
	                                <th>结束时间</th>
	                                <th>上传人</th>
	                                <th>上传时间</th>
	                                <th>状态</th>
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
					var yearNumDatePicker = new A.my97datepicker({
						id: 'yearNumTime',
						name: 'yearNum',
						render:'#publishId',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					//类别
					var statusCombobox = new A.combobox({
						render: "#statusId",
						datasource:${companyTrendsTypeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var exportExcel="";
					var trainPlanDatatables = new A.datatables({
						render: '#trainPlan_table',
						options: {
					        "ajax": {
					            "url": format_url("/companyTrends/search"),
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
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "title",width: "15%",orderable: true}, 
							          {data: "content",width: "15%",orderable: true}, 
							          {data: "publishTime",width: "10%",orderable: true}, 
							          {data: "endTime",width: "10%",orderable: true}, 
							          {data: "uploadPerson",width: "10%",orderable: true}, 
							          {data: "uploadTime",width: "10%",orderable: true}, 
							          {data: "statusName",name:"status",width: "10%",orderable: true}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcel){
												 exportExcels(format_url("/companyTrends/exportExcel"),JSON.stringify(conditions));
											 }
											 exportExcel="";
										 },
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
//                     						listFormDialog = new A.dialog({
//                         						width:1070 ,
//                         						height:800 ,
//                         						title: "公司动态增加",
//                         						url:format_url('/companyTrends/getAdd'),
//                         						closed: function(){
//                         							trainPlanDatatables.draw(false);
//                         						}	
//                         					}).render();
            							A.loadPage({
											render : '#page-container',
											url : format_url('/companyTrends/getAdd')
										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = trainPlanDatatables.getSelectRowDatas();
										var ids = [];
										var userIds = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												userIds.push(data[i].createPeopleId);
											}
										}
										
										var loginUser = '${sysUserEntity.id}';
					                    var loginName = '${sysUserEntity.loginName}';
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
										var url = format_url('/companyTrends/bulkDelete/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result == 'success'){
														alert('删除成功');
														trainPlanDatatables.draw(false);
	            									}else{
	            										alert(result.errorMsg);
	            									}
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
									var loginUser = '${sysUserEntity.id}';
				                    var loginName = '${sysUserEntity.loginName}';
									if(data.createPeopleId!=loginUser||(data.status!="1" && data.status!="5"&& data.status!="7")){
										btnNode.hide();
									}
									
								},
								events:{
								click: function(event, nRow, nData){
									var id = nData.id;
// 									listFormDialog = new A.dialog({
// 										width: 1070,
// 										height:800 ,
// 										title: "公司动态修改",
// 										url:format_url('/companyTrends/getEdit/' + id),
// 										closed: function(){
// 											trainPlanDatatables.draw(false);
// 										}
// 									}).render();
									A.loadPage({
										render : '#page-container',
										url : format_url('/companyTrends/getEdit/' + id)
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
				                    var loginName = '${sysUserEntity.loginName}';
									if(data.createPeopleId!=loginUser||data.status!="1"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/companyTrends/deleteOne/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											trainPlanDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						},{
							id:"submit",
							label:"提交",
							icon: "fa fa-check-square-o bigger-130",
							className: " edit",
							render: function(btnNode, data){
								var loginUser = '${sysUserEntity.id}';
			                    var loginName = '${sysUserEntity.loginName}';
								if(data.createPeopleId!=loginUser||data.status!="1"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
// 									var id = nData.id;
// 									$.ajax({
// 										url: format_url('/companyTrends/tijiaoValidate/'+id),
// 										contentType : 'application/json',
// 										dataType : 'JSON',
// 										type: 'POST',
// 										data : JSON.stringify(nData),
// 										success: function(result){
// 											if(result.result == 'success'){
// 												workticketDialog = new A.dialog({
// 													title:"提交确认",
// 													url:format_url("/companyTrends/sureSubmit?id="+ id),
// 													height:400,
// 													width:500
// 												}).render();
//         									} else{
//         										alert(result.errorMsg);
//         									}
// 										}
// 									});
// 								}
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "公司动态提交人",
										url:format_url('/companyTrends/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											var id = nData.id;
											var url =format_url('/companyTrends/submit/'+ id);
											if(listFormDialog.resule){
												  obj.userList=listFormDialog.resule.join(",");
														$.ajax({
															url : url,
				    										contentType : 'application/json',
				    										dataType : 'JSON',
				    										type : 'POST',
				    										data:JSON.stringify(obj),
				    										success: function(result){
				    											if(result.result=="success"){
				    												alert("提交成功");
				    												trainPlanDatatables.draw(false);
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
						id:"cancelSubmit",
						label:"取消发布",
						icon: "fa fa-ban bigger-130",
						className: " red del",
						render: function(btnNode, data){
							var loginUser = '${sysUserEntity.id}';
		                    var loginName = '${sysUserEntity.loginName}';
							if((data.createPeopleId!=loginUser&&loginName!="super")||data.status!="2"){
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								var url =format_url('/companyTrends/cancelPublish/'+ id);
								A.confirm('您确认要取消发布吗？',function(){
									$.ajax({
										url : url,
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										success: function(result){
											alert('取消发布成功');
											trainPlanDatatables.draw(false);
										},
										error:function(v,n){
											alert('取消发布失败');
										}
									});
								});
							}
						}
				},{
					id:"detail",
				 	label: "查看",
					icon: "fa fa-binoculars bigger-130",
					className: "blue search",
					events:{
						click: function(event, nRow, nData){
							debugger;
							var id = nData.id;
// 							listFormDialog = new A.dialog({
// 								width: 1070,
// 								height:800 ,
// 								title: "公司动态查看",
// 								url:format_url('/companyTrends/getshowPage/' + id),
// 								closed: function(){
// 									trainPlanDatatables.draw(false);
// 								}
// 							}).render();
							A.loadPage({
								render : '#page-container',
								url : format_url('/companyTrends/getshowPage/' + id)
							});
						}
					}
				},{
					id:"preview",
				 	label: "预览",
					icon: "fa fa-eye bigger-130",
					className: "blue search",
					events:{
						click: function(event, nRow, nData){
							var id = nData.id;
// 							listFormDialog = new A.dialog({
// 								width: 1070,
// 								height:800 ,
// 								title: "公司动态预览",
// 								url:format_url('/companyTrends/getPreviewPage/' + id),
// 								closed: function(){
// 									trainPlanDatatables.draw(false);
// 								}
// 							}).render();
							
							A.loadPage({
								render : '#page-container',
								url : format_url('/companyTrends/getPreviewPage/' + id)
							});
						}
					}
				},{
					id:"setTop",
				 	label: "置顶",
					icon: "glyphicon glyphicon-menu-up",
					className: "blue search",
					render: function(btnNode, data){
						var loginUser = '${sysUserEntity.id}';
						var loginName = '${sysUserEntity.loginName}';
						if(loginName != "super" || data.setTop == "1" || data.status!="2"){
							btnNode.hide();
						}
						
					},
					events:{
						click: function(event, nRow, nData){
							var id = nData.id;
							var url =format_url('/companyTrends/setTop/'+ id);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								success: function(result){
									alert('置顶成功');
									trainPlanDatatables.draw(false);
								},
								error:function(v,n){
									alert('置顶失败');
								}
							});
							
						}
					}
				},{
					id:"cancelTop",
				 	label: "取消置顶",
					icon: "glyphicon glyphicon-menu-down",
					className: "blue search",
					render: function(btnNode, data){
						var loginUser = '${sysUserEntity.id}';
						var loginName = '${sysUserEntity.loginName}';
						if(loginName != "super" || data.setTop != "1" || data.status!="2"){
							btnNode.hide();
						}
					},
					events:{
						click: function(event, nRow, nData){
							var id = nData.id;
							var url =format_url('/companyTrends/cancelTop/'+ id);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								success: function(result){
									alert('取消置顶成功');
									trainPlanDatatables.draw(false);
								},
								error:function(v,n){
									alert('取消置顶失败');
								}
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

						if($('#searchtitle').val()){
        					conditions.push({
        						field: 'C_TITLE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchtitle').val()
        					});
						}

						if($("#yearNumTime").val()){
        					conditions.push({
        						field: 'C_PUBLISH_TIME',
        						fieldType:'DATE',
        						matchType:'EQ',
        						value:$("#yearNumTime").val()+" 00:00:00"
        					});
						}
						
						if($("#statusId").val()){
    						conditions.push({
    							field: 'C_STATUS',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#statusId").val()
    						});
						}

						trainPlanDatatables.draw();
					};
					$('#btnReset').on('click',function(){
						$('#searchtitle').val('')
						$('#yearNumTime').val('')
						$('#statusId').val('')
						$("#statusId").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>