	function throwError(e) {
		var content = '[message]: '
	        + e.message
	        + '\n\n[name]: '
	        + e.name,
	        line = e.line || e.lineNumber,
	        data = e.source,
	        source = data.split(/\n/),
	        startLine, endLine,
	        // 可以看见错误代码附近多少行
	        veiwLineNum = 8;
        if (line) {
	        content += '\n\n[line]: '
	        + line;
	        content += '\n\n[source]: ';
	        startLine = (line - veiwLineNum) || 0;
	        endLine = (line + veiwLineNum) < source.length? (line + veiwLineNum): source.length;
	        if(startLine > 0) {
	        	content += '\n...';
	        }
	        for (var i = startLine; i < endLine; i++) {
	        	content += '\n' + (i + 1) + source[i];
	        };
	        if(endLine < source.length) {
	        	content += '\n...';
	        }
	    }
		console.error(content);
	}

	Aptech.loadPage = function(cfg){
    	$('.popover').remove();
		try{
			$(cfg.render).load(cfg.url, cfg.data, function(response,status,xhr){
				if(status == "error"){
					$(cfg.render).empty();
					$(cfg.render).append(response);
				}
				if(cfg.callback){
					cfg.callback(response,status,xhr);	
				}
			});	
		}catch(e){
			throwError(e);
		}
	}

$.fn.serializeObject = function()    
{
   var o = {};    
   var a = this.serializeArray();
   $.each(a, function() {    
       if (o[this.name]) {    
           if (!o[this.name].push) {    
               o[this.name] = [o[this.name]];    
           }    
           o[this.name].push(this.value || null);    
       } else {    
           o[this.name] = this.value || null;    
       }    
   });    
   return o;    
}; 

