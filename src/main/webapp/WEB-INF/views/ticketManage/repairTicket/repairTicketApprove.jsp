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
		<div class="tabbable">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active" >
			 			<a  data-toggle="tab" href="#workitemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
<!-- 		 			<li> -->
<!-- 			 			<a  data-toggle="tab" href="#workcarditemEdit" aria-expanded="false"> -->
<!-- 							<i class="green ace-icon fa fa-list bigger-120"></i> -->
<!-- 							安全风险控制卡 -->
<!-- 						</a> -->
<!-- 		 			</li> -->
		 		</ul>
		 		
			<div class="tab-content">
			<div id="workitemEdit" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 600px">
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormEdit">
		        <input type="hidden" id="id" name="id" value="${repairTicketEntity.id}" />
		        <input type="hidden" id="twoTotal"/>
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
										抢修已结束
										<input type="checkbox" name="repairResult" value="0" disabled="disabled"/>
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										抢修未结束已转移工作票
										
										<input type="checkbox" name="repairResult" value="1" disabled="disabled"/>
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">保留安全措施</label>
									<div class="col-md-10">
										<textarea id="retainSafe" name="retainSafe" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workticketRepairEntity.retainSafe}</textarea>
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
<!-- 	                                <th>操作</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                 </table> -->
<!--                <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">抢修地点注意事项</h5> -->
<!-- 	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 	                                <th>序号</th> -->
<!-- 	                                <th>安全措施</th> -->
<!-- 	                                <th>抢修地点注意事项</th> -->
<!--                                     <th>操作</th> -->
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
<!--                                     <th>操作</th> -->
<!--                                 </tr> -->
<!--                             </thead> -->
<!--                 </table> -->
<!-- 	 		</div> -->
	 		
	 		<!-- 列表结束 -->
	 		<!-- 审批字段开始 -->
<!-- 		    <form class="form-horizontal"  style="margin-right:100px;" id="spForm"> -->
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
			<!-- 审批字段结束 -->
			</div>
			<!-- 风险卡开始 -->
			<div id="workcarditemEdit" class="tab-pane fade" style="overflow-x:hidden;overflow-y: auto;height: 600px">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormCardEdit">
						<input name="id" type="hidden" value="${workControlCardEntity.id}"/>
						<input name="workticketId" type="hidden" value="${repairTicketEntity.id}"/>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="guarderName" type="text" readonly="readonly"  value="${repairTicketEntity.guarderName}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">工作票编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${repairTicketEntity.code}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly"  placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">作业类别</label>
									<div class="col-md-10">
										(1)
										<input  id="cardSort"  type="hidden"></input>
										<c:forEach items="${cardSortList}" var="cardList" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardList.name}<input id="cardSort${cardList.code}" disabled="disabled" name="cardSort1" type="checkbox"  value="${cardList.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(2)
										<input  id="cardSortTwo"  type="hidden"></input>
										<c:forEach items="${cardSortListTwo}" var="cardListTwo" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListTwo.name}<input id="cardSortTwo${cardListTwo.code}" disabled="disabled" name="cardSortTwo1" type="checkbox"  value="${cardListTwo.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(3)
										<input  id="cardSortThree"  type="hidden"></input>
										<c:forEach items="${cardSortListThree}" var="cardListThree" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListThree.name}<input id="cardSortThree${cardListThree.code}" disabled="disabled" name="cardSortThree1" type="checkbox"  value="${cardListThree.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(4)
										<input  id="cardSortFour"  type="hidden"></input>
										<c:forEach items="${cardSortListFour}" var="cardListFour" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListFour.name}<input id="cardSortFour${cardListFour.code}" disabled="disabled" name="cardSortFour1" type="checkbox"  value="${cardListFour.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">补充说明</label>
									<div class="col-md-10">
										<textarea id="cardSortDescription"   readonly="readonly" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workControlCardEntity.cardSortDescription}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员签名</label>
									<div class="col-md-10">
										<textarea id="workClassMember"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${repairTicketEntity.workClassMember}</textarea>
									</div>
								</div>
    		</form>
    		<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">作业风险分析与主要预控措施</h5>
	 			<table id="controlCardRisk-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>危险点分析</th>
	                                <th>主要控制措施</th>
                                </tr>
                            </thead>
                 </table>
	 		</div>
			</div>
			<!-- 风险卡结束 -->
			</div>
			</div><!-- tabbable -->
			</div><!-- page-content -->
				
        
        <div style="margin-right:100px;margin-top: 20px;">
        		 <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
					    <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
						<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-floppy-saved"></i>
		    				再提交
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='fpBtn'}">
						<button id="fpBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-remove-circle"></i>
		    				废票
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zjzzBtn'}">
		    			<button id="zjzzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				值长（终结）
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zjXkBtn'}">
						<button id="zjXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				许可（终结）
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='zjBtn'}">
		        		<button id="zjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				工作负责人（终结）
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='pzBtn'}">
		    			<button id="pzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    					值长确定
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='fzrBtn'}">
		    			<button id="fzrBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				工作负责人确定
		    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='xkBtn'}">
		    			<button id="xkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="glyphicon glyphicon-ok-sign"></i>
		    				运行许可签字
		    			</button>
		    			</c:if>
	    		</c:forEach>
	        	</div>
		<script type="text/javascript">
			jQuery(function($) {
				var controlCardRiskDatatables="";
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					if('${workticketRepairEntity.repairResult}'!=''){
						 $(":checkbox[value='${workticketRepairEntity.repairResult}']").prop("checked",true);;
					}
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//表单详细
						if($(e.target).attr('href') == "#workitemEdit"){
						}
 						//2
						if($(e.target).attr('href') == "#workcarditemEdit"){
							controlCardRiskDatatables.draw(true);
						}
 					 });
					$('#bzform').validate({
						debug:true,
						rules: {
							},
						submitHandler: function (form) {
							var id = ${workticketRepairEntity.id};
							//修改按钮
							var url = format_url("/workticketRepair/updateBz/"+id);
							var obj = $("#bzform").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									 window.scrollTo(0,0);
									  A.loadPage({
											render : '#page-container',
											url : format_url("/todoTask/list/"+ 1 + "/" + 10)
									  });
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#bcbzBtn").on("click", function(){
    					$("#bzform").submit();
    				});
					
					//下面是安全措施的列表 1
					var workid=${repairTicketEntity.id};
					var conditionsOne=[];
					var workSafeOneDatatables="";
					workSafeOneDatatables = new A.datatables({
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
		            					value:'${repairTicketEntity.id}'
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
							          ],
										btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											render: function(btnNode, data){
												var workStatus=${repairTicketEntity.workStatus};
												if(!(workStatus=="22"||workStatus=="28")){
													btnNode.hide();
												}
											},
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
			            								workSafeOneDialog = new A.dialog({
															width: 800,
															height: 300,
															title: "安全措施编辑",
															url:format_url("/workSafe/getApproveEdit/"+ id+"/"+1),
															closed: function(){
																workSafeOneDatatables.draw(false);
															}
														}).render();
												}
											}
										}]
						}
					}).render();
