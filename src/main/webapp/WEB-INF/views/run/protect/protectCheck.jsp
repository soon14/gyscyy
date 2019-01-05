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
				<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			    	<input type="hidden" id="id" name="id" value="${dataMap.id}" />
			    	<input type="hidden" id="protectId" name="protectId" value="${dataMap.id}" />			    	
			    	<input type="hidden" id="checkState" name="checkState" value="${dataMap.checkState}" />			
			   <div class="form-group">
			   		 <label class="col-md-2 control-label no-padding-right">
					    申请编号
				    </label>
				    <div class="col-md-4">
				    			<input  id="unitName" name="unitName" type="text" value="${dataMap.code}" class="col-md-12" readonly="readonly">				    
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>单位名称
				    </label>
				    <div class="col-md-4">
				    	<input  id="unit" name="unit" type="text" value="${dataMap.unitName}" class="col-md-12" readonly="readonly">
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>设备名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="20" value="${dataMap.equipName}" readonly="readonly">
					</div>
				    <label class="col-md-2 control-label no-padding-right">
					    保护变动方式
				    </label>
				    <div class="col-md-4">
				    	<select id="protectWayDiv" class="col-md-12" name="protectWay" disabled="disabled"></select>				    
				    </div>	
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    申请人
				    </label>
				    <div class="col-md-4">
				    	<select id="applyPersonIdDiv" class="col-md-12" name="applyPersonId" disabled="disabled"></select>
						<input id="applyPersonId" name="applyPersonId" value="${dataMap.applyPersonId}" type="hidden"/>
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
						<span style="color:red;">*</span>设备电压(kV)
					</label>
					<div class="col-md-4">
                	<input class="col-md-12" id="equipVoltage" name="equipVoltage" type="text" placeholder="" maxlength="64" value="${dataMap.equipVoltage }" readonly="readonly" >					                  
                	</div>
               </div>
               <c:if test="${dataMap.applyType==0 }">
             <div id="applyTypechange">
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>发令人
					</label>
					  	<div class="col-md-4">
						<input  id="dispatchPerson" name="dispatchPerson" type="text" value="${dispaComEntity.dispatchPerson}" class="col-md-12" readonly="readonly">				    
				   		</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度
				    </label>
				   	 	<div class="col-md-4">
						<input  id="dispath" name="dispath" type="text" value="${dispaComEntity.dispathName}" class="col-md-12" readonly="readonly">				    
				   		</div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>调度时间
					</label>
						<div class="col-md-4">
						<input  id="time" name="time" type="text" value="<fmt:formatDate value="${dispaComEntity.time}" type="both" pattern="yyyy-MM-dd HH:mm"/>" class="col-md-12" readonly="readonly">				    
				   		</div>
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>受令人
				    </label>
				    	<div class="col-md-4">
						<input  id="dutyChiefPerson" name="dutyChiefPerson" type="text" value="${dispaComEntity.dutyChiefPerson}" class="col-md-12" readonly="readonly">				    
				   		</div>
               </div>
             </div>
             </c:if>
			 <div class="form-group">				
				 <label class="col-md-2 control-label no-padding-right">
					    保护变动原因
				    </label>
				    <div class="col-md-10">
				     <textarea placeholder="请输入保护变动内容" name="protectReason"  class="col-md-12" style="height:90px; resize:none;"  readonly="readonly">${dataMap.protectReason}</textarea>
					</div>
					</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    保护变动内容
				    </label>
				    <div class="col-md-10">
					<textarea placeholder="请输入保护变动内容" name="protectContent"  class="col-md-12" style="height:90px; resize:none;"  readonly="readonly">${dataMap.protectContent}</textarea>
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
			</form>
    		<div style="margin-right:100px;" id="button">      
         		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">   
         		 <c:if test="${nodeList.authFactorCode=='ztjBtn'}">
						<button id="ztjBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon fa fa-floppy-o"></i>
		    				再提交
		    			</button>
		    			</c:if>
		    	<c:if test="${nodeList.authFactorCode=='qxBtn'}">
