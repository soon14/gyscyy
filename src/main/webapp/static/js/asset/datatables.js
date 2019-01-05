define(function(require, exports, module){
	require("../thirdparty/dataTables.buttons.min.js");
	require("../thirdparty/dataTables.select.min");
	require("../thirdparty/jquery.dataTables.bootstrap.min.js");
	Aptech.datatables = function(config){
		var 	_options = {
	       processing: true,
	       serverSide: true,
		   paging: true,
		   ordering: false,
		   autoWidth: false,
	       dom: '<"row min-height-50"<"col-xs-12 toolbar">>t<"row pt5 pb0"<"pageLeft"i><"pageCenter"p><"pageRight mt5"l>>',
		   language: {
		        "sProcessing": "处理中...",
		        "sLengthMenu": "显示 _MENU_ 项结果",
		        "sZeroRecords": "无",
		        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
		        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
		        //"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
		        "sInfoFiltered":"",
		        "sInfoPostFix": "",
		        "sSearch": "搜索:",
		        "sUrl": "",
		        "sEmptyTable": "无",
		        "sLoadingRecords": "加载中...",
		        "sInfoThousands": ",",
		        "oPaginate": {
		            "sFirst": "首页",
		            "sPrevious": "上页",
		            "sNext": "下页",
		            "sLast": "末页"
		        },
				select: {
					rows: {
						_: "%d 行被选择",
						0: "",
						1: "1 行被选择"
					}
				},
		        "oAria": {
		            "sSortAscending": ": 以升序排列此列",
		            "sSortDescending": ": 以降序排列此列"
		        }
		    },
			searching: false,
			bLengthChange : true,
			aLengthMenu: [20,30,50],
			bInfo : true,//页脚信息
			checked: true
		};
		$.extend(true, _options, config.options);
		this._config = config;
		this._options = _options;
		this._bindFlag = false;	
	};
		
	Aptech.datatables.prototype={
		render: function(){
			var _self = this;
			var optWidth = 100;
			if(this._options.optWidth){
				optWidth = this._options.optWidth;
			}
			if(_self._options.checked){
				var checkbox = { orderable: false,"sClass": "center", "width": "50px", "render": function(data, type, row){
					var rootDiv = $("<div></div>")
					var checkboxNode = $('<label class="pos-rel"><input type="checkbox" data-id='+row.id+' class="ace" /><span class="lbl"></span></label>');
					rootDiv.append(checkboxNode);
					return rootDiv.html();
				}
				};
				_self._options.columns.splice(1, 0, checkbox);
			}
			$.each(_self._options.columns, function(index, column){
				if(column.editType){
					column.createdCell = function(td, cellData, rowData, row, col){
						$(td).empty();
						if(column.editType=='input'){
							if(column.editHeight){
								if(column.editReadOnly){
									var inputNode = $('<input id="'+ column.data +'Data'+ rowData.id +'" name="'+ column.data +'" type="text" class="col-md-12" readonly="readonly" value="' + cellData +'" style="height:'+ column.editHeight +';width:'+column.editWidth+'">');
								}else{
									if(column.onblur){
										var inputNode = $('<input id="'+ column.data +'Data'+ rowData.id +'" name="'+ column.data +'" type="text" class="col-md-12" value="' + cellData +'" style="height:'+ column.editHeight +';width:'+column.editWidth+'" onblur=inputBlur(this)>');
									}else{
										var inputNode = $('<input id="'+ column.data +'Data'+ rowData.id +'" name="'+ column.data +'" type="text" class="col-md-12" value="' + cellData +'" style="height:'+ column.editHeight +';width:'+column.editWidth+'">');
									}
								}
							}else{
								if(column.editReadOnly){
									var inputNode = $('<input id="'+ column.data +'Data" name="'+ column.data +'" type="text" class="col-md-12" readonly="readonly" value="' + cellData +'" style="width:'+column.editWidth+'">');
								}else{
									if(column.onblur){
										var inputNode = $('<input id="'+ column.data +'Data" name="'+ column.data +'" type="text" class="col-md-12" value="' + cellData +'" style="width:'+column.editWidth+'" onblur=inputBlur(this)>');
									}else{
										var inputNode = $('<input id="'+ column.data +'Data" name="'+ column.data +'" type="text" class="col-md-12" value="' + cellData +'" style="width:'+column.editWidth+'">');
									}
								}
							}
							$(td).append(inputNode);
							if(_self._options.customCreateCell){
								_self._options.customCreateCell(column, td, cellData, rowData, row, col);
							}
							inputNode.val(cellData);
						}if(column.editType == 'combobox'){
							var render = column.data + row + 'Select';
							var containerName = column.data;
							if(column.data == null || column.data==""){
								containerName = column.name;
							}
							var container = $('<select id="' + render +'" name="'+ containerName + '"></select>');
							$(td).append(container);
							if(_self._options.customCreateCell){
								_self._options.customCreateCell(column, td, cellData, rowData, row, col);
							}
							var cfg = {
									"container" : container,
							}
							$.extend(true, cfg, column.cfg);
							var editCombobox = new A.combobox(cfg).render();

							if(cfg.change){
								editCombobox.change(cfg.change);
							}
							if(cellData){
								editCombobox.setValue(cellData);
							}
						}if(column.editType == 'combotree'){
							var render = column.data + row + 'Div';
							var container = $('<div id="' + render +'"></div>');
							$(td).append(container);
							if(_self._options.customCreateCell){
								_self._options.customCreateCell(column, td, cellData, rowData, row, col);
							}
							var cfg = {
									"container" : container,
									options: {
										treeId:  render+'DropdownTree',
									}
							}
							$.extend(true, cfg, column.cfg);
							var editCombotree = new A.combotree(cfg).render();
							if(rowData){
								editCombotree.setValue(cellData);
							}
						}if(column.editType == 'my97datepicker'){
							var render = column.data + row + 'Div';
							var container = $('<div id="' + render +'" style="width:100%;"></div>');
							$(td).append(container);
							if(_self._options.customCreateCell){
								_self._options.customCreateCell(column, td, cellData, rowData, row, col);
							}
							var cfg = {
									isDisabled:false,
									"id": column.data + row + 'my97datepicker',
									"container" : container,
							}
							$.extend(true, cfg, column.cfg);
							var datePickerName = new A.my97datepicker(cfg).render();
							//设置默认初始值，setValue
							if(rowData){
								datePickerName.setValue(cellData);
							}
						}if(column.editType == 'selectbox'){
							/*
							var render = column.data + row + 'Box';
							var container = $('<div id="' + render +'"></div>');
							$(td).append(container);
							var cfg = {
								id:column.data + row + 'Id' ,
								render:container,
							}
							$.extend(true, cfg, column.cfg);
							var selectBoxName = new A.selectbox(cfg).render();
							var getDocumentId = '#'+column.data + row + 'Name';
							if(rowData){
								selectBoxName.setValue(cellData,cellData);
							}*/
						}
					};
				}
			});
			
			//生成按钮
			if(_self._options.btns){
				var btns = { orderable: false,"sClass": "center middle", "width": optWidth, "render": function(data, type, row){
						var rootDiv = $("<div></div>")
						var btnDiv = $("<div class='action-buttons btnList'></div>");
//					    var changeBtn = $('<a class="glyphicon glyphicon-list-alt changeBtn"  onclick ="changeForNome(this)" style="color:grey;cursor: pointer; margin-right:5px;" title="修改" aria-hidden="true"></a>');
//						btnDiv.append(changeBtn);
						if(_self._options.btns){
							$.each(_self._options.btns, function(index, rowBtn){
								var rowBtnA = $("<a data-id='"+rowBtn.id+"' class='"+rowBtn.className+"' style='cursor: pointer;text-align:left!important;padding:0 8px' title='"+ rowBtn.label +"'><i class='"+rowBtn.icon+"'></i></a>");
								btnDiv.append(rowBtnA);
							});
						}
						rootDiv.append(btnDiv);
						return rootDiv.html();
					}
				};
				if(_self._options.btns){
					_self._options.columns.push(btns);
				}
			}
			
			_self._options["fnRowCallback"] = function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
					if(_self._options.btns){
						$.each(_self._options.btns, function(index, rowBtn){
							if(rowBtn.render){
								var btnNode = $(nRow).find("a[data-id="+rowBtn.id+"]");
								rowBtn.render(btnNode, aData);
							}
							$.each(rowBtn.events, function(eventName, callBack){
								$(nRow).find("a[data-id="+rowBtn.id+"]").unbind();
								$(nRow).find("a[data-id="+rowBtn.id+"]").on(eventName,  function(event){
									var args = [nRow, aData];
									args.unshift([event]);
									callBack.apply(this, args);
								});
							});
						});
					}
			}
			
			_self._options["drawCallback"] = function(){
				//
			}
			
			_self._datatables = $(_self._config.render).DataTable(_self._options);
			
			if(_self._options.toolbars && _self._options.toolbars.length>0){
				//TODO 渲染列表的Toolbar按钮
				$.each(_self._options.toolbars, function(index, toolbar){
					var toolbarBtn = $("<button class='btn btn-xs "+toolbar.className+"' style='float:right; margin-left:5px;' type='button'><i class='ace-icon "+toolbar.icon+"'></i>"+toolbar.label+"</button>");
					$(_self._config.render).prev().children(".toolbar").prepend(toolbarBtn);
					if(toolbar.events){
						$.each(toolbar.events, function(eventName, callBack){
							toolbarBtn.on(eventName, function(event){
								var args = [event];
								callBack.apply(this, args);
							});
						});
					}
				});
			}else{
				$(_self._config.render).prev().children(".toolbar").parent().remove();
			}
			
			if(!_self._options.bInfo && !_self._options.paging){
				$(_self._config.render).parent().children(".row.pt5.pb0").remove();
			}
			
			_self._datatables.on('draw.dt', function(){
				$(_self._config.render + ' > thead > tr > th input[type=checkbox]').removeAttr("checked");
			});
			
			_self._datatables.on( 'select', function ( e, dt, type, index ) {
				if ( type === 'row' ) {
					$( _self._datatables.row( index ).node() ).find('input:checkbox').prop('checked', true);
				}
			} );
			_self._datatables.on( 'deselect', function ( e, dt, type, index ) {
				if ( type === 'row' ) {
					$( _self._datatables.row( index ).node() ).find('input:checkbox').prop('checked', false);
					$(_self._config.render + ' > thead > tr > th input[type=checkbox]').removeAttr("checked");
				}
			} );
			
			//设置选中事件
			$(_self._config.render).on('click', 'td input[type=checkbox]' , function(){
				var row = $(this).closest('tr').get(0);
				
				var getAllRow = $(this).parents('tbody').children('tr').children('td').children('label').children('input[type=checkbox]');
				var chargeNum = 0;
				for(var i=0;i<getAllRow.length;i++){
					var getNowInput = $(getAllRow[i]).is(":checked");
					if(getNowInput){
						chargeNum++;
					}
				}
				if(chargeNum==getAllRow.length){
					$(_self._config.render + ' > thead > tr > th input[type=checkbox]').prop('checked', true);
				}
				var checked = $(this).is(":checked");
				if(!checked) {
					_self._datatables.row(row).deselect();
				}else{
					if(!_self._options.multiple){
						$(_self._config.render).find("td input[type=checkbox]").removeAttr("checked");
						$(_self._config.render).find('tbody > tr').each(function(){
							var row = this;
							_self._datatables.row(row).deselect();
						});
					}
					_self._datatables.row(row).select();
				} 
			});
			
			if(_self._options.multiple){
				$(_self._config.render + ' > thead > tr > th input[type=checkbox], '+_self._config.render+'_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					$(_self._config.render).find('tbody > tr').each(function(){
						var row = this;
						if(th_checked)
							_self._datatables.row(row).select();
						else  
							_self._datatables.row(row).deselect();
					});
				});
			}else{
				$(_self._config.render + ' > thead > tr > th input[type=checkbox]').parent().hide();
			}

			$(_self._config.render).show();
			return _self;
		},
		
		reLoad:function(url){
			var _self = this;
			_self._datatables.ajax.url(url).load();
		},
	
		getSelectRowDatas:function(){
			var _self = this;
			if(_self._options.multiple){
				return _self._datatables.rows({selected:true}).data();
			}else{
				return _self._datatables.row({selected:true}).data();
			}
		},
		
		getCurrentPage:function(){
			var _self = this;
			return _self._datatables.page();
		},
		
		getPageSize:function(){
			var _self = this;
			return _self._datatables.page.len();
		},
		addRow:function(rowData){
			var _self = this;
			if(rowData){
				_self._datatables.row.add(rowData).draw(false);
			}
		},
		
		addRows:function(rowDatas){
			var _self = this;
			if(rowDatas){
				_self._datatables.rows.add(rowDatas).draw(false);
			}
		},
		
		deleteSelectRow: function(row){
			var _self = this;
			_self._datatables.row(row).remove().draw(false);
		},
		
		setPage: function(pageNum, pageSize){
			var _self = this;
			_self._datatables.page(pageNum);
			_self._datatables.page.len(pageSize);
			_self._datatables.draw(false);
		},
		
		draw:function(flag){
			var _self = this;
			 _self._datatables.draw(flag);
		},
		getDatas:function(){
			var _self = this;
			return _self._datatables.data();
		},
		getDatasId:function(){
			var newArr = [];
			var _self = this;
			var getDates =  _self._datatables.data();
			for(var i=0;i<getDates.length;i++){
				newArr.push(getDates[i].id);
			}
			return newArr;
		},
		//取table制定的值
		getDatasValue:function(v){
			var newArr = [];
			var _self = this;
			var getDates =  _self._datatables.data();
			for(var i=0;i<getDates.length;i++){
				newArr.push(getDates[i][v]);
			}
			return newArr;
		},
		//清空数据
		clearDatas:function(){
			var _self = this;
		    _self._datatables.clear();
			var getTable = $(_self._config.render);
			getTable.children('tbody').empty();
			getTable.parent().children('.pt5').empty();
			getTable.children('tbody').append('<tr class="odd"><td valign="top" colspan="90" class="dataTables_empty">没有数据</td></tr>');
		}
	};
	return Aptech;
});
//function changeForNome(self){
//	var getColor = $(self).css('color');
//	var grey = 'rgb(128, 128, 128)';
//	var red = 'rgb(255,0,0)';
//	if(getColor==grey){
//		$(self).css('color',red);
//		$(self).nextAll().removeClass('hidden');
//	}else {
//		$(self).css('color',grey);
//		$(self).nextAll().addClass('hidden');
//	}
//}