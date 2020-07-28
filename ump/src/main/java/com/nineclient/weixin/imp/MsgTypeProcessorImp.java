package com.nineclient.weixin.imp;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;

import com.nineclient.cherry.wcc.model.WccQrCode;
import com.nineclient.constant.WccAutokbsIsReview;
import com.nineclient.constant.WccAutokbsMatchWay;
import com.nineclient.constant.WccAutokbsReplyType;
import com.nineclient.constant.WccAutokbsShowWay;
import com.nineclient.constant.WccFriendFormType;
import com.nineclient.constant.WccWelcomkbsReplyType;
import com.nineclient.constant.WccWelcomkbsType;
import com.nineclient.dto.WccAutoKbsDto;
import com.nineclient.dto.WccUccOut;
import com.nineclient.model.UmpChannel;
import com.nineclient.model.UmpCompanyService;
import com.nineclient.model.WccAutokbs;
import com.nineclient.model.WccFriend;
import com.nineclient.model.WccMaterials;
import com.nineclient.model.WccPlatformUser;
import com.nineclient.model.WccRecordMsg;
import com.nineclient.model.WccStore;
import com.nineclient.model.WccWelcomkbs;
import com.nineclient.model.wccreport.ReportAutokbs;
import com.nineclient.utils.Global;
import com.nineclient.web.WccWxCommController;
import com.nineclient.weixin.EventProcessor;
import com.nineclient.weixin.MsgTypeProcessor;

