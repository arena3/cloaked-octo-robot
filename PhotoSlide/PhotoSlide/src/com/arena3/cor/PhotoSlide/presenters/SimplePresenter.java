package com.arena3.cor.PhotoSlide.presenters;

import java.util.ArrayList;

import com.arena3.cor.PhotoSlide.ActionResolver;
import com.arena3.cor.PhotoSlide.PhotoManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimplePresenter implements Presenter {

	private Sprite sprite;
	private SpriteBatch spriteBatch;
	
	private PhotoManager manager;
	private ActionResolver actionResolver;
	
	public SimplePresenter(PhotoManager manager)
	{
		this.manager = manager;
		this.actionResolver = this.manager.getPhotoSlide().getActionResolver();
		this.spriteBatch = this.manager.getRenderer().getSpriteBatch();
		
		ArrayList<String> filePaths = actionResolver.getFilePaths();
		
		Pixmap pm = actionResolver.getImagePixmap(filePaths.get(0));
		Texture texture = new Texture(pm);
		this.sprite = new Sprite(texture);
		this.sprite.rotate(45);
	}
	
	@Override
	public void render() {
		sprite.draw(spriteBatch);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

}
