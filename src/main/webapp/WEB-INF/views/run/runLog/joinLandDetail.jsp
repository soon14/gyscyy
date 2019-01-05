<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
			<div class="tabbable" style="margin-top:20px;">
			<ul class="nav nav-tabs" id="myTab2">
				<c:set var="temp" value="1"></c:set>
				<c:forEach items="${joinLandVoList}" var="unitList" varStatus="vs">
						<li <c:if test="${vs.index==0}"> class="active" </c:if>
						<c:if test="${vs.index!=0}"> class="" </c:if>
					>
					<a data-toggle="tab" href="#${temp}Div" aria-expanded="true">
						<i class="green ace-icon fa fa-columns bigger-120"></i>
						${unitList.sysUnitEntity.name}
					</a>
					<c:set var="temp" value="${temp+1}"></c:set>
					</li>
				</c:forEach>
			</ul>
		<form id="addJoinLandForm" class="form-horizontal" role="form">
			<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 600px">
							<c:set var="temp" value="1"></c:set>
							<c:forEach items="${joinLandVoList}" var="unitList" varStatus="vs">
								<div id="${temp}Div" <c:if test="${vs.index==0}"> class="tab-pane fade active in" </c:if> 
									<c:if test="${vs.index!=0}"> class="tab-pane fade" </c:if>  >
											<c:forEach items="${unitList.joinLandList}" var="JoinLandEntity" varStatus="vs2">
												 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 " >
														<label class="col-lg-6  control-label no-padding-right" name="joinLand-${JoinLandEntity.id}">${JoinLandEntity.equipName}</label>
														<div class=" col-lg-6">
														<select id="joinLand-${JoinLandEntity.id}" disabled="disabled"
																name="joinLand-${JoinLandEntity.id}" > </select> 
														</div>
													</div>
											</c:forEach>
								</div>
								<c:set var="temp" value="${temp+1}"></c:set>
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
				var joinLandVoJsonList=${joinLandVoJsonList};
				for(var i=0;i<joinLandVoJsonList.length;i++){
					var joinLandArray=joinLandVoJsonList[i].joinLandList;
					for(var j=0;j<joinLandArray.length;j++){
						 var swordbrake_status =  joinLandArray[j].swordbrake_status;
						 var joinLandcombobox = new A.combobox({
							 render : "#joinLand-"+joinLandArray[j].id,
							 datasource : ${joinLandCombobox},
							 allowBlank: true,
							 options : {
								"disable_search_threshold" : 10
							 },
							 initValue:swordbrake_status,
						}).render();
						if(swordbrake_status){
							if(swordbrake_status==1){
								$("label[name='joinLand-"+joinLandArray[j].id+"']").css("color","green");
							}else if(swordbrake_status==2){
								$("label[name='joinLand-"+joinLandArray[j].id+"']").css("color","red");
							}
						}
						joinLandcombobox.setValue(joinLandArray[j].swordbrake_status);
					}
				}
		});
	});
	</script>
    </body>
</html>