<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="col-md-12" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
			     <input class="col-md-12" id="rlId" name="rlId" type="hidden" value="${dataMap.rlId}">
			    	<input type="hidden" id="checkState" name="checkState" value="${dataMap.checkState}" />
			    	<input type="hidden" id="meetingFlag" name="meetingFlag" value="${dataMap.meetingFlag}" />
			     <div class="form-group">									
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>安排时间
				    </label>
				    	<div class="col-md-4">
										<div id="workTimeDiv"></div>
								</div>
                      <label class="col-md-2 control-label no-padding-right">
						负责人
				    </label>
				    <div class="col-md-4">
                	<input name="fZR" type="text" id="fZR" value="${dataMap.fZR }"/>			                  					                   
                    </div>
               </div>	
                <div class="form-group">									
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成情况
				    </label>
				    <div class="col-md-4">
                	<input name="checkState" type="checkbox" id="checkStateid"/>			                  					                   
                    </div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工作内容
					</label>
					<div class="col-md-10">
				     <textarea id="meetingContent" placeholder="请输入工作内容" name="meetingContent"  class="col-md-12" style="height:150px; resize:none;">${dataMap.meetingContent}</textarea>				    					
                	</div>							   
               </div>
               
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn1" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				if(${dataMap.meetingFlag}==2){
					$('#meetingContent').attr("readonly","readonly");
				}
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					var checkState='${dataMap.checkState}';
					if(checkState.trim()=='checked'){
					     $("[name='checkState']").attr("checked",'true');//全选						
					}
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "workTimeId",
						name:"workTime",
						render:"#workTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					plandateStartDatePicker.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${dataMap.workTime}" type="both"/>');
        			var appData = ${dataMapJson};
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules:  {meetingContent:{required:true,   maxlength:64},checkState:{required:true},workTime:{required:true},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/safeMeeting/updateForWind/"+id+"/false");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
						//	var checkStatetag=document.getElementsByName('checkState');
							if($('#checkStateid').is(":checked"))	
							{
								obj.checkState='checked';
							}else{
								obj.checkState='uncheck';								
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn1").on("click", function(){
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>