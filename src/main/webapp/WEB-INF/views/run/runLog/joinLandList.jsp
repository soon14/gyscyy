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
								<div id="${temp}Div" <c:if test="${vs.index==0}"> class="tab-pane fade  active in" </c:if> 
									<c:if test="${vs.index!=0}"> class="tab-pane fade" </c:if>  >
											<c:forEach items="${unitList.joinLandList}" var="JoinLandEntity" varStatus="vs2">
												 <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 " >
														<label class="col-lg-8  control-label no-padding-right" name="joinLand-${JoinLandEntity.id}">${JoinLandEntity.equipName}</label>
														<div class=" col-lg-4">
														<select id="joinLand-${JoinLandEntity.id}" 
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
			<div style="margin-top:20px;height: 50px">
		    			<button id="saveJoinLandBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				保存
		    			</button>
		    		</div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
	jQuery(function($) {
		seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker','duallistbox','selectbox'], function(A){
				$('#myTab2 a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
				});
				var joinLandVoJsonList=${joinLandVoJsonList};
				var joinLandcombobox ;
				for(var i=0;i<joinLandVoJsonList.length;i++){
					var joinLandArray=joinLandVoJsonList[i].joinLandList;
					for(var j=0;j<joinLandArray.length;j++){
						var swordbrake_status =  joinLandArray[j].swordbrake_status;
						joinLandcombobox = new A.combobox({
							render : "#joinLand-"+joinLandArray[j].id,
							datasource : ${joinLandCombobox},
							allowBlank: true,
							options : {
								"disable_search_threshold" : 10
							},
							initValue:swordbrake_status
						}).render();
						var name = joinLandArray[j].id;
						$("select[name='joinLand-"+name+"']").on("change",function(event){
							var v = $(this).val();
							var attrName = $(this).attr("name");
							if(v==1){
								$("label[name='"+attrName+"']").css("color","green");
							}else if(v==2){
								$("label[name='"+attrName+"']").css("color","red");
							}
						})
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
				$('#addJoinLandForm').validate({
					debug:true,
					rules:   {},
					submitHandler: function (form) {
						//添加按钮
						var url = format_url("/joinLand/save");
						//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
						var obj = $("#addJoinLandForm").serializeObject();
						$.ajax({
							url : url,
// 							contentType : 'application/json',
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							dataType : 'JSON',
							data : {joinLand:JSON.stringify(obj)},
							cache: false,
							type : 'POST',
							success: function(result){
								alert('保存成功');
// 								 $("#page-container").load(format_url('/defect/index'));
							},
							error:function(v,n){
								alert('保存失败');
							}
						});
					}
				});
				$("#saveJoinLandBtn").on("click", function(){
					$("#addJoinLandForm").submit();
				});
		});
	});
	</script>
    </body>
</html>