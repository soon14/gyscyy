<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
   		<div class="row">
			<form class="form-horizontal" role="form"  style="margin-right:0%;" id="addForm">
	           <input class="col-md-12" id="id" name="id" type="hidden" value="${equipLedgerEntity.id}">			
		       <div class="widget-main no-padding">
		       	   <h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		       	   <div style="margin-right:10%;" style="margin-bottom:0px;">
				       <div class="form-group" style="margin-top: 100px;">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备编号
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="equipmentNumber" name="code" type="text"  maxlength="64" value="${equipLedgerEntity.code}" readonly="readonly">
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
								<span style="color:red;">*</span>设备类型
							</label>
							<div class="col-md-4">
							   <select class="col-md-12 chosen-select" id="equipType" name="equipType"></select>	
		                	</div>
		                		<label id="businessbusinessTypeLabel" class="col-md-2 control-label no-padding-right">
								业务类型
							</label>
							<div id="businessbusinessTypeDiv" class="col-md-4">
							   <select class="col-md-12 chosen-select" id="businessType" name="businessType"></select>	
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
				      <div class="form-group form-horizontal">
							<label class="col-md-2 control-label no-padding-right">附件</label>
							<div class="col-md-10" id="divfile" >
							</div>
					  </div>
	               </div>
               </div>
            </form>
            <form id="equipParameterForm">
	            <div class="row">
					<div class="col-xs-12">
						<!-- div.dataTables_borderWrap -->
						<div class="widget-main no-padding">
							<h5 class='table-title header smaller blue'>基础参数</h5>
							<table id="equipParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>参数名称</th>
		                                <th>默认值</th>
	                                </tr>
	                            </thead>
	                        </table>
	                    </div>
	                </div>
	            </div>
            </form>
            <form id="equipTechnologyForm">
             <div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">技术参数</h5>
						<table id="equipTechnologyParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>参数名称</th>
	                                <th>默认值</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
	    		<div >
	    			<button id="saveBtn" class="btn btn-xs btn-success pull-right" type="button" style="margin-top:15px;">
	    				<i class="ace-icon fa fa-floppy-o"></i>
	    				保存
	    			</button>
	    		</div>
            </div>
            </form>
		</div>
		<script type="text/javascript">
			var equipParameterDatatables,equipTechnologyDatatables,treeEquipParameterSelectDialog,equipModelSelectId;
			jQuery(function($) {
				var baseParameterArray = [],techlogoyParameterArray = [];
				seajs.use(['combobox','combotree','dialog','datatables','uploaddropzone'], function(A){
					var equipLedgerId  = '${equipLedgerEntity.id}';
					//文件上传
					var uploaddropzone=new A.uploaddropzone({
						render : "#divfile",
						fileId:${equipLedgerEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
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
					//业务类型
					var BusinessCombobox = new A.combobox({
						render: "#businessType",
						datasource:${businessType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					BusinessCombobox.setValue('${equipLedgerEntity.businessType}');
					//基础参数
					equipParameterDatatables = new A.datatables({
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
					            	d.length = 1000;
									d.start = 0;
									d.draw = 0;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        bInfo:false,
					        paging : false,
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "parameter",width: "auto",orderable: true,editType:"input"},
							          {data: "defaultValue",width: "auto",orderable: true,editType:"input"}
							],
						  	toolbars: [{label:"设备模版",
							   icon:"glyphicon glyphicon-plus",
							   className:"btn-success",
							   events:{
		    						click:function(event){
		    							var equipType = equipTypeCombobox.getValue();
		    							if(equipType){
			    							treeEquipParameterSelectDialog = new A.dialog({
			    	     						title:"设备模版",
			    	     						width:$(window).width()*0.75,
			    	     						height:$(window).height()*0.5,
			    	     						url:format_url('/equipModel/selectModelList/'+equipType),
			    	     						closed: function(){
			    	     							//如果没有选择模版
			    	     							if(equipModelSelectId){
				    	     							//请求模版
				    	     							equipParameterDatatables.reLoad(format_url("/modelParameter/search"));
				    	     							equipTechnologyDatatables.reLoad(format_url("/modelParameter/search"));
			    	     							}
			    	     							//清空模版ID
			    	     							equipModelSelectId= null;
			    	     						}
			    	     					}).render();
		    							}else{
		    								alert('请选择设备类型！');
		    							}
		    						}
								}
						}]
						}
					}).render();
					//技术参数
					equipTechnologyDatatables = new A.datatables({
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
					    					value:2});
					            		conditions.push({
					            			field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipModelSelectId});
					            	}else{
					            		conditions.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:2});
					            		conditions.push({
					            			field:'C_EQUIP_LEDGER_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipLedgerId});
					            	}
					            	d.length = 1000;
									d.start = 0;
									d.draw = 0;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        bInfo:false,
					        paging : false,
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "parameter",width: "auto",orderable: true,editType:"input"},
							          {data: "defaultValue",width: "auto",orderable: true,editType:"input"}]
						}
					}).render();
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
							var url = format_url("/equipLedger/updateEquimentLedger");
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							//设备参数
							var equipParameterList = $("#equipParameterForm").arraySerializeObject();
							//技术参数
							var equipTechnologyList = $("#equipTechnologyForm").arraySerializeObject();
							
							var params = {};
							params.equipmentInfo = obj;
							params.equipParameter = equipParameterList;
							params.equipTechnology = equipTechnologyList;
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(params),
								cache: false,
								type : 'POST',
								success: function(result){
									var result = result.result;
									if(result=='success'){
										alert('修改成功');
										unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
									}else{
										alert("物理设备已存在!");
									}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
    				//基础参数动态表单验证
					$('#equipParameterForm').validate({
						debug:true,
						rules:  {
						}
					});
					//技术参数动态表单验证
					$('#equipTechnologyForm').validate({
						debug:true,
						rules:  {
						}
					});
					$("#saveBtn").on("click", function(){
						//清空模版ID
						equipModelSelectId= null;
						$("#addForm").submit();
    				});
					if(treeNodelevel=='4'){
						$("#businessbusinessTypeLabel").hide();
						$("#businessbusinessTypeDiv").hide();
					}else{
						$("#businessbusinessTypeLabel").show();
						$("#businessbusinessTypeDiv").show();
					}
				});
			});
        </script>
    </body>
</html>