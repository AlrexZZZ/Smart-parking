function isclick(g, j) {
	var x = $(g).offset().top;
	var y = $(g).offset().left;
	var xwin = $("#containout").offset().left;
	var h1 = $(g).height();
	var w1 = $(g).width();
	var w2 = parseInt($("#clickdel").width()) / 2;
	var h = parseInt(h1) + parseInt(x) + 10;
	var w = parseInt(y) - parseInt(xwin) - parseInt(w2) / 2 - 6;

	$("#" + j).css("display", "none");
	$("#clickdel").css({
		"top" : h,
		"left" : w,
		"display" : "block"
	});
};

function mover(d, c, f) {
	$(d).children("img").attr('src', c);
	var x = $(d).offset().top;
	var y = $(d).offset().left;
	var xwin = $("#containout").offset().left;
	var h1 = $(d).height();
	var w1 = $(d).width();
	var h = parseInt(h1) + parseInt(x) + 10;
	var w = parseInt(y) - parseInt(xwin) + 25;
	$("#" + f).css({
		"top" : h,
		"left" : w,
		"display" : "block"
	});
};

function mout(d, e, z) {
	$(d).children("img").attr('src', e);
	$("#" + z).css("display", "none");
};

$(document).ready(function() {
	$("#dantuwen").mouseover(function() {
		$("#dantuwen").attr('src', '/ump/resources/images/wendan2.png');
	});

	$("#dantuwen").mouseout(function() {
		var a = $("#dantuwen").attr('src', '/ump/resources/images/wendan1.png');
	});
	$("#duotuwen").mouseover(function() {
		$("#duotuwen").attr('src', '/ump/resources/images/wenduo2.png');
	});

	$("#duotuwen").mouseout(function() {
		$("#duotuwen").attr('src', '/ump/resources/images/wenduo1.png');
	});

	$("#tw_add").mouseover(function() {

		$("#tw_add_tu").css("display", "none");
		$("#tw_add_tw").css("display", "block");
	});

	$("#tw_add").mouseout(function() {
		$("#tw_add_tu").css("display", "block");
		$("#tw_add_tw").css("display", "none");
	});

	$("#clickdeconde_anniur").click(function() {
		$("#clickdel").css("display", "none");
	});
	
	$("#sure_delete").click(function() {
//		alert($(this).attr("alt"));
		console.log($(this));
//		$.get("abc.html",function(){
//			alert('ddd');
//		});
	});
});
