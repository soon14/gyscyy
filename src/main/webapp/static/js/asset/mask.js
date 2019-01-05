define(function(require, exports, module){
	//require("../../css/themes/{theme}/colorbox.min.css");
	//require("../thirdparty/jquery.colorbox.min");
	
	Aptech.mask = function(option){
	};
	
	
	Aptech.mask.prototype = {
		
			render: function(){
				var _self = this;
				var content = $('<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>');
				
				var mask = '<div class="modal-dialog"><img src="../static/images/loading2.gif" style="width:100px;height:100px;margin-top:55%;margin-left:45%;"/></div>';
				content.append(mask);
				$("body").append(content);
				$('#myModal').modal('show');
				_self._mask = content;
				return _self;
			},
			close:function(){
				$('#myModal').modal('hide');
			}
	};
	
	return Aptech;
	
});