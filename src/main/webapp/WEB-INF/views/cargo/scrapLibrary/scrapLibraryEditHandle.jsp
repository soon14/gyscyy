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
    			<li>报废处理</li>
    			<li class="active">修改</li>
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
			<input class="col-md-12" id="stationSourceId" name="stationSourceId" type="hidden" value="${ scrapLibraryEntity.stationSourceId }">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						处理编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="">
                    </div>
<!--                     <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						来源电站 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-4"> -->
<%-- 				    <input class="col-md-12"  type="text" readonly="readonly" value="${scrapLibraryEntity.stationSourceName}"></input> --%>
<!--                     </div> -->
					<label class="col-md-2 control-label no-padding-right">
						仓库
				    </label>
				    <div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select> -->
						<input class="col-md-12"    type="text" readonly="readonly" value="${sareHouseEntity.wareHouseName}"></input>
						<input class="col-md-12" id="warehouseId" name="warehouseId"  type="hidden" readonly="readonly" value="${scrapLibraryEntity.warehouseId}"></input>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						出入库时间
					</label>
					 <div class="col-md-4">
				    <div id="instockTimeDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
					<div class="col-md-4">
					<select class="col-md-12 chosen-select" id="userId" name="userId"></select>
							</div>
               </div>
                <div class="form-group">
				<label class="col-md-2 control-label no-padding-right">备注</label>
				<div class="col-md-10">
					<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${scrapLibraryEntity.remark }</textarea>
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
						<h5 class='table-title header smaller blue' >报废物资明细列表</h5>
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
			                               <!--  <th>货区</th>
			                                <th>货位</th> -->
			                                <th><span style="color:red;">*</span>物资属性</th>
			                                <th><span style="color:red;">*</span>有效期</th>
			                                <th><span style="color:red;">*</span>价格</th>
		                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                      </form>
                    </div>
                </div>
            </div>
            </div>
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top: 30px">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
		var entityJson  = ${entityJson};
		var goodsAreaValues=${goodsAreaValues};
		var goodsAttribute= ${goodsAttribute};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var systemdate = ${systemdate};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${scrapLibraryEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//部门控件下拉树
// 					var unitId = new A.combotree({
// 					render: "#unitIdDiv",
// 					name: 'stationSourceId',
// 					//返回数据待后台返回TODO
// 					datasource: ${unitNameIdTreeList},
// 					width:"210px",
// 					options: {
// 						treeId: 'searchunitId',
// 						data: {
// 							key: {
// 								name: "name"
// 							},
// 							simpleData: {
// 								enable: true,
// 								idKey: "id",
// 								pIdKey: "parentId"
// 							}
// 						},
// 					}
// 				}).render();
// 					unitId.setValue(${scrapLibraryEntity.stationSourceId});
					//申请人
					var userId = new A.combobox({
						render : "#userId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					userId.setValue(${scrapLibraryEntity.userId});
					//日期组件
					var instockTimePicker = new A.my97datepicker({
						id: "instockTimeDivId",
						name:"instockTime",
						render:"#instockTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					instockTimePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${scrapLibraryEntity.instockTime}" type="both"/>');
					/* //报废来源
						var scrapSourceCombobox= new A.combobox({
						render : "#scrapSource",
						datasource : ${scrapSourceCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); 
					scrapSourceCombobox.setValue("${scrapLibraryEntity.scrapSource}"); */
					//入库类型
// 					var instockTypeCombobox= new A.combobox({
// 						render : "#instockType",
// 						datasource : ${instockTypeCombobox},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					instockTypeCombobox.setValue("${scrapLibraryEntity.instockType}");
					//处理方式
					/* var processModeCombobox= new A.combobox({
						render : "#processMode",
						datasource : ${processModeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); 
					processModeCombobox.setValue("${scrapLibraryEntity.processMode}"); */
					var dataId = entityJson.id;
					var goodsAllocationVal=[];
					
					var conditions=[];
					var materialResult = [];
// 					var goodsAttribute= ${goodsAttribute};
					var editInstockMaterialDatatables = new A.datatables({
						render: '#material_table',
						options: {
					        serverSide: false,
					        multiple : true,
							ordering: true,
							checked:false,
							optWidth: 120,
							
							order:[[0,'asc']], // 为了配合弹出框必须这么写
							"fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [
							         {data:"metrialId", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "10%",orderable: true}, 
							         {data: "materialName",width: "14%",orderable: true}, 
							         {data: "materialModel",width: "15%",orderable: true}, 
							         {data: "materialManufacturer",width: "15%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true,editType:"input",editWidth:"95%"}, 
							         {data: "materialUnitName",width: "7%",orderable: true,editType:"input",editReadOnly:true},
							         /* {data: "goodsAreaId",width: "8%" ,orderable: true, editType:"combobox"},
							         {data: "goodsAllocationId",width: "8%" ,orderable: true, editType:"combobox"}, */
							       //添加物资属性   有效期   价格三项属性
							         {data: "goodsAttribute",width: "8%",orderable: false,editType:"input",editReadOnly:true
                                    },
							         {data: "goodsValidity",width: "10%",orderable: true,render:function(data){
							        	  if(data == null || data == ""){
							        		  return "";
							        	  }
							        	  var d = new Date(data);  
							        	  var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-');  
							        	  return dformat;  
							        	 
							          }}, 
							         {data: "goodsPrice",width: "8%",orderable: true}
							        ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var objMaterial = $('#materialForm').serializeObject();
										var obgMaterialArr = Object.keys(objMaterial);
										console.log(objMaterial);
										if(objMaterial.goodsAttribute!=null){
											alert("不可以再添加数据");
											return;
										}
            							var materialIdArray = [];
            							var wareHouseChooseId="${scrapLibraryEntity.warehouseId}";/* wareHouseCombobox.getValue(); //warehouseId*/
            							if(materialResult){
	            							for(var i=0;i<materialResult.length;i++){
	        									materialIdArray.push(materialResult[i].id);
	        								}
            							}
            							selectOutMaterial4EditDialog  = new A.dialog({
                    						width: 1550,
                    						height: 700,
                    						title: "物资信息选择",
                    						url:format_url('/materialCategory/selectOutStockMaterialCategory4EditHandle?materialOutStockIdArray='+materialIdArray+'&wareHouseChoose='+wareHouseChooseId),
                    						closed: function(){
                    							var result = selectOutMaterial4EditDialog.resule;
                    							if(result){
                    								for(var i=0;i<result.length;i++){
                    									materialResult.push(result[i]);
                    									editInstockMaterialDatatables.addRow({"metrialId":result[i].id, "materialCode":result[i].code, "materialName":result[i].name,"materialModel":result[i].model, 
                    										"materialManufacturer":result[i].manufacturerName, "count":"","materialUnitName":result[i].unitName
                    										,"goodsAttribute":result[i].goodsAttribute,"goodsValidity":result[i].goodsValidity,"goodsPrice":result[i].goodsPrice,"showGoodsAttribute":result[i].showGoodsAttribute,"stockId":result[i].stockId});
                    								}
                    							}
                    							editInstockMaterialDatatables.draw(false);
                    						}	
                    					}).render()
            						}
        						}
        					}],
							btns: [{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									"click": function(event, nRow, nData){
										//入库物资明细id
										var id = nData.id;
										var metrialIdData=nData.metrialId;
										if(id==undefined){
											//循环已有物资明细id的数组
											for(var i=0;i<materialResult.length;i++){
												//取到删除的物资明细id
					                            var materialId = nData.materialId;
												//循环数组中的每一个物资明细id
					                            var materialResultId = materialResult[i].id;
												//如果两个id相等，在数组中去掉这个id
					                            if(materialId == materialResultId){
					                               materialResult.splice(i,1);
					                            }
					                         }
											editOutstockMaterialDatatables.deleteSelectRow(nRow);
											editOutstockMaterialDatatables.draw(false);
										}else{
										var url =format_url('/scrapLibraryDetail/'+id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											//循环已有物资明细id的数组
        											for(var i=0;i<materialResult.length;i++){
        												//取到删除的物资明细id
        					                            //var materialId = nData.materialId;
        												//循环数组中的每一个物资明细id
        					                            var materialResultId = materialResult[i].id;
        												//如果两个id相等，在数组中去掉这个id
        					                            if(metrialIdData == materialResultId){
        					                               materialResult.splice(i,1);
        					                            }
        					                        }
        											editInstockMaterialDatatables.deleteSelectRow(nRow);
        											editInstockMaterialDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});		
									}
								}
							}
						}]
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
											materialResult.push({"id":tableData[i].metrialId});
											console.log(tableData[i].metrialId);
										}
										
									}
								}
								
							}
							
						});
					}; 
					
					initDataTable();
					function coordinationWorkTableFormValid(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#materialForm').validate({
								debug:true,
								rules: {
									count:{number:true,maxlength:20}
								}
							});
					}
					
