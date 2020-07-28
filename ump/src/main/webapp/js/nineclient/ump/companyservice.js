function selectProduct(id,selectChannelId,selectVersionId){
	var url="/ump/umpchannels/loadChannelsByProductId";
	var param={productId:id};
	//加载渠道
	if(selectChannelId)
	dataAjax(url, param, null, null, createSelect, selectChannelId);
	var url="/ump/umpversions/loadVersionByProductId";
	//加载版本
	if(selectVersionId)
	dataAjax(url, param, null, null, createSelect, selectVersionId);
}