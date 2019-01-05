<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<ul class="breadcrumb">
    			<li>
    				<i class="ace-icon fa fa-home home-icon"></i>
    				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
    			</li>
    
    			<li>物资管理</li>
    			<li>报废申请</li>
    			<li>库外申请</li>
    			<li class="active">查看</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtn" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">基本信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input id="id" name="id" type="hidden" value="${id }">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						申请编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="${scrapLibraryEntity.code }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						出入库时间
					</label>
					 <div class="col-md-4">
				    <input class="col-md-12" id="instockTime" name="instockTime" readonly="readonly" type="text" placeholder="" maxlength="64" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${scrapLibraryEntity.instockTime}" type="both"/>">
                    </div>
<!--                     <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						来源电站 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-4"> -->
<%-- 				   <input class="col-md-12" id="stationSourceId" name="stationSourceId" readonly="readonly" type="text" placeholder="" maxlength="64" value="${SysUnitEntity.name }"> --%>
<!--                     </div> -->
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">
						申请人
				    </label>
				    <div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select> -->
						<input class="col-md-12"    type="text" readonly="readonly" value="${sysUserEntity.name}"></input>
                    </div>
               </div>
                <div class="form-group">
				<label class="col-md-2 control-label no-padding-right">备注</label>
				<div class="col-md-10">
					<textarea name="remark" placeholder="" readonly="readonly" style="height:100px;  resize:none;" class="col-md-12" maxlength="128">${scrapLibraryEntity.remark }</textarea>
				</div>
			</div>
               </form>
               <div class="page-content">
		        <div class="form-group form-horizontal" style="margin-right:100px;">
						<label class="col-md-2 control-label no-padding-right">
							附件
	                    </label>
						<div class="col-md-10" id="divfile">
	                    </div>
	                </div>
		      
				<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>出库明细列表</h5>
						<form id="materialForm" class="form-horizontal" role="form">
						<table id="material_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<!-- <th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th> -->
        							 <th>序号</th>
			                                <th>物资编码</th>
			                                <th>物资名称</th>
			                                <th>规格型号</th>
			                                <th>生产厂家</th>
			                                <th><span style="color:red;">*</span>数量</th>
			                                <th>单位</th>
			                          <!--       <th>货区</th>
			                                <th>货位</th> -->
			                                <th>物资属性</th>
			                                <th>有效期</th>
			                                <th>价格</th>
                                </tr>
                            </thead>
                        </table>
                      </form>
                    </div>
                </div>
            </div>
            </div>
		</div>
		<script type="text/javascript">
		var entityJson  = ${entityJson};
		var materialResult = [];
// 		var goodsAreaValues=${goodsAreaValues};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${scrapLibraryEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					var dataId = entityJson.id;
					var editInstockMaterialDatatables = new A.datatables({
						render: '#material_table',
						options: {
					        serverSide: false,
					        multiple : true,
							ordering: true,
							checked:false,
							optWidth: 120,
							order:[["1",'asc']],
							"fnDrawCallback"    : function(){
									this.api().column(1).nodes().each(function(cell, i) {
									cell.innerHTML =  i + 1;
								});
							},
							"customCreateCell" : function(column, td, cellData, rowData){
								if(column.data == "goodsAreaId"){
									if(cellData){
										var _self = $(td).children()[0];
										$.ajax({
											url :format_url("/wareHouse/getGoodPositionByGoodAreas/" + cellData),
											contentType: "application/json",
											dataType : "JSON",
											success: function(result){
												var goodsAllocationSelect = $(_self).parent().next().children()[0];
												goodsAllocationVal = result.options;
												var cfg = {
													"container" : $(goodsAllocationSelect),
													name : "goodsAllocationId",
													datasource: goodsAllocationVal,
													allowBlank: true,
													options:{
														"disable_search_threshold":10
													}
												};
												var goodsAllocationIdsCombobox = new A.combobox(cfg)
												goodsAllocationIdsCombobox.render();
												goodsAllocationIdsCombobox.setValue(rowData.goodsAllocationId);
											}
										});
									}
									column.cfg = { 
										datasource : goodsAreaValues, 
										name : "goodsAreaId",
										allowBlank: true, options:{
											"disable_search_threshold":10
										},
										change: function(event, value){
											var _self = this;
											$.ajax({
												url :format_url("/wareHouse/getGoodPositionByGoodAreas/" + value.selected),
												contentType: "application/json",
												dataType : "JSON",
												success: function(result){
													var goodsAllocationSelect = $(_self).parent().next().children()[0];
													goodsAllocationVal = result.options;
													var cfg = {
														"container" : $(goodsAllocationSelect),
														name : "goodsAllocationId",
														datasource: goodsAllocationVal,
														allowBlank: true,
														options:{
															"disable_search_threshold":10
														}
													};
													var goodsAllocationIdsCombobox = new A.combobox(cfg)
													goodsAllocationIdsCombobox.render();
												}
											})
							         	}};
								}
							},
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "8%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true}, 
							         {data: "materialUnitName",width: "7%",orderable: true},
							         /* {data: "goodsAreaName",width: "8%" ,orderable: true},
							         {data: "goodsAllocationName",width: "8%" ,orderable: true}, */
							         //添加物资属性   有效期   价格三项属性
							         {data: "goodsAttribute",width: "8%" ,orderable: true, editType:"input" , editReadOnly:true},
							         {data: "showGoodsValidity",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},
							         {data: "goodsPrice",width: "7%" ,orderable: true, editType:"input",editWidth:"95%",editReadOnly:true}
							        ],
						}
					}).render();
					
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						$.ajax({
							url: format_url("/scrapLibrary/editData/list/"+entityJson.id),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								if(result.data[0].id){
									editInstockMaterialDatatables.addRows(result.data);
									var tableData = result.data; 
									if(tableData){
										for(var i=0;i<tableData.length;i++){
											materialResult.push({"id":tableData[i].materialId});
										}
									}
								}
							}
							
						});
					}; 
					
					initDataTable();
        			var appData = ${entityJson};
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							instockTime:{required:true,maxlength:20},
							scrapSource:{required:true,maxlength:20},
							stationSourceId:{required:true,maxlength:20},
							instockType:{required:true,maxlength:20},
							processMode:{required:true,maxlength:20},
							fileId:{maxlength:4000},
							remark:{maxlength:128},
							warehouseId:{required:true,maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/scrapLibrary/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							var objMaterial = $('#materialForm').serializeObject();
							var objMaterialCount = objMaterial.count;
								obj.materialIdArray = [];
							var objGoodsArea = objMaterial.goodsAreaId;
							var objGoodsAllocation = objMaterial.goodsAllocationId;
								obj.materialId = [];
								obj.goodsAreaId = [];
								obj.goodsAllocationId = [];
							if(objMaterialCount){
								obj.materialCountArray = objMaterialCount;
								obj.goodsArea = objGoodsArea;
								obj.goodsAllocation = objGoodsAllocation;
								for(var i=0;i<materialResult.length;i++){
									obj.materialIdArray.push(materialResult[i].id);
								}
							}
							var materialSize = obj.materialIdArray;
							if(materialSize.length==0){
								alert("请选择入库物资！");
								return;
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#backBtn").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/scrapLibrary/index/3")
						});
    				});
				});
			});
        </script>
    </body>
</html>