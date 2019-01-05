<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<!-- 		<div class="row"> -->
			<div class=" col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline text-right" role="form">
					<div class="clearfix" style="width: 95%">
						<div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
							<label class="">设备编号：</label>
							<input id="searchequipmentNumberForList" class="input-width" placeholder="请输入设备编号" type="text" >
					   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
							<label class="">设备名称：</label>
							<input id="searchequipmentName" class="input-width" placeholder="请输入设备名称" type="text" >
						</div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
							<label class="">设备类型：</label>
							<div class='form-groupp padding-zero input-width  text-left'>
								<select id="searchequipTypeForList" class="chosen-select input-width"></select>
							</div>
					   </div>
			           <div class="form-group col-lg-3 col-md-3 col-sm-3 col-xs-12">
							<label class="">规格型号：</label>
							<input id="searchequipmentVersionForList" class="input-width" placeholder="请输入规格型号" type="text" >
					   </div>
					</div>
					<div class="clearfix" style="width: 95%">
						<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
							<label class="">制造商：</label>
							<input id="searchmanuFacturerForList" class="input-width" placeholder="请输入制造商" type="text" >
						</div>
						<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
						</div>
						<div class="form-groupcol-lg-3 col-md-3 col-sm-6 col-xs-12 " style="float:right;margin-bottom:10px">
							<button id="btnSearchForEquipLedger" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>查询</button>
							<button id="btnResetForEquipLedger" class="btn btn-xs btn-primary"><i class="glyphicon glyphicon-repeat"></i>重置</button>
						</div>
					</div>
				</div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">设备台帐</h5>
						<table id="equipLedgerListForTreeTable" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th class="center sorting_disabled" style="width:50px;">
        								<label class="pos-rel">
        									<input type="checkbox" class="ace" />
        									<span class="lbl"></span>
        								</label>
        							</th>
	                                <th>设备编号</th>
	                                <th>设备名称</th>
	                                <th>设备类型</th>
	                                <th>规格型号</th>
	                                <th>制造商</th>
	                                <th>备注</th>
	                                <th>创建人</th>
	                                <th>创建时间</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
             <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">基础参数</h5>
						<table id="selectEquipBaseParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
            <div class="row">
				<div class="col-xs-12">
					<div class="widget-main no-padding">
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">技术参数</h5>
						<table id="selectEquipTechnologyParameter-table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
        </div>    
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var equipSelectId=-1,conditions=[];
			var id = "id";
			var selectEquipParameterDatatables,selectEquipTechnologyDatatables;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree'], function(A){
					var equipTypeCombobox = new A.combobox({
						render: "#searchequipTypeForList",
						datasource:${equipType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var equipLedgerDatatables = new A.datatables({
						render: '#equipLedgerListForTreeTable',
						options: {
					        "ajax": {
					            "url": format_url("/equipLedger/dataList/${pathCode}/${id}"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              },
								 complete:function(result){
									 if(typeof(result.responseJSON)!="undefined" && result.responseJSON.data.length>0){
										 equipSelectId = result.responseJSON.data[0].id;
									 }
									 selectEquipParameterDatatables.draw(true);
									 selectEquipTechnologyDatatables.draw(true);
								 }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							bStateSave: true,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:true},
							          {data: "code",width: "0%",orderable: true},
							          {data: "name",width: "0%",orderable: true},
							          {data: "equipTypeName",name:"equipType",width: "0%",orderable: true},
							          {data: "equipmentVersion",width: "0%",orderable: true},
							          {data: "manuFacturer",width: "0%",orderable: true}, 
							          {data: "remark",width: "0%",orderable: true},
							          {data: "createUserName",width: "0%",orderable: true},
							          {data: "createDate",width: "0%",orderable: true}]
						}
					}).render();
					equipLedgerDatatables._datatables.search(JSON.stringify(conditions)).draw();
					//基础参数
				    selectEquipParameterDatatables = new A.datatables({
						render: '#selectEquipBaseParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	var	conditions1=[];
					            	if(equipSelectId){
					            		conditions1.push({
											field: 'C_PARAMETER_TYPE',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:1});
					            		conditions1.push({
					            			field:'C_EQUIP_LEDGER_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipSelectId});
					            	}else{
					            		conditions1.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1});
					            	}
					            	d.conditions = conditions1;
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
				    selectEquipTechnologyDatatables = new A.datatables({
						render: '#selectEquipTechnologyParameter-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipParameter/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
						            var conditions2=[];
					            	if(equipSelectId){
					            		conditions2.push({
											field: 'C_PARAMETER_TYPE',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:2});
					            		conditions2.push({
					            			field:'C_EQUIP_LEDGER_ID',
					            			fieldType:'INT',
					    					matchType:'EQ',
					    					value:equipSelectId});
					            	}else{
					            		conditions2.push({
											field: 'C_ID',
											fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1});
					            	}
					            	d.conditions = conditions2;
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
					//查询
					$('#btnSearchForEquipLedger').on('click',function(){
						conditions=[];
						if($("#searchequipmentNumberForList").val()){
	    					conditions.push({
	        					field: 'L.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$("#searchequipmentNumberForList").val()
	        				});
						}
						if($("#searchequipmentName").val()){
	    					conditions.push({
	        					field: 'L.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$("#searchequipmentName").val()
	        				});
						}
						if(equipTypeCombobox.getValue()!=null&&equipTypeCombobox.getValue()+""!=""){
							conditions.push({
	        					field: 'L.C_EQUIP_TYPE',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:equipTypeCombobox.getValue()
	        				});
						}
						if($("#searchequipmentVersionForList").val()){
							conditions.push({
	        					field: 'L.C_VERSION',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$("#searchequipmentVersionForList").val()
	        				});
						}
						if($("#searchmanuFacturerForList").val()){
							conditions.push({
	        					field: 'L.C_MANU_FACTURER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$("#searchmanuFacturerForList").val()
	        				});
						}
						equipLedgerDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					//重置
					$('#btnResetForEquipLedger').on('click',function(){
						$('#searchequipmentNumberForList').val("")
						$('#searchequipmentName').val("")
						$('#searchequipTypeForList').val("")
						$('#searchequipTypeForList').trigger("chosen:updated");
						$('#searchequipmentVersionForList').val("")
						$('#searchmanuFacturerForList').val("")
					});
					//模版列表点击一行事件
					$("#equipLedgerListForTreeTable").on("click","tr",function(){
						equipSelectId = $(this).find("input[type=checkbox]").attr("data-id");
						selectEquipParameterDatatables.draw(true);
						selectEquipTechnologyDatatables.draw(true);
					})
				});
			});
        </script>
    </body>
</html>