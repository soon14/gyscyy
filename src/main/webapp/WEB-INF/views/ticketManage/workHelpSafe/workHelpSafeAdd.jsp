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
					<li class="active">两票管理</li>
					<li class="active">继电保护安全措施票</li>
					<li class="active">新增</li>
				</ul>
		</div>
		<div class="col-md-12" >
		<div class="page-content">
		<div class="tabbable" style="margin-top: 20px;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li class="active">
			 			<a   data-toggle="tab" href="#workitem" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
		 		</ul>
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBackTicket" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				<div class="tab-content" style="overflow-x:hidden;overflow-y: auto;height: 650px">
					
				<!-- 第一个div开始 -->
				<div id="workitem" class="tab-pane fade active in">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
								<input type="hidden" id="uuidWorkTicket" name="uuidWorkTicket" value="${uuid}"/>
								<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
								<input type="hidden" id="selectUser" name="selectUser"/>
								<input type="hidden" id="typicalTicketId" name="typicalTicketId"   value="${typicalTicketId}"/>
				
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位名称</label>
								<div class="col-md-4">
										<input class="col-md-12" id="unitNameId" type="hidden" readonly="readonly" name="unitNameId" value="${userEntity.unitId}"></input>
										<input class="col-md-12" id="unitName" type="text" readonly="readonly"  value="${userEntity.unitName}"></input>
								</div>
								</div>
								<div class="form-group">
									<!-- <label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>班组</label>
									<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="groupId" name="groupId"></select>
								</div> -->
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly" name="guarderName" value="${userEntity.name}"></input>
								</div>
								</div>
								<!-- <div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人员</label>
									<div class="col-md-10">
										<textarea id="workClassMember" name="workClassMember" placeholder="名字要用逗号间隔如 1,2,3" style="height:80px; resize:none;" class="col-md-12" maxlength="128" onblur="gotoFindClassMember();"></textarea>
									</div>
								</div> -->
								
								<!-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople" name="workClassPeople" type="text" readonly="readonly" maxlength="20" value="">
								</div>
								</div> -->
								
								
								<!-- <div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作内容</label>
									<div class="col-md-10">
										<textarea id="content" name="content" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作地点</label>
									<div class="col-md-10">
										<textarea id="address" name="address" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div> -->
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">设备编号</label>
									<div class="col-md-4">
										<div id="equipmentCodeWorkOneDiv">
										</div>
									</div>
									<label class="col-md-2 control-label no-padding-right">设备名称</label>
									<div class="col-md-4">
												<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" readonly="readonly" maxlength="64" >
									</div>
								</div>
								<!-- <div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划开始时间</label>
									<div class="col-md-4">
										<div id="plandateStartDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划终了时间</label>
								<div class="col-md-4">
										<div id="plandateEndDiv"></div>
								</div>
								</div> -->
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">备注</label>
									<div class="col-md-10">
										<textarea id="remarkOther" name="remarkOther" placeholder="请填写备注" style="height:80px; resize:none;" class="col-md-12" maxlength="256"></textarea>
									</div>
								</div>
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="flawCodeDiv"></div> -->
<!-- 								</div> -->
								
<!-- 							</div> -->
							
    			<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">操作项目</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>执行</th>
<!-- 	                                <th>执行时间</th> -->
	                                <th>操作项目</th>
	                                <th>恢复</th>
