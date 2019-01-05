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
			     <input class="col-md-12" id="contextV"  type="hidden" value="${ entity.context }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    主题
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="title" name="title" type="text" placeholder="" maxlength="258" value="${entity.title}" readonly>
				    </div>	
    		     </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    消息内容
				    </label>
				    <div class="col-md-10">
<!-- 					    <textarea name="context"   placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="0" readonly></textarea> -->
							<div id="contextval" style="width:100%;height:100px;border:1px solid #CCC;background-color:#F5F5F5">${ entity.context }</div>
				    </div>
			    </div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    消息发送人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="sendPerson" name="sendPerson" type="text" placeholder="" maxlength="20" value="${entity.sendPerson}" readonly>
				    </div>
				   </div>
			   <div class="form-group">	
				    <label class="col-md-2 control-label no-padding-right">
					    发送时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="type" name="type" type="text" placeholder="" maxlength="10" value="${entity.sendTimeString}"  readonly>
					</div>
				</div>
			</form>
    		<div style="margin-right:100px;">
        		<button id="closeBtn" class="btn btn-xs btn-primary pull-right">
        			<i class="ace-icon fa fa-reply"></i>
        			返回
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					$("#closeBtn").on("click", function(){
						listFormDialog.close();
    				});
					
				
					
					
				});
			});
        </script>
    </body>
</html>