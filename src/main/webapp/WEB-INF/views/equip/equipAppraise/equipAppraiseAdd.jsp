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
				<li>
					设备管理
				</li>
				<li class="active">设备评价管理</li>
				<li class="active">新增</li>
			</ul><!-- /.breadcrumb -->
		</div>
		<div class="col-md-12" >
			<h5 class='table-title header smaller blue' style="margin-bottom:0px;">基础信息</h5>
			<div class="tabbable" style="margin-top:3%;">
			 		<div style="float:right; margin-top:-35px;margin-right:25px;">
						<button id="btnBack" class="btn btn-xs btn-primary" type="button">
							<i class="fa fa-reply"></i>
							返回
						</button>
					</div>	
	    	</div>			
			<form class="form-horizontal" role="form"  style="margin-left:9%;width: 75%" id="addForm">
				<div style="margin: 7% 0 0 -10%">
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>设备编号
					    </label>
					    <div class="col-md-4">
							 <div id="equipmentDivId"></div>
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="20" value="" readonly=readonly>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							原评
						</label>
						<div class="col-md-4">
<!-- 		                   <select id="equipGradePre" class="" name="equipGradePre"></select> -->
		                     <input class="col-md-12" id=""equipGradePre"" name=""equipGradePre"" type="text" placeholder="" maxlength="20" value="" readonly=readonly>
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现评
						</label>
						<div class="col-md-4">
						 <select id="equipGradeNow" class="" name="equipGradeNow"></select>
	                	</div>
	               </div>
	                <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>评定或变动等级依据和原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseReason" name="equipAppraiseReason"  placeholder="请输入评定或变动等级依据和原因" maxlength="500" rows="5" ></textarea>
	                    </div>
	               </div>
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现在存在主要缺陷及其影响
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseInfluence" name="equipAppraiseInfluence"  placeholder="请输入现在存在主要缺陷及其影响" maxlength="500" rows="5" ></textarea>
	                    </div>
	               </div>	               
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>备注
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseComment" name="equipAppraiseComment"  placeholder="请输入备注" maxlength="500" rows="5" ></textarea>
	                    </div>
	               </div>
			       <div class="form-group">
					     <label class="col-md-2 control-label no-padding-right">
										设备评价人
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAppraisePerson" name="equipAppraisePerson" type="text" placeholder="" maxlength="64" value="${userEntity}" readonly=readonly>
	                	</div>
			       		 <label class="col-md-2 control-label no-padding-right">
							设备评价日期
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipAppraiseDate" name="equipAppraiseDate" type="text" placeholder="" maxlength="" value="" readonly=readonly>
	                    </div>
	               </div>
	            </div>
	         </form>
    		<div style="margin-right:100px;margin-top:35px">
    			<button id="saveBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		</div>
		<script type="text/javascript">
			var userUnitRels = ${userUnitRels};
			//初始数据备份
			var processUserUnitRels = ${userUnitRels};
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					//设备编号
					var myDate = new Date().Format("yyyy-MM-dd");
						$("#equipAppraiseDate").val(myDate);
						
					var selectEquipLedger = new A.selectbox({
						id: 'equipCode',
						name:'equipCode',
						title:'设备台账',
// 						url:'/equipAppraise/selectEquipLedger',
						url:'/equipLedger/selectEquipLedger?userUnitRels='+userUnitRels,
						render:'#equipmentDivId',
						width:'1100',//弹出窗体的宽度  可以不写这行
						hight:'780',//弹出窗体的高度  可以不写这行
						callback: function(data,self){
							idList = "";
							nameList = "";
							codeList = "";
							for(var i=0; i< data.length;i++){
								codeList += data[i].code+',';
 								nameList += data[i].name+',';
 								idList += data[i].id+',';
						    }
							selectEquipLedger.setValue(codeList,codeList);
							if(data&&data[0]){
								$("#equipCode").val(codeList);
								$("#equipName").val(nameList);
							};
						}
					}).render();
					
					 var equipGradeNowCombobox = new A.combobox({
						render: "#equipGradeNow",
						datasource:${equipGradeNow},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render(); 
					
					
					$('#addForm').validate({
						debug:true,
						rules:  {
							id:{
								required: true,
								maxlength:20
								},
							equipCode:{
									required: true
									},
							equipmentId:{
								required: true
								},
							equipAppraiseComment:{
								required: true,
								maxlength:500
								},
							equipAppraiseReason:{
								required: true,
								maxlength:500
								},
							equipAppraiseInfluence:{
								required: true,
								maxlength:500
								},
							equipGradeNow:{
								required: true,
								},
							equipAppraiseDate:{      
								maxlength:64
								},
							equipAppraisePerson:{      
								maxlength:64
								}
							},
						submitHandler: function (form) {
							$("#saveBtn").attr({"disabled":"disabled"});
							//添加按钮
							var url = format_url("/equipAppraise/addEquipAppraise");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addForm").serializeObject();
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									A.loadPage({
										render : '#page-container',
										url : format_url("/equipAppraise/index")
									});
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					//带设备验证;
					$("#saveBtn").on("click", function(){
						$("#addForm").submit();
    				});

					//返回
					$("#btnBack").on("click", function(e){
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/equipAppraise/index'));
					});
				});
			});
        </script>
    </body>
</html>