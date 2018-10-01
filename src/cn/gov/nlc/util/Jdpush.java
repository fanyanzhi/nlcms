package cn.gov.nlc.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class Jdpush {
	protected static final Logger LOG = LoggerFactory.getLogger(Jdpush.class);

	// demo App defined in resources/jpush-api.conf EXTRA_EXTRA

	public static final String TITLE = "国家图书馆";
	public static final String ALERT = "国家图书馆闭馆通知1019";
	public static final String MSG_CONTENT = "10月1日至7日闭馆";
	public static final String REGISTRATION_ID = "0900e8d85ef";
	public static final String TAG = "tag_api";

	private static final String appKey = "58d4855bae0cc4b290dd6416";
	private static final String masterSecret = "9755447e1695f4c2c3252fd0";
	
	private static final String httpproxy = PropertiesUtils.getPropertyValue("httpproxy");
	private static final String httpsproxyport = PropertiesUtils.getPropertyValue("httpsproxyport");

	public static JPushClient jpushClient = null;

	public static void testSendPush(String appKey, String masterSecret) {

		jpushClient = new JPushClient(masterSecret, appKey, 3);

		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy

		// For push, all you need do is to build PushPayload object.
		// PushPayload payload = buildPushObject_all_all_alert();
		// 生成推送的内容，这里我们先测试全部推送 android
		// (1
		// PushPayload payload = buildPushObject_all_alert();//ok
		// (2
		// PushPayload payload = buildPushObject_android_and_ios();// ok
		// 3
		// PushPayload payload = buildPushObject_android_tag_alertWithTitle2();
		// ios
		// PushPayload payload = buildPushObject_all_all_alert();
		// ios 可以用
		// PushPayload payload = buildPushObject_all_alias_alert3();
		// ios可以用
		PushPayload payload = buildPushObject_ios_tag_alertWithTitle2();
		// ios可以用
		// PushPayload payload = buildPushObject_android_and_ios();
		// PushPayload payload =
		// buildPushObject_ios_audienceMore_messageWithExtras();
		// //buildPushObject_android_tag_alertWithTitle();
		// PushPayload payload = buildPushObject_all_alias_alert();

		try {
			System.out.println(payload.toString());
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result + "................................");

			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 发送广播到ios和安卓 所有人的
	 */
	public static void pushAll(String title, Map<String, String> message) {

	}

	/**
	 * 以别名方式发送
	 * 
	 * @param alias
	 * @param title
	 * @param message
	 */
	public static void pushWithAlias(String alias, String title, Map<String, String> message) {

	}

	/*
	 * public static PushPayload buildPushObject_all_all_alert() { return
	 * PushPayload.alertAll(ALERT); }
	 */

	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all())
				.setNotification(Notification.android(ALERT, TITLE, null)).build();
	}

	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and("tag1", "tag_all"))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(ALERT).setBadge(5)
								.setSound("happy").addExtra("from", "JPush").build())
						.build())
				.setMessage(Message.content(MSG_CONTENT))
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
						.addAudienceTarget(AudienceTarget.alias("alias1", "alias2")).build())
				.setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).addExtra("from", "JPush").build()).build();
	}

	/***
	 */
	public static PushPayload send_N(String registrationId, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())// 必填
																			// 推送平台设置
				.setAudience(Audience.registrationId(registrationId)).setNotification(Notification.alert(alert))
				/**
				 * 如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。
				 * True 表示推送生产环境，False 表示要推送开发环境； 如 果不指定则为推送生产环境
				 */
				.setOptions(Options.newBuilder().setApnsProduction(false).build()).build();
	}

	public static PushPayload buildPushObject_all_alert2() {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(ALERT)).build();
	}

	/**
	 * 推送改特定用户，加类别的 可以,
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert() {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias("lfk1010"))
				.setNotification(Notification.alert(ALERT)).setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT)
						.addExtra("notice", "1DBC5EB26B154EDDE050007F010042FD").build())
				.build();
	}

	/**************************************/
	// 1)
	/**
	 * 推送给所有人，所有平台-->可以
	 * {"platform":"all","audience":"all","notification":{"alert":
	 * "国家图书馆闭馆通知1019"},"options":{"sendno":1939330627,"apns_production":false}}
	 * {"msg_id":3280722559,"sendno":1939330627}................................
	 * 2016-10-19 13:49:11 INFO cn.gov.nlc.util.Jdpush:60 - Got result -
	 * {"msg_id":3280722559,"sendno":1939330627} sucess
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_alert() {
		return PushPayload.newBuilder().setPlatform(Platform.all())// 设置接受的平台
				.setAudience(Audience.all())// Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
				.setNotification(Notification.alert(ALERT)).build();
	}

	// 2)
	/**
	 * {"platform":["android","ios"],"audience":"all","notification":{"alert":
	 * "国家图书馆闭馆通知1019","android":{"alert":"国家图书馆闭馆通知1019","title":"国家图书馆"},"ios"
	 * :{"alert":"国家图书馆闭馆通知1019","extras":{"notice":
	 * "1DBC5EB26B154EDDE050007F010042FD"},"badge":"+1","sound":""}},"options":{
	 * "sendno":628845487,"apns_production":false}} 2016-10-19 13:59:57 INFO
	 * cn.gov.nlc.util.Jdpush:60 - Got result -
	 * {"msg_id":3250998535,"sendno":628845487}
	 * {"msg_id":3250998535,"sendno":628845487}................................
	 * sucess
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_android_and_ios() {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
				.setNotification(
						Notification.newBuilder().setAlert(ALERT)
								.addPlatformNotification(AndroidNotification.newBuilder().setTitle(TITLE).build())
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1)
										.addExtra("notice", "1DBC5EB26B154EDDE050007F010042FD").build())
								.build())
				.build();
	}

	// 3
	/**
	 * android,客户端可以,特定的人
	 * 推送内容包括{"platform":["android"],"audience":{"alias":["lfk1010"]},
	 * "notification":{"android":{"alert":"国家图书馆闭馆通知1019","extras":{"news":
	 * "4aeafd24574f5cda0157b138e49e7079"},"title":"国家图书馆"}},"options":{"sendno"
	 * :676106587,"apns_production":false}}
	 * {"msg_id":2336928913,"sendno":676106587}................................
	 * 2016-10-19 09:40:37 INFO cn.gov.nlc.util.Jdpush:55 - Got result -
	 * {"msg_id":2336928913,"sendno":676106587}
	 * 
	 * 
	 * 
	 * {"platform":["android"],"audience":{"alias":["lfk1010"]},"notification":{
	 * "android":{"alert":"国家图书馆闭馆通知1019","extras":{"notice":
	 * "1DBC5EB26B154EDDE050007F010042FD"},"title":"国家图书馆"}},"options":{"sendno"
	 * :928548734,"apns_production":false}}
	 * {"msg_id":3284570772,"sendno":928548734}................................
	 * 2016-10-19 14:04:09 INFO cn.gov.nlc.util.Jdpush:64 - Got result -
	 * {"msg_id":3284570772,"sendno":928548734} sucess
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle2() {
		Map<String, String> news = new HashMap<String, String>();
		news.put("message", "大家好，系统测试");
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias("lfk1010"))
				.setNotification(Notification.android(ALERT, TITLE, news)).build();
	}

	//////////////// ios
	public static PushPayload buildPushObject_all_all_alert() {
		return PushPayload.alertAll(ALERT);
	}

	public static PushPayload buildPushObject_all_alias_alert3() {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.all())
				.setNotification(Notification.alert(ALERT)).build();
	}

	public static PushPayload buildPushObject_android_tag_alertWithTitle3() {
		return PushPayload.newBuilder().setPlatform(Platform.ios())
				// .setAudience(Audience.tag("tag1"))
				.setNotification(Notification.android(ALERT, TITLE, null)).build();
	}

	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage3() {
		return PushPayload.newBuilder().setPlatform(Platform.ios())
				// .setAudience(Audience.tag_and("tag1", "tag_all"))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(ALERT).setBadge(5)
								.setSound("happy").addExtra("from", "JPush").build())
						.build())
				.setMessage(Message.content(MSG_CONTENT))
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	public static PushPayload buildPushObject_ios_tag_alertWithTitle2() {
		Map<String, String> news = new HashMap<String, String>();
		news.put("message", "大家好，系统测试");
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias("cnki_wsy"))
				.setNotification(Notification.ios(ALERT, news)).build();
	}

	/*******************************************
	 * 2016-11-06,可以写一个定时任务，在容器启动的时候一直循环去表里面读数据，推送。还可以在插入的时候推送，明早试一下
	 ***************************************************/

	public static boolean pushMessage(int sort, String alias, String alert, String title, String type, String id) {
		int port = new Integer(httpsproxyport);
		HttpProxy proxy = new HttpProxy(httpproxy, port);
		jpushClient = new JPushClient(masterSecret, appKey, 1, proxy);

		
		PushPayload payload = null;
		if (sort == 1) {
			payload = buildPushObject_all_alias_alert(alias, alert, title, type, id);
		} else if (sort == 2) {
			payload = buildPushObject_all_tag_alert(alias, alert, title, type, id);
		} else {
			payload = buildPushObject_to_all(alert, title, type, id);
		}

		try {
			LOG.info("【推送的内容】" + payload.toString());
			PushResult result = jpushClient.sendPush(payload);

			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
		return true;
	}

	/**
	 * 针对新闻、公告、专题、文津
	 * 
	 * @param alert
	 * @param title
	 * @param type
	 * @param id
	 * @return
	 */
	public static PushPayload buildPushObject_to_all(String alert, String title, String type, String id) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtra(type, id).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra(type, id).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

	}

	/**
	 * 推送改特定用户，加类别的 可以, 周一需要试试这个好用吗，这个给android好用，需要给ios试试
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String alert, String title, String type,
			String id) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtra(type, id).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra(type, id).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

		/*
		 * return PushPayload.newBuilder().setPlatform(Platform.android_ios()).
		 * setAudience(Audience.alias(alias))
		 * .setNotification(Notification.alert(alert))
		 * .setMessage(Message.newBuilder().setMsgContent(msg_content).addExtra(
		 * type, id).build()).build();
		 */

	}

	// 极光android针对个人的可能有问题
	public static PushPayload buildPushObject_all_tag_alert(String tag, String alert, String title, String type,
			String id) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.tag(tag))
				.setNotification(Notification.newBuilder().setAlert(alert)
						.addPlatformNotification(
								AndroidNotification.newBuilder().setTitle(title).addExtra(type, id).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra(type, id).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();

	}
}
