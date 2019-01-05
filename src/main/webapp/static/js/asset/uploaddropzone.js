define(function(require, exports, module){
	require("../../js/thirdparty/dropzone.min");
	require("../../css/themes/{theme}/dropzone.min.css");
	Aptech.uploaddropzone = function(config){
		this._config = config;
		var getDefect = config.defectUrl?config.defectUrl:'defect';
		var options = {
				url:format_url("/fileDropzone/upload?type="+getDefect),
				thumbnailHeight: 70,
				thumbnailWidth: 70,
				maxFilesize: 60,
				maxFiles:30,
				filesLength:0
		}
		if(config.options){
			$.extend(true, options, config.options);
			
		}
		this.options = options;
	};

	Aptech.uploaddropzone.prototype={
		render : function(index){
			var _self = this;
			var getId = 'dropzone';
			if(!index){
				index = '';
			}
			var background = "#fff";
			if(_self._config.chargeUp){
				background = '#f5f5f5';
			}
			getId = getId+index;
			var thisdiv =$(_self._config.render);
			var divfrom = $('<div> <form action="./dummy.html" class="dropzone well" id="'+getId+'" style="background-color:'+background+'">'+
				'<div class="fallback"><input name="file" type="file" multiple="" /></div>'+
				'</form></div>');
			var divpreview = $('<div id="preview-template" class="hide">'+
				'<div class="dz-preview dz-file-preview dropClick"  title="点击下载"  onclick="checkMeDownLoadFile(this)"><div class="dz-image">'+
				'<a  class="img_a" target="_blank"><img data-dz-thumbnail="" width="'+_self.options.thumbnailWidth +'" height="'+_self.options.thumbnailHeight +'" src="../static/images/file.png"/></a></div>'+
				'<div class="dz-details"><div class="dz-size"><span data-dz-size=""></span></div>'+
				'<div class="dz-filename"><span data-dz-name=""></span></div></div>'+
				'<div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress=""></span></div>'+
				'<div class="dz-error-message"><span data-dz-errormessage=""></span></div>'+
				'<div class="dz-success-mark"><span class="fa-stack fa-lg bigger-150">'+
				'<i class="fa fa-circle fa-stack-2x white"></i>'+
				'<i class="fa fa-check fa-stack-1x fa-inverse green"></i></span></div></div></div>');
			thisdiv.append(divfrom);
			thisdiv.append(divpreview);
			for(i in _self._config.fileId){
				_self._config.fileId[i].urls=format_url(_self._config.fileId[i].url);
			};
			try {
				Dropzone.autoDiscover = false;
				var setMessage = '';
				if(!_self._config.chargeUp){
					setMessage = '<span class="smaller-60 grey"><i class="upload-icon ace-icon fa fa-cloud-upload blue"></i>拖拽或者点击上传文件</span>';
				}else{
					setMessage = '<span class="smaller-60 grey"><i class="upload-icon ace-icon fa fa-cloud-upload blue"></i>暂无上传文件</span>';
				}
				getId = '#dropzone'+index;
				new Dropzone(getId, {
					previewTemplate: $('#preview-template').html(),
					url:_self.options.url,
					thumbnailHeight: _self.options.thumbnailHeight,
					thumbnailWidth: _self.options.thumbnailWidth,
					maxFilesize: _self.options.maxFilesize,
					maxFiles:_self.options.maxFiles,
					clickable:!_self._config.chargeUp,
					acceptedFiles: _self._config.acceptedFiles,//筛选上传文件类型
					autoProcessQueue: _self._config.autoProcessQueue,//是否自动上传
					addRemoveLinks : _self._config.addRemoveLinks,//显示删除按钮
					dictRemoveFile: '删除',
					dictFileTooBig: "该上传文件为({{filesize}}MiB)超出{{maxFilesize}}MiB的限制",
					dictMaxFilesExceeded:'本次上传已超过最多'+_self.options.maxFiles+'个文件的限制，多余文件将不被会上传',
					dictDefaultMessage :setMessage
					,
					thumbnail: function(file, dataUrl) {
						if (file.previewElement) {
							$(file.previewElement).removeClass("dz-file-preview");
							var images = $(file.previewElement).find("[data-dz-thumbnail]").each(function() {
								var thumbnailElement = this;
								thumbnailElement.alt = file.name;
								thumbnailElement.src = dataUrl;
							});
							setTimeout(function() { $(file.previewElement).addClass("dz-image-preview"); }, 1);
						}
					},
		            success: function(a) {
		            	$('.dropClick').on('click',function(){
		            		if(_self._config.clickCallBack){
		            			_self._config.clickCallBack()
		            		}
						});
	            		if(a.xhr){
	            			var responseTextS = a.xhr.responseText;
		            		var objS = JSON.parse(responseTextS);
		            		if(objS.result == "error"){
			            		var errDiv = "<div class='dz-error-message'><span data-dz-errormessage>"+objS.errorMsg+"</span></div>"
			            			$(a.previewElement).addClass('dz-error');
			            			$(a.previewElement).append(errDiv);
			            			alert(objS.errorMsg);
			            		return;
			            	}
	            		}
		            	
		            	if(a.url){
		            		$(a.previewElement).find(".dz-image a").each(function(){
			            		var thumbnailA = this;
			            		var responseText = encodeURI(a.url);
			            		$(thumbnailA).attr('href',format_url(responseText));
		            		})
		            	}else{
		            		$(a.previewElement).find(".dz-image a").each(function(){
			            		var thumbnailA = this;
			            		var responseText = a.xhr.responseText;
			            		var obj = JSON.parse(responseText);
			            		var getUrl = encodeURI(obj.url);
			            		$(thumbnailA).attr('href',format_url(getUrl));
			            	})
		            	}
		            	if(_self._config.successCallBack){
		            		_self._config.successCallBackFunction();
		            	}
		                return a.previewElement ? a.previewElement.classList.add("dz-success") : void 0
		            },
					init: function () {
						var get_a;
						//初始化图
						this.emit("initimage", _self._config.fileId);
//				    	当上传完成后的事件，接受的数据为JSON格式  
						this.on("complete", function (data) {
							if(data.xhr){
								var obj=JSON.parse(data.xhr.response);
							}
							_self._config.fileId.push(obj);
						});
						this.on("queuecomplete",function(file) {
			                //上传完成后触发的方法
							_self.options.filesLength = this.getAcceptedFiles().length;
			            });
						//删除图片的事件
						this.on("removedfile", function (data) {
							_self.options.filesLength = this.getAcceptedFiles().length;
							if(_self._config.deleteCallBack){
			            		_self._config.deleteCallBackFunction();
			            	}
							if(data.xhr){
								var obj=JSON.parse(data.xhr.response);
								for (var i = 0; i < _self._config.fileId.length; i++) {
									var filejson=_self._config.fileId[i];
									if(filejson){
										if(filejson.url==obj.url){
											_self._config.fileId.splice(i,1);
										}
									}else{
										_self._config.fileId.splice(i,1);
									}
								}
							}else if(data.url){
								for (var i = 0; i < _self._config.fileId.length; i++) {
									var filejson=_self._config.fileId[i];
									if(filejson.url==data.url){
										_self._config.fileId.splice(i,1);
									}
									if(!filejson){
										_self._config.fileId.splice(i,1);
									}
								}
							}else {
								for (var i = 0; i < _self._config.fileId.length; i++){
									var filejson=_self._config.fileId[i];
									if(!filejson){
										_self._config.fileId.splice(i,1);
									}
								}
							};
						});
					}
				});
			} catch(e) {
				alert('暂不支持该浏览器');
			}
			return _self;
		},
		getValue: function(){
			var _self=this;
			for (var i = 0; i < _self._config.fileId.length; i++){
				var filejson=_self._config.fileId[i];
				if(!filejson){
					_self._config.fileId.splice(i,1);
				}
			}
			return _self._config.fileId;
		},
		//获取上传成功的长度
		getLenght:function(){
			var _self=this;
			return _self.options.filesLength;
		},
		//获取实际长度
		getNowLength:function(){
			var _self=this;
			return $(_self._config.render).find('.dz-complete').length;
		}
	};

	return Aptech;
});
function checkMeDownLoadFile(self){
	var getSrc = $(self).children().find('.img_a')[0].href;
	var getNowA = $(self).children().find('.img_a')[0];
	var chargeIE = !!window.ActiveXObject || "ActiveXObject" in window;
	  if (chargeIE) {
		  var fileURL=window.open (getSrc,"_blank","height=0,width=0,toolbar=no,menubar=no,scrollbars=no,resizable=on,location=no,status=no");
	        fileURL.document.execCommand("SaveAs");
	        fileURL.window.close();
	        fileURL.close();
	    } //判断是否IE浏览器
	    else{
	    	getNowA.setAttribute("download", "");
	    } //判断是否Chrome浏览器
}  