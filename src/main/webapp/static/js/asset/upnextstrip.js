define(function(require, exports, module){
	Aptech.upnextstrip = function(config){
			var _config = {
				render: "",
				url:"",
				data:"",
			};
			
			var _options = {
			};
			
			this._config = config;
			if(this._config.render){
				this._config.container = $(config.render);
			}
			$.extend(true, _options, config.options);
			this._options = _options;
	};
	
	Aptech.upnextstrip.prototype = {
			render:  function(){
				var _self = this;
				var  dedit=JSON.parse(_self._config.data);
				var upperBtn=$('<button id="upperBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">'+
				'<i class="glyphicon glyphicon-chevron-up"></i>上一条</button>');
				var nextBtn=$('<button id="nextBtn" class="btn btn-xs btn-success pull-right"  style="margin-right:15px;">'+
        					 '<i class="glyphicon glyphicon-chevron-down"></i>下一条</button>');
				$(_self._config.render).append(nextBtn);
				$(_self._config.render).append(upperBtn);
				if(dedit.start==0){
					upperBtn.attr("class","btn btn-xs disabled pull-right");
				}else{
					upperBtn.on("click", function(e){
					dedit.start=dedit.start-1;
					$.ajax({url : _self._config.url,
						contentType : 'application/json',
						type : 'POST',
						data : JSON.stringify(dedit),
						success : function(result) {
					    	$('.popover').remove();
							var divshow = $("#page-container");
							divshow.text("");// 清空数据
							divshow.append(result); // 添加Html内容，不能用Text 或 Val
						}
					});
				});
				}
				if(dedit.start==(dedit.total-1)){
					nextBtn.attr("class","btn btn-xs disabled  pull-right ");
				}else{
					nextBtn.on("click", function(e){
    					dedit.start=dedit.start+1;
						$.ajax({url : _self._config.url,
							contentType : 'application/json',
							type : 'POST',
							data : JSON.stringify(dedit),
							success : function(result) {
								$('.popover').remove();
								var divshow = $("#page-container");
								divshow.text("");// 清空数据
								divshow.append(result); // 添加Html内容，不能用Text 或 Val
							}
						});
    				});
				}
				return _self;
			},
			
	};
	
	return Aptech;
});