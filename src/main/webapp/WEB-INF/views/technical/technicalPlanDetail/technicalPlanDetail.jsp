<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
				               <input id="id" name="id" type="hidden" value="${technicalPlandetailEntity.id}"/>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">项目名称</label>
									<div class="col-md-4">
										 <input class="col-md-12" id="planName" name="planName" type="text"  readonly="readonly" 
										 maxlength="128" value="${technicalPlandetailEntity.planName}">
								    </div>
									<label class="col-md-2 control-label no-padding-right">定检周期</label>
									<div class="col-md-4">
										<input id="djzq" type="text" readonly="readonly"
												value="${technicalPlandetailEntity.djzqStr}" class="col-md-12">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">本年计划时间</label>
									<div class="col-md-4">
										<input id="bjtime" type="text" readonly="readonly"
												value='<fmt:formatDate  pattern="yyyy-MM" value="${technicalPlandetailEntity.nowTime}" type="date"/>'
												class="col-md-12">
									</div>
									<label class="col-md-2 control-label no-padding-right">实际完成时间</label>
									<div class="col-md-4">
										<input id="sjtime" type="text" readonly="readonly"
												value='<fmt:formatDate  pattern="yyyy-MM" value="${technicalPlandetailEntity.wcTime}" type="date"/>'
												class="col-md-12">
									</div>
							    </div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">完成状态</label>
									<div class="col-md-4">
										<input id="wcStatus" type="text" readonly="readonly"
												value="${technicalPlandetailEntity.wcStatusStr}" class="col-md-12">
								</div>
								</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">完成情况</label>
									<div class="col-md-10">
										<textarea id="gzzj" name="gzzj"  readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${technicalPlandetailEntity.wcqk}</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">存在问题和风险</label>
									<div class="col-md-10">
										<textarea id="remark" name="remark" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${technicalPlandetailEntity.danger}</textarea>
									</div>
								</div>
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="detailFile">
									</div>
								</div>
							
				</form>
				<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			</div>
    	</div>
<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					var id='${technicalPlandetailEntity.technicalWorkid}';
					var technicalId='${technicalPlandetailEntity.technicalId}';
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#detailFile",
						fileId:${technicalPlandetailEntity.fileid},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
					
				});
			});
        </script>
    </body>
</html>