<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
			<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
				<div class="clearfix">
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="searchcode">物资编码：</label>
		                <input id="searchcode" class="input-width text-left" placeholder="请输入物资编码" type="text" >
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="searchname">物资名称：</label>
		                <input id="searchname" class="input-width text-left" placeholder="请输入物资名称" type="text" >
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="searchmodel">规格型号：</label>
		                <input id="searchmodel" class="input-width text-left" placeholder="请输入规格型号" type="text" >
                   </div>
				</div>
				<div class="clearfix">
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="searchmanufacturer">生产厂家：</label>
						<div class="input-width padding-zero  text-left">
			                <select id="searchmanufacturer" class="form-control chosen-select" searchtype style="width: 150px;"></select>
						</div>
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="searchmanagement">管理方式：</label>
						<div class="input-width padding-zero  text-left">
			                <select id="searchmanagement" class="form-control chosen-select" style="width:150px;"></select>
						</div>
                   </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="searchMaterBtn" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="resetBtn" class="btn btn-xs btn-primary">
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
						<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>物资信息列表</h5>
						<table id="materialCategorySelect_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>物资编码</th>
	                                <th>物资名称</th>
	                                <th>规格型号</th>
	                                <th>计数单位</th>
	                                <th>生产厂家</th>
	                                <th>管理方式</th>
	                                <th>物资属性</th>
	                                <th>有效期</th>
	                                <th>价格</th>
	                                <th>物资数量</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div style="margin-right:100px;margin-top:10px;">
       		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
   			<button id="selectMaterialBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				确定
   			</button>
   		</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var resule=[];
			var materialInfoArray=[];
			var userUnitRels=[];
			jQuery(function($) {
				seajs.use(['datatables','combobox', 'confirm', 'dialog'], function(A){
					var conditions=[];
					var managementData = ${managementCombobox};
					var managementCombobox = new A.combobox({
						render: "#searchmanagement",
						datasource:managementData,
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//生产厂家下拉框
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
					var existDataId = ${existDataId};
					var wareHouseChoose=${wareHouseChoose};
					var materialCategoryDatatables = new A.datatables({
						render: '#materialCategorySelect_table',
						options: {
					        "ajax": {
					            "url": format_url("/materialCategory/searchOutStockMaterial?existDataId="+existDataId+'&wareHouseChoose='+wareHouseChoose),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: false,
							//order:[["3",'asc']],
							optWidth: 0,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "10%",orderable: true}, 
							          {data: "name",width: "12%",orderable: true}, 
							          {data: "model",width: "10%",orderable: true}, 
							          {data: "unitName",name:"unit",width: "10%",orderable: true}, 
							          {data: "manufacturerName",name:"manufacturer",width: "10%",orderable: true}, 
							          {data: "managementTypeName",name:"management",width: "10%",orderable: true}, 
							          {data: "goodsAttribute",name:"goodsAttribute",width: "8%",orderable: true}, 
							          {data: "goodsValidity",width: "8%",orderable: true,render:function(data){
							        	  if(data == null || data == ""){
							        		  return "";
							        	  }
							        	  var d = new Date(data);  
							        	  var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-');  
							        	  return dformat;  
							        	 
							          }},  
							          {data: "goodsPrice",width: "8%",orderable: true},
							          {data: "inventoryCount",width: "10%",orderable: true}
									],
									fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										//设置选中
										$("#materialCategorySelect_table").find('td input[type=checkbox]').each(function(i){
											var row = $(this).closest('tr').get(0);
											var data=materialCategoryDatatables._datatables.row(row).data();
											for(var j=0;j<userUnitRels.length;j++){
												if(data.id==userUnitRels[j]){
													materialCategoryDatatables._datatables.row(row).select();
												};
											}
										});
									}
						}
					}).render();
					//设置选中事件
					$(materialCategoryDatatables._config.render).on('click', 'td input[type=checkbox]' , function(i){
						var row = $(this).closest('tr').get(0);
						var checked = $(this).is(":checked");
						var data=materialCategoryDatatables._datatables.row(row).data();
						if(checked) {
							userUnitRels.push(data.id);
						}else{
							userUnitRels.remove(data.id);
						} 
					});
					//设置全选事件
					$( '#materialCategorySelect_table > thead > tr > th input[type=checkbox], #materialCategorySelect_table_wrapper input[type=checkbox]').eq(0).on('click', function(){
							var th_checked = this.checked;
							$('#materialCategorySelect_table').find('tbody > tr').each(function(){
								var flag=true;
								var row = this;
								var data=materialCategoryDatatables._datatables.row(row).data();
								if(th_checked){
									for (var int = 0; int < userUnitRels.length; int++) {
										if(userUnitRels[int]==data.id){
											flag=false;
										}
									}
									if(flag){
										userUnitRels.push(data.id);
									}
								}else{
									for (var int = 0; int < userUnitRels.length; int++) {
										if(userUnitRels[int]==data.id){
											flag=false;
										}
									}
									if(!flag){
										userUnitRels.remove(data.id);
									}
								}  
							});
						});
					//确定保存数据
					$("#selectMaterialBtn").on("click", function(){
						resule = materialCategoryDatatables.getSelectRowDatas();
						var ids = [];
						if(resule.length && resule.length>0){
							for(var i =0; i<resule.length; i++){
								ids.push(resule[i].id);
								
								var map = {};
								map.materialid =resule[i].id; 
								map.materialCode =resule[i].code; 
								map.materialName = resule[i].name;
								map.model = resule[i].model;
								map.unitName = resule[i].unitName;
								map.manufacturerName = resule[i].manufacturerName;
								map.managementTypeName = resule[i].managementTypeName;
								map.showGoodsAttribute = resule[i].showGoodsAttribute;
								map.goodsValidity = resule[i].goodsValidity;
								map.goodsPrice = resule[i].goodsPrice;
								map.inventoryCount = resule[i].inventoryCount;
								materialInfoArray.push(map);
							
							}
						}
						if(ids.length < 1){
							alert('请选择物资！');
							resule=[];
							return;
						}
// 						if(ids.length > 1){
// 							alert('只可以选择一种物资！');
// 							resule=[];
// 							return;
// 						}
						selectMaterialDialog.close(resule);
// 						$(".bootbox-close-button.close").trigger("click");
    				});
					
					$('#searchMaterBtn').on('click',function(){
						conditions=[];
						if($('#searchcode').val()){
							conditions.push({
	    						field: 'materialCode',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchcode').val()
	    					});
						}
						if($('#searchname').val()){
							conditions.push({
	    						field: 'materialName',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchname').val()
	    					});
						}
						if($('#searchmodel').val()){
							conditions.push({
	    						field: 'materialModel',
	    						fieldType:'STRING',
	    						matchType:'LIKE',
	    						value:$('#searchmodel').val()
	    					});
						}
						if($('#searchmanufacturer').val()){
							conditions.push({
	    						field: 'C_MANUFACTURER',
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
						materialCategoryDatatables.draw();
					});
					$('#resetBtn').on('click',function(){
						$('#searchcode').val('')
						$('#searchname').val('')
						$('#searchmodel').val('')
						$('#searchmanufacturer').val('')
						/* $('#searchmanagement').val('99') */
						produtionFactoryCombobox.setValue('999');
						managementCombobox.setValue("99");
					});
				});
			});
        </script>
    </body>
</html>