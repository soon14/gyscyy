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
					<li class="active">风机工作票</li>
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
		 			
		 			<!-- <li>
			 			<a  data-toggle="tab" href="#workcarditem" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							安全风险控制卡
						</a>
		 			</li> -->
		 			
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
								<!-- <input type="hidden" id="cardSort"  name="cardSort"/>
								<input type="hidden" id="cardSortTwo" name="cardSortTwo"/>
								<input type="hidden" id="cardSortThree" name="cardSortThree"/>
								<input type="hidden" id="cardSortFour" name="cardSortFour"/> -->
								<input type="hidden" id="saveOrSubmit" name="saveOrSubmit"/>
								<input type="hidden" id="selectUser" name="selectUser"/>
								
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
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人员</label>
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
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>风机编号</label>
									<div class="col-md-4">
										<div id="equipmentCodeWorkOneDiv">
										</div>
									</div>
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>风机名称</label>
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
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">是否需停机：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="runAllowName" name="runAllowName" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="runAllowName" name="runAllowName" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">是否锁定叶轮：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="stopAllowName" name="stopAllowName" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="stopAllowName" name="stopAllowName" value="1" />
										否
										</label>
									</div>
								</div>
								<div class="form-group">
									<!-- <label class="col-md-2 control-label no-padding-right">风机编号</label>
									<div class="col-md-4">
										<input class="col-md-12" id="changeAllowIdea" name="changeAllowIdea" type="text"  maxlength="64" >
									</div> -->
									<label class="col-md-2 control-label no-padding-right">相关工作位置</label>
									<div class="col-md-4">
												<input class="col-md-12" id="endAllowIdea" name="endAllowIdea" type="text"  maxlength="64" >
									</div>
								</div>
							
	 							<!-- <div class="form-group">
										<label class="col-md-2 control-label no-padding-right">缺陷单编号</label>
										<div class="col-md-4">
											<div id="flawCodeDiv"></div>
									</div>
									
								</div> -->
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
												<input class="col-md-12" id="safeNum" name="safeNum" type="text"  maxlength="64" value="0" readonly="readonly">
										</div>
								</div>
				
    				</form>
    				<div class="widget-main no-padding">
	    				<!-- zzq修改第二次需求 -->
						<h5 class="table-title header smaller lighter blue">继电保护安全措施操作项目</h5>
			 			<table id="workAqcs-table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>执行</th>
<!-- 			                                <th>执行时间</th> -->
			                                <th>操作项目</th>
			                                <th>恢复</th>
