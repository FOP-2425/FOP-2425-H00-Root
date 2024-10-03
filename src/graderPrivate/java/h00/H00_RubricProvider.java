package h00;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H00_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H00 | Hands on mit Java & FopBot").addChildCriteria(
            Criterion.builder()
                .shortDescription("H0.1 | Matrikelnummer in Moodle")
                .build(),
            Criterion.builder()
                .shortDescription("H0.4 | Initializing FOPBot")
                .addChildCriteria(
                    criterion(
                        "Der Konstruktor von Robot wurde für Alfred und Kaspar mit korrekten Parametern aufgerufen.",
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testRobotInit", JsonParameterSet.class))
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H0.5 | Let’s start with turning")
                .addChildCriteria(
                    criterion(
                        "Beide Roboter haben die korrekte Anzahl an Drehungen durch geführt.",
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_5_1", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_5_2", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_5_3", JsonParameterSet.class))
                    ),
                    criterion(
                        "Es wurden bis hierhin genau eine for- und eine while-Schleife benutzt.",
                        -1
                    )
                )
                .minPoints(0)
                .build(),
            Criterion.builder()
                .shortDescription("H0.6 | Now we move, put, and pick")
                .addChildCriteria(
                    criterion(
                        "Beide Roboter haben die korrekten Bewegungen durchgeführt (move, turnLeft, pick, put).",
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_6_1", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_6_2", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_6_3", JsonParameterSet.class))
                    ),
                    criterion(
                        "Es wurde genau eine zusätzliche for-Schleife benutzt.",
                        -1
                    )
                )
                .minPoints(0)
                .build(),
            Criterion.builder()
                .shortDescription("H0.7 | Let’s repeat that")
                .addChildCriteria(
                    criterion(
                        "Beide Roboter haben die korrekten Bewegungen durchgeführt (move, turnLeft, pick, put).",
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_7_1", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_7_2", JsonParameterSet.class)),
                        JUnitTestRef.ofMethod(() -> MainTest.class.getDeclaredMethod("testH0_7_3", JsonParameterSet.class))
                    ),
                    criterion(
                        "Es wurden genau eine zusätzliche for-Schleife und zwei zusätzliche while-Schleifen benutzt.",
                        -1
                    ),
                    criterion(
                        "Die Läufervariable einer for-Schleife verringert sich.",
                        -1
                    )
                )
                .minPoints(0)
                .build()
        ).build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

}
