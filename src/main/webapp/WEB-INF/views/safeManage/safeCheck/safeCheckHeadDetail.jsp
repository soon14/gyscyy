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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addTargetForm">
			   <input id="id" class="col-md-12" name="id"   readonly  type="hidden" value="${ entity.id }"/>
			    <div class="form-group" >
				    <label class="col-md-2 control-label no-padding-right">
					年号
				    </label>
				    <div class="col-md-4">
						<input id="targetDateDiv" class="col-md-12" name="targetDate" value="${entity.yearNum}" readonly type="text"/>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						检查时间
					</label>
					<div class="col-md-4">
					   <input id="signDate" class="col-md-12" name="signDate" value="${entity.exportDate}" readonly type="text"/>
                	</div>
               </div>
	           <div class="form-group" >
					 <label class="col-md-2 control-label no-padding-right">
						填写人
					</label>
					<div class="col-md-4">
					 <input  class="col-md-12" id="userName" name="userName" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userEntity.name }">
                	</div>
                     <label class="col-md-2 control-label no-padding-right">
					类别
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="categorySelect" name="category" type="text" placeholder="" maxlength="64" readOnly value="${entity.categoryName }">
					</div>
               </div>
                   <div class="form-group" >
                   <label class="col-md-2 control-label no-padding-right">
						名称
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="checkName" name="checkName"style="resize:none;"  placeholder="" readonly type="text" value="${ entity.checkName}">
                	</div>
                  <label class="col-md-2 control-label no-padding-right">
						整改时间
					</label>
					<div class="col-md-4">
					   <input id="rectificationDate" class="col-md-12" name="rectificationDate" value="${entity.rectificationDate}" readonly type="text" />
                	</div>
               </div>
               <div class="form-group" >
               <label class="col-md-2 control-label no-padding-right">
						检查人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="landWaiter" name="landWaiter"style="resize:none;"  placeholder="" type="text" maxlength="128" value="${ entity.landWaiter}" readonly="readonly">
                	</div>
               </div>
               <div class="form-group" >
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
<!--     			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--     				<i class="ace-icon fa fa-floppy-o"></i> -->
<!--     				保存 -->
<!--     			</button> -->
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					var appData = ${entityJson};
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileAddress},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					
    				
				});
			});
        </script>
    </body>
</html>