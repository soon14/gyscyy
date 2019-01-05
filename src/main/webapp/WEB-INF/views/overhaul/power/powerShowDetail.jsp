<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%> 
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
	<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">
					检修管理</li>
					<li class="active">
					停送电管理</li>
					<li class="active">查看</li>
				</ul>
		</div>
       <div class="col-md-12" >
			<div style="float:right; margin-right:50px;">
				<button id="btnBack"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="    margin-top: 14px;">
        			<i class="fa fa-reply"></i>
        			返回
        		</button>
			</div>
	<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
	<div class="widget-main no-padding">
 <div class="page-content" style="margin-right:200px;">
   <div class="tabbable" style="margin-top: 20px;">
	 <div class="col-md-12" >
		<form class="form-horizontal" role="form"   id="editForm">
				<input class="col-md-12" id="requestNumber" name="requestNumber" type="hidden"  value="${ entity.requestNumber }" >
				 <input class="col-md-12" id="id" name="id" type="hidden" placeholder=""  value="${ entity.id }">
			     <input class="col-md-12" id="unitName" name="unitName" type="hidden" value="${ entity.unitName }">
			  <div class="col-md-12" >
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					  申请单位
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="unitName" name="unitName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.unitName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    申请停电时间
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="requestDate" name="requestDateString" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestDateString }">
					</div>
				</div>
				
			    <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    停送电联系人
				    </label>
				    <div class="col-md-4">
						 <input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" maxlength="64" readOnly value="${ entity.requestUserName }">
						
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;"></span>是否停送电
				    </label>
				    <div class="col-md-4">
                    	<input class="col-md-12" id="isPowerText" name="isPowerText" type="text" placeholder="" maxlength="64" readOnly value="${ entity.isPowerText }">
					</div>
				</div>
				<div class="form-group">
	                <label class="col-md-2 control-label no-padding-right">
							涉及电压等级
					    </label>
					    <div class="col-md-4" style="padding-top:7px">
						    <c:forEach items="${levelList}" var="list" varStatus="vs">
								<label class=" inline  col-md-2  no-padding-right" style="width: 80%;">
									<input id="powerLevel${list.code}" name="powerLevel" type="checkbox"  value="${list.code}"/>${list.name}
								</label>
							</c:forEach>
						</div>
						<label class="col-md-2 control-label no-padding-right">
						 停送电方式
					    </label>
					    <div class="col-md-4">
					    	<input class="col-md-12" id="powerStatusText" name="powerStatusText" type="text" placeholder="" maxlength="64" readOnly value="${ entity.powerStatusText }">
	                    </div>
			       </div>
				
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    工作任务
				    </label>
				    <div class="col-md-10">
					    <textarea name="powerDec" placeholder="" style="height:100px; resize:none;" class="col-md-12" readOnly maxlength="1024">${ entity.powerDec }</textarea>
				    </div>
			     </div>
    		    <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						  设备所在地点及名称
						</label>
						<div class="col-md-10">
							<textarea name="equipLocalName" id="equipLocalName"placeholder="请输入设备所在地点及名称" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.equipLocalName }</textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							停电范围
						</label>
						<div class="col-md-10">
							<textarea name="powerRange" id="powerRange"placeholder="请输入停电范围" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerRange }</textarea>
						</div>
	               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    要求断开的开关刀闸及应作安全措施
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="请输入要求断开的开关、刀闸及应作安全措施" readOnly style="height:100px; resize:none;" class="col-md-12" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>

			</div>
        	</form>
        </div>              
<!--     		<div style="margin-right:50px;"> -->
<!--     			<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;"> -->
<!--     				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i> -->
<!--     				保存 -->
<!--     			</button> -->
<!--         	</div> -->
        </div>	
        </div>
      </div>
	     
	     <div class="col-xs-12">		     
		<div class="widget-main no-padding">
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >协调情况</h5>				     
		<form class="form-horizontal" role="form"  style="margin-right:200px;" id="approveForm">
			 <input class="col-md-12" id="id" name="id" type="hidden" placeholder=""  value="${ entity.id }">
			<div class="col-md-12" style="margin-top:30px">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    最终停电时间
				    </label>
				     <div class="col-md-4">
							<input class="col-md-12" id="endDateString" name="endDateString" type="text" placeholder="" maxlength="64" readOnly value="${ entity.endDateString }">
	                  </div>
				</div>
			 <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
					  最终设备所在地点及名称
					</label>
					<div class="col-md-10">
						<textarea name="endEquipLocalName" id="endEquipLocalName"placeholder="请输入最终设备所在地点及名称" readOnly style="height:100px;  resize:none;" class="col-md-12" maxlength="1024">${ entity.endEquipLocalName }</textarea>
					</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							最终停电范围
						</label>
						<div class="col-md-10">
							<textarea name="endPowerRange" id="endPowerRange"placeholder="请输入最终停电范围"  style="height:100px; resize:none;" readOnly class="col-md-12" maxlength="1024">${ entity.endPowerRange }</textarea>
						</div>
	               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    最终要求断开的开关刀闸及应作安全措施
				    </label>
				    <div class="col-md-10">
					    <textarea name="measur"  id="measur"  placeholder="请输入要求断开的开关、刀闸及应作安全措施"  style="height:100px; resize:none;" readOnly class="col-md-12" maxlength="128">${ entity.measur }</textarea>
				    </div>
			     </div>
              </div>
     	</form>
     	</div>
   	</div>
   	
   		<div style="margin-right:50px;">
