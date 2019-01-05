<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="sendForm">
							<input class="col-md-12" id="id" name="id" type="hidden" value="">
		       <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消息类型
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="type" name="type"></select>
                    </div>
               </div>
		       <div id="receivePerson" class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>接收人
					</label>
					<div class="col-md-4">
	                   <div style="width:510px;display:inline-block">
							<div id="receivePersonAddDiv">
							</div>	
						</div>
                	</div>
               </div>
		       <div id="messageCategory" class="form-group" style="display:none">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消息分类
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="category" name="category"></select>
                    </div>
               </div>
		       <div id="messageGroup" class="form-group" style="display:none">
				    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消息分组
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="group" name="group"></select>
                    </div>
               </div>
		       <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>主题
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="title" name="title" type="text" placeholder="" maxlength="258" value="">
                	</div>
    				</div>
	           <div class="form-group">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>消息内容
					</label>
					<div class="col-md-10">
						<textarea id="context" name="context" placeholder="" style="height:100px; resize:none;" class="col-md-12" ></textarea>
					</div>
               </div>
            </form>
    		<div style="margin-right:100px;margin-bottom:50px;">
    			<button id="sendBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				发送
    			</button>
    		</div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					//选择信息接收人
					var flowManagerNamesDiv=new A.selectbox({
						id: 'receivePersonAddDivId',
						name:'receivePerson',
						title:'人员',
						url:'/sysUser/userSelect',
						render:'#receivePersonAddDiv',
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
					
					//信息类型（私信、公共）
					var typeComboData = ${messageTypeCombo};
					var messageTypeCombobox = new A.combobox({
						render: "#type",
						//返回数据待后台返回TODO
						datasource:typeComboData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					messageTypeCombobox.setValue("private");
					
					//信息分类
					var categoryComboData = ${messageCategoryCombo};
					var messageCategoryCombobox = new A.combobox({
						render: "#category",
						//返回数据待后台返回TODO
						datasource:categoryComboData,
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//根据信息类型决定显示和隐藏的项
					messageTypeCombobox.change(function(event,value){
						var messageType = value.selected;
						if(messageType == 'private'){
							$("#messageCategory").hide();
							$("#messageGroup").hide();
							$("#receivePerson").show();
						}else if(messageType == 'public'){
							$("#messageCategory").show();
							$("#messageGroup").show();
							$("#receivePerson").hide();
						}
					});
					
					//信息分类变更时，调出其下属子项
					var messageGroupCombobox = null;
					messageCategoryCombobox.change(function(event, value){
						var categoryCode = value.selected;
						//信息分组（分类下的子项）
						$.ajax({
							url : format_url("/messageCenter/getGroup/"+categoryCode),
							contentType: "application/json",
							dataType: "JSON",
							success: function(result){
								messageGroupCombobox = new A.combobox({
									render: "#group",
									//返回数据待后台返回
									datasource:result.messageGroupData,
									//multiple为true时select可以多选
									multiple:true,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10
									}
								}).render();
							}
						});
					});
					
					$('#sendForm').validate({
						debug:true,
						rules:  {
							type:{required:true},
							title:{maxlength:258,required:true},
							context:{required:true}
						},
						submitHandler: function (form) {
							$("#sendBtn").attr({"disabled":"disabled"});
							var url = format_url("/messageCenter/addMessage");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#sendForm").serializeObject();
							var messageCategory = messageCategoryCombobox.getValue();
							if(messageCategory){
								var messageGroup = messageGroupCombobox.getValue();
								if(messageGroup){
									obj.group = messageGroup;
								}
							}
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('发送成功');
									$('#title').val('');
									$('#context').val('');
								},
								error:function(v,n){
									alert('发送失败');
								}
							});
						}
					});
					$("#sendBtn").on("click", function(){
						$("#sendForm").submit();
    				});
    				
				});
			});
        </script>
    </body>
</html>