public class MsgTypeProcessorImp extends WeixinBasicParam implements MsgTypeProcessor {
	private final Logger log = LoggerFactory.getLogger(MsgTypeProcessorImp.class);
	/**
	 * 
	 * @param request
	 * @param response
	 * @param wccPlatformUser
	 * @param inMessage
	 * @param wxMpService
	 * @param wxMpServices
	 */
	public MsgTypeProcessorImp(	HttpServletRequest request,
	 HttpServletResponse response,
	 WccPlatformUser wccPlatformUser,
	 WxMpXmlMessage inMessage ,
	 WxMpService wxMpService,
	 WxMpService wxMpServices)
	{
		super.setRequest(request);
		super.setResponse(response);
		super.setWccPlatformUser(wccPlatformUser);
		super.setWxMpService(wxMpService);
		super.setWxMpServices(wxMpServices);
		super.setInMessage(inMessage);

	}
	@Override
	public void locationPro() throws IOException {
		// 发送地理位置
		WxMpXmlOutNewsMessage.Item defaultItem = new WxMpXmlOutNewsMessage.Item();
		String imageUrl = Global.Url+ "/ump/images/storedefault.png";
		defaultItem.setPicUrl(imageUrl);
		defaultItem.setTitle("你附近门店");
		WxMpXmlOutNewsMessage.Item item1 = new WxMpXmlOutNewsMessage.Item();
		WxMpXmlOutNewsMessage.Item item2 = new WxMpXmlOutNewsMessage.Item();
		WxMpXmlOutNewsMessage.Item item3 = new WxMpXmlOutNewsMessage.Item();
		double lngx = inMessage.getLocationX();
		double laty = inMessage.getLocationY();
		double distance = 0d;
		List<WccStore> stores = null;
		List<WccStore> dto = new ArrayList<WccStore>();
		try {
			stores = WccStore.findAllWccStoresByStatus(wccPlatformUser.getId());
			/**
			 * 如果门店小于等于三个就直接查出来
			 */
			if (null == stores) {
				String content = "你附近没有门店。";
				WxMpXmlOutTextMessage m = WxMpXmlOutTextMessage
						.TEXT().content(content)
						.fromUser(inMessage.getToUserName())
						.toUser(inMessage.getFromUserName())
						.build();
				response.getWriter().write(m.toXml());
			} else if (stores.size() > 0 && stores.size() <= 3) {// 该公众号只有三个门店就直接取出来，不做其他判断
				for (WccStore wccStore : stores) {
					String big = wccStore.getStoreBigImageUrl();
					String smail = wccStore.getStoreSmallImageUrl();

					String image = big != null ? big
							: (smail != null ? smail : "");
					if ("".equals(image) || null == image) {// 添加门店没有上传图片调用默认图片
						image = "/ump/images/stroesmaldefault.png";
						wccStore.setStoreSmallImageUrl(image);
						wccStore.setStoreBigImageUrl(image);
					}
					dto.add(wccStore);// 取距离最小的前三个门店
				}
			} else {
				log.info("dtos.size()" + stores.size());
				Object[][] obj = new Object[stores.size()][2];
				Object[][] objt = new Object[stores.size()][2];
				Double[] dou = new Double[stores.size()];
				int i = 0;
				double lng1 = 0.0;
				double lat1 = 0.0;
				double lng2 = 0.0;
				double lat2 = 0.0;
				double temp = 0.0;
				for (WccStore store : stores) {
					lng1 = Double.parseDouble(store.getStoreLngx());// 门店经度
					lat1 = Double.parseDouble(store.getStoreLaty());// 门店纬度
					lng2 = lngx;// Double.parseDouble(lngx);
					lat2 = laty;// Double.parseDouble(laty);
					temp = GetDistance(lng1, lat1, lng2, lat2);// 得到门店与用户之间的距离
					dou[i] = temp;
					obj[i][0] = store.getId();
					obj[i][1] = temp;
					i++;
				}
				Arrays.sort(dou);// 门店距离排序
				for (int k = 0; k < dou.length; k++) {
					for (int j = 0; j < obj.length; j++) {
						if (obj[j][1].toString().equals(
								dou[k].toString())) {
							objt[k][0] = obj[j][0];
						}
					}
				}
				List<Object> str = new ArrayList<Object>();
				str.add(objt[0][0]);// 取距离最小的前三个
				str.add(objt[1][0]);
				str.add(objt[2][0]);

				WccStore store1 = new WccStore();
				WccStore store2 = new WccStore();
				WccStore store3 = new WccStore();

				for (WccStore store : stores) {
					if (store.getId().toString()
							.equals(str.get(0).toString().trim())) {
						store1 = store;
					}
					if (store.getId().toString()
							.equals(str.get(1).toString().trim())) {
						store2 = store;
					}
					if (store.getId().toString()
							.equals(str.get(2).toString().trim())) {
						store3 = store;
					}
				}

				dto.add(store1);// 取距离最小的前三个门店
				dto.add(store2);
				dto.add(store3);
			}
		} catch (Exception e) {
			log.info("获取地理位置==>");
			e.printStackTrace();
		}

		if (dto != null && dto.size() > 0) {
			// 发送门店位置=
			int i = 0;
			String distan = "";
			String name = "";
			String url = "";
			String storeLngx = "";
			String storeLaty = "";
			String storePhone = "";
			String storeAddress = "";
			StringBuffer string = new StringBuffer();
			String big = "";
			String smail = "";
			String image = "";
			for (WccStore doc : dto) {
				distance = GetShortDistance(lngx, laty,
						Double.parseDouble(doc.getStoreLngx()
								.trim()), Double.parseDouble(doc
								.getStoreLaty().trim()));
				java.text.DecimalFormat df = new java.text.DecimalFormat(
						"#.00");
				if (distance < 1000) {
					distan = df.format(distance);
					distan = distan + "米";
				} else {
					distan = df.format(distance / 1000);
					distan = distan + "公里";
				}
				name = doc.getStoreName();
				url = doc.getStoreUrl();
				storeLngx = doc.getStoreLngx() != null ? doc
						.getStoreLngx() : "";
				storeLaty = doc.getStoreLaty() != null ? doc
						.getStoreLaty() : "";
				storePhone = doc.getStorePhone() != null ? doc
						.getStorePhone() : "";
				storeAddress = doc.getStoreAddres() != null ? doc
						.getStoreAddres() : "";
				string.append("地理位置坐标：经度为").append(storeLngx)
						.append(",纬度为").append(storeLaty)
						.append(" 门店电话：").append(storePhone)
						.append(" 门店地址：").append(storeAddress)
						.append(" 门店URL:").append(url);
				big = doc.getStoreBigImageUrl();
				smail = doc.getStoreSmallImageUrl();
				image = big != null ? big : (smail != null ? smail
						: "");
				if ("".equals(image) || null == image) {
					image = "/ump/images/stroesmaldefault.png";
				}

				i++;
				if (i == 1) {
					item1.setTitle(name);
					item1.setPicUrl(Global.Url + image);
					item1.setDescription(string.toString());
					item1.setUrl(Global.Url
							+ "/ump/wxController/showStore?id="
							+ doc.getId());
				}
				if (i == 2) {
					item2.setTitle(name);
					item2.setPicUrl(Global.Url + image);
					item2.setDescription(string.toString());
					item2.setUrl(Global.Url
							+ "/ump/wxController/showStore?id="
							+ doc.getId());
				}
				if (i == 3) {
					item3.setTitle(name);
					item3.setPicUrl(Global.Url + image);
					item3.setDescription(string.toString());
					item3.setUrl(Global.Url
							+ "/ump/wxController/showStore?id="
							+ doc.getId());
				}
			}

			NewsBuilder o = WxMpXmlOutMessage.NEWS();
			o.addArticle(defaultItem);
			o.addArticle(item1);
			o.fromUser(inMessage.getToUserName());
			o.toUser(inMessage.getFromUserName());
			if (null != item2.getTitle()) {
				o.addArticle(item2);
			}
			if (null != item3.getTitle()) {
				o.addArticle(item3);
			}
			WxMpXmlOutNewsMessage m = o.build();
			response.getWriter().write(m.toXml());
		} else {
			String content = "你附近没有门店。";
			WxMpXmlOutTextMessage m = WxMpXmlOutTextMessage.TEXT()
					.content(content)
					.fromUser(inMessage.getToUserName())
					.toUser(inMessage.getFromUserName()).build();
			response.getWriter().write(m.toXml());
		}

	

	}

