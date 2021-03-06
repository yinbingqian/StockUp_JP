package com.sxit.utils;

import lnpdit.lntv.tradingtime.R;
import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 分享
 * 
 * @author huanyu 类名称：ShareTool 创建时间:2014-11-20 上午9:15:35
 */
public class ShareTool {
	public static final String DESCRIPTOR = "com.umeng.share";
	public final UMSocialService mController = UMServiceFactory.getUMSocialService(DESCRIPTOR);
	public Context context;
	public Activity activity;
	public static ShareTool share;
	public String TITLE = "股涨测试标题";
	public String CONTENT = "股涨测试内容";
	public String TARGETURL = "http://www.baidu.com";
	public UMImage urlImage;
	
	public static ShareTool getInstance(Context context, Activity activity, String _title, String _content, String _url) {
		if (share == null) {
			share = new ShareTool(context, activity, _title, _content, _url);
		}
		return share;
	}

	public ShareTool(Context context, Activity activity, String _title, String _content, String _url) {
		this.context = context;
		this.activity = activity;
		
		this.TITLE = _title;
		this.CONTENT = _content;
		this.TARGETURL = _url;
		
		urlImage = new UMImage(context, R.drawable.app_icon);
		configPlatforms();
		setShareContent();
	}

	/**
	 * 相关平台配置信息
	 */
	private void configPlatforms() {
		// 移除人人，豆瓣，新浪
//		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA);
		mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		// 添加QQ、QZone平台
//		addQQQZonePlatform();
		// 添加微信、微信朋友圈平台
		addWXPlatform();
		// 添加短信平台
		addSMS();
	}

	/**
	 * 设置分享的内容
	 */
	private void setShareContent() {
	     // 配置SSO
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
		// ####设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent(urlImage);
		circleMedia.setShareContent(TITLE);
		circleMedia.setTitle(CONTENT);
		// circleMedia.setShareMedia(uMusic);
		// circleMedia.setShareMedia(video);
		circleMedia.setTargetUrl(TARGETURL);
		mController.setShareMedia(circleMedia);
		//好友
        WeiXinShareContent weixinContent = new WeiXinShareContent(urlImage);
        weixinContent.setShareContent(CONTENT);
        weixinContent.setTitle(TITLE);
        weixinContent.setTargetUrl(TARGETURL);
        mController.setShareMedia(weixinContent);
		// ####设置短信分享内容
		SmsShareContent sms = new SmsShareContent(urlImage);
		sms.setShareContent(CONTENT);
		mController.setShareMedia(sms);
		// ####设置QQ空间分享内容
		QZoneShareContent qzone = new QZoneShareContent(urlImage);
		qzone.setShareContent(CONTENT);
		qzone.setTargetUrl(TARGETURL);
		qzone.setTitle(TITLE);
		mController.setShareMedia(qzone);
		// ####QQ分享
		QQShareContent qqShareContent = new QQShareContent(urlImage);
		qqShareContent.setShareContent(CONTENT);
		qqShareContent.setTitle(TITLE);
		// qqShareContent.setShareMusic(uMusic);
		// qqShareContent.setShareVideo(video);
		qqShareContent.setTargetUrl(TARGETURL);
		mController.setShareMedia(qqShareContent);
		//####腾讯微博
        TencentWbShareContent tencent = new TencentWbShareContent(urlImage);
        tencent.setTitle(TITLE);
        tencent.setShareContent(CONTENT);
        mController.setShareMedia(tencent);
        //新浪微博
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
	}

	/**
	 * QQ
	 */
	private void addQQQZonePlatform() {
		String appId = "100424468";
		String appKey = "c7394704798a158208a74ab60104f0ba";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, appId, appKey);
		qqSsoHandler.addToSocialSDK();
		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}

	/**
	 * 微信
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx84b8e5819e8cab1d";
		String appSecret = "300346ca58ed3910162f3edf448302e0";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}

	/**
	 * 添加短信平台</br>
	 */
	private void addSMS() {
		// 添加短信
		SmsHandler smsHandler = new SmsHandler();
		smsHandler.addToSocialSDK();
	}

	/**
	 * share
	 * 
	 * @param activity
	 */
	public void shareMsg(Activity activity) {
		mController.openShare(activity, false);
	}
}
