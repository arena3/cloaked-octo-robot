package com.arena3.cor.PhotoSlide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.Pixmap;

public class GalleryUtil {

	AndroidApplication _application;

	// Get the base URI for the People table in the Contacts content
	// provider.

	public GalleryUtil(AndroidApplication application) {
		_application = application;
	}

	public void getFilePaths(BlockingOnUIRunnableContext ctx) {

		ArrayList<String> outFilePaths = new ArrayList<String>();
		
		// which image properties are we querying
		String[] projection = new String[] { MediaStore.Images.Media.DATA };

		// Make the query.
		Cursor cur = new CursorLoader(_application,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, 	// Which
																			// columns
																			// to return
				"", // Which rows to return (all rows)
				null, // Selection arguments (none)
				"" // Ordering
		).loadInBackground();

		if (cur.moveToFirst()) {
			int filepathColumn = cur
					.getColumnIndex(MediaStore.Images.Media.DATA);

			do {
				// Get the field values
				outFilePaths.add(cur.getString(filepathColumn));
			} while (cur.moveToNext());
		}

		ctx.outData = outFilePaths;
		
		cur.close();
	}
	
	void getImagePixmap(BlockingOnUIRunnableContext ctx)
	{
		String filePath = (String)ctx.inData;
		
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		options.inJustDecodeBounds = false;
		
		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
		
		byte[] byteArray = stream.toByteArray();
		
		ctx.outData = new Pixmap(byteArray, 0, byteArray.length); 
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

    	// Raw height and width of image
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;

    	if (height > reqHeight || width > reqWidth) {
    		if (reqWidth > reqHeight) {
    			inSampleSize = Math.round((float)height / (float)reqHeight);
    		} else {
    			inSampleSize = Math.round((float)width / (float)reqWidth);
    		}
    	}

    	return inSampleSize;
    }
}
