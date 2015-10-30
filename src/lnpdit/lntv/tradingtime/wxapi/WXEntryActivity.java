package lnpdit.lntv.tradingtime.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sxit.utils.Utils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
/**
 * 微信分享回调，类名固定
 * @author huanyu	
 * 类名称：WXEntryActivity   
 * 创建时间:2014-11-20 下午1:46:09
 */
public class WXEntryActivity extends WXCallbackActivity {
	private IWXAPI api;
	private static final String APP_id = "wxc8234953530c3a97";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		api = WXAPIFactory.createWXAPI(this, APP_id, true);
		api.registerApp(APP_id);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq resp) {
		// TODO Auto-generated method stub
		String result = "";
		switch (resp.hashCode()) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "分享失败";
			break;
		default:
			result = "分享异常";
			break;
		}
		Utils.showTextToast(this, result);
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "分享失败";
			break;
		default:
			result = "分享异常";
			break;
		}
		Utils.showTextToast(this, result);
		finish();
	}
}
