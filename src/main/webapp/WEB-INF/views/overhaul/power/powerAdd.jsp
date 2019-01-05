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
				<form class="form-horizontal" role="form"   id="addPowerForm" style="margin-right:60px;">
				   <input class="col-md-12" id="requestUserName" name="requestUserName" type="hidden" value="">
				   <input class="col-md-12" id="status" name="status" type="hidden" value="">
				   <input class="col-md-12" id="unitName" name="unitName" type="hidden" value="">
				   <input class="col-md-12" id="userList" name="userList" type="hidden" value="">
				   <input class="col-md-12" id="overhaulPlanId" name="overhaulPlanId" type="hidden" >
			  <!--      <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备编号
						</label>
						<div class="col-md-4">
							<div id="equipNumberDiv">
							</div>
	                	</div>
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="" type="text" placeholder="" readOnly maxlength="64"  value="">
							<input class="col-md-12" id="equipName2" name="equipName" type="hidden" placeholder=""  maxlength="64"  value="">
	                    </div>
	               </div> -->
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>申请单位
						</label>
						<div class="col-md-4" style="width:30%" >
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
						<div class="col-md-4"  style="width:30%">
							<select class="col-md-12 chosen-select" id="requestUserId" name="requestUserId"></select>
<%-- 							<input class="col-md-12" id="requestUserId" name="requestUserId" type="hidden"  maxlength="64"  value="${userEntity.id}"> --%>
<%-- 							<input class="col-md-12" id="requestUserName" name="requestUserName" type="text" placeholder="" readOnly maxlength="64"  value="${userEntity.name}"> --%>
	                	</div>
					    <label class="col-md-2 control-label no-padding-right" style="width:23%" >
							<span style="color:red;">*</span>是否停送电
					    </label>
					    <div class="col-md-4"  style="width:30%">
					    	<select class="col-md-12 chosen-select" id="isPower" name="isPower"></select>
	                    </div>
	               </div>
	               <div class="form-group">
	                <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>涉及电压等级
					    </label>
					    <div class="col-md-4" style="width:30%" >
						    <c:forEach items="${levelList}" var="list" varStatus="vs">
							<%-- 	<label class=" inline  col-md-2  no-padding-right" style="width: 50%;">
									<input id="powerLevel${list.code}" name="powerLevel" type="checkbox"  value="${list.code}">${list.name}</input>
								</label> --%>
								<label class="control-label no-padding-right"> 
									<input id="powerLevel${list.code}" name="powerLevel" type="radio" value="${list.code}" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100">${list.name}</span>
								</label>
							</c:forEach>
						</div>
						
						 <label class="col-md-2 control-label no-padding-right" style="width:23%" >
							<span style="color:red;">*</span>停送电方式
					    </label>
					    <div class="col-md-4" id="powerTypes"   style="width:30%">
					    	<select class="col-md-12 chosen-select" id="powerStatus" name="powerStatus"></select>
	                    </div>
			       </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>工作任务
						</label>
						<div class="col-md-10">
							<textarea name="powerDec" id="powerDec"placeholder="请输入工作任务" style="height:100px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							设备所在地点及名称
						</label>
						<div class="col-md-10">
							<textarea name="equipLocalName" id="equipLocalName"placeholder="请输入设备所在地点及名称" style="height:100px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							停电范围
						</label>
						<div class="col-md-10">
							<textarea name="powerRange" id="powerRange"placeholder="请输入停电范围" style="height:100px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
						</div>
	               </div>
		           <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
						 要求断开的开关刀闸及应作安全措施
						</label>
						<div class="col-md-10">
							<textarea name="remark" id="remark"placeholder="请输入要求断开的开关、刀闸及应作安全措施" style="height:100px; resize:none;" class="col-md-12" maxlength="1024"></textarea>
						</div>
	               </div>
	    			
<!-- 		           <div class="form-group"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right"> -->
<!-- 							备注 -->
<!-- 						</label> -->
<!-- 						<div class="col-md-10"> -->
<!-- 							<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea> -->
<!-- 						</div> -->
<!-- 	               </div> -->
<!-- 			       <div class="form-group form-horizontal"> -->
<!-- 						<label class="col-md-2 control-label no-padding-right"> -->
<!-- 							附件 -->
<!-- 						</label> -->
<!-- 						<div class="col-xs-10" id="divfileId"> -->
<!-- 						</div> -->
<!-- 	               </div> -->
	               
	            </form>
	        </div>
    		<div style="margin-right:100px;">
    			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" style="margin-top:50px;" data-dismiss="modal">
    				<i class="ace-icon glyphicon glyphicon-remove"></i>
    				取消
    			</button>
    			<button id="savepoBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;margin-top:50px;">
    				<i class="ace-icon glyphicon glyphicon-floppy-disk"></i>
    				保存
    			</button>
    	
    		</div>
		<script type="text/javascript">
			var listFormDialog;
			var unitNameCombox;
			var requestUserId;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox','uploaddropzone'], function(A){
		        
					//电压等级
					var type_value =[]; 
					$("input[name='powerLevel']:checked").each(function(){
							type_value.push($(this).val());
						});
					$("#powerLevel").val(type_value);
					
					//附件上传
