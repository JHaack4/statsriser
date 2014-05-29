package com.jerhis.statsriser;

import java.util.ArrayList;

//the lemon guy's stuff
public class ScribbleGuy {

  public Coord c;

  public double deathHeight, currentScore;
  public double velocity;
  public double movement;

  public final double GRAVITY = -0.5;
  public final double RESISTANCE = 0.75 / 3.15;
  public final double MAX_MOVEMENT = 8;
  public final double MAX_VELOCITY = 17;
  public final double COLLISION_HEIGHT = 20;
  public final double COLLISION_WIDTH = -10 + 80/2 + 40;
  public final double DEATH_BUFFER = 400;


  public ScribbleGuy()
  {
      c = new Coord();
      c.x = 480 / 2;
      c.y = -100;
      deathHeight = 0;
      currentScore = 0;
      velocity = MAX_VELOCITY * 1.3;
  }

  public void updatePosition(boolean right, boolean left, float deltaTime)
  {
      if (right)
          movement = MAX_MOVEMENT;
      if (left)
          movement = -MAX_MOVEMENT;

      c.x += movement * deltaTime * 0.57;
      movement *= RESISTANCE * deltaTime * 0.57;

      if (c.x > 479)
          c.x = 1;
      if (c.x < 1)
          c.x = 479;

      c.y += velocity * deltaTime * 0.57;
      velocity += GRAVITY * deltaTime * 0.57;

      if (currentScore < c.y)
          currentScore = c.y;

      if (deathHeight < c.y - DEATH_BUFFER)
          deathHeight = c.y - DEATH_BUFFER;
  }

  public boolean checkDeath() {
      return c.y >= deathHeight - 200 || deathHeight == 0;
  }

  public void checkCollision(ArrayList<Platform> platforms, float deltaTime)
  {
      for(Platform p: platforms)
      {
          if ( Math.abs(p.c.x - c.x) < COLLISION_WIDTH && Math.abs(p.c.y - c.y) < COLLISION_HEIGHT && velocity <= 0)
              p.onCollision(this);
          p.update(deltaTime);
      }
  }

  public double getMV()
  {
      return MAX_VELOCITY;
  }

}
