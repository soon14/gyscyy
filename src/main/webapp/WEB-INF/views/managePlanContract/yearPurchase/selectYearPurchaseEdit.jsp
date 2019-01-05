<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tag/Purview.tld" prefix="p"%> 
<!DOCTYPE html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<%@ include file="/WEB-INF/views/common/meta.jsp" %>
	</head>
	<body>

		<div class="page-content">
		<div class="col-lg-12 col-md-12 col-sm-12 padding-zero search-content">
				<div class="form-inline text-left " role="form">
				 <div class="clearfix">
				      <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero" >
                            <label class="searchLabel" for="searchName">设备名称</label>：
                             <input id="searchName" class="input-width text-left"  placeholder="请输入设备名称" type="text"></input>
                        </div>
                        <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                            <label class="searchLabel" for="searchTypeDiv">设备类别</label>：
                            <div class="padding-zero inputWidth  text-left">
                              <select id="searchTypeDiv" class="" ></select>
                              </div>
                        </div>
                  	  
                         <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom"style="text-align:right;" >
                            <button id="btnSearch" class="btn btn-xs btn-primary" >
                                <i class="glyphicon glyphicon-search"></i>
                                查询
                            </button>
                            <button id="btnReset" class="btn btn-xs btn-primary">
                                <i class="glyphicon glyphicon-repeat"></i>
                                重置
                            </button>
                        </div>
                        
                    </div>
                </div>
            </div>
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding">
					    <h5 class='table-title-withoutbtn header smaller blue' style='margin-bottom:0px;'>年度采购</h5>
						<table id="yearPurchase_table" class="table table-striped table-bordered table-hover" style="width:100%;">
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
	                                <th>设备名称</th>
	                                <th>设备类别</th>
	                                <th>型号规格</th>
	                                <th>数量</th>
	                                <th>计数单位</th>
	                                <th>预算单价(万元)</th>
	                                <th>预算总价(万元)</th>
	                                <th>项目名称</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
        <div style="margin-right:100px;margin-top:30px;">
       		<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
   			<button id="selectBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
   				<i class="ace-icon fa fa-floppy-o"></i>
   				确定
   			</button>
   		</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var resule=[];
			var goodsInfoArray = [];
			//var selectGoodsDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					
					//项目类别
					var statusCombobox = new A.combobox({
						render: "#searchTypeDiv",
						datasource:${typeCombobox},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
					//确定保存数据
					$("#selectBtn").on("click", function(){
						resule = yearPurchaseDatatables.getSelectRowDatas();
						var ids = [];
						if(resule.length && resule.length>0){
							for(var i =0; i<resule.length; i++){
								ids.push(resule[i].id);
								
							}
						}

						if(ids.length < 1){
							alert('请选择年度物资！');
							resule=[];
							return;
						}
						selectGoodsDialogEdit.close(resule);
// 						$(".bootbox-close-button.close").trigger("click");
    				});
					
					var existDataId = ${existDataId};
					var yearPurchaseDatatables = new A.datatables({
						render: '#yearPurchase_table',
						options: {
					        "ajax": {
					            "url": format_url("/yearPurchase/searchList?existDataId="+existDataId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					             	conditions.push({
				     					field: 'a.C_STATUS',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:'7'
				     				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
					        ordering: true,
					        order:[["3",'asc']],
							optWidth: 0,
							columns: [
							          {data:"id", visible:false,orderable:false}, 
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
							          {data: "name",width: "12%",orderable: true}, 
							          {data: "typeName",name:"type",width: "14%",orderable: true}, 
							          {data: "specification",width: "14%",orderable: true}, 
							          {data: "amount",width: "10%",orderable: true}, 
							          {data: "unitName",width: "6%",orderable: true}, 
							          {data: "budgetPrice",width: "13%",orderable: true}, 
							          {data: "totalPrice",width: "13%",orderable: true}, 
							          {data: "projectName",width: "13%",orderable: true}
							         
							          ]
										 
					
						}
					}).render();
					
					
					$('#btnSearch').on('click',function(){
						conditions=[];
						if($('#searchTypeDiv').val()){
	    					conditions.push({
	        					field: 'a.C_TYPE',
	        					fieldType:'STRING',
	        					matchType:'EQ',
	        					value:$('#searchTypeDiv').val()
	        				});
						}
				
						if($("#searchName").val()!=""){
							conditions.push({
								field: 'a.C_NAME',
								fieldType:'STRING',
								matchType:'LIKE',
								value:$("#searchName").val().trim()
							});
						}
						
						yearPurchaseDatatables.draw();
					});
					$('#btnReset').on('click',function(){
						$("#searchTypeDiv").val("");
						$("#searchTypeDiv").trigger("chosen:updated");
						$("#searchName").val('');
					});
				});
			});
        </script>
    </body>
</html>