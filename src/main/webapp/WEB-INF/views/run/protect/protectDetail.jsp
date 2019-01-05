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
		<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li class="active">运行管理</li>
					<li class="active">保护投退</li>
					<li class="active">查看</li>
				</ul><!-- /.breadcrumb -->
		</div>
<div class="page-content">		
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>

		<div class="col-md-12" style="margin-top: 30px;">
		<div style="float:right; margin-top:-70px;margin-right:100px;">
								<button id="button"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" >
				        			<i class="fa fa-reply"></i>
				        			返回
				        		</button>
							</div>
			<form class="form-horizontal" role="form"  style="margin-right:210px;" id="editForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
			
			   <div class="form-group">
				    	
				    <label class="col-md-2 control-label no-padding-right">
					    申请编号
				    </label>
				    <div class="col-md-4">
				    			<input  id="unitName" name="unitName" type="text" value="${dataMap.code}" class="col-md-12" readonly="readonly">				    
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
					    单位名称
				    </label>
				    <div class="col-md-4">
				    			<input  id="unitName" name="unitName" type="text" value="${dataMap.unitName}" class="col-md-12" readonly="readonly">				    
				    </div>
				</div>
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					    设备名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="20" value="${dataMap.equipName}" readonly="readonly">
					</div>
				   	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>设备电压(kV)
					</label>
					<div class="col-md-4">
                	<input class="col-md-12" id="equipVoltage" name="equipVoltage" type="text" placeholder="" maxlength="64" value="${dataMap.equipVoltage }" readonly="readonly" >				                  
                	</div>
				</div>
			   <div class="form-group">
			    <label class="col-md-2 control-label no-padding-right">
					    申请人
				    </label>
				    <div class="col-md-4">
				    	<select id="applyPersonIdDiv" class="col-md-12" name="applyPersonId" disabled="disabled"></select>

					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    申请时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="applyTime" name="applyTime" type="text" placeholder="${formField.toolTip}" maxlength="20"  readonly="readonly" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.applyTime}" type="date"/>'>				    				    
				    </div>				   
				</div>
			<div class="form-group">
			       <label class="col-md-2 control-label no-padding-right">
			       			<span style="color:red;">*</span>申请类别</label>
					<div class="col-md-4">
					<select id="applyTypeDiv" class="col-md-12 chosen-select" name="applyType" data-placeholder="请选择保护变动方式" disabled="disabled"></select>					          
					</div>
					<label class="col-md-2 control-label no-padding-right">
					    保护变动方式
				    </label>
				    <div class="col-md-4">
                	<select id="protectWayDiv" class="col-md-12 chosen-select" name="protectWay" data-placeholder="请选择保护变动方式" disabled="disabled"></select>					                  				    
				    </div>	
               </div>
               <div class="form-group">
						<label class="col-md-2 control-label no-padding-right" for="form-field-1">发文标题</label>
						<div class="col-md-4">
<!-- 							<div id="dispatchTitleDiv" name="dispatchId"></div> -->
							<input class="col-md-12" id="dispatchTitle" name="dispatchTitle" type="text"  value="${dataMap.dispatchTitle }" readonly>
						</div>
						<label class="col-md-2 control-label no-padding-right" for="form-field-1">发文字号</label>
				   		<div class="col-md-4">
							<input class="col-md-12" id="dispatchNumber" name="dispatchNumber" type="text"  value="${dataMap.dispatchNumber }" readonly>
						</div>
               </div>
             <div id="applyTypechange">
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>发令人
					</label>
					  	<div class="col-md-4">
						<input  id="dispatchPerson" name="dispatchPerson" type="text" value="${dispaComEntity.dispatchPerson}" class="col-md-12" readonly="readonly">				    
				   		</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>受令人
				    </label>
				    	<div class="col-md-4">
