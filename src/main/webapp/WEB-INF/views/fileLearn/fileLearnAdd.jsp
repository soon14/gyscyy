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
    			<li class="active">文件学习新增</li>
    		</ul><!-- /.breadcrumb -->
    		<div style="margin-right:100px;margin-top:10px;">
        		<button id="backBtnAdd" class="btn btn-xs btn-primary pull-right">
    				<i class="fa fa-reply"></i>
    				返回
    			</button>
        	</div>
    		<h5 class="table-title header smaller blue" style="margin-left:10px">文件学习新增</h5>
    	</div>
		<div class="col-md-12" style="width: 90%;margin-left: 20px;">
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addForm">
			<input class="col-md-12" id="approveStatus" name="approveStatus" type="hidden" value="0">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>文件名称  </label>
				    <div class="col-md-4">
						<input class="col-md-12" id="fileName" name="fileName" type="text" placeholder="" maxlength="128" value="">
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
									<input id="isNotification" name="isNotification"   type="checkbox" value="1" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100"></span>
						</label>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">是否重要 </label>
				    <div class="col-md-4">
						<label class="control-label no-padding-right"> 
									<input id="isImportant" name="isImportant"   type="checkbox" value="1" class="ace input" /> 
									&nbsp;<span class="lbl bigger-100"></span>
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
						 <input name="isAllNet" type="radio" placeholder="" maxlength="128" value="1" checked="checked" >
					</div>
					<label class="col-md-1 control-label no-padding-right">特定接收人</label>
					<div class="col-md-2">
						<input name="isAllNet" type="radio" placeholder="" maxlength="128" value="0" > 
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
						<textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" maxlength="128"></textarea>
					</div>
               </div>
                <div class="form-group" >
					<label class="col-md-2 control-label no-padding-right">附件</label>
					<div class="col-md-10" id="attachmentDiv">
					</div>
	            </div>
            </form>
    		<div style="margin-right:100px;">
    			<button id="cancelBtn"  class="btn btn-xs btn-danger pull-right" >
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
				seajs.use(['datatables','confirm','dialog','combobox','combotree','uploaddropzone','my97datepicker','selectbox'], function(A){

					//发布人选择带回
					var userEntity = ${sysUserEntity};
					var systemdate = ${systemdate};
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
					sendUserNamesDiv.setValueBack("#sendUserName",userEntity.name,userEntity.id);
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
					 //接受人
					 var receiveUserIds;
			          var addRecipientCombotree = new A.combotree({
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
					//发布时间
					var instockDate = ${systemdate};
					var instockTimeDatePicker = new A.my97datepicker({
						id: 'sendTimeAdd',
						name: 'sendTime',
						render:'#sendTimeDiv',
						datasource:instockDate,
						options : {
								isShowWeek : false,
								isShowToday:true,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
					$("#sendTimeAdd").val(instockDate);
					//提醒时间
					var instockDate = ${systemdate};
					var instockTimeDatePicker = new A.my97datepicker({
						id: 'warnTimeAdd',
						name: 'warnTime',
						render:'#warnTimeDiv',
						datasource:instockDate,
						options : {
								isShowWeek : false,
								isShowToday:true,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: 'yyyy-MM-dd HH:mm'
						}
					}).render();
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
					unitNameIdCombotree.setValue(userEntity.unitId );//默认值当前单位
					
					//附件上传
					var uploadLearnFile=new A.uploaddropzone({
						render : "#learnFileDiv",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render();
					//附件上传
					var uploadattachment=new A.uploaddropzone({
						render : "#attachmentDiv",
						fileId:[],
						autoProcessQueue:true,//是否自动上传
						addRemoveLinks : true,//显示删除按钮
					}).render(1);
					$('#addForm').validate({
						debug:true,
						rules:  {
							fileName:{required:true,maxlength:128},
							sendUnitId:{required:true,maxlength:20},
							sendUserId:{required:true,maxlength:20},
							sendTime:{required:true,maxlength:30},
							warnTime:{required:true,maxlength:30},
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/fileLearn/saveAddPage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							obj.receiveUserIds = receiveUserIds;
							//校验
							if(obj.isAllNet == '0' && (obj.receiveUserIds == null||obj.receiveUserIds=="")){
								alert("请选择接收人！");
								return;
							}
							//附件属性
							obj.learnFile=JSON.stringify(uploadLearnFile.getValue());
							obj.attachment=JSON.stringify(uploadattachment.getValue());
							//console.log(obj.attachment);
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									reback();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//是否全网接收绑定事件
					  $('input:radio[name="isAllNet"]').click( function()  {
		    			if($('input[name="isAllNet"]:checked').val() == '1'){
		    				//$("#receiveUserAddDiv button").attr("disabled", true); 
		    				addRecipientCombotree.setValue("");
		    				receiveUserIds="";
		    				$('#receiveDiv').append("<div style='position:absolute;width:100%;height:100%;top:0' id='overDiv'></div>")
		    			}else{
		    				//$("#receiveUserAddDiv button").attr("disabled", false); 
		    				$('#overDiv').remove();
		    			}
		   			 });
					$('input[name="isAllNet"]:checked').click(); 
					//保存
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});
					//返回到列表页
    				$("#backBtnAdd").on("click",function(){
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
		   
        </script>
    </body>
</html>