package com.jerhis.statsriser;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
 
public class MyGdxGame extends Game {
 
    SpriteBatch batch;
	BitmapFont font, wfont;
	Preferences prefs;
 
	public void create() {
		
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont(Gdx.files.internal("arial32.fnt"));
        wfont = new BitmapFont(Gdx.files.internal("myfont.fnt"));
		this.prefs = Gdx.app.getPreferences(".statsriser");
		this.setScreen(new ScreenSplash(this));
	}
 
	public void render() {
		super.render(); // important!
	}
 
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
 
}