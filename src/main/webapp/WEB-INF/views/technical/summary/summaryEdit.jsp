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
								<input type="hidden" id="id" name="id" value="${summaryEntity.id}" />
								<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
								<input type="hidden" id="selectUser" name="selectUser"/>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>填报单位</label>
									<div class="col-md-4">
										<div id="searchunitName"></div>
								    </div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>填报人</label>
									<div class="col-md-4">
									<input class="col-md-12" id="tbrName" name="tbrName" type="text" readonly="readonly" placeholder="" maxlength="20" value="${ summaryEntity.tbrName }">
					    			<input class="col-md-12" id="tbrId" name="tbrId" type="hidden" readonly="readonly" placeholder="" maxlength="20" value="${ summaryEntity.tbrId }">
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>填报时间</label>
									<div class="col-md-4">
										<div id="tbsjDiv"></div>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>事件名称</label>
									<div class="col-md-4">
										<input type="text" id="sjmc" name="sjmc" class="col-md-12"  maxlength="64" value="${summaryEntity.sjmc}"></input>
									</div>
								</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>总结</label>
									<div class="col-md-10">
										<textarea id="zjnr" name="zjnr" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${summaryEntity.zjnr}</textarea>
									</div>
								</div>
								
								<div class="form-group form-horizontal">
									<label class="col-md-2 control-label no-padding-right">附件</label>
									<div class="col-md-10" id="divfile">
									</div>
								</div>
					</form>		
				
    		<div style="margin-right:10px;margin-top: 20px;">
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
						fileId:${summaryEntity.fileid},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					
					var unitNameCombotree = new A.combotree({
						render: "#searchunitName",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitNameId',
							data: {
								key: {
									name: "name"
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "parentId"
								}
							}
						}
					}).render();
					unitNameCombotree.setValue('${summaryEntity.unitId}');
					
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
					plandateStartDatePicker.setValue('${summaryEntity.time}');
					
					$('#summaryEditForm').validate({
						rules:  {
							unitId:{required:true,maxlength:20},
							tbrId:{required:true,maxlength:20},
							sjmc:{required:true,maxlength:64},
							time:{required:true,maxlength:128},
							zjnr:{required:true,maxlength:1000},
							},
						submitHandler: function (form) {
							//修改按钮
							var id = ${summaryEntity.id};
							var url = format_url("/summary/update/"+ id);
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
						$("#selectUser").val("");
						$("#saveOrSubmit").val("");
						$("#summaryEditForm").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>