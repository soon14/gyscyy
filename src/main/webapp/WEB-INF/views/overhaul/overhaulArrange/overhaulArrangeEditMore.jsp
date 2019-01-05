<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li class="active">检修管理</li>
				<li class="active">设备检修记录</li>
				<li class="active">配置设备检修纪录</li>
			</ul>
		</div>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
			<div style="float:right;margin-right:50px;">
				<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
        			<i class="fa fa-reply"></i>
        			返回
        		</button>
			</div>
			<h5 class='table-title header smaller blue' style="margin-bottom:20px!important;">基础信息</h5>
			<div class="widget-main no-padding">
				<form class="form-horizontal" role="form"  style="margin-right:200px;margin-top:20px;" id="overhaulLogDetailForm">
				<input id="overhaulRecordId" name="overhaulRecordId" value="${overhaulRecordId}" type="hidden"/>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						检修单位
                    </label>
					<div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="unitId" name="unitId"></select>
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检修负责人
                    </label>
					<div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="dutyPerson" name="dutyPerson"></select>
				    </div>
                </div>
			    <div class="form-group">
			    	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>开始时间
                    </label>
					<div class="col-md-4">
                   	   <div id="logDateDiv"></div>
                    </div>
					  <label class="col-md-2 control-label no-padding-right">
					  结束时间
                    </label>
					<div class="col-md-4">
                    	 <div id="endDateDiv"></div>
                    </div>
                </div>
                 <div class="form-group">
				   <label class="col-md-2 control-label no-padding-right">
					  损失电量(万kWh)
                    </label>
                    <div class="col-md-4">
                        <input id="afterMeet" class="col-md-12" style="resize:none;" name="afterMeet" type="number"  placeholder="请输入损失电量" maxlength="128" value="${overhaulLogDetailEntities.afterMeet}">
                    </div>
                </div>
			    <div class="form-group">
             		<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作班成员
                    </label>
                    <div class="col-md-10">
	                    <textarea id="teamMemberNames" class="col-md-12" style="height:150px; resize:none;" name="teamMember"  type="text" placeholder="请输入工作班成员" maxlength="128" value="">${overhaulLogDetailEntities.teamMember}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作完成情况
                    </label>
					<div class="col-md-10">
	                    <textarea id="workFinishInfo" class="col-md-12" style="height:150px; resize:none;" name="workFinishInfo"  type="text" placeholder="请输入工作完成情况" maxlength="128" value="">${overhaulLogDetailEntities.workFinishInfo}</textarea>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>遗留问题和应急措施
                    </label>
					<div class="col-md-10">
	                    <textarea id="problemMeasures" class="col-md-12" style="height:150px; resize:none;" name="problemMeasures"  type="text" placeholder="请输入遗留问题和应急措施" maxlength="128" value="">${overhaulLogDetailEntities.problemMeasures}</textarea>
                    </div>
                </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						附件
                    </label>
					<div class="col-md-10" id="divfile">
                    </div>
                </div>
        		</form>   
        	</div>
        
        
	    <div class="col-xs-12" style="margin-top:0px;">	
		<div class="widget-main no-padding">
			<form id="overhaulWorkTask-table-form" class="form-horizontal">
			        <h5 class='table-title header smaller blue' style='margin-bottom:0px;'>工作任务</h5>			
					<table id="overhaulWorkTask-table" class="table table-striped table-bordered table-hover" style="width:100%;">
						<thead>
							<tr>
								<th style="display:none;">主键</th>
								<th>序号</th>
								<th><span style="color:red;">*</span>设备名称</th>
								<th><span style="color:red;">*</span>工作任务</th>
	                       		<th><span style="color:red;">*</span>完成状态</th>
	                          	<th>操作</th>
							</tr>
						</thead>
					</table>
			</form>
		</div>
		</div>
        	
	    <div class="col-xs-12" style="margin-top:0px;">	
		<div class="widget-main no-padding">
			<form id="overhaulSoft-table1-form" class="form-horizontal">
		        <h5 class='table-title header smaller blue' style='margin-bottom:0px;' >安全交底</h5>			
				<table id="overhaulSoft-table1" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;">*</span>交代当日任务中作业环境、存在风险</th>
                       		<th><span style="color:red;">*</span>措施检查</th>
                          	<th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		
	    <div class="col-xs-12" style="margin-top:30px;">	
		<div class="widget-main no-padding">
		 <form id="overhaulSoft-table2-form" class="form-horizontal"  onsubmit="return false;">
		 		<h5 class='table-title header smaller blue' style='margin-bottom:0px;' ></h5>
				<table id="overhaulSoft-table2" class="table table-striped table-bordered table-hover" style="width:100%;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
							<th><span style="color:red;">*</span>交代预防当日任务风险的安全作业事项</th>
                       		<th><span style="color:red;">*</span>措施检查</th>
                          	<th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		
		
	    <div class="col-xs-12" style="margin-top:0px;">	
		<div class="widget-main no-padding">
		<form id="overhaulSpareconsume-table-form" class="form-horizontal" >
	        <h5 class='table-title header smaller blue' style='margin-bottom:0px;'>备件消耗</h5>			
			<table id="overhaulSpareconsume-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th>序号</th>
                            <th>物资编码</th>
                            <th>物资名称</th>
                            <th>规格型号</th>
                            <th>生产厂家</th>
                            <th><span style="color:red;">*</span>数量</th>
                            <th>单位</th>
                            <th>设备</th>
                            <th>操作</th>
						</tr>
					</thead>
				</table>
			</form>
		</div>
		</div>
		
		<div class="col-xs-12" style="margin-right:100px;margin-top:0px;">
   			<button id="saveBtn" class="btn btn-xs btn-success pull-right">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				保存
   			</button>
   		</div>
