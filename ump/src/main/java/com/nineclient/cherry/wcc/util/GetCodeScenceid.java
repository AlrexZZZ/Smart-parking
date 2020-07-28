package com.nineclient.cherry.wcc.util;

import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.model.WccPlatformUser;

public class GetCodeScenceid {
public static int getScenceId(WccPlatformUser wccPlatformUser){
	// 生成sceneid
	int sceneid = 0;
	try {
		sceneid = WccQrCode.getMaxSceneid(wccPlatformUser);
	} catch (Exception e) {
		sceneid = 1;
	}

	if (sceneid == 0 || sceneid < 1) {
		sceneid = 1;
	} else {
		sceneid++;
	}
	return sceneid;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