<!--     			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:30px;" data-dismiss="modal"> -->
<!--     				<i class="ace-icon glyphicon glyphicon-remove"></i> -->
<!--     				取消 -->
<!--     			</button> -->
       </div>
   	
 </div>      
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					
					 //电压等级
					var type_value =[]; 
					$("input[name='powerLevel']:checked").each(function(){
							type_value.push($(this).val());
						});
						$("#powerLevel").val(type_value);
						
						//控制回显多选框开始
						var onecheck='${entity.powerLevel}';
						var  powerCheck =onecheck.split(",");
						
						$("input[name='powerLevel']").each(function(){
							if(contains(powerCheck,$(this).val())){
								 $("#powerLevel"+$(this).val()).attr("checked",'true');
							}
						});
						
						//日期组件
						var requestDateDatePicker = new A.my97datepicker({
							id: "endDate",
							render:"#endDateDiv",
							options : {
									isShowWeek : false,
									skin : 'ext',
									maxDate: "",
									minDate: "#F{$dp.$D(\\'requestDate\\')}",
									dateFmt: "yyyy-MM-dd HH:mm"
							}
						}).render();
						requestDateDatePicker.setValue('${entity.endDateString}');
					
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {requestNumber:{required:true},
								equipName:{required:true},
								unitName:{required:true},
								requestDate:{required:true},
								powerStatus:{required:true},
								powerDec:{ maxlength:1024},
								isPower:{required:true},
								equipLocalName:{maxlength:512},
								powerRange:{maxlength:512},
								powerLevel:{maxlength:64},
							endEquipLocalName:{maxlength:1024},
								endDate:{maxlength:64},
								measur:{maxlength:1024},
								endPowerRange:{ maxlength:1024}
						   },
						submitHandler: function (form) {
							var url = format_url("/power/showUpdate/"+id);
							var obj = $("#approveForm").serializeObject();
// 							obj.attchmentIds=JSON.stringify(uploaddropzone.getValue());
							obj.endDate=$('#endDate').val()+":00";
							
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result == "success"){
										alert('修改成功');
										$("#page-container").load(format_url('/power/index'));
									}else{
										alert(result.errorMsg);
									}
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					
					$("#editBtn").on("click", function(){
						if(status == '<%=PowerStatusEnum.EXECUTED.getCode()%>'){
							$('#status').val("<%=PowerStatusEnum.EXECUTED.getCode() %>");
	    					$("#editForm").submit();
						}else if(status == '<%=PowerStatusEnum.REJECT.getCode()%>'){
							$('#status').val("<%=PowerStatusEnum.REJECT.getCode() %>");
	    					$("#editForm").submit();
						}else{
							$('#status').val("<%=PowerStatusEnum.PENDING.getCode() %>");
	    					$("#editForm").submit();
						}
						
    				});
					
					$("#submitBtn").on("click", function(){
						$('#status').val("<%=PowerStatusEnum.OVERHAUL.getCode() %>");
						var url = format_url("/power/beforeSubmit");
						var obj = $("#editForm").serializeObject();
						obj.requestDate=null;
						obj.endDate=null;
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result == "success"){
									$('#status').val("<%=PowerStatusEnum.OVERHAUL.getCode() %>");
									listFormDialog = new A.dialog({
										width: 850,
										height: 481,
										title: "申请审批人",
										url:format_url('/power/sureSubmitPerson'),
										closed: function(resule){
											var obj=new Object(); 
											if(listFormDialog.resule){
												  var userList=listFormDialog.resule.join(",");
												  $('#userList').val(userList);
												  $("#editForm").submit();
											}
										}
									}).render();
								}else{
									alert(result.errorMsg);
								}
							
							},
							error:function(v,n){
								alert('验证失败');
							}
						});
    				});
					
					$("#btnBack").on("click", function(){
						 $("#page-container").load(format_url('/power/index'));
   					});
				});
			});
			
			//判断值是否在数组里的方法zzq
			function contains(arr, obj) {
				  var i = arr.length;
				  while (i--) {
				    if (arr[i] === obj) {
				      return true;
				    }
				  }
				  return false;
			}
        </script>
    </body>
</html>