package com.nineclient.cherry.wcc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nineclient.entity.MassSendEntity;
import com.nineclient.model.WccMassMessage;

/**
 * 抽象对象：素材不用重新上传，传递ENTITY对象
 * @author Administrator
 *
 */
public abstract class MessageSendAbstract {
protected MassSendEntity massSendEntity;
protected WccMassMessage massMessage=new WccMassMessage();
protected final Logger log = LoggerFactory.getLogger(MessageSendAbstract.class);

/**
 * 发送文本消息


{
     "content":"Hello World"
}


发送图片消息


{
  "media_id":"MEDIA_ID"
}


发送语音消息


{
  "media_id":"MEDIA_ID"
}


发送视频消息


{
  "media_id":"MEDIA_ID",
  "thumb_media_id":"MEDIA_ID",
  "title":"TITLE",
  "description":"DESCRIPTION"
}


发送音乐消息


{
  "title":"MUSIC_TITLE",
  "description":"MUSIC_DESCRIPTION",
  "musicurl":"MUSIC_URL",
  "hqmusicurl":"HQ_MUSIC_URL",
  "thumb_media_id":"THUMB_MEDIA_ID" 
}


发送图文消息 图文消息条数限制在10条以内，注意，如果图文数超过10，则将会无响应。

{
    "articles": [
     {
         "title":"Happy Day",
         "description":"Is Really A Happy Day",
         "url":"URL",
         "picurl":"PIC_URL"
     },
     {
         "title":"Happy Day",
         "description":"Is Really A Happy Day",
         "url":"URL",
         "picurl":"PIC_URL"
     }
     ]
}


 * @param args
 * @throws Exception
 */
public abstract String sendMessage();


	
}
