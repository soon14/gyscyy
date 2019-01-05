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
		
		<div class="page-content">
		        <form class="form-horizontal" role="form" style="margin-right:100px;" >
		        <input type="hidden" id="id" name="id" value="${repairTicketEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly"  maxlength="64" value="${repairTicketEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right">单位名称</label>
								<div class="col-md-4">
										<input  id="unitNameIdEdit"  type="text" readonly="readonly" value="${repairTicketEntity.unitName}" class="col-md-12">
								</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdEdit"  type="text" readonly="readonly" value="${repairTicketEntity.groupName}" class="col-md-12">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly"   value="${repairTicketEntity.guarderName}"></input>
								</div>
							</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员（包括工作负责人）</label>
									<div class="col-md-10">
										<textarea id="workClassMember"  readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.workClassMember}</textarea>
									</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作班人数</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople"  type="text" readonly="readonly"  maxlength="20" value="${repairTicketEntity.workClassPeople}">
								</div>
								</div>
								
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作地点</label>
									<div class="col-md-10">
										<textarea id="address" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.address}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">设备编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentCode"  type="text" readonly="readonly" maxlength="64" value="${repairTicketEntity.equipmentCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="equipmentName"  type="text" readonly="readonly" maxlength="64" value="${repairTicketEntity.equipmentName}">
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">计划开始时间</label>
									<div class="col-md-4">
										<input class="col-md-12" id="plandateStart"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${repairTicketEntity.plandateStart}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">计划终了时间</label>
								<div class="col-md-4">
										<input class="col-md-12" id="equipmentName"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${repairTicketEntity.plandateEnd}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>安全措施</label>
									<div class="col-md-10">
										<textarea id="safe" name="safe" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.safe}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他安全措施</label>
									<div class="col-md-10">
										<textarea id="otherSafe" name="otherSafe" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workticketRepairEntity.otherSafe}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">抢修结果</label>
									<div class="col-md-10" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 10%;">
										抢修已结束<input type="checkbox" name="repairResult" value="0" disabled="disabled"/>
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										抢修未结束已转移工作票<input type="checkbox" name="repairResult" value="1" disabled="disabled"/>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">保留安全措施</label>
									<div class="col-md-10">
										<textarea id="retainSafe" name="retainSafe" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workticketRepairEntity.retainSafe}</textarea>
									</div>
								</div>
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${repairTicketEntity.flawCode}"> -->
<!-- 								</div> -->
								
<!-- 							</div> -->
<!-- 				</form> -->
			<!-- 列表开始 -->
<!-- 				<div class="widget-main no-padding"> -->
<!-- 				<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">采取的安全措施(工作负责人填写)</h5> -->
<!-- 	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>序号</th> -->
<!-- 	                                <th>安全措施</th> -->
<!-- 	                                <th>执行情况</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                 </table> -->
<!--                             <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">抢修地点注意事项</h5> -->
<!-- 	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>序号</th> -->
<!-- 	                                <th>抢修地点注意事项</th> -->
<!-- 	                                <th>执行情况</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                 </table> -->
<!-- 	 			<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">经现场勘查需补充下列安全措施</h5> -->
<!-- 	 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>序号</th> -->
<!-- 	                                <th>安全措施</th> -->
<!-- 	                                <th>执行情况</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                 </table> -->
<!-- 	 		</div> -->
	 		
	 		<!-- 列表结束 -->
<!-- 	 		<form class="form-horizontal"  style="margin-right:100px;" id="spForm"> -->
			<div id="workitemXk" class="tab-pane fade active in" >
							<div id="gzpqd">
							<h5 class="table-withoutbtn header smaller">工作票确定：确认本工作票安全措施已全部执行。</h5>
							</div>
							<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">许可时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowDate" name="changeAllowDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${repairTicketEntity.changeAllowDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowName" name="changeAllowName" type="text" readonly="readonly" maxlength="64" value="${repairTicketEntity.changeAllowName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="allowPicPersonName" name="allowPicPersonName" type="text" readonly="readonly"  maxlength="64" value="${workticketRepairEntity.allowPicPersonName}">
								</div>
