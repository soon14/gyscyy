<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="unitName" readonly="readonly" type="text" maxlength="20" value="${entity.unitName}">			    
					    <div id="unitIdDiv"></div>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>记录类型
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="recordTypeName" readonly="readonly" type="text" maxlength="20" value="${entity.recordTypeName}">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>记录时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="recordTime" name="recordTime" type="text"   maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${entity.recordTime}" type="date"/>' readonly="readonly">				    									    
				    </div>
				        <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>负责人
				    	</label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="fzr" readonly="readonly" type="text" maxlength="20" value="${entity.fzr}">				    					
                    </div>		
				</div>
				<div class="form-group form-horizontal">
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<input class="col-md-12" id="attachmentName" name="attachmentName" type="hidden" value="${entity.attachmentName}">
					<div class="col-md-10" id="divfile" readonly="readonly">
					</div>
			   </div>
			   <div class="form-group">
			   		<label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>记录内容
				    </label>
				    <div class="col-md-10">
				     <textarea readonly="readonly" name="recordContent"  class="col-md-12" style="height:150px; resize:none;">${entity.recordContent}</textarea>				    
					</div>  
				</div>				
			</form>
    		<div style="margin-right:100px;">
        		<button id="closeBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			关闭
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.attachmentName},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,//屏蔽控件单击上传功能
					}).render();

					//日期组件
					var recordTimeDatePicker = new A.my97datepicker({
						id: "recordTimeId",
						name: "recordTime",
						render: "#recordTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					recordTimeDatePicker.setValue('${entity.recordTime}');

					$("#closeBtn").on("click", function(){
						listFormDialog.close();
    				});
				});
			});
        </script>
    </body>
</html>