jQuery.fn.extend( {
	customSerialize: function() {
		return jQuery.param( this.serializeArray() );
	},
	customSerializeArray: function() {
		return this.map( function() {

			// Can add propHook for "elements" to filter or add form elements
			var elements = jQuery.prop( this, "elements" );
			return elements ? jQuery.makeArray( elements ) : this;
		} )
		.filter( function() {
			var type = this.type;
			// Use .is( ":disabled" ) so that fieldset[disabled] works
			return this.name && !jQuery( this ).is( ":disabled" ) &&
				rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type ) &&
				( this.checked || !rcheckableType.test( type ) );
		} )
		.map( function( i, elem ) {
			var val = jQuery( this ).val();
			return val == null ?
				{ name: elem.name, value: '' } :
				jQuery.isArray( val ) ?
						val.length > 1 ?
							{ name: elem.name, value: val.join(",").replace( rCRLF, "\r\n" ) } :
							jQuery.map( val, function( val ) {
								return { name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
							} ) :	
					{ name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
		} ).get();
	}
} );
var r20 = /%20/g,
rbracket = /\[\]$/,
rCRLF = /\r?\n/g,
rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
rsubmittable = /^(?:input|select|textarea|keygen)/i;
var rcheckableType = ( /^(?:checkbox|radio)$/i );

var rtagName = ( /<([\w:-]+)/ );

var rscriptType = ( /^$|\/(?:java|ecma)script/i );
$.fn.arraySerializeObject = function()    
{
   var reserveArr = [];//储存数组
   var a = this.customSerializeArray();
   var o = {};
   $.each(a, function(index) {    
	   if (typeof(o[this.name])!="undefined") {    
    	   reserveArr.push(o);
    	   o={};
    	   o[this.name] = this.value || null;  
       } else {    
           o[this.name] = this.value || null;    
       }
       if(index == a.length-1){
    	   reserveArr.push(o);
       }
   });
   return reserveArr;
};  

function format_url(url, params){
	if(!url){
		return '';
	}
	var param;
	if(url.indexOf('?') > 0){
		param = "&";
	} else {
		param = "?";
	}
	if(params && params!=typeof(undefined)) {
		params = params || {};
		$.each(params, function(key, value){
			param += "&" + key + "=" + value;
		});
		param = "&uid=" + Aptech.UID;
	} else {
		param += 'uid=' + Aptech.UID;
	}
	return Aptech.CONTEXT_ROOT_PATH + url + param;
};
$( document ).ajaxSuccess(function( event, xhr, settings, data) {
	var getArr = xhr.responseText.split('<!DOCTYPE html>');
	if(getArr.length<2){
		var responseJson = JSON.parse(xhr.responseText);
		if(responseJson&&responseJson.result == "redirect"){
			window.location.href = format_url("/login/login");
		}
	}
});
/*
$.validator.setDefaults({
	errorElement: 'div',
	errorClass: 'help-block',
	focusInvalid: false,
	ignore: "[readonly],[autocomplete=off]",
	/*
	highlight: function (e) {
		$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
	},
	
	success: function (e) {
		$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
		$(e).remove();
	},*/
	/*
	errorPlacement: function (error, element) {
		
		if(element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
			var controls = element.closest('div[class*="col-"]');
			if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
			else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
		}
		else if(element.is('.select2')) {
			error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
		}
		else if(element.is('.chosen-select')) {
			error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
		}else {
			error.insertAfter(element);	
		}
	},
}); */

function formatCamelName(name, prefix, suffix){
	name = name.replace(/([A-Z])/g,"_$1").toUpperCase();
	if(prefix){
		name = prefix + "_" + name;
	}
	if(suffix){
		name = name + "_" + suffix;
	}
	return name;
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
	 var o = {
	     "M+": this.getMonth() + 1, //月份 
	     "d+": this.getDate(), //日 
	     "h+": this.getHours(), //小时 
	     "m+": this.getMinutes(), //分 
	     "s+": this.getSeconds(), //秒 
	     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	     "S": this.getMilliseconds() //毫秒 
	 };
	 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	 for (var k in o)
	 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	 return fmt;
}

window.alert = function(title, content){
	var unique_id = $.gritter.add({
		title: title,
		text: content,
		sticky: true,
		time: '1000',
		class_name: 'gritter-warning gritter-light'
	});
	setTimeout(function(){
		$.gritter.remove(unique_id, {
			fade: true,
			speed: 'slow'
		});
	}, 10000);
}
function birtPrint(reports,id){
  var host = window.location.host;
  var hostStartPos = host.indexOf("10");
  var url= "";
  if(hostStartPos == 0){
    //外网访问
    var url="http://10.222.75.201:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&id="+id;
  }else{
    //内网访问172.19.220.2
   var url="http://172.19.220.2:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&id="+id;
  }
  open(url);
	
	
}
function birtPrintSafe(reports,id,type){
	var host = window.location.host;
  var hostStartPos = host.indexOf("10");
  var url= "";
  if(hostStartPos == 0){
    //外网访问
	var url="http://10.222.75.201:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&id="+id+"&type="+type;
  }else{
    //内网访问172.19.220.2
	var url="http://172.19.220.2:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&id="+id+"&type="+type;
  }
	open(url);
}
function birtPrintThree(reports,time,nextTime,unitId){
	var host = window.location.host;
  var hostStartPos = host.indexOf("10");
  var url= "";
  if(hostStartPos == 0){
    //外网访问
	var url="http://10.222.75.201:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&time="+time+"&nextTime="+nextTime+"&unitId="+unitId;
  }else{
    //内网访问172.19.220.2
	var url="http://172.19.220.2:8088/birtWeb/frameset?__report=gyscyy/"+reports+"&__format=HTML&time="+time+"&nextTime="+nextTime+"&unitId="+unitId;
  }
	open(url);
}

jQuery.validator.addMethod("numberAndLettersVal",function(value,element){  
    return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);  
   },$.validator.format("请输入字母或数字")  
);

function exportExcels(url,conditions){
	var form=$("<form>");//定义一个form表单
	 form.attr("method","post");
	 form.attr("action",url);
	 var input=$("<input>");
	 input.attr("type","hidden");
	 input.attr("name","conditions");
	 input.attr("value",conditions);
	 $("body").append(form);//将表单放置在web中
	 form.append(input);
	 form.submit();//表单提交 ;
}