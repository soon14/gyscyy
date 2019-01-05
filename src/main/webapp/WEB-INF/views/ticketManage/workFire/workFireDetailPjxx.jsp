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
								<label class="col-md-2 control-label no-padding-right">动火工作负责人</label>
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
									<label class="col-md-2 control-label no-padding-right">工作任务</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">动火工作地点</label>
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
										<input class="col-md-12" id="plandateEnd"  type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateEnd}" type="date"/>'>
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
						<h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">动火作业单位应采取的安全措施</h5>
						<table id="workSafe-table-Seven"
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
			
	 		<!-- 列表结束 -->
	 		<!-- 审批字段开始 -->
		    <form class="form-horizontal"  style="margin-right:100px;" id="spForm">
		    <div id="workitemQf" class="tab-pane fade active in" style="margin-top: 50px;">
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作票签发人签名</label>
						<div class="col-md-4">
									<input class="col-md-12" id="signerName" name="signerName" type="text" readonly="readonly"  maxlength="64" value="${workFireEntity.signerName}">
						</div>
					<label class="col-md-2 control-label no-padding-right">签发日期</label>
						<div class="col-md-4">
									<input class="col-md-12" id="signerDate" name="signerDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.signerDate}" type="date"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">工作票会签人签名</label>
						<div class="col-md-4">
									<input class="col-md-12" id="meetSignName" name="meetSignName" type="text" readonly="readonly"  maxlength="64" value="${workFireEntity.meetSignName}">
						</div>
					<label class="col-md-2 control-label no-padding-right">会签日期</label>
						<div class="col-md-4">
									<input class="col-md-12" id="meetSignDate" name="meetSignDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.meetSignDate}" type="date"/>'>
						</div>
					</div>
			</div>
		    
		 <div id="workitemPz" class="tab-pane fade active in" >
			<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
						<div class="col-md-4">
								<input class="col-md-12" id="otherApproveFire" name=otherApproveFire type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherApproveFire}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">签名日期</label>
						<div class="col-md-4">
									<input class="col-md-12" id="otherApproveFireDate" name="otherApproveFireDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.otherApproveFireDate}" type="date"/>'>
						</div>
					
		    </div>
		    <div class="form-group">
		    
		    	      <label class="col-md-2 control-label no-padding-right">安监部门负责人签名</label>
						<div class="col-md-4">
									<input class="col-md-12" id="otherApproveSafe" name="otherApproveSafe" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherApproveSafe}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">签名日期</label>
						<div class="col-md-4">
									<input class="col-md-12" id="otherApproveSafeDate" name="otherApproveSafeDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.otherApproveSafeDate}" type="date"/>'>
						</div>
			</div>
			<div class="form-group">
		          <label class="col-md-2 control-label no-padding-right">安全总监签名</label>
					<div class="col-md-4">
						<input class="col-md-12" id="safeDirctorName" name="safeDirctorName" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.safeDirctorName}" >
					</div>
					<label class="col-md-2 control-label no-padding-right">签名日期</label>
					<div class="col-md-4">
							<input class="col-md-12" id="safeDirctorDate" name="safeDirctorDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.safeDirctorDate}" type="date"/>'>
					</div>
			</div>
			<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">分管生产负责人/总工程师签名</label>
					<div class="col-md-4">
								<input class="col-md-12" id="otherLederSign" name="otherLederSign" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherLederSign}" >
					</div>
					<label class="col-md-2 control-label no-padding-right">签名日期</label>
					<div class="col-md-4">
						<input class="col-md-12" id="otherLederSignDate" name="otherLederSignDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.otherLederSignDate}" type="date"/>'>
					</div>
			   </div>
			</div>
			
			<div id="workitemJp" class="tab-pane fade active in">
			<div class="form-group">
							<label class="col-md-2 control-label no-padding-right">运行值班人员签名</label>
							<div class="col-md-4">
										<input class="col-md-12" id="dutyMonitorName" name="dutyMonitorName" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.dutyMonitorName}" >
							</div>
							<label class="col-md-2 control-label no-padding-right">收票日期</label>
						   <div class="col-md-4">
									<input class="col-md-12" id="dutyMonitorDate" name="dutyMonitorDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.dutyMonitorDate}" type="date"/>'>
						   </div>
					</div>
			</div>

			<div id="workitemXk" class="tab-pane fade active in" >
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"> 确认本工作票安全措施已全部执行</label>
								<div class="col-md-4"></div>
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
								<label class="col-md-2 control-label no-padding-right">动火负责人签名</label>
								<div class="col-md-4">
									<input class="col-md-12" id="fireUserName" name="fireUserName" type="text" readonly="readonly" maxlength="64" value="${workFireEntity.fireUserName}">
								</div>
							</div>
			        <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">其他注意事项</label>
						<div class="col-md-10">
							<textarea id="other"  readonly="readonly" name="other"   style="height:60px; 
							resize:none;" class="col-md-12" maxlength="128">${workFireEntity.other}</textarea>
						</div>
					</div>
					
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"> 可燃性、易爆气体含量或粉尘浓度合格</label>
								<div class="col-md-4"></div>
								<div class="col-md-4">
								</div>
						</div>
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
								<div class="col-md-4">
											<input class="col-md-12" id="otherFireSign" name="otherFireSign" type="text" readonly="readonly" maxlength="64" value="${workFireEntity.otherFireSign}">
								</div>
								<label class="col-md-2 control-label no-padding-right">监护时间</label>
								<div class="col-md-4">
									<input class="col-md-12" id="otherFireSignDate" name="otherFireSignDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.otherFireSignDate}" type="date"/>'>
								</div>
						</div>
						
						<div class="form-group">
							 <label class="col-md-2 control-label no-padding-right"> 首次动火作业开工见证人签名</label>
								<div class="col-md-4"></div>
								<label class="col-md-2 control-label no-padding-right">允许动火时间</label>
								<div class="col-md-4">
										<input class="col-md-12" id="approveStarttime" name="approveStarttime" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workFireEntity.approveStarttime}" type="date"/>'>
								</div>
							</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">动火工作负责人</label>
						<div class="col-md-4">
								<input class="col-md-12" id="otherPicSign" name=otherPicSign type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherPicSign}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">动火执行人</label>
						<div class="col-md-4">
								<input class="col-md-12" id="otherExecutorSign" name=otherExecutorSign type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherExecutorSign}" >
						</div>
		    </div>
		    <div class="form-group">
		    
		    	      <label class="col-md-2 control-label no-padding-right">动火单位负责人</label>
						<div class="col-md-4">
									<input class="col-md-12" id="otherGroupSign" name="otherGroupSign" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.otherGroupSign}" >
						</div>
						<label class="col-md-2 control-label no-padding-right">消防监护人</label>
						<div class="col-md-4">
								<input class="col-md-12" id="runFireSignName" name=runFireSignName type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.runFireSignName}" >
						</div>
			</div>
			<div class="form-group">
			         <label class="col-md-2 control-label no-padding-right">安监部门负责人</label>
						<div class="col-md-4">
									<input class="col-md-12" id="runSafeSign" name="runSafeSign" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.runSafeSign}" >
						</div>
			          <label class="col-md-2 control-label no-padding-right">安全总监</label>
						<div class="col-md-4">
							<input class="col-md-12" id="runSafeDirector" name="runSafeDirector" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.runSafeDirector}" >
						</div>
			</div>
			<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">分管生产负责人/总工程师</label>
						<div class="col-md-4">
									<input class="col-md-12" id="runLederSign" name="runLederSign" type="text" readonly="readonly" maxlength="64"  value="${workFireEntity.runLederSign}" >
						</div>
			   </div>
			</div>	
			
				<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">安全交底</h5>
						<div id="workitemGzzrybd" class="tab-pane fade active in"
							style="margin-top: 20px;">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">安全交底</label>
								<div class="col-md-10">
									<textarea id="workDisclosure" name="workDisclosure"
										readonly="readonly" style="height:80px; resize:none;"
										class="col-md-12" maxlength="128">${workFireEntity.workDisclosure}</textarea>
								</div>
							</div>
						</div>	
				<div id="gzpzj">
					<h5 class="table-withoutbtn header smaller"
						style="margin-bottom:0px;">工作票终结,全部工作已结束，设备及安全措施已恢复至开工前状态，工作人员已全部撤离，材料工具已清理完毕。</h5>
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
						<label class="col-md-2 control-label no-padding-right">接地线共多少组</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endHand" name="endHand"
								type="text" readonly="readonly" maxlength="15"
								value="${workFireEntity.endHand}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">已拆除(组)</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStandIndex" name="endStandIndex"
								type="text" readonly="readonly" maxlength="128"
								value="${workFireEntity.endStandIndex}">
						</div>
						<label class="col-md-2 control-label no-padding-right">因另有工作保留(组)</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endStand" name="endStand"
								type="text" readonly="readonly" maxlength="20"
								value="${workFireEntity.endStand}">
						</div>
					</div>
				
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">动火执行人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="otherendExecutorSign" name="otherendExecutorSign"
								type="text" readonly="readonly" maxlength="128"
								value=" ${workFireEntity.otherendExecutorSign}">
						</div>
						<label class="col-md-2 control-label no-padding-right">动火工作负责人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endPicName" name="endPicName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketEntity.endPicName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">消防监护人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="otherendFireSign" name="otherendFireSign"
								type="text" readonly="readonly" maxlength="128"
								value="${workFireEntity.otherendFireSign }">
						</div>
						<label class="col-md-2 control-label no-padding-right">工作许可人签名</label>
						<div class="col-md-4">
							<input class="col-md-12" id="endAllowName" name="endAllowName"
								type="text" readonly="readonly" maxlength="20"
								value="${workTicketEntity.endAllowName}">
						</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">备注</label>
								<div class="col-md-10">
									<textarea id="remark" name="remark"   readonly="readonly"
										style="height:80px; resize:none;" class="col-md-12"
										maxlength="128">${workFireEntity.remark}</textarea>
								</div>
					</div>
				</div>
			</form>
			<!-- 审批字段结束 -->
        	</div>
        	
        	<!--  <div style="margin-right:100px;">
        		<button class="btn btn-xs btn-success pull-right" data-dismiss="modal" id="printSafe" >
        			<i class="glyphicon glyphicon-print"></i>
        			打印附页
        		</button>
        	</div>  -->
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${workTicketEntity.changeAllowIdea},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					$("#equipmentCode").attr("title",'${workTicketEntity.equipmentCode}');
					$("#equipmentName").attr("title",'${workTicketEntity.equipmentName}');
					
					
					//下面是安全措施的列表 1
					var workid=${workTicketEntity.id};
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
											return JSON.stringify(d);
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
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
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
									},  */
									{orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
									{
										data : "signerContent",
										width : "60%",
										orderable : true
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
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
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
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
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
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
// 									}, {
// 										data : "executeSituation",
// 										width : "20%",
// 										orderable : true
									} ],
								}
							}).render();	
					
					//下面是安全措施的列表 1
					var conditionsSeven=[];
					var workSafeSevenDatatables = new A.datatables({
						render: '#workSafe-table-Seven',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length = 2147483647;
					            	conditionsSeven.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:12
		            				});
					            	conditionsSeven.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
		            				});
					            	d.conditions = conditionsSeven;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							aLengthMenu: [5],
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "60%",orderable: true}
							          ]
						}
					}).render();
					
					
					$("#printSafe").on('click',function print (){
						var info=[];
						info.push(workSafeOneDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeTwoDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeThreeDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeFourDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeFiveDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeSixDatatables._datatables.page.info().recordsTotal);
						info.push(workSafeSevenDatatables._datatables.page.info().recordsTotal);
						var type=0;
						for (var int = 0; int < info.length; int++) {
							if(info[int]>type){
								type=info[int];
							}
						}
						
						if(type!=""&&type>=2){
							for (var int = 1; int < type; int++) {
								birtPrintSafe("workFireSafe.rptdesign",$("#id").val(),int);
								}
						}else{
							alert("安全措施记录至少两条才可以打印附页!");
						}
						
					});
					
				});
			});
			
			
			/* function gotoQx(){
				  window.scrollTo(0,0);
				 $("#page-container").load(format_url('/workTicket/index'));
			} */
        </script>
    </body>
</html>