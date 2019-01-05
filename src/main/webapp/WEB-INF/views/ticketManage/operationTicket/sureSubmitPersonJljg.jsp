<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="col-md-12">
    <div class="page-content">
        <div class="tabbable">

					<div id="workitem" class="tab-pane fade active in" >
						<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormQf">
						<input class="col-md-12" id="id" name="id" type="hidden"  maxlength="64" value="${electricId}">
						<input class="col-md-12" id="status" name="status" type="hidden"  maxlength="64" value="${entity.status}">
						<input class="col-md-12" id="taskId" name="taskId" type="hidden"  maxlength="64" value="${taskId}">
						<input class="col-md-12" id="procInstId" name="procInstId" type="hidden"  maxlength="64" value="${entity.procInstId}">
						<div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>开始时间   
								</label>
								<div class="col-md-4">
										 <div id="startDateDiv"  style="border:none; padding:0px;"></div>
								</div>
							<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>结束时间 
								</label>
								<div class="col-md-4">
										 <div id="endDateDiv"  style="border:none; padding:0px;"></div>
								</div>
						</div>
						
						<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">
									附件 </label>
								<div class="col-md-10" id="divfile" >
								</div>
						</div>
						</form>
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
    	seajs.use(['duallistbox', 'my97datepicker','uploaddropzone'], function(A){
    		
    		var startDateDatePicker = new A.my97datepicker({
				id : "startDateId",
				name : "startDate",
				render : "#startDateDiv",
				options : {
					isShowWeek : false,
					skin : 'ext',
					maxDate : "#F{$dp.$D(\\'endDateId\\')}",
					minDate : "",
					dateFmt : "yyyy-MM-dd HH:mm"
				}
			}).render();
    		
    	var endDateDatePicker = new A.my97datepicker({
			id : "endDateId",
			name : "endDate",
			render : "#endDateDiv",
			options : {
				isShowWeek : false,
				skin : 'ext',
				maxDate : "",
				minDate : "#F{$dp.$D(\\'startDateId\\')}",
				dateFmt : "yyyy-MM-dd HH:mm"
			}
		}).render(); 
    		
				//附件上传
				var uploaddropzone =new A.uploaddropzone({
					render : "#divfile",
					fileId:[],
					autoProcessQueue:true,//是否自动上传
					addRemoveLinks : true,//显示删除按钮
				}).render();
				
    		 	/* var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold'); */
			
				$('#addFormQf').validate({
					debug:true,
					rules:  {
						startDate:{required:true,maxlength:64},
						endDate:{required:true,maxlength:64}
						},
					submitHandler: function (form) {
						
						//添加按钮
						 url = url;
						//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
						var obj = $("#addFormQf").serializeObject();
						obj.fileId=JSON.stringify(uploaddropzone.getValue());
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result=="success"){
									alert('审批成功');	
									window.scrollTo(0,0);
									listFormDialog.close();
									$("#page-container").load(format_url('/todoTask/list/1/10'));
								}else{
									alert(result.result);
								}
							},
							error:function(v,n){
								alert('审批失败');
							}
						});
					}
				});
				
				$('#btnSavePerson').on('click',function(){
					//var selectUser=$('[name="duallistbox_demo1[]"]').val();
					url = format_url("/operationTicket/implement/"+id);
					$("#addFormQf").submit();
// 					listFormDialog.close(JSON.stringify(uploaddropzone.getValue()));
					
					/* if(selectUser==null||selectUser==""){
						alert('请选择审批人!');
						return;
					}else{
						//goBackToOperation(endDateDatePicker.getValue());
						listFormDialog.close(selectUser);
					}  */
					
				});
    	});		
    });
    
</script>
