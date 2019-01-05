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
			<div class="row" style="height:790px;overflow-y:scroll;overflow-x:hidden;padding:0px" >
				<form class="form-horizontal" role="form"  style="margin-right:200px;" id="feedBackForm">
						<input class="col-md-12" id="id"  value="${dispatchManagementEntity.id}" type="hidden">
						<input class="col-md-12" id="viewDrafterTimeValue" type="hidden" value="${drafterTime}" >
						<input class="col-md-12" id="viewRemindTimeValue" type="hidden" value="${dispatchManagementEntity.feedBackRemindTime}" >
						<input class="col-md-12" id="viewDispatchNumPreVal" type="hidden" value="${dispatchNumPreVal}" >
						<input class="col-md-12" id="viewDispatchNumYearVal" type="hidden" value="${dispatchNumYearVal}" >
						<input class="col-md-12" id="viewDispatchNumNoVal" type="hidden" value="${dispatchNumNo}" >
						<input class="col-md-12" id="viewRecipientIdVal" type="hidden" value="${dispatchManagementEntity.recipientId}" >
						
						<div class="form-group">
							<label class="col-md-12 control-label no-padding-right" style="text-align:center;font-size:12pt">中国电建集团贵阳勘测设计研究院有限公司发文处理单</label> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label "><span style="color:red;">*</span>标题：</label> 
							</div>
							<div class="col-md-10" > 
		 						<input class="col-md-12" id="viewTitle" name="title" type="text" value="${dispatchManagementEntity.title}" readonly>
							</div> 
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">发文字号：</label> 
							</div>
							<div class="col-md-10"> 
		 						<input  id="viewDispatchNum" type="text" name= "dispatchName" class="col-md-12" value="${dispatchManagementEntity.dispatchName}"  readonly>
		 					</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文类型：</label> 
							</div>
							<div class="col-md-4"> 
		 							<input  id="viewDispatchType"  class="col-md-12"  type="text" value="${dispatchManagementEntity.typeCN}" readonly>
							</div> 
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>发文部门：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewDispatchDepartment" class="col-md-12" type="text"  value="${dispatchManagementEntity.departmentName}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">缓急程度：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewEmergencyLevel" type="text" class="col-md-12"  value="${dispatchManagementEntity.emergencyLevelCN}"  readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">密级：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewSecurityLelvel" type="text" class="col-md-12" name= "dispatchNameNum" value="${dispatchManagementEntity.securityLelvelCN}"  readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿人：</label>
							</div>
							<div class="col-md-4">
								<input class="col-md-12" id="viewDrafterName"  type="text" value="${dispatchManagementEntity.drafterName }" readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>拟稿时间：</label>
							</div>
							<div class="col-md-4">
								<input  class="col-md-12"  id="viewDispatchTime" type="text" value="${dispatchManagementEntity.draftTime}" readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"> 
										<input name="feedBack" type="radio" value="1" class="ace"/> 
										&nbsp;<span class="lbl bigger-100">不需要反馈</span>
								</label>
							</div>
							<div class="col-md-3">
								<label class="control-label no-padding-right"> 
									<input name="feedBack" type="radio" value="2" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100">需要反馈</span>
								</label>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;"   id="addFeedBackRemindTimeNeed">*</span>反馈提醒时间：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewFeedBackRemindTime" type="text" style="width: 100%;"  value="${dispatchManagementEntity.feedBackRemindTime}"  readonly>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-3 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"> 
											<input name="releaseStatus" type="radio" value="1" class="ace"/> 
											&nbsp;<span class="lbl bigger-100">全部挂网&nbsp;&nbsp;&nbsp;</span>
									</label>
							</div>
							<div class="col-md-3">
								<label class="control-label no-padding-right"> 
										<input name="releaseStatus" type="radio" value="2" class="ace input" /> 
										&nbsp;<span class="lbl bigger-100">特定接收人</span>
									</label>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;" id="addRecipientNeed">*</span>接收人：</label>
							</div>
							<div class="col-md-4">
								<input  id="viewRecipient" class="col-md-12 no-padding-right" type="text"  value="${dispatchManagementEntity.recipientName}" readonly>
							</div>
						</div>
						<!-- <div class="form-group" style="display:none">
							<div class="col-md-2 no-padding-right ">
								<label class="col-md-12 control-label no-padding-right">公文正文：</label>
							</div>
							<div class="col-md-10 no-padding-right ">
								<div id="documentText"></div>
							</div>
						</div> -->
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">附件：</label>
							</div>
							<div class="col-md-10" id="appendixFile"></div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">备注：</label>
							</div>
							<div class="col-md-10">
								<textarea name="remarks" style="height:80px; resize:none;" class="col-md-12" maxlength="255" readonly>${dispatchManagementEntity.remarks}</textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right">反馈信息：</label>
							</div>
							<div class="col-md-10">
								<textarea name="feedBackComment" placeholder="请输入反馈信息" style="height:80px; resize:none;" class="col-md-12" maxlength="255" ></textarea>
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
								<input class="col-md-12" name="feedBackPersionName"  type="text" value="${drafterName }" readonly>
								<input class="col-md-12" name="feedBackPersion"  type="hidden" value="${drafterId}" readonly>
							</div>
							<div class="col-md-2 no-padding-right; ">
								<label class="col-md-12 control-label no-padding-right"><span style="color:red;">*</span>反馈时间：</label>
							</div>
							<div class="col-md-4">
								<input  class="col-md-12"  name="feedBackTime" type="text" value="${feebackTime}" readonly>
							</div>
						</div>
				</form>
			</div>
			<div style="margin-right:200px;margin-top:10px;">
				<button id="closeBtn"  class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
					<i class="ace-icon fa fa-times"></i>
					关闭
				</button>
				<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
					<i class="ace-icon fa fa-floppy-o"></i>
					提交
				</button>
			</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					//是否需要反馈 ${dispatchManagementEntity.feedBack}
					$('input[name="feedBack"]').attr("disabled", true);
					$('input[name="feedBack"][value="' + ${dispatchManagementEntity.feedBack} + '"]').attr("checked",true);
					var feedbackVal = $('input[name="feedBack"]:checked').val();
					if ("2"== feedbackVal) {
						$('#addFeedBackRemindTimeNeed').show();
					} else {
						$('#addFeedBackRemindTimeNeed').hide();
					}
					//全部挂网/特定接收人
					$('input[name="releaseStatus"]').attr("disabled", true);
					$('input[name="releaseStatus"][value="' + ${dispatchManagementEntity.releaseStatus} + '"]').attr("checked",true);
					var releaseVal = $('input[name="releaseStatus"]:checked').val();
					if ("2"== releaseVal) {
						$('#addRecipientNeed').show();
					} else {
						$('#addRecipientNeed').hide();
					}
					/* //发文正文
					var contentWysiwyg = new A.wysiwyg({
						render : "#documentText",
						options:{
							height:"300px",
							toolbar:[
										'font',
										null,
										'fontSize',
										null,
										{name:'bold', className:'btn-info'},
										{name:'italic', className:'btn-info'},
										{name:'strikethrough', className:'btn-info'},
										{name:'underline', className:'btn-info'},
										null,
										{name:'insertunorderedlist', className:'btn-success'},
										{name:'insertorderedlist', className:'btn-success'},
										{name:'outdent', className:'btn-purple'},
										{name:'indent', className:'btn-purple'},
										null,
										{name:'justifyleft', className:'btn-primary'},
										{name:'justifycenter', className:'btn-primary'},
										{name:'justifyright', className:'btn-primary'},
										{name:'justifyfull', className:'btn-inverse'},
										null,
										{name:'insertImage', className:'btn-success'},
										null,
										'foreColor',
										null,
										{name:'undo', className:'btn-grey'},
										{name:'redo', className:'btn-grey'}
								    ],
						}
					}).render();
					contentWysiwyg.setValue(${dispatchManagementEntity.documents}); */

					//附件
					 var appendixUploaddropzone =new A.uploaddropzone({
						render : "#appendixFile",
						fileId:'${dispatchManagementEntity.appendix}'==""?[]:${dispatchManagementEntity.appendix},
						autoProcessQueue : true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render("2");

					//反馈附件
					 var feedBackUploaddropzone =  new A.uploaddropzone({
							render : "#feedbackFile",
							fileId:[],
							autoProcessQueue:true,//是否自动上传
							addRemoveLinks : true,//显示删除按钮
						}).render("3");
					
					var dataId = $("#id").val();
					$("#editBtn").on("click", function(){
						var obj = $("#feedBackForm").serializeObject();
						obj.id = $("#id").val();
						
						obj.feedBackFile=JSON.stringify(feedBackUploaddropzone.getValue());
						//obj.documents=JSON.stringify(contentWysiwyg.getValue());
						
						if(eval(feedBackUploaddropzone.getValue()).length==0 && (obj.feedBackComment == null || obj.feedBackComment.length ==0)){
							alert("反馈内容与附件必须填写1个以上");
							return;
						} 
						$.ajax({
							url : format_url("/dispatchManagement/dispatchFeedBack"),
								contentType : 'application/json',
								dataType : 'JSON',
								type : 'POST',
								data : JSON.stringify(obj),
								success: function(result){
									if(result.result=="success"){
										alert("保存成功");
										firstPage();
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert("保存失败");
								}
							}); 
					});
					
					$("#closeBtn").on("click", function(){
						firstPage();
					});
				});
			});
		</script>
	</body>
</html>