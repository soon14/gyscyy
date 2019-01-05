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
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormSqsy">
		        <input type="hidden" id="id" name="id" value="${workElectricTwoEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketTwoEntity.code}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">部门</label>
									<div class="col-md-4">
										<input  id="unitNameIdSpsy"  type="text" readonly="readonly" value="${workTicketTwoEntity.unitName}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">班组</label>
									<div class="col-md-4">
										<input  id="groupIdSpsy"  type="text" readonly="readonly" value="${workTicketTwoEntity.groupName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">1.试运行设备</label>
								<div class="col-md-10">
											<input class="col-md-12" id="equipmentName"  readonly="readonly" type="text" placeholder="" maxlength="64" value="${workTicketTwoEntity.equipmentName}">
								</div>
							 </div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">2.工作地点</label>
									<div class="col-md-10">
										<input id="address"  type="text"  readonly="readonly" class="col-md-12" maxlength="128" value="${workTicketTwoEntity.address}"></input>
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">3.工作负责人</label>
									<div class="col-md-10">
											<input class="col-md-12" id="guarderName" readonly="readonly" type="text"   value="${workTicketTwoEntity.guarderName}"></input>
									</div>
							</div>
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">4.工作票及试运行涉及系统其他工作票收回。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">热控工作票编号</label>
									<div class="col-md-4">
										<input  id="heatCode"  name="heatCode" type="text" readonly="readonly" maxlength="30" value="${workElectricTwoEntity.heatCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="heatPersonName"  name="heatPersonName"  readonly="readonly" type="text" maxlength="30" value="${workElectricTwoEntity.heatPersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">电气工作票编号</label>
									<div class="col-md-4">
										<input  id="electricCode" name="electricCode" type="text" readonly="readonly" value="${workElectricTwoEntity.electricCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="electricPersonName" name="electricPersonName" type="text" readonly="readonly"  value="${workElectricTwoEntity.electricPersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">机械工作票编号</label>
									<div class="col-md-4">
										<input  id="machineCode" name="machineCode" readonly="readonly" type="text" value="${workElectricTwoEntity.machineCode}" class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">工作负责人（手签）</label>
									<div class="col-md-4">
										<input  id="machinePersonName" name="machinePersonName" readonly="readonly" type="text" value="${workElectricTwoEntity.machinePersonName}" class="col-md-12">
										
									</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">其他</label>
									<div class="col-md-10">
										<textarea id="recoverOther" name="recoverOther"  readonly="readonly" style="height:50px; resize:none;" class="col-md-12" maxlength="128">${workElectricTwoEntity.recoverOther}</textarea>
									</div>
							</div>	
							<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">工作票交回，所列安全措施已拆除，可以试运行。</h5>
							<div class="form-group" style="margin-top: 20px;">
									<label class="col-md-2 control-label no-padding-right">日期：</label>
									<div class="col-md-4">
												<input class="col-md-12" id="runSureDateSqsy" name="runSureDate" type="text" readonly="readonly" maxlength="64" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${workElectricTwoEntity.runSureDate}" type="date"/>'>
								</div>
								<label class="col-md-2 control-label no-padding-right">工作负责人</label>
								<div class="col-md-4">
											<input class="col-md-12" id="runPicName" name="runPicName" type="text" readonly="readonly" maxlength="64" value="${workElectricTwoEntity.runPicName}">
								</div>
							</div>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作许可人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="runAllowName"  type="text" readonly="readonly" maxlength="64" value="${workElectricTwoEntity.runAllowName}">
								</div>
								<label class="col-md-2 control-label no-padding-right">值长（值班长）</label>
								<div class="col-md-4">
											<input class="col-md-12" id="runManagerName"  type="text" readonly="readonly"  maxlength="64" value="${workElectricTwoEntity.runManagerName}">
								</div>
							</div>
				</form>
			
				<div class="widget-main no-padding">
				<h5 class="table-title-withoutbtn header smaller lighter " style="margin-bottom:0px;">5.拆除安全措施</h5>
	 			<table id="workSafe-table-sqsy" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>需拆除的安全措施（工作负责人填写）</th>
	                                <th>执行情况</th>
                                </tr>
                            </thead>
                </table>
        </div>
        
	    </div>
		<script type="text/javascript">
			var workSafeSqsyDatatables="";
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var conditionsSqsy=[];
					workSafeSqsyDatatables = new A.datatables({
						render: '#workSafe-table-sqsy',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSqsy.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:7//申请试运
		            				});
					            	conditionsSqsy.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:${workTicketTwoEntity.id}
		            				});
					            	d.conditions = conditionsSqsy;
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
							          ]
						}
					}).render();
					
						
				});
			});
        </script>
    </body>
</html>