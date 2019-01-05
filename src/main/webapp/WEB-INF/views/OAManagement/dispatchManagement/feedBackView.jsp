<%@ page language="java" contentType="text/html; charset=utf-8"  
	pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
	</head>
	<body>
		<div class="no-padding-right; " >
			<div class="row" style="height:300px;overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="viewForm">
						<input class="col-md-12" id="id"  value="${dispatchFeedbackEntity.id}" type="hidden">
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">反馈信息：</label>
							</div>
							<div class="col-md-10">
								<textarea name="feedBackComment" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${dispatchFeedbackEntity.feedbackComment}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">反馈附件：</label>
							</div>
							<div class="col-md-10" id="feedbackFile"></div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>反馈人：</label>
							</div>
							<div class="col-md-4">
								<input class="col-md-12" name="feedBackPersionName"  type="text" value="${dispatchFeedbackEntity.feedbackPersionName }" readonly>
								<input class="col-md-12" name="feedBackPersion"  type="hidden" value="${drafterId}" readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>反馈时间：</label>
							</div>
							<div class="col-md-4">
								<input  class="col-md-12"  name="feedBackTime" type="text" value="${dispatchFeedbackEntity.feedbackTime}" readonly>
							</div>
						</div>
					</div>
			</form>
			<div style="margin-right:20px;margin-top:5px;">
				<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					关闭
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){

					//附件
					 var appendixUploaddropzone =new A.uploaddropzone({
						render : "#feedbackFile",
						fileId:'${dispatchFeedbackEntity.feedBackFile}'==""?[]:${dispatchFeedbackEntity.feedBackFile},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();


				});
			});
		</script>
	</body>
</html>