<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
    	
		<div class="col-md-12" >
		<form class="form-horizontal" role="form" style="margin-right:100px;" id="helpSafeAqcsEditForm">
		        				<input type="hidden" id="id" name="id" value="${workSafeEntity.id}" />
		        				<input type="hidden"  name="uuidCode" value="${workSafeEntity.uuidCode}" />
		        				<input type="hidden"  name="workticketId" value="${workSafeEntity.workticketId}" />
		        				<input type="hidden"  name="orderSeq" value="${workSafeEntity.orderSeq}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行情况</label>
									<div class="col-md-4">
											<select id="executeSituation" name="executeSituation"  class="form-control chosen-select" style="width:200px;"></select>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复情况</label>
									<div class="col-md-4">
											<select id="hfSituation" name="hfSituation"  class="form-control chosen-select" style="width:200px;"></select>
									</div>
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行时间</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="aqcsStarttimeDiv"></div> -->
<!-- 									</div> -->
								</div>
<!-- 								<div class="form-group"> -->
									
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复情况</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 											<select id="hfSituation" name="hfSituation"  class="form-control chosen-select" style="width:200px;"></select> -->
<!-- 									</div> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>恢复时间</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="aqcsEndtimeDiv"></div> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>操作项目</label>
									<div class="col-md-10">
										<textarea id="signerContent" name="signerContent"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workSafeEntity.signerContent}</textarea>
									</div>
								</div>
				</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtnSafe" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//日期组件
					var aqcsStarttimeDatePicker = new A.my97datepicker({
						id: "approveStarttimeId",
						name:"executeDate",
						render:"#aqcsStarttimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					aqcsStarttimeDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${workSafeEntity.executeDate}" type="both"/>');
					//日期组件
					var aqcsEndtimeDatePicker = new A.my97datepicker({
						id: "approveEndtimeId",
						name:"hfDate",
						render:"#aqcsEndtimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					aqcsEndtimeDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${workSafeEntity.hfDate}" type="both"/>');
					//状态下拉框
					var zxqkCombobox = new A.combobox({
						render: '#executeSituation',
						datasource:${executeSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					zxqkCombobox.setValue('${workSafeEntity.executeSituation}');
					//状态下拉框
					var zxqkhfCombobox = new A.combobox({
						render: '#hfSituation',
						datasource:${hfSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					zxqkhfCombobox.setValue('${workSafeEntity.hfSituation}');
						$('#helpSafeAqcsEditForm').validate({
							debug:true,
							rules: {
								executeSituation:{required:true,maxlength:60},
// 								executeDate:{required:true},
// 								hfDate:{required:true},
								hfSituation:{required:true,maxlength:60},
								signerContent:{required:true,maxlength:128}
								},
							submitHandler: function (form) {
								var id = ${workSafeEntity.id};
								//添加按钮
								var url = format_url("/workSafe/"+id);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#helpSafeAqcsEditForm").serializeObject();
								obj.safeType=15;
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('操作成功');
										workSafeOneDialog.close();
									},
									error:function(v,n){
										alert('操作失败');
									}
								});
							}
						});
					
					
					$("#editBtnSafe").on("click", function(){
    					$("#helpSafeAqcsEditForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>