<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/common/meta.jsp"%>
</head>
<body>

	<div class="page-content">
		<div class="tabbable" style="margin-top:20px;">
			<ul class="nav nav-tabs" id="myTab2">
				<c:forEach items="${runWayVoList}" var="unitList" varStatus="vs">
						<li <c:if test="${vs.index==0}"> class="active" </c:if>
						<c:if test="${vs.index!=0}"> class="" </c:if>
					>
					<a data-toggle="tab" href="#${unitList.sysUnitEntity.id}Div" aria-expanded="true">
						<i class="green ace-icon fa fa-columns bigger-120"></i>
						${unitList.sysUnitEntity.name}
					</a>
					</li>
				</c:forEach>
			</ul>
		<form id="addRunWayForm" class="form-horizontal" role="form">
			<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 600px">
							<c:forEach items="${runWayVoList}" var="unitList" varStatus="vs">
								<div id="${unitList.sysUnitEntity.id}Div" <c:if test="${vs.index==0}"> class="tab-pane fade  active in" </c:if> 
									<c:if test="${vs.index!=0}"> class="tab-pane fade" </c:if>  >
											<c:forEach items="${unitList.runWayList}" var="runWayVo" varStatus="vs2">
												 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 " >
														<label class="col-lg-6  control-label no-padding-right" id="device-${runWayVo.id}">${runWayVo.deviceName}</label>
														<div class=" col-lg-6">
														<select id="runWay-${runWayVo.id}"  disabled="disabled"
																name="runWay-${runWayVo.id}" > </select> 
														</div>
													</div>
											</c:forEach>
								</div>
							</c:forEach>
			</div>
		</form>
		</div>
	</div>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	jQuery(function($) {
		seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox','selectbox'], function(A){
				$('#myTab2 a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
				});
				var runWayVoJsonList=${runWayVoJsonList};
				for(var i=0;i<runWayVoJsonList.length;i++){
					var runWayArray=runWayVoJsonList[i].runWayList;
					for(var j=0;j<runWayArray.length;j++){
					var	runWaycombobox = new A.combobox({
						render : "#runWay-"+runWayArray[j].id,
						datasource : ${runWayCombobox},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						initValue:'hotback',
					}).render();
					 	if(runWayArray[j].runWay){
					 		var runWay = runWayArray[j].runWay;
					 		if(runWay=="run"){
					 			$("#device-"+runWayArray[j].id).css("color","red");
					 		}else if(runWay=="hotback"){
					 			$("#device-"+runWayArray[j].id).css("color","green");
					 		}else{
					 			$("#device-"+runWayArray[j].id).css("color","blue");
					 		}
						 	runWaycombobox.setValue(runWayArray[j].runWay)
						}
					}
				}
		});
	});
	</script>
</body>
</html>