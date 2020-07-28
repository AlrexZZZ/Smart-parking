package com.nineclient.weixin;

import java.io.IOException;

public interface MsgTypeProcessor {

/**
 * 处理地理位置
 */
 public  void locationPro() throws IOException;
 /**
  * 处理事件
  */
 public void eventPro() throws IOException;
 /**
  * 处理文本
  */
 public void textPro() throws IOException;
 /**
  * 处理图片
  */
 public void imagePro()throws IOException;
 /**
  * 处理语音
  */
 public void voicePro()throws IOException;
 /**
  * 处理视频
  */
 public void videoPro()throws IOException;
 /**
  * 处理link
  */
 public void linkPro()throws IOException;
}
