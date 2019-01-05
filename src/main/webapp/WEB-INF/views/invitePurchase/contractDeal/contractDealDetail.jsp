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
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="javascript:void(0);" onclick="firstPage()">首页</a>
				</li>
				<li>
					招标采购
				</li>
				<li class="active">合同签订</li>
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
						<span style="color:red;">*</span>签订合同名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.name }">
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
						<span style="color:red;">*</span>签约时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.dealTimeStr }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>存档时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.createTimeStr }">
                	</div>
               </div>
               <div class="form-group">
                <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行开始时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="startDate" name="startDate" readonly="readonly" type="text" placeholder="" value='<fmt:formatDate  pattern="yyyy-MM-dd" value="${entity.startDate}" type="date"/>'>
                	</div>
                <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
						合同年限
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="contractLife" name="contractLife" readonly="readonly" type="text" placeholder="" value="${ entity.contractLife}年">
                    </div>
                   
               </div>
               <div class="form-group">
               <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>合同履行结束时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="endDate" name="endDate" readonly="readonly" type="text" placeholder="" value="${ entity.endDate}">
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
					var appData = ${entityJson};
					
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
							url : format_url("/contractDeal/index")
						});
    				});
				});
			});
        </script>
    </body>
</html>