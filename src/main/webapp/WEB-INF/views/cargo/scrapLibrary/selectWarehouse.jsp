<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title></title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
        <form class="form-horizontal" role="form"  style="margin-right:140px;" id="addForm">
                <div class="form-group" style="margin-right: 20px;">
					<label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>来源电站
					</label>
					<div class="col-md-4">
				    <div id="unitIdDiv"></div>
                    </div>
                    <label class="col-md-2 control-label no-padding-right">
						<span style="color:red;">*</span>仓库
				    </label>
				    <div class="col-md-4">
						<select class="col-md-12 chosen-select" id="warehouseId" name="warehouseId"></select>
                    </div>
			     </div>
			     </form>
        </div>
       	
    </div>
    <div style="margin-top: 15px;">
			<button class="btn btn-xs btn-danger pull-right" data-dismiss="modal" >
				<i class="ace-icon fa fa-times"></i>
				取消
			</button>
			
			<button id="btnSavePerson" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;" >
				<i class="ace-icon fa fa-floppy-o"></i>
				确定
			</button>
		</div>
</div>
<script type="text/javascript">
	var sureSubmitDatatables;
    jQuery(function ($) {
    	seajs.use(['duallistbox'], function(A){
    		//部门控件下拉树
			var unitId = new A.combotree({
			render: "#unitIdDiv",
			name: 'stationSourceId',
			//返回数据待后台返回TODO
			datasource: ${unitNameIdTreeList},
			width:"210px",
			options: {
				treeId: 'searchunitId',
				data: {
					key: {
						name: "name"
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"
					}
				},
				callback: {
	                beforeOnClick: function(e, treeId, treeNode){
	                	$.ajax({
							url : format_url('/scrapLibrary/getWareHouseList/'+treeNode.id),
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							success: function(result){
								var unitNameIdTreeList = eval(result.data);
								wareHouseCombobox = new A.combobox({
									render: "#warehouseId",
									datasource:unitNameIdTreeList,
									//multiple为true时select可以多选
									multiple:false,
									//allowBlank为false表示不允许为空
									allowBlank: true,
									options:{
										"disable_search_threshold":10
									}
								}).render();
							},
							error:function(v,n){
								alert('操作失败');
							}
						});
	                }
	              }
			}
		}).render();
			var wareHouseCombobox = new A.combobox({
				render: "#warehouseId",
				//返回数据待后台返回TODO
				datasource:${wareHouseIds},
				//multiple为true时select可以多选
				multiple:false,
				//allowBlank为false表示不允许为空
				allowBlank: true,
				options:{
					"disable_search_threshold":10
				}
			}).render();
    		 	var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">Filtered</span>'});
				var container1 = demo1.bootstrapDualListbox('getContainer');
				container1.find('.btn').addClass('btn-white btn-info btn-bold');
				var type = ${type};
				$('#btnSavePerson').on('click',function(){
					var warehouseId = wareHouseCombobox.getValue();
					var stationSourceId = unitId.getValue();
					var resule = [];
					resule.push(warehouseId);
					resule.push(stationSourceId);
					submitUserDialog.close(resule);
				});
    	});		
    });
    
</script>
