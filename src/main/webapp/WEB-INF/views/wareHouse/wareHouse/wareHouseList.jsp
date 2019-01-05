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
					<a href="#">首页</a>
				</li>
				<li>仓库管理</li>
				<li class="active">仓库管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content padding-zero">
				<div class="form-inline" role="form">
					<div class="clearfix">
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">仓库名称：</label>
			                <input id="searchwareHouseName" class="input-width text-left" placeholder="请输入仓库名称" type="text" >
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">所属单位：</label>
		                    <div id="searchunitId" class="input-width padding-zero  text-left"></div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">仓库类型：</label>
							<div class="input-width padding-zero  text-left">
			                	<select id="searchwareHouseType" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">仓库地址：</label>
			                <input id="searchwareHouseAddress" class="input-width text-left" placeholder="请输入仓库地址" type="text" >
	                   </div>
                    </div>
                    <div class="clearfix">
			           <!-- <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">库管员：</label>
							<div class="input-width padding-zero  text-left">
			                	<select id="searchstoreKeeper" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div> -->
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="searchLabel" for="form-field-1">启停用状态</label>：
							<div class="input-width padding-zero  text-left">
			                	<select id="searchstatus" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div>
	                   <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero"></div>
	                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >仓库管理</h5>
						<table id="wareHouse_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>仓库名称</th>
	                                <th>所属单位</th>
	                                <th>父仓库</th>
	                                <th>仓库类型</th>
	                                <th>仓库地址</th>
	                                <th>库管员</th>
	                                <th>启停用状态</th>
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
					var unitNameIdTreeList=${unitNameIdTreeList};
					var unitIdCombotree = new A.combotree({
						render: "#searchunitId",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: unitNameIdTreeList,
						width:"210px",
						options: {
							treeId: 'unitId',
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
					//combobx组件
					var wareHouseType=${wareHouseType};
					var wareHouseTypeCombobox = new A.combobox({
						render: "#searchwareHouseType",
						//返回数据待后台返回TODO
						datasource:wareHouseType,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combobx组件
					var status=${status};
					var statusCombobox = new A.combobox({
						render: "#searchstatus",
						//返回数据待后台返回TODO
						datasource:status,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					/* var userListBox=${userListBox};
					var storeKeeperCombobox = new A.combobox({
						render: "#searchstoreKeeper",
						//返回数据待后台返回TODO
						datasource:userListBox,
						//multiple为true时select可以多选
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render(); */
					var wareHouseDatatables = new A.datatables({
						render: '#wareHouse_table',
						options: {
					        "ajax": {
					            "url": format_url("/wareHouse/data/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							optWidth: 160,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "wareHouseName",width: "10%"}, 
							          {data: "unitIdName",width: "10%"}, 
							          {data: "parentWareHouseName",width: "10%"}, 
							          {data: "wareHouseTypeName",width: "10%"}, 
							          {data: "wareHouseAddress",width: "30%"}, 
							          {data: "storeKeeper",width: "10%"}, 
							          {data: "statusName",width: "10%"}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
//             						click:function(event){
//                 						A.loadPage({
// 											render : '#page-container',
// 											url:format_url('/wareHouse/getAdd'),
// 										});
//             						}
									click:function(event){
		        						listFormDialog = new A.dialog({
		            						width:850 ,
		            						height:471 ,
		            						title: "仓库添加",
		            						url:format_url('/wareHouse/getAdd'),
		            						closed: function(){
		            							wareHouseDatatables.draw(false);
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
										var data = wareHouseDatatables.getSelectRowDatas();
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
										var url = format_url('/wareHouse/deleteMulti/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result=="success"){
														alert('删除成功');
														wareHouseDatatables.draw(false);
													}
													if(result.result=="exception"){
														alert('该仓库在入库管理中被引用，禁止删除！');
														return;
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
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										$('#btnSearch').click();
										var wareHouseName = $('#searchwareHouseName').val();
										var wareHouseAddress = $('#searchwareHouseAddress').val();
										var unitId = unitIdCombotree.getValue();
										var wareHouseType = wareHouseTypeCombobox.getValue();
										var status = statusCombobox.getValue();
										window.location.href=format_url("/wareHouse/exportExcel?wareHouseName="+wareHouseName
												+"&wareHouseAddress="+wareHouseAddress+"&unitId="+unitId+"&wareHouseType="+wareHouseType
												+"&status="+status); 
									}
								}
							}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click:function(event, nRow, nData){
										var id = nData.id;
		        						listFormDialog = new A.dialog({
		            						width:850 ,
		            						height:471 ,
		            						title: "仓库编辑",
		            						url:format_url('/wareHouse/getEdit/' + id),
		            						closed: function(){
		            							wareHouseDatatables.draw(false);
		            						}	
		            					}).render()
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
										var url =format_url('/wareHouse/deleteSingle/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=="success"){
	        											alert('删除成功');
	        											wareHouseDatatables.draw(false);
        											}
        											if(result.result=="exception"){
        												alert("该仓库在入库管理中被引用，禁止删除！");
        												return;
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
								id:"start",
							 	label: "启用",
								icon: "fa fa-check bigger-130",
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
										var unitId=nData.unitId;
										var url =format_url('/wareHouse/resultConfirmStart/'+ id + '/' + status+'/'+unitId);
											A.confirm('您确定要启用模型吗？',function(){
												$.ajax({
													url : url,
		    										contentType : 'application/json',
		    										dataType : 'JSON',
		    										type : 'POST',
		    										success: function(result){
		    											if(result.result=='success'){
		    												wareHouseDatatables.draw(false);
		        											alert('启用模型成功');
		    											}
		    											if(result.result=='exception'){
		   													alert("启用失败，同一场站只能启用一个模型！");
		   													return;
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
							 	label: "停用",
								icon: "fa fa-times bigger-130",
								className: "blue search",
								render: function(btnNode, data){
									var status = data.status;
									if(status==2){
										btnNode.hide();
									}
			                      },
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var status = nData.status;
										var unitId=nData.unitId;
										var url =format_url('/wareHouse/resultConfirmStop/'+ id + '/' + status+'/'+unitId);
											A.confirm('您确定要停用模型吗？',function(){
												$.ajax({
													url : url,
		    										contentType : 'application/json',
		    										dataType : 'JSON',
		    										type : 'POST',
		    										success: function(result){
		    											wareHouseDatatables.draw(false);
		    											alert('停用模型成功');
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
						if($('#searchwareHouseName').val()){
        					conditions.push({
        						field: 'w.C_WARE_HOUSE_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchwareHouseName').val()
        					});
						}

						if(unitIdCombotree.getValue()!=null&&unitIdCombotree.getValue()+""!=""){
        					conditions.push({
        						field: 'w.C_UNIT_ID',
        						fieldType:'LONG',
        						matchType:'EQ',
        						value:unitIdCombotree.getValue()
        					});
						}

						if($('#searchwareHouseType').val()){
        					conditions.push({
        						field: 'w.C_WARE_HOUSE_TYPE',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchwareHouseType').val()
        					});
						}

						if($('#searchwareHouseAddress').val()){
        					conditions.push({
        						field: 'w.C_WARE_HOUSE_ADDRESS',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchwareHouseAddress').val()
        					});
						}

						if($('#searchstoreKeeper').val()){
        					conditions.push({
        						field: 'w.C_STORE_KEEPER',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchstoreKeeper').val()
        					});
						}

						if($('#searchstatus').val()){
        					conditions.push({
        						field: 'w.C_STATUS',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchstatus').val()
        					});
						}

						wareHouseDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchwareHouseName').val('');
						$('#searchwareHouseAddress').val('');
						unitIdCombotree.setValue(null);
						wareHouseTypeCombobox.setValue("99");
						statusCombobox.setValue("99");
					});
				});
			});
        </script>
    </body>
</html>