</div>
	<script type="text/javascript">
		    var type = "${type}";
		    var overhaulRecordId = ${overhaulRecordId};
		    var overhaulOrganizationId = ${overhaulOrganizationId};
		    var overhaulAddMore = true;
		    var overhaulWorkTask = [];
		    var overhaulspareconsume = [];
		    var localequipInfoArray;
			var fileId = ${overhaulLogDetailEntities.fileId};
			var overhaulSpareconsumeDatatables;
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone','selectbox'], function(A){
					var conditions=[];
					var equipids=[];
					var equipArr = ${equipCombox};
                    for(var i=0; i<equipArr.length;i++){
                	   equipids.push(equipArr[i].code);
                   }
                  
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId : fileId,
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true//显示删除按钮
					}).render();
					//开始时间
					var dateDatePicker = new A.my97datepicker({
						id: "logDate",
						name:"logDate",
						render:"#logDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					dateDatePicker.setValue('${overhaulLogDetailEntities.logDateString}');
					//开始时间
					var endDatePicker = new A.my97datepicker({
						id: "endDate",
						name:"endDate",
						render:"#endDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					endDatePicker.setValue('${overhaulLogDetailEntities.endDateString}');
					//检修负责人
					var dutyUsersId = new A.combobox({
						render : "#dutyPerson",
						datasource : ${dutyPersons},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					dutyUsersId.setValue(${overhaulLogDetailEntities.dutyPerson});
					//外协单位
					var unitIdCombobox = new A.combobox({
						render: "#unitId",
						//返回数据待后台返回TODO
						datasource:${unitList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					unitIdCombobox.setValue(${overhaulLogDetailEntities.unitId});
					//保存
					$("#saveBtn").on("click", function(){
						$("#overhaulLogDetailForm").submit();
    				});	
					//返回
					$('#btnBack').on('click',function(){
						 A.loadPage({
								render : '#page-container',
								url : format_url("/overhaulRecord/getDataList/"+ orgnaizationId)
						 });
					});
// 工作任务 table展示---------------------------------start-------------------------------------------------------------------------------- 
					
					var overhaulWorkTaskDatatables = new A.datatables({
						render: '#overhaulWorkTask-table',
						options: {
							 "ajax": {
						            "url": format_url("/overhaulWorkTask/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "data": function (d) {	
						            	conditions.push({
					     					field: 'O.C_OVERHAUL_RECORD_ID',
					     					fieldType:'INT',
					     					matchType:'EQ',
					     					value:overhaulRecordId
					     				});
										conditions.push({
					     					field: 'O.C_STATUS',
					     					fieldType:'STRING',
					     					matchType:'EQ',
					     					value:"1"
					     				});
						            	d.conditions = conditions;
						                return JSON.stringify(d);
						              }
						        },
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(result){
										this.api().column(1).nodes().each(function(cell, i) {
										cell.innerHTML =  i + 1;
									});
										executeEquipDataIds(result);
								},
								'fnInitComplete':function(result){
									executeEquipDataIds(result);
								},
							columns: [ {data:"id", visible:false,orderable:false}, 
							           {data: null,width: "5%",orderable: false},
							           {data: "equipName",width: "10%",orderable: false,
			                                  render : function(data, type, row, meta) {
			                                       return "<input type='hidden' value = '"+row.equipId+"' name='equipId'><input type='text' class='col-md-12' name='equipName' value='"+data+"' />";  
			                                                       }  
			                           },
							           {data: "workTask",name:"workTask",width: "73%",orderable: false,editType:"input"},
							           {data: 'finishStatus',name:"finishStatus",width: "10%",orderable: false,editType:"combobox",cfg:{ datasource : ${overhaulArrangeFinishStatus}, allowBlank: false, }}
							 ],
							optWidth:50,
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var equipDialog = new A.dialog({
            									equipInfoArray:true,
            									title:"设备台账",
            									width:'1100',//弹出窗体的宽度  可以不写这行
												hight:'780',//弹出窗体的高度  可以不写这行
            									url:format_url("/equipLedger/selectEquipLedger"),
            									closed: function(equipInfoArray){
            										var params = {};
            										params.equipInfo = equipInfoArray;
            										
            									for(var i=0;i<equipInfoArray.length;i++){
            										var equipObj = {};
            										if(equipids.length>0){
            											 if(!contains(equipids,equipInfoArray[i].equipid+"")){
            												 equipids.push(equipInfoArray[i].equipid+"");
            												
                											equipObj.code = equipInfoArray[i].equipid+"";
                    										equipObj.name = equipInfoArray[i].equipName;
                    										equipArr.push(equipObj); 
            											 }
            										}else{
            											equipids.push(equipInfoArray[i].equipid+"");
            											equipObj.code = equipInfoArray[i].equipid+"";
                										equipObj.name = equipInfoArray[i].equipName;
                										equipArr.push(equipObj); 
            										}
            										
            										}
            									
            										$.ajax({
														url: format_url("/overhaulWorkTask/addEquipInfo/"+overhaulRecordId),
														contentType: "application/json",
														data : JSON.stringify(params),
														dataType: 'JSON',
														type: 'POST',
														success: function(result){
															overhaulWorkTaskDatatables.draw(false);
															overhaulSpareconsumeDatatables._options.columns[8].cfg.datasource=equipArr;
															overhaulSpareconsumeDatatables.draw(false);
														},
														error:function(v,n){
															alert('操作失败');
														}
													})
            									}
            							}).render();
            						}
        						}
        					}],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
    										
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url: format_url("/overhaulWorkTask/delete/"+id),
													contentType: "application/json",
													dataType: 'JSON',
													type: 'POST',
													success: function(result){
														alert('删除成功');
														for(var i=0;i<equipArr.length;i++){
															//取到删除的物资明细id
								                            var materialId = nData.equipId;
															var name=nData.equipName;
															//循环数组中的每一个物资明细id
								                            var materialResultId = equipArr[i].code;
															//如果两个id相等，在数组中去掉这个id
								                            if(materialId == materialResultId){
								                            	equipArr.splice(i,1);
								                            }
								                         }
														overhaulWorkTaskDatatables.deleteSelectRow(nRow);
														overhaulWorkTaskDatatables.draw(false);
														overhaulSpareconsumeDatatables._options.columns[8].cfg.datasource=equipArr;
														overhaulSpareconsumeDatatables.draw(false);
													},
													error:function(v,n){
														alert('删除失败');
													}
												})
											});		
										}
									}
								}
							]
						}
					}).render();
