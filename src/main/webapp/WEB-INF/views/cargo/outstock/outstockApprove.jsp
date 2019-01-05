<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="ace-save-state" id="breadcrumbs" style="margin-bottom:100px;margin-right:1.5%;">
    		<h5 class="table-title header smaller blue" style="margin-left:30px">出库单审核</h5>
		</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:5%;" id="showForm">
					<div class="form-group">
						<label class="col-md-1 control-label no-padding-right">出库单号</label>
						<div class="col-md-2">
						   <input class="col-md-12" id="outstockNum" name="outstockNum" type="text" readonly="readonly"  value="${entity.outstockNum}">
	                	</div>
					    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>出库时间</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="outstockTimeDiv" name="outstockTime" type="text" readonly="readonly" value="${entity.showOutstockTime}">
	                    </div>
						<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>出库类型</label>
						<div class="col-md-2">
						   <input class="col-md-12" id="outstockType" name="outstockType" type="text" readonly="readonly" value="${entity.outstockTypeName}">
	                	</div>
					    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="outstockType" name="applicantName" type="text" readonly="readonly" value="${entity.applicantName}">
	                    </div>
	               </div>
				   <div class="form-group">
						<label class="col-md-1 control-label no-padding-right">备注</label>
						<div class="col-md-11" style="padding-left:12px">
							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly">${entity.remark}</textarea>
						</div>
	               </div>
				</form>
			<div class="page-content">
			     <div class="form-group  form-horizontal" style="margin-right:4.4%;;">
				    <label class="col-md-1 control-label no-padding-right" style="margin-left:-8px">附件</label>
					<div class="col-md-11" id="editAttachmentDiv" readonly="readonly"></div>
			     </div>
			     
			    <div class="row">
					<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >出库明细列表</h5>
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
		        							<th>序号</th>
			                                <th>物资编码</th>
			                                <th>物资名称</th>
			                                <th>规格型号</th>
			                                <th>生产厂家</th>
			                                <th>数量</th>
			                                <th>计数单位</th>
			                                <th>物质属性</th>
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
	    			<c:if test="${nodeList.authFactorCode=='ckbhBtn'}">
		        		<button  id="ckbhBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='cktgBtn'}">
		        		<button  id="cktgBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='toStockKepperBtn'}">
		        		<button  id="toStockKepperBtn"  title="提交库管员审批"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过
				    	</button>
	    			</c:if> 