	@Override
	public void eventPro() throws IOException {
		// 事件
		String event = inMessage.getEvent();
		String key = inMessage.getEventKey();// 二维码的场景值ID
		EventProcessor eventProcessor=new EventProcessorImp(event,key,this);
		if ("subscribe".equals(event)) 
		{
			eventProcessor.subScribeEvent();
		}
		else if ("unsubscribe".equals(event)) 
		{
			eventProcessor.unsbuscribeEvent();
		}
		else if ("SCAN".equals(event))
		{
			eventProcessor.scanEvent();
		}
		else	if ("CLICK".equals(event)) 
		{
			eventProcessor.clickEvent();
		}
		else if ("VIEW".equals(event))
		{
			eventProcessor.viewEvent();
		}
		else if ("MASSSENDJOBFINISH".equals(event))
		{
			eventProcessor.MassSendEvent();
		}
		else
		{
			
		}

	}

	@Override
	public void textPro() throws IOException {
		// 文本消息
		// 自动回复开始
		String openId = inMessage.getFromUserName();//openId
		WccFriend friend = null;
		friend = WccFriend.findWccFriendByOpenId(openId);
		friend.setMessageEndTime(new Date());
		friend.merge();
		WccRecordMsg record = new WccRecordMsg();
		inMessage.getContent();
		int count = 1;
		if (logMap.get(openId + "_count") == null) {
			logMap.put(openId + "_count", 1);
		} else {
			count = (Integer) logMap.get(openId + "_count");
		}
		// 记录消息
		if (inMessage.getContent() != null) {
			if (friend != null) {
				record.setOpenId(friend.getOpenId());
				record.setNickName(friend.getNickName());
				record.setSex(friend.getSex());
				record.setPlatFormAccount(friend.getPlatformUser()==null?"":friend.getPlatformUser().getAccount());
				record.setProvince(friend.getProvince() == null ? "": friend.getProvince());
				record.setPlatFormId(friend.getPlatformUser()==null?"":friend.getPlatformUser().getId().toString());
				record.setCompanyId(wccPlatformUser==null?0l:wccPlatformUser.getCompany().getId());
				record.setRecordFriendId(friend.getId());// 标识该消息是属于哪个粉丝的
				record.setFriendGroup(String.valueOf(friend.getWgroup()==null?0:friend.getWgroup().getId()));// 分组Id
				record.setRecordContent(inMessage.getContent());// 粉丝发送的消息
				record.setInsertTime(new Date());
			}
			// 获取内容
			String content = inMessage.getContent();	
			if(null != openIdMap.get(openId)){
				String msg = WccWxCommController.sendMsgToUcc(openId,inMessage.getContent());
				if(!"0".equals(uccError.getErrorCode())){
					openIdMap.remove(openId);
					timeMap.remove(openId);
					try {
						wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccError.getErrorMsg()).build());
					} catch (WxErrorException e) {
						e.printStackTrace();
					}
					record.persist();
				}
			}
			// 判断是否为回复问题编号
		   if (null != logMap.get(openId + "_log_" + content)) {
				if (logMap.get(openId + "_log_" + content).equals("KF")) {
					openIdMap.put(openId, "1");//多客服
					String companyCode = wccPlatformUser.getCompany().getCompanyCode();//公司code
					List<UmpCompanyService> companys = UmpCompanyService.findUmpCompanyServiceByCode(companyCode);//公司
					for (UmpCompanyService umpCompanyService : companys) {//公司服务
						Set<UmpChannel> channels = umpCompanyService.getChannels();
						for (UmpChannel umpChannel : channels) {//渠道
							if("全渠道智慧互动".equals(umpChannel.getChannelName())){
								openIdMap.put(openId, "2");//UCC
								break;
							}
						}
					}
				}
				if(StringUtils.isNotEmpty(openIdMap.get(openId))){//多客服	
					ManulConnect(openId);
				}
				else {
					// 根据客户选择问题进行回复
					WccAutoKbsDto autos = (WccAutoKbsDto) logMap.get(openId + "_log_" + content);
					WccAutokbs auto = WccAutokbs.findWccAutokbs(autos.getId());
					count=AutoKbsPocessor(auto, friend, record, count, openId);
					
				}
				return;
			}
			List<WccAutokbs> replySet = new ArrayList<WccAutokbs>();
			// 获取自动回复列表
			List<WccAutokbs> autokbses = WccAutokbs	.findWccAutokbsesByActiveAndIsReview(wccPlatformUser.getId());
			int partLike =0;
			String likeStr = "";
			for (WccAutokbs autokbs : autokbses) {
				// 判断匹配类型是否为全部匹配
				if (autokbs.getMatchWay().equals(
						WccAutokbsMatchWay.全部匹配)) {
					// 关键词根据，拆串
					String[] autoKeyGroup = autokbs.getKeyWord().split(",");
					for (String autoKey : autoKeyGroup) {
						// 如果内容与关键字相同
						if (content.equals(autoKey)) {
							replySet.add(autokbs);
							//自动回复统计 保存完全匹配关键词次数
							continue;
						}
					}
				} else {
					// 关键词根据，拆串
					String[] autoKeyGroup = autokbs.getKeyWord()
							.split(",");
					for (String autoKey : autoKeyGroup) {
						if (content.indexOf(autoKey) != -1) {
							replySet.add(autokbs);
							//自动回复统计 保存模糊匹配关键词次数
							partLike++;
							//记录模糊匹配关键词
							if(partLike==1){
								likeStr = autoKey;//如果只有一个模糊匹配 保存模糊匹配
							}
							else {
								likeStr=content;//其他情况都存用户发送关键词，如 完全匹配，多个模糊匹配，查询不到关键词
							}
							continue;
						}
					}
				}
			}
	      
