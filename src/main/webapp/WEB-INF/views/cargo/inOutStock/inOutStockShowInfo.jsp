<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
    			<li>入库管理</li>
    			<li class="active">入库单查看</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">入库单查看</h5>
    	</div>
			<div class="col-md-12" >
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="showForm">
					<div class="form-group">
						<label class="col-md-1 control-label no-padding-right">入库单号</label>
						<div class="col-md-2">
						   <input class="col-md-12" id="instockNum" name="instockNum" type="text" readonly="readonly"  value="${entity.instockNum}">
	                	</div>
					    <label class="col-md-1 control-label no-padding-right">入库时间</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="instockTimeDiv" name="instockTime" type="text" readonly="readonly" value="${entity.showInstockTime}">
	                    </div>
						<label class="col-md-1 control-label no-padding-right">入库类型</label>
						<div class="col-md-2">
						   <input class="col-md-12" id="instockType" name="instockType" type="text" readonly="readonly" value="${entity.instockTypeName}">
	                	</div>
					    <label class="col-md-1 control-label no-padding-right">申请人</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="applicantName" name="applicantName" type="text" readonly="readonly" value="${entity.applicantName}">
	                    </div>
	               </div>
				   <div class="form-group">
				   		<label class="col-md-1 control-label no-padding-right">审核状态</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="approveStatus" name="approveStatus" type="text" readonly="readonly" value="${entity.approveStatusName}">
	                    </div>
	                    <label class="col-md-1 control-label no-padding-right">所属仓库</label>
						<div class="col-md-2">
							<input class="col-md-12" id="wareHouseId" name="wareHouseId"  type="text" readonly="readonly" value="${entity.wareHouseName}"></input>
						</div>
						<label class="col-md-1 control-label no-padding-right">场站名称</label>
						<div class="col-md-2">
							<input class="col-md-12" id="wareHouseId" name="wareHouseId"  type="text" readonly="readonly" value="${entity.unitName}"></input>
						</div>
				   </div>
				   <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">备注</label>
						<div class="col-md-11">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly">${entity.remark}</textarea>
						</div>
	               </div>
				</form>
			<div class="page-content">
			     <div class="form-group  form-horizontal" style="margin-right:90px;">
				    <label class="col-md-1 control-label no-padding-right">附件</label>
					<div class="col-md-11" id="editAttachmentDiv" readonly="readonly"></div>
			     </div>
			     
			    <div class="row">
					<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >入库明细列表</h5>
							<form id="materialShowForm" class="form-horizontal" role="form">
								<table id="materialEdit_table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
											<!-- <th class="center sorting_disabled" style="width:50px;">
		        								<label class="pos-rel">
		        									<input type="checkbox" class="ace" />
		        									<span class="lbl"></span>
		        								</label>
		        							</th> -->
			                                <th>物资编码</th>
			                                <th>物资名称</th>
			                                <th>规格型号</th>
			                                <th>生产厂家</th>
			                                <th>数量</th>
			                                <th>计数单位</th>
			                                <th>货区</th>
			                                <th>货位</th>
			                                <th>物资属性</th>
			                                <th>有效期</th>
			                                <th>价格(元)</th>
		                                </tr>
		                            </thead>
		                        </table>
		                     </form>
	                    </div>
	                </div>
	            </div>
        	</div>
	    		<!-- <div style="margin-right:100px;margin-top:30px;">
	        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
	    				<i class="fa fa-reply"></i>
	    				返回
	    			</button>
	        	</div> -->
        	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','combobox','uploaddropzone','my97datepicker','selectbox'], function(A){
        			var appData = ${entityJson};
        			//申请人选择带回
					var userEntity = ${sysUserEntity};
					//附件上传fileId传数组
					var uploaddropzone=new A.uploaddropzone({
						render : "#editAttachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					
					var goodsAreaValues=${goodsAreaValues};
					var goodsAllocationVal=[];
// 					//仓库下拉框联动查询货区
// 					wareHouseCombobox.change(function(event, value){
// 						$.ajax({
// 							url:format_url("/wareHouse/getGoodAreasByWareHouseId/" + value.selected),
// 							contentType: "application/json",
// 							dataType : "JSON",
// 							success: function(result){
// 								goodsAreaValues = result.options;
// 							}
// 						})
// 					});
					//combotree组件  单位  add  by  zhangxb 
					var unitNameIdCombotree = new A.combotree({
						render: "#organizationDiv",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
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
					unitNameIdCombotree.setValue('${ entity.unitId }');//默认值当前单位
					var goodsAttribute= ${goodsAttribute};//物资属性  add  by  zhangxb
					var conditions=[];
					var materialResult = [];
					//入库单id
					var dataId = appData.id;
					var editInstockMaterialDatatables = new A.datatables({
						render: '#materialEdit_table',
						options: {
					        serverSide: false,
					        multiple : true,
							ordering: true,
							checked:false,
							optWidth: 0,
							//order:[["2",'asc']],
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
													name : "goodsAllocation",
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
													goodsAllocationVal = result.options;//[{code:"1", name:"1"},{code:"2",name:"2"}];
													var cfg = {
														"container" : $(goodsAllocationSelect),
														name : "goodsAllocation",
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
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "8%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true,editType:"input",editHeight:"22px",editWidth:"95%",editReadOnly:true}, 
							         {data: "materialUnitName",width: "7%",orderable: true},
							         //添加物资属性   有效期   价格三项属性
							         {data: "goodsAreaName",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},
							         {data: "goodsAllocationName",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},	
							         {data: "goodsAttribute",width: "8%" ,orderable: true, editType:"input" , editReadOnly:true},
							         {data: "showGoodsValidity",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},
							         {data: "goodsPrice",width: "7%" ,orderable: true, editType:"input",editWidth:"95%",editReadOnly:true}
							        ],
							toolbars: []
						}
					}).render();
					
					function initDataTable(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						$.ajax({
							url: format_url("/instockMove/editData/list/"+dataId),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								if(result.data){
									editInstockMaterialDatatables.addRows(result.data);
									var tableData = result.data; 
									if(tableData){
										for(var i=0;i<tableData.length;i++){
											materialResult.push(tableData[i].detailId);
										}
									}
								}
							}
							
						});
					}; 
					
					initDataTable();
					
					
					//由迁移修改页面返回列表页面
    				$("#backBtnEdit").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url('/inOutStock/index')
    					});
    				});
					
				});
			});
        </script>
    </body>
</html>