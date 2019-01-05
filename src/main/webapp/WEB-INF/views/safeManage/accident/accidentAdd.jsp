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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="summaryAddForm">
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位</label>
									<div class="col-md-4">
									<select id="searchunitName" name="unitId" class="col-md-12 chosen-select"></select>
<!-- 										<div id="searchunitName"></div> -->
								    </div>
								    <label class="col-md-2 control-label no-padding-right">
										<span style="color:red;">*</span>填写人
								    </label>
								    <div class="col-md-4">
										<input class="col-md-12" id="userName" readonly="readonly" name="userName" type="text" placeholder="" maxlength="20" value="${userEntity.name }">
										<input class="col-md-12" id="userId" name="userId" type="hidden" placeholder="" maxlength="20" value="${userEntity.id }">
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
										<input type="text" id="name" name="name" maxlength="64" class="col-md-12"></input>
									</div>
								</div>
							
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>调查报告</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea id="dcbg" name="dcbg" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea> -->
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
						fileId:[],
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
					
					//工作负责人
					var searchTbrAddName = new A.combobox({
						render : "#searchTbrAddName",
						datasource : ${sgglType},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					
					
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
					
					
					$('#summaryAddForm').validate({
						rules:  {
							unitId:{required:true,maxlength:20},
							sglb:{required:true,maxlength:2},
							name:{required:true,maxlength:64},
							time:{required:true,maxlength:128},
							createUserId:{required:true,maxlength:64},
							dcbg:{required:true,maxlength:256},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/accident");
							var obj = $("#summaryAddForm").serializeObject();
							    obj.fileid=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									summaryDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
					
					$("#saveBtnSummary").on("click", function(){
						$("#summaryAddForm").submit();
    				});
					
				});
			});
        </script>
    </body>
</html>