			ReportAutokbs rauto = new ReportAutokbs();
			rauto.setInsertTime(new Date());
			rauto.setKeyWord(likeStr);
			rauto.setPlatformUser(wccPlatformUser.getId());
			rauto.persist();
			// 查询后回复set
			if (replySet.size() == 0) {
				StringBuffer returnStr = new StringBuffer();
				String platformid = inMessage.getToUserName();
				List<WccWelcomkbs> wList = WccWelcomkbs	.findWccWelcomkbsesByPlatformUserAndTypeAndActive(
								wccPlatformUser.getId(),
								WccWelcomkbsType.ROBOT.ordinal());
				if (wList.size() > 0) {
					WccWelcomkbs robo = wList.get(0);
					String robotStr = robo.getContent();
					if (robotStr != "" & robotStr != null) {
						returnStr.append(robotStr + "\n");
					}
				}
				List<WccAutokbs> defaultList = WccAutokbs
						.findWccAutokbsesByShowway(
								WccAutokbsShowWay.默认回复.ordinal(),
								wccPlatformUser.getId());
				if (defaultList.size() > 0) {
					for (WccAutokbs auto : defaultList) {

						returnStr.append("[" + count + "]"
								+ auto.getTitle() + "\n");
						WccAutoKbsDto dto=new WccAutoKbsDto();
						try {
							BeanUtils.copyProperties(dto, auto);
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						logMap.put(openId + "_log_" + count,dto);
						count++;
						logMap.put(openId + "_count", count);
					}
				}
				System.out.println(wList.size());
				if (wList.size() > 0) {
					WccWelcomkbs robo = wList.get(0);
					System.out.println(robo.getIsCustomer());
					if(robo.getIsCustomer()){
						returnStr.append("[" + count + "]" + "人工客服");
						logMap.put(openId + "_log_" + count, "KF");
						count++;
						logMap.put(openId + "_count", count);
					}
				}
				if(returnStr.toString().endsWith("\n")){
					returnStr.delete(returnStr.length()-1, returnStr.length());
				}
				// 自动发出机器人回复语
				try {
					wxMpService.customMessageSend(WxMpCustomMessage
							.TEXT()
							.toUser(inMessage.getFromUserName())
							.content(returnStr.toString()).build());

				} catch (WxErrorException e) {
					e.printStackTrace();
				}
				String totalMsg = returnStr.toString();
				record.setToUserInsertTime(new Date());
				record.setToUserRecord(totalMsg);
				record.setMsgFrom(0);
				record.persist();
				return;
			} else if (replySet.size() == 1) {
				for (WccAutokbs auto : replySet) {
					count=AutoKbsPocessor(auto, friend, record, count, openId);
				}
			} else {
				StringBuffer returnStr = new StringBuffer();
				for (WccAutokbs auto : replySet) {
					returnStr.append("[" + count + "]"
							+ auto.getTitle() + "\n");
					WccAutoKbsDto dto=new WccAutoKbsDto();
					try {
						BeanUtils.copyProperties(dto, auto);
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					logMap.put(openId + "_log_" + count,dto);
					count++;
					logMap.put(openId + "_count", count);
				}

				try {
					wxMpService.customMessageSend(WxMpCustomMessage
							.TEXT()
							.toUser(inMessage.getFromUserName())
							.content(returnStr.toString()).build());
				} catch (WxErrorException e) {
					e.printStackTrace();
				}

				String totalMsg = returnStr.toString();
				record.setToUserInsertTime(new Date());
				record.setToUserRecord(totalMsg);
				record.persist();
				return;
			}

		}
	
	}

