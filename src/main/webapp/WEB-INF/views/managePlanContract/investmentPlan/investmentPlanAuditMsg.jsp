<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title></title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
                <!-- 下面是人的列表 -->
                <form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input id="id" value="${entity.id }" type="hidden" name="id"/>
						<input id="auditBtn" value="${entity.auditBtn }" type="hidden" name="auditBtn"/>
			
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						审批意见
					</label>
					<div class="col-md-10">
					<textarea id="auditMsg" name="auditMsg" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="64"></textarea>
                	</div>
               </div>
             <!-- 下面是人的列表 -->
			    </form>
        </div>
       	
    </div>
    <div style="margin-top: 15px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="btnSavePerson" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="ace-icon fa fa-floppy-o"></i>
				确定
			</button>
		</div>
</div>
<script type="text/javascript">
	var sureSubmitDatatables;
    jQuery(function ($) {
    	seajs.use(['duallistbox'], function(A){
				$('#btnSavePerson').on('click',function(){
					var obj = $("#approveForm").serializeObject();
					obj.yearNum = $("#yearNum").val()+"-01-01 00:00:00";
					obj.taskId=$("#taskId").val();
					obj.procInstId=$("#procInstId").val();
					obj.auditBtn = $("#auditBtn").val();
					var auditMsg=$('#auditMsg').val();
					obj.auditMsg = auditMsg;
					var url = "investmentPlan/auditReject";
					$.ajax({
						url : format_url("/"+url),
						contentType : 'application/json',
						dataType : 'JSON',
						type : 'POST',
						data:JSON.stringify(obj),
						success: function(result){
							if(result.result=="success"){
								alert("审批成功");
								window.scrollTo(0,0);
								listFormDialog.close();
								$("#page-container").load(format_url('/todoTask/list/1/10'));
							}else{
								alert(result.errorMsg);
							}
						},
						error:function(v,n){
							alert('审批失败');
						}
					});
					
				});
    	});		
    });
    
</script>
