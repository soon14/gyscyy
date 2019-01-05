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
				<div class="form-inline text-right" role="form">
                    <div class="clearfix" style="width: 95%">
                    	<input type="hidden" id="pathCodeVal" value="${pathCode}"/>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
                            <label class="" for="equipAbnormalBill">设备异动单号：</label>
                            <input id="equipAbnormalBill" class="input-width text-left" placeholder="请输入设备异动单号" type="text">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <label class="" for="equipmentCode">设备异动编号：</label>
                            <input id="equipmentCode" class="input-width text-left" placeholder="请输入设备异动编号" type="text">
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 " >
                            <label class="" for="applyUserId">申请人：</label>
                            <div class="padding-zero input-width  text-left">
                                <select id="applyUserId" class="form-control chosen-select"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" id="unitNameDiv">
                            <label class="" for="unitIdForequipAbnormal">单位名称：</label>
                            <div id="unitIdForequipAbnormal" class="input-width text-left padding-zero"></div>
                        </div>
                    </div>
					<div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">开始时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planBeginDateBeginDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planBeginDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                        <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                            <label class="">结束时间：</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planEndDateBeginDiv"  style="border:none; padding:0px;"></div>
                            </div>
                            <label style="width: 2.6%;text-align: center">~</label>
                            <div class="form-group date-input padding-zero text-left">
                                <div id="planEndDateEndDiv"   style="border:none; padding:0px;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix" style="width: 95%">
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12" style="height: 31px">
                              <label  for="processStatus">审批状态：</label>
							<div class="padding-zero input-width  text-left">
                                 <select id="processStatus"  class="form-control chosen-select"></select>
                            </div>
                        </div>
                        <div class="form-group col-lg-7 col-md-7 col-sm-6 col-xs-12">
                             <label for="abnormalDesc">设备异动说明：</label>
                                <input id="abnormalDesc"  class="input-width padding-zero derect-input  text-left" placeholder="请输入设备异动说明" type="text">
                        </div>
                        <div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12" style="float: right;">
                            <button id="btnSearchForEquipAbnormal" class="btn btn-xs btn-primary" style="overflow: hidden;width: 48%;max-width: 54px">
                                <i class="glyphicon glyphicon-search"></i>
                               		 查询
                            </button>
                            <button id="btnResetEquipAbnormal" class="btn btn-xs btn-primary"style="overflow: hidden;width: 48%;max-width: 54px" type="button">
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
						<h5 class='table-title header smaller blue' style="margin-bottom:0px!important;">设备异动</h5>
						<table id="equipAbnormal-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
