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
			<div class="col-lg-12 col-md-12 col-sm-12 search-content  padding-zero">
				<div class="form-inline text-left" role="form">
				<div class="clearfix">
					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                       <label class="searchLabel">场站名称</label>：
                       <div class="padding-zero inputWidth  text-left">
                          <select id="farmId1" class="" ></select>
                       </div>
                    </div>
<!-- 					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!--                        <label class="searchLabel">指标名称</label>： -->
<!--                        <div class="padding-zero inputWidth  text-left"> -->
<!--                           <select id="quotaId" class="" ></select> -->
<!--                        </div> -->
<!--                     </div> -->
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                        <label >计划年份：</label>
                        <div class="form-group dateInputOther padding-zero text-left">
                            <div id="statisticsYearDiv1" style="border:none; padding:0px;"></div>
                        </div>
                    </div>
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                       <button id="btnSearchAccident1" class="btn btn-xs btn-primary">
                           <i class="glyphicon glyphicon-search"></i>
                      		     查询
                       </button>
                       <button id="btnResetAccident1" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title header smaller blue' >${tableName }</h5>
						<table id="annualProductionCapacity_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">场站名称</th>
	                                <th style="text-align:center">指标名称</th>
	                                <th style="text-align:center">计划对比</th>
	                                <th style="text-align:center">1月</th>
	                                <th style="text-align:center">2月</th>
	                                <th style="text-align:center">3月</th>
	                                <th style="text-align:center">4月</th>
	                                <th style="text-align:center">5月</th>
	                                <th style="text-align:center">6月</th>
	                                <th style="text-align:center">7月</th>
	                                <th style="text-align:center">8月</th>
	                                <th style="text-align:center">9月</th>
	                                <th style="text-align:center">10月</th>
	                                <th style="text-align:center">11月</th>
	                                <th style="text-align:center">12月</th>
	                                <th style="text-align:center">合计</th>
	                                <th style="text-align:center">操作</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var exportExcelAccident="";
			var workSafeOneDialog;
			jQuery(function($) {
				seajs.use(['datatables', 'confirm','combobox','my97datepicker'], function(A){
					var conditions=[];
					var type = ${type};
					//场站名称
					var farmIdCombobox1 = new A.combobox({
						render: "#farmId1",
						datasource:${farmIdList},
						//multiple为true时select可以多选
						multiple:false,
						//allowBlank为false表示不允许为空
						allowBlank: true,
						options:{
							"disable_search_threshold":10
						}
					}).render();
					
// 					//指标名称
// 					var quotaIdCombobox = new A.combobox({
// 						render: "#quotaId",
// 						datasource:${quotaIdList},
// 						//multiple为true时select可以多选
// 						multiple:false,
// 						//allowBlank为false表示不允许为空
// 						allowBlank: true,
// 						options:{
// 							"disable_search_threshold":10
// 						}
// 					}).render();
					
					//统计年份
					var statisticsYear1 = new A.my97datepicker({
						id: 'statisticsYearId1',
						name:'year',
						render:'#statisticsYearDiv1',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "${nowYear}",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
 					statisticsYear1.setValue('${nowYear}'); 
					
					var annualProductionCapacityDatatables = new A.datatables({
						render: '#annualProductionCapacity_table',
						options: {
					        "ajax": {
					            "url": format_url("/annualProductionCapacity/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'ac.C_TYPE',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:type
				     				});
					            	conditions.push({
			        					field: 'date_format(ac.C_YEAR,"%Y")',
			        					fieldType:'DATE',
			        					matchType:'EQ',
			        					value:$('#statisticsYearId1').val()
			        				});
					            	d.type = type;
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:false,
							columns: [{data:"id", visible:false},
							          {data: "farmName",width: "8%",orderable: false,
							        	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHide=="show" ) {
								        	       	$(td).attr('rowspan',8);
								        	       	$(td).attr('align','center');
								        	       	var height=8*32;
								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      },
							          {data: "quotaName",width: "10%",
								    	  "createdCell": function (td, cellData, rowData, row, col) { 
								        	     if (rowData.tdHideQuota=="show" ) {
								        	       	$(td).attr('rowspan',2);
								        	       	$(td).attr('align','center');
								        	       	var height=2*32;
								        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
								        	     }else{
								        	    	 $(td).hide();
								        	     }
								        	}, 
								      }, 
							          {data: "planCompareName",width: "6%","sClass": "center"}, 
							          {data: "jan",width: "5.6%","sClass": "center"}, 
							          {data: "feb",width: "5.6%","sClass": "center"}, 
							          {data: "mar",width: "5.6%","sClass": "center"},  
							          {data: "apr",width: "5.6%","sClass": "center"},  
							          {data: "may",width: "5.6%","sClass": "center"},  
							          {data: "jun",width: "5.6%","sClass": "center"},  
							          {data: "jul",width: "5.6%","sClass": "center"},  
							          {data: "aug",width: "5.6%","sClass": "center"},  
							          {data: "sep",width: "5.6%","sClass": "center"},  
							          {data: "oct",width: "5.6%","sClass": "center"},  
							          {data: "nov",width: "5.6%","sClass": "center"},  
							          {data: "dec",width: "5.6%","sClass": "center"},  
							          {data: "total",width: "6%","sClass": "center"}
							          ],
							          fnPreDrawCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
											 if(exportExcelAccident){
												 exportExcels(format_url("/annualProductionCapacity/exportExcel/"+type),JSON.stringify(conditions));
											 }
											 exportExcelAccident="";
										 },
							toolbars: [
// 							           {
//         						label:"新增",
//         						icon:"glyphicon glyphicon-plus",
//         						className:"btn-success",
//         						events:{
//             						click:function(event){
//             							A.loadPage({
// 											render : '#page-container',
// 											url : format_url('/produceReply/getAdd')
// 										});
//             						}
//         						}
//         					},
							{
        						label:"批量导入",
        						icon:"glyphicon glyphicon-upload",
        						className:"btn-primary",
        						events:{
            						click:function(event){
            							workSafeOneDialog = new A.dialog({
                    						width: 800,
                    						height: 300,
                    						title: "批量导入",
                    						url:format_url("/annualProductionCapacity/getBatchAdd/"+type),
                    						closed: function(){
                    						}	
                    					}).render();
            						}
        						}
        					},{  
        						id:"dc",
        						label:"导出",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
            							$('#btnSearchAccident1').click();
            							exportExcelAccident="exportExcelAccident";
            						}
        						}
        					},{  
        						id:"dc",
        						label:"模板下载",
        						icon:"glyphicon glyphicon-download",
        						className:"btn-primary",
        						events:{
        							click:function(event){
    								 	exportExcels(format_url("/annualProductionCapacity/exportExcelModel/"+type));
            						}
        						}
        					}],
							btns: [
// 							{
// 							id:"submit",
// 						 	label: "提交审核",
// 							icon: "fa fa-check-square-o bigger-130",
// 							className: "edit",
// 							render: function(btnNode, data){
// 								var loginUserId = '${sysUserEntity.id}'
// 								if(data.planCompareId!="2"){
// 									btnNode.hide();
// 								}
// // 								if(data.uploadPerson!=loginUserId){
// // 									btnNode.hide();
// // 								}
// 							},
// 							events:{
// 								click: function(event, nRow, nData){
// 									debugger;
// 									var id = nData.id;
// 												workticketDialog = new A.dialog({
// 													title:"选择单位负责人",
// 													url:format_url("/accidentMeasuresPlan/sureSubmit?id="+ id),
// 													height:500,
// 													width:600,
// 												}).render();
// 								}
// 							}
// 						}, 
						{
							id: "history",
							label:"历史",
							icon: "fa fa-history bigger-130",
							className: "red ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
// 									if(!id){
// 										alert("没有历史数据！");
// 									}
            						listFormDialog = new A.dialog({
                						width: '1000',
                						height: '350',
                						title: "历史",
                						url:format_url("/quotaPlanHistory/toHistoryMessage/"+ id+"/"+type),
                						closed: function(){
//                 							annualProductionCapacityDatatables.draw(false);
                						}	
                					}).render()
									
								}
							}
						}]
						}
					}).render();
					$('#btnSearchAccident1').on('click',function(){
						conditions=[];
						if($('#statisticsYearId1').val()==''){
							alert("请选择年份！");
							return;
						}
						//场站名称
						if($("#farmId1").val()){
	    					conditions.push({
	    						field: 'ac.C_FARM_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#farmId1").val()
	    					});
    					}
// 						//指标名称
// 						if($("#quotaId").val()){
// 	    					conditions.push({
// 	    						field: 'ac.C_QUOTA_ID',
// 	    						fieldType:'STRING',
// 	    						matchType:'EQ',
// 	    						value:$("#quotaId").val()
// 	    					});
//     					}
						annualProductionCapacityDatatables.draw();
					});
					//重置条件并查询
					$('#btnResetAccident1').on('click',function(){
						$("#farmId1").val("");
						$("#farmId1").trigger("chosen:updated");
// 						$("#quotaId").val("");
// 						$("#quotaId").trigger("chosen:updated");
						$('#statisticsYearId1').val('');
						statisticsYear1.setValue('${nowYear}'); 
						conditions=[];
						annualProductionCapacityDatatables.draw();
					});
				});
			});
        </script>
    </body>
</html>