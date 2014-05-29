package com.jerhis.statsriser;

//the vanishing green platform
public class VanishPlatform extends Platform {
    public VanishPlatform(Coord c) {
        super(c);
        type=4;
    }

    @Override
    public void onCollision(ScribbleGuy guy) {
        basicCollisionAction(guy);
        c.y = -100;
    }

    @Override
    public void update(float deltaTime) {

    }
}
