<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
            <div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
                    <div class="clearfix" style="width: 95%">
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipName">物资编码</label>：
	                        <input id="btnSearchForEquipMaterialCategory1" class="input-width text-left" placeholder="请输入物资编码" type="text">
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipGradePre">物资名称</label>：
	                             <input id="searchMaterialName1" class="input-width text-left" placeholder="请输入物资名称" type="text">
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-left" >
	                        <label class="" for="searchMaterialModel1">规格型号</label>：
	                             <input id="searchMaterialModel1" class="input-width text-left" placeholder="请输入规格型号" type="text">
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="equipAppraisePerson">生产厂家</label>：
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="searchMaterialManuFacturer1" class="form-control chosen-select" searchtype ></select>
	                        </div>
	                    </div>
	                 </div>
                    <div class="clearfix" style="width: 95%">
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="searchMaterialManagement1">管理方式</label>：
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="searchMaterialManagement1" class="form-control chosen-select" searchtype ></select>
	                        </div>
	                    </div>
	                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                        <label class="" for="searchMaterialType1">物资类别</label>：
	                        <div class="padding-zero input-width  text-left">
	                       		 <select id="searchMaterialType1" class="form-control chosen-select" searchtype></select>
	                        </div>
	                    </div>
	                 </div>
                       
                        <div class="clearfix">
                        <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="float: right;">
                            <button id="btnSearchForEquipMaterialCategorybutton" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                               		 查询
                            </button>
                            <button id="btnResetForEquipMaterialCategory1" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px" type="button">
                                <i class="glyphicon glyphicon-repeat"></i>
                                	重置
                            </button>
                        </div>
                    </div>
                </div>
                 </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
					<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">物资信息列表</h5>
					<table id="materialCategory_table"
						class="table table-striped table-bordered table-hover"
						style="width: 100%;">
						<thead>
							<tr>
								<th style="display: none;">主键</th>
<!-- 								<th class="center sorting_disabled" style="width: 50px;"><label -->
<!-- 									class="pos-rel"> <input type="checkbox" class="ace" /> -->
<!-- 										<span class="lbl"></span> -->
<!-- 								</label></th> -->
								<th>物资编码</th>
								<th>物资名称</th>
								<th>设备编码</th>
								<th>设备名称</th>
								<th>规格型号</th>
								<th>计数单位</th>
								<th>生产厂家</th>
								<th>创建时间</th>
								<th>物资类别</th>
<!-- 								<th>供应商</th> -->
							</tr>
						</thead>
					</table>
				</div>
                </div>
            </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var conditions1=[];
					
					var equipIdJson = JSON.parse('${equipIdJson}');
					var equipIdArray = [];
					var equipIdString = "";
					for(i in equipIdJson){
						equipIdArray.push(equipIdJson[i].equipId);
					}
					if(equipIdArray.length>0){
						equipIdString = equipIdArray.join(",");
					}
					var managementData = ${managementCombobox};
					var managementCombobox = new A.combobox({
						render: "#searchMaterialManagement1",
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
						render: "#searchMaterialManuFacturer1",
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
						render: "#searchMaterialType1",
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
					            "url": format_url("/equiptree/searchMaterialCategory"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions1.push({
		            					field: 'O.C_EQUIP_ID',
		            					fieldType:'STRING',
		            					matchType:'IN',
		            					value:equipIdString
		            				});
					            	d.conditions = conditions1;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							order:[["3",'asc']],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "8%",orderable: false}, 
							          {data: "name",width: "7%",orderable: true}, 
							          {data: "equipCode",width: "7%",orderable: false}, 
							          {data: "equipName",width: "8%",orderable: false}, 
							          {data: "model",width: "10%",orderable: false}, 
							          {data: "unitName",width: "10%",orderable: false}, 
							          {data: "manufacturerName",width: "20%",orderable: false}, 
							          {data: "createDate",width: "10%",orderable: false},
// 							          {data: "managementTypeName",width: "10%",orderable: false},
							          {data: "materialTypeName",width: "10%",orderable: false},
// 							          {data: "supplierIdName",width: "10%",orderable: false}
							         ],
						}
					}).render();
					$('#btnSearchForEquipMaterialCategorybutton').on('click',function(){
						conditions1=[];
						if($('#btnSearchForEquipMaterialCategory1').val()){
							conditions1.push({
	    						field: 'M.C_CODE',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#btnSearchForEquipMaterialCategory1').val()
	    					});
						}
						if($('#searchMaterialName1').val()){
							conditions1.push({
	    						field: 'M.C_NAME',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchMaterialName1').val()
	    					});
						}
						if($('#searchMaterialModel1').val()){
							conditions1.push({
	    						field: 'M.C_MODEL',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchMaterialModel1').val()
	    					});
						}
						if($('#searchMaterialManuFacturer1').val()){
							conditions1.push({
	    						field: 'M.C_MANUFACTURER',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$('#searchMaterialManuFacturer1').val()
	    					});
						}
						if($('#searchMaterialManagement1').val()){
							conditions1.push({
								field: 'M.C_MANAGEMENT',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchMaterialManagement1').val()
							});
						}
						if($('#searchMaterialType1').val()){
							conditions1.push({
								field: 'M.C_MATERIAL_TYPE',
								fieldType:'STRING',
								matchType:'EQ',
								value:$('#searchMaterialType1').val()
							});
						}
						materialCategoryDatatables.draw();
					}); 
					$('#btnResetForEquipMaterialCategory1').on('click',function(){
						$('#btnSearchForEquipMaterialCategory1').val('')
						$('#searchMaterialName1').val('')
						$('#searchMaterialModel1').val('')
						//下拉框清空
						managementCombobox.setValue("99");
						produtionFactoryCombobox.setValue("99");
						materialTypeCombobox.setValue("99");
					});
				});
			});
        </script>
    </body>
</html>