// 					var uploaddropzone =new A.uploaddropzone({
// 						render : "#divfileId",
// 						fileId:[],
// 						autoProcessQueue:true,//是否自动上传
// 						addRemoveLinks : true,//显示删除按钮
// 					}).render();
					
					//combobx组件
					 unitNameCombox = new A.combotree({
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
						},callback:function(data){
						}
						
					}).render();
// 					unitNameCombobox.setValue(${userEntity.unitId});
// 					$("#unitName").val('${unitEntity.name}');

// 					 $(".aptech-combotree-dropdown").on('click', function(){
// 						 $(".aptech-combotree .select-close").on('click', function(){
// 								getUserList();
// 							});
// 							getUserList();
// 					 });
// 					 $(".aptech-combotree-dropdown").css("width","98%");
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
					
					//addPowerForm.settings.rules["powerStatus"]={required:true};
					$("#isPower").change(function() {
						var isPower = $("#isPower").val();
						if ( isPower=="0") {
							$("#powerStatusLableNeed").show();
							addPowerForm.settings.rules["powerStatus"]={required:true};
						} else {
							addPowerForm.settings.rules["powerStatus"]={};
							$("#powerStatusLableNeed").hide();
						}
					
					});
					
		/* 			$("input[name='powerLevel']").click(function(){
						if($(this).is(':checked')){
							var obj = $("input[name='powerLevel']");
							obj.attr('checked',false);
							//$(this).attr('checked',true).siblings().attr('checked',false);
							alert($(this).val());
							$(":checkbox[name='powerLevel'][value='" +$(this).val()+"']").prop("checked", "checked");
						}else{
							$(this).attr('checked',false).siblings().attr('checked',false);
						}
					}); */
					
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
					
					//申请人
					 requestUserId = new A.combobox({
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
// 					requestUserId.setValue(${userEntity.id});
// 					$('#requestUserName').val('${userEntity.name}');

					//日期组件
					var requestDateDatePicker = new A.my97datepicker({
						id: "requestDate",
						render:"#requestDateDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					requestDateDatePicker.setValue('${date}');
					$('#requestDate').val('${date}');
					
					//设备列表初始化
					var equipNumbeSelectBox = new A.selectbox({
						id: 'equipNumberDiv',
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
					
					
					var addPowerForm = $('#addPowerForm').validate({
						debug:true,
						rules:  {
							unitId:{required:true},
							requestDate:{required:true},
							requestUserId:{ required:true},
							equipName:{required:true},
							powerDec:{ required:true,maxlength:1024},
							isPower:{required:true},
							powerStatus:{required:true},
							equipLocalName:{maxlength:512},
							powerRange:{maxlength:512},
							//powerLevel:{maxlength:64,required:true}
						},
						    submitHandler: function (form) {
						    	if(overhaulPlanId){
									$("#overhaulPlanId").val(overhaulPlanId);
								}
							var url = format_url("/power/save");
							var obj = $("#addPowerForm").serializeObject();
							obj.requestDate = $('#requestDate').val()+":00";
// 							obj.attchmentIds=JSON.stringify(uploaddropzone.getValue());
							var powerLevel = $("input[name='powerLevel']:checked").val();
							if (powerLevel == undefined || powerLevel == null || powerLevel =="") {
								alert("请选择涉及电压等级");
								return;
							}
							debugger;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result == "success"){
										 alert('添加成功');
										 powerDatatables.draw(false);
										 listFormDialog.close();
// 										 $("#page-container").load(format_url('/power/index'));
									}else{
										alert(result.errorMsg);
									}
								
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
							
						}
					});
					
					$("#savepoBtn").on("click", function(){
						$('#status').val("<%=PowerStatusEnum.PENDING.getCode() %>");
						$("#addPowerForm").submit();
    				});
					
					$("#submitBtn").on("click", function(){
						$('#status').val("<%=PowerStatusEnum.OVERHAUL.getCode() %>");
						var url = format_url("/power/beforeSubmit");
						var obj = $("#addForm").serializeObject();
						obj.requestDate = $('#requestDate').val()+":00";
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								if(result.result == "success"){
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
												  $("#addForm").submit();
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
					
// 					$("#btnBack").on("click", function(){
// 						 $("#page-container").load(format_url('/power/index'));
//     				});
    				
				});
			});
			function getUserList(){
				console.log(0);
				var selectunitId= 0;
				if(unitNameCombobox.getValue()!=null && unitNameCombobox.getValue()!="" && unitNameCombobox.getValue()!=0){
					selectunitId = unitNameCombobox.getValue();
					$.ajax({
						url: format_url("/overhaulLog/getUserList/"+selectunitId),
						contentType: "application/json",
						dataType: 'JSON',
						type: 'POST',
						data :"",
						success: function(result){
							var userList = eval(result.data);
							requestUserId = new A.combobox({
								render : "#requestUserId",
								datasource :userList,
								allowBlank: true,
								options : {
									"disable_search_threshold" : 10
								},
								callback: function(data){
								
								}
							}).render();
						}
					});
				}else{
					requestUserId = new A.combobox({
						render : "#requestUserId",
						datasource :${requestUsers},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						},
						callback: function(data){
						
						}
					}).render();
				}
			}
        </script>
    </body>
</html>