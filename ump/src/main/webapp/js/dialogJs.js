 function showWindow(e,d){
	 
	       var windowWidth=$(window).width();
	       var windowHeight=$(window).height();
	       var elementWidth=$(d).width();
	       var elementHeight=$(d).height();
	       var eLeft=(parseInt(windowWidth)-parseInt(elementWidth))/2;
	       var eTop=Math.abs((parseInt(windowHeight)-parseInt(elementHeight))/2);
	       $(d).css({"left":eLeft,"top":eTop,}).show();
		$(window).resize(function(){  		  
			  var windowWidth=$(window).width();
	          var windowHeight=$(window).height();
	          var elementWidth=$(d).width();
	          var elementHeight=$(d).height();
	          var eLeft=(parseInt(windowWidth)-parseInt(elementWidth))/2;
	          var eTop=Math.abs((parseInt(windowHeight)-parseInt(elementHeight))/2);
			  if($(d).css("display") == "block"){$(d).css({"left":eLeft,"top":eTop,}).show();}
			  else if($(d).css("display") == "none"){$(d).css({"left":eLeft,"top":eTop,}).hide(); }
		});
}

function showBlack(){
	$("#blackcover").show();
	}
	
function hideWindow(t){
	$(t).hide();
	$("#blackcover").hide();
	}