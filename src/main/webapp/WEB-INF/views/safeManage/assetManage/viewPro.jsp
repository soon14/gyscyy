<%@ page language="java" contentType="text/html; charset=utf-8"  
	pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
	</head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
<input class="col-md-12" id="editType" name="type"  value="${assetManagementEntity.type}" type="hidden">
				<input class="col-md-12" id="id" name="id"  value="${assetManagementEntity.id}" type="hidden">
				<input class="col-md-12" id="editYearNumValue" name="year"  value="${assetManagementEntity.year}" type="hidden">
				<input class="col-md-12" id="editTimeValue" name="time"  value="${assetManagementEntity.time}" type="hidden">
				<input class="col-md-12" id="editDefualtTypeValue" name="defualtType"  value="${assetManagementEntity.defualtType}" type="hidden">
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" readonly="readonly" name="code" type="text"  placeholder="" maxlength="20" value="${assetManagementEntity.code }">
                    </div>
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="createrName" readonly="readonly" name="createrName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
						<input class="col-md-12" id="creater" name="creater" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id }">
                    </div>
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>证书
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="certificate"  name="certificate" readonly="readonly" type="text" placeholder="" maxlength="20" value="${assetManagementEntity.certificate }">
                    </div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>有效期</label>
					<div class="col-md-4">
						<input class="col-md-12" id="editName" name="name" value ="${assetManagementEntity.time}" type="text" maxlength="64" readonly>
					</div>
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label> 
				<div class="col-md-4"> 
 						<input class="col-md-12" id="assetName" name="assetName" readonly="readonly" type="text" placeholder="" maxlength="64" value="${assetManagementEntity.assetName }">
					</div> 
				<label class="col-md-2 control-label no-padding-right">单位</label> 
				<div class="col-md-4"> 
 						<input class="col-md-12" id="unitName" name="unitName" readonly="readonly" type="text" placeholder="" maxlength="64" value="${sysUnitEntity.name }">
					</div> 
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>姓名</label> 
					<div class="col-md-4"> 
 						<input class="col-md-12" id="name" name="name" readonly="readonly" type="text" placeholder="" maxlength="64" value="${assetManagementEntity.name }">
					</div> 
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>附件</label>
					<div class="col-md-10" id="divfile"></div>
				</div>
			</form>
			<div style="margin-right:100px;">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					取消
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${assetManagementEntity.appendix}'==""?[]:${assetManagementEntity.appendix},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
				});
			});
		</script>
	</body>
</html>