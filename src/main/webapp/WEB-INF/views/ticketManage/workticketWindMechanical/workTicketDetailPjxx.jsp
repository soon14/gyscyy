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
										<input type="checkbox"  name="runAllowName" disabled="disabled" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox"  name="runAllowName" disabled="disabled" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">是否锁定叶轮：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox"  name="stopAllowName" disabled="disabled" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox"  name="stopAllowName" disabled="disabled" value="1" />
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
							<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
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
								<label class="col-md-2 control-label no-padding-right">票签发人签名</label>
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
								<label class="col-md-2 control-label no-padding-right">收票人签名</label>
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
						
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票延期</h5>
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
			
			
			<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票终结,全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
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
        	<!-- <div style="margin-right:100px;">
        		<button class="btn btn-xs btn-success pull-right" data-dismiss="modal" id="printSafe" >
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
						if(this.value=='${workTicketEntity.safeFlag}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("#equipmentCode").attr("title",'${workTicketEntity.equipmentCode}');
					$("#equipmentName").attr("title",'${workTicketEntity.equipmentName}');
					
					
					/* $("input[name='safeNum']").on('click',function(){
						var value=this.value;
						$("input[name='safeNum']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeNum']").each(function () {
						if(this.value=='${workTicketEntity.safeNum}'){
				        $(this).attr('checked',true);
						}
					}); */
					
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
					//附件上传
					/* var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${workTicketEntity.changeAllowIdea},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render(); */
					
					var fileIsHave='${workTicketEntity.changeAllowIdea}';
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
							fileId:${workTicketEntity.changeAllowIdea},
							autoProcessQueue:false,//是否自动上传
							addRemoveLinks : false,//显示删除按钮
							chargeUp:true,
						}).render();
					}
					//下面是安全措施的列表 1
					var workid=${workTicketEntity.id};
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
							          {data: "hfSituation",width: "15%",orderable: true},
// 							          {data: "hfDate",width: "10%",orderable: true}
							          ]
						}
					}).render();
					//zzq修改第二次需求   继电保护安全措施票开始
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
					
					var conditionsFour=[];
					var workSafeFourDatatables = new A.datatables({
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
		            					value:${workTicketEntity.id}
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
					
					
					$("#printSafe").on('click',function print (){
						
						var info=[];
						info.push(workSafeOneDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeTwoDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeThreeDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeFourDatatables._datatables.page.info().recordsTotal);
						
						var type=0;
						for (var int = 0; int < info.length; int++) {
							if(info[int]>type){
								type=info[int];
							}	
						}
						alert(type);
						if(type!=""&&type>=2){
							for (var int = 1; int < type; int++) {
								birtPrintSafe("workTicketFjSafe.rptdesign",$("#id").val(),int);
							}
						}else{
							alert("安全措施记录至少两条才可以打印附页!");
						}
						
					});
					 
				});
			});
			
			
			 function gotoQx(){
				  window.scrollTo(0,0);
				 $("#page-container").load(format_url('/workTicketWindMechanical/index'));
			} 
        </script>
    </body>
</html>