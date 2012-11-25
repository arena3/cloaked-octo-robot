package com.arena3.cor.photoslide;

import java.util.ArrayList;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class GalleryUtil {
	
	Context _context;
	
	public GalleryUtil(Context context) {
	}

	public static ArrayList<String> GetImages(Context context) {
		ArrayList<String> result = new ArrayList<String>();

		// which image properties are we querying
		String[] projection = new String[] { MediaStore.Images.Media.DATA };

		// Get the base URI for the People table in the Contacts content
		// provider.
		Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		// Make the query.
		
        Cursor cur = new CursorLoader( context,
                images,
                projection, // Which columns to return
                "",         // Which rows to return (all rows)
                null,       // Selection arguments (none)
                ""          // Ordering
                ).loadInBackground();
        
        if ( cur != null ) {
        	int filepathColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
        	
        	if ( cur.moveToFirst() ) {
            	do {
            		
            		result.add(cur.getString(filepathColumn));
            		
            	} while ( cur.moveToNext() );
            }
        	
        	cur.close();
        }
        
		return result;
	}
}
