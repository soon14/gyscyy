<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
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
					<li class="active">运行管理</li>
					<li class="active">保护投退</li>
					<li class="active">新增</li>
				</ul><!-- /.breadcrumb -->
		</div>
	<div class="page-content">
		<div class="col-md-12">		
			<div style="float:right; margin-top:10px;margin-right:100px;">
								<button id="button"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" >
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div> 		
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:10px;' >基础信息</h5>		     
		<form class="form-horizontal" role="form" id="addForm" style="margin-right:210px;margin-top: 30px;">
		<input id="dispatchCommandId" name="dispatchCommandId" type="hidden"/>
		<input class="col-md-12" id="dispatchTitleVal" name="dispatchTitle" type="hidden">
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>单位名称
					</label>
					<div class="col-md-4">
					   <select id="unitIdDiv" name="unitId" class="col-md-12 chosen-select"></select>
                	</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备编号
				    </label>
                    <div id="equipNumberDiv" class="col-md-4"></div>
				<input class="col-md-12" id="deviceId" name="deviceId" type="hidden">									
               </div>
		       <div class="form-group">
			       <label class="col-md-2 control-label no-padding-right">设备名称</label>
					<div class="col-md-4">
					<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="64" value="" readonly="readonly">
					</div>
						<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备电压(kV)
					</label>
					<div class="col-md-4">
					<input class="col-md-12" id="equipVoltage" name="equipVoltage" type="text" placeholder="" maxlength="64" value="" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))"
						>
					</div>
<!-- 					<div class="col-md-4"> -->
<!--                 	<select id="equipVoltageDiv" class="col-md-12 chosen-select" name="equipVoltage" data-placeholder="请选择设备电压" ></select>					                   -->
<!--                 	</div> -->
               </div>
		       <div class="form-group">
		        <label class="col-md-2 control-label no-padding-right">
					<span style="color:red;">*</span>申请人
				    </label>
				    <div class="col-md-4">
					   	<select id="applyPersonIdDiv" class="col-md-12 chosen-select" name="applyPersonId" data-placeholder="请选择申请人"></select>					                       
                    </div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>申请时间
					</label>
					<div class="col-md-4">
					   <div id="applyTimeDiv"></div>
                	</div>			   
               </div>
               <div class="form-group">
			       <label class="col-md-2 control-label no-padding-right">
			      			 <span style="color:red;">*</span>申请类别</label>
					<div class="col-md-4">
					<select id="applyTypeDiv" class="col-md-12 chosen-select" name="applyType" data-placeholder="请选择申请类别"></select>					          
					</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>保护变动方式
					</label>
					<div class="col-md-4">
                	<select id="protectWayDiv" class="col-md-12 chosen-select" name="protectWay" data-placeholder="请选择保护变动方式"></select>					                  
                	</div>
               </div>
               <div class="form-group">
						<label class="col-md-2 control-label no-padding-right" for="form-field-1">发文标题</label>
						<div class="col-md-4">
							<div id="dispatchTitleDiv" name="dispatchId"></div>
						</div>
						<label class="col-md-2 control-label no-padding-right" for="form-field-1">发文字号</label>
				   		<div class="col-md-4">
							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="" readonly>
						</div>
               </div>
          <div id="applyTypechange">
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>发令人
					</label>
<!-- 				<div id="dispatchPersonDiv" class="col-md-4"></div> -->
					<div class="col-md-4">
					    <input class="col-md-12" id="dispatchPerson" name="dispatchPerson" type="text"  maxlength="64"  placeholder="请输入发令人" readonly="readonly">
					</div>    
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>受令人
				    </label>
<!--                        <div id="dutyChiefIdDiv" class="col-md-4"></div> -->
				    <div class="col-md-4">
                       <input class="col-md-12" id="dutyChiefPerson" name="dutyChiefPerson" type="text"  maxlength="64"  placeholder="请输入受令人" readonly="readonly">
<!--                        <input class="col-md-12" id="dutyChiefPersonName" name="dutyChiefPersonName" type="text"  maxlength="64"  placeholder="请输入受令人" readonly="readonly"> -->
                    </div>   
               </div>
                <div class="form-group">
                	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度
				    </label>
                   		 <div id="dispathDiv" class="col-md-4"></div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度时间
					</label>
					   <div id="timeDiv" class="col-md-4"></div>
				   
               </div>
               
            </div>
                <div class="form-group">              
                <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>保护变动原因
				    </label>
				    <div class="col-md-10">
                    <textarea placeholder="请输入保护变动原因" name="protectReason"  class="col-md-12" style="height:90px; resize:none;"></textarea>                   
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>保护变动内容
					</label>
					<div class="col-md-10">
                	   <textarea placeholder="请输入保护变动内容" name="protectContent"  class="col-md-12" style="height:90px; resize:none;"></textarea>
                	</div>
               </div>
               
               <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
                	   <textarea placeholder="请输入保护变动内容" name="remarks"  class="col-md-12" style="height:90px; resize:none;"></textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
				</div>
            </form>            
    		<div style="margin-right:100px;">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		</div>
		<script type="text/javascript">
			var unitIdCombotree;
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','selectbox','my97datepicker','uploaddropzone'], function(A){
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//单位
					 searchunitid = new A.combobox({
						render : "#unitIdDiv",
						datasource : ${unitNameIdTreeList},
						allowBlank: true,
						options : {
							"disable_search_threshold" : 10
						}
					}).render();
					//combotree组件