<!-- 									<th class="center sorting_disabled" style="width:50px;"> -->
<!--         								<label class="pos-rel"> -->
<!--         									<input type="checkbox" class="ace" /> -->
<!--         									<span class="lbl"></span> -->
<!--         								</label> -->
<!--         							</th> -->
	                                <th>设备异动单号</th>
	                                <th>单位名称</th>
	                                <th>设备异动说明</th>
	                                <th>设备异动编号</th>
	                                <th>设备名称</th>
	                                <th>开始时间</th>
	                                <th>结束时间</th>
	                                <th>申请人</th>
	                                <th>审批状态</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
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
					//单位名称
					var unitNameTreeListCombobox = new A.combotree({
						render: "#unitIdForequipAbnormal",
						name: 'unitId',
						datasource: ${unitNameIdTreeList},
						width:"230px",
						expand:true,
						multiple:false,
						options: {
							treeId: 'funCodeTree',
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
					//申请人
					var applyUserIdCombobox = new A.combobox({
						render: "#applyUserId",
						datasource:${applyUserId},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//开始时间
					var searchBeginStartfindTime = new A.my97datepicker({
						id: 'planBeginDateBegin',
						name:'planBeginDate',
						render:'#planBeginDateBeginDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//开始时间
					var searchBeginEndtfindTime = new A.my97datepicker({
						id: 'planBeginDateEnd',
						name:'planBeginDate',
						render:'#planBeginDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planBeginDateBegin\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var searchEndStartfindTime = new A.my97datepicker({
						id: 'planEndDateBegin',
						name:'planEndDate',
						render:'#planEndDateBeginDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//结束时间
					var searchEndEndfindTime = new A.my97datepicker({
						id: 'planEndDateEnd',
						name:'planEndDate',
						render:'#planEndDateEndDiv',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'planEndDateBegin\\')}",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					//状态
					var processStatusNameCombobox = new A.combobox({
						render: "#processStatus",
						datasource:${processStatus},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					if ($("#pathCodeVal").val() != '1') {
						$("#unitNameDiv").hide();
					} else {
						$("#unitNameDiv").show();
					}
					var equipAbnormalDatatables = new A.datatables({
						render: '#equipAbnormal-table',
						options: {
					        "ajax": {
					            "url": format_url("/equiptree/equipAbnormalListWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'R.C_EQUIP_CODE',
		            					fieldType:'STRING',
		            					matchType:'IN',
		            					value : equipIdString
		            				});
					            	conditions.push({
										field: 'T.C_STATUS',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:1
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
							//aLengthMenu: [5],
							//iCookieDuration:'${cookieTime}',
							iCookieDuration:cookieTime,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "equipAbnormalBill",width: "180px",orderable: true}, 
							          {data: "unitName",width: "150px",orderable: true},
							          {data: "abnormalDesc",width: "20%",orderable: true},
							          {data: "equipmentCode",width: "150px",orderable: true},
							          {data: "equipmentName",width: "150px",orderable: true},
							          {data: "planBeginDate",width: "200px",orderable: true}, 
							          {data: "planEndDate",width: "200px",orderable: true}, 
							          {data: "applyUserName",width: "100px",orderable: true}, 
							          {data: "processStatusName",name:"processStatus",width: "60px",orderable: true}]
						}
					}).render();
					$('#btnSearchForEquipAbnormal').on('click',function(){
						conditions=[];
						if($("#equipAbnormalBill").val()){
	    					conditions.push({
	        					field: 'C_EQUIP_ABNORMAL_BILL',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"equipAbnormalBill",
	        					value:$("#equipAbnormalBill").val()
	        				});
						}
						if(unitNameTreeListCombobox.getValue()!=null&&unitNameTreeListCombobox.getValue()+""!=""){
							conditions.push({
	        					field: 'T.C_UNIT_ID',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:"unitId",
	        					value:unitNameTreeListCombobox.getValue()
	        				});
						}
						if($("#equipmentCode").val()){
	    					conditions.push({
	        					field: 'E.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"equipmentId",
	        					value:$("#equipmentCode").val()
	        				});
						}
						if($("#applyUserId").val()){
	    					conditions.push({
	        					field: 'C_APPLY_USER_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					name:"applyUserId",
	        					value:$("#applyUserId").val()
	        				});
						 }
						if($("#planBeginDateBegin").val()){
	    					conditions.push({
	        					field: 'C_PLAN_BEGIN_DATE',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					name:"planBeginDateBeginDiv",
	        					value:$("#planBeginDateBegin").val()
	        				});
						 }
						if($("#planBeginDateEnd").val()){
	    					conditions.push({
	        					field: 'C_PLAN_BEGIN_DATE',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					name:"planBeginDateEndDiv",
	        					value:$("#planBeginDateEnd").val()
	        				});
						 }
						if($("#planEndDateBegin").val()){
	    					conditions.push({
	        					field: 'C_PLAN_END_DATE',
	        					fieldType:'DATE',
	        					matchType:'LE',
	        					name:"planEndDateBeginDiv",
	        					value:$("#planEndDateBegin").val()
	        				});
						 }
						if($("#planEndDateEnd").val()){
	    					conditions.push({
	        					field: 'C_PLAN_END_DATE',
	        					fieldType:'DATE',
	        					matchType:'GE',
	        					name:"planEndDateEndDiv",
	        					value:$("#planEndDateEnd").val()
	        				});
						 }
						if($("#processStatus").val()){
	    					conditions.push({
	        					field: 'C_PROCESS_STATUS',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					name:"processStatus",
	        					value:$("#processStatus").val()
	        				});
						 }
						if($("#abnormalDesc").val()){
	    					conditions.push({
	        					field: 'C_ABNORMAL_DESC',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					name:"abnormalDesc",
	        					value:$("#abnormalDesc").val()
	        				});
						 }
						equipAbnormalDatatables._datatables.search(JSON.stringify(conditions)).draw();
					}); 
					$('#btnResetEquipAbnormal').on('click',function(){
						$("#equipAbnormalBill").val("");
						$("#equipmentCode").val("");
						unitNameTreeListCombobox.setValue(null);
						$("#applyUserId").val("");
						$("#applyUserId").trigger("chosen:updated");
						$("#planBeginDateBegin").val("");
						$("#planBeginDateEnd").val("");
						$("#planEndDateBegin").val("")
						$("#planEndDateEnd").val("");
						$("#processStatus").val("");
						$("#processStatus").trigger("chosen:updated");
						$("#abnormalDesc").val("");
					});
				});
			});
        </script>
    </body>
</html>