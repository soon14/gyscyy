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
						出入库时间
					</label>
					 <div class="col-md-4">
				    <input class="col-md-12" id="instockTime" name="instockTime" readonly="readonly" type="text" placeholder="" maxlength="64"  value="${scrapLibraryEntity.instockTimeString}" >
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
						报废来源
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="scrapSource" name="scrapSource" readonly="readonly" type="text" placeholder="" maxlength="64" value="${scrapLibraryEntity.scrapSourceName }">
                    </div>
                     <label class="col-md-2 control-label no-padding-right">
						仓库
				    </label>
				    <div class="col-md-4">
<!-- 						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select> -->
						<input class="col-md-12"    type="text" readonly="readonly" value="${sareHouseEntity.wareHouseName}"></input>
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
	    			<c:if test="${nodeList.authFactorCode=='scrapRejectBtn'}">
		        		<button  id="scrapRejectBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回<!-- 报废库入库驳回 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapSuperiorAgreeBtn'}">
		        		<button  id="scrapSuperiorAgreeBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 报废库入库管理人员审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapProduceArgeeBtn'}">
		        		<button  id="scrapProduceArgeeBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 生技处审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapLeaderAgreeBtn'}">
		        		<button  id="scrapLeaderAgreeBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 生产副总审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapStockReBtn'}">
		        		<button  id="scrapStockReBtn"  title="再次提交"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				再次提交
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapDrawStockBtn'}">
		        		<button  id="scrapDrawStockBtn"  title="撤销流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				撤销流程
				    	</button>
	    			</c:if>
	    			<!-- 报废库出库流程按钮-->
	    			<c:if test="${nodeList.authFactorCode=='scrapOutRejectBtn'}">
		        		<button  id="scrapOutRejectBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回<!-- 报废库出库驳回 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapManageREBtn'}">
		        		<button  id="sacrapManageREBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				修复<!-- 报废出库管理人员修复-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapSkillBtn'}">
		        		<button  id="scrapSkillBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 报废库出库生技部审核 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapLeaderBtn'}">
		        		<button  id="scrapLeaderBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 报废库出库领导审核 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapStockManagerBtn'}">
		        		<button  id="scrapStockManagerBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				变卖<!-- 报废库出库库管员变卖-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapMoneyBtn'}">
		        		<button  id="scrapMoneyBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				入账<!-- 变卖费用入账-->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapTechnologyBtn'}">
		        		<button  id="scrapTechnologyBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				修复<!-- 专业人员修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapStockDiscardBtn'}">
		        		<button  id="scrapStockDiscardBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				丢弃<!-- 报废库出库库管员丢弃 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapManageNREBtn'}">
		        		<button  id="scrapManageNREBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				不修复<!-- 报废库出库不修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapTechnologyNREBtn'}">
		        		<button  id="scrapTechnologyNREBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				不修复<!-- 报废库出库技术不修复 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scropReflowBtn'}">
		        		<button  id="scropReflowBtn"  title="再次提交"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				再次提交<!-- 再次提交 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scropDrawflowBtn'}">
		        		<button  id="scropDrawflowBtn"  title="撤销流程"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				撤销流程<!-- 撤销流程 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapManagerSure'}">
		        		<button  id="scrapManagerSure"  title="撤销流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				确认修复<!-- 撤销流程 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='scrapManagerFalse'}">
		        		<button  id="scrapManagerFalse"  title="撤销流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				未修复<!-- 撤销流程 -->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='manageAgreeBtn'}">
		        		<button  id=manageAgreeBtn  title="管理员同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				管理员同意<!-- 管理员同意 -->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='manageDisgreeBtn'}">
		        		<button  id="manageDisgreeBtn"  title="管理员不同意"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				管理员不同意<!-- 管理员不同意 -->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='biotechAgreeBtn'}">
		        		<button  id="biotechAgreeBtn"  title="生技处同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				生技处同意<!-- 生技处同意 -->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='biotechDisagreeBtn'}">
		        		<button  id="biotechDisagreeBtn"  title="生技处不同意"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				生技处不同意<!-- 生技处不同意-->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='productionAgreeBtn'}">
		        		<button  id="productionAgreeBtn"  title="生产副总同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				生产副总同意<!-- 生产副总同意 -->
				    	</button>
	    			</c:if>            
	    			<c:if test="${nodeList.authFactorCode=='productionDisagreeBtn'}">
		        		<button  id="productionDisagreeBtn"  title="生产副总不同意"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				生产副总不同意<!-- 生产副总不同意 -->
				    	</button>
	    			</c:if>            
    			</c:forEach>
        	</div>
       	</div>
		<script type="text/javascript">
		var entityJson  = ${entityJson};
		var materialResult = [];
		var goodsAreaValues=${goodsAreaValues};
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
					
    				/* 报废库入库流程按按钮 */
				//驳回按钮点击事件
    				$("#scrapRejectBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/breakReject"),
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
    				//管理人员审核通过
					$("#scrapSuperiorAgreeBtn").on("click",function(e){
// 						$("#scrapSuperiorAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitProductBreakStore/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技部审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/productBreak"),
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
					//生技部审核
    				$("#scrapProduceArgeeBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "领导审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitLeaderBreak"),
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
					//领导审核同意按钮点击事件
    				$("#scrapLeaderAgreeBtn").on("click", function(e){
//     					$("#scrapLeaderAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/leaderPass"),
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
					$("#scrapStockReBtn").on("click", function(e){
// 						$("#scrapStockReBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitBreakAgain/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "报废出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitBreakAgain"),
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
    				$("#scrapDrawStockBtn").on("click", function(e){
//     					$("#scrapDrawStockBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/breakCancel"),
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
    		/* 报废库出库流程按按钮 */
				//驳回按钮点击事件
    				$("#scrapOutRejectBtn").on("click", function(e){
//     					$("#scrapOutRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/outReject"),
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
    				//管理人员修复审核通过
					$("#sacrapManageREBtn").on("click",function(e){
// 						$("#sacrapManageREBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitTechnology/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "专业技术审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/managerAgree"),
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
					//生技部审核
    				$("#scrapSkillBtn").on("click", function(e){
//     					$("#scrapSkillBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitOutLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "领导审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitSkillAgree"),
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
					//领导审核同意按钮点击事件
					 $("#scrapLeaderBtn").on("click", function(e){
//     					$("#scrapLeaderBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitManager/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "库管员",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/leaderAgree"),
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
					//变卖
    				$("#scrapStockManagerBtn").on("click", function(e){
//     					$("#scrapStockManagerBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitShoper/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "库管员审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitStockShop"),
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
					//技术人员修复按钮
    				$("#scrapTechnologyBtn").on("click", function(e){
// 						$("#scrapTechnologyBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitStater/'+$("#taskId").val());//提交库管员
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "报废出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/technologyRepair"),
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
					//变卖入账按钮点击事件
					 $("#scrapMoneyBtn").on("click", function(e){
//     					$("#scrapMoneyBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/addMoney"),
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
    				//丢弃
    				$("#scrapStockDiscardBtn").on("click", function(e){
//     					$("#scrapStockDiscardBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/discard"),
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
    				//报废库出库专业人员不修复按钮点击事件
    				$("#scrapTechnologyNREBtn").on("click", function(e){
//     					$("#scrapTechnologyNREBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/getSkiller/'+$("#taskId").val());//获取生技部审批人员
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技部审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitSkiller"),
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
    				//报废库出库管理人员不修复按钮点击事件
    				$("#scrapManageNREBtn").on("click", function(e){
//     					$("#scrapManageNREBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/getSkillerM/'+$("#taskId").val());//获取生技部审批人员
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技部审核提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitSkillerM"),
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
					//提交人再次提交按钮
					$("#scropReflowBtn").on("click", function(e){
// 						$("#scropReflowBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/scrapLibrary/submitReflowAgain/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "报废出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/submitReflow"),
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
    				$("#scropDrawflowBtn").on("click", function(e){
//     					$("#scropDrawflowBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/drawStockflow"),
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
    				//确认修复
    				$("#scrapManagerSure").on("click", function(e){
    					$("#scrapManagerSure").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/scrapLibrary/sureRepair"),
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
    			    //库管员返回技术人员重新修复
					$("#scrapManagerFalse").on("click",function(e){
						$("#scrapManagerFalse").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitTechnologyAgain/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "技术人员重新修复",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/repairFalse"),
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
    			    //管理员同意
					$("#manageAgreeBtn").on("click",function(e){
						$("#manageAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitManageAgree/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "管理员同意",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/manageAgree"),
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
					//管理员不同意
    				$("#manageDisgreeBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/manageDisgree"),
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
    				//生技处同意
					$("#biotechAgreeBtn").on("click",function(e){
						$("#biotechAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/scrapLibrary/submitBiotechAgree/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "生技处同意",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/scrapLibrary/biotechAgree"),
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
					//生技处不同意
    				$("#biotechDisagreeBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/biotechDisagree"),
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
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/productionAgree"),
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
					//生产副总不同意
    				$("#productionDisagreeBtn").on("click", function(e){
//     					$("#scrapRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/scrapLibrary/productionDisagree"),
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