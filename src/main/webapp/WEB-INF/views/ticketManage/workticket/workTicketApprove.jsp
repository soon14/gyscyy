<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>

	<div class="page-content">
		<div class="tabbable">
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a data-toggle="tab" href="#workitemEdit"
					aria-expanded="true"> <i
						class="green ace-icon fa fa-home bigger-120"></i> 票据信息
				</a></li>
			</ul>
			<div class="tab-content">
				<div id="workitemEdit" class="tab-pane fade active in"
					style="overflow-x:hidden;overflow-y: auto;height: 600px">
					<form class="form-horizontal" role="form"
						style="margin-right:100px;" id="workTicketFormEdit">
						<input type="hidden" id="id" name="id"
							value="${workTicketEntity.id}" /> <input type="hidden"
							id="kgsgTotal" />
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">编码</label>
							<div class="col-md-4">
								<input class="col-md-12" id="code" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketEntity.code}">
							</div>
							<label class="col-md-2 control-label no-padding-right">单位名称</label>
							<div class="col-md-4">
								<input id="unitNameIdEdit" type="text" readonly="readonly"
									value="${workTicketEntity.unitName}" class="col-md-12">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">班组</label>
							<div class="col-md-4">
								<input id="groupIdEdit" type="text" readonly="readonly"
									value="${workTicketEntity.groupName}" class="col-md-12">
							</div>
							<label class="col-md-2 control-label no-padding-right">工作负责人</label>
							<div class="col-md-4">
								<input class="col-md-12" id="guarderName" type="text"
									readonly="readonly" value="${workTicketEntity.guarderName}"></input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班成员（包括工作负责人）</label>
							<div class="col-md-10">
								<textarea id="workClassMember" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketEntity.workClassMember}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作班人数</label>
							<div class="col-md-4">
								<input class="col-md-12" id="workClassPeople" type="text"
									readonly="readonly" maxlength="20"
									value="${workTicketEntity.workClassPeople}">
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作内容</label>
							<div class="col-md-10">
								<textarea id="content" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketEntity.content}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作地点</label>
							<div class="col-md-10">
								<textarea id="address" readonly="readonly"
									style="height:80px; resize:none;" class="col-md-12"
									maxlength="128">${workTicketEntity.address}</textarea>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">设备编号</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentCode" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketEntity.equipmentCode}">
							</div>
							<label class="col-md-2 control-label no-padding-right">设备名称</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value="${workTicketEntity.equipmentName}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">计划开始时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="plandateStart" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateStart}" type="date"/>'>
							</div>
							<label class="col-md-2 control-label no-padding-right">计划终了时间</label>
							<div class="col-md-4">
								<input class="col-md-12" id="equipmentName" type="text"
									readonly="readonly" maxlength="64"
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateEnd}" type="date"/>'>
							</div>
						</div>
						<!-- 								<div class="form-group"> -->
						<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
						<!-- 									<div class="col-md-4"> -->
						<!-- 											<input class="col-md-12" id="flawCode" name="flawCode" type="text" readonly="readonly" maxlength="64" value="${workTicketEntity.flawCode}"> -->
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
									maxlength="64" value="${workTicketEntity.safeNum}">
							</div>
						</div>
						
						
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
						<!-- 列表开始 -->
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应断开的断路器</h5>
							<table id="workSafe-table"
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
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应拉开的隔离开关</h5>
							<table id="workSafe-table-Two"
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
							 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应投切的相关电源（空气开关、熔断器、连接片）低压及二次回路</h5>
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
						 <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板</h5>
						<table id="workSafe-table-Four"
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
<!-- 										class="col-md-12" maxlength="128">${workElectricEntity.remarkResponsibleName}</textarea> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label>
								<div class="col-md-10">
									<textarea id="remarkOther" name="remarkOther"   readonly="readonly"
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workElectricEntity.remarkOther}</textarea>
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
										value="${workElectricEntity.signerName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">签发日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="signerDate" name="signerDate"
										type="text" readonly="readonly" maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.signerDate}" type="date"/>'>
								</div>
							</div>
						</div>
						<div id="workitemJp" class="tab-pane fade active in">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">接票人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="ondutyName" name="ondutyName"
										type="text" readonly="readonly" maxlength="64"
										value="${workElectricEntity.ondutyName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">收票日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="getticketTime"
										name="getticketTime" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.getticketTime}" type="date"/>'>
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
<!-- 										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.approveStarttime}" type="date"/>'> -->
<!-- 								</div> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">至</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="approveEndtime" -->
<!-- 										name="approveEndtime" type="text" readonly="readonly" -->
<!-- 										maxlength="64" -->
<!-- 										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.approveEndtime}" type="date"/>'> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="dutyMonitorName" -->
<!-- 										name="dutyMonitorName" type="text" readonly="readonly" -->
<!-- 										maxlength="64" value="${workElectricEntity.dutyMonitorName}"> -->
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
									value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.changeAllowDate}" type="date"/>'>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
							<div class="col-md-4">
								<input class="col-md-12" id="changeAllowName"
									name="changeAllowName" type="text" readonly="readonly"
									maxlength="64" value="${workTicketEntity.changeAllowName}">
							</div>
							<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
							<div class="col-md-4">
								<input class="col-md-12" id="allowPicPersonName"
									name="allowPicPersonName" type="text" readonly="readonly"
									maxlength="64" value="${workElectricEntity.allowPicPersonName}">
							</div>
						</div>
						<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">是否需以手触试：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="handFlag" value="0" disabled="disabled"/>
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="handFlag" value="1" disabled="disabled"/>
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">手触试具体部位</label>
										<div class="col-md-4">
												<input class="col-md-12" id="hand" name="hand" type="text"  
												maxlength="64"  value="${workElectricEntity.hand}" readonly="readonly">
										</div>
								</div>
								<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">带电母线、导线</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="wireway" name="wireway" readonly="readonly"
																	 type="text"  maxlength="64" value="${workElectricEntity.wireway}" >
															</div>
														<label class="col-md-2 control-label no-padding-right">带电的隔离开关</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="quarantine" name="quarantine"  readonly="readonly"
																	type="text"  maxlength="64"  value="${workElectricEntity.quarantine}" />
															</div>
													</div>
								<div class="form-group">
														<label class="col-md-2 control-label no-padding-right">其他</label>
															<div class="col-md-4">
																	<input class="col-md-12" id="other" name="other"  readonly="readonly"
																	type="text"  maxlength="64"  value="${workElectricEntity.other}">
															</div>
													</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他安全措施</label>
									<div class="col-md-10">
										<textarea id="otherSafe"   readonly="readonly" name="otherSafe"   style="height:60px; 
										resize:none;" class="col-md-12" maxlength="128">${workElectricEntity.otherSafe}</textarea>
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
										maxlength="64" value="${workElectricEntity.changeOldPicName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">变更后工作负责人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeNewPicName"
										name="changeNewPicName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricEntity.changeNewPicName}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作票签发人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeSignerName"
										name="changeSignerName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricEntity.changeSignerName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">签发人确认日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeSignerDate"
										name="changeSignerDate" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.changeSignerDate}" type="date"/>'>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作许可人</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeAllowName"
										name="changeAllowName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricEntity.changeAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人确认日期</label>
								<div class="col-md-4">
									<input class="col-md-12" id="changeAllowDate"
										name="changeAllowDate" type="text" readonly="readonly"
										maxlength="64"
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.changeAllowDate}" type="date"/>'>
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
										class="col-md-12" maxlength="128">${workElectricEntity.workPersonGroup}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="workPersonPicName"
										name="workPersonPicName" type="text" readonly="readonly"
										maxlength="128"
										value="${workElectricEntity.workPersonPicName}">
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
										class="col-md-12" maxlength="128">${workElectricEntity.workDisclosure}</textarea>
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
										value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.delayDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="delayAllowName"
										name="delayAllowName" type="text" readonly="readonly"
										maxlength="64" value="${workElectricEntity.delayAllowName}">
								</div>

							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="delayPicName" name="delayPicName"
										type="text" readonly="readonly" maxlength="64"
										value="${workElectricEntity.delayPicName}">
								</div>
