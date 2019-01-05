<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					计划管理
				</li>
				<li class="active">运维检修类计划</li>
				<li class="active">年度生产物资计划</li>
				<li class="active">查看</li>
			</ul>
			</div>
			<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			<input id="id" name="id" value="${entity.id }" type="hidden">
			  <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" type="text" readonly="readonly" placeholder="" maxlength="20" value="${sysUnitEntity.name }">
<!-- 						<div id="unitIdDiv"></div> -->
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传人
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="userId" name="userId" type="text" readonly="readonly" placeholder="" maxlength="20" value="${sysUserEntity.name }">
<!-- 	                   <select class="col-md-12 chosen-select" id="userId" name="userId"></select> -->
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>物资名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="materialName" name="materialName" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.materialName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>物资规格
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="materialType" name="materialType" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.materialType }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>费用预算(万元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="charge" name="charge" readonly="readonly" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))" type="text" placeholder="" maxlength="20" value="${entity.charge }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>型号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="version" name="version" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.version }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="count" name="count" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.count }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>功能要求
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="functionRequirement" name="functionRequirement" readonly="readonly" type="text" placeholder="" maxlength="20" value="${entity.functionRequirement }">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark" class="col-md-12" readonly="readonly" style="height:90px; resize:none;">${entity.remark }</textarea>
                    </div>
               </div>
			</form>
        </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					$("#btnBack").on("click", function(){
						A.loadPage({
							render:  '#page-container',
							url : format_url("/annualProdutionPlan/list")
						});
    				});
				});
			});
        </script>
    </body>
</html>