package com.sxit.activity.adviser.publishmsg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.item.adapter.AskQuestion_Adapter;
import com.sxit.activity.th.item.bean.AskQuestion_ListBean;
import com.sxit.http.SoapRes;
import com.sxit.utils.EventCache;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 发布消息
 * 
 * @author huanyu 类名称：PublishMsg_Acitivty 创建时间:2014-11-26 下午4:52:17
 */
public class PublishMsg_Acitivty extends BaseActivity {
	private TextView tv_cancel;
	private AutoCompleteTextView actv_content, actv_abstract, actv_title;
	private Button bt_send;// 发布消息
//	private LinearLayout ll_upload;
	private Spinner spinner1;
//	private ImageView img_upload;
	// 图片上传
	private AlertDialog dialog;
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");// 图片截图路径
	private Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_publistmsg_layout);
		this.isParentActivity = false;
		initView();
		setListeners();
	}

	private void initView() {
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		bt_send = (Button) findViewById(R.id.bt_send);
		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
		actv_title = (AutoCompleteTextView) findViewById(R.id.actv_title);
		actv_abstract = (AutoCompleteTextView) findViewById(R.id.actv_abstract);
//		img_upload = (ImageView) findViewById(R.id.img_upload);
//		ll_upload = (LinearLayout) findViewById(R.id.ll_upload);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List list = new ArrayList();
		list.add("大盘消息");
		list.add("股票消息");
		list.add("财经消息");
		// list.add("福州");
		// list.add("厦门");
		spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list));
	}

	private void setListeners() {
		tv_cancel.setOnClickListener(this);
		bt_send.setOnClickListener(this);
//		ll_upload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.bt_send:
			// 校验内容
			if (actv_content.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.content_empt1));
				break;
			}
			// 校验标题
			if (actv_title.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.content_empt2));
				break;
			}
			// 校验摘要
			if (actv_abstract.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.content_empt3));
				break;
			}
			String image = "";
			if (photo != null) {
				image = ImageTool.bitmaptoString(photo);
			}
			bt_send.setEnabled(false);
			try {				
//				Utils.showProgressDialog(this, getString(R.string.submitting), true);
			} catch (Exception e) {
				// TODO: handle exception
			}
			String[] property_va = new String[] { getLoginUser().getUserid(), actv_title.getText().toString().trim(), actv_abstract.getText().toString().trim(), actv_content.getText().toString().trim(), "0", "", image };
			soapService.reportSubmit(property_va);
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 100) {
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
				} else {
					uri = Uri.fromFile(sdcardTempFile);
				}
				cropImage(uri, 100, 100, 101);
			}
			if (requestCode == 101) {
				photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
						photo = (Bitmap) extra.get("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					}
				}
//				LayoutParams para = img_upload.getLayoutParams();
//				para.height = 120;
//				para.width = 120;
//				img_upload.setLayoutParams(para);
//				img_upload.setImageBitmap(photo);
			}
		}
	}

	// 截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		photo = BitmapFactory.decodeFile(ImageActivity.nums[0]);
//		if (photo != null) {
//			LayoutParams para = img_upload.getLayoutParams();
//			para.height = 120;
//			para.width = 300;
//			img_upload.setLayoutParams(para);
//			img_upload.setImageBitmap(photo);
//		}
	}

	@Override
	protected void onDestroy() {
		try{			
			ImageTool.deleteAllPhoto("/sdcard/myImage/");
		}catch(Exception e){
			Log.e("ImageTool.deleteAllPhoto:  ", "Error!");
		}
		Utils.removeProgressDialog();
		super.onDestroy();
	}

	public void onEvent(SoapRes obj) {
		if (obj.getCode().equals(SOAP_UTILS.METHOD.REPORTSUBMIT)) {
			bt_send.setEnabled(true);
			if (obj.getObj() != null) {
				if (obj.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.send_success));
					EventCache.commandActivity.post(this.getClass().getName());
					finish();
				} else {
					Utils.showTextToast(this, getString(R.string.send_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.send_fail));
			}
		}
	}
}
