define(function(require, exports, module){
	require("../../css/themes/default/bootstrap-datetimepicker.min.css");
	require("../thirdparty/my97datepicker/WdatePicker");

	Aptech.my97datepicker = function(config){
		var chargeDisabled = false;
		if(config.isDisabled){
			chargeDisabled = true;
		}
		var _options = {
				isShowWeek : false,
				isReadOnly : false,
				isDisabled : chargeDisabled,
				skin : 'ext',
				maxDate: "",
				minDate: "",
				dateFmt: "yyyy-MM-dd"
		}
		this._config = config;
		
		if(this._config.render){
			this._config.container = $(config.render);
		}
		this._config.name = config.name || "";
		$.extend(true, _options, config.options);
		this._options = _options;
	}
	
	Aptech.my97datepicker.prototype={
		render : function(){
			var _self = this;
			var node = _self._config.container;
			node.empty();
			var divNode = $('<div class="input-group date"></div>');
			var inputNode;
			var maxDate = _self._options.maxDate? ', maxDate:\''+_self._options.maxDate + '\'' : "";
			var minDate = _self._options.minDate ? ', minDate:\''+_self._options.minDate + '\'' : "" ;
			if(this._options.isDisabled){
				if(this._options.isShowWeek){
	                inputNode = $('<input id="'+_self._config.id+'" disabled name="'+_self._config.name+'" class="form-control" size="16" onfocus="WdatePicker({dateFmt: \'' + _self._options.dateFmt +'\', errDealMode:2, isShowWeek: true, skin:\'ext\', onpicked: function(){$dp.$('+_self._config.name +').value=$dp.cal.getP(\'y\')+$dp.cal.getP(\'W\',\'WW\');}'+maxDate + minDate +'},this.blur())" type="text" value="" style="background-color: #fff !important;height: 30px;cursor: pointer">');
	            }else{
	                inputNode = $('<input id="'+ _self._config.id +'" disabled name="'+_self._config.name+'" class="form-control" size="16" onfocus="WdatePicker({dateFmt:\'' + _self._options.dateFmt +'\', isShowWeek: false, onpicked:function(){$(\'' + _self._config.render + ' input[name]\').val($dp.cal.getNewDateStr());}, skin:\''+ this._options.skin +'\'' + maxDate + minDate + '},this.blur())" type="text" value="" style="background-color: #fff !important;height: 30px;cursor: pointer">');
	            }
			}else{
				if(this._options.isShowWeek){
	                inputNode = $('<input id="'+_self._config.id+'" name="'+_self._config.name+'" class="form-control" size="16" onfocus="WdatePicker({dateFmt: \'' + _self._options.dateFmt +'\', errDealMode:2, isShowWeek: true, skin:\'ext\', onpicked: function(){$dp.$('+_self._config.name +').value=$dp.cal.getP(\'y\')+$dp.cal.getP(\'W\',\'WW\');}'+maxDate + minDate +'},this.blur())" type="text" value="" style="background-color: #fff !important;height: 30px;cursor: pointer">');
	            }else{
	                inputNode = $('<input id="'+ _self._config.id +'" name="'+_self._config.name+'" class="form-control" size="16" onfocus="WdatePicker({dateFmt:\'' + _self._options.dateFmt +'\', isShowWeek: false, onpicked:function(){$(\'' + _self._config.render + ' input[name]\').val($dp.cal.getNewDateStr());}, skin:\''+ this._options.skin +'\'' + maxDate + minDate + '},this.blur())" type="text" value="" style="background-color: #fff !important;height: 30px;cursor: pointer">');
	            }
			}
//			var inputValueNode = $("<input id='"+_self._config.name + "' name='"+ _self._config.name +"' type='hidden'>")
		    var iconNode =$('<span class="input-group-addon" style="width:1%;min-width:30px;max-width:30px;position: relative;z-index: 2;"><span class="glyphicon glyphicon-calendar"></span></span>');
			this._my97datepicker = inputNode;
			divNode.append(inputNode);
			divNode.append(iconNode);
//			divNode.append(inputValueNode);
		    node.append(divNode);
		    $("#page-container").scroll(function(){
		    	$dp.hide();
	    	});
		    return _self;
		},
		setValue: function(value){
			var strDate = value;
			if(value){
				var arrayValue = strDate.split(' ');
				var displayValueTime = value;
				var chargeIndex = value.indexOf('-');
				var _self = this;
				var displayValue = '';
				//非英文时间格式
				if(chargeIndex<0&&arrayValue.length!=6){
					var getTimeCharge = _self._options.dateFmt;//获取传入日期的格式
		    		var	itemOtherDate = value;
		    		var indexDate = getTimeCharge;//临时存放日期格式
		    		var itemDate = '';//储存被分割后的日期
		    		var indexArray = [];//储存分割出来每一项的长度
		    		var setDateArray = [];//存储遍历出来的日期
	    			var getIndex = indexDate.indexOf('-');//获取临时存放日期字符串的位置
	    			if(getIndex>=0){//如果传入的日期格式含有-，传入的值没有-
	    				var itemArr = indexDate.split('-');//将传入的日期格式转换成数组，获取每项的长度，根据返回的长度对传入的日期进行分割
	    				for(var i=0;i<itemArr.length;i++){
	    					indexArray.push(itemArr[i].length);
	    				}
	    				for(var i=0;i<indexArray.length;i++){
	    					itemDate = itemOtherDate.substring(0,indexArray[i]);
	    					itemOtherDate = itemOtherDate.substring(indexArray[i]);
	    					setDateArray.push(itemDate);
	        			}
	    				displayValue=setDateArray.join('-');
	    			}else{
	    				displayValue = value;
	    			}
				}else if(chargeIndex<0&&arrayValue.length==6){//英文时间模式
                    var dateStr=strDate.split(" ");
				    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";
				    var date = new Date(Date.parse(strGMT));
				    var y = date.getFullYear();  
				    var m = date.getMonth() + 1;
				    m = m < 10 ? ('0' + m) : m;
				    var d = date.getDate();
				    d = d < 10 ? ('0' + d) : d;
				    var h = date.getHours();
				    h = h<10?('0'+h):h;
				    var minute = date.getMinutes();
				    minute = minute < 10 ? ('0' + minute) : minute;
				    var second = date.getSeconds();
				    second = second < 10 ? ('0' + second) : second;
				    if(h=='00'&&minute=='00'&&second=='00'){
				    	h='';minute='';second='';
				    }else{
				    	h=h;
				    	minute=minute;
				    	second=second;
				    }
				    m=m=='00'?'':m;
				    d=d=='00'?'':d;
                    //通过_options.dateFmt增加一个时分秒得判断
                    var getTimeCharge = _self._options.dateFmt;
                    if(getTimeCharge.indexOf('yyyy')>=0){
                    	getTimeCharge = getTimeCharge.replace('yyyy',y);
                    }
                    if(getTimeCharge.indexOf('MM')>=0){
                    	getTimeCharge = getTimeCharge.replace('MM',m);
                    }
                    if(getTimeCharge.indexOf('dd')>=0){
                    	getTimeCharge = getTimeCharge.replace('dd',d);
                    }
                    if(getTimeCharge.indexOf('HH')>=0){
                    	getTimeCharge = getTimeCharge.replace('HH',h);
                    }
                    if(getTimeCharge.indexOf('mm')>=0){
                    	getTimeCharge = getTimeCharge.replace('mm',minute);
                    }
                    if(getTimeCharge.indexOf('ss')>=0){
                    	getTimeCharge = getTimeCharge.replace('ss',second);
                    }
                    displayValue = getTimeCharge;
				}else if(chargeIndex>=0){
					displayValue = value;
				}
				 _self._config.container.find("input").val(displayValue);
			}
		},

		getValue : function(){
			//
			var _self = this;
			if(this._options.isShowWeek){
				return $("#" + _self._config.name).val();
			}
			return _self._config.container.find("input").val();
		},
		
		setMonthValue:function(date){
			var _self = this;
			var get_date = new Date(Date.parse(date));
			var y = get_date.getFullYear();  
		    var m = get_date.getMonth() + 1;
		    m = m < 10 ? ('0' + m) : m;
		    var finalValue = y+'-'+m;
		    _self._config.container.find("input").val(finalValue);
		}
	}
	
	return Aptech;
});