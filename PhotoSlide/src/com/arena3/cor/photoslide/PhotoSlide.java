package com.arena3.cor.photoslide;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PhotoSlide extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_slide);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_photo_slide, menu);
		return true;
	}

}
