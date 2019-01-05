<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="widget-box widget-color-blue" id="menuAdd" style="min-height: 600px;">
	<div class="widget-header">
	    <h5 class="widget-title lighter smaller">
	        添加
	    </h5>
	</div>

	<div class="widget-body">
		<div id="editBody" class="widget-main padding-8">
			<form id="unitAddForm" class="form-horizontal" role="form" style="margin-right:100px;">
				<input type="hidden" name="status" value="1">
				<div class="form-group">
					<div class="col-md-4">
						<input placeholder="" id="parentId"  name="parentId" type="hidden" class="col-md-12" value="${parentId}">					
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>编码</label>
					<div class="col-md-4">
						<input placeholder="" id="code"  name="code" type="text" class="col-md-12">
					</div>
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>名称</label>
					<div class="col-md-4">
						<input placeholder="" id="name"  name="name" type="text" class="col-md-12">
					</div>									
				</div>
				<div class="form-group">
<!-- 					<label class="col-md-2 control-label no-padding-right">缩写</label>
					<div class="col-md-4">
						<input placeholder="" id="nameAB"  name="nameAB" type="text" class="col-md-12">
					</div> -->
					<label class="col-md-2 control-label no-padding-right">序号</label>
					<div class="col-md-4">
						<input placeholder="" id="order"  name="order" type="text" class="col-md-12">
					</div>				
					<label class="col-md-2 control-label no-padding-right"><span style="color:red;">*</span>层级</label>
					<div class="col-md-4">
						<select id="levelDiv" class="col-md-12" name="level"></select>
					</div>										
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">机构类型</label>
					<div class="col-md-4">
						<select id="organizationDiv" class="col-md-12" name="organization"></select>
					</div>		
				</div>		
				<div class="form-group">
					<label class="col-md-2 control-label no-padding-right">备注</label>
					<div class="col-md-10">
						<textarea id="remark" name="remark"  style="width:100%; height:150px;resize:none;"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div  class="col-sm-offset-10 col-sm-1">
						<button id="btnAdd" type="submit" class="btn btn-sm btn-primary" style="float:right;">
							<i class="ace-icon fa fa-floppy-o"></i>
							保存
						</button>
					</div>
				</div>	
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
	seajs.use(['combobox','combotree'], function(A){
		//unitLevel类型
		var unitLevelCombobox = new A.combobox({
			render: "#levelDiv",
			datasource:${unitLevel},
			options:{
				"disable_search_threshold":10
			}
		}).render();
		
		var generationTypeCombobox = new A.combobox({
			render: "#generationTypeDiv",
			datasource:${generationTypes},
			options:{
				"disable_search_threshold":10
			}
		}).render();
		
		//组织机构类型
		var organizationCombobox = new A.combobox({
			render: "#organizationDiv",
			datasource:${organization},
			options:{
				"disable_search_threshold":10
			}
		}).render();
		
    	$('#unitAddForm').validate({
    		debug: true,
			rules: {
				name: {
					required: true,
					maxlength: 30
				},
				code: {
					required: true,
					maxlength: 30,
				},
				/* type:{
					required:true
				}, */
				/* order:{
					digits:true
				}, */
				level: {
					required: true,
				}
			},
			submitHandler: function (form) {
				var url =format_url("/unit");
				var obj = $("#unitAddForm").serializeObject();
				if(obj.code){
					var patrn =  /^[A-Za-z0-9.(){};@_-]+$/;
					if(!patrn.exec(obj.code)){
						alert("编码禁止输入汉字！");
						return;
					}
				}
				$.ajax({
					url : url,
					contentType : 'application/json',
					dataType : 'JSON',
					data : JSON.stringify(obj),
					cache: false,
					type : 'POST',
					success: function(result){
						alert('添加成功');
						treeNodeId = result.data.id;
						treePnodeId = result.data.parentId;
						unitTree._dropDownTree.reAsyncChildNodes(null, "refresh");
					},
					error:function(v,n){
						alert('添加失败');
					}
				});
			}
		});
	});
</script>