<!-- 								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 											<input class="col-md-12" id="dutyMonitorName" name="dutyMonitorName" type="text" readonly="readonly" maxlength="64"  value="${workticketRepairEntity.dutyMonitorName}" > -->
<!-- 								</div> -->
							</div>
			</div>
			<div id="gzpzj">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票终结：全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
			</div>
			<div id="workitemZj" class="tab-pane fade active in" style="margin-top: 20px;">	
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">终结时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${repairTicketEntity.endTime}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endAllowName" name="endAllowName" type="text" readonly="readonly" maxlength="20" value="${repairTicketEntity.endAllowName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endPicName" name="endPicName" type="text" readonly="readonly" maxlength="128" value="${repairTicketEntity.endPicName}">
								</div>
							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">值长签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 												<input class="col-md-12" id="endDutyMonitorId" name="endDutyMonitorId" type="hidden" placeholder="" maxlength="64" value="${workticketRepairEntity.endDutyMonitorId}"> -->
<!-- 												<input class="col-md-12" id="endDutyMonitorName" name="endDutyMonitorName" type="text" readonly="readonly"  maxlength="64" value="${workticketRepairEntity.endDutyMonitorName}"> -->
<!-- 								</div> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">值长终结时间</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 											<input class="col-md-12" id="endDutyMonitorDate" name="endDutyMonitorDate" type="text" readonly="readonly" maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workticketRepairEntity.endDutyMonitorDate}" type="date"/>'> -->
<!-- 								</div> -->
<!-- 							</div> -->
 					<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">备注：</h5>
<!-- 				<form class="form-horizontal"  style="margin-right:100px;" id="bzform"> -->
						<div id="workitemBz" class="tab-pane fade active in" style="margin-top: 20px;">	
										<div class="form-group">
											<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
											<div class="col-md-10">
													<textarea id="remarkOther"  name="remarkOther"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.remarkOther}</textarea>
											</div>
										</div>
						</div>
				</div>
					</form>
                
	 			</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${repairTicketEntity.changeAllowIdea},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					$("#equipmentCode").attr("title",'${repairTicketEntity.equipmentCode}');
					$("#equipmentName").attr("title",'${repairTicketEntity.equipmentName}');
					
					if('${workticketRepairEntity.repairResult}'!=''){
						 $(":checkbox[value='${workticketRepairEntity.repairResult}']").prop("checked",true);;
					}
					
					//下面是安全措施的列表 1
					var workid=${repairTicketEntity.id};
					var conditionsOne=[];
					var workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsOne.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1
		            				});
					            	conditionsOne.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:${repairTicketEntity.id}
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5,10,20],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ]
						}
					}).render();
					
					//下面是安全措施的列表 2
					
					var conditionsTwo=[];
					var workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsTwo.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:2
		            				});
					            	conditionsTwo.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:${repairTicketEntity.id}
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5,10,20],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ]
						}
					}).render();
//下面是安全措施的列表 3
					
					var workSafeThreeDatatables="";
					var conditionsThree=[];
					workSafeThreeDatatables = new A.datatables({
						render: '#workSafe-table-Three',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsThree.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:3
		            				});
					            	conditionsThree.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${repairTicketEntity.id}'
		            				});
					            	d.conditions = conditionsThree;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5,10,20],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
						}
					}).render();
					
					 //第二个tab
						var conditionsCard=[];
						 controlCardRiskDatatables = new A.datatables({
							render: '#controlCardRisk-table',
							options: {
						        "ajax": {
						            "url": format_url("/controlCardRisk/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "data": function (d) {
						            	conditionsCard.push({
			            					field: 'C_CONTROL_ID',
			            					fieldType:'LONG',
			            					matchType:'EQ',
			            					value:'${workControlCardEntity.id}'
			            				});
						            	d.conditions = conditionsCard;
						                return JSON.stringify(d);
						              }
						        },
						        checked: false,
						        multiple : true,
								ordering: true,
								aLengthMenu: [5,10,20],
								optWidth: 120,
								columns: [{data:"id", visible:false,orderable:false}, 
								          {data: "dangerPoint",width: "auto",orderable: true},
								          {data: "mainControl",width: "auto",orderable: true}
								          ]
							}
						}).render();					
					
					 
					 
				});
			});
			
        </script>
    </body>
</html>