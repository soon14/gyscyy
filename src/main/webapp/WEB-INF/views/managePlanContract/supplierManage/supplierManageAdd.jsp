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
			  <input class="col-md-12" id="year" name="year" type="hidden"  value="${giveDate }">
			   <input class="col-md-12" id="type" name="type" type="hidden"  value="${typeEntity.id }">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>公司名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>营业执照号码
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>法人代表
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="delegateUserId" name="delegateUserId" type="text" placeholder="" maxlength="64" value="">
                	</div>
                	<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>联系人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="userId" name="userId" type="text" placeholder="" maxlength="64" value="">
                    </div>
    			</div>
    			   <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						公司电话
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="companyPhone" name="companyPhone" type="text" placeholder="" maxlength="64" value="">
                	</div>
				     <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>联系电话
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="phone" name="phone" type="text" placeholder="" maxlength="11" value="">
                	</div>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						联系人电子邮箱
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="email" name="email" type="text" placeholder="" maxlength="64" value="">
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
						采购类型
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="typeName" name="typeName" type="text" readonly="readonly" maxlength="64" value="${typeEntity.name}">
                    </div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>资质等级
					</label>
					<div class="col-md-10">
						<textarea name="level" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>从业范围
					</label>
					<div class="col-md-10">
						<textarea name="workRange" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>主要从业范围
					</label>
					<div class="col-md-10">
						<textarea name="mainWorkRange" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="4000"></textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>公司地址
					</label>
					<div class="col-md-10">
						<textarea name="address" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="512"></textarea>
					</div>
               </div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="4000"></textarea>
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{maxlength:20},
							code:{maxlength:64,required:true},
							name:{maxlength:64,required:true},
							level:{maxlength:1024,required:true},
							delegateUserId:{maxlength:64,required:true},
							workRange:{maxlength:1024,required:true},
							mainWorkRange:{maxlength:4000,required:true},
							address:{maxlength:512,required:true},
							companyPhone:{maxlength:64},
							userId:{maxlength:64,required:true},
							phone:{maxlength:11,required:true},
							email:{maxlength:64},
							remark:{maxlength:4000},
							unitId:{maxlength:64},
							type:{maxlength:64},
							status:{maxlength:64},
							year:{maxlength:64}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/supplierManage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.year = $("#year").val()+"-01-01 00:00:00";
							
							//验证手机格式
							if(obj.phone){
								//var patrn = /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/;
								var patrn = /^1(3|4|5|7|8)\d{9}$/;
								if(!patrn.exec(obj.phone)){
									alert("联系电话格式不正确！");
									return;
								}
							}
							//验证邮箱格式
							if(obj.email){
								var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
								if(!patrn.exec(obj.email)){
									alert("邮箱格式不正确！");
									return;
								}
							}
							
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