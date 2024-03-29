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
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    主键
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="id" name="id" type="text" placeholder="" maxlength="20" value="${ entity.id }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    物资名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${ entity.name }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    物资编码
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="code" name="code" type="text" placeholder="" maxlength="64" value="${ entity.code }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    创建时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="createDate" name="createDate" type="text" placeholder="" maxlength="0" value="${ entity.createDate }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    创建人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="createUserId" name="createUserId" type="text" placeholder="" maxlength="64" value="${ entity.createUserId }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    规格型号
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="model" name="model" type="text" placeholder="" maxlength="64" value="${ entity.model }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    生产厂家
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="manufacturer" name="manufacturer" type="text" placeholder="" maxlength="64" value="${ entity.manufacturer }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    单位
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" value="${ entity.unit }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="number" name="number" type="text" placeholder="" maxlength="64" value="${ entity.number }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    设备
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="${ entity.equipName }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    工作安排Id
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="overhaulArrangerId" name="overhaulArrangerId" type="text" placeholder="" maxlength="64" value="${ entity.overhaulArrangerId }">
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
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {id:{      maxlength:20},name:{      maxlength:64},code:{      maxlength:64},createDate:{      maxlength:0},createUserId:{      maxlength:64},model:{      maxlength:64},manufacturer:{      maxlength:64},unit:{      maxlength:64},number:{      maxlength:64},equipName:{      maxlength:64},overhaulArrangerId:{      maxlength:64},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/overhaulSpareconsume/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
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