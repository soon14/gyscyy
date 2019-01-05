<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
		        <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
					    接收人
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" maxlength="255" value="${ entity.userName }" readonly="readonly">
					</div>
					 <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>
					  是否学习
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectFile" name="projectFile" type="text" placeholder="" maxlength="255" value="${ entity.isLearnDic }"  readonly="readonly">
				    </div>	
				</div>
				 <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>学习时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="sendTime" name="sendTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.learnTime}" />'  readonly="readonly" >
                    </div>
                 </div>
		        <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">学习心得</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"  readonly="readonly">${entity.learnFeel}</textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="ReceiveAttachmentDiv">
					</div>
	            </div>
            </form>
            <div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    		<!-- 	<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button> -->
    		</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){
					//发布人选择带回
					var entity = ${entityJson};
					
					//附件上传
					var uploadReceiveAttachment=new A.uploaddropzone({
						render : "#ReceiveAttachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render(3);
					
				});
			});
        </script>
    </body>
</html>