package com.jerhis.statsriser;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ScreenGame implements Screen, InputProcessor {
	
	final MyGdxGame game;
	OrthographicCamera camera;

    static StatsGame statsGame = new StatsGame();
	TextureAtlas textures;
    TextureRegion black, plats[] = new TextureRegion[6], lemon, pause;
	AtlasRegion bg, pauseicon, white;
    Texture checked, unchecked;
	
	boolean right = false, left = false, paused = true, fps = false;
	int highScore = 0;
	static Sound bounceSound;
    static boolean question = false;
    static float result = -1;

	public ScreenGame(final MyGdxGame gam) {
		game = gam;
		Gdx.input.setInputProcessor(this);
 
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		
		bounceSound = Gdx.audio.newSound(Gdx.files.internal("bounce.mp3"));
		//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		textures = new TextureAtlas("gametextures.txt");
		bg = textures.findRegion("gamebgike");
		lemon = new TextureRegion(new Texture(Gdx.files.internal("newland.png")));
		plats[0] = textures.findRegion("superplatike");
		plats[1] = textures.findRegion("basicplatike");
		plats[2] = textures.findRegion("movingplatike");
		plats[3] = textures.findRegion("vanishplatike");
		plats[4] = textures.findRegion("riserplatike");

		pause = new TextureRegion(new Texture(Gdx.files.internal("start_p.png")));
		pauseicon = textures.findRegion("pauseicon");
		white = textures.findRegion("white");

        plats[5] = new TextureRegion(new Texture(Gdx.files.internal("statsplat.png")));

        black = new TextureRegion(new Texture(Gdx.files.internal("black.png")));
        checked = new Texture(Gdx.files.internal("checked.png"));
        unchecked = new Texture(Gdx.files.internal("unchecked.png"));

		highScore = game.prefs.getInteger("best", 0);
	}
 
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.86f, .86f,.86f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
 
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

        if (question) {
            statsGame.update(delta);
        }
		else if (!paused) {
			GameDisplay.update(right,left,delta*100);
			if (!GameDisplay.guy.checkDeath()) {
				if ((int)GameDisplay.guy.currentScore > highScore) {
					game.prefs.putInteger("best", (int)GameDisplay.guy.currentScore);
					game.prefs.flush();
					highScore = game.prefs.getInteger("best", 0);
				}
                GameDisplay.reset();
				GameDisplay.update(false, false, 0);
				paused = true;
			}
		}
		
		int score = (int)GameDisplay.guy.currentScore;
		
		game.batch.begin();
		
		game.batch.draw(bg, 0, 800 - ((score/3) % 1800));
		game.batch.draw(bg, 0, 800 - ((score/3 + 900) % 1800));

		game.batch.draw(pickLemon(), (int)GameDisplay.guyCoord.x - 20, (int)GameDisplay.guyCoord.y);
		for (int k = 0; k < GameDisplay.platforms.size(); k++) {
			Platform p = GameDisplay.platforms.get(k);
			game.batch.draw(plats[p.type - 1], 
					(int)p.c.x - 40, 
					(int)(p.c.y - GameDisplay.guy.deathHeight));
		}
		game.batch.draw(pauseicon,10,800-50);
		game.font.draw(game.batch, "Score: " + score, 70, 795);

        if (question) {
            game.batch.draw(black,0,0,0,0,32,32,15,30,0);
            statsGame.draw(game.wfont,game.batch, checked, unchecked);
        }
		//if (highScore > 0) 
			game.font.draw(game.batch, "Best: " + highScore, 70, 760);
		//if (fps) game.font.draw(game.batch, "fps: " + ((int)(100/delta))/100.0, 350, 795);
		//game.font.draw(game.batch, "preg: " + game.prefs.getInteger("best", -1), 70, 730);
		if (paused) game.batch.draw(pause, 0, 0);
		
		game.batch.end();

	}
	
	private TextureRegion pickLemon() {
		return lemon;
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
		textures.dispose();
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (question) {
            statsGame.touchDown(x,y,pointer);
            return false;
        }

		if (paused) {
			paused = false;
		}
		else {
			if (x < 50 && y > 800 - 50) {
				paused = true;
				return false;
			}
			right = false;
			left = false;
			if (x >= 240)
				right = true;
			if (x < 240)
				left = true;
		}
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (question) return false;
		
		if (paused) {
			if (x > 480-50 && y < 50)
				fps = !fps;
		}
		else {
			right = false;
			left = false;
		}
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		camera.unproject(pos);
		int x = (int) pos.x, y = (int) pos.y;

        if (question) {
            statsGame.touchDragged(x,y,pointer);
            return false;
        }
		
		if (paused) return false;
		right = false;
		left = false;
		if (x >= 240)
			right = true;
		if (x < 240)
			left = true;
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

