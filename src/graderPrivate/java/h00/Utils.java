package h00;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.Transition;
import fopbot.World;

import java.util.List;

public class Utils {

    /**
     * Information on {@link Robot}s needed for the exercise (id, name and expected initial values).
     */
    public enum RobotSpec {
        KASPAR(0, "Kaspar", 0, 0, Direction.DOWN, 4, RobotFamily.SQUARE_BLUE),
        ALFRED(1, "Alfred", 0, 3, Direction.DOWN, 0, RobotFamily.SQUARE_GREEN);

        public final int id;
        public final String name;

        public final int initialX;
        public final int initialY;
        public final Direction initialDirection;
        public final int initialCoins;
        public final RobotFamily robotFamily;

        RobotSpec(int id, String name, int initialX, int initialY, Direction initialDirection, int initialCoins, RobotFamily rf) {
            this.id = id;
            this.name = name;
            this.initialX = initialX;
            this.initialY = initialY;
            this.initialDirection = initialDirection;
            this.initialCoins = initialCoins;
            this.robotFamily = rf;
        }
    }

    /**
     * Get a specific robot from the world.
     *
     * @param robotSpec the {@link RobotSpec} describing the robot to get
     * @return the robot
     */
    public static Robot getRobot(RobotSpec robotSpec) {
        return World.getGlobalWorld()
            .getAllFieldEntities()
            .stream()
            .filter(Robot.class::isInstance)
            .map(Robot.class::cast)
            .filter(robot -> robot.getId().equals(Integer.toString(robotSpec.id)))
            .findAny()
            .orElseThrow();
    }

    /**
     * Deserialize {@link fopbot.Transition.RobotAction} from their string representation.
     *
     * @param serializedRobotActions the serialized RobotActions
     * @return a list of {@link fopbot.Transition.RobotAction}s
     */
    public static List<Transition.RobotAction> deserializeRobotActions(List<String> serializedRobotActions) {
        return serializedRobotActions.stream()
            .map(Transition.RobotAction::valueOf)
            .toList();
    }

    /**
     * Maps {@link Transition}s to their respective {@link fopbot.Transition.RobotAction}s.
     *
     * @param transitions a list of Transitions
     * @return a list of RobotActions
     */
    public static List<Transition.RobotAction> toRobotActions(List<Transition> transitions) {
        return transitions.stream()
            .map(transition -> transition.action)
            .toList();
    }
}
