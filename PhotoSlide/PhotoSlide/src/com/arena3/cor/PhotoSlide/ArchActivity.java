package com.arena3.cor.PhotoSlide;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;

public interface ArchActivity {

	ArrayList<String> getImageFilePaths();
	
	Pixmap getImagePixmap(String filePath);
}
