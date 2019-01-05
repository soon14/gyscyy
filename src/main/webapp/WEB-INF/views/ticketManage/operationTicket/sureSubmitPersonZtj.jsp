<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title>提交</title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
          <form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormTj">
                <input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
				<input class="col-md-12" id="status" name="status" type="hidden"  maxlength="64" value="${entity.status}">
				<input class="col-md-12" id="procInstId" name="procInstId" type="hidden"  maxlength="64" value="${entity.procInstId}">
                <!-- 下面是人的列表 -->
                <div class="form-group">

						<div class="col-sm-12" style="margin-left: 10px;">
							<select multiple="multiple" size="10" name="duallistbox_demo1[]" id="duallist" >
								<c:forEach items="${userList}" var="userList" varStatus="vs">
									<option value="${userList.loginName}">${userList.name}</option>
								</c:forEach>
								<!-- 
								<option value="option6" selected="selected">Option 6</option>
								 -->
							</select>
							<div class="hr hr-16 hr-dotted"></div>
						</div>
			     </div>
        </div>
       	
    </div>
    <div style="margin-top: 15px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="btnSavePerson" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="glyphicon glyphicon-ok-sign"></i>
				确定
			</button>
		</div>
</div>
<script type="text/javascript">
	var sureSubmitDatatables;
    jQuery(function ($) {
    	seajs.use(['duallistbox'], function(A){
    		 	var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold');
			
				$('#btnSavePerson').on('click',function(){
					
					var selectUser=$('[name="duallistbox_demo1[]"]').val();
					url = format_url("/operationTicket/rejectSubmit/"+id);
					$("#addFormTj").submit();
					
					if(selectUser==null||selectUser==""){
						alert('请选择审批人!');
						return;
					}else{
						listFormDialog.close(selectUser);
					} 
					
				});
    	});		
    });
    
</script>
