<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    	<style>
			.aptech-combotree-dropdown{
				max-height:300px !important;
			}
		</style>
    </head>
	<body>
		<div class="breadcrumbs ace-save-state" id="breadcrumbs" style="margin-bottom:100px;">
    		<ul class="breadcrumb">
    			<li>
    				<i class="ace-icon fa fa-home home-icon"></i>
    				<a href="javascript:void(0);" onclick="firstPage()">首页</a>
    			</li>
    
    			<li>OA管理</li>
    			<li>文件学习</li>
    			<li class="active">文件学习修改</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnEdit" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:10px">文件学习修改</h5>
    	</div>
		<div class="col-md-12"  style="width: 90%;margin-left: 20px;">
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editForm">
			   <input class="col-md-12" id="id" name="id" type="hidden" value="${entity.id}">
			   <input class="col-md-12" id="approveStatus" name="approveStatus" type="hidden" value="${entity.approveStatus}">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>文件名称  </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="fileName" name="fileName" type="text" placeholder="" maxlength="128" value="${ entity.fileName }">
                    </div>
                    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发文单位</label>
				    <div class="col-md-4">
						 <div id="sendUnitAddDiv"></div>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发布人</label>
					<div class="col-md-4">
	                  <div style="width:100%;display:inline-block">
							<div id="sendUserAddDiv">
							</div>	
						</div>
                	</div>
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>发文时间 </label>
				    <div class="col-md-4">
						<div id="sendTimeDiv" class="form-control" style="border:none; padding:0px;max-height:500px;display:inline-block;"></div>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>学习提醒时间 </label>
				    <div class="col-md-4">
						<div id="warnTimeDiv" class="form-control" style="border:none; padding:0px;max-height:530px;display:inline-block;"></div>
                    </div>
               </div>
		       <div class="form-group">
                    <label class="col-md-2 control-label no-padding-right">是否通知 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isNotification=='1'}">
									<input id="isNotification" name="isNotification"   type="checkbox"   checked="checked"  value="1"   class="ace input"  /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						<c:if test="${entity.isNotification!='1'}">
									<input id="isNotification" name="isNotification"   type="checkbox"     value="0"  class="ace input" /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						</label>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">是否重要 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
						<c:if test="${entity.isImportant=='1'}">
									<input id="isImportant" name="isImportant"   type="checkbox"   checked="checked"  value="1"   class="ace input"  /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</c:if>
						<c:if test="${entity.isImportant!='1'}">
									<input id="isImportant" name="isImportant"   type="checkbox"     value="0"  class="ace input" /> 
									&nbsp;<span class="lbl bigger-100"></span>
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
						 <input name="isAllNet" type="radio" placeholder="" maxlength="128" value="1">
					</div>
					<label class="col-md-1 control-label no-padding-right">特定接收人</label>
					<div class="col-md-2">
						<input name="isAllNet" type="radio" placeholder="" maxlength="128" value="0"> 
					</div>
					<label class="col-md-2 control-label no-padding-right">接收人</label>
					<div class="col-md-4" id='receiveDiv'>
	                  <div style="width:100%;display:inline-block">
							<div id="receiveUserAddDiv">
							</div>	
						</div>
                	</div>
               </div>
                <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">内容</label>
					<div class="col-md-10">
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128" >${entity.remark}</textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="attachmentDiv">
					</div>
	            </div>
            </form>
    		<div style="margin-right:100px;">
        		<button class="btn btn-xs btn-danger pull-right" id="cancelBtn">
        			<i class="ace-icon fa fa-times"></i>
        			取消
        		</button>
        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
        			<i class="ace-icon fa fa-floppy-o"></i>
        			保存
        		</button>
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){
					//发布人选择带回
					var entity = ${entityJson};
					var sendUserNamesDiv=new A.selectbox({
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
					/* var receiveUserNamesDiv=new A.selectbox({
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
					}).render(); */
					 var receiveUserIds;
			          var addRecipientCombotree1 = new A.combotree({
			            render: "#receiveUserAddDiv",
			            name: 'receiveUserIds',
			            //返回数据待后台返回TODO
			            datasource: ${recipientTree},
			            width:"210px",
			            multiple: true,
			            options: {
			             
			              treeId: 'receiveUsers',
			              data: {
			                key: {
			                  name: "name"
			                },
			                simpleData: {
			                  enable: true,
			                  idKey: "id",
			                  pIdKey: "parentId"
			                }
			              },
			              callback: {
			            	  beforeOnCheck: function(e, treeId, treeNode){
									value = "";
									ids ="";
									var tree = $.fn.zTree.getZTreeObj(treeId);
									var nodes = tree.getCheckedNodes(true);
									for (var i=0, l=nodes.length; i<l; i++) {
								    	if(nodes[i].nodeType=='2'){
									    	if(i == l-1){
												ids += nodes[i].id.replace("user","");
											}else{
												ids += nodes[i].id.replace("user","") + ",";
											}
										}
								    	if(nodes[i].nodeType!='2' && !nodes[i].isParent){
								    		nodes[i].isParent = true;
										}
									}
									receiveUserIds = ids;
									
								}
							}
			            }
			          }).render();
			          var userIds = entity.receiveUserIds.split(',');
			          for(var i=0;i<userIds.length;i++){
			        	  userIds[i]="user"+userIds[i];
			          }
			          addRecipientCombotree1.setValue(userIds);
					  receiveUserIds = entity.receiveUserIds;
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
								dateFmt: 'yyyy-MM-dd HH:mm'
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
								dateFmt: 'yyyy-MM-dd HH:mm'
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
					
					//附件上传
					var uploadLearnFile=new A.uploaddropzone({
						render : "#learnFileDiv",
						fileId:${entity.learnFile},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//附件上传
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:${entity.attachment},
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render(1);
					//单选框
					if(entity.isAllNet=='1'){
						$(":radio[name=isAllNet][value="+1+"]").attr("checked","true");
					}else{

						$(":radio[name=isAllNet][value="+0+"]").attr("checked","true");
					}
					$('#editForm').validate({
						debug:true,
						rules:  {
							fileName:{required:true,maxlength:128},
							sendUnitId:{required:true,maxlength:20},
							sendUserId:{required:true,maxlength:20},
							sendTime:{required:true,maxlength:30},
							warnTime:{required:true,maxlength:30},
							},
						submitHandler: function (form) {
							//保存按钮
							var url = format_url("/fileLearn/saveEditPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editForm").serializeObject();
							obj.receiveUserIds = receiveUserIds;
							//校验
							if(obj.isAllNet == '0' && (obj.receiveUserIds == null||obj.receiveUserIds=="")){
								alert("请选择接收人！");
								return;
							}
							//附件属性
							obj.learnFile=JSON.stringify(uploadLearnFile.getValue());
							obj.attachment=JSON.stringify(uploadattachment.getValue());
							//debugger;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('操作成功');
									reback();
								},
								error:function(v,n){
									alert('操作失败');
								}
							});
						}
					});
					//是否全网接收绑定事件
					 /* $('input:radio[name="isAllNet"]').click( function()  {
		    			if($('input[name="isAllNet"]:checked').val() == '1'){
		    				$("#receiveUserAddDiv button").attr("disabled", true); 
		    				receiveUserNamesDiv.setValue('','');
		    			}else{
		    				$("#receiveUserAddDiv button").attr("disabled", false); 
		    			}
		   			 }); */
					  $('input:radio[name="isAllNet"]').click( function()  {
		    			if($('input[name="isAllNet"]:checked').val() == '1'){
		    				//$("#receiveUserAddDiv button").attr("disabled", true); 
		    				addRecipientCombotree1.setValue("");
		    				receiveUserIds="";
		    				$('#receiveDiv').append("<div style='position:absolute;width:100%;height:100%;top:0' id='overDiv'></div>")
		    			}else{
		    				//$("#receiveUserAddDiv button").attr("disabled", false); 
		    				$('#overDiv').remove();
		    			}
		   			 });
					 $('input[name="isAllNet"]:checked').click();
					//保存
					$("#editBtn").on("click", function(){
    					$("#editForm").submit();
    				});
					//返回到列表页
    				$("#backBtnEdit").on("click",function(){
    					reback();
    				});
    				//返回到列表页
    				$("#cancelBtn").on("click",function(){
    					reback();
    				});
				});
			});
			function reback(){
				A.loadPage({
					render : '#page-container',
					url : format_url("/fileLearn/index")
				});
			}
			$("#isNotification").on("click",function(){
				if($(this).attr("checked")=='checked'){
					if($(this).val()=='1'){
						$(this).val('0');
					}else{
						$(this).val('1');
					}
				}else{
					if($(this).val()=='1'){
						$(this).val('0');
					}else{
						$(this).val('1');
					}
				}
				console.log($(this).val());
			});
			$("#isImportant").on("click",function(){
				if($(this).attr("checked")=='checked'){
					if($(this).val()=='1'){
						$(this).val('0');
					}else{
						$(this).val('1');
					}
				}else{
					if($(this).val()=='1'){
						$(this).val('0');
					}else{
						$(this).val('1');
					}
				}
				console.log($(this).val());
			});
        </script>
    </body>
</html>