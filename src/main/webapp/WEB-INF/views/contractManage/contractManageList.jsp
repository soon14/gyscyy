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
				<li class="active">合同管理</li>
				<li class="active">合同台账管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				  <div class="clearfix">
				  
				    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
							<label class="searchLabel" for="contractXdname">所属部门</label>：
							<div class="padding-zero inputWidth  text-left">
                          		<select id="contractXdnameSelect" class="" ></select>
                       		</div>
		            </div>
				  	<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
							<label class="searchLabel" for="contractCode">合同编号</label>：
							<input id="contractCode" class="inputWidth text-left" placeholder="请输入合同编号" type="text" >
		            </div>
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
							<label class="searchLabel" for="contractName">已签合同名称</label>：
			                <input id="contractName" class="inputWidth text-left" placeholder="请输入合同名称" type="text" >
		            </div>
		            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">签约时间</label>：
						<div class="form-group dateInputOther padding-zero text-left">
							<div id="searchstatisticsDateDiv"  style="border:none; padding:0px;width: 250px;"></div>
						</div>
						</div>
                   </div>
                   <div class="clearfix" >
						<div class="form-group col-lg-12 col-md-12 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >合同台账管理</h5>
						<table id="contractManage_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>所属部门</th>
	                                <th>合同编号</th>
	                                <th>已签合同名称</th>
	                                <th>供应商</th>
	                                <th>合同金额（元）</th>
	                                <th>签约时间</th>
	                                <th>采购方式</th>
	                                <th>合同是否执行完毕</th>
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
			var answerht = ${answerht};
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					//所属部门
					var ssbmListCombobox = new A.combobox({
						render: "#contractXdnameSelect",
						datasource:${unitIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					
					//统计时间
					var searchstatisticsDate = new A.my97datepicker({
						id: 'searchstatisticsDate',
						name:'searchstatisticsDate',
						render:'#searchstatisticsDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					
					
					
					//状态下拉框
					var ztTypeCombobox = new A.combobox({
						render: '#zbfsType',
						datasource:${zbfsType},
						allowBlank:true,
						options:{
							"allow_single_deselect": true,
							"disable_search_threshold":10
						}
					}).render();
					
					
					var contractManageDatatables = new A.datatables({
						render: '#contractManage_table',
						options: {
					        "ajax": {
					            "url": format_url("/contractManage/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					            	d.qdnf=$("#searchstatisticsDate").val();
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "contractXdname",width: "14%",orderable: true}, 
							          {data: "contractCode",width: "14%",orderable: true}, 
							          {data: "contractName",width: "14%",orderable: true},
							          {data: "remarkName",name:"remark",width: "12%",orderable: true}, 
							          {data: "contractMoney",width: "10%",orderable: true}, 
							          {data: "qdrq",width: "10%",orderable: true}, 
							          {data: "zbfsName",name:"zbfs",width: "10%",orderable: true},
							          {data: "yjsmoneyName",name:"yjsmoney",width: "8%",orderable: true},
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
            								listFormDialog = new A.dialog({
                        						width: '900',
                        						height: '500',
                        						title: "新增",
                        						url:format_url('/contractManage/getAdd'),
                        						closed: function(){
                        							contractManageDatatables.draw(false);
                        						}	
                        					}).render();
										}else{
											alert("只有计划经营处负责人可以新增！");
											return;
										}
            							
                						
            						}
        						}
        					},{
        						label:"批量导入",
        						icon:"glyphicon glyphicon-upload",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							if(answerht=="1"){
	            							listFormDialog = new A.dialog({
	                    						width: 800,
	                    						height: 300,
	                    						title: "批量导入",
	                    						url:format_url("/contractManage/getBatchAdd"),
	                    						closed: function(){
	                    						}	
	                    					}).render();
            							}else{
											alert("只有计划经营处负责人可以操作！");
											return;
										}
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										if(answerht=="1"){
											var data = contractManageDatatables.getSelectRowDatas();
											var ids = [];
											var userIds = [];
											if(data.length && data.length>0){
												for(var i =0; i<data.length; i++){
													ids.push(data[i].id);
													userIds.push(data[i].createUserId);
												}
											}
											if(ids.length < 1){
												alert('请选择要删除的数据');
												return;
											}
											
											var loginId = ${loginId};
											var loginName = "${loginName}";
											for(var j=0;j<userIds.length;j++){
												if(userIds[j]!=loginId&&loginName!="super"){
													alert('记录中包含不是当前登陆人的记录不能删除!');
													return;
												}
											}
											
											var url = format_url('/contractManage/bulkDelete/');
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url : url,
													contentType : 'application/json',
													dataType : 'JSON',
													type : 'DELETE',
													data : JSON.stringify(ids),
													success: function(result){
														alert('删除成功');
														contractManageDatatables.draw(false);
													},
													error:function(v,n){
														alert('操作失败');
													}
												});
											});
										}else{
											alert("只有计划经营处负责人可以操作！");
											return;
										}
									}
								}
							}, {
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										$('#btnSearch').click();
										var contractXdname = $('#contractXdnameSelect').val();
										var contractCode = $('#contractCode').val();
										var contractName = $('#contractName').val();
										var searchstatisticsDate = $('#searchstatisticsDate').val();
										
										window.location.href=format_url("/contractManage/exportExcel?contractName="+contractName
												+"&contractXdname="+contractXdname+"&contractCode="+contractCode+"&searchstatisticsDate="+searchstatisticsDate); 
									}
								}
							},{  
        						id:"dc",
        						label:"模板下载",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/contractManage/exportExcelModel"));
            						}
        						}
        					}
							],
							btns: [
							{
								id: "fkjl",
								label:"付款记录",
								icon: "icon iconfont icon-xiugai bigger-120",
								className: "green edit",
								render: function(btnNode, data){
									var loginId = ${loginId};
									var loginName = "${loginName}";
									var creatId = data.createUserId;
									if(loginId!=creatId&& loginName!='super'){
										//alert("当前登陆人不是操作人!");
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
											var id = nData.id;
											A.loadPage({
												render : '#page-container',
												url : format_url("/contractFkjl/index/"+id)
											});
									}
								}
							},
							{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var loginId = ${loginId};
									var loginName = "${loginName}";
									var creatId = data.createUserId;
									if(loginId!=creatId&& loginName!='super'){
										//alert("当前登陆人不是操作人!");
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										listFormDialog = new A.dialog({
											width: '850',
											height: '480',
											title: "修改",
											url:format_url('/contractManage/getEdit/' + id),
											closed: function(){
												contractManageDatatables.draw(false);
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
									var loginId = ${loginId};
									var loginName = "${loginName}";
									var creatId = data.createUserId;
									if(loginId!=creatId&& loginName!='super'){
										//alert("当前登陆人不是操作人!");
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/contractManage/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											contractManageDatatables.draw(false);
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
						id: "show",
						label:"查看",
						icon: "fa fa-binoculars bigger-130",
						className: "blue search",
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								listFormDialog = new A.dialog({
									width: '850',
									height: '471',
									title: "查看",
									url:format_url('/contractManage/getShow/' + id),
									closed: function(){
										contractManageDatatables.draw(false);
									}
								}).render();
							}
						}
					} , {
						id:"stop",
					 	label: "驳回",
						icon: "fa fa-times bigger-130",
						className: "blue search",
						render: function(btnNode, data){
							var isnotReject = data.isnotReject;
							if(isnotReject!="1"){
								//alert("当前登陆人不是操作人!");
								btnNode.hide();
							}
						},
						events:{
							click: function(event, nRow, nData){
								var id = nData.id;
								var status = nData.status;
								var url =format_url('/contractManage/resultConfirmStop/'+ id );
									A.confirm('您确定要驳回该合同吗？',function(){
										$.ajax({
											url : url,
    										contentType : 'application/json',
    										dataType : 'JSON',
    										type : 'POST',
    										success: function(result){
    											contractManageDatatables.draw(false);
    											alert('驳回成功');
    										},
    										error:function(v,n){
    											alert('操作失败');
    										}
										});
									});
							}
						}
				}
						]
						}
					}).render();
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						//项目名称
    					if($('#contractName').val()){
        					conditions.push({
        						field: 'C_CONTRACT_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#contractName').val()
        					});
						}
						//合同编号
    					if($('#contractCode').val()){
        					conditions.push({
        						field: 'C_CONTRACT_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#contractCode').val()
        					});
						}
						//立项文件
    					if($('#contractXdnameSelect').val()){
        					conditions.push({
        						field: 'C_CONTRACT_XDNAME',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#contractXdnameSelect').val()
        					});
						}
    					if($('#searchstatisticsDate').val()){
        					conditions.push({
        						field: 'C_QDRQ',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchstatisticsDate').val()
        					});
						}
    					
						contractManageDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#contractCode').val('');
						$('#contractName').val('');
						
						$('#contractXdnameSelect').val('');
						$("#contractXdnameSelect").trigger("chosen:updated");
						
						$("#searchstatisticsDate").val('');
					});
				});
			});
        </script>
    </body>
</html>