<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %>
    	<style type="text/css">
    		.bgdl{
    			display: none;
    		}
    	</style>
    </head>
	<body>
		
		<div class="page-content">
		<div class="tabbable">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#workitemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#workcarditemEdit" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							安全风险控制卡
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#sqsyDetail" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							申请试运
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#syhfDetail" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							试运恢复
						</a>
		 			</li>
		 		</ul>
		 		
			<div class="tab-content">
			
			<input type="hidden" id="twoTotal"/>
			
			<div id="workitemEdit" class="tab-pane fade active in" style="overflow-x:hidden;overflow-y: auto;height: 600px">
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormEdit">
		        <input type="hidden" id="id" name="id" value="${workTicketEntity.id}" />
		        <input type="hidden" id="kgsgTotal" />
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
				<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">必须采取的安全措施</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>退出一下保护、联锁</th>
	                                <th>执行情况</th>
	                                <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">退出以下控制、检测系统</h5>
	 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>退出以下控制、检测系统</th>
	                                <th>执行情况</th>
	                                <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title-withoutbtn header smaller lighter blue" id="workSafe-h5" style="margin-bottom:0px;">运行值班人员补充的安全措施（运行人员填写）</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
	                                <th>执行情况</th>
	                                <th>操作</th>
                                </tr>
                            </thead>
                </table>
                
	 		</div> 
	 		
	 		<!-- 列表结束 -->
	 		<!-- 审批字段开始 -->
		    <form class="form-horizontal"  style="margin-right:100px;" id="spForm">
		    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
	    	<c:if test="${nodeList.authFactorCode=='workitemQf'}">
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
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemJp'}">
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
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemPz'}">
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
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemXk'}">
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
			</c:if>
			
			<c:if test="${nodeList.authFactorCode=='workitemYq'}">
			  <c:if test="${WorkTicketWindEntity.delayDate!=null}">
				<h5 class="table-withoutbtn header smaller" >工作票延期</h5>
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
<!-- 				<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;"></h5> -->
			  </c:if>
			</c:if>
			
			
			
			<c:if test="${nodeList.authFactorCode=='rybdqk'}">
			<c:if test="${WorkTicketWindEntity.changeNewPicName!=null} || ${WorkTicketWindEntity.workPersonGroup!=null}">
			<div id="rybdqk">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">人员变动情况:</h5>
			</div>
			</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemGzfzrbd'}">
			<c:if test="${WorkTicketWindEntity.changeNewPicName!=null}">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作负责人变动情况</h5>
			<div id="workitemGzfzrbd" class="tab-pane fade active in" style="margin-top: 20px;">
				<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">原工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeOldPicName" name="changeOldPicName" type="text" readonly="readonly"  maxlength="64" value="${WorkTicketWindEntity.changeOldPicName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">变更后工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeNewPicName" name="changeNewPicName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeNewPicName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeSignerName" name="changeSignerName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeSignerName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">签发人确认日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="changeSignerDate" name="changeSignerDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.changeSignerDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="changeAllowName" name="changeAllowName" type="text" readonly="readonly" maxlength="64" value="${WorkTicketWindEntity.changeAllowName}">
								</div>
							<label class="col-md-2 control-label no-padding-right">工作许可人确认日期</label>
								<div class="col-md-4">
										<input class="col-md-12" id="changeAllowDate" name="changeAllowDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${WorkTicketWindEntity.changeAllowDate}" type="date"/>'>
								</div>
							</div>
			</div>
			</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemGzzrybd'}">
				<c:if test="${WorkTicketWindEntity.workPersonGroup!=null}">
					<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作人员变动情况</h5>
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
<!-- 					<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;"></h5> -->
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='jxsy'}">
			<div id="jxsy">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">检修设备试运行，填写检修设备试运行申请单，工作票交回，所列安全措施已拆除，可以试运行。</h5>
			</div>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemSyx'}">
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
			</c:if>
			<c:if test="${nodeList.authFactorCode=='jxsyhf'}">
			<div id="jxsyhf">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">检修设备试运行后，填写检修设备试运行后恢复工作申请单，工作票所列安全措施已全部执行，可以重新工作。</h5>
			</div>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemYxhf'}">
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
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzpzj'}">
			<div id="gzpzj">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票终结：全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
			</div>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='workitemZj'}">
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
			</c:if>
			
			
			</c:forEach>
			</form>
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">备注：</h5>
			<form class="form-horizontal"  style="margin-right:100px;" id="bzform" >
			<div id="workitemBz"  class="tab-pane fade active in" style="margin-top: 20px;display:none;">	
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">指定专责监护人</label>
					<div class="col-md-4">
						<select class="col-md-12 chosen-select" id="remarkGuarderId" name="remarkGuarderId" style="width: 200px;"></select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label>
					<div class="col-md-10">
						
						<textarea id="remarkResponsibleName" name="remarkResponsibleName"    style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkResponsibleName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
					<div class="col-md-10">
							<textarea id="remarkOther" name="remarkOther"   style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkOther}</textarea>
					</div>
				</div>
			</div>
			</form>
			<form class="form-horizontal"  style="margin-right:100px;" id="bzformshow">
			<div id="workitemBzShow" class="tab-pane fade active in" style="margin-top: 20px;">	
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">指定专责监护人</label>
					<div class="col-md-4">
						<input name="remarkGuarderName"  class="col-md-12" type="text" readonly="readonly" id ='remarkGuarderName'  value="${WorkTicketWindEntity.remarkGuarderName}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label>
					<div class="col-md-10">
						
						<textarea id="remarkResponsibleName" name="remarkResponsibleName"  readOnly  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkResponsibleName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
					<div class="col-md-10">
							<textarea id="remarkOther" name="remarkOther"  readOnly style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkOther}</textarea>
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
						<input name="workticketId" type="hidden" value="${workTicketEntity.id}"/>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="guarderName" type="text" readonly="readonly"  value="${workTicketEntity.guarderName}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">工作票编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketEntity.code}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly"  placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.content}</textarea>
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
										<textarea id="workClassMember"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.workClassMember}</textarea>
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
					<div id="sqsyDetail" class="tab-pane fade" style="overflow-x:hidden;overflow-y: auto;height: 600px">
					</div>
					<div id="syhfDetail" class="tab-pane fade" style="overflow-x:hidden;overflow-y: auto;height: 600px">
					</div>
			</div>
			</div><!-- tabbable -->
			</div><!-- page-content -->
        
        <div style="margin-right:100px;margin-top: 20px;">
      		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();">
      			<i class="ace-icon fa fa-times"></i>
      			关闭
      		</button>
      		
			    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
			           	<script type="text/javascript">
      						console.log('按钮状态:'+'${nodeList.authFactorCode}');
      						if(${nodeList.authFactorCode=='bcbzBtn'}){
      							$("#workitemBz").show();
      							$("#workitemBzShow").hide();
      						//指定专责监护人 组件
      						seajs.use(['combobox'], function(A){
      							var userListBoxCombobox = new A.combobox({
      								render: "#remarkGuarderId",
      								//返回数据待后台返回TODO
      								datasource:${userListBox},
      								//multiple为true时select可以多选
      								multiple:false,
      								//allowBlank为false表示不允许为空
      								allowBlank: true,
      								options:{
      									"disable_search_threshold":10
      								}
      							}).render();
          						console.log('监护人:'+'${WorkTicketWindEntity.remarkGuarderId}');

      							userListBoxCombobox.setValue('${WorkTicketWindEntity.remarkGuarderId}');
      						});
      						}
      					</script> 
					    <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
							<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				再提交
			    			</button>
		    			</c:if>
		    			<c:if test="${nodeList.authFactorCode=='fpBtn'}">
							<button id="fpBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				废票
			    			</button>
		    			</c:if>
		    			
					    <c:if test="${nodeList.authFactorCode=='bcbzBtn'}">
							<button id="bcbzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				保存备注
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='syhfZzqzBtn'}">
							<button id="syhfZzqzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				值长签字（试运恢复）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='syhfXkBtn'}">
			    			<button id="syhfXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				许可（试运恢复）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='syhfBtn'}">
		    				<c:if test="${WorkTicketWindEntity.runManagerName!=null&&WorkTicketWindEntity.runManagerName!=''}">
								<button id="syhfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				试运恢复
				    			</button>
			    			</c:if>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='sqsyZzqzBtn'}">
			    			<button id="sqsyZzqzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				值长签字（申请试运）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='sqsyXkBtn'}">
			    			<button id="sqsyXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				许可（申请试运）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='sqsyBtn'}">
		    				<c:if test="${workTicketEntity.workStatus!='19'}">
								<button id="sqsyBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				申请试运
				    			</button>
			    			</c:if>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='zjXkBtn'}">
							<button id="zjXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				许可（终结）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='zjBtn'}">
		    				<c:if test="${workTicketEntity.workStatus!='19'}">
				        		<button id="zjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				终结
				    			</button>
			    			</c:if>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='yqFzrBtn'}">
			    			<button id="yqFzrBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				负责人签字（延期）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='yqXkBtn'}">
			    			<button id="yqXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				许可（延期）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='yqZzqzBtn'}">
			    			<button id="yqZzqzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				值长签字（延期）
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='yqBtn'}">
		    				<c:if test="${workTicketEntity.workStatus!='19'}">
				    			<button id="yqBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				延期
				    			</button>
			    			</c:if>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='gzrybdBtn'}">
		    				<c:if test="${workTicketEntity.workStatus!='19'}">
				    			<button id="gzrybdBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				工作人员变动
				    			</button>
			    			</c:if>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='gzfzrbgXkBtn'}">
			    			<button id="gzfzrbgXkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    			         许可(负责人变更)
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='gzfzrbgQfBtn'}">
			    			<button id="gzfzrbgQfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				签发(负责人变更)
			    			</button>
		    			</c:if>
	    				<c:if test="${nodeList.authFactorCode=='gzfzrbgBtn'}">
		    				<c:if test="${workTicketEntity.workStatus!='19'}">
				    			<button id="gzfzrbgBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
				    				<i class="ace-icon fa fa-floppy-o"></i>
				    				工作负责人变更
				    			</button>
			    			</c:if>
		    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='xkBtn'}">
		    			<button id="xkBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				许可
		    			</button>
		    		</c:if>
	    			<c:if test="${nodeList.authFactorCode=='pzBtn'}">
		    			<button id="pzBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				值长签字
		    			</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='spBtn'}">
			    			<button id="spBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				收票
			    			</button>
	    			</c:if>
	    			<c:if test="${nodeList.authFactorCode=='qfBtn'}">
			    			<button id="qfBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
			    				<i class="ace-icon fa fa-floppy-o"></i>
			    				签发
			    			</button>
		    		</c:if>
	    	</c:forEach>
	    </div>
 		<script type="text/javascript">
			jQuery(function($) {
			  var controlCardRiskDatatables="";
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var  workTicketId='${workTicketEntity.id}'; 
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//表单详细
						if($(e.target).attr('href') == "#workitemEdit"){
						}
 						//2
						if($(e.target).attr('href') == "#workcarditemEdit"){
							controlCardRiskDatatables.draw(true);
						}
 						//3
						if($(e.target).attr('href') == "#sqsyDetail"){
							A.loadPage({
								render: "#sqsyDetail",
								url: format_url("/workTicketWindAuto/sqsyDetail/"+workTicketId)
							});
						}
 						//4
						if($(e.target).attr('href') == "#syhfDetail"){
							A.loadPage({
								render: "#syhfDetail",
								url: format_url("/workTicketWindAuto/syhfDetail/"+workTicketId)
							});
						}
 					 });
					
					console.log("专责"+'${workTicketEntity.remarkGuarderName}');
					
					$('#bzform').validate({
						debug:true,
						rules: {
							},
						submitHandler: function (form) {
							var id = '${WorkTicketWindEntity.id}';
							//修改按钮
							var url = format_url("/workTicketWindAuto/updateBz/"+id);
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
					var workid='${workTicketEntity.id}';
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
		            					value:'${workTicketEntity.id}'
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
							          ],
										btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											render: function(btnNode, data){
												var workStatus='${workTicketEntity.workStatus}';
												if(workStatus!="5"){
													btnNode.hide();
												}
											},
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													var workStatus='${workTicketEntity.workStatus}';
			            							if(workStatus=="5"){
			            								workSafeOneDialog = new A.dialog({
															width: 800,
															height: 300,
															title: "安全措施编辑",
															url:format_url("/workSafe/getApproveEdit/"+ id+"/"+1),
															closed: function(){
																workSafeOneDatatables.draw(false);
															}
														}).render();
			            							}else{
			            								alert("只有工作票许可人可以修改执行情况");
			            							}
												}
											}
										}]
						}
					}).render();
					var workid='${workTicketEntity.id}';
					var conditionsThree=[];
					var workSafeThreeDatatables="";
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
		            					value:'${workTicketEntity.id}'
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
							          ],
										btns: [{
											id: "edit",
											label:"修改",
											icon: "fa fa-pencil-square-o bigger-130",
											className: "green edit",
											render: function(btnNode, data){
												var workStatus='${workTicketEntity.workStatus}';
												if(workStatus!="5"){
													btnNode.hide();
												}
											},
											events:{
												click: function(event, nRow, nData){
													var id = nData.id;
													var workStatus='${workTicketEntity.workStatus}';
			            							if(workStatus=="5"){
			            								workSafeOneDialog = new A.dialog({
															width: 800,
															height: 300,
															title: "安全措施编辑",
															url:format_url("/workSafe/getApproveEdit/"+ id+"/"+3),
															closed: function(){
																workSafeThreeDatatables.draw(false);
															}
														}).render();
			            							}else{
			            								alert("只有工作票许可人可以修改执行情况");
			            							}
												}
											}
										}]
						}
					}).render();
					//下面是安全措施的列表 2
					var workStatusX='${workTicketEntity.workStatus}';
					var uuid='${uuid}';
					
					var workSafeTwoDatatables="";
					var conditionsTwo=[];
					var qxAdd;
					//权限 如果是值班人员 
					if(workStatusX=='3'){
						
						$('#workSafe-h5').removeClass("table-title-withoutbtn");
						$('#workSafe-h5').addClass("table-title");
						
						qxAdd=[{
							label:"新增",
							icon:"glyphicon glyphicon-plus",
							className:"btn-success",
							events:{
	    						click:function(event){
	    							var twoTotal=Number($("#twoTotal").val())+1;
	    								workSafeOneDialog = new A.dialog({
	                						width: 800,
	                						height: 300,
	                						title: "安全措施新增",
	                						url:format_url("/workSafe/getAdd?flag="+2+"&uuid="+uuid+"&total="+twoTotal+"&workId="+workid),
	                						closed: function(){
	                							workSafeTwoDatatables.draw(false);
	                							//workSafeTwoDatatables.draw(true);
	                						}	
	                					}).render();
	    						}
							}
						}];
						
						qxUpdate=[{
							id: "edit",
							label:"修改",
							icon: "fa fa-pencil-square-o bigger-130",
							className: "green edit",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									workSafeOneDialog = new A.dialog({
										width: 800,
										height: 270,
										title: "安全措施编辑",
										url:format_url("/workSafe/getEdit/"+ id+"/"+2),
										closed: function(){
											workSafeTwoDatatables.draw(false);
										}
									}).render();
								}
							}
						}, {
							id:"delete",
							label:"删除",
							icon: "fa fa-trash-o bigger-130",
							className: "red del",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var url =format_url('/workSafe/'+ id);
									A.confirm('您确认删除么？',function(){
										$.ajax({
											url : url,
    										contentType : 'application/json',
    										dataType : 'JSON',
    										type : 'DELETE',
    										success: function(result){
    											alert('删除成功');
    											workSafeTwoDatatables.draw(false);
    										},
    										error:function(v,n){
    											alert('操作失败');
    										}
										});
									});
								}
							}
					}
						]
					}else {
						qxAdd=[];
						qxUpdate=[];
					}
					
					//console.log("状态:"+workStatusX);
					if(workStatusX=='5'){
						qxUpdate=[{
							id: "edit",
							label:"修改",
							icon: "fa fa-pencil-square-o bigger-130",
							className: "green edit",
							render: function(btnNode, data){
								var workStatus='${workTicketEntity.workStatus}';
								if(workStatus!="5"){
									btnNode.hide();
								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									var workStatus='${workTicketEntity.workStatus}';
        							if(workStatus=="5"){
        								workSafeOneDialog = new A.dialog({
											width: 800,
											height: 300,
											title: "安全措施编辑",
											url:format_url("/workSafe/getApproveEdit/"+ id+"/"+2),
											closed: function(){
												workSafeTwoDatatables.draw(false);
											}
										}).render();
        							}else{
        								alert("只有工作票许可人可以修改执行情况");
        							}
								}
							}
						}];
					}
					
					workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsTwo=[]; 
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
		            					value:'${workTicketEntity.id}'
		            				});
					            	
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
										  $("#workSafe-table-Two_info").html(sPre);//下脚标
												$("#twoTotal").val(iTotal);
											},
							toolbars: qxAdd,
							btns: qxUpdate
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
								aLengthMenu: [5],
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
					   
					   var electricId='${WorkTicketWindEntity.id}';
					   //页面下方的签发按钮1
						$('#qfBtn').on('click',function(){
							var workPerson='${workTicketEntity.guarderId}';
							var loginUser='${userEntity.id}';
// 							if(workPerson==loginUser){
// 								alert("工作负责人和签发人不能是同一个人");
// 								return;
// 							}else{ 
								workSafeOneDialog = new A.dialog({
	        						width: 1000,
	        						height: 400,
	        						title: "签发",
	        						url:format_url("/workTicketWindAuto/getAddQf?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
// 							}
						});
						//页面下方的收票按钮2
						$('#spBtn').on('click',function(){
							
							
							
							var size2 = workSafeTwoDatatables.getDatas().length;
							if(size2==0){
								alert("安全措施，未填写完整!");
								return ;
							}
							
							
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "收票",
        						url:format_url("/workTicketWindAuto/getAddSp?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						//页面下方的批准按钮3
						$('#pzBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 450,
        						title: "批准",
        						url:format_url("/workTicketWindAuto/getAddPz?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的许可按钮4
						$('#xkBtn').on('click',function(){
							var workId='${workTicketEntity.id}';
							$.ajax({
								url : format_url("/workTicketWindAuto/isExecute/"+workId),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										
										var workPerson='${workTicketEntity.guarderId}';
										var qfPerson='${WorkTicketWindEntity.signerId}';
										var loginUser='${userEntity.id}';
										
// 										if(workPerson==loginUser||qfPerson==loginUser){
// 											alert("许可人和工作负责人和签发人不能是同一个人");
// 											return;
// 										} 
// 										else{
											workSafeOneDialog = new A.dialog({
				        						width: 600,
				        						height: 350,
				        						title: "许可",
				        						url:format_url("/workTicketWindAuto/getAddXk?electricId="+electricId+"&taskId="+taskId ),
				        						closed: function(){
				        						}	
				        					}).render();
// 										}
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
							
							/* $("#workSafe-table tbody").find("tr").each(function(i){
								   var tdArrs = $(this).children();
								   var zxqk=tdArrs.eq(2).text();
								   if(zxqk==""){
									   flag=false;
									   return;
								   }
						     }); */
						});
						
						//页面下方的工作负责人变更按钮
						$('#gzfzrbgBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindAuto/getAddGzfzrbd?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						//工作负责人变更  签发
						$('#gzfzrbgQfBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 460,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindAuto/getAddGzfzrbgQf?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						//工作负责人变更  许可
						$('#gzfzrbgXkBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 360,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindAuto/getAddGzfzrbgXk?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的工作人员变动按钮
						$('#gzrybdBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 700,
        						height: 250,
        						title: "工作人员变动",
        						url:format_url("/workTicketWindAuto/getAddGzrybd?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//工作人员变动  负责人
						/* $('#gzrybdFzrBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 300,
        						title: "工作人员变动",
        						url:format_url("/workElectric/getAddGzrybdFzr?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						}); */
						
						//页面下方的延期按钮
						$('#yqBtn').on('click',function(){
							var yqdate='${WorkTicketWindEntity.delayDate}';
							if(yqdate!=null&&yqdate!=""){
								alert("延期只能延期一次!");
								return;
							}
							workSafeOneDialog = new A.dialog({
        						width: 800,
        						height: 300,
        						title: "延期",
        						url:format_url("/workTicketWindAuto/getAddYq?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//延期   值长签字
						$('#yqZzqzBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "延期",
        						url:format_url("/workTicketWindAuto/getAddYqZzqz?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//延期   许可人
						$('#yqXkBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 500,
        						title: "延期",
        						url:format_url("/workTicketWindAuto/getAddYqXk?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//延期   工作负责人
						$('#yqFzrBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 300,
        						title: "延期",
        						url:format_url("/workTicketWindAuto/getAddYqFzr?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的终结按钮
						$('#zjBtn').on('click',function(){
							zjBtnDialog = new A.dialog({ 
        						width: 1000,
        						height: 600,
        						title: "终结",
        						url:format_url("/workTicketWindAuto/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId),
        						closed: function(){
        						}	
        					}).render();
// 							window.scrollTo(0,0);
// 							$("#page-container").load(format_url("/workTicketWindAuto/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//终结  许可人
						$('#zjXkBtn').on('click',function(){
							zjXkBtnDialog = new A.dialog({ 
        						width: 1000,
        						height: 500,
        						title: "终结许可",
        						url:format_url("/workTicketWindAuto/getAddZjXk?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId),
        						closed: function(){
        						}	
        					}).render();
// 							window.scrollTo(0,0);
// 							$("#page-container").load(format_url("/workTicketWindAuto/getAddZjXk?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						
						
						//页面下方的申请试运按钮
						$('#sqsyBtn').on('click',function(){// workid  electricId
							var sfsqsy='${WorkTicketWindEntity.runManagerName}';
							if(sfsqsy!=null&&sfsqsy!=""){
								alert("申请试运只能做一次!");
								return;
							}else{
								window.scrollTo(0,0);
								$("#page-container").load(format_url("/workTicketWindAuto/getEditSqsy?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
							}
						});
						//申请试运  许可人
						$('#sqsyXkBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workTicketWindAuto/getAddSqsyXk?electricId="+electricId +"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//申请试运 值长签字
						$('#sqsyZzqzBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workTicketWindAuto/getAddSqsyZzqz?electricId="+electricId +"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
							
						});
						
						
						//试运恢复  按钮
						$('#syhfBtn').on('click',function(){
							var sfsqsy='${WorkTicketWindEntity.runManagerName}';
							
							if(sfsqsy!=null&&sfsqsy!=""){
								var sfsyhf='${WorkTicketWindEntity.stopManagerName}';
								if(sfsyhf!=null&&sfsyhf!=""){
									alert("试运恢复只能做一次!");
									return;
								}else{
									window.scrollTo(0,0);
									$("#page-container").load(format_url("/workTicketWindAuto/getEditSyhf?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
								}
							}else{
								alert("必须先进行申请试运才能试运恢复!");
							}
						});
						//试运恢复  许可人
						$('#syhfXkBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workTicketWindAuto/getEditSyhfXk?workId="+ workid+"&electricId="+electricId +"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//试运恢复 值长签字
						$('#syhfZzqzBtn').on('click',function(){
							window.scrollTo(0,0);
							$("#page-container").load(format_url("/workTicketWindAuto/getEditSyhfZzqz?workId="+ workid+"&electricId="+electricId +"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//废票
						$('#fpBtn').on('click',function(){
							var obj = $("#workTicketFormEdit").serializeObject();
							obj.id='${WorkTicketWindEntity.id}';
							$.ajax({
								url : format_url("/workTicketWindAuto/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
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
							var id='${workTicketEntity.id}';
							workSafeOneDialog = new A.dialog({ 
        						width:600,
        						height: 500,
        						title: "再提交",
        						url:format_url("/workTicketWindAuto/beforeSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
						
			////////////////////////////////////////////////////////////////////////////////////////END//////////////////////////////////////////////////////////////	
				});
				
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/workTicketWindAuto/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
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