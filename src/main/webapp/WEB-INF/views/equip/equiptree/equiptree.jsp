<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<style>
.widget-color-blue2 {
    min-width: 180px;
}
</style>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="ace-icon fa fa-home home-icon"></i>
			<a href="javascript:void(0);" onclick="firstPage()">首页</a>
		</li>

		<li>
			设备管理
		</li>
		<li class="active">设备台账</li>
	</ul><!-- /.breadcrumb -->
</div>
<!-- /.page-header -->
<div class="col-sm-12">
<div class="col-md-2">
	 <div class="col-sm-12">
         <div class="widget-box widget-color-blue2" style="height: 820px;width: 115%;overflow-y :auto;overflow-x :auto;">
             <div class="widget-header">
                 <h5 class="widget-title lighter smaller" style="margin-bottom:0px;">设备台账树</h5>
                 <button class='btn btn-xs btn-danger' id="delUnitBtn" style='float:right;margin-top:4px;margin-right:2px;'><i class='ace-icon glyphicon glyphicon-trash'></i></button>
                 <button class='btn btn-xs btn-success' id="addLogicalEquip" style='float:right;margin-top:4px;' title="逻辑设备"><i class='ace-icon glyphicon glyphicon-plus' ></i></button>
                 <button class='btn btn-xs btn-info' id="addShysicsEquip" style='float:right;margin-top:4px;' title="物理设备"><i class='ace-icon glyphicon glyphicon-plus'  ></i></button>
             </div>
             <div class="widget-body">
                 <div class="widget-main padding-8">
                     <ul  class="ztree" id="uintTree"></ul>
                 </div>
             </div>
         </div>
     </div>
</div>
<div class="col-md-10">
	<div class="page-content">
		<div id="tabbable" class="tabbable" style="display: none;">
		 		<ul class="nav nav-tabs" id="myTab">
		 			<li >
			 			<a  data-toggle="tab" href="#sysUnitInfo" aria-expanded="false">
							<i class="green ace-icon fa fa-home bigger-120"></i>
							基础信息
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#equipList" aria-expanded="true">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							设备信息
						</a>
		 			</li>

		 			<li>
			 			<a  data-toggle="tab" href="#workTicket" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							工作票
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#operationTicket" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							操作票
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#overhaulTask" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							检修日志
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#defect" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							缺陷单
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#equipAbnormal" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							设备异动
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#equipAppraise" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							设备评级
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#materialCategory" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							物资类别
						</a>
		 			</li>
		 			<li>
			 			<a  data-toggle="tab" href="#testReport" aria-expanded="false">
							<i class="green ace-icon fa fa-list bigger-120"></i>
							试验报告
						</a>
		 			</li>
