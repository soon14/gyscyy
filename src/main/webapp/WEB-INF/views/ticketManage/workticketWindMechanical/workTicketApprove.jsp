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
		<div class="tabbable" >
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a  data-toggle="tab" href="#workitemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
		 			<!-- <li>
			 			<a  data-toggle="tab" href="#workcarditemEdit" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							安全风险控制卡
						</a>
		 			</li>
		 			 -->
		 		</ul>
			<div class="tab-content">
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
									<label class="col-md-2 control-label no-padding-right">工作班人员（包括工作负责人）</label>
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
									<label class="col-md-2 control-label no-padding-right">风机编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentCode"  type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.equipmentCode}">
								</div>
								<label class="col-md-2 control-label no-padding-right">风机名称</label>
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
									<label class="col-md-2 control-label no-padding-right">是否需停机：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="runAllowName" disabled="disabled" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="runAllowName" disabled="disabled" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">是否锁定叶轮：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="stopAllowName" disabled="disabled" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="stopAllowName" disabled="disabled" value="1" />
										否
										</label>
									</div>
								</div>
								<div class="form-group">
									<%-- <label class="col-md-2 control-label no-padding-right">风机编号</label>
									<div class="col-md-4">
										<input class="col-md-12" id="changeAllowIdea" readonly="readonly" value="${workTicketEntity.changeAllowIdea}" type="text"  maxlength="64" >
									</div> --%>
									<label class="col-md-2 control-label no-padding-right">相关工作位置</label>
									<div class="col-md-4">
												<input class="col-md-12" id="endAllowIdea" readonly="readonly" value="${workTicketEntity.endAllowIdea}" type="text"  maxlength="64" >
									</div>
								</div>
							
								<%-- <div class="form-group">
									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.flawCode}">
								</div>
								
							</div> --%>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">是否需办理继电保护安全措施票：</label>
								<div class="col-md-4" style="padding-top:7px">
									<label class=" inline  col-md-2  no-padding-right"
										style="width: 20%;"> <input type="checkbox" disabled="disabled"
										name="safeFlag" value="0" /> 是
									</label> <label class=" inline  col-md-2  no-padding-right"
										style="width: 20%;"> <input type="checkbox" disabled="disabled"
										name="safeFlag" value="1" /> 否
									</label>
								</div>
								<label class="col-md-2 control-label no-padding-right">共（张）</label>
								<div class="col-md-4">
									<input class="col-md-12" id="safeNum" name="safeNum" type="text" readonly="readonly"
										maxlength="64" value="${workTicketEntity.safeNum}">
								</div>
							</div>
				</form>
				<!-- 列表开始 -->
				<div class="widget-main no-padding">
				<!-- zzq修改第二次需求 -->
				<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px">继电保护安全措施操作项目</h5>
	 			<table id="workAqcs-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>执行</th>
<!-- 	                                <th>执行时间</th> -->
	                                <th>操作项目</th>
	                                <th>恢复</th>