<!-- 								<label class="col-md-2 control-label no-padding-right">值长（值班长）签名</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 									<input class="col-md-12" id="delayDutyMonitorName" -->
<!-- 										name="delayDutyMonitorName" type="text" readonly="readonly" -->
<!-- 										maxlength="64" -->
<!-- 										value="${workElectricEntity.delayDutyMonitorName}"> -->
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
								value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.endTime}" type="date"/>'>
						</div>
<!-- 					</div> -->
<!-- 					<div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right">未拆除或未拉开的接地线编号</label> -->
<!-- 						<div class="col-md-4"> -->
<!-- 							<input class="col-md-12" id="endGroupIndex" name="endGroupIndex" -->
<!-- 								type="text" readonly="readonly" maxlength="128" -->
<!-- 								value="${workElectricEntity.endGroupIndex}"> -->
<!-- 						</div> -->
						<label class="col-md-2 control-label no-padding-right">共多少组</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endGroup" name="endGroup"
								type="text" readonly="readonly" maxlength="20"
								value="${workElectricEntity.endGroup}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">未拆除（组）</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStandIndex" name="endStandIndex"
								type="text" readonly="readonly" maxlength="128"
								value="${workElectricEntity.endStandIndex}">
						</div>
						<label class="col-md-2 control-label no-padding-right">已拆除（组）</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStand" name="endStand"
								type="text" readonly="readonly" maxlength="20"
								value="${workElectricEntity.endStand}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">已汇报值长（值班长）。</label>
						<div class="col-md-4"></div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作负责人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endPicName" name="endPicName"
								type="text" readonly="readonly" maxlength="128"
								value="${workTicketEntity.endPicName}">
						</div>
						<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endAllowName" name="endAllowName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketEntity.endAllowName}">
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 审批字段结束 -->
		</form>
	</div>