<!-- 						<button id="qxBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!-- 		    				<i class="ace-icon fa fa-floppy-o"></i> -->
<!-- 		    				取消 -->
<!-- 		    			</button> -->
		    	</c:if>  			
    			<c:if test="${nodeList.authFactorCode=='zztemppassBtn'}">         			
        		<button id="zztemppassBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			专工通过
        		</button>
        		</c:if>
    			<c:if test="${nodeList.authFactorCode=='zznopassBtn'}">         			       		
        		<button id="zznopassBtn" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
        			<i class="glyphicon glyphicon-remove-circle"></i>
        			专工驳回
        		</button>
        		</c:if>
    			<c:if test="${nodeList.authFactorCode=='zrtemppassBtn'}">         			        		
        		<button id="zrtemppassBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			主任通过
        		</button>
        		</c:if>
    			<c:if test="${nodeList.authFactorCode=='zrnopassBtn'}">         			        		
        		<button id="zrnopassBtn" class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
        			<i class="glyphicon glyphicon-remove-circle"></i>
        			主任驳回
        		</button>
        		</c:if>
    			<c:if test="${nodeList.authFactorCode=='zzconfirmBtn'}">         			        		        		
        	 	<button id="zzconfirmBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			确定执行人
        		</button>
        		</c:if>
    			<c:if test="${nodeList.authFactorCode=='zxrexecuteBtn'}">         			        		
        		<button id="zxrexecuteBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			执行人执行
        		</button> 
        		</c:if>
        		</c:forEach>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker', 'uploadify','uploaddropzone'], function(A){
					var conditions=[];
					//附件上传
					var uploaddropzone =new A.uploaddropzone({
						render : "#divfile",
						fileId:${dataMap.fileId},
						autoProcessQueue:false,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true,
					}).render();
					var applyTypeCombobox = new A.combobox({
						render: "#applyTypeDiv",
						datasource:${applyTypeCombobox},
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.applyType}"
					}).render();
        			var title="保护投退提交人";
        			//combotree组件
					var unitIdCombotree = new A.combotree({
						render: "#unitIdDiv",
						name: "unitId",
						//返回数据待后台返回TODO
						datasource: ${protectTreeList},
						width:"210px",
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
						options:{
							"disable_search_threshold":10
						},
						initValue:"${dataMap.applyPersonId}"
					}).render();
					var protectWayDivCombobox = new A.combobox({
						render: "#protectWayDiv",
						datasource:${protectWayCombobox},
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
					$("#button button").on("click", function(e){
						var id=$("#id").val();
						var obj = $("#editForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						var btnName=$(e.target).attr("id");
						var url='';
						if(btnName=='zzpassBtn'){
							url='protect/pass';
						}
						if(btnName=='zrpassBtn'){
							url='protect/directorPass';
						}
						if(btnName=='zznopassBtn'){
							url='protect/noPass';
						}
						if(btnName=='zrnopassBtn'){
							url='protect/directorNoPass';
						}
						if(btnName=='zztemppassBtn'){
							url='protect/tempPass';
						}						
						if(btnName=='zrtemppassBtn'){
							url='protect/directortempPass';
						}
						if(btnName=='zgnopassBtn'){
							url='protect/zgnoPass';
						}
						if(btnName=='zgpassBtn'){
							url='protect/zgPass';
						}
						if(btnName=='zzconfirmBtn'){
							url='protect/zzConfirm';
							title="确定执行人";
						}
						if(btnName=='zxrexecuteBtn'){
							url='protect/zxrExecute';
							title="执行人执行";
						}
						if(btnName=='ztjBtn'){
							url='protect/againSubmit';
						}
						if(btnName=='qxBtn'){
							url='protect/cancel';
						}
						if(btnName=='zznopassBtn'||btnName=='zrnopassBtn'||btnName=='zgnopassBtn'||btnName=='qxBtn')
						{
							$.ajax({
								url : format_url("/"+url+"/"+id),
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('审批成功');
									window.scrollTo(0,0);
									A.loadPage({
										render : '#page-container',
										url : format_url("/todoTask/list/1/10")
									});								
									},
								error:function(v,n){
									alert('审批失败');
								}
							});
						}else{
							var urls="";	
							if(btnName=='zzconfirmBtn'){
								urls=format_url('/protect/confirmView/'+$("#taskId").val());
							}else if (btnName=='zxrexecuteBtn'){
								urls=format_url('/protect/executeView/');
							}else if (btnName=='ztjBtn'){
								urls=format_url('/protect/submitPerson/');
							}else if(btnName=='zztemppassBtn'){
								urls=format_url('/protect/submitPersonAgreeZZ?taskId='+$("#taskId").val()+'&id='+$("#id").val());
							}else{
								urls=format_url('/protect/submitPersonAgree/'+$("#taskId").val());
							}
							listFormDialog = new A.dialog({
								width: 850,
								height: 581,
								title: title,
								url:urls,
								closed: function(){
									if(listFormDialog.resule){
										obj.userList="";
										if(btnName=='ztjBtn'){
										 	obj.userList=listFormDialog.resule.join(",");
										}
										else{
											for(var i=0;i<listFormDialog.resule.length-1;i++){
												obj.userList+=listFormDialog.resule[i]+",";
											}
										 	obj.approveIdea=listFormDialog.resule[listFormDialog.resule.length-1];
										}											
										$.ajax({
											url : format_url("/"+url+"/"+id),
											contentType : 'application/json',
											dataType : 'JSON',
											type : 'POST',
											data : JSON.stringify(obj),
											success: function(result){
												if(result.result=="success"){
													alert("审批成功");
													window.scrollTo(0,0);
													A.loadPage({
														render : '#page-container',
														url : format_url("/todoTask/list/1/10")
													});
    											}else{
    												alert(result.errorMsg);
    											}
											},
											error:function(v,n){
												alert("审批失败");
											}
										});
									}
								}
							}).render();														
						}					
					});
				});
			});
        </script>
    </body>
</html>