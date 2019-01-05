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
				<li>供应商管理</li>
				<li class="active">供应商管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-xs-12 search-content padding-zero">
				<div class="form-inline" role="form">
					<div class="clearfix">
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">供应商名称：</label>
			                <input id="searchsupplierName" class="input-width text-left" placeholder="请输入供应商名称" type="text" >
	                   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
							<label class="" for="form-field-1">供应商类型</label>：
							<div class="input-width padding-zero  text-left">
			                	<select id="searchsupplierType" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
	                   </div>
			           <div class="form-group col-lg-4 col-md-4 col-sm-4 col-xs-12 padding-zero">
							<label class="" for="form-field-1">地址：</label>
			                <input id="searchaddress" class="input-width text-left" placeholder="请输入地址" type="text" style="width:88%">
	                   </div>
	                  <div class="form-group col-lg-2 col-md-2 col-sm-2 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >供应商管理</h5>
						<table id="supplier_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>供应商名称</th>
	                                <th>供应商类型</th>
	                                <th>公司电话</th>
	                                <th>公司传真</th>
	                                <th>公司网址</th>
	                                <th>公司邮箱</th>
	                                <th>地址</th>
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
					
					var supplierType=${supplierType};
					var supplierTypeCombobox = new A.combobox({
						render: "#searchsupplierType",
						//返回数据待后台返回TODO
						datasource:supplierType,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var supplierDatatables = new A.datatables({
						render: '#supplier_table',
						options: {
					        "ajax": {
					            "url": format_url("/supplier/data/list"),
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
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "supplierName",width: "10%",orderable: true}, 
							          {data: "supplierTypeName",width: "10%",orderable: false}, 
							          {data: "companyTel",width: "10%",orderable: false}, 
							          {data: "companyFax",width: "10%",orderable: false}, 
							          {data: "companyWebsite",width: "10%",orderable: false}, 
							          {data: "companyEmail",width: "10%",orderable: false}, 
							          {data: "address",width: "30%",orderable: false}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
                    					A.loadPage({
   											render : '#page-container',
   											url:format_url('/supplier/getAdd'),
   										});
            						}
        						}
        					}, {
								label:"删除",
								icon:"glyphicon glyphicon-trash",
								className:"btn-danger",
								events:{
									click: function(event){
										var data = supplierDatatables.getSelectRowDatas();
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
										var url = format_url('/supplier/deleteMulti/'+ids);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
												contentType : 'application/json',
												dataType : 'JSON',
												type : 'DELETE',
												data : JSON.stringify(ids),
												success: function(result){
													if(result.result=='success'){
														alert('删除成功');
														supplierDatatables.draw(false);
													}
													if(result.result=='exception'){
       													alert("删除失败！该供应商已被物资引用！");
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
										var searchsupplierName = $('#searchsupplierName').val();
										var searchaddress = $('#searchaddress').val();
										var supplierType = supplierTypeCombobox.getValue();
										window.location.href=format_url("/supplier/exportExcel?searchsupplierName="+searchsupplierName
												+"&searchaddress="+searchaddress+"&supplierType="+supplierType); 
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
										A.loadPage({
   											render : '#page-container',
   											url:format_url('/supplier/getEdit/' + id),
   										});
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
										var url =format_url('/supplier/deleteSingle/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											if(result.result=='success'){
														alert('删除成功');
														supplierDatatables.draw(false);
													}
													if(result.result=='exception'){
       													alert("删除失败！该供应商已被物资引用！");
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
						}]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchsupplierName').val()){
        					conditions.push({
        						field: 'C_SUPPLIER_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchsupplierName').val()
        					});
						}

						if($('#searchsupplierType').val()){
        					conditions.push({
        						field: 'C_SUPPLIER_TYPE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchsupplierType').val()
        					});
						}

						if($('#searchaddress').val()){
        					conditions.push({
        						field: 'C_ADDRESS',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchaddress').val()
        					});
						}

						supplierDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchsupplierName').val('')
						supplierTypeCombobox.setValue("99")
						$('#searchaddress').val('')
					});
				});
			});
        </script>
    </body>
</html>