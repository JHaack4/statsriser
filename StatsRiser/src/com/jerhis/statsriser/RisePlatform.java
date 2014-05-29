package com.jerhis.statsriser;

//the red platform that rises
public class RisePlatform extends Platform {

    public int diff, end;

    public RisePlatform(Coord c, int gap, int end) {
        super(c);
        type=5;
        this.diff = gap;
        this.end = end;
    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        basicCollisionAction(guy);
        c.y += diff - 25;
        c.x += Math.random() * 250 - 125;
        if (c.y > end) c.y = 0;
        if (c.x > 440 || c.x < 40) c.x = Math.random() * 200 + 140;
    }

    @Override
    public void update(float deltaTime) {

    }
}
