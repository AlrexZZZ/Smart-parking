function onBridgeReady(){
 WeixinJSBridge.call('hideOptionMenu'); //隐藏微信中网页右上角按钮
 WeixinJSBridge.call('hideToolbar'); //隐藏微信中网页底部导航栏
}

if (typeof WeixinJSBridge == "undefined"){
    if( document.addEventListener ){
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    }else if (document.attachEvent){
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
}else{
    onBridgeReady();
}
