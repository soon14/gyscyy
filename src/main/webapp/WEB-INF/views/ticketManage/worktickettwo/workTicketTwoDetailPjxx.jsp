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
		        					<form class="form-horizontal" role="form"
						style="margin-right:100px;" id="workTicketFormEdit">
						<input type="hidden" id="id" name="id"
							value="${workTicketTwoEntity.id}" /> <input type="hidden"
							id="kgsgTotal" />
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">编码</label>
							<div class="col-md-4">
								<input class="col-md-12" id="code" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketTwoEntity.code}">
							</div>
							<label class="col-md-2 control-label no-padding-right">单位名称</label>
							<div class="col-md-4">
								<input id="unitNameIdEdit" type="text" readonly="readonly"
									value="${workTicketTwoEntity.unitName}" class="col-md-12">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">班组</label>
							<div class="col-md-4">
								<input id="groupIdEdit" type="text" readonly="readonly"
									value="${workTicketTwoEntity.groupName}" class="col-md-12">
							</div>
							<label class="col-md-2 control-label no-padding-right">工作负责人</label>
							<div class="col-md-4">
								<input class="col-md-12" id="guarderName" type="text"
									readonly="readonly" value="${workTicketTwoEntity.guarderName}"></input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班成员（包括工作负责人）</label>
							<div class="col-md-10">
								<textarea id="workClassMember" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketTwoEntity.workClassMember}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班人数</label>
							<div class="col-md-4">
								<input class="col-md-12" id="workClassPeople" type="text"
									readonly="readonly" maxlength="20"
									value="${workTicketTwoEntity.workClassPeople}">
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作内容</label>
							<div class="col-md-10">
								<textarea id="content" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketTwoEntity.content}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作地点</label>
							<div class="col-md-10">
								<textarea id="address" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketTwoEntity.address}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">设备编号</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentCode" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketTwoEntity.equipmentCode}">
							</div>
							<label class="col-md-2 control-label no-padding-right">设备名称</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketTwoEntity.equipmentName}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">计划开始时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="plandateStart" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketTwoEntity.plandateStart}" type="date"/>'>
							</div>
							<label class="col-md-2 control-label no-padding-right">计划终了时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketTwoEntity.plandateEnd}" type="date"/>'>
							</div>
						</div>
						<!-- 								<div class="form-group"> -->
						<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
						<!-- 									<div class="col-md-4"> -->
						<!-- 											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${workTicketTwoEntity.flawCode}"> -->
						<!-- 								</div> -->

						<!-- 							</div> -->
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
									maxlength="64" value="${workTicketTwoEntity.safeNum}">
							</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"> 相关高压 设备状态</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workCondition"  <c:if test="${workElectricTwoEntity.workCondition==null}">value="无"</c:if>  value="${workElectricTwoEntity.workCondition}"
									name="workCondition" type="text"  maxlength="64"  readonly="readonly">
								</div>
								<label class="col-md-2 control-label no-padding-right">相关低流、低压及二次回路状态</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workConditionTwo"  <c:if test="${workElectricTwoEntity.workConditionTwo==null}">value="无"</c:if>  value="${workElectricTwoEntity.workConditionTwo}"
											 name="workConditionTwo" type="text"  maxlength="64" readonly="readonly">
								</div>
							</div>
						<div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-md-10" id="divfile">
								</div>
						</div>
						<!-- 列表开始 -->
						<!-- zzq修改第二次需求 -->
						<div class="widget-main no-padding">
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px">继电保护安全措施操作项目</h5>
			 			<table id="workAqcs-table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>执行</th>
<!-- 			                                <th>执行时间</th> -->
			                                <th>操作项目</th>
			                                <th>恢复</th>
<!-- 			                                <th>恢复时间</th> -->
		                                </tr>
		                            </thead>
		                </table>
						<!-- zzq修改第二次需求 -->
						<div class="widget-main no-padding">
							 <!-- <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应断开的断路器</h5> -->
