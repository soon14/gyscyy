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
					 填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
					</label>
					<div class="col-md-4">
						<input  class="col-md-12" id="unitId" name="unitId"  placeholder=""  type="text" maxlength="64" readonly="readonly" value="${unitName }">
								</div>
				</div>
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					    年号
				    </label>
				    <div class="col-md-4">
					  <input class="col-md-12" id="yearNum" name="yearNum" type="text"  readonly="readonly" maxlength="64" value="${ entity.yearNumString }">
					</div>
			   <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
				        <input class="col-md-12" id="businessDate" name="businessDate" type="text"  readonly="readonly" maxlength="64" value="${ entity.businessDateString }">
				    </div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>名称
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name"  type="text" readonly="readonly" maxlength="64" value="${ entity.name }" >
                    </div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>类别
				    </label>
				    <div class="col-md-4">
					     <input class="col-md-12" id="category" name="category" type="text"  readonly="readonly" maxlength="64" value="${ entity.categoryName }">
				    </div>		
				</div>
				<div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   附件
				    </label>
				   	<div class="col-md-10" id="divfile">
                    </div>
				</div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        	
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:'${entity.fileId}'==""?[]:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
				
				});
			});
        </script>
    </body>
</html>