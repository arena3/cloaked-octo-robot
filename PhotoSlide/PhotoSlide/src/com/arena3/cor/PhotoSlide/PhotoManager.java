package com.arena3.cor.PhotoSlide;

import com.arena3.cor.PhotoSlide.presenters.Presenter;
import com.arena3.cor.PhotoSlide.presenters.SimplePresenter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PhotoManager {

	private Presenter[] presenters;
	private Controller controller;
	private Renderer renderer;
	private PhotoSlide photoSlide;
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public PhotoManager(PhotoSlide photoSlide) {
		
		this.photoSlide = photoSlide;
		this.renderer = new Renderer(this);
		this.controller = new Controller(this);
		
		this.presenters = new Presenter[1];
		this.presenters[0] = new SimplePresenter(this);
		
		this.controller.setCurrentPresenter(this.presenters[0]);

	}

	public PhotoSlide getPhotoSlide() {
		return photoSlide;
	}

	public void setPhotoSlide(PhotoSlide photoSlide) {
		this.photoSlide = photoSlide;
	}

	public class Controller {
		
		private PhotoManager manager;

		private Presenter currentPresenter;
		
		public Controller(PhotoManager manager) {
			this.manager = manager;
		}

		public Presenter getCurrentPresenter() {
			return currentPresenter;
		}

		public void setCurrentPresenter(Presenter currentPresenter) {
			this.currentPresenter = currentPresenter;
		}

		public void update(float delta) {
			this.currentPresenter.update(delta);
			
		}
	}

	public class Renderer {
		
		private PhotoManager manager;
		
		private int width;
		private int height;

		private OrthographicCamera camera;

		private SpriteBatch spriteBatch;

		public SpriteBatch getSpriteBatch() {
			return spriteBatch;
		}

		public void setSpriteBatch(SpriteBatch spriteBatch) {
			this.spriteBatch = spriteBatch;
		}

		public Renderer(PhotoManager manager) {
			
			this.width = Gdx.graphics.getWidth();
			this.height = Gdx.graphics.getHeight();
			
			this.manager = manager;
			this.camera = new OrthographicCamera(width, height);
			this.camera.position.set(width/2f,height/2f, 0);
			this.camera.update();
			
			this.spriteBatch = new SpriteBatch();
			
		}

		public void setSize(int width, int height) {
			
			this.width = width;
			this.height = height;
			
		}

		public void render() {
			spriteBatch.begin();
			this.manager.controller.currentPresenter.render();
			spriteBatch.end();
			
		}
	}


}
