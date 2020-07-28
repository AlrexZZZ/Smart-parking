package com.nineclient.weixin;

import java.io.IOException;

public interface EventProcessor {
public void subScribeEvent() throws IOException;
public void unsbuscribeEvent() throws IOException;
public void scanEvent() throws IOException;
public void clickEvent() throws IOException;
public void viewEvent()throws IOException;
public void MassSendEvent() throws IOException;
}
