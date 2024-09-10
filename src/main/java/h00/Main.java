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
        Robot picker = new Robot(0, 3, Direction.DOWN, 0, RobotFamily.SQUARE_GREEN);
        Robot putter = new Robot(0, 0, Direction.DOWN, 4, RobotFamily.SQUARE_BLUE);

        // TODO: H0.5.1 - Turning with repeated instructions
        picker.turnLeft();
        picker.turnLeft();

        // TODO: H0.5.2 - Turning with for-loop
        int numberOfTurns = 2;
        for (int i = 0; i < numberOfTurns; i++) {
            picker.turnLeft();
        }

        // TODO: H0.5.3 - Turning with while-loop
        while (!putter.isFacingRight()) {
            putter.turnLeft();
        }

        // TODO: H0.6.1 - Putting with repeated instructions
        putter.putCoin();
        putter.move();
        putter.move();
        putter.move();
        putter.turnLeft();

        // TODO: H0.6.2 - Picking with repeated instructions
        picker.move();
        picker.move();
        picker.move();
        picker.pickCoin();
        picker.turnLeft();

        // TODO: H0.6.3 - Putting with for-loop
        putter.putCoin();
        int numberOfSteps = 3;
        for (int i = 0; i < numberOfSteps; i++) {
            putter.move();
        }
        putter.turnLeft();

        // TODO: H0.6.4 - Picking with while-loop
        while (picker.isFrontClear()) {
            picker.move();
        }
        picker.pickCoin();
        picker.turnLeft();

        // TODO: H0.6.5 - Picking and putting with while-loop
        putter.putCoin();
        while (putter.isFrontClear()) {
            picker.move();
            putter.move();
        }
        picker.pickCoin();
        putter.turnLeft();
        picker.turnLeft();

        // TODO: H0.6.6 - Reputting with reversed for-loop
        for (int i = picker.getNumberOfCoins(); i > 0; i--) {
            picker.putCoin();
            picker.move();
        }
        picker.turnLeft();
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