</div>
	<div style="margin-right:100px;margin-top: 20px;">

		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
			<c:if test="${nodeList.authFactorCode=='ztjBtn'}">
				<button id="ztjBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 再提交
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='fpBtn'}">
				<button id="fpBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 废票
				</button>
			</c:if>

			<c:if test="${nodeList.authFactorCode=='bcbzBtn'}">
				<button id="bcbzBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 保存备注
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='syhfZzqzBtn'}">
				<button id="syhfZzqzBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 值长签字（试运恢复）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='syhfXkBtn'}">
				<button id="syhfXkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可（试运恢复）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='syhfBtn'}">
				<c:if
					test="${workElectricEntity.runManagerName!=null&&workElectricEntity.runManagerName!=''}">
					<button id="syhfBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 试运恢复
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='sqsyZzqzBtn'}">
				<button id="sqsyZzqzBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 值长签字（申请试运）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='sqsyXkBtn'}">
				<button id="sqsyXkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可（申请试运）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='sqsyBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="sqsyBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 申请试运
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='zjXkBtn'}">
				<button id="zjXkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可（终结）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='zjBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="zjBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 终结
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='yqFzrBtn'}">
				<button id="yqFzrBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 负责人签字（延期）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='yqXkBtn'}">
				<button id="yqXkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可（延期）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='yqZzqzBtn'}">
				<button id="yqZzqzBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 值长签字（延期）
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='yqBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="yqBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 延期
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzrybdBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="gzrybdBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 工作人员变动
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzjdBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="gzjdBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 安全交底
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzfzrbgXkBtn'}">
				<button id="gzfzrbgXkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可(负责人变更)
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzfzrbgQfBtn'}">
				<button id="gzfzrbgQfBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 签发(负责人变更)
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='gzfzrbgBtn'}">
				<c:if test="${workTicketEntity.workStatus!='19'}">
					<button id="gzfzrbgBtn" class="btn btn-xs btn-success pull-right"
						style="margin-right:15px;">
						<i class="ace-icon fa fa-floppy-o"></i> 工作负责人变更
					</button>
				</c:if>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='xkBtn'}">
				<button id="xkBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 许可
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='pzBtn'}">
				<button id="pzBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 值长签字
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='spBtn'}">
				<button id="spBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 收票
				</button>
			</c:if>
			<c:if test="${nodeList.authFactorCode=='qfBtn'}">
				<button id="qfBtn" class="btn btn-xs btn-success pull-right"
					style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i> 签发
				</button>
			</c:if>
		</c:forEach>

	</div>
	<script type="text/javascript">
		jQuery(function($) {
			var controlCardRiskDatatables = "";
			seajs.use([ 'combobox', 'combotree', 'my97datepicker' ],function(A) {
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
				$("input[name='handFlag']").each(function () {
					if(this.value=='${workElectricEntity.handFlag}'){
			        $(this).attr('checked',true);
					}
				});
								var workTicketId = ${workTicketEntity.id};
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
								userListBoxCombobox
										.setValue(${workElectricEntity.remarkGuarderName});

								$('#bzform')
										.validate(
												{
													debug : true,
													rules : {},
													submitHandler : function(
															form) {
														var id = ${workElectricEntity.id};
														//修改按钮
														var url = format_url("/workElectric/updateBz/"
																+ id);
														var obj = $("#bzform")
																.serializeObject();
														$
																.ajax({
																	url : url,
																	contentType : 'application/json',
																	dataType : 'JSON',
																	data : JSON
																			.stringify(obj),
																	cache : false,
																	type : 'POST',
																	success : function(
																			result) {
																		alert('操作成功');
																		window
																				.scrollTo(
																						0,
																						0);
																		A
																				.loadPage({
																					render : '#page-container',
																					url : format_url("/todoTask/list/"
																							+ 1
																							+ "/"
																							+ 10)
																				});
																	},
																	error : function(
																			v,
																			n) {
																		alert('操作失败');
																	}
																});
													}
												});
								$("#bcbzBtn").on("click", function() {
									$("#bzform").submit();
								});

								var workid = '${workTicketEntity.id}';
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
// 										          {data: "orderSeq",width: "10%",orderable: true},
										          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
										          {data: "executeSituation",width: "15%",orderable: true},
// 										          {data: "executeDate",width: "10%",orderable: true},
										          {data: "signerContent",width: "50%",orderable: true},
										          {data: "hfSituation",width: "15%",orderable: true},
// 										          {data: "hfDate",width: "10%",orderable: true}
										          ]
									}
								}).render();
								//zzq修改第二次需求   继电保护安全措施票开始
								//下面是安全措施的列表 1
								
								var conditionsOne = [];
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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
											}
										}).render();

								//下面是安全措施的列表 2

								var conditionsTwo = [];
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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
												
											}
										}).render();

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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
											}
										}).render();
								//下面是安全措施的列表 4
								var conditionsFour = [];
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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
												
											}
										}).render();
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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
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
																	value : '${workTicketEntity.id}'
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
													width : "60%",
													orderable : true
