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
			<form class="form-horizontal" role="form"  style="margin-right:100px;margin-top:30px;" id="addForm">
			<input id="id" value="${entity.id }" type="hidden" name="id"/>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>驳回原因
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
             <!-- 下面是人的列表 -->
			    </form>
    		<div style="margin-right:100px;" id="saveBtn">
				<button class="btn btn-xs btn-danger pull-right" style="margin-top:20px;"data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="function" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:20px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				驳回
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['duallistbox'], function(A){
					var demo1 = $('select[name="userList"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
					var container1 = demo1.bootstrapDualListbox('getContainer');
					container1.find('.btn').addClass('btn-white btn-info btn-bold');
					//添加按钮
					var selectUser="";
					$("#function").on("click", function(e){
						var auditMsg=$('#auditMsg').val();
						if(auditMsg==null||auditMsg==""){
							alert('请填写驳回原因!');
							return;
						}else{
							listFormDialog.close(auditMsg);
						}
    				});
				});
			});
        </script>
    </body>
</html>