<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%> 
<%@ page import="com.aptech.business.component.dictionary.PowerStatusEnum"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
	 <div class="col-md-12" >
		<form class="form-horizontal" role="form"   id="editPowerForm" style="margin-right:90px;">
				<input class="col-md-12" id="requestNumber" name="requestNumber" type="hidden"  value="${ entity.requestNumber }" >
				<input class="col-md-12" id="status" name="status" type="hidden" value="${ entity.status }">
				 <input class="col-md-12" id="id" name="id" type="hidden" placeholder=""  value="${ entity.id }">
				 <input class="col-md-12" id="userList" name="userList" type="hidden" placeholder="" >
			     <input class="col-md-12" id="unitName" name="unitName" type="hidden" value="${ entity.unitName }">
			       <input id="overhaulPlanId" name="overhaulPlanId" type="hidden"  value="${ entity.overhaulPlanId }"/>
<!-- 			     <input class="col-md-12" id="requestUserName" name="requestUserName" type="hidden" value="${ entity.requestUserName }"> -->
			  <div class="col-md-12" >
			  
<!-- 			   <div class="form-group"> -->
<!-- 				    <label class="col-md-1 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;">*</span>申请编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 					    <input class="col-md-12" id="requestNumber" name="requestNumber" type="text" placeholder="" maxlength="64" value="${ entity.requestNumber }" readOnly> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			   <div class="form-group"> -->
<!-- 				    <label class="col-md-1 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;">*</span>设备编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<!-- 					    <div id="equipNumberDiv"> -->
<!-- 						</div> -->
<!-- 				    </div>	 -->
<!-- 				    <label class="col-md-2 control-label no-padding-right"> -->
<!-- 					    <span style="color:red;">*</span>设备名称 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 					    <input class="col-md-12" id="equipName"  type="text" placeholder="" maxlength="64" readOnly value="${ entity.equipName }"> --%>
<%-- 					    <input class="col-md-12" id="equipName2" name="equipName" type="hidden" placeholder="" maxlength="64"  value="${ entity.equipName }"> --%>
<!-- 					</div> -->
<!-- 				</div> -->
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>申请单位
				    </label>
				    <div class="col-md-4"  style="width:30%" >
					     <div class="" id="unitIdNameDiv" >
	                   	 </div>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right" style="width:23%" >
					    <span style="color:red;">*</span>申请停电时间
				    </label>
				    <div class="col-md-4" style="width:30%" >
					    <div id="requestDateDiv"></div>
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					     <span style="color:red;">*</span>停送电联系人
				    </label>
				    <div class="col-md-4" style="width:30%" >
						<select class="col-md-12 chosen-select" id="requestUserId" name="requestUserId"></select>
<%-- <input class="col-md-12" id="requestUserId" name="requestUserId" type="hidden"  maxlength="64"  value="${entity.requestUserId}"> --%>
<%-- 							<input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" readOnly maxlength="64"  value="${entity.requestUserName}"> --%>
				    </div>	
				    <label class="col-md-2 control-label no-padding-right" style="width:23%">
					    <span style="color:red;">*</span>是否停送电
				    </label>
				    <div class="col-md-4" style="width:30%" >
					  	<select class="col-md-12 chosen-select" id="isPower" name="isPower"></select>
					</div>
				</div>
				   <div class="form-group">
	                <label class="col-md-2 control-label no-padding-right">
							 <span style="color:red;">*</span>涉及电压等级
					    </label>
					    <div class="col-md-4" style=" width:31%">
						    <c:forEach items="${levelList}" var="list" varStatus="vs">
								<%-- <label class=" inline  col-md-2  no-padding-right" style="width: 50%;">
									<input id="powerLevel${list.code}" name="powerLevel" type="radio"  value="${list.code}">${list.name}</input>
								</label> --%>
								<label class="control-label no-padding-right"> 
									<input id="powerLevel${list.code}" name="powerLevel" type="radio" value="${list.code}" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100">${list.name}</span>
								</label>
							</c:forEach>
						</div>
						<label class="col-md-2 control-label no-padding-right" style="width:22%">
							<span style="color:red;" id="powerStatusLableNeed">*</span>停送电方式
					    </label>
					    <div class="col-md-4" id="powerTypes" style="width:30%">
					    	<select class="col-md-12 chosen-select" id="powerStatus" name="powerStatus"></select>
	                    </div>
			       </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>工作任务
				    </label>
				    <div class="col-md-10">
					    <textarea name="powerDec" placeholder="请输入工作任务" style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerDec }</textarea>
				    </div>
			     </div>
			     <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						  设备所在地点及名称
						</label>
						<div class="col-md-10">
							<textarea name="equipLocalName" id="equipLocalName"placeholder="请输入设备所在地点及名称" style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerDec }</textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							停电范围
						</label>
						<div class="col-md-10">
							<textarea name="powerRange" id="powerRange"placeholder="请输入停电范围" style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerDec }</textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						 要求断开的开关刀闸及应作安全措施
						</label>
						<div class="col-md-10">
							<textarea name="remark" id="remark"placeholder="请输入要求断开的开关、刀闸及应作安全措施" style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.powerDec }</textarea>
						</div>
	               </div>
    		    
