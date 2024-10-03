package h00;

import fopbot.Robot;
import fopbot.Transition;
import fopbot.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
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
import static h00.Utils.subtaskToIndex;
import static h00.Utils.toRobotActions;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class MainTest {

    private static final List<Map<Utils.RobotSpec, List<Transition>>> SUBTASK_ROBOT_TRANSITIONS = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        Main.delay = 0;

        for (int i = 0; i < 10; i++) {
            setup(i);
        }
    }

    private static void setup(int subtask) {
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
            .getTrace(getRobot(ALFRED))
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

        testMovements(params, "H0.4");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_5_1.json")
    public void testH0_5_1(JsonParameterSet params) {
        testMovements(params, "H0.5.1");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_5_2.json")
    public void testH0_5_2(JsonParameterSet params) {
        testMovements(params, "H0.5.2");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_5_3.json")
    public void testH0_5_3(JsonParameterSet params) {
        testMovements(params, "H0.5.3");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_6_1.json")
    public void testH0_6_1(JsonParameterSet params) {
        testMovements(params, "H0.6.1");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_6_2.json")
    public void testH0_6_2(JsonParameterSet params) {
        testMovements(params, "H0.6.2");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_6_3.json")
    public void testH0_6_3(JsonParameterSet params) {
        testMovements(params, "H0.6.3");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_7_1.json")
    public void testH0_7_1(JsonParameterSet params) {
        testMovements(params, "H0.7.1");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_7_2.json")
    public void testH0_7_2(JsonParameterSet params) {
        testMovements(params, "H0.7.2");
    }

    @ParameterizedTest
    @JsonParameterSetTest("H0_7_3.json")
    public void testH0_7_3(JsonParameterSet params) {
        testMovements(params, "H0.7.3");
    }

    /**
     * Tests Kaspar's and Alfred's movements.
     *
     * @param params  the expected movements (serialized)
     * @param subtask the subtask (see markers in main method)
     */
    private void testMovements(JsonParameterSet params, String subtask) {
        for (Utils.RobotSpec robotSpec : Utils.RobotSpec.values()) {
            List<Transition.RobotAction> expectedMovements = deserializeRobotActions(params.get(robotSpec.name));
            List<Transition.RobotAction> actualMovements = toRobotActions(SUBTASK_ROBOT_TRANSITIONS.get(subtaskToIndex(subtask)).get(robotSpec));

            assertEquals(expectedMovements, actualMovements, emptyContext(), result ->
                robotSpec.name + "'s movements are incorrect for task " + subtask);
        }
    }
}
