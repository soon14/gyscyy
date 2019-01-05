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
														<label class="col-lg-6  control-label no-padding-right" name="runWay-${runWayVo.id}">${runWayVo.deviceName}</label>
														<div class=" col-lg-6">
														<select id="runWay-${runWayVo.id}" 
																name="runWay-${runWayVo.id}" > </select> 
														</div>
													</div>
											</c:forEach>
								</div>
							</c:forEach>
			</div>
		</form>
		</div>
			<div style="margin-top:20px;height: 50px">
		    			<button id="saveRunWayBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
				var runWayVoJsonList=${runWayVoJsonList};
				for(var i=0;i<runWayVoJsonList.length;i++){
					var runWayArray=runWayVoJsonList[i].runWayList;
					for(var j=0;j<runWayArray.length;j++){
						var equipCode = runWayArray[j].equipCode
						var lastTwoLetter = equipCode.substr(equipCode.length-2,equipCode.length);
						var runWaycombobox;
						if(lastTwoLetter=="FJ"){
							runWaycombobox = new A.combobox({
									render : "#runWay-"+runWayArray[j].id,
									datasource : ${runWayComboboxForWind},
									allowBlank: true,
									options : {
										"disable_search_threshold" : 10
									},
									initValue:'hotback',
							}).render();
						}else{
							runWaycombobox = new A.combobox({
								render : "#runWay-"+runWayArray[j].id,
								datasource : ${runWayCombobox},
								allowBlank: true,
								options : {
									"disable_search_threshold" : 10
								},
								initValue:'hotback',
							}).render();
						}
						
						 var name = runWayArray[j].id;
						 $("select[name='runWay-"+name+"']").on("change",function(event){
								var runWay = $(this).val();
								var attrName = $(this).attr("name");
								if(runWay=="run"){
						 			$("label[name='"+attrName+"']").css("color","red");
						 		}else if(runWay=="overhaul"){
						 			$("label[name='"+attrName+"']").css("color","blue");
						 		}else {
						 			$("label[name='"+attrName+"']").css("color","green");
						 		}
							})
						if(runWayArray[j].runWay){
							var runWay = runWayArray[j].runWay;
					 		if(runWay=="run"){
					 			$("label[name='runWay-"+runWayArray[j].id+"']").css("color","red");
					 		}else if(runWay=="hotback"){
					 			$("label[name='runWay-"+runWayArray[j].id+"']").css("color","green");
					 		}else{
					 			$("label[name='runWay-"+runWayArray[j].id+"']").css("color","blue");
					 		}
							runWaycombobox.setValue(runWayArray[j].runWay)
						}
					}
				}
				$('#addRunWayForm').validate({
					debug:true,
					rules:   {},
					submitHandler: function (form) {
						//添加按钮
						var url = format_url("/runWay/save");
						//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
						var obj = $("#addRunWayForm").serializeObject();
						$.ajax({
							url : url,
							contentType : 'application/x-www-form-urlencoded; charset=UTF-8;',
							dataType : 'JSON',
							data : {runWay:JSON.stringify(obj)},
							cache: false,
							type : 'POST',
							success: function(result){
								alert('保存成功');
							},
							error:function(v,n){
								alert('保存失败');
							}
						});
					}
				});
				$("#saveRunWayBtn").on("click", function(){
					$("#addRunWayForm").submit();
				});
		});
	});
	</script>
</body>
</html>