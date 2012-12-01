package com.arena3.cor.PhotoSlide;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;

public interface ActionResolver {

	public ArrayList<String> getFilePaths();

	Pixmap getImagePixmap(String filePath);

}
