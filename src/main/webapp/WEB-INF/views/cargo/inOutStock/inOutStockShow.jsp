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
    			<li>出入库明细</li>
    			<li class="active">出库单查看</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:30px">出库单查看</h5>
    	</div>
    	
			<div class="col-md-12" >
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="showForm">
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
	               		<label class="col-md-1 control-label no-padding-right">审核状态</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="approveStatus" name="approveStatus" type="text" readonly="readonly" value="${entity.approveStatusName}">
	                    </div>
	                    <label class="col-md-1 control-label no-padding-right">所属仓库</label>
					    <div class="col-md-2">
							<input class="col-md-12" id="wareHouseName" name="wareHouseName"  type="text" readonly="readonly" value="${entity.wareHouseName}"></input>
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
			     <div class="form-group  form-horizontal" style="margin-right:100px;">
				    <label class="col-md-1 control-label no-padding-right">附件</label>
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
	    		<!-- <div style="margin-right:100px;margin-top:30px;">
	        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
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
					
					var conditions=[];
					var materialResult = [];
					//出库单id
					var dataId = appData.id;
					var editOutstockMaterialDatatables = new A.datatables({
						render: '#materialEdit_table',
						options: {
					        serverSide: false,
					        multiple : true,
							ordering: true,
							checked:false,
							optWidth: 0,
							order:[["2",'asc']],
							columns: [
							         {data:"id", visible:false,orderable:false}, 
							         {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
											var startIndex = meta.settings._iDisplayStart;  
											row.start=startIndex + meta.row;
											return startIndex + meta.row + 1;  
										} },
							         {data: "materialCode",width: "15%",orderable: true}, 
							         {data: "materialName",width: "15%",orderable: true}, 
							         {data: "materialModel",width: "15%",orderable: true}, 
							         {data: "materialManufacturer",width: "15%",orderable: true}, 
							         {data: "count",width: "10%",orderable: true,editType:"input",editHeight:"22px",editWidth:"95%",editReadOnly:true}, 
							         {data: "materialUnitName",width: "5%",orderable: true}, 
							         {data: "goodsAttribute",width: "5%",orderable: true},
							         {data: "goodsValidity",width: "5%",orderable: true,render:function(data){
							        	  if(data == null || data == ""){
							        		  return "";
							        	  }
							        	  var d = new Date(data);  
							        	  var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-');  
							        	  return dformat;  
							        	 
							          }}, 
							         {data: "goodsPrice",width: "5%",orderable: true}
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
									editOutstockMaterialDatatables.addRows(result.data);
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
    				$("#backBtnAdd").on("click",function(){
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