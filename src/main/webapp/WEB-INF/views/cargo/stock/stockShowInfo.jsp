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
					    单位
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="unitName" name="unitName" type="text" placeholder="" maxlength="0" value="${ entity.unitName }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    物资信息
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="materialId" name="materialId" type="text" placeholder="" maxlength="0" value="${ entity.materialId }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    库存数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="inventoryQuantity" name="inventoryQuantity" type="text" placeholder="" maxlength="20" value="${ entity.inventoryQuantity }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    标准上限
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="upperLimit" name="upperLimit" type="text" placeholder="" maxlength="20" value="${ entity.upperLimit }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    标准下限
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="lowerLimit" name="lowerLimit" type="text" placeholder="" maxlength="20" value="${ entity.lowerLimit }">
					</div>
				</div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
        			<i class="fa fa-reply"></i>
        			返回
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			var appData = ${entityJson};
				});
			});
        </script>
    </body>
</html>