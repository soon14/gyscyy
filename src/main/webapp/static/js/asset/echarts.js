define(function(require, exports, module){
	require("../thirdparty/echarts/echarts.min");
	require("../../css/themes/{theme}/echarts/default");
	
	Aptech.echarts = function(config){
		var _options  = {};
		$.extend(true, _options, config.options);
		this._config = config;
		this._options = _options;
	};
	
	Aptech.echarts.prototype = {
		render: function(){
			var _self = this;
			var getEcharts = this._echarts = echarts.init($(this._config.render)[0]);
			this._echarts.setOption(this._config.options);
			window.onresize = function(){
				getEcharts.resize();
			}
			return _self;
		},
		
		getECharts: function(){
			return this._echarts;
		}
	};
	
	return Aptech;
});