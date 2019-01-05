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
					计划经营管理
				</li>
				<li class="active">设备物资采购计划</li>
				<li class="active">年度采购</li>
				<li class="active">年度实际采购信息</li>
				<li class="active">查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style=" margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
			 <input class="col-md-12" id="id" name="id" type="hidden"  value="${entity.id }" >
<!-- 			   <div class="form-group"> -->
<!-- 			   		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 						<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value=""> -->
<!--                     </div> -->
<!--                </div> -->
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备类别
					</label>
					<div class="col-md-4">
					   <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">	
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>规格型号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>实际采购时间
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.realBuyTimeStr }">
                    </div>
               </div>
		       <div class="form-group">
		       		<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="amount" name="amount" type="text" placeholder="0" readonly="readonly" maxlength="64" value="${ entity.amount }" >
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>实际单价(万元)
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="budgetPrice" name="budgetPrice" type="text" placeholder="0" readonly="readonly" maxlength="64" value="${ entity.budgetPrice }" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>实际总价(万元)
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="0" maxlength="64" value="${ entity.totalPrice }"  readonly="readonly" >
                	</div>
               </div>
            </form>
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var yearPurchaseId = ${yearPurchaseId};
					
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/yearPurchase/yearPurchaseReal/"+yearPurchaseId)
						});
    				});
				});
			});
			
        </script>
    </body>
</html>