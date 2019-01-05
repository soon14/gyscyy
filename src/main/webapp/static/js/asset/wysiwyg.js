define(function(require, exports, module){
	require("../../css/themes/{theme}/bootstrap-duallistbox.min.css");
	require("../thirdparty/jquery-ui.custom.min");
	require("../thirdparty/jquery.ui.touch-punch.min");
	require("../thirdparty/markdown.min");
	require("../thirdparty/bootstrap-markdown.min");
	require("../thirdparty/jquery.hotkeys.index.min");
	require("../thirdparty/bootstrap-wysiwyg.min");
	Aptech.wysiwyg = function(config){
		
		var _options={
				height: "250px",
				editable: true,
				toolbar:[
						'font',
						null,
						'fontSize',
						null,
						{name:'bold', className:'btn-info'},
						{name:'italic', className:'btn-info'},
						{name:'strikethrough', className:'btn-info'},
						{name:'underline', className:'btn-info'},
						null,
						{name:'insertunorderedlist', className:'btn-success'},
						{name:'insertorderedlist', className:'btn-success'},
						{name:'outdent', className:'btn-purple'},
						{name:'indent', className:'btn-purple'},
						null,
						{name:'justifyleft', className:'btn-primary'},
						{name:'justifycenter', className:'btn-primary'},
						{name:'justifyright', className:'btn-primary'},
						{name:'justifyfull', className:'btn-inverse'},
						null,
						{name:'createLink', className:'btn-pink'},
						{name:'unlink', className:'btn-pink'},
						null,
						{name:'insertImage', className:'btn-success'},
						null,
						'foreColor',
						null,
						{name:'undo', className:'btn-grey'},
						{name:'redo', className:'btn-grey'}
				    ]
		}
	
		$.extend(true, _options, config.options);
		this._options = _options;
		this._options._render = config.render;
		if(config.options.toolbar){
			this._options.toolbar = config.options.toolbar;
		}
	};
	
	
	Aptech.wysiwyg.prototype = {
		
			render: function(){
				var _self = this;
				var _render = _self._options._render;
				var content = $('<div></div>');
				$(_render).addClass("wysiwyg-editor");
				$(_render).ace_wysiwyg({
					toolbar:_self._options.toolbar,
					'wysiwyg': {
						fileUploadError: function(reason, detail) {

							var msg='';
							if (reason==='unsupported-file-type') { msg = "上传格式不正确"; }
							else {
								console.log("error uploading file", reason, detail);
							}
							alert(msg);
						}
					}
				}).prev().addClass('wysiwyg-style2');
				$(".wysiwyg-editor").css("height",_self._options.height);
				$(".wysiwyg-editor").css("max-height","500px");
				
				var editable = _self._options.editable;
				if(!editable){
					$(_render).attr("contenteditable",false);
				}
				return _self;
			},
			getValue:function(){
				debugger;
				var _self = this;
				var _render = _self._options._render;
				return $(_render).html();
			},
			setValue:function(data){
				var _self = this;
				var _render = _self._options._render;
				$(_render).html(data);
			}
			
			
	};
	
	return Aptech;
});