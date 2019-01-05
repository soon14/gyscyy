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
					检修管理
				</li>
				<li class="active">设备检修记录</li>
				<li class="active">设备检修记录查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
			<div style="float:right;margin-right:10px;">
				<button id="btnBack1"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style="margin-top: 14px;">
	       			<i class="fa fa-reply"></i>
	       			返回
	       		</button>
			</div>
	      <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
	      <div class="widget-main no-padding">
<!-- 		<div class="col-md-12" > -->
			<form class="form-horizontal" role="form"  style="margin-right:210px;margin-top:20px;" id="overhaulRecordForm">
			<input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 开始时间
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="startDate" name="startDate" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.startDateString }">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    结束时间
				    </label>
				    <div class="col-md-4">
				      <input class="col-md-12" id="endDate" name="endDate" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.endDateString }">
				    </div>
				</div>
			   
			   <div class="form-group">
				     <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  项目名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
				    </div>
				    <label class="col-md-2 control-label no-padding-right">
					    设备属地
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="equipLocal" name="equipLocal" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ localName }">
					</div>
				</div>
				<div class="form-group">
<!-- 				   	<label class="col-md-2 control-label no-padding-right"> -->
<!-- 					  <span style="color:red;">*</span>  检修单位 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 				     <input class="col-md-12" id="unitId" name="unitId" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${unitName }"> --%>
<!-- 				    </div>	 -->
				    <label class="col-md-2 control-label no-padding-right">
					  <span style="color:red;">*</span>  检修负责人
				    </label>
				    <div class="col-md-4">
					     <input class="col-md-12" id="dutyUserId" name="dutyUserId" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${userName }">
					</div>
				</div>
			</form>
			<div class="col-xs-12" style="margin-top:50px;">	
			<div class="widget-main no-padding">
    	      <h5 class='table-title-withoutbtn header smaller blue' style="margin-bottom: 0px;">检修日志明细</h5>
    	      	<table id="overhaulLogDetail_table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="display:none;">主键</th>
							<th class="center sorting_disabled" style="width:50px;">
     								<label class="pos-rel">
     									<input type="checkbox" class="ace" />
     									<span class="lbl"></span>
     								</label>
        					</th>
							  <th>序号</th>
<!--                               <th>外协单位</th> -->
                              <th>工作完成情况</th>
                              <th>损失电量(万kWh)</th>
                              <th>负责人</th>
                              <th>开始时间</th>
	                      	<th>操作</th>
						</tr>
					</thead>
				</table>
    	      </div>
             </div>
			</div>
    		<div style="margin-right:100px;margin-top: 20px;">
<!--         		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal"> -->
<!--         			<i class="ace-icon fa fa-times"></i> -->
<!--         			取消 -->
<!--         		</button> -->
<!--         		<button id="editBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;"> -->
<!--         			<i class="ace-icon fa fa-floppy-o"></i> -->
<!--         			保存 -->
<!--         		</button> -->
        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker','uploaddropzone','wysiwyg'], function(A){
					var conditions=[];
					var id = $('#id').val();
					  //检修日志明细
					var overhaulLogDetailDatatables = new A.datatables({
						render: '#overhaulLogDetail_table',
						options: {
					        "ajax": {
					            "url": format_url("/overhaulLogDetail/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					              	conditions.push({
										field: 'D.C_STATUS',
				    					fieldType:'STRING',
				    					matchType:'EQ',
				    					value:'1'
				    				});
					            	conditions.push({
				     					field: 'D.C_OVERHAUL_ARRANGE_ID',
				     					fieldType:'INT',
				     					matchType:'EQ',
				     					value:id
				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							optWidth: 80,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"5%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	}},
// 							          {data: "unitName",name:"unitId",width: "auto",orderable: true}, 
							          {data: "workFinishInfo",width: "auto",orderable: true}, 
							          {data: "afterMeet",width: "auto",orderable: true}, 
							          {data: "dutyPersonName",name:"dutyPerson",width: "auto",orderable: true}, 
							          {data: "logDateString",name:"logDate",width: "auto",orderable: true}
							          ],
							      	btns:[{
										id: "detail",
										label:"查看",
										icon: "fa fa-binoculars bigger-130",
										className: "blue ",
										events:{
											click: function(event, nRow, nData){
// 												var overhaulRecordId = nData.id;
// 												A.loadPage({
// 													render : '#page-container',
// 													url:format_url('/overhaulArrange/getDetailMore/'+overhaulRecordId),
// 												});
												var overhaulLogDetailId = nData.id;
												var overhaulRecordId = "${ entity.id }";
												A.loadPage({
														render : '#page-container',
														url:format_url('/overhaulArrange/getDetailMore/'+overhaulLogDetailId+'/'+overhaulRecordId),
												});
											}
									 	}
									}]
				
						}
					}).render();
					
					
					
					//返回
					$("#btnBack1").on("click", function(e){
						var orgnaizationId = ${orgnaizationId};
						window.scrollTo(0,0);
						$("#page-container").load(format_url('/overhaulRecord/getDataList/'+orgnaizationId));
					});
				});
			});
        </script>
    </body>
</html>