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
				<li class="active">安全计划</li>
				<li class="active">安全技术措施计划</li>
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
						<span style="color:red;">*</span>生产单位
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.unitName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>分类
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" readonly="readonly" type="text" placeholder="" value="${ entity.classifyName }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>内容
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="content" name="content" readonly="readonly" type="text" placeholder="" value="${ entity.content }">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>费用预算(万元)
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="money" name="money" type="text" readonly="readonly" placeholder="" value="${ entity.money }">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="endTime" name="endTime" type="text" readonly="readonly" placeholder="" value="${ entity.endTime }">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						备注
				    </label>
				    <div class="col-md-10">
						<textarea placeholder="请输入备注" name="remark" class="col-md-12" readonly="readonly" style="height:100px; resize:none;">${ entity.remark }</textarea>
                    </div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						附件 -->
<!-- 				    </label> -->
<!-- 					<div class="col-md-10" id="divfile"></div> -->
<!--                </div> -->

            </form>
<!--     		<div style="margin-right:100px;"> -->
<!--     			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--     				<i class="ace-icon fa fa-floppy-o"></i> -->
<!--     				保存 -->
<!--     			</button> -->
<!--     		</div> -->
		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					
					//附件上传
// 					var uploaddropzone =new A.uploaddropzone({
// 						render : "#divfile",
// 						fileId:[],
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 					}).render();

					$("#btnBack").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/safeStep/index")
						});
    				});
				});
			});
        </script>
    </body>
</html>