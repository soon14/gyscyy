define(function(require, exports, module){
	
	Aptech.uploadifyBatch = function(config){
		
		var _config = {
			name: "",
			multiple: false,
			allowBlank: false,
			initValue:"",
			url: "",
			type: "file",
			callback: null
		};
		var _options = {
			no_file:'无文件 ...',
			btn_choose:'浏览',
			btn_change:'修改',
			droppable:false,
			onchange:null,
			thumbnail:false, //| true | large
			before_remove: function(){
				$(this).parent().parent().find("input[name='"+config.name+"Name']").val("");
				$(this).parent().parent().find("input[name='"+config.name+"Url']").val("");
				if(config.before_remove){
					config.before_remove();
				}else{
					return true;
				}
			},
			//whitelist:'gif|png|jpg|jpeg'
			blacklist:'exe|php|jsp'
		};

		this._config = config;
		$.extend(true, _options, config.options);
		this._options = _options;
	};
	
	Aptech.uploadifyBatch.prototype = {
			render: function(){
				var _self = this;
				//添加按钮
				var addDiv=$('<div class="action-buttons pull-right col-xs-1"></div>');
				var addA=$('<a href="#" id="id-add-attachment"></a>');
				var addI=$('<i class="ace-icon glyphicon glyphicon-plus green "></i>');
				//组装按钮
				addA.append(addI);
				addDiv.append(addA);
				//创建上传组件
				createFile(_self,addDiv);
				//新增按钮事件
				addA.on('click', function(){
					//删除按钮
					var deleteDiv=$('<div class="action-buttons pull-right col-xs-1"></div>');
					var deleteA=$('<a href="#" data-action="delete" class="middle"></a>');
					var deleteI=$('<i class="aace-icon fa fa-trash-o red bigger-130 middle"></i>');
					deleteA.append(deleteI);
					deleteDiv.append(deleteA);
					//创建上传组件
					createFile(_self,deleteDiv);
				});
				function createFile(_self,addDiv){
					//返回值 type="hidden"
					var fileName=$('<input  name="'+_self._config.name+'Name"  type="hidden"/>');
					var fileUrl=$(' <input  name="'+_self._config.name+'Url"   type="hidden"/>');
					addDiv.append($(fileName));
					addDiv.append($(fileUrl));
					//创建上传组件
					var file = $('<input type="file" name="attachment[]"  />').appendTo(_self._config.render);
					file.ace_file_input(_self._options);
					file.closest('.ace-file-input').addClass('width-90 inline')
					.wrap('<div class=" file-input-container"></div>')
					.parent().append(addDiv)
					.find('a[data-action=delete]').on('click', function(e){
						e.preventDefault();
						$(this).closest('.file-input-container').hide(300, function(){ $(this).remove() });
					});
					file.on('change', function(){
						var fd = new FormData();
						var files = $(this).data('ace_input_files');
						if(files && files.length > 0) {
							for(var f = 0; f < files.length; f++) {
								fd.append('Filedata', files[f]);
							}
						}
						fd.append('type', _self._config.type);
						$.ajax({
					        url: _self._config.url,
					        type: 'POST',
				         	processData: false,//important
					  		contentType: false,//important
					     	dataType: 'JSON',//depending on your server side response
					        data: fd,//our FormData object
					        success: function(result){
					        	if(_self._config.callback){
					        		if(result.result == 'success'){
					        			fileName.val(result.name);
					        			fileUrl.val(result.url);
					        		}else{
					        			alert(result.errorMsg);
					        		}
					        	}
					        }
						});
					});
				}
				return _self;
			},
			getNameValue: function(){
				var fileName=[];
				$("input[name='"+this._config.name+"Name']").each(function(){
					fileName.push($(this).val());
				});
				return fileName;
			},
			getUrlValue: function(){
				var fileUrl=[];
				$("input[name='"+this._config.name+"Url']").each(function(){
					fileUrl.push($(this).val());
				});
				return fileUrl;
			},
			setValue: function(value){
				var _self = this;
				return $(_self._config.render).val(value);
			}
	};
	
	return Aptech;
});