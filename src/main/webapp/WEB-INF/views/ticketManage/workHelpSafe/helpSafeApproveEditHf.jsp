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
    	
		<div class="col-md-12" >
		<form class="form-horizontal" role="form" style="margin-right:100px;" id="workSafeEditForm">
		        <input type="hidden" id="id" name="id" value="${workSafeEntity.id}" />
		        <input type="hidden"  name="workticketId" value="${workSafeEntity.workticketId}" />
		        <input type="hidden"  name="uuidCode" value="${workSafeEntity.uuidCode}" />
		        <input type="hidden"  name="executeSituation" value="${workSafeEntity.executeSituation}" />
		        <input type="hidden"  name="executeDate" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${workSafeEntity.executeDate}" type="date"/>'  />
								<input class="col-md-12" id="orderSeq" name="orderSeq" type="hidden"   maxlength="20" value="${workSafeEntity.orderSeq}">
								<div class="form-group">
									
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行情况</label>
									<div class="col-md-4">
											<select id="hfSituation" name="hfSituation"  class="form-control chosen-select" style="width:200px;"></select>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>执行时间</label>
									<div class="col-md-4">
										<div id="approveHfsjDiv"></div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>安全措施</label>
									<div class="col-md-10">
										<textarea id="signerContent" name="signerContent" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workSafeEntity.signerContent}</textarea>
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
					var approveEndtimeDatePicker = new A.my97datepicker({
						id: "approveHfsjId",
						name:"hfDate",
						render:"#approveHfsjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					//状态下拉框
					var zxqkCombobox = new A.combobox({
						render: '#hfSituation',
						datasource:${hfSituation},
						allowBlank:true,
						options:{
							"disable_search_threshold":10,
							"placeholder_text_single" : "请选择"
						}
					}).render();
					zxqkCombobox.setValue('${workSafeEntity.hfSituation}');
					
					var flag=${flag};
						$('#workSafeEditForm').validate({
							debug:true,
							rules: {
								hfSituation:{required:true,maxlength:60},
								hfDate:{required:true}
								},
							submitHandler: function (form) {
								var id = ${workSafeEntity.id};
								//添加按钮
								var url = format_url("/workSafe/"+id);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#workSafeEditForm").serializeObject();
								obj.safeType=${flag};
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
    					$("#workSafeEditForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>