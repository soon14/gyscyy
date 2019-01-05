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
			<form id="unitAddForm" class="form-horizontal" role="form" style="margin-right:100px;">
				<div class="form-group">
					<div class="col-md-4">
						<input placeholder="" id="parentId"  name="parentId" type="hidden" class="col-md-12" value="${parentId}">					
						<input placeholder="" id="pathCode"  name="pathCode" type="hidden" class="col-md-12" value="${pathCode}">					
						<input placeholder="" id="unitId"  name="unitId" type="hidden" class="col-md-12" value="${unitId}">					
						<input placeholder="" id="equipId"  name="equipId" type="hidden" class="col-md-12" value="1">					
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
					<div class="col-md-4">
						<input placeholder="" id="name"  name="name" type="text" class="col-md-12">
					</div>									
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
					<div class="col-md-4">
						<input placeholder="" id="code"  name="code" type="text" class="col-md-12">
					</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>组织机构</label> -->
<!-- 					<div class="col-md-4"> -->
<!-- 						<div id="sysUnitTree" ></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="form-group">
					 <label class="col-md-2 control-label no-padding-right">备注</label>
                    <div class="col-md-10">
						<textarea class="col-md-12" id="remark"  name="remark" style="height:80px; resize:none;"  maxlength="500"></textarea>
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
    	$('#unitAddForm').validate({
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
					required: true
				}
			},
			submitHandler: function (form) {
				var url =format_url("/equiptree/isAdd");
				var obj = $("#unitAddForm").serializeObject();
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
							alert('添加成功');
							unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
						}else{
								alert("逻辑设备已存在!");
						}
					},	
					error:function(v,n){
						alert('添加失败');
					}
				});
			}
		});
	});
});
</script>
</body>
</html>