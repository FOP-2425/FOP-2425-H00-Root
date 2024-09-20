package h00;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;

/**
 * Main entry point in executing the program.
 */
public class Main {
    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        // This sets up the FOPBot World with a 4x4 grid. (DO NOT TOUCH)
        setupWorld();

        // TODO: H0.4 - Initializing FOPBot
        Robot kasper = new Robot(0, 0, Direction.DOWN, 4, RobotFamily.SQUARE_BLUE);
        Robot alfred = new Robot(0, 3, Direction.DOWN, 0, RobotFamily.SQUARE_GREEN);

        // TODO: H0.5.1 - Turning with repeated instructions
        alfred.turnLeft();
        alfred.turnLeft();

        // TODO: H0.5.2 - Turning with for-loop
        int numberOfTurns = 2;
        for (int i = 0; i < numberOfTurns; i++) {
            alfred.turnLeft();
        }

        // TODO: H0.5.3 - Turning with while-loop
        while (!kasper.isFacingRight()) {
            kasper.turnLeft();
        }

        // TODO: H0.6.1 - Putting with repeated instructions
        kasper.putCoin();
        kasper.move();
        kasper.move();
        kasper.move();
        kasper.turnLeft();

        // TODO: H0.6.2 - Picking with repeated instructions
        alfred.move();
        alfred.move();
        alfred.move();
        alfred.pickCoin();
        alfred.turnLeft();

        // TODO: H0.6.3 - Putting with for-loop
        kasper.putCoin();
        int numberOfSteps = 3;
        for (int i = 0; i < numberOfSteps; i++) {
            kasper.move();
        }
        kasper.turnLeft();

        // TODO: H0.7.1 - Picking with while-loop
        while (alfred.isFrontClear()) {
            alfred.move();
        }
        alfred.pickCoin();
        alfred.turnLeft();

        // TODO: H0.7.2 - Picking and putting with while-loop
        kasper.putCoin();
        while (kasper.isFrontClear()) {
            alfred.move();
            kasper.move();
        }
        alfred.pickCoin();
        kasper.turnLeft();
        alfred.turnLeft();

        // TODO: H0.7.3 - Reputting with reversed for-loop
        for (int i = alfred.getNumberOfCoins(); i > 0; i--) {
            alfred.putCoin();
            alfred.move();
        }
        alfred.turnLeft();
    }

    public static void setupWorld() {
        // variable representing width/size of world
        final int worldSize = 4;

        // setting world size symmetrical, meaning height = width
        World.setSize(worldSize, worldSize);

        // speed of how fast the world gets refreshed (e.g. how fast the robot(s) act)
        // the lower the number, the faster the refresh
        World.setDelay(300);

        // make it possible to see the world window
        World.setVisible(true);
    }
}