<!-- 							<table id="workSafe-table" -->
<!-- 								class="table table-striped table-bordered table-hover" -->
<!-- 								style="width:100%;"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<th style="display:none;">主键</th> -->
<!-- 										<th>序号</th> -->
<!-- 										<th>安全措施</th> -->
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<!-- 							</table> -->
<!-- 							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应拉开的隔离开关</h5> -->
<!-- 							<table id="workSafe-table-Two" -->
<!-- 								class="table table-striped table-bordered table-hover" -->
<!-- 								style="width:100%;"> -->
<!-- 								<thead> -->
<!-- 									<tr> -->
<!-- 										<th style="display:none;">主键</th> -->
<!-- 										<th>序号</th> -->
<!-- 										<th>安全措施</th> -->
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
<!-- 									</tr> -->
<!-- 								</thead> -->
<!-- 							</table> -->
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应投切的相关电源（空气开关、熔断器、连接片）压板、低压及二次回路</h5>
							<table id="workSafe-table-Three"
								class="table table-striped table-bordered table-hover"
								style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
										<th>序号</th>
										<th>安全措施</th>
<!-- 										<th>执行情况</th> -->
<!-- 										<th>操作</th> -->
									</tr>
								</thead>
							</table>
						</div>
<!-- 						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板</h5> -->
<!-- 						<table id="workSafe-table-Four" -->
<!-- 							class="table table-striped table-bordered table-hover" -->
<!-- 							style="width:100%;"> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<th style="display:none;">主键</th> -->
<!-- 									<th>序号</th> -->
<!-- 									<th>安全措施</th> -->
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
<!-- 								</tr> -->
<!-- 							</thead> -->
<!-- 						</table> -->
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应设遮拦、应挂标示牌（位置）</h5>
						<table id="workSafe-table-Five"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">其他安全措施和注意事项</h5>
						<table id="workSafe-table-Six"
							class="table table-striped table-bordered table-hover"
							style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
									<th>序号</th>
									<th>安全措施</th>
<!-- 									<th>执行情况</th> -->
<!-- 									<th>操作</th> -->
								</tr>
							</thead>
						</table>
						<h5 class="table-withoutbtn header smaller"
							style="margin-bottom:0px;">备注：</h5>
						<div id="workitemBz" class="tab-pane fade active in"
							style="margin-top: 20px;">
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">指定专责监护人</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<select class="col-md-12 chosen-select" id="remarkGuarderName" disabled="disabled" -->
<!-- 										name="remarkGuarderName" style="width: 200px;"></select> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label> -->
<!-- 								<div class="col-md-10"> -->
<!-- 									<textarea id="remarkResponsibleName" -->
<!-- 										name="remarkResponsibleName" style="height:80px; resize:none;"  readonly="readonly" -->
<!-- 										class="col-md-12" maxlength="128">${workElectricTwoEntity.remarkResponsibleName}</textarea> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
								<div class="col-md-10">
									<textarea id="remarkOther" name="remarkOther"   readonly="readonly"
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workElectricTwoEntity.remarkOther}</textarea>
								</div>
							</div>
						</div>  
						<!-- 列表结束 -->
						<!-- 审批字段开始 -->
						<div id="workitemQf" class="tab-pane fade active in"
							style="margin-top: 50px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="signerName" name="signerName"
										type="text" readonly="readonly" maxlength="64"
										value="${workElectricTwoEntity.signerName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">签发日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="signerDate" name="signerDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.signerDate}" type="date"/>'>
								</div>
							</div>
						</div>
						<div id="workitemJp" class="tab-pane fade active in">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">接票人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="ondutyName" name="ondutyName"
										type="text" readonly="readonly" maxlength="64"
										value="${workElectricTwoEntity.ondutyName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">收票日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="getticketTime"
										name="getticketTime" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.getticketTime}" type="date"/>'>
								</div>
							</div>
						</div>
						<div id="workitemPz" class="tab-pane fade active in">
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">批准开始工作时间自</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="approveStarttime" -->
<!-- 										name="approveStarttime" type="text" readonly="readonly" -->
<!-- 										maxlength="64" -->
<!-- 										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.approveStarttime}" type="date"/>'> -->
<!-- 								</div> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">至</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="approveEndtime" -->
<!-- 										name="approveEndtime" type="text" readonly="readonly" -->
<!-- 										maxlength="64" -->
<!-- 										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.approveEndtime}" type="date"/>'> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="dutyMonitorName" -->
<!-- 										name="dutyMonitorName" type="text" readonly="readonly" -->
<!-- 										maxlength="64" value="${workElectricTwoEntity.dutyMonitorName}"> -->
<!-- 								</div> -->
<!-- 							</div> -->
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">确认本工作票安全措施已全部执行</label>
							<div class="col-md-4"></div>
							<label class="col-md-2 control-label no-padding-right">许可时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="changeAllowDate"
									name="changeAllowDate" type="text" readonly="readonly"
									maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketTwoEntity.changeAllowDate}" type="date"/>'>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
							<div class="col-md-4">
								<input class="col-md-12" id="changeAllowName"
									name="changeAllowName" type="text" readonly="readonly"
									maxlength="64" value="${workTicketTwoEntity.changeAllowName}">
							</div>
							<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
							<div class="col-md-4">
								<input class="col-md-12" id="allowPicPersonName"
									name="allowPicPersonName" type="text" readonly="readonly"
									maxlength="64" value="${workElectricTwoEntity.allowPicPersonName}">
							</div>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">是否需以手触试：</label> -->
