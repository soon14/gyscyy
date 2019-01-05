<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<h5 class="table-title header smaller blue" style="margin-left:30px">报废库审核</h5>
		</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
			<input id="id" name="id" type="hidden" value="${id }">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						报废库单号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" readonly="readonly" type="text" placeholder="" maxlength="64" value="${scrapLibraryEntity.code }">
                    </div>
                         <label class="col-md-2 control-label no-padding-right">
						仓库
				    </label>
				    <div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select> -->
						<input class="col-md-12"    type="text" readonly="readonly" value="${sareHouseEntity.wareHouseName}"></input>
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
						出入库时间
					</label>
					 <div class="col-md-4">
				    <input class="col-md-12" id="instockTime" name="instockTime" readonly="readonly" type="text" placeholder="" maxlength="64"  value="${scrapLibraryEntity.instockTimeString}" >
                    </div>
                	<label class="col-md-2 control-label no-padding-right">
						报废来源
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="scrapSource" name="scrapSource" readonly="readonly" type="text" placeholder="" maxlength="64" value="${scrapLibraryEntity.scrapSourceName }">
                    </div>
               </div>
               <div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
						申请人
				    </label>
				    <div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select> -->
						<input class="col-md-12"    type="text" readonly="readonly" value="${applyUserEntity.name}"></input>
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
		        <div class="form-group form-horizontal" style="margin-right:200px;">
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
						<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >报废明细列表</h5>
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
    		<div style="margin-right:50px;margin-top:10px;">
        		<!-- <button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button> -->
    			<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
    				<!-- 报废库入库流程按钮-->
	    			<c:if test="${nodeList.authFactorCode=='repairBtn'}">
		        		<button  id="repairBtn"  title="可修复"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				可修复<!-- 可修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='unRepairBtn'}">
		        		<button  id="unRepairBtn"  title="不可修复"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				不可修复<!-- 不可修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='repairedBtn'}">
		        		<button  id="repairedBtn"  title="已修复"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				已修复<!-- 已修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='disRepairBtn'}">
		        		<button  id="disRepairBtn"  title="未修复"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				未修复<!-- 未修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='confirmBtn'}">
		        		<button  id="confirmBtn"  title="确认"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				确认
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='unConfirmBtn'}">
		        		<button  id="unConfirmBtn"  title="未确认"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				未确认
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='biotechAgreeBtn'}">
		        		<button  id="biotechAgreeBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意<!-- 生技处通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='biotechDisagreeBtn'}">
		        		<button  id="biotechDisagreeBtn"  title="不同意"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				不同意<!-- 生技处不通过-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='productionAgreeBtn'}">
		        		<button  id="productionAgreeBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意<!-- 生产副总通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='productionDisagreeBtn'}">
		        		<button  id="productionDisagreeBtn"  title="不同意"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				不同意<!-- 生产副总不同意 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='dropBtn'}">
		        		<button  id="dropBtn"  title="丢弃"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				丢弃<!-- 丢弃-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='saleBtn'}">
		        		<button  id="saleBtn"  title="变卖"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				变卖<!-- 变卖-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='dealBtn'}">
		        		<button  id="dealBtn"  title="处理"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				处理<!-- 处理 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='ztjBtn'}">
		        		<button  id="ztjBtn"  title="再提交"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				再提交<!-- 再提交 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='zfBtn'}">
		        		<button  id="zfBtn"  title="作废"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				作废<!-- 作废-->
				    	</button>
	    			</c:if>
    			</c:forEach>
        	</div>
       	</div>
		<script type="text/javascript">
		var entityJson  = ${entityJson};
		var materialResult = [];
		var goodsAreaValues=${goodsAreaValues};
		var type = ${stockType};
			jQuery(function($) {
				seajs.use(['datatables','combobox','uploaddropzone','my97datepicker','selectbox'], function(A){
				//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${scrapLibraryEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					var dataId = entityJson.id;
        			var appData = ${entityJson};
        			//申请人选择带回
					var conditions=[];
					var materialResult = [];
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
							         {data:"metrialId", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "8%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true}, 
							         {data: "materialUnitName",width: "7%",orderable: true},
							        /*  {data: "goodsAreaName",width: "8%" ,orderable: true},
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
					
    			
    				//管理人员可修复
					$("#repairBtn").on("click",function(e){
// 						$("#scrapSuperiorAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitRepair/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "管理人员审核",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/manageRepair"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
					});
    				//管理人员不可修复
					$("#unRepairBtn").on("click",function(e){
// 						$("#scrapSuperiorAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitReject/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "管理人员不可修复",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/unRepair"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
					});
					//专业人员修复
    				$("#repairedBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitRepair/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "专业人员修复",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/repairedAgree"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
					//专业人员未修复
    				$("#disRepairBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitReject/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "专业人员未修复",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/disRepair"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
    				//库管员确认
    				$("#confirmBtn").on("click", function(e){
    					var type = ${stockType};
    					if(type==3){
    						$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
        					var urls=format_url('/scrapLibrary/selectWarehouse/'+$("#taskId").val());
    						var obj = $("#approveForm").serializeObject();
    						obj.taskId=$("#taskId").val();
    						obj.procInstId=$("#procInstId").val();
        					submitUserDialog = new A.dialog({
    							width: 800,
    							height: 400,
    							title: "选择仓库",
    							url:urls,
    							closed: function(){
    								if(submitUserDialog.resule){
    									console.log(submitUserDialog);
    								 	obj.warehouseId=submitUserDialog.resule[0];
    								 	obj.stationSourceId=submitUserDialog.resule[1];
    								 	obj.id = dataId;
    									$.ajax({
    										url : format_url("/scrapLibrary/confirmAgree"),
    										contentType : 'application/json',
    										dataType : 'JSON',
    										type : 'POST',
    										data : JSON.stringify(obj),
    										success: function(result){
    											if(result.result=="success"){
    												alert("审批成功");
    												window.scrollTo(0,0);
    												A.loadPage({
    													render : '#page-container',
    													url : format_url("/todoTask/list/1/10")
    												});
    											}else{
    												alert(result.errorMsg);
    											}
    										},
    										error:function(v,n){
    											alert("审批失败");
    										}
    									});
    								}
    							}
    						}).render();
    					}else{
    						var obj = $("#approveForm").serializeObject();
    						obj.taskId=$("#taskId").val();
    						obj.procInstId=$("#procInstId").val();
    						obj.id = dataId;
    						obj.type=type;
        					$.ajax({
    							url : format_url("/scrapLibrary/confirmAgree"),
    							contentType : 'application/json',
    							dataType : 'JSON',
    							type : 'POST',
    							data : JSON.stringify(obj),
    							success: function(result){
    								if(result.result=="success"){
    									alert("审批成功");
    									window.scrollTo(0,0);
    									A.loadPage({
    										render : '#page-container',
    										url : format_url("/todoTask/list/1/10")
    									});
    								}else{
    									alert(result.errorMsg);
    								}
    							},
    							error:function(v,n){
    								alert("审批失败");
    							}
    						});
    					}
//     					
    				});
    				//库管员确认
//     				$("#confirmBtn").on("click", function(e){
// 						var obj = $("#approveForm").serializeObject();
// 						obj.taskId=$("#taskId").val();
// 						obj.procInstId=$("#procInstId").val();
// 						obj.id = dataId;
//     					$.ajax({
// 							url : format_url("/scrapLibrary/confirmAgree"),
// 							contentType : 'application/json',
// 							dataType : 'JSON',
// 							type : 'POST',
// 							data : JSON.stringify(obj),
// 							success: function(result){
// 								if(result.result=="success"){
// 									alert("审批成功");
// 									window.scrollTo(0,0);
// 									A.loadPage({
// 										render : '#page-container',
// 										url : format_url("/todoTask/list/1/10")
// 									});
// 								}else{
// 									alert(result.errorMsg);
// 								}
// 							},
// 							error:function(v,n){
// 								alert("审批失败");
// 							}
// 						});
//     				});
    				//库管员未确认
    				$("#unConfirmBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitReject/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "库管员未确认",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/unConfirm"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
    				//生技部同意
    				$("#biotechAgreeBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitRepair/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技部人员审核",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/bioAgree"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
    				//生技部不同意
    				$("#biotechDisagreeBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/bioDisagree"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
    				//生产副总同意
    				$("#productionAgreeBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitRepair/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技部人员审核",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/proAgree"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
    				//生产副总不同意
    				$("#productionDisagreeBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/proDisagree"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
    				//变卖
    				$("#saleBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						obj.type=type;
						$.ajax({
							url : format_url("/scrapLibrary/sale"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
//     					submitUserDialog = new A.dialog({
// 							width: 850,
// 							height: 481,
// 							title: "综合管理员处理",
// 							url:urls,
// 							closed: function(){
// 								if(submitUserDialog.resule){
// 								 	obj.userList=submitUserDialog.resule.join(",");
// 								 	obj.id = dataId;
// 									$.ajax({
// 										url : format_url("/scrapLibrary/sale"),
// 										contentType : 'application/json',
// 										dataType : 'JSON',
// 										type : 'POST',
// 										data : JSON.stringify(obj),
// 										success: function(result){
// 											if(result.result=="success"){
// 												alert("审批成功");
// 												window.scrollTo(0,0);
// 												A.loadPage({
// 													render : '#page-container',
// 													url : format_url("/todoTask/list/1/10")
// 												});
// 											}else{
// 												alert(result.errorMsg);
// 											}
// 										},
// 										error:function(v,n){
// 											alert("审批失败");
// 										}
// 									});
// 								}
// 							}
// 						}).render();
    				});
    				//丢弃
    				$("#dropBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						obj.type=type;
    					$.ajax({
							url : format_url("/scrapLibrary/drop"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
    				//综合管理员处理
    				$("#dealBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						obj.type=type;
    					$.ajax({
							url : format_url("/scrapLibrary/deal"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});
					//提交人再次提交按钮
					$("#ztjBtn").on("click", function(e){
// 						$("#scrapStockReBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitRepair/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "管理人员再提交",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitAgain"),
										contentType : 'application/json',
										dataType : 'JSON',
										type : 'POST',
										data : JSON.stringify(obj),
										success: function(result){
											if(result.result=="success"){
												alert("审批成功");
												window.scrollTo(0,0);
												A.loadPage({
													render : '#page-container',
													url : format_url("/todoTask/list/1/10")
												});
											}else{
												alert(result.errorMsg);
											}
										},
										error:function(v,n){
											alert("审批失败");
										}
									});
								}
							}
						}).render();
    				});
					
					//提交人取消按钮
    				$("#zfBtn").on("click", function(e){
//     					$("#scrapDrawStockBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						obj.type=type;
						$.ajax({
							url : format_url("/scrapLibrary/cancel"),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							data : JSON.stringify(obj),
							success: function(result){
								if(result.result=="success"){
									alert("审批成功");
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});
								}else{
									alert(result.errorMsg);
								}
							},
							error:function(v,n){
								alert("审批失败");
							}
						});
    				});	
				});
			});
        </script>
    </body>
</html>