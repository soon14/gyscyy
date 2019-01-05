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
                          <select id="farmId11" class="" ></select>
                       </div>
                    </div>
<!-- 					<div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero"> -->
<!--                        <label class="searchLabel">指标名称</label>： -->
<!--                        <div class="padding-zero inputWidth  text-left"> -->
<!--                           <select id="quotaId" class="" ></select> -->
<!--                        </div> -->
<!--                     </div> -->
                    <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12 padding-zero">
                        <label >统计年份：</label>
                        <div class="form-group dateInputOther padding-zero text-left">
                            <div id="statisticsYearDiv11" style="border:none; padding:0px;"></div>
                        </div>
                    </div>
                    <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-zero btnSearchBottom" style="text-align:right; ">
                       <button id="btnSearchAccident11" class="btn btn-xs btn-primary">
                           <i class="glyphicon glyphicon-search"></i>
                      		     查询
                       </button>
                       <button id="btnResetAccident11" class="btn btn-xs btn-primary" >
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
						<h5 class='table-title-withoutbtn header smaller blue' style="margin-bottom:0px;">${tableName }</h5>
						<table id="quotaPlanApprove1_table" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>
								<tr>
									<th style="display:none;">主键</th>
	                                <th style="text-align:center">序号</th>
	                                <th style="text-align:center">场站名称</th>
	                                <th style="text-align:center">计划年份</th>
	                                <th style="text-align:center">生产运行单位</th>
	                                <th style="text-align:center">生产技术处</th>
	                                <th style="text-align:center">计划经营处</th>
	                                <th style="text-align:center">单位领导</th>
	                                <th style="text-align:center">计划经营处下文执行</th>
	                                <th style="text-align:center">流程状态</th>
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
					var farmIdCombobox11 = new A.combobox({
						render: "#farmId11",
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
					var statisticsYear11 = new A.my97datepicker({
						id: 'statisticsYearId11',
						name:'year',
						render:'#statisticsYearDiv11',
						options : {
								isShowWeek : false,
								skin : 'ext',
								maxDate: "${nowYear}",
								minDate: "",
								dateFmt: "yyyy"
						}
					}).render();
					statisticsYear11.setValue('${nowYear}'); 
					
					var quotaPlanApprove1Datatables = new A.datatables({
						render: '#quotaPlanApprove1_table',
						options: {
					        "ajax": {
					            "url": format_url("/quotaPlanApprove/search"),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
				     					field: 'q.C_TYPE',
				     					fieldType:'STRING',
				     					matchType:'EQ',
				     					value:type
				     				});
					            	conditions.push({
			        					field: 'date_format(q.C_YEAR,"%Y")',
			        					fieldType:'DATE',
			        					matchType:'EQ',
			        					value:$('#statisticsYearId11').val()
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							ordering: true,
							checked: false,
							optWidth: 80,
							columns: [{data:"id", visible:false,orderable:false},
							          {orderable: false,"width":"3%", "sClass": "center",render : function(data, type, row, meta) {
						                   var startIndex = meta.settings._iDisplayStart;  
						                   row.start=startIndex + meta.row;
						                   return startIndex + meta.row + 1;  
						               	} },
						              {data: "farmName",name:"farmId",width: "auto",orderable: true},
						              {data: "yearStr",name:"year",width: "auto",orderable: true},
						              {data: "productionRunningName",name:"productionRunningId",width: "auto",orderable: true},
						              {data: "productionSkillName",name:"productionSkillId",width: "auto",orderable: true},
						              {data: "planRunningName",name:"planRunningId",width: "auto",orderable: true},
						              {data: "leaderName",name:"leaderId",width: "auto",orderable: true},
						              {data: "planRunningDownName",name:"planRunningDown",width: "auto",orderable: true},
						              {data: "statusName",name:"status",width: "auto",orderable: true}
							          ],
				          toolbars: [
							],
							btns: [
							{
							id:"submit",
						 	label: "提交审核",
							icon: "fa fa-check-square-o bigger-130",
							className: "edit",
							render: function(btnNode, data){
// 								var loginUserId = '${sysUserEntity.id}'
								if(data.status!="0"){
									btnNode.hide();
								}
// 								if(data.uploadPerson!=loginUserId){
// 									btnNode.hide();
// 								}
							},
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									workticketDialog = new A.dialog({
										title:"选择生产技术处人员",
										url:format_url("/quotaPlanApprove/sureSubmit?id="+ id+"&type="+type),
										height:500,
										width:600,
									}).render();
								}
							}
						},{
							id: "detail",
							label:"查看",
							icon: "fa fa-binoculars bigger-130",
							className: "blue ",
							events:{
								click: function(event, nRow, nData){
									var id = nData.id;
									A.loadPage({
										render : '#page-container',
										url : format_url("/quotaPlanApprove/getDetail/"+ id+"/"+type)
									});
								}
							}
						} ]
						}
					}).render();
					$('#btnSearchAccident11').on('click',function(){
						conditions=[];
						if($('#statisticsYearId11').val()==''){
							alert("请选择年份！");
							return;
						}
						//场站名称
						if($("#farmId11").val()){
	    					conditions.push({
	    						field: 'q.C_FARM_ID',
	    						fieldType:'STRING',
	    						matchType:'EQ',
	    						value:$("#farmId11").val()
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
						quotaPlanApprove1Datatables.draw();
					});
					//重置条件并查询
					$('#btnResetAccident11').on('click',function(){
						$("#farmId11").val("");
						$("#farmId11").trigger("chosen:updated");
// 						$("#quotaId").val("");
// 						$("#quotaId").trigger("chosen:updated");
						$('#statisticsYearId11').val('');
						conditions=[];
						quotaPlanApprove1Datatables.draw();
					});
				});
			});
			
			function goBackToSubmitPerson(id,selectUser){//回调函数
				var url =format_url("/quotaPlanApprove/submit?workId="+id+"&selectUser="+selectUser);
				$.ajax({
					contentType: "application/json",
					dataType:"JSON",
					url : url,
					success: function(result){
						if(result.result=="success"){
							alert('审批成功');
							A.loadPage({
								render : '#page-container',
								url:format_url("/annualProductionCapacity/list"),
							});
						}else{
							alert(result.errorMsg);
						}
					},
					error:function(v,n){
						alert('审批失败');
					}
				});
				
			}
        </script>
    </body>
</html>