<!-- 									<div class="col-md-4" style="padding-top:7px"> -->
<!-- 										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;"> -->
<!-- 										<input type="checkbox" name="handFlag" value="0" disabled="disabled"/> -->
<!-- 										是 -->
<!-- 										</label> -->
<!-- 										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;"> -->
<!-- 										<input type="checkbox" name="handFlag" value="1" disabled="disabled"/> -->
<!-- 										否 -->
<!-- 										</label> -->
<!-- 									</div> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">手触试具体部位</label> -->
<!-- 										<div class="col-md-4"> -->
<!-- 												<input class="col-md-12" id="hand" name="hand" type="text"   -->
<!-- 												maxlength="64"  value="${workElectricTwoEntity.hand}" readonly="readonly"> -->
<!-- 										</div> -->
<!-- 								</div> -->
								<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">带电母线、导线</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="wireway" name="wireway" readonly="readonly"
																	 type="text"  maxlength="64" value="${workElectricTwoEntity.wireway}" >
															</div>
														<label class="col-md-2 control-label no-padding-right">带电的隔离开关</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="quarantine" name="quarantine"  readonly="readonly"
																	type="text"  maxlength="64"  value="${workElectricTwoEntity.quarantine}" />
															</div>
													</div>
								<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">其他</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="other" name="other"  readonly="readonly"
																	type="text"  maxlength="64"  value="${workElectricTwoEntity.other}">
															</div>
													</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他安全措施</label>
									<div class="col-md-10">
										<textarea id="otherSafe"   readonly="readonly" name="otherSafe"   style="height:60px; 
										resize:none;" class="col-md-12" maxlength="128">${workElectricTwoEntity.otherSafe}</textarea>
									</div>
								</div>
						<h5 class="table-withoutbtn header smaller"
							style="margin-bottom:0px;">工作负责人变动情况</h5>
						<div id="workitemGzfzrbd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">原工作负责人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeOldPicName"
										name="changeOldPicName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricTwoEntity.changeOldPicName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">变更后工作负责人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeNewPicName"
										name="changeNewPicName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricTwoEntity.changeNewPicName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeSignerName"
										name="changeSignerName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricTwoEntity.changeSignerName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">签发人确认日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeSignerDate"
										name="changeSignerDate" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.changeSignerDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeAllowName"
										name="changeAllowName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricTwoEntity.changeAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人确认日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeAllowDate"
										name="changeAllowDate" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.changeAllowDate}" type="date"/>'>
								</div>
							</div>
						</div>
						<h5 class="table-withoutbtn header smaller"
							style="margin-bottom:0px;">工作人员变动情况</h5>
						<div id="workitemGzzrybd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">变动人员姓名、日期及时间</label>
								<div class="col-md-10">
									<textarea id="workPersonGroup" name="workPersonGroup"
										readonly="readonly" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128">${workElectricTwoEntity.workPersonGroup}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workPersonPicName"
										name="workPersonPicName" type="text" readonly="readonly"
										maxlength="128"
										value="${workElectricTwoEntity.workPersonPicName}">
								</div>
							</div>
						</div>
						<h5 class="table-withoutbtn header smaller"
							style="margin-bottom:0px;">安全交底</h5>
						<div id="workitemGzzrybd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">安全交底</label>
								<div class="col-md-10">
									<textarea id="workDisclosure" name="workDisclosure"
										readonly="readonly" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128">${workElectricTwoEntity.workDisclosure}</textarea>
								</div>
							</div>
						</div>
						<div id="gzpyq">
							<h5 class="table-withoutbtn header smaller"
								style="margin-bottom:0px;">工作票延期：</h5>
						</div>
						<div id="workitemYq" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">有效期延长到</label>
								<div class="col-md-4">
									<input class="col-md-12" id="delayDate" name="delayDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.delayDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="delayAllowName"
										name="delayAllowName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricTwoEntity.delayAllowName}">
								</div>

							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="delayPicName" name="delayPicName"
										type="text" readonly="readonly" maxlength="64"
										value="${workElectricTwoEntity.delayPicName}">
								</div>
