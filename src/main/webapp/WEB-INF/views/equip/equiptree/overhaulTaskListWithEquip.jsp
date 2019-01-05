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
			<div class="clearfix" style="width: 95%">
				<input id="equitIdVal" type="hidden"></input>
				<input id="unitIdVal" type="hidden" value="${unitId}"></input>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
					<label class="" for="searchequipNameDivForOverhaulTask">设备名称：</label>
					<div id="searchequipNameDivForOverhaulTask" class="input-width text-left padding-zero">
					</div>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
					<label>完成状态：</label>
					<div class="padding-zero input-width  text-left">
						<select id="finishStatus" name="finishStatus"></select>
					</div>
				</div>
				<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
					<label class="" for="workTask">工作任务：</label>
					<div class="form-group inputWidth text-left">
						<input id="workTask" name="workTask" style="width: 200px;"></input>
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
				<table id="overhaulWorkTask-table"
					class="table table-striped table-bordered table-hover"
					style="width: 100%;">
					<thead>
						<tr>
									<th style="display:none;">主键</th>
<!-- 									<th class="center sorting_disabled" style="width:50px;"> -->
<!--         								<label class="pos-rel"> -->
<!--         									<input type="checkbox" class="ace" /> -->
<!--         									<span class="lbl"></span> -->
<!--         								</label> -->
<!--         							</th> -->
<!--         							<th>序号</th> -->
	                                <th>单位名称</th>
	                                <th>设备名称</th>
	                                <th>工作任务</th>
	                                <th>检修人员</th>
	                                <th>填报人</th>
	                                <th>日期</th>
	                                <!-- <th>请假人员</th> -->
                                    <th>外协单位</th>
                                    <th>完成状态</th>
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
					//设备名称
					var searchequipName=new A.selectbox({
						id: 'searchequipName',
						name:'searchequipName',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#searchequipNameDivForOverhaulTask',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								var equitName="";
								var equitIds = "";
								for (var i=0; i<data.length;i++) {
									equitName += data[i].name;
									equitIds += data[i].id;
									if (i != data.length-1) {
										equitName += ",";
										equitIds += ",";
									}
								};
								$("#searchequipName").val(equitName);
								$("#equitIdVal").val(equitIds);
							};
						}
					}).render();
					//完成状态
					var finishStatus = new A.combobox({
						render : "#finishStatus",
						datasource : ${overhaulArrangeFinishStatus},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var overhaulWorkTaskDatatables = new A.datatables({
						render: '#overhaulWorkTask-table',
						options: {
							 "ajax": {
						            "url": format_url("/overhaulWorkTask/searchData"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "data": function (d) {
						            	//设备关联缺陷ID
						            	if ($('#equitIdVal').val()  != "") {
											conditions.push({
					        					field: 'O.C_EQUIP_ID',
					        					fieldType:'INT',
					        					matchType:'IN',
					        				    value: $('#equitIdVal').val()
					        				});
						            	} else {
											conditions.push({
					        					field: 'O.C_EQUIP_ID',
					        					fieldType:'INT',
					        					matchType:'IN',
					        				    value: equipIdString
					        				});
						            	}
						            	if ($('#unitIdVal').val()  != "0" && $('#unitIdVal').val()  != "-1") {
							            	conditions.push({
					        					field: 'r.C_UNIT_ID',
					        					fieldType:'STRING',
					        					matchType:'EQ',
					        				    value: $('#unitIdVal').val()
					        				});
						            		
						            	}
						            	d.conditions = conditions;
						                return JSON.stringify(d);
						              }
						        },
						        multiple : true,
						        checked:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							columns: [{data:"logid", visible:false,orderable:true},
// 								          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
// 							                   var startIndex = meta.settings._iDisplayStart;  
// 							                   row.start=startIndex + meta.row;
// 							                   return startIndex + meta.row + 1;  
// 							               	} },
										  {data: "logunitName",width: "10%",orderable: false}, 
										  {data: "equipName",width: "10%",orderable: true}, 
										  {data: "workTask",width: "10%",orderable: true}, 
										  {data: "logdutyUserName",width: "10%",orderable: true}, 
										  {data: "logsubmitUserName",width: "10%",orderable: true},  
										  {data: "loglogDateString",name:"loglogDate",width: "12%",orderable: true}, 
										 /*  {data: "logaskPersonName",width: "15%",orderable: true}, */
										  {data: "logoutUnitName",width: "15%",orderable: true},
										  {data: "logfinishStatusString",name:"logfinishStatus",width: "5%",orderable: true}],
							optWidth:50
						}
					}).render();
					$('#btnSearchForWorkRecord').on('click',function(){
						conditions=[];
					/* 	if($('#searchequipName').val()){
	    					conditions.push({
	        					field: 'O.C_EQUIP_NAME',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					type:1,
	        					value:$('#searchequipName').val()
	        				});
						} */
// 						if ($('#equitIdVal').val()  != "") {
// 							conditions.push({
// 	        					field: 'O.C_EQUIP_ID',
// 	        					fieldType:'INT',
// 	        					matchType:'IN',
// 	        				    value: $('#equitIdVal').val()
// 	        				});
// 		            	}
						if($('#finishStatus').val()){
	    					conditions.push({
	        					field: 'O.C_FINISH_STATUS',
	        					fieldType:'INT',
	        					matchType:'EQ',
	        					type:1,
	        					value:$('#finishStatus').val()
	        				});
						}
						if($('#workTask').val()){
	    					conditions.push({
	        					field: 'O.C_WORK_TASK',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					type:1,
	        					value:$('#workTask').val()
	        				});
						}
						overhaulWorkTaskDatatables.draw();
					});
					
					$('#btnResetForWorkRecord').on('click',function(){
						$("#searchequipName").val("");
						$("#equitIdVal").val("");
						$("#finishStatus").val("");
						$("#finishStatus").trigger("chosen:updated");
						$("#workTask").val("");
						processUserUnitRels = [];
					});
				});
			});
        </script>
</body>
</html>