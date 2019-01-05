<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
		<ul class="breadcrumb">
			<li><i class="ace-icon fa fa-home home-icon"></i> <a href="javascript:void(0);" onclick="firstPage()">首页</a>
			</li>
			<li>物资管理</li>
			<li class="active">物资类别</li>
		</ul>
		<!-- /.breadcrumb -->
	</div>
	<div class="page-content">
		<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
		               <div class="form-group  col-lg-6 col-md-6 col-sm-6  padding-zero" style="height: 31px;">
		                <div  class="clearfix groupDiv">
						<label class="searchLabel" for="searchcode">物资编码</label>：
						<input id="searchcode" class="inputWidth text-left" placeholder="请输入物资编码" type="text">
						</div>
						<div  class="clearfix groupRightDiv">
						<label class="searchLabel" for="searchname">物资名称</label>： 
						<input id="searchname" class="inputWidth text-left" placeholder="请输入物资名称" type="text">
						</div>
						</div>
						 <div class="form-group  col-lg-6 col-md-6 col-sm-6  padding-zero" style="height: 31px;">
						   <div  class="clearfix groupDiv">
						   <label class="searchLabel" for="searchmodel">规格型号</label>：
						   <input id="searchmodel" class="inputWidth text-left" placeholder="请输入规格型号" type="text">
						   </div>
						   <div  class="clearfix groupRightDiv">
						   <label class="searchLabel" for="searchmanufacturer">生产厂家</label>： 
							<div class="inputWidth padding-zero  text-left">
						       <select id="searchmanufacturer" class="form-control chosen-select" searchtype ></select>
<!-- 						       <input  id="searchmanufacturer" class="inputWidth text-left" placeholder="请输入生产 " type="text" ></select> -->
						    </div>
						   </div>
						 </div>
				</div>
				<div class="clearfix">
		               <div class="form-group  col-lg-6 col-md-6 col-sm-6  padding-zero" style="height: 31px;">
<!-- 		               <div  class="clearfix groupDiv"> -->
<!-- 						<label class="searchLabel" for="searchmanagement">管理方式</label>：  -->
<!-- 						<div class="inputWidth padding-zero  text-left"> -->
<!-- 						<select id="searchmanagement" class="form-control chosen-select" searchtype ></select> -->
<!-- 						</div> -->
<!-- 						</div> -->
						  <div  class="clearfix groupRightDiv">
						  <label class="searchLabel" for="searchMaterialType">物资类别</label>： 
							<div class="input-width padding-zero  text-left">
							<select id="searchMaterialType" class="form-control chosen-select" searchtype></select>
							</div>
						  </div>
				</div>
	              <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
					<button id="btnSearch" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
						<i class="glyphicon glyphicon-search"></i> 查询
					</button>
					<button id="btnReset" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
						<i class="glyphicon glyphicon-repeat"></i> 重置
					</button>
				</div>
			</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<!-- div.dataTables_borderWrap -->
				<div class="widget-main no-padding">
					<h5 class='table-title header smaller blue'>物资信息列表</h5>
					<table id="materialCategory_table"
						class="table table-striped table-bordered table-hover"
						style="width: 100%;">
						<thead>
							<tr>
								<th style="display: none;">主键</th>
								<th class="center sorting_disabled" style="width: 50px;"><label
									class="pos-rel"> <input type="checkbox" class="ace" />
										<span class="lbl"></span>
								</label></th>
								<th>物资编码</th>
								<th>物资名称</th>
								<th>规格型号</th>
								<th>计数单位</th>
								<th>生产厂家</th>