<!-- 								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="delayDutyMonitorName" -->
<!-- 										name="delayDutyMonitorName" type="text" readonly="readonly" -->
<!-- 										maxlength="64" -->
<!-- 										value="${workElectricTwoEntity.delayDutyMonitorName}"> -->
<!-- 								</div> -->
							</div>
						</div>
				<div id="gzpzj">
					<h5 class="table-withoutbtn header smaller"
						style="margin-bottom:0px;">工作票终结：全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
				</div>
				<div id="workitemZj" class="tab-pane fade active in"
					style="margin-top: 20px;">
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">终结时间</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endTime" name="endTime" type="text"
								readonly="readonly" maxlength="20"
								value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketTwoEntity.endTime}" type="date"/>'>
						</div>
<!-- 					</div> -->
<!-- 					<div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">未拆除或未拉开的接地线编号</label> -->
<!-- 						<div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="endGroupIndex" name="endGroupIndex" -->
<!-- 								type="text" readonly="readonly" maxlength="128" -->
<!-- 								value="${workElectricTwoEntity.endGroupIndex}"> -->
<!-- 						</div> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">共多少组</label> -->
<!-- 						<div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="endGroup" name="endGroup" -->
<!-- 								type="text" readonly="readonly" maxlength="20" -->
<!-- 								value="${workElectricTwoEntity.endGroup}"> -->
<!-- 						</div> -->
					</div>
