<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="col-md-12" >
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="summaryEditForm">
								<input type="hidden" id="id" name="id" value="${accidentEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">单位</label>
									<div class="col-md-4">
										<input type="text" id="unitName" name="unitName" class="col-md-12"  readonly="readonly" value="${accidentEntity.unitName}"></input>
								    </div>
								    <label class="col-md-2 control-label no-padding-right">
										填写人
								    </label>
								    <div class="col-md-4">
								    <input type="text" id="createUserId" name="createUserId" class="col-md-12"  readonly="readonly" value="${userName}"></input>
				                    </div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">时间</label>
									<div class="col-md-4">
										<input type="text" id="time" name="time" class="col-md-12" readonly="readonly" value="${accidentEntity.timeString}"></input>
									</div>
										<label class="col-md-2 control-label no-padding-right">类别</label>
									<div class="col-md-4">
										<input type="text" id="sglb" name="sglb" class="col-md-12" readonly="readonly" value="${accidentEntity.sglbName}"></input>
									</div>
								</div>
							
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">名称</label>
									<div class="col-md-4">
										<input type="text" id="name" name="name" class="col-md-12" readonly="readonly" value="${accidentEntity.name}"></input>
									</div>
								</div>
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">调查报告</label> -->
<!-- 									<div class="col-md-10"> -->
<%-- 										<textarea id="dcbg" name="dcbg" readonly="readonly" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${accidentEntity.dcbg}</textarea> --%>
<!-- 									</div> -->
<!-- 								</div> -->
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
					</form>		
				
    		<div style="margin-right:10px;margin-top: 20px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${accidentEntity.fileid},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					
				});
			});
        </script>
    </body>
</html>