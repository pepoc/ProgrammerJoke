package com.pepoc.programmerjoke.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pepoc.programmerjoke.Config;
import com.pepoc.programmerjoke.R;
import com.pepoc.programmerjoke.utils.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXManager {
	
	private static IWXAPI wxapi = null;
	public final static int SHARE_FRIEND = 0;
	public final static int SHARE_MOMENTS = 1;

	public final static IWXAPI getIWXAPI(Context context) {
		if (null == wxapi) {
			wxapi = WXAPIFactory.createWXAPI(context, Config.WX_APP_ID, false);
			wxapi.registerApp(Config.WX_APP_ID);
		}
		return wxapi;
	}
	
	public static void shareUrlToWeChat(Context context, String url, String title, String description, int shareType) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = description;
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = "webpage" + System.currentTimeMillis();
		req.message = msg;
		if (SHARE_FRIEND == shareType) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		} else if (SHARE_MOMENTS == shareType) {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		getIWXAPI(context).sendReq(req);
	}
}
