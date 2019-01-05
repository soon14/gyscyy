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
    			<li>报废申请</li>
    			<li>库内申请</li>
    			<li class="active">新增</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">基本信息</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:140px;" id="addForm">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>申请编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>出入库时间
					</label>
					 <div class="col-md-4">
				    <div id="instockTimeDiv"></div>
                    </div>

               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
					<div class="col-md-4">
					<select class="col-md-12 chosen-select" id="userId" name="userId"></select>
							</div>
							<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>仓库
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select>
                    </div>
               </div>
               <div class="form-group">
                <!--                     <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>来源电站 -->
<!-- 					</label> -->
					<div class="col-md-4">
				    <div id="unitIdDiv" style="display: none"></div>
                    </div>
                    
               </div>
                <div class="form-group">
				<label class="col-md-2 control-label no-padding-right">备注</label>
				<div class="col-md-10">
					<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
				</div>
			</div>
               </form>
               <div class="page-content">
		        <div class="form-group form-horizontal" style="margin-left:-38px">
						<label class="col-md-2 control-label no-padding-right">
							附件
	                    </label>
						<div class="col-md-10" id="divfile" style="padding-right:130px">
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
			                                <!-- <th>货区</th>
			                                <th>货位</th> -->
			                                <th><span style="color:red;">*</span>物资属性</th>
			                                <th><span style="color:red;">*</span>有效期</th>
			                                <th><span style="color:red;">*</span>价格(元)</th>
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
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var systemdate = ${systemdate};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var wareHouseCombobox = new A.combobox({
						render: "#warehouseId",
						//返回数据待后台返回TODO
						datasource:${wareHouseIds},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//申请人
					var userId = new A.combobox({
						render : "#userId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					userId.setValue(${userEntity.id});
					//部门控件下拉树
					var unitId = new A.combotree({
					render: "#unitIdDiv",
					name: 'stationSourceId',
					//返回数据待后台返回TODO
					datasource: ${unitNameIdTreeList},
					width:"210px",
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
						},
						callback: {
			                beforeOnClick: function(e, treeId, treeNode){
			                	$.ajax({
									url : format_url('/scrapLibrary/getWareHouseList/'+treeNode.id),
									contentType : 'application/json',
									dataType : 'JSON',
									type : 'POST',
									success: function(result){
										var unitNameIdTreeList = eval(result.data);
										wareHouseCombobox = new A.combobox({
											render: "#warehouseId",
											datasource:unitNameIdTreeList,
											//multiple为true时select可以多选
											multiple:false,
											//allowBlank为false表示不允许为空
											allowBlank: true,
											options:{
												"disable_search_threshold":10
											}
										}).render();
									},
									error:function(v,n){
										alert('操作失败');
									}
								});
			                }
			              }
					}
				}).render();
					unitId.setValue(${userEntity.unitId});
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
					//报废来源
					/* var scrapSourceCombobox= new A.combobox({
						render : "#scrapSource",
						datasource : ${scrapSourceCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); */
					//出入库类型
					var instockTypeCombobox= new A.combobox({
						render : "#instockType",
						datasource : ${instockTypeCombobox},
						
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//处理方式
					/* var processModeCombobox= new A.combobox({
						render : "#processMode",
						datasource : ${processModeCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render(); */
					
					var flag=false;
					var goodsAreaValues=[];
					var goodsAllocation=[];
					var goodsAttribute= ${goodsAttribute};
					wareHouseCombobox.change(function(event, value){
						if(flag){
							A.confirm('您确认改变仓库名称么？(改变后现有报废明细将会清空)',function(){
								instockMaterialDatatables.clearDatas();
							});
						}
						flag=true;
						$.ajax({
							url:format_url("/wareHouse/getGoodAreasByWareHouseId/" + value.selected),
							contentType: "application/json",
							dataType : "JSON",
							success: function(result){
								goodsAreaValues = result.options;
							}
						})
					});
					var conditions=[];
					var materialResult = [];
					var instockMaterialDatatables = new A.datatables({
						render: '#material_table',
						options: {
					        serverSide: false,
					        multiple : true,
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							optWidth: 120,
							//order:[["1",'asc']],  多余去掉
							"fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
									cell.innerHTML =  i + 1;
								});
							},
							"customCreateCell" : function(column){
								if(column.data == "goodsAreaId"){
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
													goodsAllocation = result.options;
													var cfg = {
														"container" : $(goodsAllocationSelect),
														name : "goodsAllocationId",
														datasource: goodsAllocation,
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
							multiple : true,
							ordering: true,
							optWidth: 80,
							//order:[[0,'desc']],
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "10%",orderable: true}, 
							         {data: "materialModel",width: "15%",orderable: true}, 
							         {data: "materialManufacturer",width: "15%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true,editType:"input",editWidth:"95%"}, 
							         {data: "materialUnitName",width: "7%",orderable: true,editType:"input",editReadOnly:true},
							         /* {data: "goodsAreaId",width: "8%" ,orderable: true, editType:"combobox", cfg:{ datasource : goodsAreaValues, allowBlank: true  }  },
							         {data: "goodsAllocationId",width: "8%" ,orderable: true, editType:"combobox"}, *//* ,cfg:{ datasource :goodsAllocation, allowBlank: true } */ 
							         //添加物资属性   有效期   价格三项属性
							         {data: "goodsAttribute",width: "12%" ,orderable: true,editType:"input",editReadOnly:true},
							         {data: "goodsValidity",width: "12%" ,orderable: true,render:function(data){
							        	  if(data == null || data == ""){
							        		  return "";
							        	  }
							        	  var d = new Date(data);  
							        	  var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-');  
							        	  return dformat;  
							        	 
							          }},
							         {data: "goodsPrice",width: "7%" ,orderable: true}
							         ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
										var wareHouseChoose=wareHouseCombobox.getValue();
										if(wareHouseChoose==""||wareHouseChoose==null){
											alert("请先选择所属仓库");
											return;
										}
										var objMaterial = $('#materialForm').serializeObject();
										var obgMaterialArr = Object.keys(objMaterial);
										console.log(objMaterial);
// 										if(obgMaterialArr.length!=0){
// 											alert("不可以再添加数据");
// 											return;
// 										}
            							var dataIdArray = instockMaterialDatatables.getDatasId();
            							selectMaterialDialog = new A.dialog({
                    						width: 1550,
                    						height: 700,
                    						title: "物资信息选择",
                    						url:format_url('/materialCategory/selectOutStockMaterialCategoryIn?dataIdArray='+dataIdArray+'&wareHouseChoose='+wareHouseChoose),
                    						closed: function(){
                    							var result = selectMaterialDialog.resule;
                    							if(result){
                    								for(var i=0;i<result.length;i++){
                    									materialResult.push(result[i]);
                    									instockMaterialDatatables.addRow({"id":result[i].id, "materialCode":result[i].code, "materialName":result[i].name,"materialModel":result[i].model, 
                    										"materialManufacturer":result[i].manufacturerName, "count":"","materialUnitName":result[i].unitName
                    										,/* "goodsAreaId":null,"goodsAllocationId":null, */"goodsAttribute":result[i].goodsAttribute,"goodsValidity":result[i].goodsValidity,"goodsPrice":result[i].goodsPrice
                    										});
                    								}
                    							}
                    							instockMaterialDatatables.draw(false);
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
										var id = nData.id;
										A.confirm('您确认删除么？',function(){
											alert('删除成功');
											//循环已有物资明细id的数组
											for(var i=0;i<materialResult.length;i++){
												//取到删除的物资明细id
					                            var materialId = nData.id;
												//循环数组中的每一个物资明细id
					                            var materialResultId = materialResult[i].id;
												//如果两个id相等，在数组中去掉这个id
					                            if(materialId == materialResultId){
					                               materialResult.splice(i,1);
					                            }
					                        }
											instockMaterialDatatables.deleteSelectRow(nRow);
										});		
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
							url: format_url("/scrapLibrary/search/list"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								instockMaterialDatatables.addRows(result.data);
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
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							instockTime:{required:true,      maxlength:20},
							scrapSource:{required:true,      maxlength:20},
							stationSourceId:{required:true,      maxlength:20},
							instockType:{required:true,      maxlength:20},
							processMode:{required:true,      maxlength:20},
							warehouseId:{required:true,      maxlength:20},
							userId:{required:true,      maxlength:20},
							scrapSource:{required:true,      maxlength:20},
							processMode:{required:true,      maxlength:20},
							},
						submitHandler: function (form) {
							//添加按钮
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.type=${type}
							var objMaterial = $('#materialForm').serializeObject();
							var objMaterialCount = objMaterial.count;
							var objGoodsAttribute = objMaterial.goodsAttribute;
							var materialUnitName = objMaterial.materialUnitName;
							obj.materialUnitName = materialUnitName;
							obj.materialId = [];
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
										obj.materialId.push(materialResult[i].id);
										propertyMap[materialResult[i].id] = materialResult[i];
									}
								}else{
									if(materialResult.length==1){
										for(var i=0;i<materialResult.length;i++){
											obj.materialId.push(materialResult[i].id);
											propertyMap[materialResult[i].id] = materialResult[i];
										}
									}else{
										for(var i=1;i<materialResult.length;i++){
											obj.materialId.push(materialResult[i].id);
											propertyMap[materialResult[i].id] = materialResult[i];
										}
									}
								}
								obj.propertyMap = propertyMap;
							}
							var materialSize = obj.materialId;
							if(materialSize.length==0){
								alert("请选择出库物资！");
								return;
							}
							
							if(obj.materialCount==null||obj.materialCount=="null"||obj.materialCount.indexOf("null")>=0||obj.materialCount.indexOf(null)>=0){
								alert("请输入数量！");
								return;
							}
							
							var url = format_url("/scrapLibrary/saveAddPage/"+materialSize);
								$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=='success'){
										alert('添加成功');
										A.loadPage({
											render : '#page-container',
											url : format_url('/scrapLibrary/index/1')
										});
									}
									if(result.result=='exception'){
										if(!result.data){
											alert("库存数量不足！");
											return;
										}
									}
									if(result.result=='exceptionOne'){
											alert("单位是个的，不允许填写小数！");
											return;
									}
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						if(!coordinationWorkTableFormValid().form()){
							return;
						}
						var objMaterial = $('#materialForm').serializeObject();
						var objMaterialCount = objMaterial.count;
						var result = selectMaterialDialog.resule;
						var count = result[0].inventoryCount;
						var intcount = parseInt(count);
						var intMaterialCount = parseInt(objMaterialCount);
						if(intMaterialCount>intcount){
							alert("数量不能大于物资数量！");
							return;
						}
						$("#addForm").submit();
    				});
					$("#backBtnAdd").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/scrapLibrary/index/1')
						});
    				});
    				
				});
			});
        </script>
    </body>
</html>