// 					unitIdCombotree = new A.combotree({
// 						render: "#unitIdDiv",
// 						name: "unitId",
// 						//返回数据待后台返回TODO
// 						datasource: ${protectTreeList},
// 						width:"210px",
// 						options: {
// 							treeId: 'unitId',
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
// 					unitIdCombotree.setValue(${userEntity.unitId});
					var applyTypeCombobox = new A.combobox({
						render: "#applyTypeDiv",
						datasource:${applyTypeCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
					}).render();
					$("#applyTypechange").hide();
					applyTypeCombobox.change(function(){
						if(applyTypeCombobox.getValue()==0){
							$("#applyTypechange").show();
						}else{
							$("#applyTypechange").hide();
						}
						
					});
					var equipVoltageCombobox = new A.combobox({
						render: "#equipVoltageDiv",
						datasource:${equipVoltageCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//combobox组件
					var planPersonCombobox = new A.combobox({
							render: "#applyPersonIdDiv",
							datasource:${protectCombobox},
							options:{
								"disable_search_threshold":10
							}
						}).render();
					planPersonCombobox.setValue(${userEntity.id});
					var protectWayCombobox = new A.combobox({
						render: "#protectWayDiv",
						datasource:${protectWayCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					//日期组件
					var applyTimeDatePicker = new A.my97datepicker({
						id: "applyTimeId",
						name:"applyTime",
						render:"#applyTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					applyTimeDatePicker.setValue('${giveDate}');
					var selectDispatchTitle = new A.selectbox({
						id: 'selectDispatchTitle',
						name: 'dispatchId',
						title:'发文标题',
						url:'/trainManagement/gotoSelectDispatchPage',
						render:'#dispatchTitleDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							if(data && data[0]){
								$("#dispatchNumber").val (data[0].dispatchName);
								$("#dispatchTitleVal").val (data[0].title);
								self.setValue(data[0].title,data[0].id);
							};
						}
					}).render();
					var equipIds = [];
					var equipNumberList = "";
					var equipName = "";
					var deviceId = "";
					//设备
					var selecttreeDh = new A.selectbox({
						id: 'equipNumber',
						name:'equipNumber',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger?userUnitRels='+userUnitRels,
						render:'#equipNumberDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data.length>0){
								equipNumberList="";
	                            equipName="";
	                            deviceId="";
							}
							for(var i=0; i< data.length;i++){
								 equipNumberList += data[i].code+',';
								 equipName += data[i].name+',';
								 deviceId += data[i].id+',';
							}
							selecttreeDh.setValue(equipNumberList,equipNumberList);
							$("#equipNumber").val(equipNumberList);
							$("#equipName").val(equipName);
							$("#deviceId").val(deviceId);
						}
					}).render();
					//调度
					new A.selectbox({
						id: 'dispath',
						name:'dispath',
						title:'调度命令',
						url:'/dispaCom/selectindex',
						render:'#dispathDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								$("#dispatchCommandId").val(data[0].id);
								$("#dispatchPerson").val(data[0].dispatchPerson);
								$("#dispath").val(data[0].dispathName);
								$("#time").val(data[0].time);
								$("#dutyChiefPerson").val(data[0].dutyChiefPerson);
							};							
						}
					}).render();
					//调度时间
					new A.selectbox({
						id: 'time',
						name:'time',
						title:'调度命令',
						url:'/dispaCom/selectindex',
						render:'#timeDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							if(data&&data[0]){
								$("#dispatchCommandId").val(data[0].id);
								$("#dispatchPerson").val(data[0].dispatchPerson);
								$("#dispath").val(data[0].dispathName);
								$("#time").val(data[0].time);
								$("#dutyChiefPerson").val(data[0].dutyChiefPerson);
							};							
						}
					}).render();
					var flag = false;
					searchunitid.change(function(event, value){
							if(flag){
								A.confirm('您确认改变单位名称么？(改变后现有设备编码和设备名称将会清空)',function(){
									selecttreeDh.setValue();
									$("#equipName").val(null);
								});
							}
							flag=true;
						});
					$('#addForm').validate({
						debug:true,
						rules:  {unitId:{required:true,maxlength:20},
							deviceId:{required:true,maxlength:200},
							protectWay:{required:true,maxlength:20},
							applyPersonId:{required:true,maxlength:20},
							applyTime:{required:true,date:true},
							protectReason:{required:true,maxlength:128},
							protectContent:{required:true,maxlength:128},
							applyType:{required:true,maxlength:128},
							equipVoltage:{digits:true,required:true,maxlength:128},
// 							dispatchPerson:{required:true,maxlength:128},
// 							dispath:{required:true,maxlength:128},
// 							time:{required:true,maxlength:128},
// 							dutyChiefPerson:{required:true,maxlength:128},
						},
						submitHandler: function (form) {
							if(applyTypeCombobox.getValue()==0){
								if(!$("#dispatchPerson").val()){
									alert("请选择调度信息!");
									return;
								}
							}
							//添加按钮
							var url = format_url("/protect/save");
							var obj = $("#addForm").serializeObject();
							obj.fileId=JSON.stringify(uploaddropzone.getValue());
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									if(result.result=="success"){

									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/protect/index")
									});
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
					$("#saveBtn").on("click", function(){
						var equipVoltage = $("#equipVoltage").val();
						if($("#equipVoltage").val()=="35"||$("#equipVoltage").val()=="110"){
							
						}else{
							alert("设备电压只能填写35或者110");
							return;
						}
						$("#addForm").submit();
    				});
					$('#button').on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/protect/index")
						});
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