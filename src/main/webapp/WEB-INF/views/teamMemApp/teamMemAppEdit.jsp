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
		    <form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="${ entity.id }">
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="name" name="name" type="text" placeholder="请输入名称" maxlength="32" value="${ entity.name }">
								</div>
							<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
								<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" placeholder="请输入编码" maxlength="32" value="${ entity.code }">
								</div>
							</div>
							<div class="form-group">
								<!-- <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>值长</label>
								<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="teamLeaderId" name="teamLeaderId"></select>
								</div> -->
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>创建时间</label>
								<div class="col-md-4">
										<div id="createDateDiv"></div>
								</div>

							</div>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-md-2 control-label no-padding-right"><span style="color:red;"></span>组员</label> -->
<!-- 								<div class="col-md-4"> -->
<!-- 										<select class="col-md-12 chosen-select" id="teamMemberIds" name="teamMemberIds"></select> -->
<!-- 								</div> -->
<!-- 							</div> -->
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea name="remark" placeholder="请输入备注内容" style="height:100px; resize:none;" class="col-md-12" maxlength="255">${ entity.remark }</textarea>
									</div>
								</div>
			</form>
		</div>
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
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					//日期组件
					var createDateDatePicker = new A.my97datepicker({
						id: "createDateId",
						name: "createDate",
						render: "#createDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					createDateDatePicker.setValue('<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${entity.createDate}" type="date"/>');
					//combobx组件
                	/* var teamLeaderIdCombobox = new A.combobox({
                		render: "#teamLeaderId",
                		//返回数据待后台返回TODO
                		datasource:${userListBox},
                		//multiple为true时select可以多选
                		multiple:false,
                		//allowBlank为false表示不允许为空
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                    teamLeaderIdCombobox.setValue('${entity.teamLeaderId}'); */
                    
					var id = $('#id').val();
					var codes=${codes};
					$('#editForm').validate({
						debug:true,
						rules: {id:{      maxlength:20},
							name:{required:true,      maxlength:64},
							code:{required:true,      maxlength:64},
							createDate:{   required:true,     maxlength:33},
							//teamLeaderId:{     required:true,   maxlength:11},
							remark:{      maxlength:255}},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/teamMemApp/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();

							var code = obj.code;
							if(codes.indexOf(code)>=0){
								alert("修改失败，编号不可重复");
								return;
							}
							obj.deleteFlag = 1;
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
					$.getScript(format_url("/static/js/testUrl.js"),function(){
					});
				});
			});
        </script>
    </body>
</html>