<!-- 			      <div class="form-group"> -->
<!-- 				    <label class="col-md-1 control-label no-padding-right"> -->
<!-- 					    备注 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-10"> -->
<%-- 					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="1024">${ entity.remark }</textarea> --%>
<!-- 				    </div> -->
<!-- 			     </div> -->
<!-- 			   <div class="form-group"> -->
<!-- 				    <div class="form-group form-horizontal"> -->
<!-- 					<label class="col-md-1 control-label no-padding-right"> -->
<!-- 						附件 -->
<!-- 					</label> -->
<!-- 					<div class="col-xs-10" id="divfile"> -->
<!-- 					</div> -->
<!--                		</div>	 -->
<!-- 				</div> -->
				
				 </div>
        	</form>
        </div>              
    		<div style="margin-right:50px;">
    			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:50px;" data-dismiss="modal">
    				<i class="ace-icon glyphicon glyphicon-remove"></i>
    				取消
    			</button>
    			<button id="editpoBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
<!--     			<button id="submitBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;"> -->
<!--     				<i class="ace-icon glyphicon glyphicon-ok"></i> -->
<!--     				提交审批 -->
<!--     			</button> -->
        	</div>
        	
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
					var appData = ${entityJson};
					var id = ${ entity.id };
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
						
					//附件上传
// 					var uploaddropzone =new A.uploaddropzone({
// 						render : "#divfile",
// 						fileId:${entity.attchmentIds},
// 						maxFilesize:1,
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 					}).render();
					
					//停送电类型
					var powerlist = new A.combobox({
						render : "#isPower",
						datasource : ${isPower},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							
						}
					}).render();
					powerlist.setValue('${entity.isPower}');
					
					$("#isPower").change(function() {
						var isPower = $("#isPower").val();
						if ( isPower=="0") {
							$("#powerStatusLableNeed").show();
							editPowerForm.settings.rules["powerStatus"]={required:true};
						} else {
							editPowerForm.settings.rules["powerStatus"]={};
							$("#powerStatusLableNeed").hide();
						}
					
					});
					
					//停送电类型
					var powerTypeslist = new A.combobox({
						render : "#powerStatus",
						datasource : ${powerTypes},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							
						}
					}).render();
					powerTypeslist.setValue('${entity.powerStatus}');
        			
					var status = '${entity.status}';
				
					//单位下拉树
        			var unitNameCombobox = new A.combotree({
						render: "#unitIdNameDiv",
						name: 'unitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"210px",
						options: {
							treeId: 'unitId',
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
						},
						callback: function(data){
							if(data&&data[0]){
								$("#unitName").val(data[0].name);
							};
							
						}
					}).render();
					unitNameCombobox.setValue(${entity.unitId});

// 					申请人
					var requestUserId = new A.combobox({
						render : "#requestUserId",
						datasource : ${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
							if(data&&data[0]){
								$("#requestUserName").val(data[0].Name);
							};
							
						}
					}).render();
					requestUserId.setValue(${entity.requestUserId});
        			
					//日期组件
					var requestDateDatePicker = new A.my97datepicker({
						id: "requestDate",
						name:"requestDate",
						render:"#requestDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					requestDateDatePicker.setValue('${entity.requestDateString}');
					$('#requestDate').val('${entity.requestDateString}');
					
                    
					
					//设备列表
					var equipNumberDiv = new A.selectbox({
						id: 'equipNumber',
						name:'equipNumber',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipNumberDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							if(data&&data[0]){
								$("#equipNumber").val(data[0].code);
								$("#equipName").val(data[0].name);
								$("#equipName2").val(data[0].name);
								self.setValue(data[0].code);
							};
							
						}
					}).render();
// 					$("#equipNumber").val('${entity.equipNumber}');
					
					equipNumberDiv.setValue('${entity.equipNumber}');
				
					var editPowerForm = $('#editPowerForm').validate({
						debug:true,
						rules: {requestNumber:{required:true},
								equipName:{required:true},
								unitName:{required:true},
								requestDate:{required:true},
								//powerStatus:{required:true},
								powerDec:{ maxlength:1024},
								isPower:{required:true},
								equipLocalName:{maxlength:512},
								powerRange:{maxlength:512},
								//powerLevel:{maxlength:64,required:true}
						   },
						submitHandler: function (form) {
							var url = format_url("/power/update/"+id);
							var obj = $("#editPowerForm").serializeObject();
// 							obj.attchmentIds=JSON.stringify(uploaddropzone.getValue());
							obj.requestDate=$('#requestDate').val()+":00";
							
							var powerLevel = $("input[name='powerLevel']:checked").val();
							if (powerLevel == undefined || powerLevel == null || powerLevel =="") {
								alert("请选择涉及电压等级");
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
									if(result.result == "success"){
										alert('修改成功');
										 powerDatatables.draw(false);
										 listFormDialog.close();
// 										$("#page-container").load(format_url('/power/index'));
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
					
					var isPower = $("#isPower").val();
					if ( isPower=="0") {
						$("#powerStatusLableNeed").show();
						editPowerForm.settings.rules["powerStatus"]={required:true};
					} else {
						editPowerForm.settings.rules["powerStatus"]={};
						$("#powerStatusLableNeed").hide();
					}
					
					$("#editpoBtn").on("click", function(){
						$("#editPowerForm").submit();
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