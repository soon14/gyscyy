<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content" style="overflow-height:auto;max-height:750px;">
			<div class="col-xs-12 search-content">
				<div class="form-inline text-right" role="form">
				<div class="clearfix">
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">设备编号：</label>
		                <input id="searchequipmentNumber" class="input-width text-left" placeholder="请输入设备编号" type="text" >
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">设备名称：</label>
		                <input id="searchequipmentName" class="input-width text-left" placeholder="请输入设备名称" type="text" >
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">规格型号：</label>
		                <input id="searchequipmentVersion" class="input-width text-left" placeholder="请输入规格型号" type="text" >
                   </div>
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">制造商：</label>
		                <input id="searchmanuFacturer" class="input-width text-left"placeholder="请输入制造商" type="text" >
                   </div>
                 </div>
                 <div class="clearfix">
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">风电场：</label>
				  		<div class="padding-zero input-width  text-left">
		                <select id="searchequipUnit" style="width:150px;"></select>
                   		</div>
                   </div>
                   <div class="form-group" style="float:right; margin-right:30px;">
						<button id="SelectEquipbtnSearch" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-search"></i>
							查询
						</button>
						<button id="SelectEquipbtnReset" class="btn btn-xs btn-primary">
							<i class="glyphicon glyphicon-repeat"></i>
							重置
						</button>								
					</div>
					</div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">设备台帐</h5>
						<table id="equipLedger-table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>规格型号</th>
	                                <th>制造商</th>
	                                <th>备注</th>
	                                <th>创建人</th>
	                                <th>风电场</th>
	                                <th>创建时间</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div style="margin-right:100px;margin-top:10px;">
		        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
							<i class="ace-icon fa fa-times"></i>
							取消
						</button>
		    			<button id="selectBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				确定
		    			</button>
		    		</div>
		    		<div  class="form-group" style="height:20px;"></div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var data=[];
			var unitId = "${unitId}";
			jQuery(function($) {
				var conditions=[];
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					
					var equipAppraiseIdList=${equipAppraiseIdList};
					
					var equipUnitCombobox = new A.combobox({
						render: "#searchequipUnit",
						datasource:${equipUnit},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					equipLedgerDatatables = new A.datatables({
						render: '#equipLedger-table',
						options: {
					        "ajax": {
					            "url": format_url("/equipAppraise/searchEquipLedger?equipAppraiseIdList="+equipAppraiseIdList),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
										field: 'L.C_STATUS',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:1
			        				});
					            	conditions.push({
										field: 'TR.C_UNIT_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:unitId
			        				});
					            	if(typeof unitIdCombotree !='undefined'){
						            	conditions.push({
											field: 'TREE.C_UNIT_ID',
				        					fieldType:'INT',
				        					matchType:'EQ',
				        					value:unitIdCombotree.getValue()
				        				});
					           		}
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [10],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "10%",orderable: true},
							          {data: "name",width: "10%",orderable: true},
							          {data: "equipmentVersion",width: "10%",orderable: true},
							          {data: "manuFacturer",width: "10%",orderable: true}, 
							          {data: "remark",width: "20%",orderable: true}, 
							          {data: "createUserName",width: "10%",orderable: true},
							          {data: "unitName",width: "10%",orderable: true},
							          {data: "updateDate",width: "15%",orderable: true}]
						}
					}).render();
					$("#selectBtn").on("click", function(){
						data = equipLedgerDatatables.getSelectRowDatas();
						var ids = [];
						if(data.length && data.length>0){
							for(var i =0; i<data.length; i++){
								ids.push(data[i].id);
							}
						}
						if(ids.length < 1){
							alert('请选择设备');
							data=[];
							return;
						}
// 						if(ids.length > 1){
// 							alert('只能选择一个设备');
// 							data=[];
// 							return;
// 						}
 						$(".bootbox-close-button.close").trigger("click");
    				});
					$('#SelectEquipbtnSearch').on('click',function(){
						 searchequipmentNumber = $("#searchequipmentNumber").val();
						 searchequipmentName = $("#searchequipmentName").val();
						 searchequipmentVersion = $("#searchequipmentVersion").val();
						 searchmanuFacturer = $("#searchmanuFacturer").val();
						 searchequipUnit = $("#searchequipUnit").val();
						conditions=[];
						if(searchequipmentNumber){
	    					conditions.push({
	        					field: 'L.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchequipmentNumber.trim()
	        				});
						}
						if(searchequipmentName){
	    					conditions.push({
	        					field: 'L.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchequipmentName.trim()
	        				});
						}
						if(searchequipmentVersion){
							conditions.push({
	        					field: 'L.C_VERSION',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchequipmentVersion.trim()
	        				});
						}
						if(searchmanuFacturer){
							conditions.push({
	        					field: 'L.C_MANU_FACTURER',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchmanuFacturer.trim()
	        				});
						}
						if(searchequipUnit){
							conditions.push({
	        					field: 'TR.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:searchequipUnit
	        				});
						}
						equipLedgerDatatables.draw(true);
					});
					$('#SelectEquipbtnReset').on('click',function(){
						$('#searchequipmentNumber').val('')
						$('#searchequipmentName').val('')
						$('#searchequipmentVersion').val('')
						$('#searchmanuFacturer').val('')
						searchequipmentNumber = "";
						searchequipmentName = "";
						searchequipmentVersion = "";
						searchmanuFacturer = "";
						$('#searchequipUnit').val('');
						$("#searchequipUnit").trigger("chosen:updated");
					});
				});
			});
        </script>
    </body>
</html>