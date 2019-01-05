<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="row" >
			<form id="unitEditForm" class="form-horizontal" role="form" style="margin-right:100px;">
				<div class="form-group">
					<div class="col-md-4">
						<input placeholder="" id="parentId"  name="parentId" type="hidden" class="col-md-12" value="${equipTreeEntity.parentId}">					
						<input placeholder="" id="pathCode"  name="pathCode" type="hidden" class="col-md-12" value="${equipTreeEntity.pathCode}">	
						<input placeholder="" id="unitId"  name="unitId" type="hidden" class="col-md-12" value="${equipTreeEntity.unitId}">	
						<input class="col-md-12" id="id" name="id" type="hidden" value="${equipTreeEntity.id}">				
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
					<div class="col-md-4">
						<input placeholder="" id="name"  name="name" type="text" class="col-md-12" value="${equipTreeEntity.name}">
					</div>									
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
					<div class="col-md-4">
						<input placeholder="" id="code"  name="code" type="text" class="col-md-12" value="${equipTreeEntity.code}">
					</div>
				</div>
				<div class="form-group">
					 <label class="col-md-2 control-label no-padding-right">备注</label>
                    <div class="col-md-10">
						<textarea class="col-md-12" id="remark"  name="remark" style="height:80px; resize:none;"  maxlength="500">${equipTreeEntity.remark}</textarea>
                    </div>							
				</div>
				<div class="form-group">
				</div>					
				<div class="form-group">
    				<div style="margin-right:0px;margin-top: 1%">
    					<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;float: right;">
    					<i class="ace-icon fa fa-floppy-o"></i>
    					保存
    					</button>
    				</div>
				</div>	
			</form>
		</div>
<script type="text/javascript">
jQuery(function($) {
	seajs.use(['combobox','combotree'], function(A){
    	$('#unitEditForm').validate({
    		debug: true,
			rules: {
				name: {
					required: true,
					maxlength: 30
				},
				code: {
					required: true,
					maxlength: 30
				},
				unitId: {
					required: true,
					maxlength: 30
				}
			},
			submitHandler: function (form) {
				var id = ${id};
				var url =format_url("/equiptree/isEdit/"+id);
				var obj = $("#unitEditForm").serializeObject();
				var params = {};
				params.equipTreeInfo = obj;
				$.ajax({
					url : url,
					contentType : 'application/json',
					dataType : 'JSON',
					data : JSON.stringify(params),
					cache: false,
					type : 'POST',
					success: function(result){
						if(result.result=='success'){
							alert('修改成功');
							unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
						}else{
							alert("逻辑设备已存在!");
						}
					},
					error:function(v,n){
						alert('修改失败');
					}
				});
			}
		});
	});
});
</script>
</body>
</html>