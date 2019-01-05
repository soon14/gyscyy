define(function(require, exports, module){
	//require("../../css/themes/{theme}/colorbox.min.css");
	//require("../thirdparty/jquery.colorbox.min");
	
	Aptech.dialog = function(option){
		
		var _options={
				width:'850',
				height: '471',
				title:"title",
				url: '',
				opened:'',
				closed:''
		}
	
		$.extend(true, _options, option);
		this._options = _options;
	};
	
	
	Aptech.dialog.prototype = {
		
			render: function(){
				var _self = this;
				var content = $('<div></div>');
				var dialog = bootbox.dialog({
					title: _self._options.title,
					message: "<div id='dialogContent'></div>",
					callback:_self._options.closed,
			        className: 'custom-dialog',
			        onEscape: function(){
			        	$('.popover').remove();
			        	if(_self._options.closed){
			        		if(_self._options.equipInfoArray){
			        			_self._options.closed(equipInfoArray);
			        		}else{
			        			_self._options.closed();
			        		}
			        	}
			        },
			        animate:false
				});
				
				
				dialog.init(function(){
					try{
						$(".custom-dialog .modal-dialog").css("width", _self._options.width+"px");
						$(".custom-dialog .modal-dialog").css("margin",0+"px");
						$(".custom-dialog .modal-body").css("height", (_self._options.height-45)+'px');
						$("#dialogContent").load(_self._options.url, _self._options.data, function(response,status,xhr){
							if(status == "error"){
								$("#dialogContent").empty();
								$("#dialogContent").append(content);
							}
							if(_self._options.opened){
								_self._options.opened(response,status,xhr);	
							}
						});	
					}catch(e){
						throwError(e);
					}
				});
				_self._dialog = dialog;
				return _self;
			},
			
			close: function(){
				var _self = this;
				$(".bootbox-close-button.close").trigger("click");
			},
			close: function(resule){
				var _self = this;
				_self.resule=resule;
				$(".bootbox-close-button.close").trigger("click");
			}
	};
	
	return Aptech;
	
});