//工作任务table展示 -----------------------------end---------------------------------------------------------------------
					//安全交底 table展示---------------------------------start-------------------------------------------------------------------------------- 
					var overhaulSafetDatatables1 = new A.datatables({
						render: '#overhaulSoft-table1',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
							});
						},
							columns: [ {data:"id", visible:false,orderable:false}, 
							           {data: null,width: "5%",orderable: false},
							           {data: "danger",width: "83%",orderable: false, editType:"input"},
							           {data: 'wayCheck',name:"wayCheck",width: "10%",orderable: false, editType:"combobox",cfg:{ datasource : ${checkState}, allowBlank: false, } }
							 ],
							optWidth:50,
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							overhaulSafetDatatables1.addRow({"id":null, "danger":"", "wayCheck":""});
            						}
        						}
        					}],
							btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
											A.confirm('您确认删除么？',function(){
													alert('删除成功');
													overhaulSafetDatatables1.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					
					function overhaulSafetinitDataTable1(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhaulRecordId = overhaulRecordId;
						params.logType = 'jobRisk';
						$.ajax({
							url: format_url("/overhaulSafe/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulSafetDatatables1.addRows(result.data);
							}
							
						})
					};
					overhaulSafetinitDataTable1();
					var overhaulSafetDatatables2 = new A.datatables({
						render: '#overhaulSoft-table2',
						options: {
							    serverSide: false,
						        multiple : true,
						        checked:false,
								bInfo:false,
								paging:false,
								ordering:true,
								optWidth: 120,
								order:[["1",'asc']],
							    "fnDrawCallback"    : function(){
								this.api().column(1).nodes().each(function(cell, i) {
								cell.innerHTML =  i + 1;
								});
						},
						columns: [ {data:"id", visible:false,orderable:false}, 
						           {data: null,width: "5%",orderable: false}, 
						           {data: "danger",width: "83%",orderable: false, editType:"input"}, 
								   {data: 'wayCheck',name:"wayCheck",width: "10%",orderable: false, editType:"combobox" ,cfg:{ datasource : ${checkState}, allowBlank: false, } }
						 ],
						 
						optWidth:50,
						toolbars: [{
    						label:"新增",
    						icon:"glyphicon glyphicon-plus",
    						className:"btn-success",
    						events:{
        						click:function(event){
        							overhaulSafetDatatables2.addRow({"id":null, "danger":"", "wayCheck":""});
        						}
    						}
    					}],
						btns:[
								{
									id:"delete",
									label:"删除",
									icon: "fa fa-trash-o bigger-130",
									className: "red del",
									events:{
										"click": function(event, nRow, nData){
											var id = nData.id;
											A.confirm('您确认删除么？',function(){
												overhaulSafetDatatables2.deleteSelectRow(nRow);
											});		
										}
									}
								}
							]
						}
					}).render();
					
					function overhaulSafetinitDataTable2(){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhaulRecordId = overhaulRecordId;
						params.logType = 'safItem';
						$.ajax({
							url: format_url("/overhaulSafe/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								overhaulSafetDatatables2.addRows(result.data);
							}
						})
					};
					overhaulSafetinitDataTable2();
//安全交底table展示 -----------------------------end---------------------------------------------------------------------
					//物资消耗 table展示 -----------------------------start---------------------------------------------------------------------
					var conditions=[];
					var materialResult = [];
				
					overhaulSpareconsumeDatatables = new A.datatables({
						render: '#overhaulSpareconsume-table',
						options: {
							"ajax": {
					            "url": format_url("/overhaulSpareconsume/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'O.C_OVERHAUL_RECORD_ID',
				     					fieldType:'INT',
				     					matchType:'EQ',
				     					value:overhaulRecordId
				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
					        checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							optWidth: 120,
							order:[["1",'asc']],
						    "fnDrawCallback"    : function(result){
								this.api().column(1).nodes().each(function(cell, i) {
									cell.innerHTML =  i + 1;
								});
									executeDataIds(result);
							},
							columns:[
							         {data:"id", visible:false,orderable:false}, 
							         {data: null,width: "5%",orderable: true},
							         {data: "code",width: "16%",orderable: true}, 
							         {data: "name",width: "16%",orderable: true}, 
							         {data: "model",width: "20%",orderable: true}, 
							         {data: "manufacturer", width: "15%",orderable: true}, 
							         {data: "number",width: "10%",orderable: true,editType:"input",editWidth:"95%"}, 
							         {data: "unit",name:"unit",width: "5%",orderable: true},
							         {data: "equipId",name:"equipName",width: "11%",orderable: true,editType:"combobox",
							        	 cfg:{datasource : equipArr, allowBlank: false, change:true} 
							         }
							],
							optWidth:50,
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							
            							var dataIdArray = overhaulSpareconsumeDatatables.getDatasId();
										
											 selectMaterialDialog = new A.dialog({
            									materialInfoArray:true,
            									title:"物资信息选择",
            									width:'1100',//弹出窗体的宽度  可以不写这行
												hight:'880',//弹出窗体的高度  可以不写这行
            									url:format_url("/materialCategory/selectOutStockMaterialCategory"),
            									closed: function(){
            										var sds = selectMaterialDialog.materialInfoArray;
            										var params = {};
            										params.materialInfo = materialInfoArray;
            										$.ajax({
														url: format_url("/overhaulSpareconsume/addMaterialInfo/"+overhaulRecordId),
														contentType: "application/json",
														data : JSON.stringify(params),
														dataType: 'JSON',
														type: 'POST',
														success: function(result){
															overhaulSpareconsumeDatatables._options.columns[8].cfg.datasource=equipArr;
															overhaulSpareconsumeDatatables.draw(false);
														},
														error:function(v,n){
															alert('操作失败');
														}
													})
            									}
            							}).render();
								
            						}
        						}
        					}],
							btns: [{
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{"click": function(event, nRow, nData){
											var id = nData.id;
											A.confirm('您确认删除么？',function(){
												$.ajax({
													url: format_url("/overhaulSpareconsume/delete/"+id),
													contentType: "application/json",
													dataType: 'JSON',
													type: 'POST',
													success: function(result){
														alert('删除成功');
														overhaulSpareconsumeDatatables.deleteSelectRow(nRow);
// 														overhaulSpareconsumeDatatables.draw(false);
													},
													error:function(v,n){
														alert('删除失败');
													}
												})
											});		
										}

								}
						}]
						}
					}).render();
					function overhaulSpareconsumeinitDataTable(flag){
						var params = {};
						params.length = 10;
						params.start = 0;
						params.draw = 0;
						params.conditions = [];
						params.overhaulRecordId = overhaulRecordId;
						$.ajax({
							url: format_url("/overhaulSpareconsume/search"),
							contentType: "application/json",
							dataType: 'JSON',
							type: 'POST',
							data : JSON.stringify(params),
							success: function(result){
								if(flag){
									overhaulSpareconsumeDatatables.addRows(result.data);
								}
							}
							
						})
					};
					overhaulSpareconsumeinitDataTable(true);
					
		//物资消耗 table展示 -----------------------------end----------------------------------------------------------
					$('#overhaulLogDetailForm').validate({
						debug:true,
						rules:  {
							unitName:{required:true},
							dutyUserId:{required:true},
							logDate:{required:true},
							teamMember:{required:true},
							workFinishInfo:{required:true},
							problemMeasures:{required:true}
						},
						submitHandler: function (form) {
							if(!validOverhaulWorkTaskform().form()){
								return;
							}
							if(!validOverhaulspareform().form()){
								return;
							}
							//添加按钮
							var obj = $("#overhaulLogDetailForm").serializeObject();
							var projectobj1 = $("#overhaulSoft-table1-form").serializeObject();
							var projectobj2 = $("#overhaulSoft-table2-form").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							obj.saftList1 = gettableData('overhaulSoft-table1');
							obj.saftList2 = gettableData('overhaulSoft-table2');
							
							obj.logDate = obj.logDate+" 00:00:00";
							obj.endDate = obj.endDate+" 00:00:00";
							overhaulWorkTask = [];
							var workTaskList = gettableData("overhaulWorkTask-table");
							for(var i=0;i<workTaskList.length;i++){
								var workTaskObj = {};
								workTaskObj.id = localsequipInfoArray[i];
								workTaskObj.equipName = workTaskList[i].equipName;
								workTaskObj.workTask = workTaskList[i].workTask;
								workTaskObj.finishStatus = workTaskList[i].finishStatus;
								
								overhaulWorkTask.push(workTaskObj);
							}
							obj.workTaskList = overhaulWorkTask;
							overhaulspareconsume = [];
						
							var spareList =gettableData("overhaulSpareconsume-table");  // 
							var spareListto=$("#overhaulSpareconsume-table-form").serializeObject();
						
							for(var i=0;i<spareList.length;i++){
								var spareObj = {};
								spareObj.id = localequipInfoArray[i];
								spareObj.code = spareList[i].code;
								spareObj.name = spareList[i].name;
								spareObj.model = spareList[i].model;
								spareObj.manufacturer = spareList[i].manufacturer;
								spareObj.number = spareList[i].number;
								spareObj.unitName = spareList[i].unit;
								spareObj.equipId = spareList[i].equipId;
								overhaulspareconsume.push(spareObj);
								
							}
							obj.spareconsumeList = overhaulspareconsume;
							
							var flag1  =false;
							var flag2  =false;
							
							
							for(var key in projectobj1){
								flag1 =true;
								break;
							}
							for(var key in projectobj2){
								flag2 =true;
								break;
							}
							
							if(!flag1){
								alert("安全交底中的作业环境、存在风险不能为空");
								return;
							}
							if(!flag2){
								alert("安全交底中的安全作业事项不能为空");
								return;
							}
							
							
							$.ajax({
								url : format_url("/overhaulLogDetail/addMore"),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									 if(result.result == "success"){
										 alert('保存成功');
										 A.loadPage({
												render : '#page-container',
												url : format_url("/overhaulArrange/getInfo/"+ overhaulOrganizationId+"/"+overhaulRecordId)
										 });
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//工作任务验证
					function validOverhaulWorkTaskform(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#overhaulWorkTask-table-form').validate({
								debug:true,
								rules:  {
									workTask:{required:true}
								}
							});
					}
					
					function validOverhaulspareform(){
						 /*关键在此增加了一个return，返回的是一个validate对象，这个对象有一个form方法，返回的是是否通过验证*/
						 return $('#overhaulSpareconsume-table-form').validate({
								debug:true,
								rules:  {
									number:{required:true}
								}
							});
					}
				});
			});
			//从列表转化成键值对数组
			function gettableData(tableid){
				var projectobj = $("#"+tableid+"-form").serializeObject();
				var tablelength = $("#"+tableid+"").find("tr").length;
				if(tablelength==1){
					return null;
				}
				var resultList= [];
				for(var key in projectobj){
					var keylist = [];
					if(tablelength == 2){
						keylist.push(projectobj[key]);
					}else{
						keylist=projectobj[key];
					}
					if(keylist){
						for(var i = 0; i<keylist.length; i ++){
							if(resultList.length != keylist.length){
								var result={};
								result[key] = keylist[i];
								resultList.push(result);
							}
							else{
								resultList[i][key] = keylist[i];
							}
						}
					}
				}
				return resultList;
			}
			//处理工作安排ID数组
			function executeDataIds(oSettings){
				var jsonObj = oSettings.json.data;
				localequipInfoArray = [];
				for(var i=0;i<jsonObj.length;i++){
					localequipInfoArray.push(jsonObj[i].id);
				}
			}
			function executeEquipDataIds(oSettings){
				var jsonObj = oSettings.json.data;
				localsequipInfoArray = [];
				for(var i=0;i<jsonObj.length;i++){
					localsequipInfoArray.push(jsonObj[i].id);
				}
			}
			//验证是否到了封存时间
			function isHidden(plogDate){
				var logDate = new Date(plogDate).getTime();
				var myDate = new Date();
				var nowDate = myDate.getTime();
				//一天毫秒值
				var firstDay = 24*3600*1000;
				//五天毫秒值
				var fifthDay = 5*24*3600*1000
				return nowDate-logDate>fifthDay;
			}
			
			//判断值是否在数组里的方法zzq
			function contains(arr, obj) {
				  var i = arr.length;
				  while (i--) {
				    if (arr[i] === obj) {
				      return true;
				    }
				  }
				  return false;
			}
        </script>
    </body>
</html>