package com.jerhis.statsriser;

import java.util.ArrayList;

//the platform framework
public abstract class Platform {

    public Coord c;
    public int type;

    public Platform(Coord c)
    {
        this.c = c;
    }

    public static int previous = 0;
    public static Platform spawn(int location)
    {
    	if (previous == 5) {
    		previous = 0;
    		return null;
    	}

    	int randX = (int) (Math.random() * 400 - 200);
    	
        //defines sections
        int section = 0;
        if (location >= 2500) section = 1;
        if (location >= 5000) section = 2;
        if (location >= 20000) section = 3;
        if (location >= 40000) section = 4;
        if (location >= 70000) section = 5;
        if (location >= 100000) section = 6;
        
        if (previous == 3)
    		if (Math.random() < section * .15)
    			return (new MovingPlatform(new Coord(240 + (int) (randX * .5),location)));
    	
    	if (previous == 4)
    		if (Math.random() < section * .15)
    			return (new VanishPlatform(new Coord(240 + randX, location)));

        //defines the gap between platforms
        int[] gap = {25, 50, 95, 145, 210, 230, 250};

        //defines the spawn frequency
        int[][] percent = {
        		{1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,4,6},
                {1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,4,4,6},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,4,4,4,6},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,4,4,4,5,6,6},
                {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,4,4,4,5,6,6},
                {1,2,2,2,2,2,2,2,2,2,2,3,3,3,3,4,4,4,4,5,5,6,6},
                {1,1,1,2,2,2,3,4,5,5,5,5,6,6}};

		int randType = (int) (Math.random() * percent[section].length);
		previous = percent[section][randType];
		switch (percent[section][randType]) {
		case 1:
			return (new SuperPlatform(new Coord(240 + randX, location)));

		case 3:
			return (new MovingPlatform(new Coord(240 + (int) (randX * .5),location)));

		case 4:
			return (new VanishPlatform(new Coord(240 + randX, location)));

		case 5:
			return (new RisePlatform(new Coord(240 + randX * .2, location),
					gap[section], location + 2500));

        case 6: return new QuestionPlatform(new Coord(240 + randX, location));

		case 2: default:
			return (new BasicPlatform(new Coord(240 + randX, location)));
		}
        
    }

    //will define what happens when the platform is hit
    //modifies itself and the guy
    public abstract void onCollision(ScribbleGuy guy);
    public abstract void update(float deltaTime);

    //basic action
    public void basicCollisionAction(ScribbleGuy guy)
    {
        guy.velocity = guy.getMV();
        //ScreenGame.bounceSound.play();
    }

}
