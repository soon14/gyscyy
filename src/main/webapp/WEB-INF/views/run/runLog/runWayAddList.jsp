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
			<form class="form-horizontal" role="form"  style="margin-right:100px;" id="addRWListForm">
			    	<input type="hidden" id="rlId" name="rlId" value="${rlId}" />
			    	<input type="hidden" id="unitId" name="unitId" value="${unitId}" />			    	
			<div class="row">
				<div class="col-xs-12">
					<!-- div.dataTables_borderWrap -->
					<div class="widget-main no-padding" id="defectStatistics-print">
                        <h5 class="table-title-withoutbtn header smaller blue lighter" style="margin-bottom:0px;">设备清单</h5>
						<table id="runWayAddDatatables" class="table table-striped table-bordered table-hover" style="width:100%;">
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
        <div style="margin-right:100px;margin-top:20px;">
    			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal">
    				<i class="ace-icon fa fa-times"></i>
    				取消
    			</button>
    			<button id="saveRWListBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">
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
					var rlId=${rlId};
					var unitId=${unitId};
					var runWayAddDatatables = new A.datatables({
						render: '#runWayAddDatatables',
						options: {
					        "ajax": {
					            "url": format_url("/runWay/addSearch/"+unitId),
					            "contentType": "application/json",
					            "dataType": "JSON",
					            "data": function (d) {					            	
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
								      {data: "equipTypeName",width: "auto",orderable: true							        	   
								      },
							          {data: "name",width: "auto",orderable: false}
								      ,
							          {width: "50%",orderable: false,
										"render": function(data, type, row){
											var html='<input type="radio" name="'+row.id+'" id="'+row.id+'_'+row.equipType+'_run," value="'+row.id+'_'+row.equipType+'_run" ">运行'
													+'<input type="radio" name="'+row.id+'" id="'+row.id+'_'+row.equipType+'_backup," value="'+row.id+'_'+row.equipType+'_backup" ">冷备用'
													+'<input type="radio" name="'+row.id+'" id="'+row.id+'_'+row.equipType+'_hotback," value="'+row.id+'_'+row.equipType+'_hotback" ">热备'
													+'<input type="radio" name="'+row.id+'" id="'+row.id+'_'+row.equipType+'_repair," value="'+row.id+'_'+row.equipType+'_repair" ">检修';
							        	  return html ;
							          	}
							          }
							          
							 ],
							 fnInfoCallback: function (oSettings, iStart, iEnd, iMax, iTotal, sPre) {
								 
							 }
						}
					}).render();
					
					$('#addRWListForm').validate({
						debug:true,
						rules:  {},
						submitHandler: function (form) {
							var formDatas='';
							var formDatasTemp='';
 							 $("#runWayAddDatatables  tbody").find("tr").each(function(i,n){						
								  var tdArrs = $(this).children();								 
								 formDatasTemp= tdArrs.eq(2).find("input[type='radio']:checked").val(); 
								 formDatasTemp+=',';
								 formDatas+=formDatasTemp;							 	
						     });
							//添加按钮
							var url = format_url("/runWay/insertList");
							//serializeObject()用于Jquery将form转换成用于ajax参数的Javascript Object
							var obj = $("#addRWListForm").serializeObject();
							if(formDatas=='undefined,'){
								alert("该风场没有设备不能添加操作！");
								return;
							}
							obj.rowspanNum=formDatas;	
							$.ajax({
								url : url,
								contentType : 'application/json',
								dataType : 'JSON',
								data : JSON.stringify(obj),
								cache: false,
								type : 'POST',
								success: function(result){
									alert('添加成功');
									listFormDialog.close();
								},
								error:function(v,n){
									alert('添加失败');
								}
							});
						}
					});
					$("#saveRWListBtn").on("click", function(){
						$("#addRWListForm").submit();
    				});
				});
			});
			function asd(id,name) {
				if(rowflag==""){
					formdatas+=id;
				}
				if(rowflag!=""){
					if(rowflag!=name){
						formdatas+=id;
					}
				}
				rowflag=name;
			}
        </script>
    </body>
</html>