<%-- 	    			<c:if test="${nodeList.authFactorCode=='ckldshBtn'}"> --%>
<!-- 		        		<button  id="ckldshBtn"  title="领导审核"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 		    				<i class="ace-icon glyphicon glyphicon-level-up"></i> -->
<!-- 		    				领导审核 -->
<!-- 				    	</button> -->
<%-- 	    			</c:if>  --%>
	    			<c:if test="${nodeList.authFactorCode=='cancelProcessBtn'}">
		        		<button  id="cancelProcessBtn"  title="取消流程"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-sign"></i>
		    				取消流程
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='submitAgainBtn'}">
		        		<button  id="submitAgainBtn"  title="再次提交"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				再次提交
				    	</button>
	    			</c:if>
	    			<!-- 报废出库流程按钮 add  by  zhangxb-->
	    			<c:if test="${nodeList.authFactorCode=='breakRejectBtn'}">
		        		<button  id="breakRejectBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='manageAgreeBtn'}">
		        		<button  id="manageAgreeBtn"  title="管理人员通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 管理人员审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='produceArgeeBtn'}">
		        		<button  id="produceArgeeBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 生技处审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='leaderAgreeBtn'}">
		        		<button  id="leaderAgreeBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过<!-- 生产副总审核通过 -->
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='stockReBtn'}">
		        		<button  id="stockReBtn"  title="再次提交"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				再次提交
				    	</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='drawStockBtn'}">
		        		<button  id="drawStockBtn"  title="撤销流程"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				撤销流程
				    	</button>
	    			</c:if>      
    			</c:forEach>
        	</div>
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
							order:[["1",'asc']],
							"fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
						"customCreateCell" : function(column, td, cellData, rowData){
							if(column.data == "goodsArea"){
								if(cellData){
									var _self = $(td).children()[0];
									$.ajax({
										url :format_url("/wareHouse/getGoodPositionByGoodAreas/" + cellData),
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
											goodsAllocationIdsCombobox.setValue(rowData.goodsAllocation);
										}
									});
								}
								column.cfg = { 
									datasource : goodsAreaValues, 
									name : "goodsArea",
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
							         {data:"materialId", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true},   
							         {data: "materialCode",width: "10%",orderable: true}, 
							         {data: "materialName",width: "10%",orderable: true}, 
							         {data: "materialModel",width: "15%",orderable: true}, 
							         {data: "materialManufacturer",width: "15%",orderable: true}, 
							         {data: "count",width: "10%",orderable: true,editType:"input",editHeight:"25px",editWidth:"95%",editReadOnly:true}, 
							         {data: "materialUnitName",width: "5%",orderable: true},
							         {data: "goodsAttribute",width: "8%",orderable: true},
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
							url: format_url("/outstockMove/editData/list/"+dataId),
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
											materialResult.push(tableData[i].materialId);
										}
									}
								}
							}
							
						});
					}; 
					
					initDataTable();
					
					
					//由迁移修改页面返回列表页面
    				/* $("#backBtnEdit").on("click",function(){
    					A.loadPage({
    						render : '#page-container',
    						url : format_url('/outstockMove/index')
    					});
    				}); */
					
					//驳回按钮点击事件
    				$("#ckbhBtn").on("click", function(e){
    					$("#ckbhBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/outstockMove/reject"),
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
					$("#toStockKepperBtn").on("click",function(e){
						$("#toStockKepperBtn").attr({"disabled":"disabled"});
						var urls=format_url('/outstockMove/submitPersonStorekeeper/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/outstockMove/stockKepper"),
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
					//最后通过按钮点击事件
    				$("#cktgBtn").on("click", function(e){
    					$("#cktgBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/outstockMove/submitPerson/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/outstockMove/pass"),
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
					//最后通过按钮点击事件
//     				$("#cktgBtn").on("click", function(e){
//     					$("#cktgBtn").attr({"disabled":"disabled"});
//     					var obj = $("#approveForm").serializeObject();
// 						obj.taskId=$("#taskId").val();
// 						obj.procInstId=$("#procInstId").val();
// 						obj.id = dataId;
// 						$.ajax({
// 							url : format_url("/outstockMove/pass"),
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
					//送领导审核按钮点击事件
    				$("#ckldshBtn").on("click", function(e){
    					$("#ckldshBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/outstockMove/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/outstockMove/leader"),
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
					$("#submitAgainBtn").on("click", function(e){
						$("#submitAgainBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/outstockMove/submitPersonAgain/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "出库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/outstockMove/submitAgain"),
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
    				$("#cancelProcessBtn").on("click", function(e){
    					$("#cancelProcessBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/outstockMove/cancel"),
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
    				/* 报废出库流程按按钮add  by  zhangxb */
				//驳回按钮点击事件
    				$("#breakRejectBtn").on("click", function(e){
    					$("#breakRejectBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/outstockMove/breakReject"),
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
					$("#superiorAgreeBtn").on("click",function(e){
						$("#superiorAgreeBtn").attr({"disabled":"disabled"});
						var urls=format_url('/outstockMove/submitProductBreakStore/'+$("#taskId").val());
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
										url : format_url("/outstockMove/productBreak"),
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
    				$("#produceArgeeBtn").on("click", function(e){
    					$("#produceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/outstockMove/submitLeader/'+$("#taskId").val());
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
										url : format_url("/outstockMove/submitLeaderBreak"),
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
    				$("#leaderAgreeBtn").on("click", function(e){
    					$("#leaderAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/outstockMove/leaderPass"),
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
					//管理人员同意按钮点击事件
    				$("#manageAgreeBtn").on("click", function(e){
    					$("#manageAgreeBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/outstockMove/manageAgree"),
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
					$("#stockReBtn").on("click", function(e){
						$("#stockReBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/outstockMove/submitBreakAgain/'+$("#taskId").val());
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
										url : format_url("/outstockMove/submitBreakAgain"),
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
    				$("#drawStockBtn").on("click", function(e){
    					$("#drawStockBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/outstockMove/breakCancel"),
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