//下面是安全措施的列表 2
					
					var workSafeTwoDatatables="";
					var conditionsTwo=[];
					workSafeTwoDatatables = new A.datatables({
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
		            					value:'${repairTicketEntity.id}'
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
							          ],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var workStatus=${repairTicketEntity.workStatus};
									if(!(workStatus=="22"||workStatus=="28")){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 300,
											title: "安全措施编辑",
											url:format_url("/workSafe/getApproveEdit/"+ id+"/"+2),
											closed: function(){
												workSafeTwoDatatables.draw(false);
											}
										}).render();
									}
								}
							}]
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
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								render: function(btnNode, data){
									var workStatus=${repairTicketEntity.workStatus};
									if(!(workStatus=="22"||workStatus=="28")){
										btnNode.hide();
									}
								},
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 300,
											title: "安全措施编辑",
											url:format_url("/workSafe/getApproveEdit/"+ id+"/"+3),
											closed: function(){
												workSafeThreeDatatables.draw(false);
											}
										}).render();
									}
								}
							}]
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
						 
						//控制回显多选框开始
						var onecheck='${workControlCardEntity.cardSort}';
						var  oneCheck =onecheck.split(",");
						$("input[name='cardSort1']").each(function(){
							if(contains(oneCheck,$(this).val())){
								 $("#cardSort"+$(this).val()).attr("checked",'true');
							}
						});
						var onecheckTwo='${workControlCardEntity.cardSortTwo}';
						var  oneCheckTwo =onecheckTwo.split(",");
						$("input[name='cardSortTwo1']").each(function(){
							if(contains(oneCheckTwo,$(this).val())){
								 $("#cardSortTwo"+$(this).val()).attr("checked",'true');
							}
						});
						
						var onecheckThree='${workControlCardEntity.cardSortThree}';
						var  oneCheckThree =onecheckThree.split(",");
						$("input[name='cardSortThree1']").each(function(){
							if(contains(oneCheckThree,$(this).val())){
								 $("#cardSortThree"+$(this).val()).attr("checked",'true');
							}
						});
						
						var onecheckFour='${workControlCardEntity.cardSortFour}';
						var  oneCheckFour =onecheckFour.split(",");
						$("input[name='cardSortFour1']").each(function(){
							if(contains(oneCheckFour,$(this).val())){
								 $("#cardSortFour"+$(this).val()).attr("checked",'true');
							}
						});
					   //控制回显多选框结束
					   var taskId=$("#taskId").val();
					   var procInstId=$("#procInstId").val();
					   var procDefId=$("#procDefId").val();
					   
					   var electricId=${workticketRepairEntity.id};
						//页面下方的许可按钮1
						$('#xkBtn').on('click',function(){
							var workId=${repairTicketEntity.id};
							//zzq修改
							var workPerson = '${repairTicketEntity.guarderId}';
							var qfPerson = '${workticketRepairEntity.allowPicPersonId}';
							var loginUser = '${userEntity.id}';

// 							if(workPerson==loginUser||qfPerson==loginUser){
// 								alert("许可人和工作负责人不能是同一个人");
// 								return;
// 							}else{
							$.ajax({
								url : format_url("/repairTicket/isExecute/"+workId),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
//  										if(workPerson==loginUser){
//  											alert("工作负责人、许可人和值长不能为同一个人");
//  											return;
//  										}else{
											workSafeOneDialog = new A.dialog({
				        						width: 800,
				        						height: 550,
				        						title: "许可",
				        						url:format_url("/workticketRepair/getAddXk?electricId="+electricId+"&taskId="+taskId ),
				        						closed: function(){
				        						}	
				        					}).render();
 										}
// 									}else{
// 										alert(result.errorMsg);
// 									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
//  							}
						});
						 //页面下方的签发按钮2
						$('#fzrBtn').on('click',function(){
							//zzq修改开始
							var workPerson = '${repairTicketEntity.guarderId}';
							var qfPerson = '${workticketRepairEntity.allowPicPersonId}';
							var loginUser = '${userEntity.id}';
//  							if(qfPerson==loginUser){
//  								alert("工作负责人和许可人不能是同一个人");
//  								return;
//  							}else{
 							//zzq修改结束
								workSafeOneDialog = new A.dialog({
	        						width: 1000,
	        						height: 450,
	        						title: "工作负责人确认",
	        						url:format_url("/workticketRepair/getAddQf?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
//  							}
						});
						//页面下方的批准按钮3
						$('#pzBtn').on('click',function(){
							var workPerson='${repairTicketEntity.guarderId}';
							var loginUser='${userEntity.id}';
							var changeAllowId='${repairTicketEntity.changeAllowId}';
//  							if(workPerson==loginUser||changeAllowId==loginUser){
//  								alert("工作负责人、许可人和值长不能为同一个人");
//  								return;
//  							}else{
								workSafeOneDialog = new A.dialog({
	        						width: 1000,
	        						height: 450,
	        						title: "值长确定",
	        						url:format_url("/workticketRepair/getAddPz?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
//  							}
						});
						//页面下方的终结按钮4
						$('#zjBtn').on('click',function(){
							//zzq修改开始
							var qfPerson = '${repairTicketEntity.changeAllowId}';
							var loginUser = '${userEntity.id}';
							/* if(loginUser==qfPerson){
								alert("工作负责人和许可人不能是同一个人");
								return;
							}else{ */
								window.scrollTo(0,0);
								$("#page-container").load(format_url("/workticketRepair/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
// 							}
						});
						//终结  许可人
						$('#zjXkBtn').on('click',function(){
							//zzq修改开始
							var workPerson = '${repairTicketEntity.guarderId}';
							var qfPerson = '${workticketRepairEntity.allowPicPersonId}';
							var loginUser = '${userEntity.id}';
							/* if(workPerson==loginUser||qfPerson==loginUser){
								alert("许可人和工作负责人不能是同一个人");
								return;
							}else{ */
 							//zzq修改结束
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workticketRepair/getAddZjXk?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
//  							}
						});
						//终结  值长
						$('#zjzzBtn').on('click',function(){
							//zzq修改开始
							//var workPerson = '${repairTicketEntity.guarderId}';
							var qfPerson = '${workticketRepairEntity.endAllowIdZhu}';
							var loginUser = '${userEntity.id}';
// 							if(workPerson==loginUser||qfPerson==loginUser){
// 								alert("工作负责人和许可人不能是同一个人");
// 								return;
// 							}else{
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workticketRepair/getAddZjZz?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
								
// 							}
 							//zzq修改结束
						});
						//废票
						$('#fpBtn').on('click',function(){
							var obj = $("#workTicketFormEdit").serializeObject();
							obj.id=${workticketRepairEntity.id};
							$.ajax({
								url : format_url("/workticketRepair/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('废票成功');	
										window.scrollTo(0,0);
										$("#page-container").load(format_url('/todoTask/list/1/10'));
									}else{
										alert(result.result);
									}
								},
								error:function(v,n){
									alert('废票失败');
								}
							});
						});
						
						//再提交
						$('#ztjBtn').on('click',function(){
							var id='${repairTicketEntity.id}';
							workSafeOneDialog = new A.dialog({
        						width: 500,
        						height: 450,
        						title: "再提交",
        						url:format_url("/repairTicket/sureSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/workticketRepair/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('操作成功');
							window.scrollTo(0,0);
							$("#page-container").load(format_url('/todoTask/list/1/10'));
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('操作失败');
						workTicketDatatables.draw(false);
					}
				});
				
			}
			
			
			function gotoQx(){
				  window.scrollTo(0,0);
				  A.loadPage({
						render : '#page-container',
						url : format_url("/todoTask/list/"+ 1 + "/" + 10)
					});
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