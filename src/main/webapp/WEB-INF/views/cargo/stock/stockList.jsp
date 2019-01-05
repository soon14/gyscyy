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
				<li class="active">库存管理</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="page-content">
			<div class=" col-lg-12 col-md-12 col-sm-12  padding-zero search-content">
				<div class="form-inline text-left" role="form">
					<div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
								<label class="searchLabel" for="searchUnitName">单位名称</label>：
				                <div id="searchUnitNameDiv" class="inputWidth text-left padding-zero"></div>
			                </div>
			                <div  class="clearfix  groupRightDiv">
				                <label class="searchLabel" for="searchMaterialCode">物资编号</label>：
				                <input id="searchMaterialCode" class="inputWidth text-left" placeholder="请输入物资编号" type="text" >
			                </div>
			            </div>
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchMaterialName">物资名称</label>：
		                	<input id="searchMaterialName" class="inputWidth text-left" placeholder="请输入物资名称" type="text" >
							</div>
							<div  class="clearfix  groupRightDiv">
							<label class="searchLabel" for="searchMaterialModel">规格型号</label>：
		               		<input id="searchMaterialModel" class="inputWidth text-left" placeholder="请输入规格型号" type="text" >
							</div>
		           		 </div>
               	 	</div>
                <div class="clearfix">
						<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
							<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchMaterialFacture">生产厂家</label>：
			                <div class="inputWidth padding-zero  text-left">
						       <select id="searchMaterialFacture" class="form-control chosen-select" searchtype style="width: 150px;"></select>
						    </div>
						    </div>
						    <div  class="clearfix  groupRightDiv">
						    <label class="searchLabel" for="form-field-5">管理方式</label>：
							<div class="inputWidth searchManagement  text-left">
			                    <select id="searchManagement" class="form-control chosen-select" style="width:150px;"></select>
			                </div>
						    </div>
                   		</div>
					<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchInventoryQuantityStart">库存数量</label>：
			                <input id="searchInventoryQuantityStart" class="inputWidth text-left" placeholder="库存数量" type="text" style="width:31%;">
			                <label class="" for="searchInventoryQuantityEnd">~</label>
			                <input id="searchInventoryQuantityEnd" class="inputWidth text-left" placeholder="库存数量" type="text" style="width:31%;">
		           		</div>
		           		<div  class="clearfix  groupRightDiv">
							<label class="searchLabel" for="searchUpperLimitStart">标准上限</label>：
			                <input id="searchUpperLimitStart" class="inputWidth text-left" placeholder="标准上限" type="text" style="width:31%;" >
			                <label class="" for="searchUpperLimitEnd">~</label>
			                <input id="searchUpperLimitEnd" class="inputWidth text-left" placeholder="标准上限" type="text" style="width:31%;">
	                   	</div>
                   	</div>
		        </div>
		        <div class="clearfix">
					<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
						<div  class="clearfix groupDiv">
						<label class="searchLabel" for="searchLowerLimitStart">标准下限</label>：
		                    <input id="searchLowerLimitStart" class="inputWidth text-left" placeholder="标准下限" type="text" style="width:31%;">
		                <label class="" for="searchLowerLimitEnd">~</label>
		                <input id="searchLowerLimitEnd" class="inputWidth text-left" placeholder="标准下限" type="text" style="width:31%;">
		          		 </div>
			           <div  class="clearfix  groupRightDiv">
							<label class="searchLabel" for="searchShortageStart">短缺数量</label>：
			                <input id="searchShortageStart" placeholder="短缺数量" type="text" style="width:31%;">
			                <label class="" for="searchShortageEnd">~</label>
			                <input id="searchShortageEnd" class="input-width text-left" placeholder="短缺数量" type="text" style="width:31%;">
	                   </div>
                   </div>
                   
					<div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
					<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchIsShortage">是否短缺</label>：
			                <input id="searchIsShortage" name="searchIsShortage" class="form-control" placeholder="短缺数量" type="checkbox" value="1">
							<div  class="inputWidth text-left padding-zero"></div>
							</div>
							<div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchMaterialFacture">仓库</label>：
			                <div class="inputWidth padding-zero  text-left">
						       <select id="searchWarehouse" class="form-control chosen-select" searchtype style="width: 150px;"></select>
						    </div>
						    </div>
                  	</div>
                  	 </div>
                  	  <div class="clearfix">
                  	  <div class="form-group  col-lg-6 col-md-6 col-sm-6 padding-zero" style="height: 31px;">
                  	  <div  class="clearfix groupDiv">
							<label class="searchLabel" for="searchMaterialFacture">物资类别</label>：
			                <div class="inputWidth padding-zero  text-left">
						       <select id="searchMaterialType" class="form-control chosen-select" searchtype style="width: 150px;"></select>
						    </div>
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
                        <h5 class='table-title header smaller blue'>库存表</h5>
						<table id="stock_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>单位名称</th>
	                                <th>所属仓库</th>
	                                <th>物资编号</th>
	                                <th>物资名称</th>
	                                <th>规格型号</th>
	                                <th>生产厂家</th>
	                                <th>计数单位</th>
	                                <th>管理方式</th>
	                                <th>有效期</th>
	                                <th>价格(元)</th>
	                                <th>库存数量</th>
	                                <th>标准上限</th>
	                                <th>标准下限</th>
	                                <th>短缺数量</th>
	                                <th>短缺</th>
	                                <th>入库类型</th>
	                                <th>物资类别</th>
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
					var dutyName=${dutyName};
					
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
					//仓库
					var warehouse = ${wareHouseIds};
					var warehouseCombobox = new A.combobox({
						render: "#searchWarehouse",
						datasource:warehouse,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//物资类别
					var materialTypeList = ${materialTypeList};
					var materialTypeCombobox = new A.combobox({
						render: "#searchMaterialType",
						datasource:materialTypeList,
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
					var searchunitId = new A.combotree({
						render: "#searchUnitNameDiv",
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
					var productionFactoryData = ${productionFactoryCombobox};
					var produtionFactoryCombobox = new A.combobox({
						render: "#searchMaterialFacture",
						datasource:productionFactoryData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var stockDatatables = new A.datatables({
						render: '#stock_table',
						options: {
					        "ajax": {
					            "url": format_url("/stock/data/list"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'sk.C_UNIT_ID',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:${unitId}
		            				}); 
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							//order:[["1",'desc']],
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {data: "unitName",name:"unitId",width: "auto",orderable: false}, 
							          {data: "wareHouseName",width: "auto",orderable: false},
							          {data: "materialCode",width: "auto",orderable: true}, 
							          {data: "materialName",width: "auto",orderable: false}, 
							          {data: "materialModel",width: "auto",orderable: false}, 
							          {data: "manufacturerName",width: "auto",orderable: false}, 
							          {data: "materialUnitName",width: "auto",orderable: false}, 
							          {data: "managementTypeName",width: "auto",orderable: false}, 
							          {data: "showGoodsValidity",width: "auto",orderable: false},
							          {data: "goodsPrice",width: "auto",orderable: false}, 
							          {data: "inventoryQuantity",width: "auto",orderable: false,editType:"input",editHeight:"22px",editWidth:"95%",
							        	  render : function(data, type, row, meta) {
							                   if(dutyName.indexOf('库管员')<0){
							        		     stockDatatables._options.columns[12].editReadOnly="ReadOnly";
							                   } 
							                   return data;
							               	}}, 
							          {data: "upperLimit",width: "auto",orderable: false,editType:"input",editHeight:"22px",editWidth:"95%"}, 
							          {data: "lowerLimit",width: "auto",orderable: false,editType:"input",editHeight:"22px",editWidth:"95%"},
							          {data: "shortage",width: "auto",orderable: false,editType:"input",editReadOnly:"ReadOnly",editWidth:"95%",editHeight:"22px"},
							          {data: "isShortageName",width: "auto",orderable: false},
							          {data: "typeName",name:"type",width: "auto",orderable: false},
							          {data: "materialTypeName",name:"type",width: "auto",orderable: false}
							         ],
							toolbars: [{
								label:"保存",
								icon: "fa fa-floppy-o",
								className: "btn-success",
								events:{
									click: function(event){
										var data = stockDatatables.getSelectRowDatas();
										var ids = [];
										var cksl = [];
										var upper = [];
										var lower = [];
										if(data.length && data.length>0){
											for(var i =0; i<data.length; i++){
												ids.push(data[i].id);
												var ckslData = $('#inventoryQuantityData'+data[i].id).val();
												var upperLimitData = $('#upperLimitData'+data[i].id).val();
												var lowerLimitData = $('#lowerLimitData'+data[i].id).val();
												if(ckslData != ""){
													var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
													if(!patrn.exec(ckslData)){
														alert("库存数量不是正整数、小数或0！");
														return;
													}
												}else{
													ckslData = "0";
												}
												if(upperLimitData != ""){
													var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
													if(!patrn.exec(upperLimitData)){
														alert("标准上限不是正整数、小数或0！");
														return;
													}
												}else{
													upperLimitData = "0";
												}
												if(lowerLimitData != ""){
													var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
													if(!patrn.exec(lowerLimitData)){
														alert("标准下限不是正整数、小数或0！");
														return;
													}
												}else{
													lowerLimitData = "0";
												}
												cksl.push(ckslData);
												upper.push(upperLimitData);
												lower.push(lowerLimitData);
											}
										}
										if(ids.length < 1){
											alert('请选择要保存的数据');
											return;
										}
										var url = format_url('/stock/bulkSave?ids='+ids+"&cksl="+cksl+"&upper="+upper+"&lower="+lower);
										$.ajax({
											url : url,
											contentType : 'application/json',
											dataType : 'JSON',
											type : 'GET',
											success: function(result){
												if(result.result == 'success'){
													alert("操作成功");
													stockDatatables.draw(false);
												}else if (result.result == 'error') {
													alert(result.errorMsg);
												}else{
													alert(result.result);
												}
											},
											error:function(v,n){
												alert('操作失败');
											}
										});
									}
								}
							},{
								label:"导出",
								icon: "glyphicon glyphicon-download",
								className: "btn-primary",
								events:{
									click:function(event){
            							searchFunction();
    									exportExcels(format_url("/stock/exportExcel"),JSON.stringify(conditions));
            						}
								}
							}],
							btns: [{
								id:"save",
								label:"保存",
								icon: "fa bigger-130",
								className: "fa fa-floppy-o",
								events:{
									"click": function(event, nRow, nData){
										var id = nData.id;
										var inventoryQuantity = $('#inventoryQuantityData'+id).val();
										var upperLimit = $('#upperLimitData'+id).val();
										var lowerLimit = $('#lowerLimitData'+id).val();
										if(inventoryQuantity != ""){
											var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
											if(!patrn.exec(inventoryQuantity)){
												alert("库存数量不是正整数、小数或0！");
												return;
											}
										}else{
											inventoryQuantity = "0";
										}
										if(upperLimit != ""){
											var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
											if(!patrn.exec(upperLimit)){
												alert("标准上限不是正整数、小数或0！");
												return;
											}
										}else{
											upperLimit = "0";
										}
										if(lowerLimit != ""){
											var patrn = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
											if(!patrn.exec(lowerLimit)){
												alert("标准下限不是正整数、小数或0！");
												return;
											}
										}else{
											lowerLimit = "0";
										}
										  $.ajax({
											url: format_url("/stock/singleSave/"+id+"/"+inventoryQuantity+"/"+upperLimit+"/"+lowerLimit),
											contentType : 'application/json',
											dataType : 'JSON',
											type: 'POST',
											success: function(result){
												if(result.result == 'success'){
													
													alert("操作成功");
													stockDatatables.draw(false);
												}else if (result.result == 'error') {
													alert(result.errorMsg);
												}else{
													alert(result.result);
												}
											}
										});
									}
								}
							}]
						}
					}).render();
					
					
					$('#btnSearch').on('click',function(){
						conditions=[];
    					//风场名称
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'sk.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					value:searchunitId.getValue()
	        				});
						}
    					if($('#searchMaterialCode').val()){
        					conditions.push({
        						field: 'mc.C_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialCode').val()
        					});
						}
    					if($('#searchMaterialName').val()){
        					conditions.push({
        						field: 'mc.C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialName').val()
        					});
						}
    					if($('#searchMaterialModel').val()){
        					conditions.push({
        						field: 'materialModel',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialModel').val()
        					});
						}
    					if($('#searchManagement').val()){
        					conditions.push({
        						field: 'materialManagement',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchManagement').val()
        					});
						}
    					if($('#searchMaterialFacture').val()){
        					conditions.push({
        						field: 'materialManufacturer',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchMaterialFacture').val()
        					});
						}
    					if($('#searchInventoryQuantityStart').val()){
        					conditions.push({
        						field: 'inventoryQuantity',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchInventoryQuantityStart').val())
        					});
						}
    					if($('#searchInventoryQuantityEnd').val()){
        					conditions.push({
        						field: 'inventoryQuantity',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchInventoryQuantityEnd').val())
        					});
						}
    					if($('#searchUpperLimitStart').val()){
        					conditions.push({
        						field: 'upperLimit',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchUpperLimitStart').val())
        					});
						}
    					if($('#searchUpperLimitEnd').val()){
        					conditions.push({
        						field: 'upperLimit',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchUpperLimitEnd').val())
        					});
						}
    					if($('#searchLowerLimitStart').val()){
        					conditions.push({
        						field: 'lowerLimit',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchLowerLimitStart').val())
        					});
						}
    					if($('#searchLowerLimitEnd').val()){
        					conditions.push({
        						field: 'lowerLimit',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchLowerLimitEnd').val())
        					});
						}
    					if($('#searchShortageStart').val()){
        					conditions.push({
        						field: 'shortage',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchShortageStart').val())
        					});
						}
    					if($('#searchShortageEnd').val()){
        					conditions.push({
        						field: 'shortage',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchShortageEnd').val())
        					});
						}
    					if($("input[type='checkbox']:checked").val()){
    						conditions.push({
        						field: 'isShortage',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$("input[type='checkbox']:checked").val()
        					});
    					}
    					if($('#searchWarehouse').val()){
        					conditions.push({
        						field: 'wareHouseName',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchWarehouse').val()
        					});
						}
    					if($('#searchMaterialType').val()){
        					conditions.push({
        						field: 'mc.C_MATERIAL_TYPE',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchMaterialType').val()
        					});
						}
						stockDatatables.draw();
					});
					function searchFunction(){
						conditions=[];
    					//风场名称
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'sk.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					value:searchunitId.getValue()
	        				});
						}
    					if($('#searchMaterialCode').val()){
        					conditions.push({
        						field: 'mc.C_CODE',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialCode').val()
        					});
						}
    					if($('#searchMaterialName').val()){
        					conditions.push({
        						field: 'mc.C_NAME',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialName').val()
        					});
						}
    					if($('#searchMaterialModel').val()){
        					conditions.push({
        						field: 'materialModel',
        						fieldType:'STRING',
        						matchType:'LIKE',
        						value:$('#searchMaterialModel').val()
        					});
						}
    					if($('#searchManagement').val()){
        					conditions.push({
        						field: 'materialManagement',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchManagement').val()
        					});
						}
    					if($('#searchMaterialFacture').val()){
        					conditions.push({
        						field: 'materialManufacturer',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$('#searchMaterialFacture').val()
        					});
						}
    					if($('#searchInventoryQuantityStart').val()){
        					conditions.push({
        						field: 'inventoryQuantity',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchInventoryQuantityStart').val())
        					});
						}
    					if($('#searchInventoryQuantityEnd').val()){
        					conditions.push({
        						field: 'inventoryQuantity',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchInventoryQuantityEnd').val())
        					});
						}
    					if($('#searchUpperLimitStart').val()){
        					conditions.push({
        						field: 'upperLimit',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchUpperLimitStart').val())
        					});
						}
    					if($('#searchUpperLimitEnd').val()){
        					conditions.push({
        						field: 'upperLimit',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchUpperLimitEnd').val())
        					});
						}
    					if($('#searchLowerLimitStart').val()){
        					conditions.push({
        						field: 'lowerLimit',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchLowerLimitStart').val())
        					});
						}
    					if($('#searchLowerLimitEnd').val()){
        					conditions.push({
        						field: 'lowerLimit',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchLowerLimitEnd').val())
        					});
						}
    					if($('#searchShortageStart').val()){
        					conditions.push({
        						field: 'shortage',
        						fieldType:'INT',
        						matchType:'GE',
        						value:parseInt($('#searchShortageStart').val())
        					});
						}
    					if($('#searchShortageEnd').val()){
        					conditions.push({
        						field: 'shortage',
        						fieldType:'INT',
        						matchType:'LE',
        						value:parseInt($('#searchShortageEnd').val())
        					});
						}
    					if($("input[type='checkbox']:checked").val()){
    						conditions.push({
        						field: 'isShortage',
        						fieldType:'STRING',
        						matchType:'EQ',
        						value:$("input[type='checkbox']:checked").val()
        					});
    					}
    					
						stockDatatables.draw();
					};
					$('#btnReset').on('click',function(){
						searchunitId.setValue(null);
						$('#searchMaterialCode').val('');
						$('#searchMaterialName').val('');
						$('#searchMaterialModel').val('');
						$('#searchManagement').val('');
						$("#searchManagement").trigger("chosen:updated");
						$('#searchMaterialFacture').val('');
						$("#searchMaterialFacture").trigger("chosen:updated");
						$('#searchInventoryQuantityStart').val('');
						$('#searchInventoryQuantityEnd').val('');
						$('#searchUpperLimitStart').val('');
						$('#searchUpperLimitEnd').val('');
						$('#searchLowerLimitStart').val('');
						$('#searchLowerLimitEnd').val('');
						$('#searchShortageStart').val('');
						$('#searchShortageEnd').val('');
						$("input[type='checkbox']").removeAttr("checked");
						$('#searchWarehouse').val('');
						$("#searchWarehouse").trigger("chosen:updated");
						$('#searchMaterialType').val('');
						$("#searchMaterialType").trigger("chosen:updated");
					});
					
				});
			});
        </script>
    </body>
</html>