<!-- 								<th>管理方式</th> -->
								<th>物资类别</th>
								<th>备注</th>
								<!-- <th>供应商</th> -->
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox'], function(A){
					var conditions=[];
					var managementData = ${managementCombobox};
					var managementCombobox = new A.combobox({
						render: "#searchmanagement",
						datasource:managementData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var productionFactoryData = ${productionFactoryCombobox};
					var produtionFactoryCombobox = new A.combobox({
						render: "#searchmanufacturer",
						datasource:productionFactoryData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var materialTypeData = ${materialTypeCombobox};
					var materialTypeCombobox = new A.combobox({
						render: "#searchMaterialType",
						datasource:materialTypeData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var materialCategoryDatatables = new A.datatables({
						render: '#materialCategory_table',
						options: {
					        "ajax": {
					            "url": format_url("/materialCategory/data/list"),
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
							          {data: "code",width: "10%",orderable: false}, 
							          {data: "name",width: "18%",orderable: false}, 
							          {data: "model",width: "18%",orderable: false}, 
							          {data: "unitName",name:"unit",width: "9%",orderable: false}, 
							          {data: "manufacturerName",name:"manufacturer",width: "20%",orderable: false}, 
// 							          {data: "managementTypeName",width: "10%",orderable: false},
							          {data: "materialTypeName",name:"materialType",width: "15%",orderable: false},
// 							          {data: "supplierIdName",width: "10%",orderable: false} */
							          {data: "backUp",width: "15%",orderable: false}
							         ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                						listFormDialog = new A.dialog({
                    						width: 480,
                    						height: 600,
                    						title: "物资信息增加",
                    						url:format_url('/materialCategory/getAdd'),
                    						closed: function(){
                    							materialCategoryDatatables.draw(false);
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
										var data = materialCategoryDatatables.getSelectRowDatas();
										var ids = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												if(data[i].quote != "0" && data[i].quote != "1"){
													ids.push(data[i].id);
												}else{
													alert("有被出入库记录引用的物资数据，不可删除！");
													return;
												}
											}
										}
										if(ids.length < 1){
											alert('请选择要删除的数据');
											return;
										}
										var url = format_url('/materialCategory/multipleDel');
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													alert('删除成功');
													materialCategoryDatatables.draw(false);
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
										var searchcode = $('#searchcode').val();
										var searchname = encodeURI(encodeURI($('#searchname').val()));
										var searchmodel = encodeURI(encodeURI($('#searchmodel').val()));
										var produtionFactory = produtionFactoryCombobox.getValue();
										var searchmanagement = managementCombobox.getValue();
										var searchMaterialType = materialTypeCombobox.getValue();
										var searchmanufacturer = $('#searchmanufacturer').val();
										window.location.href=format_url("/materialCategory/exportExcel?searchcode="+searchcode
												+"&searchname="+searchname+"&searchmodel="+searchmodel
												+"&produtionFactory="+produtionFactory+"&searchmanagement="+searchmanagement
												+"&searchMaterialType="+searchMaterialType+"&searchmanufacturer="+searchmanufacturer);
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
										listFormDialog = new A.dialog({
											width: 480,
											height: 600,
											title: "物资信息修改",
											url:format_url('/materialCategory/getEdit/' + id),
											closed: function(){
												materialCategoryDatatables.draw(false);
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
									var loginUser = '${sysUserEntity.id}';
				                    var loginName = '${sysUserEntity.loginName}'
									if(data.createPeopleId!=loginUser&&loginName!="super"){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var quote = nData.quote;
										if(quote!="0" && quote!="1"){
											var url =format_url('/materialCategory/singleDel/'+ id);										
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url : url,
	        										contentType : 'application/json',
	        										dataType : 'JSON',
	        										type : 'DELETE',
	        										success: function(result){
	        											alert('删除成功');
	        											materialCategoryDatatables.draw(false);
	        										},
	        										error:function(v,n){
	        											alert('操作失败');
	        										}
												});
											});
										}else if(quote=="0"){
											alert("该物资在入库记录中被引用，不可删除！");
											return;
										}else if(quote=="1"){
											alert("该物资在出库记录中被引用，不可删除！");
											return;
										}
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
									listFormDialog = new A.dialog({
										width: 480,
										height: 600,
										title: "物资信息查看",
										url:format_url('/materialCategory/getshowPage/' + id),
										closed: function(){
											materialCategoryDatatables.draw(false);
										}
									}).render();
// 									A.loadPage({
// 										render : '#page-container',
// 										url : format_url('/materialCategory/getshowPage/' + id)
// 									});
								}
							}
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchcode').val()){
							conditions.push({
	    						field: 'code',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchcode').val()
	    					});
						}
						if($('#searchname').val()){
							conditions.push({
	    						field: 'name',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchname').val()
	    					});
						}
						if($('#searchmodel').val()){
							conditions.push({
	    						field: 'model',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchmodel').val()
	    					});
						}
						if($('#searchmanufacturer').val()){
							conditions.push({
	    						field: 'manufacturer',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$('#searchmanufacturer').val()
	    					});
						}
						if($('#searchmanagement').val()){
							conditions.push({
								field: 'management',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchmanagement').val()
							});
						}
						if($('#searchMaterialType').val()){
							conditions.push({
								field: 'materialType',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchMaterialType').val()
							});
						}
						materialCategoryDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchcode').val('')
						$('#searchname').val('')
						$('#searchmodel').val('')
						$("#searchMaterialType").val("");
						$("#searchMaterialType").trigger("chosen:updated");
						$("#searchmanagement").val("");
						$("#searchmanagement").trigger("chosen:updated");
						$("#searchmanufacturer").val("");
						$("#searchmanufacturer").trigger("chosen:updated");
						//下拉框清空
// 						managementCombobox.setValue("99");
// 						produtionFactoryCombobox.setValue("99");
					
					});
				});
			});
        </script>
</body>
</html>