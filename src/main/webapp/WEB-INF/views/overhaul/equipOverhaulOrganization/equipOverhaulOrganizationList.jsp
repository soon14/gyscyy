<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
		<style>
		.dataTables_wrapper{
			margin-top:54px;
		}
		</style>
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
				<li class="active">单位列表</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
                    <div class="clearfix" >
                    	<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
		                    <label class="searchLabel" for="searchunitIdDiv">公司名称</label>：
		                     	<div class="padding-zero inputWidth  text-left">
		                      		<select id="searchUnitNameDiv" class="" ></select>
	   						 	</div>
		               	</div>
						<div class="form-group col-lg-9 col-md-9 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
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
						<h5 class='table-title header smaller blue' >设备检修记录公司列表</h5>
						<table id="equipOverhaulOrganization_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>公司编码</th>
	                                <th>公司名称</th>
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
			var conditions=[];
			var orgAniZationArray = '1,3,4,7';
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					//组织机构
					var nameCombobox = new A.combobox({
						render: "#searchUnitNameDiv",
						datasource: ${unitList},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render()
					var equipOverhaulOrganizationDatatables = new A.datatables({
						render: '#equipOverhaulOrganization_table',
						options: {
					        "ajax": {
					            "url": format_url("/equipOverhaulOrganization/getDataList"),
// 					            "url": format_url("/sysUnit/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
// 					            	conditions.push({
// 										field : 'C_ORGANIZATION',
// 										fieldType : 'STRING',
// 										matchType : 'IN',
// 										value : orgAniZationArray
// 									});
// 					            	conditions.push({
// 										field : 'C_STATUS',
// 										fieldType : 'STRING',
// 										matchType : 'EQ',
// 										value : '1'
// 									});
// 					            	conditions.push({
// 										field : 'C_DELETE_FLAG',
// 										fieldType : 'STRING',
// 										matchType : 'EQ',
// 										value : "N"
// 									});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "name",width: "auto",orderable: true},
							          {data: "code", name:"code",width: "auto",orderable: true}], 
// 							toolbars: [{
//         						label:"新增",
//         						icon:"glyphicon glyphicon-plus",
//         						className:"btn-success",
//         						events:{
//             						click:function(event){
//                 						listFormDialog = new A.dialog({
//                     						width: '850',
//                     						height: '471',
//                     						title: "设备检修纪录单位增加",
//                     						url:format_url('/equipOverhaulOrganization/getAdd'),
//                     						closed: function(){
//                     							equipOverhaulOrganizationDatatables.draw(false);
//                     						}	
//                     					}).render()
//             						}
//         						}
//         					}, {
// 								label:"删除",
// 								icon:"glyphicon glyphicon-trash",
// 								className:"btn-danger",
// 								events:{
// 									click: function(event){
// 										var data = equipOverhaulOrganizationDatatables.getSelectRowDatas();
// 										var ids = [];
// 										if(data.length && data.length>0){
// 											for(var i =0; i<data.length; i++){
// 												ids.push(data[i].id);
// 											}
// 										}
// 										if(ids.length < 1){
// 											alert('请选择要删除的数据');
// 											return;
// 										}
// 										var url = format_url('/equipOverhaulOrganization/bulkDelete/');
// 										A.confirm('您确认删除么？',function(){
// 											$.ajax({
// 												url : url,
// 												contentType : 'application/json',
// 												dataType : 'JSON',
// 												type : 'DELETE',
// 												data : JSON.stringify(ids),
// 												success: function(result){
// 													alert('删除成功');
// 													equipOverhaulOrganizationDatatables.draw(false);
// 												},
// 												error:function(v,n){
// 													alert('操作失败');
// 												}
// 											});
// 										});
// 									}
// 								}
// 							}],
							btns: [
// 							   {id: "edit",
// 								label:"修改",
// 								icon: "fa fa-pencil-square-o bigger-130",
// 								className: "green edit",
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										listFormDialog = new A.dialog({
// 											width: '850',
// 											height: '471',
// 											title: "设备检修纪录单位编辑",
// 											url:format_url('/equipOverhaulOrganization/getEdit/' + id),
// 											closed: function(){
// 												equipOverhaulOrganizationDatatables.draw(false);
// 											}
// 										}).render();
// 									}
// 								}
// 							}, {
// 								id:"delete",
// 								label:"删除",
// 								icon: "fa fa-trash-o bigger-130",
// 								className: "red del",
// 								events:{
// 									click: function(event, nRow, nData){
// 										var id = nData.id;
// 										var url =format_url('/equipOverhaulOrganization/'+ id);
// 										A.confirm('您确认删除么？',function(){
// 											$.ajax({
// 												url : url,
//         										contentType : 'application/json',
//         										dataType : 'JSON',
//         										type : 'DELETE',
//         										success: function(result){
//         											alert('删除成功');
//         											equipOverhaulOrganizationDatatables.draw(false);
//         										},
//         										error:function(v,n){
//         											alert('操作失败');
//         										}
// 											});
// 										});
// 									}
// 								}
// 						},
						{
							id: "detail",
							label:"配置项目",
							icon: "fa fa-cog bigger-130",
							className: "blue ",
							render: function(btnNode, data){
								var userUnitId=${userUnitId};
								var untiId=data.id;
								if(userUnitId!=untiId){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									//设备检修单位ID
									var orgnaizationId = nData.id;
									A.loadPage({
											render : '#page-container',
											url:format_url("/overhaulRecord/getDataList/"+orgnaizationId)
									});
								}
							}
						 }]
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if ( $("#searchUnitNameDiv").val() != undefined && $("#searchUnitNameDiv").val() != "") {
							var unitId = $("#searchUnitNameDiv").val();
							conditions.push({
								field : 'C_ID',
								fieldType : 'INT',
								matchType : 'EQ',
								value : unitId
							});
						}
						equipOverhaulOrganizationDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchUnitNameDiv").val("");
						$("#searchUnitNameDiv").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>