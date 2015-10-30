package com.sxit.activity.adviser.publishmsg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.sxit.activity.base.BaseActivity;
import com.sxit.utils.EventCache;
import com.sxit.utils.ImageTool;
import com.sxit.utils.Utils;

import lnpdit.lntv.tradingtime.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 自定义剪切
 * @author huanyu	
 * 类名称：ImageActivity   
 * 创建时间:2014-11-26 下午4:47:12
 */
public class ImageActivity extends BaseActivity {

	private ImageView mImageView = null;
	// 截图边框
	private TextView tv_broder;
	private DisplayMetrics dm = null;
	// 保存按钮
	private Button bt_save;
	// 撤销按钮
	private Button bt_repeal;
	private Intent intent;
	// 图片位置
	private int order = 0;
	// 临时图片名称
	private Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
	// 起始位子
	private int start_x = 0, start_y = 0;
	private Matrix matrix = new Matrix();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		initView();
		// 启动相机
		intent = getIntent();
		order = intent.getIntExtra("order", 0);
		if (order == 0) {
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, order);
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent, order);
		}
	}

	/**
	 * 初始化
	 */
	public void initView() {
		mImageView = (ImageView) findViewById(R.id.imageView_content);
		// 初始化图片位置
		getImageInitialize();
		if (width != 0 || height != 0) {
			center();
		}
		// 保存按钮
		bt_save = (Button) findViewById(R.id.bt_save);
		// 撤销按钮
		bt_repeal = (Button) findViewById(R.id.bt_repeal);
		tv_broder = (TextView) findViewById(R.id.tv_broder);
		MulitPointTouchListener onTouch = new MulitPointTouchListener(this);
		mImageView.setOnTouchListener(onTouch);
		bt_save.setOnClickListener(this);
		bt_repeal.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_save:
			int[] location = new int[2];
			tv_broder.getLocationOnScreen(location);
			start_x = location[0];
			start_y = location[1];
			// 截图
			getBitmap();
			finish();
			break;
		case R.id.bt_repeal:
			if (order == 0) {
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, order);
			} else {
				intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, order);
			}
			break;
		default:
			break;
		}
	}

	public void center() {
		center(true, true);
	}

	/**
	 * 横向、纵向居中
	 */
	protected void center(boolean horizontal, boolean vertical) {

		Matrix m = new Matrix();
		m.set(matrix);
		RectF rect = new RectF(0, 0, width, height);
		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			// 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下放留空则往下移
			int screenHeight = dm.heightPixels;
			if (height < screenHeight) {
				deltaY = (screenHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < screenHeight) {
				deltaY = mImageView.getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int screenWidth = dm.widthPixels;
			if (width < screenWidth) {
				deltaX = (screenWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < screenWidth) {
				deltaX = screenWidth - rect.right;
			}
		}
		matrix.postTranslate(deltaX, deltaY);
		mImageView.setImageMatrix(matrix);
	}

	/**
	 * 回调相机
	 */
	private Bitmap photo;
	private Bitmap bit;
	// 图片名称
	public static String nums[] = { "/sdcard/myImage/img0.png" };
	// 缩放比例
	private int scale = 1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("requestCode = " + requestCode);
		// System.out.println("resultCode = " + resultCode);
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Utils.showTextToast(this, "SD卡不可用");
			return;
		}
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				photo = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
				getScale(photo);
				bit = ImageTool.zoomBitmap(photo, photo.getWidth() / scale, photo.getHeight() / scale);
				mImageView.setImageBitmap(bit);
				// ImageTools.savePhotoToSDCard(bit, nums[requestCode]);
				break;
			case 1:
				ContentResolver resolver = getContentResolver();
				Uri originalUri = data.getData(); //获得图片的uri
				try {
					photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//
				//显得到bitmap图片
				getScale(photo);
				bit = ImageTool.zoomBitmap(photo, photo.getWidth() / scale, photo.getHeight() / scale);
				mImageView.setImageBitmap(bit);
				break;
			default:
				break;
			}
			// 将本机图片的宽高存入缓存,以便计算下次初始位置
			if (width == 0 && height == 0) {
				preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
				Editor editor = preferences.edit();
				editor.putInt("width", bit.getWidth());
				editor.putInt("height", bit.getHeight());
				editor.commit();
			}
		} else {
			finish();
		}
	}

	// 使用SharedPreferences来记录图片的宽高，计算初始位置
	private SharedPreferences preferences;
	private int width = 0;
	private int height = 0;

	/**
	 * 初始化image显示位置
	 */
	public void getImageInitialize() {
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		Editor editor = preferences.edit();
		width = preferences.getInt("width", 0);
		height = preferences.getInt("height", 0);
	}

	/**
	 * 获取缩放比例,填充宽度
	 * 
	 * @param imageBitmap
	 * @return
	 */
	public int getScale(Bitmap imageBitmap) {
		if (scale != 0) {
			return scale;
		}
		int windowWidth = Utils.getWindowWidth(ImageActivity.this);
		scale = imageBitmap.getWidth() / windowWidth;
		return scale;
	}

	/**
	 * 局部截屏300 x 190
	 * 
	 * @return
	 */
	private int newHeight = 250;
	private int newWidth = 450;
	private float scaleWidth;
	private float scaleHeight;

	private void getBitmap() {
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		bit = view.getDrawingCache();
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int toHeight = frame.top;

		bit = Bitmap.createBitmap(bit, start_x, start_y, tv_broder.getMeasuredWidth(), tv_broder.getMeasuredHeight());
		// 基于不同分辩率给图片缩放定值300x190
		// 计算缩放比例
		scaleWidth = ((float) newWidth) / bit.getWidth();
		scaleHeight = ((float) newHeight) / bit.getHeight();
		// 取得想要缩放的matrix参数
		matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
		if (bit == null) {
			System.out.println("bit为空");
		} else {
			System.out.println("bit不为空");
			ImageTool.savePhotoToSDCard(bit, nums[0]);
		}
		view.setDrawingCacheEnabled(false);
	}

	/** 监听返回键 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			Utils.showTextToast(ImageActivity.this, "您还没有保存图片");
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bit != null) {
			System.out.println("回收bitmap");
			bit.recycle();
		}
	}
}
