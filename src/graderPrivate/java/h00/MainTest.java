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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static h00.Utils.RobotSpec.ALFRED;
import static h00.Utils.RobotSpec.KASPAR;
import static h00.Utils.deserializeRobotActions;
import static h00.Utils.getRobot;
import static h00.Utils.toRobotActions;

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
}
