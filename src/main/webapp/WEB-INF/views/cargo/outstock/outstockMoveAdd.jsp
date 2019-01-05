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
    			<li>出库管理</li>
    			<li class="active">出库填报</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">出库单添加</h5>
    	</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="">
		       <div class="form-group">
					<label class="col-md-1 control-label no-padding-right">出库单号</label>
					<div class="col-md-3">
					   <input class="col-md-12" id="outstockNum" name="outstockNum" type="text" readonly="readonly" placeholder="" maxlength="64" value="${genetateCode}">
                	</div>
				    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>出库时间</label>
				    <div class="col-md-3">
						<div id="outstockTimeDiv" class="form-control" style="border:none; padding:0px;max-height:500px;display:inline-block;"></div>
                    </div>
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>出库类型</label>
					<div class="col-md-3">
					   <select class="col-md-12 chosen-select" id="outstockType" name="outstockType"></select>	
                	</div>
               </div>
               <div class="form-group">
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
				    <div class="col-md-3">
						<div class="col-md-12 " style="padding:0px;display:inline-block">
							<div id="applicantAddDiv">
							</div>	
						</div>
                    </div>
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>所属仓库</label>
					<div class="col-md-3">
						<select class="col-md-12 chosen-select" id="wareHouseId" name="wareHouseId"></select>
					</div>
