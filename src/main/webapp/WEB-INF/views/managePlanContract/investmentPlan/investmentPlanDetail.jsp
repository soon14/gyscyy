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
				<li class="active">投资计划</li>
				<li class="active">投资计划查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:10px;">
			<button id="btnBackDe"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"   style="margin-right:200px;margin-top:30px;" id="editForm">
				<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="20" value="${ entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目类别
				    </label>
				    <div class="col-md-4">
				     	<input class="col-md-12" id="category" name="category" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.categoryName }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  年份
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="yearNum" name="yearNum" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.yearNumString }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 股权结构(%)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="stockStruct" name="stockStruct" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.stockStruct }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设规模
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="buildSize" name="buildSize" type="text" placeholder="" maxlength="64" readonly="readonly"  value="${ entity.buildSize }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设规模量纲
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="buildSizeUnit" name="buildSizeUnit" type="text" placeholder="" maxlength="64" readonly="readonly" value="${ entity.buildSizeUnit }">
				    </div>
				</div>
			   <div class="form-group">
				    	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 建设阶段
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="buildStage" name="buildStage" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.buildStageName }">
					</div>
					  <label class="col-md-2 control-label no-padding-right">
						填写人
					  </label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" readonly="readonly" maxlength="64" value="${userName }">
                	</div>
				</div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 年度完成主要工程节点
				    </label>
				    <div class="col-md-10">
					    <textarea name="content" placeholder="" style="height:100px; resize:none;" class="col-md-12"  readonly="readonly" maxlength="512">${ entity.content }</textarea>
				    </div>
			     </div>
			     <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>小计
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalMoney" name="totalMoney" type="text" placeholder="" maxlength="64" readonly="readonly" value="${ entity.totalMoney }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 自有金额
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="ownMoney" name="ownMoney" type="text" placeholder="" readonly="readonly"  maxlength="64" value="${ entity.ownMoney }">
				    </div>	
				</div>
			   <div class="form-group">
				   
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>贷款
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="loanMoney" name="loanMoney" type="text" placeholder="" readonly="readonly"  maxlength="64" value="${ entity.loanMoney }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    其他
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="other" name="other" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.other }">
				    </div>	
				</div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly"   maxlength="128">${ entity.remark }</textarea>
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
<!--         		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--         			<i class="ace-icon fa fa-times"></i> -->
<!--         			取消 -->
<!--         		</button> -->
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
						fileId:'${entity.fileId}'==""?[]:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
        			
					
					//返回
					$("#btnBackDe").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/investmentPlan/index'));
					});
				});
			});
        </script>
    </body>
</html>