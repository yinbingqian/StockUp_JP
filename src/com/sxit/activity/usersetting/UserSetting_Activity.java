package com.sxit.activity.usersetting;

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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.register.CityNew_Activity;
import com.sxit.activity.register.Province_Activity;
import com.sxit.entity.LoginUser;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 编辑资料 Acy
 * 
 * @author huanyu 类名称：UserData_Activity 创建时间:2014-11-3 上午9:05:59
 */
public class UserSetting_Activity extends BaseActivity {
	private ImageView img_back, header;
	private RelativeLayout rl_name, rl_password, rl_sex, rl_city, rl_head;// 昵称，密码，性别，股龄，投资风格
//	private RelativeLayout  rl_stockAge, rl_stockStyle;// 股龄，投资风格
	private TextView tv_name, tv_sex, address,  settingline_tv_1, settingline_tv_2, settingline_tv_3, settingline_tv_4, settingline_tv_5, settingline_tv_6;
//	private TextView stock_age, stock_style;
	private AutoCompleteTextView actv_password;
	private String province, city;
	//
	private AlertDialog dialog;
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");//图片截图路径
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting);
		initView();
		setListeners();
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		header = (ImageView) findViewById(R.id.header);
		rl_name = (RelativeLayout) findViewById(R.id.rl_name);
		rl_password = (RelativeLayout) findViewById(R.id.rl_password);
		rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
//		rl_stockAge = (RelativeLayout) findViewById(R.id.rl_stockAge);
//		rl_stockStyle = (RelativeLayout) findViewById(R.id.rl_stockStyle);
		rl_head = (RelativeLayout) findViewById(R.id.rl_head);
		rl_city = (RelativeLayout) findViewById(R.id.rl_city);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		address = (TextView) findViewById(R.id.address);
//		stock_age = (TextView) findViewById(R.id.stock_age);
//		stock_style = (TextView) findViewById(R.id.stock_style);
//		settingline_tv_1 = (TextView) findViewById(R.id.settingline_tv_1);
//		settingline_tv_2 = (TextView) findViewById(R.id.settingline_tv_2);
		settingline_tv_3 = (TextView) findViewById(R.id.settingline_tv_3);
		settingline_tv_4 = (TextView) findViewById(R.id.settingline_tv_4);
//		settingline_tv_5 = (TextView) findViewById(R.id.settingline_tv_5);
//		settingline_tv_6 = (TextView) findViewById(R.id.settingline_tv_6);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
		if (getLoginUser().getLoginType() == 0) {
//			rl_stockAge.setVisibility(View.GONE);
			rl_city.setVisibility(View.GONE);
//			rl_stockStyle.setVisibility(View.GONE);
			settingline_tv_1.setVisibility(View.GONE);
			settingline_tv_2.setVisibility(View.GONE);
			settingline_tv_3.setVisibility(View.GONE);
			settingline_tv_4.setVisibility(View.GONE);
			settingline_tv_5.setVisibility(View.GONE);
			settingline_tv_6.setVisibility(View.GONE);
			rl_name.setEnabled(false);
			rl_password.setEnabled(false);
			rl_sex.setEnabled(false);
		}
		Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), header, Instance.user_options);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		rl_name.setOnClickListener(this);
		rl_password.setOnClickListener(this);
		rl_sex.setOnClickListener(this);
//		rl_stockAge.setOnClickListener(this);
//		rl_stockStyle.setOnClickListener(this);
		rl_city.setOnClickListener(this);
		rl_head.setOnClickListener(this);
	}

	public void setUI() {
		tv_name.setText(getLoginUser().getRealname());
		
//		if (getLoginUser().getStock_style().startsWith("stockStyle")) {
//			stock_age.setText("");
//		}else {			
//			stock_style.setText(getLoginUser().getStock_style());
//		}
//		if (getLoginUser().getStock_age().startsWith("stockAge")) {
//			stock_age.setText("");
//		}else {			
//			stock_age.setText(getLoginUser().getStock_age());
//		}
		tv_sex.setText(getLoginUser().getSex().equals("0") ? "男" : "女");
		address.setText(getLoginUser().getProvince() + " · " + getLoginUser().getCity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.rl_name:
			intent.setClass(UserSetting_Activity.this, UserSetting_Name_Activity.class);
			intent.putExtra("name", tv_name.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_password:
			intent.setClass(UserSetting_Activity.this, UserSetting_Password_Activity.class);
			intent.putExtra("password", actv_password.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_sex:
			intent.setClass(UserSetting_Activity.this, UserSetting_Sex_Activity.class);
			intent.putExtra("sex", tv_sex.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_stockAge:
			intent.setClass(UserSetting_Activity.this, Stock_Age_Activity.class);
			startActivity(intent);
			break;
		case R.id.rl_stockStyle:
			intent.setClass(UserSetting_Activity.this, Stock_Style_Activity.class);
			startActivity(intent);
			break;
		case R.id.rl_city:
			intent.setClass(UserSetting_Activity.this, Province_Activity.class);
			startActivity(intent);
			break;
		case R.id.rl_head:
			if (dialog == null) {
				dialog = new AlertDialog.Builder(this).setItems(new String[] { "相机", "相册" }, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
							startActivityForResult(openCameraIntent, 100);
						} else {
							Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
							openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(openAlbumIntent, 100);
						}
					}
				}).create();
			}
			if (!dialog.isShowing()) {
				dialog.show();
			}
			break;
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
				Bitmap photo = null;
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
//				header.setImageBitmap(photo);
				String []property_va = new String[]{getLoginUser().getUserid(),ImageTool.bitmaptoString(photo)};
				soapService.userEditor_head(property_va);
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

	public void onEvent(String... str) {
		for (String name : str) {
			if (name.equals(UserSetting_Name_Activity.class.getName())) {
				tv_name.setText(str[1]);
			}
			if (name.equals(UserSetting_Password_Activity.class.getName())) {

			}
			if (name.equals(UserSetting_Sex_Activity.class.getName())) {
				tv_sex.setText(str[1]);
			}
//			if (name.equals(Stock_Age_Activity.class.getName())) {
//				stock_age.setText(str[1]);
//			}
//			if (name.equals(Stock_Style_Activity.class.getName())) {
//				stock_style.setText(str[1]);
//			}
			if (name.equals(CityNew_Activity.class.getName())) {
				if (str[1] != null && str[2] != null) {
					province = str[1];
					city = str[2];
					String[] property_va = new String[] { getLoginUser().getUserid(), str[1], str[2] };
					soapService.userEditor_city(property_va);
				}
			}
		}
	}

	@Override
	protected void onResume() {
		setUI();
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_CITY)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					LoginUser user = getLoginUser();
					user.setProvince(province);
					user.setCity(city);
					setLoginUser(user);
					address.setText(province + " · " + city);
					Utils.showTextToast(this, getString(R.string.edit_success));
				} else {
					Utils.showTextToast(this, getString(R.string.edit_password_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}else if(res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_HEAD)){
			if (res.getObj() != null) {
				LoginUser user = getLoginUser();
				user.setHeadpic(res.getObj().toString());
				setLoginUser(user);
				Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), header, Instance.user_options);
			}else{
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}
	}
}