<!-- 					<div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">未拆除（组）</label> -->
<!-- 						<div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="endStandIndex" name="endStandIndex" -->
<!-- 								type="text" readonly="readonly" maxlength="128" -->
<!-- 								value="${workElectricTwoEntity.endStandIndex}"> -->
<!-- 						</div> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">以拆除（组）</label> -->
<!-- 						<div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="endStand" name="endStand" -->
<!-- 								type="text" readonly="readonly" maxlength="20" -->
<!-- 								value="${workElectricTwoEntity.endStand}"> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">已汇报值长（值班长）。</label>
						<div class="col-md-4"></div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endPicName" name="endPicName"
								type="text" readonly="readonly" maxlength="128"
								value="${workTicketTwoEntity.endPicName}">
						</div>
						<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endAllowName" name="endAllowName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketTwoEntity.endAllowName}">
						</div>
					</div>
				</div>
			</div>
			<!-- 审批字段结束 -->
        	</form>
        	</div> 
   			<!-- <div style="margin-right:100px;">
        		<button class="btn btn-xs btn-success pull-right" data-dismiss="modal" id="printSafeTicketTwo"  >
        			<i class="glyphicon glyphicon-print"></i>
        			打印附页
        		</button>
        	</div> -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					$("input[name='safeFlag']").on('click',function(){
						var value=this.value;
						$("input[name='safeFlag']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeFlag']").each(function () {
						if(this.value=='${workTicketTwoEntity.safeFlag}'){
				        $(this).attr('checked',true);
						}
					});
					$("input[name='handFlag']").each(function () {
						if(this.value=='${workElectricTwoEntity.handFlag}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("#equipmentCode").attr("title",'${workTicketTwoEntity.equipmentCode}');
					$("#equipmentName").attr("title",'${workTicketTwoEntity.equipmentName}');
					
					var fileIsHave='${workTicketTwoEntity.changeAllowIdea}';
					if(fileIsHave==null||fileIsHave==""){
						//附件上传
						var uploaddropzone =new A.uploaddropzone({
							render : "#divfile",
							fileId:[],
							autoProcessQueue:false,//是否自动上传
							addRemoveLinks : false,//显示删除按钮
							chargeUp:true,
						}).render();
					}else{
						//附件上传
						var uploaddropzone =new A.uploaddropzone({
							render : "#divfile",
							fileId:${workTicketTwoEntity.changeAllowIdea},
							autoProcessQueue:false,//是否自动上传
							addRemoveLinks : false,//显示删除按钮
							chargeUp:true,
						}).render();
					}
					
					//指定专责监护人 组件
					var userListBoxCombobox = new A.combobox({
						render : "#remarkGuarderName",
						//返回数据待后台返回TODO
						datasource : ${userListBox},
						//multiple为true时select可以多选
						multiple : false,
						//allowBlank为false表示不允许为空
						allowBlank : true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					userListBoxCombobox.setValue(${workElectricTwoEntity.remarkGuarderName});
					//下面是安全措施的列表 1
					var workid = '${workTicketTwoEntity.id}';
					
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
										value : '${workTicketTwoEntity.id}'
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
							          {data: "hfSituation",width: "15%",orderable: true},
// 							          {data: "hfDate",width: "10%",orderable: true}
							          ]
						}
					}).render();
					//zzq修改第二次需求   继电保护安全措施票开始
					/* var conditionsOne = [];
					var workSafeOneDatatables = new A.datatables(
							{
								render : '#workSafe-table',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsOne
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 1
													});
											conditionsOne
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsOne;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, {
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
								}
							}).render();
 */
					//下面是安全措施的列表 2

					/* var conditionsTwo = [];
					var workSafeTwoDatatables = new A.datatables(
							{
								render : '#workSafe-table-Two',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsTwo
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 2
													});
											conditionsTwo
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsTwo;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, {
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
									
								}
							}).render(); */

					//下面是安全措施的列表 3

					var conditionsThree = [];
					var workSafeThreeDatatables = new A.datatables(
							{
								render : '#workSafe-table-Three',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsThree
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 3
													});
											conditionsThree
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsThree;
											d.length = 2147483647;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
								}
							}).render();
					//下面是安全措施的列表 4
					/* var conditionsFour = [];
					var workSafeFourDatatables = new A.datatables(
							{
								render : '#workSafe-table-Four',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsFour = [];
											d.length = 2147483647;
											conditionsFour
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 4
													});
											conditionsFour
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsFour;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									paging : false,
									checked : false,
									bInfo : false,
									aLengthMenu : [ 5 ],
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, {
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
									
								}
							}).render(); */
					//下面是安全措施的列表 5
					var conditionsFive = [];
					var workSafeFiveDatatables = new A.datatables(
							{
								render : '#workSafe-table-Five',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											d.length = 2147483647;
											conditionsFive = [];
											conditionsFive
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 5
													});
											conditionsFive
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsFive;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
									
								}
							}).render();
					//下面是安全措施的列表 6
					var conditionsSix = [];
					var workSafeSixDatatables = new A.datatables(
							{
								render : '#workSafe-table-Six',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsSix = [];
											d.length = 2147483647;
											conditionsSix
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 6
													});
											conditionsSix
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketTwoEntity.id}'
													});
											d.conditions = conditionsSix;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									checked : false,
									paging : false,
									bInfo : false,
									columns : [ {
										data : "id",
										visible : false,
										orderable : false
									}, /* {
										data : "orderSeq",
										width : "10%",
										orderable : true
									}, */ 
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "80%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
								}
							}).render();
					
					$("#printSafeTicketTwo").on('click',function print (){
						var info=[];
						info.push(workSafeThreeDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeFiveDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeSixDatatables._datatables.page.info().recordsTotal);
						var type=0;
						for (var int = 0; int < info.length; int++) {
							if(info[int]>type){
								type=info[int];
							}
						}
						
						if(type!=""&&type>=2){
							for (var int = 1; int < type; int++) {
								birtPrintSafe("workTicketTwoSafe.rptdesign",$("#id").val(),int);
								}
						}else{
							alert("安全措施记录至少两条才可以打印附页!");
						}
					});
				});
			});
        </script>
    </body>
</html>