<!-- 	                                <th>恢复时间</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				</div>
				</form>
		    	</div>
		    		<!-- 第一个div结束 -->
    		</div>
    	</div>
    </div>
    		<div style="margin-right:100px;margin-top: 20px;">
    			<!-- <button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button> -->
    			<button id="saveBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    			<c:if test="${typicalTicketId==null||typicalTicketId==''}">
    			<button id="submitBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="glyphicon glyphicon-floppy-saved"></i>
    				保存并提交
    			</button>
    			</c:if>
    		</div>
    		
		</div>
		<script type="text/javascript">
			var workSafeOneDialog;
			var workSafeTwoDialog;
			var controlCardRiskDatatables="";
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					
					var uuid='${uuid}';
					/* var selectboxDh=new A.selectbox({
						id: 'flawCodeId',
						name:'flawCode',
						title:'缺陷单',
						url:'/defect/defectList',
						render:'#flawCodeDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'720',//弹出窗体的高度  可以不写这行
						callback: function(data){
							//$("#flawCode").val(data.code);
							selectboxDh.setValue(data.code,data.code);
						}
					}).render(); */
					
					var equipList=[];
					var ids=[];
					var nameList='';
					var codeList='';
					var selecttreeDh=new A.selectbox({
						id: 'equipmentWorkOneCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger',
						render:'#equipmentCodeWorkOneDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
// 							if(data&&data[0]){
// 								selecttreeDh.setValue(data[0].code,data[0].code);
// 								$("#equipmentName").val(data[0].name);
// 							};
							 for(var i=0; i< data.length;i++){
								 if(ids.length>0){
									 if(!contains(ids,data[i].id)){
										ids.push(data[i].id);
									     codeList += data[i].code+',';
									     nameList += data[i].name+',';
										 
									 }
								}else{
								  ids.push(data[i].id);
								  codeList += data[i].code+',';
								  nameList += data[i].name+',';
									
								}
							 }
							    selecttreeDh.setValue(codeList,codeList);
								$("#equipmentName").val(nameList);
							
						}
					}).render();
					
					//combobx组件
					/* var groupIdCombobox = new A.combobox({
						render: "#groupId",
						//返回数据待后台返回TODO
						datasource:${groupIdCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render(); */
					
					
					
					/* //日期组件
					var plandateStartDatePicker = new A.my97datepicker({
						id: "plandateStartId",
						name:"plandateStart",
						render:"#plandateStartDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					//日期组件
					var plandateEndDatePicker = new A.my97datepicker({
						id: "plandateEndId",
						name:"plandateEnd",
						render:"#plandateEndDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render(); */
					
					$('#workTicketForm').validate({
						rules:  {
							unitNameId:{required:true,maxlength:64},
							//groupId:{required:true,maxlength:20},
							guarderName:{required:true,maxlength:64},
							//workClassMember:{required:true,maxlength:128},
							//workClassPeople:{required:true,maxlength:20},
							//content:{required:true,maxlength:128},
							//address:{required:true,maxlength:128},
							//plandateStart:{required:true},
							//plandateEnd:{required:true},
							},
						submitHandler: function (form) {
							/* if($("#workClassMember").val()==""){
								alert("工作班成员不能为空！");
								return;
							}
							if($("#content").val()==""){
								alert("工作内容不能为空！");
								return;
							}
							if($("#address").val()==""){
								alert("工作地点不能为空！");
								return;
							}
							if(plandateStartDatePicker.getValue()>plandateEndDatePicker.getValue()){
								alert("计划开始时间不能大于计划终了时间！");
								return;
							} */
							////////////////////////////
							$.ajax({
								url: format_url('/workHelpSafe/addValidate/'+uuid),
								contentType : 'application/json',
								dataType : 'JSON',
								type: 'POST',
								success: function(result){
									if(result.result == 'success'){
										//添加按钮
										var url = format_url("/workHelpSafe");
										var obj = $("#workTicketForm").serializeObject();
										$.ajax({
											url : url,
											contentType : 'application/json',
											dataType : 'JSON',
											data : JSON.stringify(obj),
											cache: false,
											type : 'POST',
											success: function(result){
												alert('添加成功');
												window.scrollTo(0,0);
												//这里是典型票点过来的保存，还是第一种票直接的保存，的判断跳转列表
												var typicalTicketId='${typicalTicketId}';
												if(typicalTicketId!=null&&typicalTicketId!=''){
													A.loadPage({
														render : '#page-container',
														url : format_url("/typicalTicket/index")
													});
												}else{
													A.loadPage({
														render : '#page-container',
														url : format_url("/workHelpSafe/index")
													});
												}
												
											},
											error:function(v,n){
												alert('添加失败');
											}
										});
										
									} else{
   										alert(result.errorMsg);
   									}
								}
							});
							
						}
					});
					
					//安全措施 开始
					//下面是安全措施的列表 1
					var conditionsOne=[];
					var workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsOne=[];
					            	conditionsOne.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1
		            				});
					            	conditionsOne.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "executeSituation",width: "15%",orderable: true},
// 							          {data: "executeDate",width: "10%",orderable: true},
							          {data: "signerContent",width: "50%",orderable: true},
							          {data: "hfSituation",width: "15%",orderable: true},
// 							          {data: "hfDate",width: "10%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeOneDatatables._datatables.page.info();
            							var  oneTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "操作项目新增",
                        						url:format_url("/workSafe/getHelpSafeAdd?flag="+1+"&uuid="+uuid+"&total="+oneTotal),
                        						closed: function(){
                        							workSafeOneDatatables.draw(false);
                        						}	
                        					}).render();
            							}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 270,
											title: "操作项目编辑",
											url:format_url("/workSafe/getHelpSafeEdit/"+ id+"/"+1),
											closed: function(){
												workSafeOneDatatables.draw(false);
											}
										}).render();
									}
								}
							}, {
								id:"delete",
								label:"删除",
								icon: "fa fa-trash-o bigger-130",
								className: "red del",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeOneDatatables.draw(false);
        										},
        										error:function(v,n){
        											alert('操作失败');
        										}
											});
										});
									}
								}
						}]
						}
					}).render();
						
					
					
					//安全措施结束
					$("#saveBtnTicket").on("click", function(){
// 						var oneTotal =workSafeOneDatatables._datatables.page.info().recordsTotal;
						$("#selectUser").val("");
						$("#saveOrSubmit").val("");
						$("#workTicketForm").submit();
    				});
					//保存并提交
					$("#submitBtnTicket").on("click", function(){
						workticketDialog = new A.dialog({
							title:"提交确认",
							url:format_url("/workHelpSafe/sureSubmit"),
							height:400,
							width:500
						}).render();
						
						
    				});
					$('#btnBackTicket').on('click',function(){
						var typicalTicketId='${typicalTicketId}';
						if(typicalTicketId!=null&&typicalTicketId!=''){
							A.loadPage({
								render : '#page-container',
								url : format_url("/typicalTicket/index")
							});
						}else{
							A.loadPage({
								render : '#page-container',
								url : format_url("/workHelpSafe/index?uuid="+uuid)
							});
						}
					});
					
				});
			});
			function gotoFindClassMember(){
				var classMember=$("#workClassMember").val();
				var count=0;
				var date = classMember.replace(/，/g,",").split(",");
				for(var i=0;i<date.length;i++){
					if(date[i]!=""){
						count=count+1;
					}
				}
				count=count+1;
				$("#workClassPeople").val(count);
			}
			function gotoQx(){
				var typicalTicketId='${typicalTicketId}';
				if(typicalTicketId!=null&&typicalTicketId!=''){
					A.loadPage({
						render : '#page-container',
						url : format_url("/typicalTicket/index")
					});
				}else{
					var uuid=$("#uuidWorkTicket").val();
					 $("#page-container").load(format_url("/workHelpSafe/index?uuid="+uuid));
				}
				 
			}
			function goBackToSubmitPerson(id,selectUser){//回调函数
				//给审批人  和提交标识 赋值
				$("#selectUser").val(selectUser);
				$("#saveOrSubmit").val("submit");
				$("#workTicketForm").submit();
			}
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