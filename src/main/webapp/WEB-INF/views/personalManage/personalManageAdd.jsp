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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>工号
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="code" name="code" type="text" placeholder="请输入工号" maxlength="64" value="">
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>姓名
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="name" name="name" type="text" placeholder="请输入姓名" maxlength="64" value="">
                	</div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>性别
					</label>
					<div class="col-md-4">
					   <select class="col-md-12 chosen-select" id="sex" name="sex"></select>	
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位
				    </label>
				    <div class="col-md-4">
						<div id="unitTreeDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						手机号
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="mobile" name="mobile" type="text" placeholder="请输入手机号" maxlength="32" value="">
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						邮箱
				    </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="mail" name="mail" type="text" placeholder="请输入邮箱地址" maxlength="128" value="">
                    </div>
               </div>
		       <!-- <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						相片地址
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="photoUrl" name="photoUrl" type="text" placeholder="请输入相片地址" maxlength="256" value="">
                	</div>
               </div> -->
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>是否为外部人员</label>
					<div class="col-md-4">
						<select id="outStatus" class="col-md-12 chosen-select" onchange="changeUnit()"></select>
					</div>
					<label class="col-md-2 control-label no-padding-right" id="unitTitle" style="display:none;"><span style="color:red;">*</span>参建单位</label>
					<div class="col-md-4" id="unitDiv" style="display:none;">
						<select id="constructionUnits" class="col-md-12 chosen-select" ></select>
					</div>										
				</div>
                <div class="form-group">									
				    <label class="col-md-2 control-label no-padding-right">
						关联用户
				    </label>
				    <div class="col-md-1">
                	<input name="associateUser" type="checkbox" id="associateUserId" value="1"/>			                  					                   
                    </div>
               </div> 
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="请输入备注" style="height:100px; resize:none;" class="col-md-12" maxlength="256"></textarea>
					</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//combobx组件
					var sexComboboxData = ${sexCombobox};
					var sexCombobox = new A.combobox({
						render: "#sex",
						//返回数据待后台返回
						datasource:sexComboboxData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combotree组件
					var unitTreeList = ${unitTreeList};
					var unitCombotree = new A.combotree({
						render: "#unitTreeDiv",
						name: "unit",
						//返回数据待后台返回
						datasource: unitTreeList,
						width:"210px",
						options: {
							treeId: 'unit',
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
					var outStatusCombobox = ${outStatusCombobox}
					var outStatusCombobox = new A.combobox({
						render: "#outStatus",
						name:"outStatus",
						datasource:outStatusCombobox,
						multiple:false,
						allowBlank:true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var constructionUnitsCombobox = ${constructionUnitsCombobox}
					var constructionUnitsCombobox = new A.combobox({
						render: "#constructionUnits",
						name:"constructionUnits",
						datasource:constructionUnitsCombobox,
						multiple:false,
						allowBlank:true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					var codes=${codes};
					$('#addForm').validate({
						debug:true,
						rules:  {
							name:{required:true,maxlength:64},
							code:{required:true,maxlength:64},
							sex:{required:true,maxlength:64},
							unit:{required:true,maxlength:11},
							mobile:{maxlength:32},
							mail:{maxlength:128},
							outStatus:{required:true},
							remark:{ maxlength:256}
						},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/personalManage/AddUser");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							var code=obj.code;
							/*判断人员工号移到controller中判断*/
							/* if(codes.indexOf(code)>=0){
								alert("添加失败，人员工号不可重复");
								return;
							} */
							//验证手机格式
							if(obj.mobile){
								var patrn = /^1(3|4|5|7|8)\d{9}$/;
								if(!patrn.exec(obj.mobile)){
									alert("手机号格式不正确！");
									return;
								}
							}
							//验证邮箱格式
							if(obj.mail){
								var patrn = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
								if(!patrn.exec(obj.mail)){
									alert("邮箱地址格式不正确！");
									return;
								}
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert(result.data);
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){			
							$("#addForm").submit();		
				});
			});
			});
			function changeUnit(){
				if($("#outStatus").val()==1){
					$("#unitDiv").show();
					$("#unitTitle").show();
				}else{
					$("#unitDiv").hide();
					$("#unitTitle").hide();
				}
			}
        </script>
    </body>
</html>