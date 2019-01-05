define(function(require, exports, module){
	require("../../css/themes/{theme}/chosen.min.css");
	require("../../css/themes/{theme}/ace.min.css");	
	require("../thirdparty/chosen.jquery.min");
	Aptech.combobox = function(config){
			var _config = {
				name: "",
				datasource:"",
				multiple: false,
				allowBlank: false,
				initValue:"",
				container:"",
				
			};
			
			var _options = {
				//"allow_single_deselect": true,
			    "placeholder_text_multiple":"--请选择--",
			    "placeholder_text_single" : "--请选择--",
			    "no_results_text" : "没有匹配到数据",
			    search_contains: true, 
			};
			
			this._config = config;
			if(this._config.render){
				this._config.container = $(config.render);
			}
			$.extend(true, _options, config.options);
			this._options = _options;
	};
	
	Aptech.combobox.prototype = {
			render:  function(){
				var _self = this;
				_self._config.container.attr("name", _self._config.name);
				if(_self._config.datasource){
					_self._config.container.empty();
					if(_self._config.allowBlank&&!_self._config.multiple){
						var option = $("<option value=''></option><option value=''>&nbsp</option>");
						_self._config.container.append(option);
					}else if(_self._config.allowBlank&&_self._config.multiple){
						var option = $("<option value=''></option>");
						_self._config.container.append(option);
					}
					for(var i=0; i<_self._config.datasource.length; i++){
						var option = $("<option value="+ _self._config.datasource[i].code +">"+ _self._config.datasource[i].name+"</option>");
						_self._config.container.append(option);
					}
				}
				if(_self._config.multiple){
					_self._config.container.attr("multiple", "multiple");
				}
				_self.chosen = _self._config.container.chosen(_self._options);
				_self.chosen.trigger("chosen:updated");
				if(_self._config.initValue){
					_self.setValue(_self._config.initValue);
				}
				$(_self._config.render).on('change',function(e,params){
					if(params.selected){
						$(_self._config.render).parent().removeClass('has-error');
						$(_self._config.render).next('label').remove();
						$(_self._config.render).next('.chosen-container').removeClass('error')
					}
				})
				return _self;
			},
			
			getValue: function(){
				var _self = this;
				if(_self._config.multiple){
					var resultValues = [];
	                var options = $(_self._config.render + " option:selected");
	                for (i = 0; i < options.length; i++) {
	                	resultValues.push($(options[i]).val());
	                }
	                return resultValues.join(",");
				}
				return $(this._config.render).val();
			},
			
			setValue: function(value){
				var _self = this;
				if(value){
					if(_self._config.multiple){
						if(typeof value=='string'){
							var arr = value.split(',');
				            var length = arr.length;
				            var value = '';
				            var setArr = [];
				            for (i = 0; i < length; i++) {
				                value = arr[i];
				                setArr.push(value);
				                _self._config.container.val(setArr)
				            }
						}else{
				             _self._config.container.val(value);
						}
					}else{
						_self._config.container.val(value);
					}
					_self.chosen.trigger("chosen:updated");
				}	
			},
			clearValue:function(){
				var _self = this;
				_self._config.container.val('');
				_self.chosen.trigger("chosen:updated");
			},
			change: function(callback){
				var _self=this;
				_self.chosen.change(callback);
			},
	};
	
	return Aptech;
});