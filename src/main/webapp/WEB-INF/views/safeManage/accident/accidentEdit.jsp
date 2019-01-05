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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="summaryEditForm">
								<input type="hidden" id="id" name="id" value="${accidentEntity.id}" />
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位</label>
									<div class="col-md-4">
								         <select id="searchunitName" name="unitId" class="col-md-12 chosen-select"></select>
								    </div>
								     <label class="col-md-2 control-label no-padding-right">
										<span style="color:red;">*</span>填写人
								    </label>
								    <div class="col-md-4">
								    <input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${sysUserEntity.name }">
									<input class="col-md-12" id="createUserId" name="createUserId" type="hidden" placeholder="" maxlength="20" value="${sysUserEntity.id}">
				                    </div>
								    
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>时间</label>
									<div class="col-md-4">
										<div id="tbsjDiv"></div>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>类别</label>
									<div class="col-md-4">
										<select id="searchTbrAddName" name="sglb"></select>
									</div>
								
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
									<div class="col-md-4">
										<input type="text" id="name" name="name" class="col-md-12"  maxlength="64" value="${accidentEntity.name}"></input>
									</div>
								</div>
							
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>调查报告</label> -->
<!-- 									<div class="col-md-10"> -->
<%-- 										<textarea id="dcbg" name="dcbg" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${accidentEntity.dcbg}</textarea> --%>
<!-- 									</div> -->
<!-- 								</div> -->
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
					</form>		
				
    		<div style="margin-right:10px;margin-top: 20px;">
    		  <button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtnSummary" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			var workSafeOneDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${accidentEntity.fileid},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
// 					var unitNameCombotree = new A.combotree({
// 						render: "#searchunitName",
// 						name: 'unitId',
// 						//返回数据待后台返回TODO
// 						datasource: ${unitNameIdTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'unitNameId',
// 							data: {
// 								key: {
// 									name: "name"
// 								},
// 								simpleData: {
// 									enable: true,
// 									idKey: "id",
// 									pIdKey: "parentId"
// 								}
// 							}
// 						}
// 					}).render();
					//单位
					 searchunitid = new A.combobox({
						render : "#searchunitName",
						datasource : ${unitList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					 searchunitid.setValue('${accidentEntity.unitId}');
					//工作负责人
					var searchTbrAddName = new A.combobox({
						render : "#searchTbrAddName",
						datasource : ${sgglType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					searchTbrAddName.setValue('${accidentEntity.sglb}');
					
					
					//日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "tbsjDivId",
						name:"time",
						render:"#tbsjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					plandateStartDatePicker.setValue('${accidentEntity.time}');
					
					$('#summaryEditForm').validate({
						rules:  {
							unitId:{required:true,maxlength:20},
							sglb:{required:true,maxlength:2},
							name:{required:true,maxlength:64},
							time:{required:true,maxlength:128},
// 							dcbg:{required:true,maxlength:256},
							},
						submitHandler: function (form) {
							//修改按钮
							var id = ${accidentEntity.id};
							var url = format_url("/accident/update/"+ id);
							var obj = $("#summaryEditForm").serializeObject();
							    obj.fileid=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('修改成功');
									summaryDialog.close();
										
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					
					
					$("#saveBtnSummary").on("click", function(){
						$("#summaryEditForm").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>