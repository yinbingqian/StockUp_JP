package com.sxit.activity.adviser.answer;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.android.app.IAlixPay;
import com.sxit.activity.adviser.th.item.bean.Question_ListBean;
import com.sxit.activity.base.BaseActivity;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.EventCache;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 顾问解答
 * 
 * @author huanyu 类名称：AAnswerQusestion_Activity 创建时间:2014-11-17 上午11:52:55
 */
public class AAnswerQusestion_Activity extends BaseActivity {
	private TextView tv_cancel;
	private TextView tv_realName;// 昵称
	private TextView tv_crtime;// 发布时间
	private TextView tv_aging;// 截止时间
	private TextView tv_content;//问题内容
	private ImageView img_icon;// 头像
//	private ImageView img_upload;
//	private RelativeLayout rl_upload;// 上传图片
	private Question_ListBean bean;
//	private TextView tv_questionState;// mark
	private AutoCompleteTextView actv_content;
	private Button bt_send;
	//
	private AlertDialog dialog;
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");// 图片截图路径
	private Bitmap photo;
	
	String mark;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_answer_question_layout);
		this.isParentActivity = false;
		intent = getIntent();

		mark = intent.getStringExtra("mark");
		bean = (Question_ListBean) intent.getSerializableExtra("question");
		initView();
		setListeners();
		setUI();
	}

	private void initView() {
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_realName = (TextView) findViewById(R.id.tv_realName);
		tv_crtime = (TextView) findViewById(R.id.tv_crtime);
		tv_aging = (TextView) findViewById(R.id.tv_aging);
		tv_content = (TextView) findViewById(R.id.tv_content);
//		tv_questionState = (TextView) findViewById(R.id.tv_questionState);
		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
		img_icon = (ImageView) findViewById(R.id.img_icon);
//		img_upload = (ImageView) findViewById(R.id.img_upload);
		bt_send = (Button) findViewById(R.id.bt_send);
//		rl_upload = (RelativeLayout) findViewById(R.id.rl_upload);
	}

	private void setListeners() {
		tv_cancel.setOnClickListener(this);
		bt_send.setOnClickListener(this);
//		rl_upload.setOnClickListener(this);
	}

	private void setUI() {
		tv_realName.setText(bean.getUserName());
		tv_crtime.setText(bean.getCrtime());
		tv_aging.setText(bean.getAging());
		tv_content.setText(bean.getContent());
//		tv_questionState.setText(mark);
//		tv_questionState.setText(bean.getMark());
		Instance.imageLoader.displayImage(bean.getHeadpic(), img_icon, Instance.user_options);// head
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.bt_send:
			if (actv_content.getText().toString().equals("")) {
				Utils.showTextToast(this, getString(R.string.content_empt1));
				break;
			}
			String[] property_va = new String[] { bean.getId(), actv_content.getText().toString(), actv_content.getText().toString(), getLoginUser().getUserid() };
//			String[] property_va = new String[] { bean.getId(), bean.getMark(), actv_content.getText().toString(), getLoginUser().getUserid() };
			soapService.communReplyAdd(property_va);
			break;
//		case R.id.rl_upload:
//			if (dialog == null) {
//				dialog = new AlertDialog.Builder(this).setItems(new String[] { "相机", "相册" }, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						if (which == 0) {
//							Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//							openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
//							startActivityForResult(openCameraIntent, 100);
//						} else {
//							Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//							openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//							startActivityForResult(openAlbumIntent, 100);
//						}
//					}
//				}).create();
//			}
//			if (!dialog.isShowing()) {
//				dialog.show();
//			}
//			break;
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

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.COMMUNREPLYADD)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.send_success));
					finish();
					EventCache.commandActivity.post(new String[] { this.getClass().getName(), "1" });
				} else {
					Utils.showTextToast(this, getString(R.string.send_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.send_fail));
			}
		}
	}
}
