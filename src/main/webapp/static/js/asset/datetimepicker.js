define(function(require, exports, module){
	require("../../css/themes/{theme}/bootstrap-datetimepicker.min.css");
	require("../thirdparty/moment.min");	
	require("../thirdparty/bootstrap-datetimepicker.min");
	Aptech.datetimepicker = function(config){		
		var _options = {
	        format: "yyyy-mm-dd",
	        name:'',
	        language:  'zh-CN',
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
		};
		this._config = config;
		$.extend(true, _options, config.options);
		this._options = _options;
	};

	Aptech.datetimepicker.prototype={
		render: function(){
			_self = this;
			var node =$(_self._config.render);
			node.empty();
			var divNode = $('<div class="input-group date"></div>')
			var inputNode = $('<input class="form-control" size="16" type="text" value=""  name="'+ _self._options.name +'" readonly>');
		    var iconNode =$('<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>');
			
		    divNode.append(inputNode);
		    divNode.append(iconNode);
		    
		    node.append(divNode);
		    
			this._datetimepicker =  $(divNode).datetimepicker(_self._options);
			
			return _self;
		},
		
		setValue: function(value){
			//
			$(this._config.render + " input").val(value);
		},
		
		getValue : function(){
			//
			return $(this._config.render + " input").val();
		},
		
		change : function(callback){
			var _self = this;
			this._datetimepicker.on("change", callback);
		}
	};
	
	
	return Aptech;
});