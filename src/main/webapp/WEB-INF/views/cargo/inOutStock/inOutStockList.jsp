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
				<li class="active">出入库明细</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchcode">单据编号</label>：
			                <input id="searchcode" class="inputWidth text-left" placeholder="请输入单据编号" type="text" >
		                </div>
		                <div  class="clearfix  groupRightDiv">
			                <label class="searchLabel" for="searchtype">单据类型</label>：
							<div class="inputWidth padding-zero  text-left">
			                   <select id="searchtype" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
		                </div>
		                
		            </div>
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<label class="searchLabel" for="searchStarttimeDiv">单据时间</label>：
							<div class="form-group dateInput padding-zero text-left">
                    	  <div id="searchStarttimeDiv" style="border:none; padding:0px;"></div>
                    	</div>
							<div class="toLabel" for="searchStartTimeRightDiv" >~</div>
							<div class="form-group dateInput padding-zero text-left">
                          <div id="searchEndtimeDiv" style="border:none; padding:0px;"></div>
                        </div>
                   </div>
                </div>
                <div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<div  class="clearfix groupDiv">
                   		<label class="searchLabel" for="searchMaterialCode">物资编号</label>：
                   		<input id="searchMaterialCode" class="inputWidth text-left" placeholder="请输入物资编号" type="text" >
                   		</div>
                   		<div  class="clearfix  groupRightDiv">
                   		<label class="searchLabel" for="searchMaterialName">物资名称</label>：
                   		<input id="searchMaterialName" class="inputWidth text-left" placeholder="请输入物资名称" type="text" >
                   		</div>
                   		
                  		 </div>
                  	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                  		<div  class="clearfix groupDiv">
                   		<label class="searchLabel" for="searchMaterialModel">规格型号</label>：
                   		<input id="searchMaterialModel" class="inputWidth text-left" placeholder="请输入规格型号" type="text" >
                   		</div>
                   		<div  class="clearfix  groupRightDiv">
                   		<label class="searchLabel" for="searchManagement">管理方式</label>：
                   		<div class="inputWidth padding-zero  text-left">
		                  <select id="searchManagement" class="form-control chosen-select" ></select>
		                </div>
                   		</div>
                </div>
                </div>
                <div class="clearfix">
                  	<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                  	<div  class="clearfix groupDiv">
                  	<label class="searchLabel" for="searchFacture">生产厂家</label>：
		                <div class="inputWidth padding-zero  text-left">
					       <select id="searchFacture" class="form-control chosen-select" searchtype ></select>
					    </div>
                  	</div>
                  	<div  class="clearfix  groupRightDiv">
                  		<label class="searchLabel" for="searchstationName">场站名称</label>：
		                <div id="searchStationNameDiv" class="inputWidth text-left padding-zero"></div>
                  	</div>
                  	</div>
	                <div  class="form-group col-lg-6 col-md-6 col-sm-6 padding-zero text-right btnSearchBottom">
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
                        <h5 class='table-title header smaller blue' >出入库明细表</h5>
						<table id="inOutStock_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>单据编号</th>
	                                <th>所属仓库</th>
	                                <th>场站名称</th>
	                                <th class='onlyLeft'>单据时间</th>
	                                <th>单据类型</th>
	                                <th>物资编号</th>
	                                <th>物资名称</th>
	                                <th>规格型号</th>
	                                <th>生产厂家</th>
	                                <th>计数单位</th>
	                                <th>管理方式</th>
	                                <th class='onlyLeft'>数量</th>
	                                <th>物资类别</th>
                                   <!--  <th>操作</th> -->
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
			var inOutStockDatatables;
			jQuery(function($) {
				seajs.use(['datatables','combobox','my97datepicker','combotree'], function(A){
					var conditions=[];
					var startDatePicker = new A.my97datepicker({
						id: 'searchStarttime',
						name: 'time',
						render:'#searchStarttimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "#F{$dp.$D(\\'searchEndtime\\')}",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var endDatePicker = new A.my97datepicker({
						id: 'searchEndtime',
						name: 'time',
						render:'#searchEndtimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStarttime\\')}",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					var typeDataSource = ${typeCombobox};
					var typeCombobox = new A.combobox({
						render: "#searchtype",
						datasource:typeDataSource,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var manageDataSource = ${managementCombobox};
					var managementCombobox = new A.combobox({
						render: "#searchManagement",
						datasource:manageDataSource,
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
						render: "#searchFacture",
						datasource:productionFactoryData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//单位下拉树
					var unitNameTreeData = ${unitNameIdTreeList};
					var searchunitIdTree = new A.combotree({
						render: "#searchStationNameDiv",
						name: 'searchunitId',
						//返回数据待后台返回
						datasource: unitNameTreeData,
						width:"110px",
						options: {
							treeId: 'searchunitId',
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
					inOutStockDatatables = new A.datatables({
						render: '#inOutStock_table',
						options: {
					        "ajax": {
					            "url": format_url("/inOutStock/data/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        //bProcessing:true,
					        multiple : true,
							ordering: true,
							optWidth: 100,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "auto",orderable: false},
							          {data: "wareHouseName",width: "auto",orderable: false},
							          {data: "unitName",width: "auto",orderable: false}, 
							          {data: "time",width: "auto",orderable: false,sClass:'center'}, 
							          {data: "typeName",width: "auto",orderable: false}, 
							          {data: "materialCode",width: "auto",orderable: false}, 
							          {data: "materialName",width: "auto",orderable: false}, 
							          {data: "materialModel",width: "auto",orderable: false}, 
							          {data: "manufacturerName",width: "auto",orderable: false}, 
							          {data: "materialUnitName",width: "auto",orderable: false}, 
							          {data: "managementTypeName",width: "auto",orderable: false}, 
							          {data: "count",width: "auto",orderable: false,sClass:'right'},
							          {data: "materialTypeName",width: "auto",orderable: false}
							         ],
							         
						    columnDefs: [
						                 {
				                            "targets": [2],
				                            "data": "code",
				                            "render": function(data, type, full,other) {
				                            	var getRow = other.row;
				                                return "<a href='#'  onclick='checkCodeA("+getRow+")'>"+data+"</a>";
				                            }
						                 }
						                ],
				                     
					                     
		                        toolbars: [{
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click: function(event){
										$('#btnSearch').click();
										var searchcode = $('#searchcode').val();
										var searchType = typeCombobox.getValue();
										var startTime = $('#searchStarttime').val();
										var endTime = $('#searchEndtime').val();
										var searchMaterialCode = $('#searchMaterialCode').val();
										var searchMaterialName = encodeURI(encodeURI($('#searchMaterialName').val()));
										var searchMaterialModel = encodeURI(encodeURI($('#searchMaterialModel').val()));
										var management = managementCombobox.getValue();
										var searchFacture = produtionFactoryCombobox.getValue();
										var searchstationName = searchunitIdTree.getValue();
										
										window.location.href=format_url("/inOutStock/exportExcel?searchcode="+searchcode+"&searchType="+searchType+"&startTime="+startTime+"&endTime="+endTime
												+"&searchMaterialCode="+searchMaterialCode+"&searchMaterialName="+searchMaterialName+"&searchMaterialModel="+searchMaterialModel
												+"&management="+management+"&searchFacture="+searchFacture+"&searchstationName="+searchstationName); 
									}  										
								}
							}],
						}
					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						//单据编号
    					if($('#searchcode').val()){
        					conditions.push({
        						field: 'stock.C_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchcode').val()
        					});
						}
						//场站名称
    					if(searchunitIdTree.getValue()!=null&&searchunitIdTree.getValue()+""!=""){
        					conditions.push({
        						field: 'stock.C_UNIT_ID',
        						fieldType:'LONG',
        						matchType:'EQ',
        						value:searchunitIdTree.getValue()
        					});
						}
						//单据时间开始
						if($("#searchStarttime").val()){
        					conditions.push({
        						field: 'time',
        						fieldType:'DATE',
        						matchType:'GE',
        						value:$("#searchStarttime").val()
        					});
						}
						//单据时间结束
						if($("#searchEndtime").val()){
        					conditions.push({
        						field: 'time',
        						fieldType:'DATE',
        						matchType:'LE',
        						value:$("#searchEndtime").val()
        					});
						}
						//物资编号
						if($("#searchMaterialCode").val()){
							conditions.push({
								field: 'material.C_CODE',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchMaterialCode").val()
							});
						}
						//物资名称
						if($("#searchMaterialName").val()){
							conditions.push({
								field: 'material.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchMaterialName").val()
							});
						}
						//规格型号
						if($("#searchMaterialModel").val()){
							conditions.push({
								field: 'material.C_MODEL',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchMaterialModel").val()
							});
						}
						//生产厂家
						if($("#searchFacture").val()){
							conditions.push({
								field: 'material.C_MANUFACTURER',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#searchFacture").val()
							});
						}
						//管理方式
						if($("#searchManagement").val()){
							conditions.push({
								field: 'material.C_MANAGEMENT',
								fieldType:'STRING',
								matchType:'EQ',
								value:$("#searchManagement").val()
							});
						}
						//单据类型
						if($("#searchtype").val()){
    						conditions.push({
    							field: 'stock.C_TYPE',
    							fieldType:'STRING',
    							matchType:'EQ',
    							value:$("#searchtype").val()
    						});
						}
						inOutStockDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$('#searchcode').val('');
						$('#searchstationName').val('');
						$("#searchStarttime").val('');
						$("#searchEndtime").val('');
						$("#searchMaterialCode").val('');
						$("#searchMaterialName").val('');
						$("#searchMaterialModel").val('');
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
						$("#searchManagement").val("");
						$("#searchManagement").trigger("chosen:updated");
						$("#searchFacture").val("");
						$("#searchFacture").trigger("chosen:updated");
// 						produtionFactoryCombobox.setValue("99");
// 						typeCombobox.setValue("99");
						searchunitIdTree.setValue("99");
// 						managementCombobox.setValue("99");
					});
				});
			});
			function checkCodeA(index){
				var getId = inOutStockDatatables.getDatas()[index].id;
				A.loadPage({
					render: "#page-container",
					url: format_url("/inOutStock/showInfo/"+getId)
				});
			}
        </script>
    </body>
</html>