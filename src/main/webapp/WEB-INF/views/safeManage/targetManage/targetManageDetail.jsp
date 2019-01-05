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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
		      <input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
		      
<!-- 		      <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						目标责任书名称 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10"> -->
<%-- 	                   <textarea class="col-md-12" id="name" name="name"style="height:80px; resize:none;" readonly maxlength="128">${ entity.name }</textarea> --%>
<!--                 	</div> -->
<!--                </div> -->
		
			   <div class="form-group">
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    签订单位 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 				    	<input class="col-md-12" id="signUnitName" name="signUnitName" type="text" placeholder="" maxlength="64" readOnly value="${entity.signUnitName }"> --%>
<!-- 				    </div> -->
					<label class="col-md-2 control-label no-padding-right">
					    年号
				    </label>
				    <div class="col-md-4">
				    	<input id="targetDate" class="col-md-12" name="targetDate" value="${entity.targetDateString}" readonly type="text"/>
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    时间
				    </label>
				    <div class="col-md-4">
				    	<input id="signDate" class="col-md-12" name="signDate" value="${entity.signDateString}" readonly type="text"/>
				    </div>	
				</div>
				  <div class="form-group">
					
					<label class="col-md-2 control-label no-padding-right">
					 填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div> 
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="name" name="name" readonly="readonly" type="text" placeholder="" maxlength="64" value="${entity.name }">
								</div>
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						备注 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10"> -->
<%-- 	                   <textarea class="col-md-12" id="remark" name="remark"style="height:80px; resize:none;"  readonly  maxlength="128">${ entity.remark}</textarea> --%>
<!--                 	</div> -->
<!--                </div> -->
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    附件
				    </label>
				   	<div class="col-md-10" id="divfile">
                    </div>
				</div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
<!--         		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--         			<i class="ace-icon fa fa-floppy-o"></i> -->
<!--         			保存 -->
<!--         		</button> -->
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
        			

					
			
// 					$("#editBtn").on("click", function(){
//     					$("#editForm").submit();
//     				});
				});
			});
        </script>
    </body>
</html>