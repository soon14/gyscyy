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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editRCForm">
						    	<input type="hidden" id="id" name="id" value="${entity.id}" />
			
			    <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						检查人
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="checkPerson" name="checkPerson" type="hidden" placeholder="" maxlength="" value="${userEntity.id }" >				                      
	                   		<input class="col-md-12" id="checkPersonName" name="checkPersonName" type="text" placeholder="" maxlength="" value="${userEntity.name }" readonly="readonly">
<!-- 					   	<select id="checkPersonDiv" class="col-md-12 chosen-select" name="checkPerson" data-placeholder="请选择申请人" disabled="disabled" ></select>					                        -->
	                   
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						检查时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="checkDate" name="checkDate" type="text" placeholder="" maxlength=""  value="<fmt:formatDate value="${entity.checkDate }" type="both" pattern="yyyy-MM-dd HH:mm"/>" readonly="readonly">
                    </div>
               </div>
		       <div class="form-group">
					
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>检查结果
					</label>
					<div class="col-md-4">
					   	<select id="checkResultaddDiv" class="col-md-12 chosen-select" name="checkResult" data-placeholder="请选择检查结果"></select>					                       					
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作记录类型
					</label>
					<div class="col-md-4">
					   	<select id="typeaddDiv" class="col-md-12 chosen-select" name="type" data-placeholder="请选择工作记录类型"></select>					                       										
                	</div>
				   
               </div>
		       <div class="form-group">
					 <label class="col-md-2 control-label no-padding-right">
						意见和建议
				    </label>
				    <div class="col-md-10">
                	   <textarea placeholder="请输入意见和建议" name="suggest"  class="col-md-12" style="height:90px; resize:none;">${entity.suggest }</textarea>
						
                    </div>
               </div>
            </form>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
//         			var planPersonCombobox = new A.combobox({
// 						render: "#checkPersonDiv",
// 						datasource:${workPlanCombobox},
// 						allowBlank: true,
// 						initValue:"${entity.checkPerson}",
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
				var checkResultCombobox = new A.combobox({
					render: "#checkResultaddDiv",
					datasource:${runCheckCombobox},
					allowBlank: true,
					initValue:"${entity.checkResult}",
					options:{
						"disable_search_threshold":10
					}
				}).render();
				var typeCombobox = new A.combobox({
					render: "#typeaddDiv",
					datasource:${workRecordCombobox},
					allowBlank: true,
					initValue:"${entity.type}",
					options:{
						"disable_search_threshold":10
					}
				}).render();
					var id = $('#id').val();
					$('#editRCForm').validate({
						debug:true,
						rules: {
							checkResult:{ required:true},
							suggest:{ maxlength:100},
							checkDate:{required:true,date:true},
							type:{ required:true}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/runCheck/update/");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editRCForm").serializeObject();
							$("#checkPersonDiv").attr("disabled",false);
// 							obj.checkPerson=$("#checkPersonDiv").val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editRCForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>