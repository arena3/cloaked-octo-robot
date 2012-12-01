package com.arena3.cor.PhotoSlide;

import com.arena3.cor.PhotoSlide.PhotoManager.Controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class PhotoSlideScreen implements Screen {

	private PhotoSlide photoSlide;

	private PhotoManager manager;
	private PhotoManager.Controller controller;
	private PhotoManager.Renderer renderer;
	
	private int width, height;
	
	public PhotoSlideScreen(PhotoSlide photoSlide) {
		this.photoSlide = photoSlide;
	}
	
	@Override
	public void show() {
		manager = new PhotoManager(photoSlide);
		controller = manager.getController();
		renderer = manager.getRenderer();

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {

		renderer.setSize(width, height);
		
		this.width = width;
		this.height = height;
		
		

	}
	

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
