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
				<input  type="hidden" id="zbidAdd" value="${zbidAdd}" name="contractManageID" />
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>未付合同金额（元）
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="wfhtje" name="wfhtje" type="text" readonly="readonly" maxlength="64" value="${nopayMoney }">
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>第几次付款
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="xh" name="xh" type="text" readonly="readonly" maxlength="32" value="${xh }">
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>付款时间
                    </label>
					<div class="col-md-9">
						<div id="fksjDiv"></div>
	                    <!-- <input class="col-md-12" id="fksj" name="fksj" type="text" placeholder="" maxlength="0" value=""> -->
                    </div>
                </div>
		        <div class="form-group">
					<label class="col-md-3 control-label no-padding-right">
						<span style="color:red;">*</span>付款金额
                    </label>
					<div class="col-md-9">
	                    <input class="col-md-12" id="fkje" name="fkje" type="text" placeholder="" maxlength="64" value="">
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
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					
					
					//日期组件
					var fksjPicker = new A.my97datepicker({
						id: "fksjId",
						name:"fksj",
						render:"#fksjDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd"
						}
					}).render();
					
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							wfhtje:{required:true,maxlength:64,number:true},
							xh:{required:true,maxlength:2,digits:true},
							fksj:{required:true,maxlength:64},
							fkje:{required:true,maxlength:64,number:true},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/contractFkjl");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.status=1;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFkjlDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveBtn").on("click", function(){
						var wfhtje=$("#wfhtje").val();
						var fkje=$("#fkje").val();
						if(parseFloat(fkje)<=parseFloat(0)){
							alert("付款金额必须大于0!");
							return;
						}
						
						if(parseFloat(fkje)>parseFloat(wfhtje)){
							alert("付款金额不能大于未付款金额!");
							return;
						}
						$("#addForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>