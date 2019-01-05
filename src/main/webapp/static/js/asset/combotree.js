define(function(require, exports, module){
	require("../../css/themes/{theme}/tree/zTreeStyle/zTreeStyle.css");
	require("../thirdparty/ztree/jquery.ztree.all.js");
	require("../../css/themes/{theme}/combotree.css");	
	Aptech.combotree= function(config){
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
			},
			callTreeBack:function(){}
		}
		
		this._config = config;
		this._name = config.name || "";
		this._width = config.width || "";
		if(this._config.render){
			this._config.container = $(config.render);
		}
		$.extend(true, _options, config.options);
		this._options = _options;
		this._posFlag = true;
		this._multiple = config.multiple || false;
		this._expand = config.expand || false;
	}
	
	Aptech.combotree.prototype = {
			render: function(){
				var _self = this;
				this._name = _self._config.name || "";
				var containerNode = _self._config.container;
				var displayNode;
				if(this._width){
					displayNode = $('<div class="aptech-combotree"></div>');
				}else{
					displayNode = $('<div class="aptech-combotree" ></div>');
				}
				$('.form-group .aptech-combotree').css('position','relative');
				var aNode = $('<a tabindex="1"></a>');
				var spanNode = $('<span class="info">--请选择--</span>');
				
				_self.spanNode = spanNode;
				var inputNode = $('<input class="combotree" name="' + this._name +'" style="top: 0;position: absolute;z-index:-1;height: 27px;width: 80%;" value="">');
				_self.inputNode = inputNode;
				var btnNode = $('<div><i class="fa fa-caret-down"></i></div>');
				var dropDownDiv = $('<div class="aptech-combotree-dropdown" style="max-height: 500px; overflow-x: hidden; overflow-y: scroll;"><ul id='+ _self._options.treeId +' class="ztree" ></ul></div>');
				var opts;
				if(_self._config.callback){
					_self._config.callback(_self._config.datasource);
				}
				if(!_self._multiple){
					if(_self._options.callback&&_self._options.callback.beforeOnClick){
						opts = {
								check: {
									enable: false,
								},
								callback: {
									onClick: function(e, treeId, treeNode){
										_self._options.callback.beforeOnClick(e, treeId, treeNode);
										if (!treeNode.isParent) {
											_self._displayNames = treeNode.name;
											_self._values = treeNode.id;
											inputNode.val(treeNode.id);
											spanNode.removeClass("info");
											spanNode.attr("title", treeNode.name);
											var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');
											
											abbrNode.on("click", function(event){
												event.preventDefault(); 
												event.stopPropagation(); 
												spanNode.addClass("info");
												spanNode.removeAttr("title");
												spanNode.html("--请选择--");
												_self.inputNode.val("");
												_self._displayNames='';
												_self.setValue(null);
											});
											_self._options.callTreeBack();
											spanNode.empty();
											spanNode.html(treeNode.name);
											spanNode.append(abbrNode);
											dropDownDiv.hide();
										}

									}
								}
						};
					}else{
						opts = {
								check: {
									enable: false,
								},
								callback: {
									onClick: function(e, treeId, treeNode){
										_self._displayNames = treeNode.name;
										_self._values = treeNode.id;
										inputNode.val(treeNode.id);
										spanNode.removeClass("info");
										spanNode.attr("title", treeNode.name);
										var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');

										abbrNode.on("click", function(event){
											event.preventDefault();
											event.stopPropagation();
											spanNode.addClass("info");
											spanNode.removeAttr("title");
											spanNode.html("--请选择--");
											_self.inputNode.val("");
											_self.setValue(null);
										});
										_self._options.callTreeBack();
										spanNode.empty();
										spanNode.html(treeNode.name);
										spanNode.append(abbrNode);
										dropDownDiv.hide();

									}
								}
						};
					}
				}else{
					if(_self._options.callback&&_self._options.callback.beforeOnCheck){
						opts={
							check: {
								enable: true,
								chkStyle: "checkbox",
								chkboxType: { "Y" : "ps", "N" : "ps" }
							},
							callback: {
								onCheck: function(e, treeId, treeNode){
									_self._options.callback.beforeOnCheck(e, treeId, treeNode);
									var nodes = this.getZTreeObj(treeId).getCheckedNodes(true),
									value = "";
									ids ="";
									nodes.sort(function compare(a,b){return a.id-b.id;});
									for (var i=0, l=nodes.length; i<l; i++) {
										if(!nodes[i].isParent){
											value += nodes[i].name + ",";
											if(i == l-1){
												ids += nodes[i].id;
											}else{
												ids += nodes[i].id + ",";
											}
										}
									}
									if (value.length > 0 ) value = value.substring(0, value.length-1);
									_self._displayNames = value;
									_self._values = ids;
									inputNode.val(ids);
									spanNode.attr("title", value);


									var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');

									abbrNode.on("click", function(event){
										event.preventDefault();
										event.stopPropagation();
										spanNode.addClass("info");
										spanNode.removeAttr("title");
										spanNode.html("--请选择--");
										_self.inputNode.val("");
										_self.setValue(null);
									});
									_self._options.callTreeBack();
									spanNode.empty();
									spanNode.html(value);
									spanNode.append(abbrNode);
								}
							}
						}
					}else{
						opts ={
								check: {
									enable: true,
									chkStyle: "checkbox",
									chkboxType: { "Y" : "ps", "N" : "ps" }
								},
								callback: {
									onCheck: function(e, treeId, treeNode){
										var nodes = this.getZTreeObj(treeId).getCheckedNodes(true),
										value = "";
										ids ="";
										nodes.sort(function compare(a,b){return a.id-b.id;});
										for (var i=0, l=nodes.length; i<l; i++) {
											if(!nodes[i].isParent){
												value += nodes[i].name + ",";
												if(i == l-1){
													ids += nodes[i].id;
												}else{
													ids += nodes[i].id + ",";
												}
											}
										}
										if (value.length > 0 ) value = value.substring(0, value.length-1);
										_self._displayNames = value;
										_self._values = ids;
										inputNode.val(ids);
										spanNode.attr("title", value);

										var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');

										abbrNode.on("click", function(event){
											event.preventDefault();
											event.stopPropagation();
											spanNode.addClass("info");
											spanNode.removeAttr("title");
											spanNode.html("--请选择--");
											_self.inputNode.val("");
											_self.setValue(null);
										});
										_self._options.callTreeBack();
										spanNode.empty();
										spanNode.html(value);
										spanNode.append(abbrNode);
									}
								}
						};
					}
				}
				$.extend(true, this._options, opts);
				aNode.append(spanNode);
				_self._displayNode = spanNode;
				aNode.append(inputNode);
				aNode.append(btnNode);
				displayNode.append(aNode);
				containerNode.append(displayNode);
				containerNode.append(dropDownDiv);
				dropDownDiv.hide();
				
				aNode.blur(function(){
					if(!_self._posFlag){
						if(!$(this).parent().siblings(".aptech-combotree-dropdown").is(":hidden")){
							$(this).parent().siblings(".aptech-combotree-dropdown").hide();
							_self.inputNode.trigger("blur");
							_self._posFlag = false;
						}
					}else{
						setTimeout(function () { aNode.focus(); }, 0);
						_self._posFlag = true;
					}
				});
				
				dropDownDiv.on('mouseenter', function(){
					_self._posFlag = true;
				});
				
				dropDownDiv.on('mouseleave', function(){
					_self._posFlag = false;
				});
				var dropDownTreeContainer = _self._config.container.find(".ztree");
				_self._dropDownTree = $.fn.zTree.init(dropDownTreeContainer, _self._options, _self._config.datasource);
				
				aNode.on("click", function(){
					if($(this).parent().siblings(".aptech-combotree-dropdown").is(":hidden")){
						$(this).parent().siblings(".aptech-combotree-dropdown").show();    //如果元素为隐藏,则将它显现
						_self._posFlag = false;
					}else{
						$(this).parent().siblings(".aptech-combotree-dropdown").hide();    //如果元素为显现,则将其隐藏
						_self._posFlag = false;
					}
				})
				
				if(_self._expand){
					var nodes = _self._dropDownTree.getNodes();
					_self._dropDownTree.expandNode(nodes[0],true,true,true);
				}
				return _self;
			},
			
			setValue : function(value, param){
				var _self = this;
				_self._dropDownTree.checkAllNodes(false);
				var nodeParam = param || "id";
				if(_self._config.multiple){
					var displayNames = "";
					for(var i=0; i<value.length; i++){
						var treeId = value[i];
						var selectNode = _self._dropDownTree.getNodeByParam(nodeParam, treeId, null);
						if(selectNode){
							_self._dropDownTree.checkNode(selectNode, true, true , true);
							displayNames += selectNode.name + ",";
						}
					}
					if (displayNames.length > 0 ) displayNames = displayNames.substring(0, displayNames.length-1);
					_self._displayNames = displayNames;
					_self._values = value;
					_self.inputNode.val(value);
					_self.spanNode.removeClass("info");
					_self.spanNode.attr("title", displayNames);
					
					
					var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');
					
					abbrNode.on("click", function(event){
						event.preventDefault(); 
						event.stopPropagation(); 
						_self.spanNode.addClass("info");
						_self.spanNode.removeAttr("title");
						_self.spanNode.html("--请选择--");
						_self.inputNode.val("");
						_self._values=null;
					});
					_self.spanNode.empty();
					_self.spanNode.html(displayNames);
					_self.spanNode.append(abbrNode);
				}else{
					var selectNode = _self._dropDownTree.getNodeByParam(nodeParam, value, null);
					if(selectNode){
						_self._dropDownTree.selectNode(selectNode);
						_self._values = value;
						_self.inputNode.val(value);
						_self.spanNode.removeClass("info");
						_self.spanNode.attr("title", selectNode.name);

						var abbrNode = $('<i class="fa fa-times select-close" aria-hidden="true"></i>');
						
						abbrNode.on("click", function(event){
							event.preventDefault(); 
							event.stopPropagation(); 
							_self.spanNode.addClass("info");
							_self.spanNode.removeAttr("title");
							_self.spanNode.html("--请选择--");
							_self.inputNode.val("");
							_self._values=null;
						});
						_self.spanNode.empty();
						_self.spanNode.html(selectNode.name);
						_self.spanNode.append(abbrNode);
						
					}else{
						_self._values = "";
						_self.inputNode.val("");
						_self.spanNode.attr("title","");
						_self.spanNode.html("<span class='info'>--请选择--</span>");
					}
				}
			},
			
			getValue : function(){
				var _self = this;
				return _self._values;
			},
			setDatasource: function(datasource){
				$.fn.zTree.init($(this._config.render + " .ztree"), this._options, datasource);
				var _self = this;
				_self.spanNode.html("<span>--请选择--</span>");
			}	
	};
	
	return Aptech;
});