<!-- 		 			<li> -->
<!-- 			 			<a  data-toggle="tab" href="#workRecord" aria-expanded="false"> -->
<!-- 							<i class="green ace-icon fa fa-list bigger-120"></i> -->
<!-- 							定期工作 -->
<!-- 						</a> -->
<!-- 		 			</li> -->
		 		</ul>
			<div class="tab-content">
					<!-- 组织机构基础 -->
 					<div id="sysUnitInfo" class="tab-pane fade "></div>
					<!-- 设备信息 -->
					<div id="equipList" class="tab-pane fade"></div>
					<!-- 工作票-->
					<div id="workTicket" class="tab-pane fade"></div>
					<!-- 操作票-->
					<div id="operationTicket" class="tab-pane fade"></div>
					<!-- 检修日志-->
					<div id="overhaulTask" class="tab-pane fade"></div>
					<!-- 缺陷单-->
					<div id="defect" class="tab-pane fade"></div>
					<!-- 设备异动-->
					<div id="equipAbnormal" class="tab-pane fade"></div>
					<!-- 设备评价-->
					<div id="equipAppraise" class="tab-pane fade"></div>
					<!-- 运行记录-->
					<div id="workRecord" class="tab-pane fade"></div>
					<!-- 物资类别-->
					<div id="materialCategory" class="tab-pane fade"></div>
					<!-- 试验报告-->
					<div id="testReport" class="tab-pane fade"></div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	var pathCode,id,equipId,unitId,treeNodelevel;
	var unitTree;
	var tabSwitch="#sysUnitInfo";
	var renderId;
	var showBaseInfo;
	var flag = true;
	var addFlag = false;
	var noEditFlag = true;
    jQuery(function ($) {
    	seajs.use(['tree','confirm','combobox','combotree'], function(A){
    		
    		var selectNode;
    		
    		unitTree = new A.tree({
				render: '#uintTree',
				options: {
					treeId: 'uintTree',
					async:{
						enable: true,
						url: format_url("/equiptree/showTree"),
						type:"post",
						dataType:"json"
					},
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
						onClick: function(event, treeId, treeNode){
							id = treeNode.id;
							equipId = treeNode.equipId;
							unitId = treeNode.unitId;
							pathCode = treeNode.pathCode;
							treeNodelevel = treeNode.treeNodelevel;
							showBaseInfo(true);
						}
					},
					view: {
						showLine: true
					},
					multiple : false
				}
			}).render();

			$("#delUnitBtn").on("click",function(){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				if(selNode){
					if(!selNode.getParentNode()){
						alert("根节点不能删除");
						return;
					};
					var id = selNode.id;
					var url =format_url("/equiptree/isdel/" + id);
					A.confirm('您确认删除该项？',function(){
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'POST',
							success: function(result){
								var result = result.result;
								if(result=='success'){
									alert('删除成功');
									unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
								}else{
									if(result=='HAVESUB'){
										alert('请先删除子节点设备');
									}
								}
							},
							error:function(v,n){
								alert('操作失败');
							}
						});
					});
				}else{
					alert('请选择删除的节点');
				}
			});
			//新增逻辑设备
			$("#addLogicalEquip").on("click",function(){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				if(selNode){
					var pid = selNode.id;
					var pathCode = selNode.pathCode;
					var equipId = selNode.equipId;
					addFlag = true;
					noEditFlag = false;
					showBaseInfo(false,true);
					noEditFlag = true;
					A.loadPage({
						render: "#sysUnitInfo",
						url: format_url("/equiptree/getAddLogicalEquip/"+pid+"/"+unitId +"/"+pathCode),
					});
				}else{
					alert("请选择节点");
				}
			});
			//新增物理设备
			$("#addShysicsEquip").on("click",function(){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				if(selNode){
					var id = selNode.id;
					var unitId = selNode.unitId;
					var pathCode = selNode.pathCode;
					var equipId = selNode.equipId;
					addFlag = true;
					noEditFlag = false;
					showBaseInfo(false,true);
					noEditFlag = true;
					A.loadPage({
						render: "#sysUnitInfo",
						url: format_url("/equiptree/getAddPhysicsEquip/" + id+"/"+unitId +"/"+pathCode),
					});
					
				}else{
					alert("请选择节点");
				}
			});
			//tab
			$('#myTab a[data-toggle="tab"]').on('click', function (e) {
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				tabSwitch = $(e.target).attr('href');
				if(selNode){
					if(noEditFlag){
						id = selNode.id;
						equipId = selNode.equipId;
						unitId = selNode.unitId;
						pathCode = selNode.pathCode;
						selectNode = tabSwitch;
						switch(tabSwitch){
							case "#sysUnitInfo":
								renderId = "#sysUnitInfo";
								if(equipId==-1 || equipId==-2){
									A.loadPage({
										render: renderId,
										url: format_url("/equiptree/getEdit/"+id)
									});	
								}
								if(equipId!=-1 && equipId!=-2){
									A.loadPage({
										render: renderId,
										url: format_url("/equiptree/getEdit/"+equipId+"/"+unitId)
									});
								}
							break;
							case "#equipList":
								renderId = "#equipList";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/listForTree/"+pathCode+"/"+id)
								});
							break;
							
							case "#workTicket":
								renderId = "#workTicket";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/workTicketListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#operationTicket":
								renderId = "#operationTicket";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/operationTicketInitWithEquip/"+pathCode+"/"+id)
								});
								
							break;
							case "#overhaulTask":
								renderId = "#overhaulTask";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/overhaulTaskInitWithEquip/"+pathCode+"/"+id)
								});
								
							break;
							case "#workRecord":
								renderId = "#workRecord";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/workRecordListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#equipAbnormal":
								renderId = "#equipAbnormal";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/equipAbnormalListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#equipAppraise":
								renderId = "#equipAppraise";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/equipAppraiseListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#defect":
								renderId = "#defect";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/defectListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#materialCategory":
								renderId = "#materialCategory";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/materialCategoryListInitWithEquip/"+pathCode+"/"+id)
								});
							break;
							case "#testReport":
								renderId = "#testReport";
								$(renderId).empty();
								A.loadPage({
									render: renderId,
									url: format_url("/equiptree/testReportListInitWithEquip/"+pathCode)
								});
							break;
						} 
					}
				}else{
					alert("请选择节点");
				}
	    	})
	    	
	    	showBaseInfo = function(flag,addFlag){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				$("#tabbable").show();
				if(tabSwitch=="#sysUnitInfo" || addFlag){
					tabSwitch = "#sysUnitInfo";
					selectNode = "#sysUnitInfo";
				}
				$("#tabbable").find("[href='"+ selectNode +"']").trigger("click");
			}
		  	function sleep(n) { //n表示的毫秒数
	            var start = new Date().getTime();
	            while (true) if (new Date().getTime() - start > n) break;
	        } 
	    });
    });
</script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.remove {margin-left:2px;margin-right: 2px;background-position: -110px -64px;vertical-align: top;}
</style>