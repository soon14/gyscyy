<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
			<div class="col-lg-12 col-md-12 col-sm-12 search-content">
				<div class="form-inline  text-right" role="form">
					 <div class="clearfix" style="width: 95%">
				           <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
								<label class="">操作票编号：</label>
				                <input id="searchcodeForOperationTicket" class="input-width text-left" placeholder="请输入操作票编号" type="text" >
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<label class="" for="form-field-1">单位名称：</label>
								 <div id="searchunitIdDivForOperaion" class="input-width text-left padding-zero"></div>
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<label class="" for="form-field-1">操作人：</label>
								 <div class="padding-zero input-width  text-left">
		                   		 <select id="searchoperateId" class="col-md-12" name="searchoperateId"></select>
		                   		 </div>
		                   </div>
				           <div class="form-group form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
								<label class="" for="form-field-1">监护人：</label>
								 <div class="padding-zero input-width  text-left">
		                   		 <select id="searchguardianId" class="col-md-12" name="searchguardianId"></select>
		                   		 </div>
		                   </div>
	                    </div>
						<div class="clearfix" style="width: 95%">
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="" for="form-field-1">开始时间：</label>
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartstartDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndstartDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
					           <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
									<label class="" for="form-field-1">终了时间：</label>
									<div class="form-group date-input padding-zero text-left">
		                                <div id="searchStartendDateDiv"  style="border:none; padding:0px;"></div>
		                            </div>
			                        <label style="width: 2.6%;text-align: center">~</label>
			                        <div class="form-group date-input padding-zero text-left">
		                                <div id="searchEndendDateDiv"   style="border:none; padding:0px;"></div>
		                            </div>
			                   </div>
	                    </div>
						 <div class="clearfix" style="width: 95%">
		                     <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
	                            <label class="min-label" for="searchprocessStatus">状态：</label>
	                            <div class="input-width padding-zero  text-left">
	                                <select id="searchprocessStatusForOperationTicket"  name="searchprocessStatus"></select>
	                            </div>
	                          </div>
								 <div class="form-group col-lg-7 col-md-7 col-sm-6 col-xs-12">
		                            <label class="" for="searchdepict">操作任务：</label>
		                            <input id="searchworkText" class="input-width padding-zero derect-input  text-left" placeholder="请输入操作任务" type="text">
		                        </div>
                       		  <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="text-align: center">
	                            <button id="btnSearchForOperationTicket" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
	                                <i class="glyphicon glyphicon-search"></i>
	                           		     查询
	                            </button>
	                            <button id="btnResetForOperationTicket" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px">
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
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">操作票</h5>
						<table id="operationTicket_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
<!-- 									<th class="center sorting_disabled" style="width:50px;"> -->
<!--         								<label class="pos-rel"> -->
<!--         									<input type="checkbox" class="ace" /> -->
<!--         									<span class="lbl"></span> -->
<!--         								</label> -->
<!--         							</th> -->
	                                <th>操作票编号</th>
	                                <th>单位名称</th>
	                                <th>操作人</th>
	                                <th>监护人</th>
	                                <th>操作任务</th>
	                                <th>设备名称</th>
	                                <th>开始时间</th>
	                                <th>终了时间</th>
	                                <th>状态</th>
	                                <th>典型票</th>
