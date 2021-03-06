package com.sxit.activity.askingquest;

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
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.pay.CommodityListActivity;
import com.alipay.sdk.pay.PayDemoActivity;
import com.sxit.activity.askingquest.adapter.AskingQuestionInfo_Adapter;
import com.sxit.activity.askingquest.adapter.AskingQuestionInfo_Adapter.ViewHolder_Info;
import com.sxit.activity.base.BaseActivity;
import com.sxit.customview.MyGridView;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.utils.EventCache;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 提问 Acy
 * 
 * @author huanyu 类名称：AskingQuestion_Activity 创建时间:2014-10-28 上午10:54:35
 */
public class AskingQuestionInfo_Activity extends BaseActivity {
	private TextView tv_cancel, tv_title, tv_next, tv_moneyShow, tv_mark;
	private AskingQuestionInfo_Adapter adapter;
	private EditText actv_content;// 问题内容
//	private AutoCompleteTextView actv_content;// 问题内容
//	private LinearLayout ll_upload;
//	private ImageView img_upload;
	private MyGridView gridView;
	private String[] moneys = new String[] { "0", "5", "10", "20", "30", "50", "75", "100" };
	private String money = "0";
	private ViewHolder_Info holder_info;
	private Button bt_pay;
	//
	private AlertDialog dialog;
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");// 图片截图路径
	private Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_askingquestioninfo);
		this.isParentActivity = false;
		initView();
		setListeners();
	}

	private void initView() {
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_next = (TextView) findViewById(R.id.tv_next);
		tv_mark = (TextView) findViewById(R.id.tv_mark);// 财富值
		actv_content = (EditText) findViewById(R.id.actv_content);
//		actv_content = (AutoCompleteTextView) findViewById(R.id.actv_content);
//		tv_moneyShow = (TextView) findViewById(R.id.tv_moneyShow);
		gridView = (MyGridView) findViewById(R.id.gridView);
		bt_pay = (Button) findViewById(R.id.bt_pay);
//		img_upload = (ImageView) findViewById(R.id.img_upload);
//		ll_upload = (LinearLayout) findViewById(R.id.ll_upload);
		adapter = new AskingQuestionInfo_Adapter(this, moneys);
		gridView.setAdapter(adapter);
	}

	private void setListeners() {
		tv_next.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		bt_pay.setOnClickListener(this);
//		ll_upload.setOnClickListener(this);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (Integer.parseInt(moneys[position]) <= Integer.parseInt(getLoginUser().getMark()) || Integer.parseInt(moneys[position]) == 0) {
					for (int i = 0; i < parent.getCount(); i++) {
						View v = parent.getChildAt(i);
						if (position == i) {// 当前选中的Item改变背景颜色
							view.setBackgroundResource(R.drawable.grid_select);
							ViewHolder_Info holder = (ViewHolder_Info) view.getTag();
							holder.tv_money.setTextColor(AskingQuestionInfo_Activity.this.getResources().getColor(R.color.white));
							money = moneys[position];
							tv_moneyShow.setText("红心数" + money);
						} else {
							v.setBackgroundResource(R.drawable.grid_normal);
							ViewHolder_Info holder = (ViewHolder_Info) v.getTag();
							holder.tv_money.setTextColor(AskingQuestionInfo_Activity.this.getResources().getColor(R.color.register_text_color));
						}
					}
					money = "0";
					holder_info = (ViewHolder_Info) view.getTag();
				} else {
					Utils.showTextToast(AskingQuestionInfo_Activity.this, getString(R.string.nomoney));
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next:
			if (actv_content.getText().toString().trim().equals("")) {
				Utils.showTextToast(this, getString(R.string.content_empty));
				break;
			}
			if (money == null) {
				Utils.showTextToast(this, getString(R.string.money_empty));
				break;
			}
			intent.setClass(AskingQuestionInfo_Activity.this, OpAnswerTimeActivity.class);
			intent.putExtra("content", actv_content.getText().toString());
			intent.putExtra("money", money);
			String image = "";
			if(photo != null){
				image = ImageTool.bitmaptoString(photo);
			}
			intent.putExtra("image", image);
			startActivity(intent);
			break;
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.bt_pay:
			intent.setClass(AskingQuestionInfo_Activity.this, CommodityListActivity.class);
//			intent.setClass(AskingQuestionInfo_Activity.this, PayDemoActivity.class);
			startActivity(intent);
			break;
//		case R.id.ll_upload:
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
		if (tv_mark != null) {
			tv_mark.setText(getLoginUser().getMark());
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.COMMUNICATIONADD)) {
			if (res.getObj().toString().equals("true")) {
				Utils.showTextToast(this, getString(R.string.submit_success));
				LoginUser user = getLoginUser();
				user.setMark(Integer.parseInt(getLoginUser().getMark()) - Integer.parseInt(money) + "");
				setLoginUser(user);
				finish();
				EventCache.commandActivity.post(new String[] { this.getClass().getName() });
			} else {
				Utils.showTextToast(this, getString(R.string.submit_fail));
			}
		}
	}
}
