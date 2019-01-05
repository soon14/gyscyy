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
					<li class="active">工作票管理</li>
					<li class="active">电气第一种工作票</li>
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
				<div class="tab-content">
					
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
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>班组</label>
									<div class="col-md-4">
										<select class="col-md-12 chosen-select" id="groupId" name="groupId"></select>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly" name="guarderName" value="${userEntity.name}"></input>
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班成员</label>
									<div class="col-md-10">
										<textarea id="workClassMember" name="workClassMember" placeholder="名字要用逗号间隔如 1,2,3" style="height:80px; resize:none;" class="col-md-12" maxlength="128" onblur="gotoFindClassMember();"></textarea>
									</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople" name="workClassPeople" type="text" readonly="readonly" maxlength="20" value="">
								</div>
								</div>
								
								
								<div class="form-group">
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
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备编号</label>
<!-- 									<input class="col-md-12" id="equipmentCode" name="equipmentCode" type="hidden"  maxlength="128" > -->
									<div class="col-md-4">
										<div id="equipmentCodeWorkOneDiv">
										</div>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备名称</label>
									<div class="col-md-4">
												<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" readonly="readonly" maxlength="64" >
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划开始时间</label>
									<div class="col-md-4">
										<div id="plandateStartDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>计划终了时间</label>
								<div class="col-md-4">
										<div id="plandateEndDiv"></div>
								</div>
							</div>
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<div id="flawCodeDiv"></div> -->
<!-- 								</div> -->
								
<!-- 							</div> -->
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">是否需办理继电保护安全措施票：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="safeFlag" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" name="safeFlag" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">共（张）</label>
										<div class="col-md-4">
												<input class="col-md-12" id="safeNum" name="safeNum" type="text"  maxlength="64"  value="0" readonly="readonly" >
										</div>
								</div>
				<!-- zzq修改第二次需求 -->
				<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">继电保护安全措施操作项目</h5>
	 			<table id="workAqcs-table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
				<!-- zzq修改第二次需求 -->
    			<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">应断开的断路器</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title header smaller lighter blue">应拉开的隔离开关</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
	 			<h5 class="table-title header smaller lighter blue">应投切的相关电源（空气开关、熔断器、连接片）低压及二次回路</h5>
	 				<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					</div>
					<h5 class="table-title header smaller lighter blue">应合上的接地刀闸（双重名称或编号）、装设的接地线（装设地点）、应设绝缘挡板</h5>
					<table id="workSafe-table-Four" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					<h5 class="table-title header smaller lighter blue">应设遮拦、应挂标示牌（位置）</h5>
					<table id="workSafe-table-Five" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
					<h5 class="table-title header smaller lighter blue">其他安全措施和注意事项</h5>
					<table id="workSafe-table-Six" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
<!-- 	                                <th>执行情况</th> -->
                                    <th>操作</th>
                                </tr>
                            </thead>
                	</table>
<!-- 					<h5 class="table-withoutbtn header smaller" style="margin-bottom:0px;">备注：</h5> -->
<!-- 						<div id="workitemBz" class="tab-pane fade active in" style="margin-top: 20px;">	 -->
<!-- 										<div class="form-group"> -->
<!-- 											<label class="col-md-2 control-label no-padding-right">指定专责监护人</label> -->
<!-- 											<div class="col-md-4"> -->
<!-- 														<select class="col-md-12 chosen-select" id="remarkGuarderName" name="remarkGuarderName" style="width: 200px;"></select> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										<div class="form-group"> -->
<!-- 											<label class="col-md-2 control-label no-padding-right">负责监护(地点及具体工作)</label> -->
<!-- 											<div class="col-md-10"> -->
<!-- 													<textarea id="remarkResponsibleName"   name="remarkResponsibleName" -->
<!-- 													 style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 										<div class="form-group"> -->
<!-- 											<label class="col-md-2 control-label no-padding-right">其他事项（备注）</label> -->
<!-- 											<div class="col-md-10"> -->
<!-- 													<textarea id="remarkOther"   name="remarkOther" -->
<!-- 													  style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea> -->
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 						</div> -->
					</form>
		    	</div>
		    		<!-- 第一个div结束 -->
    		</div>
    	</div>
    </div>
    		<div style="margin-right:100px;margin-top: 20px;">
