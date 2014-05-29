package com.jerhis.statsriser;

//the black super high platform
public class SuperPlatform extends Platform {
    public SuperPlatform(Coord c) {
        super(c);
        type=1;
    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        if (Math.random() < 0.04)
            guy.velocity = guy.getMV() * 7.02;
        else
            guy.velocity = guy.getMV() * 2.98;

       // ScreenGame.bounceSound.play();
    }

    @Override
    public void update(float deltaTime) {

    }
}
