<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<title>Treeview - Ace Admin</title>
<%@ include file="/WEB-INF/views/common/meta.jsp" %>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="ace-icon fa fa-home home-icon"></i>
			<a href="javascript:void(0)" onclick="firstPage()">首页</a>
		</li>

		<li>
			系统管理
		</li>
		<li class="active">组织机构管理</li>

	</ul><!-- /.breadcrumb -->
</div>
<!-- /.page-header -->
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <!-- #section:plugins/fuelux.treeview -->
            <div class="row">
                <div class="col-sm-3">
                    <div class="widget-box widget-color-blue2" style="min-height: 600px;">
                        <div class="widget-header">
                            <h5 class="widget-title lighter smaller">组织</h5>
                            <button class='btn btn-xs btn-danger' id="delUnitBtn" style='float:right;margin-top:4px;margin-right:2px;' title="删除"><i class='ace-icon glyphicon glyphicon-trash'></i></button>
                            <button class='btn btn-xs btn-success' id="addUnitBtn" style='float:right;margin-top:4px;' title="增加"><i class='ace-icon glyphicon glyphicon-plus'></i></button>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main padding-8">
                                <ul  class="ztree" id="uintTree"></ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="unit-content" class="col-sm-9">
                	
                	<!-- Menu Content -->
                </div>
            </div>

            <!-- /section:plugins/fuelux.treeview -->

            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
</div>
<script src="../static/js/thirdparty/tree.min.js"></script>
<script src="${ctx}/static/js/thirdparty/bootbox.js"></script>
<script type="text/javascript">
	var unitTree;
 	var treeNodeId = "";
 	var treePnodeId = "";
    jQuery(function ($) {
    	seajs.use(['tree','confirm','combobox','combotree'], function(A){
    		unitTree = new A.tree({
				render: '#uintTree',
				options: {
					treeId: 'uintTree',
					async:{
						enable: true,
						url: format_url("/unit/getUnit"),
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
						onAsyncSuccess: function(event, treeId, treeNode, msg){
							var currentTree = this.getZTreeObj(treeId);
							var nodes = currentTree.getNodes();
							if(treeNodeId==""){
								currentTree.expandNode(nodes[0],true,true,true);
								//默认选中节点
								currentTree.selectNode(nodes[0]);
								//初始化加载节点点击事件
								currentTree.setting.callback.onClick(null, treeId, nodes[0]);//触发函数
							}else{
								var expendTreeNode = currentTree.getNodeByParam("id",treePnodeId ); 
								var selectTreeNode = currentTree.getNodeByParam("id",treeNodeId ); 
								currentTree.expandNode(expendTreeNode,true,false,true);
								currentTree.selectNode(selectTreeNode,true);
							}
						},
						onClick: function(event, treeId, treeNode){
							var unitId = treeNode.id;
							if(unitId>-1){
								A.loadPage({
									render: "#unit-content",
									url: format_url("/unit/edit/" + unitId),
								});	
							}
						},
					},
					view: {
						showLine: true
					}
				}
			}).render();

			$("#delUnitBtn").on("click",function(){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				if(selNode){
					if(!selNode.getParentNode()){
						alert("根节点不能删除");
						return;
					};
					if(selNode.children){
						alert("节点下有子节点不能删除");
						return;
					}
					var sid = selNode.id;
					var url ="${ctx}/unit/del/" + sid;
					A.confirm('您确认删除该项菜单么？',function(){
						$.ajax({
							url : url,
							contentType : 'application/json',
							dataType : 'JSON',
							type : 'delete',
							success: function(result){
								alert('删除成功');
								unitTree._dropDownTree.reAsyncChildNodes(null, "refresh",false);
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
			$("#addUnitBtn").on("click",function(){
				var selNode =unitTree._dropDownTree.getSelectedNodes()[0];
				if(selNode){
					var pid = selNode.id;
					A.loadPage({
						render: "#unit-content",
						url: format_url("/unit/add/" + pid),
					});
				}else{
					alert("请选择节点");
				}
			});
    	})
    });
</script>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree li span.button.remove {margin-left:2px;margin-right: 2px;background-position: -110px -64px;vertical-align: top;}
</style>