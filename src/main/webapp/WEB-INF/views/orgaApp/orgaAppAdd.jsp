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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="32" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>编码
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="32" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>组织机构
					</label>
					<div class="col-md-4">
					   <div id="organizationDiv"></div>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>组长
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="teamLeader" name="teamLeader"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;"></span>组员
					</label>
					<div class="col-md-4">
						<select class="col-md-12 chosen-select" id="teamMember" name="teamMember"></select>
                	</div>
    				</div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="255"></textarea>
					</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//combotree组件
					var unitNameIdCombotree = new A.combotree({
						render: "#organizationDiv",
						name: 'organization',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					
					//combobx组件
					var teamLeaderCombobox = new A.combobox({
						render : "#teamLeader",
						datasource : ${userListBox},
						multiple:false,
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					var teamMemberCombobox = new A.combobox({
						render: "#teamMember",
						//返回数据待后台返回TODO
						datasource:${userListBox2},
						//multiple为true时select可以多选
						multiple:true,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					
					var codes=${codes};
					$('#addForm').validate({
						debug:true,
						rules:  {id:{ maxlength:20},
							name:{ required:true,     maxlength:32},
							code:{  required:true,    maxlength:32},
							organization:{ required:true,     maxlength:20},
							teamLeader:{  required:true,    maxlength:64},
							teamMember:{    maxlength:64},
							remark:{      maxlength:255},
							},
						submitHandler: function (form) {
							
							//添加按钮
							var url = format_url("/orgaApp");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							var code=obj.code;
							if(codes.indexOf(code)>=0){
								alert("添加失败，编码不可重复");
								return;
							}
							obj.teamMember = teamMemberCombobox.getValue();
							obj.deleteFlag = "N";
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>