	@Override
	public void imagePro() {
		// 图片消息
		String fromUser = inMessage.getFromUserName();// openid
		WccFriend friend = new WccFriend();
		friend = WccFriend.findWccFriendByOpenId(fromUser);
		friend.setMessageEndTime(new Date());
		friend.merge();
		WccRecordMsg record = new WccRecordMsg();
		record.setFriendGroup(friend.getWgroup().getId() + "");
		record.setInsertTime(new Date());
		record.setMsgFrom(1);
		record.setNickName(friend.getNickName());
		record.setSex(friend.getSex());
		record.setOpenId(friend.getOpenId());
		record.setPlatFormAccount(wccPlatformUser.getAccount());
		record.setPlatFormId(wccPlatformUser.getId() + "");
		record.setProvince(friend.getProvince());
		record.setRecordContent(inMessage.getPicUrl() + "--"
				+ inMessage.getMediaId());
		record.setRecordFriendId(friend.getId());
		record.setToUserInsertTime(new Date());
		record.setCompanyId(wccPlatformUser.getCompany().getId());
		record.persist();
	

	}

	@Override
	public void voicePro() {
		// 语音消息
		String mediaId = inMessage.getMediaId();
		try {
			File file = wxMpService.mediaDownload(mediaId);
			File nfile = new File(request.getSession()
					.getServletContext().getRealPath("/")
					+ "attached/voice/");
			System.out.println(nfile.getPath());
			if (!nfile.exists()) {
				nfile.mkdirs();
			}
			String ctxPath = nfile.getPath() + "/" + file.getName();
			System.out.println(ctxPath);
			copyFile(file.getPath(), ctxPath);

			File source = new File(ctxPath);
			File target = new File(ctxPath.substring(0,
					ctxPath.length() - 3)
					+ "mp3");
			AudioAttributes audio = new AudioAttributes();
			Encoder encoder = new Encoder();

			try {
				for (String ss : encoder
						.getSupportedEncodingFormats()) {
					System.out.println(ss);
				}
			} catch (EncoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// pcm_s16le libmp3lame libvorbis libfaac
			audio.setCodec("libmp3lame");
			EncodingAttributes attrs = new EncodingAttributes();
			attrs.setFormat("mp3");
			attrs.setAudioAttributes(audio);

			try {
				encoder.encode(source, target, attrs);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InputFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EncoderException e) {

			} finally {
				String fromUser = inMessage.getFromUserName();// openid
				WccFriend friend = new WccFriend();
				friend = WccFriend.findWccFriendByOpenId(fromUser);
				friend.setMessageEndTime(new Date());
				friend.merge();
				WccRecordMsg record = new WccRecordMsg();
				record.setFriendGroup(friend.getWgroup().getId()
						+ "");
				record.setInsertTime(new Date());
				record.setMsgFrom(2);
				record.setNickName(friend.getNickName());
				record.setSex(friend.getSex());
				record.setOpenId(friend.getOpenId());
				record.setPlatFormAccount(wccPlatformUser
						.getAccount());
				record.setPlatFormId(wccPlatformUser.getId() + "");
				record.setProvince(friend.getProvince());
				record.setRecordContent("/ump/attached/voice/"
						+ target.getName());
				record.setRecordFriendId(friend.getId());
				record.setToUserInsertTime(new Date());
				record.setCompanyId(wccPlatformUser.getCompany()
						.getId());
				record.persist();
			}

		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	

	}

	@Override
	public void videoPro() {
		// 视频消息
		String mediaId = inMessage.getMediaId();
		try {
			File file = wxMpService.mediaDownload(mediaId);
			File nfile = new File(request.getSession()
					.getServletContext().getRealPath("/")
					+ "download/video/");
			System.out.println(nfile.getPath());
			if (!nfile.exists()) {
				nfile.mkdirs();
			}
			String ctxPath = nfile.getPath() + "/" + file.getName();
			System.out.println(ctxPath);
			copyFile(file.getPath(), ctxPath);

			String fromUser = inMessage.getFromUserName();// openid
			WccFriend friend = new WccFriend();
			friend = WccFriend.findWccFriendByOpenId(fromUser);
			friend.setMessageEndTime(new Date());
			friend.merge();
			WccRecordMsg record = new WccRecordMsg();
			record.setFriendGroup(friend.getWgroup().getId() + "");
			record.setInsertTime(new Date());
			record.setMsgFrom(3);
			record.setNickName(friend.getNickName());
			record.setSex(friend.getSex());
			record.setOpenId(friend.getOpenId());
			record.setPlatFormAccount(wccPlatformUser.getAccount());
			record.setPlatFormId(wccPlatformUser.getId() + "");
			record.setProvince(friend.getProvince());
			record.setRecordContent("/ump/attached/voice/"
					+ file.getName());
			record.setRecordFriendId(friend.getId());
			record.setToUserInsertTime(new Date());
			record.setCompanyId(wccPlatformUser.getCompany()
					.getId());
			record.persist();
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	

	}

	@Override
	public void linkPro() {
		// TODO Auto-generated method stub

	}
	public void ManulConnect(String openId) throws IOException
{
	if("1".equals(openIdMap.get(openId))){//多客服
		WxMpXmlOutTextMessage m2 = new WxMpXmlOutTextMessage();
		m2.setContent(inMessage.getContent());
		m2.setFromUserName(inMessage.getToUserName());
		m2.setToUserName(inMessage.getFromUserName());
		// m2.setMsgType(inMessage.getMsgType());
		m2.setMsgType(WxConsts.XML_TRANSFER_CUSTOMER_SERVICE);
		m2.setCreateTime(inMessage.getCreateTime());
		response.getWriter().write(m2.toXml());
	}else if("2".equals(openIdMap.get(openId))){//UCC对接
		if(null == uccToken){
			WccWxCommController.checkHand();
		}
		if(null == uccToken){
			openIdMap.remove(openId);
			timeMap.remove(openId);
			try {
				wxMpServiceMap.get(openId).customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content("服务器异常").build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		WccUccOut uccOut = WccWxCommController.UccIn(openId);
		if(null == uccOut){
			return;
		}
		System.out.println("uccOut="+uccOut.toString());
		if(!"0".equals(uccError.getErrorCode())){
			openIdMap.remove(openId);
			timeMap.remove(openId);
			try {
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccError.getErrorMsg()).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
			try {
				wxMpService.customMessageSend(WxMpCustomMessage.TEXT().toUser(openId).content(uccOut.getMsg()).build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		System.out.println("ucc====================================");
	}
}
	/**
	 * 处理相近问题的方法
	 * @param auto
	 * @param record
	 * @param count
	 * @param openId
	 * @param msgFrom
	 * @return
	 * @throws IOException
	 */
  public int handleIssues(WccAutokbs auto,WccRecordMsg record,int count,String openId,int msgFrom) throws IOException
  {
		if (!auto.getRelatedIssues().equals("")) {
			String[] issues = auto
					.getRelatedIssues().split(",");
			String issuStr = outMessageProblem(
					count, inMessage, response,
					auto, record, issues);
			try {
				wxMpService
						.customMessageSend(WxMpCustomMessage
								.TEXT()
								.toUser(inMessage
										.getFromUserName())
								.content(issuStr)
								.build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			for (String issu : issues) {
				WccAutokbs autoKbs = WccAutokbs
						.findWccAutokbs(Long
								.parseLong(issu));
				if (autoKbs.getActive()
						&& autoKbs
								.getIsReview()
								.equals(WccAutokbsIsReview.审核通过)) {
					logMap.put(openId + "_log_"
							+ count, autoKbs);
					count++;
					logMap.put(openId + "_count",
							count);
				}
			}
		} else {
			record.setToUserInsertTime(new Date());
			record.setToUserRecord(auto.getMaterials()==null?"":auto.getMaterials().getThumbnailUrl());
			record.setMsgFrom(msgFrom);
			record.persist();
		}
		return count;
	  
  }
  /**
   * 处理wccAutoKbs问题,图文消息
 * @throws IOException 
   */
  public  int AutoKbsPocessor(WccAutokbs auto,WccFriend friend,WccRecordMsg record,int count,String openId) throws IOException
  {
		// 根据客户选择问题进行回复
		if (auto.getReplyType().equals(	WccAutokbsReplyType.文本)) {
			try {
				wxMpService
						.customMessageSend(WxMpCustomMessage
								.TEXT()
								.toUser(inMessage
										.getFromUserName())
								.content(
										auto.getContent())
								.build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			count= handleIssues(auto, record, count, openId,0);
		} else if (auto.getReplyType().equals(
				WccAutokbsReplyType.图片)) {

			uploadAutoMadie(auto, request, wxMpService,
					"image");

			if (auto.getMaterials().getMaterialId() != null) {
				try {
					wxMpService
							.customMessageSend(WxMpCustomMessage
									.IMAGE()
									.toUser(inMessage
											.getFromUserName())
									.mediaId(
											auto.getMaterials()
													.getMaterialId())
									.build());
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
			count= handleIssues(auto, record, count, openId,1);
		} else if (auto.getReplyType().equals(
				WccAutokbsReplyType.语音)) {

			uploadAutoMadie(auto, request, wxMpService,
					"voice");

			try {
				wxMpService
						.customMessageSend(WxMpCustomMessage
								.VOICE()
								.toUser(inMessage
										.getFromUserName())
								.mediaId(
										auto.getMaterials()
												.getMaterialId())
								.build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			count= handleIssues(auto, record, count, openId,2);
		} else if (auto.getReplyType().equals(
				WccAutokbsReplyType.视频)) {

			uploadAutoMadie(auto, request, wxMpService,
					"video");

			try {
				wxMpService
						.customMessageSend(WxMpCustomMessage
								.VIDEO()
								.toUser(inMessage
										.getFromUserName())
								.mediaId(
										auto.getMaterials()
												.getMaterialId())
								.description(
										auto.getMaterials()
												.getDescription())
								.build());
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			count= handleIssues(auto, record, count, openId,3);
		} else if (auto.getReplyType().equals(
				WccAutokbsReplyType.图文)) {
			me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder builder = WxMpCustomMessage
					.NEWS();
			builder.toUser(inMessage.getFromUserName());
			WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
			article.setDescription(auto.getMaterials()
					.getDescription());
			article.setPicUrl(Global.Url
					+ auto.getMaterials()
							.getThumbnailUrl());
			article.setTitle(auto.getMaterials()
					.getTitle());
			article.setUrl(Global.Url
					+ "/ump/wccmaterialses/showdetail/"
					+ auto.getMaterials().getId()+"?friendId="+friend.getId());
			builder.addArticle(article);
			List<WccMaterials> list = auto
					.getMaterials().getChildren();
			if (list.size() > 0) {
				for (WccMaterials mater : list) {
					WxMpCustomMessage.WxArticle childArticle = new WxMpCustomMessage.WxArticle();
					childArticle.setDescription(mater
							.getDescription());
					childArticle.setPicUrl(Global.Url
							+ mater.getThumbnailUrl());
					childArticle.setTitle(mater
							.getTitle());
					childArticle
							.setUrl(Global.Url
									+ "/ump/wccmaterialses/showdetail/"
									+ mater.getId()+"?friendId="+friend.getId());
					builder.addArticle(childArticle);
				}
			}
			WxMpCustomMessage message = builder.build();
			try {
				wxMpService.customMessageSend(message);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			count= handleIssues(auto, record, count, openId,4);

		}
	
	  return count;
  }
	
}
