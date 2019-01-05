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
					招标采购
				</li>
				<li class="active">立项批复</li>
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
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>所属部门
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unitId" name="unitId" readonly="readonly" type="text" placeholder="" value="${ entity.unitName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>采购事项
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" readonly="readonly" type="text" placeholder="" value="${ entity.name }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>立项单位
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="departmentId" name="departmentId" readonly="readonly" type="text" placeholder="" value="${ entity.departmentName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>现估价(元)
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="judge" name="judge" readonly="readonly" type="text" placeholder="" value="${ entity.judge }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>采购方式
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="purchaseModeId" name="purchaseModeId" readonly="readonly" type="text" placeholder="" value="${ entity.purchaseModeName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>上传时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="updateDate" name="updateDate" readonly="readonly" type="text" placeholder="" value="${ entity.updateDateStr }">
                    </div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea readonly="readonly" name="remark" class="col-md-12" style="height:100px; resize:none;">${ entity.remark }</textarea>
                    </div>
               </div>
               <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
					<div class="col-md-10" id="divfile"></div>
               </div>
            </form>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.file},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/produceReply/index")
						});
    				});
				});
			});
        </script>
    </body>
</html>