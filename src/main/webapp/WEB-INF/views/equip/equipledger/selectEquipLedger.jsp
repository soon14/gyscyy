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
						<label class="" for="form-field-1">设备编码：</label>
		                <input id="searchequipmentNumber" class="input-width text-left" placeholder="请输入设备编码" type="text" >
                   </div>
		           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">设备名称：</label>
		                <input id="searchequipmentNames" class="input-width text-left" placeholder="请输入设备名称" type="text" >
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">设备类型：</label>
				  		<div class="padding-zero input-width  text-left">
		                <select id="searchequipType" style="width:150px;"></select>
                   		</div>
                   </div>
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">规格型号：</label>
		                <input id="searchequipmentVersion" class="input-width text-left" placeholder="请输入规格型号" type="text" >
                   </div>
                 </div>
                 <div class="clearfix">
                   <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
						<label class="" for="form-field-1">制造商：</label>
		                <input id="searchmanuFacturer" class="input-width text-left"placeholder="请输入制造商" type="text" >
                   </div>
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
	                                <th>设备编码</th>
	                                <th>设备名称</th>
	                                <th>设备类型</th>
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
		        		<button id="cancel" class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
							<i  class="ace-icon fa fa-times"></i>
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
			var equipInfoArray = [];
			var unitId = "${unitId}";
			//选中、取消、翻页时保存临时数据
			var tempUserUnitRels = [];
			var flag = true;
			jQuery(function($) {
				var conditions=[];
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var equipTypeCombobox = new A.combobox({
						render: "#searchequipType",
						datasource:${equipType},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
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
					            "url": format_url("/equipLedger/search"),
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
											field: 'TR.C_UNIT_ID',
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
							searching: true,
							bStateSave: true,
							optWidth: 80,
							aLengthMenu: [10],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "code",width: "0%",orderable: true},
							          {data: "name",width: "0%",orderable: true},
							          {data: "equipTypeName",name:"equipType",width: "0%",orderable: true},
							          {data: "equipmentVersion",width: "0%",orderable: true},
							          {data: "manuFacturer",width: "0%",orderable: true}, 
							          {data: "remark",width: "0%",orderable: true}, 
							          {data: "createUserName",width: "0%",orderable: true},
							          {data: "unitName",width: "0%",orderable: true},
							          {data: "updateDate",width: "0%",orderable: true}],
										fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											//设置选中
											$("#equipLedger-table").find('td input[type=checkbox]').each(function(i){
												var row = $(this).closest('tr').get(0);
												var data=equipLedgerDatatables._datatables.row(row).data();
												for(var j=0;j<userUnitRels.length;j++){
													if(data.id==userUnitRels[j].id){
														equipLedgerDatatables._datatables.row(row).select();
													};
												}
											});
										}
						}
					}).render();
					//设置选中事件
					$(equipLedgerDatatables._config.render).on('click', 'td input[type=checkbox]' , function(i){
						var row = $(this).closest('tr').get(0);
						var checked = $(this).is(":checked");
						var data=equipLedgerDatatables._datatables.row(row).data();
						if(checked) {
							var flag = true;
							for(i in userUnitRels){
								if(data.id==userUnitRels[i].id){
									flag = false;
								}
							}
							if(flag){
								userUnitRels.push(data);
								tempUserUnitRels.push(data);
							}
						}else{
							for(i in userUnitRels){
								if(data.id==userUnitRels[i].id){
									userUnitRels.splice(i,1);
								}
							}
							for(j in tempUserUnitRels){
								if(data.id==tempUserUnitRels[j].id){
									tempUserUnitRels.splice(j,1);
								}
							}
						}
					});
					//设置全选事件
					$( '#equipLedger-table > thead > tr > th input[type=checkbox], #equipLedger-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
							var th_checked = this.checked;
							$('#equipLedger-table').find('tbody > tr').each(function(){
								var flag=true;
								var row = this;
								var data=equipLedgerDatatables._datatables.row(row).data();
								if(th_checked){
									for (var i = 0; i < userUnitRels.length; i++) {
										if(userUnitRels[i].id==data.id){
											flag=false;
										}
									}
									if(flag){
										userUnitRels.push(data);
										tempUserUnitRels.push(data);
									}
								}else{
									for (var j = 0; j < userUnitRels.length; j++) {
										if(data.id==userUnitRels[j].id){
											userUnitRels.splice(j,1);
										}
									}
									for(k in tempUserUnitRels){
										if(data.id==tempUserUnitRels[k].id){
											tempUserUnitRels.splice(k,1);
										}
									}
								}  
							});
					});
					//取消时清除选中数据
					function removeUnConfirmDate(){
						var userUnitRelsIndex = [];
						var tempUserUnitRelsIndex = [];
						for(i in userUnitRels){
							for(j in tempUserUnitRels){
								if(userUnitRels[i].id==tempUserUnitRels[j].id){
									userUnitRelsIndex.push(i);
									tempUserUnitRelsIndex.push(j);
								}
							}
						}
						var a = 0;
						for(b in userUnitRelsIndex){
							userUnitRels.splice(userUnitRelsIndex[b]-a,1);
							a++;
						}
						var m = 0;
						for(n in tempUserUnitRelsIndex){
							tempUserUnitRels.splice(tempUserUnitRelsIndex[n]-m,1);
							m++;
						}
						userUnitRels = JSON.parse(JSON.stringify(processUserUnitRels)); 
					}
					$("#selectBtn").on("click", function(){
						data = userUnitRels;
						var ids = [];
						if(data.length && data.length>0){
							for(var i =0; i<data.length; i++){
								ids.push(data[i].id);
								var map = {};
								map.equipid =data[i].id; 
								map.equipName = data[i].name;
								map.equipManageType = data[i].equipManageType;
								map.equipManageTypeName = data[i].equipManageTypeName;
								equipInfoArray.push(map);
							}
						}
						if(ids.length < 1){
							alert('请选择设备');
							data=[];
							return;
						}
						processUserUnitRels = JSON.parse(JSON.stringify(userUnitRels));
// 						if(typeof overhaulAddMore =='undefined'){
// 							if(ids.length > 1){
// 								alert('只能选择一个设备');
// 								data=[];
// 								return;
// 							}
// 						}
// 						if(typeof overhaulEditMore =='undefined'){
// 							if(ids.length > 1){
// 								alert('只能选择一个设备');
// 								data=[];
// 								return;
// 							}
// 						}
						flag = false;
 						$(".bootbox-close-button.close").trigger("click");
    				});
					$('#SelectEquipbtnSearch').on('click',function(){
						 searchequipmentNumber = $("#searchequipmentNumber").val();
						 searchequipmentNames = $("#searchequipmentNames").val();
						 searchequipType = $("#searchequipType").val();
						 searchequipUnit = $("#searchequipUnit").val();
						 searchequipmentVersion = $("#searchequipmentVersion").val();
						 searchmanuFacturer = $("#searchmanuFacturer").val();
						conditions=[];
						if(searchequipmentNumber){
	    					conditions.push({
	        					field: 'L.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchequipmentNumber.trim()
	        				});
						}
						if(searchequipmentNames){
	    					conditions.push({
	        					field: 'L.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:searchequipmentNames.trim()
	        				});
						}
						if(searchequipType){
							conditions.push({
	        					field: 'L.C_EQUIP_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:searchequipType
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
						equipLedgerDatatables.draw(true);
					});
					$('#SelectEquipbtnReset').on('click',function(){
						$('#searchequipmentNumber').val('')
						$('#searchequipmentNames').val('')
						equipTypeCombobox.setValue('');
						$('#searchequipmentVersion').val('')
						$('#searchmanuFacturer').val('')
						searchequipmentNumber = "";
						searchequipmentNames = "";
						searchequipType = "";
						$('#searchequipType').val('');
						$("#searchequipType").trigger("chosen:updated");
						$('#searchequipUnit').val('');
						$("#searchequipUnit").trigger("chosen:updated");
						searchequipmentVersion = "";
						searchmanuFacturer = "";
					});
					//取消按钮
					$("#cancel").on('click',function(){
						removeUnConfirmDate();
					});
					//关闭按钮
					$(".bootbox-close-button.close").on('click',function(){
						if(flag){
							removeUnConfirmDate();
						}
					});
				});
			});
        </script>
    </body>
</html>