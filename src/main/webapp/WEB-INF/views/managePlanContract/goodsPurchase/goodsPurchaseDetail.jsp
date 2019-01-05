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
					计划经营管理
				</li>
				<li class="active">设备物资采购计划</li>
				<li class="active">零星采购</li>
				<li class="active">查看</li>
			</ul>
		</div>
		<div class="page-content" style="margin-left:30px;margin-right:30px;">
		<div style="float:right;margin-right:50px;">
			<button id="btnBack3"class="btn btn-xs btn-primary  pull-right"data-dismiss="modal" style=" margin-top: 14px;">
       			<i class="fa fa-reply"></i>
       			返回
       		</button>
		</div>
		<h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;' >基础信息</h5>
		<div class="col-md-12" style="margin-top: 30px">
			<form class="form-horizontal" role="form"  style="margin-right:200px;" id="editgoodsForm">
			  <input class="col-md-12" id="id" name="id" type="hidden" placeholder="" maxlength="20" value="${ entity.id }">
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 项目采购名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="name" name="name" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.name }">
					</div>
						<label class="col-md-2 control-label no-padding-right">
						采购人员
					</label>
					<div class="col-md-4">
	                   <input class="col-md-12" id="createUserId" name="createUserId" type="text" readonly="readonly" placeholder="" maxlength="64" value="${userName }" >
                	</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>设备类别
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="type" name="type" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.typeName }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>物资采购计划类别
				    </label>
				    <div class="col-md-4">
				    <input class="col-md-12" id="planType" name="planType" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.planTypeName }">
					</div>
				</div>
			   <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>型号规格
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="specification" name="specification" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.specification }">
				    </div>	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 数量
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="amount" name="amount" type="number" placeholder="" readonly="readonly" maxlength="64" value="${ entity.amount }">
					</div>
				</div>
				<div class="form-group">
			      	<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计数单位
					</label>
					<div class="col-md-4">
	                  <input class="col-md-12" id="unit" name="unit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${entity.unitName}">
                	</div>
				     <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计单价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="budgePrice" name="budgePrice" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.budgePrice }" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
				    </div>	
				</div>
			   <div class="form-group">
				   
				    <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计总价(万元)
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="totalPrice" name="totalPrice" type="text" placeholder=""  readonly="readonly" maxlength="64" value="${ entity.totalPrice }" onkeyup="value=value.replace(/[\D]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\D]/g,''))">
					</div>
					 <label class="col-md-2 control-label no-padding-right">
					    <span style="color:red;">*</span>预计采购时间
				    </label>
				    <div class="col-md-4">
				    	<input class="col-md-12" id="buyTime" name="buyTime" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.buyTimeString }">
				    </div>
				</div>
			   <div class="form-group">
				   	
				    <label class="col-md-2 control-label no-padding-right">
					   <span style="color:red;">*</span> 采购计划名称
				    </label>
				    <div class="col-md-4">
					    <input class="col-md-12" id="projectName" name="projectName" type="text" placeholder="" readonly="readonly" maxlength="64" value="${ entity.projectName }">
					</div>
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>计划处室
					</label>
					<div class="col-md-4">
	                 <input class="col-md-12" id="dutyUnit" name="dutyUnit" type="text" placeholder="" maxlength="64" readOnly="readOnly" value="${unitEntity.name}">
                	</div>
				</div>
				<div class="form-group">
<!-- 			   		<label class="col-md-2 control-label no-padding-right"> -->
<!-- 						<span style="color:red;">*</span>计划编号 -->
<!-- 				    </label> -->
<!-- 				    <div class="col-md-4"> -->
<%-- 						<input class="col-md-12" id="code" name="code" type="text" readonly="readonly" placeholder="" maxlength="64" value="${ entity.code}"> --%>
<!--                     </div> -->
                	<label class="col-md-2 control-label no-padding-right">
						公司名称
				    </label>
				    <div class="col-md-4">
				     <input class="col-md-12" id="companyName" name="companyName" readonly="readonly" type="text"  placeholder="" maxlength="64" value="${organizationEntity.name }" >
                    </div>
               </div>
			      <div class="form-group">
				    <label class="col-md-2 control-label no-padding-right">
					    备注
				    </label>
				    <div class="col-md-10">
					    <textarea name="remark" placeholder="" style="height:100px; resize:none;" class="col-md-12" readonly="readonly" maxlength="128">${ entity.remark }</textarea>
				    </div>
			     </div>
				</div>
				
				 <div  id="jhgl" style="text-align: left;color: #478fca;font-weight: lighter;">
                </div>	
                <hr> 
			</form>
    		<div style="margin-right:100px;">

        	</div>
        </div>
		<script type="text/javascript">
			jQuery(function($) {
				seajs.use(['combobox','combotree','my97datepicker'], function(A){
        			
					$("#btnBack3").on("click", function(){
						A.loadPage({
							render : '#page-container',
							url : format_url("/yearPurchase/list/"+1)
						});
    				});
					var id = $('#id').val();
					if('${entity.planType}'==1){
						$("#jhgl").append(' <div class="col-xs-12" style="margin-top:0px;">'	
						     +'  <div class="widget-main no-padding">'
						     +' <form id="goodsRelation-table-form" class="form-horizontal" >'
						     +'		        <h5 class="table-title-withoutbtn header smaller blue" style="margin-bottom:0px;">计划关联</h5>	'
						     +' <table id="goodsRelation_table" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom:30px; color: black;">'
						     +' 	<thead>'
						     +' 			<tr>'
						     +'                 <th>项目名称</th>'
						     +'                 <th>设备名称</th>'
						     +'                 <th>设备类别</th>'
						     +'                 <th>型号规格</th>'
						     +'                 <th>数量</th>'
						     +'                 <th>计数单位</th>'
						     +'                 <th>预算单价(万元)</th>'
						     +'                 <th>预算总价(万元)</th>'
						     +' 			</tr>'
						     +' 		</thead>'
						     +' 	</table>'
						     +' </form>'
						     +' </div>'
						     +' </div>');
						
						var goodsRelationDatatables;
						var conditions=[];
						 goodsRelationDatatables = new A.datatables({
							render: '#goodsRelation_table',
							options: {
								    serverSide: false,
							        multiple : true,
							        checked:false,
									bInfo:false,
									paging:false,
									ordering:true,
									optWidth: 120,
									order:[["1",'asc']],
							
							columns: [
							          {data: "projectName",width: "18%",orderable: true},
							          {data: "equipName",width: "18%",orderable: false},
							          {data: "equipType",width: "10%",orderable: true}, 
							          {data: "specification",width: "10%",orderable: true}, 
							          {data: "amount",width: "10%",orderable: true},
							          {data: "unit",width: "10%",orderable: true},
							          {data: "budgePrice",width: "10%",orderable: true}, 
							          {data: "totalPrice",width: "10%",orderable: true}
							         
							          ],
		
							}
						}).render();
						 
					 		function initDataTable(){
								var params = {};
								params.length = 10;
								params.start = 0;
								params.draw = 0;
								params.conditions = [];
// 								params.goodsId = id;
								$.ajax({
// 									"url": format_url("/goodsRelation/search"),
									"url": format_url("/goodsRelation/editData/list/"+id),
									contentType: "application/json",
									dataType: 'JSON',
									type: 'POST',
									data : JSON.stringify(params),
									success: function(result){
										goodsRelationDatatables.addRows(result.data);
									}
									
								});
							}; 
							
							initDataTable();  
						
					}
				});
			});
        </script>
    </body>
</html>