package com.jerhis.statsriser;

public class QuestionPlatform extends Platform {

    public QuestionPlatform(Coord c) {
        super(c);
        type=6;
    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        if (Math.random() < 0.04)
            guy.velocity = guy.getMV() * 7.02;
        else
            guy.velocity = guy.getMV() * 2.98;
        ScreenGame.question = true;
        ScreenGame.statsGame.begin();
        c.y = -100;
    }

    @Override
    public void update(float deltaTime)
    {

    }

}