<!--     			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();"> -->
<!--     				<i class="ace-icon fa fa-times"></i> -->
<!--     				取消 -->
<!--     			</button> -->
    			<button id="saveBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" type="button">
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
				seajs.use(['combobox','combotree','my97datepicker','selectbox','datatables'], function(A){
					$("input[name='safeFlag']").on('click',function(){
						var value=this.value;
						$("input[name='safeFlag']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					var uuid='${uuid}';
					
					var selectboxDh=new A.selectbox({
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
					}).render();
					
					var equipList=[];
					var newEquipList=[];
// 					var nameList='';
// 					var codeList='';
// 					var ids=[];
					var selecttreeDh=new A.selectbox({
						id: 'equipmentWorkOneCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger?userUnitRels='+userUnitRels,
						render:'#equipmentCodeWorkOneDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							var nameList='';
							var codeList='';
							var ids=[];
							
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
							
							$("#equipmentWorkOneCode").attr("title",codeList);
							$("#equipmentName").attr("title",nameList);
						
					}
					}).render();
					
					//combobx组件
					var groupIdCombobox = new A.combobox({
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
					}).render();
					
					//指定专责监护人 组件
					var userListBoxCombobox = new A.combobox({
						render: "#remarkGuarderName",
						//返回数据待后台返回TODO
						datasource:${userListBox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//日期组件
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
								minDate: "#F{$dp.$D(\\'plandateStartId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					
					$('#workTicketForm').validate({
						rules:  {
							unitNameId:{required:true,maxlength:64},
							groupId:{required:true,maxlength:20},
							guarderName:{required:true,maxlength:64},
							workClassMember:{required:true,maxlength:128},
							workClassPeople:{required:true,maxlength:20},
							equipmentCode : {required : true,maxlength:128},
							equipmentName:{maxlength:128},
							content:{required:true,maxlength:128},
							address:{required:true,maxlength:128},
							plandateStart:{required:true},
							plandateEnd:{required:true},
							safeNum:{digits:true },
							},
						submitHandler: function (form) {
							if($("#workClassMember").val()==""){
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
							}
							//添加按钮
							var url = format_url("/workTicket/add");
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
											url : format_url("/workTicket/index")
										});
									}
									
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
					
					//zzq修改第二次需求   继电保护安全措施票开始
					var conditionsAqcs=[];
					var  oneTotal;
					var workSafeAqcsDatatables;
					 workSafeAqcsDatatables = new A.datatables({
						render: '#workAqcs-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsAqcs=[];
					            	conditionsAqcs.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:15//为各种票引用的安全措施的固定值
		            				});
					            	conditionsAqcs.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsAqcs;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
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
            							var safeFlag=$("input[name='safeFlag']:checked").val();
            							if(safeFlag!="0"){
            								alert("您没有选择办理继电保护安全措施票!");
            								return;
            							}
            							var info =workSafeAqcsDatatables._datatables.page.info();
            							 oneTotal=info.recordsTotal+1;
            						
            								workSafeOneDialog = new A.dialog({
                        						width: 950,
                        						height: 350,
                        						title: "操作项目新增",
                        						url:format_url("/workSafe/getHelpSafeAqcsAdd?flag="+15+"&uuid="+uuid+"&total="+oneTotal),
                        						closed: function(){
                        							workSafeAqcsDatatables.draw(false);
                        							gotoFindSafeNum(oneTotal,20);
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
											width: 950,
											height: 350,
											title: "操作项目编辑",
											url:format_url("/workSafe/getHelpSafeAqcsEdit/"+ id),
											closed: function(){
												workSafeAqcsDatatables.draw(false);
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
										var info =workSafeAqcsDatatables._datatables.page.info();
        							       oneTotal=info.recordsTotal-1;
										var url =format_url('/workSafe/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSafeAqcsDatatables.draw(false);
        											gotoFindSafeNum(oneTotal,20);
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
					//zzq修改第二次需求   继电保护安全措施票开始
					
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
						    optWidth:50,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
						
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							 var info =workSafeOneDatatables._datatables.page.info();
            							var  oneTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+1+"&uuid="+uuid+"&total="+oneTotal),
                        						closed: function(){
                        							workSafeOneDatatables.draw(false);
                        						}	
                        					}).render(); 
            							}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
                        				    var type=1;
                        				    debugger;
                        					var dataIdArray = workSafeOneDatatables.getDatasId();
                        					workSafeOneDialog = new A.dialog({
            								safeInfoArray:true,
        									title:"安全措施",
        									width:'1100',//弹出窗体的宽度  可以不写这行
											hight:'800',//弹出窗体的高度  可以不写这行
        									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
        									closed: function(){
        										var params = {};
        										params.safeInfo = safeInfoArray;
        										var result = workSafeOneDialog.resule;
        										
        									$.ajax({
													url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
													contentType: "application/json",
													data : JSON.stringify(params),
													dataType: 'JSON',
													type: 'POST',
													success: function(result){
														workSafeOneDatatables.draw(false);
													},
													error:function(v,n){
														alert('操作失败');
													}
												}) 
        									}
        							}).render(); 
            							}
        						}
        					}],
							btns: [ {
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+1),
											closed: function(){
												workSafeOneDatatables.draw(false);
											}
										}).render();
									}
								}
							},  {
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

					//下面是安全措施的列表 2
					var conditionsTwo=[];
					var workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsTwo=[];
					            	conditionsTwo.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:2
		            				});
					            	conditionsTwo.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            						    var info =workSafeTwoDatatables._datatables.page.info();
            							var  twoTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+2+"&uuid="+uuid+"&total="+twoTotal ),
                        						closed: function(){
                        							workSafeTwoDatatables.draw(false);
                        						}	
                        					}).render(); 
            						}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var type=2;
                    					var dataIdArray = workSafeTwoDatatables.getDatasId();
                    					workSafeOneDialog = new A.dialog({
        								safeInfoArray:true,
    									title:"安全措施",
    									width:'1100',//弹出窗体的宽度  可以不写这行
										hight:'800',//弹出窗体的高度  可以不写这行
    									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
    									closed: function(){
    										var params = {};
    										params.safeInfo = safeInfoArray;
    										
    									$.ajax({
												url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
												contentType: "application/json",
												data : JSON.stringify(params),
												dataType: 'JSON',
												type: 'POST',
												success: function(result){
													workSafeTwoDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											}) 
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+2),
											closed: function(){
												workSafeTwoDatatables.draw(false);
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
        											workSafeTwoDatatables.draw(false);
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
					//下面是安全措施的列表 3
					var conditionsThree=[];
					var workSafeThreeDatatables = new A.datatables({
						render: '#workSafe-table-Three',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsThree=[];
					            	conditionsThree.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:3
		            				});
					            	conditionsThree.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsThree;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							    var info =workSafeThreeDatatables._datatables.page.info();
            								var  threeTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+3+"&uuid="+uuid+"&total="+threeTotal ),
                        						closed: function(){
                        							workSafeThreeDatatables.draw(false);
                        						}	
                        					}).render(); 
            						}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var type=3;
                    					var dataIdArray = workSafeThreeDatatables.getDatasId();
                    					workSafeOneDialog = new A.dialog({
        								safeInfoArray:true,
    									title:"安全措施",
    									width:'1100',//弹出窗体的宽度  可以不写这行
										hight:'800',//弹出窗体的高度  可以不写这行
    									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
    									closed: function(){
    										var params = {};
    										params.safeInfo = safeInfoArray;
    										
    									$.ajax({
												url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
												contentType: "application/json",
												data : JSON.stringify(params),
												dataType: 'JSON',
												type: 'POST',
												success: function(result){
													workSafeThreeDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											}) 
    									}
    							}).render();
            							}
        						}
        					}],
							btns: [ {
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+3),
											closed: function(){
												workSafeThreeDatatables.draw(false);
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
        											workSafeThreeDatatables.draw(false);
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
					//下面是安全措施的列表 4
					var conditionsFour=[];
					var workSafeFourDatatables = new A.datatables({
						render: '#workSafe-table-Four',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFour=[];
					            	d.length=2147483647;
					            	conditionsFour.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:4
		            				});
					            	conditionsFour.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsFour;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							paging:false,
							checked: false,
							bInfo:false,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							 	var info =workSafeFourDatatables._datatables.page.info();
            								var  fourTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+4+"&uuid="+uuid+"&total="+fourTotal ),
                        						closed: function(){
                        							workSafeFourDatatables.draw(false);
                        						}	
                        					}).render(); 
            						}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var type=4;
                    					var dataIdArray = workSafeFourDatatables.getDatasId();
                    					workSafeOneDialog = new A.dialog({
        								safeInfoArray:true,
    									title:"安全措施",
    									width:'1100',//弹出窗体的宽度  可以不写这行
										hight:'800',//弹出窗体的高度  可以不写这行
    									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
    									closed: function(){
    										var params = {};
    										params.safeInfo = safeInfoArray;
    										
    									$.ajax({
												url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
												contentType: "application/json",
												data : JSON.stringify(params),
												dataType: 'JSON',
												type: 'POST',
												success: function(result){
													workSafeFourDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											}) 
    									}
    							}).render();
            							}
        						}
        					}],
							btns: [ {
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+4),
											closed: function(){
												workSafeFourDatatables.draw(false);
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
        											workSafeFourDatatables.draw(false);
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
					//下面是安全措施的列表 5
					var conditionsFive =[];
					var workSafeFiveDatatables = new A.datatables({
						render: '#workSafe-table-Five',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	d.length=2147483647;
					            	conditionsFive=[];
					            	conditionsFive.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:5
		            				});
					            	conditionsFive.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsFive;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeFiveDatatables._datatables.page.info();
            								var  fiveTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+5+"&uuid="+uuid+"&total="+fiveTotal ),
                        						closed: function(){
                        							workSafeFiveDatatables.draw(false);
                        						}	
                        					}).render();
            						}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var type=5;
                    					var dataIdArray = workSafeFiveDatatables.getDatasId();
                    					workSafeOneDialog = new A.dialog({
        								safeInfoArray:true,
    									title:"安全措施",
    									width:'1100',//弹出窗体的宽度  可以不写这行
										hight:'800',//弹出窗体的高度  可以不写这行
    									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
    									closed: function(){
    										var params = {};
    										params.safeInfo = safeInfoArray;
    										
    									$.ajax({
												url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
												contentType: "application/json",
												data : JSON.stringify(params),
												dataType: 'JSON',
												type: 'POST',
												success: function(result){
													workSafeFiveDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											}) 
    									}
    							}).render();
            							}
        						}
        					}],
							btns: [ {
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+5),
											closed: function(){
												workSafeFiveDatatables.draw(false);
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
        											workSafeFiveDatatables.draw(false);
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
					//下面是安全措施的列表 6
					var conditionsSix=[];
					var workSafeSixDatatables = new A.datatables({
						render: '#workSafe-table-Six',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsSix=[];
					            	d.length=2147483647;
					            	conditionsSix.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:6
		            				});
					            	conditionsSix.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditionsSix;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							paging:false,
							bInfo:false,
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true},
// 							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            								var info =workSafeSixDatatables._datatables.page.info();
            								var  sixTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+6+"&uuid="+uuid+"&total="+sixTotal ),
                        						closed: function(){
                        							workSafeSixDatatables.draw(false);
                        						}	
                        					}).render(); 
            						}
        						}
        					},{
        						label:"引用模板",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-primary",
        						render: function(data){
			                    },
        						events:{
            						click:function(event){
            							var type=6;
                    					var dataIdArray = workSafeSixDatatables.getDatasId();
                    					workSafeOneDialog = new A.dialog({
        								safeInfoArray:true,
    									title:"安全措施",
    									width:'1100',//弹出窗体的宽度  可以不写这行
										hight:'800',//弹出窗体的高度  可以不写这行
    									url:format_url("/workSafeContent/selectSafeContent?dataIdArray="+dataIdArray),
    									closed: function(){
    										var params = {};
    										params.safeInfo = safeInfoArray;
    										
    									$.ajax({
												url: format_url("/workSafe/addSafeInfo/"+uuid+"/"+type),
												contentType: "application/json",
												data : JSON.stringify(params),
												dataType: 'JSON',
												type: 'POST',
												success: function(result){
													workSafeSixDatatables.draw(false);
												},
												error:function(v,n){
													alert('操作失败');
												}
											}) 
    									}
    							}).render();
            							}
        						}
        					}],
							btns: [ {
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
											title: "安全措施编辑",
											url:format_url("/workSafe/getEdit/"+ id+"/"+6),
											closed: function(){
												workSafeSixDatatables.draw(false);
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
        											workSafeSixDatatables.draw(false);
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
								url:format_url("/workTicket/sureSubmit"),
								height:450,
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
								url : format_url("/workTicket/index?uuid="+uuid)
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
					 $("#page-container").load(format_url("/workTicket/index?uuid="+uuid));
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
			
			//打印页数
			function gotoFindSafeNum(oneTotal,limit){
			
			var count=((oneTotal%limit==0)?parseInt(oneTotal/limit):parseInt(oneTotal/limit+1));
// 				var count=0;
// 					if(oneTotal/limit <=1){
// 						count=count+1;
// 					}else if(oneTotal/limit <=2){
// 						count=count+2;
// 					}else if(oneTotal/limit <=3){
// 						count=count+3;
// 					}else if(oneTotal/limit <=4){
// 						count=count+4;
// 					}else if(oneTotal/limit <=5){
// 						count=count+5;
// 					}
				$("#safeNum").val(count);
			}
			
			//处理按错ID数组
			function safeDataIds(oSettings){
				var jsonObj = oSettings.json.data;
				localInfoArray = [];
				for(var i=0;i<jsonObj.length;i++){
					localInfoArray.push(jsonObj[i].id);
				}
			}
        </script>
    </body>
</html>