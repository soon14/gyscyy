<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html lang="zh">
	<head>
    	<meta charset="UTF-8">
    	<%@ include file="/WEB-INF/views/common/meta.jsp" %> 
    	<style type="text/css">
    		.tab-content-own{
    		border:1px solid #C5D0DC;
    		padding:16px 12px 50px;
    		position:relative;
    		}
    	</style>
    </head>
	<body>
		 <div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="javascript:void(0);" onclick="firstPage()">首页</a>
					</li>
					<li>
						两票管理
					</li>
						<li class="active">工作票管理</li>
					<li >风机工作票</li>
					<li class="active">修改</li>
				</ul>
		</div>
		
		<div class="col-md-12" >
		
		<div class="page-content">
		<div class="tabbable" style="margin-top: 20px;">
		 		<ul class="nav nav-tabs">
		 			<li class="active">
			 			<a id="workLiEdit" data-toggle="tab" href="#workitemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							票据信息
						</a>
		 			</li>
		 			<!-- <li>
			 			<a id="workcardLiEdit" data-toggle="tab" href="#workcarditemEdit" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							安全风险控制卡
						</a>
		 			</li> -->
		 		</ul>
		 		<div style="float:right; margin-top:-35px;margin-right:55px;">
					<button id="btnBack" class="btn btn-xs btn-primary">
						<i class="fa fa-reply"></i>
						返回
					</button>
				</div>	
				<div class="tab-content-own">
				<div id="workitemEdit" class="tab-pane fade active in">
				
		        <form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormEdit">
		        				<input type="hidden" id="id" name="id" value="${workTicketEntity.id}" />
								<input type="hidden" id="electricId" name="electricId"/>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">编码</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketEntity.code}">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>单位名称</label>
								<div class="col-md-4">
										<input  id="unitNameIdEdit"  type="hidden" value="${workTicketEntity.unitNameId}" class="col-md-12">
										<input class="col-md-12" id="unitNameId" type="hidden" readonly="readonly" name="unitNameId" value="${userEntity.unitId}"></input>
										<input class="col-md-12" id="unitName" type="text" readonly="readonly"  value="${userEntity.unitName}"></input>
								</div>
							</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>班组</label>
									<div class="col-md-4">
										<input  id="groupIdEdit"  type="hidden" value="${workTicketEntity.groupId}" class="col-md-12">
										<select class="col-md-12 chosen-select" id="groupId" name="groupId"></select>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作负责人</label>
								<div class="col-md-4">
										<input class="col-md-12" id="guarderName" type="text" readonly="readonly" name="guarderName"  value="${workTicketEntity.guarderName}"></input>
								</div>
							</div>
							
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人员</label>
									<div class="col-md-10">
										<textarea id="workClassMember" name="workClassMember" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128" onblur="gotoFindClassMember();">${workTicketEntity.workClassMember}</textarea>
									</div>
								</div>
								
								<div class="form-group">
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作班人数（包括工作负责人）</label>
								<div class="col-md-4">
											<input class="col-md-12" id="workClassPeople" name="workClassPeople" type="text" readonly="readonly" maxlength="20" value="${workTicketEntity.workClassPeople}">
								</div>
								</div>
								
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>工作内容</label>
									<div class="col-md-10">
										<textarea id="content" name="content" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>风机编号</label>
									<div class="col-md-4">
											<div id="equipmentCodeEditDiv"></div>
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>风机名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="equipmentName"
										name="equipmentName" type="text" readonly="readonly"
										maxlength="64" value="${workTicketEntity.equipmentName}">
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
									<%-- <label class="col-md-2 control-label no-padding-right">风机编号</label>
									<div class="col-md-4">
										<input class="col-md-12" id="changeAllowIdea" name="changeAllowIdea" value="${workTicketEntity.changeAllowIdea}" type="text"  maxlength="64" >
									</div> --%>
									<label class="col-md-2 control-label no-padding-right">相关工作位置</label>
									<div class="col-md-4">
												<input class="col-md-12" id="endAllowIdea" name="endAllowIdea" value="${workTicketEntity.endAllowIdea}" type="text"  maxlength="64" >
									</div>
								</div>
