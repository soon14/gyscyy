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
		<form class="form-horizontal" role="form"  style="margin-right:100px;" id="editRWListForm">
					<input type="hidden" id="rlId" name="rlId" value="${rlId}" />
			    	<input type="hidden" id="unitId" name="unitId" value="${unitId}" />			
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding" id="defectStatistics-print">
                        <h5 class="table-title-withoutbtn header smaller blue lighter" style="margin-bottom:0px;">设备清单</h5>
						<table id="runWayEditDatatables" class="table table-striped table-bordered table-hover" style="width:100%;">
							<thead>							
								<tr>
									<th style="display:none;">主键</th>
	                                <th>设备类别</th>
	                                <th>设备名称</th>
	                                <th>运行方式</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            </form>
        </div>
        <div style="margin-right:100px;margin-top:20px">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="editRWListBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
    				<i class="ace-icon fa fa-floppy-o"></i>
    				保存
    			</button>
    		</div>
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			var listFormDialog;
			var formdatas="";
			var rowflag="";
			jQuery(function($) {
				seajs.use(['datatables', 'confirm', 'dialog','combobox','combotree','my97datepicker'], function(A){
					var conditions=[];
					var unitId=${unitId};
					var rlId=${rlId};
					var runWayEditDatatables = new A.datatables({
						render: '#runWayEditDatatables',
						options: {
					        "ajax": {
					            "url": format_url("/runWay/editSearch/"+unitId+'?rlId='+rlId),
					            "contentType": "application/json",
					            "type": "POST",
					            "dataType": "JSON",
					            "data": function (d) {
					            	conditions.push({
			        					field: 'C_UNIT_ID',
			        					fieldType:'INT',
			        					matchType:'EQ',
			        					value:${unitId}
			        				});
					            	d.conditions = conditions;
					                return JSON.stringify(d);
					              }
					        },
					        multiple : true,
							checked:false,
							bInfo:false,
							paging:false,
							ordering:true,
							columns: [{data:"unitId", visible:false,orderable:false}, 
								      {data: "deviceTypeName",width: "auto",orderable: true,	
								"createdCell": function (td, cellData, rowData, row, col) { 
					        	     if (rowData.tdHide=="show" ) {
					        	       	$(td).attr('rowspan',rowData.rowspanNum);
					        	       	$(td).attr('align','center');
					        	       	var height=rowData.rowspanNum*32;
					        	       	$(td).attr('style','height:'+height+'px;line-height:'+height+'px');
					        	     }else{
					        	    	 $(td).hide();
					        	     }
					        	}, 
								      },
							          {data: "deviceName",width: "auto",orderable: false}
								      ,
							          {width: "50%",orderable: false,
										"render": function(data, type, row){
											var run="run";var backup="backup";var hotback="hotback";var repair="repair";
											var afterhtml='';
											var runWay=new Array('run','backup','hotback','repair');
											var runWayName=new Array('运行','冷备用','热备','检修');
											var html='';
											for(var i=0;i<runWay.length;i++){	
												var beforehtml='<input type="radio" name="'+row.id+'_'+row.runWayName+'" id="'+row.id+'_'+row.runWayName+'_'+runWay[i]+'" value="'+row.id+'_'+row.deviceId+'_'+row.deviceType+'_';
												
												if(row.runWay==runWay[i]){
													afterhtml='"  checked="checked" >';
												}else {
													afterhtml='" >';
												}
												html+=beforehtml+runWay[i]+afterhtml+runWayName[i];
											}
							        	  return html ;
							          	}
							          }							          
							 ],
							 fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 
							 }
						}
					}).render();
					$('#editRWListForm').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							var formDatas='';
							var formDatasTemp='';
 							 $("#runWayEditDatatables  tbody").find("tr").each(function(i,n){						
								  var tdArrs = $(this).children();								 
								 formDatasTemp= tdArrs.eq(2).find("input[type='radio']:checked").val(); 
								 formDatasTemp+=',';
								 formDatas+=formDatasTemp;							 	
						     }); 
						     
						  /*   var trList = $("#runWayEditDatatables  tbody").children("tr");
						     for (var i=0;i<trList.length;i++) {
						         var tdArr = trList.eq(i).find("td");
						         var getVal = tdArr.eq(2).find("input[type='radio']:checked").val();
						         alert(getVal);
						     }*/						     
							//添加按钮
							var url = format_url("/runWay/editList");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#editRWListForm").serializeObject();
							obj.rowspanNum=formDatas;
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('保存成功');
									listFormDialog.close();

								},
								error:function(v,n){
									alert('保存失败');
								}
							});
						}
					});
					$("#editRWListBtn").on("click", function(){						
						 $("#editRWListForm").submit();
    				});
				});
			});
		/*	function asd(id,name) {
				if(rowflag==""){
					id+=',';
					formdatas+=id;
				}
				if(rowflag!=""){
					if(rowflag!=name){
						id+=',';						
						formdatas+=id;
					}
				}
				rowflag=name;
			}*/

        </script>
    </body>
</html>