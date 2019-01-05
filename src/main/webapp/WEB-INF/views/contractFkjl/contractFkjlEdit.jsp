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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
				<input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
				<input  type="hidden" id="contractManageID" value="${entity.contractManageID}" name="contractManageID" />
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>未付合同金额（元）
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="wfhtje" name="wfhtje" type="text" readonly="readonly" maxlength="64" value="${entity.wfhtje }">
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>第几次付款
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="xh" name="xh" type="text" readonly="readonly" maxlength="32" value="${entity.xh }">
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>付款时间
                    </label>
					<div class="col-md-9">
						<div id="fksjEditDiv"></div>
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>付款金额
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="fkje" name="fkje" type="text" placeholder="" maxlength="64" value="${entity.fkje }">
                    </div>
                </div>
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					//日期组件
					var fksjEditPicker = new A.my97datepicker({
						id: "fksjIdEdit",
						name:"fksj",
						render:"#fksjEditDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					fksjEditPicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd" value="${entity.fksj}" type="both"/>');
					
					
					$('#editForm').validate({
						debug:true,
						rules: {
							wfhtje:{required:true,maxlength:64,number:true},
							xh:{required:true,maxlength:2,digits:true},
							fksj:{required:true,maxlength:64},
							fkje:{required:true,maxlength:64,number:true},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/contractFkjl/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.status=1;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFkjlDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
						var wfhtje=$("#wfhtje").val();
						var fkje=$("#fkje").val();
						if(parseFloat(fkje)<=parseFloat(0)){
							alert("付款金额必须大于0!");
							return;
						}
						
						if(parseFloat(fkje)>parseFloat(wfhtje)){
							alert("付款金额不能大于未付款金额!");
							return;
						}
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>