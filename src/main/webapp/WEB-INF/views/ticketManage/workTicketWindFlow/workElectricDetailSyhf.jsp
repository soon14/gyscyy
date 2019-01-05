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
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormSyhf">
		        <input type="hidden" id="id" name="id" value="${workElectricEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketEntity.code}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">部门</label>
									<div class="col-md-4">
										<input  id="unitNameIdSpsy"  type="text" readonly="readonly" value="${workTicketEntity.unitName}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdSpsy"  type="text" readonly="readonly" value="${workTicketEntity.groupName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">1.试运行设备</label>
								<div class="col-md-10">
											<input class="col-md-12" id="equipmentName"  readonly="readonly" type="text" placeholder="" maxlength="64" value="${workTicketEntity.equipmentName}">
								</div>
							 </div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">2.工作地点</label>
									<div class="col-md-10">
										<input id="address"  type="text"  readonly="readonly" class="col-md-12" maxlength="128" value="${workTicketEntity.address}"></input>
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">3.工作负责人</label>
									<div class="col-md-10">
											<input class="col-md-12" id="guarderName" readonly="readonly" type="text"   value="${workTicketEntity.guarderName}"></input>
									</div>
							</div>
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票所列安全措施已全部执行，可以重新工作。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">日期：</label>
									<div class="col-md-4">
												<input class="col-md-12" id="stopSureDateSqsy" name="stopSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricEntity.stopSureDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="stopPicName" name="stopPicName" type="text" readonly="readonly" maxlength="64" value="${workElectricEntity.stopPicName}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作许可人</label>
									<div class="col-md-4">
												<input class="col-md-12" id="stopAllowName"  type="text" readonly="readonly"  maxlength="64" value="${workElectricEntity.stopAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">值长（值班长）</label>
								<div class="col-md-4">
												<input class="col-md-12" id="stopManagerName"  type="text" readonly="readonly"  maxlength="64" value="${workElectricEntity.stopManagerName}">
								</div>
							</div>
				</form>
			
				<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller lighter " style="margin-bottom:0px;">4.恢复到原检修状态的安全措施</h5>
	 			<table id="workSafe-table-syhf" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>需恢复到原检修状态的安全措施</th>
	                                <th>执行情况</th>
                                </tr>
                            </thead>
                </table>
				
       			</div>
        </div>
		<script type="text/javascript">
			var workSafeSyhfDatatables="";
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var conditionsSyhf=[];
					workSafeSyhfDatatables = new A.datatables({
						render: '#workSafe-table-syhf',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSyhf.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:8//试运恢复
		            				});
					            	conditionsSyhf.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
		            				});
					            	d.conditions = conditionsSyhf;
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
        </script>
    </body>
</html>