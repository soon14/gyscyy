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
    			<li class="active">入库单修改</li>
    		</ul><!-- /.breadcrumb -->
	    	<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">入库单修改</h5>
    	</div>
    	
			<div class="col-md-12" >
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				     <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
				     <input class="col-md-12" id="wareHouseId" name="wareHouseId" type="hidden" value="${ entity.wareHouseId }">
				     <input class="col-md-12" id="unitId" name="unitId" type="hidden" value="${ entity.unitId }">
					<div class="form-group">
						<label class="col-md-1 control-label no-padding-right">入库单号</label>
						<div class="col-md-2">
						   <input class="col-md-12" id="instockNum" name="instockNum" type="text" readonly="readonly" placeholder="" maxlength="64" value="${entity.instockNum}">
	                	</div>
					    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>入库时间</label>
					    <div class="col-md-2">
							<div id="instockTimeDiv" class="form-control" style="border:none; padding:0px;max-height:500px;display:inline-block;"></div>
	                    </div>
						<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>入库类型</label>
						<div class="col-md-2">
						   <select class="col-md-12 chosen-select" id="instockType" name="instockType"></select>	
	                	</div>
					    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
					    <div class="col-md-2">
							<div style="width:210px;display:inline-block">
								<div id="applicantEditDiv">
								</div>	
							</div>
	                    </div>
	               </div>
	               <div class="form-group">
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>仓库名称</label>
					<div class="col-md-2">
						<!-- <select class="col-md-12 chosen-select" id="wareHouseId" name="wareHouseId"></select> -->
						<input class="col-md-12" id="wareHouseName" name="wareHouseName"  type="text" readonly="readonly" value="${entity.wareHouseName}"></input>
					</div>
