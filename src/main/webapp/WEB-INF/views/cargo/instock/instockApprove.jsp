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
    		<h5 class="table-title header smaller blue" style="margin-left:30px;margin-right:20px"">入库单审核</h5>
		</div>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:4%;" id="approveForm">
				<div class="form-group">
					<label class="col-md-1 control-label no-padding-right" style="padding-left:3px">入库单号</label>
					<div class="col-md-2">
					   <input class="col-md-12" id="instockNum" name="instockNum" type="text" readonly="readonly"  value="${entity.instockNum}">
                	</div>
				    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>入库时间</label>
				    <div class="col-md-2">
						<input class="col-md-12" id="instockTimeDiv"  type="text" readonly="readonly" value="${entity.showInstockTime}">
                    </div>
					<label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>入库类型</label>
					<div class="col-md-2">
					   <input class="col-md-12" id="instockType" name="instockType" type="text" readonly="readonly" value="${entity.instockTypeName}">
                	</div>
				    <label class="col-md-1 control-label no-padding-right"><span style="color:red;">*</span>申请人</label>
				    <div class="col-md-2">
						<input class="col-md-12" id="instockType" name="applicantName" type="text" readonly="readonly" value="${entity.applicantName}">
                    </div>
               </div>
			   <div class="form-group" style="padding-left:0px">
					<label class="col-md-1 control-label no-padding-right">备注</label>
					<div class="col-md-11"  style="width:91.05%">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly">${entity.remark}</textarea>
					</div>
               </div>
			</form>
		<div class="page-content">
		     <div class="form-group  form-horizontal" style="margin-right:3.4%;">
			    <label class="col-md-1 control-label no-padding-right" style="width:7.6%">附件</label>
				<div class="col-md-11" id="editAttachmentDiv" readonly="readonly" style="width:91.7%"></div>
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
	        							<th>序号</th>
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
    		<div style="margin-right:50px;margin-top:10px;">
        		<!-- <button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button> -->
    			<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
	    			<c:if test="${nodeList.authFactorCode=='rkbhBtn'}">
		        		<button  id="rkbhBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='rktgBtn'}">
		        		<button  id="rktgBtn"  title="通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				通过
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='rkzyshBtn'}">
		        		<button  id="rkzyshBtn"  title="专业审核"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-level-up"></i>
		    				专业审核
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='manageAgreeBtn'}">
		        		<button  id="manageAgreeBtn"  title="管理人员通过"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				管理员通过
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='manageRejectBtn'}">
		        		<button  id="manageRejectBtn"  title="管理员不通过"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				管理员驳回
				    	</button>
	    			</c:if> 
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
					//var goodsAttribute= ${goodsAttribute};//物资属性  add  by  zhangxb
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
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true}, 
							         {data: "materialCode",width: "8%",orderable: true}, 
							         {data: "materialName",width: "8%",orderable: true}, 
							         {data: "materialModel",width: "12%",orderable: true}, 
							         {data: "materialManufacturer",width: "12%",orderable: true}, 
							         {data: "count",width: "6%",orderable: true,editType:"input",editHeight:"25px",editWidth:"95%",editReadOnly:true}, 
							         {data: "materialUnitName",width: "7%",orderable: true},
							         //添加物资属性   有效期   价格三项属性
							         {data: "goodsAreaName",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},
							         {data: "goodsAllocationName",width: "8%" ,orderable: true, editType:"input",editReadOnly:true},	
							         {data: "goodsAttribute",width: "8%" ,orderable: true, editType:"input" ,editReadOnly:true},
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
    						url : format_url('/instockMove/index')
    					});
    				}); */
					
					//驳回按钮点击事件
    				$("#rkbhBtn").on("click", function(e){
    					$("#rkbhBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/instockMove/reject"),
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
					
    				//通过按钮点击事件
    				$("#rktgBtn").on("click", function(e){
//     					$("#scrapProduceArgeeBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/instockMove/submitPerson/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "库管员通过",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/instockMove/pass"),
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
					//管理员通过按钮点击事件
    				$("#manageAgreeBtn").on("click", function(e){
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/instockMove/managePass"),
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
    				//管理员驳回按钮点击事件
    				$("#manageRejectBtn").on("click", function(e){
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/instockMove/manageReject"),
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
					//送专业审核按钮点击事件
    				$("#rkzyshBtn").on("click", function(e){
    					$("#rkzyshBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/instockMove/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "入库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/instockMove/leader"),
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
    					var urls=format_url('/instockMove/submitPersonAgain/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "入库管理提交人",
							url:urls,
							closed: function(){
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/instockMove/submitAgain"),
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
							url : format_url("/instockMove/cancel"),
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