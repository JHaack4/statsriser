package com.jerhis.statsriser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class ScreenSplash implements Screen {
	
	final MyGdxGame game;
	 
	OrthographicCamera camera;
	
	long startTime;
	Texture splash;
 
	public ScreenSplash(final MyGdxGame gam) {
		game = gam;
 
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		
		splash = new Texture(Gdx.files.internal("splash.png"));
 
		startTime = System.currentTimeMillis();
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
 
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		//game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
		game.batch.draw(splash, 0, 430);
		game.batch.end();
 
		//if (Gdx.input.isTouched()) {
			//game.setScreen(new ScreenGame(game));
			//dispose();
		//}
		if (System.currentTimeMillis() > 2000 + startTime)
		{
			game.setScreen(new ScreenGame(game));
			dispose();
		}
	}
 
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void pause() {
	}
 
	@Override
	public void resume() {
	}
 
	@Override
	public void dispose() {
	}
	
}
	