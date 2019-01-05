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
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						主键
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="id" name="id" type="text" placeholder="" maxlength="20" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						创建人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						创建时间
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" maxlength="0" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						设备名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						设备类别
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="equipType" name="equipType" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						型号规格
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="specification" name="specification" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						数量
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="amount" name="amount" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						预计单价
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="budgePrice" name="budgePrice" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						预计总价
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder="" maxlength="64" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						项目名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="64" value="">
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						物资采购Id
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="goodsPurchaseId" name="goodsPurchaseId" type="text" placeholder="" maxlength="64" value="">
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
					$('#addForm').validate({
						debug:true,
						rules:  {id:{      maxlength:20},createUserId:{      maxlength:64},createDate:{      maxlength:0},equipName:{      maxlength:64},equipType:{      maxlength:64},specification:{      maxlength:64},amount:{      maxlength:64},budgePrice:{      maxlength:64},totalPrice:{      maxlength:64},projectName:{      maxlength:64},goodsPurchaseId:{      maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/goodsRelation");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
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