<%-- 							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right">缺陷单编号</label>
								<div class="col-md-4">
								<div id="flawCodeDiv"></div>
								<input class="col-md-12" id="flawCode" name="flawCode" type="text" placeholder="" maxlength="64" value="${workTicketEntity.flawCode}">
								</div>
							</div> --%>
							<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">是否需办理继电保护安全措施票：</label>
									<div class="col-md-4" style="padding-top:7px">
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="safeFlag" name="safeFlag" value="0" />
										是
										</label>
										<label class=" inline  col-md-2  no-padding-right" style="width: 20%;">
										<input type="checkbox" id="safeFlag" name="safeFlag" value="1" />
										否
										</label>
									</div>
									<label class="col-md-2 control-label no-padding-right">共（张）</label>
										<div class="col-md-4">
												<input class="col-md-12" id="safeNum" name="safeNum" type="text"  maxlength="64" value="${workTicketEntity.safeNum}" readonly="readonly">
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
<!-- 	                                <th>执行时间</th> -->
	                                <th>操作项目</th>
	                                <th>恢复</th>
<!-- 	                                <th>恢复时间</th> -->
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
		 		<div style="margin-right:100px;margin-top: 20px;">
	        		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();">
	        			<i class="ace-icon fa fa-times"></i>
	        			取消
	        		</button>
	        		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
	        			<i class="ace-icon fa fa-floppy-o"></i>
	        			保存
	        		</button>
	        	</div>
				</div>
			<div id="workcarditemEdit" class="tab-pane fade active in">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketFormCardEdit">
						<input name="id" type="hidden" value="${workControlCardEntity.id}"/>
						<input name="workticketId" type="hidden" value="${workTicketEntity.id}"/>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="guarderName" type="text" readonly="readonly"  value="${workTicketEntity.guarderName}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">工作票编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="code"  type="text" readonly="readonly" placeholder="" maxlength="64" value="${workTicketEntity.code}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="content"  readonly="readonly"  placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.content}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">作业类别</label>
									<div class="col-md-10">
										(1)
										<input name="cardSort" id="cardSort"  type="hidden"></input>
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
										<input name="cardSortTwo" id="cardSortTwo"  type="hidden"></input>
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
										<input name="cardSortThree" id="cardSortThree"  type="hidden"></input>
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
										<input name="cardSortFour" id="cardSortFour"  type="hidden"></input>
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
										<textarea id="cardSortDescription"   name="cardSortDescription" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workControlCardEntity.cardSortDescription}</textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员签名</label>
									<div class="col-md-10">
										<textarea id="workClassMember"  readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128">${workTicketEntity.workClassMember}</textarea>
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
    		<div style="margin-right:100px;margin-top: 20px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" onclick="gotoQx();">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveBtnCardTwo" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
			</div>
			</div>
			</div>
			</div>
        </div>
		<script type="text/javascript">
			var controlCardRiskDatatables="";
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					$("#workcarditemEdit").hide();
					$("input[name='safeFlag']").on('click',function(){
						var value=this.value;
						$("input[name='safeFlag']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeFlag']").each(function () {
						if(this.value=='${workTicketEntity.safeFlag}'){
				        $(this).attr('checked',true);
						}
					});
					$("input[name='safeNum']").on('click',function(){
						var value=this.value;
						$("input[name='safeNum']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='safeNum']").each(function () {
						if(this.value=='${workTicketEntity.safeNum}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("input[name='runAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='runAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='runAllowName']").each(function () {
						if(this.value=='${WorkTicketWindEntity.runAllowName}'){
				        $(this).attr('checked',true);
						}
					});
					
					$("input[name='stopAllowName']").on('click',function(){
						var value=this.value;
						$("input[name='stopAllowName']").each(function () {
							if(value!=this.value){
					        $(this).attr('checked',false);
							}
						});
					});
					$("input[name='stopAllowName']").each(function () {
						if(this.value=='${WorkTicketWindEntity.stopAllowName}'){
				        $(this).attr('checked',true);
						}
					});
					
					var equipList=[];
				
					var selecttreeDh = new A.selectbox({
						id : 'equipmentEditCode',
						name : 'equipmentCode',
						title : '设备台账',
						url : '/equipLedger/selectEquipLedger',
						render : '#equipmentCodeEditDiv',
						width : '1100',//弹出窗体的宽度  可以不写这行
						hight : '780',//弹出窗体的高度  可以不写这行
						callback : function(data) {
							
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
								
								$("#equipmentEditCode").attr("title",codeList);
								$("#equipmentName").attr("title",nameList);

						}
					}).render();
					selecttreeDh.setValue('${workTicketEntity.equipmentCode}');
					
					var qxdcodebox=new A.selectbox({
						id: 'flawCodeIdEdit',
						name:'flawCode',
						title:'缺陷单',
						url:'/workTicketWindMechanical/defectList',
						render:'#flawCodeDiv',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'720',//弹出窗体的高度  可以不写这行
						callback: function(data){
							$("#flawCode").val(data.code);
						}
					}).render();
					$("#flawCode").val('${workTicketEntity.flawCode}');//回显示
					
					//combobx组件
                	var groupIdComboboxEdit = new A.combobox({
                		render: "#groupId",
                		//返回数据待后台返回TODO
                		datasource:${groupIdCombobox},
                		multiple:false,
                		allowBlank: true,
                		options:{
                			"disable_search_threshold":10
                		}
                	}).render();
                	groupIdComboboxEdit.setValue($("#groupIdEdit").val());
					
					//日期组件
					var plandateStartDatePickerEdit = new A.my97datepicker({
						id: "plandateStartId",
						name: "plandateStart",
						render: "#plandateStartDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					plandateStartDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateStart}" type="both"/>');
					//日期组件
					var plandateEndDatePickerEdit = new A.my97datepicker({
						id: "plandateEndId",
						name: "plandateEnd",
						render: "#plandateEndDiv",
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "",
								minDate: "#F{$dp.$D(\\'plandateStartId\\')}",
								dateFmt: "yyyy-MM-dd HH:mm"
						}
					}).render();
					plandateEndDatePickerEdit.setValue('<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${workTicketEntity.plandateEnd}" type="both"/>');
					
					
					//指定专责监护人 组件
					var userListBoxCombobox = new A.combobox({
						render: "#remarkGuarderId",
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
					userListBoxCombobox.setValue(${WorkTicketWindEntity.remarkGuarderId});
					
					$('#workTicketFormEdit').validate({
						debug:true,
						rules: {
							unitNameId:{required:true,maxlength:64},
							groupId:{required:true,maxlength:20},
							guarderName:{required:true,maxlength:64},
							workClassMember:{required:true,maxlength:128},
							workClassPeople:{required:true,maxlength:20},
							content:{required:true,maxlength:128},
							equipmentCode:{required:true},
							equipmentName:{required:true},
							address:{required:true,maxlength:128},
							plandateStart:{required:true,maxlength:20},
							plandateEnd:{required:true,maxlength:20}
							},
						submitHandler: function (form) {
							var id = ${workTicketEntity.id};
							//////////////////////////////////
							$.ajax({
								url: format_url('/workTicketWindMechanical/editValidate/'+id),
								contentType : 'application/json',
								dataType : 'JSON',
								type: 'POST',
								success: function(result){
									if(result.result == 'success'){
										//修改按钮
										var url = format_url("/workTicketWindMechanical/update/"+id);
										//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
										var obj = $("#workTicketFormEdit").serializeObject();
										$.ajax({
											url : url,
											contentType : 'application/json',
											dataType : 'JSON',
											data : JSON.stringify(obj),
											cache: false,
											type : 'POST',
											success: function(result){
												alert('操作成功');
												A.loadPage({
													render : '#page-container',
													url : format_url("/workTicketWindMechanical/index")
												});
											},
											error:function(v,n){
												alert('操作失败');
											}
										});
										
									} else{
   										alert(result.errorMsg);
   									}
								}
							});	
						}
					});
					$("#editBtn").on("click", function(){
						
						
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
						$("#electricId").val(${WorkTicketWindEntity.id});
						var workPerson = '${workTicketEntity.guarderId}';
	                    var loginUser = '${userEntity.id}';
	                    if(workPerson!=loginUser){
	                       alert("当前登陆人不是工作负责人不能修改!");
	                       return;
	                    }else{
	   						$("#workTicketFormEdit").submit();
	                    }
    				});
					
					
					$('#btnBack').on('click',function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/workTicketWindMechanical/index")
						});
					});
					
					
//下面是安全措施的列表 1
					var workid='${workTicketEntity.id}';
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
										field : 'C_WORKTICKET_ID',
										fieldType : 'LONG',
										matchType : 'EQ',
										value : '${workTicketEntity.id}'
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
            							var info =workSafeAqcsDatatables._datatables.page.info();
            						 oneTotal=info.recordsTotal+1;
            								workSafeOneDialog = new A.dialog({
                        						width: 950,
                        						height: 350,
                        						title: "操作项目新增",
                        						url:format_url("/workSafe/getHelpSafeAqcsAdd?flag="+15+"&workId="+ workid+"&total="+oneTotal),
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
					            	conditionsOne.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:1
		            				});
					            	conditionsOne.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
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
        								var  oneTotal=info.recordsTotal+1;
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 300,
                    						title: "安全措施新增",
                    						url:format_url("/workSafe/getAdd?flag="+1+"&workId="+workid+"&total="+oneTotal ),
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
											height: 300,
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
					            	conditionsTwo.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:2
		            				});
					            	conditionsTwo.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
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
                    						url:format_url("/workSafe/getAdd?flag="+2+"&workId="+workid+"&total="+twoTotal ),
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
											height: 300,
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
					            	conditionsThree.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:3
		            				});
					            	conditionsThree.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:'${workTicketEntity.id}'
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
                    						url:format_url("/workSafe/getAdd?flag="+3+"&workId="+workid+"&total="+threeTotal ),
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
											height: 300,
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
					var conditionsFour = [];
					var workSafeFourDatatables = new A.datatables(
							{
								render : '#workSafe-table-Four',
								options : {
									"ajax" : {
										"url" : format_url("/workSafe/search"),
										"contentType" : "application/json",
										"type" : "POST",
										"dataType" : "JSON",
										"data" : function(d) {
											conditionsFour = [];
											d.length = 2147483647;
											conditionsFour
													.push({
														field : 'C_SAFE_TYPE',
														fieldType : 'INT',
														matchType : 'EQ',
														value : 4
													});
											conditionsFour
													.push({
														field : 'C_WORKTICKET_ID',
														fieldType : 'LONG',
														matchType : 'EQ',
														value : '${workTicketEntity.id}'
													});
											d.conditions = conditionsFour;
											return JSON
													.stringify(d);
										}
									},
									multiple : true,
									ordering : true,
									paging : false,
									checked : false,
									bInfo : false,
									columns : [ {data : "id",visible : false,orderable : false}, 
// 									            {data : "orderSeq",width : "10%",orderable : true}, 
									            {orderable: false,"width":"10%", "sClass": "left",render : function(data, type, row, meta) {
									                   var startIndex = meta.settings._iDisplayStart;  
									                   row.start=startIndex + meta.row;
									                   return startIndex + meta.row + 1;  
									               	} },
									            {data : "signerContent",width : "80%",orderable : true} 
									            ],
									toolbars : [ /* {
										label : "新增",
										icon : "glyphicon glyphicon-plus",
										className : "btn-success",
										events : {
											click : function(event) {
												var info = workSafeFourDatatables._datatables.page.info();
												var fourTotal = info.recordsTotal + 1;
												workSafeOneDialog = new A.dialog(
														{
															width : 800,
															height : 300,
															title : "安全措施新增",
															url : format_url("/workSafe/getAdd?flag="
																	+ 4
																	+ "&workId="
																	+ workid
																	+ "&total="
																	+ fourTotal),
															closed : function() {
																workSafeFourDatatables
																		.draw(false);
															}
														}).render();
											}
										}
									} */ ],
									btns : [
											{
												id : "edit",
												label : "修改",
												icon : "fa fa-pencil-square-o bigger-130",
												className : "green edit",
												events : {
													click : function(
															event,
															nRow,
															nData) {
														var id = nData.id;
														workSafeOneDialog = new A.dialog(
																{
																	width : 800,
																	height : 270,
																	title : "安全措施编辑",
																	url : format_url("/workSafe/getEdit/"
																			+ id
																			+ "/"
																			+ 4),
																	closed : function() {
																		workSafeFourDatatables
																				.draw(false);
																	}
																})
																.render();
													}
												}
											}
											/* ,{
												id : "delete",
												label : "删除",
												icon : "fa fa-trash-o bigger-130",
												className : "red del",
												events : {
													click : function(
															event,
															nRow,
															nData) {
														var id = nData.id;
														var url = format_url('/workSafe/'
																+ id);
														A.confirm('您确认删除么？',
																		function() {
																			$.ajax({
																						url : url,
																						contentType : 'application/json',
																						dataType : 'JSON',
																						type : 'DELETE',
																						success : function(
																								result) {
																							alert('删除成功');
																							workSafeFourDatatables
																									.draw(false);
																						},
																						error : function(
																								v,
																								n) {
																							alert('操作失败');
																						}
																					});
																		});
													}
												}
											}  */]
								}
							}).render();
					
					 
					 
					 //第二个tab
					 $('#workTicketFormCardEdit').validate({
							debug:true,
							rules:  {},
							submitHandler: function (form) {
								//添加按钮
								var cardId='${workControlCardEntity.id}';
								var url = format_url("/workControlCard/"+cardId);
								//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
								var obj = $("#workTicketFormCardEdit").serializeObject();
								$.ajax({
									url : url,
									contentType : 'application/json',
									dataType : 'JSON',
									data : JSON.stringify(obj),
									cache: false,
									type : 'POST',
									success: function(result){
										alert('修改成功');
										A.loadPage({
											render : '#page-container',
											url : format_url("/workTicketWindMechanical/index")
										});
									},
									error:function(v,n){
										alert('添加失败');
									}
								});
							}
						});
						$("#saveBtnCardTwo").on("click", function(){
							
							var checksum=$('input[type=checkbox]:checked').length;
							if(checksum==""){
								alert("安全风险控制卡，未填写完整");
								return;
							}	
							var size = controlCardRiskDatatables.getDatas().length;
							if(size==0){
								alert("安全风险控制卡，未填写完整");
								return ;
							}
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
							
							$("#workTicketFormCardEdit").submit();

	    				});
						
						
						
						var conditionsCard=[];
						 controlCardRiskDatatables = new A.datatables({
							render: '#controlCardRisk-table',
							options: {
						        "ajax": {
						            "url": format_url("/controlCardRisk/search"),
						            "contentType": "application/json",
						            "type": "POST",
						            "dataType": "JSON",
						            "data": function (d) {
						            	conditionsCard.push({
			            					field: 'C_CONTROL_ID',
			            					fieldType:'LONG',
			            					matchType:'EQ',
			            					value:'${workControlCardEntity.id}'
			            				});
						            	d.conditions = conditionsCard;
						                return JSON.stringify(d);
						              }
						        },
						        checked: false,
						        multiple : true,
								ordering: true,
								optWidth: 120,
								aLengthMenu: [5],
								columns: [{data:"id", visible:false,orderable:false}, 
								          {data: "dangerPoint",width: "auto",orderable: true},
								          {data: "mainControl",width: "auto",orderable: true}
								          ],
								toolbars: [{
	        						label:"新增",
	        						icon:"glyphicon glyphicon-plus",
	        						className:"btn-success",
	        						events:{
	            						click:function(event){
	            							var cardId='${workControlCardEntity.id}';
	            							workSafeTwoDialog = new A.dialog({
	                    						width: 800,
	                    						height: 350,
	                    						title: "工作票控制卡风险与措施增加",
	                    						url:format_url('/controlCardRisk/getAdd?cardId='+cardId),
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
						 
						//控制回显多选框开始
						var onecheck='${workControlCardEntity.cardSort}';
						var  oneCheck =onecheck.split(",");
						$("input[name='cardSort1']").each(function(){
							if(contains(oneCheck,$(this).val())){
								 $("#cardSort"+$(this).val()).attr("checked",'true');
							}
						});
						var onecheckTwo='${workControlCardEntity.cardSortTwo}';
						var  oneCheckTwo =onecheckTwo.split(",");
						$("input[name='cardSortTwo1']").each(function(){
							if(contains(oneCheckTwo,$(this).val())){
								 $("#cardSortTwo"+$(this).val()).attr("checked",'true');
							}
						});
						
						var onecheckThree='${workControlCardEntity.cardSortThree}';
						var  oneCheckThree =onecheckThree.split(",");
						$("input[name='cardSortThree1']").each(function(){
							if(contains(oneCheckThree,$(this).val())){
								 $("#cardSortThree"+$(this).val()).attr("checked",'true');
							}
						});
						
						var onecheckFour='${workControlCardEntity.cardSortFour}';
						var  oneCheckFour =onecheckFour.split(",");
						$("input[name='cardSortFour1']").each(function(){
							if(contains(oneCheckFour,$(this).val())){
								 $("#cardSortFour"+$(this).val()).attr("checked",'true');
							}
						});
					   //控制回显多选框结束
				});
			});
			
			
			function gotoQx(){
				 $("#page-container").load(format_url('/workTicketWindMechanical/index'));
			}
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