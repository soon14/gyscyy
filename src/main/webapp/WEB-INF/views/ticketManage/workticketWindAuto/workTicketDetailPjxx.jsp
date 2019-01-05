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
		        <input type="hidden" id="id" name="id" value="${workTicketEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly"  maxlength="64" value="${workTicketEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right">单位名称</label>
								<div class="col-md-4">
										<input  id="unitNameIdEdit"  type="text" readonly="readonly" value="${workTicketEntity.unitName}" class="col-md-12">
								</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdEdit"  type="text" readonly="readonly" value="${workTicketEntity.groupName}" class="col-md-12">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly"   value="${workTicketEntity.guarderName}"></input>
								</div>
							</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员（包括工作负责人）</label>
									<div class="col-md-10">
										<textarea id="workClassMember"  readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.workClassMember}</textarea>
									</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作班人数</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople"  type="text" readonly="readonly"  maxlength="20" value="${workTicketEntity.workClassPeople}">
								</div>
								</div>
								
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作地点</label>
									<div class="col-md-10">
										<textarea id="address" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.address}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">设备编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentCode"  type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.equipmentCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">设备名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="equipmentName"  type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.equipmentName}">
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">计划开始时间</label>
									<div class="col-md-4">
										<input class="col-md-12" id="plandateStart"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateStart}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">计划终了时间</label>
								<div class="col-md-4">
										<input class="col-md-12" id="equipmentName"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateEnd}" type="date"/>'>
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.flawCode}">
								</div>
								
							</div>
				</form>
			<!-- 列表开始 -->
				<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">必须采取的安全措施</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>退出一下保护、联锁</th>
	                                <th>执行情况</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">退出以下控制、检测系统</h5>
	 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>退出以下控制、检测系统</th>
	                                <th>执行情况</th>
                                </tr>
                            </thead>
                </table>
				 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">运行值班人员补充的安全措施（运行人员填写）</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
	                                <th>执行情况</th>
                                </tr>
                            </thead>
                </table>
                
	 		</div>
	 		
	 		<!-- 列表结束 -->
	 		<!-- 审批字段开始 -->
		    <form class="form-horizontal"  style="margin-right:100px;" id="spForm">
		    <div id="workitemQf" class="tab-pane fade active in" style="margin-top: 50px;">
	 						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="signerName" name="signerName" type="text" readonly="readonly"  maxlength="64" value="${WorkTicketWindEntity.signerName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">签发日期</label>
								<div class="col-md-4">
											<input class="col-md-12" id="signerDate" name="signerDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.signerDate}" type="date"/>'>
								</div>
							</div>
			</div>
			
			<div id="workitemJp" class="tab-pane fade active in" >
	 						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">接票人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="ondutyName" name="ondutyName" type="text" readonly="readonly"  maxlength="64" value="${WorkTicketWindEntity.ondutyName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">收票日期</label>
								<div class="col-md-4">
											<input class="col-md-12" id="getticketTime" name="getticketTime" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.getticketTime}" type="date"/>'>
								</div>
							</div>
			</div>
			
			<div id="workitemPz" class="tab-pane fade active in" >
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">批准开始工作时间自</label>
								<div class="col-md-4">
										<input class="col-md-12" id="approveStarttime" name="approveStarttime" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.approveStarttime}" type="date"/>'>
								</div>
							<label class="col-md-2 control-label no-padding-right">至</label>
								<div class="col-md-4">
										<input class="col-md-12" id="approveEndtime" name="approveEndtime" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.approveEndtime}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="dutyMonitorName" name="dutyMonitorName" type="text" readonly="readonly" maxlength="64"  value="${WorkTicketWindEntity.dutyMonitorName}" >
								</div>
							</div>
			</div>
			<div id="workitemXk" class="tab-pane fade active in" >
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">确认本工作票安全措施已全部执行</label>
								<div class="col-md-4">
								</div>
							<label class="col-md-2 control-label no-padding-right">许可时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowDate" name="changeAllowDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.changeAllowDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowName" name="changeAllowName" type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.changeAllowName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="allowPicPersonName" name="allowPicPersonName" type="text" readonly="readonly"  maxlength="64" value="${WorkTicketWindEntity.allowPicPersonName}">
								</div>
							</div>
			</div>
			
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">工作负责人变动情况</h5>
			<div id="workitemGzfzrbd" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">原工作负责人名字</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeOldPicName" name="changeOldPicName" type="text" readonly="readonly"  maxlength="64" value="${WorkTicketWindEntity.changeOldPicName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">变更后工作负责人名字</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeNewPicName" name="changeNewPicName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeNewPicName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeSignerName" name="changeSignerName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeSignerName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">签发人确认日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="changeSignerDate" name="changeSignerDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.changeSignerDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人名字</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowName" name="changeAllowName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeAllowName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">工作许可人确认日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="changeAllowDate" name="changeAllowDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.changeAllowDate}" type="date"/>'>
								</div>
							</div>
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">工作人员变动情况</h5>
			<div id="workitemGzzrybd" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">变动人员姓名、日期及时间</label>
									<div class="col-md-10">
										<textarea id="workPersonGroup" name="workPersonGroup"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.workPersonGroup}</textarea>
								</div>
							</div>
							<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workPersonPicName" name="workPersonPicName" type="text" readonly="readonly" maxlength="128" value="${WorkTicketWindEntity.workPersonPicName}">
								</div>
							</div>
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">工作票延期</h5>
			<div id="workitemYq" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">有效期延长到</label>
								<div class="col-md-4">
										<input class="col-md-12" id="delayDate" name="delayDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.delayDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="delayAllowName" name="delayAllowName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.delayAllowName}">
								</div>
							
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="delayPicName" name="delayPicName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.delayPicName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="delayDutyMonitorName" name="delayDutyMonitorName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.delayDutyMonitorName}">
								</div>
							</div>
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">检修设备试运行，填写检修设备试运行申请单，工作票交回，所列安全措施已拆除，可以试运行。</h5>
			<div id="workitemSyx" class="tab-pane fade active in" style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="runAllowName" name="runAllowName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.runAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="runSureDate" name="runSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.runSureDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="runPicName" name="runPicName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.runPicName}">
								</div>
							</div>
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">检修设备试运行后，填写检修设备试运行后恢复工作申请单，工作票所列安全措施已全部执行，可以重新工作。</h5>
			<div id="workitemYxhf" class="tab-pane fade active in" style="margin-top: 20px;">				
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="stopAllowName" name="stopAllowName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.stopAllowName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="stopSureDate" name="stopSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.stopSureDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="stopPicName" name="stopPicName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.stopPicName}">
								</div>
							</div>
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">工作票终结：全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
			<div id="workitemZj" class="tab-pane fade active in" style="margin-top: 20px;">	
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">终结时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">已汇报值长（值班长）。</label>
								<div class="col-md-4"></div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endPicName" name="endPicName" type="text" readonly="readonly" maxlength="128" value="${workTicketEntity.endPicName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endAllowName" name="endAllowName" type="text" readonly="readonly" maxlength="20" value="${workTicketEntity.endAllowName}">
								</div>
							</div>
							
			</div>
			<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">备注：</h5>
			<div id="workitemBz" class="tab-pane fade active in" style="margin-top: 20px;">	
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">指定专责监护人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="remarkGuarderName" name="remarkGuarderName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.remarkGuarderName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label>
								<div class="col-md-10">
										<textarea id="remarkResponsibleName" name="remarkResponsibleName"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkResponsibleName}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
								<div class="col-md-10">
										<textarea id="remarkOther" name="remarkOther"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkOther}</textarea>
								</div>
							</div>
			</div>
			</form>
        	</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					//下面是安全措施的列表 1
					var workid=${workTicketEntity.id};
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
		            					value:${workTicketEntity.id}
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5],
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
		            					value:${workTicketEntity.id}
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ]
						}
					}).render();
					var conditionsThree=[];
					var workSafeThreeDatatables = new A.datatables({
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
		            					value:${workTicketEntity.id}
		            				});
					            	d.conditions = conditionsThree;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ]
						}
					}).render();
					
				});
			});
			
			
			 function gotoQx(){
				  window.scrollTo(0,0);
				 $("#page-container").load(format_url('/workTicketWindAuto/index'));
			} 
        </script>
    </body>
</html>