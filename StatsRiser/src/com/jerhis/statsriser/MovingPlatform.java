package com.jerhis.statsriser;

//the blue moving platform
public class MovingPlatform extends Platform {

    public int center;
    public boolean right = Math.random() < .5;

    public MovingPlatform(Coord c) {
        super(c);
        type=3;
        center = (int)c.x;

    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        basicCollisionAction(guy);
    }

    @Override
    public void update(float deltaTime) {
        if (right)
        {
            c.x += 1 * deltaTime;
        }
        else
        {
            c.x -= 1 * deltaTime;
        }
        if (c.x >= center + 100)
            right = false;
        else if (c.x <= center - 100)
            right = true;
    }
}
