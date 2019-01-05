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
                    <span style="color:red;">*</span>时间
				    </label>
				    <div class="col-md-4">
				        <input class="col-md-12" id="createDate" name="createDate" type="text"  readonly="readonly" maxlength="64" value="${ entity.createDateString }">
				    </div>
				</div>
				
				<div class="form-group">
				   	<label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span> 单位名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="unitId" name="unitId" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${unitName }">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>安措内容
				    </label>
				   <div class="col-md-10">
						<textarea id="content" name="content" placeholder="请输入安措内容" style="height:80px; resize:none;" class="col-md-12" maxlength="512" readonly="readonly">${entity.content }</textarea>
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
				
				});
			});
        </script>
    </body>
</html>