//         			var appData = ${entityJson};
// 					var id = $('#id').val();
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
							userId:{required:true,maxlength:20},
							scrapSource:{required:true,      maxlength:20},
							processMode:{required:true,      maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.type=${type};
							var objMaterial = $('#materialForm').serializeObject();
							var objGoodsAttribute = objMaterial.goodsAttribute;
							var objMaterialCount = objMaterial.count;
							var materialUnitName = objMaterial.materialUnitName;
							obj.materialUnitName = materialUnitName;
								obj.materialIdArray = [];
								obj.materialId = [];
								obj.stockId = materialResult[0].stockId;
							var propertyMap = new Map();
							if(objMaterialCount){
								obj.materialCount = objMaterialCount;
// 								for(var i=0;i<materialResult.length;i++){
// 									obj.materialId.push(materialResult[i].id);
// 								}
								obj.goodsAttribute=objGoodsAttribute;
								var objtype = typeof(obj.materialCount);
								if(objtype!="string"){
									var inumber=materialResult.length-obj.materialCount.length;
									for(var i=inumber;i<materialResult.length;i++){
										obj.materialIdArray.push(materialResult[i].id);
										propertyMap[materialResult[i].id] = materialResult[i];
									}
								}else{
									if(materialResult.length==1){
										for(var i=0;i<materialResult.length;i++){
											obj.materialIdArray.push(materialResult[i].id);
											propertyMap[materialResult[i].id] = materialResult[i];
										}
									}else{
										for(var i=1;i<materialResult.length;i++){
											obj.materialIdArray.push(materialResult[i].id);
											propertyMap[materialResult[i].id] = materialResult[i];
										}
									}
								}
								obj.propertyMap = propertyMap;
							}
							var materialSize = obj.materialIdArray;
// 							if(objMaterialCount){
// 								obj.materialCount = objMaterialCount;
// 								obj.goodsAttribute=objGoodsAttribute;
// 								obj.goodsValidity=objMaterial.goodsValidity;
// 								obj.goodsPrice = objMaterial.goodsPrice;
// 								for(var i=0;i<materialResult.length;i++){
// 									obj.materialIdArray.push(materialResult[i].id);
// 									propertyMap[materialResult[i].id] = materialResult[i];
// 								}
// 								obj.propertyMap = propertyMap;
// 							}
							debugger;
							var url = format_url("/scrapLibrary/saveEditPage/"+materialSize);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=='success'){
										alert('修改成功');
										A.loadPage({
											render : '#page-container',
											url : format_url('/scrapLibrary/index/2')
										});
									}
									if(result.result=='exception'){
										if(!result.data){
											alert("库存数量不足！");
											return;
										}
									}
									if(result.result=='exceptionOne'){
										alert("不允许填写小数！");
										return;
								}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#materialForm").validate({
						debug:true,
						rules:  {
						}
					})
					$("#saveBtn").on("click", function(){
						if(!coordinationWorkTableFormValid().form()){
							return;
						}
						var objMaterial = $('#materialForm').serializeObject();
						var objMaterialCount = objMaterial.count;
						var countEdit = "${count}";
						var materialId = "${materialId}"
						var goodsAttribute = objMaterial.goodsAttribute;
						if(goodsAttribute==null){
							alert("请选择报废物资！");
							return;
						}
// 						if(materialId==materialResult[0].id){
							
// 							var intcount = parseInt(objMaterialCount);
// 							if(intcount>countEdit){
// 								alert("数量不能大于物资数量！");
// 								return;
// 							}
// 						}else{
// 							var result = selectOutMaterial4EditDialog.resule;
// 							var count = result[0].inventoryCount;
// 							var intcount = parseInt(count);
// 							var intMaterialCount = parseInt(objMaterialCount);
// 							if(intMaterialCount>intcount){
// 								alert("数量不能大于物资数量！");
// 								return;
// 							}
// 						}
    					$("#editForm").submit();
    				});
					$("#backBtn").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/scrapLibrary/index/2')
						});
    				});
				});
			});
        </script>
    </body>
</html>