<!-- 			                                <th>恢复时间</th> -->
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                </table>
						<!-- zzq修改第二次需求 -->
    				
						<h5 class="table-title header smaller lighter blue">应断开箱变断路器（熔断器或空气开关）、隔离开关(大约125个汉字)</h5>
			 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>安全措施</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                </table>
						<h5 class="table-title header smaller lighter blue">应合上的接地刀闸、装设的接地线（装设地点）、应设绝缘挡板、悬挂安全绳（出仓或起吊）(大约125个汉字)</h5>
			 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>安全措施</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                </table>
			 			<h5 class="table-title header smaller lighter blue">应设遮拦、应挂标示牌（位置）(大约125个汉字)</h5>
			 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>安全措施</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                	</table>
		                <h5 class="table-title header smaller lighter blue">其他安全措施和注意事项(大约125个汉字)</h5>
			 			<table id="workSafe-table-Four" class="table table-striped table-bordered table-hover" style="width:100%;margin-top:54px;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>序号</th>
			                                <th>安全措施</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                	</table>
					</div>
					
			
		    		</div>
		    		<!-- 第一个div结束 -->
		    		<!-- 第二个div开始 -->
		    		<div id="workcarditem" class="tab-pane fade">
		    		<form class="form-horizontal" role="form" style="margin-right:100px;" id="cardform">
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="guarderName" type="text" readonly="readonly"  value="${userEntity.name}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="codeCard"  type="text" readonly="readonly" placeholder="" maxlength="64" value="">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="contentCard"  disabled="disabled"  style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">作业类别</label>
									<div class="col-md-10">
										(1)
										<c:forEach items="${cardSortList}" var="cardList" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardList.name}<input id="cardSort${cardList.code}" name="cardSort1" type="checkbox"  value="${cardList.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(2)
										<c:forEach items="${cardSortListTwo}" var="cardListTwo" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListTwo.name}<input id="cardSortTwo${cardListTwo.code}" name="cardSortTwo1" type="checkbox"  value="${cardListTwo.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(3)
										<c:forEach items="${cardSortListThree}" var="cardListThree" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListThree.name}<input id="cardSortThree${cardListThree.code}" name="cardSortThree1" type="checkbox"  value="${cardListThree.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(4)
										<c:forEach items="${cardSortListFour}" var="cardListFour" varStatus="vs">
										<label class="inline" style="width: 10%">
											${cardListFour.name}<input id="cardSortFour${cardListFour.code}" name="cardSortFour1" type="checkbox"  value="${cardListFour.code}"></input>
										</label>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">补充说明</label>
									<div class="col-md-10">
										<textarea id="cardSortDescription"    placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员签名</label>
									<div class="col-md-10">
										<textarea id="workClassMemberCard"  disabled="disabled"  style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
					</form>
		    		<div class="widget-main no-padding">
						<h5 class="table-title header smaller lighter blue">作业风险分析与主要预控措施</h5>
			 			<table id="controlCardRisk-table" class="table table-striped table-bordered table-hover" style="width:100%;">
									<thead>
										<tr>
											<th style="display:none;">主键</th>
			                                <th>危险点分析</th>
			                                <th>主要控制措施</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                 </table>
			 		</div>
			 		
		    		</div>
		    		<!-- 第二个div结束 -->
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
    			<button id="submitBtnTicket" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<!-- <i class="ace-icon fa fa-floppy-o"></i> -->
    				<i class="glyphicon glyphicon-floppy-saved"></i>
    				保存并提交
    			</button>
    		</div>
    		<div class="col-md-12" style="height:40px;">
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
					$("input[name='safeFlag']").on('click',function(){
						var value=this.value;
						$("input[name='safeFlag']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeNum']").on('click',function(){
						var value=this.value;
						$("input[name='safeNum']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					
					$("input[name='runAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='runAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					
					$("input[name='stopAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='stopAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					var uuid='${uuid}';
					
					$('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
 						//1tab
						if($(e.target).attr('href') == "#workitem"){
						}
						//2tab
						if($(e.target).attr('href') == "#workcarditem"){
							$("#contentCard").val($("#content").val());
							$("#workClassMemberCard").val($("#workClassMember").val());
							controlCardRiskDatatables.draw(true);
						} 
 					});
					
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
				
					var selecttreeDh=new A.selectbox({
						id: 'equipmentWorkOneCode',
						name:'equipmentCode',
						title:'设备台账',
						url:'/equipLedger/selectEquipLedger?userUnitRels='+userUnitRels,
						render:'#equipmentCodeWorkOneDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data){
							
							var ids=[];
							var nameList='';
							var codeList='';
							
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
							content:{required:true,maxlength:128},
							equipmentCode:{required:true},
							equipmentName:{required:true},
							address:{required:true,maxlength:128},
							plandateStart:{required:true},
							plandateEnd:{required:true}
							},
						submitHandler: function (form) {
							
							//sml添加开始
							debugger;
							var safecontent1=workSafeOneDatatables.getDatas()[0].signerContent;
							var safecontent2=workSafeTwoDatatables.getDatas()[0].signerContent;
							var safecontent3=workSafeThreeDatatables.getDatas()[0].signerContent;
							var safecontent4=workSafeFourDatatables.getDatas()[0].signerContent;
						
// 							if(safecontent1=="无"|| safecontent2=="无"|| safecontent3=="无"|| safecontent4=="无"){
// 								alert('安全措施信息填写不完整！');
// 								return;
// 							}//结束
							
							///////////////////////////////////
							$.ajax({
								url: format_url('/workTicketWindMechanical/addValidate/'+uuid),
								contentType : 'application/json',
								dataType : 'JSON',
								type: 'POST',
								success: function(result){
									if(result.result == 'success'){
										//添加按钮
										var url = format_url("/workTicketWindMechanical");
										//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
										var obj = $("#workTicketForm").serializeObject();
											/* obj.cardSortDescription=$("#cardSortDescription").val(); */
										$.ajax({
											url : url,
											contentType : 'application/json',
											dataType : 'JSON',
											data : JSON.stringify(obj),
											cache: false,
											type : 'POST',
											success: function(result){
												//var workid=result.data.id;
												alert('添加成功');
												A.loadPage({
													render : '#page-container',
													url : format_url("/workTicketWindMechanical/index")
												});
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
					
					//zzq修改第二次需求   继电保护安全措施票开始
					var conditionsAqcs=[];
					var  oneTotal;
					var workSafeAqcsDatatables = new A.datatables({
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
							          {data: "hfSituation",width: "15%",orderable: true}
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
					var workSafeOneDatatables="";
					var conditionsOne=[];
					workSafeOneDatatables = new A.datatables({
						render: '#workSafe-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
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
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [/* {
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var info =workSafeOneDatatables._datatables.page.info();
										var oneTotal=info.recordsTotal+1;
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
        					} */],
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
											url:format_url("/workSafe/getEdit/"+ id+"/"+1),
											closed: function(){
												workSafeOneDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
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
						} */]
						}
					}).render();
					
					//下面是安全措施的列表 2
					
					var workSafeTwoDatatables="";
					var conditionsTwo=[];
					workSafeTwoDatatables = new A.datatables({
						render: '#workSafe-table-Two',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
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
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [/* {
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
        					} */],
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
							}/* , {
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
						} */]
						}
					}).render();
					
					//下面是安全措施的列表 3
					
					var workSafeThreeDatatables="";
					var conditionsThree=[];
					workSafeThreeDatatables = new A.datatables({
						render: '#workSafe-table-Three',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
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
							optWidth: 80,
							aLengthMenu: [5],
							columns: [{data:"id", visible:false,orderable:false}, 
// 							          {data: "orderSeq",width: "10%",orderable: true},
							          {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [/* {
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
        					} */],
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
											url:format_url("/workSafe/getEdit/"+ id+"/"+3),
											closed: function(){
												workSafeThreeDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
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
						} */]
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
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [/* {
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
        					} */],
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
											url:format_url("/workSafe/getEdit/"+ id+"/"+4),
											closed: function(){
												workSafeFourDatatables.draw(false);
											}
										}).render();
									}
								}
							}/* , {
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
						} */]
						}
					}).render();
					
					
					
					//安全措施结束
					$("#saveBtnTicket").on("click", function(){
						/* var size1 = workSafeOneDatatables.getDatas().length;
						if(size1==0){
							alert("安全措施，未填写完整!");
							return ;
						}
						var checksum=$('input[type=checkbox]:checked').length;
						if(checksum==""){
							alert("安全风险控制卡，未填写完整");
							return;
						}	
						var size = controlCardRiskDatatables.getDatas().length;
						if(size==0){
							alert("安全风险控制卡，未填写完整");
							return ;
						} */
						/* var chk_value =[]; 
						$("input[name='cardSort1']:checked").each(function(){
							chk_value.push($(this).val());
						});
						$("#cardSort").val(chk_value);
						
						var chk_valueTwo =[]; 
						$("input[name='cardSortTwo1']:checked").each(function(){
							chk_valueTwo.push($(this).val());
						});
						$("#cardSortTwo").val(chk_valueTwo);
						
						var chk_valueThree =[]; 
						$("input[name='cardSortThree1']:checked").each(function(){
							chk_valueThree.push($(this).val());
						});
						$("#cardSortThree").val(chk_valueThree);
						var chk_valueFour =[]; 
						$("input[name='cardSortFour1']:checked").each(function(){
							chk_valueFour.push($(this).val());
						});
						$("#cardSortFour").val(chk_valueFour); */
						
						$("#selectUser").val("");
						$("#saveOrSubmit").val("");
						$("#workTicketForm").submit();
    				});
					//保存并提交
					$("#submitBtnTicket").on("click", function(){
						//判断
						/* var size1 = workSafeOneDatatables.getDatas().length;
						if(size1==0){
							alert("安全措施，未填写完整!");
							return ;
						}
						var checksum=$('input[type=checkbox]:checked').length;
						if(checksum==""){
							alert("安全风险控制卡，未填写完整");
							return;
						}	
						var size = controlCardRiskDatatables.getDatas().length;
						if(size==0){
							alert("安全风险控制卡，未填写完整");
							return ;
						} */
						
						workticketDialog = new A.dialog({
							title:"提交确认",
							url:format_url("/workTicketWindMechanical/beforeSubmit"),
							height:500,
							width:500
						}).render();
						
    				});
					
					var conditions=[];
					 controlCardRiskDatatables = new A.datatables({
						render: '#controlCardRisk-table',
						options: {
					        "ajax": {
					            "url": format_url("/controlCardRisk/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions=[];
					            	conditions.push({
		            					field: 'C_UUID_CODE',
		            					fieldType:'STRING',
		            					matchType:'EQ',
		            					value:uuid
		            				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        checked: false,
					        multiple : true,
							ordering: true,
							aLengthMenu: [5],
							optWidth: 120,
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "dangerPoint",width: "40%",orderable: true},
							          {data: "mainControl",width: "40%",orderable: true},
							          ],
							toolbars: [{
	    						label:"新增",
	    						icon:"glyphicon glyphicon-plus",
	    						className:"btn-success",
	    						events:{
	        						click:function(event){
	        								workSafeTwoDialog = new A.dialog({
	                    						width: 800,
	                    						height: 350,
	                    						title: "作业风险分析与主要预控措施新增",
	                    						url:format_url("/controlCardRisk/getAdd?uuid="+uuid),
	                    						closed: function(){
	                    							controlCardRiskDatatables.draw(false);
	                    						}	
	                    					}).render();
	        						}
	    						}
	    					}
							],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil-square-o bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeTwoDialog = new A.dialog({
											width: 800,
											height: 350,
											title: "工作票控制卡风险与措施编辑",
											url:format_url('/controlCardRisk/getEdit/' + id),
											closed: function(){
												controlCardRiskDatatables.draw(false);
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
										var url =format_url('/controlCardRisk/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
	    										contentType : 'application/json',
	    										dataType : 'JSON',
	    										type : 'DELETE',
	    										success: function(result){
	    											alert('删除成功');
	    											controlCardRiskDatatables.draw(false);
	    										},
	    										error:function(v,n){
	    											alert('操作失败');
	    										}
											});
										});
									}
								}
						}
							]
						}
					}).render();
					 
					
					//处理checkbox+++++
					$('#btnBackTicket').on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/workTicketWindMechanical/index?uuid="+uuid)
						});
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
				 var uuid=$("#uuidWorkTicket").val();
				 $("#page-container").load(format_url("/workTicketWindMechanical/index?uuid="+uuid));
			}
			
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var chk_value =[]; 
				$("input[name='cardSort1']:checked").each(function(){
					chk_value.push($(this).val());
				});
				$("#cardSort").val(chk_value);
				
				var chk_valueTwo =[]; 
				$("input[name='cardSortTwo1']:checked").each(function(){
					chk_valueTwo.push($(this).val());
				});
				$("#cardSortTwo").val(chk_valueTwo);
				
				var chk_valueThree =[]; 
				$("input[name='cardSortThree1']:checked").each(function(){
					chk_valueThree.push($(this).val());
				});
				$("#cardSortThree").val(chk_valueThree);
				
				var chk_valueFour =[]; 
				$("input[name='cardSortFour1']:checked").each(function(){
					chk_valueFour.push($(this).val());
				});
				$("#cardSortFour").val(chk_valueFour);
				
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

				$("#safeNum").val(count);
				
			}
        </script>
    </body>
</html>