<!-- 	                                <th>恢复时间</th> -->
                                </tr>
                            </thead>
                </table>
				<!-- zzq修改第二次需求 -->
				<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">应断开箱变断路器（熔断器或空气开关）、隔离开关</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                </tr>
                            </thead>
                </table>
                <!-- zzq修改第二次需求 -->
				 <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">应合上的接地刀闸、装设的接地线（装设地点）、应设绝缘挡板、悬挂安全绳（出仓或起吊）</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
                                </tr>
                            </thead>
                </table>
	 			<h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">应设遮拦、应挂标示牌（位置）</h5>
		 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>序号</th>
		                                <th>安全措施</th>
	                                </tr>
	                            </thead>
	                	</table>
	                <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">其他安全措施和注意事项</h5>
		 			<table id="workSafe-table-Four" class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>序号</th>
		                                <th>安全措施</th>
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
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他安全注意事项</label>
									<div class="col-md-10">
										<textarea id="otherSafe"   readonly="readonly" name="otherSafe"   style="height:60px; 
										resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkResponsibleName}</textarea>
									</div>
							</div>
			</div>
			
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
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">安全交底</h5>
			<div id="workitemGzzrybd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">安全交底</label>
								<div class="col-md-10">
									<textarea id="workCondition" name="workCondition"
										readonly="readonly" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128">${WorkTicketWindEntity.workCondition}</textarea>
								</div>
							</div>
			</div>
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
							</div>
			</div>
			<div id="gzpzj">
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票终结,全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
			</div>
			<div id="workitemZj" class="tab-pane fade active in" style="margin-top: 20px;">	
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">终结时间</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">接地线共多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endGroup" name="endGroup" type="text" readonly="readonly" maxlength="20" value="${WorkTicketWindEntity.endGroup}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">已拆除多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endStandIndex" name="endStandIndex" type="text" readonly="readonly" maxlength="128" value="${WorkTicketWindEntity.endStandIndex}">
								</div>
								<label class="col-md-2 control-label no-padding-right">保留多少组</label>
								<div class="col-md-4">
											<input class="col-md-12" id="endStand" name="endStand" type="text" readonly="readonly" maxlength="20" value="${WorkTicketWindEntity.endStand}">
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
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">备注</label>
								<div class="col-md-10">
										<textarea id="remarkOther" name="remarkOther"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${WorkTicketWindEntity.remarkOther}</textarea>
								</div>
							</div>
			</div>
			</form>
			<!-- 审批字段结束 -->
			</div>
			<!-- 风险卡开始 -->
			<%-- <div id="workcarditemEdit" class="tab-pane fade" style="overflow-x:hidden;overflow-y: auto;height: 600px">
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
			</div> --%>
			<!-- 风险卡结束 -->
			</div>
			</div><!-- tabbable -->
			</div><!-- page-content -->
        
        <div style="margin-right:100px;margin-top: 20px;">
			    <c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
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
			    				安全交底
			    			</button>
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
					$("input[name='safeFlag']").on('click',function(){
						var value=this.value;
						$("input[name='safeFlag']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeFlag']").each(function () {
						if(this.value=='${workTicketEntity.safeFlag}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("input[name='runAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='runAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='runAllowName']").each(function () {
						if(this.value=='${WorkTicketWindEntity.runAllowName}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("input[name='stopAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='stopAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='stopAllowName']").each(function () {
						if(this.value=='${WorkTicketWindEntity.stopAllowName}'){
				        $(this).attr('checked',true);
						}
					});
					
					var  workTicketId='${workTicketEntity.id}'; 
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//表单详细
						if($(e.target).attr('href') == "#workitemEdit"){
						}
 						//2
						if($(e.target).attr('href') == "#workcarditemEdit"){
							controlCardRiskDatatables.draw(true);
						}
 					 });
					
					//下面是安全措施的列表 1
					var workid='${workTicketEntity.id}';
					//zzq修改第二次需求   继电保护安全措施票开始
					var conditionsAqcs=[];
					var workSafeAqcsDatatables = new A.datatables({
						render: '#workAqcs-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsAqcs=[];
					            	conditionsAqcs.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:15//为各种票引用的安全措施的固定值
		            				});
					            	conditionsAqcs.push({
										field : 'C_WORKTICKET_ID',
										fieldType : 'LONG',
										matchType : 'EQ',
										value : '${workTicketEntity.id}'
									});
					            	d.conditions = conditionsAqcs;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "executeSituation",width: "15%",orderable: true},
// 							          {data: "executeDate",width: "10%",orderable: true},
							          {data: "signerContent",width: "50%",orderable: true},
							          {data: "hfSituation",width: "15%",orderable: true}
// 							          {data: "hfDate",width: "10%",orderable: true}
							          ]
						}
					}).render();
					//zzq修改第二次需求   继电保护安全措施票开始
					
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
							paging : false,
							bInfo : false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
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
							paging : false,
							bInfo : false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
						}
					}).render();
					
					//下面是安全措施的列表 3
					
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
							paging : false,
							bInfo : false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
						}
					}).render();
					
					//下面是安全措施的列表 4
					var workSafeFourDatatables="";
					var conditionsFour=[];
					 workSafeFourDatatables = new A.datatables({
						render: '#workSafe-table-Four',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFour.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:4
		            				});
					            	conditionsFour.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
		            				});
					            	d.conditions = conditionsFour;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging : false,
							bInfo : false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "signerContent",width: "10%",orderable: true},
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
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
  							/* if(workPerson==loginUser){
 								alert("工作负责人和签发人不能是同一个人");
 								return;
 							}else{  */
								workSafeOneDialog = new A.dialog({
	        						width: 1000,
	        						height: 400,
	        						title: "签发",
	        						url:format_url("/workTicketWindFlow/getAddQf?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
//  							}
						});
						//页面下方的收票按钮2
						$('#spBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "收票",
        						url:format_url("/workTicketWindFlow/getAddSp?electricId="+electricId+"&taskId="+taskId ),
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
        						url:format_url("/workTicketWindFlow/getAddPz?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的许可按钮4
						$('#xkBtn').on('click',function(){
							//zzq修改
							var workPerson = '${workTicketEntity.guarderId}';
							var qfPerson = '${WorkTicketWindEntity.signerId}';
							var loginUser = '${userEntity.id}';

// 								if(workPerson==loginUser||qfPerson==loginUser){
// 									alert("许可人和工作负责人和签发人不能是同一个人");
// 									return;
// 								}else{
								workSafeOneDialog = new A.dialog({
	        						width: 650,
	        						height: 450,
	        						title: "许可",
	        						url:format_url("/workTicketWindFlow/getAddXk?electricId="+electricId+"&taskId="+taskId ),
	        						closed: function(){
	        						}	
	        					}).render();
// 								}
						});
						
						//页面下方的工作负责人变更按钮
						$('#gzfzrbgBtn').on('click',function(){
							var gzfzrbgTime='${WorkTicketWindEntity.changeNewPicId}';
							if(gzfzrbgTime!=null&&gzfzrbgTime!=""){
								alert("工作负责人只能变更一次!");
								return;
							}
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 400,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindFlow/getAddGzfzrbd?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//工作负责人变更  签发
						$('#gzfzrbgQfBtn').on('click',function(){
							//zzq修改
							var workPerson = '${workTicketEntity.guarderId}';
							var xkPerson = '${WorkTicketWindEntity.changeAllowId}';
							var loginUser = '${userEntity.id}';
							/* if(workPerson==loginUser||xkPerson==loginUser){
								alert("签发人和工作负责人和许可人不能是同一个人");
								return;
							}else{ */
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 460,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindFlow/getAddGzfzrbgQf?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
// 							}
						});
						//工作负责人变更  许可
						$('#gzfzrbgXkBtn').on('click',function(){
							//zzq修改
							var workPerson = '${workTicketEntity.guarderId}';
							var qfPerson = '${WorkTicketWindEntity.signerId}';
							var loginUser = '${userEntity.id}';

// 								if(workPerson==loginUser||qfPerson==loginUser){
// 									alert("许可人和工作负责人和签发人不能是同一个人");
// 									return;
// 								}else{
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 360,
        						title: "工作负责人变更",
        						url:format_url("/workTicketWindFlow/getAddGzfzrbgXk?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
// 							}
						});
						
						//页面下方的工作人员变动按钮
						$('#gzrybdBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 700,
        						height: 250,
        						title: "工作人员变动",
        						url:format_url("/workTicketWindFlow/getAddGzrybd?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//页面下方的安全交底的按钮
						$('#bcbzBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 700,
        						height: 250,
        						title: "安全交底",
        						url:format_url("/workTicketWindFlow/getAddAqjd?electricId="+electricId+"&taskId="+taskId ),
        						closed: function(){
        						}	
        					}).render();
						});
						
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
        						url:format_url("/workTicketWindFlow/getAddYq?electricId="+electricId +"&taskId="+taskId),
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
        						url:format_url("/workTicketWindFlow/getAddYqZzqz?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						//延期   许可人
						$('#yqXkBtn').on('click',function(){
							//zzq修改
							var workPerson = '${workTicketEntity.guarderId}';
							var qfPerson = '${WorkTicketWindEntity.signerId}';
							var loginUser = '${userEntity.id}';

// 								if(workPerson==loginUser||qfPerson==loginUser){
// 									alert("许可人和工作负责人和签发人不能是同一个人");
// 									return;
// 								}else{
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 300,
        						title: "延期",
        						url:format_url("/workTicketWindFlow/getAddYqXk?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
// 							}
						});
						//延期   工作负责人
						$('#yqFzrBtn').on('click',function(){
							workSafeOneDialog = new A.dialog({
        						width: 1000,
        						height: 300,
        						title: "延期",
        						url:format_url("/workTicketWindFlow/getAddYqFzr?electricId="+electricId +"&taskId="+taskId),
        						closed: function(){
        						}	
        					}).render();
						});
						
						//页面下方的终结按钮
						$('#zjBtn').on('click',function(){
							/* zjBtnDialog = new A.dialog({ 
        						width: 1000,
        						height: 600,
        						title: "终结",
        						url:format_url("/workTicketWindFlow/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId),
        						closed: function(){
        						}	
        					}).render(); */
 							window.scrollTo(0,0);
 							$("#page-container").load(format_url("/workTicketWindFlow/getAddZj?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
						});
						//终结  许可人
						$('#zjXkBtn').on('click',function(){
							//zzq修改
							var workPerson = '${workTicketEntity.guarderId}';
							var qfPerson = '${WorkTicketWindEntity.signerId}';
							var loginUser = '${userEntity.id}';

 							window.scrollTo(0,0);
 							$("#page-container").load(format_url("/workTicketWindFlow/getAddZjXk?workId="+ workid+"&electricId="+electricId+"&taskId="+taskId+"&procInstId="+procInstId+"&procDefId="+procDefId));
// 							}
						});
						
						
						
						
						//废票
						$('#fpBtn').on('click',function(){
							var obj = $("#workTicketFormEdit").serializeObject();
							obj.id='${WorkTicketWindEntity.id}';
							$.ajax({
								url : format_url("/workTicketWindFlow/disAgreeFp?taskId="+taskId+"&procInstId="+procInstId+"&selectUser="+""),
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
        						width: 500,
        						height: 450,
        						title: "再提交",
        						url:format_url("/workTicketWindMechanical/beforeSubmit?id="+ id),
        						closed: function(){
        						}	
        					}).render();
						});
						
				});
				
			});
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var taskId=$("#taskId").val();
				var procInstId=$("#procInstId").val();
				var url =format_url("/workTicketWindFlow/againSubmit?workId="+id+"&selectUser="+selectUser+"&taskId="+taskId+"&procInstId="+procInstId);
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