<%-- 						<input  id="dutyChiefPerson" name="dutyChiefPerson" type="text" value="${sysUser.name}" class="col-md-12" readonly="readonly">	 --%>
						<input class="col-md-12" id="dutyChiefPersonDiv" name="dutyChiefPerson"
						type="text"  maxlength="64"  placeholder="请输入受令人" value="${dispaComEntity.dutyChiefPerson}" readonly="readonly">			    
				   		</div>
               </div>
                <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度
				    </label>
				   	 	<div class="col-md-4">
						<input  id="dispath" name="dispath" type="text" value="${dispaComEntity.dispathName}" class="col-md-12" readonly="readonly">				    
				   		</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度时间
					</label>
						<div class="col-md-4">
						<input  id="time" name="time" type="text" value="<fmt:formatDate value="${dispaComEntity.time}" type="both" pattern="yyyy-MM-dd HH:mm"/>" class="col-md-12" readonly="readonly">				    
				   		</div>
               </div>
             </div>
			 <div class="form-group">				
				 <label class="col-md-2 control-label no-padding-right">
					    保护变动原因
				    </label>
				    <div class="col-md-10">
				     <textarea placeholder="请输入保护变动内容" name="protectReason"  class="col-md-12" style="height:90px; resize:none;" readonly="readonly">${dataMap.protectReason}</textarea>
					</div>
					</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    保护变动内容
				    </label>
				    <div class="col-md-10">
					<textarea placeholder="请输入保护变动内容" name="protectContent"  class="col-md-12" style="height:90px; resize:none;" readonly="readonly">${dataMap.protectContent}</textarea>
				    </div>
				</div>	
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
                	   <textarea placeholder="请输入保护变动内容" name="remarks"  class="col-md-12" style="height:90px; resize:none;"  readonly="readonly" >${dataMap.remarks}</textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfile">
								</div>
				</div>
				<div class="form-group">					
				    <label class="col-md-2 control-label no-padding-right">
						执行人
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="executePersonId" name="executePersonId" disabled="disabled"></select>
                    </div>
                     <label class="col-md-2 control-label no-padding-right">
						监护人
				    </label>
				    <div class="col-md-4">
				    	<select class="col-md-12 chosen-select" id="wardPersonId" name="wardPersonId" disabled="disabled"></select>
                    </div>
               </div>
               <div class="form-group">
		        <label class="col-md-2 control-label no-padding-right">
						执行结束时间
					</label>
					<div class="col-md-4">
					   <div id="executeEndtimeDiv"></div>
				    <input class="col-md-12" id="executeEndtime" name="executeEndtime" type="text" placeholder="${formField.toolTip}" maxlength="20" value='<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${dataMap.executeEndtime}" type="date"/>' readonly="readonly">	
                	</div>					   				   			  
				</div>		    
               <div class="form-group">
  				<label class="col-md-2 control-label no-padding-right">
						完成情况
				    </label>	              
                <div class="col-md-10">
                    <textarea placeholder="请输入完成情况" name="finishSituation"  class="col-md-12" style="height:150px; resize:none;" readonly="readonly">${dataMap.finishSituation}</textarea>                   				    
                    </div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						备注
					</label>
					<div class="col-md-10">
                	   <textarea placeholder="请输入保护变动内容" name="executeRemarks"  class="col-md-12" style="height:90px; resize:none;">${dataMap.executeRemarks}</textarea>
                	</div>
               </div>
               <div class="form-group form-horizontal">
								<label class="col-md-2 control-label no-padding-right">附件</label>
								<div class="col-xs-10" id="divfileExecute">
								</div>
				</div>
			</form>
        </div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone'], function(A){
        			//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${dataMap.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					//附件上传
					var uploaddropzoneExecute =new A.uploaddropzone({
						render : "#divfileExecute",
						fileId:${dataMap.executeFileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render('Other');
					var applyTypeCombobox = new A.combobox({
						render: "#applyTypeDiv",
						datasource:${applyTypeCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.applyType}"
					}).render();
					var equipVoltageCombobox = new A.combobox({
						render: "#equipVoltageDiv",
						datasource:${equipVoltageCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.equipVoltage}"
					}).render();
					//combotree组件
					var unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: ${protectTreeList},
						width:"210px",
						disabled:true,
						options: {
							treeId: "unitId",
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
					unitIdCombotree.setValue($("#unit").val());
					var planPersonCombobox = new A.combobox({
						render: "#applyPersonIdDiv",
						datasource:${protectCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.applyPersonId}"
					}).render();
					
					var wardPersonIdCombobox = new A.combobox({
						render: "#wardPersonId",
						datasource:${protectCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.wardPersonId}"
					}).render();
					var executePersonIdCombobox = new A.combobox({
						render: "#executePersonId",
						datasource:${protectCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.executePersonId}"
					}).render();
					
					var protectWayCombobox = new A.combobox({
						render: "#protectWayDiv",
						datasource:${protectWayCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.protectWay}"
					}).render();
					//日期组件
					var applyTimeDatePicker = new A.my97datepicker({
						id: "applyTimeId",
						name: "applyTime",
						render: "#applyTimeDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					applyTimeDatePicker.setValue('${dataMap.applyTime}');
					var id = $('#id').val();
					if("${dataMap.applyType}"=="0"){
						$("#applyTypechange").show();
					}else{
						$("#applyTypechange").hide();
					}
					applyTypeCombobox.change(function(){
						if(applyTypeCombobox.getValue()==0){
							$("#applyTypechange").show();
						}else{
							$("#applyTypechange").hide();
						}
						
					});
					var id = $('#id').val();
					$('#editForm').validate({
						debug:true,
						rules: {unitId:{required:true,maxlength:20},
							deviceId:{required:true,maxlength:20},
							protectWay:{required:true,maxlength:20},
							applyPersonId:{required:true,maxlength:20},
							applyTime:{required:true,date:true},
							protectReason:{required:true,maxlength:128},
							protectContent:{required:true,maxlength:128},},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/protect/"+id);
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
					$('#button').on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/protect/index")
						});
					});
				});
			});
        </script>
    </body>
</html>