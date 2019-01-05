<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
			<div class="col-xs-12 search-content">
						<div class="form-inline text-right" role="form">
                    <div class="clearfix">
						<input id="unitIdVal" type="hidden" value="${unitId}"></input>

                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="searchDefectequipIdDiv">设备名称：</label>
                            <div id="searchequipNameDivForDefect" class="input-width  text-left padding-zero"></div>
                            <input id="searchequipIdForDefect" type="hidden" value="">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" >
                            <label class="" for="searchappraisalUserId">鉴定人员：</label>
                            <div class="padding-zero input-width  text-left">
                                <select id="searchappraisalUserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                          <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" style="margin-top: 7px">缺陷类型：</label>
                            <div class="form-group  input-width padding-zero  text-left">
                                <select id="searchtype"  name="searchtype"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" id ="unitDiv">
                            <label class="" for="searchunitIdDiv">单位名称：</label>
                            <div id="searchunitIdDivForDefect" class="input-width text-left padding-zero"></div>
                        </div>
                    </div>
					<div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="min-label" for="searchfindUserId">发现人员：</label>
                            <div class="form-groupp adding-zero input-width  text-left">
                                <select id="searchfindUserId" class="" name="searchtype"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">发现时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="searchStartfindTimeDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="searchEndfindTimeDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="min-label" for="searchprocessStatus">流程状态：</label>
                            <div class="input-width padding-zero  text-left">
                                <select id="searchprocessStatus"  name="searchprocessStatus"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="col-lg-2 " style="width:16%" for="searchdepict">缺陷描述：</label>
                            <div class="col-lg-10 col-md-7 col-sm-6 col-xs-12 " style="width:84%;padding-left:2px">
                            	<input id="searchdepict" class="col-sm-12" placeholder="请输入缺陷描述" type="text">
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-2 col-sm-12 col-xs-12" style="text-align: right">
                            <button id="btnSearchForDefect" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnResetForDefect" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px">
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
					<div class="widget-main no-padding" id="defect-print">
                        <h5 class="table-title header smaller blue" style="margin-bottom:0px!important;">缺陷管理</h5>
						<table id="defect-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>单位名称</th>
	                                <th>设备编码</th>
	                                <th>设备名称</th>
	                                <th>发现人员</th>
	                                <th>发现时间</th>
	                                <th>缺陷类型</th>
	                                <th>缺陷描述</th>
	                                <th>鉴定人员</th>
	                                <th>流程状态</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
<object id="LODOP_OB"
	classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0"
	height="0">

	<embed id="LODOP_EM" type="application/x-print-lodop" width="0"
		height="0" pluginspage="install_lodop32.exe"></embed>

