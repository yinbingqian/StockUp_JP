package com.sxit.activity.chatmsg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

import lnpdit.lntv.tradingtime.R;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class Chat_Img_Display_Activity extends Activity {
	static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %%";

	private ImageView mImageView;
//	private TextView mCurrMatrixTv;

	private PhotoViewAttacher mAttacher;

//	private Toast mCurrentToast;
	
	Bitmap bitmap;
	
	static int IO_BUFFER_SIZE = 3*1024;
    
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_chat_img_display_layout);  
          
        Intent intent = this.getIntent();
        final String url = intent.getStringExtra("url");
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        
        mImageView = (ImageView) findViewById(R.id.imageview);
        Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub				
				bitmap  = GetLocalOrNetBitmap(url);
				Message msg = new Message();
				msg.arg1 = 0;
				mHandler.sendMessage(msg);
			}
		});
        thread.start();
    }  
    
    private Handler mHandler = new Handler(){ 
        @Override
        public void handleMessage(Message msg) {
        	if (msg.arg1 == 0) {				
        		mImageView.setImageBitmap(bitmap);// 填充控件  
        		
        		mAttacher = new PhotoViewAttacher(mImageView);
        		// Lets attach some listeners, not required though!
        		mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
        		mAttacher.setOnPhotoTapListener(new PhotoTapListener());
			}
        }
    };
    
    public static Bitmap GetLocalOrNetBitmap(String url)  
    {  
        Bitmap bitmap = null;  
        InputStream in = null;  
        BufferedOutputStream out = null;  
        try  
        {  
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);  
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();  
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);  
            copy(in, out);  
            out.flush();  
            byte[] data = dataStream.toByteArray();  
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  
            data = null;  
            return bitmap;  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
            return null;  
        }  
    }
    
    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    
	@Override
	public void onDestroy() {
		super.onDestroy();

		// Need to call clean-up
		mAttacher.cleanup();
	}

	private class PhotoTapListener implements OnPhotoTapListener {

		@Override
		public void onPhotoTap(View view, float x, float y) {
			float xPercentage = x * 100f;
			float yPercentage = y * 100f;

//			if (null != mCurrentToast) {
//				mCurrentToast.cancel();
//			}

//			mCurrentToast = Toast.makeText(Chat_Img_Display_Activity.this,
//					String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage), Toast.LENGTH_SHORT);
//			mCurrentToast.show();
		}
	}

	private class MatrixChangeListener implements OnMatrixChangedListener {

		@Override
		public void onMatrixChanged(RectF rect) {
//			mCurrMatrixTv.setText(rect.toString());
		}
	}
}
