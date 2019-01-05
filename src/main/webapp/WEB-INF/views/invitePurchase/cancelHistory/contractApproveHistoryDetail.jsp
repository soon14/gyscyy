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
				<li class="active">合同审批</li>
				<li class="active">历史</li>
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
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >合同审批信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>所属部门
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.unitName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同编号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.contractCode }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.itemName }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>供应商
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.supplierName }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同金额(元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${entity.money }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>采购方式
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${entity.purchaseModeName }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
					<div class="col-md-10" id="divfile"></div>
               </div>
            </form>
            
        <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >撤回信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
		       <div class="form-group" style="margin-top: 30px">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>撤回操作人员
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" type="text" placeholder="" readonly="readonly" value="${ entity.userName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>撤回时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" type="text" placeholder="" readonly="readonly" value="${ entity.cancelTimeStr }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						撤回原因
				    </label>
				    <div class="col-md-10">
						<textarea name="remark" class="col-md-12" readonly="readonly" style="height:100px; resize:none;">${ entity.cancelReason }</textarea>
                    </div>
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
							url : format_url("/cancelHistory/getHistoryList/3")
						});
    				});
				});
			});
        </script>
    </body>
</html>