<!-- 					<label class="col-md-1 control-label no-padding-right" ><span style="color:red;">*</span>场站名称</label> -->
				    <div class="col-md-3">
						 <div id="organizationDiv" style="display: none"></div>
                    </div>
			   </div>
	           <div class="form-group">
					<label class="col-md-1 control-label no-padding-right">备注</label>
					<div class="col-md-11">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
               </form>
               <div class="form-group">
                   <div class="form-group form-horizontal" style="margin-right:90px;">
					<label class="col-md-1 control-label no-padding-right">
						附件
					</label>
					<div class="col-md-11" id="attachmentDiv">
					</div>
               </div>
			<div class="form-group">
				 <label class="col-md-1 control-label no-padding-right"></label>
				<div class="col-xs-11" style="padding:0px;display:inline-block">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<form id="materialForm" class="form-horizontal" role="form" style="width:94%;">
						<h5 class='table-title header smaller blue' >出库明细列表</h5>
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
	                                <th>计数单位</th>
	                                <th>物资属性</th>
	                                <th>有效期</th>
	                                <th>价格(元)</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                        </table>
                      </form>
                    </div>
                </div>
            </div>
        </div>
    	<div  class="form-group" style="margin-right:90px;margin-top:30px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:5px;margin-top:20px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
    			<!-- <button id="submitBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-saved"></i>
    				提交审核
    			</button> -->
    		</div>
		</div>
		<script type="text/javascript">
		    var selectMaterialDialog;
			jQuery(function($) {
				seajs.use(['datatables','confirm','dialog','combobox','uploaddropzone','my97datepicker','selectbox'], function(A){
					//申请人选择带回
					var userEntity = ${sysUserEntity};
					var flowManagerNamesDiv=new A.selectbox({
						id: 'applicantDivId',
						name:'applicant',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#applicantAddDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					flowManagerNamesDiv.setValueBack("#applicantDivId",userEntity.name,userEntity.id);
					
					//出库时间
					var instockDate = ${systemdate};
					var outstockTimeDatePicker = new A.my97datepicker({
						id: 'outstockTimeId',
						name: 'outstockTime',
						render:'#outstockTimeDiv',
						datasource:instockDate,
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					$("#outstockTimeId").val(instockDate);
					
					//附件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//combobx组件
					var outstockTypeData = ${outstockTypeCombobox};
					var outstockTypeCombobox = new A.combobox({
						render: "#outstockType",
						//返回数据待后台返回TODO
						datasource:outstockTypeData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					outstockTypeCombobox.setValue(1);
					var wareHouseData = ${wareHouseIds};
					var wareHouseCombobox = new A.combobox({
						render: "#wareHouseId",
						//返回数据待后台返回TODO
						datasource:wareHouseData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
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
							},
							callback: {
				                beforeOnClick: function(e, treeId, treeNode){
				                	$.ajax({
										url : format_url('/outstockMove/getWareHouseList/'+treeNode.id),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										success: function(result){
											var unitNameIdTreeList = eval(result.data);
											wareHouseCombobox = new A.combobox({
												render: "#wareHouseId",
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
					unitNameIdCombotree.setValue(userEntity.unitId );//默认值当前单位
					
					var flag=false;
					wareHouseCombobox.change(function(event, value){
						if(flag){
							A.confirm('您确认改变仓库名称么？(改变后现有出库明细将会清空)',function(){
								outstockMaterialDatatables.clearDatas();
							});
						}
						flag=true;
					});
					
					var conditions=[];
					var materialResult = [];
					var outstockMaterialDatatables = new A.datatables({
						render: '#material_table',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true},
							         {data: "materialCode",width: "12%",orderable: true}, 
							         {data: "materialName",width: "12%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "8%",orderable: true,editType:"input",editWidth:"95%"}, 
							         {data: "materialUnitName",width: "8%",orderable: true,editType:"input",editReadOnly:true},
							         {data: "goodsAttribute",width: "8%",orderable: true,editType:"input",editReadOnly:true},
							         {data: "goodsValidity",width: "8%",orderable: true,render:function(data){
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
            							var dataIdArray = outstockMaterialDatatables.getDatasId();
										var wareHouseChoose=wareHouseCombobox.getValue();
										if(wareHouseChoose==""||wareHouseChoose==null){
											alert("请先选择所属仓库");
											return;
										}
										var objMaterial = $('#materialForm').serializeObject();
										var obgMaterialArr = Object.keys(objMaterial);
										console.log(objMaterial);
// 										if(objMaterial.goodsAttribute!=null){
// 											alert("不可以再添加数据");
// 											return;
// 										}
            							selectMaterialDialog = new A.dialog({
                    						width: 1550,
                    						height: 900,
                    						title: "物资信息选择",
											url:format_url('/materialCategory/selectOutStockMaterialCategory?dataIdArray='+dataIdArray+'&wareHouseChoose='+wareHouseChoose),
											closed: function(){
                    							var result = selectMaterialDialog.resule;
                    							if(result){
                    								for(var i=0;i<result.length;i++){
                    									materialResult.push(result[i]);
                    									outstockMaterialDatatables.addRow({"id":result[i].id, "materialCode":result[i].code, "materialName":result[i].name,"materialModel":result[i].model, "materialManufacturer":result[i].manufacturerName, "count":"","materialUnitName":result[i].unitName,"goodsAttribute":result[i].goodsAttribute,"goodsValidity":result[i].goodsValidity,"goodsPrice":result[i].goodsPrice,"showGoodsAttribute":result[i].showGoodsAttribute});
                    								}
                    							}
                    							outstockMaterialDatatables.draw(false);
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
					                            var materialId = nData.materialId;
												//循环数组中的每一个物资明细id
					                            var materialResultId = materialResult[i].id;
												//如果两个id相等，在数组中去掉这个id
					                            if(materialId == materialResultId){
					                               materialResult.splice(i,1);
					                            }
					                         }
											outstockMaterialDatatables.deleteSelectRow(nRow);
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
							url: format_url("/outstockMove/search/list"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								outstockMaterialDatatables.addRows(result.data);
							}
							
						});
					}; 
					
					initDataTable();
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{maxlength:20},
							outstockNum:{maxlength:64},
							outstockTimeDiv:{required:true,maxlength:0},
							outstockType:{required:true,maxlength:20},
							applicant:{required:true,maxlength:64},
							attachment:{maxlength:128},
							remark:{maxlength:128}
							},
						submitHandler: function (form) {
							//添加按钮
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.attachment=JSON.stringify(uploaddropzone.getValue());
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
							
							var url = format_url("/outstockMove/saveAddPage/"+materialSize);
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
											url : format_url('/outstockMove/index')
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
									alert('添加失败');
								}
							});
						}
					});
					
					
					function coordinationWorkTableFormValid(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#materialForm').validate({
								debug:true,
								rules: {
									count:{number:true,maxlength:20},
									goodsPrice:{number:true,maxlength:20}
								}
							});
					}
					
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
					
					//由添加迁移页返回到列表页
    				$("#backBtnAdd").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url("/outstockMove/index" )
    					});
    				});
				});
			});
        </script>
    </body>
</html>