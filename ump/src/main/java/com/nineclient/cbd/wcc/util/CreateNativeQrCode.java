package com.nineclient.cbd.wcc.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class CreateNativeQrCode {
public static String getQrCodePath(String filePath,String url){
	
	String qrCodeName = null;
	try {
        
	    // String content = "http://weixin.qq.com/q/9kly_ezlxVW88bQPJGFn";
	    //   String content = "http://weixin.qq.com/q/50kCaD3lt1XOoKWcVGFn";
	     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	     Map hints = new HashMap();
	     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	     BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 430, 430,hints);
	     File file = new File(filePath);
	      
	     if(!file .exists()  && !file .isDirectory())      
	     {       
	         file .mkdirs();    
	     }
	     String codeName = System.currentTimeMillis()+"";
	     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", new File(filePath, codeName+".jpg"));
	      qrCodeName = codeName+".jpg";
	 } catch (Exception e) {
	     e.printStackTrace();
	 }
	
	return qrCodeName;
}
	public static void main(String[] args) {
		
    System.out.println(getQrCodePath("d:/at", "http://weixin.qq.com/q/9kly_ezlxVW88bQPJGFn"));

	}

}
