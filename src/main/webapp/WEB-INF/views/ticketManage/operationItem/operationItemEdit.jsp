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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editItemForm">
<!-- 			<div id="approveDetail" style="display: none;"> -->
<!--  		       <div class="form-group" >  -->
<!--  					<label class="col-md-2 control-label no-padding-right">  -->
<!--  						<span style="color:red;">*</span>模拟  -->
<!--  					</label>  -->
<!--  					<div class="col-md-4">  -->
<!--  					<select id="simulation" class="col-md-12" name="simulation"></select>  -->
<!--                  	</div>  -->
<!--  				    <label class="col-md-2 control-label no-padding-right">  -->
<!--  						<span style="color:red;">*</span>实际  -->
<!--  				    </label>  -->
<!--  				    <div class="col-md-4">  -->
<!--  				    <select id="actual" class="col-md-12" name="actual"></select>  -->
<!--                      </div>  -->
<!--                 </div> -->
<!--                      <div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right"> -->
<!-- 										<span style="color:red;">*</span>终止原因</label> -->
<!-- 									<div class="col-md-10"> -->
<!-- 										<textarea name="reason" placeholder="" id="reason"  -->
<!-- 											style="height:100px; resize:none;" class="col-md-12" -->
<!-- 											maxlength="128"></textarea> -->
<!-- 									</div> -->
<!-- 					</div> -->
<!--             </div> -->
            <div id="editDetail">
		       <div class="form-group" >
					<%-- <label class="col-md-2 control-label no-padding-right">
						顺序
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="order" name="order" type="text" placeholder="" maxlength="64" value="${t.order}">
                	</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>完成时间
					</label>
					<div class="col-md-4">
						<div id="finishDateDiv"></div>
                	</div> --%>
               </div>
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>操作项目
				    </label>
				    <div class="col-md-10">
	                   <textarea class="col-md-12" id="operationItem" name="operationItem" 
	                   placeholder="" maxlength="50" >${t.operationItem}</textarea>
                    </div>
               </div>
             </div>
            </form>
    		<div style="margin-right:100px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="editItemBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			jQuery(function($) {
// 				if($("#status").val()==5||$("#status").val()==7){
// 					$("#approveDetail").show();
// 					$("#editDetail").hide();
// 					var operateItemNum=$("#operateItemNum").val();
// 					if(operateItemNum&&operateItemNum>=$("#operationItem_table tbody tr").length){
// 						$("#simulation").attr("disabled","disabled");
// 						$("#actual").attr("disabled","disabled");
// 					} /* else{
// 						$("#actual").attr("disabled","disabled");
// 					}  */
// 				}
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					//模拟
					var simulationId = new A.combobox({
						render : "#simulation",
						datasource : ${actualtype},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					simulationId.setValue('${t.simulation}');
					//实际
					var actualId = new A.combobox({
						render : "#actual",
						datasource : ${actualtype},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					actualId.setValue('${t.actual}');
					//日期组件
					var finishDatePicker = new A.my97datepicker({
						id: "finishDateId",
						name:"finishDate",
						render:"#finishDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					finishDatePicker.setValue('<fmt:formatDate value="${t.finishDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>');
					$('#editItemForm').validate({
						debug:true,
						rules:  {
 							simulation : {maxlength : 64},
 							actual : {maxlength : 64},
							order : {digits : true,maxlength : 64},
							operationItem : {required : true,maxlength : 64},
							finishDate : {required : false,maxlength : 64},
						},
						submitHandler: function (form) {
							//修改按钮
							var url = format_url("/operationItem/update/"+${t.id});
							var obj = $("#editItemForm").serializeObject();
							obj.operationId=$("#id").val();
							obj.id=${t.id};
							obj.operateItemNum=$("#operateItemNum").val();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){
										alert('修改成功');
										listFormDialog.close();
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('修改失败');
								}
							});
						}
					});
					$("#editItemBtn").on("click", function(){
						$("#editItemForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>