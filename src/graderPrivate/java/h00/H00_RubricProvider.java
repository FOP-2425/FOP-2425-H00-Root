package h00;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H00_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H00 | Hands on mit Java & FopBot").addChildCriteria(
            Criterion.builder()
                .shortDescription("H0.1 | Matrickelnummer in Moodle")
                .build(),
            Criterion.builder()
                .shortDescription("H0.4 | Initializing FOPBot")
                .addChildCriteria(
                    criterion(
                        "Der Konstruktor von Robot wurde für Alfred und Kaspar mit korrekten Parametern aufgerufen."
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H0.5 | Let’s start with turning")
                .addChildCriteria(
                    criterion(
                        "Beide Roboter haben die korrekte Anzahl an Drehungen durch geführt. "
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
                        "Beide Roboter haben die korrekten Bewegungen durchgeführt (move, turnLeft, pick, put). "
                    ),
                    criterion(
                        "Es wurde genau eine zusätzliche for-Schleife benutzt.",
                        -1
                    )
                )
                .minPoints(0)
                .build(),
            Criterion.builder()
                .shortDescription("H0.7 | Let’s repeat that ")
                .addChildCriteria(
                    criterion(
                        "Beide Roboter haben die korrekten Bewegungen durchgeführt (move, turnLeft, pick, put). "
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