<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>
	<div class="col-lg-12 col-md-12 col-sm-12 search-content">
		<div class="form-inline text-right" role="form">
			<div class="clearfix">
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label class="" for="form-field-1">单位名称：</label>
					<div id="searchunitIdDiv"
						class="input-width text-left padding-zero"></div>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label>负责人：</label>
					<div class="padding-zero input-width  text-left">

						<select id="selplanPersonIdDiv" name="planPersonId"
							data-placeholder="请选择计划指定人"></select>
					</div>
				</div>
				<div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
					<label class="" for="startTimeDiv">工作时间：</label>
					<div class="form-group date-input padding-zero text-left">
						<div id="startTimeDiv" style="border: none; padding: 0px;"></div>
					</div>
					<label style="width: 2.6%; text-align: center">~</label>
					<div class="form-group date-input padding-zero text-left">
						<div id="endTimeDiv" style="border: none; padding: 0px;"></div>
					</div>
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label class="" for="searchequipIdDiv">设备名称：</label>
					<div id="searchequipNameDiv" class="input-width text-left padding-zero">
					</div>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label class="" for="searchdepict">工作类型：</label> 
					<input id="workType" class="input-width text-left" placeholder="请输入工作类型" type="text">
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label class="min-label" for="searchprocessStatus">状态：</label>
					<div class="input-width padding-zero  text-left">
						<select id="checkStateDiv" name="checkState"></select>
					</div>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<label class="min-label" for="searchprocessStatus">班次：</label>
					<div class="input-width padding-zero  text-left">
						<select id="teamDiv" name="team"></select>
					</div>
				</div>
			</div>
			<div class="clearfix">
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
				</div>
				<div class="form-group col-lg-2 col-md-2 col-sm-12 col-xs-12"
					style="float: right;">
					<button id="btnSearchForWorkRecord" class="btn btn-xs btn-primary"
						style="overflow: hidden; width: 48%; max-width: 54px">
						<i class="glyphicon glyphicon-search"></i> 查询
					</button>
					<button id="btnResetForWorkRecord" class="btn btn-xs btn-primary"
						style="overflow: hidden; width: 48%; max-width: 54px">
						<i class="glyphicon glyphicon-repeat"></i> 重置
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<!-- div.dataTables_borderWrap -->
			<div class="widget-main no-padding">
				<h5 class="table-title header smaller blue"
					style="margin-bottom: 0px !important;">定期工作记录</h5>
				<table id="workRecord-table"
					class="table table-striped table-bordered table-hover"
					style="width: 100%;">
					<thead>
						<tr>
							<th style="display: none;">主键</th>
							<th>设备名称</th>
							<th>工作类型</th>
							<th>工作时间</th>
							<th>班次</th>
							<th>负责人</th>
							<th>单位名称</th>
							<th>状态</th>
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
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','selectbox'], function(A){
					var conditions=[];
					var equipIdJson = JSON.parse('${equipIdJson}');
					var equipIdArray = new Array();
					var equipIdString = "";
					for(i in equipIdJson){
						equipIdArray.push(equipIdJson[i].equipId);
					}
					if(equipIdArray.length>0){
						equipIdString = equipIdArray.join(",");
					}
					var searchunitId = new A.combotree({
						render: "#searchunitIdDiv",
						name: "searchunitId",
						//返回数据待后台返回TODO
						datasource: ${workRecordTreeList},
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
							}
						}
					}).render();	
					var searchequipName=new A.selectbox({
						id: 'searchequipName',
						name:'searchequipName',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#searchequipNameDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								$("#searchequipName").val(data[0].name);
							};
							
						}
					}).render();
					var planPersonCombobox = new A.combobox({
						render: "#selplanPersonIdDiv",
						datasource:${workRecordCombobox},
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10,
							"width":"100%"										
						}
					}).render();
					//日期组件
					var startTimeDatePicker = new A.my97datepicker({
						id: "startTimeId",
						name:"startTime",
						render:"#startTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//日期组件
					var endTimeDatePicker = new A.my97datepicker({
						id: "endTimeId",
						name:"endTime",
						render:"#endTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'startTimeId\\')}",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					//班次
					var teamCombobox = new A.combobox({
						render: "#teamDiv",
						datasource:${orgAppCombobox},
						allowBlank: true,
						width:"210px",
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//审批状态
					var teamCombobox = new A.combobox({
						render: "#checkStateDiv",
						datasource:${processStatusCombobox},
						allowBlank: true,
						width:"210px",
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var workRecordDatatables = new A.datatables({
						render: '#workRecord-table',
						options: {
					        "ajax": {
					            "url": format_url("/equiptree/workRecordListWithEquip"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
		            					field: 'C_DEVICE_ID',
		            					fieldType:'INT',
		            					matchType:'IN',
		            					value:equipIdString
		            				});
// 					            	conditions.push({
// 		            					field: 'C_STATUS',
// 		            					fieldType:'INT',
// 		            					matchType:'IN',
// 		            					value:1
// 		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:true}, 
							          {data: "equipName",width: "10%",orderable: true}, 
							          {data: "workType",width: "10%",orderable: true}, 
							          {data: "workTime",width: "15%",orderable: true}, 
							          {data: "teamName",width: "10%",orderable: true}, 
							          {name:"dutyPersonId",data: "dutyPersonName",width: "10%",orderable: true}, 
							          {name:"unitId",data: "unitName",width: "10%",orderable: true}, 						        
							          {data: "processStatusName",name:"checkState",width: "14%",orderable: true}],
// 						
						}
					}).render();
					$('#btnSearchForWorkRecord').on('click',function(){
						conditions=[];
						if($('#startTimeId').val()){
    					conditions.push({
    						field: 'a.C_WORK_TIME',
    						fieldType:'DATE',
    						matchType:'GE',
    						value:$("#startTimeId").val()
    					});
						}
						if($('#endTimeId').val()){
    					conditions.push({
    						field: 'a.C_WORK_TIME',
    						fieldType:'DATE',
    						matchType:'LE',
    						value:$('#endTimeId').val()
    					});
						}
						if($('#selplanPersonIdDiv').val()){												
    					conditions.push({
    						field: 'a.C_DUTY_PERSON_ID',
    						fieldType:'INT',
    						matchType:'EQ',
    						value:$('#selplanPersonIdDiv').val()
    					});
						}
						if(searchunitId.getValue()!=undefined&&searchunitId.getValue()+""!=""){
	    					conditions.push({
	        					field: 'a.C_UNIT_ID',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					value:searchunitId.getValue()
	        				});
						}
						if($('#searchequipName').val()){
	    					conditions.push({
	        					field: 'f.C_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchequipName').val()
	        				});
						}
						if($('#workType').val()){
	    					conditions.push({
	        					field: 'a.C_WORK_TYPE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#workType').val()
	        				});
						}
						if($('#planId').val()){
	    					conditions.push({
	        					field: 'e.C_CODE',
	        					fieldType:'STRING',
	        					matchType:'LIKE',
	        					value:$('#planId').val()
	        				});
						}
						if($('#checkStateDiv').val()){
	    					conditions.push({
	        					field: 'a.C_CHECK_STATE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#checkStateDiv').val()
	        				});
						}
						if($('#teamDiv').val()){
	    					conditions.push({
	        					field: 'a.C_TEAM',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#teamDiv').val()
	        				});
						}
						console.log(conditions)
						workRecordDatatables.draw();
					});
					
					$('#btnResetForWorkRecord').on('click',function(){
						searchunitId.setValue(undefined);
						$("#selplanPersonIdDiv").val("");
						$("#selplanPersonIdDiv").trigger("chosen:updated");
						$("#startTimeId").val("");
						$("#endTimeId").val("");
						$("#planId").val("");
						$("#workType").val("");
						$("#searchequipName").val("");
						$("#checkStateDiv").val("");
						$("#checkStateDiv").trigger("chosen:updated");
						$("#teamDiv").val("");
						$("#teamDiv").trigger("chosen:updated");
						processUserUnitRels = [];
					});
				});
			});
        </script>
</body>
</html>