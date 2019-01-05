<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<!-- 		<div class="breadcrumbs ace-save-state" id="breadcrumbs"> -->
<!-- 			<ul class="breadcrumb"> -->
<!-- 				<li> -->
<!-- 					<i class="ace-icon fa fa-home home-icon"></i> -->
<!-- 					<a href="javascript:void(0);" onclick="firstPage()">首页</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a href="#">设备管理</a> -->
<!-- 				</li> -->
<!-- 				<li class="active">设备台账</li> -->
<!-- 			</ul>/.breadcrumb -->
<!-- 		</div> -->
<!-- 		<div class="tabbable" style="margin-top:2%;"> -->
<!-- 		 		<div style="float:right; margin-top:-35px;margin-right:55px;"> -->
<!-- 					<button id="btnBack" class="btn btn-xs btn-primary"> -->
<!-- 						<i class="glyphicon glyphicon-share-alt"></i> -->
<!-- 						返回 -->
<!-- 					</button> -->
<!-- 				</div>	 -->
<!--     	</div> -->
    	<div class="page-content">
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:0%;" id="addForm">
	           <input class="col-md-12" id="id" name="id" type="hidden" value="${equipLedgerEntity.id}">			
		       <div>
		       	   <h5 class='table-title header smaller blue' >基础信息</h5>
		       	   <div>
				       <div class="form-group" style="margin-top: 10%;">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备编号
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="equipmentNumber" name="code" type="text"  maxlength="64" value="${equipLedgerEntity.code}">
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备名称
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="equipmentName" name="name" type="text" maxlength="64" value="${equipLedgerEntity.name}">
		                    </div>
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备类型
							</label>
							<div class="col-md-4">
							   <select class="col-md-12 chosen-select" id="equipType" name="equipType"></select>	
		                	</div>
		                	 <label class="col-md-2 control-label no-padding-right">
								设备模版
						    </label>
						    <div class="col-md-4">
								 <select class="col-md-12 chosen-select" id="equipmodelid" name="equipModelid"></select>
		                    </div>
						  
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								制造商
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="manuFacturer" name="manuFacturer" type="text"  maxlength="64" value="${equipLedgerEntity.manuFacturer}">
		                	</div>
		                	  <label class="col-md-2 control-label no-padding-right">
								规格型号
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="equipmentVersion" name="equipmentVersion" type="text"  maxlength="64" value="${equipLedgerEntity.equipmentVersion}">
		                    </div>
		                	
		               </div>
				       <div class="form-group">
						    <label class="col-md-2 control-label no-padding-right">
								附件
						    </label>
						    <div class="col-md-10">
								<input type="file" class="col-md-12" id="fileId" id="" name="fileId"/>
		                    </div>
		               </div>
				       <div class="form-group">
						    <label class="col-md-2 control-label no-padding-right">
								备注
						    </label>
		                    <div class="col-md-10">
								<textarea class="col-md-12" id="remark"  name="remark" style="height:80px; resize:none;"  maxlength="500">${equipLedgerEntity.remark}</textarea>
		                    </div>
		               </div>
	               </div>
               	  
               </div>
            </form>
            <div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >基础参数</h5>
						<table id="equipParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>参数</th>
	                                <th>默认值</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
             <div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >技术参数</h5>
						<table id="equipTechnologyParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>参数</th>
	                                <th>默认值</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','dialog','datatables'], function(A){
					var equipLedgerId  = '${equipLedgerEntity.id}';
					var equipModelSelectId;
					//设备类型
					var equipTypeCombobox = new A.combobox({
						render: "#equipType",
						datasource:${equipType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					equipTypeCombobox.setValue('${equipLedgerEntity.equipType}');
					//设备模版
					var equipModelCombobox = new A.combobox({
						render: "#equipmodelid",
						datasource:${equipModel},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
					}).render();
					equipModelCombobox.setValue('${equipLedgerEntity.equipModelid}');
					//基础参数
					var equipParameterDatatables = new A.datatables({
						render: '#equipParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            		conditions=[];
					            	if(equipModelSelectId){
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions.push({field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipModelSelectId});
					            	}else{
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions.push({field:'C_EQUIP_LEDGER_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipLedgerId});
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}]
						}
					}).render();
					//技术参数
					var equipTechnologyDatatables = new A.datatables({
						render: '#equipTechnologyParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
						            	conditions=[];
					            	if(equipModelSelectId){
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions.push({field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipModelSelectId});
					            	}else{
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions.push({field:'C_EQUIP_LEDGER_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipLedgerId});
					            	}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}],
					
						}
					}).render();
					equipModelCombobox.change(function(){
						equipModelSelectId = equipModelCombobox.getValue()
						equipParameterDatatables.reLoad(format_url("/modelParameter/search"));
						equipParameterDatatables.draw();
						equipTechnologyDatatables.reLoad(format_url("/modelParameter/search"));
						equipTechnologyDatatables.draw();
					});
    				$('#addForm').validate({
						debug:false,
						rules: {
							equipmentNumber: {
								required: true,
								maxlength: 64
							},
							equipmentName: {
								required: true,
								maxlength: 64
							},
							equipType: {
								required: true
							},
							unitId: {
								required: true
							}
						},
						submitHandler: function (form) {
							var id = '${equipLedgerEntity.id}';
							var url = format_url("/equipLedger/"+id);
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('修改成功');
									unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url('/equipLedger/index/10')
						});
    				});
				});
			});
        </script>
    </body>
</html>