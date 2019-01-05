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
				<form class="form-horizontal" role="form" style="margin-right:100px;" id="addFormCard">
				
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作负责人</label>
									<div class="col-md-4">
											<input class="col-md-12" id="guarderName" type="text" readonly="readonly"  value="${name}"></input>
									</div>
									<label class="col-md-2 control-label no-padding-right">编号</label>
									<div class="col-md-4">
											<input class="col-md-12" id="codeCard"  type="text" readonly="readonly" placeholder="" maxlength="64" value="">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作内容</label>
									<div class="col-md-10">
										<textarea id="contentCard"  readonly="readonly"  placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">作业类别</label>
									<div class="col-md-10">
										(1)
										<input name="cardSort" id="cardSort"  type="hidden"></input>
										<c:forEach items="${cardSortList}" var="cardList" varStatus="vs">
											${cardList.name}<input id="cardSort${cardList.code}" name="cardSort1" type="checkbox"  value="${cardList.code}"></input>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(2)
										<input name="cardSortTwo" id="cardSortTwo"  type="hidden"></input>
										<c:forEach items="${cardSortListTwo}" var="cardListTwo" varStatus="vs">
											${cardListTwo.name}<input id="cardSortTwo${cardListTwo.code}" name="cardSortTwo1" type="checkbox"  value="${cardListTwo.code}"></input>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(3)
										<input name="cardSortThree" id="cardSortThree"  type="hidden"></input>
										<c:forEach items="${cardSortListThree}" var="cardListThree" varStatus="vs">
											${cardListThree.name}<input id="cardSortThree${cardListThree.code}" name="cardSortThree1" type="checkbox"  value="${cardListThree.code}"></input>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right"></label>
									<div class="col-md-10">
										(4)
										<input name="cardSortFour" id="cardSortFour"  type="hidden"></input>
										<c:forEach items="${cardSortListFour}" var="cardListFour" varStatus="vs">
											${cardListFour.name}<input id="cardSortFour${cardListFour.code}" name="cardSortFour1" type="checkbox"  value="${cardListFour.code}"></input>
										</c:forEach>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">补充说明</label>
									<div class="col-md-10">
										<textarea id="cardSortDescription"   name="cardSortDescription" placeholder="" style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label no-padding-right">工作班成员签名</label>
									<div class="col-md-10">
										<textarea id="workClassMemberCard"  readonly="readonly"  style="height:80px; resize:none;" class="col-md-12" maxlength="128"></textarea>
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
    			<button id="saveBtnCard" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
    	</div>
		<script type="text/javascript">
		var controlCardRiskDatatables="";
		jQuery(function($) {
			seajs.use(['combobox','combotree','my97datepicker'], function(A){
				var workidcard=$("#workid").val();
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
	            					field: 'C_CONTROL_ID',
	            					fieldType:'LONG',
	            					matchType:'EQ',
	            					value:$("#workCardId").val()
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
						          {data: "dangerPoint",width: "auto",orderable: true},
						          {data: "mainControl",width: "auto",orderable: true}
						          ],
						toolbars: [{
    						label:"新增",
    						icon:"glyphicon glyphicon-plus",
    						className:"btn-success",
    						events:{
        						click:function(event){
        							var workCardId=$("#workCardId").val();
        							if(workidcard==""){
        								alert("请先填写票据信息并点击保存!");
        							}else if(workCardId==""){
        								alert("请先填写安全风险控制卡并点击保存!");
        							}else{
        								workSafeTwoDialog = new A.dialog({
                    						width: 800,
                    						height: 350,
                    						title: "作业风险分析与主要预控措施新增",
                    						url:format_url("/controlCardRisk/getAdd?cardId="+workCardId),
                    						closed: function(){
                    							controlCardRiskDatatables.draw(false);
                    						}	
                    					}).render();
        							}
        						}
    						}
    					}
						],
						btns: [{
							id: "edit",
							label:"修改",
							icon: "fa fa-pencil bigger-130",
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
				 
				
				$("#codeCard").val($("#code").val());
				$("#contentCard").val($("#content").val());
				$("#workClassMemberCard").val($("#workClassMember").val());
				//处理checkbox+++++
				
				$('#addFormCard').validate({
					debug:true,
					rules:  {},
					submitHandler: function (form) {
						//添加按钮
						var url = format_url("/workControlCard");
						//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
						var obj = $("#addFormCard").serializeObject();
						obj.workticketId=$("#workid").val();
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							data : JSON.stringify(obj),
							cache: false,
							type : 'POST',
							success: function(result){
								var workCardId=result.data.id;
								$("#workCardId").val(workCardId);
								alert('添加成功');
							},
							error:function(v,n){
								alert('添加失败');
							}
						});
					}
				});
				$("#saveBtnCard").on("click", function(){
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
					//存起来一直有的值  为了回显示
					$("#cardSortPjxx").val(chk_value);
					$("#cardSortTwoPjxx").val(chk_valueTwo);
					$("#cardSortThreePjxx").val(chk_valueThree);
					$("#cardSortFourPjxx").val(chk_valueFour);
					
					
					
					if(workidcard==""){
						alert("请先填写票据信息并点击保存!");
					}else{
						var saveId=$("#workCardId").val();
						if(saveId!=""){
							alert("安全风险控制卡的信息已经保存过了!");
						}else{
							$("#addFormCard").submit();
						}
					}
				});
				//回显示
				//控制回显多选框开始
				var onecheck=$("#cardSortPjxx").val();
				var  oneCheck =onecheck.split(",");
				$("input[name='cardSort1']").each(function(){
					if(contains(oneCheck,$(this).val())){
						 $("#cardSort"+$(this).val()).attr("checked",'true');
					}
				});
				var onecheckTwo=$("#cardSortTwoPjxx").val();
				var  oneCheckTwo =onecheckTwo.split(",");
				$("input[name='cardSortTwo1']").each(function(){
					if(contains(oneCheckTwo,$(this).val())){
						 $("#cardSortTwo"+$(this).val()).attr("checked",'true');
					}
				});
				
				var onecheckThree=$("#cardSortThreePjxx").val();
				var  oneCheckThree =onecheckThree.split(",");
				$("input[name='cardSortThree1']").each(function(){
					if(contains(oneCheckThree,$(this).val())){
						 $("#cardSortThree"+$(this).val()).attr("checked",'true');
					}
				});
				
				var onecheckFour=$("#cardSortFourPjxx").val();
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
			 $("#page-container").load(format_url('/workTicket/index'));
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