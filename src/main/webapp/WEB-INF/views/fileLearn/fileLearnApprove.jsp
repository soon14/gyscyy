<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    </head>
	<body>
		<div class="ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<h5 class="table-title header smaller blue" style="margin-left:10px">文件学习审核</h5>
		</div>
		<div class="col-md-12"  style="width: 90%;margin-left: 20px;" >
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="approveForm">
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
		      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>文件名称  </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="fileName" name="fileName" type="text" placeholder="" maxlength="128" value="${ entity.fileName }"  readonly="readonly" >
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发文单位</label>
				    <div class="col-md-4">
						<input class="col-md-12" id="sendUnitName" name="sendUnitName" type="text" placeholder="" maxlength="128" value="${ entity.sendUnitName }"  readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发布人</label>
                	<div class="col-md-4">
						<input class="col-md-12" id="sendUserName" name="sendUserName" type="text" placeholder="" maxlength="128" value="${ entity.sendUserName }"  readonly="readonly" >
                    </div>
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发文时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="sendTime" name="sendTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.sendTime}"  pattern="yyyy-MM-dd HH:mm" />'  readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>学习提醒时间 </label>
                    <div class="col-md-4">
						<input class="col-md-12" id="warnTime" name="warnTime" type="text" placeholder="" maxlength="128" value='<fmt:formatDate value="${entity.warnTime}" pattern="yyyy-MM-dd HH:mm" />'   readonly="readonly" >
                    </div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">是否通知 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isNotification=='1'}">
									<input   type="checkbox"   checked="checked"  value="1"   class="ace input"  disabled="disabled"  /> 
									&nbsp;<span class="lbl bigger-100"></span>
									<input id="isNotification"  name="isNotification"   type="hidden"    value="1"     /> 
						</c:if>
						<c:if test="${entity.isNotification!='1'}">
									<input   type="checkbox"     value="0"  class="ace input"  disabled="disabled"   /> 
									&nbsp;<span class="lbl bigger-100"></span>
									<input id="isNotification" name="isNotification"   type="hidden"    value="0"     />
						</c:if>
						</label>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">是否重要 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isImportant=='1'}">
									<input   type="checkbox"   checked="checked"  value="1"   class="ace input"  disabled="disabled"  /> 
									&nbsp;<span class="lbl bigger-100"></span>
									<input id="isImportant" name="isImportant"   type="hidden"    value="1"     /> 
						</c:if>
						<c:if test="${entity.isImportant!='1'}">
									<input   type="checkbox"     value="0"  class="ace input"  disabled="disabled"   /> 
									&nbsp;<span class="lbl bigger-100"></span>
									<input id="isImportant" name="isImportant"   type="hidden"    value="0"     /> 
						</c:if>
						</label>
                    </div>
               </div>
                <div class="form-group form-horizontal" >
					<label class="col-md-2 control-label no-padding-right">学习文件</label>
					<div class="col-md-10" id="learnFileDiv">
					</div>
	            </div>
	            <div class="form-group" >
	            	<label class="col-md-2 control-label no-padding-right">全部挂网</label>
					<div class="col-md-1">
						 <input name="isAllNet" type="radio" placeholder="" maxlength="128" value="1"   disabled="true">
					</div>
					<label class="col-md-1 control-label no-padding-right">特定接收人</label>
					<div class="col-md-2">
						<input name="isAllNet" type="radio" placeholder="" maxlength="128" value="0"  disabled="true"> 
					</div>
					<label class="col-md-2 control-label no-padding-right">接收人</label>
                	<div class="col-md-4">
						<input class="col-md-12" id="receiveUserNames" name="receiveUserNames" type="text" placeholder="" maxlength="128" value="${ entity.receiveUserNames }"  readonly="readonly" >
						<input class="col-md-12" id="receiveUserIds" name="receiveUserIds" type="hidden" placeholder="" maxlength="128" value="${ entity.receiveUserIds }"  readonly="readonly" >
                    </div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">内容</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"  readonly="readonly">${entity.remark}</textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="attachmentDiv">
					</div>
	            </div>
	             <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">审批意见</label>
					<div class="col-md-10">
						<textarea name="approveComment" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"  readonly="readonly"></textarea>
					</div>
               </div>
            </form>
    		<div style="margin-right:100px;">
        		<c:forEach items="${nodeList}" var="nodeList" varStatus="vs">
	    			<c:if test="${nodeList.authFactorCode=='wjxxbhBtn'}">
		        		<button  id="wjxxbhBtn"  title="驳回"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-circle"></i>
		    				驳回
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='wjxxtyBtn'}">
		        		<button  id="wjxxtyBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='wjxxfbBtn'}">
		        		<button  id="wjxxfbBtn"  title="领导审核"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-level-up"></i>
		    				发布
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='wjxxtjBtn'}">
		        		<button  id="wjxxtjBtn"  title="取消流程"  class="btn btn-xs btn-danger pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-remove-sign"></i>
		    				提交
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='leaderBtn'}">
		        		<button  id="leaderBtn"  title="同意"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-ok"></i>
		    				同意
				    	</button>
	    			</c:if> 
	    			<c:if test="${nodeList.authFactorCode=='fbBtn'}">
		        		<button  id="fbBtn"  title="发布"  class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
		    				<i class="ace-icon glyphicon glyphicon-level-up"></i>
		    				发布
				    	</button>
	    			</c:if> 
    			</c:forEach>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){
					//发布人选择带回
					var entity = ${entityJson};
					var dataId = entity.id;
				/* 	var sendUserNamesDiv=new A.selectbox({
						id: 'sendUserName',
						name:'sendUserId',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=1',
						render:'#sendUserAddDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					sendUserNamesDiv.setValueBack("#sendUserName",entity.sendUserName,entity.sendUserId);
					//接收人
					var sendUserNamesDiv=new A.selectbox({
						id: 'receiveUsers',
						name:'receiveUserIds',
						title:'人员',
						url:'/sysUser/userSelect?singleSelect=0',
						render:'#receiveUserAddDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							var names =[];
							var ids=[];
							for(var i =0; i<data.length; i++){
								names.push(data[i].name);
								ids.push(data[i].id);
							}
							self.setValue(names,ids);
						}
					}).render();
					sendUserNamesDiv.setValueBack("#receiveUsers",entity.receiveUserNames,entity.receiveUserIds);
					//发布时间
					var sendTimeDatePicker = new A.my97datepicker({
						id: 'sendTimeAdd',
						name: 'sendTime',
						render:'#sendTimeDiv',
						options : {
								isShowWeek : false,
								isShowToday:true,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render();
					$("#sendTimeAdd").val(entity.sendTime);
					//提醒时间
					var warnDatePicker = new A.my97datepicker({
						id: 'warnTimeAdd',
						name: 'warnTime',
						render:'#warnTimeDiv',
						options : {
								isShowWeek : false,
								isShowToday:true,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd'
						}
					}).render(1);
					$("#warnTimeAdd").val(entity.warnTime);
					//combotree组件  单位
					var unitNameIdCombotree = new A.combotree({
						render: "#sendUnitAddDiv",
						name: 'sendUnitId',
						//返回数据待后台返回TODO
						datasource: ${unitNameIdTreeList},
						width:"500px",
						options: {
							treeId: 'sendUnit',
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
					unitNameIdCombotree.setValue(entity.sendUnitId );//默认值当前单位
					 */
					//附件上传
					var uploadLearnFile=new A.uploaddropzone({
						render : "#learnFileDiv",
						fileId:${entity.learnFile},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render();
					//附件上传
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : false,//显示删除按钮
						chargeUp:true
					}).render(1);
					//单选框
					if(entity.isAllNet=='1'){
						$(":radio[name=isAllNet][value="+1+"]").attr("checked","true");
					}else{

						$(":radio[name=isAllNet][value="+0+"]").attr("checked","true");
					}

					//驳回按钮点击事件
    				$("#wjxxbhBtn").on("click", function(e){
    					$("#wjxxbhBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
    					$.ajax({
							url : format_url("/fileLearn/reject"),
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
    				});
    				//通过按钮点击事件
    				$("#wjxxtyBtn").on("click", function(e){
    					$("#wjxxtyBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/fileLearn/pass"),
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
    				});
    				//部门领导通过按钮点击事件
    				$("#leaderBtn").on("click", function(e){
    					$("#leaderBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/fileLearn/pass"),
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
    				});
    				//发布按钮点击事件
    				$("#wjxxfbBtn").on("click", function(e){
    					$("#wjxxfbBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/fileLearn/publish"),
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
    				});
    				//发布按钮点击事件
    				$("#fbBtn").on("click", function(e){
    					$("#fbBtn").attr({"disabled":"disabled"});
    					var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
						obj.id = dataId;
						$.ajax({
							url : format_url("/fileLearn/publish"),
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
    				});
    				
    				//提交人再次提交按钮
					$("#wjxxtjBtn").on("click", function(e){
						$("#wjxxtjBtn").attr({"disabled":"disabled"});
    					var urls=format_url('/fileLearn/submitPersonLeader/'+$("#taskId").val());
						var obj = $("#approveForm").serializeObject();
						obj.taskId=$("#taskId").val();
						obj.procInstId=$("#procInstId").val();
    					submitUserDialog = new A.dialog({
							width: 850,
							height: 481,
							title: "审批领导",
							url:urls,
							closed: function(){
								debugger;
								if(submitUserDialog.resule){
								 	obj.userList=submitUserDialog.resule.join(",");
								 	obj.id = dataId;
									$.ajax({
										url : format_url("/fileLearn/submitAgain"),
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
    				});
					
					
				});
			});
			
			function reback(){
				A.loadPage({
					render : '#page-container',
					url : format_url("/fileLearn/index")
				});
			}
        </script>
    </body>
</html>