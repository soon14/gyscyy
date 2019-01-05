<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
<div class="page-content" style="margin-left:30px;margin-right:30px;">
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
<div class="widget-main no-padding">
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top:20px;" id="overhaulLogForm">
			     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
                    </label>
					<div class="col-md-4">
				    	<select id="unitId" name="unitId" ></select>
                    	<input id="unitName"  name="unitName"  type="hidden"/>
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>日志时间
                    </label>
					<div class="col-md-4">
                    	<input id="logDate" class="col-md-12" name="logDate" value="${overhaulLogEntity.logDateString}" readonly type="text"/>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填报人
                    </label>
					<div class="col-md-4">
                    	<input id="submitUserName" class="col-md-12" name="submitUserName" value="${overhaulLogEntity.submitUserName}" readOnly type="text"/>
                    	<input id="submitUserId" class="col-md-12" name="submitUserId" value="${overhaulLogEntity.submitUserId}" readOnly type="hidden"/>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"  style="text-align:right;">
						请假人员及事由
                    </label>
					<div class="col-md-10">
                   		 <textarea id="other" class="col-md-12" style="height:150px; resize:none;" name="other" type="text" placeholder="请输入请假人员及事由" maxlength="128" value="">${overhaulLogEntity.other}</textarea>
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
       <div class="col-xs-12" style="margin-top:50px;">	
			<div class="widget-main no-padding">
		   		<h5 class='table-title header smaller blue'>工作安排</h5>			
				<table id="overhaulArrange-table" class="table table-striped table-bordered table-hover" style="width:100%;margin:55px 0 0 0;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th class="center sorting_disabled" style="width:50px;">
     								<label class="pos-rel">
     									<input type="checkbox" class="ace" />
     									<span class="lbl"></span>
     								</label>
        					</th>
							<th>序号</th>
	                      	<th>负责人</th>
	                      	<th>完成状态</th>
	                      	<th>备注</th>
	                      	<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
        </div>
   		<div class="col-xs-12" style="height:50px;">
   		</div>	
</div>
		<script type="text/javascript">
			var overhaullogDate = "${overhaulLogEntity.logDateString}";
			var companyId;
			var unitId;
			var dutyUsersId;
			var type = "${type}";
			var overhaulLogId = ${id};
			var overhaulArrangeDatatables;
			var taskId = $("#taskId").val();
			var procInstId = $("#procInstId").val();
			var procDefId = $("#procDefId").val();
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var tempOverhaulLogId=${id};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${overhaulLogEntity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					//检修单位
					 unitId = new A.combobox({
						render : "#unitId",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					unitId.setValue(${overhaulLogEntity.unitId});
					$("#unitName").val('${overhaulLogEntity.unitName}');
					//工作安排
					overhaulArrangeDatatables = new A.datatables({
						render: '#overhaulArrange-table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulArrange/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "async":false, 
					            "data": function (d) {
					            	conditions = [];
					            	if(tempOverhaulLogId){
					            		conditions.push({
											field: 'O.C_OVERHAUL_LOG_ID',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:tempOverhaulLogId
					    				});
					            	}else{
					            		conditions.push({
											field: 'O.C_OVERHAUL_LOG_ID',
					    					fieldType:'INT',
					    					matchType:'EQ',
					    					value:-1
					    				});
					            	}
					            	conditions.push({
										field: 'O.C_STATUS',
				    					fieldType:'STRING',
				    					matchType:'EQ',
				    					value:'1'
				    				});
					            	conditions.push({
										field: 'O.C_DUTY_PERSON_ID',
				    					fieldType:'INT',
				    					matchType:'EQ',
				    					value:${userEntity.id}
				    				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}},
									  {data: "dutyPersonName",width: "15%",orderable: true},  
									  {data: "finishStatusString",name:"finishStatus",width: "10%",orderable: true}, 
									  {data: "remark",width: "60%",orderable: true}],
						    fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
		                           if(iTotal==0){
		                                $("#overhaulArrange-table .odd td").attr("colspan",6);
		                           }
		                    },btns:[
   							   {
								id: "edit",
								label:"详细信息填报",
								icon: "fa fa-cog bigger-130",
								className: "blue ",
								render: function(btnNode, data){
									var logDate = new Date('${overhaulLogEntity.logDateString}').getTime();
									var myDate = new Date();
									var nowHour = myDate.getHours()*3600*1000;
									var nowMinutes = myDate.getMinutes()*60*1000;
									var nowSeconds = myDate.getSeconds()*1000;
									var nowMilliseconds = myDate.getMilliseconds();
									var zeroDate = myDate.getTime()-(nowHour+nowMinutes+nowSeconds+nowMilliseconds);
									if((logDate<zeroDate && myDate.getHours()>=8)){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var overhaulArrangeId = nData.id;
										var logDate = new Date('${overhaulLogEntity.logDateString}').getTime();
										var myDate = new Date();
										var nowHour = myDate.getHours()*3600*1000;
										var nowMinutes = myDate.getMinutes()*60*1000;
										var nowSeconds = myDate.getSeconds()*1000;
										var nowMilliseconds = myDate.getMilliseconds();
										var zeroDate = myDate.getTime()-(nowHour+nowMinutes+nowSeconds+nowMilliseconds);
										if(logDate<zeroDate && myDate.getHours()>=8){
											alert("第二天上午8点以后之前的检修日志无法修改!");
											return;
										}else{
											A.loadPage({
												render : '#page-container',
												url:format_url('/overhaulArrange/getAddMore/'+overhaulLogId+'/'+overhaulArrangeId+'/'+type+'/'+taskId+'/'+procInstId+'/'+procDefId+'/'+overhaullogDate),
											});
										}
									}
								}
							 },{
									id: "submit",
									label:"提交",
									icon: "fa fa-check-square-o bigger-130",
									className: "edit",
									render: function(btnNode, data){
										if(data.processStatus==1){
											btnNode.hide();
										}
									},
									events:{
										click: function(event, nRow, nData){
											var params = {};
											var overhaulArrangeId = nData.id;
											A.confirm('确认提交吗？',function(){
	    										$.ajax({
													url: format_url("/overhaulArrange/aloneSubmit/"+overhaulLogId+'/'+overhaulArrangeId+'/'+taskId+'/'+procInstId),
													contentType: "application/json",
													data : JSON.stringify(params),
													dataType: 'JSON',
													type: 'POST',
													success: function(result){
														alert('操作成功');
														overhaulArrangeDatatables.draw(false);
													},
													error:function(v,n){
														alert('操作失败');
													}
												})
											});
										}
									}
								 }
						  ]
						}
					}).render();
				});
			});
        </script>
    </body>
</html>