<!-- 					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>场站名称</label> -->
<!-- 				    <div class="col-md-2"> -->
<%-- 						 <input class="col-md-12"  type="text" readonly="readonly" value="${entity.unitName}"></input> --%>
<!--                     </div> -->
               </div>
				   <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">备注</label>
						<div class="col-md-11">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128">${entity.remark}</textarea>
						</div>
	               </div>
	               </form>
			<div class="page-content">
			     <div class="form-group  form-horizontal" style="margin-right:90px;">
				    <label class="col-md-1 control-label" style="padding-right:12px;">附件</label>
					<div class="col-md-11" id="editAttachmentDiv"></div>
			     </div>
			     
			    <div class="row">
					<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class='table-title header smaller blue' >入库明细列表</h5>
							<form id="materialEditForm" class="form-horizontal" role="form">
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
			                                <th>序号</th>
			                                <th>物资编码</th>
			                                <th>物资名称</th>
			                                <th>规格型号</th>
			                                <th>生产厂家</th>
			                                <th><span style="color:red;">*</span>数量</th>
			                                <th>计数单位</th>
			                                <th>货区</th>
			                                <th>货位</th>
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
	    		<div style="margin-right:100px;margin-top:30px;">
	        		<button id="editInstockBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
	        			<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
	        			保存
	        		</button>
	        		<!-- <button id="submitInstockEditBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
	    				<i class="ace-icon glyphicon glyphicon-floppy-saved"></i>
	    				提交审核
	    			</button> -->
	        	</div>
        	</div>
		<script type="text/javascript">
		var selectMaterial4EditDialog;
		var instockDetailList = ${instockDetailList};
			jQuery(function($) {
				seajs.use(['datatables','combobox','uploaddropzone','my97datepicker','selectbox'], function(A){
        			var appData = ${entityJson};
        			var systemdate = ${systemdate};
        			//申请人选择带回
					var editManagerNamesDiv=new A.selectbox({
						id: 'applicantEditDivId',
						name:'applicant',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#applicantEditDiv',
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
					editManagerNamesDiv.setValueBack("#applicantEditDivId",appData.applicantName,appData.applicant);
					//入库时间
					var editInstockTimeDatePicker = new A.my97datepicker({
						id: 'instockTimeId',
						name: 'instockTime',
						render:'#instockTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					editInstockTimeDatePicker.setValue(appData.showInstockTime);
					//附件上传fileId传数组
					var uploaddropzone=new A.uploaddropzone({
						render : "#editAttachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//combobx组件
					var instockData = ${instockTypeCombobox};
                	var editInstockTypeCombobox = new A.combobox({
                		render: "#instockType",
                		//返回数据待后台返回TODO
                		datasource:instockData,
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	editInstockTypeCombobox.setValue(appData.instockType);
                	//combotree组件  单位  add  by  zhangxb 
// 					var unitNameIdCombotree = new A.combotree({
// 						render: "#organizationDiv",
// 						name: 'unitId',
// 						//返回数据待后台返回TODO
// 						datasource: ${unitNameIdTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'unitNameId',
// 							data: {
// 								key: {
// 									name: "name"
// 								},
// 								simpleData: {
// 									enable: true,
// 									idKey: "id",
// 									pIdKey: "parentId"
// 								}
// 							}
// 						}
// 					}).render();
// 					unitNameIdCombotree.setValue('${ entity.unitId }');//默认值当前单位,显示修改单位
                	//combobx组件
					
					var goodsAreaValues=${goodsAreaValues};
					var goodsAllocationVal=[];
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
							multiple : true,
							ordering: true,
							optWidth: 80,
							//order:[[0,'desc']],
							columns: [
							         {data:"materialId", visible:false,orderable: true}, 
							         {data: null,width: "5%",orderable: false}, 
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "8%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true,editType:"input",editWidth:"95%"}, 
							         {data: "materialUnitName",width: "7%",orderable: true,editType:"input",editReadOnly:true},
							         {data: "goodsAreaId",width: "8%" ,orderable: true, editType:"combobox"},
							         {data: "goodsAllocationId",width: "8%" ,orderable: true, editType:"combobox"},
							         //添加物资属性   有效期   价格三项属性
							         {data: "goodsAttribute",width: "8%" ,orderable: true, editType:"input",editReadOnly:true   },
							         {data: "showGoodsValidity",width: "8%" ,orderable: true, editType:"my97datepicker",sClass:'noError',cfg:{name: 'goodsValidity',
									        options : {isShowWeek : false,skin : 'ext',maxDate: "",minDate: systemdate,dateFmt: 'yyyy-MM-dd'}}},
							         {data: "goodsPrice",width: "7%" ,orderable: true, editType:"input",editWidth:"95%"}
							        ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var objMaterial = $('#materialEditForm').serializeObject();
										var obgMaterialArr = Object.keys(objMaterial);
            							var materialIdArray = [];
            							if(materialResult){
	            							for(var i=0;i<materialResult.length;i++){
	        									materialIdArray.push(materialResult[i].id);
	        								}
            							}
            							selectMaterial4EditDialog = new A.dialog({
                    						width: 1550,
                    						height: 700,
                    						title: "物资信息选择",
                    						url:format_url('/materialCategory/selectMaterialCategory4Edit?eidtInstockDataIdArray='+materialIdArray+"&instockDetailList="+instockDetailList),
                    						closed: function(){
                    							var result = selectMaterial4EditDialog.resule;
                    							if(result){
                    								for(var i=0;i<result.length;i++){
                    									materialResult.push(result[i]);
                    									editInstockMaterialDatatables.addRow({"materialId":result[i].id, "materialCode":result[i].code, "materialName":result[i].name,"materialModel":result[i].model, 
                    										"materialManufacturer":result[i].manufacturerName, "count":"","materialUnitName":result[i].unitName
                    										,"goodsAreaId":null,"goodsAllocationId":null,"goodsAttribute":result[i].materialTypeName,"showGoodsValidity":null,"goodsPrice":""});
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
										var url =format_url('/instockDetail/'+id);
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
        					                            var materialId = nData.materialId;
        												//循环数组中的每一个物资明细id
        					                            var materialResultId = materialResult[i].id;
        												//如果两个id相等，在数组中去掉这个id
        					                            if(materialId == materialResultId){
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
							url: format_url("/instockMove/editData/list/"+dataId),
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
					
					function coordinationWorkTableFormValid(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#materialEditForm').validate({
								debug:true,
								rules: {
									count:{number:true,maxlength:20},
									goodsPrice:{number:true,maxlength:20}
								}
							});
					}
					
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{maxlength:20},
							instockNum:{maxlength:64},
							instockTime:{required:true,maxlength:10},
							instockType:{required:true,maxlength:20},
							applicant:{required:true,maxlength:64},
							attachment:{maxlength:128},
							remark:{maxlength:128},
							wareHouseId:{required:true,maxlength:20}
						},
						submitHandler: function (form) {
							//添加按钮
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.attachment=JSON.stringify(uploaddropzone.getValue());
							var objMaterial = $('#materialEditForm').serializeObject();
							var objMaterialCount = objMaterial.count;
								obj.materialIdArray = [];
							var objGoodsArea = objMaterial.goodsAreaId;
							var objGoodsAllocation = objMaterial.goodsAllocationId;
							var objShowGoodsAttribute = objMaterial.showGoodsAttribute;
							var objGoodsValidity = objMaterial.goodsValidity;
							var objGoodsPrice = objMaterial.goodsPrice;
							var materialUnitName = objMaterial.materialUnitName;
							obj.materialUnitName = materialUnitName;
								obj.materialId = [];
								obj.goodsAreaId = [];
								obj.goodsAllocationId = [];
							var objGoodsAttribute = objMaterial.goodsAttribute;
							if(objMaterialCount){
								obj.materialCountArray = objMaterialCount;
								obj.goodsArea = objGoodsArea;
								obj.goodsAllocation = objGoodsAllocation;
								obj.goodsPrice=objGoodsPrice;
								obj.count=objMaterialCount;
								obj.goodsValidity=objGoodsValidity;
								obj.showGoodsAttribute=objShowGoodsAttribute;
								obj.goodsAttribute = objGoodsAttribute;
								for(var i=0;i<materialResult.length;i++){
									obj.materialIdArray.push(materialResult[i].id);
								}
							}
							 var materialSize = obj.materialIdArray;
							if(materialSize.length==0){
								alert("请选择入库物资！");
								return;
							} 
							if(obj.goodsArea==null||obj.goodsArea=="null"||obj.goodsArea.indexOf("null")>=0||obj.goodsArea.indexOf(null)>=0){
									alert("请选择货区！");
									return;
								}
								if(obj.goodsAllocation==null||obj.goodsAllocation=="null"||obj.goodsAllocation.indexOf("null")>=0||obj.goodsAllocation.indexOf(null)>=0){
									alert("请选择货位！");
									return;
								}
							//add  by  zhangxb
// 								if(obj.goodsAttribute==null||obj.goodsAttribute=="null"||obj.goodsAttribute.indexOf("null")>=0||obj.goodsAttribute.indexOf(null)>=0){
// 									alert("请选择物资属性！");
// 									return;
// 								}
							if(obj.goodsValidity==null||obj.goodsValidity=="null"||obj.goodsValidity.indexOf("null")>=0||obj.goodsValidity.indexOf(null)>=0){
									alert("请选择有效期！");
									return;
								}if(obj.goodsPrice==null||obj.goodsPrice=="null"||obj.goodsPrice.indexOf("null")>=0||obj.goodsPrice.indexOf(null)>=0){
									alert("请输入价格！");
									return;
								}if(obj.count==null||obj.count=="null"||obj.count.indexOf("null")>=0||obj.count.indexOf(null)>=0){
									alert("请输入数量！");
									return;
								}
								

							var url = format_url("/instockMove/saveEditPage/"+materialSize);
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
											url : format_url('/instockMove/index')
										});
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
					
					
					$("#editInstockBtn").on("click", function(){
						if(!coordinationWorkTableFormValid().form()){
							return;
						}
    					$("#editForm").submit();
    				});
					
					/* $('#submitInstockEditBtn').on("click",function(){
						$("#editInstockBtn").click();
					}); */
					
					//由迁移修改页面返回列表页面
    				$("#backBtnEdit").on("click",function(){
    					/* A.loadPage({
    						render : '#page-container',
    						url : format_url('/instockMove/index/' + currentPage + '/' + pageSize)
    					}); */
    					$("#page-container").load(format_url('/instockMove/index'));
    				});
					
				});
			});
        </script>
    </body>
</html>