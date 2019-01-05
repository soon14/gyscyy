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
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
<!-- 			   <div class="form-group"> -->
<!-- 			   		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 						<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="${ entity.code}"> --%>
<!--                     </div> -->
<!--                </div> -->
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>项目采购名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    采购计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.projectName }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 设备类别
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  规格型号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.amount }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预算单价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgetPrice" name="budgetPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.budgetPrice }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预算总价
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.totalPrice }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 预计采购时间
				    </label>
				    <div class="col-md-4">
				        <input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.buyTimeString }">
					</div>
				</div>
				
			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
                    </div>
    		     </div>
    		     <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>责任处室
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${unitEntity.name}">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyName" name="companyName" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12"  readonly="readonly" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
			    <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						附件
				    </label>
						<div class="col-md-10" id="divfile">
                       </div>
               </div>
			</form>
    		<div style="margin-right:100px;">
<!--         		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--         			<i class="ace-icon fa fa-floppy-o"></i> -->
<!--         			保存 -->
<!--         		</button> -->
        	</div>
        </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			
        			//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
        	
					
					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/yearPurchase/list/"+'/2')
						});
    				});
				});
			});
        </script>
    </body>
</html>