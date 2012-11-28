package com.arena3.cor.PhotoSlide;

import java.util.ArrayList;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap;

public class MainActivity extends AndroidApplication implements ArchActivity {

	GalleryUtil gu = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;

		gu = new GalleryUtil(this);

		initialize(new PhotoSlide(this), cfg);
	}

	@Override
	public ArrayList<String> getImageFilePaths() {
		final BlockingOnUIRunnableContext ctx = new BlockingOnUIRunnableContext(
				null);

		new BlockingOnUIRunnable(this, new BlockingOnUIRunnableListener() {

			@Override
			public void onRunOnUIThread() {
				gu.getFilePaths(ctx);
			}
		}).startOnUIAndWait();

		return (ArrayList<String>) ctx.outData;
	}

	@Override
	public Pixmap getImagePixmap(final String filePath) {
		// return gu.getImagePixmap(filePath);

		final BlockingOnUIRunnableContext ctx = new BlockingOnUIRunnableContext(
				filePath);

		new BlockingOnUIRunnable(this, new BlockingOnUIRunnableListener() {

			@Override
			public void onRunOnUIThread() {
				gu.getImagePixmap(ctx);

			}
		}).startOnUIAndWait();

		return (Pixmap) ctx.outData;
	}
}