</object>


		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox','selectbox'], function(A){
					var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					var dedit;
					var conditions=[];
					var equipIdJson = JSON.parse('${equipIdJson}');

					var equipIdArray = new Array();
					var equipIdString = "";
					for(i in equipIdJson){
						equipIdArray.push(equipIdJson[i].code);
					}
					if(equipIdArray.length>0){
						equipIdString = equipIdArray.join(",");
					}
					//设备名称
					var searchequipName=new A.selectbox({
						id: 'searchequipNameForDefect',
						name:'searchequipName',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#searchequipNameDivForDefect',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								var equitName="";
								var equitIds = "";
								for (var i=0; i<data.length;i++) {
									equitName += data[i].name;
									equitIds += data[i].code;
									if (i != data.length-1) {
										equitName += ",";
										equitIds += ",";
									}
								};
								$("#searchequipNameForDefect").val(equitName);
								$("#searchequipIdForDefect").val(equitIds);
							};
							
						}
					}).render();
					//设备控件下拉树
					var searchequipId = new A.combotree({
					render: "#searchDefectequipIdDiv",
					name: 'searchequipId',
					//返回数据待后台返回TODO
					datasource: [],
					width:"210px",
					options: {
						treeId: 'searchequipId',
						data: {
							key: {
								name: "name"
							},
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "parentId"
							}
						}
					}
				}).render();
				

					//部门控件下拉树
					var searchunitId = new A.combotree({
							render: "#searchunitIdDivForDefect",
							name: 'searchunitId',
							//返回数据待后台返回TODO
							datasource: ${unitNameIdTreeList},
							width:"210px",
							options: {
								treeId: 'searchunitId',
								data: {
									key: {
										name: "name"
									},
									simpleData: {
										enable: true,
										idKey: "id",
										pIdKey: "parentId"
									}
								},
								callback:{
									beforeOnClick:function(e,treeId, treeNode){
										var check = (treeNode && !treeNode.isParent);
										if (!check){
											alert("请选择风场");
										}else{
											$.ajax({
												type : 'POST',
												url : format_url('/defect/equipTree/' + treeNode.id),
												contentType : 'application/json',
												dataType : 'JSON',
												data : "",
												cache: false,
												success: function(data){
													//var obj = JSON.parse(data);
													var obj = eval(data.data);
													searchequipId.setDatasource(obj);
													$("input[name='searchequipName']").val("");
													$("#searchequipIdForDefect").val("");
												}
											});
										}
										return check;
									}
								}
							}
						}).render(); 

					var searchStartfindTime = new A.my97datepicker({
						id: 'searchStartfindTime',
						name:'searchStartfindTime',
						render:'#searchStartfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var searchEndfindTime = new A.my97datepicker({
						id: 'searchEndfindTime',
						name:'searchEndfindTime',
						render:'#searchEndfindTimeDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartfindTime\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//鉴定人
					var searchappraisalUserId = new A.combobox({
						render : "#searchappraisalUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//发现人
					var searchfindUserId = new A.combobox({
						render : "#searchfindUserId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchprocessStatus = new A.combobox({
						render : "#searchprocessStatus",
						datasource : ${searchprocessStatus},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchtype = new A.combobox({
						render : "#searchtype",
						datasource : ${searchtype},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					if ($("#unitIdVal").val() =='0') {
						$("#unitDiv").show();
					} else {
						$("#unitDiv").hide();
					}
					
					var defectDatatables = new A.datatables({
						render: '#defect-table',
						options: {
					        "ajax": {
					            "url": format_url("/equiptree/defectListWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
								if($('#searchequipIdForDefect').val() != null && $('#searchequipIdForDefect').val()+"" != ""){
				    					conditions.push({
				        					field: 'T1.C_EQUIP_CODE',
				        					fieldType:'STRING',
				        					matchType:'IN',
				        					name : searchequipId,
				        					type:3,
				        					value: $('#searchequipIdForDefect').val()
				        				});
									} else {
						            	//设备关联缺陷ID
				        				conditions.push({
				        						field: 'T1.C_EQUIP_CODE',
				        						fieldType:'STRING',
				        						matchType:'IN',
				        						value : equipIdString
				        				}); 
										
									}
			        			
// 									conditions.push({
// 		            					field: 'C_STATUS',
// 		            					fieldType:'INT',
// 		            					matchType:'IN',
// 		            					value:1
// 		            				});
					            	d.conditions = conditions;
					            	//上一条、下一条代码
// 					            	dedit=d;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							bStateSave: true,
							checked:false,
							searching: true,
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
// 						                   var startIndex = meta.settings._iDisplayStart;  
// 						                   row.start=startIndex + meta.row;
// 						                   return startIndex + meta.row + 1;  
// 						               	} },
							          {data: "unitName",width: "8%",orderable: true}, 
							          {data: "equipCode",width: "8%",orderable: true}, 
							          {data: "equipName",width: "8%",orderable: true}, 
							          {data: "findUserName",width: "8%",orderable: true}, 
							          {data: "findTime",width: "10%",orderable: true}, 
							          {data: "typeName",width: "8%",orderable: true},
							          {data: "depict",width: "12%",orderable: true}, 
							          {data: "appraisalUserName",width: "8%",orderable: true}, 
							          {data: "processStatusName",width: "8%",orderable: false}
							]
						}
					}).render();
					
					$('#btnSearchForDefect').on('click',function(){
						conditions=[];
// 						if($('#earchcode').val()){
// 	    					conditions.push({
// 	        					field: 'T.C_CODE',
// 	        					fieldType:'STRING',
// 	        					matchType:'EQ',
// 	        					name:'searchcode',
// 	        					type:1,
// 	        					value:$('#searchcode').val()
// 	        				});
// 						}
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name: searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						} 
/* 						if($('#searchequipIdForDefect').val()!=null&&$('#searchequipIdForDefect').val()+""!=""){
	    					conditions.push({
	        					field: 'T1.C_EQUIP_CODE',
	        					fieldType:'STRING',
	        					matchType:'IN',
	        					name : searchequipId,
	        					type:3,
	        					value: $('#searchequipIdForDefect').val()
	        				});
						} */
						if($('#searchfindUserId').val()){
	    					conditions.push({
	        					field: 'T.C_FIND_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchfindUserId',
	        					type:1,
	        					value:$('#searchfindUserId').val()
	        				});
						}
						if($('#searchappraisalUserId').val()){
	    					conditions.push({
	        					field: 'T.C_APPRAISAL_USER_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchappraisalUserId',
	        					type:1,
	        					value:$('#searchappraisalUserId').val()
	        				});
						}
						if($('#searchStartfindTime').val()){
	    					conditions.push({
	    						field: 'T.C_FIND_TIME',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartfindTime',
	        					type:1,
	    						value:$('#searchStartfindTime').val()
	    					});
						}
						if($('#searchEndfindTime').val()){
	    					conditions.push({
	    						field: 'T.C_FIND_TIME',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndfindTime',
	        					type:1,
	    						value:$('#searchEndfindTime').val()
	    					});
						}
						if($('#searchtype').val()){
	    					conditions.push({
	        					field: 'T.C_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchtype',
	        					type:1,
	        					value:$('#searchtype').val()
	        				});
						}
						if($('#searchdepict').val()){
	    					conditions.push({
	        					field: 'T.C_DEPICT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchdepict',
	        					type:1,
	        					value:$('#searchdepict').val()
	        				});
						}
						if($('#searchprocessStatus').val()){
	    					conditions.push({
	        					field: 'T.C_PROCESS_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchprocessStatus',
	        					type:1,
	        					value:$('#searchprocessStatus').val()
	        				});
						}
						defectDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnResetForDefect').on('click',function(){
// 						$("#searchcode").val('');
						searchunitId.setValue(null);
						$("#searchequipNameForDefect").val("");
						$("#searchequipIdForDefect").val("");
						$("#searchfindUserId").val("");
						$("#searchfindUserId").trigger("chosen:updated");
						$("#searchappraisalUserId").val("");
						$("#searchappraisalUserId").trigger("chosen:updated");
						$("#searchtype").val("");
						$("#searchtype").trigger("chosen:updated");
						$("#searchprocessStatus").val("");
						$("#searchprocessStatus").trigger("chosen:updated");
						$("#searchStartfindTime").val("");
						$("#searchEndfindTime").val("");
						$("#searchdepict").val("");
						processUserUnitRels = [];
					});
				});
			});
        </script>
    </body>
</html>