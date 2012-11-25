package com.arena3.cor.photoslide;

import java.util.ArrayList;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class PhotoSlide extends Activity {
	
	protected CCGLSurfaceView _glSurfaceView;

	private CCDirector _cocos2dContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		ArrayList<String> paths = GalleryUtil.GetImages(getApplicationContext());
		
		for(String path : paths)
		{
			Log.d("PATH", path);
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		_glSurfaceView = new CCGLSurfaceView(this);

		setContentView(_glSurfaceView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_photo_slide, menu);
		return true;
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		CCDirector.sharedDirector().attachInView(_glSurfaceView);

		CCDirector.sharedDirector().setDisplayFPS(true);

		CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		CCDirector.sharedDirector().pause();		
	}

	@Override
	public void onResume()
	{
		super.onResume();

		CCDirector.sharedDirector().resume();	}

	@Override
	public void onStop()
	{
		super.onStop();	
		
		_glSurfaceView.queueEvent(new Runnable() {
			
			@Override
			public void run() {
				CCDirector.sharedDirector().end();
				
			}
		});
	}

}
