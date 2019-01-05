(function($ , undefined) {
	$(document).on("ready", function(){
		/*A.loadPage({
			render : '#page-container',
			url : format_url("/demo/index")
		});	*/
	});
	
	function renderUI(datasource, render){
		//
		showAll(datasource, render);
	};
	
	function showAll(menuList, parent){
        for (var menu in menuList) {
            //如果有子节点，则遍历该子节点
            if (menuList[menu].childrens && menuList[menu].childrens.length > 0) {
            	var childrenA;
            	if(menuList[menu].icon){
            		var iconNode = $('<i class="menu-icon ' + menuList[menu].icon+'" ></i>');
            		if(parent.attr("id") != "sidebar-menu"){
            			iconNode = $('<i class="menu-icon fa fa-caret-right"></i>')
            		}
                	childrenA = $('<a href="#" class="dropdown-toggle"></a>')
                		.append(iconNode)
                		.append('<span class="menu-text">'+menuList[menu].name+'</span>')
                		.append('<b class="arrow fa fa-angle-down"></b>');	
            	}else{
            		var iconNode = $("<i></i>");
            		if(parent.attr("id") != "sidebar-menu"){
            			iconNode = $('<i class="menu-icon fa fa-caret-right"></i>')
            		}
                	childrenA = $('<a href="#" class="dropdown-toggle"></a>')
                		.append(iconNode)
                		.append('<span class="menu-text">'+menuList[menu].name+'</span>')
                		.append('<b class="arrow fa fa-angle-down"></b>');
            	}
            	var childrenB = $('<b class="arrow"></b>');
            	childrenA.attr("data-url", menuList[menu].funUrl);
                var childrenLi = $('<li></li>');
                $(childrenLi).append(childrenA)
                	.append("<ul class='submenu'></ul>")
                	.appendTo(parent);
                showAll(menuList[menu].childrens, $(childrenLi).children().eq(1));
            }else {
            	//如果该节点没有子节点，则直接将该节点li以及文本创建好直接添加到父亲节点中
            	var childrenA;
            	if(menuList[menu].icon){
            		var iconNode = $('<i class="menu-icon ' + menuList[menu].icon+'" ></i>');
            		if(parent.attr("id") != "sidebar-menu"){
            			iconNode = $('<i class="menu-icon fa fa-caret-right"></i>')
            			childrenA = $('<a href="#"></a>').append(iconNode)
            			.append('<span class="menu-text"><i class="submenu-icon '+ menuList[menu].icon +'"></i>'+menuList[menu].name+'</span>');	
            		}else{
            			childrenA = $('<a href="#"></a>').append(iconNode)
            			.append('<span class="menu-text">'+menuList[menu].name+'</span>');	
            		}
            	}else{
            		var iconNode = $('<i></i>');
            		if(parent.attr("id") != "sidebar-menu"){
            			iconNode = $('<i class="menu-icon fa fa-caret-right"></i>')
            		}
                	childrenA = $('<a href="#"></a>').append(iconNode)
                	.append('<span class="menu-text">'+menuList[menu].name+'</span>');
            	}
            	if(menuList[menu].funUrl==null){
            		childrenA.attr("data-url", "/");
            	}else{
            		childrenA.attr("data-url", menuList[menu].funUrl);
            	}
             	childrenA.attr("data-type", menuList[menu].type);
             	if(menuList[menu].type == "1"){
             		childrenA.attr("data-app", menuList[menu].appId);
             	}
               $("<li></li>").append(childrenA).appendTo(parent);
            }
        }
	}
	
	var datasource = [{funUrl: '/mainPage/mainPageInit', icon:'glyphicon glyphicon-home ', name:'首页'},
						{funUrl: '', icon:'fa fa-cog', name:'信息管理', childrens:[
						                                                          {funUrl:'/sysUser/list', icon:'user', name:'业务分类'},
						                                                          {funUrl:'/sysFunction/index', icon:'user', name:'信息集管理'}
						]},
						{funUrl: '', icon:'fa fa-cog', name:'应用管理', childrens:[
						                                                          {funUrl:'/sysUser/list', icon:'user', name:'业务分类'},
						                                                          {funUrl:'/sysFunction/index', icon:'user', name:'信息集管理'}
						]},
						{funUrl: '', icon:'fa fa-cog', name:'流程管理', childrens:[
						                                                          {funUrl:'/sysUser/list', icon:'user', name:'业务分类'},
						                                                          {funUrl:'/sysFunction/index', icon:'user', name:'表单管理'},
						                                                          {funUrl:'/sysMenuitem/index', icon:'user', name:'流程管理'}
						]},
						{funUrl: '', icon:'fa fa-cog', name:'系统管理', childrens:[
						                                                          {funUrl:'/sysUser/list', icon:'user', name:'用户管理'},
						                                                          {funUrl:'/sysFunction/index', icon:'user', name:'功能管理'},
						                                                          {funUrl:'/sysMenuItem/index', icon:'user', name:'菜单管理'},
    						                                                      {funUrl:'/sysUnit/index', icon:'user', name:'组织机构管理'},
						                                                          {funUrl:'/sysCodeItem/list', icon:'user', name:'编码管理'},
						                                                          {funUrl:'/sysRole/list', icon:'user', name:'角色管理'}
						]},
						{funUrl: '', icon:'fa fa-cog', name:'Demo', childrens:[
						                                                          {funUrl:'/demo/index', icon:'user', name:'测试页面'},
						                                                          /*
						                                                          {funUrl:'/ht/edit', icon:'user', name:'WebScada页面'},
						                                                          {funUrl:'/ht/zjxt', icon:'user', name:'主接线图'},
						                                                          {funUrl:'/ht/mxbj', icon:'user', name:'模型编辑器'},
						                                                          {funUrl:'/ht/fjfbt', icon:'user', name:'风机分布图'},
						                                                          {funUrl:'/ht/single', icon:'user', name:'单机剖面图'},
						                                                          {funUrl:'/unity/index', icon:'user', name:'unity 3D页面'},
						                                                          {funUrl:'/report/index', icon:'user', name:'报表测试页面'},    */       
						]}								
					];
	$("#sidebar-menu").empty();
	$.ajax({
		url :  format_url("/sysMenuItem/getMenuByType/1"),
		contentType : 'application/json',
		dataType : 'JSON',
		success : function(result){
			renderUI(result, $("#sidebar-menu"));
			$("#sidebar-menu [data-url]").on("click", function(){
				if(!$(this).hasClass("dropdown-toggle")){
					$("#sidebar-menu .active").removeClass("active");
					$(this).parents("li").addClass("active");
					var menuType = $(this).attr("data-type");
					var url;
					if(menuType == "0"){
						url = $(this).attr("data-url");
					}else{
						var appId = $(this).attr("data-app");
						url = "/template/getApp/" + appId;
					}
					
					if(url.indexOf("ht")>0){
						var iframe = $("<iframe id='pageContent' width='100%' height='820px' frameborder='0' style='overflow:scroll;'></iframe>");
						iframe.attr("src", format_url(url));
						$("#page-container").empty();
						$("#page-container").append(iframe);
					}else{
						cookieTime="0.1";
						A.loadPage({
							render : '#page-container',
							url : format_url(url),
							callback:function(){
								cookieTime="";
							}
						});	
					}
				}
			});
		}
	});
	$("#sidebar-menu").on('click',function(){
		var getChild = $(this).children('li');
		var chileHeight = $(getChild).height()+1;
		var chileNum = $(getChild).length;
		var totleHeight = chileHeight*chileNum;
		var chargeNum = 0;
		$(getChild).each(function(){
			var _self = this;
			if($(_self).hasClass('open')){
				chargeNum++;
				return;
			}
		})
		$("#sidebar").getNiceScroll().resize();
		var sideBarHeight = $("#sidebar").height();
		var shortcutsHeight =  $('#sidebar-shortcuts').height();
		var collapseHeight =  $('#sidebar-collapse').height();
		var sideBarMenuBar = $("#sidebar-menu").height()+shortcutsHeight+collapseHeight;
		if(chargeNum==0){
			if(sideBarHeight<=sideBarMenuBar){
				$("#sidebar").getNiceScroll().show();
			}else{
				$("#sidebar").getNiceScroll().hide();
			}
		}else{
			var getFirstHeight = totleHeight+collapseHeight+shortcutsHeight;
			if(getFirstHeight<=sideBarHeight){
				$("#sidebar").getNiceScroll().hide();
			}else {
				$("#sidebar").getNiceScroll().show();
			}
		}
		
		
		

	})
	$("#sidebar-collapse").on('click',function(){
		var width=$("#sidebar-collapse").width();
		if(width>100){
			$("#ascrail2000").hide();
		}else{
			$("#ascrail2000").show();
		}
	})
})(jQuery);