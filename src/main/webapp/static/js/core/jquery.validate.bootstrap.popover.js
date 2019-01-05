$.extend($.validator.prototype, {
    showLabel: function(element, message) {
    }
});
$.extend($.validator.defaults, {
    validClass: 'success',
	errorClass: 'error',
	focusInvalid: false,
	onfocusout:false,
    errorElement: 'span',
	ignore: ":hidden:not(select,.combotree,input),[readonly],[autocomplete=off]",
    highlight: function (element, errorClass, validClass) {
        var $element;
        if (element.type === 'radio') {
            $element = this.findByName(element.name);
        } else {
	       	 if($(element).is(".combotree")){
	       		$element = $(element).parent();
	    	 }else if($(element).is(":hidden")){
	    		 if(element.type=='hidden'){
	    			 $element = $(element);
	    		 }else{
	    			 $element = $(element).next();
	    		 }
	    	 }else{
	    		 $element = $(element);
	    	 }
        }
        $element.addClass(errorClass).removeClass(validClass);
        $element.parent("div").addClass("has-error");
    },
    unhighlight: function (element, errorClass, validClass) {
        var $element;
        if (element.type === 'radio') {
            $element = this.findByName(element.name);
        } else {
	       	 if($(element).is(".combotree")){
	       		$element = $(element).parent();
	    	 }else if($(element).is(":hidden")){
	    		 if($(element).attr("type")=='hidden'){
	    			 $element = $(element).parent();
	    		 }else{
	    			 $element = $(element).next();
	    		 }
	    		 $element = $(element).next();
	    	 }else{
	    		 $element = $(element);
	    	 }
        }
        $element.removeClass(errorClass).addClass(validClass);
        $element.parent("div").removeClass("has-error");
    },
    showErrors: function (errorMap, errorList) {
        $.each(this.successList, function (index, value) {
        	 if($(value).is(".combotree")){
        		 $(value).parent().popover('destroy');
        	 }else if($(value).is(":hidden")){
        		 if($(value).attr("type")=='hidden'){
        			 $(value).parent().popover('destroy');
	    		 }else{
	    			 $(value).next().popover('destroy');
	    		 }
        	 }else{
        		 $(value).popover('destroy');
        	 }
        });
        
        $.each(errorList, function (index, value) {
	       	 var errorElement;
	    	 $(value.element).popover('destroy');
	    	 if($(value.element).is(".combotree")){
	    		 errorElement = $(value.element).parent();
	    	 }else if($(value.element).is(":hidden")){
	    		 if($(value.element).attr("type")=='hidden'){
	    			 errorElement = $(value.element).parent();;
	    		 }else{
	    			 errorElement = $(value.element).next();
	    		 }
	    		 errorElement = $(value.element).next();
	    	 }else{
	    		 errorElement = $(value.element);
	    	 }
        	 errorElement.popover('destroy');	    	 
        });
        setTimeout(function () {
	        $.each(errorList, function (index, value) {
	        	 var errorElement;
	        	 $(value.element).popover('destroy');
	        	 if($(value.element).is(".combotree")){
	        		 errorElement = $(value.element).parent();
	        	 }else if($(value.element).is(":hidden")){
	        		 if($(value.element).attr("type")=='hidden'){
		    			 errorElement = $(value.element).parent();
		    		 }else{
		    			 errorElement = $(value.element).next();
		    		 }
	        	 }else{
	        		 errorElement = $(value.element);
	        	 }
	    		 var pop = errorElement.popover({
	    			 container: "body",
	    			 placement: "right",
	    			 content:value.message,
	    			 trigger: 'manual'
	    		 });
	             pop.popover('show');
	    	});
    	}, 500);
        this.defaultShowErrors();
    }
});