// 												}, {
// 													data : "executeSituation",
// 													width : "20%",
// 													orderable : true
												} ],
											}
										}).render();
								//控制回显多选框结束
								var taskId = $("#taskId").val();
								var procInstId = $("#procInstId").val();
								var procDefId = $("#procDefId").val();

								var electricId = ${workElectricEntity.id};
								//页面下方的签发按钮1
								$('#qfBtn').on('click',function() {
													//zzq修改开始
													var workPerson = '${workTicketEntity.guarderId}';
													var loginUser = '${userEntity.id}';
// 						 							if(workPerson==loginUser){
// 						 								alert("工作负责人和签发人不能是同一个人");
// 						 								return;
// 						 							}else{
						 							//zzq修改结束
													//判断签发人应填写的安全措施写了吗
													$.ajax({
																url : format_url("/workTicket/isQfAqcs/"
																		+ workTicketId),
																contentType : 'application/json',
																dataType : 'JSON',
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {

																		workSafeOneDialog = new A.dialog(
																				{
																					width : 1000,
																					height : 450,
																					title : "签发",
																					url : format_url("/workElectric/getAddQf?electricId="
																							+ electricId
																							+ "&taskId="
																							+ taskId),
																					closed : function() {
																					}
																				})
																				.render();

																	} else {
																		alert(result.errorMsg);
																	}
																},
																error : function(
																		v, n) {
																	alert('操作失败');
																}
															});
														//zzq修改开始
// 													 	}
													 	//zzq修改结束
												});
								//页面下方的收票按钮2
								$('#spBtn').on('click',function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 450,
																title : "收票",
																url : format_url("/workElectric/getAddSp?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});
								//页面下方的批准按钮3
								$('#pzBtn').on('click',function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 450,
																title : "批准",
																url : format_url("/workElectric/getAddPz?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});

								//页面下方的许可按钮4
								$('#xkBtn').on('click',function() {
													var workId = ${workTicketEntity.id};
													$.ajax({
																url : format_url("/workTicket/isExecute/"
																		+ workId),
																contentType : 'application/json',
																dataType : 'JSON',
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {
																		//zzq修改
																		var workPerson = '${workTicketEntity.guarderId}';
																		var qfPerson = '${workElectricEntity.signerId}';
																		var loginUser = '${userEntity.id}';

// 								 										if(workPerson==loginUser||qfPerson==loginUser){
// 								 											alert("许可人和工作负责人和签发人不能是同一个人");
// 								 											return;
// 								 										}else{
																		//许可的时候判断条数
																		$.ajax({
																					url : format_url("/workTicket/isXkAqcs/"
																							+ workTicketId),
																					contentType : 'application/json',
																					dataType : 'JSON',
																					type : 'POST',
																					success : function(
																							result) {
																						if (result.result == "success") {
																							workSafeOneDialog = new A.dialog(
																									{
																										width : 851,
																										height : 500,
																										title : "许可",
																										url : format_url("/workElectric/getAddXk?electricId="
																												+ electricId
																												+ "&taskId="
																												+ taskId),
																										closed : function() {
																										}
																									})
																									.render();
																						} else {
																							alert(result.errorMsg);
																						}
																					},
																					error : function(
																							v,
																							n) {
																						alert('操作失败');
																					}
																				});
// 																		 }//zzq修改
																	} else {
																		alert(result.errorMsg);
																	}
																},
																error : function(
																		v, n) {
																	alert('操作失败');
																}
															});
												});

								//页面下方的工作负责人变更按钮
								$('#gzfzrbgBtn')
										.on(
												'click',
												function() {
													var newPic = '${workElectricEntity.changeNewPicId}';
													if (newPic != null
															&& newPic != "") {
														alert("工作负责人只能变更一次!");
														return;
													}

													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 420,
																title : "工作负责人变更",
																url : format_url("/workElectric/getAddGzfzrbd?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});
								//工作负责人变更  签发
								$('#gzfzrbgQfBtn').on('click',function() {
													//zzq修改
										var workPerson = '${workTicketEntity.guarderId}';
										var xkPerson = '${workTicketEntity.changeAllowId}';
										var loginUser = '${userEntity.id}';
// 										if(workPerson==loginUser||xkPerson==loginUser){
// 											alert("签发人和工作负责人和许可人不能是同一个人");
// 											return;
// 										}else{
											workSafeOneDialog = new A.dialog(
													{
														width : 1000,
														height : 460,
														title : "工作负责人变更",
														url : format_url("/workElectric/getAddGzfzrbgQf?electricId="
																+ electricId
																+ "&taskId="
																+ taskId),
														closed : function() {
														}
													}).render();
// 										}
													
								});
								//工作负责人变更  许可
								$('#gzfzrbgXkBtn').on('click',function() {
										//zzq修改
										var workPerson = '${workTicketEntity.guarderId}';
										var qfPerson = '${workElectricEntity.signerId}';
										var loginUser = '${userEntity.id}';

// 										if(workPerson==loginUser||qfPerson==loginUser){
// 											alert("许可人和工作负责人和签发人不能是同一个人");
// 											return;
// 										}else{
											workSafeOneDialog = new A.dialog(
													{
														width : 1000,
														height : 360,
														title : "工作负责人变更",
														url : format_url("/workElectric/getAddGzfzrbgXk?electricId="
																+ electricId
																+ "&taskId="
																+ taskId),
														closed : function() {
														}
											}).render();
// 										}
													
								});

								//页面下方的工作人员变动按钮
								$('#gzrybdBtn')
										.on(
												'click',
												function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 700,
																height : 250,
																title : "工作人员变动",
																url : format_url("/workElectric/getAddGzrybd?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});
								//页面下方的安全交底按钮
								$('#gzjdBtn')
										.on(
												'click',
												function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 700,
																height : 250,
																title : "安全交底",
																url : format_url("/workElectric/getAddGzjd?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
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
								$('#yqBtn')
										.on(
												'click',
												function() {
													var yqdate = '${workElectricEntity.delayDate}';
													if (yqdate != null
															&& yqdate != "") {
														alert("延期只能延期一次!");
														return;
													}
													workSafeOneDialog = new A.dialog(
															{
																width : 800,
																height : 300,
																title : "延期",
																url : format_url("/workElectric/getAddYq?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});
								//延期   值长签字
								$('#yqZzqzBtn')
										.on(
												'click',
												function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 400,
																title : "延期",
																url : format_url("/workElectric/getAddYqZzqz?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});
								//延期   许可人
								$('#yqXkBtn').on('click',function() {
													//zzq修改
													var workPerson = '${workTicketEntity.guarderId}';
													var qfPerson = '${workElectricEntity.signerId}';
													var loginUser = '${userEntity.id}';

// 			 										if(workPerson==loginUser||qfPerson==loginUser){
// 			 											alert("许可人和工作负责人和签发人不能是同一个人");
// 			 											return;
// 			 										}else{
			 											workSafeOneDialog = new A.dialog(
																{
																	width : 1000,
																	height : 300,
																	title : "延期",
																	url : format_url("/workElectric/getAddYqXk?electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId),
																	closed : function() {
																	}
														}).render();
// 			 										}
												});
								//延期   工作负责人
								$('#yqFzrBtn')
										.on(
												'click',
												function() {
													workSafeOneDialog = new A.dialog(
															{
																width : 1000,
																height : 300,
																title : "延期",
																url : format_url("/workElectric/getAddYqFzr?electricId="
																		+ electricId
																		+ "&taskId="
																		+ taskId),
																closed : function() {
																}
															}).render();
												});

								//页面下方的终结按钮
								$('#zjBtn')
										.on(
												'click',
												function() {
													window.scrollTo(0, 0);
													$("#page-container")
															.load(
																	format_url("/workElectric/getAddZj?workId="
																			+ workid
																			+ "&electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId
																			+ "&procInstId="
																			+ procInstId
																			+ "&procDefId="
																			+ procDefId));
												});
								//终结  许可人
								$('#zjXkBtn').on('click',function() {
									//zzq修改
									var workPerson = '${workTicketEntity.guarderId}';
									var qfPerson = '${workElectricEntity.signerId}';
									var loginUser = '${userEntity.id}';

// 									if(workPerson==loginUser||qfPerson==loginUser){
// 										alert("许可人和工作负责人和签发人不能是同一个人");
// 										return;
// 									}else{
										window.scrollTo(0, 0);
										$("#page-container").load(
														format_url("/workElectric/getAddZjXk?workId="
																+ workid
																+ "&electricId="
																+ electricId
																+ "&taskId="
																+ taskId
																+ "&procInstId="
																+ procInstId
																+ "&procDefId="
																+ procDefId));
// 									}
								});

								//页面下方的申请试运按钮
								$('#sqsyBtn')
										.on(
												'click',
												function() {// workid  electricId
													var sfsqsy = '${workElectricEntity.runManagerName}';
													if (sfsqsy != null
															&& sfsqsy != "") {
														alert("申请试运只能做一次!");
														return;
													} else {
														window.scrollTo(0, 0);
														$("#page-container")
																.load(
																		format_url("/workElectric/getEditSqsy?workId="
																				+ workid
																				+ "&electricId="
																				+ electricId
																				+ "&taskId="
																				+ taskId
																				+ "&procInstId="
																				+ procInstId
																				+ "&procDefId="
																				+ procDefId));
													}
												});
								//申请试运  许可人
								$('#sqsyXkBtn')
										.on(
												'click',
												function() {
													window.scrollTo(0, 0);
													$("#page-container")
															.load(
																	format_url("/workElectric/getAddSqsyXk?electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId
																			+ "&procInstId="
																			+ procInstId
																			+ "&procDefId="
																			+ procDefId));
												});
								//申请试运 值长签字
								$('#sqsyZzqzBtn')
										.on(
												'click',
												function() {
													window.scrollTo(0, 0);
													$("#page-container")
															.load(
																	format_url("/workElectric/getAddSqsyZzqz?electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId
																			+ "&procInstId="
																			+ procInstId
																			+ "&procDefId="
																			+ procDefId));

												});

								//试运恢复  按钮
								$('#syhfBtn')
										.on(
												'click',
												function() {
													var sfsqsy = '${workElectricEntity.runManagerName}';
													if (sfsqsy != null
															&& sfsqsy != "") {
														var sfsyhf = '${workElectricEntity.stopManagerName}';
														if (sfsyhf != null
																&& sfsyhf != "") {
															alert("试运恢复只能做一次!");
															return;
														} else {
															window.scrollTo(0,
																	0);
															$("#page-container")
																	.load(
																			format_url("/workElectric/getEditSyhf?workId="
																					+ workid
																					+ "&electricId="
																					+ electricId
																					+ "&taskId="
																					+ taskId
																					+ "&procInstId="
																					+ procInstId
																					+ "&procDefId="
																					+ procDefId));
														}
													} else {
														alert("必须先进行申请试运才能试运恢复!");
													}
												});
								//试运恢复  许可人
								$('#syhfXkBtn')
										.on(
												'click',
												function() {
													window.scrollTo(0, 0);
													$("#page-container")
															.load(
																	format_url("/workElectric/getEditSyhfXk?workId="
																			+ workid
																			+ "&electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId
																			+ "&procInstId="
																			+ procInstId
																			+ "&procDefId="
																			+ procDefId));
												});
								//试运恢复 值长签字
								$('#syhfZzqzBtn')
										.on(
												'click',
												function() {
													window.scrollTo(0, 0);
													$("#page-container")
															.load(
																	format_url("/workElectric/getEditSyhfZzqz?workId="
																			+ workid
																			+ "&electricId="
																			+ electricId
																			+ "&taskId="
																			+ taskId
																			+ "&procInstId="
																			+ procInstId
																			+ "&procDefId="
																			+ procDefId));
												});
								//废票
								$('#fpBtn')
										.on(
												'click',
												function() {
													var obj = $(
															"#workTicketFormEdit")
															.serializeObject();
													obj.id = ${workElectricEntity.id};
													$
															.ajax({
																url : format_url("/workElectric/disAgreeFp?taskId="
																		+ taskId
																		+ "&procInstId="
																		+ procInstId
																		+ "&selectUser="
																		+ ""),
																contentType : 'application/json',
																dataType : 'JSON',
																data : JSON
																		.stringify(obj),
																cache : false,
																type : 'POST',
																success : function(
																		result) {
																	if (result.result == "success") {
																		alert('废票成功');
																		window
																				.scrollTo(
																						0,
																						0);
																		$(
																				"#page-container")
																				.load(
																						format_url('/todoTask/list/1/10'));
																	} else {
																		alert(result.result);
																	}
																},
																error : function(
																		v, n) {
																	alert('废票失败');
																}
															});
												});

								//再提交
								$('#ztjBtn')
										.on(
												'click',
												function() {
													var id = '${workTicketEntity.id}';
													workSafeOneDialog = new A.dialog(
															{
																width : 500,
																height : 450,
																title : "再提交",
																url : format_url("/workTicket/sureSubmit?id="
																		+ id),
																closed : function() {
																}
															}).render();
												});
							});
		});
		function goBackToSubmitPerson(id, selectUser) {//回调函数
			var taskId = $("#taskId").val();
			var procInstId = $("#procInstId").val();
			var url = format_url("/workElectric/againSubmit?workId=" + id
					+ "&selectUser=" + selectUser + "&taskId=" + taskId
					+ "&procInstId=" + procInstId);
			$.ajax({
				contentType : "application/json",
				dataType : "JSON",
				url : url,
				success : function(result) {
					if (result.result == "success") {
						alert('操作成功');
						window.scrollTo(0, 0);
						$("#page-container").load(
								format_url('/todoTask/list/1/10'));
					} else {
						alert(result.errorMsg);
					}
				},
				error : function(v, n) {
					alert('操作失败');
					workTicketDatatables.draw(false);
				}
			});

		}
		function gotoQx() {
			window.scrollTo(0, 0);
			A.loadPage({
				render : '#page-container',
				url : format_url("/todoTask/list/" + 1 + "/" + 10)
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