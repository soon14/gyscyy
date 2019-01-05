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
				<li class="active">查看</li>
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
			<form class="form-horizontal" role="form"  style="margin-left:9%;width: 75%" id="editForm">
				<input id="equipAppraiseId" type="hidden"  name="id" value="${EquipAppraiseEntity.id}"/>
				<div style="margin: 7% 0 0 -10%">
			       <div class="form-group">
					    <label class="col-md-2 control-label no-padding-right">
							设备编号
					    </label>
					    <div class="col-md-4">
							 <input class="col-md-12" id="equipCode" name="equipCode" type="text" placeholder="" maxlength="20" value=" ${EquipAppraiseEntity.equipName}" readonly=readonly>
	                    </div>
	                    <label class="col-md-2 control-label no-padding-right">
							设备名称
					    </label>
					    <div class="col-md-4">
							<input class="col-md-12" id="equipName" name="equipName" type="text" placeholder="" maxlength="20" value="${EquipAppraiseEntity.equipCode} " readonly=readonly>
	                    </div>
	               </div>
			       <div class="form-group">
						<label class="col-md-2 control-label no-padding-right">
							原评
						</label>
						<div class="col-md-4">
 							<input class="col-md-12" id="equipGradePre" name="equipGradePre" type="hidden" placeholder="" maxlength="20" value="${EquipAppraiseEntity.equipGradeNow}" readonly=readonly> 
 							<input class="col-md-12" id="equipGradePre1" name="equipGradePre1" type="text" placeholder="" maxlength="20" value="${EquipAppraiseEntity.equipGradeNowName}" readonly=readonly> 
<!--  		                   <select id="equipGradePre" class="" name="equipGradePre" ></select>  -->
	                	</div>
						<label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现评
						</label>
						<div class="col-md-4">
		                   <select id="equipGradeNow" class="" name="equipGradeNow" disabled="disabled"></select>
	                	</div>
	               </div>
	                <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>评定或变动等级依据和原因
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseReason" name="equipAppraiseReason"  maxlength="500" rows="5" readonly=readonly>${EquipAppraiseEntity.equipAppraiseReason}</textarea>
	                    </div>
	               </div>
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>现在存在主要缺陷及其影响
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseInfluence" name="equipAppraiseInfluence"  maxlength="500" rows="5" readonly=readonly>${EquipAppraiseEntity.equipAppraiseComment}</textarea>
	                    </div>
	               </div>	               
			       <div class="form-group">
	      				 <label class="col-md-2 control-label no-padding-right">
							<span style="color:red;">*</span>备注
					    </label>
					    <div class="col-md-10">
							<textarea class="col-md-12" id="equipAppraiseComment" name="equipAppraiseComment"  maxlength="500" rows="5" readonly=readonly>${EquipAppraiseEntity.equipAppraiseInfluence} </textarea>
	                    </div>
	               </div>
			       <div class="form-group">
					     <label class="col-md-2 control-label no-padding-right">
										设备评价人
						</label>
						<div class="col-md-4">
		                   <input class="col-md-12" id="equipAppraisePerson" name="equipAppraisePerson" type="text" placeholder="" maxlength="64" value="${EquipAppraiseEntity.equipAppraisePerson}" readonly=readonly>
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
				
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','selectbox'], function(A){
					//获得日期
					var myDate = new Date().Format("yyyy-MM-dd");
						$("#equipAppraiseDate").val(myDate);
					
					var equipGradeNowCombobox = new A.combobox({
						render: "#equipGradeNow",
						datasource:${equipGradeNow},
						multiple:false,
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();	
					
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