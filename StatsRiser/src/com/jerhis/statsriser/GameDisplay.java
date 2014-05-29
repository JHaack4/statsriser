package com.jerhis.statsriser;

import java.util.ArrayList;

//this class handles all game movement, collisions, spawning, ect
public class GameDisplay {

  public static ScribbleGuy guy = new ScribbleGuy();
  public static int spawnLocation = 0, gap = 25;
  public static Coord guyCoord = new Coord(0,-200);

  public static ArrayList<Platform> platforms = new ArrayList<Platform>();

	public static void update(boolean right, boolean left, float deltaTime) {
		guy.updatePosition(right, left, deltaTime);
		spawnPlatforms();
		guyCoord = new Coord(guy.c.x, (guy.c.y - guy.deathHeight));
		guy.checkCollision(platforms, deltaTime);
	}

	// this spawns platforms in the block 5000 ahead from the current block
	private static void spawnPlatforms() {
		while (guy.currentScore > spawnLocation - 400 || spawnLocation <= 800) {
			int section = 0;
			if (spawnLocation >= 2500)
				section = 1;
			if (spawnLocation >= 5000)
				section = 2;
			if (spawnLocation >= 20000)
				section = 3;
			if (spawnLocation >= 40000)
				section = 4;
			if (spawnLocation >= 70000)
				section = 5;
			if (spawnLocation >= 100000)
				section = 6;
			// defines the gap between platforms
			int[] gaps = { 25, 50, 95, 145, 210, 230, 250 };
			gap = gaps[section];

			Platform p = Platform.spawn(spawnLocation);

			spawnLocation += gap;

			if (p != null) {
				platforms.add(p);;
			}
		}
		while (platforms.size() > 0 && platforms.get(0).c.y < guy.currentScore - 450) {
			platforms.remove(0);
		}
	}

	public static void reset() {
		guy = new ScribbleGuy();
		platforms.clear();
		spawnLocation = 0;
		guyCoord = new Coord(0, -100);
	}
}