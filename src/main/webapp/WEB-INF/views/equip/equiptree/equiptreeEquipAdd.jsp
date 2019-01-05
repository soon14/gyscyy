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
			<form class="form-horizontal" role="form"  id="addForm">
	           <input class="col-md-12" id="equipParentId" name="equipParentId" type="hidden"  maxlength="20" value="${equipParentId}">
	           <input class="col-md-12" id="unitId" name="unitId" type="hidden"  maxlength="20" value="${unitId}">
	           <input class="col-md-12" id="pathCode" name="pathCode" type="hidden"  maxlength="20" value="${pathCode}">
	           <input class="col-md-12" id="paramArray" name="paramArray" type="hidden"  maxlength="20" >
		       <div>
		       	   <h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
		       	   <div  style="margin-right:200px;">
				       <div class="form-group" style="margin-top: 100px;">
							<label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备编号
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="code" name="code" type="text"  maxlength="64" value="">
		                	</div>
						    <label class="col-md-2 control-label no-padding-right">
								<span style="color:red;">*</span>设备名称
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="name" name="name" type="text"  maxlength="64" value="">
		                    </div>
		               </div>
				       <div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								制造商
							</label>
							<div class="col-md-4">
			                   <input class="col-md-12" id="manuFacturer" name="manuFacturer" type="text"  maxlength="64" value="">
		                	</div>
		                	  <label class="col-md-2 control-label no-padding-right">
								规格型号
						    </label>
						    <div class="col-md-4">
								<input class="col-md-12" id="equipmentVersion" name="equipmentVersion" type="text"  maxlength="64" value="">
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
								<textarea class="col-md-12" id="remark"  name="remark" style="height:80px; resize:none;"  maxlength="500"></textarea>
		                    </div>
		               </div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">
								附件 
							</label>
							<div class="col-md-10" id="divfile" ></div>
					   </div>
	               </div>
               </div>
        </form>
        <form id="equipParameterForm">
           <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						 <h5 class='table-title header smaller blue' >基础参数</h5>
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
            </div>
         </form>   
    		<div style="margin-right:0px;margin-top: 1%">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;float: right;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			var equipParameterDatatables,equipTechnologyDatatables,treeEquipParameterSelectDialog,equipModelSelectId;
			jQuery(function($) {
 				seajs.use(['combobox','combotree','dialog','datatables','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
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
					//基础参数
					equipParameterDatatables = new A.datatables({
						render: '#equipParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/modelParameter/search"),
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
					            		conditions.push(
					            			{field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipModelSelectId});
					            	}else{
					            		conditions.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-100});
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
						    	     							equipParameterDatatables.draw(true);
						    	 		 						equipTechnologyDatatables.draw(true);
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
					            "url": format_url("/modelParameter/search"),
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
					            		conditions.push({field:'C_MODEL_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipModelSelectId});
					            	}else{
					            		conditions.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-100});
					            	}
					            	d.length = 1000;
									d.start = 0;
									d.draw = 0;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        bInfo:false,
					        paging:false,
					        multiple : false,
					        checked: false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "parameter",width: "auto",orderable: true,editType:"input"},
							          {data: "defaultValue",width: "auto",orderable: true,editType:"input"}
							         ]
						}
					}).render();
    				$('#addForm').validate({
						debug:false,
						rules: {
							code: {
								required: true,
								maxlength: 64
							},
							name: {
								required: true,
								maxlength: 64
							},
							equipType: {
								required: true
							}
						},
						submitHandler: function (form){
							var url = format_url("/equipLedger/addEquimentLedger");
							var obj = $("#addForm").serializeObject();
// 							附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
// 							设备参数
							var equipParameterList = $("#equipParameterForm").arraySerializeObject();
// 							技术参数
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
										alert('添加成功');
										unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
									}else{
										alert("物理设备已存在!");
									}
								},
								error:function(v,n){
									alert('添加失败');
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