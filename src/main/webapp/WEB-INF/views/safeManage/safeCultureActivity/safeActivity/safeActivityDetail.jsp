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
						<span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="creatorName" readonly="readonly" name="creatorName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
                    </div>
					 <label class="col-md-2 control-label no-padding-right">
						名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name"style="resize:none;"  placeholder="" maxlength="128" type="text" value="${entity.name}"  readonly="true">
                	</div>
<!--                 	<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						类别 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 				    	<input class="col-md-12" id="name" name="name"style="resize:none;"  placeholder="" maxlength="128" type="text" value="${entity.typeName}"  readonly="true"> --%>
<!-- 					</div> -->
				</div>
			   
	           <div class="form-group">
                    
                    <label class="col-md-2 control-label no-padding-right">
						年号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="name" name="name"style="resize:none;"  placeholder="" maxlength="128" type="text" value="${entity.yearNum}"  readonly="true">
                    </div>
                     <label class="col-md-2 control-label no-padding-right">
						时间
					</label>
					<div class="col-md-4">
					   <input class="col-md-12" id="name" name="name"style="resize:none;"  placeholder="" maxlength="128" type="text" value="${entity.dateStr}"  readonly="true">
                	</div>
               </div>
               <div class="form-group">
				<label class="col-md-2 control-label no-padding-right" >
					附件
                   </label>
				   <div class="col-md-10" id="divfile">
                   </div>
               </div>
			</form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				返回
    			</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['uploaddropzone'], function(A){
					//相关资料 上传控件
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
				});
			});
        </script>
    </body>
</html>