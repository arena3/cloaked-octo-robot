package com.arena3.cor.PhotoSlide;

import com.badlogic.gdx.Game;

public class PhotoSlide extends Game {

	private ActionResolver actionResolver;
	
	public ActionResolver getActionResolver() {
		return actionResolver;
	}

	public void setActionResolver(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	private PhotoSlideScreen photoSlideScreen;

	public PhotoSlide(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}

	@Override
	public void create() {
		photoSlideScreen = new PhotoSlideScreen(this);
		
		setScreen(photoSlideScreen);
	}
}