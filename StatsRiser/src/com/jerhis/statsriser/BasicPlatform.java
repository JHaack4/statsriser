package com.jerhis.statsriser;

//the yellow platform with basic action
public class BasicPlatform extends Platform {

    public BasicPlatform(Coord c) {
        super(c);
        type=2;
    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        basicCollisionAction(guy);
    }

    @Override
    public void update(float deltaTime)
    {

    }

}
