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
				    <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
				       <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="hidden" maxlength="64" readonly="readonly" value="${entity.createUserId }">
				        <input  class="col-md-12" id="userName" name="userName" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
					    <div id="createDateDiv"></div>
				    </div>
				</div>
				
				<div class="form-group">
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="unitId" name="unitId"></select>
                    </div>
            	</div>
			   
			   <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>安措内容
				    </label>
                    <div class="col-md-10">
						<textarea id="content" name="content" placeholder="请输入安措内容" style="height:80px; resize:none;" class="col-md-12" maxlength="512" >${entity.content }</textarea>
                    </div>
				</div>
				
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
        			
        			
					//日期组件
					var businessDateDatePicker = new A.my97datepicker({
						id: "createDateId",
						name: "createDate",
						render: "#createDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					businessDateDatePicker.setValue(appData.createDateString);
					
					var unitIdCombobox = new A.combobox({
                		render: "#unitId",
                		//返回数据待后台返回TODO
                		datasource:${unitList},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                    unitIdCombobox.setValue(${entity.getUnitId()});
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{      maxlength:20},
							content:{required:true,      maxlength:512},
							createDate:{required:true,      maxlength:64},
							unitId:{required:true,      maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/workSafeContent/savePage/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							
							obj.createDate = $('#createDateId').val()+":00";
							
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
					$("#editBtn").on("click", function(){
						
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>