<!--                                     <th>操作</th> -->
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			var listFormDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
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
					//操作人
					var searchfindUserId = new A.combobox({
						render : "#searchoperateId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//监护人
					var searchfindUserId = new A.combobox({
						render : "#searchguardianId",
						datasource : ${searchuser},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var searchprocessStatus = new A.combobox({
						render : "#searchprocessStatusForOperationTicket",
						datasource : ${searchprocessStatus},
						allowBlank: true,
						options : {
							"allow_single_deselect": true
							
						}
					}).render();
					//部门控件下拉树
					var searchunitId = new A.combotree({
					render: "#searchunitIdDivForOperaion",
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
					}
				}).render();
					//开始时间
					var searchStartstartDate = new A.my97datepicker({
						id: 'searchStartstartDate',
						name:'searchStartstartDate',
						render:'#searchStartstartDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var searchEndstartDate = new A.my97datepicker({
						id: 'searchEndstartDate',
						name:'searchEndstartDate',
						render:'#searchEndstartDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartstartDate\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var searchStartendDate = new A.my97datepicker({
						id: 'searchStartendDate',
						name:'searchStartendDate',
						render:'#searchStartendDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//终了时间
					var searchEndendDate = new A.my97datepicker({
						id: 'searchEndendDate',
						name:'searchEndendDate',
						render:'#searchEndendDateDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'searchStartendDate\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					var operationTicketDatatables = new A.datatables({
						render: '#operationTicket_table',
						options: {
					        "ajax": {
					            "url": format_url("/equiptree/operationListWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'C_EQUIP_CODE',
		            					fieldType:'STRING',
		            					matchType:'IN',
		            					value:equipIdString
		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 100,
							bStateSave: true,
							searching: true,
							checked:false,
							//aLengthMenu: [2],
							//iCookieDuration:'${cookieTime}',
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "code",width: "200px",orderable: true}, 
							          {data: "unitName",width: "100px",orderable: true},
							          {data: "operateName",width: "100px",orderable: true},
							          {data: "guardianName",width: "100px",orderable: true},
							          {data: "workText",width: "100px",orderable: true}, 
							          {data: "equipName",width: "200px",orderable: true},
							          {data: "startDate",width: "100px",orderable: true}, 
							          {data: "endDate",width: "100px",orderable: true},
							          {data: "statusName",name:"status",width: "100px",orderable: true},
							          {data: "isSetName",name:"isSet",width: "100px",orderable: true}]
						}
					}).render();
					$('#btnSearchForOperationTicket').on('click',function(){
						conditions=[];
						if($('#searchcodeForOperationTicket').val()){
	    					conditions.push({
	        					field: 'T.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchcode',
	        					type:1,
	        					value:$('#searchcodeForOperationTicket').val()
	        				});
						}
						if(searchunitId.getValue()!=null&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:searchunitId,
	        					type:2,
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchoperateId').val()){
	    					conditions.push({
	        					field: 'T.C_OPERATE_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchoperateId',
	        					type:1,
	        					value:$('#searchoperateId').val()
	        				});
						}
						if($('#searchguardianId').val()){
	    					conditions.push({
	        					field: 'T.C_GUARDIAN_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchguardianId',
	        					type:1,
	        					value:$('#searchguardianId').val()
	        				});
						}
						if($('#searchStartstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartstartDate',
	        					type:1,
	    						value:$('#searchStartstartDate').val()
	    					});
						}
						if($('#searchEndstartDate').val()){
	    					conditions.push({
	    						field: 'T.C_START_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndstartDate',
	        					type:1,
	    						value:$('#searchEndstartDate').val()
	    					});
						}
						if($('#searchStartendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'GE',
	    						name:'searchStartendDate',
	        					type:1,
	    						value:$('#searchStartendDate').val()
	    					});
						}
						if($('#searchEndendDate').val()){
	    					conditions.push({
	    						field: 'T.C_END_DATE',
	    						fieldType:'DATE',
	    						matchType:'LE',
	    						name:'searchEndendDate',
	        					type:1,
	    						value:$('#searchEndendDate').val()
	    					});
						}
						if($('#searchworkText').val()){
	    					conditions.push({
	        					field: 'T.C_WORK_TEXT',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:'searchworkText',
	        					type:1,
	        					value:$('#searchworkText').val()
	        				});
						}
						if($('#searchprocessStatusForOperationTicket').val()){
	    					conditions.push({
	        					field: 'T.C_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:'searchprocessStatus',
	        					type:1,
	        					value:$('#searchprocessStatusForOperationTicket').val()
	        				});
						}
						operationTicketDatatables._datatables.search(JSON.stringify(conditions)).draw();
					});
					$('#btnResetForOperationTicket').on('click',function(){
						$("#searchcodeForOperationTicket").val('');
						searchunitId.setValue(undefined);
						$("#searchoperateId").val("");
						$("#searchoperateId").trigger("chosen:updated");
						$("#searchguardianId").val("");
						$("#searchguardianId").trigger("chosen:updated");
						$("#searchprocessStatusForOperationTicket").val("");
						$("#searchprocessStatusForOperationTicket").trigger("chosen:updated");
						$("#searchStartstartDate").val("");
						$("#searchEndstartDate").val("");
						$("#searchStartendDate").val("");
						$("#searchEndendDate").val("");
						$("#searchworkText").val('');
					});
				});
			});
        </script>
    </body>
</html>