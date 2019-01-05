<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="zh">
    <head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>
		<div class="page-content">
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="workTicketForm">
				
				<input id="cardSortPjxx" type="hidden"/>
				<input id="cardSortTwoPjxx" type="hidden"/>
				<input id="cardSortThreePjxx" type="hidden"/>
				<input id="cardSortFourPjxx" type="hidden"/>
				<input type="hidden" id="kgsgTotal"   />
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
											<input class="col-md-12" id="workClassPeople" name="workClassPeople" type="text" placeholder="" maxlength="20" value="">
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
									<div class="col-md-4">
											<input class="col-md-12" id="equipmentCode" name="equipmentCode" type="text" placeholder="" maxlength="64" value="">
								</div>
								<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>设备名称</label>
								<div class="col-md-4">
											<input class="col-md-12" id="equipmentName" name="equipmentName" type="text" placeholder="" maxlength="64" value="">
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
									<label class="col-md-2 control-label no-padding-right">缺陷单编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="flawCode" name="flawCode" type="text" placeholder="" maxlength="64" value="">
								</div>
								
							</div>
				
    		</form>
    		<div class="widget-main no-padding">
				<h5 class="table-title header smaller lighter blue">应断开的断路器、隔离开关，应取下的熔断器，应解除的继保护连接片等(包括填写前已断开、取下、解除的，注明编号)</h5>
	 			<table id="workSafe-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
	                                <th>执行情况</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
				<h5 class="table-title header smaller lighter blue">应装设接地线、应合上接地刀闸(注明确切地点、名称及接地线编号，接地线编号运行填写)</h5>
	 			<table id="workSafe-table-Two" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
	                                <th>执行情况</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
	 			<h5 class="table-title header smaller lighter blue">应设遮栏、应挂标示牌及防止二次回路误碰等措施</h5>
	 			<table id="workSafe-table-Three" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>序号</th>
	                                <th>安全措施</th>
	                                <th>执行情况</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                </table>
                
                 <!-- 这个特殊 放一行2个 -->
                <table style="width:100%;">
	                <tr>
	                <td style="width:48%;">
	                <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">工作地点保留带电部分或注意事项(由工作票签发人填写)</h5>
		 			<table id="workSafe-table-Four" class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>工作地点保留带电部分或注意事项</th>
	                                    <!-- <th>操作</th> -->
	                                </tr>
	                            </thead>
	                </table>
	                </td>
	                <td style="width:4%;"></td>
	                <td style="width:48%;">
	                <h5 class="table-title-withoutbtn header smaller lighter blue" style="margin-bottom:0px;">补充工作地点保留带电部分和安全措施(由工作许可人填写)</h5>
		 			<table id="workSafe-table-Five" class="table table-striped table-bordered table-hover" style="width:100%;">
								<thead>
									<tr>
										<th style="display:none;">主键</th>
		                                <th>补充工作地点保留带电部分和安全措施</th>
	                                    <!-- <th>操作</th> -->
	                                </tr>
	                            </thead>
	                </table>
	                </td>
	                </tr>
                </table>
                <!-- 这个特殊 放一行2个 -->
                 
                <h5 class="table-title header smaller lighter blue">工作票间断措施(间断只允许五天，等级检修不用填写)</h5>
	 			<table id="workSituation-table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th>开工时间</th>
	                                <th>工作负责人名字</th>
	                                <th>工作许可人名字</th>
	                                <th>收工时间</th>
	                                <th>工作负责人名字</th>
	                                <th>工作许可人名字</th>
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
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
    	</div>
		<script type="text/javascript">
			var workSafeOneDialog;
			var workSafeTwoDialog;
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
					
					
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
								dateFmt: "yyyy-MM-dd HH:mm:ss"
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
								dateFmt: "yyyy-MM-dd HH:mm:ss"
						}
					}).render();
					
					$('#workTicketForm').validate({
						debug:true,
						rules:  {
							unitNameId:{required:true,maxlength:64},
							groupId:{required:true,maxlength:20},
							guarderName:{required:true,maxlength:64},
							workClassMember:{required:true,maxlength:128},
							workClassPeople:{required:true,maxlength:20},
							equipmentCode:{required:true,maxlength:64},
							equipmentName:{required:true,maxlength:64},
							content:{required:true,maxlength:128},
							address:{required:true,maxlength:128},
							plandateStart:{required:true},
							plandateEnd:{required:true}
							},
						submitHandler: function (form) {
							//添加按钮
							var url = format_url("/workTicketLineTwo");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#workTicketForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									var workid=result.data.id;
									$("#workid").val(workid);
									alert('添加成功');
									/* A.loadPage({
										render : '#page-container',
										url : format_url("/workTicket/index")
									}); */
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					
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
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsOne;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5,10,20],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+1+"&workId="+workid),
                        						closed: function(){
                        							workSafeOneDatatables.draw(false);
                        						}	
                        					}).render();
            							}
            							
            							
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
											url:format_url("/workSafe/getEdit/"+ id+"/"+1),
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
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsTwo;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5,10,20],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+2+"&workId="+workid ),
                        						closed: function(){
                        							workSafeTwoDatatables.draw(false);
                        						}	
                        					}).render();
            							}
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
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsThree;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [5,10,20],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "orderSeq",width: "10%",orderable: true},
							          {data: "signerContent",width: "60%",orderable: true},
							          {data: "executeSituation",width: "20%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 300,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+3+"&workId="+workid ),
                        						closed: function(){
                        							workSafeThreeDatatables.draw(false);
                        						}	
                        					}).render();
            							}
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
					var workSafeFourDatatables="";
					var conditionsFour=[];
					workSafeFourDatatables = new A.datatables({
						render: '#workSafe-table-Four',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFour=[];
					            	conditionsFour.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:4
		            				});
					            	conditionsFour.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsFour;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [3],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
						
						}
					}).render();
					/* var workSafeFourDatatables="";
					var conditionsFour=[];
					workSafeFourDatatables = new A.datatables({
						render: '#workSafe-table-Four',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFour=[];
					            	conditionsFour.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:4
		            				});
					            	conditionsFour.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsFour;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [3],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 230,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+4 +"&workId="+workid),
                        						closed: function(){
                        							workSafeFourDatatables.draw(false);
                        						}	
                        					}).render();
            							}
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 230,
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
					}).render(); */
					
					
					//下面是安全措施的列表 5
					var workSafeFiveDatatables="";
					var conditionsFive=[];
					workSafeFiveDatatables = new A.datatables({
						render: '#workSafe-table-Five',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFive=[];
					            	conditionsFive.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:5
		            				});
					            	conditionsFive.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.flag="five";
					            	d.conditions = conditionsFive;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [3],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "signerContent",width: "80%",orderable: true}
							          ]
						}
					}).render();
					/* var workSafeFiveDatatables="";
					var conditionsFive=[];
					workSafeFiveDatatables = new A.datatables({
						render: '#workSafe-table-Five',
						options: {
					        "ajax": {
					            "url": format_url("/workSafe/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsFive=[];
					            	conditionsFive.push({
		            					field: 'C_SAFE_TYPE',
		            					fieldType:'INT',
		            					matchType:'EQ',
		            					value:5
		            				});
					            	conditionsFive.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.flag="five";
					            	d.conditions = conditionsFive;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							aLengthMenu: [3],
							columns: [{data:"id", visible:false,orderable:false}, 
							          {data: "signerContent",width: "80%",orderable: true}
							          ],
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								workSafeOneDialog = new A.dialog({
                        						width: 800,
                        						height: 230,
                        						title: "安全措施新增",
                        						url:format_url("/workSafe/getAdd?flag="+5+"&workId="+workid ),
                        						closed: function(){
                        							workSafeFiveDatatables.draw(false);
                        						}	
                        					}).render();
            							}
            						}
        						}
        					}],
							btns: [{
								id: "edit",
								label:"修改",
								icon: "fa fa-pencil bigger-130",
								className: "green edit",
								events:{
									click: function(event, nRow, nData){
										var id = nData.id;
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 230,
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
					}).render(); */
					
					//开工收工情况
					var conditionsKgsg=[];
					var workSituationDatatables="";
					 workSituationDatatables = new A.datatables({
						render: '#workSituation-table',
						options: {
					        "ajax": {
					            "url": format_url("/workSituation/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditionsKgsg=[];
					            	conditionsKgsg.push({
		            					field: 'C_WORKTICKET_ID',
		            					fieldType:'LONG',
		            					matchType:'EQ',
		            					value:$("#workid").val()
		            				});
					            	d.conditions = conditionsKgsg;
					                return JSON.stringify(d);
					              }
					        },
					        checked: false,
					        multiple : true,
							ordering: true,
							optWidth: 80,
							aLengthMenu: [5,10,20],
							columns: [{data:"id", visible:false,orderable:false},
							          {data: "startDate",width: "auto",orderable: true}, 
							          {data: "startPicName",width: "auto",orderable: true},
							          {data: "startAllowName",width: "auto",orderable: true},
							          {data: "endDate",width: "auto",orderable: true}, 
							          {data: "endPicName",width: "0%",orderable: true}, 
							          {data: "endAllowName",width: "0%",orderable: true}
							          ],
							fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											$("#kgsgTotal").val(iTotal);
										},
							toolbars: [{
        						label:"新增",
        						icon:"glyphicon glyphicon-plus",
        						className:"btn-success",
        						events:{
            						click:function(event){
            							var workid=$("#workid").val();
            							if(workid==""){
            								alert("请先填写票据信息并点击保存!");
            							}else{
            								var kgsgTotal=$("#kgsgTotal").val();
                							if(kgsgTotal=="5"){
                								alert("最多只能添加5条就录!");
                							}else{
                								workSafeOneDialog = new A.dialog({
                            						width: 800,
                            						height: 450,
                            						title: "开工和收工情况增加",
                            						url:format_url("/workSituation/getAdd?workId="+workid),
                            						closed: function(){
                            							workSituationDatatables.draw(false);
                            						}	
                            					}).render();
                							}
            								
            							}
            							
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
										workSafeOneDialog = new A.dialog({
											width: 800,
											height: 450,
											title: "开工和收工情况编辑",
											url:format_url('/workSituation/getEdit/' + id),
											closed: function(){
												workSituationDatatables.draw(false);
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
										var url =format_url('/workSituation/'+ id);
										A.confirm('您确认删除么？',function(){
											$.ajax({
												url : url,
        										contentType : 'application/json',
        										dataType : 'JSON',
        										type : 'DELETE',
        										success: function(result){
        											alert('删除成功');
        											workSituationDatatables.draw(false);
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
					$("#saveBtn").on("click", function(){
						var saveId=$("#workid").val();
						if(saveId!=""){
							alert("票据信息已经保存过了!");
						}else{
							$("#workTicketForm").submit();
						}
    				});
    				
				});
			});
			
			function gotoFindClassMember(){
				var classMember=$("#workClassMember").val();
				var count=0;
				var date = classMember.split(",");
				for(var i=0;i<date.length;i++){
					if(date[i]!=""){
						count=count+1;
					}
				}
				count=count+1;
				$("#workClassPeople").val(count);
			}
			function gotoQx(){
				 $("#page-container").load(format_url('/workTicketLineTwo/index'));
			}
        </script>
    </body>
</html>