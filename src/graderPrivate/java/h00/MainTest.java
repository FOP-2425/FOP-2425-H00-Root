package h00;

import fopbot.Robot;
import fopbot.Transition;
import fopbot.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static h00.Utils.RobotSpec.ALFRED;
import static h00.Utils.RobotSpec.KASPAR;
import static h00.Utils.deserializeRobotActions;
import static h00.Utils.getRobot;
import static h00.Utils.toRobotActions;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainTest {

    private static final List<Map<Utils.RobotSpec, List<Transition>>> SUBTASK_ROBOT_TRANSITIONS = new ArrayList<>();

    @BeforeAll
    public static void setDelay() {
        Main.delay = 0;
    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, -1})
    public void setup(int subtask) {
        Main.runToSubtask = subtask;
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            System.err.println("Exception occurred while invoking main method, trying to continue...");
            e.printStackTrace(System.err);
        }

        List<Transition> kasperTransitions = World.getGlobalWorld()
            .getTrace(getRobot(KASPAR))
            .getTransitions();
        List<Transition> alfredTransitions = World.getGlobalWorld()
            .getTrace(getRobot(Utils.RobotSpec.ALFRED))
            .getTransitions();

        int kasperPreviousTransitions = 0;
        int alfredPreviousTransitions = 0;
        if (subtask > 0) {
            kasperPreviousTransitions = SUBTASK_ROBOT_TRANSITIONS.stream()
                .mapToInt(map -> map.get(KASPAR).size())
                .sum() - subtask;  // ignore NONE actions
            alfredPreviousTransitions = SUBTASK_ROBOT_TRANSITIONS.stream()
                .mapToInt(map -> map.get(ALFRED).size())
                .sum() - subtask;  // ignore NONE actions
        }
        Map<Utils.RobotSpec, List<Transition>> currentRobotActions = Map.of(
            KASPAR, kasperTransitions.subList(kasperPreviousTransitions, kasperTransitions.size()),
            ALFRED, alfredTransitions.subList(alfredPreviousTransitions, alfredTransitions.size())
        );
        SUBTASK_ROBOT_TRANSITIONS.add(currentRobotActions);
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_4.json")
    public void testRobotInit(JsonParameterSet params) {
        for (Utils.RobotSpec robotSpec : Utils.RobotSpec.values()) {
            List<Transition> transitions = SUBTASK_ROBOT_TRANSITIONS.get(0).get(robotSpec);
            Robot robot = transitions.get(0).robot;  // getRobot(RobotSpec) won't work because it already moved
            Context context = contextBuilder()
                .add("reference robot", robotSpec)
                .add("actual robot", robot)
                .build();

            assertEquals(robotSpec.initialX, robot.getX(), context, result -> robotSpec.name + "'s x-coordinate is incorrect");
            assertEquals(robotSpec.initialY, robot.getY(), context, result -> robotSpec.name + "'s y-coordinate is incorrect");
            assertEquals(robotSpec.initialDirection, robot.getDirection(), context, result -> robotSpec.name + "'s direction is incorrect");
            assertEquals(robotSpec.initialCoins, robot.getNumberOfCoins(), context, result -> robotSpec.name + " has an incorrect number of coins");
            assertEquals(robotSpec.robotFamily, robot.getRobotFamily(), context, result -> robotSpec.name + "'s RobotFamily is incorrect");
        }

        testMovements(params, 0);
    }

    /**
     * Tests Kaspar's and Alfred's movements.
     *
     * @param params  the expected movements (serialized)
     * @param subtask the subtask number (see markers in main method)
     */
    private void testMovements(JsonParameterSet params, int subtask) {
        for (Utils.RobotSpec robotSpec : Utils.RobotSpec.values()) {
            List<Transition.RobotAction> expectedMovements = deserializeRobotActions(params.get(robotSpec.name));
            List<Transition.RobotAction> actualMovements = toRobotActions(SUBTASK_ROBOT_TRANSITIONS.get(subtask).get(robotSpec));

            assertEquals(expectedMovements, actualMovements, emptyContext(), result -> robotSpec.name + "'s movements are incorrect");
        }
    }
}
