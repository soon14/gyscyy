define(function(require, exports, module){
	require("../../css/themes/{theme}/tree/zTreeStyle/zTreeStyle.css");
	require("../thirdparty/ztree/jquery.ztree.all.js");
	require("../thirdparty/ztree/jquery.ztree.exedit.js");
	Aptech.tree= function(config){
		var _options = {	
			view: {
				dblClickExpand: false,
				showLine: false,
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		this._config = config;
		$.extend(true, _options, config.options);
		this._options = _options;
	};
	
	Aptech.tree.prototype = {
			render: function(){
				_self = this;
				var containerNode = $(_self._config.render);
				var inputNode = $('<input name="' + this._name +'" style="display:none;" value="">');
				var dropDownDiv = $('<div class=""><ul id=' + _self._options.treeId +' class="ztree"></ul></div>');
				containerNode.append(inputNode);
				containerNode.append(dropDownDiv);
				_self._dropDownTree = $.fn.zTree.init($(_self._config.render + " .ztree"), _self._options, _self._config.datasource);
				
				return _self;
			},
			
			setValue : function(value){
				var _self = this;
				if(value.length){
					for(var i=0; i<value.length; i++){
						var treeId = value[i];
						var selectNode = _self._dropDownTree.getNodeByParam("id", treeId, null);
						_self._dropDownTree.checkNode(selectNode, true, true, true);
					}
				}else{
					var selectNode = _self._dropDownTree.getNodeByParam("id", value, null);
					 _self._dropDownTree.selectNode(selectNode);
				}
			},
			
			getValue : function(){
				var ids ="";
				 var nodes = _self._dropDownTree.getCheckedNodes(true);
				 if(nodes){
					for (var i=0, l=nodes.length; i<l; i++) {
						if(!nodes[i].isParent){
							if(i == l-1){
								ids += nodes[i].id;
							}else{
								ids += nodes[i].id + ",";	
							}
						}
					}
				 }else{
					 nodes = _self._dropDownTree.getSelectedNodes();
					 if(nodes){
						 ids = nodes[0].id;
					 }
				 }
				return ids;
			},
			setParentValue : function(value){
				var _self = this;
				if(value.length){
					for(var i=0; i<value.length; i++){
						var treeId = value[i];
						var selectNode = _self._dropDownTree.getNodeByParam("id", treeId, null);
						if(!selectNode.isParent){
						_self._dropDownTree.checkNode(selectNode, true, true, true);
						}
					}
				}else{
					var selectNode = _self._dropDownTree.getNodeByParam("id", value, null);
					 _self._dropDownTree.selectNode(selectNode);
				}
			},
			getParentValue : function(){
				var ids ="";
				var nodes = _self._dropDownTree.getCheckedNodes(true);
				if(nodes){
					for (var i=0, l=nodes.length; i<l; i++) {
						if(i == l-1){
							ids += nodes[i].id;
						}else{
							ids += nodes[i].id + ",";	
						}
					}
				}else{
					nodes = _self._dropDownTree.getSelectedNodes();
					if(nodes){
						ids = nodes[0].id;
					}
				}
				return ids;
			},
			getPathValue : function(){
				var ids ="";
				var nodes = _self._dropDownTree.getCheckedNodes(true);
				 if(nodes){
					for (var i=0, l=nodes.length; i<l; i++) {
							if(i == l-1){
								ids += nodes[i].pathCode;
							}else{
								ids += nodes[i].pathCode + ",";	
							}
					}
				 }else{
					 var nodes = _self._dropDownTree.getSelectedNodes();
					 if(nodes){
						 ids = nodes[0].pathCode;
					 }
				 }
				return ids;
			}
	};
	return Aptech;
});