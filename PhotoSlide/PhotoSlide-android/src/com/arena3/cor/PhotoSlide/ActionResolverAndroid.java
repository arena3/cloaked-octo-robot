package com.arena3.cor.PhotoSlide;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;

import com.badlogic.gdx.graphics.Pixmap;

public class ActionResolverAndroid implements ActionResolver {
	
	Handler uiHandler;
    Context appContext;
    
    GalleryUtil gu;
    
    public ActionResolverAndroid(Context appContext) {
    	uiHandler = new Handler();
    	this.appContext = appContext;
    	gu = new GalleryUtil(appContext);
    }

    ArrayList<String> outFilePaths;
    
	@Override
	public ArrayList<String> getFilePaths() {
		outFilePaths = null;

		new ThreadRunAndBlock(uiHandler, new ThreadRunAndBlockAction() {
			
			@Override
			public void runAction() {
				outFilePaths = gu.getFilePaths();
				
			}
		}).runAndBlock();
				
		return outFilePaths;
	}

	@Override
	public Pixmap getImagePixmap(String filePath) {
	
		return gu.getImagePixmap(filePath);
	}
}
