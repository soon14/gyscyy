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
			   <input class="col-md-12" id="id" name="id" type="hidden"  maxlength="20" value="${ entity.id }">
<%-- 			    <input class="col-md-12" id="spiritName" name="spiritName" type="hidden" placeholder="" maxlength="64" value="${ entity.spiritName }"> --%>
<%-- 			      <input class="col-md-12" id="docName" name="docName" type="hidden" placeholder="" maxlength="64" value="${ entity.docName }"> --%>
<%-- 			        <input class="col-md-12" id="name" name="name" type="hidden" placeholder="" maxlength="64" value="${ entity.name }"> --%>
			         <input class="col-md-12" id="status" name="status" type="hidden" placeholder="" maxlength="64" value="${ entity.status }">
			         <input class="col-md-12" id="type" name="type" type="hidden" placeholder="" maxlength="64" value="${ entity.type }">
			 
	
		
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					   	<span style="color:red;">*</span> 年号 
				    </label>
				    <div class="col-md-4">
					    <div id="fileDateDiv"></div>
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 时间
				    </label>
				    <div class="col-md-4">
					    <div id="receiveTimeDiv"></div>
					</div>
				</div>
			   <div class="form-group">
				   
				    	<label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>填写人
				    </label>
				    <div class="col-md-4">
				         <input  class="col-md-12" id="userName" name="userName" placeholder=""  type="text" maxlength="64" readonly="readonly" value="${userName }">
				         <input  class="col-md-12" id="createUserId" name="createUserId" placeholder=""  type="hidden" maxlength="64" readonly="readonly" value="${entity.createUserId }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>名称
					</label>
					<div class="col-md-4">
								<input class="col-md-12" id="name" name="name" type="text" placeholder="" maxlength="64" value="${entity.name }">
								</div>	
				</div>
				<div class="form-group">
				<label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>附件
                   </label>
				   <div class="col-md-10" id="divfile">
                   </div>
               </div>
<!-- 		       <div class="form-group"> -->
<!-- 					<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						精神名称附件 -->
<!-- 					</label> -->
<!-- 					<div class="col-md-10" id="divSpiritFile"> -->
<!--                    </div> -->
<!--                </div> -->
<!--                 <div class="form-group"> -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 						相关资料 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-10" id="divDocFile"> -->
<!--                    </div> -->
<!--                </div> -->
			
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			var appData = ${entityJson};
        			
        			//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${entity.fileId},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
						options : {
							maxFiles:1,
						}
					}).render();
					
// 					var uploaddropzonedoc =new A.uploaddropzone({
// 						render : "#divDocFile",
// 						fileId:${entity.docId},
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 						options : {
// 							maxFiles:1,
// 						}
// 					}).render(1);
					
// 					var uploaddropzonespirit =new A.uploaddropzone({
// 						render : "#divSpiritFile",
// 						fileId:${entity.spiritDocId},
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 						options : {
// 							maxFiles:1,
// 						}
// 					}).render(2);
					
					//单位
// 					 searchunitid = new A.combobox({
// 						render : "#unitId",
// 						datasource : ${unitList},
// 						allowBlank: true,
// 						options : {
// 							"disable_search_threshold" : 10
// 						}
// 					}).render();
// 					 searchunitid.setValue(appData.unitId);
// 						$("#unitName").val('${entity.unitName}');
			
					//日期组件
					var receiveTimeDatePicker = new A.my97datepicker({
						id: "receiveTimeId",
						name: "receiveTime",
						render: "#receiveTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					receiveTimeDatePicker.setValue(appData.receiveTimeString);
					//日期组件
					var fileDateDatePicker = new A.my97datepicker({
						id: "fileDateId",
						name: "fileDate",
						render: "#fileDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					fileDateDatePicker.setValue(appData.fileDateString);
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {
							id:{      maxlength:20},
							name:{  required:true,     maxlength:64},
							spiritName:{      maxlength:64},
							unitId:{   required:true,    maxlength:64},
							receiveTime:{   required:true,    maxlength:64},
							fileDate:{  required:true,      maxlength:64},
							docName:{      maxlength:64}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/upperFile/fileUpdate/"+id+"/2");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							
							obj.fileDate = $('#fileDateId').val()+"-01-01 00:00:00";
							obj.receiveTime = $('#receiveTimeId').val()+" 00:00:00";
							//附件上传
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
// 							obj.docId=JSON.stringify(uploaddropzonedoc.getValue());
// 							obj.spiritDocId=JSON.stringify(uploaddropzonespirit.getValue());
							
							if(eval(uploaddropzone.getValue()).length==0){
								alert("请上传附件");
								return;
							}
							if(uploaddropzone.getLenght()>1){
								alert("只能上传一个附件！");
								return;
							}
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
						
						var yearNumStr = fileDateDatePicker.getValue();
						var checkDateStr = receiveTimeDatePicker.getValue().split("-")[0];
						if(yearNumStr!=checkDateStr){
							alert("年号和时间不符");
							return;
						}
    					$("#editForm").submit();
    				});
				});
			});
        </script>
    </body>
</html>