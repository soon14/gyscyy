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
           <form class="form-horizontal " role="form"  style="margin-right:100px;margin-top:30px;" id="techDataUploadFileForm">
				<div class="col-md-12" id="fileUpload">
					<div class="form-group">
						<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>文件上传</label>
						<div class="col-md-10">
							<input id="testFile" name="file"  type="file" class="col-md-3">
						</div>
					</div>
				</div>
				<input id="fileUrl" name="fileUrl"  type="hidden" class="col-md-3">
				<input id="fileName" name="fileName"  type="hidden" class="col-md-3">
				
			</form>
            <div class="col-md-12"  style="margin-right:100px;margin-top:60px;">
    			<button id="cancelBtn" class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
        </div>
        <script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','combobox','combotree','uploadify','my97datepicker'], function(A){
						var testFileUploader = new A.uploadify({
							render : "#testFile",
							name : "file",
							url : format_url("/file/upload"),
							type : "excel",
							options : {
								droppable : true,
								thumbnail : false
							},
							callback : function(result) {
								if(result.result =="success"){
									$("#fileName").val(result.name);
									$("#fileUrl").val(result.url);
								}else{
									alert("上传失败");
								}
								
							}
						}).render();
						$('#techDataUploadFileForm').validate({
							debug:true,
							rules:  {
								},
							submitHandler: function (form) {
								//添加按钮
								var fileName = $('#fileName').val();
								var fileUrl = $('#fileUrl').val();
								if(fileName==null || fileName==""){
									alert("请选择上传文件");
									return;
								}
								var obj = $("#techDataUploadFileForm").serializeObject();
								var url = format_url("/annualMaintenancePlan/importAnnualMaintenancePlan?fileName="+fileName+"&fileUrl="+fileUrl);
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										if(result.result=="success"){
											 alert('导入成功');
											 workSafeOneDialog.close();
											 $("#page-container").load(format_url('/annualMaintenancePlan/list'));
										}else if(result.result == "error"){
											workSafeOneDialog.close();
											alert(result.errorMsg);
										}else{
											alert('导入失败');
										}
										
									},
									error:function(v,n){
										alert('导入失败');
									}
								});
							}
						});
						//保存按钮
						$("#saveBtn").on("click", function(){
							$("#techDataUploadFileForm").submit();
	    				});
				});
			});
			</script>
    </body>
</html>