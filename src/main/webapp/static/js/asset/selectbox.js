define(function(require, exports, module){

	Aptech.selectbox = function(config){
		
		this._config = config;
		this._config._name = config.name || "";
		this._config._title = config.title || "";
		this._config._url = config.url || "";
		this._config._style = config.divstyle || "";
		this._config._inputstyle = config.inputstyle || "";
		this._config._width = config.width || "";
		this._config._hight = config.hight || "";
		
//		(this._config._style == "") ? this._config._style="width:100%" : this._config._style=this._config._style;
//		(this._config._inputstyle == "") ? this._config._inputstyle="width:90%" : this._config._inputstyle=this._config._inputstyle;
		(this._config._width == "") ? this._config._width="1500" : this._config._width=this._config._width;
		(this._config._hight == "") ? this._config._hight="700" : this._config._hight=this._config._hight;
	};
	
	Aptech.selectbox.prototype={
		render : function(){
			var _self = this;
			var node =$(_self._config.render);
			node.empty();
			var widthParam=this._config._width;
			var hightParam=this._config._hight;
			var divNode = $('<div class="input-group date" style="' + this._config._style +'"></div>');
			var inputNodeOne = $('<input   id="' + this._config.id +'"   style="' + this._config._inputstyle +'" type="text" class="form-control inputFir" readonly="readonly"/>');
			var setValueInput = '';
			if(this._config.id==this._config._name){
				setValueInput = $('<input  name="' + this._config._name +'"  style="' + this._config._inputstyle +'" type="text" class="' + this._config.id +' form-control hidden inputSen"/>');
			}else{
				setValueInput = $('<input id="' + this._config._name +'" name="' + this._config._name +'"  style="' + this._config._inputstyle +'" type="text" class="' + this._config.id +' form-control hidden inputSen"/>');
			}
			var spanNode=$('<span class="input-group-addon btn-primary" style="width: 1%;padding:0px 0px!important; border:0px solid !important;position:relative;z-index:3"></span>');
			var buttonNode=$('<button type="button" class="btn btn-primary btn-sm" ></button>');
			var iNode=$('<i class="ace-icon fa fa-search icon-on-right bigger-110 "></i>');
			divNode.append(inputNodeOne);
			buttonNode.append(iNode);
			spanNode.append(buttonNode);
			node.append(divNode);
			divNode.append(setValueInput);
			divNode.append(spanNode);
			buttonNode.on("click", function(){
				 var selectDialog = new A.dialog({
					title:_self._config._title,
					width:widthParam,
					height:hightParam,
					url:format_url(_self._config._url),
					closed: function(){
						if(_self._config.callback){
							_self._config.callback(data,_self);
						}
					}
				}).render();
			});
		    return _self;
		},
		setValue:function(valueFirse,valueSecond){
			var _self = this;
			var getType = typeof _self._config.render;
			if(getType=='string'){
				$(_self._config.render).find(".inputFir").val(valueFirse);
				if(valueSecond){
					$(_self._config.render).find(".inputSen").val(valueSecond);
				}else{
					$(_self._config.render).find(".inputSen").val(valueFirse);
				}
			}else{
				_self._config.render.find(".inputFir").val(valueFirse);
				if(valueSecond){
					_self._config.render.find(".inputSen").val(valueSecond);
				}else{
					_self._config.render.find(".inputSen").val(valueFirse);
				}
			}
		},
		//赋值方法
		setValueBack:function(id,valueId,valueName){
			if(!valueName){
				valueName = valueId;
			}
			$(id).val(valueId);
			$(id).next('input').val(valueName);
		},
		close: function(){
			var _self = this;
			_self.resule=resule;
			$(".bootbox-close-button.close").trigger("click");
		}
	};
	
	return Aptech;
});