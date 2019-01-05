<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body style="overflow: auto;">
		<div class="page-content">
			<div class="col-xs-12 search-content">
				<div class="form-inline" role="form">
		           <div class="form-group">
						<label class="" for="form-field-1">模版编号：</label>
		                <input id="searchmodelNumber" class="form-control" placeholder="请输入模版编号" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">设备名称：</label>
		                <input id="searchequipName" class="form-control" placeholder="请输入设备名称" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">规格型号：</label>
		                <input id="searchspecificationModel" class="form-control" placeholder="请输入规格型号" type="text" >
                   </div>
		           <div class="form-group">
						<label class="" for="form-field-1">制造商：</label>
		                <input id="searchmanuFacturer" class="form-control" placeholder="请输入制造商" type="text" >
                   </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="btnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="btnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
            </div>
            <!-- 模版列表 -->
			<div class="row">
				<div class="col-xs-12">
<!-- 					div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' >设备模版</h5>
						<table id="equipModel-table" name="equipModeltable" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 7%;">
							<thead>
								<tr id="equipModeltr" >
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>模版编号</th>
	                                <th>设备名称</th>
	                                <th>规格型号</th>
	                                <th>制造商</th>
	                                <th>设备类型</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
<!--             <div class="row"> -->
<!-- 				<div class="col-xs-12"> -->
					<!-- div.dataTables_borderWrap -->
<!-- 					<div class="widget-main no-padding"> -->
<!-- 						<h5 class='table-title header smaller blue' >基础参数</h5> -->
<!-- 						<table id="selectEquipBaseParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>参数</th> -->
<!-- 	                                <th>默认值</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                         </table> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
<!--             <div class="row"> -->
<!-- 				<div class="col-xs-12"> -->
					<!-- div.dataTables_borderWrap -->
<!-- 					<div class="widget-main no-padding"> -->
<!-- 						<h5 class='table-title header smaller blue' >技术参数</h5> -->
<!-- 						<table id="selectEquipTechnologyParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top: 5%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>参数</th> -->
<!-- 	                                <th>默认值</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                         </table> -->
<!--                     </div> -->
<!--                 </div> -->
<!--             </div> -->
		<div style="margin-right:0px;margin-top: 1%">
   			<button id="saveBtnForDialog" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;float: right;">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				保存
   			</button>
   		</div>
        </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				var equipType = ${equipType};
				seajs.use(['datatables', 'dialog','combobox','combotree'], function(A){
					var conditions=[],equipModelDatatablesSelectId,selecttag;
					var equipTypeCombobox = new A.combobox({
						render: "#searchequipType",
						datasource:${equipType},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					var equipModelDatatables = new A.datatables({
						render: '#equipModel-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipModel/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
										field: 'C_EQUIP_TYPE',
				    					fieldType:'INT',
				    					matchType:'EQ',
				    					value:equipType});
					            	conditions.push({
										field: 'C_STATUS',
				    					fieldType:'INT',
				    					matchType:'EQ',
				    					value:1});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple :false,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},{data: "modelNumber",width: "0%",orderable: true}, {data: "equipName",width: "0%",orderable: true}, {data: "specificationModel",width: "0%",orderable: true}, {data: "manuFacturer",width: "0%",orderable: true},{data: "equipTypeName",name:"equipType",width: "0%",orderable: true}],
						}
					}).render();
				
// 					var selectEquipParameterDatatables = new A.datatables({
// 						render: '#selectEquipBaseParameter-table',
// 						options: {
// 					        "ajax": {
// 					            "url": format_url("/modelParameter/search"),
// 					            "contentType": "application/json",
// 					            "type": "POST",
// 					            "dataType": "JSON",
// 					            "data": function (d) {
// 					            		conditions=[];
// 					            	if(equipModelDatatablesSelectId){
// 					            		conditions.push({
// 											field: 'C_PARAMETER_TYPE',
// 					    					fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:1});
// 					            		conditions.push({field:'C_MODEL_ID',
// 					            			fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:equipModelDatatablesSelectId});
// 					            	}else{
// 					            		conditions.push({
// 											field: 'C_ID',
// 											fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:-1});
// 					            	}
// 					            	d.conditions = conditions;
// 					                return JSON.stringify(d);
// 					              }
// 					        },
// 					        multiple : false,
// 					        checked: false,
// 							ordering: true,
// 							optWidth: 80,
// 							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}]
// 						}
// 					}).render();
					
// 					var selectEquipTechnologyDatatables = new A.datatables({
// 						render: '#selectEquipTechnologyParameter-table',
// 						options: {
// 					        "ajax": {
// 					            "url": format_url("/modelParameter/search"),
// 					            "contentType": "application/json",
// 					            "type": "POST",
// 					            "dataType": "JSON",
// 					            "data": function (d) {
// 						            conditions=[];
// 					            	if(equipModelDatatablesSelectId){
// 					            		conditions.push({
// 											field: 'C_PARAMETER_TYPE',
// 											fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:2});
// 					            		conditions.push({field:'C_MODEL_ID',
// 					            			fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:equipModelDatatablesSelectId});
// 					            	}else{
// 					            		conditions.push({
// 											field: 'C_ID',
// 											fieldType:'INT',
// 					    					matchType:'EQ',
// 					    					value:-1});
// 					            	}
// 					            	d.conditions = conditions;
// 					                return JSON.stringify(d);
// 					              }
// 					        },
// 					        multiple : false,
// 					        checked: false,
// 							ordering: true,
// 							optWidth: 80,
// 							columns: [{data:"id", visible:false,orderable:false}, {data: "parameter",width: "auto",orderable: true}, {data: "defaultValue",width: "auto",orderable: true}],
// 						}
// 					}).render();
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($("#searchmodelNumber").val()){
	    					conditions.push({
	        					field: 'C_MODEL_NUMBER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchmodelNumber',
	        					value:$("#searchmodelNumber").val()
	        				});
						}
						if($("#searchequipName").val()){
	    					conditions.push({
	        					field: 'C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchequipName',
	        					value:$("#searchequipName").val()
	        				});
						}
						if($("#searchspecificationModel").val()){
	    					conditions.push({
	        					field: 'C_SPECIFICATION_MODEL',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchspecificationModel',
	        					value:$("#searchspecificationModel").val()
	        				});
						}
						if($("#searchmanuFacturer").val()){
	    					conditions.push({
	        					field: 'C_MANU_FACTURER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'value:searchmanuFacturer',
	        					value:$("#searchmanuFacturer").val()
	        				});
						}

						equipModelDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#saveBtnForDialog').on('click',function(){
						if(selecttag){
							selecttag = null;
							treeEquipParameterSelectDialog.close();
						}else{
							alert("请选择模版!");
						}
					});
					$('#btnReset').on('click',function(){
						$('#searchmodelNumber').val('');
						$('#searchequipName').val('');
						$('#searchspecificationModel').val('');
						$('#searchmanuFacturer').val('');
					});
					//模版列表点击一行事件
					$("#equipModel-table").on("click","tr",function(){
						selecttag = equipModelSelectId = $(this).find("input[type=checkbox]").attr("data-id");
					})
				});
			});
        </script>
    </body>
</html>